use ec_agent_reporting::{LIMIT, Spool, deliver, hash, hex};
use serde_json::json;
use std::{fs, io, path::PathBuf};

struct Fixture(PathBuf);
impl Drop for Fixture {
    fn drop(&mut self) {
        let _ = fs::remove_dir_all(&self.0);
    }
}

#[test]
fn spool_preserves_bytes_and_reports_limit_without_overwriting() {
    let dir = std::env::temp_dir().join(format!(
        "ec-spool-limit-{}-{}",
        std::process::id(),
        std::time::SystemTime::now()
            .duration_since(std::time::UNIX_EPOCH)
            .unwrap()
            .as_nanos()
    ));
    let mut spool = Spool::new(dir.clone()).unwrap();
    let _fixture = Fixture(dir.clone());
    spool.append("stdout", b"\0\xff");
    let first = fs::read(dir.join("1")).unwrap();
    assert_eq!(
        serde_json::from_slice::<serde_json::Value>(&first).unwrap(),
        json!({"sequence":1,"stream":"stdout","data":"00ff"})
    );
    spool.append("stderr", &vec![b'x'; LIMIT]);
    assert!(spool.truncated);
    assert!(!spool.failed);
    assert_eq!(spool.bytes, LIMIT);
    assert_eq!(spool.sequence, 2);
    spool.append("stdout", b"beyond limit");
    assert_eq!(spool.sequence, 2);
    assert_eq!(fs::read(dir.join("1")).unwrap(), first);
    assert_eq!(hex(b"\0\xff"), "00ff");
    assert_eq!(
        hash(b"a"),
        "ca978112ca1bbdcafac231b39a23dc4da786eff8147c4e72b9807785afee48bb"
    );
}

#[test]
fn delivery_retries_only_transport_and_honors_authority_and_rejections() {
    let mut calls = 0;
    let receipt = deliver(
        || {
            calls += 1;
            Ok((calls == 2).then(|| json!({"receipt":7})))
        },
        || false,
    )
    .unwrap();
    assert_eq!(receipt, json!({"receipt":7}));
    assert_eq!(calls, 2);
    calls = 0;
    let error = deliver(
        || {
            calls += 1;
            Ok(Some(json!({})))
        },
        || true,
    )
    .unwrap_err();
    assert_eq!(error.to_string(), "authority_lost");
    assert_eq!(calls, 0);
    let error = deliver(
        || {
            calls += 1;
            Err(io::Error::other("rejected"))
        },
        || false,
    )
    .unwrap_err();
    assert_eq!(error.to_string(), "rejected");
    assert_eq!(calls, 1);
}
