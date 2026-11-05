# Bounds, resource safety and performance evidence

- Static Kahn/DFS topological processing is O(V+E) with linear queue; using a priority queue for deterministic minimum-ID extraction is typically O((V+E)log V). Cycle witness via DFS is O(V+E).
- Reachability BFS/DFS is O(V+E) per query. Running it for all nodes can become expensive; do not present it as free.
- Enumerating every topological ordering can be exponential, and all directed paths can be exponentially numerous. Use bounded small-graph exhaustive tests; do not enumerate for production-size graphs.
- A weighted DAG longest path (with all required nonnegative integer weights) gives a **dependency-only** critical-path measure, not a build wall-clock prediction. Queueing, retries, heterogeneous agent capabilities and caches invalidate a direct timing interpretation.
- Guard maxima for nodes, edges, fan-in/fan-out, depth/label size, generated matrix cardinality and processing budgets. Defaults in the fixture are *illustrative*, not validated product capacity.

Benchmarks: record exact input distributions, node/edge cardinality, graph family (chain, wide, dense, disconnected), runtime/build versions, memory, CPU and results. Do **not** invent throughput or choose incremental algorithms from a paper's benchmarks as though workloads match.

Source foundations: [petgraph toposort complexity](https://docs.rs/petgraph/latest/petgraph/algo/fn.toposort.html), [Kelley critical path](https://doi.org/10.1287/opre.9.3.296), [Pearce–Kelly empirical study](https://doi.org/10.1145/1187436.1210590).
