# Security Controls for Incremental Frontend Streams

**Transport:** require HTTPS/WSS in real deployment (exceptional localhost tests are explicitly nonproduction), trusted service endpoints and reviewed CSP/CORS policy. Browser native `EventSource` credentials are constrained to the API's supported options; it does not accept arbitrary custom headers by construction. For WebSocket, browsers send an `Origin` on handshakes; the server must validate the origin against an exact allowlist for cookie-authenticated sessions, plus authenticate and authorize each resource and subsequent operation. Origin is not a replacement for authentication.

**Scope:** stream subscriptions can contain sensitive data. Partition by identity, organization and resource, and authorize changes even after connection establishment. Revalidate session expiry; protect against cross-site WebSocket hijacking, event injection, cross-scope replay and high-volume streams. On logout, close transport, cancel retry, clear sensitive projection, and invalidate generation so pending async events cannot re-populate the screen.

**Untrusted events:** validate schema/size/type; treat `data` as text until parsed; use DOM text nodes (`textContent` / framework escaped interpolation) rather than `innerHTML`; do not show raw tokens, secrets or personal details in telemetry. Bound parsing and per-client buffers. Consider endpoint abuse, compression bombs, oversized frames and resource-exhaustion controls in the service implementation.

**Evidence:** a hidden button, successful browser mock or local `Origin` check does not prove server authorization. Require real handshake with disallowed origin, unauthenticated and expired session, wrong scope at subscription, malicious payload, long-lived connection after revocation, and connection quota under test environment. Security verification belongs to backend/application-security beyond this generic client method.

Sources: OWASP WebSocket Security Cheat Sheet; RFC 6455; WHATWG EventSource; Vue template escaping guides (consult deployed framework version). No secret values appear in bundled fixtures.
