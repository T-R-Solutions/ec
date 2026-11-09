---
name: ec-pipeline-authoring-workspace
description: Implement or review Ec pipeline canvas, inspector, Form/YAML coordination and draft interactions. Skip DAG algorithms, schema ownership and execution scheduling.
---

## Acionamento de scripts no Ec

Neste checkout, esta regra prevalece sobre recomendações de execução desta skill e
suas referências. Scripts de verificação só podem ser acionados após pedido explícito
de commit. Pergunte exatamente: “Deseja executar o script automate.sh (que roda todos os outros scripts)?”
Ofereça somente `sim` e `não`, sem resposta automática; aguarde a escolha.
Com `não`, prossiga normalmente com os commits solicitados, sem executar scripts.
Com `sim`, execute da raiz `bash scripts/automate.sh run`, sem filtros, e só crie
os commits após todos os scripts da suíte terminarem sem erros. Falha, interrupção,
ferramenta ausente ou execução incompleta bloqueiam os commits nessa opção.
Não execute scripts avulsos nem os acione automaticamente durante implementação,
revisão ou encerramento. Inspeção manual continua permitida; checks não executados
permanecem explicitamente não verificados. Detalhes: `docs/development/task-verification.md`
(caminho relativo à raiz Ec). Em outros projetos, siga a política do consumidor.

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

Complete the requested change or diagnosis and the checks that support its claims. Report the
affected contract, observed results, actual versions/commands and material gaps. Keep structural
review, simulated cases, real integration and production evidence distinct. Imported research and
execution records are historical context, not results of the current task.

This folder works without SDD or other skills. Consult an available specialist only for a concrete
unresolved question; its absence is a gap only when essential to acceptance. Use the project's
existing record for a bounded change. This method grants no external access, publication or
destructive-action authority.
