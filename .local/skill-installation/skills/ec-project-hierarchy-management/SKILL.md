---
name: ec-project-hierarchy-management
description: Implement or review Ec project trees, inheritance and hierarchy mutations. Skip pipeline DAGs, membership graphs and schema or permission-engine ownership.
---

## Acionamento de scripts no Ec

Neste checkout, esta regra prevalece sobre recomendações de execução desta skill e
suas referências. Scripts de verificação só podem ser acionados após pedido explícito
de commit. Pergunte exatamente: “Deseja executar o script automate.sh (que roda todos os outros scripts)?”
Ofereça somente `sim` e `não`, sem resposta automática; aguarde a escolha.
Com `não`, prossiga normalmente com os commits solicitados, sem executar scripts.
Com `sim`, execute da raiz `bash scripts/automate.sh run`, sem filtros, e só crie
os commits após todos os scripts da suíte terminarem sem erros. Falha, interrupção,
ferramenta ausente ou execução incompleta bloqueiam os commits nessa opção.
Não execute scripts avulsos nem os acione automaticamente durante implementação,
revisão ou encerramento. Inspeção manual continua permitida; checks não executados
permanecem explicitamente não verificados. Detalhes: `docs/development/task-verification.md`
(caminho relativo à raiz Ec). Em outros projetos, siga a política do consumidor.

# Ec Project Hierarchy Management

## Scope and inputs

Identify stable project IDs, scopes, parent references, attached resources and family-specific
inheritance rules. A hierarchy diagram is not a product policy.

Use the Ec consumer's current product requirements and implementation as authority. Locate them
rather than assuming filenames, schemas, endpoints or deployed features. Examples in this folder are
technical proposals until confirmed there.

## Procedure

1. Define tree invariants and independent traversal checks; a parent foreign key alone does not
   prevent cycles.
2. Specify inheritance separately for configuration, templates, connections, pools and access
   bindings; preserve provenance and precedence rather than guessing a universal merge.
3. Compare storage/index alternatives using real access and move patterns, while preserving
   canonical identity and historical references.
4. Preview move, rename, copy, archive and delete separately; authorize both affected scopes and
   commit against a fresh revision, including concurrent cycle checks.
5. Test concurrent moves, stale previews, permission changes, deep/wide trees and accessible move
   alternatives. Rebuild derived indexes without changing desired-state authority.

## Load detail when needed

- [Hierarchy Model, Inheritance and Mutation Design
  Notes](references/hierarchy-model-and-inheritance.md): consult for hierarchy model and
  inheritance.
- [Routing and Verification Oracles — Ec Project Hierarchy](references/routing-and-test-oracles.md):
  consult for routing and test oracles.
- [Source Ledger and Engineering Decisions — Ec Project
  Hierarchy](references/sources-and-decisions.md): consult for sources and decisions.
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
