# Contract Testing, Browser Proof and Evidence Boundaries

> Commands, fixture paths and historical execution claims from the source package are illustrative or imported evidence unless the corresponding files are present and independently checked; they are not current test results or bundled executable tools.

Build traceable test oracles from each approved route and navigation REQ/AC. Test **three layers independently**:

1. Pure route contract: URL parsing/serialization, invalid input rejection, scope identity, duplicate and unknown query behavior, deterministic generation, redirect target restrictions, history state model and async transition fencing.
2. Browser integration: real native History API (push/replace/back/forward), anchor semantics, focus and titles, deep-link reload over HTTP with actual deployed server, 404 and asset behaviors, keyboard and responsive state.
3. Real application integration: installed Vue Router version, guards with real identity/session lifecycle, backend resource authorization, SSR if applicable, API denial behavior, configuration persistence where needed and cross-scope security.

**Never combine synthetic fixture passes with production acceptance metrics.** The included Node tests compile a framework-free model; the Chromium suite exercises an `about:blank` history stack with fragment URLs because the execution environment blocks path-mode HTTP/file navigations. Real Vue Router, deployed backend and server-fallback checks are NOT EXECUTED. A passing client guard test does not certify RBAC or backend authorization.

Test at minimum: direct link; page reload; bad resource; query round trip; back/forward; replace/push behavior; browser external/modified click; route update without remount; guard abort/cancel/redirect; async race; identity revocation; unknown route/404; malformed URL/open redirect; focus/title/scroll; slow lazy chunk; login return; SSR isolation. Tag unexecuted cases `NOT VERIFIED` and avoid invented pass counts.

Evidence record: source revision/hash, route table, test ID/REQ/AC, actual command, environment/browser/runtime versions, input URL, expected and observed route, HTTP response, access identity (redacted), screenshots/logs if safe, reason for NOT EXECUTED and any residual risk.

Sources: [Vue Router Navigation Failures](https://router.vuejs.org/guide/advanced/navigation-failures), [MDN History API](https://developer.mozilla.org/en-US/docs/Web/API/History_API/Working_with_the_History_API), [W3C WCAG](https://www.w3.org/TR/WCAG22/).
