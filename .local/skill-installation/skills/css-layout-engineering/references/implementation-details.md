# Detailed decisions and failure cases

Read only for a decision needing more detail than the entry point. Apply the relevant steps to the
actual task; the sections are not a mandatory artifact or test checklist. Use existing project
contracts in their actual format. File names such as spec.md, plan.md or tasks.md are examples, not
required dependencies. Other skills are optional expertise, never enforcement mechanisms.

Ec references below are conditional consumer examples. They impose no Ec documents, YAML format,
stack or product policy on a generic task.

## Method: do not skip evidence gates

1. **Locate the authoritative scope.** Inspect repository instructions, design system, CSS
   ownership, target browsers and minimum versions, viewport/container constraints, real semantic
   HTML and data/label lengths, writing direction, zoom/reflow requirements, screenshots/test
   failures and the current relevant project workflow feature. Use the destination project's actual
   requirements; product-specific examples are conditional. Decide whether the work is a bounded
   local change or a complete project workflow feature. Record observed behavior and missing
   environment; do not call an unimplemented screen "working".
2. **Describe layout invariants before CSS.** Identify regions and their primary/secondary axes,
   keyboard/source order, minimum and maximum inline sizes, intentional scroll containers,
   breakpoints driven by content and container constraints, long text paths, responsive panel
   states, and whether each control remains reachable. Specify measurable rules, e.g. no root
   horizontal overflow at defined CSS widths, meaningful information not clipped, table scroll
   confined to a labeled region and no overlapping critical controls. Avoid pixel constants inferred
   from one screenshot. See [application shells and overflow](responsive-shells-and-overflow.md).
3. **Pick Grid or Flexbox by problem structure.** Use CSS Grid for true two-dimensional
   allocation/areas/tracks and Flexbox for single-axis sizing, alignment and wrapping. Prefer DOM
   order as the meaningful reading/keyboard order; CSS `order` and grid placement must not
   masquerade as semantic reordering. Control min-content with `minmax(0,1fr)` on flexible grid
   tracks and `min-inline-size:0` on nested flex/grid items where appropriate; do not
   indiscriminately force `overflow:hidden`. Understand `flex-basis`, `flex-shrink`, min-content,
   replaced elements, `box-sizing`, `aspect-ratio` and intrinsic sizing before hard-coded widths.
   See [Grid](grid-and-intrinsic-sizing.md) and [Flexbox](flexbox-and-content-flow.md).
4. **Adapt to the *correct* constraint.** Use `@media` for viewport, device and user preference
   changes and `@container` for a reusable component's containing region. Size queries require
   eligible ancestors (`container-type:inline-size` / containment); they do not style the query
   container itself. Document named-container choice, nearest-ancestor behavior, breakpoints,
   fallback and compatibility. Treat style, scroll-state and anchored queries as separately gated
   capabilities, not universal replacements for size queries. Use `@supports` for progressive
   enhancement, but understand that syntax support does not guarantee bug-free behavior. See
   [queries and fallbacks](container-media-and-feature-queries.md).
5. **Preserve content, direction and accessibility boundaries.** Prefer logical properties
   (`padding-inline`, `inset-inline`, `min-inline-size`) and test LTR/RTL as well as long localized
   labels. Verify text at 200%, equivalent 320 CSS pixel reflow conditions, focus and critical
   action availability. WCAG 2.2 SC 1.4.10 permits two-dimensional scrolling for content requiring
   it (e.g. some data tables), **not** a blanket exemption for the entire page. An observed 320px
   viewport is a reflow proxy, not proof of 400% browser zoom or WCAG compliance. Keep overflow
   localized, scroll areas labeled and keyboard reachable. Route full WCAG claims to
   `web-accessibility`. See [logical flow](logical-properties-and-localization.md) and [layout
   accessibility](layout-accessibility-and-zoom.md).
6. **Keep CSS extensible, resilient and measurable.** Work with the project's existing tokens
   without defining a new brand. Use layered cascade (`@layer`) where useful; check
   specificity/origin, user overrides and `!important` interactions. Use relative units, `clamp()`
   and responsive sizing where content warrants. Consider layout invalidation, expensive animations,
   shifting and measurement under actual workloads; do not declare one layout algorithm fastest
   without profiling a representative scenario. Use `@supports`/fallbacks where required by
   supported browsers. See [cascade and performance](cascade-performance-and-resilience.md).
7. **Implement a browser geometry test matrix and negative probes.** Exercise defined viewport
   widths including breakpoint edges, component widths held at the same viewport, light/dark where
   available, RTL/LTR, long IDs, zoom/text resizing, empty/loading/error state variants, nested
   tables and dynamically resized panels. Measure computed grid tracks, client vs scroll sizes and
   critical bounding boxes; assert intentional exceptions separately. Include mutation probes that
   deliberately cause overflow, hidden controls, wrong RTL border, failed container switching and
   clipped labels. Screenshots/visual diffs can aid review but require stable browser/font/platform
   baselines and human interpretation; geometry tests may miss visual collisions. See [browser
   oracles](browser-verification-and-oracles.md).
8. **Report exact evidence and nonclaims.** Deliver affected files and before/after conditions,
   browser/OS/Playwright versions, measured viewports and container sizes, test commands/exits,
   expected negative outcomes, accepted exceptions, untested browsers/devices/zoom and
   requirement/acceptance IDs references. Mark `NOT EXECUTED` when browser/test inputs are absent; a
   static CSS file is not evidence of rendered correctness. In Ec, desired-state YAML and
   Visual/Form/YAML parity remain norm/project workflow responsibilities: do not encode a competing
   configuration source or claim an operational action completed because its layout changed.


## Further references by decision

Read only the reference relevant to the unresolved decision. Research registers describe imported authorship, not current execution.

- [Browser verification, geometry tests and fault sensitivity](browser-verification-and-oracles.md): browser verification and oracles.
- [CSS cascade, resilience and performance evidence](cascade-performance-and-resilience.md): cascade performance and resilience.
- [Media, container, and feature queries](container-media-and-feature-queries.md): container media and feature queries.
- [Flexbox and content flow](flexbox-and-content-flow.md): flexbox and content flow.
- [Grid and intrinsic sizing — measurable engineering method](grid-and-intrinsic-sizing.md): grid and intrinsic sizing.
- [CSS layout accessibility, reflow and zoom — scoped verification](layout-accessibility-and-zoom.md): layout accessibility and zoom.
- [Logical flow, RTL and localization in layouts](logical-properties-and-localization.md): logical properties and localization.
- [Application shells, density and intentional overflow](responsive-shells-and-overflow.md): responsive shells and overflow.
- [Skill routing scenarios and integration acceptance plan](routing-and-selection-oracles.md): routing and selection oracles.
- [Evidence register — CSS Layout Engineering v1.0](sources-and-decisions.md): source provenance or a disputed recommendation.
