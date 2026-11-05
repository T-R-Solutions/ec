# Safe Administrative Commands, Audit and Authorization

> Use the destination's existing requirements and evidence format; named workflow documents are examples, not prerequisites. Optional SDD mapping is configured outside this skill.

## State and privilege boundaries

Command request, actual external effect and audit admission are different facts. Draw a sequence explicitly:

`preview(scope + actor + action + desired/effective revision + impact)` → `authorization + optional reauthentication/independent approval` → `revalidate preview and current permission` → `idempotent durable request admission + audit admission (defined atomic boundary)` → `worker/executor` → `authoritative observation` → `confirmed | failed | unknown`.

Keep `accepted`, `running` and `confirmed` distinct. An HTTP `202 Accepted` implies non-final processing; an ambiguous network failure cannot establish that a worker did nothing. On retry, reuse an appropriately scoped idempotency key; a reused key with a different payload is a conflict, not a new operation. Idempotency scope, TTL, concurrency and replay policy belong to project workflow.

High-impact actions should show user, instance/tenant, affected components, exact command, version, expected downtime/data impact, action owner, reason, available rollback and whether successful execution is externally verifiable. A same-user repeated click may reuse the original request; an unrelated actor is never authorized by another actor's confirmation token.

**No non-atomic promises:** a database transaction can atomically write a request row and an audit event to the same database. It cannot atomically commit a Git revision, remote power cycle, external secret-store change or backup provider call. Use explicit intent/receipt/reconciliation with unknown state and ownership links. An audit entry marked `completed` must be based on a confirmed effect, not a request to attempt the effect.

## Audit event envelope (proposed fields)

`event_id`, `actor_principal_id`, `actor_authn_context`, `impersonation/privileged_mode`, `instance_id`, `project/tenant_scope`, `action`, `target_id`, `request_id`, `idempotency_key_fingerprint`, `authorization_policy_revision`, `desired_revision_before/after` (when applicable), `decision`, `phase`, `outcome`, `occurred_at`, `observed_at`, `trace_id`, `redacted_diff_digest`, `source`, `integrity_state`.

Avoid raw secrets, session tokens, passwords, arbitrary user-submitted query strings, unconstrained request bodies or hidden personal data. Validate length/encoding, neutralize CR/LF/control characters and untrusted HTML, and prevent spreadsheet formula injection in exported CSV (escaping policy and export content types require dedicated testing). Audit read and export are themselves sensitive operations. Retention/disposal must follow approved policy and applicable legal/privacy constraints; not hardcoded here.

**Integrity caveat:** append-only SQL permissions and hash-chain continuity may detect some accidental or unauthorized changes under a bounded threat model. Neither prevents a database superuser, compromised signing key, or backup restore from rewriting both content and the checks. Evaluate independent/off-host integrity checkpoints, segregation, trusted clocks, access monitoring and recovery evidence before claiming tamper evidence.

## Failure and concurrency oracles

- Privilege revoked between preview and submit: reject; log a redacted denial if admission storage permits.
- Desired-policy revision changed: reject stale preview; require new preview and confirmation.
- Same idempotency key, same actor/payload: return same request; no duplicate side effect.
- Same idempotency key, different actor or payload: reject conflict, never inherit another user's privilege.
- Audit write fails before local command commit: rollback the local transaction; no `accepted` result.
- Audit append succeeds but remote effect times out: leave external outcome `unknown` and reconcile; never claim global atomicity.
- Cancellation races with execution: use the owning command service's outcome and do not infer a final state from the button click.
- Audit export read denied: no partial stream or file should escape.
- Break-glass enabled: perform time-bound policy checks and independent audit; do not equate elevated UI mode with actual authorization.

OWASP logging/authorization guidance (O13/O23), NIST log and audit/incident guidance (O14–O16), PostgreSQL transaction isolation (O24/O25), RFC 9110/9457 (O17/O18), operator-mistake empirical studies (P08/P09). See [source register](sources-and-decisions.md).
