---
name: http-api-contracts
description: Design or evolve HTTP resource, method, representation, error and compatibility contracts. Use for HTTP semantics and consumer-facing changes; skip non-HTTP protocols and test-suite strategy alone.
---

No Ec, siga `docs/development/task-verification.md` para acionar checks e
`docs/development/local-state.md` para arquivos de trabalho (caminhos desde a
raiz). Em outros consumidores, siga as políticas locais.

# HTTP API Contracts — Explicit Semantics, Evolvable Consumers, Measurable Evidence

## Scope and inputs

Inspect current endpoints, consumers, schema versions, authentication, storage effects and deployed
intermediaries. Preserve the existing protocol contract unless change is intentional.

Preserve the consumer's stack and policies. Product-specific examples in references do not establish
requirements for this task.

## Procedure

1. Specify resource identity, method semantics, request/response media and errors independently of
   implementation framework.
2. Define validation, absent/null/empty semantics and bounded request/query behavior. Treat an
   OpenAPI schema as one part of the contract, not proof of behavior.
3. Use representation validators and conditional writes where the contract supports them; separate
   precondition failure from application conflict.
4. Specify cache privacy, authorization, asynchronous acceptance versus completion and idempotency
   scope. A transport timeout leaves write outcome uncertain.
5. Check compatible consumer/version pairs and normal, invalid, denied, concurrent and duplicate
   outcomes with real adapters when available.

## Load detail when needed

- [Resource Identity and HTTP Method Semantics](references/resource-and-method-semantics.md):
  consult for resource and method semantics.
- [Conditional Requests: Validators, Lost Updates and
  Atomicity](references/conditional-requests-and-lost-update.md): consult for conditional requests
  and lost update.
- [API Versioning, Deprecation and Consumer
  Compatibility](references/versioning-deprecation-and-compatibility.md): consult for versioning
  deprecation and compatibility.
- [Detailed decisions and failure cases](references/implementation-details.md): consult for a
  complex case, additional constraints or the remaining thematic references; apply only relevant
  steps.

## Completion and limits

Registre contrato afetado, inputs, método, resultado e lacunas; distinga inspeção,
simulação e integração real. Registros importados não são resultados atuais.

Aplique apenas os passos pertinentes, usando o registro existente do projeto.
A skill funciona sem SDD; consulte auxiliares disponíveis somente para questões
distintas ainda não resolvidas. O método não amplia a autorização da tarefa.
