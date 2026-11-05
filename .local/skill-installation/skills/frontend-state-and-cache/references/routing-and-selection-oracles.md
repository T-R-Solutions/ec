# Routing and selection oracles

> Ec-specific rules below apply only to an Ec consumer whose current contract establishes them. Commands, fixture paths and historical execution claims from the source package are illustrative or imported evidence unless the corresponding files are present and independently checked; they are not current test results or bundled executable tools.

The descriptions below are selection fixtures, not evaluations run against an autonomous coding agent. No agent activation benchmark has been executed. Selection should use the primary intent and domain authority; do not recursively activate neighboring skills.

| Kind | User request | Primary choice | Why |
|---|---|---|---|
| Positive | Two tabs issue duplicate status requests | `frontend-state-and-cache` | shared in-flight query, deduplication |
| Positive | A stale response replaces the new project data | `frontend-state-and-cache` | scope key and generation fence |
| Positive | After logout, data from the previous identity remains visible | `frontend-state-and-cache` | private cache purge and auth partition |
| Positive | Cache keeps showing a stale build queue after reconnect | `frontend-state-and-cache` | freshness and invalidation |
| Positive | A low-risk optimistic client action fails and must roll back | `frontend-state-and-cache` | client cache mutation lifecycle |
| Positive | SSR renders another user's cached data | `frontend-state-and-cache` | cross-request state isolation |
| Negative | Design the PostgreSQL scheduler's queue indexes | `postgresql` | server DB internals, not frontend state |
| Negative | Define the YAML schema for a Project | `ec-declarative-resource-schemas` | project-specific contract |
| Negative | Improve a CSS Grid layout at 375px | `css-layout-engineering` | style and responsive geometry |
| Negative | Implement web socket handshake, framing and backpressure | `web-realtime-streaming` | transport mechanics |
| Negative | Add unit validation and field errors to a form | `frontend-form-engineering` | field-level behavior |
| Negative | Choose whether to use Redis on the server | `database-engineering` | remote storage architecture |
| Negative | Design an HTTP 412 conditional update response | `http-api-contracts` | API semantic authority |
| Boundary | Server event has no defined resource ordering | `frontend-state-and-cache` when invalidating/refetching client cache | do not invent event revision |
| Boundary | Profile Vue component re-renders on data refresh | `vue3-engineering` primary, this skill only for cache invalidation | separate component and server state |
| Boundary | User asks whether permissions may be optimistic | Ec authorization policy first, this skill only for view mechanics | no generic policy invented |
| Boundary | Designer YAML loses fields after a mode switch | `ec-semantic-configuration-projections` primary | semantics not generic cache |
| Boundary | Build cache keys depend on branch | `cicd-build-cache`, not this skill | artifact caching outside browser |
| Boundary | Implement a query status banner | this skill for freshness truth; `web-accessibility` for complete AT audit | keep authority distinct |

To verify activation for real, run blinded prompt-selection evaluations including these cases, score false positives/negatives, inspect actual agent traces, and document results. These tests are **NOT EXECUTED** in this package.
