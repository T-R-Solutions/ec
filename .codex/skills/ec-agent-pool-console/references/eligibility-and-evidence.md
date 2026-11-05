# Explainable Eligibility and Evidence — Engineering Decision Guide

> Use the destination's existing requirements and evidence format; named workflow documents are examples, not prerequisites. Optional SDD mapping is configured outside this skill.

## Separate the questions

1. **Configuration match:** Is this agent in an allowed pool, with project scope, resource policy and required capabilities satisfied? This is a versioned desired-state question.
2. **Authorization and trust:** Does the approved identity/pool/workload policy permit the scheduling decision? A label or self-report does not authenticate a device or prove isolation.
3. **Operational readiness:** Does the server hold sufficiently recent evidence of agent connection, enabled status, maintenance state and available slots/resources?
4. **Scheduler outcome:** Did the authoritative scheduler actually reserve/assign a job? A candidate's eligibility cannot answer this; only the scheduler's committed decision can.

**Do not conflate** capability matching with current readiness, resource capacity with policy permissions, or stale observations with confirmed zeros. Never report a fabricated precise queue rank or ETA.

## Example reason taxonomy (method proposal; NOT an Ec API contract)

| Axis | Example reason code | Character | Required evidence |
|---|---|---|---|
| Desired scope | `project_not_allowed` | Hard constraint | Published YAML revision and project identifier |
| Desired membership | `pool_membership_mismatch` | Hard constraint | Versioned pool membership and registered agent ID |
| Policy | `pool_disabled` | Hard constraint | Published configuration and effective revision |
| Identity | `unauthorized`, `quarantined` | Hard constraint | Server authorization/security result |
| Compatibility | `protocol_mismatch` | Hard constraint | Version negotiation and approved compatibility matrix |
| Capability | `verified_capability_missing` | Hard constraint for protected job | Verified capability provenance and protected-job policy |
| Connection | `disconnected` | Temporary availability | Server-observed liveness/connection state |
| Maintenance | `draining` | Temporary availability | Confirmed maintenance state |
| Busy | `slots_available_insufficient` | Temporary availability | Server-side capacity measurement |
| Resources | `memory_mib_available_insufficient` | Temporary availability | Capacity snapshot with units and revision |
| Evidence | `observation_stale` | Unknown | Server observation age against **project-approved** freshness window |
| Evidence | `connected_unknown` | Unknown | Missing verified connection signal |
| Evidence | `isolation_not_verified` | Unknown or policy fail | Concrete trust policy and verification state |
| Evidence | `slots_available_unknown` | Unknown | Missing/invalid capacity snapshot |

These codes are illustrative names for unit-test fixtures only. **Never copy them into Ec's public API without a reviewed project workflow contract.** Distinguish security-policy block, temporary shortage and unknown evidence in explanations; multiple independent reasons may coexist. A safe example precedence is hard failure → `ineligible`; otherwise unknown → `unknown`; otherwise temporary → `temporarily_unavailable`; otherwise `eligible_candidate`. This is an **engineering proposal**, not a chosen Ec policy.

## Snapshot and consistency protocol

- Include agent ID, pool ID, desired-config revision, agent observation version, server observation timestamp, and data source. If source and revision disagree, show mixed/unknown state rather than asserting readiness.
- Accept only monotonic server-issued observation versions for each identity/session. Reject conflicting same-version payloads; ignore strictly older messages. Never use agent wall-clock to choose the newest authoritative state.
- A fresh snapshot is not a reservation. Slot/resource counts may change between observation and dispatch; the authoritative scheduler must revalidate and transact allocation.
- Show `observed N seconds ago` (if verified clock inputs exist), not `online now` based solely on a previous heartbeat or an uncensored caching assumption.
- Missing capacity is **unknown**, not `0` or `unlimited`. A subtotal can be reported as `known free slots`, with incomplete-coverage flag. Do not call that subtotal schedulable capacity.
- For security-sensitive users, expose only the reasons the viewer is authorized to inspect; a blocked result can safely say “Details restricted.”

## Resource and constraint mechanics

Kubernetes distinguishes *hard filters* from preference scoring and separates *resource requests* from runtime measurements; GitHub Actions combines label and runner group conditions. These provide conceptual reference points, not Ec field names or semantics. Do not import Kubernetes taint/affinity syntax, GitHub `runs-on`, Azure `demands`, or TeamCity's one-pool-per-agent restriction into Ec automatically.

Where numeric requests are approved, use explicit units and dimensions (slots, millicores, memory MiB, accelerator counts). A process may report usage without proving allocatable capacity. Changes in desired `maxSlots`, pool association or requirements need a semantic impact preview, and *capacity accounting belongs to the scheduler*, not the console.

## Diagnosis oracle

For every diagnostic response, ask:

- What exact job and configuration revision does this answer concern?
- Which agent/pool and server observation revision were checked?
- Is the issue `ineligible`, `temporarily_unavailable`, `unknown`, or merely a lower scheduling priority?
- Does each reason identify authoritative origin and a justified remedy?
- Can a user with fewer privileges infer hidden project membership or protected capabilities from detailed text?
- Would the same data lead to the same ordered codes in a repeat call?

If any answer depends on unspecified Ec policy, request the project workflow decision and do not silently assume the vendor default.
