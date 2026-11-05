# Source Register, Scientific Evidence and Decision Boundaries

> Imported authoring record. Research dates, consultations and execution claims below belong to the supplied source package; they were not reproduced in this adaptation. Referenced validation scripts, fixtures, product norms and logs are not bundled unless present in this folder. Verify their availability and provenance before use. No historical PASS is current acceptance.

**Research scope:** Ec agent inventory, desired pools, project associations, compatibility diagnostics, verified capacity, maintenance/draining operations and accessible administrative UX. **Source selection:** official specifications/vendor or first-party documentation, academic conference publications and an institutional engineering book chapter. Forums, user anecdotes, marketing rankings and undocumented benchmarks were excluded. This is a **scoped, comprehensive review of relevant passages**, not a representation that every book and entire documentation site on scheduling was read.

## Project norms actually consulted

- **EC-N1 — `EC-REGRAS-DE-DESIGN-E-PRODUTO.md`:** §§2–3, 14–16, 19–20, 22–23, 26–28, 34–36, and the document as a whole. Product identity Ec; YAML-only desired configuration, orthogonal operational persistence; project/pool administration, Rust+Vue+TypeScript stack, explicit roles, WCAG 2.2 AA goal, truthful UI and tests. Product **authority**, not text for duplication inside a skill.
- **EC-N2 — `EC-REGRAS-CRIACAO-DE-TELAS-E-IMAGENS.md`:** §§3–8, 13–17, 19–22, and the document as a whole. Agent/pool inventory, compatibility reasons, meaningful data, responsiveness, status distinctions, security, interaction accessibility and honest mockups. Product **authority**, not an implemented application.

## Official specifications and vendor engineering docs

*`Access verified` reports the specific page content examined during this research; it is not a claim of reading all linked subpages.*

| ID | Primary source | Access verified | Narrow takeaway and non-transfer boundary |
|---|---|---|---|
| P01 | JetBrains, [Configuring Agent Pools](https://www.jetbrains.com/help/teamcity/cloud/configuring-agent-pools.html) | Agent membership, project association, permissions, incompatible-move warnings and UI operations inspected | Pool access restrictions can strand jobs; **TeamCity's single-pool membership is not an Ec decision**. |
| P02 | JetBrains, [Build Agent Basics](https://www.jetbrains.com/help/teamcity/cloud/install-and-start-teamcity-agents.html) | Agent status dimensions (connected, authorized, enabled), compatibility and workload inspected | Separate orthogonal lifecycle dimensions; **do not import TeamCity's one-job-per-agent policy**. |
| P03 | JetBrains, [Agent Requirements](https://www.jetbrains.com/help/teamcity/cloud/configuring-agent-requirements.html) | Requirement expressions, compatible/incompatible diagnostics and queue note inspected | Explaining absence of compatible agents improves diagnosis; no Kotlin DSL in Ec YAML. |
| P04 | JetBrains, [Build Agents Configuration and Maintenance](https://www.jetbrains.com/help/teamcity/build-agents-configuration-and-maintenance.html) | Enabled/disabled and maintenance behavior, pool grouping and agent difference tools inspected | Drain/disable is distinct from disconnection; exact Ec effects belong to SDD. |
| P05 | GitHub Actions, [Use self-hosted runners in a workflow](https://docs.github.com/en/actions/how-tos/manage-runners/self-hosted-runners/use-in-a-workflow) | Runner labels, cumulative matching and group conditions examined | Multiple independent constraints; **not a proposed Ec `runs-on` syntax**. |
| P06 | GitHub Actions, [Runner groups](https://docs.github.com/en/actions/concepts/runners/runner-groups) | Runner-group access concepts inspected | Resource organization and access are different from capacity or readiness. |
| P07 | GitHub Actions, [Monitoring and troubleshooting runners](https://docs.github.com/en/actions/how-tos/manage-runners/self-hosted-runners/monitor-and-troubleshoot) | Idle/active/offline status and diagnostics examined | Offline is an observation, not proof of revocation or exact queue time. |
| P08 | GitHub, [Secure use of self-hosted runners](https://docs.github.com/en/actions/reference/security/secure-use) | Untrusted workflow risks and persistent runner compromise warnings examined | Avoid treating self-reported labels as protection from untrusted execution. |
| P09 | GitLab, [Configuring runners](https://docs.gitlab.com/ci/runners/configure_runners/) | Tag matching, protected runners, registration/rotation requirements examined | Protected workloads need authorization/trust gates; not a GitLab-schema transplant. |
| P10 | Microsoft, [Azure Pipelines agents](https://learn.microsoft.com/en-us/azure/devops/pipelines/agents/agents?view=azure-devops) | Capabilities and demands sections examined | Capability matching is distinct from availability, and reported information needs provenance. |
| P11 | Kubernetes, [Kubernetes Scheduler](https://kubernetes.io/docs/concepts/scheduling-eviction/kube-scheduler/) | Two-stage filtering/scoring and binding inspected | Feasible candidate is not chosen placement; scheduling ownership is separate. |
| P12 | Kubernetes, [Taints and Tolerations](https://kubernetes.io/docs/concepts/scheduling-eviction/taint-and-toleration/) | NoSchedule, PreferNoSchedule, NoExecute and non-guaranteeing tolerations inspected | Avoid equating allowance with final scheduling; **do not copy Kubernetes taints into Ec**. |
| P13 | Kubernetes, [Assigning Pods to Nodes](https://kubernetes.io/docs/concepts/scheduling-eviction/assign-pod-node/) | Required versus preferred node affinity inspected | Hard filters and soft preferences need distinct reason classes. |
| P14 | Kubernetes, [Scheduling Framework](https://kubernetes.io/docs/concepts/scheduling-eviction/scheduling-framework/) | Scheduling cycle/binding separation inspected | Console cannot infer committed allocation from a candidate preview. |
| P15 | Kubernetes, [Resource Management for Pods and Containers](https://kubernetes.io/docs/concepts/configuration/manage-resources-containers/) | Requests/limits, resource units and allocatable capacity sections inspected | Resource requests, consumption and allocatable resources are different; no Ec quantity schema inferred. |
| P16 | HashiCorp, [Nomad placement constraints](https://developer.hashicorp.com/nomad/docs/job-specification/constraint) | Constraint composition, scheduling consequences and contradictory constraints inspected | Incompatible constraints can make work unplaceable; Nomad/HCL is not Ec config. |
| P17 | PostgreSQL, [SELECT locking clause](https://www.postgresql.org/docs/current/sql-select.html) | `FOR UPDATE`, `NOWAIT`, `SKIP LOCKED` and queue-specific warning inspected | Queue selection and locks are scheduler transaction topics, **not a pool UI implementation mandate**. |
| P18 | OWASP, [CI/CD Security Cheat Sheet](https://cheatsheetseries.owasp.org/cheatsheets/CI_CD_Security_Cheat_Sheet.html) | Pipeline access control, untrusted workers, asset inventory and secure config sections inspected | Pool assignment must not grant privileged workload execution by appearance alone. |
| P19 | NIST, [SP 800-207 Zero Trust Architecture](https://csrc.nist.gov/pubs/sp/800/207/final) | Official abstract, resource-centric authentication and authorization model examined | Device/subject identity and policy decisions are separate; not a requirement that Ec adopt a named product. |
| P20 | W3C, [WCAG 2.2](https://www.w3.org/TR/WCAG22/) | Keyboard, dragging alternative, target size, status messages, name/role/value excerpts examined | Accessible interactive operations need actual tests; a screenshot does not verify WCAG. |
| P21 | W3C, [WAI-ARIA Grid Pattern](https://www.w3.org/WAI/ARIA/apg/patterns/grid/) | Focus navigation, tab sequence and editing tradeoffs inspected | Use interactive grids only when necessary; require managed keyboard focus. |
| P22 | W3C, [WAI-ARIA Table Pattern](https://www.w3.org/WAI/ARIA/apg/patterns/table/) | Static table vs interactive grid distinctions inspected | Prefer native table semantics for read-only pool/agent inventory. |
| P23 | Vue team, [TypeScript with Composition API](https://vuejs.org/guide/typescript/composition-api) | Typed props/emits, ref/computed and handler sections examined | Build typed administrative components with explicit unknown states, no ad hoc runtime parsing. |
| P24 | TypeScript team, [Discriminated Unions](https://www.typescriptlang.org/docs/handbook/unions-and-intersections.html) | Tagged union and exhaustive narrowing examples examined | Typed state categories reduce fall-through UI bugs; not a substitute for server validation. |
| P25 | YAML Language Development Team, [YAML 1.2.2](https://yaml.org/spec/1.2/) | Specification revision and data-language role reviewed | A single desired-state representation; actual Ec resource schema is still unspecified. |
| P26 | Ewaschuk, Google, [Site Reliability Engineering, ch. 6: Monitoring Distributed Systems](https://sre.google/sre-book/monitoring-distributed-systems/) | Definitions, black-box/white-box monitoring, dashboards, signal quality sections examined | Monitoring observations should support operator decisions, not decorative metrics; **engineering book chapter, not peer-reviewed experiment**. |

**Primary count: 26 relevant distinct first-party/official sources**, plus two project norms. Sources were scoped to applicable passages. Site navigation and adjacent documentation were not equated with a full read of their contents.

## Academic works — research depth and transfer limits

| ID | Academic publication | Access verified | Supported result and limitation |
|---|---|---|---|
| A01 | Ghodsi et al. (2011), [*Dominant Resource Fairness*](https://www.usenix.org/legacy/event/nsdi11/tech/full_papers/Ghodsi.pdf), NSDI 2011 | Original full text accessible; abstract, §4.2 and discrete-allocation discussion inspected | Multidimensional capacity/fairness is more complex than free slot counts; **fairness algorithms belong to `cicd-scheduler`, not this console**. |
| A02 | Gog et al. (2016), [*Firmament: Fast, Centralized Cluster Scheduling at Scale*](https://www.usenix.org/system/files/conference/osdi16/osdi16-gog.pdf), OSDI 2016 | Full PDF accessible; abstract, introductory trade-off and placement design passages inspected | Feasibility, optimization and placement are distinct; paper's numbers do not forecast Ec performance. |
| A03 | Schwarzkopf et al. (2013), [*Omega*](https://research.google.com/pubs/archive/41684.pdf), EuroSys 2013 | Original full text accessible; abstract, §3 scheduler taxonomy, §3.2 and concurrency conclusions inspected | Independent decisions over shared state create contention/staleness; the UI should disclose snapshot revisions. Not an Ec scheduler architecture mandate. |
| A04 | Verma et al. (2015), [*Large-scale cluster management at Google with Borg*](https://research.google.com/pubs/archive/43438.pdf), EuroSys 2015 | Original full PDF accessed; abstract and user/operational monitoring sections inspected | Placement, capacity, isolation and monitoring must be treated as separate mechanisms. No Borg-scale claim for Ec. |
| A05 | Isard et al. (2009), [*Quincy: Fair Scheduling for Distributed Computing Clusters*](https://www.sigops.org/s/conferences/sosp/2009/papers/isard-sosp09.pdf), SOSP 2009 | Original full PDF accessed; abstract, §3.1 queues and placement/fairness discussion inspected | Queue topology and fairness can make naive “next agent” assertions wrong. Do not implement Quincy in console. |
| A06 | Ousterhout et al. (2013), [*Sparrow: Distributed, Low Latency Scheduling*](https://cs.stanford.edu/~matei/papers/2013/sosp_sparrow.pdf), SOSP 2013 | Original full PDF accessed; abstract, introduction and sampled-placement rationale inspected | Scheduler decisions can depend on partial state; no universally exact current rank can be inferred from snapshots alone. |
| A07 | Hindman et al. (2011), [*Mesos: A Platform for Fine-Grained Resource Sharing in the Data Center*](https://www.usenix.org/conference/nsdi11/mesos-platform-fine-grained-resource-sharing-data-center), NSDI 2011 | Official conference publication page and abstract examined; full article **not** read for this skill | Resource allocation and task placement can be separate responsibility layers. No Mesos architecture mandate. |

**Research count: 7 peer-reviewed conference works.** Six original papers had relevant passages inspected directly; one was considered from the official abstract and publication metadata. This register does not claim exhaustive book reading, full-paper review for every entry, or experimental validation of the Ec application.

## Source-derived observations versus proposed Ec methodology

| Topic | Supported by source | Skill method (proposal, not implemented Ec) |
|---|---|---|
| Pool membership | TeamCity and GitHub demonstrate scope constraints with different implementations | Inspect the **Ec-approved** membership/cardinality contract and preview access and availability effects. |
| Eligibility | Kubernetes differentiates filter, score and bind; TeamCity exposes incompatible reasons | Make stable diagnosis of hard mismatch/temporary unavailability/unknown; never reserve jobs from UI. |
| Trusted capability | GitHub and OWASP warn that self-hosted runner workloads can compromise environment | Require evidence provenance for protected-workload capability; avoid treating labels as attestation. |
| Capacity | Kubernetes distinguishes request/allocatable/actual usage; DRF treats multiple resource types | Show units and snapshot provenance; separate known slots from actual eligible capacity. |
| Maintenance | TeamCity distinguishes disable and maintenance; GitHub distinguishes idle/active/offline | Expose a versioned maintenance request with server confirmation; do not cancel existing work by assumption. |
| Live status | SRE book explains monitoring signal types and W3C requires accessible status announcement | Present last observation, separate unknown/disconnected, and test screen-reader announcements. |
| Revision and concurrency | Omega discusses shared-state optimistic conflicts; PostgreSQL warns queue snapshots can be inconsistent | Preview changes with expected revision; let publication and scheduler own transactional outcomes. |
| Declarative configuration | The Ec norm mandates YAML only for desired state | Use published YAML as authority, operational snapshots from backend; no independent form/visual source. |

## Decisions reserved for approved SDD

1. Pool membership cardinality and inheritance from project hierarchies.
2. Agent enrollment, authorization and capability attestation/provenance.
3. Exact pool and agent resource schema, required fields, versions, supported capability types and units.
4. RBAC for creating, editing, moving, draining, quarantining and deleting pools/agents.
5. Maintenance vs disable vs quarantine semantics and whether running work survives each action.
6. Config publication, approval and rollout policy for changing assignments with active work.
7. Scheduler's resource accounting, quotas, dispatch eligibility and capacity reservations; whether exact queue rank/ETA even exists.
8. Version/freshness of heartbeat-derived snapshots and permitted user-facing state categories.
9. Audit retention, redaction, observability endpoint, authorization of detailed rejection reasons.
10. Any particular Vue component architecture, view state model, and API field mapping after inspecting actual Ec code.

**Implementation and validation limitation:** No real Ec repository, running Rust agent/control plane, PostgreSQL database, authenticated HTTP endpoint, scheduler, browser accessibility test or agent-model behavior experiment was exercised. Only the synthetic reference model is executable here. No result in this package proves that the Ec product currently supports these states/actions, or that it can deploy to production.
