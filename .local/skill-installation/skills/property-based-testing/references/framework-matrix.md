# Framework Selection Without Inventing Runtime Evidence

> Commands, fixture paths and historical execution claims from the source package are illustrative or imported evidence unless the corresponding files are present and independently checked; they are not current test results or bundled executable tools.

| Environment | Appropriate tooling | Notes / limitations |
|---|---|---|
| Python | Hypothesis strategies, stateful testing and example database | Choose current pinned version, test `@given` and replay in real environment |
| Rust | proptest; optional `proptest-state-machine` | Pin crate and MSRV, verify cargo lock and generated regression files |
| TypeScript / JavaScript | fast-check | Pin version and replay path/seed; ensure source/bundle supports intended target |
| Rust bytes/parser fuzzing | cargo-fuzz + libFuzzer/sanitizers | Actual compiler/target and resource limits required; no inference from Python fixture |
| Bounded small domain | explicit exhaustive enumeration / SmallCheck-like | Specify finite bound and count; cannot conclude global correctness |
| Generic teaching fixture | Python standard library `random`, `unittest` | Demonstrates the method but provides no auto-shrinker/framework integration guarantee |

**Versions:** As observed in documentation on 2026-11-01, `proptest` docs.rs advertises 1.12.0 (2026-10-10). Current Hypothesis/fast-check docs may evolve; pin exact tested versions before claiming compatibility. The current environment has no `hypothesis`, Rust compiler or cargo; this package does not claim execution of their templates.

## Use the smallest sufficient scope

A fixed known regression remains a normal example test; adding randomization without a useful oracle can reduce diagnosis quality. Prefer structured generators for complex valid inputs. Use coverage-guided fuzzers for code paths/crash/UB exploration, and model-based properties for state semantics. Avoid introducing heavy frameworks unless their fault detection and maintenance costs justify them in the target repository.
