# Hierarchy Model, Inheritance and Mutation Design Notes

> Use the destination's existing requirements and evidence format; named workflow documents are examples, not prerequisites. Optional SDD mapping is configured outside this skill.

This is an **engineering reference**, not a new Ec schema. Names and examples below are abstract. The Ec YAML schema and access policies must be approved in project workflow and resolved in the actual product contract before dependent application code is generated.

## 1. Distinct relations and invariants

Use separate conceptual relations:

- `project_parent(child, parent)` — acyclic containment in an explicitly defined administrative scope; at most one direct parent when a tree is chosen.
- `project_resource(project, resource, relation)` — stable association to repository/pipeline/template/pool/integration or another resource.
- `team_member`, `role_assignment`, `effective_permission` — separate identity and authorization relations, **not** aliases of `project_parent`.
- `configuration_revision(project/resource, immutable_revision)` — desired configuration identity, **not** a current build/lease/session.
- `execution_provenance(run, source_revision, configuration_revision)` — historical operational identity, unaffected retroactively by a project move.

Recommended explicit checks, with exact policy/constraints sourced from approved Ec project workflow: no duplicate stable ID; no dangling direct parent; no self-parent; no directed cycle; valid declared scope; known schema version; no unstable reference rewritten silently; unchanged historical provenance. A parent pointer's foreign key does **not** prove acyclicity.

`Ancestors(p)` walks parents toward the root. `Descendants(p)` walks child edges. A move of `p` under `q` is invalid when `q = p` or `q ∈ Descendants(p)`. Reject on candidate graph, and repeat that check at the effective commit/authority boundary with fresh concurrency controls. For scope transfers, provide an explicit migration contract; do not guess that a root or organization can be crossed.

## 2. Inheritance is not one global union

Create an inheritance specification for **each family** before implementing resolution. Illustrative table; every row needs an actual Ec decision:

| Family | Possible techniques | Security constraint / provenance question |
|---|---|---|
| Non-sensitive display defaults | Nearest-ancestor wins; explicit local override | Return origin and effective value; missing value is not fabricated |
| Build variables/parameters | Merge or override with typed validation | Which names inherit? Are secrets always external references? |
| Repositories/integrations | Explicit association; optionally inherited reference | Scope, credential availability, trust and revocation must be checked |
| Pipeline templates | Explicit versioned reference or approved inheritance | Preserve revisions and inherited template source |
| Agent pools/quotas | Policy-checked availability or bounded inheritance | A move must not silently add capacity/privilege |
| Authorization policies and role bindings | External access engine with policy-specific semantics | Do not model as simple nearest-ancestor override or unqualified union |
| Project-level security constraints | Enforce/merge/reject according to approved policy | A child must not weaken protected ancestry constraints by implication |

An effective value is preferably `(resolved value, origin ID, source revision, rule ID)`. A UI can display a direct value, an inherited value and the reason for a blocked override. That computation belongs to the appropriate domain handler; this skill coordinates the provenance and impact query.

## 3. Safe change protocol (method, not publication implementation)

1. Read the **same** immutable base desired revision for source, destination, descendants, local settings, attachments and reference map; record the current policy/authorization revision.
2. Generate the candidate tree and apply the invariant checker. A stale or incomplete projection must not validate unknown descendants as absent.
3. Compute an *old vs candidate* impact map for **every affected descendant**: provenance changes, gained/lost effective access from the real permission engine, team associations, agent/pool eligibility, template and repository availability, URLs/paths, webhook destinations, quotas, history references and sensitive operations.
4. Require fresh server-side authorization for source **and** target and action; limit scopes and provide policy-based approval. Bind confirmation to the exact candidate digest and base/policy identities. Treat unresolved security-impact data as a blocker, not an empty diff.
5. Hand the candidate to `ec-declarative-resource-schemas` validation and `ec-configuration-publication` for exact candidate approval, CAS publication and reconciliation. A SQL projection update or client-tree redraw is not evidence of durable YAML publication.
6. Expose `draft`, `published`, `reconciling`, `synced` and `unknown` only when the appropriate backend-confirmed predicates hold. Historical executions retain their originally pinned revisions.

**Rename:** preserve stable ID if the schema defines it separately from a slug; check path aliases and inbound references. **Copy:** mint new IDs where required, state whether child resources and secrets are copied, and never silently copy run history. **Archive:** define retention and behavior of active runs and inbound references. **Delete:** require explicit dependency policy (block, migrate, or approved cascade); do not infer cascade from a UI click. **Reparent:** preview both loss and gain of inherited access and values.

## 4. Data-structure alternatives and experiments

| Option | Read pattern | Subtree relocation cost/risk | Start decision |
|---|---|---|---|
| Parent adjacency with recursive CTE | Traverse ancestors/descendants with `WITH RECURSIVE`; index parent and scope as needed | Single canonical parent edge but query/index/cache invalidation; cycle checks must be enforced | Practical baseline; not automatically optimal |
| Materialized path / `ltree` | Prefix ancestor/descendant filters and GiST indexes | Rewrites descendant paths for a moved subtree; identity must not equal path | Evaluate after measuring query vs move frequency |
| Closure relation `(ancestor, descendant, depth)` | Fast ancestor/descendant access from indexed closure | O(subtree × relevant ancestor) maintenance and concurrency complexity (depending on design) | Only if read pressure justifies upkeep |
| In-memory dynamic tree (e.g. link-cut) | Useful for specialized link/cut path aggregates | Adds complex state/projection consistency; theoretical time bound does not include persistence and access recalculation | Do **not** choose without verified workload and proven need |

Benchmark with fixed fixture sizes **defined before measurement** (e.g. balanced and skewed trees, tens to thousands of nodes), realistic read/move ratios, warmed vs cold cache, authorization recomputation, and p50/p95 query and operation latency. These are test *designs*, not claimed targets or observed Ec results. Track transaction conflicts, stale indexes, and cost of moving a large subtree.

## 5. Concurrency caveat

A PostgreSQL transaction can atomically change a database representation but **cannot** atomically update an external canonical Git revision by itself. `SERIALIZABLE` can abort and requires complete replay; row locks alone may miss a concurrent change to another ancestor unless the lock strategy covers the topology read set. Advisory locks are cooperative. When desired state lives in Git, the publication skill's durable CAS/revision boundary decides success; database tree indexes are derived. Never conflate a successful in-memory move or SQL commit with published YAML.

## 6. Accessibility and state feedback

Tree navigation should follow the WAI-ARIA treeview pattern where appropriate; distinguish keyboard focus from selection. Provide selection plus explicit **Move to project...** controls as alternatives to drag, including a single-pointer route; keyboard-only support does not by itself satisfy WCAG 2.5.7. Show old/new locations and a substantive permission/inheritance impact preview before committing. Do not claim move success before the server confirms the relevant publication or application state. A narrow viewport may use a list/selector rather than an illegibly scaled tree.
