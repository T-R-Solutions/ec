# Source register and engineering decisions

> Imported authoring record. Research dates, consultations and execution claims below belong to the supplied source package; they were not reproduced in this adaptation. Referenced validation scripts, fixtures, product norms and logs are not bundled unless present in this folder. Verify their availability and provenance before use. No historical PASS is current acceptance.

All source dates were checked on 2026-11-01. **Specifications and implementation documentation** describe available contracts; **peer-reviewed research** establishes algorithms or scoped observations; this skill's examples are engineering decisions, **not** an adopted Ec specification.

## Authoritative / maintained technical references

| ID | Reference | Exact supported use | Does NOT establish |
|---|---|---|---|
| O1 | [Python standard library `graphlib.TopologicalSorter`](https://docs.python.org/3/library/graphlib.html) | Prerequisite-oriented graph API, `get_ready`/`done`, `CycleError`, possible insertion-order tie | Ec scheduler or order of execution |
| O2 | [Rust petgraph `toposort`](https://docs.rs/petgraph/latest/petgraph/algo/fn.toposort.html) | Iterative topological order, cyclic graph error, published O(V+E) for this algorithm | Version deployed in Ec; Rust tests here |
| O3 | [Rust petgraph `is_cyclic_directed`](https://docs.rs/petgraph/latest/petgraph/algo/fn.is_cyclic_directed.html) | Directed cycle detection | Exact diagnostic witness from product |
| O4 | [NetworkX DAG algorithms](https://networkx.org/documentation/stable/reference/algorithms/dag.html) | Ancestors, descendants, topological generations, lexicographical order | Python library in Ec or allowed conditional semantics |
| O5 | [NetworkX lexicographical topological sort](https://networkx.org/documentation/stable/reference/algorithms/generated/networkx.algorithms.dag.lexicographical_topological_sort.html) | Lexical tie-break, distinct from arbitrary topo output | Language-independent collation equivalence |
| O6 | [GitHub Actions jobs dependencies](https://docs.github.com/en/actions/how-tos/write-workflows/choose-what-workflows-do/use-jobs) | Example vendor's `needs`, default fail/skip behavior, condition exceptions | Policy of Ec/other CI systems |
| O7 | [Bazel query language](https://bazel.build/query/language) | Dependency graphs, cycles and query semantics depend on graph phase | Interchangeable pipeline jobs and Bazel build graph |

## Research (primary publications)

- R1 Kahn, A. B. (1962). [Topological sorting of large networks](https://doi.org/10.1145/368996.369025), *Communications of the ACM* 5(11):558–562.
- R2 Tarjan, R. E. (1972). [Depth-First Search and Linear Graph Algorithms](https://doi.org/10.1137/0201010), *SIAM J. Computing* 1(2):146–160.
- R3 Pearce, D. J. and Kelly, P. H. J. (2007). [A dynamic topological sort algorithm for directed acyclic graphs](https://doi.org/10.1145/1187436.1210590), *ACM Journal of Experimental Algorithmics* 11.
- R4 Bhattacharya, S. and Kulkarni, J. (2020). [An improved algorithm for incremental cycle detection and topological ordering in sparse graphs](https://doi.org/10.1137/1.9781611975994.153), *SODA* 2020, pp. 2509–2521.
- R5 Kelley, J. E. Jr. (1961). [Critical-Path Planning and Scheduling: Mathematical Basis](https://doi.org/10.1287/opre.9.3.296), *Operations Research* 9(3):296–320.
- R6 Coffman, E. G. Jr. and Graham, R. L. (1972). [Optimal Scheduling for Two-Processor Systems](https://doi.org/10.1007/BF00288685), *Acta Informatica* 1:200–213.

The literature supports known graph algorithms and limited models. It does **not** certify throughput, timing, safety or behavior of Ec, nor prove a particular implementation faster without representative benchmarks. R6's assumptions are restricted to a specific scheduling model, not general CI/CD distributed dispatch.

## Decisions within THIS skill, not product obligations

1. Use generic typed `{id, needs}` nodes **in tests only**, with edge direction `prerequisite → dependent`.
2. Validate strictly and report deterministic problem codes; do not coerce or repair malformed graphs silently.
3. Choose a heap for global lexical ready tie-break; use a separate Kahn iteration for dependency generations. Both must be tested independently.
4. Use a Python stdlib differential oracle and an independent TypeScript implementation. No reliance on network access or private Ec code.
5. Treat a dependency readiness check as a pure function of an **explicit** policy and provided observed state; never mutate operational records.
6. Keep finite limits and report tested sizes; do not describe them as measured capacity of a CI/CD product.
7. Avoid teaching users that an incrementally maintained ordering always outperforms full recomputation. Benchmark on actual workloads first.
