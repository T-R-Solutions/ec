# Routing, Evaluation and Real Ec Product Acceptance Oracles

> Use the destination's existing requirements and evidence format; named workflow documents are examples, not prerequisites. Optional SDD mapping is configured outside this skill. Commands, fixture paths and historical execution claims from the source package are illustrative or imported evidence unless the corresponding files are present and independently checked; they are not current test results or bundled executable tools.

All following items are **planned test/agent-evaluation cases**, not executed Ec application tests. The included synthetic test runner uses C01–C22 and is separately documented; it does not satisfy A01–A48 here.

## Routing evaluation cases (16 planned)

| ID | User request | Expected primary selection |
|---|---|---|
| R01 | Build reusable Ec YAML-Visual-Form roundtrip regression suite | Activate this skill |
| R02 | Link real Ec `REQ/AC` across frontend/backend/auth and evidence | Activate this skill |
| R03 | Create negative cross-project access fixtures for Ec project hierarchy | Activate; use effective-permissions owner as needed |
| R04 | Compare immutable published YAML, effective revision and pinned job histories | Activate this skill |
| R05 | Build project workflow traceability between rejected stale approvals and backend receipts | Activate this skill |
| R06 | Add replay/reorder/fencing acceptance tests for a particular Ec run contract | Activate; delegate actual agent protocol to owner |
| R07 | Prove that `202 Accepted` doesn't cause an Ec UI success assertion | Activate this skill |
| R08 | Design reproducible invalid-YAML and semantic extension contract corpus for Ec | Activate; consume real schema owner |
| R09 | Rewrite a YAML 1.2.2 parser | Do not activate; select `ec-declarative-resource-schemas` |
| R10 | Build the visual DAG editor | Do not activate; select `ec-pipeline-authoring-workspace` |
| R11 | Implement effective RBAC evaluation | Do not activate; select `ec-effective-permissions` |
| R12 | Design generic software test strategy | Do not activate; select `software-testing` |
| R13 | Write consumer-driven contract tests for a non-Ec HTTP API | Do not activate; select `api-testing` |
| R14 | Develop generic stateful fuzz/shrink library | Do not activate; select `property-based-testing` |
| R15 | Implement scheduler leases and priority selection | Do not activate; select `cicd-scheduler` |
| R16 | Test only Vue component ARIA role querying | Do not activate as primary; select `frontend-testing` / `web-accessibility` |

## Real Ec product contract acceptance cases (48 planned)

The exact contract input, version and available interface must be resolved from the project workflow and the real repository. **Do not auto-convert these proposed cases into product commitments.**

| ID | Planned independent real-system oracle and negative control |
|---|---|
| A01 | Valid schema resource: parse YAML, produce Visual/Form and reserialize without semantic loss. |
| A02 | Different YAML mapping-key order: same semantic resource as reviewed golden. |
| A03 | Ordered jobs/steps permuted: semantic order changes when schema declares order meaningful. |
| A04 | Type distinctions `false`/`0`, `null`/absent, numeric/text maintain intended schema meaning. |
| A05 | Supported extension/reference/expression survives mode changes and no-op edit. |
| A06 | Unrepresentable Visual property never silently disappears on mode transition. |
| A07 | Invalid YAML draft does not corrupt last valid projection or published effective config. |
| A08 | Duplicate mapping keys are rejected with context; no last-key-wins execution. |
| A09 | Hostile tag/alias expansion/depth/size vectors obey configured parser safety budgets. |
| A10 | Comment/format changes are acknowledged or previewed without claiming byte-preservation. |
| A11 | Valid YAML edit updates form/visual represented fields deterministically. |
| A12 | Deprecated/unknown schema `apiVersion` receives approved diagnostic/migration. |
| A13 | Inline forbidden credential is not published nor leaked in errors/logs. |
| A14 | Approved secret reference survives roundtrip without materialization in YAML. |
| A15 | Missing referenced project/pool/role produces explicit validation failure/impact. |
| A16 | DAG cycles or missing dependencies are rejected by actual graph owner. |
| A17 | Allowed actor with correct scope/policy revision can perform project-approved action. |
| A18 | Anonymous or unknown actor is denied at real backend action/resource boundary. |
| A19 | Read-only role cannot publish, run privileged actions or mutate desired resource. |
| A20 | Cross-project object ID substitution cannot read or mutate another project's data. |
| A21 | Role assignment is distinguished from effective permission after inheritance/deny. |
| A22 | Revocation between preview and submission prevents protected effect. |
| A23 | Stale/replayed approval cannot authorize a different target or newer revision. |
| A24 | Self-escalation and global-vs-project privilege boundary are correctly rejected. |
| A25 | Denied action has no unauthorized protected side effects; denial audit is correct. |
| A26 | Draft editing does not change the effective published revision. |
| A27 | Validated proposal pending review does not appear as published or synchronized. |
| A28 | Approved publish writes/pins actual immutable YAML revision and verifiable reference. |
| A29 | Two writers on same base cannot silently overwrite each other. |
| A30 | Stale compare-and-swap base returns conflict or approved explicit reconciliation. |
| A31 | Previously read revision contents remain immutable after later publication. |
| A32 | Repository/config/source revision identities are not conflated. |
| A33 | A submitted job/attempt retains its published config/source pins after subsequent publication. |
| A34 | Stored query projections can be rebuilt from canonical YAML without semantic divergence. |
| A35 | Stale prior reconcile receipt never marks a new desired revision synchronized. |
| A36 | Fresh valid applied receipt with matching observed revision may be shown synced. |
| A37 | Mismatched valid applied revision is shown drifted, never synced. |
| A38 | Missing/contradictory/stale telemetry is unknown, not success. |
| A39 | Reordered/duplicate older events cannot replace newer confirmed state. |
| A40 | HTTP request acceptance does not become completed status before backend confirmation. |
| A41 | Queued run has identity and pinned code/config revisions; actual agent observations correlate. |
| A42 | Cancel-requested and cancel-confirmed remain distinguishable in UI and API history. |
| A43 | Retry creates separately traceable attempt under explicit policy; terminal history unmodified. |
| A44 | Stale agent fence cannot finalize a newer attempt or overwrite its artifacts. |
| A45 | Timeout/network uncertainty is presented as pending/unknown and reconciled by receipt. |
| A46 | Test runner records approved/rejected/not-verified/inconclusive/not-applicable with traceability. |
| A47 | Changed spec, policy, code or schema input hash invalidates stale acceptance evidence. |
| A48 | Real browser keyboard/assistive-tech and light/dark responsive tests verify the relevant state/permission messages. |

## Test suite / evidence partition

- **Python oracles and synthetic model:** `validation/test_oracles.py`, `validation/test_reference_model.py` and 22 C-cases prove only local harness logic.
- **Mutation probes:** 12 deliberate defects; measure limited fault-detection sensitivity, not real Ec mutation score.
- **TypeScript view model:** direct assertion test of synthetic TypeScript and strict compile; requires real Vue DOM/browser test separately.
- **SQLite tests:** toy operational receipt and run-pin transaction conditions; not PostgreSQL, Git or actual Ec.
- **project workflow structure:** original version 1.3.0 validator on temporary copy with positive and bad-path negative; no product criterion checked.
- **Real Ec:** none of A01–A48 executed in this archive. The complete list becomes an acceptance plan only after relevant project workflow feature and actual Ec contracts are known; omit/unmark nonapplicable cases explicitly with explanation.

## Independent agent-evaluation rubric (planned)

Run R01–R16 with positive/negative/boundary requests against a fixed model/build, source commit, complete instructions, reference files and current feature spec. Score actual selection, domain boundary, false schema assumptions, accurate evidence tier, correct YAML/operational separation, approved-vs-observed distinction and reproduction. Test without and with the skill, independent human adjudication and repeated sampling. **0 agent evaluations executed; no model uplift or selection accuracy measured.**
