# Bounded Queues, Slow Consumers, Flow Control

**Facts:** the WHATWG Streams API supports `desiredSize`, `ready`, `highWaterMark`, and pipe-chain backpressure. This does not automatically apply to EventSource or the standard browser WebSocket interface. `WebSocket.bufferedAmount` counts outgoing client-queued bytes, not an acknowledged backlog on the server. Classic WebSocket does not offer native browser receive backpressure (MDN).

**Design:** declare max queue size in bytes/events, source rate, render budget and recovery strategy. For *lossless critical* state transitions, an overflow must trigger a visible error/resynchronization strategy, source slowdown if the contract supports it, or a stable durable replay path; never silently discard. For *ephemeral coalescible* data (presence/visual progress), the product may allow drop/coalesce by exact entity key and epoch with an observable drop counter and known freshness effect. An arbitrary task log should not be labeled ephemeral to evade the bound.

**High/low watermarks:** the sender may inspect `bufferedAmount` before queuing further client messages. It is a local guard, not a transport-level credit from the server. For receiving, consider app-level credits or checkpoint/snapshot when supported by the service; a tight `onmessage` callback that keeps accumulating everything cannot impose network backpressure.

**Testing:** burst volume, sustained slow consumer, payload cap, memory growth, overflow on critical event, coalescing on ephemeral event, backlog while hidden tab, reconnection while over limit, and user logout cleanup. Compare metrics measured on actual deployed browser/hardware against explicit acceptance targets; no invented universal threshold.
