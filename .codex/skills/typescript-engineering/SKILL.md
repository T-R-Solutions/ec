---
name: typescript-engineering
description: Implement, refactor or review TypeScript types, boundaries, public APIs and compiler/module configuration. Skip framework lifecycle and runtime validation claims based only on type checking.
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

# TypeScript Engineering

## Scope and inputs

Inspect the installed compiler, tsconfig inheritance, package exports, runtime/bundler, declarations
and consumers. Verify version-dependent flags against the target toolchain.

Preserve the consumer's stack and policies. Product-specific examples in references do not establish
requirements for this task.

## Procedure

1. Model valid states and narrow unknown external values before use; distinguish absence,
   nullability and readonly views from runtime immutability.
2. Use generics and conditional types when they preserve a real relation; avoid assertions or broad
   any types that hide unmet proof obligations.
3. Keep public signatures and emitted declarations compatible with actual consumers, including
   JavaScript callers where supported.
4. Align module format, resolution, package exports and emitted paths with runtime behavior; a
   compiler paths mapping is not a runtime resolver.
5. Run discovered compiler and consumer checks plus runtime boundary tests. Compilation does not
   validate input data, effects or framework behavior.

## Load detail when needed

- [Strictness, narrowing, discriminated unions and state-space
  proof](references/strictness-control-flow-and-boundaries.md): consult for strictness control flow
  and boundaries.
- [Generic relationships, inference, `satisfies` and API
  ergonomics](references/generics-and-public-api-design.md): consult for generics and public api
  design.
- [Runtime trust boundaries, JavaScript interoperability and safe
  parsing](references/runtime-interop-and-safety.md): consult for runtime interop and safety.
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
