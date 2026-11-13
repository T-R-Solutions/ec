---
name: ec-agent-control-protocol
description: Implement or review Ec agent/control-plane authentication, version negotiation, heartbeats, leases and result receipts. Skip scheduling policy and local process execution.
---

No Ec, siga `docs/development/task-verification.md` para acionar checks e
`docs/development/local-state.md` para arquivos de trabalho (caminhos desde a
raiz). Em outros consumidores, siga as políticas locais.

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

Registre contrato afetado, inputs, método, resultado e lacunas; distinga inspeção,
simulação e integração real. Registros importados não são resultados atuais.

Aplique apenas os passos pertinentes, usando o registro existente do projeto.
A skill funciona sem SDD; consulte auxiliares disponíveis somente para questões
distintas ainda não resolvidas. O método não amplia a autorização da tarefa.
