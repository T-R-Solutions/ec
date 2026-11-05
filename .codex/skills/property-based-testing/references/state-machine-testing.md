# Model-Based and Stateful Property Testing

> Ec-specific rules below apply only to an Ec consumer whose current contract establishes them.

Define a finite or abstract **reference model**, a system under test, allowed commands, command generators, transition and observable expected output. Check after *every* action, not just the final state. Record model assumptions, command enablement and error behavior for rejected transitions.

Example synthetic revision store:

- Model state `(revision, value)` begins `(0, None)`.
- `put(expected, value)` succeeds iff `expected == revision`, atomically advancing revision once; else rejects without mutation.
- Generate both matching and intentionally stale revisions. After each command compare actual and model revision/value and response.

This is **not an Ec data model**. No in-memory simulation proves transaction atomicity under multiple processes, lease fencing, database durability or external side-effect exactly-once semantics. For a real CI scheduler, test actual database transactions, concurrent workers, partitions and restarts with real components under controlled failure injection.

Hypothesis provides `RuleBasedStateMachine`, `rule`, `precondition` and `invariant`; `proptest-state-machine` has reference-machine machinery and currently documents sequential support. Concurrent scheduler testing requires additional tool-specific scheduling/linearizability probes, not merely increasing sequence count. Report rejection frequency and missing command combinations.
