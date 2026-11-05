# Provider discovery, issuer trust and JWKS keys

**Inputs:** approved exact issuer URL, expected client ID, explicit discovery mode, metadata endpoint policy, key rotation lifecycle and legitimate network egress. OIDC Discovery Core requires metadata issuer equality with the already selected issuer and HTTPS endpoints. JSON metadata is **not** an authority to change the trusted issuer, redirect URI or RP identity.

**Algorithm:** choose the registered issuer; retrieve `/.well-known/openid-configuration` via HTTPS (when the spec and issuer profile permit); check `issuer` exact string equality; validate `authorization_endpoint`, `token_endpoint`, `jwks_uri` and any optional logout path under trust/SSRF policy; verify response content type/size; pin the JWKS URI; load only public issuer keys; select exactly one compatible `kid`/`alg`/key-use entry. Do not dynamically follow `jku` and `x5u` from untrusted ID Tokens.

**Rotation:** cache trusted public keys according to bounded policy, refresh once when a known issuer uses a new `kid`, and fail closed if signature or issuer validation still fails. Denial of service from unknown `kid` floods needs a rate limit; key rotation should not be handled by accepting unsigned tokens. For multi-tenant IdPs, do not treat distinct issuer paths as identical origins: exact issuer IDs matter. A demonstration fixture that checks permitted hostnames is **not** a production-grade SSRF egress control.

**Tests:** trusted metadata; issuer mismatch; non-HTTPS endpoint; loopback/private IP endpoint; `jwks_uri` changed unexpectedly; duplicate `kid`; bad key type and signature; key rollover; unavailable issuer; injected redirect; overly large metadata; stale key policy. Vendor-specific discovery variants require traceable ADRs and tests.
