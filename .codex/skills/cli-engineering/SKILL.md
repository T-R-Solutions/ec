---
name: cli-engineering
description: Design, implement, or review command-line behavior, help, machine output, exit codes, and safe retries. Use for CLI consumer contracts; skip shell scripting without a CLI design question.
---

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

Complete the requested change or diagnosis and the checks that support its claims. Report the
affected contract, observed results, actual versions/commands and material gaps. Keep structural
review, simulated cases, real integration and production evidence distinct. Imported research and
execution records are historical context, not results of the current task.

This folder works without SDD or other skills. Consult an available specialist only for a concrete
unresolved question; its absence is a gap only when essential to acceptance. Use the project's
existing record for a bounded change. This method grants no external access, publication or
destructive-action authority.
