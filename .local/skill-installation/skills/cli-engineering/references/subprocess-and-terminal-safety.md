# Process boundaries, hostile text and shell integration

Prefer structured process APIs: `std::process::Command` in Rust, or an equivalent `argv` array on other platforms. Do not interpolate user strings into `/bin/sh -c`, PowerShell or `cmd.exe`. Parameter separation limits shell injection but **does not** prevent argument injection: validate command, allowed flags, operand position, URLs, file paths and target working directory. When a child can hang, enforce timeout, cancellation and process-tree cleanup policy and test output limits.

Terminals interpret control sequences. Treat data from servers, filenames, logs and user names as **hostile presentation input**: sanitize ANSI CSI/OSC, carriage returns, bidi controls and unprintable characters before presenting them. Test for terminal-resize, color disabled in pipe, UTF-8 and shell escaping. Never assume colored text is accessible. The basic fixture sanitizer deliberately supports only a subset and is not a full terminal safety implementation.

Use real OS/PTY tests for signal and pipeline behavior, graceful shutdown and broken-pipe handling; do not infer those properties from a Python unit test. Reference OWASP command injection guidance and Rust process/terminal docs.
