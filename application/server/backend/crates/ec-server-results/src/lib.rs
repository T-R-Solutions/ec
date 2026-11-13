//! Attempt output; authority and terminal transitions belong to orchestration.
use ec_server_access::hash;
use ec_server_orchestration::{self as control, Failure, Owner};
use postgres::Transaction;
use serde_json::{Value, json};
#[cfg(test)]
mod contracts_tests;
fn db(_: postgres::Error) -> Failure {
    (500, "database")
}
fn decode(v: &Value) -> Result<Vec<u8>, Failure> {
    let s = v.as_str().ok_or((400, "hex"))?;
    if s.len() > 131_072 || !s.len().is_multiple_of(2) {
        return Err((413, "chunk_limit"));
    }
    s.as_bytes()
        .chunks(2)
        .map(|b| {
            let s = std::str::from_utf8(b).map_err(|_| (400, "hex"))?;
            u8::from_str_radix(s, 16).map_err(|_| (400, "hex"))
        })
        .collect()
}
fn hex(b: &[u8]) -> String {
    use std::fmt::Write;
    let mut out = String::with_capacity(b.len() * 2);
    for byte in b {
        let _ = write!(out, "{byte:02x}");
    }
    out
}
fn seq(payload: &Value) -> Result<i64, Failure> {
    payload["sequence"]
        .as_i64()
        .filter(|s| *s > 0)
        .ok_or((400, "sequence"))
}
fn parse(s: &str) -> Result<Value, Failure> {
    serde_json::from_str(s).map_err(|_| (500, "stored_json"))
}
/// Recupera logs, staging e recibo de artefato da tentativa.
///
/// # Errors
/// Retorna falha de banco, envelope, autoridade ou conflito de integridade/estado.
pub fn read(transaction: &mut Transaction<'_>, id: i64) -> Result<Value, Failure> {
    let logs=transaction.query("SELECT sequence,stream,data,receipt FROM output_logs WHERE attempt_id=$1 ORDER BY sequence", &[&id]).map_err(db)?.into_iter().map(|row|json!({"sequence":row.get::<_,i64>(0),"stream":row.get::<_,String>(1),"data":hex(&row.get::<_,Vec<u8>>(2)),"receipt":parse(row.get(3)).ok()})).collect::<Vec<_>>();
    let upload=transaction.query_opt("SELECT size,sha256,receipt FROM output_uploads WHERE attempt_id=$1", &[&id]).map_err(db)?.map(|row|json!({"size":row.get::<_,i64>(0),"sha256":row.get::<_,String>(1),"receipt":row.get::<_,Option<String>>(2).as_deref().and_then(|s|parse(s).ok())}));
    let parts = transaction
        .query(
            "SELECT sequence,data FROM output_parts WHERE attempt_id=$1 ORDER BY sequence",
            &[&id],
        )
        .map_err(db)?
        .into_iter()
        .map(|row| json!({"sequence":row.get::<_,i64>(0),"data":hex(&row.get::<_,Vec<u8>>(1))}))
        .collect::<Vec<_>>();
    // Partial data is staging, explicitly distinct from a published artifact.
    Ok(json!({"logs":logs,"upload":upload,"parts":parts}))
}
/// Persiste saída idempotente sob a autoridade atual da tentativa.
///
/// # Errors
/// Retorna falha de banco, envelope, autoridade ou conflito de integridade/estado.
pub fn mutate(
    transaction: &mut Transaction<'_>,
    owner: &Owner,
    id: i64,
    action: &str,
    payload: &Value,
) -> Result<Value, Failure> {
    let fence = payload["fence"].as_i64().ok_or((400, "fence"))?;
    control::output_authority(transaction, owner, id, fence, false)?;
    match action {
        "finish" => finish(transaction, owner, id, payload, fence),
        "logs" | "artifact-chunk" => chunk(transaction, owner, id, action, payload, fence),
        "artifact-start" => artifact_start(transaction, owner, id, payload, fence),
        "artifact-finish" => artifact_finish(transaction, owner, id, fence),
        _ => Err((400, "route")),
    }
}

fn finish(
    transaction: &mut Transaction<'_>,
    owner: &Owner,
    id: i64,
    payload: &Value,
    fence: i64,
) -> Result<Value, Failure> {
    let a = control::owned_attempt(transaction, owner, id)?;
    if !a["resultReceipt"].is_null() {
        return control::mutate(transaction, owner, id, "result", payload);
    }
    control::output_authority(transaction, owner, id, fence, true)?;
    let last: i64 = transaction
        .query_one(
            "SELECT COALESCE(max(sequence),0) FROM output_logs WHERE attempt_id=$1",
            &[&id],
        )
        .map_err(db)?
        .get(0);
    if payload["output"]["lastSequence"] != last || !payload["output"]["truncated"].is_boolean() {
        return Err((409, "logs_incomplete"));
    }
    if payload["output"]["artifactError"].is_null()
        && transaction
            .query_opt(
                "SELECT attempt_id FROM output_uploads WHERE attempt_id=$1 AND receipt IS NOT NULL",
                &[&id],
            )
            .map_err(db)?
            .is_none()
    {
        return Err((409, "artifact_incomplete"));
    }
    control::mutate(transaction, owner, id, "result", payload)
}

fn chunk(
    transaction: &mut Transaction<'_>,
    owner: &Owner,
    id: i64,
    action: &str,
    payload: &Value,
    fence: i64,
) -> Result<Value, Failure> {
    let sequence = seq(payload)?;
    let data = decode(&payload["data"])?;
    if data.is_empty() || sequence > 2048 {
        return Err((413, "chunk_count_or_empty"));
    }
    let existing_chunk_query = if action == "logs" {
        "SELECT data,stream FROM output_logs WHERE attempt_id=$1 AND sequence=$2"
    } else {
        "SELECT data FROM output_parts WHERE attempt_id=$1 AND sequence=$2"
    };
    if let Some(row) = transaction
        .query_opt(existing_chunk_query, &[&id, &sequence])
        .map_err(db)?
    {
        if row.get::<_, Vec<u8>>(0) != data
            || (action == "logs" && payload["stream"] != row.get::<_, String>(1))
        {
            return Err((409, "chunk_conflict"));
        }
        return Ok(json!({"sequence":sequence,"bytes":data.len()}));
    }
    control::output_authority(transaction, owner, id, fence, true)?;
    let totals_query = if action == "logs" {
        "SELECT COALESCE(max(sequence),0),COALESCE(sum(octet_length(data)),0)::bigint FROM output_logs WHERE attempt_id=$1"
    } else {
        "SELECT COALESCE(max(sequence),0),COALESCE(sum(octet_length(data)),0)::bigint FROM output_parts WHERE attempt_id=$1"
    };
    let row = transaction.query_one(totals_query, &[&id]).map_err(db)?;
    if sequence != row.get::<_, i64>(0) + 1 {
        return Err((409, "sequence_gap"));
    }
    let total =
        row.get::<_, i64>(1) + i64::try_from(data.len()).map_err(|_| (413, "output_limit"))?;
    if total > 16_777_216 {
        return Err((413, "output_limit"));
    }
    let receipt = json!({"sequence":sequence,"bytes":data.len()});
    if action == "logs" {
        let stream = payload["stream"]
            .as_str()
            .filter(|s| ["stdout", "stderr"].contains(s))
            .ok_or((400, "stream"))?;
        transaction
            .execute(
                "INSERT INTO output_logs VALUES($1,$2,$3,$4,$5)",
                &[&id, &sequence, &stream, &data, &receipt.to_string()],
            )
            .map_err(db)?;
    } else {
        let upload = transaction
            .query_opt(
                "SELECT size,receipt FROM output_uploads WHERE attempt_id=$1",
                &[&id],
            )
            .map_err(db)?
            .ok_or((409, "upload_missing"))?;
        if total > upload.get::<_, i64>(0) || upload.get::<_, Option<String>>(1).is_some() {
            return Err((409, "upload_closed"));
        }
        transaction
            .execute(
                "INSERT INTO output_parts VALUES($1,$2,$3)",
                &[&id, &sequence, &data],
            )
            .map_err(db)?;
    }
    Ok(receipt)
}

fn artifact_start(
    transaction: &mut Transaction<'_>,
    owner: &Owner,
    id: i64,
    payload: &Value,
    fence: i64,
) -> Result<Value, Failure> {
    let size = payload["size"]
        .as_i64()
        .filter(|s| (0..=16_777_216).contains(s))
        .ok_or((413, "artifact_limit"))?;
    let sha = payload["sha256"]
        .as_str()
        .filter(|s| {
            s.len() == 64
                && s.bytes()
                    .all(|b| b.is_ascii_hexdigit() && !b.is_ascii_uppercase())
        })
        .ok_or((400, "sha256"))?;
    if let Some(row) = transaction
        .query_opt(
            "SELECT size,sha256 FROM output_uploads WHERE attempt_id=$1",
            &[&id],
        )
        .map_err(db)?
    {
        if row.get::<_, i64>(0) != size || row.get::<_, String>(1) != sha {
            return Err((409, "upload_conflict"));
        }
    } else {
        control::output_authority(transaction, owner, id, fence, true)?;
        transaction
            .execute(
                "INSERT INTO output_uploads(attempt_id,size,sha256) VALUES($1,$2,$3)",
                &[&id, &size, &sha],
            )
            .map_err(db)?;
    }
    Ok(json!({"size":size,"sha256":sha}))
}

fn artifact_finish(
    transaction: &mut Transaction<'_>,
    owner: &Owner,
    id: i64,
    fence: i64,
) -> Result<Value, Failure> {
    let upload = transaction
        .query_opt(
            "SELECT size,sha256,receipt FROM output_uploads WHERE attempt_id=$1",
            &[&id],
        )
        .map_err(db)?
        .ok_or((409, "upload_missing"))?;
    if let Some(s) = upload.get::<_, Option<String>>(2) {
        return parse(&s);
    }
    control::output_authority(transaction, owner, id, fence, true)?;
    let mut bytes = Vec::new();
    for row in transaction
        .query(
            "SELECT data FROM output_parts WHERE attempt_id=$1 ORDER BY sequence",
            &[&id],
        )
        .map_err(db)?
    {
        bytes.extend(row.get::<_, Vec<u8>>(0));
    }
    if i64::try_from(bytes.len()).map_err(|_| (413, "artifact_limit"))? != upload.get::<_, i64>(0)
        || hash(&bytes) != upload.get::<_, String>(1)
    {
        return Err((409, "artifact_integrity"));
    }
    let receipt = json!({"attemptId":id,"size":bytes.len(),"sha256":hash(&bytes)});
    transaction
        .execute(
            "UPDATE output_uploads SET receipt=$2 WHERE attempt_id=$1",
            &[&id, &receipt.to_string()],
        )
        .map_err(db)?;
    Ok(receipt)
}
