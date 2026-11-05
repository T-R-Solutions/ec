# Rendering, performance, toolchain and test evidence

> Ec-specific rules below apply only to an Ec consumer whose current contract establishes them. Commands, fixture paths and historical execution claims from the source package are illustrative or imported evidence unless the corresponding files are present and independently checked; they are not current test results or bundled executable tools.

**Scope:** optimizing/reviewing Vue component rendering based on measured hot spots and verifying Vue-facing behavior. Source [V4, V11, V12, V15–V17] and research [P2, P3].

## Sequence: measure, change, remeasure

1. Fix target device/browser, app revision, dataset, operation, Vue version and representative workload; use production-like build, not only dev HMR.
2. Separate initial load metrics (LCP/INP when relevant, JS assets) from update latency (large table/filter/log append/DAG interactions). Record profiler traces/median distributions when measured; do not invent targets.
3. Inspect render trees and dependency updates. Before adding memoization, prefer stable child props; check list keys preserve identity through reorder. Measure virtual scrolling for long lists and focus/screen-reader consequences.
4. Use lazy components/routes for cold areas, verify preloading priorities and error boundaries. Avoid `v-once` for content that must reflect newer state; `v-memo` is conditional and can hide legitimate updates if dependencies are incomplete.
5. For large immutable payloads, evaluate `shallowRef` root replacement and avoid invisible nested writes; `markRaw` only for foreign objects or measured necessity.
6. Inspect skipped/stale render impacts (labels, status updates, result count, keyboard focus). Only optimize once semantics are proven unchanged.

## Test layers and what they prove

| Test | Can support | Does not prove |
|---|---|---|
| `vue-tsc --noEmit` | Vue SFC+TS typing compatibility for installed toolchain | runtime correctness, security or a11y |
| `vite build` or project build | build graph/transforms pass in chosen environment | TS semantic accuracy if separate checker absent |
| Vue Test Utils component tests + Vitest | mounted props/events/slots/lifecycle and tested DOM effects | complete assistive compatibility or backend authorization |
| Browser E2E/keyboard test | specific user journeys/DOM/focus in tested browser | unseen browsers or exact business semantics |
| Profiling/benchmark with raw artifacts | observations for tested workload and environment | universal superiority over other frameworks |
| Standalone reference-state model | algorithm/example behavior in isolation | Vue runtime, SFC compiler or production Ec behavior |

### Correct test behavior

- Prefer assertions against accessible output, observed emitted events and user interactions over implementation internals.
- Use `await nextTick()` for Vue-scheduled DOM mutations and wait for pending promises with the testing framework's documented helpers when necessary.
- Keep input fixtures separated from expected outputs; document intentional faults that would be detected; use precise negative scenarios.
- Reset mocks/fake timers between tests and never allow a skipped integration test to turn an acceptance criterion green.
- Typecheck and component-test the real installed Vue version; a copied sample or a synthetic reference model does not certify the Ec application.

## Scientific evidence interpretation

The ACM reactive programming survey [P1] is a taxonomy and conceptual foundation; it does **not** benchmark Vue. The IEEE test oracle survey [P2] supports independent expected results but does not validate this package's examples. The ICSE'14 study [P3] found low-to-moderate relationship between coverage and fault detection in sampled **Java programs** after controlling for suite size: it is a reason to track fault detection and failure oracles instead of claiming 'high coverage = high confidence'; it is not a direct estimate for Vue/TypeScript.
