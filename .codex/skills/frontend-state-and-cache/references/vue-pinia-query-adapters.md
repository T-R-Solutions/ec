# Vue 3 / Pinia / TanStack Query adapter boundaries

This skill is framework-agnostic at the method layer, while providing a Vue adapter profile because that stack is relevant to the host project. **Check actual installed package versions and code before applying instructions.** No package install, dependency migration or benchmark is assumed.

## Local state: Vue reactivity / Pinia

For small local interactions, `ref`, `computed` and component-owned state are adequate. Use Pinia where consistent shared state, action centralization, type inference and SSR-scoped instances matter. Do not automatically mirror all server query responses into Pinia; this can create two mutable copies of the same remote record. Pinia outside-component stores must be created after installing the proper instance, and in SSR must be tied to the request's app instance.

## Remote data: query cache

If a query library is approved, define a deterministic `queryKey` containing all data-shaping variables and auth context, a query function that reports errors by rejected Promise, a freshness/invalidation policy, cancellation with provided `signal`, and suitable query scopes. For TanStack Query v5, examine `staleTime`, `gcTime`, `invalidateQueries`, `cancelQueries` and optimistic mutation lifecycle; do not silently reuse v4 terminology/defaults.

## Vue interaction specifics

Reactive keys must track dependent refs; avoid keys that ignore changed project, filter, branch, locale or principal. Use computed selectors that *derive* user interface state from query snapshots. A loading spinner should not hide valid stale data unless product policy says to block it. Resolve all authoritative server transitions before showing saved/published/effective statuses.

## Implementation acceptance tests

1. Two components observing a query share the same key, not duplicate network requests.
2. Changing an auth/tenant scope invalidates or isolates every protected result; SSR clients do not share caches across requests.
3. A forced refetch cannot let an obsolete completion overwrite a later one.
4. An optimistic update rejection restores the baseline; for critical operations optimism is disabled.
5. Version upgrade from TanStack v4 to v5 is accompanied by tests for renamed `cacheTime`/`gcTime` and changed default assumptions.

References: [Vue state](https://vuejs.org/guide/scaling-up/state-management.html), [Pinia store definition](https://pinia.vuejs.org/core-concepts/), [Pinia outside component](https://pinia.vuejs.org/core-concepts/outside-component-usage.html), [TanStack Vue keys](https://tanstack.com/query/latest/docs/framework/vue/guides/query-keys), [v5 migration](https://tanstack.com/query/latest/docs/framework/vue/guides/migrating-to-v5).
