# State ownership and projections

> Commands, fixture paths and historical execution claims from the source package are illustrative or imported evidence unless the corresponding files are present and independently checked; they are not current test results or bundled executable tools.

## Identify which system owns which value

Separate at least four categories before introducing a global store:

| Category | Examples | Truth / lifecycle | Appropriate tool |
|---|---|---|---|
| Ephemeral component interaction | open inspector, hover, selected tab | local to view until navigation | component state / composable |
| Shared client UI state | density, recent-view preference, transient filter | user session or browser policy, not server authority by default | shared reactive store |
| Remote query state | paged run list, agent status, permissions projection | authoritative remote service; cache is discardable | query cache + validation/invalidation |
| Draft/command | proposed edit, approval request | uncommitted command until server acknowledges each step | explicit draft/mutation state |

A server-derived cache is a **materialized observation**. A local store may hold a copy for presentation but cannot gain authority by being reactive. Avoid copying server data from query cache into a second writable global store unless needed and the synchronization contract is explicit.

## Workflow

1. List each field as `owner`, `writer`, `reader`, `scope`, `revision`, `expiry`, `sensitive`, `rebuild source`, and `failure behavior`.
2. Assign one write coordinator per field; create pure projections for read-only views. Do not assume two representations are semantically equal because their fields look alike.
3. Distinguish selected project (local context) from target project resource (server data), and optimistic draft from the submitted or published revision.
4. On navigation, state derivation should not silently keep privileged contents from the previous project; use a cache key with the complete identity and scope.
5. Set `unavailable`, `stale`, `unknown`, `loading` and `error` states rather than interpreting absence as success.

## Acceptance oracle

Mount two consumers of the same query and confirm deduplicated reads; change a local tab without invalidating remote resources; change a data-shaping filter and verify a different query key. Reconcile a server-confirmed update without mutating a concurrent unsaved draft.

## Boundary

Vue's one-way data-flow guidance and Pinia document the shared-client-state design space, not a rule that every network resource must be stored in Pinia. Product specifications own persistence and publication rules.

References: [Vue state management](https://vuejs.org/guide/scaling-up/state-management.html), [TanStack Vue Query queries](https://tanstack.com/query/latest/docs/framework/vue/guides/queries).
