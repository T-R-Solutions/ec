# Evidence-bound Health and Operations Information Architecture

> Use the destination's existing requirements and evidence format; named workflow documents are examples, not prerequisites. Optional SDD mapping is configured outside this skill.

## Distinguish different truths

| Question | Data authority | Inference rule and UI wording |
|---|---|---|
| Is the service process alive? | Liveness probe of that process | `alive` only at the probe's observation time; does not imply readiness |
| Is it able to serve the requested workload? | Readiness check and dependency/canary evidence | Show `ready`, `not ready` or `unknown` with age; avoid mapping liveness to readiness |
| Are users succeeding? | Appropriately defined SLI or end-to-end checks | Show window, denominator, scope and missing data; not a derived result of readiness alone |
| Is telemetry reliable? | Collection receipt, heartbeat, sequence, source and ingest timestamps | Stale, unavailable, contradictory or future-dated = `unknown`; a zero-valued metric is not a missing sample |
| Is desired configuration applied? | Versioned canonical YAML + authoritative reconciliation evidence | Published != effective; compare desired and effective revisions using the publication owner |
| Has an alert fired? | Alert evaluator and versioned alert policy | Show `firing`, `pending`, `resolved`, `suppressed` or `evaluation unknown` only if evaluator confirms it |
| Has an operator action completed? | Authoritative operation receipt and outcome | Request accepted != operation completed; timeout => `unknown` pending reconciliation |

**Illustrative health summary aggregation** (an project workflow design candidate, not a fixed Ec contract): confirmed critical component outage = `unavailable`; otherwise any required missing/stale/contradictory observation = `unknown`; otherwise confirmed degraded component = `degraded`; only a fully evaluated, fresh and complete required set can display `healthy`. Always list every component's own state instead of concealing the source detail. Disagreement between a probe and user-experienced SLI should be visible; do not average it away.

## Diagnostic query contract

A query should declare `scope`, `subject resource`, `time range`, `unit`, `aggregation`, `required permission`, `source`, `sampled_at`, `received_at`, `last_successful_refresh`, `data lag`, `quality`, `coverage`, `warning`, `links` and `correlation_id`. The same time range must apply to a visual comparison; if scope/unit/window differ, reject an apples-to-oranges conclusion.

Separate **source time** from **observed ingestion time** (OpenTelemetry Log Data Model); use a monotone local elapsed-time clock for timeout logic where possible. Cross-node timestamps may be skewed. For status updates, compare an explicit monotonically increasing authoritative sequence or revision; reject older updates and flag same-sequence contradictory payloads as `unknown`.

**Prometheus guidance:** avoid labels with unbounded per-user/per-run cardinality. Preserve high-cardinality correlation identifiers in trace/log backends rather than turning them into metric labels by default. Alert operators based on actionable user impact, not a page for every low-level technical symptom.

## Suggested console routes (not implemented)

1. **Instance overview**: current service/tenant scope, confirmed incidents, affected tasks, staleness notice, maintenance banner and operator next actions.
2. **Service health and dependencies**: explicit liveness/readiness/customer-impact columns, source, age and drill-down links to owning domain.
3. **Incidents and alerts**: search/filter by severity, state, owner, time, fingerprint; suppression/acknowledgment distinguished from actual resolution.
4. **Administrative settings**: resource-based global policy editing using one YAML semantic draft, represented via Visual/Form/YAML, before/after diff, review and effective revision.
5. **Audit explorer**: immutable event ID, actor, scoped target, decision, revision, event/observed times, correlation and redacted change summary; filtered export with independent permission.
6. **Recovery and maintenance**: scheduled/accepted/running/verified/rehearsed status with warnings about partial evidence and recovery dependencies.

Use a specific operator journey and accessible statuses. Never fill empty charts with invented values. Status labels must not depend only on color. A global dashboard cannot expose user or service secrets, even for a privileged viewer.

## Source linkage

OpenTelemetry logs/metrics/events (O01–O03), Kubernetes probes (O09), PostgreSQL monitoring freshness (O10), Prometheus and Grafana alert guidance (O07/O08), Google SRE monitoring/SLOs (O04–O06), gray failure research (P01) and tail-at-scale risks (P06). See [source register](sources-and-decisions.md).
