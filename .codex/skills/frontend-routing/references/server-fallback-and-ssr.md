# Deployment Routing, 404 Semantics and SSR Isolation

A browser-route path is not necessarily an API or static file path. In **HTML5 history mode**, the web server needs rules to serve the SPA shell for known client routes when receiving a direct HTTP GET. Serve actual assets, API paths, and security-sensitive server endpoints through their proper routes; an unconditional catch-all may mask genuine errors and cause incorrect HTTP 200 for missing pages.

Vue Router's history-mode guide states that a blanket fallback to `index.html` means the server will not naturally report 404 for unknown client URLs. A client-side catch-all shows a not-found view; when correct HTTP statuses are required (e.g., SSR, crawlers or monitoring), decide server-side routing or SSR behavior explicitly and test it.

When SSR is enabled, instantiate a per-request router and use `createMemoryHistory()` as described by Vue Router. Memory history does not automatically start a navigation; push the initial route and await readiness. Do not share per-user route/auth state between requests, or assume the browser History API exists during server rendering.

Acceptance evidence requires real HTTP reload of deep links; server return codes; missing asset 404; unknown SPA path behavior; route-prefetch failures; SSR cross-request isolation; base path/reverse proxy deployment; and behavior behind authentication middleware.

Sources: [Vue Router History Modes](https://router.vuejs.org/guide/essentials/history-mode), [Vue Server Side Rendering Guide](https://vuejs.org/guide/scaling-up/ssr.html), [MDN Working with History API](https://developer.mozilla.org/en-US/docs/Web/API/History_API/Working_with_the_History_API).
