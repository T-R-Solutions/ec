---
name: web-realtime-streaming
description: Implement or diagnose browser SSE/WebSocket delivery, lifecycle, replay and bounded buffering. Skip durable server messaging guarantees and UI rendering without a streaming concern.
---

No Ec, siga `docs/development/task-verification.md` para acionar checks e
`docs/development/local-state.md` para arquivos de trabalho (caminhos desde a
raiz). Em outros consumidores, siga as políticas locais.

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

Registre contrato afetado, inputs, método, resultado e lacunas; distinga inspeção,
simulação e integração real. Registros importados não são resultados atuais.

Aplique apenas os passos pertinentes, usando o registro existente do projeto.
A skill funciona sem SDD; consulte auxiliares disponíveis somente para questões
distintas ainda não resolvidas. O método não amplia a autorização da tarefa.
