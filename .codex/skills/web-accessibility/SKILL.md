---
name: web-accessibility
description: Implement or evaluate semantic, keyboard, focus, perception and assistive-technology behavior on the web. Use for concrete accessibility requirements; skip visual taste and conformance claims from screenshots.
---

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

Complete the requested change or diagnosis and the checks that support its claims. Report the
affected contract, observed results, actual versions/commands and material gaps. Keep structural
review, simulated cases, real integration and production evidence distinct. Imported research and
execution records are historical context, not results of the current task.

This folder works without SDD or other skills. Consult an available specialist only for a concrete
unresolved question; its absence is a gap only when essential to acceptance. Use the project's
existing record for a bounded change. This method grants no external access, publication or
destructive-action authority.
