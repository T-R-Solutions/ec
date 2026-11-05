# Routing and executable oracles
Positive: argv with whitespace is preserved; stdout/stderr collected; exit 0; workspace isolated.
Negative: executable missing, nonzero exit, untrusted shell interpolation rejected, required isolation unavailable.
Boundary: zero output, high output, deadline at exit, cancellation after exit, cancellation repeated.
Faults: stuck child, child spawning descendants, output producer blocking, SIGTERM ignored, cleanup failure.
Selection: choose this skill for runtime process behavior, not for scheduler fairness, agent protocol, DAG validation, UI, or artifact retention.
Proof levels: source inspection < synthetic model < local subprocess < disposable-host isolation < real product integration. Do not promote a lower level to a higher one.
