# Detailed decisions and failure cases

Read only for a decision needing more detail than the entry point. Apply the relevant steps to the
actual task; the sections are not a mandatory artifact or test checklist. Use existing project
contracts in their actual format. File names such as spec.md, plan.md or tasks.md are examples, not
required dependencies. Other skills are optional expertise, never enforcement mechanisms.

Product rules, stack choices and authority locations must be verified in the Ec consumer. Examples
do not establish deployed capabilities or override its current contract.

## Implementation method

1. **Locate real authority, scope and test subject.** Read the applicable Ec product documents and
   the current project workflow constitution/spec/plan/tasks/evidence, actual Rust/PostgreSQL and
   Vue/TypeScript code, API/schema versions, identity test realm, configured agents and permitted
   test environments. Inventory implemented, proposed and unavailable contracts. Never manufacture
   an Ec endpoint, field, policy, status, schema version, fixture validity or implementation test
   result. Choose local versus project workflow route according to the existing project workflow
   workflow, not this skill. See [harness architecture](harness-architecture-and-adapters.md).
2. **Build criterion-to-oracle traceability before implementation.** For each in-scope
   `REQ-*`/`AC-*`, identify actor, operation, resource/scope, precondition, expected observation,
   rejected outcome, oracle owner, trust boundary, revision inputs, fixture data, result
   classification and cleanup. Include concrete normal, negative, boundary and concurrency/fault
   cases. Specify what is *independently* observed in the Ec implementation, not merely what a
   stub's expected output says. Record inputs and versions so a change invalidates stale evidence.
   See [routing and real Ec acceptance](routing-and-test-oracles.md).
3. **Put the test harness beside, not inside, production authority.** Expose thin adapters over the
   *real* existing Rust/domain API, authenticated HTTP interfaces, PostgreSQL observations and
   Vue/browser interaction, according to the feature plan; do not create a shadow Ec configuration
   store, shadow RBAC, invented YAML schema, or test-only production bypass. Desired configuration
   is canonical **YAML**; transport DTOs, hashes, test metadata and operational receipts are not
   alternative declarative configuration files. Use version- and scope-pinned disposable fixtures
   with no real credentials; treat log/run/database state separately. Refuse destructive tests
   without explicit isolated authorization.
4. **Prove semantic three-mode equivalence and non-loss.** Start from project workflow-validated
   YAML and an independent expected semantic representation. Exercise **YAML → model → Visual/Form →
   edits → model → YAML**, both directions and interrupted/invalid-draft paths; verify mapping order
   independence, sequence order sensitivity, typed scalars, references, expressions, extension
   fields, defaults, inheritance and no silent field removal. Distinguish semantic equality from
   byte equality; formatting/comments need separate source-preservation/diff checks when supported.
   Invalid, ambiguous, duplicate-key, hostile-tag and size/alias-abuse vectors must fail under
   actual parser/schema policy; avoid claiming a fixture's `apiVersion` is a shipped Ec contract.
   See [configuration vector design](semantic-config-contract-vectors.md).
5. **Assert effective authorization and version/reconciliation boundaries.** Test allowed/denied
   actors and scopes **on the backend**; no implicit grants, privilege escalation, stale approvals,
   cross-project reads or mutations after denial. Verify denied operations leave all scoped effects
   unchanged. Test draft/published/effective distinctions, optimistic conflicts, immutable revision
   history, run pinning, old snapshots and observed drift/freshness; never infer synchronized from a
   saved draft, an HTTP `202`, an old heartbeat or a successful validation alone. Check underlying
   confirmation receipts and independent query paths. See [authorization, revision and
   reconciliation](authorization-version-and-reconciliation-oracles.md).
6. **Exercise executable lifecycle and fault histories.** Build model-based sequences around
   submission, queue/claim/run, cancel requested versus cancelled, retry as an explicit new attempt,
   terminal confirmation, lost agent/fencing, reorder/duplicate/delayed events, reconnect and
   partial external effects. Injections must be safe, deterministic or reproducibly seeded, bounded
   and isolated; match actual project workflow policy. Test transactionality and concurrency using
   actual Ec Rust/PostgreSQL where available; **SQLite and in-memory demos cannot establish
   PostgreSQL or distributed execution semantics**. See [faults, events and
   evidence](execution-evidence-and-fault-injection.md).
7. **Prove oracle sensitivity and adapter integrity.** Include seeded metamorphic properties,
   adversarial controls, deliberately defective observations/mutants, positive/negative/boundary
   routing, failure of invalid bridge paths and detection of intentionally wrong outcomes. Compare
   expected values against independent specification/golden datasets; avoid generating both expected
   and actual from the same production implementation. Test adapters must not quietly turn
   timeouts/skips/transport errors into passes or use a synthetic/reference adapter to label a real
   Ec acceptance criterion approved. Optional advanced generative techniques remain with
   `property-based-testing`.
8. **Return reproducible, bounded evidence and gaps.** For each AC record exact command,
   environment, target revision/hash, fixture seed, observations, tier
   (structure/reference/integration/browser/real agent), outcome using the **five project workflow
   statuses**, changed input fingerprints, access redaction and limitations. Block an `APROVADO`
   claim for stale or merely synthetic evidence. Re-run affected criteria after changes and classify
   unexecuted Ec integration, real authorization, PostgreSQL contention, Vue/accessibility and
   actual agent behavior as **NÃO VERIFICADO** or **INCONCLUSIVO** as appropriate; never claim that
   package checks prove Ec conformance.


## Further references by decision

Read only the reference relevant to the unresolved decision. Research registers describe imported authorship, not current execution.

- [Authorization, Revision and Reconciliation Contract Oracles](authorization-version-and-reconciliation-oracles.md): authorization version and reconciliation oracles.
- [Run Histories, Bounded Faults, and Evidence-Gate Design](execution-evidence-and-fault-injection.md): execution evidence and fault injection.
- [Harness Architecture, Independent Oracles, and Adapter Contracts](harness-architecture-and-adapters.md): harness architecture and adapters.
- [Routing, Evaluation and Real Ec Product Acceptance Oracles](routing-and-test-oracles.md): routing and test oracles.
- [Semantic Configuration Vectors and Three-Mode Oracles](semantic-config-contract-vectors.md): semantic config contract vectors.
- [Primary Sources, Papers, Evidence Depth, and Engineering Decisions](sources-and-decisions.md): source provenance or a disputed recommendation.
