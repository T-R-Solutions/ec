# Official and Scientific Source Register — Ec Operations and Administration Console

> Imported authoring record. Research dates, consultations and execution claims below belong to the supplied source package; they were not reproduced in this adaptation. Referenced validation scripts, fixtures, product norms and logs are not bundled unless present in this folder. Verify their availability and provenance before use. No historical PASS is current acceptance.

**Reviewed:** 2026-10-31. **Research rule:** official vendor/specification text, standards, original research papers and published scholarly work only. No forums, unverified metrics, invented benchmark results or unsupported claims of Ec deployment. The table distinguishes **sections examined** from **abstract/metadata only**. URLs are origin references, not Ec configuration sources. The two mandatory Ec normative files were read in full and take precedence over examples from third-party products.

## Ec primary requirements (read in full)

| ID | Authority | Source | Depth and Ec decision |
|---|---|---|---|
| E01 | Ec product norm | `EC-REGRAS-DE-DESIGN-E-PRODUTO.md` (447 lines; supplied project file) | Full normative document examined. Instance administration, audit, security, global policies, availability, retention, backups, operational truth, desired YAML and Visual/Form/YAML parity. |
| E02 | Ec visual norm | `EC-REGRAS-CRIACAO-DE-TELAS-E-IMAGENS.md` (206 lines; supplied project file) | Full normative document examined. Accessible operational states, truthful dashboards and operator actions, coherent navigation, no pretend controls or invented data. This skill supplies implementation methods, not a UI image. |
| E03 | SDD 1.3.0 | `skills/sdd/model/` and `skills/sdd/chain/README.md` from original supplied `skills_zip.zip` | Workflow/bridge contract and validator inspected. SDD owns actual specs, policies, approvals and evidence; bridge stays optional. |

## Official standards, primary documentation and authoritative practitioner guidance

| ID | Source and verified URL | Sections examined and relevant finding |
|---|---|---|
| O01 | OpenTelemetry — [Logs Data Model](https://opentelemetry.io/docs/specs/otel/logs/data-model/) | `Timestamp`, `ObservedTimestamp`, trace/resource correlation. Event time differs from observation time. |
| O02 | OpenTelemetry — [Metrics Data Model](https://opentelemetry.io/docs/specs/otel/metrics/data-model/) | Temporality, explicit no-recorded-value flags, metric stream ownership. Missing != zero. |
| O03 | OpenTelemetry — [Event Semantic Conventions](https://opentelemetry.io/docs/specs/semconv/general/events/) | Time-of-occurrence versus observed-time semantics. Event identity separate from collection time. |
| O04 | Google SRE book — [Monitoring Distributed Systems](https://sre.google/sre-book/monitoring-distributed-systems/) | Black-box/white-box, symptoms versus causes, four golden signals. Practitioner synthesis, not an Ec metric baseline. |
| O05 | Google SRE Workbook — [Monitoring](https://sre.google/workbook/monitoring/) | Sources of monitoring data, alert routing, high-cardinality costs and retention windows. |
| O06 | Google SRE Workbook — [Alerting on SLOs](https://sre.google/workbook/alerting-on-slos/) | SLO burn-rate windows and low-traffic caveat. Example thresholds are not automatically Ec policy. |
| O07 | Prometheus — [Alerting rules](https://prometheus.io/docs/prometheus/latest/configuration/alerting_rules/) | Pending/firing and `keep_firing_for`; an evaluator state is not a service health claim. |
| O08 | Grafana — [No Data and Error states](https://grafana.com/docs/grafana/latest/alerting/fundamentals/alert-rule-evaluation/nodata-and-error-states/) | Missing samples vs query errors vs normal, alert evaluation semantics; illustrative UI counterexample for 'no data = healthy'. |
| O09 | Kubernetes — [Liveness, readiness and startup probes](https://kubernetes.io/docs/tasks/configure-pod-container/configure-liveness-readiness-startup-probes/) | Distinct probe purpose; readiness governs whether traffic is accepted, not universal availability. Ec does not presume Kubernetes deployment. |
| O10 | PostgreSQL 18 — [Monitoring statistics](https://www.postgresql.org/docs/current/monitoring-stats.html) | Statistics flush/caching and reset limits; raw counters can be delayed or reset. PostgreSQL 18 is the currently documented version, not a claim about installed Ec version. |
| O11 | PostgreSQL 18 — [`pg_verifybackup`](https://www.postgresql.org/docs/current/app-pgverifybackup.html) | Manifest/WAL verification scope; documentation explicitly requires test restores and application checks. |
| O12 | PostgreSQL 18 — [Continuous archiving and PITR](https://www.postgresql.org/docs/current/continuous-archiving.html) | WAL/base backup relationship and recovery prerequisites. No automatically valid RPO/RTO. |
| O13 | OWASP — [Logging Cheat Sheet](https://cheatsheetseries.owasp.org/cheatsheets/Logging_Cheat_Sheet.html) | Security event semantics, data sanitization, logging failures, sensitive data restrictions and injection risks. |
| O14 | NIST — [SP 800-92 Guide to Computer Security Log Management](https://csrc.nist.gov/pubs/sp/800/92/final) | Published final Sep 2006: log collection, protection, review and retention planning. NIST SP 800-92 Rev. 1 **remains a draft** in the examined NIST publication listing; do not label it final. |
| O15 | NIST — [SP 800-53 Rev. 5/Release 5.2.0](https://csrc.nist.gov/pubs/sp/800/53/r5/upd1/final) | Audit and Accountability and Access Control families; controls are guidance, not a claim of certified compliance. |
| O16 | NIST — [SP 800-61 Rev. 3 final](https://csrc.nist.gov/pubs/sp/800/61/r3/final) | 2025 revision for incident response and recovery; explicitly supersedes Rev. 2. |
| O17 | IETF — [RFC 9110 HTTP Semantics](https://www.rfc-editor.org/rfc/rfc9110.html) | 202 Accepted non-final; 412 precondition failures, ETag/If-Match. No API endpoint selected. |
| O18 | IETF — [RFC 9457 Problem Details for HTTP APIs](https://www.rfc-editor.org/rfc/rfc9457.html) | 2023 successor to RFC 7807, safe errors with minimal information exposure. JSON HTTP wire format is not Ec YAML desired configuration. |
| O19 | W3C — [WCAG 2.2](https://www.w3.org/TR/WCAG22/) | Focus visible and 4.1.3 status messages; reference target is WCAG 2.2 AA. No conformance claimed from synthetic checks. |
| O20 | W3C — [ARIA APG Modal Dialog](https://www.w3.org/WAI/ARIA/apg/patterns/dialog-modal/) | Keyboard/focus/labeling of risk confirmation flows. |
| O21 | Vue 3 — [Watchers](https://vuejs.org/guide/essentials/watchers.html) | Side-effect cleanup for stale async fetches; `onWatcherCleanup` only available in Vue 3.5+ and must be registered synchronously. Prefer callback `onCleanup` where version independent. |
| O22 | Vue 3 — [Testing Guide](https://vuejs.org/guide/scaling-up/testing.html) | Unit/component/end-to-end layers. Pure TS tests cannot prove Vue DOM behavior. |
| O23 | OWASP — [Authorization Cheat Sheet](https://cheatsheetseries.owasp.org/cheatsheets/Authorization_Cheat_Sheet.html) | Deny-by-default and server-side enforcement on every request. Applies to audit reads/exports and privileged writes. |
| O24 | PostgreSQL 18 — [Transaction isolation](https://www.postgresql.org/docs/current/transaction-iso.html) | Distinct isolation levels, retry and concurrency behavior. Same-database transaction atomicity does not extend to third parties or Git. |
| O25 | PostgreSQL 18 — [INSERT / ON CONFLICT](https://www.postgresql.org/docs/current/sql-insert.html) | Unique constraints and on-conflict as one possible local idempotent admission mechanism. Not an actual Ec schema. |
| O26 | TypeScript — [Discriminated unions](https://www.typescriptlang.org/docs/handbook/unions-and-intersections.html) | Checked per-state view-model data and exhaustive rendering. |
| O27 | SQLite — [Transactions](https://www.sqlite.org/lang_transaction.html) | Controlled local SQLite failure-injection fixture only; does not prove PostgreSQL or distributed transaction behavior. |
| O28 | NIST — [Log Management project status](https://csrc.nist.gov/projects/log-management) | Rev. 1 of SP 800-92 shown as draft, original 2006 SP 800-92 as final. Source-version caveat. |
| O29 | PostgreSQL 18 — [Backup and Restore overview](https://www.postgresql.org/docs/current/backup.html) | Backup approaches are not interchangeable; compare physical, archive/PITR and logical dump capability against the actual recovery objective. Do not invent a configured backup mechanism. |
| O30 | PostgreSQL 18 — [SQL Dump](https://www.postgresql.org/docs/current/backup-dump.html) | Logical dumps can restore database objects but are not continuous physical backups, and cannot substitute for PITR/WAL-based recovery. Choose test evidence by backup mode. |

## Scholarly and peer-reviewed papers

| ID | Research and venue | Sections examined / depth | Supported methodological inference; caveat |
|---|---|---|---|
| P01 | Huang et al., [*Gray Failure: The Achilles' Heel of Cloud-Scale Systems*](https://www.microsoft.com/en-us/research/wp-content/uploads/2017/06/paper-1.pdf), ACM HotOS 2017, DOI [10.1145/3102980.3103005](https://doi.org/10.1145/3102980.3103005) | Original author's PDF; abstract, motivation, differential observability and examples examined. | Multiple independent signals and disagreement notices; heartbeats can miss user-visible failures. No proof of Ec failure rates. |
| P02 | Candea & Fox, [*Crash-Only Software*](https://www.usenix.org/legacy/events/hotos03/tech/full_papers/candea/candea_html/), USENIX HotOS IX 2003 | Original published HTML, abstract and initial design sections examined; paper PDF link in search unexpectedly redirected to another paper, so HTML is the substantive evidence. | Recoverability is a design objective, but copying a crash/restart strategy without application-specific invariants is unsafe. |
| P03 | Schneier & Kelsey, [*Secure Audit Logs to Support Computer Forensics*](https://www.schneier.com/academic/archives/1999/05/secure_audit_logs_to.html), ACM TISSEC 1999 | Author-hosted bibliographic metadata and abstract examined; **full text not verified** due site throttling. | Motivates cryptographic log tamper-evidence; does **not** establish a simple hash-chain's security or Ec compliance. Publication title/source metadata may differ in catalogues. |
| P04 | Lamport, [*Time, Clocks, and the Ordering of Events in a Distributed System*](https://pages.cs.wisc.edu/~remzi/Classes/739/Spring2003/Papers/clocks.pdf), CACM 21(7), 1978, DOI 10.1145/359545.359563 | Full original PDF accessed, abstract, happened-before introduction and partial-order discussion examined. | Event timestamps alone cannot prove causal order; retain stable sequence/IDs and evidence provenance. |
| P05 | Chandy & Lamport, [*Distributed Snapshots: Determining Global States of Distributed Systems*](https://www.microsoft.com/en-us/research/wp-content/uploads/2016/12/Determining-Global-States-of-a-Distributed-System.pdf), ACM TOCS 3(1) 1985, DOI 10.1145/214451.214456 | Full original PDF accessed, abstract, introduction and checkpoint rationale examined. | Cross-system snapshots require an actual consistent snapshot/recovery protocol; do not infer this from unrelated backup statuses. |
| P06 | Dean & Barroso, [*The Tail at Scale*](https://research.google/pubs/the-tail-at-scale/), CACM 56, 2013 | Google's publisher metadata and abstract examined; **full article not verified**. | Tail latency can dominate complex monitoring views; measure query/UI latency with representative data instead of inventing a target. |
| P07 | Fox, [*Toward Recovery-Oriented Computing*](https://www.vldb.org/conf/2002/S25P01.pdf), VLDB 2002 | Original conference PDF, introduction and MTTR recovery perspective examined. | Prefer actionable recovery/restore drills over a cosmetic 'backup exists' badge; specific improvements remain hypotheses until tested. |
| P08 | Oppenheimer, Ganapathi & Patterson, [*Why Do Internet Services Fail, and What Can Be Done About It?*](https://www.usenix.org/legacy/event/usits03/tech/full_papers/oppenheimer/oppenheimer_html/), USENIX USITS 2003 | Original proceedings HTML examined: abstract, introduction, service-failure root causes, operator errors, repair delays and failure case studies. | Operator error was the leading cause **in two of three studied services**, not a universal failure-rate estimate; motivates scope previews and safer maintenance UI. |
| P09 | Nagaraja et al., [*Understanding and Dealing with Operator Mistakes in Internet Services*](https://www.usenix.org/legacy/event/osdi04/tech/full_papers/naragaraja/naragaraja_html/), USENIX OSDI 2004 | Original published HTML examined: abstract, introduction, operator experiments, validation environment and limitations; study included **43 experiments and 21 volunteer operators**. | Preview/validation gates reduce some studied operator errors, not all; do not transfer experimental detection rates to Ec or claim a preview is proof of safety. |

**Scholarly depth summary:** 9 published academic papers: 7 original full-text HTML/PDF publications examined by relevant sections (P01, P02 via proceedings HTML, P04, P05, P07, P08, P09); 2 abstract/metadata only (P03, P06). Reading selected sections is **not** equivalent to reading every page. The sources inform technique, not a mandatory Ec product specification.


## Engineering decisions and unresolved trade-offs

| ID | Decision grounded in evidence | Boundary/verification required |
|---|---|---|
| D01 | Split process liveness, readiness, end-to-end success, telemetry quality, revision convergence, alerts and operation status. | SDD defines real Ec probe contracts and critical dependencies. E01/O01/O09/P01. |
| D02 | Missing, stale, contradictory or future-timestamped evidence never implies healthy. Missing and zero are distinct. | SDD defines tolerances and authoritative collection sequence. O02/O08/P01. |
| D03 | Operator summary is actionable, source-linked and scoped; it does not replace telemetry instrumentation. | Signals and SLO calculations remain with `observability-and-diagnostics`. O04/O05/O06/P06. |
| D04 | Every privileged read/export/write is backend-authorized per scope/action; audit exports are separate capabilities. | `ec-effective-permissions` owns policy evaluation. O13/O15/O23. |
| D05 | Preview, confirmation, idempotent admission, execution and authoritative confirmation are separate phases. | SDD owns actions, approvals, TTLs and precondition contracts. O17/O24/O25/P08/P09. |
| D06 | Admission + admission audit may share one local DB transaction; remote side effects cannot join it atomically. | Local SQLite tests prove only an illustrative DB boundary. O24/O27. |
| D07 | Append-only or chained logs are not automatically tamper-proof; threat model and independent integrity proof required. | Dedicated security implementation and independent audit needed. O14/O15/P03. |
| D08 | Backup integrity, restoration rehearsal and recovery capability are separate levels of evidence. | PostgreSQL backup engine owner and actual restore drill required. O11/O12/O29/O30/P05/P07. |
| D09 | Cross-node timestamps are annotations, not definitive causality; use revisions and sequences. | Clock skew, duplicates and contradictory same-sequence events must be tested. O01/P04. |
| D10 | Three editing modes preserve canonical YAML for desired global settings; runtime/audit records are operational. | Schema/projection/publication skills own conversions and publication. E01/E02. |
| D11 | Console states use labels, time/source, keyboard focus and accessible status messages; never green for `unknown`. | Real Vue/browser assistive-tech tests needed. O19/O20/O21/O22. |
| D12 | Treat `No Data`, evaluator error, suppressed alert and active incident as distinct states. | Alert evaluator owns state; UI must not reinterpret automatically. O06/O07/O08. |
| D13 | No universal uptime, MTTR, RPO/RTO, restore success or performance assertion without Ec measurements. | Keep real measurements and objectives separate, with scope and provenance. E01/O04/P06/P07. |
| D14 | Treat multi-tenant scope filtering and audit export formula injection as explicit negative test oracles. | Data minimization, export policy and retention must be decided in SDD. O13/O15/O23. |
| D15 | Determine the actual backup strategy before promising PITR, full instance restore or data-object scope. A logical `pg_dump` is not a continuous WAL-based physical recovery chain. | SDD specifies backup implementation, verified recovery objective and restore exercise per strategy. O11/O12/O29/O30. |
| D16 | Historical operator-error studies motivate independent impact previews, scoped confirmations and post-effect verification, not zero-error assurances. | Design mitigations must be tested with actual Ec operators and threat model before asserting benefit; the studies' samples are not Ec benchmarks. P08/P09. |

## Normative conformity, conflicts and limitations

- **Applied:** `EC-REGRAS-DE-DESIGN-E-PRODUTO.md`; `EC-REGRAS-CRIACAO-DE-TELAS-E-IMAGENS.md`; original supplied SDD 1.3.0 model conventions.
- **External-model conflicts avoided:** Kubernetes health probes are an analogy, not a required Ec Kubernetes stack. Grafana's configurable `No Data -> Normal` option is **not** adopted as Ec default. An HTTP JSON problem response is wire data, not a second desired-configuration source. PostgreSQL metrics are operational observations, not canonical YAML. NIST controls are not claims of certification.
- **Remaining product-design decisions:** actual global resource schemas, action/approval APIs, tenant boundaries, status freshness, backup provider, audit integrity/threat model, maintenance workflows and recovery targets remain SDD tasks.
- **Evidence limit:** No Ec application repository, live Rust/agent runtime, deployed collectors, real PostgreSQL, restore exercise, independent security audit, Vue browser accessibility evaluation or LLM-agent evaluation was available. All executable tests in this package run on synthetic local models and cannot certify Ec security or functionality.
