---
name: information-architecture
description: Design or evaluate navigation, taxonomy, labels and search for findability. Use for task-to-destination ambiguity; skip visual styling, router implementation and unsupported aesthetic judgments.
---

No Ec, siga `docs/development/task-verification.md` para acionar checks e
`docs/development/local-state.md` para arquivos de trabalho (caminhos desde a
raiz). Em outros consumidores, siga as políticas locais.

# Information Architecture — Task-Based Findability

## Scope and inputs

Collect audience tasks, content/resource inventory, existing labels, permission scopes and observed
navigation failures. Treat untested user expectations as hypotheses.

Preserve the consumer's stack and policies. Product-specific examples in references do not establish
requirements for this task.

## Procedure

1. Map actual tasks to destinations and distinguish global, local and contextual navigation.
2. Choose labels and grouping from content and user vocabulary; do not derive the whole navigation
   tree from backend entities or an arbitrary depth limit.
3. Define location, scope, breadcrumbs, search/filter semantics and denied or missing destinations
   without leaking protected resources.
4. Compare proportionate alternatives, including retaining the current organization; plan old-link
   and renamed-destination transitions.
5. Evaluate representative finding and recovery tasks using appropriate observation or research. A
   tree diagram or screenshot alone does not establish findability.

## Load detail when needed

- [Global, local, and contextual navigation](references/navigation-and-context.md): consult for
  navigation and context.
- [Labels, categories, and information scent](references/labeling-and-information-scent.md): consult
  for labeling and information scent.
- [Falsifiable acceptance criteria and evidence
  reporting](references/acceptance-contracts-and-reporting.md): consult for acceptance contracts and
  reporting.
- [Detailed decisions and failure cases](references/implementation-details.md): consult for a
  complex case, additional constraints or the remaining thematic references; apply only relevant
  steps.

## Completion and limits

Registre contrato afetado, inputs, método, resultado e lacunas; distinga inspeção,
simulação e integração real. Registros importados não são resultados atuais.

Aplique apenas os passos pertinentes, usando o registro existente do projeto.
A skill funciona sem SDD; consulte auxiliares disponíveis somente para questões
distintas ainda não resolvidas. O método não amplia a autorização da tarefa.
