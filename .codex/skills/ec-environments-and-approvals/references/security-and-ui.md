# Administration, Security and UI — Technical Decision Guide

> Use the destination's existing requirements and evidence format; named workflow documents are examples, not prerequisites. Optional SDD mapping is configured outside this skill.

## Actors and actions must be separated

Explicitly distinguish permission to view an environment, configure protection rules, request promotion, approve a request, launch an approved request, monitor a target, override a gate, and roll back. A person may hold several roles, but multiple group memberships are not multiple independent people. Avoid conflating reviewers with deployment executors or administrators. An explicit no-self-review constraint, a multi-reviewer threshold, emergency override, and approval carryover behavior belong in the project workflow; fail closed when an action's rule is unknown.

For configuration changes affecting the gate itself, check actor authority, scope, baseline YAML revision and changes to who becomes able to deploy. Never let the very change under review unilaterally waive the approval protecting that change. The effective-permissions skill owns the authorization algorithm; this skill identifies when to call it and how to consume its versioned result.

## Provenance and secrets

- Show digest/revision references, verified builder and attestation status only if the trust-verification service actually supplied these. A hash string alone is not a verified provenance statement.
- The SLSA 1.2 Build verification guide separates checking artifact digest, signature, builder identity and expectations; do not equate “signature exists” with “approved to deploy”.
- Store secret references in desired YAML; use privileged on-demand materialization when the trusted execution path requires it, after required gates have passed. Do not include plaintext secrets in a review form, audit event, log, or example screenshots.
- A policy-approved break-glass path, when supported, needs distinct authority, declared override scope, short-lived effect, audit trail, disclosure and post-event review. Do not create a generic `force=true` shortcut in a frontend or assume all admins can override.

## Operator screens and UX evidence

Build only flows called for by the approved spec. Common meaningful areas: environment detail, release candidate review, gate checklist, reviewer inbox, deployment attempt timeline, health diagnostics, history and permitted rollback. Present: destination, artifact digest, immutable source/config revisions, what changes, blast radius, test/provenance results, required approvals, authenticated reviewer, time zone/window, audit evidence and a clear next action. Avoid a green “success” on submit; use server-confirmed state with freshness/source labels.

Confirmation dialogs identify the exact target and artifact before potentially destructive actions. Native buttons, clear focus management, keyboard support and appropriate dialog semantics take priority over drag/drop gimmicks. WAI-ARIA alert dialogs can be appropriate for an interruptive high-risk confirmation; ordinary approvals may use simpler forms/dialogs as appropriate. Status changes that do not move focus should be perceivable via live-region semantics; WCAG 2.2's Focus Visible and Status Messages criteria are separate.

Unknown readiness cannot be visualized as zero failure or successful rollout. Distinguish proposal, approved, accepted, deploying, verifying, healthy, failed, drifted and unknown only where matching actual API states exist. When designing responsive layouts, prioritize a readable review summary and table/list alternatives, not squeezed dashboards. Provide light/dark functional parity and protect policy/actor details under resource authorization.

## Failure analysis and evidence inventory

Check stolen/stale reviews; reviewer revocation between review and dispatch; cross-environment or cross-project approval replay; digest changes; Git/operational projection delay; freeze beginning after review; concurrent promotions; delayed completion from an older attempt; target refusing request; “started” response lost; deployment fails after an approval; false health success; permission and audit leakage; and hidden/retracted environment secrets. For each failure, record *source of observation*, *observed revision*, *what was applied*, *what was only requested*, and *how to reconcile uncertainty*.

### Decisions requiring approved project contract

Actual `Environment`, `ApprovalGate`, `Release` and `Deployment` resource schema and versions; ownership and scope inheritance; gate types and precedence; quorum and self-review semantics; expiry, time zones, veto and emergency bypass; effective-access roles; integrity verifier/trust roots; artifact handling and secret access; environment mutual exclusion; permissible rollback; target adapters; audit retention; state/event API; and precise UI copy. A comparison with any vendor is informative evidence only, not an Ec feature contract.
