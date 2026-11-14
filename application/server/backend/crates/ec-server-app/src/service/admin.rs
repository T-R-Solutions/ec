use super::http::serve;
use super::{AnyError, connect, name, random};
use ec_server_orchestration as orchestration;
use postgres::Client;
use std::{fs::OpenOptions, io::Write, os::unix::fs::OpenOptionsExt};
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
                tx.batch_execute(include_str!("../../../../migrations/001_publication.sql"))?;
            }
            if tx
                .query_opt("SELECT version FROM ec_migrations WHERE version=2", &[])?
                .is_none()
            {
                tx.batch_execute(include_str!("../../../../migrations/002_orchestration.sql"))?;
            }
            if tx
                .query_opt("SELECT version FROM ec_migrations WHERE version=3", &[])?
                .is_none()
            {
                tx.batch_execute(include_str!("../../../../migrations/003_results.sql"))?;
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
    tx.execute("INSERT INTO sessions(hash,identity,expires) VALUES($1,$2,clock_timestamp()+make_interval(secs => $3))", &[&ec_contracts::hash(token.as_bytes()),actor,&f64::from(ec_contracts::SESSION_SECONDS)])?;
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
    if !ec_contracts::valid_commit(&args[3]) {
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
