# Source register, evaluated claims, research limits and choices

> Imported authoring record. Research dates, consultations and execution claims below belong to the supplied source package; they were not reproduced in this adaptation. Referenced validation scripts, fixtures, product norms and logs are not bundled unless present in this folder. Verify their availability and provenance before use. No historical PASS is current acceptance.

**Research performed:** 2026-10-31. **Verification priority:** official Vue/TypeScript/W3C documentation for APIs and normative behavior; peer-reviewed ACM/IEEE studies for general scientific concepts and test methodology; actual repository/benchmarks for application-specific assertions. All external content is consulted as evidence, not as authority to override local project rules.

## Official documentation accessed

| ID | Maintainer / page | URL | Relevant, independently documented behavior |
|---|---|---|---|
| V1 | Vue — Reactivity Fundamentals | https://vuejs.org/guide/essentials/reactivity-fundamentals | refs, proxies, destructuring caveats, nextTick and DOM batching |
| V2 | Vue — Watchers | https://vuejs.org/guide/essentials/watchers | watch vs watchEffect, cleanup, timing and sync constraints |
| V3 | Vue — Composables | https://vuejs.org/guide/reusability/composables | `use*` convention, input normalization, setup and cleanup |
| V4 | Vue — Computed | https://vuejs.org/guide/essentials/computed | reactive dependency caching; getters without side effects |
| V5 | Vue — SFC script setup | https://vuejs.org/api/sfc-script-setup | compile-time macros, prop destructure and compile assumptions |
| V6 | Vue — TypeScript with Composition API | https://vuejs.org/guide/typescript/composition-api | type-based props/emits, template refs, version limitations |
| V7 | Vue — Component Events | https://vuejs.org/guide/components/events | emits declaration; events do not bubble |
| V8 | Vue — Component v-model | https://vuejs.org/guide/components/v-model | `defineModel` 3.4+, prop/event translation, default de-sync warning |
| V9 | Vue — Lifecycle Hooks | https://vuejs.org/guide/essentials/lifecycle | onMounted/onUnmounted and synchronous registration |
| V10 | Vue — Server-Side Rendering | https://vuejs.org/guide/scaling-up/ssr | SSR lifecycle limits, client DOM and cross-request state |
| V11 | Vue — Performance | https://vuejs.org/guide/best-practices/performance | profiling, stable props, lazy loading, virtualization, shallow refs |
| V12 | Vue — Testing | https://vuejs.org/guide/scaling-up/testing | test layers, component contract focus |
| V13 | Vue — Reactivity API: Core | https://vuejs.org/api/reactivity-core | watcher timing and `onWatcherCleanup` constraint |
| V14 | Vue — Using Vue with TypeScript | https://vuejs.org/guide/typescript/overview | Vite transpilation does not typecheck; vue-tsc CLI |
| V15 | Vue Test Utils — Asynchronous behavior | https://test-utils.vuejs.org/guide/advanced/async-suspense | nextTick vs flushPromises; await triggers; async setup/Suspense |
| V16 | Vitest — Mocking | https://vitest.dev/guide/mocking.html | restore fake/mock state; avoid contaminated results |
| V17 | Vitest — Timers | https://vitest.dev/guide/mocking/timers | fake timer usage and cleanup |
| V18 | Vue — Security | https://vuejs.org/guide/best-practices/security | unsafe user templates/HTML/URLs and rendering boundaries |
| V19 | Vue — Accessibility | https://vuejs.org/guide/best-practices/accessibility | semantics, navigation, skip links |
| T1 | TypeScript — strict | https://www.typescriptlang.org/tsconfig/strict | static check family and version-sensitive diagnostics |
| T2 | TypeScript — exactOptionalPropertyTypes | https://www.typescriptlang.org/tsconfig/exactOptionalPropertyTypes.html | optional property absence vs explicit undefined |
| W1 | W3C — WCAG 2.2 | https://www.w3.org/TR/WCAG22/ | testable success criteria; not a claim of achieved conformance |
| R1 | Vue.js core — releases | https://github.com/vuejs/core/releases | latest stable v3.5.44 visible, v3.6 RC still prerelease at research time |

**Version note:** The research snapshot shows 3.5.44 as latest stable (2026-10-31). Skills are **not pinned** to that version: inspect the consuming repo. Some APIs are older; use version-specific fallbacks. A version page is not evidence that the target project has that version installed.

## Peer-reviewed and academically grounded sources consulted

| ID | Publication and DOI | Type of evidence | Supports | Does *not* support |
|---|---|---|---|---|
| P1 | E. Bainomugisha, A. L. Carreton, T. van Cutsem, S. Mostinckx, W. de Meuter, **A Survey on Reactive Programming**, *ACM Computing Surveys* 45(4), Article 52 (2013), DOI: https://doi.org/10.1145/2501654.2501666 | Refereed survey; publisher abstract and bibliographic page checked | Reactive dependency propagation is a well-studied design space with explicit tradeoffs | Vue 3 implementation correctness, faster runtime or lower memory than alternatives |
| P2 | E. T. Barr, M. Harman, P. McMinn, M. Shahbaz, S. Yoo, **The Oracle Problem in Software Testing: A Survey**, *IEEE Transactions on Software Engineering* 41(5), 507–525 (2015), DOI: https://doi.org/10.1109/TSE.2014.2372785 | Peer-reviewed survey; IEEE/UCL abstracts checked | Need independent/defined test oracles; metamorphic/specification-driven methods are options | Certification that a project's tests contain good oracles merely by existing |
| P3 | L. Inozemtseva, R. Holmes, **Coverage Is Not Strongly Correlated with Test Suite Effectiveness**, *ICSE 2014*, 435–445, DOI: https://doi.org/10.1145/2568225.2568271 | Peer-reviewed empirical study; conference listing and author manuscript abstract checked | In studied Java systems, coverage alone was a weak/moderate predictor after accounting for test suite size | Quantitative generalization of its numbers to Vue/TypeScript, UI, SSR or Ec |

**Access-depth caveat:** The accessible publisher/conference abstracts and bibliographic pages were verified; this package does not assert a full-paper systematic review, reproduction of published experiments or new peer-reviewed findings. Scientific sources inform the methodology **only**. The official Vue docs are the authoritative reference for the exact Vue API descriptions, not those academic papers. No framework performance benchmark or Ec app test was executed.

## Engineering decisions and rejected shortcuts

| Decision | Evidence | Rationale and limitation |
|---|---|---|
| Prefer `ref` and controlled props for replaceable state | V1, V5–V8 | Limits aliasing and accidental shadow copies; does not replace project stores |
| Make async invalidation observable | V2, V3, V13; P1 conceptual | Abort + identity guard for non-cooperative work; backend effects remain outside component |
| Separate computed derivation from effects | V4 | Cached getters should be pure; IO belongs to explicit effect owner |
| Treat `vue-tsc` as independent from Vite build | V14, T1 | Build alone is not semantic typing; lockfile/tool versions must be verified |
| Reject untrusted Vue templates and unsanitized HTML | V18 | Escape applies to interpolation, not arbitrary executable templates |
| Measure before `v-memo`/virtualization | V11; P3 only testing caution | No unmeasured performance claim or universal threshold |
| Prefer user-facing component assertions | V12, V15; P2 | Tests should detect wrong output/events, not only implementation detail |
| Separate synthetic and installed-Vue results | P2, P3 | A reference simulation cannot prove Vue renderer or application behavior |
| Leave SDD and Ec constraints outside generic skill | Ec catalog / norms | Generic competence stays reusable and has no competing policy ownership |

## Conflict and missing evidence

- No conflict was identified between these generic Vue techniques and the two supplied Ec norms; the norms govern actual Ec usage.
- The original research used JSON configuration and deferred UI, while the current Ec norms replace both decisions. This skill deliberately does not endorse the historical assumptions.
- No Ec repository, browser runner, actual Vue dependency installation, released YAML schema, production metrics or real end-to-end results were supplied. They remain **not verified**.
- Network package installation was unavailable in the validation environment; therefore no actual Vue SFC compilation or runtime assertion is claimed in this package.

**Evidence classification:** Real Vue runtime, Ec application behavior and agent selection are NOT VERIFIED; installed Vue and Ec integration testing were NOT EXECUTED in this package. These limitations apply despite successful structural and synthetic checks.

**Non-transferability:** The cited academic evidence is not a direct benchmark of Vue, nor a direct experiment on the Ec codebase.
