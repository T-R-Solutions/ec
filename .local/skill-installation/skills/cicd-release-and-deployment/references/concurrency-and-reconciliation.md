# Concurrency, idempotency and reconciliation

Define one active release operation per `{tenant, environment}` unless the product spec supports safe multiplexing. Enforce with **database constraint** or durable lease and compare-and-swap (CAS), not an in-process boolean. Every deployment attempt has idempotency key scoped to tenant/environment/actor or another documented domain and a request fingerprint. Identical duplicates may return the same handle; conflicting duplicates must fail.

Persist requested state and audit/outbox atomically before acknowledgment. Record provider generation/fencing where supported. On server restart or ambiguous provider response, query authoritative target state instead of assuming prior request completed. Old provider events may not overwrite state for newer attempt/generation. External side effects are not inherently exactly-once, regardless of internal idempotency.

Verify with two independent DB connections, stale CAS updates, simultaneous deploy requests, lost response after commit, replayed approval and delayed provider confirmation. SQLite provides a local concurrency model; PostgreSQL multi-instance transaction/lease semantics remain integration gates.
