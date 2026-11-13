---
name: ec-integrations-and-webhooks
description: Implement or review Ec connector administration, credential references, webhook setup and diagnostics. Skip generic VCS event processing and identity-federation protocol design.
---

No Ec, siga `docs/development/task-verification.md` para acionar checks e
`docs/development/local-state.md` para arquivos de trabalho (caminhos desde a
raiz). Em outros consumidores, siga as políticas locais.

# Ec Integrations and Webhooks

## Scope and inputs

Inspect actual provider contracts, connector scope, credential storage, outbound destinations,
webhook endpoints and authorized administration operations.

Use the Ec consumer's current product requirements and implementation as authority. Locate them
rather than assuming filenames, schemas, endpoints or deployed features. Examples in this folder are
technical proposals until confirmed there.

## Procedure

1. Separate desired connector configuration from secret material, provider receipts and observed
   connectivity; never persist raw credentials in declarative configuration.
2. Bind credential issuance, rotation, revocation and callback handling to the real actor, scope and
   provider contract.
3. Constrain outbound URL resolution and redirects, and authenticate inbound deliveries with bounded
   parsing, replay handling and safe diagnostics.
4. Distinguish saved, verified, connected and operational states. A test-connection response cannot
   prove all scopes, webhook delivery or downstream authorization.
5. Test wrong scope, revoked secrets, duplicate delivery, endpoint changes and partial
   setup/removal; report external provider checks separately from fixtures.

## Load detail when needed

- [Connector Configuration, Authority and
  Credentials](references/connector-configuration-and-credentials.md): consult for connector
  configuration and credentials.
- [Inbound Webhook Trust, Replay and Durable
  Admission](references/inbound-webhook-security-and-admission.md): consult for inbound webhook
  security and admission.
- [Routing and Real Ec Acceptance Test Oracles](references/routing-and-test-oracles.md): consult for
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
