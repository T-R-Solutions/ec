# Composable lifecycle, async effects and SSR

> Ec-specific rules below apply only to an Ec consumer whose current contract establishes them. Commands, fixture paths and historical execution claims from the source package are illustrative or imported evidence unless the corresponding files are present and independently checked; they are not current test results or bundled executable tools.

**Scope:** component-scoped effects and composables; app-wide caches, WebSockets, navigation and authentication are separate competencies. Sources [V2, V3, V9, V10, V13].

## Effect ownership table

| Resource | Owner/registration | Disposal/invalidation | Negative test |
|---|---|---|---|
| DOM listener | component `onMounted` | `onUnmounted` remove same listener | no callback after unmount |
| interval/timeout | mounted component or owned scope | clear timer on unmount/scope stop | no repeated work after unmount |
| `watch` with async request | sync setup/watch callback | `onCleanup` and `AbortController`; stale identity guard | earlier A response cannot overwrite newer B |
| observer (Resize/Intersection) | mounted element with null-aware ref | disconnect observer on unmount/target change | listener bound to old node is gone |
| global reactivity outside components | deliberate `effectScope` | `.stop()` | no unbounded stale effects |
| `provide`/`inject` | component/request scope | scope-limited identity | no injection from unrelated context |
| SSR request state | request-scoped creation | request end | separate users never share mutable state |

## API and lifecycle invariants

1. Call active-instance-dependent composables synchronously inside `setup()`/`<script setup>`. Vue compiler preserves context for `<script setup>` top-level await; do not generalize this to ordinary async callbacks.
2. `onMounted` and `onUnmounted` are client lifecycle hooks, not SSR lifecycle. DOM-dependent code must be gated by client mount. Avoid timers / sockets in SSR `setup` without request isolation and cleanup.
3. For request-backed composables choose explicit `Ref`, getter or plain value inputs. `toValue()` normalizes MaybeRefOrGetter; call it inside tracked context when reactivity is required.
4. Cleanup alone does not guarantee a non-cooperative promise stops: add an active/generation guard. Distinguish a rejected operation, cancellation and superseded result in the state contract.
5. Abort any outstanding controller on invalidation *before* starting the replacement; a late reject/resolve must be ignored if obsolete, without suppressing genuine latest errors.
6. Avoid concurrent overlapping non-idempotent mutations in a component without backend request/response identity and policy. Component cancellation cannot undo already-applied server effects.
7. Do not expose entire mutable reactive internals of a composable unless deliberately part of its public contract; prefer read-only projections and actions.

## Tests to request from the consuming repository

- Mount -> effect registered exactly once -> unmount -> registered resource removed exactly once.
- A request starts, then B starts and resolves; A resolves later; view displays B only.
- A rejects after B succeeds; B remains success.
- Unmount during pending request; no state commit or warning occurs after disposal.
- Async loader ignores abort; generation guard still rejects stale completion.
- In SSR, render two requests with different seed data and assert no cross-request leakage.
- Trigger dependency updates and verify watcher callback timing where flush phase matters.

`validation/reference_latest_resource.mjs` exercises a generic async race/cleanup state machine as a **synthetic** model. It is not Vue, SSR or an Ec app; to establish Vue behavior write actual mounted/component tests using official test tooling.
