//! Durable single-slot orchestration. Callers own commit and user authorization.
use ec_server_access::hash;
use postgres::Transaction;
use serde_json::{Value, json};
pub type Failure = (u16, &'static str);
fn db(_: postgres::Error) -> Failure {
    (500, "database")
}
fn value(snapshot: &str) -> Result<Value, Failure> {
    serde_json::from_str(snapshot).map_err(|_| (500, "stored_json"))
}
/// Serializa a operação e captura o relógio PostgreSQL da transação.
///
/// # Errors
/// Retorna falha de banco ao obter o guard ou criar o relógio temporário.
pub fn guard(transaction: &mut Transaction<'_>) -> Result<(), Failure> {
    transaction.batch_execute("SELECT pg_advisory_xact_lock(746232); CREATE TEMP TABLE ec_operation_clock ON COMMIT DROP AS SELECT clock_timestamp() AS instant").map_err(db)
}
/// Reconcilia leases e tentativas inválidas com a fila durável.
///
/// # Errors
/// Retorna falha de banco nas leituras ou atualizações da reconciliação.
pub fn reconcile(transaction: &mut Transaction<'_>) -> Result<(), Failure> {
    let rows = transaction.query("SELECT a.id,a.run_id,a.ordinal,r.cancel_requested FROM attempts a JOIN runs r ON r.id=a.run_id JOIN agents g ON g.name=a.agent JOIN agent_sessions s ON s.id=a.session_id WHERE a.state IN ('assigned','running') AND (a.lease_until<=(SELECT instant FROM ec_operation_clock) OR g.current_session<>a.session_id OR g.revoked OR s.expires<=(SELECT instant FROM ec_operation_clock)) ORDER BY a.id FOR UPDATE OF a,r", &[]).map_err(db)?;
    for row in rows {
        let (id, run, ordinal, cancel): (i64, i64, i32, bool) =
            (row.get(0), row.get(1), row.get(2), row.get(3));
        transaction
            .execute("UPDATE attempts SET state='lease_lost' WHERE id=$1", &[&id])
            .map_err(db)?;
        let state = if cancel {
            "cancelled"
        } else if ordinal == 2 {
            "failed"
        } else {
            "queued"
        };
        let result = if cancel {
            Some(json!({"outcome":"cancelled","cause":"lease_lost"}).to_string())
        } else if ordinal == 2 {
            Some(json!({"outcome":"failed","cause":"attempts_exhausted"}).to_string())
        } else {
            None
        };
        transaction
            .execute(
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
/// Consulta o recibo idempotente de uma submissão.
///
/// # Errors
/// Retorna falha de banco ou recibo persistido inválido.
pub fn submission_receipt(
    transaction: &mut Transaction<'_>,
    project: &str,
    actor: &str,
    key: &str,
) -> Result<Option<Value>, Failure> {
    transaction
        .query_opt(
            "SELECT receipt FROM run_receipts WHERE project=$1 AND identity=$2 AND request_id=$3",
            &[&project, &actor, &key],
        )
        .map_err(db)?
        .map(|row| value(row.get(0)))
        .transpose()
}
/// Submete uma revisão autorizada ou recupera a solicitação idempotente.
///
/// # Errors
/// Retorna conflito de revisão, falha de banco ou recibo inválido.
pub fn submit(
    transaction: &mut Transaction<'_>,
    project: &str,
    actor: &str,
    key: &str,
    snapshot: &Snapshot,
) -> Result<Value, Failure> {
    if let Some(old) = submission_receipt(transaction, project, actor, key)? {
        return if old["revisionId"] == snapshot.revision {
            Ok(old)
        } else {
            Err((409, "request_conflict"))
        };
    }
    let id:i64=transaction.query_one("INSERT INTO runs(project,revision,config_hash,commit_sha,configuration) VALUES($1,$2,$3,$4,$5) RETURNING id", &[&project,&snapshot.revision,&snapshot.hash,&snapshot.commit,&snapshot.configuration]).map_err(db)?.get(0);
    let receipt = json!({"requestId":key,"runId":id,"revisionId":snapshot.revision,"configHash":snapshot.hash,"commit":snapshot.commit});
    transaction
        .execute(
            "INSERT INTO run_receipts VALUES($1,$2,$3,$4,$5,$6)",
            &[
                &project,
                &actor,
                &key,
                &snapshot.revision,
                &id,
                &receipt.to_string(),
            ],
        )
        .map_err(db)?;
    Ok(receipt)
}
/// Recupera a execução e suas tentativas no projeto.
///
/// # Errors
/// Retorna execução ausente, falha de banco ou estado JSON inválido.
pub fn get_run(
    transaction: &mut Transaction<'_>,
    project: &str,
    id: i64,
) -> Result<Value, Failure> {
    let row=transaction.query_opt("SELECT revision,config_hash,commit_sha,state,cancel_requested,result FROM runs WHERE project=$1 AND id=$2", &[&project,&id]).map_err(db)?.ok_or((404,"run"))?;
    let attempts = transaction
        .query(
            "SELECT id FROM attempts WHERE run_id=$1 ORDER BY ordinal",
            &[&id],
        )
        .map_err(db)?
        .into_iter()
        .map(|row| attempt(transaction, row.get(0)))
        .collect::<Result<Vec<_>, _>>()?;
    let result = row
        .get::<_, Option<String>>(5)
        .as_deref()
        .map(value)
        .transpose()?;
    Ok(
        json!({"runId":id,"revisionId":row.get::<_,i64>(0),"configHash":row.get::<_,String>(1),"commit":row.get::<_,String>(2),"state":row.get::<_,String>(3),"cancelRequested":row.get::<_,bool>(4),"result":result,"attempts":attempts}),
    )
}
/// Lista as execuções confirmadas de um projeto.
///
/// # Errors
/// Retorna falha de banco ou erro ao reconstruir uma execução.
pub fn list_runs(transaction: &mut Transaction<'_>, project: &str) -> Result<Value, Failure> {
    let ids = transaction
        .query(
            "SELECT id FROM runs WHERE project=$1 ORDER BY id",
            &[&project],
        )
        .map_err(db)?;
    Ok(
        json!({"runs":ids.into_iter().map(|row|get_run(transaction,project,row.get(0))).collect::<Result<Vec<_>,_>>()?}),
    )
}
/// Solicita cancelamento respeitando o estado confirmado da execução.
///
/// # Errors
/// Retorna execução ausente ou falha de banco.
pub fn cancel(transaction: &mut Transaction<'_>, project: &str, id: i64) -> Result<Value, Failure> {
    let row = transaction
        .query_opt(
            "SELECT state FROM runs WHERE project=$1 AND id=$2 FOR UPDATE",
            &[&project, &id],
        )
        .map_err(db)?
        .ok_or((404, "run"))?;
    let state: String = row.get(0);
    if !["queued", "assigned", "running"].contains(&state.as_str()) {
        return Ok(json!({"effective":false,"state":state}));
    }
    transaction.execute("UPDATE runs SET cancel_requested=true,state=CASE WHEN state='queued' THEN 'cancelled' ELSE state END,result=CASE WHEN state='queued' THEN '{\"outcome\":\"cancelled\",\"cause\":\"queued_cancel\"}' ELSE result END WHERE id=$1", &[&id]).map_err(db)?;
    Ok(
        json!({"effective":true,"state":if state=="queued" {"cancelled"} else {&state},"cancelRequested":true}),
    )
}
/// Provisiona a identidade local de um Agent no projeto.
///
/// # Errors
/// Retorna falha de banco ao inserir a identidade.
pub fn provision(
    transaction: &mut Transaction<'_>,
    name: &str,
    project: &str,
    token: &str,
) -> Result<(), Failure> {
    transaction
        .execute(
            "INSERT INTO agents(name,project,credential_hash) VALUES($1,$2,$3)",
            &[&name, &project, &hash(token.as_bytes())],
        )
        .map_err(db)?;
    Ok(())
}
/// Cria uma sessão do Agent usando sua credencial provisionada.
///
/// # Errors
/// Retorna credencial inválida ou falha de banco.
pub fn session(
    transaction: &mut Transaction<'_>,
    credential: &str,
    id: &str,
    token: &str,
) -> Result<Value, Failure> {
    let agent: String = transaction
        .query_opt(
            "SELECT name FROM agents WHERE credential_hash=$1 AND NOT revoked FOR UPDATE",
            &[&hash(credential.as_bytes())],
        )
        .map_err(db)?
        .ok_or((401, "agent_credential"))?
        .get(0);
    transaction.execute("INSERT INTO agent_sessions VALUES($1,$2,$3,(SELECT instant FROM ec_operation_clock)+interval '8 hours')", &[&id,&agent,&hash(token.as_bytes())]).map_err(db)?;
    transaction
        .execute(
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
/// Resolve o proprietário de uma sessão Agent válida.
///
/// # Errors
/// Retorna sessão inválida ou falha de banco.
pub fn authenticate(transaction: &mut Transaction<'_>, token: &str) -> Result<Owner, Failure> {
    let row=transaction.query_opt("SELECT g.name,s.id,g.project FROM agents g JOIN agent_sessions s ON s.id=g.current_session WHERE s.token_hash=$1 AND NOT g.revoked AND s.expires>(SELECT instant FROM ec_operation_clock) FOR SHARE OF g,s", &[&hash(token.as_bytes())]).map_err(db)?.ok_or((401,"agent_session"))?;
    Ok(Owner {
        agent: row.get(0),
        session: row.get(1),
        project: row.get(2),
    })
}
fn attempt(transaction: &mut Transaction<'_>, id: i64) -> Result<Value, Failure> {
    let row=transaction.query_opt("SELECT run_id,ordinal,agent,session_id,fence,lease_until::text,state,reservation_receipt,result_receipt FROM attempts WHERE id=$1", &[&id]).map_err(db)?.ok_or((404,"attempt"))?;
    let receipt = row
        .get::<_, Option<String>>(8)
        .as_deref()
        .map(value)
        .transpose()?;
    Ok(
        json!({"attemptId":id,"runId":row.get::<_,i64>(0),"ordinal":row.get::<_,i32>(1),"agentId":row.get::<_,String>(2),"agentSessionId":row.get::<_,String>(3),"fence":row.get::<_,i64>(4),"leaseUntil":row.get::<_,String>(5),"state":row.get::<_,String>(6),"reservationReceipt":value(row.get(7))?,"resultReceipt":receipt}),
    )
}
/// Consulta uma tentativa pertencente ao Agent autenticado.
///
/// # Errors
/// Retorna tentativa ausente, falha de banco ou recibo inválido.
pub fn owned_attempt(
    transaction: &mut Transaction<'_>,
    owner: &Owner,
    id: i64,
) -> Result<Value, Failure> {
    if transaction
        .query_opt(
            "SELECT id FROM attempts WHERE id=$1 AND agent=$2",
            &[&id, &owner.agent],
        )
        .map_err(db)?
        .is_none()
    {
        return Err((404, "attempt"));
    }
    attempt(transaction, id)
}
/// Lista as tentativas pertencentes ao Agent autenticado.
///
/// # Errors
/// Retorna falha de banco ou erro ao reconstruir uma tentativa.
pub fn owned_attempts(transaction: &mut Transaction<'_>, owner: &Owner) -> Result<Value, Failure> {
    let ids = transaction
        .query(
            "SELECT id FROM attempts WHERE agent=$1 ORDER BY id",
            &[&owner.agent],
        )
        .map_err(db)?;
    Ok(
        json!({"attempts":ids.into_iter().map(|row|attempt(transaction,row.get(0))).collect::<Result<Vec<_>,_>>()?}),
    )
}
/// Reserva ou recupera uma tentativa ativa para o Agent.
///
/// # Errors
/// Retorna falha de banco ou recibo de tentativa inválido.
pub fn reserve(transaction: &mut Transaction<'_>, owner: &Owner) -> Result<Value, Failure> {
    reconcile(transaction)?;
    if let Some(row) = transaction
        .query_opt(
            "SELECT id FROM attempts WHERE agent=$1 AND state IN ('assigned','running')",
            &[&owner.agent],
        )
        .map_err(db)?
    {
        return dispatch(transaction, row.get(0));
    }
    let Some(row)=transaction.query_opt("SELECT id FROM runs WHERE project=$1 AND state='queued' ORDER BY id LIMIT 1 FOR UPDATE", &[&owner.project]).map_err(db)? else {return Ok(json!({"attempt":null}));};
    let run: i64 = row.get(0);
    let id:i64=transaction.query_one("INSERT INTO attempts(run_id,ordinal,agent,session_id,lease_until,state) SELECT $1,COALESCE(max(ordinal),0)+1,$2,$3,(SELECT instant FROM ec_operation_clock)+interval '30 seconds','assigned' FROM attempts WHERE run_id=$1 RETURNING id", &[&run,&owner.agent,&owner.session]).map_err(db)?.get(0);
    let attempt_state = attempt(transaction, id)?;
    let receipt = json!({"attemptId":id,"runId":run,"ordinal":attempt_state["ordinal"],"agentSessionId":owner.session,"fence":attempt_state["fence"],"leaseUntil":attempt_state["leaseUntil"]});
    transaction
        .execute(
            "UPDATE attempts SET reservation_receipt=$2 WHERE id=$1",
            &[&id, &receipt.to_string()],
        )
        .map_err(db)?;
    transaction
        .execute("UPDATE runs SET state='assigned' WHERE id=$1", &[&run])
        .map_err(db)?;
    dispatch(transaction, id)
}
fn dispatch(transaction: &mut Transaction<'_>, id: i64) -> Result<Value, Failure> {
    let attempt_state = attempt(transaction, id)?;
    let run = attempt_state["runId"].as_i64().ok_or((500, "run"))?;
    let row=transaction.query_one("SELECT revision,config_hash,commit_sha,configuration,cancel_requested FROM runs WHERE id=$1", &[&run]).map_err(db)?;
    Ok(
        json!({"attempt":attempt_state,"revisionId":row.get::<_,i64>(0),"configHash":row.get::<_,String>(1),"commit":row.get::<_,String>(2),"configuration":value(row.get(3))?,"cancelRequested":row.get::<_,bool>(4)}),
    )
}
/// Aplica uma transição sob a autoridade e lease atuais da tentativa.
///
/// # Errors
/// Retorna erro de envelope, autoridade, estado, lease ou banco.
pub fn mutate(
    transaction: &mut Transaction<'_>,
    owner: &Owner,
    id: i64,
    action: &str,
    payload: &Value,
) -> Result<Value, Failure> {
    let fence = payload["fence"].as_i64().ok_or((400, "fence"))?;
    let attempt_state = owned_attempt(transaction, owner, id)?;
    if attempt_state["agentSessionId"] != owner.session || attempt_state["fence"] != fence {
        return Err((409, "stale_owner"));
    }
    let row=transaction.query_one("SELECT a.run_id,a.state,a.lease_until>(SELECT instant FROM ec_operation_clock),a.result_payload,a.result_receipt,r.cancel_requested FROM attempts a JOIN runs r ON r.id=a.run_id WHERE a.id=$1 FOR UPDATE OF a,r", &[&id]).map_err(db)?;
    let (run, state, valid, cancel): (i64, String, bool, bool) =
        (row.get(0), row.get(1), row.get(2), row.get(5));
    let result_action = action == "result";
    if result_action {
        let outcome = payload["outcome"].as_str().ok_or((400, "outcome"))?;
        let exit = &payload["exitCode"];
        let code = exit.as_i64().filter(|c| i32::try_from(*c).is_ok());
        if !match outcome {
            "succeeded" => code == Some(0),
            "failed" => {
                code.is_some_and(|c| c != 0)
                    || (exit.is_null()
                        && payload["output"]["signal"]
                            .as_i64()
                            .is_some_and(|snapshot| snapshot > 0))
            }
            "timed_out" | "cancelled" | "preparation_failed" => exit.is_null(),
            _ => false,
        } {
            return Err((422, "result"));
        }
        if let Some(old) = row.get::<_, Option<String>>(3) {
            if value(&old)? != *payload {
                return Err((409, "result_conflict"));
            }
            return value(row.get::<_, &str>(4));
        }
    }
    if !valid || !["assigned", "running"].contains(&state.as_str()) {
        return Err((409, "lease_or_state"));
    }
    match action {
        "heartbeat" => {
            transaction.execute("UPDATE attempts SET lease_until=(SELECT instant FROM ec_operation_clock)+interval '30 seconds' WHERE id=$1", &[&id]).map_err(db)?;
            Ok(json!({"attempt":attempt(transaction,id)?,"cancelRequested":cancel}))
        }
        "running" => {
            if cancel {
                return Err((409, "cancel_requested"));
            }
            transaction
                .execute("UPDATE attempts SET state='running' WHERE id=$1", &[&id])
                .map_err(db)?;
            transaction
                .execute("UPDATE runs SET state='running' WHERE id=$1", &[&run])
                .map_err(db)?;
            Ok(json!({"attempt":attempt(transaction,id)?}))
        }
        "result" => {
            let reported = payload["outcome"].as_str().ok_or((400, "outcome"))?;
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
            let receipt = json!({"attemptId":id,"runId":run,"fence":fence,"state":run_state,"reportedOutcome":reported,"exitCode":payload["exitCode"],"output":payload.get("output")});
            transaction
                .execute(
                    "UPDATE attempts SET state=$2,result_payload=$3,result_receipt=$4 WHERE id=$1",
                    &[&id, &outcome, &payload.to_string(), &receipt.to_string()],
                )
                .map_err(db)?;
            transaction
                .execute(
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
/// Revoga a identidade do Agent na transação corrente.
///
/// # Errors
/// Retorna falha de banco ao persistir a revogação.
pub fn revoke(transaction: &mut Transaction<'_>, agent: &str) -> Result<(), Failure> {
    transaction
        .execute("UPDATE agents SET revoked=true WHERE name=$1", &[&agent])
        .map_err(db)?;
    Ok(())
}

/// Caller must hold guard. Read access does not confer mutation authority.
/// Verifica a autoridade da tentativa para ler ou publicar saída.
///
/// # Errors
/// Retorna proprietário inválido, lease/estado incompatível ou falha de banco.
pub fn output_authority(
    transaction: &mut Transaction<'_>,
    owner: &Owner,
    id: i64,
    fence: i64,
    new_content: bool,
) -> Result<(), Failure> {
    let attempt_state = owned_attempt(transaction, owner, id)?;
    if attempt_state["agentSessionId"] != owner.session || attempt_state["fence"] != fence {
        return Err((409, "stale_owner"));
    }
    if new_content {
        let valid:bool=transaction.query_one("SELECT state IN ('assigned','running') AND lease_until>(SELECT instant FROM ec_operation_clock) FROM attempts WHERE id=$1", &[&id]).map_err(db)?.get(0);
        if !valid {
            return Err((409, "lease_or_state"));
        }
    }
    Ok(())
}
/// Public authorization adapter for project-scoped result retrieval.
/// Verifica que a tentativa pertence à execução e ao projeto.
///
/// # Errors
/// Retorna tentativa ausente ou falha de banco.
pub fn project_attempt(
    transaction: &mut Transaction<'_>,
    project: &str,
    run: i64,
    id: i64,
) -> Result<(), Failure> {
    if transaction.query_opt("SELECT a.id FROM attempts a JOIN runs r ON r.id=a.run_id WHERE a.id=$1 AND r.id=$2 AND r.project=$3", &[&id,&run,&project]).map_err(db)?.is_none(){return Err((404,"attempt"));}
    Ok(())
}
