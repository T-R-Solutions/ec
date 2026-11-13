---
name: ec-declarative-resource-schemas
description: Design or review Ec YAML resource schemas, parser boundaries, validation, evolution and diagnostics. Skip editor presentation, publication and execution without a schema question.
---

No Ec, siga `docs/development/task-verification.md` para acionar checks e
`docs/development/local-state.md` para arquivos de trabalho (caminhos desde a
raiz). Em outros consumidores, siga as políticas locais.

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

Registre contrato afetado, inputs, método, resultado e lacunas; distinga inspeção,
simulação e integração real. Registros importados não são resultados atuais.

Aplique apenas os passos pertinentes, usando o registro existente do projeto.
A skill funciona sem SDD; consulte auxiliares disponíveis somente para questões
distintas ainda não resolvidas. O método não amplia a autorização da tarefa.
