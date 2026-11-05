# Authentication, tokens and secret exposure boundaries

Avoid passwords, bearer tokens and API secrets on `argv`: they can be present in shell history and visible to process listings or diagnostics. Prefer a platform-supported keychain, identity federation flow or short-lived session credential manager when the application's architecture supports it. Environment variables are not magically secure and must be evaluated against CI logs, crash dumps, child processes and shared hosts. An SSH agent or OS credential store is not itself a substitute for server authorization.

The CLI may identify the selected profile/issuer/scope, but must not treat it as proof of backend permission. Explicitly enforce TLS, token audience/scope, expiry and permissions at the service boundary under the actual authentication and authorization contracts. Log stable, redacted request IDs and statuses, not tokens, auth headers, password fields or private keys. Review error strings and shell completion for accidental secret disclosure.

Relevant methods: OWASP Secrets Management, Identity Federation and Application Security skills. This skill designs the user-visible interaction with that security subsystem, not the subsystem itself.
