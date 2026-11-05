# Process execution contract
- Pass executable and argv as separate fields; Rust `std::process::Command::args` does not require a shell. Treat untrusted executable selection and PATH as separate attack surfaces.
- Spawn errors differ from nonzero exits. `Child` is not automatically waited on by Drop; reap processes.
- Working directory is not a security boundary; validate resolved paths against the workspace and prevent symlink escapes.
- Process-group semantics differ across Linux/macOS/Windows. Windows requires explicit job-object or equivalent policy for process trees.
- Distinguish output collection from streaming. A convenience `output()` can buffer unbounded output for large workloads; define a bounded strategy.
