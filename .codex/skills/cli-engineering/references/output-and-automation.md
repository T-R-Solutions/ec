# Human output, machine output and pipelines

> Ec-specific rules below apply only to an Ec consumer whose current contract establishes them.

Machine output must be documented with stable field names, schema/version and encoding; diagnostics should not contaminate stdout. Use stderr for help on errors and transient progress, stdout for data the caller requested. A structured error on stderr may use JSON when explicitly requested. JSON in machine **output** or HTTP transport is not declarative configuration, and does not change the Ec YAML-only rule.

Do not parse human text with grep as the only automation interface if a stable format is required. Define paging, time representation (explicit timezone/UTC), null/error/missing semantics, line delimiting and whether fields may be added. If human tables use ANSI, disable it in non-TTY output; treat `NO_COLOR` as an informal convention and permit explicit command options to override it. Avoid log injection via ANSI OSC/CSI escape or control characters from untrusted server data.

Do not print a success banner when request is merely submitted. Distinguish `accepted`, `pending`, `succeeded`, `failed`, `cancelled` and `unknown` as the API supports. Avoid printing arbitrary progress to stdout under `--output json`. Test streaming/pipes, broken pipes and partial output separately on real target runtimes.
