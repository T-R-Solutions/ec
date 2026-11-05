# Detailed decisions and failure cases

Read only for a decision needing more detail than the entry point. Apply the relevant steps to the
actual task; the sections are not a mandatory artifact or test checklist. Use existing project
contracts in their actual format. File names such as spec.md, plan.md or tasks.md are examples, not
required dependencies. Other skills are optional expertise, never enforcement mechanisms.

Ec references below are conditional consumer examples. They impose no Ec documents, YAML format,
stack or product policy on a generic task.

## Method — select references as needed

1. **Specify observable stream behavior from REQ/AC.** Create a matrix of producer, subscription,
   resource scope, version, message shape, sequence/cursor, tolerated delay, consumer effect,
   forbidden effect, and proof layer. Undefined constraints are spec gaps, not defaults. [Contract
   matrix](integration-contract-and-evidence.md).
2. **Choose a transport from direction and deployment reality.** EventSource/`text/event-stream` is
   server-to-browser push with defined parsing/reconnection; WebSocket is bidirectional message
   framing, with application-level replay required. Consider network intermediaries, auth, browser
   support and the need for flow control before a choice. Do not claim either is universally faster.
   [Transport selection](transport-choice-and-protocols.md).
3. **Separate wire parsing from domain validation.** For SSE handle UTF-8, BOM, CR/LF/CRLF,
   blank-line dispatch, multi-line `data`, `event`, `id` and numeric `retry`, comments and bounded
   untrusted payloads. Native `EventSource` already implements this; a custom parser is justified
   only for a documented need. For WebSocket validate JSON/binary type and schema *after* receiving
   a complete browser message. [SSE](sse-event-stream-format-and-resume.md) and
   [WS](websocket-lifecycle-and-message-contracts.md).
4. **Build a scoped connection lifecycle.** State transitions must distinguish
   connecting/open/retrying/stale/offline/reauth-required/closed. Make start/stop idempotent, clean
   timers/listeners/connections at logout, route changes and unmount, invalidate generations so
   previous sessions cannot repopulate state; SSR never opens a browser connection.
   [Lifecycle](vue-lifecycle-and-client-architecture.md).
5. **Implement bounded and classified recovery.** Transient transport failure may retry with capped
   backoff plus jitter; auth errors require reauthentication; expired cursors or detected gaps
   require authorized snapshot and reconciliation; terminal errors must stop. Native EventSource
   reconnect semantics may differ from custom reconnection; avoid stacking retry loops.
   [Recovery](reconnect-retry-and-offline.md).
6. **Treat cursor, sequence, and revision as separate concerns.** `Last-Event-ID` is an opaque SSE
   transport cursor, not an application acknowledgment or a guaranteed monotonic numeric sequence.
   Apply authorized events once by stable stream/epoch/sequence where promised; detect gaps and
   freeze/resynchronize instead of guessing missing state. Preserve immutable revision boundaries
   when the feature requires them. [Sequencing](sequencing-deduplication-and-snapshots.md).
7. **Control memory and consumer pressure.** Bound UI/history queues in measurable units; use
   high/low watermarks, pause/snapshot, or documented loss policy. Coalesce only explicitly
   ephemeral metrics/presence. Do not drop audit, financial, build logs or authoritative mutations
   silently. `WebSocket.bufferedAmount` is local outgoing queue size, not a server ACK, and classic
   `WebSocket` has no browser receive backpressure. [Flow
   control](backpressure-bounded-queues-and-flow-control.md).
8. **Apply security controls at server and browser.** Treat messages as untrusted, authorize each
   subscription/resource on the server, validate `Origin` for cookie-authenticated WebSockets,
   secure TLS transport, enforce size/velocity limits, avoid secrets in URL/logs, and never render
   event text as unsanitized HTML. Credential renewal/revocation and CORS rules must be tested, not
   assumed from client guards. [Security](security-origin-auth-and-data-isolation.md).
9. **Expose actionable quality states.** Show last trusted update, scope, stale vs offline,
   gap/recovering, error and a route to inspect/refresh; don't fake 'live' after socket disconnect
   or show a successful operation without confirmed server state. Collect actual reconnect, gap,
   buffer, drop and replay metrics without secrets. [Diagnostics](observability-and-reliability.md).
10. **Run layered oracles and preserve proof boundaries.** Validate pure parser/reducer with seeded
    cases and mutation tests; drive browser with fixture transport; test real SSE/WS handshake,
    errors, intermediaries, proxies, credentials and auth revocation against the real backend when
    available. Mark missing Vue and live API checks **NOT EXECUTED**. A model/mocked browser cannot
    certify server delivery, resume retention or product AC. [Test
    matrix](testing-transport-and-browser-matrix.md), [Failure
    matrix](failure-and-recovery-matrix.md).


## Hard rejection criteria

Reject an implementation or evidence claim if it treats an SSE cursor as application ACK; assumes
native WebSocket auto-replays messages; promises exactly-once external effects; adds a second hidden
desired configuration format; drops critical events on queue overflow; renders untrusted stream text
as HTML; mixes user/workspace stream state on logout; accepts a gap as complete history; blindly
retries 401/403; exposes tokens in querystrings; omits Origin validation in the server contract for
cookie-authenticated WS; promises automatic receive backpressure for the classic WebSocket API;
counts local `send()`/socket open as a confirmed operation; reports Chromium-only tests as
cross-browser; or promotes synthetic fixtures to Ec production acceptance.


## Further references by decision

Read only the reference relevant to the unresolved decision. Research registers describe imported authorship, not current execution.

- [Bounded Queues, Slow Consumers, Flow Control](backpressure-bounded-queues-and-flow-control.md): backpressure bounded queues and flow control.
- [Failure Injection and Expected Recovery Oracles](failure-and-recovery-matrix.md): failure and recovery matrix.
- [Integration Contract, project workflow Requirements and Evidence Ledger](integration-contract-and-evidence.md): integration contract and evidence.
- [Realtime Observability: Signals, Not Decorative Status](observability-and-reliability.md): observability and reliability.
- [Reconnect, Offline, Retry-After, Expired Cursors](reconnect-retry-and-offline.md): reconnect retry and offline.
- [Trigger / Non-Trigger / Borderline Routing and Selection Tests](routing-and-test-oracles.md): routing and test oracles.
- [Scientific Evidence: What Transfers and What Does Not](scientific-evidence-and-limits.md): source provenance or a disputed recommendation.
- [Security Controls for Incremental Frontend Streams](security-origin-auth-and-data-isolation.md): security origin auth and data isolation.
- [Delivery Semantics: Order, Deduplication and Snapshot Reconciliation](sequencing-deduplication-and-snapshots.md): sequencing deduplication and snapshots.
- [Verified Standards, Official Documentation, Research and Decisions](sources-and-decisions.md): source provenance or a disputed recommendation.
- [SSE: Parser, Event IDs, Reconnection and Resume Limits](sse-event-stream-format-and-resume.md): sse event stream format and resume.
- [Layered Verification: Pure Model, Browser, Loopback, Application](testing-transport-and-browser-matrix.md): testing transport and browser matrix.
- [Transport Selection: Direction, Recovery and Intermediaries](transport-choice-and-protocols.md): transport choice and protocols.
- [Vue Lifecycle, Client Connection Scope and SSR](vue-lifecycle-and-client-architecture.md): vue lifecycle and client architecture.
- [WebSocket: Client Contract and Lifecycle](websocket-lifecycle-and-message-contracts.md): websocket lifecycle and message contracts.
