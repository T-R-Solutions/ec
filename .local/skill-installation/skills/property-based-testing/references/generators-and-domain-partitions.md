# Generator Design, Domain Coverage and Reject Limits

> Ec-specific rules below apply only to an Ec consumer whose current contract establishes them.

## Partition deliberately

Produce **valid**, **invalid-but-structured** and **arbitrary malformed bytes** as separate streams. These have different oracles: valid input must behave semantically; structured-invalid must return controlled errors; malformed bytes must not bypass resource limits, corrupt state or cause an unapproved crash. Document accepted rejection behavior.

Use boundary partitions: empty and singleton, min/max signed integer, zero, duplicates, long lists, duplicate keys, Unicode combining characters, invalid UTF-8, nested depth, unknown enum, stale revision, mixed scopes and version changes **only where the contract covers them**.

## Build valid inputs constructively

Compose strategies from valid primitives rather than `assume(is_valid(x))` on arbitrary bytes. High discard rates can result in vacuous/inefficient tests. For sequential state machines, generate commands from current model state and deliberately inject invalid operations in a separate partition.

## Budget and sampling

Record cases per partition and budget. Random generators can have sampling bias; do not claim uniform distribution without proof. Include deterministic known examples and finite exhaustive boundaries. Record `generated`, `accepted`, `rejected`, `invalid`, `malformed`, and runtime version. Separate generator test coverage from the target program's code coverage.

## Parser and resource bounds

Keep input byte limit, nesting/depth limit, CPU budget, timeouts, and explicit overflow/rejection semantics. Never run untrusted fixture output with elevated permissions or production secrets. For YAML, honor project authority and actual parser/schema versions; don't embed a new Ec schema here.
