
CREATE TABLE IF NOT EXISTS ec_migrations(version integer PRIMARY KEY);
CREATE TABLE identities(name text PRIMARY KEY);
CREATE TABLE sessions(hash text PRIMARY KEY, identity text NOT NULL REFERENCES identities, expires timestamptz NOT NULL, revoked boolean NOT NULL DEFAULT false);
CREATE TABLE projects(name text PRIMARY KEY, published bigint NOT NULL DEFAULT 0, effective bigint NOT NULL DEFAULT 0);
CREATE TABLE grants(project text REFERENCES projects, identity text REFERENCES identities, role text NOT NULL CHECK(role IN ('viewer','operator','admin')), PRIMARY KEY(project,identity));
CREATE TABLE repositories(project text REFERENCES projects, path text NOT NULL, commit_sha text NOT NULL, PRIMARY KEY(project,path,commit_sha));
CREATE TABLE revisions(project text REFERENCES projects, revision bigint CHECK(revision>0), yaml bytea NOT NULL, hash text NOT NULL, projection text NOT NULL, PRIMARY KEY(project,revision));
CREATE TABLE receipts(project text, identity text REFERENCES identities, request_id text, base bigint NOT NULL, revision bigint NOT NULL, PRIMARY KEY(project,identity,request_id), FOREIGN KEY(project,revision) REFERENCES revisions);
CREATE FUNCTION immutable_record() RETURNS trigger LANGUAGE plpgsql AS $$ BEGIN RAISE EXCEPTION 'immutable'; END $$;
CREATE TRIGGER immutable_revision BEFORE UPDATE OR DELETE ON revisions FOR EACH ROW EXECUTE FUNCTION immutable_record();
CREATE TRIGGER immutable_receipt BEFORE UPDATE OR DELETE ON receipts FOR EACH ROW EXECUTE FUNCTION immutable_record();
INSERT INTO ec_migrations VALUES(1);
