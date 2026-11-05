# Gate and Promotion Lifecycle — Technical Decision Guide

> Use the destination's existing requirements and evidence format; named workflow documents are examples, not prerequisites. Optional SDD mapping is configured outside this skill.

## Four separately evidenced authorities

| Layer | Examples | Source / expiry | What it cannot prove |
|---|---|---|---|
| Published desired YAML | Environment definition, protection settings, reviewer policy, gates, allowed sources, secret references | Versioned authoritative YAML; reconciliation state | A deployment was attempted or succeeded |
| Trusted identity and authorization | Actor identity, approver/deployer permission, relevant role/scope revision | Authorization service at the time of the action | The artifact was verified or the deployment finished |
| Operational decision/dispatch | Request, frozen subject, decision evidence, vote event, dispatch receipt, attempts | Transactional operational backend; server timestamps / IDs | Infrastructure is healthy |
| Target observations | Target revision, rollout state, health/check evidence, drift | Executor/target observer with revision and freshness | The reviewed artifact was necessarily the one deployed, absent a matching digest |

A deployment target may be an external cloud, cluster or other environment. Do not invent topology or assume Kubernetes is the only target. Scope and environment identifiers must use approved schemas.

## Illustrative (not Ec API) request lifecycle

1. **Requested** — actor authenticated and permitted to submit; immutable subject frozen. Some policy checks may still be missing.
2. **Awaiting checks / review** — gate reasons and required reviewers visible. `unknown` is neither passing nor a definitive rejection.
3. **Approved / eligible for handoff** — quorum satisfied under a particular policy revision and frozen subject; no deployment has started.
4. **Queued / blocked / accepted by executor** — a separate dispatch attempt has been attempted; conflicts, freezes and health may prevent start.
5. **Running / verifying** — executor reported attempt and matching revision; health is not inferred from `accepted`.
6. **Healthy / failed / unknown outcome** — correlated target observation; no rollback implied unless separately implemented and evidenced.

A reject, expiry, policy version mismatch, verified digest mismatch or superseded request must have an explicit outcome. Prevent attempts in non-releasable states. Record immutable audit events with origin and correlation identifiers. New subject = new approval request (unless an approved project contract rule explicitly says which nonsemantic edits may be retained).

## Bind approvals to immutable evidence

Canonical encoding of the *request fingerprint* (for a synthetic test only): environment ID, project/scope ID, artifact digest, source revision, config revision, gate/policy revision, target, requester and nonce. Hash the canonical representation; a hash is an identifier, **not** a signature or proof of trusted provenance. Never let client-provided fingerprint equality substitute for verifying the trusted server record. Additional policy-dependent fields can be included in the production contract; project workflow must specify the exact encoding.

Approval records are operational and scoped by request ID, authenticated reviewer, decision, timestamp and fingerprint. Distinctness is by verified **principal**, not by group memberships. Quorum semantics vary (one-of-many, k-of-n, grouped mandatory approvals, veto); do not hardcode vendor defaults. Recheck approver/deployer rights and current policy at dispatch, or document the project-approved bounded snapshot-consistency alternative. Expiry uses a trusted server clock and clear inclusive/exclusive cutoffs.

## Dispatch safety and scheduling

- **Time of check vs time of use:** recheck before external side effects, inside the strongest practical DB transaction/CAS boundary. A DB transaction cannot atomically commit a remote deployment and Git publication. Persist an outbox/intent and recover from unknown acknowledgements; deduplicate by trusted command ID and subject.
- **Conflicting requests:** lock the environment, use a monotonic sequence/fence, or apply a documented supersession protocol. `sequential`, `run latest` and explicit cancellation have different semantics (Azure Pipelines / GitLab). An older completion event must not rewrite the newer run's observed state.
- **Window overlap:** classify hard freeze, scheduled allow/deny, manual override and running-operation continuation separately; Argo CD has documented `allow`/`deny` and optional `syncOverrun` behaviors. Ec policy must be specified, not copied.
- **Rollback:** treat reversal as another controlled execution with identified artifact, target, policy and review decisions. Target-specific rollback support must be verified; no “exactly once” guarantees for arbitrary external effects.
- **Completion:** correlate request + attempt + artifact digest + target revision + observation source. A timeout is `unknown` until an authoritative result is read; do not report “failed” merely because one HTTP response disappeared.

## Example gate matrix — diagnostic semantics, not a product policy

| Check | Trusted evidence | If absent or stale | If explicitly failed |
|---|---|---|---|
| Artifact identity | Registry digest + verified provenance/trust chain | `unknown:artifact-unverified` | `blocked:artifact-mismatch` |
| Config revision | Versioned effective YAML | `unknown:config-effective-unconfirmed` | `invalidated:revision-changed` |
| Reviewer | Verified principal and effective permission version | `unknown:authorization-stale` | `blocked:not-authorized` |
| Approval quorum | Server vote ledger for immutable subject | `pending:approval-required` | `rejected:gate-veto` |
| Allowed window | Server clock + approved policy revision | `unknown:window-not-evaluated` | `blocked:outside-window` |
| Target concurrency | Server-side reservation/fence | `unknown:lock-state` | `blocked:conflicting-operation` |
| Deployment result | Executor/target matched revision observation | `unknown:not-confirmed` | `failed:verified-target-failure` |

Reason codes above are illustrative. Their exact names/transport are project workflow-owned. Keep disabled vs unavailable vs unknown as separate conditions.
