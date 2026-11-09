//! Attempt output; authority and terminal transitions belong to orchestration.
use ec_server_access::hash;
use ec_server_orchestration::{self as control, Failure, Owner};
use postgres::Transaction;
use serde_json::{Value, json};
fn db(_: postgres::Error) -> Failure {
    (500, "database")
}
fn decode(v: &Value) -> Result<Vec<u8>, Failure> {
    let s = v.as_str().ok_or((400, "hex"))?;
    if s.len() > 131072 || !s.len().is_multiple_of(2) {
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
    b.iter().map(|b| format!("{b:02x}")).collect()
}
fn seq(p: &Value) -> Result<i64, Failure> {
    p["sequence"]
        .as_i64()
        .filter(|s| *s > 0)
        .ok_or((400, "sequence"))
}
fn parse(s: String) -> Result<Value, Failure> {
    serde_json::from_str(&s).map_err(|_| (500, "stored_json"))
}
pub fn read(t: &mut Transaction<'_>, id: i64) -> Result<Value, Failure> {
    let logs=t.query("SELECT sequence,stream,data,receipt FROM output_logs WHERE attempt_id=$1 ORDER BY sequence", &[&id]).map_err(db)?.into_iter().map(|r|json!({"sequence":r.get::<_,i64>(0),"stream":r.get::<_,String>(1),"data":hex(&r.get::<_,Vec<u8>>(2)),"receipt":parse(r.get(3)).ok()})).collect::<Vec<_>>();
    let upload=t.query_opt("SELECT size,sha256,receipt FROM output_uploads WHERE attempt_id=$1", &[&id]).map_err(db)?.map(|r|json!({"size":r.get::<_,i64>(0),"sha256":r.get::<_,String>(1),"receipt":r.get::<_,Option<String>>(2).and_then(|s|parse(s).ok())}));
    let parts = t
        .query(
            "SELECT sequence,data FROM output_parts WHERE attempt_id=$1 ORDER BY sequence",
            &[&id],
        )
        .map_err(db)?
        .into_iter()
        .map(|r| json!({"sequence":r.get::<_,i64>(0),"data":hex(&r.get::<_,Vec<u8>>(1))}))
        .collect::<Vec<_>>();
    // Partial data is staging, explicitly distinct from a published artifact.
    Ok(json!({"logs":logs,"upload":upload,"parts":parts}))
}
pub fn mutate(
    t: &mut Transaction<'_>,
    o: &Owner,
    id: i64,
    action: &str,
    p: &Value,
) -> Result<Value, Failure> {
    let fence = p["fence"].as_i64().ok_or((400, "fence"))?;
    control::output_authority(t, o, id, fence, false)?;
    if action == "finish" {
        let a = control::owned_attempt(t, o, id)?;
        if !a["resultReceipt"].is_null() {
            return control::mutate(t, o, id, "result", p);
        }
        control::output_authority(t, o, id, fence, true)?;
        let last: i64 = t
            .query_one(
                "SELECT COALESCE(max(sequence),0) FROM output_logs WHERE attempt_id=$1",
                &[&id],
            )
            .map_err(db)?
            .get(0);
        if p["output"]["lastSequence"] != last || !p["output"]["truncated"].is_boolean() {
            return Err((409, "logs_incomplete"));
        }
        if p["output"]["artifactError"].is_null()
            && t.query_opt(
                "SELECT attempt_id FROM output_uploads WHERE attempt_id=$1 AND receipt IS NOT NULL",
                &[&id],
            )
            .map_err(db)?
            .is_none()
        {
            return Err((409, "artifact_incomplete"));
        }
        return control::mutate(t, o, id, "result", p);
    }
    if action == "logs" || action == "artifact-chunk" {
        let n = seq(p)?;
        let data = decode(&p["data"])?;
        if data.is_empty() || n > 2048 {
            return Err((413, "chunk_count_or_empty"));
        }
        let existing_chunk_query = if action == "logs" {
            "SELECT data,stream FROM output_logs WHERE attempt_id=$1 AND sequence=$2"
        } else {
            "SELECT data FROM output_parts WHERE attempt_id=$1 AND sequence=$2"
        };
        if let Some(r) = t.query_opt(existing_chunk_query, &[&id, &n]).map_err(db)? {
            if r.get::<_, Vec<u8>>(0) != data
                || (action == "logs" && p["stream"] != r.get::<_, String>(1))
            {
                return Err((409, "chunk_conflict"));
            }
            return Ok(json!({"sequence":n,"bytes":data.len()}));
        }
        control::output_authority(t, o, id, fence, true)?;
        let totals_query = if action == "logs" {
            "SELECT COALESCE(max(sequence),0),COALESCE(sum(octet_length(data)),0)::bigint FROM output_logs WHERE attempt_id=$1"
        } else {
            "SELECT COALESCE(max(sequence),0),COALESCE(sum(octet_length(data)),0)::bigint FROM output_parts WHERE attempt_id=$1"
        };
        let r = t.query_one(totals_query, &[&id]).map_err(db)?;
        if n != r.get::<_, i64>(0) + 1 {
            return Err((409, "sequence_gap"));
        }
        let total = r.get::<_, i64>(1) + data.len() as i64;
        if total > 16777216 {
            return Err((413, "output_limit"));
        }
        let receipt = json!({"sequence":n,"bytes":data.len()});
        if action == "logs" {
            let stream = p["stream"]
                .as_str()
                .filter(|s| ["stdout", "stderr"].contains(s))
                .ok_or((400, "stream"))?;
            t.execute(
                "INSERT INTO output_logs VALUES($1,$2,$3,$4,$5)",
                &[&id, &n, &stream, &data, &receipt.to_string()],
            )
            .map_err(db)?;
        } else {
            let u = t
                .query_opt(
                    "SELECT size,receipt FROM output_uploads WHERE attempt_id=$1",
                    &[&id],
                )
                .map_err(db)?
                .ok_or((409, "upload_missing"))?;
            if total > u.get::<_, i64>(0) || u.get::<_, Option<String>>(1).is_some() {
                return Err((409, "upload_closed"));
            }
            t.execute(
                "INSERT INTO output_parts VALUES($1,$2,$3)",
                &[&id, &n, &data],
            )
            .map_err(db)?;
        }
        return Ok(receipt);
    }
    if action == "artifact-start" {
        let size = p["size"]
            .as_i64()
            .filter(|s| (0..=16777216).contains(s))
            .ok_or((413, "artifact_limit"))?;
        let sha = p["sha256"]
            .as_str()
            .filter(|s| {
                s.len() == 64
                    && s.bytes()
                        .all(|b| b.is_ascii_hexdigit() && !b.is_ascii_uppercase())
            })
            .ok_or((400, "sha256"))?;
        if let Some(r) = t
            .query_opt(
                "SELECT size,sha256 FROM output_uploads WHERE attempt_id=$1",
                &[&id],
            )
            .map_err(db)?
        {
            if r.get::<_, i64>(0) != size || r.get::<_, String>(1) != sha {
                return Err((409, "upload_conflict"));
            }
        } else {
            control::output_authority(t, o, id, fence, true)?;
            t.execute(
                "INSERT INTO output_uploads(attempt_id,size,sha256) VALUES($1,$2,$3)",
                &[&id, &size, &sha],
            )
            .map_err(db)?;
        }
        return Ok(json!({"size":size,"sha256":sha}));
    }
    if action == "artifact-finish" {
        let u = t
            .query_opt(
                "SELECT size,sha256,receipt FROM output_uploads WHERE attempt_id=$1",
                &[&id],
            )
            .map_err(db)?
            .ok_or((409, "upload_missing"))?;
        if let Some(s) = u.get::<_, Option<String>>(2) {
            return parse(s);
        }
        control::output_authority(t, o, id, fence, true)?;
        let mut bytes = Vec::new();
        for r in t
            .query(
                "SELECT data FROM output_parts WHERE attempt_id=$1 ORDER BY sequence",
                &[&id],
            )
            .map_err(db)?
        {
            bytes.extend(r.get::<_, Vec<u8>>(0));
        }
        if bytes.len() as i64 != u.get::<_, i64>(0) || hash(&bytes) != u.get::<_, String>(1) {
            return Err((409, "artifact_integrity"));
        }
        let receipt = json!({"attemptId":id,"size":bytes.len(),"sha256":hash(&bytes)});
        t.execute(
            "UPDATE output_uploads SET receipt=$2 WHERE attempt_id=$1",
            &[&id, &receipt.to_string()],
        )
        .map_err(db)?;
        return Ok(receipt);
    }
    Err((400, "route"))
}
