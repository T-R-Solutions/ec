---
name: cicd-logs-and-test-results
description: Implement or review CI/CD log chunks, replay, redaction and test-report ingestion. Skip platform telemetry design, process output capture and UI layout.
---

# CI/CD Logs and Test Results

## Aplicação no Ec

Identificar chunks por job/attempt/stream/offset; testar duplicatas, gaps, replay e redaction entre chunks. Controlar volume sem impedir cancelamento; truncamento nunca silencioso.

## Scope and inputs

Identify producer/attempt identity, ordering and replay contract, storage limits, sensitive data
rules and supported test-report formats.

Preserve the consumer's stack and policies. Product-specific examples in references do not establish
requirements for this task.

## Procedure

1. Bind chunks to stream/attempt and sequence, distinguishing duplicates, gaps, receipt and durable
   storage; reconnect must not silently invent continuity.
2. Bound buffers, parsing and retention; make truncation or dropped content observable rather than
   reporting a complete log.
3. Redact at appropriate boundaries without assuming secrets align with chunk boundaries, and
   prevent terminal/control markup injection.
4. Parse reports as untrusted data with entity expansion, path and resource limits; keep build
   failure, test failure, skipped tests and missing reports distinct.
5. Test reordered/replayed chunks, crash-after-commit, malformed encodings/reports and split-secret
   leakage. A green collector does not mean tests passed.

## Load detail when needed

- [Chunk ingestion](references/chunks-and-replay.md): consult for chunks and replay.
- [Security and redaction](references/security-and-redaction.md): consult for security and redaction.
- [Test report ingestion](references/test-report-ingestion.md): consult for test report ingestion.
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
