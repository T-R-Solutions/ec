use super::protocol::{before_commit, body, envelope, positive};
use super::{connect, random};
use ec_contracts::Failure;
use ec_server_orchestration as orchestration;
use serde_json::Value;
use tiny_http::Request;
pub(super) fn reconcile_once() -> Result<(), Failure> {
    let mut client = connect().map_err(|_| (500, "database"))?;
    let mut transaction = client.transaction().map_err(|_| (500, "database"))?;
    transaction
        .batch_execute("SET LOCAL lock_timeout='5s'; SET LOCAL statement_timeout='10s'")
        .map_err(|_| (500, "database"))?;
    orchestration::guard(&mut transaction)?;
    orchestration::reconcile(&mut transaction)?;
    transaction.commit().map_err(|_| (500, "database"))
}
pub(super) fn agent_route(
    request: &mut Request,
    mut transaction: postgres::Transaction<'_>,
    token: &str,
) -> Result<Value, Failure> {
    let payload = if request.method().as_str() == "POST" {
        Some(body(request)?)
    } else {
        None
    };
    orchestration::guard(&mut transaction)?;
    let url = request.url().to_owned();
    let route: Vec<_> = url.split('/').collect();
    let method = request.method().as_str().to_owned();
    let result = if method == "POST" && url == "/api/v1/agent/session" {
        // Validate credential before revealing capability diagnostics.
        // Session insertion rolls back if the subsequent envelope is rejected.
        let id = random().map_err(|_| (500, "random"))?;
        let session_token = random().map_err(|_| (500, "random"))?;
        let result = orchestration::session(&mut transaction, token, &id, &session_token)?;
        let payload = payload.as_ref().ok_or((400, "body"))?;
        envelope(payload, &["protocolVersion", "platform", "slots"])?;
        if payload["protocolVersion"] != ec_contracts::PROTOCOL_VERSION
            || payload["platform"] != "linux"
            || payload["slots"] != ec_contracts::AGENT_SLOTS
        {
            return Err((422, "capabilities"));
        }
        result
    } else {
        let owner = orchestration::authenticate(&mut transaction, token)?;
        if method == "POST" && url == "/api/v1/agent/reserve" {
            envelope(payload.as_ref().ok_or((400, "body"))?, &[])?;
            orchestration::reserve(&mut transaction, &owner)?
        } else if method == "GET" && url == "/api/v1/agent/attempts" {
            orchestration::owned_attempts(&mut transaction, &owner)?
        } else if method == "GET" && route.len() == 6 && route[4] == "attempts" {
            orchestration::owned_attempt(&mut transaction, &owner, positive(route[5])?)?
        } else if method == "GET"
            && route.len() == 7
            && route[4] == "attempts"
            && route[6] == "output"
        {
            let id = positive(route[5])?;
            orchestration::owned_attempt(&mut transaction, &owner, id)?;
            ec_server_results::read(&mut transaction, id)?
        } else if method == "POST"
            && route.len() == 7
            && route[4] == "attempts"
            && [
                "logs",
                "artifact-start",
                "artifact-chunk",
                "artifact-finish",
                "finish",
            ]
            .contains(&route[6])
        {
            let payload = payload.as_ref().ok_or((400, "body"))?;
            let keys: &[&str] = match route[6] {
                "logs" => &["fence", "sequence", "stream", "data"],
                "artifact-start" => &["fence", "size", "sha256"],
                "artifact-chunk" => &["fence", "sequence", "data"],
                "artifact-finish" => &["fence"],
                _ => &["fence", "outcome", "exitCode", "output"],
            };
            envelope(payload, keys)?;
            ec_server_results::mutate(
                &mut transaction,
                &owner,
                positive(route[5])?,
                route[6],
                payload,
            )?
        } else if method == "POST"
            && route.len() == 7
            && route[4] == "attempts"
            && ["heartbeat", "running", "result"].contains(&route[6])
        {
            let payload = payload.as_ref().ok_or((400, "body"))?;
            envelope(
                payload,
                if route[6] == "result" {
                    &["fence", "outcome", "exitCode"]
                } else {
                    &["fence"]
                },
            )?;
            orchestration::mutate(
                &mut transaction,
                &owner,
                positive(route[5])?,
                route[6],
                payload,
            )?
        } else {
            return Err((400, "route"));
        }
    };
    before_commit(request)?;
    transaction.commit().map_err(|_| (500, "database"))?;
    Ok(result)
}
