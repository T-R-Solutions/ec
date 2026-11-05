# Transport Selection: Direction, Recovery and Intermediaries

| Concern | Server-Sent Events / EventSource | Classic WebSocket |
|---|---|---|
| Communication | Server → browser (one-way) | Duplex |
| Standard | WHATWG HTML § 9.2 | RFC 6455; RFC 8441 for HTTP/2 bootstrapping |
| Framing | UTF-8 `text/event-stream` line protocol | Text/binary framed messages |
| Browser reconnect | EventSource defines automatic reconnect for recoverable cases | Application must implement if wanted |
| Resume | Browser sends last event ID; server storage/replay is separate | No inherent resume; application-defined |
| Client send | Separate HTTP request or other channel | `socket.send()` once OPEN, *not a server ACK* |
| Backpressure | EventSource has no consumer-controlled receive pause; limit/snapshot strategy needed | Classic browser WebSocket lacks native receive backpressure |
| Authentication | Browser EventSource has credentials option, no general arbitrary header parameter | Browser WS constructor does not allow arbitrary HTTP headers; validate cookies/origin/session or negotiated auth, server authorization |
| Runtime | HTTP intermediaries may buffer / time out; test actual deployment | Proxy handshake, close behavior and session idle timeout require testing |

Do not select a transport based on an unsupported “SSE is always faster” or “WebSocket delivers exactly once” claim. For deployments that need true backpressure and advanced transport features, evaluate standard browser-supported alternatives such as WebTransport with explicit support and infrastructure requirements; `WebSocketStream` is non-standard/experimental per MDN. Do not treat novel APIs as universal.

**Selection record:** approved use case; browser/runtime support; connections per user/tab; auth and Origin model; server resource budget; replay retention; proxy/CDN features; packet loss and mobile network scenarios; binary/text payload; operational observability; compatibility with existing clients. If any dimension is unknown, document it as a risk and test gate, not a fabricated capability.
