---
name: css-layout-engineering
description: Implement or diagnose CSS sizing, Grid/Flexbox, responsive layout, overflow and reflow. Use for concrete layout behavior; skip branding, navigation taxonomy and framework state.
---

No Ec, siga `docs/development/task-verification.md` para acionar checks e
`docs/development/local-state.md` para arquivos de trabalho (caminhos desde a
raiz). Em outros consumidores, siga as políticas locais.

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

Registre contrato afetado, inputs, método, resultado e lacunas; distinga inspeção,
simulação e integração real. Registros importados não são resultados atuais.

Aplique apenas os passos pertinentes, usando o registro existente do projeto.
A skill funciona sem SDD; consulte auxiliares disponíveis somente para questões
distintas ainda não resolvidas. O método não amplia a autorização da tarefa.
