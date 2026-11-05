# Connector Configuration, Authority and Credentials

> Use the destination's existing requirements and evidence format; named workflow documents are examples, not prerequisites. Optional SDD mapping is configured outside this skill.

## Authority separation

| Concern | Canonical authority | Rebuild / lifecycle implication |
|---|---|---|
| Desired provider connector, scope, enabled event families, outbound destination policy, credential **reference**, redaction/retention *policy* | Versioned Ec YAML; three semantically equivalent editing projections | Reconstruct after restart from YAML revision; schema/permission/approval checks at publication |
| Provider OAuth access/refresh tokens, installation keys, signing secrets | Approved secrets manager, never YAML | Rotate and revoke via credential owner; audit references, not secret bytes |
| User consent session, PKCE verifier/state, installation handshake | Short-lived protected operational session | Bind to requesting user/browser, nonce and target provider installation; expire/remove |
| Actual provider installation/account and effective granted scopes | Provider-authoritative API, with freshness metadata | Do not infer provider authorization solely from the requested YAML |
| Inbound webhook request/replay receipt, outbox event, worker lease, failure and redelivery | Durable operational database | Survive restart; unique provider-scoped keys; retry policy |
| Connector's last confirmed revision and operational health | Reconciled status/projection | Unknown or drifted on lost confirmation; never call draft 'connected' |

**Notation:** resource fields below are design vocabulary, **not** an approved Ec API/schema. These are not YAML examples to publish; schema decisions belong to project workflow. Suggested semantic fields: connector ID, provider kind/version, tenant/project scope, installation identity, allowed repository set, event subscriptions, desired enablement, `credentialRef`/`signingKeyRef`, outbound destination ID, retry policy reference, exact desired revision, and unknown-extension preservation strategy.

## Authorization matrix required in the project workflow

For each action (create, connect, reauthorize, grant repository scope, rotate, test, replay, disable, delete, inspect payload), enumerate actor and scope (global/project/integration), Ec effective permission, provider account permission, required approval and audit record. Provider installation permission is **not** equivalent to Ec project administration. Binding an installation to another tenant or broadening repository scope demands revalidation.

## OAuth and delegated access

- Choose the provider's supported installation credential protocol, not a generic login token by convenience. GitHub App installation permissions determine available repositories/webhooks; over-scoped service/user tokens create avoidable risk.
- When using OAuth, apply RFC 9700's Authorization Code + PKCE recommendations, exact redirect matching, CSRF/state binding, issuer mix-up prevention and strong client authentication where appropriate. For browser-based apps, consult RFC 10017 (August 2026) on token handling and browser architecture. Do not blindly paste older OAuth 2.0 implicit-grant examples.
- Check OAuth callback state, target provider, audience, issuer, tenant/installation, resource scope and requesting user in one transaction. Reject wrong-tenant callbacks and stale authorization attempts. A token granted by a provider is **not** an Ec permission to alter configuration.
- Keep refresh tokens and webhook secrets in the secret broker; apply narrow resource scopes and documented rotation/revocation. Do not return secret values in connector read APIs or include them in debug, error or audit output.
- To rotate an inbound signing key safely: stage new key, establish a bounded overlap where the provider protocol permits multiple signatures/keys, verify both *approved* keys, revoke the old key and prove old-key rejection. Do not silently fall back to weak plaintext-token mode when signed verification fails.

## Three-mode parity and operational console

Use one typed semantic draft. Visual may use connector/event/destination mapping; Form uses explicit inputs; YAML exposes the full resource. Test a round trip that preserves advanced fields, provider extensions, resource IDs and secret references, while never exposing the underlying credential. An invalid YAML draft cannot modify provider subscriptions. "Saved draft", "published YAML", "provider install authorized", "webhook delivery accepted" and "remote effect confirmed" are **different states**. A service that is unprovisioned or whose provider health was not recently observed must not be shown as connected.

**Source anchors:** Ec normative sections 2, 11, 14-16, 18, 23, 26 and 34; GitHub App permission docs [O06], OAuth [O15-O17] and academic OAuth analyses [P01-P02] in the [research register](sources-and-decisions.md).
