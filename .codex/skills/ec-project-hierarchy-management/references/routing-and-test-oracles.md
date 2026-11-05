# Routing and Verification Oracles — Ec Project Hierarchy

> Commands, fixture paths and historical execution claims from the source package are illustrative or imported evidence unless the corresponding files are present and independently checked; they are not current test results or bundled executable tools.

These entries are **proposed acceptance/evaluation scenarios**, except for the individually named **synthetic Python tests** recorded as executed only after successful test runner output. A scenario listed here is not by itself evidence of an Ec feature or successful Ec test.

## A. Skill selection: positive and negative (planned agent evaluation)

| Scenario | User request shape | Route | Reason / handoff |
|---|---|---|---|
| SEL-01 | Create a new project with a parent and validate its place in the project tree | SELECT | not schema validation |
| SEL-02 | Move a subproject and preview inherited parameter changes | SELECT | yes |
| SEL-03 | Copy a project subtree, including or excluding historical run data | SELECT | yes; determine policy first |
| SEL-04 | Change a project slug and diagnose links to associated resources | SELECT | yes |
| SEL-05 | Archive a project with active builds and external dependencies | SELECT | yes; lifecycle semantics needed |
| SEL-06 | A Vue router wants a general breadcrumbs component | DO NOT SELECT | vue3-engineering / web-accessibility |
| SEL-07 | A user edits an unrelated YAML pipeline job step | DO NOT SELECT | ec-declarative-resource-schemas / pipeline authoring |
| SEL-08 | Compute an actual end-user effective RBAC permission | DO NOT SELECT | ec-effective-permissions |
| SEL-09 | Review the same project move for access-scope impact | SELECT | permission engine is a separate dependency |
| SEL-10 | Plan a database-only schema migration unrelated to project containment | DO NOT SELECT | postgresql |
| SEL-11 | Implement a pipeline execution dependency DAG | DO NOT SELECT | cicd-pipeline-graph |
| SEL-12 | Implement compare-and-swap Git YAML publication for any resource | DO NOT SELECT | ec-configuration-publication |
| SEL-13 | Diagnose a parent-change race or topology cycle | SELECT | yes |
| SEL-14 | Render a full tree with keyboard and non-drag move actions | SELECT | handoff UI accessibility details |
| SEL-15 | Edit team nesting without changing any project relationships | DO NOT SELECT | ec-people-and-access-administration |
| SEL-16 | Rebuild a cached project subtree index from effective YAML | SELECT | derived data only |

**Agent selection status:** NOT EXECUTED. The target model, prompts, baseline and blind grading have not been supplied.

## B. Synthetic invariant tests (individual test methods, run by Python `unittest`)

The reference model deliberately uses **toy nearest-ancestor parameter resolution and toy grant union**, not the Ec access model. All names below match `validation/test_reference_model.py` verbatim.

| Test | Executable test | Evidence class |
|---|---|---|
| SYN-01 | `test_base_graph_is_valid` | SYNTHETIC EXECUTED (if test log is green) |
| SYN-02 | `test_ancestors_nearest_first` | SYNTHETIC EXECUTED (if test log is green) |
| SYN-03 | `test_root_ancestors_empty` | SYNTHETIC EXECUTED (if test log is green) |
| SYN-04 | `test_descendants_breadth_first` | SYNTHETIC EXECUTED (if test log is green) |
| SYN-05 | `test_leaf_descendants_empty` | SYNTHETIC EXECUTED (if test log is green) |
| SYN-06 | `test_root_and_depth` | SYNTHETIC EXECUTED (if test log is green) |
| SYN-07 | `test_missing_ancestor_lookup_rejected` | SYNTHETIC EXECUTED (if test log is green) |
| SYN-08 | `test_missing_descendants_lookup_rejected` | SYNTHETIC EXECUTED (if test log is green) |
| SYN-09 | `test_nearest_ancestor_parameter_value_and_source` | SYNTHETIC EXECUTED (if test log is green) |
| SYN-10 | `test_root_parameter_inheritance` | SYNTHETIC EXECUTED (if test log is green) |
| SYN-11 | `test_toy_grants_transitive` | SYNTHETIC EXECUTED (if test log is green) |
| SYN-12 | `test_self_parent_rejected` | SYNTHETIC EXECUTED (if test log is green) |
| SYN-13 | `test_missing_parent_rejected` | SYNTHETIC EXECUTED (if test log is green) |
| SYN-14 | `test_direct_cycle_rejected` | SYNTHETIC EXECUTED (if test log is green) |
| SYN-15 | `test_longer_cycle_rejected` | SYNTHETIC EXECUTED (if test log is green) |
| SYN-16 | `test_duplicate_sibling_slug_rejected` | SYNTHETIC EXECUTED (if test log is green) |
| SYN-17 | `test_duplicate_local_parameter_rejected` | SYNTHETIC EXECUTED (if test log is green) |
| SYN-18 | `test_stable_id_key_mismatch_rejected` | SYNTHETIC EXECUTED (if test log is green) |
| SYN-19 | `test_missing_zone_rejected` | SYNTHETIC EXECUTED (if test log is green) |
| SYN-20 | `test_cross_zone_parent_rejected` | SYNTHETIC EXECUTED (if test log is green) |
| SYN-21 | `test_duplicate_reference_rejected` | SYNTHETIC EXECUTED (if test log is green) |
| SYN-22 | `test_configured_depth_bound_rejects` | SYNTHETIC EXECUTED (if test log is green) |
| SYN-23 | `test_invalid_depth_limit_rejected` | SYNTHETIC EXECUTED (if test log is green) |
| SYN-24 | `test_empty_graph_rejected_by_fixture_policy` | SYNTHETIC EXECUTED (if test log is green) |
| SYN-25 | `test_self_move_rejected` | SYNTHETIC EXECUTED (if test log is green) |
| SYN-26 | `test_move_beneath_descendant_rejected` | SYNTHETIC EXECUTED (if test log is green) |
| SYN-27 | `test_root_move_rejected_by_fixture_policy` | SYNTHETIC EXECUTED (if test log is green) |
| SYN-28 | `test_unknown_source_rejected` | SYNTHETIC EXECUTED (if test log is green) |
| SYN-29 | `test_unknown_destination_rejected` | SYNTHETIC EXECUTED (if test log is green) |
| SYN-30 | `test_move_onto_collision_slug_rejected` | SYNTHETIC EXECUTED (if test log is green) |
| SYN-31 | `test_cross_root_rejected_by_fixture` | SYNTHETIC EXECUTED (if test log is green) |
| SYN-32 | `test_stale_base_rejected` | SYNTHETIC EXECUTED (if test log is green) |
| SYN-33 | `test_missing_source_authorization` | SYNTHETIC EXECUTED (if test log is green) |
| SYN-34 | `test_missing_target_authorization` | SYNTHETIC EXECUTED (if test log is green) |
| SYN-35 | `test_missing_actor_rejected` | SYNTHETIC EXECUTED (if test log is green) |
| SYN-36 | `test_missing_policy_revision_rejected` | SYNTHETIC EXECUTED (if test log is green) |
| SYN-37 | `test_move_preview_affected_subtree` | SYNTHETIC EXECUTED (if test log is green) |
| SYN-38 | `test_move_preview_access_added` | SYNTHETIC EXECUTED (if test log is green) |
| SYN-39 | `test_move_preview_access_removed` | SYNTHETIC EXECUTED (if test log is green) |
| SYN-40 | `test_move_preview_parameter_origin_changes` | SYNTHETIC EXECUTED (if test log is green) |
| SYN-41 | `test_preview_path_consumers_flagged` | SYNTHETIC EXECUTED (if test log is green) |
| SYN-42 | `test_preview_preserves_stable_references` | SYNTHETIC EXECUTED (if test log is green) |
| SYN-43 | `test_candidate_digest_changes_for_real_move` | SYNTHETIC EXECUTED (if test log is green) |
| SYN-44 | `test_move_keeps_ids_and_history_pins` | SYNTHETIC EXECUTED (if test log is green) |
| SYN-45 | `test_move_changes_only_parent_edge` | SYNTHETIC EXECUTED (if test log is green) |
| SYN-46 | `test_original_graph_immutable_from_draft_apply` | SYNTHETIC EXECUTED (if test log is green) |
| SYN-47 | `test_revision_increment_after_move` | SYNTHETIC EXECUTED (if test log is green) |
| SYN-48 | `test_denied_if_review_missing` | SYNTHETIC EXECUTED (if test log is green) |
| SYN-49 | `test_denied_if_confirmation_digest_tampered` | SYNTHETIC EXECUTED (if test log is green) |
| SYN-50 | `test_denied_if_preview_impact_tampered` | SYNTHETIC EXECUTED (if test log is green) |
| SYN-51 | `test_denied_if_preview_candidate_tampered` | SYNTHETIC EXECUTED (if test log is green) |
| SYN-52 | `test_denied_if_policy_revision_changed` | SYNTHETIC EXECUTED (if test log is green) |
| SYN-53 | `test_denied_if_actor_changed` | SYNTHETIC EXECUTED (if test log is green) |
| SYN-54 | `test_denied_if_source_permission_revoked` | SYNTHETIC EXECUTED (if test log is green) |
| SYN-55 | `test_denied_if_target_permission_revoked` | SYNTHETIC EXECUTED (if test log is green) |
| SYN-56 | `test_stale_draft_after_competing_move` | SYNTHETIC EXECUTED (if test log is green) |
| SYN-57 | `test_modified_graph_same_revision_rejected` | SYNTHETIC EXECUTED (if test log is green) |
| SYN-58 | `test_noop_move_does_not_bump_revision` | SYNTHETIC EXECUTED (if test log is green) |
| SYN-59 | `test_failing_move_does_not_modify_original` | SYNTHETIC EXECUTED (if test log is green) |
| SYN-60 | `test_toy_access_loss_after_parent_switch` | SYNTHETIC EXECUTED (if test log is green) |
| SYN-61 | `test_depth_limit_rechecked_on_candidate` | SYNTHETIC EXECUTED (if test log is green) |
| SYN-62 | `test_randomized_valid_moves_preserve_graph_axioms` | SYNTHETIC EXECUTED (if test log is green) |
| SYN-63 | `test_randomized_ancestor_descendant_duality` | SYNTHETIC EXECUTED (if test log is green) |
| SYN-64 | `test_deterministic_digest_with_different_mapping_order` | SYNTHETIC EXECUTED (if test log is green) |

## C. Full Ec integration and agent acceptance (not executed)

| Test | Required real-world exercise | Required environment | Status |
|---|---|---|---|
| EC-01 | Load the approved Ec Project YAML schemas and validate example files | Actual Ec repository | NOT EXECUTED |
| EC-02 | Create project by Visual mode; inspect canonical YAML and revision | Actual Ec UI+backend | NOT EXECUTED |
| EC-03 | Create project by Form mode; compare semantic YAML | Actual Ec UI+backend | NOT EXECUTED |
| EC-04 | Create project by YAML; inspect Visual tree and form | Actual Ec UI+backend | NOT EXECUTED |
| EC-05 | Attempt to move a project below its descendant through each API/UI mode | Actual Ec API+UI | NOT EXECUTED |
| EC-06 | Attempt to race two opposite project moves under concurrent publication | Real Git/PG/API | NOT EXECUTED |
| EC-07 | Re-evaluate source/destination authorization when policy changes after preview | Actual Ec permission engine | NOT EXECUTED |
| EC-08 | Compare effective grants for every affected descendant under old/new parent | Actual Ec permission engine | NOT EXECUTED |
| EC-09 | Verify privilege loss does not leave stale cached access grants | Actual Ec permission cache | NOT EXECUTED |
| EC-10 | Verify privilege gain requires authorized publication and reconciliation | Actual Ec permission cache | NOT EXECUTED |
| EC-11 | Verify no secret material moves or leaks as embedded YAML | Actual Ec schema/secret layer | NOT EXECUTED |
| EC-12 | Verify pool/agent capabilities and quotas after reparenting | Actual Ec scheduler/pool rules | NOT EXECUTED |
| EC-13 | Verify repository/template ownership and references after reparenting | Actual Ec resource graph | NOT EXECUTED |
| EC-14 | Verify cross-scope boundary and transfer policy | Approved Ec scope contract | NOT EXECUTED |
| EC-15 | Verify stable project IDs and historical run provenance after move | Actual Ec runtime/provenance | NOT EXECUTED |
| EC-16 | Verify duplicate sibling slugs based on the approved schema | Actual Ec schema | NOT EXECUTED |
| EC-17 | Verify archive/restore with active runs and retention policy | Actual Ec lifecycle | NOT EXECUTED |
| EC-18 | Verify delete/cascade policies and protected references | Actual Ec resource contract | NOT EXECUTED |
| EC-19 | Benchmark adjacency vs ltree vs closure for measured project sizes | Representative PostgreSQL/workload | NOT EXECUTED |
| EC-20 | Test screen-reader navigation and single-pointer alternative to dragging | Actual Ec frontend browser | NOT EXECUTED |
| EC-21 | Replay derived hierarchy projections after a crash/restart | Actual Ec reconciliation | NOT EXECUTED |
| EC-22 | Simulate unavailable Git authority and prevent premature success status | Actual Ec publication API | NOT EXECUTED |
| EC-23 | Confirm links/aliases after rename or project transfer | Actual Ec UI/API | NOT EXECUTED |
| EC-24 | Run actual agent-based skill routing evaluation with control cohort | Agent model and evaluation harness | NOT EXECUTED |

## D. Evaluation and risk controls

For the actual Ec rollout, record each scenario under a concrete approved project acceptance ID. Run the same tasks with and without the skill; grade authorization correctness, semantic preservation, hierarchy-cycle rejection, provenance, UI feedback and source citations before comparing token use.

Safety-critical gates: no cross-scope access grant from a stale preview; no false success acknowledgment; no YAML bypass; no historical run revision overwrite; no automatic destructive cascade without an approved policy. If no access-control policy is available, mark the scenario blocked rather than inventing one.

The synthetic digest is *not* a signed/authenticated approval token. Test booleans are *not* a real access-control engine. Database and Git atomicity are outside the Python model.
