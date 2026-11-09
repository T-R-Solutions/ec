---
name: ec-run-execution-operations
description: Implement or review Ec operator-facing run/job/attempt status, cancellation, retry and diagnostics. Skip scheduler allocation, local process control and configuration publication.
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

# Ec Run Execution Operations

## Scope and inputs

Inspect actual lifecycle vocabulary, immutable revisions, command APIs, permissions, event ordering
and persisted records. Keep observed behavior distinct from proposed states.

Use the Ec consumer's current product requirements and implementation as authority. Locate them
rather than assuming filenames, schemas, endpoints or deployed features. Examples in this folder are
technical proposals until confirmed there.

## Procedure

1. Separate backend execution state, operation-request state and transport freshness; a click or
   HTTP acceptance does not prove completion.
2. Specify allowed actions, rejected transitions, expected revision, actor/scope, operation identity
   and observable completion.
3. Reauthorize server-side and reconcile duplicate or uncertain requests. Cancellation must
   distinguish request, signaling, cleanup and terminal confirmation, including races with natural
   completion.
4. Give retries their contract-defined new run/attempt identity and preserved lineage; do not imply
   exactly-once external effects or reuse stale authorization.
5. Test stale/gapped events, concurrent cancel/retry, revoked permissions and honest accessible
   status. Fetch authoritative state when continuity cannot be established.

## Load detail when needed

- [Lifecycle, request, and observation contract — implementation
  guide](references/lifecycle-and-operation-contract.md): consult for lifecycle and operation
  contract.
- [Skill selection, counterexamples and test oracles](references/routing-and-test-oracles.md):
  consult for routing and test oracles.
- [Primary sources, scientific grounding and decision
  traceability](references/sources-and-decisions.md): consult for sources and decisions.
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
