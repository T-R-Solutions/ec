# Topological ordering versus generations

For each edge `u→v`, `u` must precede `v`. A DAG often has several valid linear orders. Determinism is a **tie-break policy**, not a graph theorem.

**Kahn FIFO:** Initialize indegrees, enqueue all zero-indegree vertices, remove one, decrement successors. O(V+E) when queues/adjacency are linear; insertion order may affect output. **Kahn heap:** Store the *global* currently ready set in a priority queue; at each removal take smallest canonical ID. Complexity O((V+E)log V) with straightforward heap, as used by the fixture. **DFS topo:** Also O(V+E) with visited state; must detect cycles and avoid recursion exhaustion.

**Generations:** At each round, take *all* nodes with zero indegree in that round; unlock their successors for the next round. The resulting ordered **batches** describe dependency depth. Concatenating sorted generations is not necessarily the lexicographically smallest global topological order. The cross-language fixture specifically detects this difference.

Never interpret a layer as one simultaneous wall-clock dispatch. A scheduler must still apply resources, availability, quotas, reservations and authorization.

Independent sources: [Python graphlib](https://docs.python.org/3/library/graphlib.html), [NetworkX DAG](https://networkx.org/documentation/stable/reference/algorithms/dag.html), [Kahn 1962](https://doi.org/10.1145/368996.369025).
