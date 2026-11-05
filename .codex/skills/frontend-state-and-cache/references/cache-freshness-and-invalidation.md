# Freshness, expiry and invalidation

> Ec-specific rules below apply only to an Ec consumer whose current contract establishes them.

## Orthogonal dimensions

`hasValue` answers whether a previous representation exists; `isFetching` describes activity; `fresh/stale` is a cache-policy inference; `error/offline` explains why it could not be refreshed. A stale value may be *useful* for logs/history, but may be unacceptable for permissions, approval eligibility or deployment readiness. Define this per feature. Never equate "last known" with "currently true".

- **Freshness lifetime (`staleTime` or app policy):** time until cached data is eligible for refetch, not a promise it has not changed.
- **Invalidation:** explicit instruction to mark results obsolete after a mutation, privilege change or external event.
- **Inactivity eviction (`gcTime` in TanStack Query v5):** removes unused entries; it does not define freshness of actively observed data.
- **Retry:** only repeat safe, idempotent or contract-supported reads/writes. Backoff/jitter are policy choices; no fixed budget is universally correct.
- **HTTP Cache-Control/ETag:** independent HTTP cache layer and validation semantics; application caches must not casually override origin privacy directives.

## Invalidation matrix example (illustrative, not Ec contract)

| Event | Target queries | Visible state | Recovery |
|---|---|---|---|
| user updates item | item details and matching list indexes | pending / stale until authoritative reply | specific invalidation and refetch |
| role revoked | all affected principal-scope entries | inaccessible/not authorized | purge, server reauthorization |
| reconnect after offline | queries eligible for reconnect refresh | last-known, timestamped | background refetch |
| versioned event gap | affected resource/dependency subtree | stale/unknown | full snapshot revalidation |
| active observer disappears | no immediate user-visible change | retained per explicit GC policy | evict after unobserved interval |

## Measurable oracles

Instrument number of actual network fetches, stale observation age distribution, invalidation-to-refresh duration, error state, duplicate-work count and cache memory footprint. Record representative test conditions. A higher cache hit rate can coexist with more stale data; never equate hit rate alone with correctness.

References: [TanStack important defaults v5](https://tanstack.com/query/latest/docs/framework/vue/guides/important-defaults), [Invalidation](https://tanstack.com/query/latest/docs/framework/vue/guides/query-invalidation), [Caching examples](https://tanstack.com/query/latest/docs/framework/vue/guides/caching), [RFC 9111](https://www.rfc-editor.org/rfc/rfc9111.html).
