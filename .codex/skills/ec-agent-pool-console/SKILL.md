---
name: ec-agent-pool-console
description: Implement or review Ec agent/pool administration and explainable eligibility views. Skip scheduler placement algorithms, agent wire protocols and local execution.
---

No Ec, siga `docs/development/task-verification.md` para acionar checks e
`docs/development/local-state.md` para arquivos de trabalho (caminhos desde a
raiz). Em outros consumidores, siga as políticas locais.

# Ec Agent and Pool Console

## Scope and inputs

Inspect approved pool/project relationships, runtime observations, permissions and actual APIs.
Separate desired configuration from sessions, capacity and active leases.

Use the Ec consumer's current product requirements and implementation as authority. Locate them
rather than assuming filenames, schemas, endpoints or deployed features. Examples in this folder are
technical proposals until confirmed there.

## Procedure

1. Expose enrollment, connectivity, maintenance, trust, compatibility and occupancy as distinct
   evidence where supported; missing data is not measured zero.
2. Explain eligibility through scoped policy, verified capabilities, freshness and capacity. Do not
   fabricate queue position or wait-time estimates.
3. Preview assignment changes against source and target scopes, active work and the current
   revision; recheck authorization at mutation.
4. Model drain/disable/remove using the actual operation contract. A request to drain does not imply
   active work was cancelled.
5. Test stale snapshots, concurrent reassignment, permission changes and accessible recovery.
   Confirm effects through backend state rather than button feedback.

## Load detail when needed

- [Explainable Eligibility and Evidence — Engineering Decision
  Guide](references/eligibility-and-evidence.md): consult for eligibility and evidence.
- [Pool Administration, Operator Safety and UX — Method Guide](references/administration-and-ui.md):
  consult for administration and ui.
- [Activation and Real Integration Oracles](references/routing-and-test-oracles.md): consult for
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
