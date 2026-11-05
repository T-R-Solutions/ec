# Testing layers, oracles and assistive technology

> Ec-specific rules below apply only to an Ec consumer whose current contract establishes them. Commands, fixture paths and historical execution claims from the source package are illustrative or imported evidence unless the corresponding files are present and independently checked; they are not current test results or bundled executable tools.

| Layer | Observable outputs | Cannot establish by itself |
|---|---|---|
| Static validation/lint | syntax, some ARIA/HTML hazards, rule hits | actual keyboard usability, announcements or WCAG completeness |
| Accessibility scanner (e.g. axe-core) | machine-detectable findings with rule IDs, impact and selectors | absence of all accessibility barriers or conformance |
| Browser UI automation | roles/names, focus, key sequence, active DOM, visible error, computed style/geometry | real screen-reader output, all browser engines, physical input |
| Manual expert WCAG review | contextual criteria, reading/navigation order, real zoom and meaningful alternatives | all disability lived experiences |
| Screen reader/browser/OS exercises | announcement semantics, reading order and real interaction in a declared combination | behavior in every assistive technology |
| Evaluation with users with disabilities | real task friction, workarounds and unmet needs | universal WCAG conformance from a small sample |

## Evaluation procedure

1. Fix the scope: feature/URLs/revision, WCAG level, browser OS/AT support and the complete user journey.
2. Select automated checks, manual WCAG checks, keyboard and screen-reader tasks; include success/failure, denied and disabled states, zoom/locales/themes, assistive technologies suited to the task.
3. Run tests with reproduction commands and screenshots/videos when needed. Record pass, fail, not executed, inconclusive and not applicable **per success criterion**.
4. Inject controlled violations into a disposable fixture and ensure relevant oracles fail; reset them afterward.
5. Have a qualified evaluator interpret ambiguous findings. **Automated scanners may miss failures and can report false positives.** Do not report a clean scan as a conformance certificate.
6. Document unresolved AT or cross-browser differences and which participants/AT were *actually* used.

## Example browser oracles (Playwright)

Use `get_by_role('dialog', name='Confirm change')`, `get_by_role('textbox', name='Display label')`, inspect `aria-describedby` after invalid submit, press `Tab`, `Shift+Tab`, arrow keys or `Escape`, and verify focus restoration. These assertions use the browser accessibility mapping for the current engine. `aria-live` presence without actual AT testing is **unverified announcement behavior**.

If using Playwright+axe, cite exact versions and ruleset, scan pages/states after interaction rather than only landing pages, and retain automated findings without claiming a complete audit. Playwright official guide: https://playwright.dev/docs/accessibility-testing . W3C evaluation tools overview: https://www.w3.org/WAI/test-evaluate/tools/selecting/ . W3C WCAG-EM 2.0: https://www.w3.org/TR/wcag-em-2/ . W3C guidance for user involvement: https://www.w3.org/WAI/test-evaluate/involving-users/ .

## Reproduction of distributed sample

`python validation/run_browser_tests.py` runs selected DOM/keyboard/contrast/geometry oracles under Chromium. `python validation/run_mutations.py` introduces synthetic accessibility defects and expects those oracles to reject them. The fixture is generic HTML/CSS/JavaScript and was not rendered by Vue; neither script makes a claim about assistive technology compatibility or the real Ec UI.
