---
name: ec-run-execution-operations
description: Implement or review Ec operator-facing run/job/attempt status, cancellation, retry and diagnostics. Skip scheduler allocation, local process control and configuration publication.
---

No Ec, siga `docs/development/task-verification.md` para acionar checks e
`docs/development/local-state.md` para arquivos de trabalho (caminhos desde a
raiz). Em outros consumidores, siga as políticas locais.

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

Registre contrato afetado, inputs, método, resultado e lacunas; distinga inspeção,
simulação e integração real. Registros importados não são resultados atuais.

Aplique apenas os passos pertinentes, usando o registro existente do projeto.
A skill funciona sem SDD; consulte auxiliares disponíveis somente para questões
distintas ainda não resolvidas. O método não amplia a autorização da tarefa.
