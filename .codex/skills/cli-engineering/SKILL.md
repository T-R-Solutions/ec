---
name: cli-engineering
description: Design, implement, or review command-line behavior, help, machine output, exit codes, and safe retries. Use for CLI consumer contracts; skip shell scripting without a CLI design question.
---

No Ec, siga `docs/development/task-verification.md` para acionar checks e
`docs/development/local-state.md` para arquivos de trabalho (caminhos desde a
raiz). Em outros consumidores, siga as políticas locais.

# CLI Engineering — Discoverable, Scriptable and Safe Commands

## Scope and inputs

Identify human and automated callers, supported shells/platforms, current commands, backend
operations, output compatibility and credentials. Preserve scripts that consume the interface.

Preserve the consumer's stack and policies. Product-specific examples in references do not establish
requirements for this task.

## Procedure

1. Define command grammar, help and validation from actual user tasks; keep missing input distinct
   from an explicit empty value.
2. Separate stable machine output on stdout from diagnostics on stderr. Preserve exit status, signal
   and broken-pipe semantics; do not infer success from printed text.
3. Bind destructive confirmation to the actual target and intent. Support automation deliberately;
   never infer permission from noninteractive mode or a force flag.
4. Classify retries by the backend's operation identity and side effects. Reconcile a lost response
   before repeating an unsafe operation.
5. Test pipes, non-TTY execution, invalid arguments, cancellation, denied actions and
   terminal-control injection; redact credentials in every output path.

## Load detail when needed

- [Command taxonomy, information scent and help](references/command-taxonomy-and-help.md): consult
  for command taxonomy and help.
- [Human output, machine output and pipelines](references/output-and-automation.md): consult for
  output and automation.
- [Authentication, tokens and secret exposure boundaries](references/authentication-and-secrets.md):
  consult for authentication and secrets.
- [Detailed decisions and failure cases](references/implementation-details.md): consult for a
  complex case, additional constraints or the remaining thematic references; apply only relevant
  steps.

## Completion and limits

Registre contrato afetado, inputs, método, resultado e lacunas; distinga inspeção,
simulação e integração real. Registros importados não são resultados atuais.

Aplique apenas os passos pertinentes, usando o registro existente do projeto.
A skill funciona sem SDD; consulte auxiliares disponíveis somente para questões
distintas ainda não resolvidas. O método não amplia a autorização da tarefa.
