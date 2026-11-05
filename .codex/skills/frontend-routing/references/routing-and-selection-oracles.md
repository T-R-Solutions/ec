# Skill-Selection Oracles: Positive, Negative and Borderline

> Ec-specific rules below apply only to an Ec consumer whose current contract establishes them.

For each prompt, choose the **primary competence**; a secondary skill is justified only for a demonstrated independent issue. The examples are classification cases, not actual automated agent-evaluation results.

| ID | Prompt | Select? | Reason / boundary |
|---|---|---|---|
| P01 | “Vue Router deep links break after reloading nested URLs.” | YES | History deployment/fallback contract and path 404 |
| P02 | “The Back button loses selected tab and page filter.” | YES | Route query serialization and navigation history |
| P03 | “An async route guard shows a stale admin view after logout.” | YES | Navigation guard lifecycle; server authorization requires separate security review |
| P04 | “Design typed named routes and safe return URLs.” | YES | Route identity, encoding, open redirect prevention |
| P05 | “Browser Back changes URL but not the displayed page.” | YES | popstate + router synchronization |
| N01 | “Pick the sidebar information hierarchy and labels.” | NO | `information-architecture` |
| N02 | “Validate fields and dirty state in a registration form.” | NO | `frontend-form-engineering` |
| N03 | “Tune remote cache freshness and invalidation.” | NO | `frontend-state-and-cache` |
| N04 | “Implement OIDC refresh and logout token revocation.” | NO | `identity-federation` |
| N05 | “Design GraphQL error objects and HTTP status contracts.” | NO | `http-api-contracts` |
| N06 | “Create a CSS layout with a resizable sidebar.” | NO | `css-layout-engineering` |
| N07 | “Create YAML schema and publication policy for Ec.” | NO | Ec-specific schema/publication skills |
| B01 | “A user switches workspace but old workspace data remains.” | CONDITIONAL | Select routing for route identity/transition; state-cache for cached response scoping |
| B02 | “Route-level badge should reflect permissions.” | CONDITIONAL | Routing for navigation visibility; backend RBAC and UX separate |
| B03 | “I want a full E2E test of logged-in navigation.” | CONDITIONAL | Frontend-testing primary; routing for navigation contract/invariants |
| B04 | “The dashboard link's label confuses operators.” | NO (normally) | IA/design-review unless URL/history implementation is broken |

**Boundary test:** “Should admins be able to open `/admin` if they know the URL?” No client guard can establish authority: require backend authorization and the current spec. Use this skill only for route navigation UX and fail-closed presentation.
