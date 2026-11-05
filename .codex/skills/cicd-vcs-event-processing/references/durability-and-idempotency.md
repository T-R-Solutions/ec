# Durable ingestion
Use uniqueness constraint over provider + tenant + installation + repository + delivery ID; compare payload digest on duplicate IDs and reject conflicting reuse.
Transactionally insert accepted event and trigger intent (outbox). Return acknowledgment after commit, not after in-memory queue insertion. On crash after commit before response, redelivery returns deduplicated acceptance.
SQLite fixture demonstrates local transaction/uniqueness only; production PostgreSQL requires multi-writer race, rollback and crash tests.
A single delivery may intentionally produce multiple pipeline intents under a versioned fan-out policy; uniqueness must include pipeline identity where required. No exactly-once guarantee for external effects.
