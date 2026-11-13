---
name: cicd-release-and-deployment
description: Implement or review CI/CD artifact promotion, rollout gates, deployment state and recovery. Skip product-specific environment resources, scheduling and artifact-storage internals.
---

No Ec, siga `docs/development/task-verification.md` para acionar checks e
`docs/development/local-state.md` para arquivos de trabalho (caminhos desde a
raiz). Em outros consumidores, siga as políticas locais.

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

Registre contrato afetado, inputs, método, resultado e lacunas; distinga inspeção,
simulação e integração real. Registros importados não são resultados atuais.

Aplique apenas os passos pertinentes, usando o registro existente do projeto.
A skill funciona sem SDD; consulte auxiliares disponíveis somente para questões
distintas ainda não resolvidas. O método não amplia a autorização da tarefa.
