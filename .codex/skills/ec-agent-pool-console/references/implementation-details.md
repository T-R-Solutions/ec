# Detailed decisions and failure cases

Read only for a decision needing more detail than the entry point. Apply the relevant steps to the
actual task; the sections are not a mandatory artifact or test checklist. Use existing project
contracts in their actual format. File names such as spec.md, plan.md or tasks.md are examples, not
required dependencies. Other skills are optional expertise, never enforcement mechanisms.

Product rules, stack choices and authority locations must be verified in the Ec consumer. Examples
do not establish deployed capabilities or override its current contract.

## Implementation method

1. **Inspect real contracts.** Locate the approved Ec resource schemas, project/pool relationships,
   runtime agent snapshots, permissions and existing APIs. Record missing contracts in the project
   workflow rather than inventing field names or treating vendor semantics as Ec features.
2. **Map two authorities.** Separate *desired* pool configuration (published, versioned YAML),
   including membership policy, project association, quotas and placement constraints *when
   specified*, from operational records (sessions, connectivity, reported capabilities, active
   leases, utilization and last observation). Show the configured revision and the observed
   timestamp/version independently.
3. **Model status as orthogonal evidence.** Treat connected, enrolled/authorized, administratively
   enabled, draining/maintenance, quarantined, compatible and busy as independent dimensions where
   supported. Distinguish `unknown` or stale data from measured zero or confirmed offline status. Do
   not reduce every condition to a green/red agent flag.
4. **Build an explainable eligibility matrix.** Evaluate *hard scope/policy constraints*, *verified
   capability and trust requirements*, *protocol compatibility*, *maintenance/connectivity
   conditions*, and *available capacity* separately. Return stable, testable reason codes with
   evidence provenance and observation version. An **eligible candidate is not a dispatch
   reservation or a promise of scheduler selection**. For missing/stale evidence, return `unknown`
   and withhold any ready-to-run claim.
5. **Separate diagnostics from scheduling.** Display blocking reasons and suggested remediations
   grounded in actual data. Do not compute placement order, fairness, queue position or estimated
   wait time unless the owning scheduler exposes verified outputs. Do not infer trust or hardware
   capability solely from an untrusted agent-supplied label.
6. **Prepare safe administrative changes.** For move/assign/unassign, preview affected projects,
   authorized scopes, currently running work, capacity changes and potentially stranded jobs. Check
   both source and target scope authorization and published revision; reject stale updates. Route
   persistent desired changes through `ec-configuration-publication`. Keep drafts, validation,
   approval, publication and synchronization visibly different.
7. **Treat maintenance as an operation, not a YAML write.** For drain, disable, quarantine or
   remove, derive the exact impact from the approved operation contract. A drain normally stops
   *new* admission; do not silently cancel active work. Represent `requested`, `accepted`, `in
   progress`, `confirmed` and `unknown` according to actual server evidence, never merely UI
   optimism.
8. **Deliver operable screens.** Use responsive inventory tables, scoped filters, pool/project
   association, capacity breakdown, compatibility inspector, audit trail and confirmation dialogs.
   Provide meaningful accessible table or grid semantics, keyboard actions, status announcements,
   dark/light parity and no hidden critical information. Never expose secrets or unauthorized
   eligibility details.
9. **Verify under change and failure.** Test stale observations, out-of-order events, incorrect
   capability provenance, no compatible agent, busy-but-compatible agents, drain while running,
   concurrent pool reassignments, privilege changes, revision conflicts, disconnection and UI
   refresh. Run real Rust/API/PostgreSQL/Vue and accessibility evaluations **only when an Ec
   repository and execution environment are available**; otherwise label evidence as synthetic.


## Further references by decision

Read only the reference relevant to the unresolved decision. Research registers describe imported authorship, not current execution.

- [Pool Administration, Operator Safety and UX — Method Guide](administration-and-ui.md): administration and ui.
- [Explainable Eligibility and Evidence — Engineering Decision Guide](eligibility-and-evidence.md): eligibility and evidence.
- [Activation and Real Integration Oracles](routing-and-test-oracles.md): routing and test oracles.
- [Source Register, Scientific Evidence and Decision Boundaries](sources-and-decisions.md): source provenance or a disputed recommendation.
