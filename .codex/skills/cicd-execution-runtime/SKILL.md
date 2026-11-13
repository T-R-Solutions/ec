---
name: cicd-execution-runtime
description: Implement or review CI/CD step execution, output draining, deadlines, cancellation and process isolation. Skip queue scheduling, agent protocols and log storage.
---

No Ec, siga `docs/development/task-verification.md` para acionar checks e
`docs/development/local-state.md` para arquivos de trabalho (caminhos desde a
raiz). Em outros consumidores, siga as políticas locais.

# CI/CD Execution Runtime Engineering

## Aplicação no Ec

Distinguir comando recebido, processo iniciado, término observado e resultado persistido no Server. Cancelamento exige observar descendentes e coleta; testar corridas e efeitos parciais.

## Scope and inputs

Identify host OS, executable/arguments, trust tier, environment/secrets, workspace, cleanup
ownership and required containment. Discover real host capabilities and project commands.

Preserve the consumer's stack and policies. Product-specific examples in references do not establish
requirements for this task.

## Procedure

1. Keep executable and argv separate; use a shell only under an explicit command contract with
   appropriate injection controls.
2. Establish required containment and bounded resources before untrusted work starts. A working
   directory, process group or privilege flag is not a complete sandbox.
3. Own attempt/workspace identity and drain stdout/stderr concurrently with bounded buffering and an
   explicit loss/backpressure policy.
4. Apply monotonic deadlines, graceful termination, bounded escalation and process-tree cleanup.
   Distinguish cancellation requested, signal delivery and reaped completion.
5. Test spawn failure, output saturation, natural-exit races, surviving descendants and cleanup
   errors on supported hosts; simulated states do not prove isolation.

## Load detail when needed

- [Process execution contract](references/process-execution.md): consult for process execution.
- [Cancellation and deadlines](references/cancellation.md): consult for cancellation.
- [Isolation and resource limits](references/isolation.md): consult for isolation.
- [Detailed decisions and failure cases](references/implementation-details.md): consult for a
  complex case, additional constraints or the remaining thematic references; apply only relevant
  steps.

## Completion and limits

Registre contrato afetado, inputs, método, resultado e lacunas; distinga inspeção,
simulação e integração real. Registros importados não são resultados atuais.

Aplique apenas os passos pertinentes, usando o registro existente do projeto.
A skill funciona sem SDD; consulte auxiliares disponíveis somente para questões
distintas ainda não resolvidas. O método não amplia a autorização da tarefa.
