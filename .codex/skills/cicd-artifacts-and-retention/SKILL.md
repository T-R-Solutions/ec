---
name: cicd-artifacts-and-retention
description: Implement or review CI/CD artifact ingestion, integrity, provenance, retrieval and retention. Skip build caches and process execution.
---

# CI/CD Artifacts and Retention

## Scope and inputs

Identify artifact scope, producer/attempt identity, storage semantics, limits, holds, deletion
obligations and authorization. Do not invent retention periods or compliance policy.

Preserve the consumer's stack and policies. Product-specific examples in references do not establish
requirements for this task.

## Procedure

1. Bind uploads to immutable identity and scope; separate partial upload, verified content and
   published manifest.
2. Validate digests, lengths and archive/path constraints before making content retrievable;
   metadata alone is not integrity evidence.
3. Authorize retrieval and deletion independently, preserving tenant/trust boundaries and
   secret-safe provenance.
4. Define retention, holds and garbage-collection reachability with concurrency and interrupted
   deletion in mind; verify eligibility again before destructive effects.
5. Test corrupted/partial upload, unauthorized access, duplicate finalization, hold races and
   recovery. A disposable storage model does not prove backend durability.

## Load detail when needed

- [Upload and integrity](references/integrity-and-upload.md): consult for integrity and upload.
- [Provenance and access](references/provenance-and-access.md): consult for provenance and access.
- [Retention and GC](references/retention-and-gc.md): consult for retention and gc.
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
