# Global, local, and contextual navigation

## Three questions

At every meaningful destination, users must know: **where am I? what can I do? how do I move within the relevant scope?** Maintain distinct but coordinated global navigation, local context (team/project/workspace) and the focused task surface. Never fill a global sidebar with every nested resource; consider the density and evidence for how often users cross areas.

### Navigation mechanisms

| Pattern | Suitable use | Misuse to avoid |
|---|---|---|
| Global navigation | Major cross-product areas | Every individual project/pipeline as a global first-level item |
| Section navigation | Related tools within a selected scope | Items from a different scope with no warning |
| Hierarchical tree | True nested containment | A procedural dependency DAG represented as parent/child |
| Breadcrumbs | Current hierarchy path and upward links | Recent click history or a wizard's step tracker |
| Contextual links | Related or downstream tasks | Hidden alternate access to unauthorized resources |
| Search / command palette | Known-item and cross-area retrieval | Replacing recognizable routes with undiscoverable commands |
| Tabs | Alternate views of same entity/context | Routing unrelated areas into tabs until unmanageable |

Map alternative entry points: start from system overview, project detail, deep link, run notification, audit event or search result. Show context and destination explicitly; avoid assuming navigation always begins at home.

## Selection and responsive behavior

Use semantic labels and current-state indicators. Collapsing sections for small viewports must not silently remove required destinations. In-page navigation and local navigation must preserve hierarchy and focus. Consult an available CSS, routing or accessibility specialist only for an unresolved implementation question. Any choice between wide/shallow and narrow/deep must be justified by tested tasks, not fixed folk rules.

## Review checks

Can a user distinguish global and local state? Does moving between workspaces update search/breadcrumb scope? Are entity labels unambiguous? Is a deep link self-locating? Are error, no access and unavailable screens recoverable without fabricating a destination?

Sources: [W3C WAI Navigation Design](https://www.w3.org/WAI/curricula/designer-modules/navigation-design/), [USWDS side navigation](https://designsystem.digital.gov/components/side-navigation/), [W3C WAI Menus](https://www.w3.org/WAI/tutorials/menus/).
