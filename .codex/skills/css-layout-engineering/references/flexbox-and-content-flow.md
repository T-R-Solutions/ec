# Flexbox and content flow

**Use for:** button toolbars, breadcrumbs, metadata rows, horizontal menus, split-pane headers, non-overlapping single-axis widgets. **Standard:** [W3C CSS Flexbox Level 1](https://www.w3.org/TR/css-flexbox-1/).

Flex is primarily a single-axis layout method: main axis determined by `flex-direction`, cross-axis alignment determined by `align-items` and `align-content` (for multiple flex lines). It is not interchangeable with Grid in true two-dimensional layouts. Flex items start with an automatic minimum size which may preserve the longest unbreakable string and push controls out of the viewport. If the component must shrink, use an appropriate `min-inline-size:0`, `max-inline-size`, `overflow-wrap:anywhere`, or explicit local scrolling. Do not assume `flex:1` alone prevents content overflow.

```css
.command-row {
  display: flex;
  flex-wrap: wrap;
  gap: .5rem;
  min-inline-size: 0;
  align-items: center;
}
.command-row > * { max-inline-size: 100%; overflow-wrap: anywhere; }
.command-row .search { flex: 1 1 14rem; min-inline-size: 0; }
```

## Real input questions

- Are actions permitted to wrap, or must the region expose horizontal scrolling (with visible affordance and keyboard access)?
- Are labels translated, contain UUID/hash paths, use RTL text, or expand under user font preferences?
- Does a grouped control need a minimum usable width, and is there enough vertical room when wrapping?
- Is the *source order* meaningful when the flex direction changes? `row-reverse` and CSS `order` alter visual order, not underlying DOM order.
- Would a different visual presentation preserve functions better than hiding essential controls at narrow viewports?

## Oracle

Test `getComputedStyle(toolbar).flexWrap`, each critical control's bounding rectangle, non-overlap at widths near wrapping threshold, and keyboard focus reachability. A mutation `flex-wrap:nowrap!important` should fail if the contract requires wraps; a mutation forcing a 1000px child should reveal overflow. **Beware:** changing `flex-wrap` to `wrap` alone is not sufficient: a child with an unbreakable intrinsic minimum may still overflow.

For CSS Grid track choice see [Grid reference](grid-and-intrinsic-sizing.md). For reflow criteria see [accessibility and zoom](layout-accessibility-and-zoom.md). Do not invent preferred layout aesthetics; use existing component tokens.
