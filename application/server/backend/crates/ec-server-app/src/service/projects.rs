use super::protocol::{before_commit, body, envelope, positive, validate_pipeline};
use ec_server_orchestration as orchestration;
use ec_server_projects::publication::{self, Candidate, Failure};
use serde_json::{Value, json};
use tiny_http::Request;
#[derive(Clone, Copy)]
pub(super) struct ProjectRequest<'a> {
    pub(super) actor: &'a str,
    pub(super) token: &'a str,
    pub(super) project: &'a str,
    pub(super) role: &'a str,
    pub(super) method: &'a str,
    pub(super) route: &'a [&'a str],
}
impl ProjectRequest<'_> {
    pub(super) fn handle(
        self,
        request: &mut Request,
        mut transaction: postgres::Transaction<'_>,
    ) -> Result<Value, Failure> {
        let db = |_| (500, "database");
        let Self {
            actor,
            token,
            project,
            role,
            method,
            route,
            ..
        } = self;
        if method == "GET" && route.len() == 6 && route[5] == "agents" {
            let rows=transaction.query("SELECT a.name,a.revoked,COALESCE(s.expires>clock_timestamp(),false) AND NOT a.revoked FROM agents a LEFT JOIN agent_sessions s ON s.id=a.current_session WHERE a.project=$1 ORDER BY a.name", &[&project]).map_err(db)?;
            return Ok(
                json!({"agents":rows.iter().map(|request|json!({"name":request.get::<_,String>(0),"revoked":request.get::<_,bool>(1),"sessionValid":request.get::<_,bool>(2)})).collect::<Vec<_>>()}),
            );
        }
        if method == "POST" && route.len() == 6 && route[5] == "validation" {
            return validate_pipeline(request, project);
        }
        let publishing = method == "POST" || (route.len() == 7 && route[5] == "receipts");
        if publishing && role == "viewer" {
            return Err((403, "permission"));
        }
        if method == "GET"
            && route.len() == 10
            && route[5] == "runs"
            && route[7] == "attempts"
            && route[9] == "output"
        {
            orchestration::guard(&mut transaction)?;
            if ec_server_access::authenticate(&mut transaction, token)
                .map_err(db)?
                .is_none()
            {
                return Err((401, "session"));
            }
            orchestration::project_attempt(
                &mut transaction,
                project,
                positive(route[6])?,
                positive(route[8])?,
            )?;
            return ec_server_results::read(&mut transaction, positive(route[8])?);
        }
        if route.len() >= 6 && ["runs", "run-receipts"].contains(&route[5]) {
            return self.runs(request, transaction);
        }
        if method == "GET" && route.len() == 5 {
            let row = transaction
                .query_one(
                    "SELECT published,effective FROM projects WHERE name=$1",
                    &[&project],
                )
                .map_err(db)?;
            return Ok(
                json!({"project":project,"published":row.get::<_,i64>(0),"effective":row.get::<_,i64>(1)}),
            );
        }
        if method == "GET" && route.len() == 7 && route[5] == "receipts" {
            return publication::receipt(&mut transaction, project, actor, route[6])
                .map_err(db)?
                .ok_or((404, "receipt"));
        }
        if method == "GET" && route.len() == 7 && route[5] == "revisions" {
            let rev = route[6].parse::<i64>().map_err(|_| (400, "revision"))?;
            let row = transaction
                .query_opt(
                    "SELECT yaml,hash,projection FROM revisions WHERE project=$1 AND revision=$2",
                    &[&project, &rev],
                )
                .map_err(db)?
                .ok_or((404, "revision"))?;
            let yaml = String::from_utf8(row.get(0)).map_err(|_| (500, "stored_yaml"))?;
            return Ok(
                json!({"revision":rev,"yaml":yaml,"sha256":row.get::<_,String>(1),"projection":serde_json::from_str::<Value>(&row.get::<_,String>(2)).map_err(|_|(500,"projection"))?}),
            );
        }
        if method != "POST" || route.len() != 6 || route[5] != "publications" {
            return Err((400, "route"));
        }
        self.publish(request, transaction)
    }
    fn runs(
        self,
        request: &mut Request,
        mut transaction: postgres::Transaction<'_>,
    ) -> Result<Value, Failure> {
        let db = |_| (500, "database");
        let Self {
            actor,
            token,
            project,
            method,
            route,
            ..
        } = self;
        let payload = if method == "POST" {
            Some(body(request)?)
        } else {
            None
        };
        orchestration::guard(&mut transaction)?;
        // Recheck expiry after waiting on the orchestration guard.
        if ec_server_access::authenticate(&mut transaction, token)
            .map_err(db)?
            .is_none()
        {
            return Err((401, "session"));
        }
        let result = if method == "GET" && route.len() == 7 && route[5] == "run-receipts" {
            orchestration::submission_receipt(&mut transaction, project, actor, route[6])?
                .ok_or((404, "receipt"))?
        } else if method == "GET" && route.len() == 6 && route[5] == "runs" {
            orchestration::list_runs(&mut transaction, project)?
        } else if method == "GET" && route.len() == 7 && route[5] == "runs" {
            orchestration::get_run(&mut transaction, project, positive(route[6])?)?
        } else if method == "POST" && route.len() == 8 && route[5] == "runs" && route[7] == "cancel"
        {
            envelope(payload.as_ref().ok_or((400, "body"))?, &[])?;
            orchestration::cancel(&mut transaction, project, positive(route[6])?)?
        } else if method == "POST" && route.len() == 6 && route[5] == "runs" {
            let payload = payload.as_ref().ok_or((400, "body"))?;
            envelope(payload, &["requestId", "revisionId"])?;
            let key = payload["requestId"]
                .as_str()
                .filter(|s| ec_contracts::valid_request_id(s))
                .ok_or((400, "request_id"))?;
            let revision = payload["revisionId"]
                .as_i64()
                .filter(|n| *n > 0)
                .ok_or((400, "revision"))?;
            orchestration::submit(
                &mut transaction,
                project,
                actor,
                key,
                revision,
                |transaction| {
                    let snapshot = publication::snapshot(transaction, project, revision)?
                        .ok_or((404, "revision"))?;
                    Ok(orchestration::Snapshot {
                        revision,
                        hash: snapshot.hash,
                        commit: snapshot.commit,
                        configuration: snapshot.configuration,
                    })
                },
            )?
        } else {
            return Err((400, "route"));
        };
        before_commit(request)?;
        transaction.commit().map_err(db)?;
        Ok(result)
    }
    fn publish(
        self,
        request: &mut Request,
        mut transaction: postgres::Transaction<'_>,
    ) -> Result<Value, Failure> {
        let db = |_| (500, "database");
        let Self {
            actor,
            token,
            project,
            ..
        } = self;
        let payload = body(request)?;
        if payload.as_object().is_none_or(|o| o.len() != 3) {
            return Err((400, "envelope"));
        }
        let id = payload["requestId"].as_str().ok_or((400, "request_id"))?;
        if !ec_contracts::valid_request_id(id) {
            return Err((400, "request_id"));
        }
        let base = payload["baseRevision"]
            .as_i64()
            .filter(|b| *b >= 0)
            .ok_or((400, "base"))?;
        let yaml = payload["yaml"].as_str().ok_or((400, "yaml"))?;
        if yaml.len() > ec_contracts::MAX_INPUT_BYTES {
            return Err((413, "yaml_limit"));
        }
        let result = publication::publish(
            &mut transaction,
            Candidate {
                project,
                actor,
                id,
                base,
                yaml,
            },
            |transaction| {
                // Reavaliar expiração depois da espera no lock, inclusive em replay.
                if ec_server_access::authenticate(transaction, token)
                    .map_err(db)?
                    .is_none()
                {
                    return Err((401, "session"));
                }
                Ok(())
            },
            |transaction| {
                let fields = ec_server_configuration::parse(yaml).map_err(|_| (422, "yaml"))?;
                let model = ec_server_projects::ProjectPipeline::validate(&fields)
                    .map_err(|_| (422, "schema"))?;
                if model.project != project {
                    return Err((422, "project_reference"));
                }
                if transaction.query_opt(
                    "SELECT path FROM repositories WHERE project=$1 AND path=$2 AND commit_sha=$3 FOR SHARE",
                    &[&project, &model.repository, &model.commit],
                ).map_err(db)?.is_none() { return Err((422, "repository_reference")); }
                serde_json::to_string(&fields).map_err(|_| (500, "projection"))
            },
        )?;
        if result.created {
            before_commit(request)?;
        }
        if ec_server_access::authenticate(&mut transaction, token)
            .map_err(db)?
            .is_none()
        {
            return Err((401, "session"));
        }
        transaction.commit().map_err(db)?;
        Ok(result.receipt)
    }
}
