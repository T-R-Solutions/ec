# Command taxonomy, information scent and help

> Ec-specific rules below apply only to an Ec consumer whose current contract establishes them.

## Observable contract

For each command, record `actor`, `scope`, `resource`, `verb`, `risk`, `flags`, `permissions`, `server operation`, `output`, `exit statuses`, `stable alias/version`, and `REQ/AC`. Use a command map reviewed against actual tasks, not merely the backend REST route names. Decide consistency (`noun verb` vs `verb noun`) once per CLI. Keep destructive verbs distinguishable from read-only verbs and operations which merely submit requests.

Help should be available without a network, token or logged-in user. Include concise synopsis, available subcommands, each flag's scope/default, at least one realistic example with **dummy** values, and the error exit for invalid syntax. For complex CLI, support command-specific `--help`, plus suggestions drawn from valid verbs; do not autocorrect and execute privileged commands.

Examples in this file are illustrative, **not existing Ec commands**: `democtl resource get --id sample`; `democtl change request --id sample --revision 1 --idempotency-key request-0001 --dry-run`. Stable, documented names matter for scripts. A new command or deprecated alias is a consumer-visible compatibility decision, not a local refactor.

**Oracles:** `--help` is offline/zero; invalid option or missing subcommand is nonzero; aliases/flag combinations do not silently change target; no secrets appear in completion suggestions. Trace the new grammar to approved spec before implementation.
