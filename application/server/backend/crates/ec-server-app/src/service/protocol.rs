#[cfg(feature = "test-failpoints")]
use super::http::header;
use ec_contracts::Failure;
#[cfg(test)]
use ec_contracts::valid_name as name;
use serde_json::{Value, json};
use std::io::Read;
use tiny_http::Request;
#[cfg(test)]
#[path = "../service_tests.rs"]
mod tests;
pub(super) fn validate_pipeline(request: &mut Request, project: &str) -> Result<Value, Failure> {
    let payload = body(request)?;
    envelope(&payload, &["yaml"])?;
    let yaml = payload["yaml"].as_str().ok_or((400, "yaml"))?;
    if yaml.len() > ec_contracts::MAX_INPUT_BYTES {
        return Err((413, "yaml_limit"));
    }
    let fields = match ec_server_configuration::parse(yaml) {
        Ok(f) => f,
        Err(d) => {
            return Ok(
                json!({"valid":false,"diagnostic":{"code":d.code,"message":d.message,"line":d.line,"column":d.column}}),
            );
        }
    };
    if let Err(d) = ec_server_projects::ProjectPipeline::validate(&fields) {
        return Ok(
            json!({"valid":false,"diagnostic":{"code":"schema","field":d.field,"message":d.message}}),
        );
    }
    if fields.get("project").map(String::as_str) != Some(project) {
        return Ok(
            json!({"valid":false,"diagnostic":{"code":"project_reference","field":"project","message":"Projeto difere do escopo"}}),
        );
    }
    Ok(json!({"valid":true,"projection":fields}))
}

pub(super) fn positive(s: &str) -> Result<i64, Failure> {
    s.parse::<i64>().ok().filter(|n| *n > 0).ok_or((400, "id"))
}
pub(super) fn envelope(payload: &Value, keys: &[&str]) -> Result<(), Failure> {
    if payload
        .as_object()
        .is_none_or(|o| o.len() != keys.len() || keys.iter().any(|k| !o.contains_key(*k)))
    {
        return Err((400, "envelope"));
    }
    Ok(())
}
pub(super) fn body(request: &mut Request) -> Result<Value, Failure> {
    let limit = if request.url().starts_with("/api/v1/agent/")
        && (request.url().ends_with("/logs") || request.url().ends_with("/artifact-chunk"))
    {
        ec_contracts::HTTP_OUTPUT_BODY_MAX_BYTES
    } else {
        ec_contracts::HTTP_BODY_MAX_BYTES
    };
    let mut bytes = Vec::new();
    request
        .as_reader()
        .take(limit + 1)
        .read_to_end(&mut bytes)
        .map_err(|_| (400, "body"))?;
    if bytes.len() as u64 > limit {
        return Err((413, "body_limit"));
    }
    serde_json::from_slice(&bytes).map_err(|_| (400, "json"))
}
pub(super) fn before_commit(request: &Request) -> Result<(), Failure> {
    #[cfg(not(feature = "test-failpoints"))]
    let _ = request;
    #[cfg(feature = "test-failpoints")]
    match header(request, "X-Ec-Test-Failure").as_str() {
        "before-commit" => return Err((500, "injected_before_commit")),
        "crash-before-commit" => std::process::exit(86),
        _ => {}
    }
    Ok(())
}
