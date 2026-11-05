# Vue Lifecycle, Client Connection Scope and SSR

A stream connection is a long-lived, resource-consuming side effect. In Vue 3, bind it to the smallest legitimate lifetime: a component `onMounted()` or service-level subscriber manager with explicit acquire/release. `onUnmounted()` closes sockets and EventSource, clears timers and listeners. Do not create live browser connections at module evaluation time or while rendering on the server; the Vue official lifecycle documentation notes that `onUnmounted` does not run during SSR.

Avoid a socket per repeated row/component. Prefer a single connection per authorized scope where a product contract permits multiplexing, using reference-counted subscriptions to avoid duplicate transport. Keep auth/session and resource scope explicit and treat new route parameters as a new generation; discard late events from the previous generation. If multiple tabs use separate connections, account for origin connection limits and service quotas, but do not assume BroadcastChannel authorization or cross-tab sharing without a reviewed model.

**UI contract:** status must distinguish `connecting`, `live`, `retrying`, `stale`, `gap/snapshot-required`, `reauth`, `disconnected`, and `unknown` where relevant. Live state cannot be inferred from last displayed green badge after transport loss. Log panels require bounded virtualization and accessible updates; don't announce every high-frequency log line via ARIA live region.

**Example responsibility boundary:** this skill defines connection lifetime and event application. `vue3-engineering` owns SFC/reactivity implementation; `frontend-state-and-cache` owns generic query caches; `frontend-testing` owns E2E harness mechanics; the domain spec owns what a build's status actually means.
