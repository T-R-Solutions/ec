# Lifecycle, Recovery and Truthful Plugin Console

> Use the destination's existing requirements and evidence format; named workflow documents are examples, not prerequisites. Optional SDD mapping is configured outside this skill.

## Separate four evidence levels

1. **Declarative desired state** (canonical versioned YAML only): pinned target release/digest, scope, enabled intent, requested permissions or approved policy references as allowed by actual schema. Edited through Visual, Form or YAML with semantic parity. `ec-configuration-publication` owns versioned publication.
2. **Acquisition/verification state** (operational): immutable artifact digest, signed/trusted provenance receipt, verifier/policy revisions, dependencies, quarantine reasons, staging. Not desired YAML.
3. **Runtime state** (operational): starting/stopping/healthy/unhealthy, worker identity/generation, heartbeats, broker epochs, resource budget, crash count and audit. Not desired YAML.
4. **User action state** (operational): requested/accepted/in-progress/confirmed/rejected/unknown; an accepted HTTP request is not proof of activation, removal or successful stop.

## Illustrative transition protocol (not an Ec API)

| Intent | Preparation | Commit / confirmation | Failure handling |
|---|---|---|---|
| Install | Publish desired YAML pin + grants policy, verify metadata and artifact; stage immutable bytes | Start isolated runtime with new generation, confirm readiness and fresh authorization | Incompatible/unverified → blocked; runtime failure → failed; do not claim active |
| Disable | Publish `enabled=false` or equivalent approved field, reject all new hostcalls immediately | Revoke credentials, request drain/stop, confirm process/instance termination and resource cleanup | Stop timeout / partition → `stopping` or `unknown`; do not falsely report stopped |
| Update | Publish new exact artifact pin with diff/impact; verify new digest and dependencies; choose stop-first/blue-green explicitly | Revoke old generation, confirm old stopped when required, start new and confirm; immutable history retained | Retry is idempotent with same operation ID; partial activation must not expose stale code |
| Rollback | New authorized publication referencing retained previous digest, **reverified under current policy** | Repeat installation/activation gates with new generation and grants | Previous release revoked/incompatible → block; no blind revert |
| Remove | Publish removal/disable with dependent impact and data-retention decision | Deny access, stop and confirm, securely cleanup operational data per approved retention | Unknown cleanup → quarantine and retry; audit must survive removal |
| Security quarantine | Revoke digest/trust/grant from independent authority | Deny new hostcalls before waiting for stop, fence generation and investigate | Existing out-of-band effects may already have occurred; document residual risk |

A **stop-first** update is a conservative illustrative default for a synthetic model; whether the Ec accepts downtime, concurrent generations or blue-green staging is **an project workflow decision**. A globally atomic switch across Git, PostgreSQL, runtime, credentials and remote services must not be claimed.

## Controller invariants

- All operations are scoped by expected desired revision and a unique operation ID; retries cannot unexpectedly apply to a new digest or scope.
- Installed / verified / compatible / authorized / running / healthy are independent predicates. Readiness must reference the exact started generation and desired digest.
- Recheck trust policy and authorization at transition time; invalidate cached receipts after revocation. Never trust a publisher-provided assertion of its own identity or approvals.
- Runtime generation is monotonic per slot; old readiness events, agent status and hostcalls are rejected after a transition. Revocation is enforced before asynchronous cleanup completes.
- Dependency effects are explicit: disabling/removing an active provider requires dependency impact analysis and either safe stop/blocking or policy-authorized migration.
- On restart, state is derived from the authoritative revision, durable operation records and **fresh** runtime observations, not last-known green frontend cache. Unknown/partition is not success.
- Audit each attempted control action, actor/authorization outcome, desired revision, artifact digest, signed evidence reference, generation, timestamps and failure category without secrets.

## UX and three-mode equivalence

Provide a plugin catalog/detail and install/update/disable/remove flow with scope, version/digest, publisher, dependencies, *requested* vs *granted* access, verified provenance status, runtime status, last observation and remediation. A dense table/form is preferable to decorative graphs. Offer a visual dependency/permission map where informative, a complete form and YAML view of the **same desired model**. Operational health/revocation events are read-only status or independent commands, never false YAML configuration fields. Use explicit text/icons in addition to color. Confirm destructive operations with affected dependents; display state of backend confirmation. Prefer accessible keyboard-first controls, responsive layout and clear denied/loading/unknown states. This is a design **method**, not a delivered Ec UI or WCAG audit.

## Open project workflow decisions / gates

Choose artifact packaging/registry; signer identity and root rotation; TUF/Sigstore/SLSA integration vs equivalent trust strategy; trust freshness policy; approved plugin runtime and OS deployment platforms; native-execution policy; archive formats/limits; requested/granted capability vocabulary and scopes; broker API; secrets issuance/revocation; sandbox CPU/memory/network/FS/FD/PID limits; update drain/blue-green policy; replay and fencing scope; failure and quarantine retention; dependency and rollback rules; fleet-wide consistency and rollout strategy. Each gate requires acceptance tests in the **actual** Rust/TypeScript/backend system.
