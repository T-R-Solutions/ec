CREATE TABLE output_logs(attempt_id bigint REFERENCES attempts, sequence bigint CHECK(sequence>0), stream text CHECK(stream IN ('stdout','stderr')), data bytea NOT NULL CHECK(octet_length(data)<=65536), receipt text NOT NULL, PRIMARY KEY(attempt_id,sequence));
CREATE TABLE output_uploads(attempt_id bigint PRIMARY KEY REFERENCES attempts, size bigint NOT NULL CHECK(size BETWEEN 0 AND 16777216), sha256 text NOT NULL CHECK(sha256 ~ '^[0-9a-f]{64}$'), receipt text);
CREATE TABLE output_parts(attempt_id bigint REFERENCES output_uploads, sequence bigint CHECK(sequence>0), data bytea NOT NULL CHECK(octet_length(data)<=65536), PRIMARY KEY(attempt_id,sequence));
CREATE TRIGGER immutable_log BEFORE UPDATE OR DELETE ON output_logs FOR EACH ROW EXECUTE FUNCTION immutable_record();
CREATE TRIGGER immutable_part BEFORE UPDATE OR DELETE ON output_parts FOR EACH ROW EXECUTE FUNCTION immutable_record();
INSERT INTO ec_migrations VALUES(3);
