use ec_agent_execution::run;
use ec_agent_reporting::Spool;
use serde_json::Value;
use std::{
    fs,
    path::PathBuf,
    sync::{Arc, Mutex},
};

struct Fixture(PathBuf);
impl Fixture {
    fn new() -> Self {
        let path = std::env::temp_dir().join(format!(
            "ec-process-{}-{}",
            std::process::id(),
            std::time::SystemTime::now()
                .duration_since(std::time::UNIX_EPOCH)
                .unwrap()
                .as_nanos()
        ));
        fs::create_dir(&path).unwrap();
        Self(path)
    }
    fn spool(&self) -> Arc<Mutex<Spool>> {
        Arc::new(Mutex::new(Spool::new(self.0.join("spool")).unwrap()))
    }
}
impl Drop for Fixture {
    fn drop(&mut self) {
        let _ = fs::remove_dir_all(&self.0);
    }
}

#[test]
fn captures_both_streams_and_preserves_exit_failure() {
    let f = Fixture::new();
    let spool = f.spool();
    let result = run(
        "printf 'out'; printf 'err' >&2; exit 7",
        &f.0,
        10,
        spool.clone(),
        || None,
    )
    .unwrap();
    assert_eq!(result.status.code(), Some(7));
    assert_eq!(result.reason, None);
    let s = spool.lock().unwrap();
    assert!(!s.failed);
    assert!(!s.truncated);
    assert_eq!(s.bytes, 6);
    assert_eq!(s.sequence, 2);
    let mut records = (1..=s.sequence)
        .map(|n| {
            serde_json::from_slice::<Value>(&fs::read(s.dir.join(n.to_string())).unwrap()).unwrap()
        })
        .collect::<Vec<_>>();
    records.sort_by_key(|v| v["stream"].as_str().unwrap().to_owned());
    assert_eq!(records[0]["stream"], "stderr");
    assert_eq!(records[0]["data"], "657272");
    assert_eq!(records[1]["stream"], "stdout");
    assert_eq!(records[1]["data"], "6f7574");
}

#[test]
fn large_output_is_chunked_without_loss_and_success_is_explicit() {
    let f = Fixture::new();
    let spool = f.spool();
    let result = run("head -c 70000 /dev/zero", &f.0, 10, spool.clone(), || None).unwrap();
    assert!(result.status.success());
    assert_eq!(result.reason, None);
    let s = spool.lock().unwrap();
    assert_eq!(s.bytes, 70000);
    assert_eq!(s.sequence, 3);
    for (n, size) in [(1, 32768), (2, 32768), (3, 4464)] {
        let record: Value =
            serde_json::from_slice(&fs::read(s.dir.join(n.to_string())).unwrap()).unwrap();
        assert_eq!(record["data"].as_str().unwrap(), "00".repeat(size));
    }
}

#[test]
fn timeout_and_authority_stop_terminate_the_process_group() {
    for reason in [None, Some("authority_lost"), Some("cancelled")] {
        let f = Fixture::new();
        let result = run(
            "sleep 30 & wait",
            &f.0,
            if reason.is_none() { 0 } else { 10 },
            f.spool(),
            || reason,
        )
        .unwrap();
        assert_eq!(result.reason, Some(reason.unwrap_or("timed_out")));
        assert!(!result.status.success());
    }
}

#[test]
fn invalid_working_directory_does_not_execute_the_script() {
    let f = Fixture::new();
    assert!(
        run(
            "touch must-not-exist",
            &f.0.join("missing"),
            1,
            f.spool(),
            || None
        )
        .is_err()
    );
    assert!(!f.0.join("must-not-exist").exists());
}
