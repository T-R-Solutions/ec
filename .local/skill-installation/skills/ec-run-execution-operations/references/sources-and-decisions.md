# Primary sources, scientific grounding and decision traceability

> Imported authoring record. Research dates, consultations and execution claims below belong to the supplied source package; they were not reproduced in this adaptation. Referenced validation scripts, fixtures, product norms and logs are not bundled unless present in this folder. Verify their availability and provenance before use. No historical PASS is current acceptance.

**Review date:** 2026-10-31. **Rule:** no forums, Reddit, unsupported claims, or invented benchmark results. Source-specific details are not Ec implementation evidence. The entries below distinguish the **portion actually inspected** from unexamined material. Titles and URLs are retained for verification; this file is a synthesis, not a copy of their text.

## 1. Normative Ec sources (read in the supplied project)

| Identifier | Document and actual scope used | Product consequence; only the norm has authority |
|---|---|---|
| EC-N1 | `EC-REGRAS-DE-DESIGN-E-PRODUTO.md`, §§2, 14–16, 19–23, 26–28, 32–35 (also consulted document scope and product identity) | Canonical YAML for **desired configuration**, operational data held separately; immutable revision per submitted run; real backend confirmation, retry/cancel truth, authorization, feedback and accessibility |
| EC-N2 | `EC-REGRAS-CRIACAO-DE-TELAS-E-IMAGENS.md`, §§4–8, 13–18, 20–22 | Honest run/queue/agent statuses, traceable revision, dense usable operational views, status message semantics and screen-design boundaries |

No contradictory rule was identified. These documents remain Portuguese and are **not translated, copied, or replaced** in this English agent-skill package. The skill records methods without restating the entire legal/policy body.

## 2. Standards and first-party implementation manuals

| ID | Primary source — exact URL | Access depth examined | Decision transferred into skill | Limitation |
|---|---|---|---|---|
| ST-01 | IETF, RFC 9110, [HTTP Semantics](https://www.rfc-editor.org/rfc/rfc9110.html), §§9.2.2, 13.1.1, 15.3.3, 15.5.10–12 | Relevant sections and examples inspected | Distinguish 202 acceptance from completion; method idempotency from application-level POST dedup; preconditions/409/412 for stale mutations | Does **not** prescribe Ec routes or idempotency keys |
| ST-02 | IETF, RFC 9457, [Problem Details for HTTP APIs](https://www.rfc-editor.org/rfc/rfc9457.html), §§1, 3, 5 | Introduction, object definitions and security considerations inspected | Machine-readable conflict/permission error payload design | JSON is acceptable as an **API transport**, never alternate Ec declarative config |
| ST-03 | PostgreSQL, [SELECT locking / SKIP LOCKED](https://www.postgresql.org/docs/current/sql-select.html) | Locking clause and `LIMIT`/ordering sections inspected | Queue consumers may skip locked rows; resulting inconsistent view is inappropriate for authoritative position claims | Scheduling algorithm owned by `cicd-scheduler` |
| ST-04 | PostgreSQL, [Transaction Isolation](https://www.postgresql.org/docs/current/transaction-iso.html) | Isolation behavior and retry guidance inspected | Use versioned/transactional command updates, test concurrency and serialization failures | No Ec database schema supplied |
| ST-05 | PostgreSQL, [INSERT / ON CONFLICT](https://www.postgresql.org/docs/current/sql-insert.html) | Atomic upsert semantics and conflict target sections inspected | Possible implementation of durable dedupe receipts with unique constraint | Not proof of complete request/idempotency design |
| ST-06 | GitHub Docs, [Re-running workflows and jobs](https://docs.github.com/en/actions/how-tos/manage-workflow-runs/re-run-workflows-and-jobs) | Re-run capability, actor and commit/ref sections inspected | Retry/re-run semantics and privileges can differ; require Ec specification, track fixed source revisions | GitHub's 30-day/50-run rules are **not** Ec policy |
| ST-07 | GitHub Docs, [Canceling a workflow run](https://docs.github.com/en/actions/how-tos/manage-workflow-runs/cancel-a-workflow-run) | Permission and cancel operation sections inspected | Operator permissions and queued/running cancellation routes | GitHub mechanics are comparison only |
| ST-08 | GitHub Docs, [Workflow run REST API](https://docs.github.com/en/rest/actions/workflow-runs) | API scope, run details and cancel/rerun operations inspected | Distinguish run identifiers from operational command endpoint | Do not adopt GitHub status or endpoint schema |
| ST-09 | GitLab Docs, [Jobs API](https://docs.gitlab.com/api/jobs/) | Job status values, cancel/retry endpoints, permissions and pagination sections inspected | `canceling` distinct from `canceled`; status details and repeat attempts must be modeled explicitly | GitLab states are **not** Ec required enum |
| ST-10 | GitLab Docs, [Scripts and job logs: cancellation](https://docs.gitlab.com/ci/yaml/script/) | `after_script` behavior when canceling inspected | Cleanup can continue during cancellation; no instant terminal status | Applicable only where specified and implemented |
| ST-11 | Tekton Docs, [PipelineRuns](https://tekton.dev/docs/pipelines/pipelineruns/) | Cancel, graceful cancel and stop examples examined | Cancellation is policy-sensitive; cleanup/finalization options differ | Tekton's CRD fields are **not** Ec resources |
| ST-12 | Kubernetes Docs, [Job controller](https://kubernetes.io/docs/concepts/workloads/controllers/job/) | Backoff, deadline, replacement-pod and terminal handling sections inspected | Retry and timeout must not be conflated; controllers may perform work after failure detection | Not evidence Ec runs on Kubernetes |
| ST-13 | Tokio, [task cancellation](https://docs.rs/tokio/latest/tokio/task/) | `abort`, `spawn_blocking`, completion race and awaiting handle sections inspected | A cancellation signal does not establish process termination or final failure | No actor/process termination API implemented in this skill |
| ST-14 | Tokio Util, [CancellationToken](https://docs.rs/tokio-util/latest/tokio_util/sync/struct.CancellationToken.html) | Token/child token and cancellation-safety details inspected | Cooperative cancellation is an operational stage, not a confirmed run outcome | Must verify actual Rust dependency versions |
| ST-15 | OpenTelemetry, [CI/CD semconv](https://opentelemetry.io/docs/specs/semconv/cicd/) | Scope and signal category inspected | Separate spans/metrics/log signals when measuring run operations | **Release Candidate**, pin exact versions before adoption |
| ST-16 | OpenTelemetry, [CI/CD metrics](https://opentelemetry.io/docs/specs/semconv/cicd/cicd-metrics/) | State/result attributes and stability markings inspected | Pipeline run **phase** and result are distinct dimensions; stable identifiers for correlation | RC vocabulary not canonical Ec status enumeration |
| ST-17 | W3C WAI, [WCAG 2.2 SC 4.1.3 Status Messages](https://www.w3.org/WAI/WCAG22/Understanding/status-messages.html) | Criterion, intent and examples inspected | Announce asynchronous status changes without forcing focus | Understanding is informative; conformance requires browser/AT testing |
| ST-18 | AWS Builders' Library, [Making retries safe with idempotent APIs](https://aws.amazon.com/builders-library/making-retries-safe-with-idempotent-APIs/) | Deduplication/idempotent-call sections examined | Bind retry request identity to intent and recover after lost acknowledgement | Practitioner guidance, not peer-reviewed standard; no AWS dependency |

Standards from PostgreSQL docs reflect the currently retrieved manual; the Ec runtime/database version is **unknown**. External systems show plausible implementation trade-offs rather than feature approval.

## 3. Peer-reviewed papers, conference research and books

| ID | Academic source | Access verified | Supportable inference | Evidence limits |
|---|---|---|---|---|
| A-01 | Garcia-Molina, H. and Salem, K., [*Sagas*](https://www.cs.princeton.edu/techreports/1987/070.pdf), Princeton technical report 1987 / ACM SIGMOD 1987, [DOI](https://doi.org/10.1145/38713.38742) | Original paper PDF abstract, introduction and discussion of decomposition/compensation inspected | Multi-step effects may require *explicit* compensation and are not a simple atomic rollback | Does **not** prove arbitrary build-side-effects can be compensated; avoid promising rollback |
| A-02 | Burrows, M., [*The Chubby lock service for loosely-coupled distributed systems*](https://www.usenix.org/legacy/event/osdi06/tech/full_papers/burrows/burrows_html/index.html), USENIX OSDI 2006 | Full HTML accessible; introduction, delayed-message, locks and leases passages inspected | Late messages from a previous owner are dangerous; run operation views need identity and source/fence validation (delegated to agent/scheduler) | Does not prescribe Ec lease implementation |
| A-03 | Akidau et al., [*The Dataflow Model*](https://www.vldb.org/pvldb/vol8/p1792-Akidau.pdf), *PVLDB* 8(12), 2015 | Original PDF abstract, §1 and §1.3 time domains inspected | Event occurrence time, observation time and updates may differ; out-of-order stream observation needs explicit version/recovery semantics | Processing model is **not** a CI run event schema; transfer is an architectural analogy |
| A-04 | Dean, J. and Barroso, L. A., [*The Tail at Scale*](https://barroso.org/publications/TheTailAtScale.pdf), *Communications of the ACM* 56(2), 2013 | Original article PDF introduction, queueing and response-tail passages inspected | Queue latency is variable; a single fixed ETA or local guessed queue position is unjustified without measurements | Not a benchmark of Ec and not a reason to hardcode performance thresholds |
| A-05 | Helland, P., [*Life beyond Distributed Transactions: an Apostate’s Opinion*](https://www.cidrdb.org/cidr2007/papers/cidr07p15.pdf), CIDR 2007 | Conference **position paper**, abstract/introduction examined | No automatic atomicity across independent side effects; design observable reconciliation boundaries | Position/opinion paper, **not** an empirical evaluation or a proof of universal impossibility |

**Books:** no full-length book on transactions/CI execution was available and verified during this research window. We therefore **do not claim to have read one**. The papers and specifications above are sufficient for the scope defined here; future additions should record exact chapters/pages actually consulted. No secondary book summary was treated as primary evidence.

## 4. Synthesized decisions and evidence strength

| Decision ID | Derived method | Primary evidence | Classification |
|---|---|---|---|
| D-01 | Three separate state domains: actual attempt, command receipt, observation freshness | EC-N1, ST-01, ST-09, ST-13 | Engineering synthesis (not a standardized Ec model) |
| D-02 | Durable, identified retry with immutable prior attempt and provenance | EC-N1, ST-06, ST-09, A-01 | Engineering recommendation; representation requires SDD |
| D-03 | No fabricated cancel completion; race may end normally | EC-N1, ST-10–14 | Supported by API/runtime behavior; exact transitions remain SDD |
| D-04 | Request deduplication and safe replay after lost response | ST-01, ST-05, ST-18 | Recommended method; key TTL and API structure require SDD |
| D-05 | Versioned snapshots/events, gap detection and authoritative recovery | EC-N1, A-02, A-03, ST-08 | Engineering inference, must test event transport guarantees |
| D-06 | Queue position/ETA only with observed authoritative semantics | EC-N1, ST-03, A-04 | Supported caution; no numeric promise |
| D-07 | RBAC checked on backend even if UI suggests permission | EC-N1, ST-07, ST-09 | Product norm + vendor comparators |
| D-08 | Separate accessible progress/status announcements | EC-N1, EC-N2, ST-17 | W3C conformance objective, not tested by synthetic model |
| D-09 | No exactly-once external work guarantee | EC-N1, A-01, A-05, ST-13 | Product rule and research-supported trade-off |
| D-10 | OTel CI/CD attrs require version/stability check | ST-15, ST-16 | Official RC metadata, not committed Ec instrumentation |

## 5. Research exclusions, unanswered questions and citation integrity

- Sources not used: Stack Overflow, Reddit, developer forums, marketing comparisons, anonymous benchmarking claims, AI-generated documentation or unsupported code snippets.
- No Ec source checkout, approved feature SDD, actual DB schema, deployed executor or browser was provided; no actual Ec runtime behavior can be asserted.
- Exact retry eligibility, partial retries, job vs pipeline run identity, cancel grace period, queue order/ETA, policy for successful reruns, command receipt persistence, event sequence contract, audit retention, and API status mappings belong to **approved SDD decisions**.
- No PostgreSQL run was executed; tests use an isolated Python reference controller and cannot establish serializable transactional correctness, distributed cancellation, authorization security, WCAG conformance or measured efficiency.
- Formal references use stable first-party URLs/DOIs; copied quotes and copyrighted passages are intentionally omitted from the skill to minimize context and preserve attribution.
