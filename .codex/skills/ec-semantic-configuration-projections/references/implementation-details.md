# Detailed decisions and failure cases

Read only for a decision needing more detail than the entry point. Apply the relevant steps to the
actual task; the sections are not a mandatory artifact or test checklist. Use existing project
contracts in their actual format. File names such as spec.md, plan.md or tasks.md are examples, not
required dependencies. Other skills are optional expertise, never enforcement mechanisms.

Product rules, stack choices and authority locations must be verified in the Ec consumer. Examples
do not establish deployed capabilities or override its current contract.

## Decision procedure

1. **Identify the source and equivalence relation.** Locate the versioned YAML resource contract and
   define the semantic equality relation for this resource/version: which field values, typed
   references, sequence ordering, defaults, extension fields, and identity matter. Separate semantic
   equality from byte-for-byte YAML presentation and UI layout. Do not treat YAML mapping-key order
   as execution order. Do not assume that parsing and serializing preserve comments or style.
2. **Inventory each projection before coding.** For each `kind` and schema version, describe fields
   as `displayed-editable`, `displayed-read-only`, `not-renderable-preserved`, or
   `unsupported-blocked`. Record stable identities, validation dependencies, allowed operations, and
   the fallback to Form/YAML. If an edit cannot be applied without information loss or unapproved
   assumptions, reject or disable that edit and explain why; never rebuild a resource solely from
   visible fields.
3. **Design an asymmetric update contract.** Model a view as `get(source) -> view`, and an update as
   `put(source, editedView) -> result`, with both the **original source** and the requested change
   available. For each supported view/operation verify **GetPut** (`put(s, get(s)) ≈ s`) and
   **PutGet** (`get(put(s, v)) ≈ v` on accepted valid inputs). Test **PutPut** only for update
   classes where its prerequisites and semantics are explicitly demonstrated; it is not a universal
   lens law. `≈` denotes the approved resource-level equivalence, not textual equality. See [the law
   and model notes](lens-laws-and-data-model.md).
4. **Use one editable semantic draft, not synchronizing stores.** Parse/validate YAML through the
   contract's existing boundary. Derive Form/Visual projections from the same validated draft.
   Represent user changes as typed, narrow operations with stable IDs, logical paths, a base
   revision, and an explicit result (`applied`, `rejected`, or `needs-conflict-resolution`). Keep
   YAML text while invalid in a separate **ephemeral editing buffer** and preserve the last valid
   draft; never apply invalid text or silently reset it on mode switching. Vue `computed`
   projections should be pure; watchers may coordinate external effects, not serve as authority or
   cross-mode synchronization loops.
5. **Preserve the unseen complement.** Apply edits against the original complete resource, retaining
   supported extension/unknown fields, advanced settings, semantic omission vs explicit
   `null`/empty/default distinctions, stable references, and unrelated nodes. Preserve
   comments/anchors/presentation when the actual editor/parser supports source-aware incremental
   editing; otherwise show a text diff and require acknowledgement before a rewrite that changes
   presentation. Do not promise byte-perfect fidelity without evidence.
6. **Handle edge cases explicitly.** Reject edits that introduce invalid DAG edges or cycles, break
   references, violate permission or schema constraints, or clash with a newer base revision. Keep
   view-only state (selection, viewport, temporary node positions) separate from desired
   configuration unless an approved resource schema explicitly includes it. Undo/redo must reverse
   validated draft operations without erasing unrelated fields or pretending to undo an already
   published change.
7. **Test the projection, not just the renderer.** Build hand-authored fixtures and generated cases
   for each resource/version/capability class. Assert semantic round trips, no-op preservation,
   supported edit reflection, retention of hidden fields, stable identity under reorder,
   invalid-buffer isolation, inaccessible edits, stale revision conflicts, undo/redo, and
   keyboard/non-drag equivalents for Visual operations. Use independent serialization and observable
   backend checks when available; snapshot-only tests are insufficient.
8. **Report evidence without overstating it.** Produce the projection/capability matrix, exact
   operations and expected outcomes, preservation analysis, test commands and *observed* results,
   open gaps, and links to project acceptance criteria. Mark unexecuted tests as **planned**.


## Further references by decision

Read only the reference relevant to the unresolved decision. Research registers describe imported authorship, not current execution.

- [Formal Notes — Bidirectional Ec Draft Projections](lens-laws-and-data-model.md): lens laws and data model.
- [Source Matrix and Synthesis — Ec Semantic Configuration Projections](sources-and-decisions.md): source provenance or a disputed recommendation.
- [Selection Cases and Planned Test Oracles](test-oracles-and-routing.md): test oracles and routing.
