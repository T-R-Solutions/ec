# Peer-Reviewed Research — What It Supports and Does Not Support

> Imported authoring record. Research dates, consultations and execution claims below belong to the supplied source package; they were not reproduced in this adaptation. Referenced validation scripts, fixtures, product norms and logs are not bundled unless present in this folder. Verify their availability and provenance before use. No historical PASS is current acceptance.

These sources were verified from ACM/IEEE or research-institution records. Bibliographic year, DOI and claim are kept together; no benchmark numbers are transferred to the Ec.

| ID | Peer-reviewed work | Evidence used | Scope limit |
|---|---|---|---|
| P01 | Claessen & Hughes, *QuickCheck: A Lightweight Tool for Random Testing of Haskell Programs*, ICFP 2000, DOI [10.1145/351240.351266](https://doi.org/10.1145/351240.351266) | Expressing properties and trying automatically generated inputs, with case studies and pitfalls | Haskell/tool of its era, not effectiveness measurements in Rust/Vue |
| P02 | Runciman, Naylor & Lindblad, *SmallCheck and Lazy SmallCheck: Automatic Exhaustive Testing for Small Values*, Haskell Symposium 2008, DOI [10.1145/1411286.1411292](https://doi.org/10.1145/1411286.1411292) | Exhaustive enumeration within bounded depth complements random generation | Bounded domains only; no whole-program exhaustiveness |
| P03 | Claessen et al., *Finding Race Conditions in Erlang with QuickCheck and PULSE*, ICFP 2009, DOI [10.1145/1596550.1596574](https://doi.org/10.1145/1596550.1596574) | Combining generated commands and controlled scheduling for concurrent fault detection in a case study | Does not prove sequential property tests find distributed races or PostgreSQL correctness |
| P04 | Barr et al., *The Oracle Problem in Software Testing: A Survey*, IEEE TSE 2015, DOI [10.1109/TSE.2014.2372785](https://doi.org/10.1109/TSE.2014.2372785) | Independent correctness judgments, reference models, specifications and metamorphic properties | A survey of strategies, not validation that our oracles are complete |
| P05 | Segura et al., *A Survey on Metamorphic Testing*, IEEE TSE 2016, DOI [10.1109/TSE.2016.2532875](https://doi.org/10.1109/TSE.2016.2532875) | Metamorphic relations when expected output is hard to compute | Relations require domain justification; some are too weak or incorrect |
| P06 | Inozemtseva & Holmes, *Coverage Is Not Strongly Correlated with Test Suite Effectiveness*, ICSE 2014, DOI [10.1145/2568225.2568271](https://doi.org/10.1145/2568225.2568271) | Coverage alone is not a reliable substitute for fault-detection evidence when test-suite size is controlled | Empirical data from Java systems; no universal numerical threshold applies |

## Research-to-method trace

- **Generator diversity and explicit oracle (P01, P04)** → define meaningful properties and positive/negative domains before generating data.
- **Small boundary enumeration (P02)** → complement random samples with exhaustive small classes, recording the bound.
- **State transitions and interleavings (P03)** → build reference models and, for concurrency, demand an actual scheduling/concurrency harness.
- **Metamorphic tests (P05)** → verify invariants on related inputs independently of implementation code.
- **Quality signals (P06)** → report mutant detection and regressions instead of proclaiming correctness from a coverage number.

## No extrapolation

None of these publications establishes that a particular Ec resource, Rust agent, CI scheduler, YAML parser or Vue renderer is correct. They motivate **test techniques** subject to approved contracts and executed integration tests. The included synthetic fixtures are explicitly separate from scientific results.
