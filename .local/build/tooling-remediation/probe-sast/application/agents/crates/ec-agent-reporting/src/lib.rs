//! Bounded durable local spool. Raw bytes are lossless hex on the wire.
use serde_json::{Value, json};
use sha2::{Digest, Sha256};
use std::{
    fs::{self, File, OpenOptions},
    io::{self, Write},
    path::{Path, PathBuf},
};
pub const LIMIT: usize = 16 * 1024 * 1024;
pub fn hash(b: &[u8]) -> String {
    format!("{:x}", Sha256::digest(b))
}
pub fn hex(b: &[u8]) -> String {
    b.iter().map(|b| format!("{b:02x}")).collect()
}
pub fn persist(p: &Path, v: &Value) -> io::Result<()> {
    let tmp = p.with_extension("tmp");
    let mut f = OpenOptions::new().write(true).create_new(true).open(&tmp)?;
    f.write_all(v.to_string().as_bytes())?;
    f.sync_all()?;
    fs::rename(&tmp, p)?;
    File::open(p.parent().unwrap())?.sync_all()
}
pub struct Spool {
    pub dir: PathBuf,
    pub sequence: u64,
    pub bytes: usize,
    pub truncated: bool,
    pub failed: bool,
}
impl Spool {
    pub fn new(dir: PathBuf) -> io::Result<Self> {
        fs::create_dir(&dir)?;
        Ok(Self {
            dir,
            sequence: 0,
            bytes: 0,
            truncated: false,
            failed: false,
        })
    }
    pub fn append(&mut self, stream: &str, b: &[u8]) {
        let keep = b.len().min(LIMIT - self.bytes);
        self.truncated |= keep < b.len();
        if keep == 0 || self.failed {
            return;
        }
        self.sequence += 1;
        self.bytes += keep;
        let v = json!({"sequence":self.sequence,"stream":stream,"data":hex(&b[..keep])});
        if persist(&self.dir.join(self.sequence.to_string()), &v).is_err() {
            self.failed = true;
        }
    }
}

/// Replay only a persisted payload; the adapter distinguishes transient transport
/// failure (None) from a rejection. No retry of process execution belongs here.
pub fn deliver(
    mut send: impl FnMut() -> io::Result<Option<Value>>,
    lost: impl Fn() -> bool,
) -> io::Result<Value> {
    let deadline = std::time::Instant::now() + std::time::Duration::from_secs(12);
    loop {
        if lost() {
            return Err(io::Error::other("authority_lost"));
        }
        match send()? {
            Some(v) => return Ok(v),
            None if std::time::Instant::now() < deadline => {
                std::thread::sleep(std::time::Duration::from_millis(200))
            }
            None => return Err(io::Error::other("delivery_unconfirmed")),
        }
    }
}

#[cfg(test)]
mod tests {
    use super::*;
    #[test]
    fn disk_write_failure_is_explicit_and_does_not_replace_accepted_chunk() {
        let dir = std::env::temp_dir().join(format!(
            "ec-spool-{}-{}",
            std::process::id(),
            std::time::SystemTime::now()
                .duration_since(std::time::UNIX_EPOCH)
                .unwrap()
                .as_nanos()
        ));
        let mut spool = Spool::new(dir.clone()).unwrap();
        spool.append("stdout", b"accepted");
        let accepted = fs::read(dir.join("1")).unwrap();
        // A real filesystem conflict forces a write failure without a mock sink.
        fs::create_dir(dir.join("2.tmp")).unwrap();
        spool.append("stderr", b"pending");
        assert!(spool.failed);
        assert!(!dir.join("2").exists());
        assert_eq!(fs::read(dir.join("1")).unwrap(), accepted);
        fs::remove_dir_all(dir).unwrap();
    }
}
