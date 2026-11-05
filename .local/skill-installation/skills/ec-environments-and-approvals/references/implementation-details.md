# Detailed decisions and failure cases

Read only for a decision needing more detail than the entry point. Apply the relevant steps to the
actual task; the sections are not a mandatory artifact or test checklist. Use existing project
contracts in their actual format. File names such as spec.md, plan.md or tasks.md are examples, not
required dependencies. Other skills are optional expertise, never enforcement mechanisms.

Product rules, stack choices and authority locations must be verified in the Ec consumer. Examples
do not establish deployed capabilities or override its current contract.

## Engineering decision procedure

1. **Discover the actual contracts and trust boundaries.** Inspect approved project contract,
   versioned YAML schemas, authenticated principals, artifact provenance, release/run records,
   service integrations and current Ec APIs. Identify environment scope, resource owners, target
   identity and the deployment executor. Record missing identifiers, gate semantics or state
   transitions as project workflow decisions; do not import GitHub/GitLab/Azure behavior by analogy.
2. **Separate desired configuration from operational evidence.** Keep desired environments,
   protection policies, gate definitions, approval requirements and approved secret *references* in
   versioned canonical YAML. Keep requests, votes, check results, execution attempts, secrets
   materialization, approvals audit, environmental observations and deployment history in
   operational stores. Represent draft, published and effective revisions separately; delegate the
   YAML mutation and reconciliation mechanism to `ec-configuration-publication`.
3. **Freeze the promotion subject.** Construct a server-verified, immutable request context
   including project/scope, target environment, artifact digest, source commit or immutable
   revision, desired-config revision, applicable gate/policy revision, requester, request ID, and
   expiry/evidence version when defined. Bind every vote to that exact context. A changed artifact,
   target or approved policy must never inherit an old approval silently. Do not treat an unverified
   UI string or unsigned attestation as artifact verification.
4. **Make gate evaluation explicit and explainable.** Differentiate approver eligibility, reviewed
   evidence, approval quorum, separation-of-duty rules **when approved by policy**, branch/source
   restrictions, change window, freeze, required checks, artifact trust and destination
   availability. Return distinct `satisfied`, `blocked`, `pending`, `rejected`, `expired` and
   `unknown` explanations with trusted provenance, source revision and observation age. Use approved
   gate precedence; if it is unspecified, surface a blocking project workflow decision instead of
   guessing a default. Evaluate against current evidence before release, not only when the review
   page opened.
5. **Design approval as an audited decision, not deployment.** Authenticate the approver through the
   trusted identity subsystem; delegate effective access and delegated authority to
   `ec-effective-permissions`. Apply the project-defined no-self-review, quorum, group, veto, expiry
   and emergency-break-glass policy. Enforce distinct actor identities for multiple approvals where
   required, even when one person belongs to several groups. Prevent repeat votes, recycled approval
   IDs, replayed commands and edits to the approved subject. An approved request is **not yet
   dispatched, applied, healthy, or successful**.
6. **Release through a guarded handoff.** Immediately before dispatch, atomically recheck the
   immutable subject, current authorization, policy/version, approvals, freeze/window, verified
   artifact, destination and any conflicting environment operation. Use a server-side
   compare-and-set/idempotency boundary and environment-level exclusion or supersession strategy
   **chosen by project workflow**, never a browser-only lock. Do not promise a distributed
   transaction across Git, PostgreSQL and external deployment targets. Record a durable dispatch
   request/receipt and distinguish `accepted`, `started`, `applied`, `healthy`, `failed`, `unknown`
   and `stale` only with matching executor evidence. Differentiate promotion to a prior artifact
   from an automatic rollback.
7. **Build an operationally truthful UI.** Show a compact release evidence summary (exact artifact
   and revisions, destination, approvers, risks, observed checks, time window, audit trail),
   separate buttons for approve/reject, request dispatch, monitor, and any supported rollback.
   Expose why an action is denied; never reveal protected secrets or unauthorized policy details.
   Confirm critical destination and consequence. Support keyboard-only operation, visible focus,
   accessible dialogs, status announcements, responsive layouts and consistent light/dark states. Do
   not present optimistic UI as confirmed deployment success.
8. **Verify races, revocation and recovery.** Test changed digest/config/policy after a vote;
   self-approval when disallowed; double-vote across reviewer groups; approver revocation;
   expired/future approvals; conflicting promotions; older deployments finishing late; retries after
   lost acknowledgements; queue and time-window transitions; external partial failures; unverified
   provenance; unknown health; audit redaction; and permissions after project/environment changes.
   Exercise actual Rust/API/PostgreSQL/Git/Vue/target integrations only when the Ec repository is
   provided. Otherwise explicitly label illustrative tests **synthetic**.


## Further references by decision

Read only the reference relevant to the unresolved decision. Research registers describe imported authorship, not current execution.

- [Gate and Promotion Lifecycle — Technical Decision Guide](gates-and-promotion-lifecycle.md): gates and promotion lifecycle.
- [Activation, Handoffs and Acceptance Oracles](routing-and-test-oracles.md): routing and test oracles.
- [Administration, Security and UI — Technical Decision Guide](security-and-ui.md): security and ui.
- [Evidence Register — Ec Environments and Approvals](sources-and-decisions.md): source provenance or a disputed recommendation.
