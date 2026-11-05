# WebSocket: Client Contract and Lifecycle

`WebSocket` provides framed duplex communication and an `OPEN` ready state. It does not automatically sequence, replay, authenticate, or persist domain messages. The browser JS API cannot emit raw protocol Ping/Pong frames (RFC 6455 control frames are managed by the implementation). Application heartbeat messages may be useful, but distinguish them from protocol ping.

**On open:** bind the authorized resource and scope; do not infer that the server has accepted a subscription until the application protocol supplies an appropriate accepted/ready event. **On close:** distinguish clean/abnormal/application policy close only from observed close code and contract; a close reason is not automatically safe to show. **On send:** `socket.send(payload)` and `bufferedAmount` indicate local queuing; neither means the command completed remotely. **On message:** parse and validate known content types/sizes, reject unknown schema revisions, and apply only to the active user/scope/generation.

**State machine:** idle → connecting → open → retrying/reauth/snapshot-required/closed. Explicit disconnect, route change and logout increment the connection generation and close the object. Late handlers must check generation; cleanup should be idempotent. Backoff is bounded and canceled on logout; `401/403` should not spin in a reconnect loop.

**Server-owned safety:** TLS termination and certificate policy; exact Origin allowlist where browser cookies are used; auth during connection and at resource subscription; session expiry handling; per-message authorization; maximum frame/message size; flow control, rate limits, observability and safe close policies. The JS side cannot enforce these server guarantees.

Use RFC 6455/8441 and an implementation's negotiated subprotocol as the concrete wire authority. Avoid assuming that an HTTP/1.1 Upgrade request represents the only possible wire handshake on HTTP/2 deployments.
