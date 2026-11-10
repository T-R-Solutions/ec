use ec_agent_workspace::prepare;
use std::{fs, path::PathBuf, process::Command};

struct Fixture(PathBuf);
impl Drop for Fixture {
    fn drop(&mut self) {
        let _ = fs::remove_dir_all(&self.0);
    }
}

fn git(args: &[&str]) -> String {
    let output = Command::new("/usr/bin/git")
        .args(args)
        .env("GIT_CONFIG_NOSYSTEM", "1")
        .env("GIT_CONFIG_GLOBAL", "/dev/null")
        .output()
        .unwrap();
    assert!(
        output.status.success(),
        "{}",
        String::from_utf8_lossy(&output.stderr)
    );
    String::from_utf8(output.stdout).unwrap().trim().to_owned()
}

#[test]
fn checkout_uses_admitted_snapshot_and_preserves_the_source() {
    let dir = std::env::temp_dir().join(format!(
        "ec-checkout-{}-{}",
        std::process::id(),
        std::time::SystemTime::now()
            .duration_since(std::time::UNIX_EPOCH)
            .unwrap()
            .as_nanos()
    ));
    fs::create_dir(&dir).unwrap();
    let fixture = Fixture(dir.canonicalize().unwrap());
    let source = fixture.0.join("source");
    fs::create_dir(&source).unwrap();
    let repo = source.to_str().unwrap();
    git(&["init", "-q", repo]);
    fs::write(source.join("pinned"), "first").unwrap();
    git(&["-C", repo, "add", "."]);
    git(&[
        "-C",
        repo,
        "-c",
        "user.name=Fixture",
        "-c",
        "user.email=fixture@example.invalid",
        "commit",
        "-qm",
        "initial",
    ]);
    let sha = git(&["-C", repo, "rev-parse", "HEAD"]);
    fs::write(source.join("pinned"), "uncommitted").unwrap();
    fs::write(source.join("untracked"), "preserve").unwrap();
    let checkout = prepare(
        &fixture.0,
        1,
        repo,
        &sha,
        std::slice::from_ref(&source),
        &|| false,
    )
    .unwrap();
    assert_eq!(
        fs::read_to_string(checkout.join("pinned")).unwrap(),
        "first"
    );
    assert!(!checkout.join("untracked").exists());
    assert_eq!(
        fs::read_to_string(source.join("pinned")).unwrap(),
        "uncommitted"
    );
    assert_eq!(
        fs::read_to_string(source.join("untracked")).unwrap(),
        "preserve"
    );
    assert!(
        prepare(
            &fixture.0,
            1,
            repo,
            &sha,
            std::slice::from_ref(&source),
            &|| false
        )
        .is_err()
    );
    assert!(!source.join("checkout").exists());
    for (id, allowed, commit) in [
        (2, vec![], sha.clone()),
        (3, vec![source.clone()], "A".repeat(40)),
        (4, vec![source.clone()], "0".repeat(39)),
    ] {
        assert!(prepare(&fixture.0, id, repo, &commit, &allowed, &|| false).is_err());
        assert!(!fixture.0.join(format!("attempt-{id}")).exists());
    }
    assert!(
        prepare(
            &fixture.0,
            5,
            repo,
            &"0".repeat(40),
            std::slice::from_ref(&source),
            &|| false
        )
        .is_err()
    );
    assert!(!fixture.0.join("attempt-5/checkout/pinned").exists());
}
