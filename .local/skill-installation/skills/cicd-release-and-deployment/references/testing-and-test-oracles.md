# Test matrix and oracles

**Positive:** registered immutable candidate -> approval bound to exact target -> start in window -> canary phases with current healthy metrics -> provider confirmation -> completed, with immutable audit chain.

**Negative:** no approver role, self approval when prohibited, expiration, wrong config revision, wrong tenant, untrusted artifact, missing provenance, stale generation, out-of-window, unsupported strategy, wrong-target receipt, rollback without previous version, incompatible schema, two concurrent active deployments.

**Boundary:** exact change window end, metric equals threshold, one sample below minimum, stage at final weight not yet provider-confirmed, zero traffic, duplicate idempotency key, old results after phase progression.

**Fault:** provider returns accepted but never completes, receipt lost, crash after DB commit, stale callback, unknown metrics, conflicting/partial telemetry, network partition during rollback, failure to abort, migration partially applied.

**Mutations:** disable policy check, skip target binding, treat unknown as pass, remove freshness, accept any receipt, bypass CAS, allow rollback without compatibility or baseline, leak state across tenants. Every mutant must first have a passing baseline oracle and then fail.

**Evidence classification:** synthetic pure model < local SQLite with independent connections < TypeScript contract consumer < provider simulator / network < real Rust/PostgreSQL / actual deployment adapter / fault injection. No lower level substitutes for the higher. Map to accepted acceptance criteria, with explicit `NOT EXECUTED` and `SPEC GAP`.
