---
name: property-based-testing
description: Design or repair generated-input, metamorphic and state-machine tests with meaningful oracles, shrinking and replay. Skip generic suite strategy and claims of correctness from random sampling.
---

# Property-Based Testing — Generators, Oracles, Shrinking, Replay

## Scope and inputs

Identify the invariant, actual implementation, input domain, framework and reproducibility
requirements. A property must come from an independent contract, not the implementation output.

Preserve the consumer's stack and policies. Product-specific examples in references do not establish
requirements for this task.

## Procedure

1. Define domain partitions and a justified property, including invalid inputs with specified
   rejection behavior.
2. Generate representative values and command sequences without excessive discarded cases; enumerate
   small domains when useful.
3. Record seeds, budgets, versions and concrete counterexamples. Confirm deterministic replay before
   treating a generated failure as a regression.
4. Shrink while preserving preconditions and the same failure; a locally minimized witness is not
   necessarily a global minimum.
5. Check meaningful negative controls and surviving faults. Separate sequential models, concurrent
   execution and instrumented fuzzing; finite samples do not prove universal correctness.

## Load detail when needed

- [Properties, Independent Oracles and Metamorphic
  Relations](references/property-oracles-and-metaprops.md): consult for property oracles and
  metaprops.
- [Generator Design, Domain Coverage and Reject
  Limits](references/generators-and-domain-partitions.md): consult for generators and domain
  partitions.
- [Shrinking and Counterexample Minimization](references/shrinking-and-minimization.md): consult for
  shrinking and minimization.
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
