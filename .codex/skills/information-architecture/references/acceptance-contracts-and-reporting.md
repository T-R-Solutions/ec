# Falsifiable acceptance criteria and evidence reporting

> Use the destination's existing requirements and evidence format; named workflow documents are examples, not prerequisites. Optional SDD mapping is configured outside this skill.

## Acceptance contract fields

`REQ/AC ID | user task | role + scope | starting context | allowed resources | expected target ID | navigation/search path | alternative path | states/errors | test method | observed evidence | verdict`.

Example (illustrative; not a product feature claim):

```text
REQ-IA-01: A member in workspace Atlas can locate the pipeline catalog.
AC-IA-01a: From Atlas project overview, a labeled navigation route reaches the
             canonical Atlas pipeline catalog without changing workspace scope.
AC-IA-01b: A direct deep link identifies the current workspace/category/page.
AC-IA-01c: "builds" search alias returns the same authorized catalog item.
AC-IA-01d: A member searching from Atlas never receives Boreal-only items.
AC-IA-01e: A member cannot discover administrator-only item metadata through
             navigation, result counts, facets or autocomplete.
Evidence: executable browser model or product test with actual roles/API plus
          task sessions; the former alone is not user findability acceptance.
```

## Negative oracles to write before approval

- Duplicate canonical route or conflicting alias must be rejected.
- Unknown parent/scope, dangling ancestor or hierarchy loop must be rejected.
- Wrong-scope search must not return another project's resource; repeated labels require context.
- Forbidden resource metadata must not leak through search or direct navigation; backend authorization is authoritative.
- Deep link, browser back/forward, renamed label and changed-parent navigation preserve or handle context deliberately.
- Query/filter reset, empty state, keyboard alternative and link semantics must remain operable.
- Redirect collision, orphaned path, stale index and unindexed resource require documented behavior.

## Evidence classification

- `PROVEN_BY_SOURCE`: documented standard requirement or published study, with exact applicability.
- `STRUCTURAL_CHECKED`: static or simulated route/taxonomy invariant.
- `SYNTHETIC_BROWSER_CHECKED`: runtime browser behavior of invented fixture only.
- `OBSERVED_USER_STUDY`: direct participant task observation with disclosed sample and procedure.
- `PRODUCT_INTEGRATION_VERIFIED`: test against actual revision/backend with real authorization.
- `NOT_EXECUTED`, `INCONCLUSIVE`, `FAILED`: explicit unresolved statuses.

Do not promote an observed category grouping, a passed Playwright fixture, a design review or an idealized screenshot into production acceptance. Attach exact commands, inputs, environment, baseline, outputs and known limitations to the current project workflow evidence/convergence artifacts. Use the `plan.md` for alternative analysis; do not duplicate the skill's method into the spec.
