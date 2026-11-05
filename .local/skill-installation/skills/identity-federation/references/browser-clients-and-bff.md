# Browser client architecture and token storage

> Ec-specific rules below apply only to an Ec consumer whose current contract establishes them.

Evaluate SPA-only OIDC versus a backend-for-frontend (BFF) or server-side web client based on threat model, topology, cross-origin constraints, UX, CSRF protection, token handling and operational cost. A BFF can keep tokens away from browser JavaScript while replacing them with a sensitive cookie-backed session; it does not eliminate XSS or CSRF risk. When using a public browser client, PKCE is mandatory under RFC 9700.

Browser-visible `localStorage` and persistent scripts should not contain long-lived secrets merely for implementation convenience. Define trusted redirect and callback origins, content security policy, cookie attributes, tab synchronization, token refresh coordination and recovery from failed refresh. If frontend and backend are separated, ensure the actual resource server verifies authorization rather than trusting an authenticated-looking UI.

For Ec, the choice of frontend Vue and backend Rust is a normative product setting; this **general skill** only provides transferable techniques. An architectural choice requires the feature `plan.md`/ADR and actual integration results.
