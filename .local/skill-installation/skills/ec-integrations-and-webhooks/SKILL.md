---
name: ec-integrations-and-webhooks
description: Implement or review Ec connector administration, credential references, webhook setup and diagnostics. Skip generic VCS event processing and identity-federation protocol design.
---

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

Complete the requested change or diagnosis and the checks that support its claims. Report the
affected contract, observed results, actual versions/commands and material gaps. Keep structural
review, simulated cases, real integration and production evidence distinct. Imported research and
execution records are historical context, not results of the current task.

This folder works without SDD or other skills. Consult an available specialist only for a concrete
unresolved question; its absence is a gap only when essential to acceptance. Use the project's
existing record for a bounded change. This method grants no external access, publication or
destructive-action authority.
