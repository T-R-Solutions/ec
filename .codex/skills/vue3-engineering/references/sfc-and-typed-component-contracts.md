# SFC and typed component contracts

**Scope:** Vue 3 SFC/compiler semantics, typed props/emits/`v-model` and component-local integration. For generic strict TS architecture use `typescript-engineering`; for form validation requirements use `frontend-form-engineering`. Sources [V5–V8, V14] in `sources-and-decisions.md`.

## Component contract checklist

- Use `<script setup lang="ts">` for new Composition API SFCs when consistent with the app. Compiler macros such as `defineProps`, `defineEmits`, `defineModel` are compiled away; **do not import them**.
- Props are parent-owned. Treat them as read-only; a locally editable draft must be explicit and have a defined commit/cancel/rebase contract.
- Declare events with typed named tuples (Vue 3.3+) or compatible call signatures; confirm event names, payload and timing with parent. Declaring emits removes known listeners from fallthrough attrs; do not accidentally combine native and component event handling.
- Component events **do not bubble** through arbitrary ancestors. Use explicit prop/event forwarding, provide/inject with bounded scope or a documented external event solution when appropriate.
- Prefer explicit slot contracts for extensibility; verify slot presence and fallback with the actual Vue compiler/typechecker. Avoid hidden cross-component mutation through slot props.
- Check fallthrough attrs with multi-root components and explicit `inheritAttrs: false` when necessary; do not permit accidental event/attribute attachment to an unrelated DOM node.
- `defineModel` requires Vue 3.4+; it maps to `modelValue` and `update:modelValue` or the argument-specific pair. A child model default when a parent is `undefined` can cause de-synchronization. Treat that as a negative integration scenario.
- Reactive props destructure default behavior is version-gated (3.5+). For 3.4 and below, use `withDefaults` and `props.x`/`toRefs` as appropriate. Mutable defaults supplied by `withDefaults` need factories.
- Use `useTemplateRef` only when version supported (3.5+); template refs are initially absent and can become null when conditionally unmounted. Never assert they exist without a runtime guard.
- Do not confuse TypeScript's static types with runtime validation for untrusted transport data. Validate backend payloads at trust boundaries using the product's chosen contract.
- Vite/esbuild transpiles TS without project-wide type checks. Run `vue-tsc --noEmit` and bundler build independently; linting and unit tests are separate checks.

## Example — typed controlled component

```vue
<script setup lang="ts">
interface Props { label: string; operationId: string; pending?: boolean }
const props = withDefaults(defineProps<Props>(), { pending: false })
const emit = defineEmits<{ retry: [operationId: string] }>()
function requestRetry(): void {
  if (!props.pending) emit('retry', props.operationId)
}
</script>

<template>
  <button type="button" :disabled="pending" @click="requestRetry">
    {{ label }}
  </button>
</template>
```

This emits a **request** and does not infer successful completion. Backend authority, eligibility and idempotency belong to the feature contract. Accessibility details beyond this example must be checked with the dedicated specialist.

## Failing examples (should be rejected)

1. `props.pending = true` mutates parent-owned input.
2. `const local = ref(props.modelValue)` without reconciliation creates an unintentional divergent source.
3. `defineModel({default: true})` with undefined parent is assumed synchronized.
4. An emit is treated as final confirmation of a backend mutation.
5. A `script setup` macro is treated as a runtime function available in arbitrary modules.
6. `vite build` alone is reported as successful Vue type checking.

## Version gate

Inspect `package.json`, resolved lockfile and actual installed versions; choose alternatives for Vue <3.5. Do not accept the version reported by a transitive dependency instead of the renderer package actually loaded by the app. Vue 3.6 release candidates are not presumed stable.
