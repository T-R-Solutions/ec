# Routing, Synthetic Test IDs and Real Ec Acceptance Oracles

> Use the destination's existing requirements and evidence format; named workflow documents are examples, not prerequisites. Optional SDD mapping is configured outside this skill. Commands, fixture paths and historical execution claims from the source package are illustrative or imported evidence unless the corresponding files are present and independently checked; they are not current test results or bundled executable tools.

**Important:** `SEL-*` cases describe agent-selection prompts but were **not executed** against a live language model. `SYN-*` cases map to real executed methods on the **synthetic Python model**, not the Ec application. `EC-*` cases require an actual Ec repo, approved project contract feature and services and are **not executed**. Passing unit tests cannot certify identity federation, authorization or production security.

## Skill selection cases (prepared, not executed)

| ID | Example task | Expected selection |
|---|---|---|
| SEL-01 | Implement user listing and scoped role bindings in Ec administration | **Select** |
| SEL-02 | Design a pending/cancelled/expired invite workflow for Ec projects | **Select** |
| SEL-03 | Add team roster editing to the Ec admin console with revision-aware publishing | **Select** |
| SEL-04 | Review user deactivation and desired access-binding cleanup | **Select** |
| SEL-05 | Implement a bulk project-member revoke preview | **Select** |
| SEL-06 | Integrate IdP-provisioned membership with Ec administrative scope declarations | **Select**, delegate provider protocol implementation |
| SEL-07 | Review identity linking when two federated identities share an email label | **Select**, use verified `identity-federation` inputs |
| SEL-08 | Implement service-account administration when a spec explicitly includes it | **Select**; require distinct security ownership |
| SEL-09 | Change a Rust compiler flag in the CI executor | **Do not select** |
| SEL-10 | Implement OIDC authorization-code flow, PKCE and token validation | **Do not select**; `identity-federation` |
| SEL-11 | Calculate whether an inherited project role permits a deployment | **Do not select**; `ec-effective-permissions` |
| SEL-12 | Design generic RBAC safety model independent of Ec administrative UI | **Do not select**; application security / access research |
| SEL-13 | Create a YAML parser and schema validator for Pipeline resources | **Do not select**; `ec-declarative-resource-schemas` |
| SEL-14 | Diagnose a scheduler lease expiry on Rust agents | **Do not select** |
| SEL-15 | Change the project/subproject tree or move one project under another | **Do not select**; `ec-project-hierarchy-management` |
| SEL-16 | Author acceptance requirements and decide who may invite admins | **Do not select** as requirement owner; project workflow `spec.md` governs |

## Executed synthetic state-machine cases

All 81 names are exact executable `unittest` method identifiers in package test file `validation/test_reference_model.py` (outside the project workflow skill import boundary). They are deliberately **illustrative**, not implementation of Ec policy, OIDC, SCIM, Git or PostgreSQL.

| Group | IDs | Property checked in synthetic model |
|---|---|---|
| Identity uniqueness and mapping | SYN-01 through SYN-11 | Federated `(iss,sub)` vs email, stable local IDs and rejection of unverified link |
| Disable/reactivation fencing | SYN-12 through SYN-17, SYN-55 through SYN-57 | Epoch, disabled-principal rejection, no silent restoration of toy bindings |
| Admin scope / role checks | SYN-18 through SYN-26, SYN-73–SYN-78 | Explicit decision fixtures, scope isolation, unsupported operation rejection |
| Candidate/publication/reconcile | SYN-27 through SYN-38, SYN-79–SYN-80 | No silent effective write, revision-bound preview/receipt, idempotence |
| Stale evidence and approvals | SYN-39 through SYN-51, SYN-81 | Revision, policy, identity and digest binding, independent-review fixture |
| Team versus role relationships | SYN-52 through SYN-54 | Separate roster and role-binding relations, revoke checks |
| Invitation operational lifecycle | SYN-58 through SYN-72 | Issue, expiration, cancel, verified redemption, no YAML/grant, redacted log |

### One-to-one test index

<!-- SYNTHETIC_TEST_INDEX: generated from the executable test methods, not invented results -->

| ID | Executed Python test method |
|---|---|
| SYN-01 | `test_01_issuer_subject_is_linked` |
| SYN-02 | `test_02_same_email_distinct_subject` |
| SYN-03 | `test_03_same_subject_distinct_issuer` |
| SYN-04 | `test_04_reject_duplicate_federated_mapping` |
| SYN-05 | `test_05_reject_reused_local_id` |
| SYN-06 | `test_06_reject_unverified_identity` |
| SYN-07 | `test_07_reject_empty_issuer` |
| SYN-08 | `test_08_reject_empty_subject` |
| SYN-09 | `test_09_reject_empty_principal_id` |
| SYN-10 | `test_10_email_update_preserves_identity` |
| SYN-11 | `test_11_unknown_identity_is_not_created_by_lookup` |
| SYN-12 | `test_12_deactivation_changes_epoch` |
| SYN-13 | `test_13_repeated_deactivation_is_idempotent` |
| SYN-14 | `test_14_reactivation_needs_provider_assertion` |
| SYN-15 | `test_15_reactivation_keeps_epoch` |
| SYN-16 | `test_16_deactivation_records_reconciliation_need` |
| SYN-17 | `test_17_deactivated_target_cannot_receive_bindings` |
| SYN-18 | `test_18_default_deny_for_non_admin` |
| SYN-19 | `test_19_wrong_scope_denied` |
| SYN-20 | `test_20_scope_must_exist` |
| SYN-21 | `test_21_wrong_role_denied` |
| SYN-22 | `test_22_unknown_target_denied` |
| SYN-23 | `test_23_wrong_operation_denied` |
| SYN-24 | `test_24_team_operation_requires_team_role` |
| SYN-25 | `test_25_role_operation_requires_role` |
| SYN-26 | `test_26_direct_nonprivileged_role_can_be_proposed` |
| SYN-27 | `test_27_proposal_not_immediately_published` |
| SYN-28 | `test_28_proposal_not_immediately_effective` |
| SYN-29 | `test_29_no_implicit_scope_leak` |
| SYN-30 | `test_30_published_role_becomes_eligible` |
| SYN-31 | `test_31_publish_not_equal_reconcile` |
| SYN-32 | `test_32_explicit_reconcile_confirmation` |
| SYN-33 | `test_33_cannot_confirm_wrong_reconcile_revision` |
| SYN-34 | `test_34_invalid_receipt_denied` |
| SYN-35 | `test_35_no_access_change_on_invalid_receipt` |
| SYN-36 | `test_36_unknown_proposal_cannot_publish` |
| SYN-37 | `test_37_successful_publication_idempotent` |
| SYN-38 | `test_38_stale_preview_after_other_publish` |
| SYN-39 | `test_39_stale_preview_after_policy_change` |
| SYN-40 | `test_40_stale_preview_after_identity_change` |
| SYN-41 | `test_41_mutated_candidate_digest_rejected` |
| SYN-42 | `test_42_no_self_role_assignment_without_review` |
| SYN-43 | `test_43_no_admin_assignment_without_review` |
| SYN-44 | `test_44_approved_admin_assignment_succeeds` |
| SYN-45 | `test_45_actor_cannot_approve_self` |
| SYN-46 | `test_46_target_cannot_approve_self` |
| SYN-47 | `test_47_unprivileged_reviewer_cannot_approve` |
| SYN-48 | `test_48_approval_binds_exact_digest` |
| SYN-49 | `test_49_approval_binds_scope` |
| SYN-50 | `test_50_approval_becomes_stale_on_policy_change` |
| SYN-51 | `test_51_approval_becomes_stale_on_revision_change` |
| SYN-52 | `test_52_team_membership_is_separate_from_role_binding` |
| SYN-53 | `test_53_team_member_revoke` |
| SYN-54 | `test_54_role_binding_revoke` |
| SYN-55 | `test_55_deactivation_fences_old_binding` |
| SYN-56 | `test_56_reactivation_does_not_restore_stale_binding` |
| SYN-57 | `test_57_after_reactivation_new_binding_revalidated` |
| SYN-58 | `test_58_invitation_requires_scope_permission` |
| SYN-59 | `test_59_invitation_is_pending` |
| SYN-60 | `test_60_invitation_token_stored_only_as_hash` |
| SYN-61 | `test_61_invitation_key_is_idempotent_without_token_replay` |
| SYN-62 | `test_62_invitation_rejects_expired_time` |
| SYN-63 | `test_63_invitation_rejects_invalid_email` |
| SYN-64 | `test_64_invitation_acceptance_requires_verified_email` |
| SYN-65 | `test_65_invitation_acceptance_requires_correct_token` |
| SYN-66 | `test_66_invitation_acceptance_is_single_use` |
| SYN-67 | `test_67_expired_invitation_cannot_be_accepted` |
| SYN-68 | `test_68_cancelled_invitation_cannot_be_accepted` |
| SYN-69 | `test_69_acceptance_does_not_grant_role_or_membership` |
| SYN-70 | `test_70_acceptance_does_not_publish_yaml` |
| SYN-71 | `test_71_cancel_requires_invitation_scope_permission` |
| SYN-72 | `test_72_disabled_principal_cannot_accept_invitation` |
| SYN-73 | `test_73_preview_does_not_mutate_desired_config` |
| SYN-74 | `test_74_unreviewed_self_membership_is_blocked` |
| SYN-75 | `test_75_independent_self_membership_review` |
| SYN-76 | `test_76_nonprivileged_team_membership_no_approval` |
| SYN-77 | `test_77_deleted_or_unknown_identifier_fails_closed` |
| SYN-78 | `test_78_aggregate_unauthorized_attempts_no_side_effects` |
| SYN-79 | `test_79_binding_does_not_become_effective_until_receipt` |
| SYN-80 | `test_80_identity_lifecycle_not_saved_as_yaml_binding` |
| SYN-81 | `test_81_reject_fabricated_review_approval` |

## Future Ec integration tests (24 prepared, NOT executed)

| ID | Required actual integration evidence / oracle |
|---|---|
| EC-01 | OIDC verified `(iss,sub)` attaches to stable Ec principal ID through trusted provider, not email; duplicate-email subjects remain separate. |
| EC-02 | Cross-provider same `sub` does not merge two principals; account-linking consent/proof verified end to end. |
| EC-03 | SCIM `id` and provisioning-domain `externalId` reconciliation is version-aware and collision-safe. |
| EC-04 | Disabled user cannot authenticate or authorize protected administration action even when a previous YAML binding remains. |
| EC-05 | Deprovisioning during a pending admin change invalidates the candidate without transient access exposure. |
| EC-06 | Reactivation behavior matches the **approved** restoration policy, including preexisting roles and sessions. |
| EC-07 | Invitation issued, rescinded, expired and accepted through actual provider/email flow with single-use token protections. |
| EC-08 | Invitation acceptance cannot bypass identity proofing and does not create unapproved effective access. |
| EC-09 | Replay, wrong identity, wrong issuer, expired and concurrent invite redemptions fail with correct audit. |
| EC-10 | Project admin cannot grant an unowned admin/global role despite being able to edit project people. |
| EC-11 | Self-escalation attempt denied or approved only under explicit, independent project workflow policy with trusted evidence. |
| EC-12 | Assignment to protected role is rechecked against policy and scope after a reviewer approves a digest. |
| EC-13 | Two administrators modify a binding concurrently; one conflicting/stale candidate is rejected or rebased correctly. |
| EC-14 | Permission evaluation consumes the same effective revision as the reported role binding; stale/unknown result does not show success. |
| EC-15 | YAML → Form → YAML preserves unexposed role binding properties without silent deletion. |
| EC-16 | YAML → Visual → YAML preserves semantic meaning of group/team associations (or clearly disables unsupported visual edits). |
| EC-17 | A real Git publication succeeds but DB/materialized access projection fails: UI shows published-not-effective with recovery path. |
| EC-18 | SCIM provisioning group deletion or out-of-order event cannot silently widen Ec access or claim reconciliation. |
| EC-19 | Real PostgreSQL constraints/transactions reject duplicate provider identity keys and inconsistent role/target references. |
| EC-20 | Removing project/team/user membership cannot strand the recovery administrator without an approved recovery procedure. |
| EC-21 | Bulk grant/revoke preview lists exact affected IDs, source/scope and result, and rejects changes in between review and commit. |
| EC-22 | Audit events preserve actor/target/action/revision/outcome and never expose invitation tokens, password, session or sensitive claims. |
| EC-23 | Frontend list/matrix/confirmation flows pass keyboard, screen-reader, contrast and dark-mode tests at meaningful viewports. |
| EC-24 | project acceptance evidence includes reproducible commands, actual revision IDs, observed failures, status and security review (not simulated outputs). |

## Execution boundaries

- **81 model methods executed:** Python in-memory state machine; no real Ec YAML parser, PostgreSQL, Git, browser or identity provider.
- **16 selection cases prepared:** not run against an LLM, therefore routing precision, false-positive rates, token cost and effectiveness remain unknown.
- **24 real Ec integration cases prepared:** not run. Role names, grants, identity epochs, independent-review rules and team layout are *synthetic assumptions* and must **never** be inferred as product requirements.
