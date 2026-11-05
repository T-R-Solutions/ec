# Source Evidence Ledger — Ec Pipeline Authoring Workspace (2026-10-31)

> Imported authoring record. Research dates, consultations and execution claims below belong to the supplied source package; they were not reproduced in this adaptation. Referenced validation scripts, fixtures, product norms and logs are not bundled unless present in this folder. Verify their availability and provenance before use. No historical PASS is current acceptance.

This is a **curated, bounded review of relevant primary materials**, not a claim to have read every existing publication. The two original Ec norms and the approved SDD, if supplied for a feature, outrank examples in external vendor documentation. No forums, Reddit or unreviewed opinion pieces were used as engineering authorities.

**Evidence grades:** `O-full` = relevant official document inspected directly; `A-sections` = peer-reviewed author/publisher full paper examined in relevant sections; `A-abstract` = only abstract/bibliographic record examined; `B-catalog` = book listing consulted, book content not read. The grade does not rank scientific validity; it reports **access depth**.

## Original Ec authorities (examined in full)

- `EC-REGRAS-DE-DESIGN-E-PRODUTO.md`: product/architecture, canonical YAML, mode parity, graph editor scope, accessibility, operations and acceptance requirements.
- `EC-REGRAS-CRIACAO-DE-TELAS-E-IMAGENS.md`: functional visual language, DAG workspace, inspector, alternative editing modes, response to invalid YAML, responsive/accessibility and realistic screen states.
- These files remain in Portuguese; the skill links to them **as authorities** rather than copying their rules into an English policy fork.

## Official standards, framework and vendor documentation

| ID | Source and stable link | Access | Exact contribution to method |
|---|---|---|---|
| O01 | [W3C WCAG 2.2](https://www.w3.org/TR/WCAG22/) | O-full (relevant criteria) | Normative success criteria baseline; not proof of browser conformance. |
| O02 | [W3C 2.1.1 Keyboard](https://www.w3.org/WAI/WCAG22/Understanding/keyboard) | O-full | All functional graph edit actions need a keyboard route. |
| O03 | [W3C 2.5.7 Dragging Movements](https://www.w3.org/WAI/WCAG22/Understanding/dragging-movements) | O-full | A separate single-pointer **without-drag** alternative is necessary for dragging functions; keyboard alone need not satisfy it. |
| O04 | [W3C 2.5.8 Target Size](https://www.w3.org/WAI/WCAG22/Understanding/target-size-minimum) | O-full | At least 24x24 CSS px or a documented exception; do not invent 44px as the AA requirement. |
| O05 | [W3C 1.4.10 Reflow](https://www.w3.org/WAI/WCAG22/Understanding/reflow) | O-full | 2D layout exception is scoped to parts that require 2D meaning; surrounding authoring tasks still need accessible reflow/alternatives. |
| O06 | [W3C 2.4.11 Focus Not Obscured (Minimum)](https://www.w3.org/WAI/WCAG22/Understanding/focus-not-obscured-minimum) | O-full | Inspector/palette/zoom layouts must not entirely obscure focused controls. |
| O07 | [WAI-ARIA Authoring Practices: Tree View](https://www.w3.org/WAI/ARIA/apg/patterns/treeview/) | O-full | Tree keyboard conventions **only if** the alternate structure is actually a tree. |
| O08 | [WAI-ARIA Authoring Practices: Treegrid](https://www.w3.org/WAI/ARIA/apg/patterns/treegrid/) | O-full | Focus and cell interaction guidance; a general DAG is **not** automatically a treegrid. |
| O09 | [MDN aria-live reference](https://developer.mozilla.org/en-US/docs/Web/Accessibility/ARIA/Reference/Attributes/aria-live) | O-full | Use low-disruption announcements for edit/error notifications; test with assistive technology. |
| O10 | [Vue 3 Computed Properties](https://vuejs.org/guide/essentials/computed) | O-full | Pure, read-only derived views; never mutate computed projections to change desired config. |
| O11 | [Vue 3 Watchers](https://vuejs.org/guide/essentials/watchers) | O-full | Watchers for side effects and stale-request cleanup, not independent mode sync loops; APIs vary by Vue minor version. |
| O12 | [Vue 3 List Rendering](https://vuejs.org/guide/essentials/list) | O-full | Stable `key` values needed for stateful reordered nodes. |
| O13 | [Vue 3 TypeScript Composition API](https://vuejs.org/guide/typescript/composition-api) | O-full | Strongly typed props, emits and events. |
| O14 | [Vue 3 Performance Guide](https://vuejs.org/guide/best-practices/performance) | O-full | Profile real render updates, stable props and large subtrees before claiming optimization. |
| O15 | [TypeScript Narrowing and `never` exhaustiveness](https://www.typescriptlang.org/docs/handbook/2/narrowing.html) | O-full | Discriminated command/result unions with compile-time exhaustive handlers. |
| O16 | [MDN Pointer Events](https://developer.mozilla.org/en-US/docs/Web/API/Pointer_events) | O-full | Handle pointer capture/cancellation for drag when used, without making it the sole editing mechanism. |
| O17 | [MDN Container Queries](https://developer.mozilla.org/en-US/docs/Web/CSS/Guides/Containment/Container_size_and_style_queries) | O-full | Responsive inspector/palette layout based on available component area. |
| O18 | [Vue Flow Guide](https://vueflow.dev/guide/) | O-full | Example candidate canvas library; supports custom nodes/edges, pan/zoom/selection; **not** approved Ec dependency. |
| O19 | [Vue Flow Controlled Flow](https://vueflow.dev/guide/controlled-flow.html) | O-full | By default it applies changes automatically; explicit controlled handling is needed when validating changes first. This documented API warns of next-major-release change. |
| O20 | [Vue Flow Graph Utilities](https://vueflow.dev/guide/utils/graph.html) | O-full | Utility surface is library-specific and not a verified Ec semantic validator. |
| O21 | [Graphviz dot layout](https://graphviz.org/docs/layouts/dot/) | O-full | Directed hierarchical layout heuristics; layout quality/performance remains benchmark-dependent. |
| O22 | [Qt QUndoStack](https://doc.qt.io/qt-6/qundostack.html) | O-full | Independent command/undo semantics reference; Qt is **not** an Ec dependency. |
| O23 | [GitLab CI `needs`](https://docs.gitlab.com/ci/yaml/needs/) | O-full | Domain comparison showing dependency relations differ from stage grouping. **Do not import its schema into Ec.** |
| O24 | [YAML 1.2.2](https://yaml.org/spec/1.2/) | O-full via Ec norms and prior schema skill; external syntax authority | YAML parser/representation concerns are delegated to the resource-schema and projection skills. |

## Peer-reviewed papers and book evidence

| ID | Work (citation / stable link) | Access | Supported contribution / constraint |
|---|---|---|---|
| A01 | Munzner (2009), *A Nested Model for Visualization Design and Validation*, IEEE TVCG 15(6), 921–928. [Author PDF](https://www.cs.ubc.ca/labs/imager/tr/2009/NestedModel/NestedModel.pdf) | A-sections (introduction, four levels, validation threats) | Evaluate domain/tasks, abstraction, visual encoding/interactions and algorithms at *different levels*. Good layout alone proves none of the upstream requirements. |
| A02 | Brehmer & Munzner (2013), *A Multi-Level Typology of Abstract Visualization Tasks*, IEEE TVCG 19(12), 2376–2385. [Author preprint](https://www.cs.ubc.ca/labs/imager/tr/2013/MultiLevelTaskTypology/brehmer_infovis13.pdf) | A-sections (abstract, taxonomy, task motivation and input/output) | Specify what edit users achieve, why, and how, before choosing UI gesture. |
| A03 | Gansner, Koutsofios, North & Vo (1993), *A Technique for Drawing Directed Graphs*, IEEE TSE 19(3), 214–230. [Full article PDF](https://www.graphviz.org/documentation/TSE93.pdf) | A-sections (layout phases, aesthetic conflicts) | Layered directed layout minimizes selected costs heuristically; direction and crossings matter, but layout position must not define execution semantics. |
| A04 | Mackinlay (1986), *Automating the Design of Graphical Presentations of Relational Information*, ACM TOG 5(2), 110–141. [Author-era republished full text](https://xeroxparcarchive.computerhistory.org/indigo/imaging/documentationgraphicscourse/.automatedIdesign.tioga%217.html) | A-sections (expressiveness/effectiveness/graphic design rules) | Visual notation must **express the required data** before optimizing aesthetics. A hidden semantic dependency is an expressiveness failure. |
| A05 | Shneiderman (1983), *Direct Manipulation: A Step Beyond Programming Languages*, IEEE Computer 16(8), 57–69. [Author-hosted full PDF](https://www.cs.umd.edu/~ben/papers/Shneiderman1983Direct.pdf) | A-sections (representation, incremental and reversible interactions) | Motivation for incremental, observable, reversible draft-edit actions; not evidence that drag interaction should be mandatory. |
| A06 | Purchase (1997), *Which Aesthetic Has the Greatest Effect on Human Understanding?*, Graph Drawing, LNCS 1353, 248–261. [University repository abstract](https://eprints.gla.ac.uk/35804/) | A-abstract **only; full text unavailable** | Abstract reports edge crossings as an important factor for task comprehension. Do **not** generalize effect size or significance to Ec without study. |
| A07 | Moody (2009), *The “Physics” of Notations*, IEEE TSE 35(6), 756–779. [DOI / bibliographic record](https://doi.org/10.1109/TSE.2009.67) | A-abstract/bibliographic **only** | Rationale for cognitively effective visual notation; do not claim to have verified its full nine-principle framework. |
| A08 | Sugiyama, Tagawa & Toda (1981), *Methods for Visual Understanding of Hierarchical System Structures*, IEEE TSMC 11(2), 109–125. [DOI](https://doi.org/10.1109/TSMC.1981.4308636) | A-abstract **only** | Historical basis for layered layout and crossing-reduction heuristics; no reproduced benchmark or implementation details. |
| B01 | Munzner (2014), *Visualization Analysis and Design*, CRC Press. [Publisher catalog](https://www.cs.ubc.ca/~tmm/vadbook/) | B-catalog **only; author-hosted chapter list read; no book chapters read** | Further reading, no factual claim depends on unread content. |

## Derived engineering decisions (proposals, not product requirements)

- **D01 (O02–O08):** Every meaningful graph mutation needs a keyboard path **and** an independently testable single-pointer-without-drag route; use structured controls as the shared backstop.
- **D02 (O10–O15, O19, A05):** Route visual and form gestures through typed semantic commands; never accept uncontrolled component mutations as canonical state.
- **D03 (O10–O14, A01, A04):** Separate semantic draft, invalid YAML text, transient view state and observed backend state; preserve hidden extensions in the complete draft.
- **D04 (O21, A01–A04, A06):** Graph layout is a readability optimization, not validation and not execution scheduling; test crossings and human outcomes separately.
- **D05 (O22, A05):** Undo/redo is scoped to validated draft history; publication rollback is owned by another skill.
- **D06 (O01–O09, O17):** Mobile structured fallback and explicit focus/navigation/announcement checks are required to assess actual authoring accessibility; static screenshots cannot prove compliance.
- **D07 (O14, A01, A02):** Do not invent performance thresholds; preregister representative tasks/graph sizes and measure performance in real browsers.
- **D08 (O23, O24):** Treat competitors and external libraries as *examples* only. Ec's YAML schema remains its own approved SDD contract.

## SDD decisions still required

The product's exact node/edge classes, semantic order vs visual order, operation permissions, connection rules, grouping behavior, layout persistence, accessible control labels, scale targets, latency budget, autosave draft policy, cross-mode edit behavior during syntax errors, undo transaction boundaries, branch conflict resolution, API validation interface, alternative view's semantic scope and telemetry requirements are **not established by this source review**. They belong to approved Ec specifications. No real Ec repository/feature SDD was supplied for execution.
