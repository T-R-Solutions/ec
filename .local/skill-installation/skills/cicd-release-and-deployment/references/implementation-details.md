# Detailed decisions and failure cases

Read only for a decision needing more detail than the entry point. Apply the relevant steps to the
actual task; the sections are not a mandatory artifact or test checklist. Use existing project
contracts in their actual format. File names such as spec.md, plan.md or tasks.md are examples, not
required dependencies. Other skills are optional expertise, never enforcement mechanisms.

Ec references below are conditional consumer examples. They impose no Ec documents, YAML format,
stack or product policy on a generic task.

## Method (execute in order)

1. **Pin release identity.** Create an immutable release manifest binding artifact digest, source
   code revision, build/config revision, target constraints and recorded provenance. Distinguish
   verification of an attestation from merely finding a field named `verified`. No moving tags or
   branch heads as authority. [Identity](release-identity-and-provenance.md)
2. **Model release vs deployment.** A release is a candidate immutable artifact/config bundle. Each
   *deployment attempt* targets one environment and has its own idempotency key, actor, approval,
   attempt identity, baseline and observed state. A promotion to another target creates a new
   auditable attempt; do not mutate the release identity. [Promotion](promotion-and-environments.md)
3. **Authorize and bind approval.** Validate permission on the server. When required, approval must
   be bound to exact target, candidate digest, config revision, policy revision and expiry. Define
   separation of duties and invalidate stale/self/forged approvals. Do not release environment
   secrets before applicable gates. [Gates](approvals-and-change-windows.md)
4. **Choose supported strategy.** Select rolling, canary, blue/green, recreate or adapter-defined
   strategy based on target capabilities, availability budget, session/state migration risk and
   traffic-router guarantees. Model replica-weight approximations separately from actual request
   traffic weights. Validate rolling constraints such as nonzero progress when maxSurge and
   maxUnavailable are both zero. [Strategies](strategies-and-traffic.md)
5. **Validate signal quality.** Define a trustworthy canary/control comparison with explicit metric
   names, time window, minimum samples, current revision/phase/target, freshness and handling for
   empty/inconclusive/inconsistent results. Unknown is **not** success. Policy thresholds must
   originate in the feature spec; synthetic numbers are test fixtures only.
   [Analysis](health-gates-and-statistics.md)
6. **Use explicit state machine.** Separate request accepted, prepared, approval pending, rolling
   out, paused, abort requested, rollback requested and **provider-confirmed** terminal states. A
   `202 Accepted`, timeout or optimistic UI cannot prove deployment completion.
   [States](deployment-state-contract.md)
7. **Avoid stale ownership.** Use atomic target lease/serialization, idempotency scope, optimistic
   versioning (CAS), immutable attempt/epoch, and reconcile after crashes or ambiguous provider
   responses. No silent last-write-wins or globally exactly-once effects.
   [Concurrency](concurrency-and-reconciliation.md)
8. **Rollback only with evidence.** Stop/abort an in-progress rollout separately from rolling back a
   completed one. Verify retained baseline artifact and provider support, target compatibility,
   database/data migration and irreversible external effects. When unsupported, recommend a
   controlled roll-forward/fix rather than a fictional automatic rollback. A rollback *request* is
   not a rollback *result*. [Rollback](abort-rollback-and-recovery.md)
   [Migrations](schema-migration-safety.md)
9. **Verify cross-cutting security and audit.** Bind release digest/target to authorization; enforce
   provenance/trust, least-privilege credentials, protected environments, external secret
   references, approved change window and complete audit events.
   [Security](security-and-supply-chain.md)
10. **Produce oracles and evidence.** Execute positive, negative, boundary, fault, concurrency,
    property and mutation cases. Distinguish synthetic Python/SQLite/TypeScript from real
    control-plane/provider/migration tests. Record exact host/tool versions and `PASS`, `FAIL`, `NOT
    EXECUTED`, `SPEC GAP` against accepted AC. The project workflow bridge is optional.
    [Verification](testing-and-test-oracles.md) [Evidence](evidence-and-reporting.md)


## Further references by decision

Read only the reference relevant to the unresolved decision. Research registers describe imported authorship, not current execution.

- [Abort, rollback and recovery](abort-rollback-and-recovery.md): abort rollback and recovery.
- [Approvals, least privilege and change windows](approvals-and-change-windows.md): approvals and change windows.
- [Concurrency, idempotency and reconciliation](concurrency-and-reconciliation.md): concurrency and reconciliation.
- [Deployment state and provider receipts](deployment-state-contract.md): deployment state contract.
- [project workflow and acceptance evidence](evidence-and-reporting.md): evidence and reporting.
- [Health gates and statistical defensibility](health-gates-and-statistics.md): health gates and statistics.
- [Promotion and environments](promotion-and-environments.md): promotion and environments.
- [Release identity, provenance and immutability](release-identity-and-provenance.md): release identity and provenance.
- [Data migration and compatibility](schema-migration-safety.md): schema migration safety.
- [Peer-reviewed research: findings, transfer limits and citations](scientific-evidence-and-limits.md): source provenance or a disputed recommendation.
- [Deployment security and supply chain](security-and-supply-chain.md): security and supply chain.
- [Skill routing examples](selection-and-boundaries.md): selection and boundaries.
- [Official sources, verified scope and engineering decisions](sources-and-decisions.md): source provenance or a disputed recommendation.
- [Deployment strategies and traffic semantics](strategies-and-traffic.md): strategies and traffic.
- [Test matrix and oracles](testing-and-test-oracles.md): testing and test oracles.
