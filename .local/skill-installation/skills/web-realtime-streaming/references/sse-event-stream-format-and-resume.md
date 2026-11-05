# SSE: Parser, Event IDs, Reconnection and Resume Limits

Use the browser's native `EventSource` whenever it meets the requirements. A custom `fetch()` + Streams parser must be justified, for example a controlled header requirement, and needs its own full grammar tests.

**WHATWG normative behavior:** stream response uses `text/event-stream`, UTF-8; one initial BOM may be ignored. Lines may end with LF, CRLF or CR. A blank line dispatches a pending event only if at least one `data` field was encountered. Multiple `data` fields join with newline; exactly one optional space is stripped after a colon. Comments start with `:`. `event:` supplies the event type; `id:` persists as the last event ID across events, and an empty `id` resets it. Ignore `id` containing NUL. `retry` must be ASCII digits, interpreted in milliseconds. The stream's final line without a blank-line terminator does not dispatch a pending message. Unknown fields are ignored.

**Important separation:** `Last-Event-ID` is an **opaque transport resume token**, not equivalent to numeric domain `seq`, revision hash, event acknowledgment, or proof that a server retained history. If an API supplies no replay retention, reconnect and obtain a coherent authorized snapshot. If replay is expired, surface the gap and explicitly resynchronize. If a browser EventSource is already managing reconnect, do not stack another independent jitter loop on the same object.

**Fixture examples (test-only):**

```text
:heartbeat

id: evt-11
event: state-change
data: {"kind":"change"}

retry: 1500

id
data: resumed-without-cursor

```

Bound individual lines/event payloads in a custom parser. Validate the structured message schema separately from SSE transport parsing; do not execute tags, code or HTML coming from `data`.

**Acceptance oracles:** LF/CRLF/CR split; UTF-8 split across chunks; a BOM; blank `data`; multi-line `data`; empty `id`; invalid NUL ID; retry non-digits; comments only; unknown fields; EOF without blank line; line and event limits; reauth, expired cursor and an entirely unavailable replay store. Do not claim “zero loss” without the server's actual contract and tests.
