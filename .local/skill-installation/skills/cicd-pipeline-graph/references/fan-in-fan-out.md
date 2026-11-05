# Dependency reachability, fan-in/fan-out and expansion

Define `fan-in(v) = |needs(v)|`, `fan-out(u) = |direct_dependents(u)|`. These counts are **not** concurrent process slots. Build both adjacency directions. Use bounded BFS/DFS to compute ancestors and descendants, counting each distinct reachable node once; do not enumerate all directed paths without approved caps (path count can grow exponentially even in a small DAG).

**Fan-in gate**: All prerequisites must satisfy the specified predicate before a pending node can become dependency-eligible. **Fan-out**: Completing one node may unlock many successors, but no scheduler reservation is implied. **Join node**: All required incoming prerequisites must meet policy.

**Expansion distinction**: Matrix fan-out (multiple jobs generated from a parameter combination) is a distinct contract: axes and cardinality bounds, stable expanded IDs, mapping of predecessor edges (all-to-all / pairwise / aggregate), conditional inclusion and failure policy must be explicitly specified. Do not infer this from direct fan-out edges.

Frontier = theoretical dependency-eligible group at a given graph epoch and status snapshot, not a transactional queue state. Re-evaluate under observed state transitions; do not treat a pure function result as an authorized execution claim.
