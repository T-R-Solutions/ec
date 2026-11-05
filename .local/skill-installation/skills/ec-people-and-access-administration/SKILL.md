---
name: ec-people-and-access-administration
description: Implement or review Ec users, teams, groups, memberships and role-binding administration. Skip federation protocols and the effective-permission evaluation algorithm.
---

# Ec People and Access Administration

## Scope and inputs

Inspect principal lifecycle, membership ownership, external identity mapping, supported bindings,
policy revisions and actual administration APIs.

Use the Ec consumer's current product requirements and implementation as authority. Locate them
rather than assuming filenames, schemas, endpoints or deployed features. Examples in this folder are
technical proposals until confirmed there.

## Procedure

1. Separate authentication identity, active account status, group membership, assigned role and
   effective access; do not treat labels as grants.
2. Model invitations, activation, suspension, deletion and membership changes only where supported,
   preserving history and immutable identity.
3. Preview binding changes with the actual permission evaluator; check delegation authority, target
   scope and self-escalation independently.
4. Reauthorize mutations against current state and reconcile concurrent grant/revoke or lost
   acknowledgments; invalidate affected access projections according to policy.
5. Test protected last-administrator rules only when specified, unauthorized grants, stale previews
   and accessible error recovery. Confirm server effects and absence of effects after denial.

## Load detail when needed

- [Identity, Administrative Lifecycle and Binding Contracts — Engineering
  Reference](references/identity-lifecycle-and-bindings.md): consult for identity lifecycle and
  bindings.
- [Routing, Synthetic Test IDs and Real Ec Acceptance
  Oracles](references/routing-and-test-oracles.md): consult for routing and test oracles.
- [Evidence Ledger and Design Decisions — Ec People and Access
  Administration](references/sources-and-decisions.md): consult for sources and decisions.
- [Detailed decisions and failure cases](references/implementation-details.md): consult for a
  complex case, additional constraints or the remaining thematic references; apply only relevant
  steps.

## Completion and limits

Complete the requested change or diagnosis and the checks that support its claims. Report the
affected contract, observed results, actual versions/commands and material gaps. Keep structural
review, simulated cases, real integration and production evidence distinct. Imported research and
execution records are historical context, not results of the current task.

This folder works without SDD or other skills. Consult an available specialist only for a concrete
unresolved question; its absence is a gap only when essential to acceptance. Use the project's
existing record for a bounded change. This method grants no external access, publication or
destructive-action authority.
