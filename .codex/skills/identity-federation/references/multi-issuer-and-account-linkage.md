# Multi-issuer, tenant mapping and identity linking

An external identity is stable only within its issuing authority. Store an authoritative key such as `(tenant ID, verified issuer, verified subject)`; adding a tenant key is necessary when the product's tenant model demands isolation. `email`, `preferred_username`, display name or unverified group claims are not interchangeable with `sub` and should not automatically link accounts or grant roles.

**Selection:** ensure the RP chooses a trusted issuer from approved registration before login and keeps the issuer bound to the request. Validate RFC 9207 authorization response `iss` where selected; protect against authorization-server mix-up and malicious metadata. A provider appearing in a catalog is not necessarily trusted to authenticate every tenant.

**Linking:** require explicitly authorized, auditable account-linking or migration workflow with proof of control of both identities where applicable. Enforce tenant scope at login, provisioning, resource access and session lookup. When IdP group membership changes, policy evaluation belongs to the authorization owner; do not silently increase permissions from a JWT claim.

**Oracles:** same email from two providers does not merge users; same `sub` across issuers does not merge; same issuer/sub across tenants does not cross tenant; unauthorized tenant provider rejected; removed provider mapping revokes or blocks sessions as specified.
