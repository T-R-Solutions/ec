# Grid and intrinsic sizing — measurable engineering method

**Read for:** two-dimensional layout allocation, repeat/minmax/subgrid, min-content overflow, nested DAG inspector sizing, or unexpected wide grid tracks. **Primary standard:** [W3C Grid Level 2](https://www.w3.org/TR/css-grid-2/); [MDN minmax()](https://developer.mozilla.org/en-US/docs/Web/CSS/Reference/Values/minmax); [MDN min-width](https://developer.mozilla.org/en-US/docs/Web/CSS/Reference/Properties/min-width).

## Track diagnosis

Grid describes row/column sizing for a parent container. The `fr` unit distributes free space, but the implicit minimum of a flexible track may be content-based. An oversized unbroken child can therefore expand the page despite `1fr`. First inspect the *containing block*, then computed grid tracks and child min/max content contributions, **not** only the overflowed child. Use `minmax(0,1fr)` for a track that is allowed to shrink below the min-content contribution; explicitly set `min-inline-size:0` on nested grid items where the automatic minimum is blocking shrink. Neither is an unconditional recipe: a design requiring minimum content dimensions may need a scroll region or a breakpoint instead.

```css
.layout {
  display: grid;
  grid-template-areas: "navigation content inspector";
  grid-template-columns: minmax(11rem, 14rem) minmax(0, 1fr) minmax(12rem, 18rem);
  gap: var(--gap);
}
.content { min-inline-size: 0; }
@media (width <= 52rem) {
  .layout {
    grid-template-areas: "navigation" "content" "inspector";
    grid-template-columns: minmax(0, 1fr);
  }
}
```

Avoid using grid-template area placement to alter actual keyboard/reading order: DOM order must remain meaningful. For a fixed-size workbench panel (logs/YAML/DAG), define containment and bounded scroll intentionally. `position: sticky` and inner scrollport behavior need separate browser evidence.

## Subgrid decision

Subgrid aligns a nested grid's tracks with a parent grid on an axis. It is helpful for shared label/field alignment or card row tracks when the containing hierarchy genuinely shares track lines, but not a replacement for semantic DOM groupings, independent component breakpoints or ordinary Flexbox. Verify required browser versions and show an acceptable fallback if older consumers are supported.

## Diagnosis and test oracle

1. Observe `getComputedStyle(element).gridTemplateColumns` and bounding boxes of parent + nested children for widths just above/below breakpoints.
2. Compare `scrollWidth` versus `clientWidth` on the root and *intended* internal scroller; a root width greater than the viewport indicates possible reflow failure, not necessarily the only bug.
3. Stress unbroken IDs, localized labels, side panels, tables and text resizing.
4. Deliberately force an invalid `min-width:1000px` in the main column; the browser oracle must report overflow or out-of-viewport critical content.
5. Preserve the valid semantic order and expect declared exceptions only for intrinsically two-dimensional content.

**Boundary:** A computed grid track measurement is rendering evidence in that browser/version; it does not prove visual usability, accessibility, performance or all-browser compatibility. Coordinate broader test strategy with [browser verification](browser-verification-and-oracles.md) and navigation semantics with `information-architecture`.
