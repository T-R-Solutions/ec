# Peer-reviewed research: evidence and limits

> Imported authoring record. Research dates, consultations and execution claims below belong to the supplied source package; they were not reproduced in this adaptation. Referenced validation scripts, fixtures, product norms and logs are not bundled unless present in this folder. Verify their availability and provenance before use. No historical PASS is current acceptance.

| Publication | Result actually supported | Appropriate use here | Invalid extrapolation |
|---|---|---|---|
| [Kahn 1962](https://doi.org/10.1145/368996.369025) | Algorithm for topological sorting of networks | Valid ordering procedure and DAG invariant | Modern throughput or automatic scheduler correctness |
| [Tarjan 1972](https://doi.org/10.1137/0201010) | Linear-time depth-first algorithms for connectivity and components | SCC/cycle investigations and complexity reasoning | All graph diagnostics run at constant cost |
| [Pearce–Kelly 2007](https://doi.org/10.1145/1187436.1210590) | Proposed dynamic topological sort; compared against alternatives on random sparse/dense DAGs | Evaluate incremental-maintenance trade-offs | It is universally best or should replace full recomputation in Ec |
| [Bhattacharya–Kulkarni 2020](https://doi.org/10.1137/1.9781611975994.153) | Theoretical randomized bound for incremental cycle detection/topological ordering | Understand asymptotic options for repeated insertions | Benchmark result for the user's CI/CD workload |
| [Kelley 1961](https://doi.org/10.1287/opre.9.3.296) | Mathematical basis of critical-path planning with precedence/durations/costs | Explain weighted longest-path concepts on DAGs | Predicted elapsed build time under queues, retries, agents, contention |
| [Coffman–Graham 1972](https://doi.org/10.1007/BF00288685) | Scheduling model and optimal solution in its restricted two-processor setting | Separate precedence graphs from resource scheduling | Any arbitrary CI/CD scheduler with varying durations is optimal |

**Evidence hierarchy.** Formal mathematical guarantees apply only when algorithm assumptions hold. Differential and property-based local tests detect classes of defects in a reference model; they are not a proof of the target implementation. Real Ec acceptance requires its approved spec, real contracts, Rust/DB integration and observed results. Academic publication is not equivalent to published product behavior.

**Unverified here.** No paper from this list is treated as a measured claim of Ec throughput. No petgraph binary, Rust code, actual scheduler, latency sample, run duration predictor or production incident analysis was executed in this package.
