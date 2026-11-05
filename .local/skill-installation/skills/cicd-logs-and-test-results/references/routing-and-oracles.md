# Test oracles
Positive: ordered chunks, idempotent replay, restart cursor, parsed testcases, authorized access.
Negative: same-sequence different bytes, cross-tenant stream, missing sequence, XML DTD/XXE, malformed counts, secret in persisted payload.
Boundary: split UTF-8 character, empty chunk, max size, 0 tests, nested suites, multiple attempts.
Fault: crash before/after commit, producer disconnect, stale attempt, truncation, out-of-order delivery, report upload failure.
Proof levels: source inspection < synthetic model < local SQLite/parser < multi-writer DB + agent < live product. A local fixture cannot certify production secret redaction.
