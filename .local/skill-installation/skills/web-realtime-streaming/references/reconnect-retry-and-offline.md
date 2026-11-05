# Reconnect, Offline, Retry-After, Expired Cursors

> Ec-specific rules below apply only to an Ec consumer whose current contract establishes them.

**Classify before retrying:** network drop, 408/429/503 and selected 5xx may be transient under the approved contract. Unauthorized and forbidden need auth correction or a stopped stream; `410 Gone` can mean replay retention expired and an authorized snapshot is needed if the API defines it. `404` on an unsupported endpoint is not a reason to reconnect indefinitely. The mapping is an example policy, not an Ec product contract.

A bounded exponential backoff with random jitter helps avoid correlated reconnect bursts; cap and initial delay must be chosen from the product/load requirements, not copied from a demo. Prefer server-provided `Retry-After` when defined. For native EventSource, WHATWG owns its reconnect mechanism and `retry:` behavior; avoid externally applying a second loop without suppressing native recovery first.

Separate user-perceived `offline`, `retrying`, `stale data`, `gap detected`, `snapshot recovery`, `authorization required` and `closed` statuses. On browser wake/reconnect, re-evaluate session and subscription scope before resuming; a visible socket is not proof the server is transmitting fresh authorized state. Document how to detect stale last-event times without conflating a quiet but healthy stream with a disconnected one.

**Cancellation gate:** `logout`, context switch, component unmount and unsubscribe invalidate generation and pending reconnect timers. A delayed event from an old scope must not mutate current state. **Recovery gate:** resync requires a trusted, coherent snapshot plus an explicit resume cursor; if the server cannot guarantee one, show partial history/unknown rather than silently reorder records.
