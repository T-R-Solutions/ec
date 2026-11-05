# Route Splitting, URL Migration and Observability

- Distinguish *route-level lazy loading* (supported by Vue Router dynamic imports) from *performance improvement*. Record actual JS chunks, transfer sizes, route transition latency, browser hardware, cache conditions and Web Vitals only if measured; don't invent speedup percentages.
- Separate errors for DNS/deployment/asset fetch, lazy-import failure, router rejection, 404, API 401/403 and inaccessible route. Provide a real recovery action and never show success for unconfirmed transitions.
- For URL migrations, maintain a versioned old-to-new route table, replacement strategy, canonical links, saved bookmark behavior, server redirects/rewrites and ambiguity handling. Preserve authorization checks and never send a privileged or private URL to an untrusted origin.
- For dynamic `addRoute`/`removeRoute`, changing the registry doesn't automatically replace the currently displayed matched component. Use safe replacement/navigation and protect against repeated redirects. Evaluate route name collision and cleanup on scope/plugin deactivation.
- On route traces redact secret query values; correlate only authorized route identity, navigation type (push/replace/pop), timestamp, outcome, and failure type. Avoid leaking sensitive full URLs to analytics.

Sources: [Vue Router Lazy Loading](https://router.vuejs.org/guide/advanced/lazy-loading.html), [Dynamic Routing](https://router.vuejs.org/guide/advanced/dynamic-routing), [Navigation Failures](https://router.vuejs.org/guide/advanced/navigation-failures.html).
