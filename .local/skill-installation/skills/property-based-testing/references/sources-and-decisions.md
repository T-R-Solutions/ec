# Source Register and Engineering Decisions

> Imported authoring record. Research dates, consultations and execution claims below belong to the supplied source package; they were not reproduced in this adaptation. Referenced validation scripts, fixtures, product norms and logs are not bundled unless present in this folder. Verify their availability and provenance before use. No historical PASS is current acceptance.

**Retrieved and inspected for 2026-11-01.** Separate normative specifications, maintained project documentation, peer-reviewed research and local decisions. A link does not prove that tests ran against its tool.

| ID | Source | Type | Used for / limitation |
|---|---|---|---|
| O01 | [Hypothesis: Stateful testing](https://hypothesis.readthedocs.io/en/latest/stateful.html) | Tool author documentation | Rules, preconditions, invariants, commands; not Ec integration |
| O02 | [Hypothesis: Strategies reference](https://hypothesis.readthedocs.io/en/latest/reference/strategies.html) | Tool documentation | Constructed strategies, target domains; no distribution guarantee |
| O03 | [Hypothesis: API/settings/reproducers](https://hypothesis.readthedocs.io/en/latest/settings.html) | Tool documentation | Examples, budgets, replay; version-specific behavior |
| O04 | [proptest on docs.rs](https://docs.rs/proptest/latest/proptest/) | Maintained Rust API | Generated values, shrinkers, macros; Rust tooling not present here |
| O05 | [proptest TestRunner](https://docs.rs/proptest/latest/proptest/test_runner/struct.TestRunner.html) | Maintained Rust API | Seeds and persistence; defaults vary by version |
| O06 | [proptest state machine guide](https://github.com/proptest-rs/proptest/blob/main/book/src/proptest/state-machine.md) | Maintained framework docs | Sequential transition models; concurrent mode not implied |
| O07 | [Rust Fuzz Book](https://rust-fuzz.github.io/book/cargo-fuzz.html) | Maintained Rust fuzz docs | cargo-fuzz invoking libFuzzer; actual Rust sanitizer target not executed |
| O08 | [LLVM libFuzzer documentation](https://llvm.org/docs/LibFuzzer.html) | Compiler project docs | Coverage-guided targets/corpus/reproducers; libFuzzer remains maintained for important fixes, original authors stopped active development |
| O09 | [fast-check](https://github.com/dubzzz/fast-check) | Library project documentation | TypeScript/JS generation, shrinking and replay; no installed fast-check |
| O10 | [Python unittest](https://docs.python.org/3/library/unittest.html) | Standard library documentation | Local executable tests only; stdlib generators are not Hypothesis |
| O11 | [Python random](https://docs.python.org/3/library/random.html) | Standard library documentation | Reproducible seeded fixture on documented runtime; not cryptographic randomness |
| O12 | [Python itertools](https://docs.python.org/3/library/itertools.html) | Standard library documentation | Bounded exhaustive small domains |
| O13 | [YAML 1.2.2](https://yaml.org/spec/1.2.2/) | Language spec | Relevant only when an approved Ec YAML schema is being tested; this skill does not define it |
| O14 | [OWASP CI/CD security](https://cheatsheetseries.owasp.org/cheatsheets/CI_CD_Security_Cheat_Sheet.html) | Security guidance | Prevent secrets/unsafe fixture execution; no security certification |

## Decisions

1. The **generic method** never encodes Ec product contracts. The project norm, current spec and relevant Ec domain skills own them.
2. The runnable **dependency-free Python teaching fixture** is deliberately small and deterministic. It is not Hypothesis, proptest, libFuzzer, or a production fuzz campaign.
3. The Rust proptest and Python Hypothesis integration templates are **not executed** unless those tools and genuine implementations are supplied. No dependency installation was attempted in the audited environment.
4. Demonstrate two distinct domain types: a framed integer parser and a revisioned value store. Neither is an Ec resource configuration format or backend.
5. Use negative controls that change **behavior** rather than only source text to verify test-oracle sensitivity.
6. Normalized reports may be JSON as validation artifacts; they must not be adopted as Ec declarative configuration.

## Evidence discipline

A successful generated suite establishes only that the samples exercised did not violate the specified oracle under the observed environment. It does **not** prove absence of defects, absence of races, spec correctness, uniform generation, or real product integration. Record all `NOT EXECUTED` dependencies.
