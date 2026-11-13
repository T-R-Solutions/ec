---
name: ec-pipeline-authoring-workspace
description: Implement or review Ec pipeline canvas, inspector, Form/YAML coordination and draft interactions. Skip DAG algorithms, schema ownership and execution scheduling.
---

No Ec, siga `docs/development/task-verification.md` para acionar checks e
`docs/development/local-state.md` para arquivos de trabalho (caminhos desde a
raiz). Em outros consumidores, siga as políticas locais.

# Ec Pipeline Authoring Workspace

## Scope and inputs

Locate supported resource kinds, editor capabilities, current draft and parser/schema contracts,
user permissions and actual save/publication semantics.

Use the Ec consumer's current product requirements and implementation as authority. Locate them
rather than assuming filenames, schemas, endpoints or deployed features. Examples in this folder are
technical proposals until confirmed there.

## Procedure

1. Keep one semantic draft with separate view state; canvas selection and viewport are not desired
   configuration unless the contract says so.
2. Define narrow edits using stable node/edge identities and explicit base revision; validate
   unsupported operations without discarding unseen fields.
3. Coordinate Visual, Form and YAML projections, preserving invalid text buffers and the last valid
   semantic state without pretending they are synchronized.
4. Provide keyboard and non-drag alternatives, contextual diagnostics, undo/redo semantics and
   honest draft/published/effective feedback.
5. Test mode switches, hidden fields, rejected edges, stale replies, undo and conflicts. A rendered
   DAG does not establish schedulability or execution.

## Load detail when needed

- [Pipeline Authoring Workspace — Interaction and State
  Contract](references/interaction-and-state-contract.md): consult for interaction and state
  contract.
- [Routing, Test Oracles and Evidence Expectations](references/routing-and-test-oracles.md): consult
  for routing and test oracles.
- [Source Evidence Ledger — Ec Pipeline Authoring Workspace
  (2026-10-31)](references/sources-and-decisions.md): consult for sources and decisions.
- [Detailed decisions and failure cases](references/implementation-details.md): consult for a
  complex case, additional constraints or the remaining thematic references; apply only relevant
  steps.

## Completion and limits

Registre contrato afetado, inputs, método, resultado e lacunas; distinga inspeção,
simulação e integração real. Registros importados não são resultados atuais.

Aplique apenas os passos pertinentes, usando o registro existente do projeto.
A skill funciona sem SDD; consulte auxiliares disponíveis somente para questões
distintas ainda não resolvidas. O método não amplia a autorização da tarefa.
