---
name: web-realtime-streaming
description: Implement or diagnose browser SSE/WebSocket delivery, lifecycle, replay and bounded buffering. Skip durable server messaging guarantees and UI rendering without a streaming concern.
---

# Web Realtime Streaming — Bounded, Recoverable, Observable Client Streams

## Scope and inputs

Inspect the actual transport, event schema, scope/authentication, cursors, retention, deployment
intermediaries and allowed loss. Distinguish critical events from coalescible updates.

Preserve the consumer's stack and policies. Product-specific examples in references do not establish
requirements for this task.

## Procedure

1. Choose transport by direction, browser support, credentials and deployment constraints; use
   native parsing unless a concrete requirement justifies a custom parser.
2. Separate connecting, live, retrying, stale, reauthentication and terminal states. Dispose
   connections/listeners on scope changes and invalidate older generations.
3. Distinguish cursor, sequence, revision and acknowledgment. Detect gaps and resynchronize from an
   authorized snapshot when continuity cannot be established.
4. Bound queues and retries, respecting the declared loss policy. Browser outgoing buffer size does
   not prove remote receipt or provide receive backpressure.
5. Test parsing, duplicate/reordered events, reconnect, expired cursors, revoked access and slow
   consumers. Separate browser fixtures from real service and proxy behavior.

## Load detail when needed

- [Transport Selection: Direction, Recovery and
  Intermediaries](references/transport-choice-and-protocols.md): consult for transport choice and
  protocols.
- [Delivery Semantics: Order, Deduplication and Snapshot
  Reconciliation](references/sequencing-deduplication-and-snapshots.md): consult for sequencing
  deduplication and snapshots.
- [Bounded Queues, Slow Consumers, Flow
  Control](references/backpressure-bounded-queues-and-flow-control.md): consult for backpressure
  bounded queues and flow control.
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
