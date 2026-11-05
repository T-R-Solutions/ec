# Independent DAG testing oracles and fault models

> Ec-specific rules below apply only to an Ec consumer whose current contract establishes them. Commands, fixture paths and historical execution claims from the source package are illustrative or imported evidence unless the corresponding files are present and independently checked; they are not current test results or bundled executable tools.

| Invariant | Oracle | Negative examples |
|---|---|---|
| Structural validation | Independent expected problem code and location | Missing reference, duplicate node/edge, self/cross cycle |
| Order completeness | Set/length equality + all edge positions | Dropped node, reversed edges, non-topological order |
| Determinism | Permuted declaration and needs arrays | Insertion-order dependence, broken tie-break |
| Generations | Longest predecessor-depth equation | Incorrect batch propagation |
| Cycle witness | Each consecutive witness pair exists as an edge; repeated start | Leftover-node list masquerading as cycle |
| Reachability | Independently enumerated path/reachability on small domains | Missing descendant, repeated counts |
| Eligibility | Independently stated success predicate and statuses | Running/failed job treated as ready |
| Edit safety | Snapshot unchanged on rejection | Partially changed published graph |
| Limits | Explicit rejection on boundary | Unbounded expansion/degree |

Fixture evidence: Python `unittest` + `graphlib.TopologicalSorter` comparison for 800 seeded DAGs; exhaustive 4-node graphs (4,096 candidate directed edge sets); long chain 3,000 nodes; 240 shared TypeScript vectors. Their sample counts are documented, not proof of universal behavior. The TypeScript model is a second implementation; correlated defects remain possible.

Mutation testing is a sensitivity check: verify the untouched baseline and validate each modified file syntactically; run a matching behavioral oracle. Do not count compiler/parser failures as caught behavioral mutants. Record survivors rather than implying every real fault is covered.

Real integration should include Rust tests when toolchain available, actual approved Ec YAML contract through its owner, backend job eligibility, revision pinning, queue/lease behavior under restarts, and real UI projection. This package cannot certify those without the application.
