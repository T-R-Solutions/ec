# Selection test oracles and future application integration matrix

> Use the destination's existing requirements and evidence format; named workflow documents are examples, not prerequisites. Optional SDD mapping is configured outside this skill. Ec-specific rules below apply only to an Ec consumer whose current contract establishes them. Commands, fixture paths and historical execution claims from the source package are illustrative or imported evidence unless the corresponding files are present and independently checked; they are not current test results or bundled executable tools.

These are **specified routing prompts and expected selections**, not measured agent results. They are used to review the skill's description/activation boundaries. Running a real agent selection evaluation requires an accessible configured agent, the complete skill index, a test harness and recorded traces; no such evaluation has run in this package.

## Positive / negative / boundary selection cases

| Case | Prompt / situation | Select TypeScript Engineering? | Primary owner or rationale |
|---|---|---|---|
| S01 | A TypeScript discriminated union allows impossible pending and failed states | **YES** | State type invariants |
| S02 | A generic function must return `T[K]` for a selected key | **YES** | Generic key correlation |
| S03 | Compiler accepts `any` at an untrusted `fetch` boundary | **YES** | Unknown narrowing + runtime validation handoff |
| S04 | Migrating library `tsconfig` to strict optional types | **YES** | Compiler option semantics |
| S05 | Publishing `.d.ts` with conditional package exports | **YES** | Public declaration/consumer build |
| S06 | NodeNext ESM import fails due to missing emitted extension | **YES** | Module resolution and runtime correspondence |
| S07 | Upgrading from TypeScript 5.8 to 7.0 breaks a compiler API consumer | **YES** | Version/compiler API migration |
| S08 | Need to reject invalid `unknown` before typed API consumption | **YES** | TS guard implementation; domain schema owner consulted |
| S09 | `never` exhaustiveness should fail when adding a new union tag | **YES** | Negative diagnostic proof |
| S10 | Project references unexpectedly emit declarations to wrong paths | **YES** | Build configuration and declarations |
| S11 | Make Vue `watch` stop after component unmount | NO | `vue3-engineering` |
| S12 | Design a permission matrix in Ec | NO | Product design / `ec-effective-permissions` |
| S13 | Implement a scheduler leasing transaction in PostgreSQL | NO | `cicd-scheduler`, `postgresql` |
| S14 | Define Ec resource YAML schema types and migrations | NO | `ec-declarative-resource-schemas` |
| S15 | Decide retry HTTP semantics and idempotency keys for public API | NO | `http-api-contracts` |
| S16 | Audit frontend tab order and WCAG focus handling | NO | `web-accessibility` |
| S17 | Author a TypeScript-typed Vue prop with no SFC macro behavior | **CONDITIONAL** | Primarily `vue3-engineering`; only select TS if a separate type-system issue exists |
| S18 | Build a form with schema-aware field validation | **CONDITIONAL** | `frontend-form-engineering`; TypeScript only if type inference / emitted contract issue |
| S19 | Generate TS definitions from Ec's versioned YAML resource schemas | **CONDITIONAL** | Schema owner defines authoritative contract; TS skill implements language projections |
| S20 | Validate external JSON transport against an Ec business schema | **CONDITIONAL** | Schema/API owner defines validity, TS implements guard typing; JSON transport != YAML resource |
| S21 | Choose a CSS layout for responsive sidebar | NO | `css-layout-engineering` |
| S22 | Tune `tsc` speed after comparing compiler versions and hardware | **CONDITIONAL** | TypeScript toolchain performance; only with measured application data |
| S23 | Configure a CLI help message and commands | NO | `cli-engineering` |
| S24 | Diagnose a race in a composable's stale fetch response | NO | `vue3-engineering`, maybe `frontend-state-and-cache` |

## Integration acceptance scenarios for the actual application — NOT EXECUTED

The following examples are an **acceptance-plan seed**, not evidence of implemented Ec behavior. Each needs a feature requirement/acceptance IDs, runnable product revision, authorized environment and independent expected outcomes.

| ID | Scenario | Expected verification |
|---|---|---|
| A01 | Actual frontend TypeScript compiler and lockfile are identified | Recorded version and `tsconfig --showConfig` |
| A02 | A TypeScript model consumes a validated Ec YAML resource | Schema authority and runtime validator confirmed separately |
| A03 | Mode projection TS types represent the full documented resource contract | Compare real YAML/Visual/Form semantics; no dropped properties |
| A04 | An unknown API response contains an invalid status | Real API transport guard rejects/makes failure explicit |
| A05 | A new backend status variant is introduced | Consumer compile-negative exhaustiveness check fails until handled |
| A06 | Authorization result is `denied` | UI remains denied independent of local TypeScript narrowing |
| A07 | Public TS interface is changed incompatibly | Real consumer types/tests fail and migration is explicit |
| A08 | `noUncheckedIndexedAccess` applied to agent list | Empty arrays safe without `!` casts |
| A09 | Typed error handling distinguishes network, auth and server errors | Runtime negative test + project workflow error presentation |
| A10 | `.d.ts` package consumers compile under supported runtimes | Clean Node/Bundler consumer matrices |
| A11 | Version change 5.x/6.x/7.x is proposed | CI with actual compiler/toolchain versions and comparative diagnostics |
| A12 | TypeScript 7 compiler API removal affects editor tooling | Compatibility checks or pinned TS 6 bridge with recorded owner |
| A13 | `tsc` success is mistaken for published/synced Ec configuration | Test ensures backend confirmed effective revision before UI success |
| A14 | Runtime callback from untyped plugin returns unexpected data | Adapter validates `unknown`; no privilege escalation |
| A15 | Third-party declarations are stale/inaccurate | Negative runtime/consumer test captures mismatch |
| A16 | Strict migration alters runtime import specifiers | Emitted JS executed in target runtime before accepting change |

**Selection result status:** cases S01–S24 are specified, but **not measured LLM agent results**. Tests A01–A16 are **NOT EXECUTED** in the absence of the Ec checkout, authentic API/contracts, dependencies and target runtime. Structural project workflow checks cannot promote any A-case to `APROVADO`.
