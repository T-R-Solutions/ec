---
name: cicd-pipeline-graph
description: Implement or review CI/CD dependency-graph validation, cycle diagnostics, deterministic ordering and readiness. Skip canvas interactions, resource scheduling and execution.
---

No Ec, siga `docs/development/task-verification.md` para acionar checks e
`docs/development/local-state.md` para arquivos de trabalho (caminhos desde a
raiz). Em outros consumidores, siga as políticas locais.

# CI/CD Pipeline Graph Engineering

## Scope and inputs

Define node/edge identity, dependency direction, duplicates, unresolved references, conditions and
graph revision. Do not infer runtime success policy from graph shape.

Preserve the consumer's stack and policies. Product-specific examples in references do not establish
requirements for this task.

## Procedure

1. Validate node/edge integrity before traversal; distinguish unknown references, self-dependencies
   and directed cycles.
2. Produce a checkable cycle witness and deterministic topological order under a declared tie-break;
   an order is not the only valid order.
3. Separate prerequisite reachability, dependents/impact and topological generations from resource
   eligibility and dispatch.
4. Evaluate readiness using the actual predecessor outcome contract, including skipped, failed,
   cancelled and conditional branches where specified.
5. Test isolated/disconnected graphs, fan-in/out, duplicate edges, cycles and edits against
   independent properties. Measure representative graph costs before claiming scalability.

## Load detail when needed

- [Graph contract and trust boundaries](references/graph-contract-and-trust-boundaries.md): consult
  for graph contract and trust boundaries.
- [Directed cycle detection and witness construction](references/cycle-diagnostics.md): consult for
  cycle diagnostics.
- [Prerequisite readiness is not execution scheduling](references/readiness-and-status.md): consult
  for readiness and status.
- [Detailed decisions and failure cases](references/implementation-details.md): consult for a
  complex case, additional constraints or the remaining thematic references; apply only relevant
  steps.

## Completion and limits

Registre contrato afetado, inputs, método, resultado e lacunas; distinga inspeção,
simulação e integração real. Registros importados não são resultados atuais.

Aplique apenas os passos pertinentes, usando o registro existente do projeto.
A skill funciona sem SDD; consulte auxiliares disponíveis somente para questões
distintas ainda não resolvidas. O método não amplia a autorização da tarefa.
