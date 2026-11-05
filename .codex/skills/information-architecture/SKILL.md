---
name: information-architecture
description: Design or evaluate navigation, taxonomy, labels and search for findability. Use for task-to-destination ambiguity; skip visual styling, router implementation and unsupported aesthetic judgments.
---

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

Complete the requested change or diagnosis and the checks that support its claims. Report the
affected contract, observed results, actual versions/commands and material gaps. Keep structural
review, simulated cases, real integration and production evidence distinct. Imported research and
execution records are historical context, not results of the current task.

This folder works without SDD or other skills. Consult an available specialist only for a concrete
unresolved question; its absence is a gap only when essential to acceptance. Use the project's
existing record for a bounded change. This method grants no external access, publication or
destructive-action authority.
