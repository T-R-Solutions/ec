---
name: cicd-build-cache
description: Implement or review CI/CD build-cache keys, trust scopes, integrity and eviction. Skip immutable run-artifact retention and runtime scheduling.
---

No Ec, siga `docs/development/task-verification.md` para acionar checks e
`docs/development/local-state.md` para arquivos de trabalho (caminhos desde a
raiz). Em outros consumidores, siga as políticas locais.

# CI/CD Build Cache Engineering

## Scope and inputs

Inspect declared build inputs/outputs, toolchain/platform identity, producer trust, namespace,
backend and fallback behavior. Cacheability is a contract to establish, not an assumption.

Preserve the consumer's stack and policies. Product-specific examples in references do not establish
requirements for this task.

## Procedure

1. Derive keys from all output-affecting inputs, including relevant environment and dependencies;
   label omitted or nondeterministic inputs.
2. Separate content identity from action identity and provenance. Verify objects before restore and
   prevent partial publication or path escape.
3. Scope reads/writes by tenant, repository and trust tier; untrusted contributors must not populate
   trusted cache entries.
4. Bound storage and restoration cost, define eviction/invalidation and handle unavailable or
   corrupt caches through a correct uncached path where allowed.
5. Test poisoning, missing inputs, concurrent publication, corruption and rebuild equivalence. A hit
   rate alone proves neither correctness nor performance gain.

## Load detail when needed

- [Keys and hermeticity](references/keys-and-hermeticity.md): consult for keys and hermeticity.
- [Trust and cache poisoning](references/trust-and-poisoning.md): consult for trust and poisoning.
- [Content-addressed storage](references/cas-and-integrity.md): consult for cas and integrity.
- [Detailed decisions and failure cases](references/implementation-details.md): consult for a
  complex case, additional constraints or the remaining thematic references; apply only relevant
  steps.

## Completion and limits

Registre contrato afetado, inputs, método, resultado e lacunas; distinga inspeção,
simulação e integração real. Registros importados não são resultados atuais.

Aplique apenas os passos pertinentes, usando o registro existente do projeto.
A skill funciona sem SDD; consulte auxiliares disponíveis somente para questões
distintas ainda não resolvidas. O método não amplia a autorização da tarefa.
