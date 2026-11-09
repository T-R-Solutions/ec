---
name: ec-operations-and-administration-console
description: Implement or review Ec health, audit, diagnostics and administrative operations screens. Skip run controls, pool management and telemetry-backend design without a console concern.
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

# Ec Operations and Administration Console

## Scope and inputs

Identify actual operational signals, protected admin commands, audit contracts, retention/access
policy and user tasks. A dashboard mockup is not evidence of a working control plane.

Use the Ec consumer's current product requirements and implementation as authority. Locate them
rather than assuming filenames, schemas, endpoints or deployed features. Examples in this folder are
technical proposals until confirmed there.

## Procedure

1. Define the source, scope, timestamp and uncertainty of each health signal; distinguish readiness,
   liveness, degraded behavior and absent telemetry.
2. Build navigation and filters around investigation tasks while respecting data visibility and
   bounded queries.
3. Preview consequential commands using current target/scope and permissions; bind confirmation to
   that intent and reauthorize server-side.
4. Separate command submission, acceptance and confirmed effects. Preserve unknown results and
   recovery paths after disconnect or timeout.
5. Test stale data, denial, partial failures, audit redaction and keyboard recovery. Do not infer
   system health from one green indicator.

## Load detail when needed

- [Evidence-bound Health and Operations Information
  Architecture](references/health-evidence-and-information-architecture.md): consult for health
  evidence and information architecture.
- [Safe Administrative Commands, Audit and
  Authorization](references/admin-command-and-audit-safety.md): consult for admin command and audit
  safety.
- [Recovery Evidence, Accessible Operator UX and Test
  Oracles](references/recovery-and-ux-oracles.md): consult for recovery and ux oracles.
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
