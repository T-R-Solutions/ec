# Scope-aware search and findability contracts

> Commands, fixture paths and historical execution claims from the source package are illustrative or imported evidence unless the corresponding files are present and independently checked; they are not current test results or bundled executable tools.

Information architecture defines **what** users need to discover and **which scope** applies. Algorithms, indexes, indexing latency, ranking models and backend authorization enforcement belong to search/API/security engineering. Never infer user relevance from a test-only deterministic sort.

## Search contract checklist

- **Scope selector:** global vs workspace/project; default is explicit. Changing context must re-scope results, counts and facets and may reset incompatible filters.
- **Query terms:** user-visible input persisted across a results page, aliases/synonyms documented and tested; locale-aware normalization where relevant.
- **Facets:** type, owner, project, status; show only allowed values. Invalid/deleted facets have a recoverable state. Define whether counts reflect filtered, authorized results.
- **Results:** identifiable canonical resource, type, parent scope, matching explanation or useful snippet (if supported), permitted action and deep link.
- **States:** loading, empty, no index, error, partial/stale results, permission denied, timeout, zero results, suggestion and clear-filter recovery. Do not present “nothing exists” when only results were inaccessible.
- **Privacy:** no unauthorized item through query completion, suggestions, labels, counts, facet values, snippets, caching or direct URL errors. Hiding a navigation link is not authorization.
- **Keyboard:** search is reachable and named; submit, results and feedback follow accessible form/navigation patterns. A result link must provide a meaningful accessible purpose.
- **History:** encode useful search state in a shareable URL where appropriate. Back/forward restores context and filter state; links survive permitted lifecycle transitions.

## Falsifiable tests

Query an exact title and a synonym; verify expected canonical ID. Query a non-match; verify recovery. Apply a facet; verify all results satisfy it. Switch workspaces; check no cross-scope result. Simulate a viewer and an administrator **on the server side**; verify no restricted result metadata is returned for the viewer. Query a removed route; verify safe error/redirect behavior. Test locale/diacritics as applicable. Measure relevance with real queries and judgments if claiming quality.

**Fixture distinction:** `validation/fixtures/catalog.yaml` and Python `search(...)` implement a toy weighted-title/synonym matcher. It is a behavior oracle for this fixture only; it is not an endorsed production ranking strategy and it is not a backend security implementation.

Source: [USWDS Search](https://designsystem.digital.gov/components/search/), [W3C WCAG 2.2 Multiple Ways](https://www.w3.org/WAI/WCAG22/Understanding/multiple-ways).
