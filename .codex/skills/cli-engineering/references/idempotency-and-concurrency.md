# Idempotency, retries, revisions and completion

> Ec-specific rules below apply only to an Ec consumer whose current contract establishes them.

Define key generation, entropy/collision policies, expiry, scope `(tenant/principal, endpoint/operation)` and binding to a canonical request digest. Identical replay should return the existing operation; same key with changed payload must fail explicitly. A timed-out submission may have succeeded: reconcile via operation ID, idempotency key or status endpoint before retry. Error responses and partial-success states must be documented; backoff/jitter are risk-specific decisions with retry budgets, not blanket guarantees.

Use server-enforced revision checks/preconditions to reject stale edits. The fixture SQLite `BEGIN IMMEDIATE` plus `UNIQUE(scope,request_key)` tests a **local transaction** with concurrent threads and illustrates deduplication only. It has no crash durability or cross-service authorization proof for Ec PostgreSQL, nor does it establish the correctness of a remote API.

The semantics of HTTP 202 Accepted are specified by RFC 9110; acceptance does not mean completion. Output should expose a concrete status link or operation ID only when the actual backend provides one. If no status resource exists, mark the requirement as a spec gap; don't fabricate one.
