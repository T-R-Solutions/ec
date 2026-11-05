# Detailed decisions and failure cases

Read only for a decision needing more detail than the entry point. Apply the relevant steps to the
actual task; the sections are not a mandatory artifact or test checklist. Use existing project
contracts in their actual format. File names such as spec.md, plan.md or tasks.md are examples, not
required dependencies. Other skills are optional expertise, never enforcement mechanisms.

Ec references below are conditional consumer examples. They impose no Ec documents, YAML format,
stack or product policy on a generic task.

## Operational procedure

1. **Trace route ownership before writing code.** Inventory every page-level navigation task, route
   identity, shareable query/fragment and non-URL view state. Map resource scope and route
   visibility to the current approved domain specification; do not design new product features.
   Deliver a route-to-REQ/AC table. [Ownership](route-identity-and-boundaries.md).
2. **Define URL round-trip.** Specify canonical pathname, stable param encoding/decoding, query
   duplicate/unknown semantics, omission/default/empty handling, repeated values, URL length/charset
   limits, hash behavior and error outcomes. Test raw input → parsed location → serialized link →
   parsed location for semantic preservation. Never put secrets in URLs. [URL
   contract](url-contracts-and-query-parameters.md).
3. **Choose router architecture based on observed environment.** Identify actual installed Vue
   Router 4 version and decide HTML5 history versus hash versus memory (SSR). Configure named
   routes, nested `RouterView`s, declared `RouteMeta`, optional typed route generation, parameter
   handling, and explicit not-found views. Do not copy Vue Router 3 legacy `next()` code without
   verifying current behavior. [Vue adapter](vue-router-implementation.md),
   [History](history-and-deep-links.md).
4. **Prove reload and browser history.** For `createWebHistory`, test direct HTTP deep-link GET,
   refresh, fallback boundaries, missing-asset 404, browser Back/Forward and query state.
   Distinguish a client-side 404 page from an HTTP 404 response. If SSR, isolate per-request
   router/auth state and test hydration. **Do not mark server fallback verified from a hash-mode
   fixture.** [History](history-and-deep-links.md), [Deployment](server-fallback-and-ssr.md).
5. **Handle guards and redirect results correctly.** Define allow, abort, redirect, error,
   duplicate, cancelled and late async guard outcomes. Await `router.push()` and inspect failure
   objects when completion matters. Use loop-bounded redirects and safe local return paths. Guards
   may improve navigation UX; **backend authorization remains mandatory** for data and actions.
   [Guards](guards-and-navigation-failures.md), [Security](safe-redirects-and-url-security.md).
6. **Fence view loads and scope transitions.** On route-param and principal/scope changes, cancel
   obsolete work where possible and guard view rendering by route identity/generation. Re-evaluate
   stale privileges on revisits, and do not display old protected content during access changes.
   Keep data caching and server authorization delegated to their domain owners.
   [Loads](data-loading-and-race-avoidance.md), [Scopes](scope-transitions-and-permissions.md).
7. **Preserve access and discoverability.** Retain semantic links, meaningful titles and headings,
   current-location indication, keyboard navigation, reliable focus management, skip links, sensible
   Back/Forward scroll restoration and route-specific error feedback. Test with assistive
   technologies for real release approval. Do not use blanket focus resets for query-only updates
   without UX justification. [Access](accessibility-focus-and-scroll.md).
8. **Plan reliability/performance and migrations without inventing gains.** Split route code only
   when appropriate to bundler and measured bundle/load budget; handle chunk fetch failure and
   retry. Preserve old bookmarks via approved migration/redirect policy and verify base path,
   reverse proxy, SSR and dynamic route injection. [Evolution](performance-and-migrations.md).
9. **Implement REQ/AC oracles and evidence.** Execute positive, negative and boundary tests of URL
   serialization, unknown routes, back/forward, reload, redirects, cancel/guard races, identity
   revocation, history replace/push, focus/scroll/title and server-side authorization. Classify pure
   model, browser, actual Vue Router runtime and real app/backend tests separately.
   [Testing](route-testing-and-evidence.md), [Science](scientific-evidence-and-limits.md).
10. **Report observed status, assumptions and blockers.** Record exact commands, code revision/hash,
    browser/dependency versions, failed cases, platform restrictions and tests not executed. Do not
    declare WCAG, security, Vue Router runtime, user outcome or production deployment compliance
    without corresponding evidence. Link to the current spec and leave unresolved decisions with
    their owner. [Official sources](sources-and-decisions.md).


## Further references by decision

Read only the reference relevant to the unresolved decision. Research registers describe imported authorship, not current execution.

- [Route-Change Focus, Titles, Scroll and Keyboard](accessibility-focus-and-scroll.md): accessibility focus and scroll.
- [View Load Lifecycle and Route-Transition Races](data-loading-and-race-avoidance.md): data loading and race avoidance.
- [Navigation Guards, Asynchronous Races and Failure Taxonomy](guards-and-navigation-failures.md): guards and navigation failures.
- [Browser History, Deep Links and Revisit Semantics](history-and-deep-links.md): history and deep links.
- [Route Splitting, URL Migration and Observability](performance-and-migrations.md): performance and migrations.
- [Route Identity, Ownership and Cross-Skill Boundaries](route-identity-and-boundaries.md): route identity and boundaries.
- [Contract Testing, Browser Proof and Evidence Boundaries](route-testing-and-evidence.md): route testing and evidence.
- [Skill-Selection Oracles: Positive, Negative and Borderline](routing-and-selection-oracles.md): routing and selection oracles.
- [URL Safety, Open Redirects and Untrusted Parameters](safe-redirects-and-url-security.md): safe redirects and url security.
- [Peer-reviewed Research: Findings and Limits for Navigation Engineering](scientific-evidence-and-limits.md): source provenance or a disputed recommendation.
- [Scope Transitions and Visibility Boundaries](scope-transitions-and-permissions.md): scope transitions and permissions.
- [Deployment Routing, 404 Semantics and SSR Isolation](server-fallback-and-ssr.md): server fallback and ssr.
- [Official Sources, Scientific Papers and Engineering Decisions](sources-and-decisions.md): source provenance or a disputed recommendation.
- [URL Encoding, Query Contracts and Canonical Links](url-contracts-and-query-parameters.md): url contracts and query parameters.
- [Vue Router 4 with TypeScript: Practical Implementation](vue-router-implementation.md): vue router implementation.
