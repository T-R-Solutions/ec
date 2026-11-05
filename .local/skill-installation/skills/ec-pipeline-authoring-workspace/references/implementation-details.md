# Detailed decisions and failure cases

Read only for a decision needing more detail than the entry point. Apply the relevant steps to the
actual task; the sections are not a mandatory artifact or test checklist. Use existing project
contracts in their actual format. File names such as spec.md, plan.md or tasks.md are examples, not
required dependencies. Other skills are optional expertise, never enforcement mechanisms.

Product rules, stack choices and authority locations must be verified in the Ec consumer. Examples
do not establish deployed capabilities or override its current contract.

## Procedure

1. **Inspect the real contract first.** Locate approved `spec.md`, `plan.md`, `tasks.md`, existing
   Vue/TypeScript components, schema version, APIs, permissions and the current canonical YAML
   revision. Inventory supported node/edge types and actions from evidence; **do not infer an Ec
   schema from a competitor's workflow format or from example fixtures**. Record each capability as
   specified, observed, proposed or unknown. If a safety-critical edit lacks policy, return an
   project workflow decision request.
2. **Build a capability and interaction matrix.** For each action (create, connect, edit, remove,
   duplicate, group, reorder, inspect, validate, undo, publish intent), record its domain command,
   required data, graph/permission validation delegate, keyboard path, single-pointer-without-drag
   path, form/YAML fallback, narrow-screen route, focus destination, and error explanation. Separate
   DAG authoring from execution monitoring. A visual canvas may be unavailable or read-only for
   unsupported semantic constructs; preserve Form/YAML access without silently dropping fields.
3. **Route all semantic edits through one command boundary.** Convert palette, pointer, keyboard,
   inspector and form actions into **the same typed command** with stable resource/node/edge IDs,
   base draft version, affected paths and operation identity. Validate via the existing schema,
   authorization and graph services before applying. Return `applied`, `rejected`, `conflict` or
   `requires-decision`, with actionable diagnostics. Never let canvas-library default mutations
   become authoritative silently. For a chosen graph library, verify its controlled-change API and
   version; do not assume specific package support or automatic validation.
4. **Keep presentation separate from desired configuration.** Viewport, pan/zoom, selections,
   pointer handles, temporary layout, inspector width and focus belong to local presentation state
   unless the *approved* schema explicitly says otherwise. Nodes and edges shown on the canvas are
   projections of the same validated draft used by Form and YAML; do not create independent editors
   or watchers that copy states in circles. Keep invalid YAML text in an isolated buffer; preserve
   the last valid draft, show errors, and require an explicit resolution before a conflicting
   semantic edit can overwrite it. Delegate formal round-trip correctness to
   `ec-semantic-configuration-projections`.
5. **Design reversible, conflict-aware interactions.** Use semantic command transactions or
   immutable snapshots to implement undo/redo of *local, validated draft edits*; never pretend an
   undo rolls back an already published revision. Exclude mere selection/viewport changes from
   semantic history. Handle delete previews (incident edges, references, downstream impact), edit
   conflicts against a changed base, bulk operations, cancel, and explicit confirmation for
   irreversible actions. Do not erase unrenderable advanced/extension fields during any inspector or
   palette action.
6. **Make graph meaning legible, not decorative.** Choose direction, layout and node visual
   encodings according to the supported task vocabulary. Preserve node identities and graph
   semantics under automatic layout; crossings, bends, compactness and stability are competing
   heuristics, not guarantees. Differentiate node categories by labels/shape and status by text plus
   symbols, not color alone. Keep zoom, minimap and auto-layout as navigational conveniences; never
   encode execution order in screen coordinates. Benchmark representative sparse, dense and large
   graphs before asserting a library's performance.
7. **Provide distinct accessible alternatives.** WCAG 2.2 SC **2.1.1** requires keyboard
   operability; SC **2.5.7** independently requires a *single-pointer alternative without dragging*.
   Keyboard-only drag alternatives do not necessarily satisfy 2.5.7. Supply accessible
   add/connect/reorder/remove via structured list/inspector/form commands, operable by click/tap and
   keyboard, with clear focus, labels, validation summaries and non-obscured focused controls. Use
   an appropriate ARIA pattern only after matching its semantics; a graph is not automatically a
   `treegrid`. For mobile, keep a usable structured editing route rather than shrinking the entire
   canvas.
8. **Separate pending work from confirmed state.** Model draft editing, validation progress and
   backend publication/reconciliation as separate truth domains. A local click is never proof of
   saved/published/synced/executed state. Display backend acknowledgements and revision IDs as
   observed; stale or disconnected data must not appear successful. UI permissions are advisory;
   protected API and publication operations must authorize independently.
9. **Validate across four layers.** (a) Contract and command tests: stable IDs, hidden fields, stale
   revisions, malformed YAML isolation, edge rejection and undo/redo; (b) interaction tests:
   pointer, keyboard, no-drag pointer, error focus, screen reader and responsive alternatives; (c)
   visual/usability evaluation: task completion, time/errors and trace comprehension with fixed user
   cohorts and graph fixtures; (d) runtime/performance: memory, long-task/interaction latency, large
   graph update cost and render regression with reproducible hardware/browser fixtures. Record which
   tests were *executed*, which were *designed only*, and which cannot run without the Ec
   repository.


## Further references by decision

Read only the reference relevant to the unresolved decision. Research registers describe imported authorship, not current execution.

- [Pipeline Authoring Workspace — Interaction and State Contract](interaction-and-state-contract.md): interaction and state contract.
- [Routing, Test Oracles and Evidence Expectations](routing-and-test-oracles.md): routing and test oracles.
- [Source Evidence Ledger — Ec Pipeline Authoring Workspace (2026-10-31)](sources-and-decisions.md): source provenance or a disputed recommendation.
