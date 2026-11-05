# Official Sources, Scientific Papers and Engineering Decisions

> Imported authoring record. Research dates, consultations and execution claims below belong to the supplied source package; they were not reproduced in this adaptation. Referenced validation scripts, fixtures, product norms and logs are not bundled unless present in this folder. Verify their availability and provenance before use. No historical PASS is current acceptance.

**Research checked 2026-11-01.** Live documentation and academic abstracts/publisher records were consulted. URLs are source pointers, not proof a particular Vue Router release or browser environment was tested. Always inspect the installed version before implementation.

## Official specifications and maintained manuals

| ID | Source | Supports |
|---|---|---|
| O01 | [Vue Router 4 Named Routes](https://router.vuejs.org/guide/essentials/named-routes.html) | name, parameter encoding, named `to` |
| O02 | [Vue Router Dynamic Matching](https://router.vuejs.org/guide/essentials/dynamic-matching.html) | params, component reuse, not-found |
| O03 | [Vue Router History Modes](https://router.vuejs.org/guide/essentials/history-mode) | web, hash, memory, server caveats |
| O04 | [Vue Router Navigation Guards](https://router.vuejs.org/guide/advanced/navigation-guards.html) | guard behavior, redirects, cancellation |
| O05 | [Vue Router Navigation Failures](https://router.vuejs.org/guide/advanced/navigation-failures) | async results and failure classes |
| O06 | [Vue Router Meta](https://router.vuejs.org/guide/advanced/meta.html) | route metadata, nonrecursive merge, TS |
| O07 | [Vue Router Typed Routes](https://router.vuejs.org/guide/advanced/typed-routes) | typed route map, version notes |
| O08 | [Vue Router Lazy Routes](https://router.vuejs.org/guide/advanced/lazy-loading.html) | dynamic imports and route chunking |
| O09 | [Vue Router Data Fetching](https://router.vuejs.org/guide/advanced/data-fetching.html) | before/after route data loading |
| O10 | [Vue Router Scroll Behavior](https://router.vuejs.org/guide/advanced/scroll-behavior) | savedPosition, hash scrolling |
| O11 | [Vue Router Dynamic Routing](https://router.vuejs.org/guide/advanced/dynamic-routing) | add/remove routes and replacement |
| O12 | [WHATWG URL Standard](https://url.spec.whatwg.org/) | URL parsing and serialization |
| O13 | [MDN History API](https://developer.mozilla.org/en-US/docs/Web/API/History_API/Working_with_the_History_API) | push/replace/popstate |
| O14 | [MDN URLSearchParams](https://developer.mozilla.org/en-US/docs/Web/API/URLSearchParams) | queries, duplicate values, encoding |
| O15 | [OWASP Unvalidated Redirects](https://cheatsheetseries.owasp.org/cheatsheets/Unvalidated_Redirects_and_Forwards_Cheat_Sheet.html) | open redirect threat and mitigations |
| O16 | [W3C WCAG 2.2](https://www.w3.org/TR/WCAG22/) | page title, focus, navigation, link purpose |
| O17 | [W3C Page Titled](https://www.w3.org/WAI/WCAG22/Understanding/page-titled) | SPA route title changes |
| O18 | [Vue SSR](https://vuejs.org/guide/scaling-up/ssr.html) | per-request SSR context and hydration |
| O19 | [MDN AbortController](https://developer.mozilla.org/en-US/docs/Web/API/AbortController) | request cancellation mechanism |
| O20 | [OWASP Broken Access Control](https://owasp.org/Top10/2021/A01_2021-Broken_Access_Control/) | server enforcement vs UI guard |

## Scientific papers (publisher / repository verified)

- P01 Cockburn & McKenzie 2001, [DOI 10.1006/ijhc.2001.0459](https://doi.org/10.1006/ijhc.2001.0459).
- P02 Cockburn, McKenzie & JasonSmith 2002, [DOI 10.1006/ijhc.2002.1025](https://doi.org/10.1006/ijhc.2002.1025).
- P03 Obendorf et al. 2007, [CHI 2007 archived PDF](https://citeseerx.ist.psu.edu/document?doi=689e25029490d93f646c0d3e4eb42a7642526c42&repid=rep1&type=pdf).
- P04 Weinreich et al. 2008, [DOI 10.1145/1326561.1326566](https://doi.org/10.1145/1326561.1326566).
- P05 Rutishauser & Fritz 2026, [DOI 10.1145/3772318.3791979](https://doi.org/10.1145/3772318.3791979).

See [scientific-evidence-and-limits.md](scientific-evidence-and-limits.md) for interpretation and non-extrapolation.

## Decision/authority ledger

| Decision | Basis | Status and limitation |
|---|---|---|
| Route names and typed params | O01/O02/O07 | Documented router behavior; actual project version untested |
| HTML5 history requires server fallback | O03/O13 | Official caveat; blocked from browser HTTP test locally |
| Explicit async navigation outcomes | O04/O05 | Documented API; synthetic model checked, Vue Router not installed |
| Local redirect validation | O12/O15 | Security practice; server trust/authorization not proven by fixture |
| Route title, focus and keyboard | O16/O17 | Normative accessibility principles, screen reader not tested |
| Back/Forward and revisits matter | P01–P05 | Task-dependent evidence, user outcomes in Ec unmeasured |
| Strict query unknown-key rejection | Synthetic fixture assumption | NOT a general standard or required Ec route convention |

The `MANIFEST.json` for this research distribution is an artifact checksum inventory, **not an Ec application configuration format**. Do not replace the Ec YAML configuration authority.
