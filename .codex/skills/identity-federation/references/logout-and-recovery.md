# Logout, reauthentication and partial failure semantics

Define who requested logout (user, RP, IdP, admin or expiry), which local sessions/tokens are revoked, whether global provider logout is requested, and the true completion signal. Use OpenID Connect RP-Initiated Logout 1.0 for provider redirection, registered `post_logout_redirect_uri` and applicable `id_token_hint` rules. Back-channel/logout-event support is a separate capability that requires signature and replay validation under that spec.

**Never conflate**: UI hides a profile; RP session invalidated; refresh family revoked; access-token no longer usable; IdP session ended; all downstream applications logged out. An unreachable IdP should not prevent the RP from invalidating its own session, but a local logout is not evidence of global SSO termination.

**Verify:** local logout, already-logged-out idempotency, provider logout redirect allowlist, back-channel replay/forged event denial, session expiry, simultaneous logout and pending callback, cross-device and cross-tab consequences, token revocation delay, user-facing error semantics. If the provider does not support a selected logout profile, disclose the limitation rather than inventing support.
