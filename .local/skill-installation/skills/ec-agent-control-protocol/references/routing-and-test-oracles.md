# Activation routing and acceptance oracles

> Use the destination's existing requirements and evidence format; named workflow documents are examples, not prerequisites. Optional SDD mapping is configured outside this skill. Commands, fixture paths and historical execution claims from the source package are illustrative or imported evidence unless the corresponding files are present and independently checked; they are not current test results or bundled executable tools.

These scenarios are **prepared test plans** for evaluating whether a coding assistant selects this skill and implements correct agent-wire behavior. They were not run against an agent with the Ec repository. The synthetic Python test suite independently checks a subset of state-machine properties; it is not an implementation acceptance suite.

## Positive activation — select this skill as the primary method

| ID | Task | Expected focus |
|---|---|---|
| S01 | Implement authenticated registration of a new Rust agent with an Ec server | enrollment/identity/session boundary |
| S02 | Design server–agent HTTP protocol version negotiation | intersection, feature gates, compatibility |
| S03 | Agent heartbeat duplicated during network retry | per-session sequence and freshness |
| S04 | Server receives an old heartbeat after an agent reboot | epoch validity and rejection |
| S05 | Diagnose agent liveness appearing fresh while its job lease is expired | separate liveness/work authority |
| S06 | Add work lease renewal request and receipt to Rust agent HTTP client | bound claim validation, expiry |
| S07 | Reject result from prior worker after a job is reassigned | fence generation and commit checks |
| S08 | Lost HTTP response during result submission | idempotent receipt lookup/replay |
| S09 | Rotate agent certificate without restoring stale sessions | credential and epoch lifecycle |
| S10 | Add structured protocol errors for stale fence or incompatible version | HTTP semantics and privacy |
| S11 | Upgrade servers while older agents remain connected | mixed-version test matrix |
| S12 | Investigate retry storms from thousands of agents | heartbeat backpressure, jitter and load |
| S13 | A restarted server loses memory of a job's fencing generation | persistent monotone authority |
| S14 | Agent self-reports more capacity and requests additional work | untrusted claims and contract routing |
| S15 | Audit agent message logs for leaked enrollment tokens | secrets and transport diagnostic boundary |
| S16 | Agent sends a result after `202 Accepted` on a prior delivery request | delivery receipt vs outcome confirmation |

## Negative activation — choose another skill

| ID | Task | Correct primary owner |
|---|---|---|
| S17 | Implement DAG dependency topological sorting | `cicd-pipeline-graph` |
| S18 | Select least-loaded agent with fair quotas | `cicd-scheduler` |
| S19 | Sandbox a shell command running in an agent | `cicd-execution-runtime` |
| S20 | Implement local Rust worker restart supervisor | `cicd-agent-runtime` |
| S21 | Build a Vue inventory page for agent pools | `ec-agent-pool-console` |
| S22 | Implement operator's retry button for a failed build | `ec-run-execution-operations` |
| S23 | Update YAML representation of an agent pool | `ec-declarative-resource-schemas` |
| S24 | Define permanent product rule for agent authentication | Ec norms/approved project contract, not a skill |
| S25 | Implement run artifact storage and retention | `cicd-artifacts-and-retention` |
| S26 | Ingest and index stdout chunks from steps | `cicd-logs-and-test-results` |
| S27 | Implement reusable HTTP OpenAPI error guidelines for all apps | `http-api-contract-engineering` |
| S28 | Explain effective role inheritance for a project manager | `ec-effective-permissions` |

## Borderline / handoff cases

| ID | Task | Selection and handoff |
|---|---|---|
| S29 | Diagnose an agent's timeout while a run is still marked successful in UI | Agent wire first for heartbeat/fence evidence; `ec-run-execution-operations` for operator projection |
| S30 | Define whether agent-pool tags may be trusted for protected workloads | Security/project workflow policy first; wire skill only for signed/validated claims field semantics |
| S31 | Implement cancel of running process over agent protocol | Wire acknowledgement here; process termination by `cicd-execution-runtime`; operator status by run skill |
| S32 | Agent connection works but scheduling rejects eligible job | Scheduling/eligibility primary; wire only if contract rejects assignment |
| S33 | Store lease and agent registry in PostgreSQL | `cicd-scheduler` for transactional acquisition; this skill checks fence/receipt API behavior |
| S34 | Optimize WebSocket reconnection for browser users | `web-realtime-streaming`, not this skill unless it concerns agent-control transport |
| S35 | Add a new agent enrollment method using mTLS | Security/project workflow decision plus this skill for registered principal/session binding |
| S36 | Test exactly-once external deploy effect after agent restart | Reject premise; runtime/deploy skill owns external idempotency, this skill limits stale result commits |

## Real Ec integration cases — planned, not executed

| ID | Contract/fault stimulus | Acceptance oracle and evidence to capture |
|---|---|---|
| I01 | Valid authorized enrollment proof | Registered stable principal and audit, one-time proof consumed |
| I02 | Reuse consumed enrollment token | No second enrollment or identity takeover |
| I03 | Stolen/revoked credential | All protected endpoints reject; no lease extensions |
| I04 | Agent sends another agent's ID | Server binds request to authenticated identity, not payload ID |
| I05 | Register with common supported wire version | Negotiated documented compatible version with required features |
| I06 | Register with disjoint version sets | Explicit incompatible outcome without work mutation |
| I07 | Offer legacy version without fencing feature | Protected work denied, no silent downgrade |
| I08 | New server + old agent | Approved compatibility behavior or explicit supported rejection |
| I09 | Old server + new agent | Approved fallback or safe rejection without lost work |
| I10 | Restart agent after registration | New epoch, old session cannot submit accepted heartbeat/result |
| I11 | Lost registration response, successful commit | Safe identity lookup/reconciliation; no unintended new agent |
| I12 | Heartbeat repeated with same sequence/payload | Same receipt or explicit duplicate; no freshness extension |
| I13 | Heartbeat replay with same sequence/modified payload | Reject conflict, preserve last accepted snapshot |
| I14 | Heartbeat sequence out of order | Older value cannot overwrite newer liveness/capacity |
| I15 | Heartbeat after grace interval | Distinguish unknown/suspected unavailable from confirmed failure |
| I16 | Remote clock is ahead/behind | Server-owned expiry unaffected by submitted timestamp |
| I17 | Heartbeats arrive in burst from many agents | Rate/memory/backpressure budgets measured, no overload promises |
| I18 | Agent advertises privileged capability | Claims do not grant workload authorization |
| I19 | Scheduler issues valid claim to current agent session | Unique attempt, assignment and monotone fence bind to holder |
| I20 | Deliver same assignment twice | Exactly one logical claim and safe replay receipt |
| I21 | Deliver same ID with different payload | Conflict without mutating accepted work |
| I22 | Lease renewed with current holder and fence | Durable expiry advances once for accepted renewal |
| I23 | Duplicate lease-renewal request | Expiry not extended by stale identical replay |
| I24 | Lease-renewal with stale session/fence | Deny and preserve newer owner |
| I25 | Agent heartbeat fresh but lease expired | Result and renewal rejected |
| I26 | Expire old claim, reassign same job to new agent | Fence strictly increases and old claim invalid |
| I27 | Delayed result arrives from old agent | Rejection at durable result sink; new result intact |
| I28 | Revoke an agent with active lease | Old session/fence invalidated, audit available |
| I29 | Control plane crashes between grant and HTTP response | Reconnect can recover durable claim; no parallel valid grants |
| I30 | Control plane restarts after result commit but before response | Durable idempotency receipt returns same immutable result |
| I31 | Two server instances race to issue work claim | Exactly one current holder; monotone fence (real PostgreSQL test) |
| I32 | Agent posts result without delivery acknowledgement | Rejected or reconciled under approved protocol; never fabricated success |
| I33 | Malformed/truncated/unknown-field request | Bounded parse failure, no crash, secret-safe error |
| I34 | HTTP `If-Match` precondition fails | Contract-defined status, no protected mutation |
| I35 | HTTP result write after 202 acceptance | Distinguish accepted delivery from durable completion |
| I36 | TLS peer has incorrect service identity | Handshake/verification rejected; no fallback to insecure HTTP |
| I37 | TLS 0-RTT early data attempts lease mutation | Rejected unless separately reviewed replay-safe protocol |
| I38 | Certificate rotation while requests in flight | Old/new identity handling matches approved revocation and session policy |
| I39 | Correlation and audit under malformed/replayed commands | Diagnostic fields preserve causality without exposing secrets |
| I40 | Agent offline then reconnects after queue reshuffle | Authoritative reassignment wins; no implicit work ownership |
| I41 | External deployment effect occurs after lease expiry | Fencing at external sink or documented idempotency/compensation; do not claim exactly-once |
| I42 | Concurrent high-volume enrollment | Auth/DB capacity tested; registry integrity maintained |
| I43 | Mixed-version result payload with new optional field | Compatible parser safely preserves/ignores per contract; no silent mutation |
| I44 | Attempt identifier reused for previously committed work | Duplicate attempt cannot be committed as new work |
| I45 | Restart database and server then reissue claim | Per-resource monotonic fence persists beyond process lifetime |
| I46 | Run assistant with/without this skill on same project workflow task | Compare safety, correctness, measured token count, edits and reproducibility |

**Evidence classes:** (`planned` in this file) vs `executed` in `validation/` and `VALIDATION.md`. No score is assigned to AI skill activation, Rust interoperability, TLS or PostgreSQL from these planned cases.
