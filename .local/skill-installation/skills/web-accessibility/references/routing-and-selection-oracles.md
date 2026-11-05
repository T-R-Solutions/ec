# Agent skill activation oracles

> Ec-specific rules below apply only to an Ec consumer whose current contract establishes them.

ARIA and other issues below are **planned agent-selection evaluations**, not executed autonomous-agent experiments.

| Class | User request | Expected selection | Reason |
|---|---|---|---|
| Positive | Fix a dialog that loses focus after closing; test keyboard dismissal | `web-accessibility` primary | focus and modal accessibility |
| Positive | Audit a visual pipeline DAG for keyboard and single-pointer alternatives | `web-accessibility` primary or paired with domain skill | accessible operation of composite UI |
| Positive | Form errors do not announce or associate with input | `web-accessibility` primary | names/descriptions and status messages |
| Positive | Evaluate WCAG 2.2 AA using AT, scanner and manual techniques | `web-accessibility` primary | evaluation methodology and defensible claims |
| Negative | Tune Grid columns and min-content overflow without semantic/accessibility change | `css-layout-engineering` | CSS placement |
| Negative | Set brand palette and interface design tokens | `design-system` | brand identity |
| Negative | Implement Vue composable that retries HTTP requests | `vue3-engineering` | framework reactivity |
| Negative | Make server scheduler choose an agent | `cicd-scheduler` | backend scheduling |
| Boundary | Responsive layout hides keyboard focus behind sticky toolbar | `web-accessibility` + CSS skill only if needed | separate owner of focus vs geometry |
| Boundary | Design and validate YAML→Form→Visual state projection | Ec semantic projection skill; accessibility only when keyboard/AT relevant | no duplicated domain rules |
| Boundary | Build form schema with async validation and inaccessible error announcements | `frontend-form-engineering` + `web-accessibility` if material | business validation vs accessibility |
| Boundary | Playwright E2E suite for a page also needs screen-reader checks | `frontend-testing` + `web-accessibility` selectively | test harness vs accessibility oracle |

**Agent evaluation procedure:** Feed one task at a time with available skill index; record actual selected skill, references loaded, evidence quality, and false positive/negative selection; compare against expected. Do not call this matrix a tested routing benchmark until the agent evaluation has actually run.
