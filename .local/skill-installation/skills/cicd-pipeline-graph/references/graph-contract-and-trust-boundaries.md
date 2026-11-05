# Graph contract and trust boundaries

> Ec-specific rules below apply only to an Ec consumer whose current contract establishes them.

## Input identification checklist

Determine graph authority and representation *before* processing: resource revision, project/pipeline scope, stable node IDs, whether edges refer to jobs/stages/steps, dependency resolution, conditional dependency semantics, allowed empty graph, allowed disconnected components, case sensitivity, Unicode normalisation, resource limits and permission to edit.

In the **generic fixture only**, each row is `{id: string, needs: string[]}`. All items are in one namespace. An edge from `compile` to `test` is represented by `test.needs = ['compile']`. This fixture format is neither Ec YAML schema nor an alternative product configuration.

Invalid examples (conceptual): `test.needs=['missing']`, duplicate `id`, duplicate prerequisite, `a.needs=['a']`, cycle `a → b → a`. Never auto-create `missing`, deduplicate, discard an unknown field, invert edges or accept a cycle just to display a graph.

Data ingestion can be untrusted; limit nodes/edges/identifier length/degree before generating adjacency. Do not include authorization tokens, secrets or arbitrary shell commands in diagnostics. Parsing and authorization remain the responsibilities of the consuming application and its approved resource contract.

IDs and collation: Python string ordering, JS UTF-16 default sorting, ICU/locale collation and bytewise Unicode order are not interchangeable. If cross-language deterministic order is required, the spec must name an algorithm and normalize/compare stable identifier bytes consistently; these fixtures use ASCII IDs for cross-language checks.

Input authority: Ec always uses YAML for desired configuration, with the three views as semantic projections; observed run state, queue and leases stay elsewhere. A graph library cannot publish YAML or authorize an edit.
