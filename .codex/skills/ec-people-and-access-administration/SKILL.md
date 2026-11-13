---
name: ec-people-and-access-administration
description: Implement or review Ec users, teams, groups, memberships and role-binding administration. Skip federation protocols and the effective-permission evaluation algorithm.
---

No Ec, siga `docs/development/task-verification.md` para acionar checks e
`docs/development/local-state.md` para arquivos de trabalho (caminhos desde a
raiz). Em outros consumidores, siga as políticas locais.

# Ec People and Access Administration

## Scope and inputs

Inspect principal lifecycle, membership ownership, external identity mapping, supported bindings,
policy revisions and actual administration APIs.

Use the Ec consumer's current product requirements and implementation as authority. Locate them
rather than assuming filenames, schemas, endpoints or deployed features. Examples in this folder are
technical proposals until confirmed there.

## Procedure

1. Separate authentication identity, active account status, group membership, assigned role and
   effective access; do not treat labels as grants.
2. Model invitations, activation, suspension, deletion and membership changes only where supported,
   preserving history and immutable identity.
3. Preview binding changes with the actual permission evaluator; check delegation authority, target
   scope and self-escalation independently.
4. Reauthorize mutations against current state and reconcile concurrent grant/revoke or lost
   acknowledgments; invalidate affected access projections according to policy.
5. Test protected last-administrator rules only when specified, unauthorized grants, stale previews
   and accessible error recovery. Confirm server effects and absence of effects after denial.

## Load detail when needed

- [Identity, Administrative Lifecycle and Binding Contracts — Engineering
  Reference](references/identity-lifecycle-and-bindings.md): consult for identity lifecycle and
  bindings.
- [Routing, Synthetic Test IDs and Real Ec Acceptance
  Oracles](references/routing-and-test-oracles.md): consult for routing and test oracles.
- [Evidence Ledger and Design Decisions — Ec People and Access
  Administration](references/sources-and-decisions.md): consult for sources and decisions.
- [Detailed decisions and failure cases](references/implementation-details.md): consult for a
  complex case, additional constraints or the remaining thematic references; apply only relevant
  steps.

## Completion and limits

Registre contrato afetado, inputs, método, resultado e lacunas; distinga inspeção,
simulação e integração real. Registros importados não são resultados atuais.

Aplique apenas os passos pertinentes, usando o registro existente do projeto.
A skill funciona sem SDD; consulte auxiliares disponíveis somente para questões
distintas ainda não resolvidas. O método não amplia a autorização da tarefa.
