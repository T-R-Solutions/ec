use super::agents::{agent_route, reconcile_once};
use super::projects::ProjectRequest;
use super::{AnyError, connect, name, random};
use ec_contracts::Failure;
use serde_json::{Value, json};
use std::{io::Read, sync::Arc};
use tiny_http::{Header, Request, Response, Server};
pub(super) fn serve() -> Result<(), AnyError> {
    let port = std::env::var("EC_PORT")
        .unwrap_or_else(|_| "8080".into())
        .parse::<u16>()?;
    let server = Arc::new(Server::http(("127.0.0.1", port))?);
    let stopping = Arc::new(std::sync::atomic::AtomicBool::new(false));
    // Only the local test build accepts EOF on its private stdin as shutdown.
    // Returning normally flushes LLVM profiles; no HTTP shutdown route is added.
    #[cfg(feature = "test-failpoints")]
    if std::env::var("EC_TEST_GRACEFUL_STOP").as_deref() == Ok("1") {
        let stopping = stopping.clone();
        std::thread::spawn(move || {
            let _ = std::io::stdin().read(&mut [0]);
            stopping.store(true, std::sync::atomic::Ordering::SeqCst);
        });
    }
    // No in-memory queue: startup and periodic reconciliation use the same database guard.
    reconcile_once().map_err(|_| "Reconciliação inicial falhou")?;
    #[cfg(feature = "test-failpoints")]
    let reconcile_enabled = std::env::var("EC_TEST_PAUSE_RECONCILER").as_deref() != Ok("1");
    #[cfg(not(feature = "test-failpoints"))]
    let reconcile_enabled = true;
    if reconcile_enabled {
        std::thread::spawn(|| {
            loop {
                std::thread::sleep(std::time::Duration::from_secs(1));
                if reconcile_once().is_err() {
                    eprintln!("orchestration reconciliation failed");
                }
            }
        });
    }
    let mut workers = Vec::new();
    let content_type = Header::from_bytes("Content-Type", "application/json")
        .map_err(|()| "Header Content-Type inválido")?;
    let cache_control = Header::from_bytes("Cache-Control", "no-store")
        .map_err(|()| "Header Cache-Control inválido")?;
    for _ in 0..4 {
        let server = server.clone();
        let stopping = stopping.clone();
        let content_type = content_type.clone();
        let cache_control = cache_control.clone();
        workers.push(std::thread::spawn(move || {
            while !stopping.load(std::sync::atomic::Ordering::SeqCst) {
                let mut request = match server.recv_timeout(std::time::Duration::from_millis(100)) {
                    Ok(Some(request)) => request,
                    Ok(None) => continue,
                    Err(_) => break,
                };
                let rid = random().unwrap_or_else(|_| "unavailable".into());
                let (status, value) = match handle(&mut request) {
                    Ok(value) => (200, value),
                    Err((status, code)) => {
                        (status, json!({"code":code,"message":code,"requestId":rid}))
                    }
                };
                #[cfg(feature = "test-failpoints")]
                if status == 200 && header(&request, "X-Ec-Test-Failure") == "after-commit" {
                    drop(request.into_writer());
                    continue;
                }
                let response = Response::from_string(value.to_string())
                    .with_status_code(status)
                    .with_header(content_type.clone())
                    .with_header(cache_control.clone());
                let _ = request.respond(response);
            }
        }));
    }
    for worker in workers {
        worker.join().map_err(|_| "Worker interrompido")?;
    }
    Ok(())
}
pub(super) fn header(request: &Request, key: &str) -> String {
    request
        .headers()
        .iter()
        .find(|h| h.field.as_str().as_str().eq_ignore_ascii_case(key))
        .map(|h| h.value.to_string())
        .unwrap_or_default()
}
fn handle(request: &mut Request) -> Result<Value, Failure> {
    let db = |_| (500, "database");
    let mut client = connect().map_err(db)?;
    let mut transaction = client.transaction().map_err(db)?;
    transaction.batch_execute("SET LOCAL application_name='ec-http'; SET LOCAL lock_timeout='5s'; SET LOCAL statement_timeout='10s'")
        .map_err(db)?;
    let auth = header(request, "Authorization");
    let token = auth.strip_prefix("Bearer ").ok_or((401, "session"))?;
    if request.url().starts_with("/api/v1/agent/") {
        return agent_route(request, transaction, token);
    }
    let actor = ec_server_access::authenticate(&mut transaction, token)
        .map_err(db)?
        .ok_or((401, "session"))?;
    let url = request.url().to_owned();
    let route: Vec<_> = url.split('/').collect();
    let method = request.method().as_str().to_owned();
    if url == "/api/v1/session" && method == "GET" {
        let row = transaction
            .query_one(
                "SELECT expires::text FROM sessions WHERE hash=$1",
                &[&ec_contracts::hash(token.as_bytes())],
            )
            .map_err(db)?;
        return Ok(json!({"identity":actor,"expires":row.get::<_,String>(0)}));
    }
    if url == "/api/v1/projects" && method == "GET" {
        let rows = transaction.query("SELECT p.name,g.role,p.published,p.effective FROM projects p JOIN grants g ON g.project=p.name WHERE g.identity=$1 ORDER BY p.name", &[&actor]).map_err(db)?;
        return Ok(
            json!({"projects":rows.iter().map(|request|json!({"project":request.get::<_,String>(0),"role":request.get::<_,String>(1),"published":request.get::<_,i64>(2),"effective":request.get::<_,i64>(3)})).collect::<Vec<_>>()}),
        );
    }
    if url == "/api/v1/session" && method == "DELETE" {
        // Upgrade own session lock; session revocation is serialized with in-flight publication.
        transaction
            .execute(
                "UPDATE sessions SET revoked=true WHERE hash=$1",
                &[&ec_contracts::hash(token.as_bytes())],
            )
            .map_err(db)?;
        transaction.commit().map_err(db)?;
        return Ok(json!({"revoked":true}));
    }
    if route.len() < 5 || route[..4] != ["", "api", "v1", "projects"] || !name(route[4]) {
        return Err((400, "route"));
    }
    let project = route[4];
    let role = ec_server_access::role(&mut transaction, project, &actor)
        .map_err(db)?
        .ok_or((404, "project"))?;
    ProjectRequest {
        actor: &actor,
        token,
        project,
        role: &role,
        method: &method,
        route: &route,
    }
    .handle(request, transaction)
}
