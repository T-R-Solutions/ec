---
name: cicd-scheduler
description: Implement or review CI/CD eligibility, queue fairness, capacity reservations, leases and recovery. Skip graph editing, agent wire protocols and step execution.
---

# CI/CD Scheduler Engineering

## Aplicação no Ec

PostgreSQL é a autoridade global planejada de fila, tentativas, quotas e leases. Testar duas reservas concorrentes, fencing obsoleto e reatribuição; lease expirado não encerra efeitos externos.

## Scope and inputs

Inspect actual queue/job/slot/quota authorities, trust requirements, lease clocks, retry policy and
workload. Resolve fairness and progress requirements before selecting mechanisms.

Preserve the consumer's stack and policies. Product-specific examples in references do not establish
requirements for this task.

## Procedure

1. Separate DAG readiness from resource eligibility; explain blocked conditions using current
   trusted observations.
2. Reserve attempt, slot and quota atomically at their authority, or state the equivalent
   coordination guarantee and failure windows. Avoid unprotected read-then-write allocation.
3. Commit durable ownership before dispatch and reconcile uncertain delivery; retry whole safe
   transactions where the actual storage contract requires it.
4. Fence reassigned owners at every protected receiver. Expiry alone does not stop old work or undo
   its side effects.
5. Test competing schedulers, quota exhaustion, duplicate dispatch, restart, stale completion and
   starvation. A locking primitive alone does not prove fairness or exactly-once effects.

## Load detail when needed

- [transactions.md](references/transactions.md): consult for transactions.
- [leases.md](references/leases.md): consult for leases.
- [fairness.md](references/fairness.md): consult for fairness.
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
