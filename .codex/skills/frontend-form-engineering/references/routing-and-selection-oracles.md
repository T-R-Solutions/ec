# Skill routing oracles (for agent evaluation)

> Ec-specific rules below apply only to an Ec consumer whose current contract establishes them.

The following are proposed selection scenarios. **An agent-routing experiment has not been run.** The presence of cases in this file is not evidence that a model selects skills correctly.

| Type | User task | Expected selection | Rationale |
|---|---|---|---|
| Positive | “Build an editable settings form with a dirty baseline and reset behavior.” | `frontend-form-engineering` primary | Draft lifecycle |
| Positive | “Server field errors appear on the wrong controls; fix mapping and summary.” | `frontend-form-engineering` primary | Error mapping and accessible field recovery |
| Positive | “Submitting twice sends two unsafe requests, and stale saves overwrite new edits.” | `frontend-form-engineering` primary | Async concurrency |
| Positive | “Retain advanced values when a section collapses, unless disabled by the spec.” | `frontend-form-engineering` primary | Dynamic field preservation |
| Positive | “Implement a repeatable multi-row form with stable error IDs.” | `frontend-form-engineering` primary | Repeated input and keyed validation |
| Negative | “Which tokens and colors should Ec forms use?” | `design-system` | Styling authority, not form logic |
| Negative | “Make this form fit mobile using CSS Grid and Container Queries.” | `css-layout-engineering` | Layout engineering |
| Negative | “Define and migrate the canonical Ec Project YAML schema.” | `ec-declarative-resource-schemas` | Product contract |
| Negative | “Create YAML ↔ canvas ↔ form round-trip semantic equivalence tests.” | `ec-semantic-configuration-projections` | Product-wide projection contract |
| Negative | “Design RBAC policy evaluation and prevent privilege escalation.” | `ec-effective-permissions` / `application-security` | Authorization, not form UX |
| Negative | “Define HTTP 409 response structure and versioning.” | `http-api-contracts` | API design |
| Negative | “Audit WCAG AA conformance of the entire dashboard.” | `web-accessibility` | General accessibility audit |
| Boundary | “Make user management screen more accessible.” | Form skill only if field behavior/error/focus, otherwise `web-accessibility` | Resolve scope before activation |
| Boundary | “Improve a form that has a nice layout but users can't find which field failed.” | `frontend-form-engineering` primary; accessibility supplemental when needed | Form error behavior is central |
| Boundary | “After editing a project form, unknown YAML fields disappear.” | Form skill for patch mechanics; Ec schema/projection authority must be consulted | No independent YAML model |
| Boundary | “I clicked Save, but resource is pending approval.” | `ec-configuration-publication` primary if publication lifecycle is the question | Form must display confirmed semantic state, not invent workflow |
| Boundary | “Build a Vue component wrapping a complex form input.” | `vue3-engineering` primary if component mechanics dominate; form skill when state/validation dominates | Avoid duplicate specializations |

## Scoring in an actual evaluation

Freeze the agent/model version and complete candidate skill inventory. Score selection/abstention, necessary reference reads, separation of authorities, preservation of product norms and implementation evidence. Include paraphrases, difficult composites and negative controls. Report denominator and failures only when actually observed; do not claim any observed agent performance from this table.
