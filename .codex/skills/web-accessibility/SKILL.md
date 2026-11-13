---
name: web-accessibility
description: Implement or evaluate semantic, keyboard, focus, perception and assistive-technology behavior on the web. Use for concrete accessibility requirements; skip visual taste and conformance claims from screenshots.
---

No Ec, siga `docs/development/task-verification.md` para acionar checks e
`docs/development/local-state.md` para arquivos de trabalho (caminhos desde a
raiz). Em outros consumidores, siga as políticas locais.

# Web Accessibility Engineering

## Scope and inputs

Identify the user task, affected states, actual accessibility target and
browser/assistive-technology baseline. Do not invent a conformance level or legal requirement.

Preserve the consumer's stack and policies. Product-specific examples in references do not establish
requirements for this task.

## Procedure

1. Trace complete tasks, including denied actions, errors and recovery, and map applicable criteria
   to observable behavior.
2. Prefer native semantics and verify accessible names and relationships; ARIA attributes alone do
   not provide interaction.
3. Implement and test keyboard operation, modality, focus visibility/restoration and alternatives to
   pointer-only interaction.
4. Check affected contrast, reflow, text sizing, motion, target size and status feedback against the
   selected criteria and their exceptions.
5. Combine appropriate automated, browser, manual and assistive-technology checks. Report each
   environment and untested criterion; a clean scanner is not a conformance certificate.

## Load detail when needed

- [Semantic HTML, roles, names and information structures](references/semantics-and-names.md):
  consult for semantics and names.
- [Keyboard/focus and modal dialog contract](references/keyboard-focus-and-dialogs.md): consult for
  keyboard focus and dialogs.
- [Testing layers, oracles and assistive
  technology](references/testing-oracles-and-assistive-technology.md): consult for testing oracles
  and assistive technology.
- [Detailed decisions and failure cases](references/implementation-details.md): consult for a
  complex case, additional constraints or the remaining thematic references; apply only relevant
  steps.

## Completion and limits

Registre contrato afetado, inputs, método, resultado e lacunas; distinga inspeção,
simulação e integração real. Registros importados não são resultados atuais.

Aplique apenas os passos pertinentes, usando o registro existente do projeto.
A skill funciona sem SDD; consulte auxiliares disponíveis somente para questões
distintas ainda não resolvidas. O método não amplia a autorização da tarefa.
