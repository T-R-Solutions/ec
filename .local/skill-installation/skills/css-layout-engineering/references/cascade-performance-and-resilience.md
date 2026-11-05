# CSS cascade, resilience and performance evidence

Primary guidance: [MDN CSS cascade introduction](https://developer.mozilla.org/en-US/docs/Web/CSS/Guides/Cascade/Introduction), [MDN cascade layers](https://developer.mozilla.org/en-US/docs/Learn_web_development/Core/Styling_basics/Cascade_layers), [MDN feature queries](https://developer.mozilla.org/en-US/docs/Web/CSS/Guides/Conditional_rules/Using_feature_queries).

## Predictable extensibility

Cascade depends on declaration origin/importance, layers, specificity, scoping proximity and source order. Layer order can prevent fragile specificity escalation; it is not inherently faster and should not override existing design system conventions unilaterally. `!important` can invert normal layer priority; verify actual styles rather than inferring from selector complexity.

Suggested tasks: inspect computed styles where a media/container rule appears ignored; identify external plugin styles and user-agent styles; apply `@layer` if local team conventions permit; document origin/layer for overrides; keep tokens in the designated design system. Avoid an `!important` cascade arms race or resetting every element on every screen.

## Responsiveness and performance

An app that recomputes large graphs/log lists may experience frame drops from JavaScript or paint/compositing, not from Grid itself. Profile before attributing performance to `grid` vs `flex`. Investigate layout shifts, style recalculation, DOM volume, font loading, dynamic size containment, `content-visibility`, animation targets and GPU/resource constraints in the intended browser. Record *measured* input sizes, browser version, device/CPU throttle, repeat counts, distributions and trace evidence. Do not invent frame times, speedup percentages, CLS gains or universal fastest layout methods.

Feature-gate new CSS with a usable fallback. `@supports` tests parsing support, **not** correctness, so check the rendered outcome. `@container` containment may alter intrinsic sizing and should be tested with real and empty content.

## Verification

Compare before/after computed geometry and scroll metrics under a fixed test matrix. When performance is materially implicated, collect browser traces for representative large tables, deeply nested inspectors and DAG nodes, and differentiate layout/paint from script and network. If no profiling was done, state **NOT EXECUTED performance measurement**. Coordinate bundle/runtime issues with relevant frontend and performance skills instead of expanding CSS scope.
