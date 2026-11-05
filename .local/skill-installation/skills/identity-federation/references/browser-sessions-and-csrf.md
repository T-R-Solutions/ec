# Browser sessions, fixation, CSRF and timeouts

After verifying an identity assertion, create a fresh opaque browser-session identifier; store principal and permissions *server side*, under separate controls. Discard or rotate any pre-authentication session ID. Do not encode roles, tokens or secrets in public session IDs. Cookie scope should be explicit: `Secure`, `HttpOnly`, a justified `SameSite` setting, `Path=/` and no `Domain` for a `__Host-` cookie. Cookie settings reduce exposure but do **not** replace CSRF defense or prevent all XSS consequences.

Define **idle** and **absolute** timeouts and enforce them on the backend, not only in JavaScript. Require CSRF protection for state-changing cookie-authenticated operations; review Origin/Referer, anti-CSRF tokens and cookie behavior in redirects or embedded SSO workflows. Reauthentication/step-up requirements must come from the accepted assurance and risk policy, with NIST SP 800-63B-4 as a reference where relevant.

Session invariants: login from a new verified identity rotates the session; tenant/identity changes revoke or replace sessions; an expired, revoked or logged-out session cannot authorize a request; session refresh is not automatic token acceptance; errors may not leak whether a hidden account exists. Test cross-tab login/logout, browser back-navigation and auth UI stale cache, and network failure scenarios.

The demo uses an in-memory map and illustrative timeouts; it is **not durable**, cluster-safe or suitable for production authentication.
