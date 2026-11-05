# Test Scenarios and Oracles — Ec YAML Resource Contracts

> Use the destination's existing requirements and evidence format; named workflow documents are examples, not prerequisites. Optional SDD mapping is configured outside this skill. Commands, fixture paths and historical execution claims from the source package are illustrative or imported evidence unless the corresponding files are present and independently checked; they are not current test results or bundled executable tools.

**Status:** every case below is **planned**, except the explicitly documented static checks and project workflow checks in `VALIDATION.md`. Examples are synthetic and do **not** establish that any particular `kind` or field exists in Ec.

## Skill selection: routing corpus

| ID | Representative request | Expected choice | Reason |
|---|---|---|---|
| SEL-01 | Add a backward-compatible field to a versioned YAML resource | Activate | Resource contract semantics and migration. |
| SEL-02 | Reject duplicate keys currently accepted by the parser | Activate | Parser boundary and loss prevention. |
| SEL-03 | Evaluate YAML 1.1 versus 1.2 boolean resolution | Activate | Scalar/tag resolution affects typed contracts. |
| SEL-04 | Fix UI spacing and colors without changing schemas | Do not activate | Layout does not alter a resource contract. |
| SEL-05 | Preserve comments while switching Visual/YAML editors | Boundary consultation only | This skill can describe parser limits; the editing specialty owns the implementation. |
| SEL-06 | Fix a scheduler lease without changing resource fields | Do not activate | Distributed execution, not schema design. |
| SEL-07 | Fix publication of an outdated revision without schema changes | Do not activate | Publication and reconciliation concern. |
| SEL-08 | Migrate a pool schema and change its Visual editor | Activate as the contract-primary skill | Schema migration requires this skill; UI changes require a separate specialty. |
| SEL-09 | Introduce a new `kind` based only on an illustrative example | Activate to investigate; block unapproved implementation | An approved project contract contract is required; do not invent capabilities. |
| SEL-10 | Implement RBAC without declarative configuration changes | Do not activate | Authorization domain work does not inherently require a schema. |

These expected outcomes are **not** measured results of an agent selecting the skill. A real assessment must invoke the model with fixed catalog/context, capture its actual choices, and calculate class-specific performance.

## Technical cases: fixtures and oracles **to implement in Ec**

| ID | Condition / input | Validation stage and expected oracle | Risk addressed |
|---|---|---|---|
| T-01 | Simple YAML with fields approved by a real specification | Parsing succeeds; declare an Ec resource valid only after typed and domain validation. | Confusing parse success with contract acceptance. |
| T-02 | `name: a\nname: b` | Reject the duplicate **before** overwrite in map construction. | Data loss. |
| T-03 | `a: 1\n"a": 2` | Reject equivalent keys when both resolve to the same tag/value. | Disguised duplicates. |
| T-04 | `0xB: x\n11: y` | Compare numeric key identities if supported by the chosen schema/parser; otherwise record the unsupported case. | Canonical identity. |
| T-05 | `enabled: "false"` versus `enabled: false` | Distinguish a string from a boolean according to the selected parser and typed contract. | Undocumented coercion. |
| T-06 | `feature: on` | Check YAML 1.2 behavior against 1.1-style variants; reject contract-incompatible resolution. | Scalar-resolution drift. |
| T-07 | Missing field, `null`, `""`, or `false` | Validate these states independently unless the actual contract declares an equivalence. | Incorrect defaults. |
| T-08 | `!Custom` | YAML can represent tags; prohibit construction of unapproved executable objects under Ec policy. | Code execution / tag abuse. |
| T-09 | Cyclic or recursively referenced alias | Do not call it a universal syntax error; apply explicit Ec policy or describe decoder limitations. | Recursion and resource cost. |
| T-10 | Heavily reused aliases or deeply nested input | Enforce documented limits and controlled errors; derive values from measurements. | Denial of service. |
| T-11 | Stream with two `---` documents | Accept or reject according to an approved rule; never silently ignore the second document. | Silent data loss. |
| T-12 | Unknown field | Explicitly reject or preserve under an approved extension contract; never silently drop it. | Round-trip data loss. |
| T-13 | Unsupported `apiVersion` in a real resource contract | Report a version error with no silent fallback. | Compatibility regression. |
| T-14 | Missing cross-resource reference | Report referential-integrity error with a logical path; do not call it a parse error. | Broken references. |
| T-15 | Reordered mapping keys | No change to typed semantics. | Incidental ordering. |
| T-16 | Reordered step sequence | Preserve the changed ordering when the approved contract makes sequence order meaningful. | Execution-order error. |
| T-17 | Version conversion with a complete mapping | Preserve the resource's meaning; verify with semantic fixtures/diffs. | Migration regression. |
| T-18 | Version conversion with irrecoverable field meaning | Fail explicitly rather than discard information. | Semantic data loss. |
| T-19 | Invalid nested field | Return actual path, error code, and cause; provide a source span only if supported. | Fabricated diagnostics. |
| T-20 | Document containing comments and anchors | Do not require byte-identical preservation from a parser without CST/source metadata; delegate editing preservation. | Conflating data and presentation. |
| T-21 | Empty input, invalid UTF-8, NUL, oversized input | Controlled outcome at defined parser/contract limits, recorded as an actual test result. | Robustness and abuse. |
| T-22 | Supported resource and reserialization | `decode(encode(decode(y)))` is semantically equivalent for supported contracts; byte identity is not required. | Round-trip consistency. |

## Future execution and evidence

For every `T-XX` executed in real code, capture the exact command, parser and schema revisions, input, output, and oracle result (`PASS`, `FAIL`, `INCONCLUSIVE`, `NOT RUN`). Inclusion in this table **does not** mean a test passed. Pin the YAML Test Suite release or commit. Use generators and `cargo fuzz` only if the relevant project, toolchain, and harness are available. For property-based tests, record seeds, generation strategies, shrunk counterexamples, and observed results.

### Negative criteria that block approval

- The skill declares another canonical configuration format alongside YAML or copies an Ec normative obligation instead of referencing the source.
- The skill assumes an unobserved API, `kind`, version, parser, CI command, or library.
- A parser test is presented as proof of business logic or authorization.
- Migration silently removes unknown fields, changes behavior-affecting defaults without a contract, or rewrites immutable historical revisions.
- The project workflow bridge is treated as a specification, or merely referencing another skill activates it automatically.
- A test not executed is described as passing.
