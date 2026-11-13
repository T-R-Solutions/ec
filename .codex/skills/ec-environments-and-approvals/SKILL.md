---
name: ec-environments-and-approvals
description: Implement or review Ec environment resources, promotion requests, approval gates and operational feedback. Skip generic rollout algorithms and configuration publication.
---

No Ec, siga `docs/development/task-verification.md` para acionar checks e
`docs/development/local-state.md` para arquivos de trabalho (caminhos desde a
raiz). Em outros consumidores, siga as políticas locais.

# Ec Environments and Approvals

## Scope and inputs

Locate environment schemas, target identity, release/artifact provenance, trusted actors, executor
and approval policy. Discover supported operations rather than borrowing another CI product model.

Use the Ec consumer's current product requirements and implementation as authority. Locate them
rather than assuming filenames, schemas, endpoints or deployed features. Examples in this folder are
technical proposals until confirmed there.

## Procedure

1. Separate desired environment/protection configuration from votes, check results, deployment
   attempts and secret materialization.
2. Bind a promotion request to exact source/configuration/artifact revisions and target; approval
   for one candidate cannot authorize another.
3. Enforce approver eligibility, separation of duty, policy freshness, expiry and current
   authorization at the trusted execution boundary.
4. Distinguish gate pending/approved/rejected from executor acceptance and observed deployment.
   Reconcile uncertain outcomes before retrying.
5. Test changed candidates, revoked approval rights, concurrent requests, expired gates, failed
   execution and accessible recovery without claiming rollback erased external effects.

## Load detail when needed

- [Gate and Promotion Lifecycle — Technical Decision
  Guide](references/gates-and-promotion-lifecycle.md): consult for gates and promotion lifecycle.
- [Administration, Security and UI — Technical Decision Guide](references/security-and-ui.md):
  consult for security and ui.
- [Activation, Handoffs and Acceptance Oracles](references/routing-and-test-oracles.md): consult for
  routing and test oracles.
- [Detailed decisions and failure cases](references/implementation-details.md): consult for a
  complex case, additional constraints or the remaining thematic references; apply only relevant
  steps.

## Completion and limits

Registre contrato afetado, inputs, método, resultado e lacunas; distinga inspeção,
simulação e integração real. Registros importados não são resultados atuais.

Aplique apenas os passos pertinentes, usando o registro existente do projeto.
A skill funciona sem SDD; consulte auxiliares disponíveis somente para questões
distintas ainda não resolvidas. O método não amplia a autorização da tarefa.
