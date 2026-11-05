---
name: frontend-testing
description: Implement or diagnose frontend unit, component, browser and end-to-end tests. Use for interaction or harness evidence; skip generic test strategy and backend-only contracts.
---

# Frontend Testing — Observable Contracts, Deterministic Tests and Trustworthy Evidence

## Scope and inputs

Locate the actual frontend, dependencies, supported browsers, backend boundary, test commands and
expected user outcome. A synthetic target is a different test subject.

Preserve the consumer's stack and policies. Product-specific examples in references do not establish
requirements for this task.

## Procedure

1. Map each risk to the smallest test boundary that can observe it; distinguish pure logic,
   component rendering, browser behavior and backend integration.
2. Derive assertions from visible outcomes and an independent contract. Prefer semantic queries; a
   click or successful request alone does not establish completion.
3. Control fixtures, time, network and asynchronous conditions. Use bounded observation instead of
   sleeps and preserve failures rather than retrying them into a pass.
4. Exercise affected error, denied, empty, loading, cancellation and recovery states. Keep
   mocked-provider checks distinct from real integrations.
5. Inspect discovery, failures, skips and cleanup; retain reproductions and versions. Browser
   automation alone does not prove assistive-technology support or usability.

## Load detail when needed

- [Test layers, scope, and ownership](references/test-layers-and-scope.md): consult for test layers and scope.
- [Vue component testing with Vue Test Utils v2](references/vue-component-testing.md): consult for
  vue component testing.
- [Async causality and deterministic clocks](references/asynchronous-behavior-and-timers.md):
  consult for asynchronous behavior and timers.
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
