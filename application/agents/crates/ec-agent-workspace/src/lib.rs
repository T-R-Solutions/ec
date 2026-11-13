//! Local admitted Git only. No source cleanup, network or inherited Git config.
use std::os::unix::{
    fs::{MetadataExt, OpenOptionsExt},
    process::CommandExt,
};
use std::{
    fs,
    io::{self, Read},
    path::{Component, Path, PathBuf},
    process::{Command, Stdio},
    thread,
    time::{Duration, Instant},
};
fn git(args: &[&str], stop: &impl Fn() -> bool) -> io::Result<()> {
    let mut child = Command::new("/usr/bin/git")
        .args([
            "-c",
            "core.hooksPath=/dev/null",
            "-c",
            "protocol.allow=never",
            "-c",
            "protocol.file.allow=always",
        ])
        .args(args)
        .env_clear()
        .env("PATH", "/usr/bin:/bin")
        .env("GIT_CONFIG_NOSYSTEM", "1")
        .env("GIT_CONFIG_GLOBAL", "/dev/null")
        .env("GIT_TERMINAL_PROMPT", "0")
        .stdout(Stdio::null())
        .stderr(Stdio::null())
        .process_group(0)
        .spawn()?;
    let start = Instant::now();
    let pid = i32::try_from(child.id()).map_err(|_| io::Error::other("process_id"))?;
    loop {
        if let Some(s) = child.try_wait()? {
            return if s.success() {
                Ok(())
            } else {
                Err(io::Error::other("git_failed"))
            };
        }
        if stop() || start.elapsed() > Duration::from_secs(30) {
            // SAFETY: own live child process group, negative id selects group; no pointers.
            unsafe {
                libc::kill(-pid, libc::SIGKILL);
            }
            child.wait()?;
            return Err(io::Error::other("preparation_stopped"));
        }
        thread::sleep(Duration::from_millis(20));
    }
}
/// Prepara checkout isolado de um repositório local previamente admitido.
///
/// # Errors
/// Retorna erro de admissão, filesystem, Git, cancelamento ou prazo excedido.
pub fn prepare(
    root: &Path,
    id: i64,
    repository: &str,
    commit: &str,
    allow: &[PathBuf],
    stop: &impl Fn() -> bool,
) -> io::Result<PathBuf> {
    let source = fs::canonicalize(repository)?;
    if !allow.contains(&source)
        || source.to_str() != Some(repository)
        || commit.len() != 40
        || !commit
            .bytes()
            .all(|b| b.is_ascii_hexdigit() && !b.is_ascii_uppercase())
    {
        return Err(io::Error::other("repository_not_admitted"));
    }
    let dir = root.join(format!("attempt-{id}"));
    fs::create_dir(&dir)?;
    let checkout = dir.join("checkout");
    let dest = checkout.to_str().ok_or_else(|| io::Error::other("path"))?;
    git(
        &[
            "clone",
            "--no-checkout",
            "--no-hardlinks",
            "--template=",
            "--",
            repository,
            dest,
        ],
        stop,
    )?;
    git(&["-C", dest, "checkout", "--detach", commit], stop)?;
    // rev-parse produces a bounded SHA; repository is the newly created clone.
    let head = fs::read_to_string(checkout.join(".git/HEAD"))?;
    if head.trim() != commit {
        return Err(io::Error::other("head_mismatch"));
    }
    Ok(checkout)
}
/// Lê um artefato regular interno ao checkout, sem seguir symlinks.
///
/// # Errors
/// Retorna erro para caminho inválido, link, tipo/tamanho proibido ou falha de leitura.
pub fn artifact(root: &Path, name: &str) -> io::Result<Vec<u8>> {
    let path = Path::new(name);
    if path
        .components()
        .any(|c| !matches!(c, Component::Normal(_)))
    {
        return Err(io::Error::other("artifact_path"));
    }
    let mut p = root.to_path_buf();
    for c in path.components() {
        p.push(c);
        if fs::symlink_metadata(&p)?.file_type().is_symlink() {
            return Err(io::Error::other("artifact_symlink"));
        }
    }
    let mut f = fs::OpenOptions::new()
        .read(true)
        .custom_flags(libc::O_NOFOLLOW | libc::O_NONBLOCK)
        .open(p)?;
    let before = f.metadata()?;
    if !before.is_file() || before.len() > 16_777_216 {
        return Err(io::Error::other("artifact_type_or_limit"));
    }
    let mut b = Vec::new();
    (&mut f).take(16_777_217).read_to_end(&mut b)?;
    let after = f.metadata()?;
    if b.len() as u64 != before.len()
        || before.len() != after.len()
        || before.mtime_nsec() != after.mtime_nsec()
        || before.mtime() != after.mtime()
    {
        return Err(io::Error::other("artifact_changed"));
    }
    Ok(b)
}

#[cfg(test)]
mod tests {
    use super::*;
    #[test]
    fn real_files_reject_escape_nonregular_missing_and_oversize() {
        let root = std::env::temp_dir().join(format!(
            "ec-artifact-{}-{}",
            std::process::id(),
            std::time::SystemTime::now()
                .duration_since(std::time::UNIX_EPOCH)
                .unwrap()
                .as_nanos()
        ));
        fs::create_dir(&root).unwrap();
        fs::write(root.join("valid"), b"\xff\0real").unwrap();
        assert_eq!(artifact(&root, "valid").unwrap(), b"\xff\0real");
        assert!(artifact(&root, "../outside").is_err());
        assert!(artifact(&root, "/etc/passwd").is_err());
        assert!(artifact(&root, "missing").is_err());
        std::os::unix::fs::symlink("/etc/passwd", root.join("escape")).unwrap();
        assert!(artifact(&root, "escape").is_err());
        std::os::unix::fs::symlink("/etc", root.join("parent")).unwrap();
        assert!(artifact(&root, "parent/passwd").is_err());
        fs::create_dir(root.join("directory")).unwrap();
        assert!(artifact(&root, "directory").is_err());
        let large = fs::File::create(root.join("large")).unwrap();
        large.set_len(16_777_217).unwrap();
        assert!(artifact(&root, "large").is_err());
        fs::remove_dir_all(root).unwrap();
    }
}
