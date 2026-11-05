# URL Safety, Open Redirects and Untrusted Parameters

**Threat:** a `return`, `next` or `redirect` query value supplied by an attacker can send a user to a malicious origin or to a privileged view without re-evaluation. OWASP recommends not using arbitrary user-provided destinations; map stable IDs to approved destinations where possible. If a URL must be accepted, parse it and validate scheme, origin, and any path restrictions, not raw prefix matching.

```ts
function isAllowedRelativeDestination(raw: string): boolean {
  if (!raw.startsWith('/') || raw.startsWith('//') || raw.includes('\')) return false
  const parsed = new URL(raw, location.origin)
  return parsed.origin === location.origin
    && parsed.username === '' && parsed.password === ''
    // AND validate route allowlist, path segments, query contract, scope, etc.
}
```

This snippet alone is **not a security-complete allowlist**. For example, dot path normalization, percent-encoded delimiters, double decoding, reverse proxies and downstream redirect handlers can change semantics. Validate the exact final redirect target and reauthorize target resource on the backend. Never store credentials, one-time login tokens, secret values or personally sensitive payloads in navigational URLs. Client-side route matching, route meta, and hidden menu items do **not** prove access control.

Require test inputs: `https://evil.test/`, `//evil.test/`, `\evil`, encoded dot/slash/double-decoding patterns, unknown route, login redirect loop, URL with credentials, duplicate `return` parameters, and identity change while redirecting.

Sources: [OWASP Unvalidated Redirects Cheat Sheet](https://cheatsheetseries.owasp.org/cheatsheets/Unvalidated_Redirects_and_Forwards_Cheat_Sheet.html), [WHATWG URL Standard](https://url.spec.whatwg.org/), [MDN URL API](https://developer.mozilla.org/en-US/docs/Web/API/URL).
