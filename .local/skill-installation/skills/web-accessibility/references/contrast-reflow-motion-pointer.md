# Perceptual and input constraints: what to verify

## Color and distinguishability

WCAG 2.2 SC 1.4.3 (AA) uses text contrast 4.5:1 for normal text and 3:1 for large text, with specific exceptions; SC 1.4.11 (AA) uses 3:1 where required to identify non-text UI states/graphic parts. Color must not be the only carrier of state (1.4.1). Compute contrast from actual computed foreground/background including transparency, overlapping layers and focus/disabled/selection conditions. Never infer contrast from design tokens alone when compositing changes perceived color. High contrast/forced colors should be tested in target browsers. A WCAG AAA focus indicator SC 2.4.13 is not required merely because the target is WCAG AA.

## Resize, reflow and layout

Test at 200% text enlargement (1.4.4), equivalent 320 CSS-px width / 400% zoom reference conditions for SC 1.4.10, both scrolling directions, keyboard navigation and long localized strings. Native zoom and CSS root-font scaling are **different experiments**; document which ran. Allow exceptions for content requiring two-dimensional layouts; do not excuse unnecessary page-wide horizontal scrolling. Check focus visibility when sticky layers are present and on-screen controls remain usable at increased text size.

## Reduced motion and precision

Honor `prefers-reduced-motion`; remove nonessential animations and avoid motion-triggered vestibular problems, with applicable exceptions. SC 2.5.8 requires at least 24×24 CSS-px targets **or one of its exemptions**; do not enforce universal 44×44 for a WCAG 2.2 AA verdict. For every user-authored drag gesture, supply a click/tap alternative performing the equivalent action (SC 2.5.7), not just a keyboard shortcut. Consider voice input, touch and switch input paths.

## Measurement boundaries

Synthetic browser fixtures can check computed colors, target rectangles and overflow, but cannot automatically evaluate contrast against *all* composited images or target-size exceptions. If you test pure text on uniform backgrounds, describe that limited scope. Position changes, dynamically revealed controls, zoom and RTL are material variants.

**Primary:** https://www.w3.org/TR/WCAG22/ ; https://www.w3.org/WAI/WCAG22/Understanding/target-size-minimum ; https://www.w3.org/WAI/WCAG22/Understanding/dragging-movements ; https://www.w3.org/WAI/WCAG22/Understanding/focus-not-obscured-minimum .
