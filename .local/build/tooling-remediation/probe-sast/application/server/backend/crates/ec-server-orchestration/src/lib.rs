//! Durable single-slot orchestration. Callers own commit and user authorization.
use ec_server_access::hash;
use postgres::Transaction;
use serde_json::{Value, json};
pub type Failure = (u16, &'static str);
fn db(_: postgres::Error) -> Failure {
    (500, "database")
}
fn value(s: String) -> Result<Value, Failure> {
    serde_json::from_str(&s).map_err(|_| (500, "stored_json"))
}
pub fn guard(t: &mut Transaction<'_>) -> Result<(), Failure> {
    t.batch_execute("SELECT pg_advisory_xact_lock(746232); CREATE TEMP TABLE ec_operation_clock ON COMMIT DROP AS SELECT clock_timestamp() AS instant").map_err(db)
}
pub fn reconcile(t: &mut Transaction<'_>) -> Result<(), Failure> {
    let rows = t.query("SELECT a.id,a.run_id,a.ordinal,r.cancel_requested FROM attempts a JOIN runs r ON r.id=a.run_id JOIN agents g ON g.name=a.agent JOIN agent_sessions s ON s.id=a.session_id WHERE a.state IN ('assigned','running') AND (a.lease_until<=(SELECT instant FROM ec_operation_clock) OR g.current_session<>a.session_id OR g.revoked OR s.expires<=(SELECT instant FROM ec_operation_clock)) ORDER BY a.id FOR UPDATE OF a,r", &[]).map_err(db)?;
    for r in rows {
        let (id, run, n, cancel): (i64, i64, i32, bool) = (r.get(0), r.get(1), r.get(2), r.get(3));
        t.execute("UPDATE attempts SET state='lease_lost' WHERE id=$1", &[&id])
            .map_err(db)?;
        let state = if cancel {
            "cancelled"
        } else if n == 2 {
            "failed"
        } else {
            "queued"
        };
        let result = if cancel {
            Some(json!({"outcome":"cancelled","cause":"lease_lost"}).to_string())
        } else if n == 2 {
            Some(json!({"outcome":"failed","cause":"attempts_exhausted"}).to_string())
        } else {
            None
        };
        t.execute(
            "UPDATE runs SET state=$2,result=$3 WHERE id=$1",
            &[&run, &state, &result],
        )
        .map_err(db)?;
    }
    Ok(())
}
/// Snapshot is supplied by the authorized publication boundary, never by HTTP input.
pub struct Snapshot {
    pub revision: i64,
    pub hash: String,
    pub commit: String,
    pub configuration: String,
}
pub fn submission_receipt(
    t: &mut Transaction<'_>,
    project: &str,
    actor: &str,
    key: &str,
) -> Result<Option<Value>, Failure> {
    t.query_opt(
        "SELECT receipt FROM run_receipts WHERE project=$1 AND identity=$2 AND request_id=$3",
        &[&project, &actor, &key],
    )
    .map_err(db)?
    .map(|r| value(r.get(0)))
    .transpose()
}
pub fn submit(
    t: &mut Transaction<'_>,
    project: &str,
    actor: &str,
    key: &str,
    s: Snapshot,
) -> Result<Value, Failure> {
    if let Some(old) = submission_receipt(t, project, actor, key)? {
        return if old["revisionId"] == s.revision {
            Ok(old)
        } else {
            Err((409, "request_conflict"))
        };
    }
    let id:i64=t.query_one("INSERT INTO runs(project,revision,config_hash,commit_sha,configuration) VALUES($1,$2,$3,$4,$5) RETURNING id", &[&project,&s.revision,&s.hash,&s.commit,&s.configuration]).map_err(db)?.get(0);
    let receipt = json!({"requestId":key,"runId":id,"revisionId":s.revision,"configHash":s.hash,"commit":s.commit});
    t.execute(
        "INSERT INTO run_receipts VALUES($1,$2,$3,$4,$5,$6)",
        &[
            &project,
            &actor,
            &key,
            &s.revision,
            &id,
            &receipt.to_string(),
        ],
    )
    .map_err(db)?;
    Ok(receipt)
}
pub fn get_run(t: &mut Transaction<'_>, project: &str, id: i64) -> Result<Value, Failure> {
    let r=t.query_opt("SELECT revision,config_hash,commit_sha,state,cancel_requested,result FROM runs WHERE project=$1 AND id=$2", &[&project,&id]).map_err(db)?.ok_or((404,"run"))?;
    let attempts = t
        .query(
            "SELECT id FROM attempts WHERE run_id=$1 ORDER BY ordinal",
            &[&id],
        )
        .map_err(db)?
        .into_iter()
        .map(|r| attempt(t, r.get(0)))
        .collect::<Result<Vec<_>, _>>()?;
    let result = r.get::<_, Option<String>>(5).map(value).transpose()?;
    Ok(
        json!({"runId":id,"revisionId":r.get::<_,i64>(0),"configHash":r.get::<_,String>(1),"commit":r.get::<_,String>(2),"state":r.get::<_,String>(3),"cancelRequested":r.get::<_,bool>(4),"result":result,"attempts":attempts}),
    )
}
pub fn list_runs(t: &mut Transaction<'_>, project: &str) -> Result<Value, Failure> {
    let ids = t
        .query(
            "SELECT id FROM runs WHERE project=$1 ORDER BY id",
            &[&project],
        )
        .map_err(db)?;
    Ok(
        json!({"runs":ids.into_iter().map(|r|get_run(t,project,r.get(0))).collect::<Result<Vec<_>,_>>()?}),
    )
}
pub fn cancel(t: &mut Transaction<'_>, project: &str, id: i64) -> Result<Value, Failure> {
    let r = t
        .query_opt(
            "SELECT state FROM runs WHERE project=$1 AND id=$2 FOR UPDATE",
            &[&project, &id],
        )
        .map_err(db)?
        .ok_or((404, "run"))?;
    let state: String = r.get(0);
    if !["queued", "assigned", "running"].contains(&state.as_str()) {
        return Ok(json!({"effective":false,"state":state}));
    }
    t.execute("UPDATE runs SET cancel_requested=true,state=CASE WHEN state='queued' THEN 'cancelled' ELSE state END,result=CASE WHEN state='queued' THEN '{\"outcome\":\"cancelled\",\"cause\":\"queued_cancel\"}' ELSE result END WHERE id=$1", &[&id]).map_err(db)?;
    Ok(
        json!({"effective":true,"state":if state=="queued" {"cancelled"} else {&state},"cancelRequested":true}),
    )
}
pub fn provision(
    t: &mut Transaction<'_>,
    name: &str,
    project: &str,
    token: &str,
) -> Result<(), Failure> {
    t.execute(
        "INSERT INTO agents(name,project,credential_hash) VALUES($1,$2,$3)",
        &[&name, &project, &hash(token.as_bytes())],
    )
    .map_err(db)?;
    Ok(())
}
pub fn session(
    t: &mut Transaction<'_>,
    credential: &str,
    id: &str,
    token: &str,
) -> Result<Value, Failure> {
    let agent: String = t
        .query_opt(
            "SELECT name FROM agents WHERE credential_hash=$1 AND NOT revoked FOR UPDATE",
            &[&hash(credential.as_bytes())],
        )
        .map_err(db)?
        .ok_or((401, "agent_credential"))?
        .get(0);
    t.execute("INSERT INTO agent_sessions VALUES($1,$2,$3,(SELECT instant FROM ec_operation_clock)+interval '8 hours')", &[&id,&agent,&hash(token.as_bytes())]).map_err(db)?;
    t.execute(
        "UPDATE agents SET current_session=$2 WHERE name=$1",
        &[&agent, &id],
    )
    .map_err(db)?;
    Ok(json!({"agentSessionId":id,"token":token,"protocolVersion":1,"leaseSeconds":30}))
}
pub struct Owner {
    agent: String,
    session: String,
    project: String,
}
pub fn authenticate(t: &mut Transaction<'_>, token: &str) -> Result<Owner, Failure> {
    let r=t.query_opt("SELECT g.name,s.id,g.project FROM agents g JOIN agent_sessions s ON s.id=g.current_session WHERE s.token_hash=$1 AND NOT g.revoked AND s.expires>(SELECT instant FROM ec_operation_clock) FOR SHARE OF g,s", &[&hash(token.as_bytes())]).map_err(db)?.ok_or((401,"agent_session"))?;
    Ok(Owner {
        agent: r.get(0),
        session: r.get(1),
        project: r.get(2),
    })
}
fn attempt(t: &mut Transaction<'_>, id: i64) -> Result<Value, Failure> {
    let r=t.query_opt("SELECT run_id,ordinal,agent,session_id,fence,lease_until::text,state,reservation_receipt,result_receipt FROM attempts WHERE id=$1", &[&id]).map_err(db)?.ok_or((404,"attempt"))?;
    let receipt = r.get::<_, Option<String>>(8).map(value).transpose()?;
    Ok(
        json!({"attemptId":id,"runId":r.get::<_,i64>(0),"ordinal":r.get::<_,i32>(1),"agentId":r.get::<_,String>(2),"agentSessionId":r.get::<_,String>(3),"fence":r.get::<_,i64>(4),"leaseUntil":r.get::<_,String>(5),"state":r.get::<_,String>(6),"reservationReceipt":value(r.get(7))?,"resultReceipt":receipt}),
    )
}
pub fn owned_attempt(t: &mut Transaction<'_>, o: &Owner, id: i64) -> Result<Value, Failure> {
    if t.query_opt(
        "SELECT id FROM attempts WHERE id=$1 AND agent=$2",
        &[&id, &o.agent],
    )
    .map_err(db)?
    .is_none()
    {
        return Err((404, "attempt"));
    }
    attempt(t, id)
}
pub fn owned_attempts(t: &mut Transaction<'_>, o: &Owner) -> Result<Value, Failure> {
    let ids = t
        .query(
            "SELECT id FROM attempts WHERE agent=$1 ORDER BY id",
            &[&o.agent],
        )
        .map_err(db)?;
    Ok(
        json!({"attempts":ids.into_iter().map(|r|attempt(t,r.get(0))).collect::<Result<Vec<_>,_>>()?}),
    )
}
pub fn reserve(t: &mut Transaction<'_>, o: &Owner) -> Result<Value, Failure> {
    reconcile(t)?;
    if let Some(r) = t
        .query_opt(
            "SELECT id FROM attempts WHERE agent=$1 AND state IN ('assigned','running')",
            &[&o.agent],
        )
        .map_err(db)?
    {
        return dispatch(t, r.get(0));
    }
    let Some(r)=t.query_opt("SELECT id FROM runs WHERE project=$1 AND state='queued' ORDER BY id LIMIT 1 FOR UPDATE", &[&o.project]).map_err(db)? else {return Ok(json!({"attempt":null}));};
    let run: i64 = r.get(0);
    let id:i64=t.query_one("INSERT INTO attempts(run_id,ordinal,agent,session_id,lease_until,state) SELECT $1,COALESCE(max(ordinal),0)+1,$2,$3,(SELECT instant FROM ec_operation_clock)+interval '30 seconds','assigned' FROM attempts WHERE run_id=$1 RETURNING id", &[&run,&o.agent,&o.session]).map_err(db)?.get(0);
    let a = attempt(t, id)?;
    let receipt = json!({"attemptId":id,"runId":run,"ordinal":a["ordinal"],"agentSessionId":o.session,"fence":a["fence"],"leaseUntil":a["leaseUntil"]});
    t.execute(
        "UPDATE attempts SET reservation_receipt=$2 WHERE id=$1",
        &[&id, &receipt.to_string()],
    )
    .map_err(db)?;
    t.execute("UPDATE runs SET state='assigned' WHERE id=$1", &[&run])
        .map_err(db)?;
    dispatch(t, id)
}
fn dispatch(t: &mut Transaction<'_>, id: i64) -> Result<Value, Failure> {
    let a = attempt(t, id)?;
    let run = a["runId"].as_i64().ok_or((500, "run"))?;
    let r=t.query_one("SELECT revision,config_hash,commit_sha,configuration,cancel_requested FROM runs WHERE id=$1", &[&run]).map_err(db)?;
    Ok(
        json!({"attempt":a,"revisionId":r.get::<_,i64>(0),"configHash":r.get::<_,String>(1),"commit":r.get::<_,String>(2),"configuration":value(r.get(3))?,"cancelRequested":r.get::<_,bool>(4)}),
    )
}
pub fn mutate(
    t: &mut Transaction<'_>,
    o: &Owner,
    id: i64,
    action: &str,
    p: &Value,
) -> Result<Value, Failure> {
    let fence = p["fence"].as_i64().ok_or((400, "fence"))?;
    let a = owned_attempt(t, o, id)?;
    if a["agentSessionId"] != o.session || a["fence"] != fence {
        return Err((409, "stale_owner"));
    }
    let r=t.query_one("SELECT a.run_id,a.state,a.lease_until>(SELECT instant FROM ec_operation_clock),a.result_payload,a.result_receipt,r.cancel_requested FROM attempts a JOIN runs r ON r.id=a.run_id WHERE a.id=$1 FOR UPDATE OF a,r", &[&id]).map_err(db)?;
    let (run, state, valid, cancel): (i64, String, bool, bool) =
        (r.get(0), r.get(1), r.get(2), r.get(5));
    let result_action = action == "result";
    if result_action {
        let outcome = p["outcome"].as_str().ok_or((400, "outcome"))?;
        let exit = &p["exitCode"];
        let code = exit.as_i64().filter(|c| i32::try_from(*c).is_ok());
        if !match outcome {
            "succeeded" => code == Some(0),
            "failed" => {
                code.is_some_and(|c| c != 0)
                    || (exit.is_null() && p["output"]["signal"].as_i64().is_some_and(|s| s > 0))
            }
            "timed_out" | "cancelled" | "preparation_failed" => exit.is_null(),
            _ => false,
        } {
            return Err((422, "result"));
        }
        if let Some(old) = r.get::<_, Option<String>>(3) {
            if value(old)? != *p {
                return Err((409, "result_conflict"));
            }
            return value(r.get::<_, String>(4));
        }
    }
    if !valid || !["assigned", "running"].contains(&state.as_str()) {
        return Err((409, "lease_or_state"));
    }
    match action {
        "heartbeat" => {
            t.execute("UPDATE attempts SET lease_until=(SELECT instant FROM ec_operation_clock)+interval '30 seconds' WHERE id=$1", &[&id]).map_err(db)?;
            Ok(json!({"attempt":attempt(t,id)?,"cancelRequested":cancel}))
        }
        "running" => {
            if cancel {
                return Err((409, "cancel_requested"));
            }
            t.execute("UPDATE attempts SET state='running' WHERE id=$1", &[&id])
                .map_err(db)?;
            t.execute("UPDATE runs SET state='running' WHERE id=$1", &[&run])
                .map_err(db)?;
            Ok(json!({"attempt":attempt(t,id)?}))
        }
        "result" => {
            let reported = p["outcome"].as_str().ok_or((400, "outcome"))?;
            if (reported == "preparation_failed" && state != "assigned")
                || (reported == "cancelled" && !cancel)
                || (state == "assigned"
                    && reported != "preparation_failed"
                    && !(reported == "cancelled" && cancel))
            {
                return Err((409, "transition"));
            }
            let outcome = if cancel { "cancelled" } else { reported };
            let run_state = if outcome == "preparation_failed" {
                "failed"
            } else {
                outcome
            };
            let receipt = json!({"attemptId":id,"runId":run,"fence":fence,"state":run_state,"reportedOutcome":reported,"exitCode":p["exitCode"],"output":p.get("output")});
            t.execute(
                "UPDATE attempts SET state=$2,result_payload=$3,result_receipt=$4 WHERE id=$1",
                &[&id, &outcome, &p.to_string(), &receipt.to_string()],
            )
            .map_err(db)?;
            t.execute(
                "UPDATE runs SET state=$2,result=$3 WHERE id=$1",
                &[&run, &run_state, &receipt.to_string()],
            )
            .map_err(db)?;
            Ok(receipt)
        }
        _ => Err((400, "route")),
    }
}

/// Local administrative revocation, serialized with all attempt authorities.
pub fn revoke(t: &mut Transaction<'_>, agent: &str) -> Result<(), Failure> {
    t.execute("UPDATE agents SET revoked=true WHERE name=$1", &[&agent])
        .map_err(db)?;
    Ok(())
}

/// Caller must hold guard. Read access does not confer mutation authority.
pub fn output_authority(
    t: &mut Transaction<'_>,
    o: &Owner,
    id: i64,
    fence: i64,
    new_content: bool,
) -> Result<(), Failure> {
    let a = owned_attempt(t, o, id)?;
    if a["agentSessionId"] != o.session || a["fence"] != fence {
        return Err((409, "stale_owner"));
    }
    if new_content {
        let valid:bool=t.query_one("SELECT state IN ('assigned','running') AND lease_until>(SELECT instant FROM ec_operation_clock) FROM attempts WHERE id=$1", &[&id]).map_err(db)?.get(0);
        if !valid {
            return Err((409, "lease_or_state"));
        }
    }
    Ok(())
}
/// Public authorization adapter for project-scoped result retrieval.
pub fn project_attempt(
    t: &mut Transaction<'_>,
    project: &str,
    run: i64,
    id: i64,
) -> Result<(), Failure> {
    if t.query_opt("SELECT a.id FROM attempts a JOIN runs r ON r.id=a.run_id WHERE a.id=$1 AND r.id=$2 AND r.project=$3", &[&id,&run,&project]).map_err(db)?.is_none(){return Err((404,"attempt"));}
    Ok(())
}
