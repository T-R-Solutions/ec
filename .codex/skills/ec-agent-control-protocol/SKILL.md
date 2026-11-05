---
name: ec-agent-control-protocol
description: Implement or review Ec agent/control-plane authentication, version negotiation, heartbeats, leases and result receipts. Skip scheduling policy and local process execution.
---

# Ec Agent Control Protocol

## Aplicação no Ec

HTTP versionado é a decisão vigente. gRPC bidirecional/HTTP2/Protobuf e compatibilidade N/N−1 específica são propostas sujeitas a ADR e ensaios. Separar ACK durável, efeito aplicado e conclusão; verificar autenticação, sessão, tentativa, fencing, replay, reconexão e backpressure.

## Scope and inputs

Locate actual agent/server contracts, enrollment authority, session epochs, attempt identities,
persistence and protocol versions. Discover the implementation language instead of assuming a Rust
service exists.

Use the Ec consumer's current product requirements and implementation as authority. Locate them
rather than assuming filenames, schemas, endpoints or deployed features. Examples in this folder are
technical proposals until confirmed there.

## Procedure

1. Separate identity, session liveness, work ownership, delivery acknowledgment and scheduler eligibility.
2. Negotiate compatible protocol/capability versions explicitly; self-reported capabilities do not
   grant trust or permissions.
3. Authenticate enrollment and session replacement, bind heartbeat sequence to the session, and
   prevent duplicate or old observations from refreshing liveness.
4. Bind each claim and result to agent, session, attempt and server-authoritative fencing
   generation. Enforce stale-owner rejection at the receiver.
5. Test lost acknowledgments, reconnect, revoked credentials, duplicate/reordered events, restart
   and reassignment. A heartbeat is not proof of lease ownership or successful work.

## Load detail when needed

- [Protocol design and safety decision guide](references/protocol-decision-guide.md): consult for
  protocol decision guide.
- [Threat and compatibility matrix (review aid, not product
  policy)](references/threat-and-compatibility-matrix.md): consult for threat and compatibility
  matrix.
- [Activation routing and acceptance oracles](references/routing-and-test-oracles.md): consult for
  routing and test oracles.
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
