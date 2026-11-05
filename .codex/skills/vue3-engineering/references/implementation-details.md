# Detailed decisions and failure cases

Read only for a decision needing more detail than the entry point. Apply the relevant steps to the
actual task; the sections are not a mandatory artifact or test checklist. Use existing project
contracts in their actual format. File names such as spec.md, plan.md or tasks.md are examples, not
required dependencies. Other skills are optional expertise, never enforcement mechanisms.

Ec references below are conditional consumer examples. They impose no Ec documents, YAML format,
stack or product policy on a generic task.

## Implementation method

1. **Inspect reality and select the route.** Locate repository instructions, active project workflow
   feature or local-change scope, Vue/runtime/compiler and TypeScript versions, framework mode (SPA,
   SSR, SSG), test setup, actual component consumers, user-visible states and authorization
   boundaries. Use the destination project's actual requirements. Separate implemented code from
   proposed capability. Select local vs project workflow route using the existing project workflow
   workflow, not arbitrary documentation volume.
2. **Specify the Vue component contract.** Identify props and mutability, emitted events and
   payloads, slots, `v-model` identity, rendering/empty/error states, loading/confirmation
   semantics, lifecycle ownership, accessibility interface and external dependencies. Prefer
   controlled props-down/events-up boundaries. Do not mutate props or use an internal ref as a
   second source of truth for a controlled value. For version-sensitive macros (`defineModel` 3.4+,
   reactive props destructure enabled by default in 3.5+, `onWatcherCleanup` 3.5+), check installed
   versions and supply version-appropriate alternatives. See [component and TypeScript
   contracts](sfc-and-typed-component-contracts.md).
3. **Choose the correct reactive primitive.** Prefer `ref` for replaceable values and
   component-local primitives; use `reactive` for cohesive object state where proxy identity is
   retained; use `toRef`/`toRefs` for destructuring reactive object fields when needed. Use
   `computed` for deterministic derived state, without IO, mutation or time-dependent hidden inputs.
   Use `watch`/`watchEffect` only for effects with understood dependencies and flush timing. Use
   `shallowRef`/`markRaw` selectively for large immutable or externally owned data, with root
   replacement and measured justification. Beware ref unwrapping exceptions in arrays/collections.
   See [reactivity](reactivity-and-watchers.md).
4. **Design side-effect ownership and cancellation.** Encapsulate reusable component logic in
   appropriately named composables; call lifecycle-dependent composables synchronously during
   `setup` or `<script setup>` (except documented compiler-preserved top-level-await context).
   Register and clean up listeners, timers, observers, fetches and watchers. Register watcher
   invalidation *before* asynchronous boundaries, abort obsolete fetch work with `AbortController`,
   and guard against late non-cooperative responses with an identity/generation check. Never render
   a stale earlier response as the latest. If SSR applies, avoid cross-request mutable singletons
   and DOM use before mount. See [composable lifecycle](composables-lifecycle-and-ssr.md).
5. **Implement SFCs with explicit, tested semantics.** Prefer `<script setup lang="ts">` and typed
   `defineProps`/`defineEmits`; keep data contracts explicit, narrow unknown data before rendering,
   handle `null` template refs and pending async states. Do not mistake Vite transpilation for type
   checking: use `vue-tsc --noEmit` and actual build/test commands. `defineModel` expands to a
   prop/event pair: check parent-child controlled synchronization, especially defaults. Never treat
   Vue template escaping as permission to compile untrusted templates, render unsanitized HTML, or
   trust arbitrary URLs. See [SFC contracts](sfc-and-typed-component-contracts.md) and [security
   boundaries](security-accessibility-and-product-boundaries.md).
6. **Preserve faithful UI and product authority.** Represent pending, error, empty, success and
   denied states based on real evidence; use semantic HTML, labeled controls, keyboard access and
   stable focus. These are integration checkpoints, *not* substitutes for dedicated accessibility
   testing or a design system. In the Ec application, the Visual, Form and YAML editors must project
   the same canonical configuration; this skill implements components only and delegates semantic
   equivalence to `ec-semantic-configuration-projections` and publication to
   `ec-configuration-publication`. Do not infer backend authorization or persisted success from
   local component state.
7. **Prove correctness before tuning performance.** Test public component contracts, emitted
   payloads, DOM states and lifecycle cleanup; test stale responses, prop changes, unmount, error
   and cancellation. Verify Vue/SFC compile, strict `vue-tsc` checking, relevant Vue Test
   Utils/Vitest tests, and browser keyboard/focus behavior when required. For expensive
   graphs/logs/lists, profile first; then evaluate stable props, list virtualization, route-level
   splitting, shallow immutable state, `v-memo` or render scopes with representative data. Document
   before/after environment and measurements; do not invent benchmarks. See [rendering and
   measurement](rendering-performance-and-testing.md).
8. **Return bounded, reproducible evidence.** Record files/versions, decisions, owners, Vue and
   TypeScript commands, observed diagnostics, unit/component/browser evidence, targeted acceptance
   criterias and unresolved integration. Distinguish compiler checks, synthetic examples, Vue
   runtime tests, actual application tests, WCAG review and measured performance. Report absence of
   Vue packages, browser environment or Ec checkout as **NOT EXECUTED**, not success; fix failed
   tests rather than weakening an AC.


## Further references by decision

Read only the reference relevant to the unresolved decision. Research registers describe imported authorship, not current execution.

- [Composable lifecycle, async effects and SSR](composables-lifecycle-and-ssr.md): composables lifecycle and ssr.
- [Reactivity, derivation and watchers (Vue 3)](reactivity-and-watchers.md): reactivity and watchers.
- [Rendering, performance, toolchain and test evidence](rendering-performance-and-testing.md): rendering performance and testing.
- [Activation routing, negative/boundary selection and integration cases](routing-and-selection-oracles.md): routing and selection oracles.
- [Security, accessibility and product responsibility boundaries](security-accessibility-and-product-boundaries.md): security accessibility and product boundaries.
- [SFC and typed component contracts](sfc-and-typed-component-contracts.md): sfc and typed component contracts.
- [Source register, evaluated claims, research limits and choices](sources-and-decisions.md): source provenance or a disputed recommendation.
