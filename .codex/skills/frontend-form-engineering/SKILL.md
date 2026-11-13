---
name: frontend-form-engineering
description: Implement or review web form state, validation, submission and recovery. Use for interactive field and commit semantics; skip resource-schema ownership and unrelated component styling.
---

No Ec, siga `docs/development/task-verification.md` para acionar checks e
`docs/development/local-state.md` para arquivos de trabalho (caminhos desde a
raiz). Em outros consumidores, siga as políticas locais.

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

Registre contrato afetado, inputs, método, resultado e lacunas; distinga inspeção,
simulação e integração real. Registros importados não são resultados atuais.

Aplique apenas os passos pertinentes, usando o registro existente do projeto.
A skill funciona sem SDD; consulte auxiliares disponíveis somente para questões
distintas ainda não resolvidas. O método não amplia a autorização da tarefa.
