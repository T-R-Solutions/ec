use ec_agent_control::{Client, Error};
use ec_agent_reporting::{Spool, hash, hex, persist};
use serde_json::{Value, json};
use std::os::{
    fd::AsRawFd,
    unix::{
        fs::{OpenOptionsExt, PermissionsExt},
        process::ExitStatusExt,
    },
};
use std::{
    fs, io,
    net::SocketAddr,
    path::{Path, PathBuf},
    sync::{
        Arc, Mutex,
        atomic::{AtomicBool, Ordering},
    },
    thread,
    time::Duration,
};
static SHUTDOWN: AtomicBool = AtomicBool::new(false);
extern "C" fn shutdown(_: i32) {
    SHUTDOWN.store(true, Ordering::SeqCst);
}
fn err(s: &str) -> io::Error {
    io::Error::other(s)
}
fn private(p: &Path) -> io::Result<Vec<u8>> {
    let m = fs::symlink_metadata(p)?;
    if !m.is_file() || m.permissions().mode() & 0o077 != 0 {
        return Err(err("private_file_required"));
    }
    fs::read(p)
}
fn text<'a>(v: &'a Value, k: &str) -> io::Result<&'a str> {
    v[k].as_str().ok_or_else(|| err("invalid_envelope"))
}
fn request(c: &Client, method: &str, path: &str, v: Option<&Value>) -> io::Result<Value> {
    c.call(method, path, v)
        .map_err(|_| err("server_request_failed"))
}
fn deliver(c: &Client, path: &str, v: &Value, lost: &AtomicBool) -> io::Result<Value> {
    ec_agent_reporting::deliver(
        || match c.call("POST", path, Some(v)) {
            Ok(v) => Ok(Some(v)),
            Err(Error::Transport) => Ok(None),
            _ => Err(err("delivery_unconfirmed")),
        },
        || lost.load(Ordering::SeqCst),
    )
}
fn main() {
    if run().is_err() {
        eprintln!("Agent interrompido; examine journal e autoridade no Server");
        std::process::exit(1);
    }
}
fn run() -> io::Result<()> {
    let args: Vec<_> = std::env::args_os().skip(1).collect();
    if args.len() != 1 {
        return Err(err("config_path_required"));
    }
    let cfg: Value =
        serde_json::from_slice(&private(Path::new(&args[0]))?).map_err(|_| err("config"))?;
    let root = fs::canonicalize(text(&cfg, "root")?)?;
    if fs::metadata(&root)?.permissions().mode() & 0o077 != 0 {
        return Err(err("private_root_required"));
    }
    let lock = fs::OpenOptions::new()
        .read(true)
        .write(true)
        .create(true)
        .truncate(false)
        .mode(0o600)
        .custom_flags(libc::O_NOFOLLOW)
        .open(root.join("agent.lock"))?;
    // SAFETY: valid open fd; flock has no pointer argument. Inheritance deliberately
    // keeps the instance excluded while cooperative descendants survive an Agent crash.
    unsafe {
        if libc::flock(lock.as_raw_fd(), libc::LOCK_EX | libc::LOCK_NB) != 0 {
            return Err(err("previous_processes_active"));
        }
        if libc::fcntl(lock.as_raw_fd(), libc::F_SETFD, 0) != 0 {
            return Err(io::Error::last_os_error());
        }
        libc::signal(libc::SIGTERM, shutdown as *const () as libc::sighandler_t);
        libc::signal(libc::SIGINT, shutdown as *const () as libc::sighandler_t);
    }
    let address: SocketAddr = text(&cfg, "address")?.parse().map_err(|_| err("address"))?;
    if !address.ip().is_loopback() {
        return Err(err("loopback_required"));
    }
    let credential = String::from_utf8(private(Path::new(text(&cfg, "credentialFile")?))?)
        .map_err(|_| err("credential"))?;
    let mut c = Client {
        address,
        token: credential,
    };
    let session = request(
        &c,
        "POST",
        "/agent/session",
        Some(&json!({"protocolVersion":1,"platform":"linux","slots":1})),
    )?;
    if session["protocolVersion"] != 1
        || session["leaseSeconds"] != 30
        || text(&session, "token")?.len() != 64
    {
        return Err(err("session_envelope"));
    }
    let session_id = text(&session, "agentSessionId")?.to_owned();
    text(&session, "token")?.clone_into(&mut c.token);
    // New session can inspect history but never resume mutation of old attempts.
    let history = request(&c, "GET", "/agent/attempts", None)?;
    if root.join("journal.json").exists() {
        let old: Value = serde_json::from_slice(&fs::read(root.join("journal.json"))?)
            .map_err(|_| err("journal"))?;
        let id = &old["dispatch"]["attempt"]["attemptId"];
        let observed = history["attempts"]
            .as_array()
            .and_then(|a| a.iter().find(|a| &a["attemptId"] == id));
        persist(
            &root.join("previous-journal.json"),
            &json!({"local":old,"observed":observed}),
        )?;
    }
    let allow = cfg["repositories"]
        .as_array()
        .ok_or_else(|| err("allowlist"))?
        .iter()
        .map(|s| fs::canonicalize(s.as_str().ok_or_else(|| err("allowlist"))?))
        .collect::<io::Result<Vec<PathBuf>>>()?;
    while !SHUTDOWN.load(Ordering::SeqCst) {
        check_retention(&root)?;
        // A lost reserve response is recovered through the server's active-reservation replay.
        let dispatch = match c.call("POST", "/agent/reserve", Some(&json!({}))) {
            Ok(v) => v,
            Err(Error::Transport) => {
                thread::sleep(Duration::from_millis(200));
                continue;
            }
            _ => return Err(err("reserve")),
        };
        if dispatch["attempt"].is_null() {
            if cfg["once"] == true {
                return Ok(());
            }
            thread::sleep(Duration::from_millis(200));
            continue;
        }
        process_dispatch(&c, &root, &allow, &session_id, &dispatch)?;
        if cfg["once"] == true {
            return Ok(());
        }
    }
    Ok(())
}

fn process_dispatch(
    c: &Client,
    root: &Path,
    allow: &[PathBuf],
    session_id: &str,
    dispatch: &Value,
) -> io::Result<()> {
    let a = &dispatch["attempt"];
    let id = a["attemptId"]
        .as_i64()
        .filter(|n| *n > 0)
        .ok_or_else(|| err("attempt"))?;
    let fence = a["fence"]
        .as_i64()
        .filter(|n| *n > 0)
        .ok_or_else(|| err("fence"))?;
    if a["agentSessionId"] != session_id
        || a["state"] != "assigned"
        || ![Some(1), Some(2)].contains(&a["ordinal"].as_i64())
        || a["runId"].as_i64().is_none_or(|n| n <= 0)
        || dispatch["revisionId"].as_i64().is_none_or(|n| n <= 0)
        || text(dispatch, "configHash")?.len() != 64
    {
        return Err(err("dispatch"));
    }
    let base = format!("/agent/attempts/{id}");
    let observed = request(c, "GET", &base, None)?;
    if observed != *a {
        return Err(err("attempt_changed"));
    }
    persist(
        &root.join("journal.json"),
        &json!({"dispatch":dispatch,"phase":"reserved"}),
    )?;
    let lost = Arc::new(AtomicBool::new(false));
    let cancel = Arc::new(AtomicBool::new(false));
    let done = Arc::new(AtomicBool::new(false));
    let heartbeat = ec_agent_control::heartbeat(
        c.clone(),
        base.clone(),
        fence,
        lost.clone(),
        cancel.clone(),
        done.clone(),
    );
    let outcome = Attempt {
        client: c,
        root,
        repositories: allow,
        session_id,
        dispatch,
        id,
        fence,
        base: &base,
        lost: &lost,
        cancel: &cancel,
    }
    .execute();
    done.store(true, Ordering::SeqCst);
    heartbeat.join().map_err(|_| err("heartbeat_worker"))?;
    outcome
}

#[derive(Clone, Copy)]
struct Attempt<'a> {
    client: &'a Client,
    root: &'a Path,
    repositories: &'a [PathBuf],
    session_id: &'a str,
    dispatch: &'a Value,
    id: i64,
    fence: i64,
    base: &'a str,
    lost: &'a AtomicBool,
    cancel: &'a AtomicBool,
}
impl Attempt<'_> {
    fn execute(self) -> io::Result<()> {
        let Self {
            client: c,
            root,
            repositories: allow,
            dispatch,
            id,
            fence,
            base,
            lost,
            cancel,
            ..
        } = self;
        let model = &dispatch["configuration"];
        let commit = text(dispatch, "commit")?;
        if model["commit"] != commit {
            return Err(err("snapshot_commit"));
        }
        let stop = || {
            lost.load(Ordering::SeqCst)
                || cancel.load(Ordering::SeqCst)
                || SHUTDOWN.load(Ordering::SeqCst)
        };
        #[cfg(feature = "test-failpoints")]
        if let Ok(gate) = std::env::var("EC_AGENT_TEST_PREPARE_GATE") {
            fs::write(format!("{gate}.ready"), b"preparing")?;
            while !Path::new(&gate).exists() && !stop() {
                thread::sleep(Duration::from_millis(20));
            }
        }
        let workspace =
            ec_agent_workspace::prepare(root, id, text(model, "repository")?, commit, allow, &stop);
        let mut output = json!({"lastSequence":0,"truncated":false,"artifactError":"preparation_failed","signal":null});
        let mut code = None;
        let mut result = "preparation_failed";
        if let Ok(workspace) = workspace {
            if cancel.load(Ordering::SeqCst) {
                result = "cancelled";
            } else if stop() {
                return Err(err("stopped"));
            } else {
                (code, result, output) = self.run_workspace(&workspace, model)?;
            }
        } else if cancel.load(Ordering::SeqCst) {
            result = "cancelled";
        }
        if lost.load(Ordering::SeqCst) {
            return Err(err("authority_lost"));
        }
        let payload = json!({"fence":fence,"outcome":result,"exitCode":code,"output":output});
        persist(
            &root.join("journal.json"),
            &json!({"dispatch":dispatch,"phase":"pending","payload":payload}),
        )?;
        let receipt = deliver(c, &format!("{base}/finish"), &payload, lost)?;
        persist(
            &root.join("journal.json"),
            &json!({"dispatch":dispatch,"phase":"confirmed","receipt":receipt}),
        )?;
        Ok(())
    }
    fn run_workspace(
        self,
        workspace: &Path,
        model: &Value,
    ) -> io::Result<(Option<i32>, &'static str, Value)> {
        let Self {
            client: c,
            root,
            session_id,
            dispatch,
            id,
            fence,
            base,
            lost,
            cancel,
            ..
        } = self;
        let running = deliver(c, &format!("{base}/running"), &json!({"fence":fence}), lost)?;
        if running["attempt"]["attemptId"] != id
            || running["attempt"]["fence"] != fence
            || running["attempt"]["agentSessionId"] != session_id
            || running["attempt"]["state"] != "running"
        {
            return Err(err("running_unconfirmed"));
        }
        persist(
            &root.join("journal.json"),
            &json!({"dispatch":dispatch,"phase":"starting"}),
        )?;
        let spool = Arc::new(Mutex::new(Spool::new(
            root.join(format!("attempt-{id}/spool")),
        )?));
        let timeout = text(model, "timeoutSeconds")?
            .parse::<u64>()
            .ok()
            .filter(|n| (1..=3600).contains(n))
            .ok_or_else(|| err("timeout"))?;
        let complete = ec_agent_execution::run(
            text(model, "script")?,
            workspace,
            timeout,
            spool.clone(),
            || {
                if lost.load(Ordering::SeqCst) {
                    Some("authority_lost")
                } else if cancel.load(Ordering::SeqCst) {
                    Some("cancelled")
                } else if SHUTDOWN.load(Ordering::SeqCst) {
                    Some("shutdown")
                } else {
                    None
                }
            },
        )?;
        if lost.load(Ordering::SeqCst) || complete.reason == Some("shutdown") {
            return Err(err("local_terminated_without_authority"));
        }
        let mut code = complete.status.code();
        let result = complete.reason.unwrap_or_else(|| {
            if complete.status.success() {
                "succeeded"
            } else {
                "failed"
            }
        });
        if complete.reason.is_some() {
            code = None;
        }
        let (sequence, truncated, failed, dir) = {
            let state = spool.lock().map_err(|_| err("spool_poisoned"))?;
            (
                state.sequence,
                state.truncated,
                state.failed,
                state.dir.clone(),
            )
        };
        if failed {
            return Err(err("spool_io_failure"));
        }
        let mut output = json!({"lastSequence":sequence,"truncated":truncated,"artifactError":null,"signal":complete.status.signal()});
        persist(
            &root.join("journal.json"),
            &json!({"dispatch":dispatch,"phase":"produced","outcome":result,"exitCode":code,"output":output}),
        )?;
        self.deliver_logs(&dir, sequence)?;
        self.deliver_artifact(workspace, model, &dir, &mut output)?;
        Ok((code, result, output))
    }
    fn deliver_logs(self, dir: &Path, sequence: u64) -> io::Result<()> {
        let Self {
            client: c,
            fence,
            base,
            lost,
            ..
        } = self;
        for n in 1..=sequence {
            let mut chunk: Value = serde_json::from_slice(&fs::read(dir.join(n.to_string()))?)
                .map_err(|_| err("spool"))?;
            chunk["fence"] = json!(fence);
            let ack = deliver(c, &format!("{base}/logs"), &chunk, lost)?;
            persist(&dir.join(format!("ack-{n}")), &ack)?;
        }
        Ok(())
    }
    fn deliver_artifact(
        self,
        workspace: &Path,
        model: &Value,
        dir: &Path,
        output: &mut Value,
    ) -> io::Result<()> {
        let Self {
            client: c,
            fence,
            base,
            lost,
            ..
        } = self;
        match ec_agent_workspace::artifact(workspace, text(model, "artifact")?) {
            Ok(bytes) => {
                let sha = hash(&bytes);
                persist(
                    &dir.join("artifact.json"),
                    &json!({"data":hex(&bytes),"sha256":sha}),
                )?;
                deliver(
                    c,
                    &format!("{base}/artifact-start"),
                    &json!({"fence":fence,"size":bytes.len(),"sha256":sha}),
                    lost,
                )?;
                for (n, b) in bytes.chunks(32768).enumerate() {
                    deliver(
                        c,
                        &format!("{base}/artifact-chunk"),
                        &json!({"fence":fence,"sequence":n+1,"data":hex(b)}),
                        lost,
                    )?;
                }
                let receipt = deliver(
                    c,
                    &format!("{base}/artifact-finish"),
                    &json!({"fence":fence}),
                    lost,
                )?;
                persist(&dir.join("artifact-receipt.json"), &receipt)?;
            }
            Err(_) => {
                output["artifactError"] = json!("absent_invalid_changed_or_exceeded");
            }
        }
        Ok(())
    }
}

fn check_retention(root: &Path) -> io::Result<()> {
    if fs::read_dir(root)?
        .filter_map(Result::ok)
        .filter(|p| p.file_name().to_string_lossy().starts_with("attempt-"))
        .count()
        >= 16
    {
        return Err(err("local_retention_limit"));
    }
    Ok(())
}
