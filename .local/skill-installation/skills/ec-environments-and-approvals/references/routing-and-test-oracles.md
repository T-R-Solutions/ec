# Activation, Handoffs and Acceptance Oracles

> Use the destination's existing requirements and evidence format; named workflow documents are examples, not prerequisites. Optional SDD mapping is configured outside this skill.

## Selection tests: choose this skill for

1. Build an environment inventory view with clear protected/unprotected/unknown state.
2. Create a release review panel displaying an immutable artifact and target.
3. Add request/approve/reject operations to the Ec environment console.
4. Explain why a deployment request cannot proceed after approval.
5. Check reviewer quorum against the project-approved policy.
6. Build an operator view for maintenance/freeze windows and check status.
7. Show provenance and testing evidence before deployment authorization.
8. Add an audit timeline for environment approvals and promotions.
9. Implement a UI handoff from approved gate to a deployment executor.
10. Protect against repeated votes and cross-environment approval replay.
11. Investigate revocation or policy changes between approval and start.
12. Build a safe approval/rollback request UX where target support is confirmed.
13. Display target-verified deployment outcome and unknown status.
14. Preview edits to environment gate protection and project visibility.
15. Expose keyboard-operable review and release details on mobile.
16. Investigate conflicting promotions or older delayed completion events.

## Negative routing: select adjacent owner instead

1. Algorithm for canary, blue-green, shadow traffic or automated rollback: `cicd-release-and-deployment`.
2. Git YAML commit, three-way merge, drift reconciliation: `ec-configuration-publication`.
3. Effective RBAC evaluator or group inheritance policy: `ec-effective-permissions`.
4. User/team invitation and deprovisioning: `ec-people-and-access-administration`.
5. YAML syntax parsing/versioned schema: `ec-declarative-resource-schemas`.
6. Lossless Visual/Form/YAML AST edit: `ec-semantic-configuration-projections`.
7. Pipeline source DAG construction and canvas layout: `ec-pipeline-authoring-workspace`.
8. Worker run retries, cancellation and log streaming: `ec-run-execution-operations`.
9. Generic Vue component typing or CSS: `vue3-engineering` / `css-layout-engineering`.
10. Actual project specification policy drafting: the project workflow, not a skill.

## Acceptance-hypothesis families for real Ec tests (NOT executed)

| ID | Scenario | Accept only with real evidence |
|---|---|---|
| A01 | Approve an exact artifact digest and target | API records immutable subject ID and verified authenticated reviewer |
| A02 | Change artifact digest after approval | Old approvals cannot authorize new content |
| A03 | Change environment or project | Old vote cannot release a different target/scope |
| A04 | Change YAML effective gate policy revision | Re-evaluation or invalidation follows approved project contract semantics |
| A05 | Disallow self-review | Initiator cannot approve when policy prohibits it |
| A06 | Multiple group memberships | One verified principal does not count twice unless expressly specified |
| A07 | Approver role revoked | No stale approval can bypass required current authorization checks |
| A08 | Quorum not met | No dispatch despite approved votes below threshold |
| A09 | Explicit rejection | Final rejection blocks dispatch, with audit evidence |
| A10 | Expired request | Server clock, not browser clock, decides expiration |
| A11 | Manual approval complete | UI says approved, not deployed/healthy |
| A12 | Freeze active | Request remains blocked unless approved override contract exists |
| A13 | Allowed window expires after approval | Handoff checks current window before side effects |
| A14 | Unverified provenance | No release solely from a digest string or untrusted attestation |
| A15 | Changed source/config revision | Exact approved candidate must match at dispatch |
| A16 | Simultaneous promotions | Server prevents conflicting target writes under selected policy |
| A17 | Delayed older completion | Old event does not overwrite newer attempt status |
| A18 | Network acknowledgement lost | Retry is safely deduplicated and outcome reconciled |
| A19 | Successful approval, failed target | Approval and target failure remain distinct |
| A20 | Target health never observed | Display `unknown`, never success |
| A21 | Rollback request to older artifact | New controlled deployment with proper approval when applicable |
| A22 | Unauthorized reviewer views secret metadata | No plaintext or sensitive policy leakage |
| A23 | Keyboard-only approve/reject | Focus, labels, status announcements and target confirmation verified |
| A24 | Screen reader status review | Announcements match real backend state, no false success |
| A25 | Narrow viewport / light and dark themes | Risks, revisions and decisions remain readable and operable |
| A26 | YAML Visual/Form round-trip | Environment policy semantics and advanced fields preserved |
| A27 | Git publication partial failure | Environment draft/effective and release request are not conflated |
| A28 | Emergency override | Only authorized, scoped, time-limited, audited override succeeds |
| A29 | Distinct approver/deployer permissions | Ability to approve does not grant permission to deploy |
| A30 | Target unhealthy after previously healthy | Fresh observation supersedes old health with provenance |
| A31 | Auditor exports timeline | Redacted immutable events with observed revision and actor attribution |
| A32 | Concurrent policy change and dispatch | Atomic check/side-effect boundary prevents TOCTOU bypass |
| A33 | Environment removed/archived while pending | Request transition or invalidation uses explicit project workflow policy |
| A34 | Source branch restriction changed | Gate recheck uses effective trusted version |
| A35 | Reviewer absent/unavailable | Pending state and notification reflect actual workflow rule |
| A36 | Unknown authorization service | Fail closed without falsely labeling actor definitively denied |

## Agent evaluation — separate from structure tests

Use paired tasks with and without the skill on the actual Ec repository. Score correct skill activation, no invented fields/endpoints, correct authority split, request fingerprint verification, error/unknown semantics, permission freshness, test reproducibility, raw token count and implementation change quality. Manually inspect errors; synthetic model tests do not establish AI correctness. Pre-register tasks and sample sizes before making comparative performance claims.
