# Authorization, Revision and Reconciliation Contract Oracles

> Use the destination's existing requirements and evidence format; named workflow documents are examples, not prerequisites. Optional SDD mapping is configured outside this skill. Commands, fixture paths and historical execution claims from the source package are illustrative or imported evidence unless the corresponding files are present and independently checked; they are not current test results or bundled executable tools.

## Effective permission test dimensions

Test actual backend decisions across actor, scope, resource, action, policy/role revision and time. Use independently reviewed policy matrices from the feature's project workflow. Display-only client guards are never an authorization oracle. If a test principal has no valid authentication in the isolated environment, mark the test blocked; do not bypass the middleware or call a privileged internal function as a substitute.

| Case | Expected observable invariant |
|---|---|
| Explicit allow | Only actions approved under the exact real policy and scope succeed |
| Explicit deny / absent grant | No data disclosure and no write side effect |
| Cross-project enumeration | Same route/HTTP ability but other object's ID never returns unauthorized data |
| Revocation race | Permission revoked between preview and effect blocks protected action |
| Approval mismatch | Approval is valid for exact actor/action/target/policy revision and not reusable as blanket privilege |
| Self-escalation | Policy update cannot create unauthorized broader effective permissions |
| Inheritance override | Effective permission matches project-defined scoped inheritance/deny precedence |
| Role vs permission | UI and backend distinguish assigned role and calculated effective capability |
| Plugin/admin boundary | A project-level permission cannot imply global install/admin rights |
| Read-only access | GET/list/search cannot disclose forbidden existence/metadata through pagination/counts |
| Denied mutation | Persistence, publication, audit outcomes and external effects remain unchanged except separately authorized denial audit |

**Caution:** A rejected request can legitimately produce a denial/audit entry. “No side effect” here means no **forbidden protected operation**, not that even a security log must remain byte-for-byte unchanged. Package synthetic `snapshot()` measures a deliberately limited desired-state projection, not all operational side effects.

## Revision hierarchy

- **Draft revision:** user edits, may be invalid, not yet authoritative or effective.
- **Validated proposal:** syntactically/semantically evaluated and authorized for review; not automatically published.
- **Published immutable revision:** versioned canonical YAML acknowledged by the appropriate authority, ideally Git with immutable commit/hash reference.
- **Effective reconciled revision:** actual applied resource with confirmed observation, potentially lagging published target.
- **Run pin:** immutable configuration and code revisions fixed at run submission/dispatch per real contract; later editing does not alter old run semantics.

Test these as separate IDs and states. A stored database projection is not a second source of desired configuration. A `201`, `202`, a committed local receipt or `git push` initiated does not automatically establish that an external actor applied a change; check the operation and evidence separately. HTTP `If-Match` and `412` are possible version-conflict mechanisms **if actually adopted**; do not force an API pattern on the Ec spec.

## Revision and concurrency oracles

- On concurrent publish requests with the same expected revision, the project-defined optimistic or serialized policy must prevent lost updates; record actual PostgreSQL transaction boundary and Git publication conflict handling.
- On an authorization or approval denial, the effective YAML revision, backend effects and external side effects must remain unchanged, with a scoped rejection/audit event where required.
- On an old revision read, verify full immutable resource content and referenced version identity, not just revision string.
- For a run started before publication, verify all observed steps/attempts continue to cite the pinned pair `(config_revision, source_revision)` unless the actual project workflow explicitly supports a separate new attempt with a distinct immutable contract.
- On a Git failure after local DB admission or vice versa, report **unknown/partial**, never a fictitious cross-system atomic transaction.
- On an unstable/duplicate receipt, distinguish request accepted, committed local transaction, Git-revision visible and applied/confirmed states.

## Reconciliation evidence dimensions

Use direct observation from Ec reconciliation owners. The reference oracle in `validation/oracles.py` illustrates a conservative decision gate, **not the Ec state machine**:

`desired_revision`, `observed_revision`, `observation_sequence`, `observed_at`, `received_at`, `receipt_validation`, `result`, `now` and **project-defined freshness/skew limits**.

| Observation | Conservative outcome |
|---|---|
| Verified current applied revision matches desired | `synced` |
| Verified current applied revision differs | `drifted` |
| Valid current reconcile operation still pending | `reconciling` |
| Explicit current failed reconcile operation | `failed` |
| Missing/invalid/stale/out-of-order/future/conflicting observation | `unknown` (or appropriately qualified by actual project workflow) |
| New publication supersedes old receipt | Old receipt cannot establish new target synced |
| Partial agent/worker disconnect | Cannot infer applied success from stale heartbeat |

The synthetic model uses integer timestamps and an artificial freshness window; **no fixed Ec freshness budget or allowed clock skew was assumed**. Real systems should tolerate explicitly bounded skew or use monotonic versions/receipts to avoid making wall-clock causality claims. Apply the project UX norms to text/shape/keyboard announcement so `synced` is not communicated by color alone.

## Negative fault sequences

1. Edit -> validate -> publish denied due to stale role -> check DB, YAML Git history, audit and external effect separately.
2. Publish A -> queue run pin A -> publish B -> process run -> check run pin A.
3. Two editors based on A -> publish B and C concurrently -> verify one accepted/appropriate resolution; no silent last-writer-wins.
4. Reconciler reports `Synced(A)` after target B -> show stale/unknown for B, not synced.
5. Network loss after command acceptance -> reconcile on authoritative receipt, not browser success toast.
6. Nonrepresentable extension field edited from basic form -> verify no loss; reject incompatible mode action.

These are proposed contracts, not evidence that the Ec code has implemented them.
