---
name: cicd-logs-and-test-results
description: Implement or review CI/CD log chunks, replay, redaction and test-report ingestion. Skip platform telemetry design, process output capture and UI layout.
---

No Ec, siga `docs/development/task-verification.md` para acionar checks e
`docs/development/local-state.md` para arquivos de trabalho (caminhos desde a
raiz). Em outros consumidores, siga as políticas locais.

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

Registre contrato afetado, inputs, método, resultado e lacunas; distinga inspeção,
simulação e integração real. Registros importados não são resultados atuais.

Aplique apenas os passos pertinentes, usando o registro existente do projeto.
A skill funciona sem SDD; consulte auxiliares disponíveis somente para questões
distintas ainda não resolvidas. O método não amplia a autorização da tarefa.
