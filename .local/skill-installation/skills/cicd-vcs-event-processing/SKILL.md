---
name: cicd-vcs-event-processing
description: Implement or review CI/CD VCS webhook authentication, event normalization, revision resolution and durable triggers. Skip connector administration and scheduler dispatch.
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

# CI/CD VCS Event Processing

## Scope and inputs

Locate provider/delivery contracts, trust scopes, signature rules, branch/PR policies, immutable
revision APIs and durable intent storage.

Preserve the consumer's stack and policies. Product-specific examples in references do not establish
requirements for this task.

## Procedure

1. Authenticate bounded raw deliveries before trusting normalized fields; distinguish provider
   receipt identity from business trigger identity.
2. Normalize only documented event semantics and apply explicit branch/PR filters without assuming
   forked code has trusted credentials.
3. Resolve immutable source/configuration revisions and provenance; a mutable branch name is not a
   reproducible execution input.
4. Persist deduplication and trigger intent with a defined commit point and recovery path. Replayed
   delivery must not silently create duplicate effects.
5. Test forged/replayed deliveries, conflicting duplicate IDs, missing revisions, provider outage
   and crash windows. Real provider and database guarantees need their own integration evidence.

## Load detail when needed

- [Authentication](references/authentication.md): consult for authentication.
- [Durable ingestion](references/durability-and-idempotency.md): consult for durability and idempotency.
- [Immutable revisions and trust](references/revisions-and-trust.md): consult for revisions and trust.
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
