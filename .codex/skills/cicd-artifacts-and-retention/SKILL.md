---
name: cicd-artifacts-and-retention
description: Implement or review CI/CD artifact ingestion, integrity, provenance, retrieval and retention. Skip build caches and process execution.
---

No Ec, siga `docs/development/task-verification.md` para acionar checks e
`docs/development/local-state.md` para arquivos de trabalho (caminhos desde a
raiz). Em outros consumidores, siga as políticas locais.

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

Registre contrato afetado, inputs, método, resultado e lacunas; distinga inspeção,
simulação e integração real. Registros importados não são resultados atuais.

Aplique apenas os passos pertinentes, usando o registro existente do projeto.
A skill funciona sem SDD; consulte auxiliares disponíveis somente para questões
distintas ainda não resolvidas. O método não amplia a autorização da tarefa.
