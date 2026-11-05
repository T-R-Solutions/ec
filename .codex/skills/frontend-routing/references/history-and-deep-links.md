# Browser History, Deep Links and Revisit Semantics

`pushState()` adds an entry; `replaceState()` updates the current entry; the user agent emits `popstate` when moving within session history. Importantly, `pushState` does **not** trigger a `popstate` event and changing history state does not fetch server content. The router synchronizes view state with the browser URL.

**Expected behavior**

1. Opening a copied deep link directly renders the intended route or a scoped error, without requiring previous navigation.
2. Reloading the link works under the selected deployment mode. `createWebHistory()` requires server fallback to an SPA shell for client-owned routes; it may need SSR or server checks for proper 404 HTTP semantics.
3. Back/Forward restores the route, query and allowed view context. Route transitions and focus/scroll treatment must remain coherent.
4. Replace transient canonicalization or filter changes only when the spec says no extra history entry is appropriate; avoid replacing users' meaningful navigation steps.
5. Catch unknown routes rather than silently redirecting to Home. Preserve diagnostic context and test reload and navigation history with actual server and browser.
6. If `createWebHashHistory()` is required, document the URL tradeoffs, fragment collision with in-page anchors, and limitations for SEO and direct server processing. It does not prove correct browser History mode.

**Critical environment warning.** The distributed Chromium fixture uses native history entries on an `about:blank#/...` fragment route because the sandbox blocks network and file navigations. It tests *a different deployment history mode*. Server fallback, real path deep-link reloads and HTTP status codes remain NOT EXECUTED, even when the fragment tests pass.

Sources: [MDN Working with the History API](https://developer.mozilla.org/en-US/docs/Web/API/History_API/Working_with_the_History_API), [Vue Router history modes](https://router.vuejs.org/guide/essentials/history-mode), [Vue Router Scroll Behavior](https://router.vuejs.org/guide/advanced/scroll-behavior).
