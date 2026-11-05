# Publication State Model, Atomicity Boundaries, and Failure Recovery

> Use the destination's existing requirements and evidence format; named workflow documents are examples, not prerequisites. Optional SDD mapping is configured outside this skill. Commands, fixture paths and historical execution claims from the source package are illustrative or imported evidence unless the corresponding files are present and independently checked; they are not current test results or bundled executable tools.

This is a **design and testing aid**, not an implemented Ec API/schema, an authorization policy, or a completed project specification. Substitute actual names, capabilities and acceptance identifiers only after examining the real repository.

## Two distinct state dimensions

An implementation can have separate proposal/publication and observation/reconciliation axes; a single flat enum can obscure concurrent resource states.

| Axis | Example state | Grounded predicate / meaning |
|---|---|---|
| Candidate | Draft | Editable candidate retained, not necessarily valid or effective. |
| Candidate | Validated | Contract checks completed for the **exact** candidate content and referenced versions. |
| Candidate | Pending approval | Review requested for a digest, affected scope and base. |
| Candidate | Approved | Approval belongs to that exact digest/base/scope and remains within policy at publish time. |
| Publication | Publishing | Authority ref mutation attempted or awaiting verified outcome; not yet safe to report published. |
| Publication | Published | Immutable candidate revision accepted by the authoritative store and its reference verified. |
| Application | Reconciling | Observed resources are being checked/applied toward a particular published revision. |
| Application | Synced | Verified observed result matches the expected scope/revision; no pending unapplied resources. |
| Application | Drifted | Verified difference between expected published revision and observed state. |
| Application | Failed | An operation failed with a recorded reason/next-action; does not imply publication undone. |
| Indeterminate | Unknown outcome | Remote write may have succeeded although client timed out. First query actual authority; do not blindly retry. |

State labels are **proposed representations**, not a universal mandatory ordering. Partial failures, independent scopes, delayed observations and policy differences can require more precise state combinations.

## Publication atomicity and commit point

**Canonical authority choice matters.** Ec's *desired YAML* is the canonical content; normative guidance prefers a versioned Git authority. If Git is selected, define the publication commit point as the verified acceptance of a specific ref+commit at the authoritative Git remote. PostgreSQL records may contain draft metadata, intent, delivery state and read projections, but cannot supersede YAML as desired-configuration authority.

A safe, testable design sketch:

1. Read base revision `B`, derive exact candidate YAML `C` and its content digest `H(C)`.
2. Run current schema/reference/secret checks; collect affected scopes, semantic diff and policy references.
3. Obtain approvals bound to `(B, H(C), scope, policy-version)` and authorize at publication time.
4. Build a content-addressed commit `R(C)` pointing to `B`; use an authority-supported compare-and-swap for `ref: B -> R(C)`.
5. On verified success: report `published_revision = R(C)`. On CAS rejection: return an actionable `B/current/C` conflict. On timeout: look up authoritative remote ref and commit before classifying.
6. Independently repair/update PostgreSQL's derived projections and enqueue/observe reconciliation. Never display `synced` until the runtime actually confirms the targeted revision.

The sketch does **not** say how Ec must structure branches, PRs, DB tables, Git APIs or status endpoints. Those are decisions to make in project workflow with real implementation evidence.

## Cross-system failure table

| Crash/failure window | Safe externally visible meaning | Expected recovery route |
|---|---|---|
| Before validation | Draft only | Preserve draft, no effective change. |
| Validation/authorization failure | Rejected candidate; existing published revision untouched | Correct candidate, request authorization anew. |
| Approval granted then YAML changed | Approval stale; no publication | Bind new digest and revalidate/reapprove. |
| Remote CAS rejected | Conflict, not published | Fetch current ref; show three-way diff, rebase through explicit approval. |
| CAS request times out | Unknown, NOT proven failure or success | Query remote ref/commit; retry only if idempotency analysis proves safe. |
| Git ref accepted; DB projection update crashes | **Published** at authority; **not yet synced**. A stale DB status field must not be exposed as a verified `synced` result. | Rebuild/replay derived DB from authoritative revision; use explicit `unknown` when the authority cannot be checked. |
| DB intent committed; Git not updated | Still not published | Retry conditional Git operation or mark blocked; intent cannot override Git. |
| Reconcile partially applies then crashes | Published; application incomplete or unknown | Level-triggered check of current state and revision; avoid assuming intermediate steps completed. |
| Old reconciler finishes after newer publication | Must not overwrite newer intended config | Verify desired revision immediately before effect; use fencing/version check where appropriate. |
| Target unavailable indefinitely | Cannot guarantee convergence | Bounded attempts, retry policy, observable blocked/failed state and incident action. |
| Drift observed with autoheal disabled | Drifted, not silently repaired | Show diff and require policy-approved next step. |
| Rollback request | New proposal to restore previous content | Full validation/authorization/revision, preserve audit; no promise to undo executions. |

## Safety invariants to test

- `Published(R)` implies the authoritative ref **accepted and verified** `R`, not merely a DB status flag.
- `Synced(R, scope)` implies an observed match for `R` within that scope, not merely an event sent.
- `Publish(C)` requires `Validated(C)` and an approval valid for `H(C), B, scope` under policy at publication time.
- A failed revision precondition must not replace an unrelated concurrent publication.
- A retry using the same request identity and payload must not create an additional publication; the same identity with a different payload must fail.
- A superseded worker must not silently apply stale desired state over a newer publication.
- A run's `(source_revision, effective_configuration_revision)` remains immutable after admission.
- A corrupt or incomplete projection cannot be used as a second desired-state authority.

## Conditional liveness (not a false promise)

A reasonable hypothesis is **eventually stable reconciliation**: if desired configuration stops changing, required services become available, effects are retry-safe or compensated, workers are scheduled fairly, and authorized actions remain possible, then the observed state should eventually converge and remain matching. These assumptions are essential; this is not proof that Ec implements the property. The OSDI 2024 Anvil paper provides a precise formalization for controller reconciliation, but an Ec proof would require a model of its actual transitions and failure semantics.

## Useful implementation distinctions

- **Three-way diff** compares the author's base, latest published revision and candidate; a simple two-way text diff can conceal overwritten remote edits. Add resource-semantic impact assessment, but do not assume byte-level order conveys YAML meaning.
- **Idempotency key** is an operation request identity, not a substitute for Git CAS or approval; bind it to a canonical request fingerprint and retain outcomes durably under the relevant retention contract.
- **Atomic DB/outbox** is suitable for a DB-owned intent/event transaction. It cannot atomically include a remote Git push without a separate distributed transaction protocol; recovery/projection rebuild must cover the gap.
- **PostgreSQL SERIALIZABLE** gives a transactional isolation guarantee with abort/retry; it does not make a Git remote write part of that transaction. Check the actual supported database version and isolation needs.
- **Config rollback** changes desired YAML (new revision). **Run rollback** and **deployment rollback** are separate operational features governed elsewhere.
- **Explicit states in UI** should say what is verified: draft saved, published revision accepted, reconciliation pending, in sync, drifted, failed or unknown. Never infer server acknowledgment from optimistic client updates.
