# Activation and Real Integration Oracles

> Use the destination's existing requirements and evidence format; named workflow documents are examples, not prerequisites. Optional SDD mapping is configured outside this skill.

The examples below are **prepared** and **NOT EXECUTED** against Ec. They are not evidence of correctness, accessibility, capacity, performance or behavioral skill selection.

## Skill selection cases

| Case | Request | Select? | Lead owner / reason |
|---|---|---|---|
| S01 | Add a scoped pool inventory with connected, busy and unknown agents | Yes | Agent/pool operational UI |
| S02 | Explain why no agent can run a job in the selected project | Yes | Eligibility evidence and remediation |
| S03 | Show pool membership change impact before user approval | Yes | Administrative preview |
| S04 | Build a keyboard-operable agent maintenance dialog | Yes | Agent administrative flow |
| S05 | Display last observation and source of agent capabilities | Yes | Provenance presentation |
| S06 | Compare two agents for compatible toolchain requirements | Yes | Compatibility inspector |
| S07 | Add light/dark and narrow-screen variants for agent details | Yes | Domain UX + accessibility |
| S08 | Explain busy candidate versus incompatible candidate | Yes | Eligibility taxonomy |
| S09 | Show how many slots are known free across a pool | Yes | Capacity with completeness annotation |
| S10 | Show restricted user a safe reason for denied agent usage | Yes | Redacted diagnostic representation |
| S11 | Preview disabling a pool used by several projects | Yes | Change impact |
| S12 | Link an agent detail to running jobs with confirmed IDs | Yes | Operational navigation |
| S13 | Add pool assignment via Visual/Form/YAML without loss | Yes, auxiliary | Lead `ec-semantic-configuration-projections`, console owns pool flow |
| S14 | Render a summary of verified versus self-reported capabilities | Yes | Capability provenance |
| S15 | Prevent displaying stale idle as ready after disconnect | Yes | Freshness and unknown state |
| S16 | Add status filters for quarantined, disabled and offline | Yes | Agent inventory |
| S17 | Implement the fair scheduling algorithm | No | `cicd-scheduler` |
| S18 | Implement job claim and PostgreSQL `SKIP LOCKED` | No | `cicd-scheduler` and `postgresql` |
| S19 | Implement signed agent registration HTTP payload | No | `ec-agent-control-protocol` |
| S20 | Implement worker shell step timeout and kill | No | `cicd-execution-runtime` |
| S21 | Build the generic YAML parser and duplicate-key rejection | No | `ec-declarative-resource-schemas` |
| S22 | Implement audit/transaction semantics for YAML publication | No | `ec-configuration-publication` |
| S23 | Design a global RBAC inheritance policy | No | `ec-effective-permissions` |
| S24 | Implement build cancel/retry operation API | No | `ec-run-execution-operations` |
| S25 | Create a general CSS container-query reference library | No | `css-layout-engineering` |
| S26 | Build general Vue state/composable utility | No | `vue3-engineering` |
| S27 | Evaluate actual WCAG 2.2 AA across the whole product | No, auxiliary | `web-accessibility` owns cross-product verification |
| S28 | Optimize scheduler score with DRF or min-cost flow | No | `cicd-scheduler`; academic reference is not console code |

## Real Ec integration oracles — to be executed only with repository access

| ID | Setup / action | Required assertion |
|---|---|---|
| I01 | Create a pool in Form mode | A validated YAML draft is produced, not an independent database-only desired state |
| I02 | Edit a pool via YAML | Visual and Form views reflect the same model semantically |
| I03 | Change project-to-pool association | Show diff and affected projects before publication |
| I04 | Save draft without publishing | The effective configuration remains unchanged |
| I05 | Publish accepted pool revision | UI does not report applied until backend confirms reconcile |
| I06 | Publish an invalid YAML draft | Reject publication; preserve prior effective resource |
| I07 | Server marks pool drift | UI differentiates published from currently effective state |
| I08 | Move agent from one pool to another | Check permissions on both pools and all impacted project scopes |
| I09 | Reuse old configuration version during pool move | Reject stale update; show conflict resolution path |
| I10 | Disable a pool with queued jobs | Preview blocked work using verified queue state; no invented ETA |
| I11 | Drain an idle agent | New admissions stop only after server confirms drain policy |
| I12 | Drain a busy agent | Existing jobs are not silently canceled; UI accurately reports active work |
| I13 | Disconnect an agent | Distinguish connection loss from disabled/quarantined |
| I14 | Last heartbeat arrives late | Older status cannot overwrite fresher observation |
| I15 | Capacity response is absent | Unknown capacity cannot appear as zero or infinite |
| I16 | Available slots exceed total | Show data inconsistency; never mark ready |
| I17 | Agent has toolchain mismatch | Reason includes actual requirement key/value and source if viewer authorized |
| I18 | Agent lacks capability provenance | Protected job must not be declared ready from self-reported label alone |
| I19 | Job references a non-associated project | Show a hard scope rejection, not a resource shortage |
| I20 | Project association removed while work runs | Reveal exact policy-defined behavior and impact |
| I21 | Agent connected and idle but unauthorized | Never show ready-to-run state |
| I22 | Agent authorized and online but disabled | Keep statuses independent |
| I23 | Agent in maintenance and with free slots | Do not count as available for new assignments |
| I24 | No candidates meet requirements | Distinguish permanent mismatch from temporary lack of resources |
| I25 | Job has multiple eligible candidates | Do not imply which one scheduler will choose |
| I26 | Scheduler selection conflicts with stale preview | Reconcile to committed assignment; do not overwrite authority |
| I27 | UI receives HTTP 202 for drain request | Show accepted/requested, not completed |
| I28 | Server rejects drain due permission | Show denied and preserve previous confirmed state |
| I29 | Concurrent edit and maintenance request | Detect conflicting expected revisions or show consistent independent effects |
| I30 | Actor with project-only role changes global pool | Deny server-side and avoid privileged UI action |
| I31 | Low-privilege actor asks why a protected agent fails | Redact project/capability details not authorized to inspect |
| I32 | Reset filters while server updates | Keep stable row identities, focus, selection and scope |
| I33 | Use keyboard to navigate inventory and open agent details | Reach every relevant action without mouse |
| I34 | Use a screen reader for asynchronous status updates | State is announced without unwanted focus movement |
| I35 | Move a pool membership by drag interaction | Provide equivalent non-drag single-pointer action and keyboard route |
| I36 | Render agent inventory at narrow viewport | Critical state and last observation remain available, not clipped |
| I37 | Switch light/dark themes | Test contrast, focus and semantic status text in both |
| I38 | Load a large agent list | Benchmark virtualization only on representative hardware with recorded conditions |
| I39 | Server sends disconnected and success in opposing event orders | Do not falsely combine independent states into green success |
| I40 | Reload after a server restart | Rebuild view from versioned desired YAML and operational evidence, not stale local optimism |

## Behavioral evaluation protocol — not yet executed

For each of 28 selection cases, run with and without the skill in the actual target agent harness. Record activation decisions, unnecessary skill loading, token usage, unsupported field inventions, required artifacts, test command provenance and correctness under failures. For the 40 Ec integration oracles, record repository commit, feature flag, fixture, command, exit code, output hash, screenshot/browser accessibility evidence where relevant, and any unresolved project workflow decision. Compare results only against preregistered accepted requirements.
