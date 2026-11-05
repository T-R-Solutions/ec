# Route-Change Focus, Titles, Scroll and Keyboard

In a SPA, a new view may appear without a new document load. Keep the user oriented: update a descriptive document title, expose a page heading and appropriate landmarks, provide a bypass mechanism for repeated navigation, and restore/move focus in a predictable order when a route changes. Avoid forcing focus or resetting scroll while the user is reading, or on every query-only change without a reason.

**WCAG 2.2** includes requirements for page titles (2.4.2), focus order (2.4.3), link purpose (2.4.4), bypass blocks (2.4.1) and focus visibility (2.4.7), among others. W3C guidance explicitly discusses dynamic titles for SPA view changes. Meeting these criteria needs more than adding `tabindex` to a heading; assess actual keyboard path and assistive technology behavior.

For `<RouterLink>`, retain real anchor semantics for browser context menu, copy link, modified clicks and keyboard operation. A router interception must not incorrectly capture external links or block Ctrl/Cmd-click. Restore `savedPosition` for back/forward when appropriate and handle hash targets and reduced-motion preferences. In hash routing, URL routing hashes and in-page anchor hashes can conflict and require a documented alternative.

Tests: keyboard navigation, heading/title update after route, skip link, browser Back and Forward, scroll on popstate, focus after cancel/redirect, viewport reflow, actual screen reader test with declared browser/AT matrix.

Sources: [W3C WCAG 2.2](https://www.w3.org/TR/WCAG22/), [W3C Page Titled](https://www.w3.org/WAI/WCAG22/Understanding/page-titled), [Vue Router Scroll Behavior](https://router.vuejs.org/guide/advanced/scroll-behavior).
