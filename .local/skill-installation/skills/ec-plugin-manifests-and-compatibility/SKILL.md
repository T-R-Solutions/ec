---
name: ec-plugin-manifests-and-compatibility
description: Design or review Ec plugin manifests, extension contracts and dependency compatibility. Skip runtime isolation, artifact trust verification and generic package upgrades.
---

# Ec Plugin Manifests and Compatibility

## Scope and inputs

Locate the host-owned extension registry, schema and API versions, range language, candidate
releases and current consumers. Do not invent manifest fields or extension points.

Use the Ec consumer's current product requirements and implementation as authority. Locate them
rather than assuming filenames, schemas, endpoints or deployed features. Examples in this folder are
technical proposals until confirmed there.

## Procedure

1. Separate plugin identity/release, manifest schema, host API, extension contract and artifact digest.
2. Define required and optional capabilities/dependencies with explicit namespaces, cardinality and
   unknown-field behavior.
3. Evaluate every compatibility axis, including prereleases and declared platform requirements; do
   not use a single version comparison as the whole oracle.
4. Resolve the bounded dependency graph and explain conflicts, missing providers, cycles and
   optional dependency behavior without silently changing candidates.
5. Test invalid and incompatible manifests and migration cases. Metadata compatibility does not
   verify publisher trust, safe execution or permission grants.

## Load detail when needed

- [Manifest and Version Matrix — Engineering Reference](references/manifest-and-version-matrix.md):
  consult for manifest and version matrix.
- [Capability Constraints, Dependency Resolution and
  Tests](references/constraint-resolution-and-contract-tests.md): consult for constraint resolution
  and contract tests.
- [Security Boundary and Plugin Catalog Presentation](references/security-catalog-and-ui.md):
  consult for security catalog and ui.
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
