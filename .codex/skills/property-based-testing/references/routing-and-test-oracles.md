# Skill Routing and Selection Oracles

> Ec-specific rules below apply only to an Ec consumer whose current contract establishes them.

## Positive selection

P01 parser semantic round-trip on generated valid resources; P02 byte-level fuzzing of malformed inputs; P03 shrinking a found counterexample; P04 state-machine event sequences; P05 metamorphic and differential properties; P06 reproducible seed/corpus replay; P07 mutation sensitivity check.

## Negative selection

N01 general testing strategy; N02 add a specific Ec YAML field; N03 browser component/E2E test implementation without generated properties; N04 claim a concurrent scheduler is safe based on random single-process tests; N05 performance profiling/benchmarking; N06 declare WCAG compliance by running random tests.

## Boundary selection

B01 generated data for browser testing: only this skill's generator/oracle part, while E2E harness remains `frontend-testing`. B02 authorization invariants: this skill implements test generation; approved domain policy remains `ec-effective-permissions`. B03 state-machine concurrency: generate sequential histories here; use distributed/concurrency expert methods and real backends for races.

## Evidence matrix

| Property ID | Synthetic fixture | Expected outcome | Real Ec acceptance |
|---|---|---|---|
| PROP-ROUNDTRIP | framed integer sequence | exact value equality + canonical byte encoding | NOT EXECUTED; no Ec YAML parser supplied |
| PROP-MALFORMED | bounded byte payloads | valid canonical or controlled ValueError, no unexpected exception | NOT EXECUTED; no Rust parser supplied |
| PROP-SORT | integer array sort | order + `Counter` multiset + idempotence | NOT EXECUTED; not an Ec API |
| PROP-CAS | revisioned synthetic store | independent model after each command | NOT EXECUTED; no PostgreSQL/HTTP API supplied |
| PROP-SHRINK | intentional buggy sorting | smaller still-failing witness with same signature | NOT EXECUTED on actual product |
| PROP-MUTANTS | synthetic contract mutants | each targeted defect causes expected oracle failure | NOT EXECUTED against Ec |

Avoid confusion with `ec-product-contract-test-harness`: it owns mapping actual Ec feature ACs to real integration evidence; this skill owns the generic testing technique.
