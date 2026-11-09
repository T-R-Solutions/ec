//! Real Linux process group supervision; trusted local jobs, not a sandbox.
use ec_agent_reporting::Spool;
use std::os::unix::process::CommandExt;
use std::{
    io::{self, Read},
    path::Path,
    process::{Command, ExitStatus, Stdio},
    sync::{Arc, Mutex},
    thread,
    time::{Duration, Instant},
};
fn drain(mut input: impl Read, stream: &str, spool: Arc<Mutex<Spool>>) -> io::Result<()> {
    let mut b = [0; 32768];
    let mut used = 0;
    loop {
        let n = input.read(&mut b[used..])?;
        if n == 0 {
            if used > 0 {
                spool.lock().unwrap().append(stream, &b[..used]);
            }
            return Ok(());
        }
        used += n;
        if used == b.len() {
            spool.lock().unwrap().append(stream, &b);
            used = 0;
        }
    }
}
pub struct Completion {
    pub status: ExitStatus,
    pub reason: Option<&'static str>,
}
pub fn run(
    script: &str,
    cwd: &Path,
    timeout: u64,
    spool: Arc<Mutex<Spool>>,
    stop: impl Fn() -> Option<&'static str>,
) -> io::Result<Completion> {
    let mut child = Command::new("/bin/sh")
        .args(["-c", script])
        .current_dir(cwd)
        .env_clear()
        .env("PATH", "/usr/bin:/bin")
        .env("HOME", cwd)
        .stdin(Stdio::null())
        .stdout(Stdio::piped())
        .stderr(Stdio::piped())
        .process_group(0)
        .spawn()?;
    let pid = child.id() as i32;
    let out = child.stdout.take().unwrap();
    let err = child.stderr.take().unwrap();
    let s = spool.clone();
    let a = thread::spawn(move || drain(out, "stdout", s));
    let b = thread::spawn(move || drain(err, "stderr", spool));
    let start = Instant::now();
    let mut reason = None;
    let status = loop {
        if let Some(status) = child.try_wait()? {
            break status;
        }
        reason = stop()
            .or_else(|| (start.elapsed() >= Duration::from_secs(timeout)).then_some("timed_out"));
        if reason.is_some() {
            // SAFETY: group was created for this live child and has not been reaped.
            unsafe {
                libc::kill(-pid, libc::SIGTERM);
            }
            let grace = Instant::now();
            while grace.elapsed() < Duration::from_secs(2) {
                thread::sleep(Duration::from_millis(20));
            }
            // SAFETY: group remains owned until collection completes, cooperative jobs cannot escape.
            unsafe {
                libc::kill(-pid, libc::SIGKILL);
            }
            break child.wait()?;
        }
        thread::sleep(Duration::from_millis(20));
    };
    // Terminate background descendants even when the shell exits naturally.
    // SAFETY: own group; no PID persisted or reused across restart.
    unsafe {
        libc::kill(-pid, libc::SIGKILL);
    }
    a.join().map_err(|_| io::Error::other("stdout_worker"))??;
    b.join().map_err(|_| io::Error::other("stderr_worker"))??;
    Ok(Completion { status, reason })
}
