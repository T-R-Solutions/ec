# Effective Authorization Evaluation and Freshness — Engineering Reference

> Use the destination's existing requirements and evidence format; named workflow documents are examples, not prerequisites. Optional SDD mapping is configured outside this skill.

This document supplies a **method and sample contract**, not a finalized Ec API or policy. The accompanying Python model uses invented example role/action names; these are **synthetic fixtures**, not product features.

## 1. Normalize the question

The authorization question is not "what role is printed next to a person?" It is:

`Check(verifiedPrincipal, action, resourceId, trustedContext, minimumEffectiveRevision)`.

Required reasoning inputs:

- Identity: verified stable principal ID; activity/deprovision state and epoch from the trusted identity subsystem.
- Resource: stable ID, type, tenant, project scope, version of topology, and actual object state if relevant.
- Policy: **published/effective** canonical YAML-derived role/policy/binding revision; valid schema and references.
- Relations: team/group memberships, direct bindings, optional role hierarchy and their effective revision(s).
- Context: trusted and adequately fresh attributes (environment, approval state, owner, session assurance etc.) defined by the approved project contract. UI fields are not trusted attributes.
- Required consistency: a minimum revision or equivalent watermark after a known revocation/change. Do not replace a revision requirement with an unverified TTL.

Represent a single coherent observed tuple such as:

`{configRevision, topologyRevision, membershipRevision, identityRevision, attributesRevision, evaluatorVersion}`.

The tuple is illustrative; a deployment may use a single strongly consistent snapshot or a verified causal token. **Freshness is a correctness property; a random tuple of individually fresh values is not automatically a coherent snapshot.**

## 2. Define result states explicitly

| Result | Meaning | Enforcer action | Operator rendering |
|---|---|---|---|
| `ALLOW` | Verified, applicable authorization at required freshness | Continue protected operation, subject to all other controls | Allowed; show bounded explanation if viewer permitted |
| `DENY` | Verified evaluation of policy at required freshness yielded no authorization or a policy prohibition | Do not perform | Denied and relevant non-sensitive reason |
| `INDETERMINATE` | Evaluation unavailable, invalid, missing trusted context, revision not satisfied, graph cycle/limit, or inconsistent snapshots | **Do not perform**; safe retry/escalation according to project workflow | Unavailable/stale; *not* "no permission" |

A policy engine may choose `deny` for individual evaluation errors or skip a failing rule; **Ec must define and validate its own behavior**. The synthetic test model rejects incomplete input instead of silently accepting. Authorization result details for a denied/unknown actor are minimized to prevent information disclosure.

## 3. Typed graph boundaries

1. *Project tree*: `scope -> optional parent` with one validated tenant. Determines which ancestor scopes might apply, only as approved.
2. *Group memberships*: `user/member -> group` possibly nested; never infer role from a team's label. If supported, validate acyclicity or bound traversal with explicit semantics.
3. *Role hierarchy*: `role -> included role(s)` only if approved; role inheritance is **not** equivalent to project inheritance.
4. *Bindings*: stable ID, assigned principal or group, role ID, scope, origin/provenance, version, effect/constraints only if approved.
5. *Conditions*: checked against typed attributes and trusted provenance; missing context can be **indeterminate**, not automatically false.

**Algorithm outline:** validate input set and version; resolve principal status; validate tenant and resource; enumerate applicable scope path; obtain transitive memberships and roles; collect matching bindings; apply the **project-defined** combination, constraint, deny and separation-of-duty rules; return an enforcement result plus scoped evidence. Do not rely on a frontend-computed matrix for API authorization. Bound graph work with negotiated limits.

## 4. Anti-escalation and lifecycle checks

Privilege escalation requires a *second* authorization question about the actor's ability to **grant** the exact capability at its scope. The K8s analogy is `bind`/`escalate`, but Ec may choose another policy; require approved project contract semantics. A general method:

- Compare actor rights for grant-management and intended role/permissions **at the target scope**.
- Detect self-grant, cross-tenant, privileged resource and protected-role changes, as defined in project workflow.
- Ensure approval is independent and candidate/revision-bound where required.
- At commit, revalidate actor, target and policy versions; a preview cannot authorize a changed candidate.
- Evaluate before/after candidate impact with the same backend policy function. The preview is not publication.
- If a revocation changes the effective revision, cached `ALLOW` cannot outlive the approved guarantee. Frontend hiding is insufficient.

Do not invent an unconditional superuser role or impose a universal "role may only grant subset" rule without product approval. The sample reference model **chooses** that restrictive policy to test the pattern and labels it as a fixture assumption.

## 5. Freshness hazards and tests

**Causal revocation counterexample:** A user is removed from a parent project's access list, then a new sensitive artifact is added to a child project. If the artifact fetch checks an older membership snapshot, the removed user can see newly added content. This is structurally analogous to the Zanzibar *new enemy* problem; do not claim Ec uses Zanzibar or inherits its guarantees.

**Race counterexample:** An administrator previews a grant, loses administrative permission, then confirms the old preview. Prevent this by re-checking actor authorization and revision on commit; the API must not trust a preflight token without binding to exact inputs and expiration.

**Move counterexample:** A project move changes its ancestors. A cached effective permission for the old tree must not authorize an operation under the new topology. Invalidate by topology change and re-check tenant/scope identity.

**Partial publication:** A YAML Git revision may be committed while the operational policy projection is still reconciling. A decision must reference the version actually effective and should not claim `synced` without verified evidence; either pin a previous known-effective policy under an approved transition guarantee or return `INDETERMINATE` where a newer minimum is required.

**Consistency test obligations:** 
- `Check` and `ListAccessibleResources` agree for **identical principal/action/policy snapshot**; pagination and filtering must not leak objects.
- A role that grants permission via two paths yields duplicate evidence paths but one effective capability.
- Revocation plus a required newer revision must never reuse the old cache result.
- A graph traversal cycle or missing identity/context must not become an allow.
- Explanations to non-administrators cannot reveal private group/role members or hidden resource IDs.

## 6. Decision and explanation UI

For a privileged administrator, show the action and resource, source path (direct/team/role/ancestor), effect, active policy revision, evaluated timestamp/watermark, and explicit stale/unavailable indication. For general users, return only the information they are authorized to see. Use text and accessible status (WCAG 2.2) and keyboard-operable tables; do not infer `ARIA treegrid` simply because the underlying project structure is a tree. For a simulated impact preview, label changes as `proposed`, not `effective`.

## 7. Evidence to return to project workflow

Supply the policy/project workflow assumption registry; architecture alternatives; source input versions; test oracle table with actual commands/results; counterexamples; measured execution or test timings if real; and an unresolved decision list. The skill never marks an project acceptance criterion complete on the basis of examples alone.
