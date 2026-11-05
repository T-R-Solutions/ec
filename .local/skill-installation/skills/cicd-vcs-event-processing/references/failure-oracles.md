# Selection and verification
Positive: valid signature, registered repository, allowed ref, pinned revision, one durable trigger.
> Ec-specific rules below apply only to an Ec consumer whose current contract establishes them.

Negative: tampered raw body, absent signature, cross-tenant repository, unsupported event, deleted ref, untrusted fork privileged trigger, conflicting duplicate ID.
Boundary: non-ASCII body, max payload, delivery retries, branch with metacharacters, multiple matching pipelines, SHA algorithm differences.
Fault: crash before/after commit, duplicate concurrent delivery, out-of-order force pushes, provider downtime, rate-limit and replay window.
Proof hierarchy: source inspection < deterministic synthetic fixture < local durable DB < multi-writer PostgreSQL < live-provider interoperability < production Ec. Do not promote lower evidence.
