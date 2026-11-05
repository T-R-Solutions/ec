# Detailed decisions and failure cases

Read only for a decision needing more detail than the entry point. Apply the relevant steps to the
actual task; the sections are not a mandatory artifact or test checklist. Use existing project
contracts in their actual format. File names such as spec.md, plan.md or tasks.md are examples, not
required dependencies. Other skills are optional expertise, never enforcement mechanisms.

Product rules, stack choices and authority locations must be verified in the Ec consumer. Examples
do not establish deployed capabilities or override its current contract.

## Decision procedure

1. **Inventory authorities, resources and revision identities.** Find the actual YAML
   repository/branch ownership, schema version, current published ref, candidate draft, base ref,
   scope, author, approval policy, operational projection and the current job-to-config revision
   binding. Never invent filenames, database tables, endpoints or a Git provider. Distinguish
   desired YAML (authoritative), Git revision/ref (durable publication pointer), derived query
   projections, and operational run/lease/log state. If the repository does not establish Git as the
   authority implementation, document an alternative with equivalent durable immutable revisions and
   compare-and-swap behavior rather than declaring Git already deployed.
2. **Specify separate transitions and their acknowledgments.** Model `draft`, `validated`, `pending
   approval`, `approved`, `publishing`, `published` as proposal/publication states; model
   `reconciling`, `synced`, `drifted`, `failed` as an independent **application/observation axis**
   where necessary. A draft save is not validation; validation is not approval; a successful Git ref
   update is not application. Define a measurable, server-confirmed predicate for every exposed
   state. Model the ambiguous/unknown result of a timed-out remote write.
3. **Bind checks to the exact candidate.** Validate syntax, schema, references, resource graph,
   secret policy and authorization using the authoritative validators. Produce a reviewable
   *semantic and textual* change set, affected resources and risk classification. Bind approvals to
   the exact candidate digest, base revision, target scope, required approver identity and relevant
   policy version. Re-evaluate authorization and stale-policy conditions at the final publication
   boundary; do not reuse a favorable preview as an authorization decision or allow self-approval
   when policy forbids it.
4. **Prevent lost updates.** Require a base revision/strong validator and compare-and-swap at the
   actual authority boundary. For an HTTP interface, use `If-Match`/ETags when its actual
   representation contract supports them; apply RFC 9110 conditional semantics (`412` for a failed
   HTTP precondition, and possibly `428` when a precondition is required). Use `409` for
   application-level conflicts with actionable base/current/proposed comparison. For a Git-backed
   authority, consider an old-OID checked ref update, protected remote branch or equivalent provider
   API guarantee. Never silently overwrite a newer revision, rewrite published history, or claim a
   multi-ref Git transaction is a global, atomic database transaction.
5. **Design crash windows before selecting persistence patterns.** Identify the publication commit
   point and which system is allowed to report `published`. If Git is authoritative, treat *verified
   accepted Git ref/revision* as the durable publication fact; a PostgreSQL row or outbox is a
   rebuildable projection/intent, not a second desired-state authority. Git push and PostgreSQL
   commit cannot be assumed atomic together. Record intent/idempotency identity and make recovery
   distinguish `not published`, `published but projection pending`, and `remote result unknown`.
   Outbox is useful for delivery after a **database** commit, but does not make a remote Git write
   atomic with that commit.
6. **Reconcile by identity, not by hope.** Consume the published immutable revision, compute
   desired-vs-observed differences, apply bounded, repeat-safe operations where possible, and
   persist separate observed revision/result, failure reason and retry eligibility. Detect
   superseded revisions, stale workers and partial resource application. Only report `synced` after
   checking the agreed scope against that revision; expose `drifted` and explicit `unknown`/`failed`
   conditions. Recovery must make progress after transient failures under stated stability/fairness
   assumptions; it must not endlessly retry irreversible effects. Do not enable destructive
   self-healing merely because drift is detected.
7. **Pin and audit.** At job admission, attach the immutable **effective** configuration revision
   and independent source-code revision as required by the approved run contract; later publication
   must not change an admitted job's provenance. Record actor, scope, base/candidate/published
   revisions, approved digest, decision, actual authorization outcome and timestamps without
   emitting secrets. A rollback of desired configuration is a **new** validated/reviewed publication
   pointing to previously known content where appropriate; it is not deletion of historical evidence
   and does not undo external side effects automatically.
8. **Verify invariants, failures and status honesty.** Test concurrent publishers, stale base,
   approval-content tampering, permission revocation, cross-scope approval, duplicate request keys
   with differing payloads, Git acknowledgment loss, Git-before-PostgreSQL crash, stale
   reconciliation, unavailable targets, drift, rollback, pinned runs and crash recovery. Execute
   with the real authority/backend when available; otherwise label mocks and model tests
   **reference-only**. Report precise commands, observed results, untested branches, and project
   acceptance links; never convert prepared cases into claimed pass results.


## Further references by decision

Read only the reference relevant to the unresolved decision. Research registers describe imported authorship, not current execution.

- [Sources, Access Evidence, and Derived Engineering Decisions](sources-and-decisions.md): source provenance or a disputed recommendation.
- [Publication State Model, Atomicity Boundaries, and Failure Recovery](state-machine-and-boundaries.md): state machine and boundaries.
- [Selective Routing and Test Oracles](test-oracles-and-routing.md): test oracles and routing.
