# Scope Transitions and Visibility Boundaries

> Ec-specific rules below apply only to an Ec consumer whose current contract establishes them.

A route's `workspaceId`, `tenantId`, `projectId`, resource ID and region are **untrusted selectors** until validated. A URL can be stale, manually edited, shared, or opened under a different identity. Client-side navigation is not a grant of privileges, and `beforeEach` checks cannot replace enforcement at the API and data source.

For every protected route specify:

- Scope identifier and how the server decides whether the principal may read/write the requested resource.
- Handling for unknown, cross-scope, inaccessible, archived and deleted resources; decide whether 403 or 404 semantics are disclosed to the client and what UX reveals safely.
- Navigation across project/workspace boundaries, including stale in-flight route loaders, stale cached content and old tabs. For server cache isolation use `frontend-state-and-cache`; routing simply triggers the transition.
- Deep link opened after logout, permission revocation, session expiry, or identity switching. Guard failure should not display stale protected content during transition.
- Prevent privilege escalation by modifying route segments or query parameters; recheck every server operation.

Do not reuse namespace-separated URLs as a substitute for permission checks. Never infer an Ec project hierarchy or route contract from a synthetic example; use the current project specification and API implementation.

Sources: [Vue Router Meta](https://router.vuejs.org/guide/advanced/meta.html), [OWASP Broken Access Control](https://owasp.org/Top10/2021/A01_2021-Broken_Access_Control/).
