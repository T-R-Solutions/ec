# URL Encoding, Query Contracts and Canonical Links

Use the platform `URL` and `URLSearchParams` for parsing and serializing rather than manually appending `?`, `&`, `#` or percent escapes. Vue Router named routes perform parameter encoding, but the application still owns domain validation. Do not manually pre-encode a value then pass it to a serializer that will encode again.

```ts
// Example only; values and route names require a real specification.
router.push({ name: 'resource-detail', params: { workspaceId, resourceId },
              query: { page: String(page), tab: activeTab } })
```

A declared query contract must specify (a) unknown keys, (b) duplicate keys, (c) empty string versus omitted, (d) normalization and default handling, (e) enum values, (f) numeric ranges, (g) repeated-array semantics, (h) URL length limits and (i) which view states should be shareable. Naively mapping `URLSearchParams` to a plain object loses repeated values. Parsing a complete URL string as though it were a query string also produces incorrect results.

Use distinct functions for **raw untrusted inputs** and **validated navigation destinations**. Avoid double-decoding, encoded delimiters, dot segments and ambiguous Unicode in security-sensitive path policies. If a resource ID is opaque, normalize only when the authoritative contract permits it; case folding identifiers may point to another resource. An invalid URI should yield an explicit failure or a not-found view, never a generic successful route.

The included synthetic model opts for strict unknown-key rejection and a length limit. **Those are deliberately chosen fixture rules, not general Vue Router rules or application policies.** Real apps may preserve extension query parameters if the spec requires them.

Sources: [WHATWG URL Standard](https://url.spec.whatwg.org/), [MDN URLSearchParams](https://developer.mozilla.org/en-US/docs/Web/API/URLSearchParams), [Vue Router Dynamic Matching](https://router.vuejs.org/guide/essentials/dynamic-matching.html).
