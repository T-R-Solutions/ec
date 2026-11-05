# Audited Primary Sources, Scientific Literature and Decisions

> Imported authoring record. Research dates, consultations and execution claims below belong to the supplied source package; they were not reproduced in this adaptation. Referenced validation scripts, fixtures, product norms and logs are not bundled unless present in this folder. Verify their availability and provenance before use. No historical PASS is current acceptance.

Research basis: inspected on **2026-10-31**. The references below are a bounded, topic-specific primary-source review, not a claim to have exhausted every publication. "Sections examined" means substantive passages were accessible via the official source or author PDF. "Abstract/metadata only" means **no full-text claim**. The two Ec files were read in full before authoring. No forums, user comments, benchmarks or unverifiable facts were used as evidence.

## Official/primary specification register (24 entries)

| ID | Source and location | Access inspected | Specific support / applicability |
|---|---|---|---|
| O01 | Ec, `EC-REGRAS-DE-DESIGN-E-PRODUTO.md` (supplied file) | Full local text, 447 lines | Canonical desired YAML, three-mode parity, secret references, integration administration, confirmation-only UI, Rust/PostgreSQL baseline. |
| O02 | Ec, `EC-REGRAS-CRIACAO-DE-TELAS-E-IMAGENS.md` (supplied file) | Full local text, 206 lines | Integration console, scope, UX, accessibility and state truth. Visual guidance applied to method only; no image requested. |
| O03 | [GitHub — Validating webhook deliveries](https://docs.github.com/en/webhooks/using-webhooks/validating-webhook-deliveries) | Signature requirements and published test vector | Exact raw body, SHA-256 HMAC `sha256=` header, constant-time verification. |
| O04 | [GitHub — Best practices for using webhooks](https://docs.github.com/en/webhooks/using-webhooks/best-practices-for-using-webhooks) | Event selection, response deadline, redelivery, GUID sections | Provider-specific 10-second receiver deadline, 2xx response, GUID stable on redelivery. Does **not** establish signed timestamp. |
| O05 | [GitHub — Redelivering webhooks](https://docs.github.com/en/webhooks/testing-and-troubleshooting-webhooks/redelivering-webhooks) | Retry/redelivery limitations | GitHub does not automatically redeliver failed deliveries; authorized manual redelivery horizon. |
| O06 | [GitHub — Choosing permissions for a GitHub App](https://docs.github.com/en/apps/creating-github-apps/registering-a-github-app/choosing-permissions-for-a-github-app) | Permissions, resource scope, approval | Installation/app permission design with minimum scope. |
| O07 | [GitLab — Webhooks](https://docs.gitlab.com/user/project/integrations/webhooks/) | Signing token, verification example, headers, retry/disable and history sections | **19.1+** signing-token profile, `webhook-id`, signed timestamp/raw body; legacy token header is weaker. Do not project this version-specific behavior onto older installations. |
| O08 | [Slack — Verifying requests](https://api.slack.com/docs/verifying-requests-from-slack) | Signing secret and request timestamp sections | Provider-specific signature input and replay clock policy. |
| O09 | [Stripe Support — Troubleshooting webhook delivery](https://support.stripe.com/questions/webhooks-what-to-do-when-the-http-status-code-starts-with-a-four-%284xx%29-or-five-%285xx%29?locale=en-GB) | Signature failures/raw body and tolerance sections | Parsing middleware must not alter the body before signature check; timestamp freshness. Not used to assert universal retry duration. |
| O10 | [OWASP — Webhook Security Cheat Sheet](https://cheatsheetseries.owasp.org/cheatsheets/Webhook_Security_Cheat_Sheet.html) | Authentication, replay, duplicates, idempotency and abuse sections | Webhook-specific verification; explicitly notes GitHub's unsigned delivery header. Guidance, not a replacement for provider contract. |
| O11 | [OWASP — SSRF Prevention Cheat Sheet](https://cheatsheetseries.owasp.org/cheatsheets/Server_Side_Request_Forgery_Prevention_Cheat_Sheet.html) | Domain/URL/DNS rebinding and redirect sections | Exact allowlists, connection-time address enforcement and redirects. |
| O12 | [OWASP — CI/CD Security Cheat Sheet](https://cheatsheetseries.owasp.org/cheatsheets/CI_CD_Security_Cheat_Sheet.html) | Third-party integrations, credential hygiene and IAM | CI/CD integration governance and secret handling. |
| O13 | [IETF RFC 9110 — HTTP Semantics](https://www.rfc-editor.org/rfc/rfc9110.html) | §§ 9.2, 15.3 | Idempotence and `202 Accepted` semantics; no remote business-result guarantee. |
| O14 | [IETF RFC 9421 — HTTP Message Signatures](https://www.rfc-editor.org/rfc/rfc9421.html) | §§ 3.2, 7.1–7.2 | Signature covered components, application requirements, nonce and replay, TLS remains necessary. Not the protocol used by all providers. |
| O15 | [IETF RFC 9700 — OAuth 2.0 Security BCP](https://www.rfc-editor.org/rfc/rfc9700.html) | §§ 2.1–2.2, 4.14 | Modern OAuth controls, PKCE and refresh/replay security. |
| O16 | [IETF RFC 10017 — Browser-Based OAuth Applications](https://www.rfc-editor.org/rfc/rfc10017.html) | Abstract/status and browser threat scope | **Published August 2026**; applicable when Ec uses browser OAuth flows, not when service-to-service only. |
| O17 | [IETF RFC 7636 — PKCE](https://www.rfc-editor.org/rfc/rfc7636.html) | Abstract and attack/countermeasure definitions | Authorization-code verifier/challenge binding; updated guidance in RFC 9700. |
| O18 | [CloudEvents v1.0 specification](https://github.com/cloudevents/spec/blob/main/cloudevents/spec.md) | `source`, `id`, `specversion` sections | Producer-scoped event identity; not a transport-authentication or exactly-once guarantee. |
| O19 | [PostgreSQL 18 — INSERT / ON CONFLICT](https://www.postgresql.org/docs/18/sql-insert.html) | Conflict/unique-index semantics | DB uniqueness for durable event admission, subject to real Ec schema/transaction tests. |
| O20 | [PostgreSQL — Transactions](https://www.postgresql.org/docs/current/tutorial-transactions.html) | Transaction `BEGIN`, `COMMIT`, `ROLLBACK` concept | Atomic inbox + outbox records; local SQLite example is not PostgreSQL evidence. |
| O21 | [GitHub — Viewing webhook deliveries](https://docs.github.com/en/webhooks/testing-and-troubleshooting-webhooks/viewing-webhook-deliveries) | Delivery metadata, payload and trace sections | Provider diagnostics and manual investigations with appropriate authorization. |
| O22 | [W3C — WCAG 2.2](https://www.w3.org/TR/WCAG22/) | Status messages, keyboard, focus requirements referenced by norms | Targeted UI accessibility checks; no real browser conformance tested. |
| O23 | [YAML 1.2.2 specification](https://yaml.org/spec/1.2/) | Revision/version and data model | YAML-only declarative Ec resources, not webhook HTTP serialization. |
| O24 | [Kubernetes — Admission Webhook Good Practices](https://kubernetes.io/docs/concepts/cluster-administration/admission-webhooks-good-practices/) | Timeouts, side-effects, failure handling | **Analogical** insight on webhook latency/failure; Kubernetes admission hooks are **not** a model of external event webhooks in Ec. |

## Peer-reviewed/academic record (8 entries)

| ID | Work and original URL | Inspection depth | Carefully bounded insight |
|---|---|---|---|
| P01 | Fett, Küsters, Schmitz, [*A Comprehensive Formal Security Analysis of OAuth 2.0*](https://publ.sec.uni-stuttgart.de/fettkuestersschmitz-ccs-2016.pdf), ACM CCS 2016, DOI 10.1145/2976749.2978385 | Author PDF: abstract, introduction, attack/fix discussion sections examined | OAuth security depends on binding, provider and web threat assumptions; not a proof of an Ec implementation. |
| P02 | Fett, Küsters, Schmitz, [*The Web SSO Standard OpenID Connect*](https://arxiv.org/abs/1704.08539), IEEE CSF 2017, DOI 10.1109/CSF.2017.20 | Official conference metadata and author abstract inspected; full paper **not** examined | OIDC session and provider binding security is a distinct concern; not a webhook signature study. |
| P03 | Garcia-Molina, Salem, [*Sagas*](https://www.cs.princeton.edu/techreports/1987/070.pdf), ACM SIGMOD 1987 | Princeton original paper PDF, abstract, introduction and transaction/compensation discussion examined | Compensating long-lived operations are different from ACID atomicity with remote endpoints; no exactly-once webhooks claim. |
| P04 | Helland, [*Life beyond Distributed Transactions: an Apostate's Opinion*](https://www.cidrdb.org/cidr2007/papers/cidr07p15.pdf), CIDR 2007 | Original **position paper** PDF, abstract and discussion sections examined | Explicit identity/communication and uncertainty boundaries when global transactions do not exist. This is a position paper, not an empirical guarantee. |
| P05 | Saltzer, Reed, Clark, [*End-to-End Arguments in System Design*](https://web.mit.edu/saltzer/www/publications/endtoend/endtoend.pdf), ACM TOCS 2(4), 1984, DOI 10.1145/357401.357402 | Author-hosted full paper; introductory and duplicate-suppression/failure examples examined | Application-level duplicate suppression and verification remain necessary beyond transport assurances. |
| P06 | Kreps, Narkhede, Rao, [*Kafka: A Distributed Messaging System for Log Processing*](https://www.microsoft.com/en-us/research/wp-content/uploads/2017/09/Kafka.pdf), NetDB 2011 | Original proceedings paper PDF, introduction, design and limitations sections examined | Durable asynchronous messaging and offset/replay are useful reference concepts; Ec **not** presumed to use Kafka. |
| P07 | Dean, Barroso, [*The Tail at Scale*](https://barroso.org/publications/TheTailAtScale.pdf), Communications ACM 56(2), 2013, DOI 10.1145/2408776.2408794 | Author PDF abstract, variability/latency discussion examined | Timeouts, queue isolation and tail latency matter; **no Ec latency benchmark** derives from this paper. |
| P08 | DeCandia et al., [*Dynamo: Amazon's Highly Available Key-Value Store*](https://doi.org/10.1145/1294261.1294281), SOSP 2007 | Official ACM abstract and publication metadata **only** | Versioned/conflicting observed state motivates caution; no claim that its design is required for Ec. |

## Engineering synthesis and explicit decisions to resolve

| ID | Source-supported design conclusion | Decision ownership / residual limit |
|---|---|---|
| D01 | A signature authenticates **only its documented signed components**. | SDD sets provider/version trust profiles; GitHub delivery GUID is not signed. |
| D02 | Verification must consume exact body bytes before JSON interpretation. | Choose Rust transport/body limits and libraries; test provider vectors. |
| D03 | Key identity belongs to trusted connector scope, not to payload claims. | Decide tenant-aware secret broker, key rotation and audit. |
| D04 | Authenticated timestamps allow bounded freshness; not all providers include them. | Decide allowable clock skew and replay retention per provider; disclose weaker profiles. |
| D05 | Use atomic inbound receipt + durable work enqueue and unique dedup keys. | Exact PostgreSQL schema, storage budget, retention and failure recovery in SDD. |
| D06 | `202`/2xx and remote HTTP success are **not** proof of business completion. | Define named status/receipt contract and operator UX in SDD. |
| D07 | OAuth permissions and Ec authorization are distinct. | Installation binding, PKCE/state/issuer and scopes require real provider/Ec tests. |
| D08 | Outbound URL callbacks are SSRF-prone; the actual connected address must be checked. | Select Rust resolver/HTTP client/egress mechanism, test DNS race/redirect; example has no real network enforcement. |
| D09 | Retry after unknown remote result can repeat a side effect. | Define idempotency and manual replay policy, dead-letter semantics and audit. |
| D10 | YAML is desired config; tokens, deliveries and failures are not declarative. | Apply both Ec norms; do not copy provider HTTP JSON into YAML resource formats. |
| D11 | GitLab signing-token support is version-dependent; plaintext legacy headers are weaker. | Explicit provider compatibility and upgrade/downgrade policy in SDD. |
| D12 | Reliable acknowledgments and transport retries require end-to-end effect checks. | No claim of exactly-once external effects; verify with real integrations later. |

## Limitations and source conflicts

- **No Ec application repository, real secrets backend, Rust server, PostgreSQL instance or provider credentials were available.** Included tests are model and local loopback evidence only.
- GitHub, GitLab, Slack and CloudEvents have **different** identification, signature and replay semantics. A universal signature verifier would be unsafe without a per-provider contract.
- A GitLab legacy migration recommendation to fall back to a plaintext token is deliberately **not** accepted as unconditional security behavior: it needs explicit migration authorization and a security gate, never automatic downgrade after bad MAC.
- Kubernetes admission-webhook failure policies are a specialized Kubernetes subsystem; any applicability is analogical only.
- RFC 10017 is later than many legacy OAuth implementation examples; current browser guidance takes priority where applicable. Neither OAuth nor provider JSON payloads become Ec configuration formats.
- No forum, social sentiment, AI-generated source, unsourced numerical claim or fabricated benchmark was used.
