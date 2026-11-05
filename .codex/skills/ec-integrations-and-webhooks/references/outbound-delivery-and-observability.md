# Outbound Webhooks, SSRF, Reliability and Operational Console

> Use the destination's existing requirements and evidence format; named workflow documents are examples, not prerequisites. Optional SDD mapping is configured outside this skill. Commands, fixture paths and historical execution claims from the source package are illustrative or imported evidence unless the corresponding files are present and independently checked; they are not current test results or bundled executable tools.

## Destination authorization and network boundary

A user-supplied callback URL is a potential SSRF primitive. Use preapproved destination identities whenever possible. If full URLs must be accepted, parse once with a vetted URL parser, require an exact permitted scheme/host/port policy, reject userinfo, fragments, credentials in query/path, ambiguous authorities, HTTP downgrades and redirects that bypass policy. Validate all DNS responses, choose **public or explicitly authorized** addresses, and bind the actual connection to those validated addresses while retaining TLS hostname/SNI/certificate verification. Recheck on reconnect, fallback, retry and redirect; egress ACLs and cloud metadata isolation are defense in depth. A single preflight DNS lookup is **not** a race-free SSRF defense. Local test code is a policy demonstration, **not** a connection-time enforcement implementation.

## Delivery state model (illustrative, requires project workflow agreement)

| Phase | Confirmed fact | Not confirmed |
|---|---|---|
| `pending` | Event exists in durable outbox | A network request was sent |
| `leased` | Worker holds current delivery generation | Remote endpoint is healthy |
| `attempted` | A connection attempt has begun | Receiver committed it |
| `remote_http_accepted` | Observed 2xx to a specific attempt | Remote business effect completed |
| `retry_wait` | Explicit retry budget and next attempt | That previous side effect did not occur |
| `unknown` | Current outcome cannot be resolved | Any claim of failed/succeeded remote effect |
| `dead_lettered` | Delivery exhausted its local policy | A remote compensating action occurred |
| `disabled` | New delivery attempts are blocked by current desired policy | Existing external effects were rolled back |

Use a stable logical event/`Idempotency-Key` where the remote contract supports it, a unique attempt ID for each network request, `config_revision`, `target_id`, and safe correlation metadata. An outbound request signed by Ec should cover exact canonical bytes and required metadata under an project-approved protocol, preferably a documented standard. HTTP `202` (RFC 9110) is asynchronous acceptance, not downstream completion. Do not label an arbitrary HTTP `200` as a completed pipeline/deployment.

## Retry and recovery

- Retry only classified transport errors and documented response classes. Respect `Retry-After` where contractually relevant; use capped exponential backoff with jitter and per-tenant rate limits. Do not create a thundering herd during provider outage.
- Verify the **current** connector YAML revision and effective permission before manual replay/reenable and before any attempt after a policy change. Secrets rotated or removed must not be resurrected by an old worker.
- On timeouts or lost acknowledgments, the peer's effect may have happened. If peer has no idempotency protocol, record `unknown` and require an approved operator decision for potentially dangerous effects; never promise exactly-once external delivery.
- Apply deadline and attempt/age ceilings, durable dead-letter capture, retention/sanitization and controlled replay with reviewable impact. Dead-letter replays are new operations with the same underlying event identity, not a guarantee that a previous effect was absent.

## Required operator interface fields

For each connector: scope (global/project), provider and installation, desired revision vs observed revision, enabled events, permission/credential-ref health **without secret disclosure**, last successful authenticated inbound event, last queued event, outbound attempt count, failure class, redelivery availability, last observation time and explicitly `unknown` status where appropriate. For each receipt/attempt: event ID, source, event type, time received, verification profile, dedup outcome, retry schedule, redacted error, worker generation and trace correlation. Controls: test connection (clearly synthetic/probe), rotate key, disable, replay (authorization + impact preview), inspect audit and view YAML diff. Provide loading, conflict, permission-denied, stale data, disconnected, and failed status. Keyboard and mobile alternatives are required; no colorful success badge substitutes for confirmed state.

## Suggested integration acceptance gates

- Network tests using real Rust client, resolver and firewall rules: DNS rebinding after initial validation, DNS CNAME into private net, IPv6 link-local, numeric/scheme parser tricks, redirects to metadata and proxies, certificate/SNI mismatch, and retry re-resolution.
- Database integration on **actual** PostgreSQL: duplicate concurrent inserts; unique scope; crash after inbox before outbox; crash after transaction before HTTP response; worker lease expiration and outbox recovery.
- End-to-end provider sandbox/live tests: signatures and rotation, OAuth scope selection, repository event filters, provider retry duration, redelivery and disabled subscription handling.
- Frontend Vue/TypeScript: Visual/Form/YAML semantic round trip, secret redaction, confirmation-only success, WCAG 2.2 AA keyboard/focus/screen-reader and responsive layouts. No synthetic test in this package substitutes for these.

**Source anchors:** [O10-O16], [O19-O24] and academic [P03-P08] in the [research register](sources-and-decisions.md).
