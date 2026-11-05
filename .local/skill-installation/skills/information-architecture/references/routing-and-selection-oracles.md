# Skill selection positive, negative and boundary oracles

> Ec-specific rules below apply only to an Ec consumer whose current contract establishes them. Commands, fixture paths and historical execution claims from the source package are illustrative or imported evidence unless the corresponding files are present and independently checked; they are not current test results or bundled executable tools.

These cases test *human-readable routing logic*; they do not claim an automated agent selection experiment was executed.

| Case | User request | Correct routing decision and boundary |
|---|---|---|
| Positive | “Users can't tell whether they are in global settings or a project. Redesign the navigation structure.” | **Activate** `information-architecture`; model global vs contextual scope and tasks. UI implementation skills may be selected separately if needed. |
| Positive | “Our support logs show many failed searches for execution history. Improve labels and discovery paths.” | **Activate**; collect provenance and test synonyms/tasks, not assumed ranking performance. |
| Positive | “We moved project pages; old bookmarks now lead to wrong scopes.” | **Activate** for IA and migration contracts; router/backend specialists implement URLs. |
| Positive | “Design and evaluate a discoverable hierarchy for 200 resource categories.” | **Activate**; inventory, grouping hypotheses, tree/task tests and rollout plan. |
| Negative | “Make sidebar padding narrower and change the border color.” | **Do not activate**; CSS/design-system is primary. |
| Negative | “Fix Vue Router pushState errors without changing destination taxonomy.” | **Do not activate**; `frontend-routing`. |
| Negative | “Implement OAuth token exchange and backend session verification.” | **Do not activate**; `identity-federation` / security. |
| Negative | “Tune full-text search index throughput and query plans.” | **Do not activate**; search/backend/performance engineering. |
| Boundary | “Improve the sidebar.” | **Clarify/inspect context**: label/grouping/findability triggers this skill; pure visual geometry does not. |
| Boundary | “Add breadcrumbs to wizard step 3.” | **Activate only for IA critique**, explain progress indicator vs hierarchy; do not blindly add breadcrumb. |
| Boundary | “A user can see an admin-only result in search.” | **Activate for discovery/privacy contract** but escalate actual authorization bug to backend/security specialist. UI hiding is insufficient. |
| Boundary | “Link pipeline stages in a graph.” | **Do not activate** for DAG semantics; use pipeline graph/authoring domain unless the question is where users navigate among resources. |
| Boundary | “Set up the Ec YAML resource tree.” | **Do not activate** to define YAML schema; relevant Ec resource skill owns the contract. |

A structured selection test in a real agent requires task prompts, available competing skill descriptions, scoring rubric and observed decisions. Those tests are proposed only in `validation/agent-evaluation-plan.md` and must be marked `NOT EXECUTED` until run.
