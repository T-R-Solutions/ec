use ec_server_orchestration as orchestration;
use ec_server_projects::publication::{self, Candidate, Failure};
use postgres::{Client, NoTls};
use serde_json::{Value, json};
use std::fmt::Write as _;
use std::{
    fs::OpenOptions,
    io::{Read, Write},
    os::unix::fs::OpenOptionsExt,
    sync::Arc,
};
use tiny_http::{Header, Request, Response, Server};
type AnyError = Box<dyn std::error::Error + Send + Sync>;
#[cfg(test)]
#[path = "service_tests.rs"]
mod tests;
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
    let mut output = String::with_capacity(b.len() * 2);
    for byte in b {
        let _ = write!(output, "{byte:02x}");
    }
    Ok(output)
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
fn header(request: &Request, key: &str) -> String {
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
                &[&ec_server_access::hash(token.as_bytes())],
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
                &[&ec_server_access::hash(token.as_bytes())],
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

#[derive(Clone, Copy)]
struct ProjectRequest<'a> {
    actor: &'a str,
    token: &'a str,
    project: &'a str,
    role: &'a str,
    method: &'a str,
    route: &'a [&'a str],
}
impl ProjectRequest<'_> {
    fn handle(
        self,
        request: &mut Request,
        mut transaction: postgres::Transaction<'_>,
    ) -> Result<Value, Failure> {
        let db = |_| (500, "database");
        let Self {
            actor,
            token,
            project,
            role,
            method,
            route,
            ..
        } = self;
        if method == "GET" && route.len() == 6 && route[5] == "agents" {
            let rows=transaction.query("SELECT a.name,a.revoked,COALESCE(s.expires>clock_timestamp(),false) AND NOT a.revoked FROM agents a LEFT JOIN agent_sessions s ON s.id=a.current_session WHERE a.project=$1 ORDER BY a.name", &[&project]).map_err(db)?;
            return Ok(
                json!({"agents":rows.iter().map(|request|json!({"name":request.get::<_,String>(0),"revoked":request.get::<_,bool>(1),"sessionValid":request.get::<_,bool>(2)})).collect::<Vec<_>>()}),
            );
        }
        if method == "POST" && route.len() == 6 && route[5] == "validation" {
            return validate_pipeline(request, project);
        }
        let publishing = method == "POST" || (route.len() == 7 && route[5] == "receipts");
        if publishing && role == "viewer" {
            return Err((403, "permission"));
        }
        if method == "GET"
            && route.len() == 10
            && route[5] == "runs"
            && route[7] == "attempts"
            && route[9] == "output"
        {
            orchestration::guard(&mut transaction)?;
            if ec_server_access::authenticate(&mut transaction, token)
                .map_err(db)?
                .is_none()
            {
                return Err((401, "session"));
            }
            orchestration::project_attempt(
                &mut transaction,
                project,
                positive(route[6])?,
                positive(route[8])?,
            )?;
            return ec_server_results::read(&mut transaction, positive(route[8])?);
        }
        if route.len() >= 6 && ["runs", "run-receipts"].contains(&route[5]) {
            return self.runs(request, transaction);
        }
        if method == "GET" && route.len() == 5 {
            let row = transaction
                .query_one(
                    "SELECT published,effective FROM projects WHERE name=$1",
                    &[&project],
                )
                .map_err(db)?;
            return Ok(
                json!({"project":project,"published":row.get::<_,i64>(0),"effective":row.get::<_,i64>(1)}),
            );
        }
        if method == "GET" && route.len() == 7 && route[5] == "receipts" {
            return publication::receipt(&mut transaction, project, actor, route[6])
                .map_err(db)?
                .ok_or((404, "receipt"));
        }
        if method == "GET" && route.len() == 7 && route[5] == "revisions" {
            let rev = route[6].parse::<i64>().map_err(|_| (400, "revision"))?;
            let row = transaction
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
        if method != "POST" || route.len() != 6 || route[5] != "publications" {
            return Err((400, "route"));
        }
        self.publish(request, transaction)
    }
    fn runs(
        self,
        request: &mut Request,
        mut transaction: postgres::Transaction<'_>,
    ) -> Result<Value, Failure> {
        let db = |_| (500, "database");
        let Self {
            actor,
            token,
            project,
            method,
            route,
            ..
        } = self;
        let payload = if method == "POST" {
            Some(body(request)?)
        } else {
            None
        };
        orchestration::guard(&mut transaction)?;
        // Recheck expiry after waiting on the orchestration guard.
        if ec_server_access::authenticate(&mut transaction, token)
            .map_err(db)?
            .is_none()
        {
            return Err((401, "session"));
        }
        let result = if method == "GET" && route.len() == 7 && route[5] == "run-receipts" {
            orchestration::submission_receipt(&mut transaction, project, actor, route[6])?
                .ok_or((404, "receipt"))?
        } else if method == "GET" && route.len() == 6 && route[5] == "runs" {
            orchestration::list_runs(&mut transaction, project)?
        } else if method == "GET" && route.len() == 7 && route[5] == "runs" {
            orchestration::get_run(&mut transaction, project, positive(route[6])?)?
        } else if method == "POST" && route.len() == 8 && route[5] == "runs" && route[7] == "cancel"
        {
            envelope(payload.as_ref().ok_or((400, "body"))?, &[])?;
            orchestration::cancel(&mut transaction, project, positive(route[6])?)?
        } else if method == "POST" && route.len() == 6 && route[5] == "runs" {
            let payload = payload.as_ref().ok_or((400, "body"))?;
            envelope(payload, &["requestId", "revisionId"])?;
            let key = payload["requestId"]
                .as_str()
                .filter(|s| {
                    !s.is_empty()
                        && s.len() <= 64
                        && s.bytes().all(|b| b.is_ascii_alphanumeric() || b == b'-')
                })
                .ok_or((400, "request_id"))?;
            let revision = payload["revisionId"]
                .as_i64()
                .filter(|n| *n > 0)
                .ok_or((400, "revision"))?;
            if let Some(old) =
                orchestration::submission_receipt(&mut transaction, project, actor, key)?
            {
                if old["revisionId"] != revision {
                    return Err((409, "request_conflict"));
                }
                old
            } else {
                let snapshot = publication::snapshot(&mut transaction, project, revision)?
                    .ok_or((404, "revision"))?;
                orchestration::submit(
                    &mut transaction,
                    project,
                    actor,
                    key,
                    &orchestration::Snapshot {
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
        before_commit(request)?;
        transaction.commit().map_err(db)?;
        Ok(result)
    }
    fn publish(
        self,
        request: &mut Request,
        mut transaction: postgres::Transaction<'_>,
    ) -> Result<Value, Failure> {
        let db = |_| (500, "database");
        let Self {
            actor,
            token,
            project,
            ..
        } = self;
        let payload = body(request)?;
        if payload.as_object().is_none_or(|o| o.len() != 3) {
            return Err((400, "envelope"));
        }
        let id = payload["requestId"].as_str().ok_or((400, "request_id"))?;
        if id.is_empty()
            || id.len() > 64
            || !id.bytes().all(|b| b.is_ascii_alphanumeric() || b == b'-')
        {
            return Err((400, "request_id"));
        }
        let base = payload["baseRevision"]
            .as_i64()
            .filter(|b| *b >= 0)
            .ok_or((400, "base"))?;
        let yaml = payload["yaml"].as_str().ok_or((400, "yaml"))?;
        if yaml.len() > 65_536 {
            return Err((413, "yaml_limit"));
        }
        transaction
            .query_one(
                "SELECT published FROM projects WHERE name=$1 FOR UPDATE",
                &[&project],
            )
            .map_err(db)?;
        if ec_server_access::authenticate(&mut transaction, token)
            .map_err(db)?
            .is_none()
        {
            return Err((401, "session"));
        }
        if let Some(old) = publication::receipt(&mut transaction, project, actor, id).map_err(db)? {
            let revision = old["revision"].as_i64().ok_or((500, "receipt"))?;
            let bytes: Vec<u8> = transaction
                .query_one(
                    "SELECT yaml FROM revisions WHERE project=$1 AND revision=$2",
                    &[&project, &revision],
                )
                .map_err(db)?
                .get(0);
            if old["baseRevision"] != base || bytes != yaml.as_bytes() {
                return Err((409, "request_conflict"));
            }
            transaction.commit().map_err(db)?;
            return Ok(old);
        }
        let fields = ec_server_configuration::parse(yaml).map_err(|_| (422, "yaml"))?;
        let model =
            ec_server_projects::ProjectPipeline::validate(&fields).map_err(|_| (422, "schema"))?;
        if model.project != project {
            return Err((422, "project_reference"));
        }
        if transaction.query_opt(
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
            &mut transaction,
            Candidate {
                project,
                actor,
                id,
                base,
                yaml,
                projection: &projection,
            },
        )?;
        #[cfg(feature = "test-failpoints")]
        if header(request, "X-Ec-Test-Failure") == "before-commit" {
            return Err((500, "injected_before_commit"));
        }
        #[cfg(feature = "test-failpoints")]
        if header(request, "X-Ec-Test-Failure") == "crash-before-commit" {
            std::process::exit(86);
        }
        if ec_server_access::authenticate(&mut transaction, token)
            .map_err(db)?
            .is_none()
        {
            return Err((401, "session"));
        }
        transaction.commit().map_err(db)?;
        Ok(result)
    }
}

fn validate_pipeline(request: &mut Request, project: &str) -> Result<Value, Failure> {
    let payload = body(request)?;
    envelope(&payload, &["yaml"])?;
    let yaml = payload["yaml"].as_str().ok_or((400, "yaml"))?;
    if yaml.len() > 65_536 {
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

fn positive(s: &str) -> Result<i64, Failure> {
    s.parse::<i64>().ok().filter(|n| *n > 0).ok_or((400, "id"))
}
fn envelope(payload: &Value, keys: &[&str]) -> Result<(), Failure> {
    if payload
        .as_object()
        .is_none_or(|o| o.len() != keys.len() || keys.iter().any(|k| !o.contains_key(*k)))
    {
        return Err((400, "envelope"));
    }
    Ok(())
}
fn body(request: &mut Request) -> Result<Value, Failure> {
    let limit = if request.url().starts_with("/api/v1/agent/")
        && (request.url().ends_with("/logs") || request.url().ends_with("/artifact-chunk"))
    {
        140_000
    } else {
        131_072
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
fn before_commit(request: &Request) -> Result<(), Failure> {
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
fn reconcile_once() -> Result<(), Failure> {
    let mut client = connect().map_err(|_| (500, "database"))?;
    let mut transaction = client.transaction().map_err(|_| (500, "database"))?;
    transaction
        .batch_execute("SET LOCAL lock_timeout='5s'; SET LOCAL statement_timeout='10s'")
        .map_err(|_| (500, "database"))?;
    orchestration::guard(&mut transaction)?;
    orchestration::reconcile(&mut transaction)?;
    transaction.commit().map_err(|_| (500, "database"))
}
fn agent_route(
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
        if payload["protocolVersion"] != 1
            || payload["platform"] != "linux"
            || payload["slots"] != 1
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
