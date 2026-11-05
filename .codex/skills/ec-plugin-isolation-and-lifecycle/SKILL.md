---
name: ec-plugin-isolation-and-lifecycle
description: Implement or review Ec plugin artifact trust, isolation, granted capabilities and lifecycle recovery. Skip manifest compatibility alone and unrelated extension UI.
---

# Ec Plugin Isolation and Lifecycle

## Scope and inputs

Identify actual plugin formats, host boundaries, trust policy, signature/provenance checks,
requested capabilities and supported installation/upgrade operations.

Use the Ec consumer's current product requirements and implementation as authority. Locate them
rather than assuming filenames, schemas, endpoints or deployed features. Examples in this folder are
technical proposals until confirmed there.

## Procedure

1. Verify artifact identity and trusted origin independently of manifest compatibility; inspect
   archive traversal, links and extraction resource limits.
2. Enforce the intersection of requested and granted capabilities at host calls. A capability
   declaration or signature is not authorization.
3. Apply required filesystem, network, process and resource containment before execution; fail
   closed when mandatory isolation is unavailable.
4. Model install/start/stop/upgrade/remove and interrupted recovery, fencing obsolete instances and
   reconciling retained state. Disable is not necessarily termination.
5. Test tampering, revoked trust, ungranted calls, resource exhaustion, stale callbacks and partial
   upgrades with real host evidence where available.

## Load detail when needed

- [Artifact Verification, Provenance and Trust — On-Demand
  Method](references/artifact-verification-and-trust.md): consult for artifact verification and
  trust.
- [Isolation and Runtime Capability Enforcement — On-Demand
  Method](references/isolation-and-capability-enforcement.md): consult for isolation and capability
  enforcement.
- [Lifecycle, Recovery and Truthful Plugin Console](references/lifecycle-and-reconciliation.md):
  consult for lifecycle and reconciliation.
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
