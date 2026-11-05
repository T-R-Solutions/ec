# Technical sources and decision ledger

> Imported authoring record. Research dates, consultations and execution claims below belong to the supplied source package; they were not reproduced in this adaptation. Referenced validation scripts, fixtures, product norms and logs are not bundled unless present in this folder. Verify their availability and provenance before use. No historical PASS is current acceptance.

Source review date: 2026-11-01. These authorities have different weight: standards define protocol/OS behavior; official library documentation defines supported APIs; academic studies supply bounded observations; design guides are optional heuristics. A cited source cannot substitute for a test of the target CLI.

| ID | Authority and exact URL | What it actually supports | Status / limitation |
|---|---|---|---|
| O01 | POSIX Base Definitions, Utility Conventions, <https://pubs.opengroup.org/onlinepubs/9699919799/basedefs/V1_chap12.html> | Utility syntax guidelines and conventions | POSIX guidance; actual target compliance must be scoped |
| O02 | GNU Coding Standards §4.8, <https://www.gnu.org/prep/standards/html_node/Command_002dLine-Interfaces.html> | Long-option naming, help/version and GNU extension differences | GNU design guidance, not universal POSIX mandate |
| O03 | Rust `std::process::ExitCode`, <https://doc.rust-lang.org/stable/std/process/struct.ExitCode.html> | `SUCCESS`/`FAILURE`, platform caveats, `From<u8>` | Rust implementation API, no universal numeric meanings |
| O04 | Rust `std::io::IsTerminal`, <https://doc.rust-lang.org/stable/std/io/trait.IsTerminal.html> | Detect whether stream is attached to a terminal | Platform-dependent behavior |
| O05 | clap derive tutorial, <https://docs.rs/clap/latest/clap/_derive/_tutorial/> | Derive parser, help, subcommands and typed argument validation | Check actual pinned crate version before use |
| O06 | Python `argparse`, <https://docs.python.org/3/library/argparse.html> | Help/version, `allow_abbrev`, subcommands, parser errors | Prototype implementation only; actual Ec backend remains Rust |
| O07 | XDG Base Directory 0.8, <https://specifications.freedesktop.org/basedir/0.8/> | Unix-like configuration/state/cache path semantics | Does not define Windows/macOS equivalents |
| O08 | `NO_COLOR` convention, <https://no-color.org/> | Optional color-disable preference | Informal community convention, not a formal standard |
| O09 | OWASP OS Command Injection Defense, <https://cheatsheetseries.owasp.org/cheatsheets/OS_Command_Injection_Defense_Cheat_Sheet.html> | Avoid shell interpolation, use parameterized argv and validate arguments | Security guidance, not complete proof against all injection |
| O10 | OWASP Secrets Management, <https://cheatsheetseries.owasp.org/cheatsheets/Secrets_Management_Cheat_Sheet.html> | Secret lifecycle and logging prevention | Product threat model still required |
| O11 | RFC 9110, <https://www.rfc-editor.org/rfc/rfc9110> | HTTP method semantics, conditional requests, `202 Accepted` | Defines HTTP wire semantics, not CLI exit codes |
| O12 | YAML 1.2.2, <https://yaml.org/spec/1.2.2/> | YAML language specification | Ec-only rule of *sole canonical configuration* comes from its norms |
| O13 | GNU Error Messages, <https://www.gnu.org/prep/standards/html_node/Errors.html> | Diagnostics and failed system-call descriptions | GNU editorial guidance |
| O14 | Rust `std::process::Command`, <https://doc.rust-lang.org/stable/std/process/struct.Command.html> | Argument-array child process API | Validation of target executable still necessary |
| O15 | GNU long options, <https://www.gnu.org/prep/standards/html_node/Option-Table.html> | Common long-option spellings | Program-specific choices remain a product contract |

## Decisions derived from these sources

- **D01** Prefer existing standard parsers; do not implement a bespoke tokenization grammar without a demonstrated reason. Official support: O01/O02/O05/O06.
- **D02** Treat machine-output and human-output as separate contracts; stderr diagnostics and stable exit codes must be tested. O02/O03/O13, plus API RFC O11 where relevant.
- **D03** Never infer successful asynchronous completion from HTTP 202 or exit code 0 of a submission command. O11; verify against actual API.
- **D04** Include TTY and piping tests with explicit NO_COLOR handling if color is enabled. O04/O08; these are engineering recommendations, not universal user preferences.
- **D05** Reject raw secrets on argv/logging, and avoid shell interpretation. O09/O10. Backend authorization cannot be delegated to a CLI flag.
- **D06** Use native platform directories only after verifying supported OS matrix; XDG doesn't prescribe Windows/macOS locations. O07.
- **D07** Do not claim Rust/clap compatibility from a Python argparse fixture; official Rust API sources provide a migration route, not test evidence. O03/O04/O05.

For empirical evidence and methodological limitations, see `scientific-evidence-and-limits.md`. No security, performance or usability outcome for Ec was observed.
