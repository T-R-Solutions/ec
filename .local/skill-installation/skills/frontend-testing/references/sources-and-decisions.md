# Sources, provenance and technical decision ledger

> Imported authoring record. Research dates, consultations and execution claims below belong to the supplied source package; they were not reproduced in this adaptation. Referenced validation scripts, fixtures, product norms and logs are not bundled unless present in this folder. Verify their availability and provenance before use. No historical PASS is current acceptance.

**Checked:** 2026-11-01. Official sources were read through public documentation and academic metadata/publisher or university entries. The links are for future rechecking, not an assertion of permanent API-version compatibility.

## Official technical documents

| ID | Source | URI |
|---|---|---|
| O01 | Vue 3 official testing guide | https://vuejs.org/guide/scaling-up/testing.html |
| O02 | Vue Test Utils v2 guide | https://test-utils.vuejs.org/guide/ |
| O03 | Vue Test Utils async and Suspense | https://test-utils.vuejs.org/guide/advanced/async-suspense |
| O04 | Vue Test Utils API | https://test-utils.vuejs.org/api/ |
| O05 | Vitest guide | https://vitest.dev/guide/ |
| O06 | Vitest mocking | https://vitest.dev/guide/mocking.html |
| O07 | Vitest coverage | https://vitest.dev/guide/coverage |
| O08 | Vitest Browser Mode | https://vitest.dev/guide/browser/ |
| O09 | Vitest test context | https://vitest.dev/guide/test-context |
| O10 | Playwright best practices | https://playwright.dev/docs/best-practices |
| O11 | Playwright locators | https://playwright.dev/docs/locators |
| O12 | Playwright actionability | https://playwright.dev/docs/actionability |
| O13 | Playwright network mocking | https://playwright.dev/docs/network |
| O14 | Playwright trace viewer | https://playwright.dev/docs/trace-viewer-intro |
| O15 | Playwright test projects | https://playwright.dev/docs/test-projects |
| O16 | Testing Library query priority | https://testing-library.com/docs/queries/about/ |
| O17 | W3C WCAG 2.2 Recommendation | https://www.w3.org/TR/WCAG22/ |
| O18 | W3C accessibility evaluation tools limitations | https://www.w3.org/WAI/test-evaluate/tools/selecting/ |
| O19 | OWASP Web Security Testing Guide | https://owasp.org/www-project-web-security-testing-guide/ |

## Peer-reviewed evidence (metadata independently checked)

| ID | Paper and venue | DOI | Verification source |
|---|---|---|---|
| P01 | Barr et al. 2015, The Oracle Problem in Software Testing, IEEE TSE | 10.1109/TSE.2014.2372785 | https://discovery.ucl.ac.uk/id/eprint/1471263/ |
| P02 | Inozemtseva & Holmes 2014, Coverage Is Not Strongly Correlated, ICSE | 10.1145/2568225.2568271 | https://dblp.dagstuhl.de/rec/conf/icse/InozemtsevaH14.html |
| P03 | Luo et al. 2014, An Empirical Analysis of Flaky Tests, ACM FSE | 10.1145/2635868.2635920 | https://experts.illinois.edu/en/publications/an-empirical-analysis-of-flaky-tests/ |
| P04 | Jia & Harman 2011, An Analysis and Survey of the Development of Mutation Testing, IEEE TSE | 10.1109/TSE.2010.62 | https://xplorestaging.ieee.org/document/5487526 |

## Decisions with explicit limitations

1. Use behavior-first assertions and REQ/AC traceability (official Playwright and Testing Library; Barr survey); **not** a proof that internal tests are always ineffective.
2. Prefer Vue Test Utils for mounted Vue 3 tests and separate real-browser E2E (official Vue). The provided package does not install Vue.
3. Use deterministic async controls and isolated contexts (official tools and flaky-test research); no universal numerical flakiness reduction is claimed.
4. Include mutation-based test sensitivity as an optional deep check; synthetic mutants are not real-defect benchmarks.
5. Never label mocked behavior as live backend acceptance. Only actual environment-specific execution can establish a real application gate.
6. Coverage informs where to add tests but cannot serve as the only quality target (Inozemtseva & Holmes).
7. Package-level integrity is SHA-256 consistency; it is neither signed provenance nor evidence of safe execution.
