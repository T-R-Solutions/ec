---
name: internationalization
description: Implement or review locale negotiation, message resources, formatting, pluralization and bidirectional UI. Skip translation-only writing and product language policy decisions.
---

No Ec, siga `docs/development/task-verification.md` para acionar checks e
`docs/development/local-state.md` para arquivos de trabalho (caminhos desde a
raiz). Em outros consumidores, siga as políticas locais.

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

Registre contrato afetado, inputs, método, resultado e lacunas; distinga inspeção,
simulação e integração real. Registros importados não são resultados atuais.

Aplique apenas os passos pertinentes, usando o registro existente do projeto.
A skill funciona sem SDD; consulte auxiliares disponíveis somente para questões
distintas ainda não resolvidas. O método não amplia a autorização da tarefa.
