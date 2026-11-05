# Verified Standards, Official Documentation, Research and Decisions

> Imported authoring record. Research dates, consultations and execution claims below belong to the supplied source package; they were not reproduced in this adaptation. Referenced validation scripts, fixtures, product norms and logs are not bundled unless present in this folder. Verify their availability and provenance before use. No historical PASS is current acceptance.

This register was checked against current published pages on 2026-11-01. RFC and WHATWG specifications provide normative definitions; MDN and vendor framework pages are implementation documentation; OWASP is security guidance. Technical recommendations below are not new product requirements.

| ID | Origin / authority | URL | Supported point and limit |
|---|---|---|---|
| STD-01 | WHATWG HTML Living Standard, SSE | https://html.spec.whatwg.org/multipage/server-sent-events.html | Native EventSource, UTF-8 `text/event-stream`, event fields, dispatch, ID and reconnect; does not guarantee application storage or replay retention |
| STD-02 | RFC 6455, IETF | https://www.rfc-editor.org/rfc/rfc6455.html | WebSocket framing, handshake, close, ping/pong, origin and masking; no app-level exactly-once or durable replay |
| STD-03 | RFC 8441, IETF | https://www.rfc-editor.org/rfc/rfc8441.html | Extended CONNECT for WebSocket over HTTP/2; not all proxies deploy it |
| STD-04 | WHATWG Streams Standard | https://streams.spec.whatwg.org/ | Backpressure and queue high-water marks for Streams; not a guarantee for the classic WebSocket interface |
| STD-05 | RFC 9110, IETF | https://www.rfc-editor.org/rfc/rfc9110.html | Retry-After format and 503 meaning; application reconnection policies require context |
| DOC-01 | MDN WebSocket API | https://developer.mozilla.org/en-US/docs/Web/API/WebSockets_API | Classic WebSocket cannot apply receive backpressure; compatibility and nonstandard alternatives are not universal support |
| DOC-02 | MDN WebSocket `bufferedAmount` | https://developer.mozilla.org/en-US/docs/Web/API/WebSocket/bufferedAmount | Client buffered outgoing bytes, not server acceptance |
| DOC-03 | MDN Server-sent events | https://developer.mozilla.org/en-US/docs/Web/API/Server-sent_events/Using_server-sent_events | Implementation details, event examples and browser connection considerations |
| DOC-04 | MDN `EventSource` | https://developer.mozilla.org/en-US/docs/Web/API/EventSource | Browser API, close() and credentials support |
| DOC-05 | MDN `WebSocketStream` | https://developer.mozilla.org/en-US/docs/Web/API/WebSocketStream | Non-standard experimental API; do not make it default cross-browser without verified support |
| DOC-06 | Vue official lifecycle | https://vuejs.org/api/composition-api-lifecycle | `onUnmounted()` for manually-created listeners, timers, server connections; not run in SSR |
| DOC-07 | Vue official watchers | https://vuejs.org/guide/essentials/watchers.html | Reactive cleanup and lifecycle concerns; inspect installed version |
| SEC-01 | OWASP WebSocket Security Cheat Sheet | https://cheatsheetseries.owasp.org/cheatsheets/WebSocket_Security_Cheat_Sheet.html | Origin allowlisting, session/authz, message validation, limits, logging; guidance, not independent proof of a service |
| SEC-02 | OWASP Cross-Site WebSocket Hijacking guidance | https://cheatsheetseries.owasp.org/cheatsheets/WebSocket_Security_Cheat_Sheet.html#cross-site-websocket-hijacking-cswsh | Threat rationale for cookie-based WS sessions |
| DOC-08 | MDN WebSocket `send()` | https://developer.mozilla.org/en-US/docs/Web/API/WebSocket/send | Client `send()` and buffer behavior; does not imply backend job publication success |
| DOC-09 | MDN Server-sent events browser notes | https://developer.mozilla.org/en-US/docs/Web/API/Server-sent_events/Using_server-sent_events#warning | Browser connection limits context; actual deployment must be measured |

## Peer-reviewed, bibliographically verified studies

- **SCI-01** Welsh, Culler, Brewer (2001). “SEDA: An Architecture for Well-Conditioned, Scalable Internet Services”, *SOSP 2001*, pages 230–243. **Conference DOI**: https://doi.org/10.1145/502034.502057 (DBLP confirms). An ACM SIGOPS journal mirror has DOI https://doi.org/10.1145/502059.502057. Supports bounded stages and admission/load controls in the studied systems; does *not* empirically compare modern browser SSE and WebSocket.
- **SCI-02** Akidau et al. (2013). “MillWheel: Fault-Tolerant Stream Processing at Internet Scale”, *Proceedings of the VLDB Endowment* 6(11):1033–1044. DOI: https://doi.org/10.14778/2536222.2536229. Describes specialized stateful streaming guarantees; does not confer exactly-once delivery on WebSocket or EventSource.
- **SCI-03** Akidau et al. (2015). “The Dataflow Model: A Practical Approach to Balancing Correctness, Latency, and Cost in Massive-Scale, Unbounded, Out-of-Order Data Processing”, *PVLDB* 8(12):1792–1803. DOI: https://doi.org/10.14778/2824032.2824076. Motivates explicit event-time vs processing-time semantics and correctness tradeoffs; not a frontend latency benchmark.
- **SCI-04** Dean and Barroso (2013). “The Tail at Scale”, *Communications of the ACM* 56(2):74–80. DOI: https://doi.org/10.1145/2408776.2408794. Explains tail latency variability in large services; cannot set Ec service-level objectives without measurements.

## Decisions from the sources

**D-1:** SSE is the default conceptual candidate for one-way incremental updates where a browser-managed EventSource connection meets auth and intermediaries. **D-2:** Classic WebSocket is for duplex traffic, but no native replay and no receive-side application backpressure. **D-3:** Critical event loss needs a gap/snapshot strategy; ephemeral coalescing must be named. **D-4:** Observability and acceptance require the real deployment; all bundled examples are generic fixtures. **D-5:** No frontend streaming transport controls the canonical configuration format of a product.

**Evidence hierarchy:** normative standard → maintained platform docs → security guidance → scientific study in its studied environment → proposed engineering method → synthetic executable fixture → real Ec integration (NOT EXECUTED). The studies justify risks and methods, *not* product-specific thresholds or benchmarks.
