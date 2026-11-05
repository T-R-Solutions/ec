---
name: http-api-contracts
description: Design or evolve HTTP resource, method, representation, error and compatibility contracts. Use for HTTP semantics and consumer-facing changes; skip non-HTTP protocols and test-suite strategy alone.
---

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

Complete the requested change or diagnosis and the checks that support its claims. Report the
affected contract, observed results, actual versions/commands and material gaps. Keep structural
review, simulated cases, real integration and production evidence distinct. Imported research and
execution records are historical context, not results of the current task.

This folder works without SDD or other skills. Consult an available specialist only for a concrete
unresolved question; its absence is a gap only when essential to acceptance. Use the project's
existing record for a bounded change. This method grants no external access, publication or
destructive-action authority.
