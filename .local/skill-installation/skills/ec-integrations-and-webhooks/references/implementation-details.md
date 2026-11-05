# Detailed decisions and failure cases

Read only for a decision needing more detail than the entry point. Apply the relevant steps to the
actual task; the sections are not a mandatory artifact or test checklist. Use existing project
contracts in their actual format. File names such as spec.md, plan.md or tasks.md are examples, not
required dependencies. Other skills are optional expertise, never enforcement mechanisms.

Product rules, stack choices and authority locations must be verified in the Ec consumer. Examples
do not establish deployed capabilities or override its current contract.

## Implementation method

1. **Discover authority, contract and direction.** Read the applicable Ec project
   specificationifications, actual Rust control-plane code, Vue/TypeScript UI, YAML schemas,
   configuration publication, existing secret manager, identity/permissions, provider versions and
   operations storage. Enumerate each integration's tenant/project, provider installation or
   account, repository scope, inbound subscription, outbound endpoint and trust boundary. State what
   is implemented versus proposed; do **not** invent Ec endpoints or provider guarantees.
2. **Model three independent planes.** Define desired connector scope, subscriptions, policy,
   credential **references** and endpoint policy exclusively as versioned canonical Ec YAML;
   Visual/Form/YAML edit one semantic resource without discarding unsupported fields. Put actual
   OAuth credentials, API keys and webhook signing keys in an approved secrets backend; put OAuth
   transactions, inbound receipts, outbox, attempts, delivery health and audit in operational
   storage. Reuse `ec-configuration-publication` for optimistic revision control, authorization and
   reconciliation. Never place secret material in YAML, URLs, response bodies or logs.
3. **Select least-privilege identity and authorize changes.** Choose documented provider-specific
   installation authentication or OAuth Authorization Code + PKCE when applicable. Validate
   state-to-browser/session binding, redirect URI, issuer and audience, account/installation
   identity, authorized resource scope, and refresh/revocation semantics. Apply RFC 9700 and, for
   browser flows, RFC 10017; never treat OAuth as a webhook signature. Revalidate Ec action/resource
   permission and provider ownership before connecting, changing scope, rotating credentials,
   replaying or deleting; prevent self-escalation and cross-tenant credential reuse.
4. **Specify each inbound trust profile explicitly.** Identify the provider's signed bytes, headers,
   algorithm, key rotation, timestamp and replay properties; enforce body/header size and rate
   limits **before expensive work**. Verify the exact **raw** HTTP body and any signed metadata with
   a constant-time comparison before interpreting event JSON. GitHub's `X-Hub-Signature-256` signs
   the body but **not** a timestamp or `X-GitHub-Delivery`: deduplication by delivery GUID does not
   prove cryptographic freshness. GitLab's current signing-token profile covers message ID,
   timestamp and body; do not confuse it with the legacy plaintext `X-Gitlab-Token`. Reject
   missing/invalid signatures, stale authenticated timestamps where available, mismatched
   tenant/provider identity, invalid MIME/schema and unknown critical event types. Treat rotation as
   overlapping **approved** keys, never unauthenticated downgrade.
5. **Admit durably, then acknowledge accurately.** Define a provider-scoped, tenant-scoped
   deduplication identity and conflict policy; use a database uniqueness constraint plus an atomic
   inbox/outbox or equivalently durable enqueue transaction. A 2xx/202 response only means the
   defined receipt/enqueue boundary succeeded, **not** that a downstream pipeline or remote action
   completed. On storage failure, return retryable failure rather than acknowledge an event lost in
   memory. Preserve authentic redeliveries and audit duplicate/conflicting payloads without
   replaying side effects. Provider order and source event uniqueness are **not** universal; let
   `cicd-vcs-event-processing` validate event semantics and fix immutable source revisions.
6. **Constrain outbound callbacks as an SSRF boundary.** Prefer approved destination IDs or exact
   host allowlists and HTTPS; prohibit embedded credentials, URL userinfo, unsafe schemes, redirects
   to unapproved targets, loopback/link-local/metadata/private addresses unless explicitly justified
   and isolated. Validate DNS resolutions **and the actual connected address**, including retries,
   IPv6, redirects and rebinding; preserve TLS hostname validation. A hostname-only regex or
   one-time DNS lookup is insufficient. Sign outbound messages only under an approved versioned
   contract; minimize sensitive fields, rate-limit tenants, and bind callbacks to authorized scopes.
7. **Make retries observable, bounded and safe.** Use durable attempt IDs, stable logical event
   identity, idempotency keys where the peer supports them, deadlines, capped exponential backoff
   with jitter, rate limits, circuit behavior and a reviewed dead-letter/replay policy. Distinguish
   `accepted`, `queued`, `attempted`, `remote HTTP accepted`, `confirmed` (only with authoritative
   proof), `retrying`, `failed`, `unknown` and `disabled` as **illustrative** states pending Ec
   contracts. After timeout, the remote side effect is unknown; do not claim exactly-once delivery
   or use blind replay of non-idempotent requests. Gate retries and manual redelivery on the current
   YAML revision, authorization and secret rotation policy.
8. **Verify hostile paths and user-visible truth.** Build a provider test matrix (tampered body,
   changed encoding, valid signature on wrong tenant, timestamp skew, header spoof, duplicate,
   reordered events, storage crash before ACK, concurrent receipt, retry after lost ACK, DNS
   rebinding, redirect escape, expired OAuth grant, over-scoped token, secret rotation, stale
   connector revision, outbound 2xx then downstream failure). Provide correlation IDs, redacted
   diagnostic evidence, retention and recovery paths. Test **actual** Rust, PostgreSQL, provider
   sandbox/live contracts, security boundaries, Vue/TypeScript UI, keyboard/accessibility and
   Visual/Form/YAML parity before claiming Ec integration conformance.


## Further references by decision

Read only the reference relevant to the unresolved decision. Research registers describe imported authorship, not current execution.

- [Connector Configuration, Authority and Credentials](connector-configuration-and-credentials.md): connector configuration and credentials.
- [Inbound Webhook Trust, Replay and Durable Admission](inbound-webhook-security-and-admission.md): inbound webhook security and admission.
- [Outbound Webhooks, SSRF, Reliability and Operational Console](outbound-delivery-and-observability.md): outbound delivery and observability.
- [Routing and Real Ec Acceptance Test Oracles](routing-and-test-oracles.md): routing and test oracles.
- [Audited Primary Sources, Scientific Literature and Decisions](sources-and-decisions.md): source provenance or a disputed recommendation.
