# Threat boundaries for identity federation

**Actors:** browser, relying party (RP/client), authorization server (AS), identity provider (IdP), resource server, protected session store, secret manager, tenant authority and user. A self-asserted email is not a principal identity; OAuth delegated authorization and OIDC authentication are not the same event.

**Trust map:** authentication begins outside the RP; HTTPS metadata is still untrusted until matched against a registered issuer; tokens are untrusted bytes until validated; a signed ID Token does not directly grant the application's permissions. Confusing these boundaries permits login CSRF, authorization-code substitution, issuer mix-up, account swapping and invalid audience acceptance.

**Explicit abuse cases to review:** malicious IdP returning private endpoint in discovery (SSRF); RP accepting login issued for a different relying party (`aud`); token accepted with `alg=none` or attacker-provided `jku`; user A initiating transaction and user B completing it; stale browser transaction; RP elevation because an IdP provided a `groups` claim; forgotten local sessions after provider logout; session fixation and replay on refresh-family rotation. Consider DNS rebound, DNS/IP allowlisting, redirect chains and IPv4/IPv6 resolution where dynamic discovery is genuinely required; avoid naive hostname-only trust in production.

**Test oracle:** no authenticated local identity or privileged session can result from an untrusted issuer, unsigned ID Token, unaudited linker or missing browser-bound state. Security review is delegated to `application-security` when whole-system threat modeling is requested. Maintain minimal audit data with no plaintext tokens or credentials.
