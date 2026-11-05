# User research, tree testing and findability metrics

## Distinguish methods

| Method | What it can test | What it does not prove |
|---|---|---|
| Open card sorting | Perceived clusters, candidate labels, users' vocabulary | Task efficiency on a working product |
| Closed card sorting | Fit of items to proposed categories | Search relevance or navigation speed |
| Tree testing | Whether a text-only hierarchy leads to a target under task prompts | Usability of full UI, visual affordances, permissions, search |
| First-click testing | Initial direction choice and label interpretation | End-to-end task completion |
| Moderated usability sessions | Behavior, success, failure reasons and context | Population-wide performance from a small convenience sample |
| Instrumented real flows | Real routes, queries, task attempts when defined | User intent if telemetry is ambiguous; accessibility by itself |
| Synthetic browser/graph checks | Route/label/permission-projection invariants on test data | Human findability or scientific benchmark success |

## Example tree/task study plan

Recruit people representative of the target roles and experience levels; record consent, accessibility accommodation, recruitment method, locale and task familiarity. Define non-leading tasks with known correct destinations, starting entry points and permitted capabilities. Run both structure-only tasks and prototype tasks if the hypothesis claims an improved UX. Randomize order when appropriate and report learning/order effects. Avoid claiming a fixed participant count is universally sufficient. GOV.UK's research guidance offers practical methodology, but its suggested sample sizes are not power analyses for all studies.

## Candidate metrics

- `task_success = successful completed tasks / observed attempted tasks`, with task and sample definition.
- `first_choice_correct`: initial menu selection where an unambiguous correct path is defined.
- `wrong_turn_count`: detours or incorrect decisions under a specified coding scheme.
- `time_to_goal`: elapsed time from start to verified destination with exclusions and censoring policy.
- `search_reformulation`: query rewriting count; distinguish empty-result recovery from normal exploration.
- `scope_confusion`: wrong workspace/project selected or wrong dataset interpreted as current.

Specify numerator/denominator, confidence, cohort, observation window, consent/privacy, instrumentation, test revision and statistical caveats before reporting. No preset "findability score" or "maximum three clicks" threshold is evidence of adequacy. Compare before/after only under sufficiently comparable conditions, and do not generalize beyond studied tasks/users.

Sources: [GOV.UK moderated usability testing](https://www.gov.uk/service-manual/user-research/using-moderated-usability-testing), [Schmettow & Sommer 2016](https://doi.org/10.1080/0144929X.2016.1157207), [Wentzel et al. 2016](https://doi.org/10.1016/j.ijmedinf.2015.12.003).
