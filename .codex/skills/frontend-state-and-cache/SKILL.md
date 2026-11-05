---
name: frontend-state-and-cache
description: Design or repair frontend state ownership, remote-query caches, invalidation and optimistic updates. Use for cross-view consistency or stale-state defects; skip local bindings and server persistence design.
---

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

Complete the requested change or diagnosis and the checks that support its claims. Report the
affected contract, observed results, actual versions/commands and material gaps. Keep structural
review, simulated cases, real integration and production evidence distinct. Imported research and
execution records are historical context, not results of the current task.

This folder works without SDD or other skills. Consult an available specialist only for a concrete
unresolved question; its absence is a gap only when essential to acceptance. Use the project's
existing record for a bounded change. This method grants no external access, publication or
destructive-action authority.
