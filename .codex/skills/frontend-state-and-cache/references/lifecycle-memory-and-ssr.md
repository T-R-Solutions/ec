# Lifecycle, bounded memory, SSR and hydration

Client cache performance depends on both active subscriptions and inactive data retention. A cache that never discards superseded requests, stale pages or logged-out user entries becomes a privacy and memory problem. The eviction policy must be tested against observed/active entries, pending writes, previous-error state and application scope.

**SSR security:** avoid module-level mutable singleton state shared across concurrent HTTP requests. Create state/query instances per server request or isolate them by a verified auth scope. Hydrated payloads must be intentionally serialized and sanitized: secrets, tokens and server-only metadata cannot appear in rendered markup or shared CDN responses. Test cross-request isolation with two users and two projects.

**Lifecycle checklist:** observer mount/unmount, route change, foreground/background, reconnect, logout, tenant switch, session rotation, user-role change, tab eviction and SSR request disposal. Each must specify what is preserved, invalidated, refetched or purged.

**Stale time vs GC:** TanStack v5's `staleTime` controls perceived query freshness, while `gcTime` defines garbage collection after a query is no longer observed. Prior v4 docs use `cacheTime` for the latter; do not mechanically mix versions. Avoid fixed cache-size/performance claims without representative measurements. Inactive cache retention may help perceived responsiveness, but it increases private-data lifetime.

**Testing:** use injectable fake clocks for deterministic expiry/eviction, then real browser checks for lifecycle behavior. Verify an in-flight fetch resolved after logout cannot revive the disposed cache entry. Confirm no SSR data from request A is serialized into request B.

References: [Vue SSR](https://vuejs.org/guide/scaling-up/ssr.html), [Pinia SSR](https://pinia.vuejs.org/ssr/), [TanStack Vue SSR](https://tanstack.com/query/latest/docs/framework/vue/guides/ssr), [v5 migration](https://tanstack.com/query/latest/docs/framework/vue/guides/migrating-to-v5).
