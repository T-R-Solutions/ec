# Harness Architecture, Independent Oracles, and Adapter Contracts

> Use the destination's existing requirements and evidence format; named workflow documents are examples, not prerequisites. Optional SDD mapping is configured outside this skill. Commands, fixture paths and historical execution claims from the source package are illustrative or imported evidence unless the corresponding files are present and independently checked; they are not current test results or bundled executable tools.

## The problem this skill solves

In a single Ec feature, a user can author a desired resource through YAML, a visual editor or a conventional form, publish it through a versioned control plane, be authorized in multiple scopes, reconcile the published revision and run CI jobs against a pinned configuration. Isolated unit tests can all pass even if these layers disagree. This skill owns the **test harness and proof obligations spanning those owners**, not their implementations.

## Authority and contract discovery checklist

- Read the Ec norms, current project workflow feature `spec.md` with `REQ-*`/`AC-*`, affected `plan.md`, `tasks.md` and evidence/convergence; verify real code state and approved test boundary.
- Resolve target `kind`/`apiVersion` from actual validated Ec schema; distinct repository identity for canonical configuration and source code; supported presentation modes and known unrepresentable properties.
- Enumerate real backend authorization predicates, scopes, policy revision, actor identity and explicit approval state. A test-only actor is still a real authenticated principal on an isolated test realm.
- Locate immutable publication receipt/revision, effective reconciliation observation, source revision, queued/run attempt, agent/fencing evidence and audit event as implemented; document absent interfaces without substituting imagined ones.
- Identify safe ephemeral projects and storage, fixture cleanup, seed, bounded timeouts, transition budgets, clock skew and secrets backend references. Do not make CI and integration tests destructive by default.

## Test architecture (conceptual, not implemented Ec APIs)

```text
project specification (REQ/AC) + Ec norms + known implementation contracts
         |
         v
independent golden expectations / policy matrices / traceable case IDs
         |
         v
Ec product-contract harness (comparison, state history, evidence gating)
   |                 |                  |                 |
   v                 v                  v                 v
real YAML/editor   auth + API       version/reconcile   run/agent/
   adapter          adapter             adapter          browser adapter
   |                 |                  |                 |
   +-----------------+------------------+-----------------+
                         |
                         v
                 AUTHORIZED Ec instance
                         |
                         v
          observation and redacted evidence / project workflow convergence
```

An adapter **reads actual behavior**. It is not a fake whose return values are copied from expected values. Suggested capabilities, to be adapted to actual contracts:

| Port | Inputs and observable output | Primary owner |
|---|---|---|
| `parse_validate` | YAML bytes, schema version, structured diagnostics, normalized semantic resource or explicit rejection | `ec-declarative-resource-schemas` |
| `project_roundtrip` | immutable draft reference, mode and edits; complete semantic resource plus produced YAML and diff | `ec-semantic-configuration-projections` |
| `authorize_operation` | authenticated actor, scope, resource/action, policy revision and backend response | `ec-effective-permissions` |
| `publish_revision` | base/publish revision, approval, permission decision, receipt and conflict response | `ec-configuration-publication` |
| `read_history` | immutable revisions, raw canonical YAML and independently queryable effective version | publication/storage owners |
| `read_reconcile` | desired versus observed revision, freshness, event sequence and authoritative effect evidence | publication/reconciliation owner |
| `run_attempt` | pinned config and source revisions, attempt ID, ordered state/events, agent fencing and audit | run/agent/scheduler owners |
| `observe_ui` | visible status, accessible name, keyboard path, announcement and revision displayed | Vue/browser owner |

Do not ship these as invented HTTP endpoints. If no actual Ec integration adapter is available, report **NOT WIRED** rather than silently defaulting to the included synthetic model.

## Oracle independence

- **Goldens:** Construct expected semantic trees from project workflow rules and independently reviewed fixtures, not from the implementation's serializer output. A golden may be typed Python test data or a signed/verified expected rendering, but cannot become an alternative Ec configuration file.
- **Type semantics:** Compare typed scalars, extension fields and ordered sequences. Treat mapping order as presentation only, unless a real schema explicitly gives a field stronger semantics. Preserve unknown supported extension content; do not assume all unknown fields are valid.
- **Metamorphic relations:** Reorder mapping keys; split/merge visually equivalent edits; replay a no-op edit; switch modes without editing; publish then load a pinned revision. Check expected invariants across generated inputs. Random metamorphic samples complement independent goldens; they do not establish a parser compliant with YAML 1.2.2.
- **Failures:** Fail when adapters error or time out; never turn a missing observation into `true`. Record partial availability and actually skipped checks.
- **Determinism:** Bound concurrency, make jitter/clock explicit, record random seed, tool versions and target commit. For distributed tests, record operation start/complete and external confirmation separately.

## Evidence tiers and acceptance gate

| Tier | What passing establishes | What it does not establish |
|---|---|---|
| Structural | Valid files, paths, metadata and link format | A working test harness or product behavior |
| Reference model | Synthetic oracles behave as tested on synthetic observations | Ec Rust/YAML correctness or actual authorization |
| Real local SQLite | SQLite transaction outcomes on artificial tables | PostgreSQL concurrency, Git publication or Ec persistence |
| Actual integration | Specified behavior of the **observed Ec commit/environment** for covered AC | All environments, security in general, performance/availability |
| Browser + assistive technologies | Observed UI behavior in tested browsers and AT combinations | All WCAG requirements or all user needs |
| Real agent/end-to-end | Verified real job path under observed fault scope | Exactly-once external side effects or universal reliability |

### Minimal case record (conceptual)

`case_id`, `REQ-ID`, `AC-ID`, project specification hash, authorizing actor, kind/schema revision, project/scope, fixture and independence reviewer, expected observation, rejected path, actual adapter/command, checked application commit, environment, seed, backend receipt, effective outcome, timestamp, log reference, redaction statement, skipped/failed/error classification and limitations.

The five project workflow statuses are `APROVADO`, `REPROVADO`, `NÃO VERIFICADO`, `INCONCLUSIVO` and `NÃO APLICÁVEL`; do not automatically map one green local test to `APROVADO` for a product AC. A stale hash changes an approval back into an unresolved criterion until re-verification. The packaged `validation/oracles.py` enforces a conservative subset of these evidence gates; it is **not** a replacement for the original project workflow evidence validator.

## Adapter/bootstrap example and limitation

`validation/run_contract_fixture.py --mode reference` runs exactly the 22 registered synthetic cases. `--mode ec` currently **exits 2**, proving the harness does not silently certify the product without a real implementation. `validation/integration_adapter_template.py` intentionally raises `NotImplementedError`. A real harness addition should wire the selected project workflow feature's actual API and run scope-specific tests under an authorized disposable project, then keep `reference` and `ec` result channels separate.
