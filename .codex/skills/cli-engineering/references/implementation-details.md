# Detailed decisions and failure cases

Read only for a decision needing more detail than the entry point. Apply the relevant steps to the
actual task; the sections are not a mandatory artifact or test checklist. Use existing project
contracts in their actual format. File names such as spec.md, plan.md or tasks.md are examples, not
required dependencies. Other skills are optional expertise, never enforcement mechanisms.

Ec references below are conditional consumer examples. They impose no Ec documents, YAML format,
stack or product policy on a generic task.

## Method

### 1. Define task, consumer and contract surface

Map each verb, noun, option, target and scope to approved feature actors, REQ/AC, server endpoint,
backend authorization and possible operation states. Classify operations into read, preview,
request, confirm and observe. Record exit-code meanings, stdout/stderr formats, platform targets and
intended scripting compatibility. Do not infer server guarantees from a CLI mockup. See [command
taxonomy and help](command-taxonomy-and-help.md).

### 2. Design predictable grammar and help

Use a stable `noun verb` or `verb noun` hierarchy consistently, explicit subcommands, `--help` and
`--version`, clear examples, discoverable errors and `--` operand termination where supported.
Evaluate POSIX conventions as guidance and GNU long options as an interoperable extension; neither
automatically defines the project's CLI syntax. For Rust, prefer a maintained argument parser (for
example `clap`) over custom splitting; confirm actual crate version and behavior. Avoid ambiguous
prefix aliases and surprising defaults. See [argument parsing and exit
status](argument-parsing-and-exit-status.md).

### 3. Separate machine output and diagnostics

Reserve stdout for requested data, stderr for actionable diagnostics/progress, and nonzero process
exit for failure of the **CLI request**. Design stable schema/version for machine results if
consumers need it; document that successful submission of an async operation means *accepted*, not
*succeeded*. Test TTY/non-TTY, `NO_COLOR`, Unicode, locale and control-character injection. JSON on
stdout for machine output is not a competing declarative configuration file for Ec. See [output and
automation](output-and-automation.md).

### 4. Make dangerous operations explicit

Show target, scope, impact, revision and required approvals before mutation when applicable.
Separate `--dry-run` (no side effect) from `--yes` (confirmation), and require non-interactive
confirmation according to the approved spec. Never make a dangerous operation succeed because
`stdin` is not a TTY. Server-side permission checks remain authoritative. Do not expose unverified
rollback. See [confirmation and risk](confirmation-and-risk.md).

### 5. Specify concurrency, idempotency and retries

For mutations, bind a request identity to caller/scope/payload; define replay, incompatible replay,
stale revisions and partial failure. Distinguish service acceptance (e.g. 202) from terminal status.
Use conditional requests or revision checks when supported; verify server atomicity and durable
deduplication in integration tests. A local cache or SQLite toy transaction is no proof of
PostgreSQL/cluster guarantees. See [idempotency and concurrency](idempotency-and-concurrency.md).

### 6. Protect credentials and terminal boundaries

Never accept secrets in plaintext arguments by default; avoid echoing credentials into logs, errors,
history or shell completions. Use OS-appropriate credential agents/secure storage and narrow scopes.
Do not construct shell commands by interpolating user input; use an argument-array process API and
validate operands. Escape or suppress untrusted ANSI/terminal controls before printing; handle pipes
and broken stdout safely. See [authentication and secrets](authentication-and-secrets.md) and
[subprocess and terminal safety](subprocess-and-terminal-safety.md).

### 7. Test consumer compatibility and failure paths

Layer grammar/help snapshots, stdout/stderr/exit codes, property/boundary inputs, TTY and piped
output, safe interruption, retry/race, fake server, actual API integration and OS/shell matrix.
Cover invalid options, stale revisions, expired sessions, denied access, missing API, partial
failures, duplicate keys and non-UTF8 paths where relevant. Guard against false-green tests when
prerequisites are missing. The included Python + SQLite `democtl` is a **synthetic executable
fixture**, not Ec, not Rust, and not a production CLI. See [integration and
evidence](integration-and-evidence.md).

### 8. Document and hand off evidence

Deliver the command tree, help examples, behavior/error/exit table, scripting schema,
security/authorization boundaries, compatibility strategy, acceptance-test map and reproducible
results. In project workflow map `REQ → AC → plan/ADR → tasks → measured evidence`, explicitly
marking unavailable integrations `NOT EXECUTED`. See [routing and test
oracles](routing-and-test-oracles.md).


## Further references by decision

Read only the reference relevant to the unresolved decision. Research registers describe imported authorship, not current execution.

- [Parsing and exit-status design](argument-parsing-and-exit-status.md): argument parsing and exit status.
- [Authentication, tokens and secret exposure boundaries](authentication-and-secrets.md): authentication and secrets.
- [Command taxonomy, information scent and help](command-taxonomy-and-help.md): command taxonomy and help.
- [Environment, portable configuration and distribution](config-environment-and-portability.md): config environment and portability.
- [Confirmation, preview and operational risk](confirmation-and-risk.md): confirmation and risk.
- [Idempotency, retries, revisions and completion](idempotency-and-concurrency.md): idempotency and concurrency.
- [Multi-layer validation and release gate](integration-and-evidence.md): integration and evidence.
- [Human output, machine output and pipelines](output-and-automation.md): output and automation.
- [Skill routing, counterexamples and oracles](routing-and-test-oracles.md): routing and test oracles.
- [Peer-reviewed empirical and conceptual evidence — precise limits](scientific-evidence-and-limits.md): source provenance or a disputed recommendation.
- [Technical sources and decision ledger](sources-and-decisions.md): source provenance or a disputed recommendation.
- [Process boundaries, hostile text and shell integration](subprocess-and-terminal-safety.md): subprocess and terminal safety.
