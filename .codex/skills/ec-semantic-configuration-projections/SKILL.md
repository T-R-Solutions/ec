---
name: ec-semantic-configuration-projections
description: Implement or review loss-preserving Ec YAML/Form/Visual projections and draft synchronization. Skip schema definition, publication and styling without semantic edits.
---

# Ec Semantic Configuration Projections

## Scope and inputs

Locate the resource/version contract, parser capabilities, semantic equality relation and existing
edit model. Separate semantic preservation from comments and byte formatting.

Use the Ec consumer's current product requirements and implementation as authority. Locate them
rather than assuming filenames, schemas, endpoints or deployed features. Examples in this folder are
technical proposals until confirmed there.

## Procedure

1. Classify fields as editable, read-only, preserved-but-unrendered or unsupported; blocked
   capabilities must not silently discard data.
2. Model get(source) and put(source, editedView) with the complete source retained. Verify no-op
   preservation and reflection of supported edits.
3. Use one semantic draft and narrow revision-bound operations. Preserve hidden fields, sequence
   order, stable IDs and absent/null/default distinctions.
4. Keep invalid YAML buffers separate from the last valid model and expose projection availability
   honestly; reject invalid references or stale bases with a recovery path.
5. Test independent round-trip oracles, unsupported fields, reorder, mode changes and interrupted
   edits. A visually equal rendering does not establish semantic equality.

## Load detail when needed

- [Formal Notes — Bidirectional Ec Draft Projections](references/lens-laws-and-data-model.md):
  consult for lens laws and data model.
- [Selection Cases and Planned Test Oracles](references/test-oracles-and-routing.md): consult for
  test oracles and routing.
- [Source Matrix and Synthesis — Ec Semantic Configuration
  Projections](references/sources-and-decisions.md): consult for sources and decisions.
- [Detailed decisions and failure cases](references/implementation-details.md): consult for a
  complex case, additional constraints or the remaining thematic references; apply only relevant
  steps.

## Completion and limits

Complete the requested change or diagnosis and the checks that support its claims. Report the
affected contract, observed results, actual versions/commands and material gaps. Keep structural
review, simulated cases, real integration and production evidence distinct. Imported research and
execution records are historical context, not results of the current task.

This folder works without SDD or other skills. Consult an available specialist only for a concrete
unresolved question; its absence is a gap only when essential to acceptance. Use the project's
existing record for a bounded change. This method grants no external access, publication or
destructive-action authority.
