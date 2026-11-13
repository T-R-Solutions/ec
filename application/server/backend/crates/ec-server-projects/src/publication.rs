use sha2::{Digest, Sha256};
fn hash(bytes: &[u8]) -> String {
    format!("{:x}", Sha256::digest(bytes))
}
use postgres::Transaction;
use serde_json::{Value, json};
pub type Failure = (u16, &'static str);
/// Consulta um recibo já confirmado para a identidade e projeto.
///
/// # Errors
/// Retorna o erro PostgreSQL se a consulta falhar.
pub fn receipt(
    tx: &mut Transaction<'_>,
    project: &str,
    actor: &str,
    id: &str,
) -> Result<Option<Value>, postgres::Error> {
    Ok(tx.query_opt("SELECT r.revision,r.base,v.hash FROM receipts r JOIN revisions v ON v.project=r.project AND v.revision=r.revision WHERE r.project=$1 AND r.identity=$2 AND r.request_id=$3", &[&project,&actor,&id])?.map(|r| json!({"requestId":id,"revision":r.get::<_,i64>(0),"baseRevision":r.get::<_,i64>(1),"sha256":r.get::<_,String>(2)})))
}
#[derive(Clone, Copy)]
pub struct Candidate<'a> {
    pub project: &'a str,
    pub actor: &'a str,
    pub id: &'a str,
    pub base: i64,
    pub yaml: &'a str,
    pub projection: &'a str,
}
/// Publica a revisão ou recupera o recibo idempotente da mesma solicitação.
///
/// # Errors
/// Retorna falha de banco, recibo inválido ou conflito de base/solicitação.
pub fn publish(tx: &mut Transaction<'_>, c: Candidate<'_>) -> Result<Value, Failure> {
    let db = |_| (500, "database");
    let current: i64 = tx
        .query_one(
            "SELECT published FROM projects WHERE name=$1 FOR UPDATE",
            &[&c.project],
        )
        .map_err(db)?
        .get(0);
    if let Some(r) = receipt(tx, c.project, c.actor, c.id).map_err(db)? {
        let revision = r["revision"].as_i64().ok_or((500, "receipt"))?;
        let old: Vec<u8> = tx
            .query_one(
                "SELECT yaml FROM revisions WHERE project=$1 AND revision=$2",
                &[&c.project, &revision],
            )
            .map_err(db)?
            .get(0);
        return if r["baseRevision"] == c.base && old == c.yaml.as_bytes() {
            Ok(r)
        } else {
            Err((409, "request_conflict"))
        };
    }
    if current != c.base {
        return Err((409, "base_conflict"));
    }
    let next = current.checked_add(1).ok_or((409, "revision_limit"))?;
    let digest = hash(c.yaml.as_bytes());
    tx.execute(
        "INSERT INTO revisions VALUES($1,$2,$3,$4,$5)",
        &[
            &c.project,
            &next,
            &c.yaml.as_bytes(),
            &digest,
            &c.projection,
        ],
    )
    .map_err(db)?;
    tx.execute(
        "UPDATE projects SET published=$2,effective=$2 WHERE name=$1",
        &[&c.project, &next],
    )
    .map_err(db)?;
    tx.execute(
        "INSERT INTO receipts VALUES($1,$2,$3,$4,$5)",
        &[&c.project, &c.actor, &c.id, &c.base, &next],
    )
    .map_err(db)?;
    Ok(json!({"requestId":c.id,"revision":next,"baseRevision":c.base,"sha256":digest}))
}

/// Immutable published snapshot. The composition authorizes project access first.
pub struct PublishedSnapshot {
    pub hash: String,
    pub commit: String,
    pub configuration: String,
}
/// Recupera a configuração imutável publicada.
///
/// # Errors
/// Retorna falha de banco ou inconsistência da projeção/commit persistidos.
pub fn snapshot(
    tx: &mut Transaction<'_>,
    project: &str,
    revision: i64,
) -> Result<Option<PublishedSnapshot>, Failure> {
    let db = |_| (500, "database");
    let Some(r) = tx
        .query_opt(
            "SELECT hash,projection FROM revisions WHERE project=$1 AND revision=$2",
            &[&project, &revision],
        )
        .map_err(db)?
    else {
        return Ok(None);
    };
    let configuration: String = r.get(1);
    let v: Value = serde_json::from_str(&configuration).map_err(|_| (500, "projection"))?;
    let commit = v["commit"].as_str().ok_or((500, "commit"))?.to_owned();
    Ok(Some(PublishedSnapshot {
        hash: r.get(0),
        commit,
        configuration,
    }))
}
