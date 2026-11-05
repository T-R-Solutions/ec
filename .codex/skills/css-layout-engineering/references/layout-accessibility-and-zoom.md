# CSS layout accessibility, reflow and zoom — scoped verification

**Normative external references:** [WCAG 2.2](https://www.w3.org/TR/WCAG22/), specifically 1.4.10 Reflow (Level AA), 1.4.4 Resize Text (Level AA); [W3C Understanding Reflow](https://www.w3.org/WAI/WCAG22/Understanding/reflow.html), [W3C Understanding Resize Text](https://www.w3.org/WAI/WCAG22/Understanding/resize-text.html).

## What the criteria actually require

- SC 1.4.10: reflow with no loss of functionality/information and no two-dimensional scroll for vertical content at an equivalent **320 CSS px** width; horizontal content at an equivalent 256 CSS px height. Certain content that *requires* two dimensions (including some data tables, maps, diagrams) is excepted, not every dashboard or workspace.
- SC 1.4.4: text can be resized up to **200%** without loss of content or functionality, under the criterion's scope and notes.
- For wide DAG/code/table controls, determine essential two-dimensional behavior and offer appropriate alternative interaction if specified. **A CSS-only test is insufficient for certifying WCAG 2.2 AA.** Screen reader, keyboard order, accessible naming, contrast, focus and other success criteria remain separate responsibilities.

## Practical test matrix

1. Inspect at 1280 CSS pixel desktop and emulate 320 CSS pixel viewport, comparing root and bounded scroll. Reproducing a 320px viewport approximates the *reflow geometry* of some 400% zoom cases, but is **not equivalent to executing 400% zoom** in a real browser. Test actual zoom with the target browsers before an AA claim.
2. Apply font size 200% to a controlled test fixture, verifying controls, long labels, input fields and status pills remain visible. This CSS probe is not a substitute for genuine user-agent text-only zoom behavior.
3. Inspect keyboard focus and DOM order, including offscreen/collapsed panels and scroll containers. Focus being possible is only a subset of accessibility.
4. Test forms/error banners, empty/loading/failure states and long translated strings, not just the happy screenshot.
5. Do not rely solely on screenshot similarity; verify accessible information/functionality with manual and automated tools from the accessibility skill.

## Negative oracles

Introduce CSS clipping (`white-space:nowrap; overflow:hidden`), page-wide fixed width, hidden essential nav and nonwrapping toolbar. The layout tests should reject them. Do not hide a genuine failure with `overflow-x:hidden` on the page root; a root-geometry check might miss the lost content, hence critical-control bounding and clipping checks.

The relevant product's WCAG target and exception rationale belong to authoritative project documents; this method provides measurements, not a new acceptance policy.
