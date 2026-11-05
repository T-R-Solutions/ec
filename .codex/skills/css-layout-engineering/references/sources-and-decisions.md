# Evidence register — CSS Layout Engineering v1.0

> Imported authoring record. Research dates, consultations and execution claims below belong to the supplied source package; they were not reproduced in this adaptation. Referenced validation scripts, fixtures, product norms and logs are not bundled unless present in this folder. Verify their availability and provenance before use. No historical PASS is current acceptance.

**Research date:** 2026-10-31 (America/Fortaleza project context). **Evidence levels:** (A) official technical specifications/documentation for CSS and tests, (B) explicitly identified peer-reviewed empirical software-engineering publications, (C) directly executed *synthetic* browser experiments, (D) technical judgments and hypotheses. The official and scientific sources below were accessed during this creation. Published specification stage is stated where relevant: a W3C Candidate Recommendation Draft is **not falsely called a W3C Recommendation**. None of these sources shows Ec is implemented.

## Official specifications, browser guidance and tool documentation

| ID | Source and direct official URL | What it supports / limit |
|---|---|---|
| O01 | [W3C CSS Grid Level 2](https://www.w3.org/TR/css-grid-2/) | Grid two-axis track sizing, flexible lengths, subgrid; 2025 document is **Candidate Recommendation Draft**, not a performance benchmark. |
| O02 | [W3C CSS Flexible Box Layout Level 1](https://www.w3.org/TR/css-flexbox-1/) | Single-axis flex sizing, wrapping, alignment; October 2025 published state is Candidate Recommendation Draft, not universal browser compatibility. |
| O03 | [W3C CSS Containment Level 3](https://www.w3.org/TR/css-contain-3/) | Container query model, containment and eligible query containers; check implementation/version before adopting new features. |
| O04 | [MDN Using container size and style queries](https://developer.mozilla.org/en-US/docs/Web/CSS/Guides/Containment/Container_size_and_style_queries) | Container vs viewport, style query limitations and named contexts; feature list may evolve independently of supported browsers. |
| O05 | [MDN Using media queries](https://developer.mozilla.org/en-US/docs/Web/CSS/Guides/Media_queries/Using) | Viewport, orientation and user preference conditions; does not define product breakpoints. |
| O06 | [MDN Using feature queries](https://developer.mozilla.org/en-US/docs/Web/CSS/Guides/Conditional_rules/Using_feature_queries) | `@supports` syntax capability and fallback; **not** proof of full/bug-free feature implementation. |
| O07 | [MDN `minmax()`](https://developer.mozilla.org/en-US/docs/Web/CSS/Reference/Values/minmax) | Grid minimum/maximum functions and `fr`; no guarantee a specific track is readable at all sizes. |
| O08 | [MDN `min-width`](https://developer.mozilla.org/en-US/docs/Web/CSS/Reference/Properties/min-width) | Auto minimum size behavior for grid/flex items and scroll containers. |
| O09 | [MDN CSS logical properties](https://developer.mozilla.org/en-US/docs/Web/CSS/Guides/Logical_properties_and_values) | Direction-sensitive inline/block properties, RTL/writing modes; not complete Unicode bidi handling. |
| O10 | [MDN Grid logical values and writing modes](https://developer.mozilla.org/en-US/docs/Web/CSS/Guides/Grid_layout/Logical_values_and_writing_modes) | Grid's logical layout and implications of physical coordinates. |
| O11 | [W3C WCAG 2.2](https://www.w3.org/TR/WCAG22/) | Normative SC 1.4.4 Resize Text and 1.4.10 Reflow; no standalone CSS geometry test certifies all AA. |
| O12 | [W3C Understanding Reflow](https://www.w3.org/WAI/WCAG22/Understanding/reflow.html) | Equivalent 320 CSS px vertical reflow and exceptions requiring two-dimensional layout. |
| O13 | [W3C Understanding Resize Text](https://www.w3.org/WAI/WCAG22/Understanding/resize-text.html) | 200% text resize expectations and common clipping failures; text style injection is only a proxy. |
| O14 | [MDN Cascade introduction](https://developer.mozilla.org/en-US/docs/Web/CSS/Guides/Cascade/Introduction) | Origin, importance, layers, specificity, scope and order of CSS rules. |
| O15 | [MDN Cascade layers](https://developer.mozilla.org/en-US/docs/Learn_web_development/Core/Styling_basics/Cascade_layers) | Layer priority; note inversion of important declarations and unlayered normal priority. |
| O16 | [Playwright Python emulation](https://playwright.dev/python/docs/emulation) | Viewport/emulation API; not actual 400% browser zoom verification. |
| O17 | [Playwright Python screenshots](https://playwright.dev/python/docs/screenshots) | Capture an element/page for manual or downstream comparisons; screenshot alone is not layout correctness. |
| O18 | [Playwright Test visual comparisons](https://playwright.dev/docs/test-snapshots) | Screenshot baseline differences and environment sensitivity; no product proof from a screenshot. |

## Verified peer-reviewed research (directly relevant)

| ID | Paper and verified source | Supported statement, experimental scope, nonclaim |
|---|---|---|
| P01 | Walsh, T. A.; Kapfhammer, G. M.; McMinn, P. (2017), *Automated Layout Failure Detection for Responsive Web Pages without an Explicit Oracle*, **ISSTA 2017**, ACM, DOI [10.1145/3092703.3092712](https://doi.org/10.1145/3092703.3092712), [institutional record with refereed status](https://eprints.whiterose.ac.uk/id/eprint/116989/) | Authors report that their RWD layout consistency method detected 33 distinct failures in 16 of 26 studied production pages. Supports checking ranges of widths and structural consistency without assuming a single reference screenshot. **Not** a universal failure rate, proof of coverage for our fixtures, or an Ec result. |
| P02 | Walsh, T.; Kapfhammer, G. A.; McMinn, P. (2017), *ReDeCheck: An Automatic Layout Failure Checking Tool for Responsively Designed Web Pages*, **ISSTA 2017 tool demonstration**, ACM, DOI [10.1145/3092703.3098221](https://doi.org/10.1145/3092703.3098221), [refereed institutional record](https://eprints.whiterose.ac.uk/id/eprint/116991/) | Describes a tool and usage scenarios for automated responsive layout checking. Demonstration provides methodological context but is not an independent validation of our Chromium oracles and should not be counted as a distinct large-scale outcome study. |
| P03 | Althomali, I.; Kapfhammer, G. M.; McMinn, P. (2022), *Automated Repair of Responsive Web Page Layouts*, **ICST 2022**, IEEE, pp. 140–150, DOI [10.1109/ICST53961.2022.00025](https://doi.org/10.1109/ICST53961.2022.00025), [institutional refereed record](https://eprints.whiterose.ac.uk/id/eprint/230094/) | Authors evaluate a responsive-layout repair technique on real sites and participant judgments; indicates failures can be narrow-width and difficult to repair. Automated repair and study-specific participant preferences **do not** establish effectiveness, speed or appropriateness of the CSS practices and fixtures distributed here. |

## Methodological decisions and traceable support

| Decision | Evidence basis | Confidence/qualification |
|---|---|---|
| Prefer Grid for 2D and Flex for 1D geometry | O01, O02 | Official algorithms; a context-sensitive engineering guideline, not an absolute rule. |
| Avoid layout overflow due to intrinsic minimums | O01, O07, O08 | Mechanically explained; fix depends on element purpose and accepted scroll design. |
| Distinguish `@media` from `@container` | O03–O06 | Specification and guide-supported; app must verify browser versions and features. |
| Preserve content across writing directions | O09, O10 | Strong for layout direction; bidi/translation needs independent verification. |
| Include 320px reflow/200% text probes | O11–O13 | Normative thresholds, but packaged probes **do not certify WCAG**. |
| Use a viewport and container *range* with negative oracles | P01–P03, O16–O18 | Supported as test strategy; no external detection-rate claim is adopted. |
| Measure root overflow, critical boxes and clipped labels | Engineering hypothesis executed on synthetic fixture | 61 successful browser cases and 14 deliberate defect catches; not validated against app behavior or representative production failures. |
| Use CSS layers and profile rather than claim speed | O14, O15 | Correct CSS precedence guidance; no independent performance benchmark performed. |

## Reproducibility and limits

- Local evidence recorded by scripts (`validation/browser-test-output.txt`, `validation/browser-mutation-output.txt`) uses Chromium 144.0.7559.96, Python Playwright and self-contained synthetic HTML/CSS. The test contents are shipped with the skill distribution; they are **not** an Ec implementation, production browser matrix or research-paper reproduction.
- No paper in P01–P03 was independently replicated; only source claims tied to the cited studies are attributed to their authors. P02 is a tool demonstration, and the underlying research overlaps the P01 team; do not present three publications as three independent confirmations of a numeric result.
- No Firefox/WebKit, mobile native device, high-zoom cross-browser, SDD feature acceptance, automated agent route evaluation, design usability study, layout-performance benchmark or Ec server/client integration was performed.
- **Source hierarchy:** Ec norms/feature SDD own permanent product requirements; these CSS documents own a *reusable engineering method*. The historical Ec JSON configuration proposal is superseded: YAML desired-state is canonical. `MANIFEST.json` in this distribution is integrity metadata, not an Ec desired-state configuration format.

**Not independently validated:** all three papers are cited for scoped evidence only; the synthetic fixture is **not an Ec implementation**. The actual application and agent evaluation are **NOT EXECUTED**. The reported study outcomes are **not a universal** responsive-web failure rate.
