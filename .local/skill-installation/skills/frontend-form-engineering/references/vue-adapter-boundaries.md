# Vue 3 adapter boundaries — consume, don't replace, generic form semantics

> Ec-specific rules below apply only to an Ec consumer whose current contract establishes them. Commands, fixture paths and historical execution claims from the source package are illustrative or imported evidence unless the corresponding files are present and independently checked; they are not current test results or bundled executable tools.

This generic skill is framework-agnostic. The Ec frontend uses Vue + TypeScript, so an adapter may consume `vue3-engineering` patterns when the feature calls for it. This file does **not** establish a Vue version, component library, form plugin requirement or UI feature implementation.

## Vue form binding facts

- The official Vue 3 guide documents `v-model` on inputs, checkboxes, radio buttons, selects and components. It maps text/textarea to `value`/`input`, and checkboxes/radios/selects to `checked`/`change` or appropriate selected-value semantics.
- `v-model` treats reactive JavaScript state as its current source of truth; DOM `value`/`checked` initial attributes are not the correct place to set a controlled form's persistent draft baseline.
- `.lazy` changes input-to-state update timing; `.number` uses a conversion that may leave input as a string; `.trim` changes lexical values. Do not introduce modifiers where they alter domain semantics.
- Vue documents that regular `v-model` does not update during active IME composition; do not force per-keystroke validation with dangerous normalization during composition.
- Repeated form rows require stable meaningful keys; component remounting must not discard edits by accident.

## Mapping generic state to framework

```text
spec fields + revision
  -> typed initial values and baseline
  -> Vue reactive draft (projection only)
  -> validator outputs structured keyed issues
  -> submission snapshot + API adapter
  -> authoritative ACK/error/revision
  -> confirmed baseline and retained edits
```

Use narrow reusable field components exposing explicit value/change/error semantics. For complex forms, keep validation/business logic in pure functions to test outside rendering; verify reactive watchers are disposed and stale async results cannot alter new values. Use `readonly` or immutable server snapshots where possible, but copying snapshots and runtime validation remains necessary at trust boundaries.

## Not the owner of

`vue3-engineering`: component lifecycle/composables/renderer mechanics.
`typescript-engineering`: generic static typing and tsconfig.
`frontend-state-and-cache`: global remote cache/invalidation policies.
`web-accessibility`: exhaustive WCAG/assistive technology verification.
`ec-semantic-configuration-projections`: YAML round-trip and unknown-field preservation at product level.

## Integration acceptance

After a Vue implementation exists, test with the project's actual Vue version/build/test dependencies, `vue-tsc` when configured, browser event order, IME behavior, focus/keyboard and real form-to-server acknowledgement. Synthetic HTML/TypeScript fixture tests bundled here **do not** prove a Vue implementation.
