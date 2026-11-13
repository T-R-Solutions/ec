---
name: frontend-routing
description: Implement or review client-side URL, navigation, history and guard contracts, including Vue Router adapters. Skip information architecture, data-cache ownership and backend authorization design.
---

No Ec, siga `docs/development/task-verification.md` para acionar checks e
`docs/development/local-state.md` para arquivos de trabalho (caminhos desde a
raiz). Em outros consumidores, siga as políticas locais.

# Frontend Routing Engineering — URLs, History, Guards and Verifiable Navigation

## Scope and inputs

Inspect the route inventory, actual router/framework versions, hosting mode, resource scopes, old
bookmarks and supported navigation behavior.

Preserve the consumer's stack and policies. Product-specific examples in references do not establish
requirements for this task.

## Procedure

1. Define stable route identities and URL parse/serialize semantics for params, duplicate queries,
   defaults, empty values and unknown locations.
2. Choose history, hash or memory mode for the real hosting environment. Verify direct deep-link
   GET, refresh and missing assets separately from client navigation.
3. Distinguish successful, aborted, redirected, cancelled and failed navigation. Bound redirects and
   validate return destinations.
4. Fence asynchronous view loads by route and principal identity; guards never replace server
   authorization or justify stale protected content.
5. Test Back/Forward, dirty-form recovery, scope changes, focus/title/scroll and hosting fallback. A
   hash-mode fixture does not verify server fallback.

## Load detail when needed

- [URL Encoding, Query Contracts and Canonical
  Links](references/url-contracts-and-query-parameters.md): consult for url contracts and query
  parameters.
- [Browser History, Deep Links and Revisit Semantics](references/history-and-deep-links.md): consult
  for history and deep links.
- [Navigation Guards, Asynchronous Races and Failure
  Taxonomy](references/guards-and-navigation-failures.md): consult for guards and navigation
  failures.
- [Detailed decisions and failure cases](references/implementation-details.md): consult for a
  complex case, additional constraints or the remaining thematic references; apply only relevant
  steps.

## Completion and limits

Registre contrato afetado, inputs, método, resultado e lacunas; distinga inspeção,
simulação e integração real. Registros importados não são resultados atuais.

Aplique apenas os passos pertinentes, usando o registro existente do projeto.
A skill funciona sem SDD; consulte auxiliares disponíveis somente para questões
distintas ainda não resolvidas. O método não amplia a autorização da tarefa.
