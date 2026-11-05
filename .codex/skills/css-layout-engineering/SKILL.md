---
name: css-layout-engineering
description: Implement or diagnose CSS sizing, Grid/Flexbox, responsive layout, overflow and reflow. Use for concrete layout behavior; skip branding, navigation taxonomy and framework state.
---

# CSS Layout Engineering

## Scope and inputs

Inspect semantic DOM, existing tokens, supported browsers, writing directions, content bounds and
the failing viewport or container. Discover the actual visual acceptance criteria.

Preserve the consumer's stack and policies. Product-specific examples in references do not establish
requirements for this task.

## Procedure

1. State layout invariants: source order, reachable actions, intended scrolling and content that
   must remain visible.
2. Choose Grid for two-dimensional allocation and Flexbox for a single axis. Inspect intrinsic
   minimum sizes before hiding overflow or forcing fixed dimensions.
3. Use viewport or container queries according to the actual containing constraint; check feature
   support and fallbacks in the destination browser baseline.
4. Preserve logical direction, keyboard order, focus visibility and zoom/reflow behavior. Treat
   legitimate two-dimensional content exceptions separately from the page shell.
5. Verify breakpoint edges, fixed-viewport container changes, long content, RTL and relevant
   loading/error states in a browser. Measure before claiming layout performance gains.

## Load detail when needed

- [Grid and intrinsic sizing — measurable engineering
  method](references/grid-and-intrinsic-sizing.md): consult for grid and intrinsic sizing.
- [Application shells, density and intentional
  overflow](references/responsive-shells-and-overflow.md): consult for responsive shells and
  overflow.
- [CSS layout accessibility, reflow and zoom — scoped
  verification](references/layout-accessibility-and-zoom.md): consult for layout accessibility and
  zoom.
- [Detailed decisions and failure cases](references/implementation-details.md): consult for a
  complex case, additional constraints or the remaining thematic references; apply only relevant
  steps.

## Completion and limits

Complete the requested change or diagnosis and the checks that support its claims. Report the
affected contract, observed results, actual versions/commands and material gaps. Keep structural
review, simulated cases, real integration and production evidence distinct. Imported research and
execution records are historical context, not results of the current task.

This folder works without SDD or other skills. Consult an available specialist only for a concrete
unresolved question; its absence is a gap only when essential to acceptance. Use the project's
existing record for a bounded change. This method grants no external access, publication or
destructive-action authority.
