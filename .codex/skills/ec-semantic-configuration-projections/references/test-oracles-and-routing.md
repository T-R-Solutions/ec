# Selection Cases and Planned Test Oracles

> Use the destination's existing requirements and evidence format; named workflow documents are examples, not prerequisites. Optional SDD mapping is configured outside this skill.

**All scenarios in this document are PLANNED. NONE has been run against the Ec application, its UI, an LLM agent, or a reference implementation.** They are design-level acceptance candidates: verify names, schema fields, permitted operations and test tools against the actual code and approved specs before execution. Expected outcomes are **oracles to test**, not observed pass results.

## Selection routing: 12 cases

| ID | Request or symptom | Expected selection and boundary |
|---|---|---|
| SEL-01 | Switching from Visual to Form drops an advanced timeout field. | **Select this skill**: lossy projection and hidden-complement preservation. |
| SEL-02 | Editing an edge in the DAG does not change the YAML dependencies. | **Select**: view-to-source edit reflection; canvas layout behavior belongs elsewhere. |
| SEL-03 | An invalid YAML buffer causes the canvas to replace a valid draft. | **Select**: isolation of invalid buffer and validated draft. |
| SEL-04 | A project form displays only fields it knows, then deletes plugin extension fields. | **Select**: supported extension preservation and representability. |
| SEL-05 | A form edit based on an old revision overwrites an unrelated newer field. | **Select**: edit preconditions and conflict-safe projection; publication is separate. |
| SEL-06 | A schema needs a new required field and version migration. | **Do not select** as primary: `ec-declarative-resource-schemas`. Select this only if projections must adapt to the approved change. |
| SEL-07 | A deployment approval is marked published before server confirmation. | **Do not select**: `ec-configuration-publication` and operational status/authorization. |
| SEL-08 | A Vue button's color and padding are inconsistent. | **Do not select**: design-system/Vue work, not semantic projections. |
| SEL-09 | A Rust runner loses heartbeat messages. | **Do not select**: agent/control protocol or CI runtime. |
| SEL-10 | A drag-and-drop alternative modifies a job but drops `retry`. | **Select** for semantic retention; accessibility/design skill may independently review interaction. |
| SEL-11 | A YAML editor contains an uncommented invalid indentation only; no cross-mode behavior is implicated. | **Do not select** solely for syntax: schema/parser diagnostics or editor implementation. |
| SEL-12 | A visual form supports a field of a different `kind` and writes it to this resource. | **Select**: capability gating and cross-resource/version projection correctness; schema owner supplies field applicability. |

## Planned technical cases: 36

Fixtures are hypothetical and should be mapped to real resource schemas before execution. “Preserve” always means retain the approved **semantic** information; presentation fidelity has separate oracles.

| ID | Fixture / action | Expected oracle |
|---|---|---|
| T-01 | Valid typed resource → Visual → no changes → YAML | **GetPut:** resource semantics unchanged, including unshown fields. |
| T-02 | Valid typed resource → Form → no changes → YAML | **GetPut:** same semantics; defaults not spuriously materialized. |
| T-03 | Edit a supported scalar in Form → YAML → Form | **PutGet:** final visible value equals accepted edit. |
| T-04 | Edit a supported DAG edge in Visual → YAML → Visual | Supported dependency change reflects; DAG remains valid. |
| T-05 | YAML contains advanced retry/timeout values; edit job name in Visual | Advanced values and agent references are retained. |
| T-06 | YAML contains a permitted extension namespace; edit unrelated Form field | Approved extension data retained exactly in semantic meaning. |
| T-07 | YAML contains an unapproved extra field | Schema owner rejects as specified; projection does not silently discard it. |
| T-08 | Field absent vs explicitly `null`, under a schema where they differ | Edit preserves the distinction; no guessed default. |
| T-09 | Field is `false`, `0`, `""`, or `[]` | Projection does not conflate false/zero/empty with missing. |
| T-10 | Two equivalent mappings with different key order | Equal resource semantics where contract has no order; no false DAG ordering. |
| T-11 | Ordered YAML sequence is reordered through a supported control | Required semantic ordering changes correctly; unrelated items stable. |
| T-12 | Unmodified YAML contains comments in edited subtree | Source-aware editor preserves comments or explicitly previews unavoidable diff. |
| T-13 | YAML uses meaningful quotes/style and is rewritten | Presentation changes reviewed separately, never labeled lossless without proof. |
| T-14 | YAML uses anchors/aliases supported by parser and resource contract | Resolved semantics preserved; unsupported source-aware rewrite is blocked or diffed. |
| T-15 | Unsupported visual field exists in an otherwise renderable resource | Field marked not-renderable-preserved; unrelated visual edit safe. |
| T-16 | Unsupported visual operation requires dropping hidden fields | Operation disabled or rejected with a specific reason. |
| T-17 | YAML text becomes syntactically invalid while typing | Raw buffer remains; last valid semantic draft and published state unchanged. |
| T-18 | Switch from dirty invalid YAML to Form and back | Invalid text not erased without explicit user action; Form does not falsely show it as applied. |
| T-19 | Invalid YAML is corrected into a valid draft | Supported views update from validated draft exactly once, not via looping watchers. |
| T-20 | Form operation based on stale draft revision | Conflict reported; no silent last-write-wins. |
| T-21 | Concurrent independent edits to same resource | No silent overwrite; merge behavior only when specified and verified. |
| T-22 | Undo a supported Form field change | Prior semantic value restored; unrelated YAML fields untouched. |
| T-23 | Redo after Undo | Accepted change reapplied without duplication or dropping hidden fields. |
| T-24 | Reorder jobs, then edit one job by stable ID | Correct job modified despite changed sequence index. |
| T-25 | Rename a job referenced by other nodes | References remain valid or edit rejected with actionable explanation. |
| T-26 | Add a DAG edge that creates a cycle | Rejected prior to applying effective draft; error is localizable. |
| T-27 | Remove or rename a referenced project/agent/pool | No silently dangling reference; validate against approved resource contract. |
| T-28 | User lacks permission for a projected action | UI does not promise success; backend enforcement tested independently. |
| T-29 | A visual control applies an edit via keyboard | Equivalent semantic change and feedback, not merely focus navigation. |
| T-30 | Drag action has single-pointer non-drag alternative | Equivalent semantic change; WCAG 2.5.7 and keyboard access checked separately. |
| T-31 | Switch between light/dark themes or viewport sizes while editing | Semantic draft and dirty text preserved; presentation reviewed by UI owner. |
| T-32 | A noneditable view displays a partial projection | Missing capabilities explicitly disclosed; full YAML remains accessible. |
| T-33 | YAML expression/reference token is syntactically valid but unrecognized by a view | Expression retained untouched or edit blocked; never coerced to a literal guessed value. |
| T-34 | Edit resource kind/version while another view is open | Projection recalculates capabilities; stale editing is blocked; schema routing remains source of truth. |
| T-35 | User clicks Save/Publish from a projection-based editor | UI differentiates local draft, backend confirmation, and publication; actual lifecycle checks delegated. |
| T-36 | Generate hundreds of valid/invalid data fixtures and apply random edit sequences | Assert defined GetPut/PutGet/preservation and collect minimal counterexamples; generator limits and seed reported, no invented coverage. |

## How to execute after adoption

1. Inspect the actual Ec schemas and pick a representative set of resource kinds and versions (e.g., Project, Pipeline, access policy, plugin configuration, agent pool **if these kinds actually exist**). Replace hypothetical field names with real ones.
2. Build independent test fixtures for valid, invalid, extension-rich, and presentation-rich YAML. Pin library/dependency versions and test seeds.
3. Test **pure projection logic** first (typed resource → projection → typed operation), then Vue component behavior, then actual browser interaction and backend acceptance as applicable.
4. Run property-based generators against well-defined domains, not arbitrary YAML that the Ec schema forbids. Shrink counterexamples when supported by the installed harness.
5. Record commands, versions, seeds, environment, diffs, exact results and failures in the existing project workflow evidence mechanism. Do not declare a pass from the table of expected outcomes.
6. Review all cases involving authorization, published revisions and filesystem persistence with the skills that own those parts; this matrix does not replace their oracles.

## Scoring a future LLM skill evaluation

Use a blinded or consistently configured task suite to compare **with-skill** vs **without-skill** behavior. Score positive routing, negative routing, preservation, invalid draft isolation, conflict reporting, evidence honesty and token cost. Report denominators and raw failed cases; do not combine a score with unrun test cases. A passing document checker or a convincing narrative is not proof of agent behavior.
