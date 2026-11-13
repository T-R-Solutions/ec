---
name: ec-configuration-publication
description: Implement or review Ec configuration publication, immutable revisions, approval binding, conflicts and reconciliation. Skip schema definitions, editor projections and application deployment.
---

No Ec, siga `docs/development/task-verification.md` para acionar checks e
`docs/development/local-state.md` para arquivos de trabalho (caminhos desde a
raiz). Em outros consumidores, siga as políticas locais.

# Ec Configuration Publication

## Scope and inputs

Locate the actual desired-state authority, draft/base revisions, approval policy, publication commit
point and operational projections. Do not assume Git or PostgreSQL is deployed.

Use the Ec consumer's current product requirements and implementation as authority. Locate them
rather than assuming filenames, schemas, endpoints or deployed features. Examples in this folder are
technical proposals until confirmed there.

## Procedure

1. Distinguish saved draft, validated candidate, approval, published revision and observed
   application; define an authoritative predicate for every displayed status.
2. Bind approvals and authorization to candidate content, base, actor, scope and policy revision.
   Recheck at the publication boundary.
3. Prevent lost updates at the actual authority with a conditional commit or equivalent mechanism;
   never silently overwrite a newer revision.
4. Model acknowledgment loss and cross-store crash windows. If Git is authoritative, a database
   outbox cannot make Git and database writes one atomic transaction.
5. Reconcile and audit by immutable revision, reject superseded workers and preserve admitted-run
   provenance. Test concurrent publication, stale approval, unknown outcomes and recovery.

## Load detail when needed

- [Publication State Model, Atomicity Boundaries, and Failure
  Recovery](references/state-machine-and-boundaries.md): consult for state machine and boundaries.
- [Selective Routing and Test Oracles](references/test-oracles-and-routing.md): consult for test
  oracles and routing.
- [Sources, Access Evidence, and Derived Engineering
  Decisions](references/sources-and-decisions.md): consult for sources and decisions.
- [Detailed decisions and failure cases](references/implementation-details.md): consult for a
  complex case, additional constraints or the remaining thematic references; apply only relevant
  steps.

## Completion and limits

Registre contrato afetado, inputs, método, resultado e lacunas; distinga inspeção,
simulação e integração real. Registros importados não são resultados atuais.

Aplique apenas os passos pertinentes, usando o registro existente do projeto.
A skill funciona sem SDD; consulte auxiliares disponíveis somente para questões
distintas ainda não resolvidas. O método não amplia a autorização da tarefa.
