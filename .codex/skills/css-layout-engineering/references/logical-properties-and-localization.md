# Logical flow, RTL and localization in layouts

Primary references: [MDN CSS logical properties](https://developer.mozilla.org/en-US/docs/Web/CSS/Guides/Logical_properties_and_values), [MDN logical Grid/writing modes](https://developer.mozilla.org/en-US/docs/Web/CSS/Guides/Grid_layout/Logical_values_and_writing_modes).

Different writing modes redefine inline start/end and block start/end. For LTR English/Portuguese, inline-start is generally left; for RTL Arabic/Hebrew, inline-start is generally right. Vertical writing modes also change the physical mapping. Use logical properties when the intended value relates to reading direction:

```css
.inspector {
  padding-inline: 1rem;
  border-inline-start: .25rem solid currentColor;
  min-inline-size: 0;
}
.meta { margin-inline-start: auto; }
.panel { inset-inline-start: 0; }
```

Avoid claiming that swapping `left` to `inset-inline-start` automatically solves bidi or visual ordering. Check glyph directionality, mixed LTR commit hashes inside RTL labels, punctuation behavior, icons with semantic directional meaning, flex row orientation, and keyboard focus order. These may need direction isolation (`dir="auto"`, `<bdi>`) and UX/localization review beyond this CSS skill.

## Browser oracle

- Render the same DOM under `document.documentElement.dir="ltr"` and `"rtl"`.
- Measure which physical border receives `border-inline-start` (`border-left` vs. `border-right`) and whether buttons, nav and content stay inside the viewport.
- Inject a physical `border-left` override to prove the RTL invariant fails.
- Stress labels that grow by 2x and resource paths without spaces; detect root overflow, clipping and unintentional local scrolling.
- For vertical writing modes, run independent tests only if the product requires them; absence of a test means **NOT EXECUTED**, not supported by assumption.

Reference [layout reflow](layout-accessibility-and-zoom.md) for text scaling. Domain policies and localization vocabulary must come from the active application spec.
