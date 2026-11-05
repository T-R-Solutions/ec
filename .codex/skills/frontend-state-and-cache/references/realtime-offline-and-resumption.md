# Realtime events, offline and resumption

## Treat the event stream as a source of observations

SSE, WebSocket or polling can deliver data out of order, duplicate it or disconnect. This skill addresses how a client cache *consumes* authoritative events; transport implementation and streaming buffers are owned by `web-realtime-streaming`. Do not invent resumable cursor semantics, global order or exactly-once delivery.

When resource revisions are monotonic and event provenance is validated, update only matching scoped cache entries. Ignore old/duplicate updates, invalidate on known sequence gaps and refetch the authoritative snapshot after reconnection or cursor expiration. If the event contract has no ordering guarantee, mark stale and refetch instead of pretending direct event application is safe.

Offline behavior must be explicit: show the last-known value with age and origin if allowed, preserve an error/offline indicator and disable unsafe privileged actions. An offline-enabled editor needs a separate written merge, security and replay contract; local-first CRDT literature is not permission to implement unsanctioned offline mutation queues.

## Failure drill

- Disconnect while event 21 is pending; reconnect at event 25; detect gap if the contract defines contiguous event sequence.
- Revoke a role while connection is open; clear caches and force server reauthorization.
- Receive duplicate event, stale sequence or event from a different tenant; do not mix or promote it.
- Reconnect a tab after inactivity; verify an explicit revalidation path and that stale values are not shown as live.
- Confirm the observable UI never labels an agent/build "connected" or "successful" merely because an old cache entry exists.

References: [Fetch](https://developer.mozilla.org/en-US/docs/Web/API/Fetch_API/Using_Fetch), [TanStack refetch/caching behavior](https://tanstack.com/query/latest/docs/framework/vue/guides/caching); academic context: Terry et al. (1994), Kleppmann et al. (2019) in [scientific evidence](scientific-evidence-and-limits.md).
