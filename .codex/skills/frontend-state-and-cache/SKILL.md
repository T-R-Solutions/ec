---
name: frontend-state-and-cache
description: Design or repair frontend state ownership, remote-query caches, invalidation and optimistic updates. Use for cross-view consistency or stale-state defects; skip local bindings and server persistence design.
---

No Ec, siga `docs/development/task-verification.md` para acionar checks e
`docs/development/local-state.md` para arquivos de trabalho (caminhos desde a
raiz). Em outros consumidores, siga as políticas locais.

# Frontend State and Cache Engineering — Scoped Consistency and Observable Evidence

## Scope and inputs

Inventory authoritative server data, local drafts, preferences, query identity, principal/scope,
revisions and cache lifetime. Inspect actual state libraries and SSR mode.

Preserve the consumer's stack and policies. Product-specific examples in references do not establish
requirements for this task.

## Procedure

1. Assign one owner to each state class and derive projections rather than synchronizing competing stores.
2. Include every identity-affecting parameter and authorization scope in query keys; invalidate
   protected data on principal or permission changes.
3. Separate freshness, invalidation, retention and eviction. Define mutation effects across
   dependent queries from the server contract.
4. Bind optimistic writes and responses to request/base revision; prevent an old rollback or late
   response from overwriting newer state. Cancellation does not undo server effects.
5. Test concurrent mutations, logout/login, stale replies, reconnect and SSR isolation. Report
   backend-confirmed state separately from local optimistic state.

## Load detail when needed

- [State ownership and projections](references/state-ownership-and-projections.md): consult for
  state ownership and projections.
- [Deterministic query identity and isolation](references/query-identity-and-isolation.md): consult
  for query identity and isolation.
- [Mutations and optimistic client rendering](references/mutation-and-optimistic-safety.md): consult
  for mutation and optimistic safety.
- [Detailed decisions and failure cases](references/implementation-details.md): consult for a
  complex case, additional constraints or the remaining thematic references; apply only relevant
  steps.

## Completion and limits

Registre contrato afetado, inputs, método, resultado e lacunas; distinga inspeção,
simulação e integração real. Registros importados não são resultados atuais.

Aplique apenas os passos pertinentes, usando o registro existente do projeto.
A skill funciona sem SDD; consulte auxiliares disponíveis somente para questões
distintas ainda não resolvidas. O método não amplia a autorização da tarefa.
