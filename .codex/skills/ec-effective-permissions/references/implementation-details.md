# Detailed decisions and failure cases

Read only for a decision needing more detail than the entry point. Apply the relevant steps to the
actual task; the sections are not a mandatory artifact or test checklist. Use existing project
contracts in their actual format. File names such as spec.md, plan.md or tasks.md are examples, not
required dependencies. Other skills are optional expertise, never enforcement mechanisms.

Product rules, stack choices and authority locations must be verified in the Ec consumer. Examples
do not establish deployed capabilities or override its current contract.

## Decision and implementation procedure

1. **Locate and classify authoritative inputs.** Inspect the actual approved project contract and
   repository. Identify the currently **effective/published YAML revision**, stable principal
   identity, trusted active/disabled status, roles, direct/scoped bindings, team/group memberships,
   project topology, requested action, resource ID/tenant, environmental context, and
   revision/watermark of each input. Desired drafts are *not* current grants. Session secrets,
   audits and runtime attributes are operational, never Ec configuration YAML.
2. **Define the authorization contract before an algorithm.** Use `Check(principal, action,
   resource, context, minimumRevision) -> {allow | deny | indeterminate, evidence,
   observedVersions}`. Explicitly distinguish a valid policy **deny** from **indeterminate** due to
   stale, missing, cyclic, invalid or unavailable input. Both prohibit performing the protected
   action; treat indeterminate as an operational error, not proof that the subject lacks an
   entitlement. All protected server-side operations must enforce their own authorized decision; UI
   visibility is advisory only.
3. **Keep graph types distinct.** The *project tree* determines resource scope; a *role hierarchy*
   determines inherited permissions if approved; a *team/group membership graph* connects principals
   to bindings. No role follows automatically from a project parent or a UI team label. Validate
   stable IDs, tenant isolation, allowed ancestry rules, role order, reference existence, maximum
   traversal work, and cycles. Use separate traversal functions and deterministic semantics; limit
   fan-out/depth to prevent denial of service.
4. **Evaluate the approved policy, not a guessed 'highest role'.** Expand authorized subject/group
   membership, applicable direct/inherited bindings and role permissions as allowed by the project
   workflow. Then evaluate action/resource/context restrictions and applicable separation-of-duty
   constraints under the policy's **explicit combination/precedence model**. Deny-by-default is a
   security baseline; `deny-overrides`, additive roles, exceptions, role activation and inherited
   scope permissions are **not universal Ec defaults**. If precedence is unspecified, block
   implementation. Never promote `admin` by its name, assume global superuser bypass, or reuse an
   entity's display label as a security key.
5. **Make freshness and revocation a correctness property.** For each decision, pin/verify a
   coherent input snapshot or a documented bounded-consistency contract. At minimum, track effective
   YAML configuration revision plus relevant hierarchy, group, identity/status and runtime-attribute
   versions. Do not serve an old cached `allow` after revocation, membership removal, topology move,
   policy update or sensitive resource-content change. Use revision-based
   invalidation/read-after-write or equivalent guaranteed mechanism, not TTL alone. If freshness
   cannot be verified for a privileged action, return **indeterminate** and do not execute it. A Git
   commit and an operational projection are not atomically committed by default.
6. **Authorize administration and delegation independently.** The permission to edit role bindings
   is not automatically permission to grant every role. Preflight actor, *specific* proposed action,
   target, scope, role/permission set, self-grant risk, separation-of-duty constraints, approval and
   current versions; re-evaluate at commit. Implement role-subset constraints, explicit
   `bind`/`escalate`-like capabilities or independent approvals **only according to approved Ec
   policy**. Prevent cross-tenant granting, forged approvals, stale privilege checks and silent loss
   of all authorized recovery administrators. Submit desired binding changes to
   `ec-people-and-access-administration` and `ec-configuration-publication`; this skill **does not**
   persist them.
7. **Generate explainable and safe results.** Provide direct versus inherited versus
   team/group-derived grant provenance, the relevant role/binding/scope path, matched restrictions,
   confidence/freshness state and exact versions. A permission matrix must distinguish **assigned
   role**, **computed effective access**, **not yet effective**, **denied** and
   **unavailable/stale**. Display an impact diff before/after a candidate policy **using the same
   evaluation contract**, never a duplicate front-end algorithm. Filter explanations by viewer
   authorization: a denied user must not learn secret group memberships or private resource names.
   Test keyboard, screen reader and status messaging for actual admin UIs.
8. **Prove the safety envelope.** Write policy decision tables and counterexamples before
   implementation; property/state-machine test default-deny, inheritance, nested teams, cycles,
   cross-tenant isolation, role constraints, revocation freshness, stale cache, self-escalation,
   concurrent grant/revoke, direct/effective provenance, list/check equivalence, incomplete context,
   permission-change snapshots, backend/API/worker bypass, and explainability redaction. Reconcile
   oracle results against approved project contract IDs. Synthetic model results are **not** Ec
   runtime evidence. Require real server, Git, PostgreSQL, browser/assistive-tech and agent-behavior
   tests when those implementations exist.


## Further references by decision

Read only the reference relevant to the unresolved decision. Research registers describe imported authorship, not current execution.

- [Effective Authorization Evaluation and Freshness — Engineering Reference](evaluation-contract-and-freshness.md): evaluation contract and freshness.
- [Skill Routing and Verification Oracles](routing-and-test-oracles.md): routing and test oracles.
- [Evidence Ledger and Engineering Decisions — Ec Effective Permissions](sources-and-decisions.md): source provenance or a disputed recommendation.
