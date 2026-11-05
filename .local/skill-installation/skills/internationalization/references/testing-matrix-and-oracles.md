# I18n Verification Matrix and Executable Oracles

> Commands, fixture paths and historical execution claims from the source package are illustrative or imported evidence unless the corresponding files are present and independently checked; they are not current test results or bundled executable tools.

Maintain an acceptance matrix with test ID, product AC, locale, script, input class, browser/ICU/tzdb version, expected result, executed observation, evidence file and status.

| Dimension | Positive | Negative/control | Distinguish |
|---|---|---|---|
| Locale | canonical BCP 47; allowed language | invalid `pt_BR`, unsupported fallback | preference vs product availability |
| Catalog | complete keys/placeholders | missing key/renamed interpolation | source text vs resource schema |
| Plural | English, Arabic, Russian, Japanese samples | missing `other`, count absent | library grammar vs translated copy |
| Temporal | instant displayed per IANA zone | no offset, invalid date or unknown zone | timestamp vs civil date vs duration |
| Number | decimal/grouping/unit | NaN, currency mistaken for exchange | typed value vs localized text |
| Bidi | Arabic/Latin technical ID in one row | identifier changed, CSS-only direction | visual order vs bytes |
| Text | pseudo-expanded labels at narrow viewports | clipping, overflow, broken placeholders | geometric check vs readability |
| Concurrency | later locale selection wins | stale async load applies over new | request acceptance vs effective UI |
| Security | `<img>` shown literally as text | inserted HTML or script | rendering vs user content |
| Linguistic | reviewed translation/terminology | pseudo-text mistaken for real language | native reviewer vs automated suite |

**Executable local gates:** `python validation/run_node_tests.py`, `python validation/run_browser_tests.py`, `python validation/run_mutations.py`. These test a demonstrative model in local Node and Chromium. Follow with real Vue and backend E2E fixture documented under `validation/integration/`.

**Pass rule:** every suite must return exit 0; mutation tests require baseline passing and mutated counterpart failing **for the target assertion**. Do not classify timeouts or missing tools as successful mutation detections. Record all versions and actual commands. Test browser behavior across Firefox/WebKit when the deployment support matrix requires them; Chromium-only does not prove other engines.
