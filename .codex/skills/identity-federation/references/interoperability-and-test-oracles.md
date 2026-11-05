# Test matrix: model, wire, integrated provider and production

| Layer | What to exercise | Acceptance evidence | What it cannot prove |
|---|---|---|---|
| Model | PKCE, state lifecycle, JWT signature/claims, sessions, refresh rotation | deterministic tests, negative fixtures, mutation kills | real IdP/HTTP/browser semantics |
| Loopback HTTP | authorization redirect and token exchange using a toy provider | observed status, headers, redirects, key verification | HTTPS, real provider, durable storage, certification |
| Real client + IdP staging | registered redirect, metadata/key rotation, login, logout, refresh, error callbacks | redacted traces, protocol/client versions, expected account principal | production assurance alone |
| Browser | redirects, cookies, CSRF, history, tabs, expired and interrupted sessions | browser/os versions, logs, keyboard/a11y tests | all browser configurations |
| Persistence/cluster | refresh replay race, session invalidation across nodes, failover and backup | transaction and recovery evidence | upstream-provider behavior |
| Security & conformance | official OpenID Foundation conformance tests, provider-specific test cases, independent abuse review | test report and failed-case closure | universal security proof |

**Minimum negative oracles:** trusted vs rogue issuer; `state` mismatch/replay and wrong browser; code substitution; wrong PKCE verifier; mismatched nonce; signature and key confusion; `iss`/`aud`/`azp` mismatch; expiry/clock skew; token confusion; cross-tenant account linking; CSRF; session fixation and expiry; refresh replay, two simultaneous refreshes, logout race; discovery/SSRF; revocation delay. Define each test's precondition, action, expected outcome, evidence and security relevance.

Synthetic fixtures implement a deliberately limited subset of OIDC. Network test transport is HTTP loopback, not a TLS-hardened provider. The package checks are **not** formal OIDC certification or a deployed-system security audit.
