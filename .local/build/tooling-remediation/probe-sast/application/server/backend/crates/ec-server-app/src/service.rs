use ec_server_orchestration as orchestration;
use ec_server_projects::publication::{self, Candidate, Failure};
use postgres::{Client, NoTls};
use serde_json::{Value, json};
use std::{
    fs::OpenOptions,
    io::{Read, Write},
    os::unix::fs::OpenOptionsExt,
    sync::Arc,
};
use tiny_http::{Header, Request, Response, Server};
type AnyError = Box<dyn std::error::Error + Send + Sync>;
fn connect() -> Result<Client, postgres::Error> {
    Client::connect(&std::env::var("EC_DATABASE_URL").unwrap_or_default(), NoTls)
}
fn name(s: &str) -> bool {
    !s.is_empty()
        && s.len() <= 64
        && s.as_bytes()[0].is_ascii_lowercase()
        && !s.ends_with('-')
        && s.bytes()
            .all(|b| b.is_ascii_lowercase() || b.is_ascii_digit() || b == b'-')
}
fn random() -> Result<String, AnyError> {
    let mut b = [0; 32];
    std::fs::File::open("/dev/urandom")?.read_exact(&mut b)?;
    Ok(b.iter().map(|v| format!("{v:02x}")).collect())
}
/// Administrative commands require trusted local database access; none are HTTP routes.
pub fn run() -> Result<(), AnyError> {
    let args: Vec<String> = std::env::args().skip(1).collect();
    let mut client = connect()?;
    match args.first().map(String::as_str) {
        Some("migrate") if args.len() == 1 => {
            let mut tx = client.transaction()?;
            tx.batch_execute("SELECT pg_advisory_xact_lock(746231)")?;
            tx.batch_execute(
                "CREATE TABLE IF NOT EXISTS ec_migrations(version integer PRIMARY KEY)",
            )?;
            if tx
                .query_opt("SELECT version FROM ec_migrations WHERE version=1", &[])?
                .is_none()
            {
                tx.batch_execute(include_str!("../../../migrations/001_publication.sql"))?;
            }
            if tx
                .query_opt("SELECT version FROM ec_migrations WHERE version=2", &[])?
                .is_none()
            {
                tx.batch_execute(include_str!("../../../migrations/002_orchestration.sql"))?;
            }
            if tx
                .query_opt("SELECT version FROM ec_migrations WHERE version=3", &[])?
                .is_none()
            {
                tx.batch_execute(include_str!("../../../migrations/003_results.sql"))?;
            }
            tx.commit()?;
        }
        Some("provision") if args.len() == 6 => provision(&mut client, &args)?,
        Some("provision-agent") if args.len() == 4 => {
            if !name(&args[1]) || !name(&args[2]) {
                return Err("Agent inválido".into());
            }
            let token = random()?;
            let mut file = OpenOptions::new()
                .write(true)
                .create_new(true)
                .mode(0o600)
                .open(&args[3])?;
            let mut tx = client.transaction()?;
            orchestration::guard(&mut tx).map_err(|_| "Guard Agent")?;
            orchestration::provision(&mut tx, &args[1], &args[2], &token)
                .map_err(|_| "Provisionamento Agent")?;
            file.write_all(token.as_bytes())?;
            file.sync_all()?;
            tx.commit()?;
        }
        Some("revoke-agent") if args.len() == 2 => {
            let mut tx = client.transaction()?;
            orchestration::guard(&mut tx).map_err(|_| "Guard Agent")?;
            orchestration::revoke(&mut tx, &args[1]).map_err(|_| "Revogação Agent")?;
            tx.commit()?;
        }
        Some("revoke") if args.len() == 2 => {
            client.execute(
                "UPDATE sessions SET revoked=true WHERE identity=$1",
                &[&args[1]],
            )?;
        }
        Some("admit-repository") if args.len() == 4 => admit(&mut client, &args)?,
        Some("serve") if args.len() == 1 => {
            drop(client);
            serve()?;
        }
        _ => return Err("Argumentos inválidos; consulte docs/development/publication.md".into()),
    }
    Ok(())
}
fn provision(client: &mut Client, args: &[String]) -> Result<(), AnyError> {
    let (actor, project, role, path) = (&args[1], &args[2], &args[3], &args[4]);
    if !name(actor)
        || !name(project)
        || !["viewer", "operator", "admin"].contains(&role.as_str())
        || args[5] != "local"
    {
        return Err("Provisionamento inválido".into());
    }
    let token = random()?;
    let mut file = OpenOptions::new()
        .write(true)
        .create_new(true)
        .mode(0o600)
        .open(path)?;
    let mut tx = client.transaction()?;
    tx.execute(
        "INSERT INTO identities VALUES($1) ON CONFLICT DO NOTHING",
        &[actor],
    )?;
    tx.execute(
        "INSERT INTO projects(name) VALUES($1) ON CONFLICT DO NOTHING",
        &[project],
    )?;
    tx.execute(
        "INSERT INTO grants VALUES($1,$2,$3) ON CONFLICT(project,identity) DO UPDATE SET role=$3",
        &[project, actor, role],
    )?;
    tx.execute("INSERT INTO sessions(hash,identity,expires) VALUES($1,$2,clock_timestamp()+interval '8 hours')", &[&ec_server_access::hash(token.as_bytes()),actor])?;
    file.write_all(token.as_bytes())?;
    file.sync_all()?;
    tx.commit()?;
    Ok(())
}
fn admit(client: &mut Client, args: &[String]) -> Result<(), AnyError> {
    let path = std::fs::canonicalize(&args[2])?
        .to_str()
        .ok_or("Caminho inválido")?
        .to_owned();
    if args[3].len() != 40
        || !args[3]
            .bytes()
            .all(|b| b.is_ascii_digit() || (b'a'..=b'f').contains(&b))
    {
        return Err("Commit inválido".into());
    }
    let result = std::process::Command::new("git")
        .args(["-C", &path, "cat-file", "-t", &args[3]])
        .output()?;
    if !result.status.success() || result.stdout != b"commit\n" {
        return Err("Commit ausente".into());
    }
    client.execute(
        "INSERT INTO repositories VALUES($1,$2,$3) ON CONFLICT DO NOTHING",
        &[&args[1], &path, &args[3]],
    )?;
    Ok(())
}
fn serve() -> Result<(), AnyError> {
    let port = std::env::var("EC_PORT")
        .unwrap_or("8080".into())
        .parse::<u16>()?;
    let server = Arc::new(Server::http(("127.0.0.1", port))?);
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
    for _ in 0..4 {
        let server = server.clone();
        workers.push(std::thread::spawn(move || {
            for mut request in server.incoming_requests() {
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
                    .with_header(Header::from_bytes("Content-Type", "application/json").unwrap())
                    .with_header(Header::from_bytes("Cache-Control", "no-store").unwrap());
                let _ = request.respond(response);
            }
        }));
    }
    for worker in workers {
        worker.join().map_err(|_| "Worker interrompido")?;
    }
    Ok(())
}
fn header(r: &Request, key: &str) -> String {
    r.headers()
        .iter()
        .find(|h| h.field.as_str().as_str().eq_ignore_ascii_case(key))
        .map(|h| h.value.to_string())
        .unwrap_or_default()
}
fn handle(r: &mut Request) -> Result<Value, Failure> {
    let db = |_| (500, "database");
    let mut c = connect().map_err(db)?;
    let mut t = c.transaction().map_err(db)?;
    t.batch_execute("SET LOCAL application_name='ec-http'; SET LOCAL lock_timeout='5s'; SET LOCAL statement_timeout='10s'")
        .map_err(db)?;
    let auth = header(r, "Authorization");
    let token = auth.strip_prefix("Bearer ").ok_or((401, "session"))?;
    if r.url().starts_with("/api/v1/agent/") {
        return agent_route(r, t, token);
    }
    let actor = ec_server_access::authenticate(&mut t, token)
        .map_err(db)?
        .ok_or((401, "session"))?;
    let url = r.url().to_owned();
    let p: Vec<_> = url.split('/').collect();
    let method = r.method().as_str().to_owned();
    if url == "/api/v1/session" && method == "GET" {
        let row = t
            .query_one(
                "SELECT expires::text FROM sessions WHERE hash=$1",
                &[&ec_server_access::hash(token.as_bytes())],
            )
            .map_err(db)?;
        return Ok(json!({"identity":actor,"expires":row.get::<_,String>(0)}));
    }
    if url == "/api/v1/projects" && method == "GET" {
        let rows = t.query("SELECT p.name,g.role,p.published,p.effective FROM projects p JOIN grants g ON g.project=p.name WHERE g.identity=$1 ORDER BY p.name", &[&actor]).map_err(db)?;
        return Ok(
            json!({"projects":rows.iter().map(|r|json!({"project":r.get::<_,String>(0),"role":r.get::<_,String>(1),"published":r.get::<_,i64>(2),"effective":r.get::<_,i64>(3)})).collect::<Vec<_>>()}),
        );
    }
    if url == "/api/v1/session" && method == "DELETE" {
        // Upgrade own session lock; session revocation is serialized with in-flight publication.
        t.execute(
            "UPDATE sessions SET revoked=true WHERE hash=$1",
            &[&ec_server_access::hash(token.as_bytes())],
        )
        .map_err(db)?;
        t.commit().map_err(db)?;
        return Ok(json!({"revoked":true}));
    }
    if p.len() < 5 || p[..4] != ["", "api", "v1", "projects"] || !name(p[4]) {
        return Err((400, "route"));
    }
    let project = p[4];
    let role = ec_server_access::role(&mut t, project, &actor)
        .map_err(db)?
        .ok_or((404, "project"))?;
    if method == "GET" && p.len() == 6 && p[5] == "agents" {
        let rows=t.query("SELECT a.name,a.revoked,COALESCE(s.expires>clock_timestamp(),false) AND NOT a.revoked FROM agents a LEFT JOIN agent_sessions s ON s.id=a.current_session WHERE a.project=$1 ORDER BY a.name", &[&project]).map_err(db)?;
        return Ok(
            json!({"agents":rows.iter().map(|r|json!({"name":r.get::<_,String>(0),"revoked":r.get::<_,bool>(1),"sessionValid":r.get::<_,bool>(2)})).collect::<Vec<_>>()}),
        );
    }
    if method == "POST" && p.len() == 6 && p[5] == "validation" {
        let v = body(r)?;
        envelope(&v, &["yaml"])?;
        let yaml = v["yaml"].as_str().ok_or((400, "yaml"))?;
        if yaml.len() > 65536 {
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
        return Ok(json!({"valid":true,"projection":fields}));
    }
    let publishing = method == "POST" || (p.len() == 7 && p[5] == "receipts");
    if publishing && role == "viewer" {
        return Err((403, "permission"));
    }
    if method == "GET" && p.len() == 10 && p[5] == "runs" && p[7] == "attempts" && p[9] == "output"
    {
        orchestration::guard(&mut t)?;
        if ec_server_access::authenticate(&mut t, token)
            .map_err(db)?
            .is_none()
        {
            return Err((401, "session"));
        }
        orchestration::project_attempt(&mut t, project, positive(p[6])?, positive(p[8])?)?;
        return ec_server_results::read(&mut t, positive(p[8])?);
    }
    if p.len() >= 6 && ["runs", "run-receipts"].contains(&p[5]) {
        let payload = if method == "POST" {
            Some(body(r)?)
        } else {
            None
        };
        orchestration::guard(&mut t)?;
        // Recheck expiry after waiting on the orchestration guard.
        if ec_server_access::authenticate(&mut t, token)
            .map_err(db)?
            .is_none()
        {
            return Err((401, "session"));
        }
        let result = if method == "GET" && p.len() == 7 && p[5] == "run-receipts" {
            orchestration::submission_receipt(&mut t, project, &actor, p[6])?
                .ok_or((404, "receipt"))?
        } else if method == "GET" && p.len() == 6 && p[5] == "runs" {
            orchestration::list_runs(&mut t, project)?
        } else if method == "GET" && p.len() == 7 && p[5] == "runs" {
            orchestration::get_run(&mut t, project, positive(p[6])?)?
        } else if method == "POST" && p.len() == 8 && p[5] == "runs" && p[7] == "cancel" {
            envelope(payload.as_ref().ok_or((400, "body"))?, &[])?;
            orchestration::cancel(&mut t, project, positive(p[6])?)?
        } else if method == "POST" && p.len() == 6 && p[5] == "runs" {
            let v = payload.as_ref().ok_or((400, "body"))?;
            envelope(v, &["requestId", "revisionId"])?;
            let key = v["requestId"]
                .as_str()
                .filter(|s| {
                    !s.is_empty()
                        && s.len() <= 64
                        && s.bytes().all(|b| b.is_ascii_alphanumeric() || b == b'-')
                })
                .ok_or((400, "request_id"))?;
            let revision = v["revisionId"]
                .as_i64()
                .filter(|n| *n > 0)
                .ok_or((400, "revision"))?;
            if let Some(old) = orchestration::submission_receipt(&mut t, project, &actor, key)? {
                if old["revisionId"] != revision {
                    return Err((409, "request_conflict"));
                }
                old
            } else {
                let snapshot =
                    publication::snapshot(&mut t, project, revision)?.ok_or((404, "revision"))?;
                orchestration::submit(
                    &mut t,
                    project,
                    &actor,
                    key,
                    orchestration::Snapshot {
                        revision,
                        hash: snapshot.hash,
                        commit: snapshot.commit,
                        configuration: snapshot.configuration,
                    },
                )?
            }
        } else {
            return Err((400, "route"));
        };
        before_commit(r)?;
        t.commit().map_err(db)?;
        return Ok(result);
    }
    if method == "GET" && p.len() == 5 {
        let row = t
            .query_one(
                "SELECT published,effective FROM projects WHERE name=$1",
                &[&project],
            )
            .map_err(db)?;
        return Ok(
            json!({"project":project,"published":row.get::<_,i64>(0),"effective":row.get::<_,i64>(1)}),
        );
    }
    if method == "GET" && p.len() == 7 && p[5] == "receipts" {
        return publication::receipt(&mut t, project, &actor, p[6])
            .map_err(db)?
            .ok_or((404, "receipt"));
    }
    if method == "GET" && p.len() == 7 && p[5] == "revisions" {
        let rev = p[6].parse::<i64>().map_err(|_| (400, "revision"))?;
        let row = t
            .query_opt(
                "SELECT yaml,hash,projection FROM revisions WHERE project=$1 AND revision=$2",
                &[&project, &rev],
            )
            .map_err(db)?
            .ok_or((404, "revision"))?;
        let yaml = String::from_utf8(row.get(0)).map_err(|_| (500, "stored_yaml"))?;
        return Ok(
            json!({"revision":rev,"yaml":yaml,"sha256":row.get::<_,String>(1),"projection":serde_json::from_str::<Value>(&row.get::<_,String>(2)).map_err(|_|(500,"projection"))?}),
        );
    }
    if method != "POST" || p.len() != 6 || p[5] != "publications" {
        return Err((400, "route"));
    }
    let mut bytes = Vec::new();
    r.as_reader()
        .take(131073)
        .read_to_end(&mut bytes)
        .map_err(|_| (400, "body"))?;
    if bytes.len() > 131072 {
        return Err((413, "body_limit"));
    }
    let v: Value = serde_json::from_slice(&bytes).map_err(|_| (400, "json"))?;
    if v.as_object().is_none_or(|o| o.len() != 3) {
        return Err((400, "envelope"));
    }
    let id = v["requestId"].as_str().ok_or((400, "request_id"))?;
    if id.is_empty() || id.len() > 64 || !id.bytes().all(|b| b.is_ascii_alphanumeric() || b == b'-')
    {
        return Err((400, "request_id"));
    }
    let base = v["baseRevision"]
        .as_i64()
        .filter(|b| *b >= 0)
        .ok_or((400, "base"))?;
    let yaml = v["yaml"].as_str().ok_or((400, "yaml"))?;
    if yaml.len() > 65536 {
        return Err((413, "yaml_limit"));
    }
    t.query_one(
        "SELECT published FROM projects WHERE name=$1 FOR UPDATE",
        &[&project],
    )
    .map_err(db)?;
    if ec_server_access::authenticate(&mut t, token)
        .map_err(db)?
        .is_none()
    {
        return Err((401, "session"));
    }
    if let Some(old) = publication::receipt(&mut t, project, &actor, id).map_err(db)? {
        let revision = old["revision"].as_i64().ok_or((500, "receipt"))?;
        let bytes: Vec<u8> = t
            .query_one(
                "SELECT yaml FROM revisions WHERE project=$1 AND revision=$2",
                &[&project, &revision],
            )
            .map_err(db)?
            .get(0);
        if old["baseRevision"] != base || bytes != yaml.as_bytes() {
            return Err((409, "request_conflict"));
        }
        t.commit().map_err(db)?;
        return Ok(old);
    }
    let fields = ec_server_configuration::parse(yaml).map_err(|_| (422, "yaml"))?;
    let model =
        ec_server_projects::ProjectPipeline::validate(&fields).map_err(|_| (422, "schema"))?;
    if model.project != project {
        return Err((422, "project_reference"));
    }
    if t.query_opt(
        "SELECT path FROM repositories WHERE project=$1 AND path=$2 AND commit_sha=$3 FOR SHARE",
        &[&project, &model.repository, &model.commit],
    )
    .map_err(db)?
    .is_none()
    {
        return Err((422, "repository_reference"));
    }
    let projection = serde_json::to_string(&fields).map_err(|_| (500, "projection"))?;
    let result = publication::publish(
        &mut t,
        Candidate {
            project,
            actor: &actor,
            id,
            base,
            yaml,
            projection: &projection,
        },
    )?;
    #[cfg(feature = "test-failpoints")]
    if header(r, "X-Ec-Test-Failure") == "before-commit" {
        return Err((500, "injected_before_commit"));
    }
    #[cfg(feature = "test-failpoints")]
    if header(r, "X-Ec-Test-Failure") == "crash-before-commit" {
        std::process::exit(86);
    }
    if ec_server_access::authenticate(&mut t, token)
        .map_err(db)?
        .is_none()
    {
        return Err((401, "session"));
    }
    t.commit().map_err(db)?;
    Ok(result)
}

fn positive(s: &str) -> Result<i64, Failure> {
    s.parse::<i64>().ok().filter(|n| *n > 0).ok_or((400, "id"))
}
fn envelope(v: &Value, keys: &[&str]) -> Result<(), Failure> {
    if v.as_object()
        .is_none_or(|o| o.len() != keys.len() || keys.iter().any(|k| !o.contains_key(*k)))
    {
        return Err((400, "envelope"));
    }
    Ok(())
}
fn body(r: &mut Request) -> Result<Value, Failure> {
    let limit = if r.url().starts_with("/api/v1/agent/")
        && (r.url().ends_with("/logs") || r.url().ends_with("/artifact-chunk"))
    {
        140000
    } else {
        131072
    };
    let mut bytes = Vec::new();
    r.as_reader()
        .take(limit + 1)
        .read_to_end(&mut bytes)
        .map_err(|_| (400, "body"))?;
    if bytes.len() as u64 > limit {
        return Err((413, "body_limit"));
    }
    serde_json::from_slice(&bytes).map_err(|_| (400, "json"))
}
fn before_commit(_r: &Request) -> Result<(), Failure> {
    #[cfg(feature = "test-failpoints")]
    match header(_r, "X-Ec-Test-Failure").as_str() {
        "before-commit" => return Err((500, "injected_before_commit")),
        "crash-before-commit" => std::process::exit(86),
        _ => {}
    }
    Ok(())
}
fn reconcile_once() -> Result<(), Failure> {
    let mut c = connect().map_err(|_| (500, "database"))?;
    let mut t = c.transaction().map_err(|_| (500, "database"))?;
    t.batch_execute("SET LOCAL lock_timeout='5s'; SET LOCAL statement_timeout='10s'")
        .map_err(|_| (500, "database"))?;
    orchestration::guard(&mut t)?;
    orchestration::reconcile(&mut t)?;
    t.commit().map_err(|_| (500, "database"))
}
fn agent_route(
    r: &mut Request,
    mut t: postgres::Transaction<'_>,
    token: &str,
) -> Result<Value, Failure> {
    let payload = if r.method().as_str() == "POST" {
        Some(body(r)?)
    } else {
        None
    };
    orchestration::guard(&mut t)?;
    let url = r.url().to_owned();
    let p: Vec<_> = url.split('/').collect();
    let method = r.method().as_str().to_owned();
    let result = if method == "POST" && url == "/api/v1/agent/session" {
        // Validate credential before revealing capability diagnostics.
        // Session insertion rolls back if the subsequent envelope is rejected.
        let id = random().map_err(|_| (500, "random"))?;
        let session_token = random().map_err(|_| (500, "random"))?;
        let result = orchestration::session(&mut t, token, &id, &session_token)?;
        let v = payload.as_ref().ok_or((400, "body"))?;
        envelope(v, &["protocolVersion", "platform", "slots"])?;
        if v["protocolVersion"] != 1 || v["platform"] != "linux" || v["slots"] != 1 {
            return Err((422, "capabilities"));
        }
        result
    } else {
        let owner = orchestration::authenticate(&mut t, token)?;
        if method == "POST" && url == "/api/v1/agent/reserve" {
            envelope(payload.as_ref().ok_or((400, "body"))?, &[])?;
            orchestration::reserve(&mut t, &owner)?
        } else if method == "GET" && url == "/api/v1/agent/attempts" {
            orchestration::owned_attempts(&mut t, &owner)?
        } else if method == "GET" && p.len() == 6 && p[4] == "attempts" {
            orchestration::owned_attempt(&mut t, &owner, positive(p[5])?)?
        } else if method == "GET" && p.len() == 7 && p[4] == "attempts" && p[6] == "output" {
            let id = positive(p[5])?;
            orchestration::owned_attempt(&mut t, &owner, id)?;
            ec_server_results::read(&mut t, id)?
        } else if method == "POST"
            && p.len() == 7
            && p[4] == "attempts"
            && [
                "logs",
                "artifact-start",
                "artifact-chunk",
                "artifact-finish",
                "finish",
            ]
            .contains(&p[6])
        {
            let v = payload.as_ref().ok_or((400, "body"))?;
            let keys: &[&str] = match p[6] {
                "logs" => &["fence", "sequence", "stream", "data"],
                "artifact-start" => &["fence", "size", "sha256"],
                "artifact-chunk" => &["fence", "sequence", "data"],
                "artifact-finish" => &["fence"],
                _ => &["fence", "outcome", "exitCode", "output"],
            };
            envelope(v, keys)?;
            ec_server_results::mutate(&mut t, &owner, positive(p[5])?, p[6], v)?
        } else if method == "POST"
            && p.len() == 7
            && p[4] == "attempts"
            && ["heartbeat", "running", "result"].contains(&p[6])
        {
            let v = payload.as_ref().ok_or((400, "body"))?;
            envelope(
                v,
                if p[6] == "result" {
                    &["fence", "outcome", "exitCode"]
                } else {
                    &["fence"]
                },
            )?;
            orchestration::mutate(&mut t, &owner, positive(p[5])?, p[6], v)?
        } else {
            return Err((400, "route"));
        }
    };
    before_commit(r)?;
    t.commit().map_err(|_| (500, "database"))?;
    Ok(result)
}
