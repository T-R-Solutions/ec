# Run Histories, Bounded Faults, and Evidence-Gate Design

> Use the destination's existing requirements and evidence format; named workflow documents are examples, not prerequisites. Optional SDD mapping is configured outside this skill. Commands, fixture paths and historical execution claims from the source package are illustrative or imported evidence unless the corresponding files are present and independently checked; they are not current test results or bundled executable tools.

## Event history rather than a single final status

A contract test must capture the *observable history*: request ID, attempt ID, actor, scope, pinned configuration/source revisions, agent/lease generation, event sequence, event time and received time, state, command confirmation and external-effect receipt. Distinguish a user clicking **Cancel**, an accepted cancel request, agent acknowledging cancellation, successful completion racing with cancellation, and a terminal `cancelled` status.

A reference-only simplified trace used by `validation/oracles.py` is:

```text
queued → claimed → running → succeeded | failed | timed_out | lost
                    ↘ cancel_requested → cancelled | failed | succeeded | timed_out | lost
queued → cancel_requested → cancelled
```

These transitions are **synthetic test values**, not an Ec normative state-machine enumeration. The actual `ec-run-execution-operations` and `ec-agent-control-protocol` owners and the feature's project workflow decide the real states. A retry is a distinct attempt under an explicit policy; the test must never infer external exactly-once behavior from a unique local database row.

## Fault matrix

| Fault injected in isolated Ec environment | Observable oracle |
|---|---|
| Duplicate webhook/run trigger | Deduplication behavior matches actual policy; no accidental duplicate admission where prohibited |
| Stale agent lease/fencing generation | Late worker cannot overwrite a newer authoritative result |
| Heartbeat loss | UI distinguishes temporary loss/unknown versus confirmed terminal outcome |
| Reordered event chunks | Sequence/correlation prevents old status overriding new |
| Lost HTTP response after commit | Idempotent retry/reconciliation disambiguates when possible; unknown otherwise |
| Agent cancellation races with success | Terminal status and external effects reflect actual observed precedence and policy |
| Two concurrent publish requests | No unreviewed lost update; scope/revision/approval kept consistent |
| Database transaction abort | Partial durable status not represented as confirmed; audit/error outcome captured |
| Git publication fails or is rejected | Do not mark effective revision changed; reconcile explicit partial state |
| Secret reference unresolved at execution | Fail or block according to policy; never dump secret values into test artifacts |
| Delayed browser poll from previous project | Tenant/scope and sequence mismatch discarded; no cross-scope data flash |
| Outage during evidence collection | Criterion becomes inconclusive/not verified rather than success |

Use failure injection only in authorized, disposable environments. Avoid external real deployments, destructive production operations, real secrets and uncontrolled sustained concurrency. When a feature lacks a fault injector, provide a bounded specific plan and leave its status not verified rather than pretending a mock has demonstrated end-to-end resilience.

## Oracle sensitivity and false-confidence traps

- A single oracle can be faulty; pair independent expected snapshots with both positive examples and intentionally defective observations. Failure detection by the oracle is necessary but not sufficient for full assurance.
- Use seeded metamorphic scenarios to exercise equivalent mapping-key presentation, no-op editor transitions and reference immutability; do not use unconstrained arbitrary YAML text as “valid Ec resources”.
- A passing suite exercising only stubs can demonstrate that the stub agrees with itself. Cross-validate with a second observation path and separate expected dataset.
- Report mutation probe detection and surviving mutants, not arbitrary "100% coverage". Distinguish artificial mutations of the **harness** from mutation coverage of actual Ec code.
- An HTTP mock may demonstrate whether the Vue view model *would* label `202 Accepted` as pending, but cannot verify that real Ec APIs emit a correct receipt.
- Run tests with real authenticated actors, scopes, revisions, and real PostgreSQL transactions before certifying backend authorization or concurrency.

## Evidence reporting contract

For each `AC`: command/arguments, target service and commit, actual specification/schema revision, environment and isolation, RNG seed, real authenticated principal and authorization scope, timestamps, timeout and trace, output hash, sensitive-data redaction, expected/observed result, scenario class (positive/negative/boundary/fault/concurrency), evidence tier, and limitations.

Five project workflow statuses: `APROVADO` (verified against the current inputs), `REPROVADO` (observed contrary to contract), `NÃO VERIFICADO` (required evidence absent), `INCONCLUSIVO` (observed but insufficient to decide), `NÃO APLICÁVEL` (justified outside scope). Do not report a passing synthetic model as `APROVADO` for Ec. When the involved file, schema, spec, policy or implementation commit changes, recompute the input hash and re-run affected criteria.

## Included fixture layers

- `test_oracles.py`: type-sensitive independent comparison, stale-evidence gate and state histories, plus bounded deterministic samples.
- `test_reference_model.py`: synthetic product state behavior and negative rejections, with no YAML parsing.
- `run_contract_fixture.py`: 22 reference-only cross-layer scenarios and a fail-closed `--mode ec`.
- `run_mutation_probes.py`: deliberate incorrect observations (extensions dropped, statuses falsely confirmed, stale evidence approved) must be rejected by the oracles.
- `run_sqlite_integration.py`: transaction/CAS behavior under local SQLite; does not test PostgreSQL, Git or Ec.
- `ui_contract_model.ts`, `ui_contract_test.ts`: strict TypeScript behavior of a synthetic view model; not a rendered Vue/browser/accessibility test.
- `run_project workflow_fixture.py`: actual original project workflow **structural** validator on temporary extraction, including a bad-path negative.

No test here validates real Ec services. The next phase requires an project-approved integration adapter and application checkout.
