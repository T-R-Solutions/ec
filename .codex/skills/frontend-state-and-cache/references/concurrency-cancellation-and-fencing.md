# Concurrent remote reads, abort and generation fencing

## Required causal order

For identical keys, deduplication is often appropriate. For a forced refetch, increase the per-key generation, abort the previous request if supported, and accept a completion only if it still belongs to the active generation **and** auth context. Aborted network work is not always stopped on the server; an abort signal does not prove that a write did not occur.

A robust read completion predicate is `active key && scope unchanged && generation matches && !signal.aborted && revision acceptable`. Missing any of these may allow stale or cross-scope publication.

## Interleaving table

| Timeline | Event | Required result |
|---|---|---|
| t0 | A request for item 1 starts | pending A |
| t1 | query invalidated and B starts | A becomes obsolete; B pending |
| t2 | B returns rev 11 | B populates cache; rev 11 shown |
| t3 | A returns rev 12, even if its abort was ignored | A discarded because obsolete generation, despite higher revision |
| t4 | user logs out | private cache removed and old pending work fenced |

Do not confuse monotonically increasing resource revision with a query-generation token: a newer server revision delivered by an invalidated request may belong to the wrong selection or authorization context.

## Stress tests

Schedule responses in all relevant orders using manually controlled promises, not sleeps. Include duplicate requests, force refetch, cancellation before network start, late rejection, app unmount, principal switch and invalidation while cached data is visible. Assert both the final value and the returned result/reason (`ignored` vs `committed`).

References: [Fetch abort](https://developer.mozilla.org/en-US/docs/Web/API/Fetch_API/Using_Fetch), [TanStack Vue Query cancellation](https://tanstack.com/query/latest/docs/framework/vue/guides/query-cancellation).
