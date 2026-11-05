# Resource inventory, taxonomy, and relationship contracts

## Model before menus

Identify canonical IDs, user-facing labels, kinds, parent hierarchy, ownership, scope, state, actions, ACL reference, discoverability status and alternative entry paths. Do not derive identity from translated display strings. Distinguish:

- **Hierarchy** (`parent_of`): parent-child containment, used for navigation and hierarchical breadcrumbs.
- **Associations** (`related_to`): a pipeline may reference an environment; this is not proof one is the other's child.
- **Sequence** (`precedes`): steps in a procedure or workflow; not a breadcrumb parent relationship.
- **Facets** (`has_property`): owner/status/type/tag; faceted retrieval, not new permanent navigation levels.
- **Authority** (`can_read`/`can_administer`): belongs to access subsystem, not to menu filtering alone.

## Structural invariants

1. Stable unique identities per taxonomy version; unique canonical route within appropriate URL scope.
2. Named section categories with documented inclusion/exclusion rules; ambiguous cross-category items must have intentional primary placement plus contextual links.
3. No unintended hierarchy cycles, missing parent references or dead-end targets; alias/redirect changes require collision checks.
4. Resource type and project/workspace scope remain distinguishable in the page title, breadcrumb and current navigation state.
5. Navigation must not assert non-existent actions or enabled features. A draft feature in a roadmap is not a published route by default.
6. Permission-based visibility and zero-result counters use an authorized backend projection. Use an explicit hidden/forbidden resource policy and test it.

## Grouping experiments

Card sorting is one method to examine conceptual similarity and label vocabulary, not a machine that generates an objectively correct sitemap. Consider open sorting to discover categories and closed sorting to compare proposed structures. Record participants, cards, exact wording, moderator effects, ties, unstable categories and disagreements. In hierarchical navigation, **tree testing** or live task observation provides a more direct outcome measure.

A minimal inventory table: `canonical_id | kind | parent_id | scope_id | display_label | allowed_roles | preferred_route | synonym_tags | related_ids | lifecycle_state | evidence_ref`. This is a *documentation aid*, not a second source of desired application configuration.

Sources: [Wentzel et al. 2016](https://doi.org/10.1016/j.ijmedinf.2015.12.003), [Schmettow and Sommer 2016](https://doi.org/10.1080/0144929X.2016.1157207).
