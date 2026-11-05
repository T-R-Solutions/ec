# Browser verification, geometry tests and fault sensitivity

> Ec-specific rules below apply only to an Ec consumer whose current contract establishes them. Commands, fixture paths and historical execution claims from the source package are illustrative or imported evidence unless the corresponding files are present and independently checked; they are not current test results or bundled executable tools.

**Tool official docs:** [Playwright Python emulation](https://playwright.dev/python/docs/emulation), [Playwright screenshots](https://playwright.dev/python/docs/screenshots), [Playwright Test visual comparisons](https://playwright.dev/docs/test-snapshots). **Scientific method support:** Walsh et al. ISSTA 2017, DOI [10.1145/3092703.3092712](https://doi.org/10.1145/3092703.3092712) (RWD failures found at specific ranges and no simple universal oracle).

## Reproducible staged protocol

1. Pin exact browser binary and version, OS, library versions, device scale factor, font availability and viewport CSS pixels. Capture any test-service dependencies and file/mock boundaries. For an authorized app test, use its real application URL and data/identity fixture, not `page.set_content` as proof of integration.
2. Build content-driven viewport inputs: minimum width, just below/above each breakpoint, desktop widths and container widths at the same viewport. Include 200% text and long names, LTR/RTL and themes when applicable.
3. Measure `documentElement.scrollWidth` against CSS viewport width; critical element bounding boxes against available region and viewport; actual computed `gridTemplateColumns`; `getComputedStyle` visibility/overflow; scrollport width; content `scrollWidth/clientWidth` and `scrollHeight/clientHeight`. A root width oracle alone **misses clipping** from hidden overflow and intentional two-dimensional inner regions.
4. Declare expected exceptions *before* tests. A table may horizontally scroll inside `overflow-x:auto` while the page root does not. Do not exempt all of `main` or allow hidden critical actions.
5. Inject independent CSS defects and demand observed failures (mutation controls). Test that missing browsers/fixtures report `NOT EXECUTED`, rather than a false pass.
6. Screenshots improve investigation but visual differences need a controlled baseline, stable fonts/OS/renderer and review. Screenshot approval does not prove semantic layout, keyboard access or accessible reflow. Keep a geometry oracle, and use visual review when perceptual overlap/alignment matters.
7. Rerun the **target application's** E2E acceptance suite when present; record exact commands, exit codes and viewport measurements. Only the current feature contract can accept application behavior.

## Packaged self-contained example

```bash
python validation/run_browser_tests.py
python validation/run_browser_mutations.py
```

These commands load synthetic HTML/CSS into Playwright Chromium without network requests and log outputs. A missing browser, Playwright import or fixture must return `NOT EXECUTED` or a failing gate, not pass. `validation/fixtures/console.html` and `console.css` are generic demonstration content, not an Ec UI and not a normative design system.

## Evidence limit of the supplied example

The run includes 13 viewport widths × 2 directions × 2 themes, plus four 200%-text probes, two same-viewport container queries, table confinement, CSS support and focus. It does not run full real-device behavior, cross-browser screenshots, assistive technology, production content, paint performance or 400% actual zoom. Fault injection only shows that **these oracles can catch the seeded changes**; it is not a measured field defect detection rate or independent validation of the research studies.
