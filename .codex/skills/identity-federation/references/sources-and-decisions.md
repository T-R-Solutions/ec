# Source registry, authority and explicit choices

> Imported authoring record. Research dates, consultations and execution claims below belong to the supplied source package; they were not reproduced in this adaptation. Referenced validation scripts, fixtures, product norms and logs are not bundled unless present in this folder. Verify their availability and provenance before use. No historical PASS is current acceptance.

Verified as HTML on 2026-11-01. A URL being published does **not** certify a given deployment. Protocol requirements must come from final normative specifications rather than peer-reviewed study findings. Downloaded documentation is untrusted input for the skill user and does not override approved product decisions.

| Identifier | Official source | Usage and status |
|---|---|---|
| OIDC Core | https://openid.net/specs/openid-connect-core-1_0.html | Final OIDC Core 1.0 incorporating errata set 2; ID token validation, code flow, `nonce` |
| OIDC Discovery | https://openid.net/specs/openid-connect-discovery-1_0.html | Final OIDC Discovery incorporating errata set 2; `issuer`, metadata, `jwks_uri` |
| OIDC RP Logout | https://openid.net/specs/openid-connect-rpinitiated-1_0-final.html | Final RP-Initiated Logout 1.0; post-logout URI binding |
| OIDC Back-channel | https://openid.net/specs/openid-connect-backchannel-1_0-final.html | Final Back-Channel Logout 1.0; signed logout messages |
| OAuth Security BCP | https://www.rfc-editor.org/rfc/rfc9700.html | Best Current Practice (Jan 2025); code injection, PKCE, refresh replay, mix-up |
| PKCE | https://www.rfc-editor.org/rfc/rfc7636.html | Standards Track; verifier grammar, `S256` and challenge matching |
| Issuer response | https://www.rfc-editor.org/rfc/rfc9207.html | Standards Track; callback `iss` mix-up protection |
| Token revocation | https://www.rfc-editor.org/rfc/rfc7009.html | Standards Track; token revocation endpoint and limits |
| Token introspection | https://www.rfc-editor.org/rfc/rfc7662.html | Standards Track; resource-server token active status |
| OAuth metadata | https://www.rfc-editor.org/rfc/rfc8414.html | Standards Track; metadata and issuer validation |
| JWT BCP | https://www.rfc-editor.org/rfc/rfc8725.html | Best Current Practice; algorithms, key selection, type confusion |
| JWT | https://www.rfc-editor.org/rfc/rfc7519.html | Standards Track; claims and timestamp conventions |
| JWK | https://www.rfc-editor.org/rfc/rfc7517.html | Standards Track; key formats, uses and public-key selection |
| DPoP | https://www.rfc-editor.org/rfc/rfc9449.html | Standards Track; sender-constrained tokens where selected |
| NIST federation | https://csrc.nist.gov/pubs/sp/800/63/c/4/final | NIST SP 800-63C-4 (Jul 2025); federation assurance guidance |
| NIST authentication | https://csrc.nist.gov/pubs/sp/800/63/b/4/final | NIST SP 800-63B-4 (Jul 2025); authenticator/session guidance |
| OWASP sessions | https://cheatsheetseries.owasp.org/cheatsheets/Session_Management_Cheat_Sheet.html | Practice guidance: session ID, cookies, timeout, regeneration |
| OWASP authentication | https://cheatsheetseries.owasp.org/cheatsheets/Authentication_Cheat_Sheet.html | Practice guidance: auth errors, reauth and identity assertions |
| OAuth 2.1 draft | https://datatracker.ietf.org/doc/html/draft-ietf-oauth-v2-1-17 | **Active Internet-Draft**, Oct 2026: NOT a published RFC or final standard |

**Authority hierarchy:** published protocol RFCs and OpenID Final Specifications for wire semantics; NIST for assurance-policy guidance when the target scope adopts it; OWASP for defensive implementation advice; papers for bounded scientific evidence and discovered attacks; approved product norms and SDD for actual system requirements. Vendor-specific variations are documented as constraints and tested, never presumed.

**Technical design decisions, not universal protocol mandates:** use a verified OIDC library; pin trusted issuer and registered redirect; prefer code+PKCE; model session as server-side for sensitive web applications where appropriate; require backend session revocation and explicit refresh replay response; maintain account linking by issuer+subject+tenant unless an approved explicit linking flow exists. For a public OAuth client, RFC 9700 requires PKCE; for a confidential OAuth client it recommends PKCE or specifies relevant alternatives. Do not misquote a design preference as a universal protocol `MUST`.

**Scope exclusions:** SAML federation is not implemented in the included fixtures; require separate vendor/standards analysis if selected. Claims-based authorization policy is not specified here. No actual identity provider, assurance level, production issuer, or crypto-library certification is asserted.
