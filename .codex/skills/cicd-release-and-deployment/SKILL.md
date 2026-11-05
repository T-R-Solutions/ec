---
name: cicd-release-and-deployment
description: Implement or review CI/CD artifact promotion, rollout gates, deployment state and recovery. Skip product-specific environment resources, scheduling and artifact-storage internals.
---

# CI/CD Release and Deployment Engineering

## Scope and inputs

Identify artifact/configuration digests, target, current generation, trusted actor, strategy
support, approval policy, health criteria and state recovery constraints.

Preserve the consumer's stack and policies. Product-specific examples in references do not establish
requirements for this task.

## Procedure

1. Promote the identified artifact and bind configuration, provenance and target; rebuilding
   silently creates a different release.
2. Bind approvals and change windows to exact content, scope and policy. Reauthorize at execution
   and fence obsolete deployment attempts.
3. Choose rollout strategy using actual routing/provider capabilities; define observation, abort and
   recovery conditions before changing traffic.
4. Reconcile desired and observed state after partial failure or lost acknowledgment. Code rollback
   cannot reverse incompatible data migration or external effects.
5. Test concurrent promotions, stale approval, unhealthy rollout and interrupted recovery. Treat
   health sampling uncertainty explicitly and separate simulated providers from actual deployment
   evidence.

## Load detail when needed

- [Release identity, provenance and immutability](references/release-identity-and-provenance.md):
  consult for release identity and provenance.
- [Concurrency, idempotency and reconciliation](references/concurrency-and-reconciliation.md):
  consult for concurrency and reconciliation.
- [Abort, rollback and recovery](references/abort-rollback-and-recovery.md): consult for abort
  rollback and recovery.
- [Detailed decisions and failure cases](references/implementation-details.md): consult for a
  complex case, additional constraints or the remaining thematic references; apply only relevant
  steps.

## Completion and limits

Complete the requested change or diagnosis and the checks that support its claims. Report the
affected contract, observed results, actual versions/commands and material gaps. Keep structural
review, simulated cases, real integration and production evidence distinct. Imported research and
execution records are historical context, not results of the current task.

This folder works without SDD or other skills. Consult an available specialist only for a concrete
unresolved question; its absence is a gap only when essential to acceptance. Use the project's
existing record for a bounded change. This method grants no external access, publication or
destructive-action authority.
