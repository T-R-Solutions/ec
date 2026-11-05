# Identity, Administrative Lifecycle and Binding Contracts — Engineering Reference

> Use the destination's existing requirements and evidence format; named workflow documents are examples, not prerequisites. Optional SDD mapping is configured outside this skill. Commands, fixture paths and historical execution claims from the source package are illustrative or imported evidence unless the corresponding files are present and independently checked; they are not current test results or bundled executable tools.

**Illustrative contract analysis, NOT an approved Ec schema, operating policy, implemented feature, identity-provider integration or source-code design.** The project workflow and actual verified Ec repository own all concrete names, types, defaults and authorization rules. The synthetic model under `validation/` is intentionally simpler than production.

## 1. Separate identity from entitlement

| Concept | Stable identifier / data | Authority boundary | Failure to prevent |
|---|---|---|---|
| Ec principal | Internal opaque, stable ID | Trusted identity mapping, operational identity record | Rename/reused email altering grants or historical attribution |
| OIDC identity | Verified `(issuer, subject)` pair | External IdP / `identity-federation` | Auto-merging two identities because email/display name matches |
| SCIM identity | Service-provider `id`; provisioning-domain-scoped `externalId` | SCIM provider/provisioner, only if integration accepted | Treating SCIM account identifiers as trusted local YAML field values without mapping |
| User profile | Name, email labels, avatar and preferences | Provider or local profile per project workflow ownership | Assuming label is permanent or verified |
| Team/group | Stable group/team ID with explicit roster relation | Desired configuration when declarative; provider observation when external | Treating role as a team or treating team structure as project ancestry |
| Role | Stable role ID with defined capability contract | Desired YAML role/policy definitions | Inventing `Owner`, `Admin`, `Viewer` semantics from other CI systems |
| Role binding | Subject/team, role, resource scope, source and provenance | Canonical desired YAML when published | Role displayed as active before actual reconcile/authorization confirmation |
| Effective permission | Subject, action, object, context, policy/topology revision and decision provenance | Delegated `ec-effective-permissions` engine | UI computing access from direct roles alone |
| Invitation | Opaque one-use token proof, expiration, pending/accepted/cancelled/expired | Operational service, not desired YAML | Pending or accepted invite bypassing verification and declaration workflow |
| Authenticated session | Token/session identifier, authentication assurance, expiry and revocation | `identity-federation` / IdP subsystem | Password/session/token written into YAML or logs |
| Audit event | Actor, target, action, scope, before/after reference, result, timestamps | Durable operational audit subsystem | Missing security evidence or leaking tokens/PII |

## 2. Identity/keying and provisioning

- OIDC `iss + sub` is a guaranteed issuer-scoped stable pair, unlike mutable/reassignable `email`, `preferred_username` or `name`. The identity subsystem must authenticate the assertion, issuer, audience, nonce/session context and trust configuration before this administration method consumes it.
- SCIM `id` is server-issued and stable; `externalId` originates from the provisioning client and must be interpreted in that provisioning domain. An event from a new/unknown domain does not acquire authority merely by containing a matching email.
- Model authoritative versus observed fields *per source* (IdP, configured SCIM client, Ec local UI, Git-backed YAML). For each attribute define ownership, conflict, freshness, fallback and deprovision behavior. **Do not** assume SCIM auto-provisioning exists in Ec.
- Resolve account linking/merge through approved trust proof; mark collisions and provider migration as explicit administrative events. Preserve historical principal references in audits; redaction should not destroy attribution.
- Treat machine identities and human accounts differently where the project workflow authorizes them. Workload tokens are security credentials, not a YAML-resident `User` property.

## 3. Candidate administrative command contract (method-only)

A change proposal should carry the following *logical metadata*, not a mandatory Ec API field list:

`actor principal`, `action`, `target stable principal/team`, `resource scope`, `role/team ID`, `previous relation`, `proposed relation`, `canonical revision`, `identity status/version`, `authorization policy version`, `project topology version`, `candidate digest`, `approval proof reference (when required)`, `idempotency key`, `result correlation ID`.

A trusted backend applies this sequence:

1. Resolve verified actor/target and actual resource, including project-level and instance-level scopes. Deny unknown or disabled identities; don't accept names as security keys.
2. Validate explicit administrative capability for `action + target + scope + requested role/relationship`, then ask effective-access engine for the counterfactual before/after risk and delegated grant restrictions.
3. Validate referential integrity, provider ownership, protection against self-grant, another administrator's access, accidental lockout and cross-scope movement. A valid role name alone is insufficient.
4. Build an immutable candidate and diff (direct bindings, not an invented permission engine), bind approvals to candidate content + versions and require independent approval **only when policy says so**. UI must show destination/subject and consequences.
5. Recheck everything at the authoritative revision. On conflict, recompute rather than applying stale diff. Desired configuration changes go through `ec-configuration-publication` (Git/validation/reconcile); operational invite/session commands go to their dedicated service.
6. Record an audit event with outcome `requested`, `blocked`, `approved`, `published`, `reconciling`, `confirmed effective`, `failed` or `unknown` as applicable. No fake success on stale data; no tokens or raw assertions in event payloads.

A database serializable transaction cannot wrap an external Git update; use revision-aware outbox/reconcile design where required. This skill does not select a specific outbox schema or commit protocol.

## 4. State-machine obligations for invitations and accounts

### Invitation

| From | Trigger | To | Important check |
|---|---|---|---|
| absent | authorized invitation request | pending | Scope, abuse/rate policy, idempotency and TTL from project workflow; single-use secret |
| pending | trusted proof plus acceptance | accepted | Current, unexpired, not cancelled, verified recipient identity |
| pending | authorized cancellation | cancelled | No acceptance after cancel; safe concurrent compare-and-set |
| pending | authoritative expiry event/check | expired | No acceptance at or beyond expiry |
| accepted/cancelled/expired | replayed acceptance | unchanged, error | Never reopen automatically |

**Acceptance is not evidence that a desired membership is published or effective.** Some products combine acceptance with access provisioning; if Ec ever chooses that, it still must pass the authoritative review/YAML pipeline and prove identity. Do not infer a policy or default from GitHub's chosen UX.

### Account disable and reactivation

- Deprovision/disable is an identity operational event. It must have a trusted immediate-denial path for actions and an explicit reconciliation plan for desired bindings; an unchanged Git projection is not proof that access is safe.
- Stateful/cached tokens may outlive a user's disable event. Define an actual token/authorization revocation mitigation and test its race with pending admin proposals. The session service, not this skill, implements token mechanics.
- **Reactivation restore is an unresolved Ec policy.** Two defensible alternatives: (A) require a fresh approval/rebinding before former privileges return; (B) restore only a reviewed subset under a time-limited reactivation policy. Both need an explicit approved project contract contract and reconciliation proof. The synthetic model illustrates (A) using a principal epoch: it is **not** mandatory Ec behavior.
- Archive/delete identities must preserve regulated audit references and define retention/anonymization without silent orphaning; legal requirements require separate review.

## 5. UI composition and verification gates

A people/access admin interface should show: primary principal identity and verified provider (without full assertions), active/disabled/pending state, direct team membership, direct scoped role assignment, inherited/effective permissions with **verified provenance from the delegated evaluator**, last confirmed desired/effective revisions and actionable differences. Differing scopes must be labeled `instance`, `team`, `project` or `resource` only when backed by actual features.

Operations: accessible user list and filters; team/group membership editor; role assignment with source and scope; invite issue/resend/cancel and expiration; disable/reactivate (where supported); dry-run impact and diff; review/approval; bulk actions with explicit number of targets and confirmation; rollback/retry with honest state.

The UI may use tables, matrices, diagrams or forms according to data density, but must expose an equivalent keyboard/assistive route. Avoid interactive `role="grid"` unless implementing its full focus model. Test project membership in at least both clear and dark themes where supported; do not claim WCAG compliance without evidence.

## 6. Real integration requirements and model limitations

The attached offline model is limited to a **flat team roster**, illustrative `viewer/admin` role names, synthetic trusted administrative decision fixtures, mock verified-assertion flags, a fake publication receipt and in-memory invitation digests. It does **not** parse YAML, cryptographically verify OIDC/SCIM, send email, connect to Git/PostgreSQL, calculate effective permissions, revoke real sessions, or evaluate human usability. The candidate hash is **not a signature or real approval authority**; an approval in the toy model is **not proof** of authorized human review.

For actual Ec verification, test a live IdP contract with deliberate duplicate-email and multi-issuer cases; membership changes via real YAML publication and concurrent edits; provider group sync delete/update failures; database uniqueness and retry handling; session revocation propagation; actual role decision point; idempotency and replay; audit redaction; UI keyboard operation and screen reader; and administrator self-grant or lockout attacks. Attach observed commands, outputs, revisions and coverage to the approved project acceptance evidence.
