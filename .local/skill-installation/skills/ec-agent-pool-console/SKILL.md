---
name: ec-agent-pool-console
description: Implement or review Ec agent/pool administration and explainable eligibility views. Skip scheduler placement algorithms, agent wire protocols and local execution.
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

# Ec Agent and Pool Console

## Scope and inputs

Inspect approved pool/project relationships, runtime observations, permissions and actual APIs.
Separate desired configuration from sessions, capacity and active leases.

Use the Ec consumer's current product requirements and implementation as authority. Locate them
rather than assuming filenames, schemas, endpoints or deployed features. Examples in this folder are
technical proposals until confirmed there.

## Procedure

1. Expose enrollment, connectivity, maintenance, trust, compatibility and occupancy as distinct
   evidence where supported; missing data is not measured zero.
2. Explain eligibility through scoped policy, verified capabilities, freshness and capacity. Do not
   fabricate queue position or wait-time estimates.
3. Preview assignment changes against source and target scopes, active work and the current
   revision; recheck authorization at mutation.
4. Model drain/disable/remove using the actual operation contract. A request to drain does not imply
   active work was cancelled.
5. Test stale snapshots, concurrent reassignment, permission changes and accessible recovery.
   Confirm effects through backend state rather than button feedback.

## Load detail when needed

- [Explainable Eligibility and Evidence — Engineering Decision
  Guide](references/eligibility-and-evidence.md): consult for eligibility and evidence.
- [Pool Administration, Operator Safety and UX — Method Guide](references/administration-and-ui.md):
  consult for administration and ui.
- [Activation and Real Integration Oracles](references/routing-and-test-oracles.md): consult for
  routing and test oracles.
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
