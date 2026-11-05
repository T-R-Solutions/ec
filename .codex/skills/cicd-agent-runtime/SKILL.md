---
name: cicd-agent-runtime
description: Implement or review CI/CD agent lifecycle, capability reporting, local capacity and recovery. Skip scheduler allocation, product wire protocols and step process isolation.
---

# CI/CD Agent Runtime

## Aplicação no Ec

Slots e recuperação pertencem ao Agent, a autoridade de tentativa pertence ao Server. SQLite privado/outbox persist-before-send/limpeza após ACK são propostas condicionadas a ADR e testes de crash e disco cheio; não declarar adoção por instalar esta skill.

## Scope and inputs

Inspect the actual agent/server boundary, slot ownership, authentication, supported capabilities,
persistence and reconnect policy. Liveness and work ownership are different contracts.

Preserve the consumer's stack and policies. Product-specific examples in references do not establish
requirements for this task.

## Procedure

1. Model enrollment/session, online/offline, draining and terminal states from authoritative observations.
2. Advertise verified capabilities with provenance and freshness; tags are not proof of trust or
   available capacity.
3. Reserve and release local slots once per attempt, reconciling duplicates, restart and partially
   completed cleanup.
4. Bind assignments and completion to session/attempt/fencing identity; reject obsolete owners and
   reconcile before accepting new work after reconnect.
5. Test heartbeat loss, restart with active work, duplicate assignments, drain and stale completion.
   Separate state-machine simulations from real agent/server evidence.

## Load detail when needed

- [Lifecycle and liveness](references/lifecycle-and-liveness.md): consult for lifecycle and liveness.
- [Capabilities and slots](references/capabilities-and-slots.md): consult for capabilities and slots.
- [Recovery and fencing](references/recovery-and-fencing.md): consult for recovery and fencing.
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
