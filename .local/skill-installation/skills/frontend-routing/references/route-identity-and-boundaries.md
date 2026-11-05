# Route Identity, Ownership and Cross-Skill Boundaries

> Ec-specific rules below apply only to an Ec consumer whose current contract establishes them.

A route identifies a **navigation context**, not an authenticated principal or a trusted resource. Separate its pathname (hierarchy and opaque resource identifiers), query (shareable filter/page/tab state), fragment (in-document target), view-only ephemera (sidebar resizing), and server-derived data. State the authority and serialization policy for each. A URL is supplied by an untrusted caller and must never be promoted into an authorization fact.

## Route contract checklist

- Name every stable route, document the parent/child nesting, route-specific params and supported query keys, canonical trailing-slash and casing policy, and 404 behavior. Route names are implementation handles, not translated display labels.
- Paths must retain enough scope to disambiguate resource identity on reload and copy/share. A multi-tenant resource deep link requires an explicit scope key if the backend model is scoped.
- Decide whether query parameters are ordered, repeated, optional, unknown, case-sensitive, or normalized. Resolve omission versus default values and make serialization reversible for valid states.
- Keep secrets, access tokens, session IDs and sensitive credentials out of links (referrers, logs, browser history, pasted messages).
- Route parameters identify *requested* data. The server revalidates principal, resource scope, permission and resource existence for every read/write; hiding navigation is not protection.
- Keep network query caching, form ownership and canonical configuration outside routing. A route change may invalidate a view load but does not dictate a product-wide cache policy.

## Responsibility boundaries

`information-architecture` chooses the navigation taxonomy and user tasks; `frontend-routing` implements route matches, URL fidelity and history transitions; `web-accessibility` audits end-to-end assistive access; `frontend-state-and-cache` implements remote cache isolation; `identity-federation` handles identity protocols; `http-api-contracts` governs API semantics. No recursive skill activation.

**Example synthetic contract:** `workspaces/:workspace/resources/:resource` requests a resource in a specified workspace. The example is an oracle fixture, **not a promised Ec route**. For production, read the approved spec and actual implementation before translating it into a router configuration.

Source: Vue Router [Named Routes](https://router.vuejs.org/guide/essentials/named-routes.html), [Route Meta Fields](https://router.vuejs.org/guide/advanced/meta.html).
