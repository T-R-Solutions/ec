# WCAG/REQ/AC evidence matrix and reporting template

> Use the destination's existing requirements and evidence format; named workflow documents are examples, not prerequisites. Optional SDD mapping is configured outside this skill. Ec-specific rules below apply only to an Ec consumer whose current contract establishes them.

For each scoped feature, write one record per applicable criterion or behavior:

| Field | Meaning |
|---|---|
| Feature / view / complete task | E.g. permission review modal, table filter, editor fallback; use actual product references |
| AC or REQ ID | Feature project workflow traceability, never invented as a substitute for the spec |
| WCAG SC + level + version | E.g. WCAG 2.2 2.1.1 A, 2.4.11 AA, 2.5.7 AA |
| User state | Role/permission, locale, theme, input, failure state, keyboard or pointer variant |
| Oracle | Exact test/manual interaction, expected result, data source |
| Evidence | Commit/URL, browser/OS/AT versions, command or test log, screenshot, recording or user observation |
| Result | PASS, FAIL, NOT EXECUTED, INCONCLUSIVE or N/A, with rationale |
| Residual risk | Untested screen reader, unsupported browser, unverified semantics, possible false positives |

**Example — fictional and not an Ec claim:** A sample dialog opened with keyboard: modal role/name and `:modal` observed in Chromium; Escape closes and focus returns; no screen reader was run; SC 4.1.2 remains **PARTIALLY EVALUATED**. This is not a conformance determination.

## Suggested audit routes (illustrative)

- Read-only pages: headings/landmarks, tables, captions, link purposes, focus navigation and reflow.
- Editable pages: labels, input errors, hints, keyboard operations, save confirmation, conflict announcement and focus.
- Composite editors: tabs/trees/graphs, roving focus, keyboard equivalence, independent non-drag pointer alternatives.
- Operational interfaces: loading and status, live log throttling, cancel/retry confirmation, permissions and error recovery.
- Themes and localization: light/dark, RTL, long labels, zoom/reflow, high contrast when available.

## Final claims

Acceptable: “Checked SC 2.1.1 on A/B workflows using Chrome X on OS Y, with negative control Z; further AT evaluation pending.”

Not acceptable: “WCAG compliant” because there were zero automated scanner violations, one screenshot, a synthetic keyboard test, or some markup attributes.

Use WCAG-EM 2.0's definition of scope, exploration, representative sample, evaluation and report. Do not average different criterion categories into one unqualified accessibility percentage. https://www.w3.org/TR/wcag-em-2/
