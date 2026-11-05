# Detailed decisions and failure cases

Read only for a decision needing more detail than the entry point. Apply the relevant steps to the
actual task; the sections are not a mandatory artifact or test checklist. Use existing project
contracts in their actual format. File names such as spec.md, plan.md or tasks.md are examples, not
required dependencies. Other skills are optional expertise, never enforcement mechanisms.

Product rules, stack choices and authority locations must be verified in the Ec consumer. Examples
do not establish deployed capabilities or override its current contract.

## Decision procedure

1. **Map entities and scope before choosing a tree representation.** Identify immutable/stable
   project IDs, mutable display names and slugs, parent references, hierarchy roots,
   ownership/tenant boundaries (if any), attached repositories/pipelines/pools/templates/policies,
   actual desired YAML files and effective revisions. Do not equate project ancestry with team,
   role, pipeline or Git repository ancestry. If multi-tenant isolation, root immutability, depth
   limits, sibling naming uniqueness, or project IDs are not specified, record them as decisions,
   not established Ec facts.
2. **Write independent invariants and a hierarchy oracle.** For each supported tree require valid
   parent references, no self-parent/cycles, unique stable identities, consistent
   ancestor/descendant traversal, valid declared boundaries, and repeatable projected hierarchy.
   Define a source-of-truth hierarchy in canonical desired YAML; any SQL closure, path, search index
   or UI tree must be reconstructible from its effective revision. State what happens with missing
   parents, dangling attachments and partial imports. Reject malformed proposed topology before
   publication; distinguish an invalid *draft* from an effective state.
3. **Specify inheritance separately for each resource family.** For each parameter, template,
   connection, policy, credential reference, pool eligibility and role binding, document
   `inherits?`, source/precedence, override or block behavior, non-inheritable constraints and
   sensitive changes. Resolve a setting with provenance (`value`, `origin project`, `policy/schema
   revision`) instead of silently flattening ancestry. **Never assume** that a project-local
   override weakens an ancestor security policy, that every child inherits secrets, or that project
   ancestry automatically grants authorization. Authorization policies and effective permission
   calculation are delegated to `ec-effective-permissions`.
4. **Compare storage/query alternatives with measured requirements.** Start from a simple adjacency
   representation and recursive traversal as a baseline; compare an `ltree`-style materialized path,
   closure table or cached ancestor index only for stated read/write profiles. Analyze subtree
   moves, ancestors, descendant counts, depth, tree sizes, indexing, correctness and rebuild costs.
   Do not import sophisticated dynamic-tree algorithms or prescribe `ltree` by analogy without
   representative benchmarks. Never make an operational PostgreSQL projection an alternative
   desired-configuration authority.
5. **Treat move, rename, copy, archive and delete as distinct operations.** Construct a
   change-specific impact preview at the same candidate/base revision: affected subtree, new/removed
   inherited values, gains/losses of access (ask the actual authorization engine), changed
   agent/pool eligibility, broken references, hooks/webhooks, paths/URLs, environments, policy
   applicability and dependent jobs. Keep stable project IDs and historical run provenance unless an
   approved migration contract explicitly changes them. For copies, distinguish duplicated
   configuration from independent execution history, credentials and protected references. For
   archive/delete, specify retention, reversibility, blockers and external dependencies; no
   automatic cascading removal on a guess.
6. **Authorize and apply hierarchy mutations against fresh state.** Validate the actor's source
   scope, destination scope and action separately; preview access does not authorize the mutation.
   Bind review to candidate graph digest, base revision, actor, target, affected IDs and policy
   revision. Before committing, revalidate source/destination permissions and detect concurrent
   moves, stale parents, policy changes, and self-referential or cross-scope results. Apply the
   desired change only through the approved YAML validation/authorization/publication workflow
   (`ec-configuration-publication`); do not directly change PostgreSQL and call the config
   published. Return actionable conflict/impact information without claiming a durable write until
   confirmed.
7. **Design the three editing surfaces as one operation.** A tree move may originate in a visual
   tree, accessible move controls, form parent selector or YAML. All produce the same semantic
   candidate and impact evaluation. Provide keyboard navigation and a single-pointer alternative to
   dragging; present the old/new parent, access exposure and irreversible effects before
   confirmation. Use `ec-semantic-configuration-projections` for lossless view-edit laws and
   `web-accessibility` for full accessibility audits, rather than duplicating their methods here.
8. **Verify structural, security and operational consequences.** Exercise cycles; deep/wide trees;
   missing parents; conflicting slugs (if defined); crossing scope boundaries; inherited setting
   precedence; grants removed/added after reparenting; stale auth snapshots; concurrent moves;
   external reference/path failures; restart/rebuild of derived indexes; preservation of historical
   builds; and accessibility of move alternatives. Use model-based/property testing plus actual
   YAML/parser, PostgreSQL, API, UI and agent tests when available. Label simulated outcomes
   **synthetic**, test plans **unexecuted**, and unknown product contracts **unresolved**. Record
   commands, evidence and project acceptance IDs.


## Further references by decision

Read only the reference relevant to the unresolved decision. Research registers describe imported authorship, not current execution.

- [Hierarchy Model, Inheritance and Mutation Design Notes](hierarchy-model-and-inheritance.md): hierarchy model and inheritance.
- [Routing and Verification Oracles — Ec Project Hierarchy](routing-and-test-oracles.md): routing and test oracles.
- [Source Ledger and Engineering Decisions — Ec Project Hierarchy](sources-and-decisions.md): source provenance or a disputed recommendation.
