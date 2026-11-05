# Skill routing and boundary evidence

> Use the destination's existing requirements and evidence format; named workflow documents are examples, not prerequisites. Optional SDD mapping is configured outside this skill. Ec-specific rules below apply only to an Ec consumer whose current contract establishes them.

**Positive examples:** “add OIDC login with PKCE,” “investigate nonce validation failure,” “pin issuer and keys for enterprise SSO,” “design safe refresh token rotation,” “fix RP logout after IdP offboarding,” “keep separate identities with same email across two IdPs.”

**Negative examples:** “grant project administrator permission” (Ec effective permissions), “create project invite” (user administration), “write generic REST pagination schema” (`http-api-contracts`), “style sign-in form” (`frontend-form-engineering`), “audit OWASP across the whole platform” (`application-security`), “update a YAML schema field” (`ec-declarative-resource-schemas`).

**Boundary example:** “Display logged-in user's effective permissions after OIDC login.” Activate `identity-federation` only for verified authentication/session principal; add `ec-effective-permissions` for RBAC/effective authorization, and `frontend-state-and-cache` only if independent stale-session UI behavior warrants it. Record primary/secondary skill selection in project workflow `plan.md`, without recursive activation.

**Selection test method:** compare prompt to positive/negative/boundary table, map outputs to method-specific artifacts and reject accidental assumption that the user already has a deployed IdP. A written routing matrix is not a tested model-trigger accuracy claim until tested with an actual agent.
