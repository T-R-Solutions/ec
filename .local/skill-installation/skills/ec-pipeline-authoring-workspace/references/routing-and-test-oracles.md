# Routing, Test Oracles and Evidence Expectations

> Use the destination's existing requirements and evidence format; named workflow documents are examples, not prerequisites. Optional SDD mapping is configured outside this skill. Commands, fixture paths and historical execution claims from the source package are illustrative or imported evidence unless the corresponding files are present and independently checked; they are not current test results or bundled executable tools.

This file supplies **proposed activation and verification scenarios**. It is not evidence that an agent or the Ec application has passed a test. Numbers below denote cases planned or present in the synthetic test model.

## Skill selection scenarios

| ID | Task | Expected selection | Why |
|---|---|---|---|
| R01 | Implement an editable Vue pipeline DAG workspace | Activate | Canvas, inspector, interaction state |
| R02 | Provide non-drag connection creation | Activate | Alternative interaction surface |
| R03 | Add keyboard navigation to graph nodes and inspector | Activate | Keyboard authoring path |
| R04 | Maintain selection/viewport independently of draft YAML | Activate | Presentation vs semantic state |
| R05 | Implement local graph draft undo/redo | Activate | Authoring reversible commands |
| R06 | Explain a cycle detection algorithm | Do not activate | `cicd-pipeline-graph` |
| R07 | Define `kind: Pipeline` YAML schema fields | Do not activate | `ec-declarative-resource-schemas` |
| R08 | Prove Form/YAML lens laws | Do not activate | `ec-semantic-configuration-projections` |
| R09 | Publish approved YAML with compare-and-swap | Do not activate | `ec-configuration-publication` |
| R10 | Schedule jobs on agents | Do not activate | `cicd-scheduler` / runtime |
| R11 | Build a generic Vue select dropdown | Do not activate | `vue3-engineering` / design system |
| R12 | Validate reference preservation during inspector edits | Activate alongside projection skill only if needed | UI command + cross-mode invariants |
| R13 | Show live build status on existing DAG | Do not activate for authoring alone | Run monitoring skill |
| R14 | Compare Graphviz and other layout strategies | Activate for layout usability integration | Not new graph scheduler |
| R15 | User asks what an unapproved stage type should do | Block pending project workflow | Do not invent domain semantics |
| R16 | Evaluate 400-node canvas benchmark | Activate if workspace performance | Require actual metrics, not assertion |
| R17 | Investigate inaccessible drag-only edge editing | Activate | Keyboard and single-pointer alternative both required |
| R18 | Provide responsive structured editor fallback | Activate | Preserve operations on small viewports |

## Synthetic contract cases (testable with TS model)

- **T01–T10:** stable IDs, create/update/remove/connect, duplicate commands, missing references, injected DAG validator failure, explicit destructive impact acknowledgement.
- **T11–T20:** undo/redo restoration, redo invalidation, rejected command non-mutation, selection/viewport independence, preserving hidden advanced fields, stale local versions.
- **T21–T30:** invalid YAML buffer isolation, mode switching, explicit discard, repaired text, client guard denials, no backend success on local clicks.
- **T31–T40:** equivalent pointer/keyboard/single-pointer semantic actions, alternative route metadata, backend event revision matching, stale ack rejection.
- **T41–T50:** error mapping and focus path, unsupported/unknown field retention, multi-step history scenarios, targeted edge removal and connection edits.

The executable suite may contain additional generated instances; final counts must come from observed test-run output, never from this plan.

## Ec integration tests pending source checkout

1. Approved resource schema and full serialization, including extension/unknown fields.
2. Source-aware YAML editing with comments/anchors and syntax errors; test parser/editor actual capabilities.
3. Render from YAML, edit through canvas and form, serialize and compare meaning to approved project contract contract.
4. Pointer, keyboard, **single-pointer-without-drag** parity for creation/connection/deletion/reordering.
5. WCAG 2.2 AA: accessible names, focus order/visibility, 24x24 target exception accounting, screen reader, reduced motion.
6. Graph library controlled event mode, undo/redo after drag and inspector edit, escape/cancel, and distinct operation monitoring view.
7. Performance at representative 10/100/500/2000-node and sparse/dense graph fixtures; these sizes are **test inputs**, not Ec contractual limits.
8. Viewport at 320 CSS px / 400% zoom and realistic mobile/tablet sizes, testing structured fallback.
9. Server status confirmation, revision conflicts, disconnected state and safe permission rejection.
10. Real multi-operator study for task completion, error rate, time and graph comprehension; thresholds from approved project contract only.
11. Browser integration tests on versions explicitly pinned in the real frontend repository.
12. Agent selection A/B tests with and without this skill; measure completion quality, citations and token usage rather than presuming benefit.

No Ec source repository, approved feature contract or UI environment was provided for this package. Each item above is **unexecuted**.
