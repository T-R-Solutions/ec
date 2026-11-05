# Primary source ledger and decision log

> Imported authoring record. Research dates, consultations and execution claims below belong to the supplied source package; they were not reproduced in this adaptation. Referenced validation scripts, fixtures, product norms and logs are not bundled unless present in this folder. Verify their availability and provenance before use. No historical PASS is current acceptance.

**Research cut-off:** 2026-10-31. Web pages change; re-check editions and support baselines before implementing product features. Links below were resolved during package preparation; their presence is not proof of full runtime compatibility.

## Normative primary texts

- [WCAG 2.2 Recommendation, updated 12 Dec 2024](https://www.w3.org/TR/WCAG22/) — A/AA/AAA criterion statements and conformance.
- [WAI-ARIA 1.2](https://www.w3.org/TR/wai-aria-1.2/) — roles, states and properties.
- [ARIA in HTML, Recommendation 11 Aug 2026](https://www.w3.org/TR/html-aria/) — permitted role/attribute mappings.
- [ACT Rules Format 1.1, Recommendation 5 Feb 2026](https://www.w3.org/TR/act-rules-format/) — standardized rule descriptions.

## Official supporting material (non-normative)

- [WCAG-EM 2.0, Group Note Jul 2026](https://www.w3.org/TR/wcag-em-2/) — scoping, sampling, evaluation and reporting.
- [ACT Rules overview](https://www.w3.org/WAI/standards-guidelines/act/rules/about/) — informative rules, not required conformance tests.
- [WAI APG patterns](https://www.w3.org/WAI/ARIA/apg/patterns/) — keyboard conventions and widget examples.
- [APG keyboard interface practice](https://www.w3.org/WAI/ARIA/apg/practices/keyboard-interface/) — focus and composite widget principles.
- [APG modal dialog](https://www.w3.org/WAI/ARIA/apg/patterns/dialog-modal/) — naming, focus and modal behavior.
- [APG tabs](https://www.w3.org/WAI/ARIA/apg/patterns/tabs/), [treeview](https://www.w3.org/WAI/ARIA/apg/patterns/treeview/), [grid](https://www.w3.org/WAI/ARIA/apg/patterns/grid/) — widget interaction maps.
- [WAI forms labels](https://www.w3.org/WAI/tutorials/forms/labels/) and [instructions](https://www.w3.org/WAI/tutorials/forms/instructions/) — visible labels and associations.
- [WCAG focus not obscured AA](https://www.w3.org/WAI/WCAG22/Understanding/focus-not-obscured-minimum), [dragging movements AA](https://www.w3.org/WAI/WCAG22/Understanding/dragging-movements), [target size minimum AA](https://www.w3.org/WAI/WCAG22/Understanding/target-size-minimum), [status messages](https://www.w3.org/WAI/WCAG22/Understanding/status-messages) — interpretation, examples and exceptions.
- [W3C evaluation tools cannot test everything](https://www.w3.org/WAI/test-evaluate/tools/selecting/); [involving users](https://www.w3.org/WAI/test-evaluate/involving-users/).
- [Playwright accessibility testing](https://playwright.dev/docs/accessibility-testing) — implementation guidance, not W3C authority.
- [AccName 1.2 Working Draft 12 Oct 2026](https://www.w3.org/TR/2026/WD-accname-1.2-20261012/) — current draft; **not** a Recommendation.

## Scientific publications

See [scientific evidence with limits](scientific-evidence-and-limits.md) for four verified DOI-identified peer-reviewed papers, interpretation and external validity caveats. The papers are supporting evidence for methodology and **do not determine WCAG normative requirements**.

## Decisions (D-1 to D-7)

- **D-1:** Choose W3C WCAG 2.2 as the target *when the product specification requires it*. The generic skill never mandates a product identity or level.
- **D-2:** Prefer native semantic HTML; ARIA is complementary, not a substitute for keyboard behavior.
- **D-3:** Test compound UI by complete task and multiple input modes; a static screenshot is insufficient.
- **D-4:** Require human/contextual/AT evaluations for conformance evidence; browser-only/automated tests are partial.
- **D-5:** Record SC level precisely: 2.4.11 AA vs 2.4.13 AAA; 2.5.7 and 2.5.8 AA with exceptions.
- **D-6:** Treat fixture and injected mutations as local method tests; never as certification for Ec.
- **D-7:** Preserve authority boundaries among product norms, SDD, generic skill and optional bridge.
