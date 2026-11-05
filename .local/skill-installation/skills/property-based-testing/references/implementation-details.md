# Detailed decisions and failure cases

Read only for a decision needing more detail than the entry point. Apply the relevant steps to the
actual task; the sections are not a mandatory artifact or test checklist. Use existing project
contracts in their actual format. File names such as spec.md, plan.md or tasks.md are examples, not
required dependencies. Other skills are optional expertise, never enforcement mechanisms.

Ec references below are conditional consumer examples. They impose no Ec documents, YAML format,
stack or product policy on a generic task.

## Method

### 1. Write the oracle before choosing a generator

Define `REQ/AC → domain → preconditions → property/invariant → counterexample → observable
decision`. Choose independent expected behavior: mathematical invariant, simpler reference model,
published protocol/schema, approved state transition, trusted differential implementation or
justified metamorphic relation. Avoid vacuous properties, copied implementation logic, tautological
assertions and checking only for absence of exceptions. When comparing two implementations, document
shared assumptions and correlated-bug risk. See [property oracles and
metaprops](property-oracles-and-metaprops.md) and [routing and test
oracles](routing-and-test-oracles.md).

### 2. Partition the input domain and define useful generators

Generate **valid**, **invalid-but-well-formed**, and **malformed** inputs separately. Include
minimum/maximum, empty, duplicate, Unicode, size/depth, references, state transitions and
adversarial boundaries where relevant. Prefer constructing valid data by design rather than
filtering most draws with assumptions/discards. Record every generator's constraints and the
proportion of each case type; no invented distribution or uniformity guarantee. See [generators and
domain partitions](generators-and-domain-partitions.md).

### 3. Control search, randomness and replay

Record toolchain versions, seed, settings, generator implementation/version, search budget,
environment, and normalized failing input. Add **enumeration of small domains** where affordable;
randomized testing explores samples, not all inputs. Preserve minimized cases as stable regression
fixtures, and verify reproducer independently before filing a defect. Do not treat a passing seed as
proof. See [reproducibility and corpus](reproducibility-and-corpus.md).

### 4. Shrink without invalidating meaning

Minimize failing sequences and input size while preserving the original failure **and
preconditions**. Use framework-native shrinkers where available, otherwise bounded
`ddmin`/domain-aware simplifiers with a predicate recheck. Do not report smallest globally possible
case without proof; call it a locally reduced counterexample. Record the original and reduced
witness, number of checks, seed and failure signature. See [shrinking and
minimization](shrinking-and-minimization.md).

### 5. Test state machines and concurrency models

Define independent reference state, enabled commands, preconditions, transitions, postconditions and
invariants. Generate sequences with valid and rejected actions, compare output and state after every
step. Separate sequential model checking from concurrent execution and real transactional
guarantees; a deterministic in-memory revision model does not establish linearizability or
PostgreSQL atomicity. See [state machine testing](state-machine-testing.md).

### 6. Select frameworks, fuzz targets and controls appropriately

Use Hypothesis (Python), proptest (Rust), fast-check (TS/JS) or equivalent **only when available and
verified**. For coverage-guided fuzzing, use libFuzzer/cargo-fuzz where a genuine instrumented build
exists, with resource limits and sanitizer policy appropriate to the host. Control process
crashes/timeouts/OOM; isolate dangerous inputs. Distinguish generative properties, small exhaustive
testing, feedback-guided fuzzing, and performance testing. See [framework
matrix](framework-matrix.md), [fuzzing parser and security](fuzzing-parser-and-security.md).

### 7. Validate the quality of the tests

Demonstrate positive/negative/boundary selection cases. Measure whether deliberate, plausible faults
are caught and whether failure messages are actionable. Report surviving mutants and untested
contracts. Do not infer real defect-detection effectiveness from mutation count alone; coupled
synthetic mutants are a sensitivity check, not independent field evidence. Monitor discarded
generations, corpus growth and flaky failures. See [mutation oracle
quality](mutation-oracle-quality.md).

### 8. Report evidence conservatively and hand off

Deliver: property map with AC, generator definitions, executable command, budgets/seeds, original
and reduced reproducer, observed assertion, versions, pass/fail/rejected counts, artifacts and
pending actual integration. Mark runtime/tool not available as **NOT EXECUTED**, not
PASS/SKIP-as-success. For project workflow, link `spec.md` AC → `plan.md` rationale → `tasks.md` →
`evidence/` with hashes and environmental provenance. See `evidence-and-reporting.md`.


## Further references by decision

Read only the reference relevant to the unresolved decision. Research registers describe imported authorship, not current execution.

- [Deliverables, project workflow Integration and Reporting](evidence-and-reporting.md): evidence and reporting.
- [Framework Selection Without Inventing Runtime Evidence](framework-matrix.md): framework matrix.
- [Coverage-Guided Fuzzing, Parser Robustness and Safety](fuzzing-parser-and-security.md): fuzzing parser and security.
- [Generator Design, Domain Coverage and Reject Limits](generators-and-domain-partitions.md): generators and domain partitions.
- [Mutation Sensitivity and Oracle Adequacy](mutation-oracle-quality.md): mutation oracle quality.
- [Properties, Independent Oracles and Metamorphic Relations](property-oracles-and-metaprops.md): property oracles and metaprops.
- [Deterministic Replay, Evidence and Regression Corpus](reproducibility-and-corpus.md): reproducibility and corpus.
- [Skill Routing and Selection Oracles](routing-and-test-oracles.md): routing and test oracles.
- [Peer-Reviewed Research — What It Supports and Does Not Support](scientific-evidence-and-limits.md): source provenance or a disputed recommendation.
- [Shrinking and Counterexample Minimization](shrinking-and-minimization.md): shrinking and minimization.
- [Source Register and Engineering Decisions](sources-and-decisions.md): source provenance or a disputed recommendation.
- [Model-Based and Stateful Property Testing](state-machine-testing.md): state machine testing.
