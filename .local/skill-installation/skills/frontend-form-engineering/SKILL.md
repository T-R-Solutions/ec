---
name: frontend-form-engineering
description: Implement or review web form state, validation, submission and recovery. Use for interactive field and commit semantics; skip resource-schema ownership and unrelated component styling.
---

# Frontend Form Engineering — Evidence-Based State, Validation and Submission

## Scope and inputs

Identify field semantics, original revision, trusted validator, submit acknowledgment, errors and
permission boundaries. Distinguish configuration editing from operational commands.

Preserve the consumer's stack and policies. Product-specific examples in references do not establish
requirements for this task.

## Procedure

1. Model missing, null, empty, zero and false separately. Preserve advanced and unknown fields
   according to the actual contract.
2. Separate baseline, draft, dirty/touched state, validation, pending submission, confirmed save,
   rejection and conflict.
3. Use labeled native controls and actionable errors. Choose validation timing for the interaction,
   including composition input; browser validation does not authorize a write.
4. Bind submission to its payload and revision. Ignore stale responses, preserve edits made during a
   save, and reconcile duplicate or uncertain submissions.
5. Test conditional fields, invalid input, server denial, conflicts, reset and keyboard recovery. A
   local state change or emitted event does not prove persistence.

## Load detail when needed

- [Field contracts and controlled form state](references/form-contract-and-state.md): consult for
  form contract and state.
- [Async validation, submissions, concurrent edits and
  acknowledgements](references/async-submission-and-concurrency.md): consult for async submission
  and concurrency.
- [Conditional, advanced and repeated form fields](references/conditional-and-dynamic-fields.md):
  consult for conditional and dynamic fields.
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
