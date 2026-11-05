# Parsing and exit-status design

> Ec-specific rules below apply only to an Ec consumer whose current contract establishes them.

POSIX utility-syntax guidelines are guidance for compliant Unix-style utilities; GNU long options add familiar conventions such as `--help` and `--version`. Use `--` to end option parsing where supported, but recognize that downstream commands may interpret arguments differently. Avoid ambiguous option prefixes; test repeated, mutually exclusive, missing, negative-number and Unicode operand cases.

Choose an explicit command-specific exit taxonomy. A portable default may use `0` for command completion, a nonzero syntax code, and distinct documented codes for conflict, not-found, authorization and transport failures. **No universally portable numeric meanings** exist across Rust `ExitCode` and target OSes; avoid mapping signal conventions or BSD `sysexits` as mandatory product law. Exiting 0 after a request was durably **accepted** does not imply that the remote operation succeeded; the output must say `pending` and provide a query key.

Rust adapter: pin the `clap` version, prefer typed `Parser`/`Subcommand`/`ValueEnum`, test generated `--help`, returns and parse failures, and return `std::process::ExitCode` from `main`. Do not call process::exit where destructor cleanup is needed. The provided Python argparse fixture demonstrates contract testing only; it is not an implementation recommendation for Ec's Rust backend.

**Oracles:** wrong option, missing argument, invalid or out-of-range values, duplicate args; help offline; correct stdout/stderr; stable machine error type and process status. Record platform and shell in the test output.
