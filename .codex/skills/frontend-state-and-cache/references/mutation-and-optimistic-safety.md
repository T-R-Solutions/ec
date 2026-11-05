# Mutations and optimistic client rendering

## Optimism is an explicit per-operation decision

Do not interpret a click, draft edit, client `setQueryData` or HTTP request dispatch as a completed server operation. For irreversible/high-impact changes, keep the UI in pending state until server evidence arrives, then separately reflect publication/effect if the contract distinguishes them. An optimistic *visual hint* may be acceptable only for low-risk reversible changes with defined rollback and conflict handling.

## Recommended guard sequence for a reversible mutation

1. Confirm product policy explicitly allows optimistic presentation for that action; do not infer from library availability.
2. Identify key, principal, last authoritative revision and operation id.
3. Cancel/reconcile in-flight refetches; snapshot baseline and mark speculative state separately from real state.
4. Apply provisional projection (not authoritative state); block overlapping optimistic writes without an explicit composition strategy.
5. On server rejection restore baseline and invalidate/refetch; preserve user draft when restoration would lose edits.
6. On server acceptance, expose the precise contract status (`accepted`, `persisted`, `published`, `effective` as applicable), not a generic "success". Invalidate affected queries to obtain real state.
7. On transport timeout of a non-idempotent operation, show ambiguous outcome and reconcile by operation id; do not automatically retry without documented idempotency support.

## Failure cases to test

- Out-of-order server ACK must not roll back a different, newer user edit.
- Event arriving during a speculative change must not silently overwrite the draft.
- Retrying an ambiguous mutation is prohibited unless the server contract makes duplicate application safe.
- Permission revocation immediately fences pending optimistic results and purges sensitive data.
- A read-only browser demo must never imply it has deployed or published configuration.

TanStack Query's `onMutate` snapshot and rollback APIs are one implementation pattern; the domain decides whether optimism is allowed. Reference: [TanStack Vue optimistic updates](https://tanstack.com/query/latest/docs/framework/vue/guides/optimistic-updates).
