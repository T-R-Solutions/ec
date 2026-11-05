---
name: ec-declarative-resource-schemas
description: Design or review Ec YAML resource schemas, parser boundaries, validation, evolution and diagnostics. Skip editor presentation, publication and execution without a schema question.
---

# Ec Declarative Resource Contracts

## Scope and inputs

Inspect current resource kinds, versions, parser behavior, consumers and accepted extensions. YAML
representation rules and the Ec application schema are different contracts.

Use the Ec consumer's current product requirements and implementation as authority. Locate them
rather than assuming filenames, schemas, endpoints or deployed features. Examples in this folder are
technical proposals until confirmed there.

## Procedure

1. Define field types, requiredness, defaults, null/absence distinctions, references and invalid
   examples without inventing product fields.
2. Verify actual parser versions and supported tags, aliases, multi-document input, duplicate keys
   and limits. Do not infer safe support from a library name.
3. Detect duplicate resolved keys and information loss before conversion into structures that
   overwrite entries; reject or preserve unknown fields according to policy.
4. Separate parsing, version dispatch, structural validation, domain invariants, references and
   authorization. Parse success is not publication approval.
5. Test valid/invalid fixtures, migration loss, bounded hostile input and truthful diagnostic
   locations. Run parser conformance separately from real product integration.

## Load detail when needed

- [Test Scenarios and Oracles — Ec YAML Resource Contracts](references/checks-and-examples.md):
  consult for checks and examples.
- [Evidence Matrix — `ec-declarative-resource-schemas`](references/sources-and-decisions.md):
  consult for sources and decisions.
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
