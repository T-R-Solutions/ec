CREATE TABLE agents(
 name text PRIMARY KEY, project text NOT NULL REFERENCES projects,
 credential_hash text NOT NULL UNIQUE, current_session text,
 revoked boolean NOT NULL DEFAULT false
);
CREATE TABLE agent_sessions(
 id text PRIMARY KEY, agent text NOT NULL REFERENCES agents,
 token_hash text NOT NULL UNIQUE, expires timestamptz NOT NULL,
 UNIQUE(id,agent)
);
ALTER TABLE agents ADD FOREIGN KEY(current_session) REFERENCES agent_sessions(id);
CREATE TABLE runs(
 id bigint GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
 project text NOT NULL, revision bigint NOT NULL, config_hash text NOT NULL,
 commit_sha text NOT NULL CHECK(commit_sha ~ '^[0-9a-f]{40}$'),
 configuration text NOT NULL,
 state text NOT NULL DEFAULT 'queued' CHECK(state IN ('queued','assigned','running','succeeded','failed','timed_out','cancelled')),
 cancel_requested boolean NOT NULL DEFAULT false,
 result text,
 FOREIGN KEY(project,revision) REFERENCES revisions
);
CREATE TABLE run_receipts(
 project text NOT NULL REFERENCES projects, identity text NOT NULL REFERENCES identities,
 request_id text NOT NULL, revision bigint NOT NULL, run_id bigint NOT NULL UNIQUE REFERENCES runs,
 receipt text NOT NULL, PRIMARY KEY(project,identity,request_id)
);
CREATE SEQUENCE attempt_fence;
CREATE TABLE attempts(
 id bigint GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
 run_id bigint NOT NULL REFERENCES runs,
 ordinal integer NOT NULL CHECK(ordinal BETWEEN 1 AND 2),
 agent text NOT NULL REFERENCES agents, session_id text NOT NULL,
 fence bigint NOT NULL UNIQUE DEFAULT nextval('attempt_fence'),
 lease_until timestamptz NOT NULL,
 state text NOT NULL CHECK(state IN ('assigned','running','succeeded','failed','timed_out','cancelled','preparation_failed','lease_lost')),
 reservation_receipt text NOT NULL DEFAULT '{}', result_payload text, result_receipt text,
 UNIQUE(run_id,ordinal), FOREIGN KEY(session_id,agent) REFERENCES agent_sessions(id,agent)
);
CREATE UNIQUE INDEX one_active_run ON attempts(run_id) WHERE state IN ('assigned','running');
CREATE UNIQUE INDEX one_active_slot ON attempts(agent) WHERE state IN ('assigned','running');
CREATE INDEX queued_runs ON runs(id) WHERE state='queued';
CREATE TRIGGER immutable_run_receipt BEFORE UPDATE OR DELETE ON run_receipts FOR EACH ROW EXECUTE FUNCTION immutable_record();
CREATE FUNCTION protect_run() RETURNS trigger LANGUAGE plpgsql AS $$
BEGIN
 IF (OLD.project,OLD.revision,OLD.config_hash,OLD.commit_sha,OLD.configuration) IS DISTINCT FROM (NEW.project,NEW.revision,NEW.config_hash,NEW.commit_sha,NEW.configuration) THEN RAISE EXCEPTION 'immutable snapshot'; END IF;
 IF OLD.state IN ('succeeded','failed','timed_out','cancelled') AND NEW IS DISTINCT FROM OLD THEN RAISE EXCEPTION 'terminal run'; END IF;
 RETURN NEW;
END $$;
CREATE TRIGGER protect_run BEFORE UPDATE ON runs FOR EACH ROW EXECUTE FUNCTION protect_run();
CREATE FUNCTION protect_attempt() RETURNS trigger LANGUAGE plpgsql AS $$
BEGIN
 IF OLD.state NOT IN ('assigned','running') AND NEW IS DISTINCT FROM OLD THEN RAISE EXCEPTION 'terminal attempt'; END IF;
 IF (OLD.run_id,OLD.ordinal,OLD.agent,OLD.session_id,OLD.fence) IS DISTINCT FROM (NEW.run_id,NEW.ordinal,NEW.agent,NEW.session_id,NEW.fence) THEN RAISE EXCEPTION 'immutable ownership'; END IF;
 RETURN NEW;
END $$;
CREATE TRIGGER protect_attempt BEFORE UPDATE ON attempts FOR EACH ROW EXECUTE FUNCTION protect_attempt();
CREATE TRIGGER preserve_run BEFORE DELETE ON runs FOR EACH ROW EXECUTE FUNCTION immutable_record();
CREATE TRIGGER preserve_attempt BEFORE DELETE ON attempts FOR EACH ROW EXECUTE FUNCTION immutable_record();
INSERT INTO ec_migrations VALUES(2);
