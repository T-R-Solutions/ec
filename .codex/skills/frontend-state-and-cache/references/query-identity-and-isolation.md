# Deterministic query identity and isolation

> Ec-specific rules below apply only to an Ec consumer whose current contract establishes them.

## Domain-specific identity

A query key must distinguish all variables that shape the server response. Typical dimensions are tenant, principal, authorization epoch, resource kind, scope/project, permissions-dependent view, filters, sort, cursor, selected API revision and effective locale if the payload is localized. Do not include secrets or unbounded personally identifying values in logs or browser storage. Query keys are *internal cache identity*, not authentication.

For a response whose content depends on authorization, use a clearly partitioned context. On logout, role change, session revocation, tenant switch or impersonation, invalidate/clear the relevant cache and fence in-flight work. A new session epoch should not reuse snapshots from an older session unless explicitly classified public and authorized.

## Stable canonicalization

Avoid ad-hoc string concatenation (`tenant + ':' + project`) that can collide. Prefer a deterministic tuple/array representation with structured variables; sort object keys where the chosen library requires it. Reject undefined, non-finite numeric parameters, unserializable objects and accidental credentials. Treat array order as meaningful unless API contracts say otherwise.

This skill's synthetic model uses a JSON-encoded internal key, **not a JSON configuration file**. It illustrates an ephemeral data structure only. JSON used to transport/cache API records must never be misrepresented as an alternate Ec declarative resource format.

## Negative oracles

- Two users requesting the same project resource cannot read each other's response from the client cache.
- A response that began before logout cannot repopulate the new session, even if it ignores abort.
- Two projects with overlapping names but different scope IDs produce distinct keys.
- Keys include pagination/filters; a stale page cannot be shown as a different page's freshly fetched data.
- No tokens, passwords, cookies or secret values are serialized in cache keys, logs or IndexedDB.

Remember: namespace partition does not protect an endpoint. Authentication/authorization are verified at the backend on every relevant action and resource read.

References: [TanStack Query query keys](https://tanstack.com/query/latest/docs/framework/vue/guides/query-keys); [Pinia SSR isolation](https://pinia.vuejs.org/ssr/).
