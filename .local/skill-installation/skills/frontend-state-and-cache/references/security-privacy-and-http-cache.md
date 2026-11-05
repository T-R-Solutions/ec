# Privacy, authorization and HTTP/application-cache separation

**Security invariant:** backend authorization is evaluated independently from any client filter or cache namespace. A successful protected fetch proves only a response was received under one context, not that the cache may reveal it after logout, role revocation or session change.

## Layer separation

- Browser HTTP cache and intermediaries follow `Cache-Control`, `Vary`, validator and authorization rules in RFC 9111; `private` prohibits storage in *shared HTTP caches*, not all private client caching.
- Application-level query caches typically reside in JavaScript memory (or explicit persistence); HTTP headers do not automatically enforce their own eviction. Audit each layer.
- Persistent storage (localStorage/IndexedDB/service workers) has different attack, retention, quota and XSS risks; do not persist private server responses without explicit feature and security approval.

Do not put secret strings in keys or logs. Cache key fields such as tenant IDs and principal IDs may themselves reveal sensitive information when logged or persisted; prefer in-memory opaque partitions where practical. In SSR, avoid serving per-user HTML or dehydrated cache under a public CDN cache key.

Use `Cache-Control: no-store` where an HTTP response must not be cached, with the limitation explicitly recognized in RFC 9111: it cannot by itself guarantee privacy against compromised caches or other data exposure. Policies for user data must also account for service workers, query caches and browser devtools.

## Security test cases

A logged-out scope cannot read cached values; a second user cannot see SSR hydration from the first; an old in-flight response after role change is ignored; an unauthorized query must not be "fixed" by hiding UI controls only; payload rendering uses text rather than HTML injection; no auth token appears in a serialized key or fixture.

References: [RFC 9111](https://www.rfc-editor.org/rfc/rfc9111.html), [OWASP session management](https://cheatsheetseries.owasp.org/cheatsheets/Session_Management_Cheat_Sheet.html), [Vue SSR cross-request state](https://vuejs.org/guide/scaling-up/ssr.html).
