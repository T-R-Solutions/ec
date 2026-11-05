# Detailed decisions and failure cases

Read only for a decision needing more detail than the entry point. Apply the relevant steps to the
actual task; the sections are not a mandatory artifact or test checklist. Use existing project
contracts in their actual format. File names such as spec.md, plan.md or tasks.md are examples, not
required dependencies. Other skills are optional expertise, never enforcement mechanisms.

Ec references below are conditional consumer examples. They impose no Ec documents, YAML format,
stack or product policy on a generic task.

## Method

### 1 — Map principals, actors and trust domains

Identify subject, issuer, relying party, OAuth client, authorization server, resource server,
browser, backend and any federation broker. Separate *identity verification*, *session
establishment*, *authorization policies*, *provisioning* and *user-to-resource assignments*. Specify
canonical account linkage as `(tenant/scope, verified iss, verified sub)` or another explicitly
approved stable opaque identity; never auto-merge accounts by email/display name. Model tenant
crossing, malicious/compromised IdP and account lockout. Read [trust boundaries and threat
model](trust-boundaries-and-threat-model.md) and [multi issuer and account
linkage](multi-issuer-and-account-linkage.md).

### 2 — Pin provider metadata before sending secrets

Start with an explicitly trusted issuer/registration. Validate discovered `issuer` by exact
comparison; constrain HTTPS metadata, token and JWKS locations, DNS/IP/redirect handling and egress
to stop SSRF and mix-up. Do not trust arbitrary `jku`, `x5u`, dynamic redirect or endpoints
suggested by unsigned tokens. Handle metadata/JWKS key rotation without bypassing signature
verification. Read [provider discovery and key
management](provider-discovery-and-key-management.md).

### 3 — Specify an authorization-code flow

Prefer code flow with transaction-specific PKCE `S256`, cryptographically random `state` and (for
OIDC, as applicable) `nonce`; bind transaction state to the browser, issuer, client and registered
redirect. Require exact redirects and prevent unsolicited callbacks, missing/mismatched issuer, code
injection, replay and cross-session confusion. Use client authentication for confidential clients;
do not put client secrets in browser bundles. OAuth 2.1 remains an **Internet-Draft** at the time of
this package; use published RFC 9700 for OAuth 2.0 security BCP and the final OIDC specs for
normative requirements. Read [authorization code pkce and
callback](authorization-code-pkce-and-callback.md).

### 4 — Validate assertions before creating sessions

Use a maintained JOSE/OIDC library configured with allowed token types/algorithms and per-issuer
trusted JWKS. Verify signature, key identity, issuer, audience/client, `azp` when applicable,
subject, expiration, `iat`, `nbf`, nonce and applicable `auth_time`/ACR constraints. Handle JWKS
refresh safely; do not accept unsigned, wrong-key, wrong-issuer, expired or replayed ID Tokens. **An
ID Token is not an API access token**. Require introspection or resource-specific access-token
validation separately. See [id token validation and jose](id-token-validation-and-jose.md) and
[tokens and api audiences](tokens-and-api-audiences.md).

### 5 — Establish and maintain sessions

Create a new server-side/opaque browser session only after assertion validation; rotate
pre-authentication session identifiers, set protective cookies (`Secure`, `HttpOnly`, `SameSite`
where appropriate), enforce backend idle and absolute expiration, CSRF defenses and
step-up/reauthentication when required. Do not assume cookie flags make XSS harmless. For refresh
tokens, define secure storage, issuance policy, rotation or sender constraint, replay-family
invalidation, concurrency and revocation semantics. Prefer BFF design where its trade-offs fit the
application; do not mandate it without architecture context. See [browser sessions and
csrf](browser-sessions-and-csrf.md), [refresh rotation and
revocation](refresh-rotation-and-revocation.md) and [browser clients and
bff](browser-clients-and-bff.md).

### 6 — Implement logout and failure handling

Distinguish local RP session revocation, IdP logout, refresh/access token revocation and
cross-application single logout. Specify what works when the IdP is offline, back-channel events are
delayed or the browser closes; never display global logout as complete without evidence. Preserve
explicit errors without leaking tokens, subject PII, authorization codes or client secrets. See
[logout and recovery](logout-and-recovery.md).

### 7 — Prove interoperability at several levels

Test positive, negative, replay, race, timeout, issuer-switch, key-rotation and tenant-isolation
cases. Run library tests, simulated-IdP interoperability and wire-level tests, then IdP/vendor
conformance and staging tests with TLS, browser cookies, backend persistence and real login/logout.
Record provider, version, client registration and browser environment, without storing real tokens.
Reference fixtures in `validation/` are **synthetic and non-production**; they are not a certified
OIDC client/IdP and they do not validate Ec. See [interoperability and test
oracles](interoperability-and-test-oracles.md).

### 8 — Emit traceable outputs and limitations

Deliver: issuer/client registration matrix; trust-boundary diagram; protocol sequence and failure
cases; token/session lifecycle; account-linkage policy decision; interoperability/negative test
matrix; security review; observed tests and `NOT EXECUTED` cases; project workflow `REQ → AC → tasks
→ evidence`. Link to consulted official normative clauses, papers and registered risks. A
structurally valid project workflow bridge is not a production sign-off.


## Further references by decision

Read only the reference relevant to the unresolved decision. Research registers describe imported authorship, not current execution.

- [Authorization code, PKCE and callback contract](authorization-code-pkce-and-callback.md): authorization code pkce and callback.
- [Browser client architecture and token storage](browser-clients-and-bff.md): browser clients and bff.
- [Browser sessions, fixation, CSRF and timeouts](browser-sessions-and-csrf.md): browser sessions and csrf.
- [ID Token verification and JWT/JWK pitfalls](id-token-validation-and-jose.md): id token validation and jose.
- [Integration evidence, delivery and project workflow fit](integration-and-evidence.md): integration and evidence.
- [Test matrix: model, wire, integrated provider and production](interoperability-and-test-oracles.md): interoperability and test oracles.
- [Logout, reauthentication and partial failure semantics](logout-and-recovery.md): logout and recovery.
- [Multi-issuer, tenant mapping and identity linking](multi-issuer-and-account-linkage.md): multi issuer and account linkage.
- [Provider discovery, issuer trust and JWKS keys](provider-discovery-and-key-management.md): provider discovery and key management.
- [Refresh token lifecycle and replay response](refresh-rotation-and-revocation.md): refresh rotation and revocation.
- [Skill routing and boundary evidence](routing-and-test-oracles.md): routing and test oracles.
- [Peer-reviewed research: findings, scope, and limits](scientific-evidence-and-limits.md): source provenance or a disputed recommendation.
- [Source registry, authority and explicit choices](sources-and-decisions.md): source provenance or a disputed recommendation.
- [ID tokens, access tokens and API authorization](tokens-and-api-audiences.md): tokens and api audiences.
- [Threat boundaries for identity federation](trust-boundaries-and-threat-model.md): trust boundaries and threat model.
