---
name: vue3-engineering
description: Implement or review Vue 3 components and composables, reactivity, typed bindings and lifecycle cleanup. Skip standalone TypeScript, routing and remote-cache policy without a Vue-specific issue.
---

# Vue 3 Engineering

## Scope and inputs

Inspect Vue/compiler versions, SFC consumers, SPA/SSR mode, state ownership and existing build/test
commands. Verify macros and cleanup APIs against installed versions.

Preserve the consumer's stack and policies. Product-specific examples in references do not establish
requirements for this task.

## Procedure

1. Define props, emits, slots and model bindings; preserve parent-controlled state and avoid shadow
   copies or prop mutation.
2. Choose refs/reactive/computed by identity and derivation needs. Keep computed getters free of
   side effects and make watcher dependencies and timing explicit.
3. Own listeners, timers, watchers and fetches in the component/composable lifecycle. Register
   cleanup in time and fence late responses even when abort is ineffective.
4. Keep SSR state isolated by request and DOM work in the appropriate lifecycle. Do not compile
   untrusted templates or mistake interpolation escaping for arbitrary HTML safety.
5. Check component outputs, prop changes, stale responses, unmount and errors. Run actual SFC type
   checking separately from transpilation; profile before optimizing renders.

## Load detail when needed

- [Reactivity, derivation and watchers (Vue 3)](references/reactivity-and-watchers.md): consult for
  reactivity and watchers.
- [SFC and typed component contracts](references/sfc-and-typed-component-contracts.md): consult for
  sfc and typed component contracts.
- [Composable lifecycle, async effects and SSR](references/composables-lifecycle-and-ssr.md):
  consult for composables lifecycle and ssr.
- [Detailed decisions and failure cases](references/implementation-details.md): consult for a
  complex case, additional constraints or the remaining thematic references; apply only relevant
  steps.

## Completion and limits

Complete the requested change or diagnosis and the checks that support its claims. Report the
affected contract, observed results, actual versions/commands and material gaps. Keep structural
review, simulated cases, real integration and production evidence distinct. Imported research and
execution records are historical context, not results of the current task.

This folder works without SDD or other skills. Consult an available specialist only for a concrete
unresolved question; its absence is a gap only when essential to acceptance. Use the project's
existing record for a bounded change. This method grants no external access, publication or
destructive-action authority.
