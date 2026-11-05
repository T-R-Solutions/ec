# Realtime Observability: Signals, Not Decorative Status

Monitor real stream state: connection established, reconnect attempts by reason, replay cursor age, last trustworthy event time, queue depth (events/bytes), discarded ephemeral updates, critical overflow, unauthorized subscription failures, snapshot recovery and duration, downstream render budget, and version/schema incompatibility. Each metric needs source, unit, time window, scope and update timestamp. Avoid tracking full payloads, credentials, personally identifying data or raw auth headers.

**Status semantics:** `open` is a transport condition; `synced` requires authoritative watermark or snapshot contract; `stale` means a feature-specific freshness bound was exceeded or known gap occurred; `unknown` is appropriate when information is missing. A quiet healthy stream with infrequent events cannot be declared disconnected merely because a hard-coded idle timer fired; heartbeat intervals and health contract belong in specs.

**Operational failure narratives:** user navigates away → transport releases resources; network partition → stale indicator and bounded retries; cursor expires → snapshot requested, no false continuity; pending administrative action → remains pending until server confirms; tenant switch → old events discarded. Correlate frontend observations with server trace/run/stream IDs only if approved and safely exposed.

**Experimental design:** sample latency p50/p95/p99 only after instrumenting representative traffic and browsers. Compare alternatives under the same load, rates, payloads and reconnection patterns, and account for warmup/caching. The Tail at Scale (Dean/Barroso 2013) motivates tail measurement but does not define default targets for this application.
