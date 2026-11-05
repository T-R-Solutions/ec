# Selective Routing and Test Oracles

> Use the destination's existing requirements and evidence format; named workflow documents are examples, not prerequisites. Optional SDD mapping is configured outside this skill. Commands, fixture paths and historical execution claims from the source package are illustrative or imported evidence unless the corresponding files are present and independently checked; they are not current test results or bundled executable tools.

These cases are **planned applicability/acceptance oracles**. The subset tagged `SYNTHETIC EXECUTED` is covered by the local in-memory Python reference-model suite; it is **not an Ec implementation pass**. Every HTTP, Git-provider, real PostgreSQL, UI, multi-resource and agent case remains **PLANNED FOR REAL EC**. The reference model is not a production security model. Three extra tests execute the actual **local** Git `update-ref` old-OID CAS behavior; they do not exercise Git remotes, branch protections or the Ec backend.

## Fourteen skill-selection scenarios

| Case | Task | Route | Reason |
|---|---|---|---|
| SEL-01 | YAML revision publication | Select | A validated candidate is promoted to an immutable published authority revision. |
| SEL-02 | False published UI acknowledgment | Select | A frontend says published before Git authority confirms an accepted revision. |
| SEL-03 | Concurrent publishers | Select | Two writers attempt to advance the same base revision concurrently. |
| SEL-04 | Git accepted; DB stale | Select | Publication succeeds but derived PostgreSQL projection is not updated. |
| SEL-05 | Approval invalidation | Select | A candidate is edited after content-bound approval or a permission is revoked. |
| SEL-06 | Desired/observed divergence | Select | Runtime state no longer matches the acknowledged YAML revision. |
| SEL-07 | Run provenance | Select | A run needs its effective configuration revision pinned at admission. |
| SEL-08 | YAML type validation | Do not select | Only key types/parser/schema evolution is in scope; use ec-declarative-resource-schemas. |
| SEL-09 | Form input semantics | Do not select | Only hidden fields or Visual/Form editing projection is in scope; use ec-semantic-configuration-projections. |
| SEL-10 | Deployment rollout | Do not select | A deployment of application bytes/strategy is in scope, not Ec configuration publication. |
| SEL-11 | Generic index tuning | Do not select | A PostgreSQL query plan issue has no publication state or revision concern. |
| SEL-12 | CI queue fairness | Do not select | Job scheduling quotas/leases without desired configuration publication. |
| SEL-13 | Writing an project workflow feature | Do not select | Only defining or revising acceptance criteria, not technical publication design. |
| SEL-14 | Routine Git docs | Do not select | A general branch operation without Ec configuration approval or state truthfulness. |

## Forty technical oracles

| Case | Scenario | Expected observable outcome | Evidence status |
|---|---|---|---|
| T-01 | Remote authority starts with known base | Original authority head equals initial immutable base. | `SYNTHETIC EXECUTED` (`test_authority_starts_on_initial_revision`) |
| T-02 | Published differs from synced | After accepted CAS, application remains reconciling until observation. | `SYNTHETIC EXECUTED` (`test_success_is_published_but_not_synced`) |
| T-03 | Observed revision determines synced | A matching revision observation is required to mark synced. | `SYNTHETIC EXECUTED` (`test_reconcile_requires_observed_revision`) |
| T-04 | Invalid syntax cannot publish | Reject invalid candidate and preserve original published head. | `SYNTHETIC EXECUTED` (`test_invalid_syntax_rejected_without_mutating_head`) |
| T-05 | Broken references cannot publish | Reject candidate with invalid dependency/reference graph. | `SYNTHETIC EXECUTED` (`test_broken_references_rejected`) |
| T-06 | Secret policy cannot be bypassed | Reject candidate flagged as violating secrets constraints. | `SYNTHETIC EXECUTED` (`test_inline_secret_policy_rejected`) |
| T-07 | Approval binds exact digest | Change any candidate content after approval and reject the publication. | `SYNTHETIC EXECUTED` (`test_approval_content_digest_bound`) |
| T-08 | Approval binds scope | A cross-scope change invalidates prior approval. | `SYNTHETIC EXECUTED` (`test_approval_scope_bound`) |
| T-09 | Approval binds base | A new base reference invalidates the previous approval. | `SYNTHETIC EXECUTED` (`test_approval_base_bound`) |
| T-10 | Approval binds policy version | A policy version change requires renewed approval. | `SYNTHETIC EXECUTED` (`test_approval_policy_version_bound`) |
| T-11 | Authorization rechecked at publish | Revoked publishing actor is denied despite previous approval. | `SYNTHETIC EXECUTED` (`test_auth_revoked_after_approval_is_rejected_at_publish`) |
| T-12 | Reviewer authorization rechecked | Reviewers lacking the target scope cannot approve publication. | `SYNTHETIC EXECUTED` (`test_reviewer_outside_scope_rejected`) |
| T-13 | Separation of duties when required | Reject self-approval under a configured distinct-reviewer policy. | `SYNTHETIC EXECUTED` (`test_self_approval_blocked_when_policy_requires`) |
| T-14 | Self-approval conditional on policy | Do not hardcode a universal rule if policy explicitly allows it. | `SYNTHETIC EXECUTED` (`test_self_approval_is_policy_conditional`) |
| T-15 | Two publishers one authority result | One CAS succeeds and another with stale base fails without overwriting it. | `SYNTHETIC EXECUTED` (`test_stale_base_conflict_keeps_first_publisher`) |
| T-16 | Same request key same content | Retry returns previously accepted publication without a new commit. | `SYNTHETIC EXECUTED` (`test_same_request_id_is_idempotent`) |
| T-17 | Same request key changed content | Reject conflicting idempotency payload rather than return stale success. | `SYNTHETIC EXECUTED` (`test_request_id_reuse_with_different_payload_rejected`) |
| T-18 | Before-CAS crash | No published-state acknowledgment if authority has not changed. | `SYNTHETIC EXECUTED` (`test_before_cas_does_not_publish`) |
| T-19 | Recovery after before-CAS failure | A retry may safely attempt the old-base CAS after checking it still matches. | `SYNTHETIC EXECUTED` (`test_retry_after_before_cas`) |
| T-20 | Git then projection crash | An accepted Git revision must not imply updated DB projection. | `SYNTHETIC EXECUTED` (`test_after_cas_crash_does_not_falsely_mark_projection`) |
| T-21 | Remote acknowledgment lost | Recover by checking the actual ref, not by assuming an unsuccessful push. | `SYNTHETIC EXECUTED` (`test_recovery_checks_remote_ref`) |
| T-22 | Remote moves before recovery | Without a durable acceptance receipt, retain unknown rather than fabricate outcome. | `SYNTHETIC EXECUTED` (`test_recovery_unknown_if_head_changes_without_receipt`) |
| T-23 | Unknown request replay | An unknown-result same-ID retry must not blindly issue a second CAS. | `SYNTHETIC EXECUTED` (`test_duplicate_retry_during_unknown_does_not_issue_second_cas`) |
| T-24 | Rebuild projection | Corrupted derived state can be reconstructed from authoritative revision. | `SYNTHETIC EXECUTED` (`test_projection_repair_is_from_git_authority`) |
| T-25 | Reconciler failure not synced | Failed application cannot be reported synced. | `SYNTHETIC EXECUTED` (`test_failed_reconcile_not_reported_synced`) |
| T-26 | Superseded worker safety | Old worker cannot apply stale revision over a newer desired head. | `SYNTHETIC EXECUTED` (`test_stale_worker_cannot_apply_older_revision`) |
| T-27 | Drift never autohealed by assumption | Observation of mismatch yields drift, without unapproved mutation. | `SYNTHETIC EXECUTED` (`test_drift_does_not_autoheal`) |
| T-28 | Run pins effective configuration | Run provenance is immutable even across pending or later publication. | `SYNTHETIC EXECUTED` (`test_run_revision_is_effective_and_pinned`) |
| T-29 | Rollback preserves history | Restore old YAML as a new reviewed publication, with new identity. | `SYNTHETIC EXECUTED` (`test_rollback_creates_new_publication_not_mutate_history`) |
| T-30 | Revision identity binds base | Identical bytes with different parent revision produce distinct synthetic IDs. | `SYNTHETIC EXECUTED` (`test_publication_revision_identity_is_base_sensitive`) |
| T-31 | HTTP precondition failure | A stale If-Match produces RFC-compliant 412 when conditional API is chosen. | **PLANNED FOR REAL EC** |
| T-32 | Missing precondition when required | When the API requires If-Match, missing precondition returns documented 428. | **PLANNED FOR REAL EC** |
| T-33 | Semantic merge conflict | Provide actionable base/current/proposed diff on resource conflict. | **PLANNED FOR REAL EC** |
| T-34 | Target partially applied | A resource-group sync is false until every required part is observed at revision. | **PLANNED FOR REAL EC** |
| T-35 | Retry exhaustion and failure cause | Exhausted transient retries yield an actionable blocked/failed state. | **PLANNED FOR REAL EC** |
| T-36 | Audit and redaction | Store decisions, actor and revision identity but not plaintext secrets. | **PLANNED FOR REAL EC** |
| T-37 | Cross-store replay ordering | Idempotent replay handles duplicate events without stale overwrites. | **PLANNED FOR REAL EC** |
| T-38 | Actual branch protection | Rejected protected ref updates do not mark published or expose secret metadata. | **PLANNED FOR REAL EC** |
| T-39 | Immutable publication and effective revision on run | Past accepted jobs retain both source-code and effective-config identity. | `SYNTHETIC EXECUTED` (`test_run_revision_is_effective_and_pinned`) |
| T-40 | Never claim global atomicity from local Git batch | Test concurrent reads during multi-ref updates and avoid false global transaction claims. | **PLANNED FOR REAL EC** |

## How to evaluate

1. Map each feature project acceptance ID to a behavior, proposed command, necessary fixtures, expected server response and exact observation boundary. Do not invent missing IDs.
2. Start with isolated method oracles, then real integration tests with the chosen Git authority, database and HTTP provider; inject faults before CAS, after CAS, after projection, at reconciliation, and after a newer revision supersedes a worker.
3. Run concurrent publishers using barrier-controlled requests rather than assuming a sequential unit test covers races. Verify no lost update and that the loser receives a resolution path.
4. For UI status, assert the server-side acknowledgment predicate and both status axes; include screen-reader and keyboard equivalents where the existing acceptance criteria require them.
5. Publish the exact commands, observed exit codes, real environment/version information, and unanswered invariants. A Markdown matrix by itself is not an executed test.

**Actual Ec application tests: NONE has been run.** All future integration cases remain **PLANNED**, regardless of synthetic reference results.
