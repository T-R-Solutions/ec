# Detailed decisions and failure cases

Read only for a decision needing more detail than the entry point. Apply the relevant steps to the
actual task; the sections are not a mandatory artifact or test checklist. Use existing project
contracts in their actual format. File names such as spec.md, plan.md or tasks.md are examples, not
required dependencies. Other skills are optional expertise, never enforcement mechanisms.

Product rules, stack choices and authority locations must be verified in the Ec consumer. Examples
do not establish deployed capabilities or override its current contract.

## Implementation decision procedure

1. **Partition the data by authority.** Model stable Ec principal IDs, verified external identity
   keys, people, teams, groups, roles, scope bindings and account state as distinct concepts. The
   desired administrative **role/policy/team binding** is represented by canonical YAML as required
   by the Ec norms; session tokens, pending invitations, authentication events, provider lifecycle
   observations and audit records are **operational**, never secret-bearing YAML. A SCIM JSON HTTP
   payload is an **interoperability message**, not an Ec declarative configuration file. Determine
   the provisioning authority and who may edit each relationship; never silently let both the IdP
   and local UI own a field.
2. **Resolve identity without guessing from names or email.** For an OIDC-backed identity, verify
   the assertion through the trusted identity layer and map `(issuer, subject)` to one stable Ec
   principal. Treat email, username and display name as mutable attributes, not stable identity
   keys. For SCIM, distinguish service-provider `id` from the provisioning client's `externalId`,
   scoped by provisioning domain. Define a reviewed account-linking, rename, merge and deprovision
   procedure; reject unverified, ambiguous, reassigned or cross-provider merges. Do not implement
   token validation here—delegate to `identity-federation`.
3. **Keep independently testable resource relations.** Track user/principal, team membership, group
   membership, role definition, role binding, resource scope, invite and account lifecycle with
   distinct IDs and provenance. Do not assume project, team, group and role share a hierarchy; do
   not infer a role assignment from being displayed in a team table. Preserve unknown supported YAML
   properties on a form edit through `ec-semantic-configuration-projections`; use
   `ec-declarative-resource-schemas` for schema constraints. Specify delete/archive consequences,
   retained audit attribution, pending invite behavior, and external provisioning precedence before
   writing migrations or bulk operations.
4. **Design an explicit invitation and account lifecycle.** Separate invitation `pending / accepted
   / expired / cancelled` from verified account identity and effective membership. Issue
   high-entropy single-use invitation secrets through the approved service; store only protected
   digests, enforce expiration and replay limits, require identity proof/claim matching on
   redemption, and never log URLs/tokens. Accepting an invitation must not bypass the configured
   YAML publication and authorization workflow. For disabling/deprovisioning, define a trusted
   immediate-access mitigation while desired YAML cleanup reconciles; decide explicitly whether
   later reactivation can restore previous bindings. Session termination/revocation mechanics belong
   to `identity-federation`.
5. **Preflight every admin change against current authority.** Resolve actor identity, target
   identity, action, source/destination scope, target role, affected team membership and relevant
   policy/identity revisions. Call the backend authorization decision contract rather than trusting
   frontend guards or stale UI role names. Distinguish permission to *manage a user* from permission
   to *assign a particular role*. Detect attempted self-grant, delegation beyond actor authority,
   change to another administrator, protected ownership changes, cross-scope access and loss of last
   authorized recovery administrator. If an independent approval is required by policy, bind it to
   actor, exact candidate digest, scope and revision; do not assume approving a stale or changed
   request is valid. The policy semantics and effective-access computation belong to
   `ec-effective-permissions`.
6. **Use a revision-bound change workflow.** Produce a candidate with explicit additions/removals
   and a prospective impact summary; show direct assignments separately from resolved permissions.
   Re-check current subject/status, administrative capability and revision at commit. Route
   persistent desired YAML changes through `ec-configuration-publication` for validation, immutable
   revision, conflict detection and reconciliation; invitation/session operations take their
   operational service path. SQL transactional storage can enforce unique external identity keys and
   idempotency where applicable, but **cannot atomically commit an external Git revision**. Fail
   closed on unknown state and retry only safely replayable operations.
7. **Keep the administrative experience operationally truthful and accessible.** Provide
   user/team/group lists, scopes, source of access, invites with expiration,
   active/disabled/synced/pending states, role grant/revoke previews and safe bulk-selection
   summaries. Expose *assigned roles* and *effective permissions* as different views; the latter
   consumes the delegated engine's versioned result, including provenance and stale/unknown states.
   Make changes, cancellation, and confirmations keyboard-operable; support screen readers and
   explicit text feedback. Never show a saved, published, effective or revoked success state until
   the corresponding trusted service confirms it. Do not expose tokens, full identity assertions or
   protected credentials in forms, YAML previews or audit logs.
8. **Verify the invariants and failure paths.** Test `(iss,sub)` uniqueness across rename/providers;
   SCIM `id`/`externalId` mapping; role/scope referential integrity; default deny; self-escalation
   and reviewer separation; invite expiry/replay/cancel; race between role edit and actor
   revocation; deprovision/reactivation; stale policy and hierarchy revisions; Git/DB partial
   failures; offline/unknown state; redacted audit; project scope leakage; semantic round-trip via
   all three editing modes; keyboard and assistive technology. Use property/state-machine tests and
   actual provider/PG/Git/browser integration fixtures once available. **Report synthetic tests
   separately from Ec runtime tests, and do not invent results.**


## Further references by decision

Read only the reference relevant to the unresolved decision. Research registers describe imported authorship, not current execution.

- [Identity, Administrative Lifecycle and Binding Contracts — Engineering Reference](identity-lifecycle-and-bindings.md): identity lifecycle and bindings.
- [Routing, Synthetic Test IDs and Real Ec Acceptance Oracles](routing-and-test-oracles.md): routing and test oracles.
- [Evidence Ledger and Design Decisions — Ec People and Access Administration](sources-and-decisions.md): source provenance or a disputed recommendation.
