---
name: ec-effective-permissions
description: Implement or review Ec effective authorization, inherited grants, provenance, freshness and delegation controls. Skip authentication federation and access-administration UI alone.
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

# Ec Effective Permissions

## Scope and inputs

Identify principal/status, policy revision, role bindings, resource hierarchy, membership graphs and
the trusted enforcement boundary. Resolve missing grant/deny precedence from the product contract.

Use the Ec consumer's current product requirements and implementation as authority. Locate them
rather than assuming filenames, schemas, endpoints or deployed features. Examples in this folder are
technical proposals until confirmed there.

## Procedure

1. Keep project hierarchy, role hierarchy and membership graphs distinct; reject invalid cycles or
   cross-scope references according to their own contracts.
2. Evaluate explicit action/resource/context policy. Distinguish allow, deny and indeterminate;
   unavailable or stale authority must not become a grant.
3. Bind decisions to coherent policy, identity, membership and hierarchy versions. Recheck freshness
   and revocation at the actual protected operation.
4. Authorize delegation separately from permission to edit bindings; prevent unauthorized
   self-grants and privilege escalation.
5. Return safe provenance and observed versions, then test inherited grants, revocation, stale
   caches, concurrent changes and absence of effects on denial.

## Load detail when needed

- [Effective Authorization Evaluation and Freshness — Engineering
  Reference](references/evaluation-contract-and-freshness.md): consult for evaluation contract and
  freshness.
- [Skill Routing and Verification Oracles](references/routing-and-test-oracles.md): consult for
  routing and test oracles.
- [Evidence Ledger and Engineering Decisions — Ec Effective
  Permissions](references/sources-and-decisions.md): consult for sources and decisions.
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
