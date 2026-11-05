# Directed cycle detection and witness construction

A directed graph is a DAG if and only if it has no directed cycle. Kahn's algorithm consumes every node exactly when it has no cycle (after structural validation). An incomplete ordering indicates a cycle, but leftover nodes may include descendants of the cycle: **leftover nodes are not themselves a valid witness**.

Use explicit DFS colors (white/unvisited, gray/on recursion stack, black/finished). A back edge to a gray node creates a directed cycle; extract the path from that node along the current DFS stack and append the start node. Iterative DFS avoids recursion-depth failure on long input. An SCC algorithm can identify all strongly connected *regions* if multi-cycle diagnosis is required, but an SCC isn't itself a sequence of directed edges.

Require deterministic traversal of nodes and adjacency when stable diagnostics matter. Self loops can be caught earlier. The fixture tests 2-node/3-node cycles and a chain of 3,000 nodes. Product algorithms must also test large graphs, memory and adversarial inputs, with explicit limits and timeouts.

Sources: [Tarjan 1972](https://doi.org/10.1137/0201010), [petgraph toposort](https://docs.rs/petgraph/latest/petgraph/algo/fn.toposort.html), [Python graphlib](https://docs.python.org/3/library/graphlib.html).
