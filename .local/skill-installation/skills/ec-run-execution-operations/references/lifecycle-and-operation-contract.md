# Lifecycle, request, and observation contract — implementation guide

> Use the destination's existing requirements and evidence format; named workflow documents are examples, not prerequisites. Optional SDD mapping is configured outside this skill.

This is a **method and test oracle**, not the Ec production schema. Use exact project-approved names and transitions. The sample vocabulary in this file is illustrative only.

## 1. Three mutually independent state dimensions

| Dimension | Authoritative owner | Examples (illustrative) | What does **not** prove it |
|---|---|---|---|
| Run/attempt lifecycle | Durable backend run record, informed by fenced scheduler/agent evidence | queued, running, succeeded, failed, canceled, timed_out, skipped | Click, HTTP acceptance, browser event, a deleted runner process |
| Command-operation lifecycle | Durable server command/receipt record | submitted, accepted, rejected, in_progress, resolved, unknown | Run's old status, UI spinner, response timeout |
| Observation freshness | Versioned read/event channel and its verification | fresh, refreshing, stale, unknown | Browser connected flag without continuous event sequence |

**Consequence:** a `running` attempt may have an `accepted` cancel request while remaining `running`; after a network partition it may be `unknown` to the browser even if the server has already committed `succeeded`.

## 2. Identification and provenance

When supported by approved resource contracts, record stable identifiers for `project`, `pipeline`, `run`, `job`, `step`, `attempt` and each command operation. Maintain separate `config_revision` and `source_revision` pinning at submission, an authorization decision reference, origin/trigger, submitter, attempt lineage, server timestamps, terminal reason and version/event sequence. Never make a run's fixed revision follow a later configuration publication. Avoid using screen position, display name, current branch tip or user-chosen labels as immutable identity.

For retry, project workflow must choose *new run vs new attempt within the run* and what is reused (source/config revision, parameters, artifact lineage, access context). Either can be sound; this skill requires a distinct stable identity plus audit linkage. New authorization must be checked for the retry actor even if an earlier run was authorized.

## 3. Operational transition matrix, illustrative only

| Current attempt | Command or authoritative event | Candidate result | Gate and risk |
|---|---|---|---|
| queued | scheduler authoritatively begins work | running | Scheduler/lease proof; no UI-generated transition |
| queued | cancellation request accepted | still queued + cancellation pending | Await scheduler acknowledgement; no instant `canceled` |
| running | cancellation request accepted | still running + cancellation pending | Runtime cleanup and race with terminal completion |
| running | fenced runtime finishes successfully | succeeded | Terminal evidence and fixed attempt identity |
| running | fenced runtime fails | failed | Retain failure category and server timing |
| running | fenced runtime confirms cancellation | canceled | Confirmation, not a mere signal |
| running | runtime deadline exceeded with confirmed timeout | timed_out | Avoid conflating timeout and user cancellation |
| succeeded/failed/canceled/timed_out | retry request accepted | **new** queued attempt (per approved policy) | Prior attempt untouched and linked |
| terminal attempt | stale cancellation request | reject / `already completed` | Never turn committed success into canceled |
| any | gap or contradictory state event | status unknown / resync | Never accept an unverified event as terminal fact |

Unknown, blocked, waiting_for_resource, preparing and similar conditions may be valid in real products but need **Ec project workflow evidence** before becoming Ec statuses. Tekton's `CancelledRunFinally` and GitLab's `canceling` are **external examples**, not default Ec behaviors.

## 4. Command boundary and idempotency

The example request below is **illustrative metadata**, not an approved Ec endpoint or file configuration format:

```text
command: cancel | retry
actor: authenticated principal (server-derived)
target: project_id + run_id + attempt_id
precondition: expected_attempt_version
operation_identity: opaque request idempotency key (if approved)
reason: optional policy-bound reason
```

Response contract must distinguish **rejection**, **durable acceptance/receipt**, **already-applied replay**, and **authoritatively confirmed terminal effect**. The idempotency key must be bound to a request fingerprint; the same key with a different payload cannot silently create another command. A replay should return the prior receipt rather than bypassing authorization: check current actor/target rights before exposing command details.

HTTP 202 means *accepted for processing*, **not** successful completion. HTTP 409 may report state conflict, 412 may report conditional request failure, and RFC 9457 provides an error-content model; choose actual mappings in the approved HTTP contract. HTTP PUT/DELETE method idempotency does not automatically make a POST-based retry or cancellation operation idempotent.

A lost response after acceptance requires a status lookup or a deduplicated replay; auto-retrying an unsafe POST with a new key can create duplicate attempts. Durable deduplication records must have an project-defined retention window at least as long as the intended replay horizon, subject to constraints.

## 5. Race/failure matrix

| Scenario | Safe observable invariant | Engineering test |
|---|---|---|
| Cancel accepted; attempt completed before signal delivered | Terminal success/failure remains authoritative; cancel receipt resolves `too late` | Interleave completion and cancellation in both orders |
| Cancel signal sent, agent disconnects | No fabricated terminal cancellation | Reconcile after reconnection and expiration of timeouts |
| Two operators retry simultaneously | Unique attempt per distinct authorized operation; duplicate requests deduped | CAS/version conflict + idempotency replay |
| Permission revoked after UI enabled button | Backend rejects unauthorized mutation | Recheck authorization on every action, including replay |
| Response lost after accepted retry | Lookup/replay finds original attempt | Simulate dropped response and retry same key |
| Worker reports old attempt result after a new attempt exists | Never mutate new attempt with old result | Fenced result / attempt-id mismatch detection (delegate fencing) |
| Event repeats or arrives out of order | Ignore duplicate/older versions without regression | Snapshot plus duplicate and reorder streams |
| Sequence gap or transport disconnect | Render freshness degraded; resync from authority | Drop an event; recover with authoritative snapshot |
| Run queue order changes after capacity event | Do not invent stable position/ETA | UI displays only evidence-labeled server values |
| Control plane crashes after operation receipt | Durable receipts and commands recover | Real Postgres crash/recovery integration pending |
| External step already caused side effect before retry | Never claim exactly once or universal rollback | project workflow risk disclosure and domain-specific idempotency contract |

## 6. Responsibility map and verification layers

- **Scheduler/DB:** transactionally determines queue state, eligible agent, leases and reservation. `SELECT ... SKIP LOCKED` is suitable for competing queue consumers but creates an inconsistent view; never use it to compute authoritative user-visible position.
- **Agent and runtime:** process termination, timeouts, teardown, cleanup, safe retries of individual steps and lease/fencing validation. An async Rust task `abort` signal does not imply that OS processes or external side effects have ended.
- **This skill:** authenticated run commands, receipt identity, expected version handling, immutable attempt lineage, safe status projection, conflict explanations and observable operator actions.
- **Frontend:** display status, freshness, timeline and accessible command feedback. Local optimistic UI may show `requesting`, but not `canceled`/`succeeded` until server confirms.
- **Other skills:** `web-realtime-streaming` for SSE/WebSocket reconnection; `cicd-logs-and-test-results` for ordered log chunks; `observability-and-diagnostics` for tracing/metrics; `ec-effective-permissions` for the rules used by server authorization.

Validate via unit/model tests first, then PostgreSQL concurrency and recovery fixtures, real Rust-agent failure injection, API authorization/contract suites and Vue browser accessibility tests. Record measured queue/command latency with workload and quantiles, never arbitrary targets. OpenTelemetry CI/CD conventions are **Release Candidate**, so pin versions and do not assume a stable canonical Ec vocabulary.

## 7. Explicit project workflow decisions required before code integration

Request decisions for: precise run/job/attempt schema, terminal states and transitions, `retry` eligibility and lineage, partial reruns, cancellation of queued vs active work, cancellation cleanup/timeout semantics, policy for long-running external side effects, idempotency-key lifetime and request fingerprint, authorization scopes, command receipt durability, event ordering and replay source, unknown-state UX, queue estimate semantics, retention/audit, terminal edit immutability, privacy redaction, accessibility feedback and performance test datasets.
