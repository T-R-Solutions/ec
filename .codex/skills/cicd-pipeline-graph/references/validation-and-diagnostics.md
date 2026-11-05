# Validation order, bounded resources and precise diagnostics

> Ec-specific rules below apply only to an Ec consumer whose current contract establishes them.

1. Check outer structure and size before iterating. Verify node record shape and stable ID length/content; no implicit coercion.
2. Detect duplicate node IDs before indexing, not after constructing a dict that would overwrite them.
3. Reject duplicate prerequisite entries and self-dependencies. Do not normalize silently unless the approved contract explicitly allows it.
4. Validate each reference against the completed node table. Report the **consumer**, missing prerequisite, logical location and deterministic diagnostic code.
5. Construct both predecessor/successor adjacency with explicit fan-in/fan-out/edge limits.
6. Run cycle detection; if cyclic, provide a reproducible directed cycle witness with repeated start at the end (e.g. `a -> b -> c -> a`). Do not report a non-contiguous path or just unordered nodes.
7. Only accept a graph once structural + semantic validation and any domain policy hooks succeed.

The reference fixture emits `INVALID_INPUT`, `INVALID_NODE`, `INVALID_ID`, `DUPLICATE_NODE`, `INVALID_NEEDS`, `DUPLICATE_EDGE`, `INVALID_REFERENCE`, `SELF_CYCLE`, `UNKNOWN_REFERENCE`, `NODE_LIMIT`, `EDGE_LIMIT`, `FANOUT_LIMIT`, and `CYCLE`. These are **fixture** codes; no Ec API error contract is implied.

Cap error counts in an implementation that aggregates errors; avoid exponential cycle/path enumeration. A cycle witness is one example among possible cycles and should not be described as all defects. Test diagnostics with repeated runs/input permutations, unsupported types, control characters, Unicode, size boundaries and deeply chained inputs.
