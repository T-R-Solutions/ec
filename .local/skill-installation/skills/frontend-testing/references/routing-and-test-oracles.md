# Skill activation and selection oracles

> Ec-specific rules below apply only to an Ec consumer whose current contract establishes them. Commands, fixture paths and historical execution claims from the source package are illustrative or imported evidence unless the corresponding files are present and independently checked; they are not current test results or bundled executable tools.

| Request | Selection outcome | Rationale |
|---|---|---|
| “Write Vue Test Utils tests for async component and its 409 message” | **Activate** | Frontend component tests, pending async UX, negative observable outcome |
| “Use Playwright to reproduce a stale response after changing workspaces” | **Activate** | Browser user-flow oracle and race verification |
| “Is the frontend CI flaky because test cases share browser state?” | **Activate** | Test isolation, diagnostics and reruns |
| “Define all acceptance criteria for the app's permission policy” | **Do not activate** | Product specification/authorization owner; test method cannot invent norms |
| “Implement scheduler leases and fencing in Rust” | **Do not activate** | CI/CD scheduler and distributed-systems domain, not browser testing |
| “Choose the company's universal testing pyramid ratio” | **Do not activate** | General test strategy and organizational goals, `software-testing` |
| “Fix an ARIA violation on a dialog, without writing tests” | **Do not activate as primary** | `web-accessibility` and component implementation own the fix |
| “An E2E test with mocked 403 passes, does this certify server RBAC?” | **Borderline: activate for evidence critique**, reject the certification | Browser mock tests presentation, not server authorization |
| “A form saves but loses unknown YAML extension fields in the actual Ec editor” | **Borderline: frontend-testing can implement cases** | Product semantics come from `ec-semantic-configuration-projections` and approved project contract |

## Agent evaluation plan

Supply 3 positive, 3 negative and 2 borderline prompts to a real coding agent, with the skill index and competing skill descriptions. Score activation precision/recall, correct authority selection and no unapproved claim of a test passing. These cases are **designed but not executed** in this package.
