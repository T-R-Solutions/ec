# ID Token verification and JWT/JWK pitfalls

A JOSE library must perform signature verification, not merely `base64url` parsing. Configure the acceptable `alg` set and **per-issuer** verification keys before reading a token. RFC 8725 rules about algorithm verification, wrong key type, cross-JWT confusion and untrusted key URLs apply. An ID Token is a claim about **end-user authentication**, not a generic authorization bearer token for arbitrary APIs.

**Validation gates:** compact JWS structure; size/JSON/critical-parameter policy; allowlisted algorithm; exact trusted issuer; one unambiguous trusted public key; valid signature; `aud` includes intended client with approved other audiences; `azp` under profile policy; nonempty `sub`; `exp`, optional `nbf`, `iat` under defined clock-skew tolerance; `nonce` matching the initiated request where provided; `auth_time`/ACR/AMR as required by approved step-up policy. Reject tokens before issuing local sessions.

**Negative matrix:** tampered claims, missing/bad signature, wrong `kid`, duplicate key IDs, `alg=none`, HS/RSA confusion, attacker-controlled `jku`, wrong `aud`, wrong `iss`, missing/mismatched nonce, expired/not-yet-valid timestamps, token type confusion, stale key. Keep all failures observable without returning raw tokens or sensitive claims.

**Fixture limit:** the bundled Node model signs and verifies synthetic RS256 JWS messages but is **not an OIDC/JOSE library**; it omits several production JSON validation, ciphertext, profile, key cache, algorithm and hardening details. Use well-maintained libraries and official conformance suites in the real application.
