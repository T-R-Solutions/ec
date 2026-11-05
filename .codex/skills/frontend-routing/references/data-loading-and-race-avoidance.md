# View Load Lifecycle and Route-Transition Races

Fetching **before** navigation and **after** navigation are both supported patterns in the Vue Router documentation. Choose based on user-visible behavior, ability to show loading while still on the old page, error handling, and required permissions. A route transition must not allow an older route response to replace the new view.

```ts
const route = useRoute()
watch(() => [route.params.workspaceId, route.params.resourceId], async (_next, _prev, onCleanup) => {
  const controller = new AbortController()
  onCleanup(() => controller.abort())
  // Use a request generation/identity fence too: abort is not a completion guarantee.
  // Route values and method semantics must be validated against the real API.
})
```

Use route identity + session epoch and generation tokens to guard rendering of the result, including after error/abort. Clean up on unmount, on route parameter change and when an identity scope changes. Show loading, not-found, forbidden, disconnected and error statuses honestly based on server observations.

This reference covers **route-specific load cancellation** only, not query caches, stale-time policy, WebSocket resume or server revision semantics; refer to specialized skills for those topics. If a commit/deploy operation is triggered from a route, UI navigational success is not backend job success.

Sources: [Vue Router Data Fetching](https://router.vuejs.org/guide/advanced/data-fetching.html), [Vue Router Dynamic Matching](https://router.vuejs.org/guide/essentials/dynamic-matching.html), [MDN AbortController](https://developer.mozilla.org/en-US/docs/Web/API/AbortController).
