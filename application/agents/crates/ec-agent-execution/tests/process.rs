use ec_agent_execution::run;
use ec_agent_reporting::Spool;
use serde_json::Value;
use std::{
    fs, io,
    path::{Path, PathBuf},
    sync::{
        Arc, Mutex,
        atomic::{AtomicU64, Ordering},
    },
};

static NEXT_FIXTURE_ID: AtomicU64 = AtomicU64::new(0);

struct Fixture(PathBuf);
impl Fixture {
    fn new() -> Self {
        Self::new_in(&std::env::temp_dir(), &NEXT_FIXTURE_ID)
            .expect("failed to reserve a process test directory")
    }

    fn new_in(base: &Path, next_id: &AtomicU64) -> io::Result<Self> {
        for _ in 0..1024 {
            // The counter only supplies names; create_dir exclusively reserves ownership.
            let id = next_id.fetch_add(1, Ordering::Relaxed);
            let path = base.join(format!("ec-process-{}-{id}", std::process::id()));
            match fs::create_dir(&path) {
                Ok(()) => return Ok(Self(path)),
                Err(error) if error.kind() == io::ErrorKind::AlreadyExists => {}
                Err(error) => return Err(error),
            }
        }
        Err(io::Error::new(
            io::ErrorKind::AlreadyExists,
            "process test directory candidates exhausted",
        ))
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
fn fixture_skips_existing_paths_and_cleans_up_only_its_directory() {
    let root = Fixture::new();
    let prefix = format!("ec-process-{}", std::process::id());
    let occupied_dir = root.0.join(format!("{prefix}-0"));
    let occupied_file = root.0.join(format!("{prefix}-1"));
    fs::create_dir(&occupied_dir).unwrap();
    fs::write(occupied_dir.join("sentinel"), b"keep directory").unwrap();
    fs::write(&occupied_file, b"keep file").unwrap();

    let fixture = Fixture::new_in(&root.0, &AtomicU64::new(0)).unwrap();
    let owned_path = fixture.0.clone();
    assert_ne!(owned_path, occupied_dir);
    assert_ne!(owned_path, occupied_file);
    fs::write(owned_path.join("owned"), b"remove with fixture").unwrap();
    drop(fixture);

    assert!(!owned_path.exists());
    assert_eq!(
        fs::read(occupied_dir.join("sentinel")).unwrap(),
        b"keep directory"
    );
    assert_eq!(fs::read(occupied_file).unwrap(), b"keep file");
}

#[test]
fn parallel_fixtures_reserve_distinct_directories() {
    let root = Fixture::new();
    let next_id = AtomicU64::new(0);
    let start = std::sync::Barrier::new(8);
    let mut fixtures = std::thread::scope(|scope| {
        let workers = (0..8)
            .map(|_| {
                scope.spawn(|| {
                    start.wait();
                    Fixture::new_in(&root.0, &next_id).unwrap()
                })
            })
            .collect::<Vec<_>>();
        workers
            .into_iter()
            .map(|worker| worker.join().unwrap())
            .collect::<Vec<_>>()
    });
    let paths = fixtures
        .iter()
        .map(|fixture| fixture.0.clone())
        .collect::<std::collections::HashSet<_>>();
    assert_eq!(paths.len(), 8);
    for fixture in &fixtures {
        fs::write(fixture.0.join("sentinel"), b"independent").unwrap();
    }

    let first = fixtures.pop().unwrap();
    let removed = first.0.clone();
    drop(first);
    assert!(!removed.exists());
    for fixture in &fixtures {
        assert_eq!(
            fs::read(fixture.0.join("sentinel")).unwrap(),
            b"independent"
        );
    }
    drop(fixtures);
    assert_eq!(fs::read_dir(&root.0).unwrap().count(), 0);
}

#[test]
fn fixture_reports_errors_other_than_collisions() {
    let root = Fixture::new();
    let missing = root.0.join("missing");
    let error = Fixture::new_in(&missing, &AtomicU64::new(0)).err().unwrap();
    assert_eq!(error.kind(), io::ErrorKind::NotFound);
    assert!(!missing.exists());
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
    drop(s);
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
    let dir = s.dir.clone();
    drop(s);
    for (n, size) in [(1, 32768), (2, 32768), (3, 4464)] {
        let record: Value =
            serde_json::from_slice(&fs::read(dir.join(n.to_string())).unwrap()).unwrap();
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
