---
name: ec-effective-permissions
description: Implement or review Ec effective authorization, inherited grants, provenance, freshness and delegation controls. Skip authentication federation and access-administration UI alone.
---

No Ec, siga `docs/development/task-verification.md` para acionar checks e
`docs/development/local-state.md` para arquivos de trabalho (caminhos desde a
raiz). Em outros consumidores, siga as políticas locais.

# Ec Effective Permissions

## Scope and inputs

Identify principal/status, policy revision, role bindings, resource hierarchy, membership graphs and
the trusted enforcement boundary. Resolve missing grant/deny precedence from the product contract.

Use the Ec consumer's current product requirements and implementation as authority. Locate them
rather than assuming filenames, schemas, endpoints or deployed features. Examples in this folder are
technical proposals until confirmed there.

## Procedure

1. Keep project hierarchy, role hierarchy and membership graphs distinct; reject invalid cycles or
   cross-scope references according to their own contracts.
2. Evaluate explicit action/resource/context policy. Distinguish allow, deny and indeterminate;
   unavailable or stale authority must not become a grant.
3. Bind decisions to coherent policy, identity, membership and hierarchy versions. Recheck freshness
   and revocation at the actual protected operation.
4. Authorize delegation separately from permission to edit bindings; prevent unauthorized
   self-grants and privilege escalation.
5. Return safe provenance and observed versions, then test inherited grants, revocation, stale
   caches, concurrent changes and absence of effects on denial.

## Load detail when needed

- [Effective Authorization Evaluation and Freshness — Engineering
  Reference](references/evaluation-contract-and-freshness.md): consult for evaluation contract and
  freshness.
- [Skill Routing and Verification Oracles](references/routing-and-test-oracles.md): consult for
  routing and test oracles.
- [Evidence Ledger and Engineering Decisions — Ec Effective
  Permissions](references/sources-and-decisions.md): consult for sources and decisions.
- [Detailed decisions and failure cases](references/implementation-details.md): consult for a
  complex case, additional constraints or the remaining thematic references; apply only relevant
  steps.

## Completion and limits

Registre contrato afetado, inputs, método, resultado e lacunas; distinga inspeção,
simulação e integração real. Registros importados não são resultados atuais.

Aplique apenas os passos pertinentes, usando o registro existente do projeto.
A skill funciona sem SDD; consulte auxiliares disponíveis somente para questões
distintas ainda não resolvidas. O método não amplia a autorização da tarefa.
