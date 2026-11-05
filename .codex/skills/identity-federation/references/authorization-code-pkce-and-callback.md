# Authorization code, PKCE and callback contract

Use authorization code with a cryptographically random verifier. RFC 7636 defines the verifier grammar (43–128 unreserved characters), `S256=BASE64URL(SHA256(ASCII(code_verifier)))` and prohibits silently downgrading to `plain`. Public clients **MUST** use PKCE per RFC 9700; confidential clients should follow RFC 9700, their OIDC profile and approved protection policy.

**Before redirect:** create fresh `state`, per-request `nonce` if used, verifier and challenge; bind them to browser context, selected issuer, client ID and registered redirect in expiring server-side or appropriately protected state. Use exactly registered redirect URI without untrusted modification. The authorization code must never appear in application telemetry or unredacted logs.

**On callback:** require matched, one-time `state`, browser binding and intended authorization-server issuer (e.g., RFC 9207 `iss` where supported or equivalent safe mix-up control), code present, token endpoint selected from **prior trusted provider metadata**. Reject missing state, wrong browser, wrong issuer, mismatched redirect, replay and expired login. Exchange with the original PKCE verifier. A callback arriving after logout must not recreate a session.

**Tests:** valid flow; code injection; stolen/missing state; browser substitution; nonce mismatch; PKCE verifier mismatch; redirect mismatch; code replay; IdP down; login cancelled; concurrent tabs; error callback from another issuer; successful exchange followed by failed token signature. Do not interpret "HTTP 200 token response" as authenticated until ID Token is verified.
