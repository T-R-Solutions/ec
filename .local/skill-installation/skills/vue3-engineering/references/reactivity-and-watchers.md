# Reactivity, derivation and watchers (Vue 3)

**Scope:** Vue dependency tracking and component-local state. This is not remote-cache ownership or a domain workflow specification. Base: official Vue docs [V1–V4, V12] in `sources-and-decisions.md`.

## Decision matrix

| Requirement | Appropriate primitive | Check | Common fault |
|---|---|---|---|
| Primitive/reassignable entity | `ref<T>()` | reassign `.value`, template unwrapping | locally copied `.value` loses tracking |
| Cohesive mutable object | `reactive()` | proxy identity retained; use `toRef(s)` on destructure | replacing proxy / destructuring primitives silently disconnects |
| Read-only derived projection | `computed(() => ...)` | dependency change, cache, no side effect | async IO or state mutation in getter; time source not reactive |
| React to a precise source | `watch(source, cb)` | initial call option, invalidation, cleanup | accidental deep tracking / stale async result |
| Automatically track synchronous dependencies | `watchEffect(cb)` | only reads before first `await` tracked | assumes async reads tracked |
| Large immutable or foreign-state root | `shallowRef`/`shallowReactive` | replace root explicitly, profile first | deep writes assumed reactive |
| Integrate untracked external object | `markRaw` if necessary | identity boundary and ownership | accidental proxy wrapping and identity checks |
| Await DOM flush | `await nextTick()` | DOM assertion after a state mutation | assuming synchronous DOM update |

## Watcher and state-specific hazards

1. `reactive` returns a Proxy; original object mutations cannot trigger tracked updates. Nested deep proxies differ in identity from raw values. Do not use `toRaw` as a long-term authority.
2. `ref` deep-converts object values. Refs inside reactive arrays or Map entries are *not* automatically unwrapped. Verify scripts and templates separately.
3. When destructuring `defineProps` in Vue 3.5+, the compiler can rewrite access as `props.x` within `<script setup>`; this rule is version-dependent and is **not** a generic JS destructuring behavior.
4. Computed values are cached by reactive dependencies. `Date.now()` is not a dependency; a clock requires explicit reactive ticks from an owner that also cleans up the timer.
5. `watchEffect` tracks dependencies accessed **synchronously**, before its first async suspension. Use explicit `watch` for clearer external requests and cancellation.
6. `watch` default timing batches callbacks. `flush: 'post'` can observe DOM after component updates; `flush: 'sync'` has different cost/timing and should be justified with actual semantics. Do not assume event ordering without tested flush phase.
7. Register cleanup with the callback's `onCleanup` argument. `onWatcherCleanup` exists only in 3.5+ and must be called synchronously, not after `await`. Cleanup invalidates previous work; if a promise ignores abort, additionally guard its completion with `requestId` or active flag.
8. Watchers created synchronously in setup are tied to component lifecycle; watchers created asynchronously may not have the same automatic disposal association. Store and stop returned handles where needed. Effects created outside a component need explicit `effectScope`/`onScopeDispose` management.
9. Avoid using `watch` to copy props into a second independent config store. If the component edits a controlled value, define an explicit draft and commit boundary, or use a typed emit/v-model protocol.

## Required negative checks

- Mutating a raw object does not update a proxy observer as intended: test proxy-only mutation in the code under test.
- An async response started for A after B became active must not update B's view, even if A ignores `AbortSignal`.
- A stopped/unmounted watcher must not commit late results.
- Mutating a computed return object must not become the canonical update path.
- `shallowRef` nested mutation alone must not be assumed to update a consumer.
- A `ref` nested in an array must be unwrapped deliberately when used in JavaScript.

## Example: observable late response prevention

```ts
import { ref, watch, type Ref } from 'vue'

export function useLatest<T>(key: Ref<string>, load: (key: string, signal: AbortSignal) => Promise<T>) {
  const value = ref<T | null>(null) as Ref<T | null>
  const pending = ref(false)
  const failure = ref<unknown>(null)
  watch(key, (currentKey, _prev, onCleanup) => {
    const controller = new AbortController()
    let active = true
    onCleanup(() => { active = false; controller.abort() })
    pending.value = true
    failure.value = null
    void load(currentKey, controller.signal).then(
      result => { if (active) { value.value = result; pending.value = false } },
      cause => { if (active) { failure.value = cause; pending.value = false } }
    )
  }, { immediate: true })
  return { value, pending, failure }
}
```

**Limit:** Example assumes the composable runs synchronously within an active component scope and the caller supplies a trusted loader; it is not a cache, transport retry policy or product state machine. In an SSR app, request-side state isolation requires separate review. Example code is illustrative and requires compiling against the installed Vue version.
