# Protocol design and safety decision guide

> Use the destination's existing requirements and evidence format; named workflow documents are examples, not prerequisites. Optional SDD mapping is configured outside this skill.

These are **technical design oracles**, not approved Ec field names, API paths or state enumerations. Approved project workflow is the authority for their concrete shape.

## Four independent lifecycles

| Dimension | Source of truth | Example evidence | Never infer |
|---|---|---|---|
| Agent registration and session | Authenticated control plane + durable registry | stable registered principal, current epoch, revocation | same host name => same trusted agent |
| Agent availability | Server-observed heartbeat freshness | last accepted sequence, server receive time, health evidence | heartbeat => job lease valid; silence => definite crash |
| Assignment and work lease | Transactional work owner / scheduler authority | attempt ID, holder, monotonic fence, expiry, claim status | connected agent => work ownership; TTL alone => external fencing |
| Delivery and result receipts | Durable operation/result authority | command ID, request fingerprint, receipt, committed result version | HTTP response lost => operation not performed |

A single HTTP transport can carry all four; that does not combine their state machines. A design must explicitly handle partial failures between them.

## Suggested contract field questions (NOT a proposed Ec schema)

For every operation, ask for: authentication evidence, protocol version, agent ID, boot/session epoch, monotonic request sequence, command/assignment/attempt ID, current fence, expiry authority, precondition/version, replay identity/fingerprint, receipt reference, status/result, retry hint, trace correlation and audit redaction. **Omit fields irrelevant to a given operation.** Do not force the entire envelope into every message.

| Operation family | Critical preconditions | Positive evidence | Negative or uncertain outcome |
|---|---|---|---|
| Enrollment | approved installer authority, constrained one-time proof | registered immutable principal + rotation protocol | invalid/used/revoked proof; no implicit trust from capabilities |
| Registration/re-registration | verified principal, supported protocol, epoch change | current session, negotiated version, typed receipt | unsupported version, revoked agent, stale session |
| Heartbeat | authenticated current session, new sequence | server-recorded freshness and relevant observed capacity | duplicate/reordered/stale requests; no work renewal |
| Work delivery/ack | active scheduler grant bound to attempted job | receipt acknowledging **delivery**, not outcome | lost response, wrong session, stale fence, already assigned |
| Work lease renewal | active matching attempt/fence, unexpired work lease | server-confirmed new expiry/revision | expired/reassigned/revoked => cannot renew |
| Result commitment | correct authenticated holder and active fence, accepted attempt | durable commitment + result ID, replay-compatible acknowledgement | stale/fabricated/duplicate conflicting result, stale lease |
| Disconnect/drain | approved auth and drain policy | control-plane-confirmed transition | delayed signal is not guaranteed immediate termination |

## Fencing and time ownership

- A lease is a **temporary authorization to act**, not a proof that old clients stop acting when the clock expires. The server checks current holder/attempt/fence and expiry whenever it commits an effect; protected external resources must independently check a current token or use an agreed idempotency/compensation contract.
- Fences must increase monotonically for the **same resource**, across reconnects, failover and database restarts. A session epoch may also rise, but it is **not** a substitute for the per-resource fencing generation. Avoid generating fences by using a timestamp or agent-local counter.
- Use one authoritative source of lease expiration and concurrency (typically a durable server-side transaction selected by project workflow). Compare lease time using server authority; a submitted agent wall-clock value is diagnostic only. Locally, Rust `Instant` can measure time intervals, but it cannot define cross-machine lease validity.
- Heartbeat refresh does not modify work expiry. Retrying the same heartbeat/renewal sequence must not extend the deadline a second time. A fresh renewal must demonstrate active claim ownership, not merely possess a previously valid credential.
- When the backend cannot determine whether a timed-out write committed, return **unknown/reconcile** and read the authoritative attempt/receipt state before any non-idempotent repeat.

## Versioning and rollout

1. Declare client/server wire versions and required feature capabilities in approved project contract; separate *software release version* from *wire protocol version*.
2. Negotiate explicit intersection. Incompatible versions fail clearly; do not pretend partial parser success means safe compatibility.
3. Test old agent/new server, new agent/old server, reconnect during rolling upgrade, unknown optional fields, new required fields and stale result format.
4. Security-critical meaning cannot be downgraded by an unauthenticated client claiming a lower version. If a negotiated legacy mode lacks required auth/fencing, block work acquisition, not just suppress a warning.
5. Track compatibility support/deprecation and clear rejection reasoning. RFC 9745 `Deprecation` and RFC 8594 `Sunset` offer generic HTTP signals; use only if selected in project workflow.

## HTTP semantics: not an Ec endpoint proposal

- `GET` may retrieve a snapshot; `PUT` and `DELETE` have method-level idempotent intent under RFC 9110. `POST` is not generally safe to retry without an application-level replay contract.
- `202 Accepted` is **not completed work**. An acknowledgement cannot stand in for durable result confirmation.
- `If-Match` with an entity tag is a standardized precondition for a representation; failure may be `412 Precondition Failed` as described by RFC 9110. A stale fence can also have a domain-specific error. **Choose and document mapping; do not imply the RFC defines a unique fence error.**
- RFC 9457 provides a machine-readable error representation; domain-specific `type` values and privacy-sensitive detail must be chosen by project workflow.
- Backpressure: bound message and header size, per-agent inflight requests, retry amplification and server capacity. Honor documented `Retry-After`; use exponential backoff with jitter only under policy.
- TLS 1.3 is currently specified by RFC 9846 (July 2026), which supersedes RFC 8446. Its **0-RTT early data is replayable**; do not transmit state-changing registration, lease or result messages as 0-RTT unless a separately reviewed profile proves replay safety. mTLS identity and certificate-bound OAuth are candidate authentication architectures, not a preselected Ec decision.

## Required negative tests

- Old heartbeat sequence cannot refresh agent freshness or a work lease.
- Heartbeat for an old session cannot revive a superseded session.
- Agent with a fresh heartbeat but an expired work lease cannot commit.
- A delayed result from fence `f` fails after reassignment issues `f+1`.
- Duplicate receipt returns prior answer only for identical authenticated context and fingerprint; altered payload is rejected.
- Credential revocation blocks new requests and prevents resurrecting leases.
- Incompatible protocol version fails *before* any admission or work mutation.
- A valid fence is checked at the resource that accepts the result; external side effects are documented separately.
- Recovered backend state must retain prior fence monotonicity and already-committed results.

**Unresolved project workflow decisions:** enrollment and revocation authority; TLS/mTLS/token design; supported wire versions; heartbeat grace policy; session epoch semantics; lease TTL and renewal margin; per-work fencing generation; retry/dedup retention and result receipts; transaction ordering; external-effect fencing/compensation; error/visibility and observability policy.
