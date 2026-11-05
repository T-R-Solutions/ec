# Trigger / Non-Trigger / Borderline Routing and Selection Tests

> Ec-specific rules below apply only to an Ec consumer whose current contract establishes them.

Positive triggers (activate):
1. SSE `Last-Event-ID` resume leaves duplicate events or unexpectedly resets the last event id.
2. WebSocket reconnect storms after a short Wi-Fi outage.
3. Reactivity updates keep showing a previous user's events after logout.
4. An operator dashboard exhausts memory when events arrive faster than render.
5. A client misses sequence 27 and must obtain an authorized snapshot.
6. Browser WS `bufferedAmount` increases without bound after a disconnected server.
7. Need browser network tests for SSE transport framing, WS Origin rejection, and subsequent user-facing statuses.

Negative triggers (do not activate):
1. Define the PostgreSQL job log format and chunk indexes → `cicd-logs-and-test-results`.
2. Design Ec agent lease/fencing HTTP wire contract → `ec-agent-control-protocol`.
3. Define product API event schemas without an approved spec → `http-api-contracts` / project specification.
4. Implement virtualized log rendering unrelated to streaming transport → frontend implementation/testing.
5. Choose authorization roles or mutate canonical YAML on socket open → product norms/`ec-effective-permissions`.
6. General cache invalidation on ordinary REST response → `frontend-state-and-cache`.
7. Design OS-level worker process isolation → runtime/CI domain skill.

Borderline cases and correct handoff:
- “Logs are missing on reconnect”: this skill tests client cursor, gaps, bounded queue; domain log skill defines which chunks are reconstructible.
- “Stop button sends a WS message”: this skill can guard send/acks and UI state, but it cannot claim stop completion without the authoritative operation API.
- “A real-time dashboard shows green after logout”: this skill owns client lifecycle and stale state; server authorization must separately be tested.

**Selection evidence plan:** present the above prompts to an *independent agent* with the full catalog, capture activated skill IDs, compare expected primary/auxiliary choices and report confusion. The supplied static routing cases and fixture checks do not constitute that agent evaluation.
