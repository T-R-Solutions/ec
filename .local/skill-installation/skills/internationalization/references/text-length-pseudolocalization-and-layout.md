# Pseudolocalization, Expansion, Wrapping and Non-Latin Text

> Commands, fixture paths and historical execution claims from the source package are illustrative or imported evidence unless the corresponding files are present and independently checked; they are not current test results or bundled executable tools.

Pseudolocalization is a deterministic **stress technique**, not a real translation. It can accent, expand and wrap source-language strings while retaining placeholders, markup slots and technical IDs. A pseudo-RTL mode must be implemented deliberately; simply reversing Unicode code points corrupts grapheme clusters and placeholder expressions.

**Test matrix:** 320/360/768/1440 px; 200% text scaling; long German/Russian samples; Japanese no-spaces; Arabic bidi; emoji with ZWJ sequences; errors, empty states, tooltip, table headers, breadcrumb, confirmation, modals, labels, dynamic status and loading. Detect horizontal viewport scrolling and clipped critical actions, while allowing intentional inner scrolling in editors/logs.

**Mutation tests:** remove `min-width:0` in a flex/grid child, replace logical padding with `padding-left`, truncate text with fixed `height`, strip interpolation tokens, hide errors in a short label, and render pseudo words with layout overflow. A green screenshot is not a usability metric; verify readable text and available actions.

Peer-reviewed evidence from Mahajan et al. (ICST 2018 and STVR 2021) motivates tests for localization presentation failures; their measured repair rates are **not** portable performance estimates.

Code fixture: `validation/fixtures/index.html` and `app.js` use synthetic text and pseudo expansion. See `validation/outputs/browser-results.json` for the observed Chromium-only matrix.
