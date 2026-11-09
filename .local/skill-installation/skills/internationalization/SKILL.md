---
name: internationalization
description: Implement or review locale negotiation, message resources, formatting, pluralization and bidirectional UI. Skip translation-only writing and product language policy decisions.
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

# Internationalization — Locale-Explicit, Lossless, Testable Interfaces

## Scope and inputs

Identify supported locales, message/runtime library versions, fallback policy, time zones, data
types and translation ownership. Preserve user-entered data and canonical identifiers.

Preserve the consumer's stack and policies. Product-specific examples in references do not establish
requirements for this task.

## Procedure

1. Separate stored values and identifiers from localized presentation; never parse persisted numbers
   or timestamps through an incidental display locale.
2. Specify locale selection and fallback, distinguishing user choice, browser hints and application
   defaults. Keep locale, language, region and time zone separate.
3. Use structured messages with typed parameters and plural/select rules; avoid concatenation and
   unsafe translated markup.
4. Test RTL and mixed-direction content, long labels, missing keys, resource-load races and locale
   changes without losing user state.
5. Verify dates, numbers, plural cases and error messages with actual locale libraries. Linguistic
   review and assistive-technology checks require their own observations.

## Load detail when needed

- [BCP 47 Negotiation and Fallback](references/locale-negotiation-bcp47.md): consult for locale
  negotiation bcp47.
- [UI Catalog and Source-String Contract](references/resource-contracts-and-extraction.md): consult
  for resource contracts and extraction.
- [Instants, Civil Dates, Time Zones and DST](references/date-time-and-time-zones.md): consult for
  date time and time zones.
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
