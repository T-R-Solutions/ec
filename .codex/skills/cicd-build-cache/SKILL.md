---
name: cicd-build-cache
description: Implement or review CI/CD build-cache keys, trust scopes, integrity and eviction. Skip immutable run-artifact retention and runtime scheduling.
---

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

Complete the requested change or diagnosis and the checks that support its claims. Report the
affected contract, observed results, actual versions/commands and material gaps. Keep structural
review, simulated cases, real integration and production evidence distinct. Imported research and
execution records are historical context, not results of the current task.

This folder works without SDD or other skills. Consult an available specialist only for a concrete
unresolved question; its absence is a gap only when essential to acceptance. Use the project's
existing record for a bounded change. This method grants no external access, publication or
destructive-action authority.
