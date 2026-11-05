# Pool Administration, Operator Safety and UX — Method Guide

> Use the destination's existing requirements and evidence format; named workflow documents are examples, not prerequisites. Optional SDD mapping is configured outside this skill.

## Administrative pathways

| User intention | Preparation | Required confirmation / observability | Owning layer |
|---|---|---|---|
| Create or edit a pool | Inspect approved schema, project associations, restrictions, quotas, existing assignments and version | YAML draft → validation → diff → authorization → published revision → effective sync; do not claim completion earlier | `ec-configuration-publication`, `ec-declarative-resource-schemas` |
| Associate project to pool | Show inherited scope and enabled agents, allowed workload trust levels | Preview changes to execution eligibility, including unexpected expansions | `ec-effective-permissions`, publication |
| Reassign an agent | Confirm source/destination privileges, active work and configuration revision | Two-scope authorization, explicit impact and conflict resolution | Console prepares; publication commits desired resource |
| Drain agent | Inspect running work; choose approved policy on new assignments | Acknowledge operation request separately from confirmed server enforcement; running work is not automatically canceled | `ec-agent-control-protocol`, `ec-run-execution-operations` |
| Disable/quarantine agent | Show trust and operational consequences, affected jobs and audit metadata | Distinguish *disabled* vs *disconnected* vs *quarantined* vs *draining* | Server operational contract / effective permissions |
| Remove agent or pool | Check dependent projects, jobs, active leases, references, aliases, permission/access changes | Destructive action approval, verified effects and documented compensation/recovery | project workflow and relevant owning feature |

## Pool versus agent state

- **Desired state**: named pool resources, project associations, restrictions, limits and permissions as specifically defined in Ec YAML schemas. Version the authoritative configuration and display reconciliation status.
- **Operational state**: last observed connectivity, session, authorization, worker availability, busy slots, current work/lease identity, reported capacity, maintenance and failures. This data is not a second source of desired configuration.
- **Provenance**: make agent self-reports visually distinguishable from backend verified facts. Treat privilege-bearing capabilities or protected pool claims as untrusted unless attested/verified by the approved security mechanism.
- **Ordering**: a queued job may be compatible with several agents; the console never chooses or reserves one unless the `cicd-scheduler` contract exposes a committed outcome. Show reasons for *no candidate* and *candidates busy* separately.

## UX coverage and feedback

- Navigation: Infrastructure → Agents/Pools; agent detail, pool detail, associated projects, compatibility and capacity tabs. Account for global/project scopes and explicit permission boundaries, without filling the sidebar with every operation.
- Inventory tables: status text + icon, name/ID, pool, session/last observed, capabilities with trust origin, free/total slots with units and source, current jobs, maintenance state and relevant actions. For disconnected/unknown data show a timestamp and uncertainty, not an apparently live green badge.
- Diagnostics: job selector → matched/blocked/unknown agents → grouped reason codes → controlled reveal of evidence → suggested remediation → deep link to the owning permission/configuration/run view. Never infer a reason from appearance alone.
- Administrative flow: preview before edit, show project scope and affected running work, review diff and permissions, submit request, show server confirmation, show asynchronous synchronization when applicable. Do not optimistically imply completion of critical actions.
- Dense data: use semantic native tables by default; choose WAI-ARIA `grid` only when multi-cell keyboard interaction justifies the extra focus-management obligations. Filters and status announcements need screen-reader access and keyboard operation. Live updates must not steal focus.
- Reassignment by drag: if drag-and-drop is supported, provide a click/button and single-pointer alternative, keyboard path, destination confirmation and undo/compensation only when truly supported. WCAG 2.2 AA also requires keyboard and focus behavior independent of the dragging criterion.
- Responsiveness: retain key status, last observation and reason code on narrow viewports; use expandable detail/alternative list; test long project names and mixed online/unknown states. Verify light/dark states and contrast in actual interactive tests.

## Failure scenarios requiring a specification

- What happens to running jobs when a pool membership or pool trust policy changes?
- What happens when a pool is disabled while jobs are queued? Is reassignment allowed?
- Can one agent be assigned to multiple pools? The vendor examples differ; Ec's answer requires project workflow approval.
- Does agent enrollment confer any project access? It must not be assumed.
- What is the exact freshness threshold; how do disconnected and suspect differ?
- How are capability origins verified, and does a protected job require stronger attestation?
- Which actor may drain or quarantine a node and at what scope? What is the audit trail?
- What is the protocol for user-facing status after request accepted but confirmation lost?
- Are quotas, reserved capacity and priority exposed, and on what authoritative schedule?

There is **no** claim that Ec currently implements any of these actions, statuses or screens. The document describes how to implement them **after the project workflow selects their actual semantics**.
