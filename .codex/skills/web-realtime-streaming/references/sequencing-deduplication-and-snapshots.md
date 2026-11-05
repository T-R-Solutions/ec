# Delivery Semantics: Order, Deduplication and Snapshot Reconciliation

> Ec-specific rules below apply only to an Ec consumer whose current contract establishes them.

Transport order is not end-to-end correctness. Within a WebSocket connection RFC 6455 supplies ordered framing over TCP, but reconnecting can introduce duplicate, missed or differently sequenced application updates. SSE's last event ID is a resume hint, not proof of retained events. Do not infer monotonicity from arbitrary string cursors, wall-clock timestamps or a YAML document's key order.

**Suggested product-neutral contract fields (illustrative, not an Ec schema):** stable `stream_scope`, `generation/epoch`, `sequence`, `revision`, `event_id`, `kind`, `payload`, optional `resume_cursor`. The authoritative service must define which fields exist, their uniqueness/order, and whether a snapshot is atomic relative to deltas. `revision` may mean a resource/configuration revision and must not be silently collapsed into transport `id`.

**Consumer algorithm when sequential sequence exists:** maintain per-authorized-scope high-water sequence. Ignore duplicates/old events; apply the next expected sequence; on gap, enter explicit `resync-required` state and stop pretending the view is complete; obtain a coherent snapshot with a server-verified boundary and then process later deltas. Reset/clear state and invalidate old generation on scope switch and logout.

**Critical distinction:** deduplication at a browser projection may prevent duplicate rows, but cannot prevent duplicate external effects initiated upstream. Persisted events, leases and idempotency keys must be covered by the source domain and backend tests. Never claim exactly-once delivery/effects based solely on the client projection.

**Test oracles:** duplicates, reordered input, missing ranges, restart with stale cursor, concurrent scope changes, 410 expiry, authorized snapshot, invalid snapshot, old auth context, and immutable revision references where relevant.
