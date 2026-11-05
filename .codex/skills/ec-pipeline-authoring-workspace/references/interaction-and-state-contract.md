# Pipeline Authoring Workspace — Interaction and State Contract

> Use the destination's existing requirements and evidence format; named workflow documents are examples, not prerequisites. Optional SDD mapping is configured outside this skill. Commands, fixture paths and historical execution claims from the source package are illustrative or imported evidence unless the corresponding files are present and independently checked; they are not current test results or bundled executable tools.

**Status:** an engineering pattern and test fixture, **not** an approved Ec feature specification. Field names and policies below are abstract examples. Confirm exact resource kinds, operations and requirements with project workflow and code.

## Four layers of state

| Layer | Canonical authority | Examples | Undo semantics |
|---|---|---|---|
| Desired configuration | Effective, versioned Ec YAML | Declared pipeline resource and dependencies | A local draft undo does not undo publication |
| Valid local editing draft | One validated, complete resource model projected to modes | Stable node identities, edges, supported extension fields | Local command/snapshot undo/redo only |
| Invalid text buffer | Ephemeral YAML editor text, never authoritative | Syntax error with line/column and last-good snapshot | Explicit repair or discard; never silently overwritten |
| Presentation/runtime | Local UI state; backend operational status for real runs | Canvas viewport/selection/focus; backend validation/commit state | Selection/pan do not join semantic undo history |

## Typed command flow

`UI gesture -> modality adapter -> typed command (stable IDs + baseVersion + exact intention) -> authorization hint + graph/schema validation delegates -> apply to full draft or reject -> semantic history -> derive Form/Visual/YAML projections.`

A *client authorization hint* is not a protected-action authorization check. The server still rechecks permission before persistence, using published revisions and authorized identity.

Recommend explicit command outcomes: `applied`, `rejected`, `conflict`, `requires-decision`. On rejection, preserve **all** model fields, history, selection and unresolved buffer. Map diagnostics to the affected stable ID and logical resource path; provide line/column when the parser supplies them.

### State invariants and intentionally unresolved choices

- **Invariant:** visual position is not dependency precedence. `source -> target` semantics must be determined by the approved resource contract, not the screen's x/y position.
- **Invariant:** viewing, selecting, zooming and panning are not YAML edits. Persisting layout is possible **only if** project workflow/schema explicitly makes it part of desired state.
- **Invariant:** disabled/unknown field projection never erases its source value; mutation overlays onto a complete draft.
- **Invariant:** bad YAML text does not replace last-good model or trigger publication; switching tabs does not destroy text.
- **Invariant:** no local editor event changes a server-confirmed `published/synced` status without an authentic backend response.
- **Invariant:** a rejected command is atomic and has no history or data side effects.
- **Unresolved:** draft persistence method, bulk-operation transaction scope, edge handles, preview semantics, advanced field mapping and keyboard shortcuts; project workflow owns each choice.

## Interaction parity checklist

| User intent | Canvas pointer | Keyboard | Single pointer without drag | Structured fallback |
|---|---|---|---|---|
| Add node | Palette click or drag | Search/select palette and activate Add | Click Add then choose target | Form-based Create node |
| Connect nodes | Drag handle if offered | Select source then target via control | Click source connector then target connector | Form edge selector (from/to) |
| Reorder nodes | Drag if semantically supported | Move action + arrow/button | Up/Down buttons or menu | Ordered list editor (only where order matters) |
| Edit node | Inspector on selection | Focus node + open inspector | Click inspector action | Full form or YAML |
| Delete node | Delete action | Focus + Delete control | Click Remove + impact preview | Structured list action |
| Auto-layout | Toolbar | Toolbar keyboard activation | Toolbar click/tap | Not required for semantic editing |
| Navigate graph | Pan/zoom controls | Focusable buttons and node navigation | Pan/zoom buttons | Structured graph outline |

**Important:** WCAG keyboard access and no-drag pointer alternatives are independent criteria. A keyboard-only alternative is not sufficient for WCAG 2.5.7 unless the underlying function also has a single-pointer without-drag route. A `treegrid` ARIA pattern is not inherently appropriate for a DAG (a DAG is not necessarily a tree).

## UX, accessibility and graph visualization

For every node type, document: semantics, visible text, accessible name, status vocabulary, inspector fields, valid connection directions, selection/focus behavior, error reason and equivalent nonvisual controls. For an edge, expose source/target labels, conditions, and supported update actions. Labels and status must remain comprehensible in both themes and at high zoom.

Graph readability is a **measured hypothesis**. Proposed fixture metrics include number of crossings, node overlap, edge path length, aspect ratio, task completion, navigation effort, semantic comprehension errors, focus visibility, and render latency. The design must not trade away correct task meaning to optimize visual metrics. Moody (2009) is abstract-only in this research; use Munzner (2009), Mackinlay (1986), Gansner et al. (1993) for examined methodologies and Purchase (1997) only at its abstract-supported level.

For responsive modes, provide a list/outline representation preserving full authoring operations, not a screenshot-like reduced canvas. Test at both 320 CSS px reflow conditions **and** real viewport/device presets; the 2D layout exception in WCAG 1.4.10 does not exempt surrounding panels or keyboard alternatives.

## Verification layers and metrics

1. **Component contract:** controlled graph event handling, command injection, immutable snapshots, stable IDs, advanced fields and focus destinations.
2. **Synthetic model:** use `validation/editor_model.ts` and `validation/test_editor_model.cjs` to exercise command/state invariants with fake parser and graph validator. It never parses real Ec YAML.
3. **Real integration (pending):** Vue/TypeScript tests with actual graph/editor libraries, resource schema and API, browser automation, keyboard-only, pointer-only, screen-reader checks, and concurrent revision handling.
4. **User study (pending):** preregister representative pipeline-editing tasks; report time, errors, success rate and confidence intervals for target developer/operator cohorts. No fabricated aesthetic score or benchmark.

Proposed test intents are detailed in [routing and test oracles](routing-and-test-oracles.md). Thresholds for productivity, graph size, latency and accessibility exceptions belong to the approved feature contract, not this skill.
