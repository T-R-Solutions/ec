# Detailed decisions and failure cases

Read only for a decision needing more detail than the entry point. Apply the relevant steps to the
actual task; the sections are not a mandatory artifact or test checklist. Use existing project
contracts in their actual format. File names such as spec.md, plan.md or tasks.md are examples, not
required dependencies. Other skills are optional expertise, never enforcement mechanisms.

Ec references below are conditional consumer examples. They impose no Ec documents, YAML format,
stack or product policy on a generic task.

## Method

1. **Establish the question and authority.** Identify the real findability defect or proposed
   feature. Record current state vs planned functionality. Consult the product spec and applicable
   norms **before** selecting a navigation pattern. List the permitted personas, scopes,
   destinations and actions; never derive authorization from the UI or guessed labels.
2. **Inventory and model.** Enumerate resources, meaningful tasks, stable IDs, owners,
   relationships, lifecycle and available user-facing destinations. Separate hierarchy (*belongs
   to/parent of*) from adjacency (*related to*), filters (*has property*) and sequence (*next
   step*). Avoid confusing task pipelines or DAG dependencies with content navigation. See
   [inventory and taxonomy](content-model-and-taxonomy.md).
3. **Gather user evidence with limits.** Compare user vocabulary, actual paths, search queries,
   terminology and known trouble spots. Use open/closed card sorting **only** to explore conceptual
   grouping, never as automatic proof of faster navigation. Conduct tree tests or task-based
   prototype studies, including realistic users, for outcome validation; record sampling and
   uncertainty. See [discovery](discovery-and-evidence.md) and [scientific
   limits](scientific-evidence-and-limits.md).
4. **Propose an IA with alternatives.** Specify the top-level sections, per-scope local navigation,
   object detail pages, context preservation, multiple access paths and intentional cross-links.
   Choose tables/trees/search/filters/timelines based on task and entity relationships, not a
   universal sidebar. Model permissions and unavailable states without leaking protected resource
   names or counts. Present at least one plausible alternative for a consequential restructuring and
   explain trade-offs (breadth/depth, clarity, cost, migration). See
   [navigation](navigation-and-context.md).
5. **Test labels and information scent.** Use user terms, meaningful verbs/nouns, consistent
   category definitions and clear disambiguation between similarly named entities. Prefer stable
   destinations and visible context over clever abbreviations. Test what people *expect* to find
   under labels; don't impose "seven items maximum" or a fixed click-count target without actual
   task evidence. See [labels](labeling-and-information-scent.md).
6. **Define search and discovery paths.** Specify scope (global vs local), default scope,
   synonym/alias handling, query persistence, facets, result identity/context,
   empty/error/permission states, keyboard entry and recovery routes. Document privacy boundaries:
   queries, suggestions, counts and facets must not reveal unauthorized resources. Search result
   rank is an IR engineering decision requiring separate evidence. See
   [search](search-and-findability.md).
7. **Specify navigation contracts.** Define canonical URLs, breadcrumbs as hierarchy (not browser
   history), stable IDs, route migrations/redirects, changes to parents/labels, deep-link behavior,
   browser back/forward and focus expectations. Show global vs resource scope in
   title/navigation/context. Distinguish a parent-child relationship from a wizard's progress. See
   [URLs](migration-and-url-governance.md) and [wayfinding](wayfinding-and-accessibility.md).
8. **Write falsifiable acceptance criteria and negative cases.** For each task: persona, starting
   point, discoverable path, destination ID, expected scope, alternative route, handling of
   missing/forbidden resources and evidence method. Include incorrect/faulty-tree, ambiguous label,
   wrong-scope result, stale bookmark, missing breadcrumbs, invalid filter, keyboard-only navigation
   and access-denied probes. Bound metrics to observations and baselines; never claim universal
   success from a static sitemap. See [oracles](acceptance-contracts-and-reporting.md).
9. **Evaluate with appropriate layers.** Check structural graph invariants, clickable
   prototype/browser behavior, tree-testing/task-completion observations, accessibility heuristics
   and assistive-tech evaluation where relevant. Report exact tool versions, tested
   URLs/revisions/viewport, sample composition, measures, failures and limitations. A synthetic
   fixture passing is **not** usability validation or product conformance. See
   [experiments](experiments-and-metrics.md).
10. **Deliver a transition plan.** Output proposed IA map, task-to-destination matrix,
    navigation/search contracts, label dictionary, before/after migration strategy, negative
    oracles, results, unresolved hypotheses and owned decisions. Keep the project workflow feature
    spec as the behavioral authority; local improvements may use the bounded local route instead of
    creating artificial paperwork. optional integration is configured separately by the consumer.


## Correctness rules

- A taxonomy or sitemap is a **candidate model**, not proof that users can find content. Validate
  representative tasks under the target environment. Card-sorting findings and task performance can
  disagree (see empirical references).
- Every canonical destination should have a stable semantic identity; renaming a visible label
  should not silently rewrite the resource's identity or break external links. Parent changes and
  broken links require a migration plan; don't assume aliases are free of ambiguity.
- Search discovery must respect the same scope and authorization rules as direct navigation;
  **hiding** a link alone is not access control. The backend must verify authorization. Do not
  expose protected names through suggestions, aggregations or “no access” counts.
- Multiple ways to reach a page are useful and can be required under WCAG 2.2 SC 2.4.5; its
  exception for pages that are a result or step in a process still applies. Repeated navigation
  order and identifiers must respect applicable WCAG 2.2 AA criteria. W3C informative tutorial
  examples and USWDS patterns are **guidance**, not new WCAG requirements.
- Breadcrumbs express the *content hierarchy* rather than the route a user took. A wizard's progress
  is not a breadcrumb. A single content item may be accessible via contextual links without
  inventing multiple canonical parents.
- Metrics such as completion success, first correct choice, time on task, wrong turns and search
  reformulations are valid **only with a defined task, cohort, sample size, period and exact
  collection method**. No invented thresholds or benchmark claims.
- Treat labels as user-facing vocabulary, not stable authorization keys. Internationalization may
  require localized labels while IDs and paths remain stable by documented policy.
- Respect design, accessibility and configuration authorities of the consuming product. For Ec
  specifically, canonical declarative configuration is YAML; this general skill never introduces a
  competing configuration source or infers implementation from a mockup.


## Further references by decision

Read only the reference relevant to the unresolved decision. Research registers describe imported authorship, not current execution.

- [Falsifiable acceptance criteria and evidence reporting](acceptance-contracts-and-reporting.md): acceptance contracts and reporting.
- [Resource inventory, taxonomy, and relationship contracts](content-model-and-taxonomy.md): content model and taxonomy.
- [Discovery, user tasks, and evidence discipline](discovery-and-evidence.md): discovery and evidence.
- [User research, tree testing and findability metrics](experiments-and-metrics.md): experiments and metrics.
- [Labels, categories, and information scent](labeling-and-information-scent.md): labeling and information scent.
- [Canonical destinations, URLs and IA migration](migration-and-url-governance.md): migration and url governance.
- [Global, local, and contextual navigation](navigation-and-context.md): navigation and context.
- [Skill selection positive, negative and boundary oracles](routing-and-selection-oracles.md): routing and selection oracles.
- [Peer-reviewed evidence and its limits](scientific-evidence-and-limits.md): source provenance or a disputed recommendation.
- [Scope-aware search and findability contracts](search-and-findability.md): search and findability.
- [Source register, authority classification and technical decisions](sources-and-decisions.md): source provenance or a disputed recommendation.
- [Wayfinding, current location and applicable accessibility requirements](wayfinding-and-accessibility.md): wayfinding and accessibility.
