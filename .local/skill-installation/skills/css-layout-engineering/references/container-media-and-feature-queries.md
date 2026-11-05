# Media, container, and feature queries

**Standards and official guidance:** [W3C CSS Containment Level 3](https://www.w3.org/TR/css-contain-3/), [MDN container size/style queries](https://developer.mozilla.org/en-US/docs/Web/CSS/Guides/Containment/Container_size_and_style_queries), [MDN media queries](https://developer.mozilla.org/en-US/docs/Web/CSS/Guides/Media_queries/Using), [MDN feature queries](https://developer.mozilla.org/en-US/docs/Web/CSS/Guides/Conditional_rules/Using_feature_queries).

## Selection rule

| Need | Preferred mechanism | Reason |
|---|---|---|
| Layout changes when the overall viewport narrows | `@media` | Viewport/environment feature is the input |
| Reusable card layout differs at the *same viewport* when inspector/sidebar resizes | `@container` size query | Component's available inline size is the input |
| Respect reduced motion/dark preference | `@media (prefers-...)` | User preference is independent of a component width |
| Opt into a feature with fallback | `@supports` | Parser understands the property/value syntax |
| Style changes based on a container's own custom property | `@container style(...)` when supported | Verify browser support; full declaration queries are not universally available |

```css
.panel { container: panel / inline-size; }
.panel-list { display: grid; grid-template-columns: minmax(0, 1fr); }
@container panel (min-width: 34rem) {
  .panel-list { grid-template-columns: repeat(2, minmax(0,1fr)); }
}
@media (max-width: 47rem) {
  .application-shell { grid-template-columns: minmax(0,1fr); }
}
```

**Important behavior:** a size query needs an eligible *ancestor*, not necessarily a direct parent. Applying the queried layout to the container itself is generally a category error; conditional styles apply to descendants. Setting `container-type:inline-size` introduces containment and affects intrinsic sizing; test for unexpected collapse/size changes. Named containers avoid accidental nearest-ancestor selection when components are nested.

## Progressive compatibility

Use a baseline working layout before `@container` rules. `@supports` can tell whether a browser parses a declaration, not whether its implementation meets the specification or a particular rendering oracle. Newer container style/scroll-state/anchored query features require separate support checks; do not use a 2026 MDN feature list to infer any customer's locked browser. Explicitly record browser versions tested.

## Acceptance tests

- At fixed viewport 1920px, force a component container to 300px and 600px and verify one vs. two computed columns.
- At widths 320/600/768/920/1280, verify the *shell* changes at its own viewport breakpoint and retains controls.
- Inject `container-type:normal!important` and prove the expected two-column switch fails in the narrow synthetic model.
- Test content expansion and dynamic panel resize; compare layout after resize and check that the document does not acquire root horizontal overflow.
- If style query used, test exact support separately; absence of support is not a passing test.

The [verification reference](browser-verification-and-oracles.md) owns the browser test procedure; product breakpoints remain with the spec/design system.
