# Routing, Product Acceptance and Negative Oracles

> Use the destination's existing requirements and evidence format; named workflow documents are examples, not prerequisites. Optional SDD mapping is configured outside this skill.

All scenarios below are **planned evaluation cases**, not executed Ec application tests. A routing outcome is appropriate only when an LLM agent is actually evaluated using fixed prompts and outcome adjudication.

## Sixteen routing scenarios (planned)

| ID | Request | Expected routing |
|---|---|---|
| R01 | Implement instance overview health with evidence freshness | Activate this skill |
| R02 | Build audit explorer with scoped filters and secure export | Activate this skill |
| R03 | Global policy editing across Visual, Form and YAML | Activate with schemas/publication dependencies |
| R04 | Show safe server restart request/confirmation | Activate; defer actual restart protocol contract to project workflow |
| R05 | Plan operator recovery-status and restore evidence | Activate; delegate backup engine implementation |
| R06 | Investigate stale health and contradictory canary results | Activate |
| R07 | Design incident acknowledgment versus resolution | Activate |
| R08 | Define a privileged audit export view | Activate |
| R09 | Implement OpenTelemetry collector instrumentation | Do NOT activate as primary; use `observability-and-diagnostics` |
| R10 | Write canonical YAML parser | Do NOT activate; use `ec-declarative-resource-schemas` |
| R11 | Implement effective RBAC engine | Do NOT activate; use `ec-effective-permissions` |
| R12 | Implement scheduler queue and fencing | Do NOT activate; use `cicd-scheduler` |
| R13 | Implement webhook signature verifier | Do NOT activate; use `ec-integrations-and-webhooks` |
| R14 | Implement agent heartbeat wire contract | Do NOT activate; use `ec-agent-control-protocol` |
| R15 | Implement plugin sandbox | Do NOT activate; use `ec-plugin-isolation-and-lifecycle` |
| R16 | Create a backup service and validate WAL replay | Do NOT activate as primary; delegate to PostgreSQL/reliability owners |

## Thirty-six real Ec acceptance cases (planned, not executed)

| ID | Real-system oracle / acceptance evidence |
|---|---|
| A01 | Process alive but readiness fails: dashboard cannot show customer availability as healthy. |
| A02 | Readiness true but end-to-end canary fails: show customer-impact disagreement. |
| A03 | Missing required data: show unknown, not zero or healthy. |
| A04 | Source timestamp older than project workflow staleness budget: show last-known/stale. |
| A05 | Future-dated source event above allowed skew: mark unknown. |
| A06 | Same-sequence conflicting observations: flag data-quality conflict. |
| A07 | Delayed old observation cannot overwrite newer version. |
| A08 | Partial telemetry outage remains visible even if dependency probes pass. |
| A09 | Compare metrics with differing windows: UI disallows misleading comparison. |
| A10 | Alerts with suppressed state do not claim service resolution. |
| A11 | Acknowledged incident remains incident until authoritative resolution. |
| A12 | Every indicator identifies scope, unit, time and source or says unavailable. |
| A13 | Cross-project audit search denies unscoped rows at API level. |
| A14 | Audit-export permission is checked separately from audit-read permission. |
| A15 | CSV export neutralizes formula and control-character injection. |
| A16 | Audit search returns no plaintext credential/token or unredacted sensitive request body. |
| A17 | Revoked operator permission between preview and commit prevents mutation. |
| A18 | Stale desired/effective revision invalidates destructive preview. |
| A19 | Duplicate request same actor/key/content admits exactly one local command. |
| A20 | Same key with different payload/actor is rejected. |
| A21 | An audit-store failure prevents a command from being reported admitted under required atomic boundary. |
| A22 | Privileged command times out after acceptance: UI shows unknown, later reconciles by receipt. |
| A23 | Operator closing confirmation dialog does not run action. |
| A24 | HTTP 202 only renders accepted/pending, never confirmed. |
| A25 | A partial external effect never claims local DB transaction extends to remote provider. |
| A26 | Backup task completion does not imply restore possible. |
| A27 | `pg_verifybackup` pass does not render 'recovery tested'. |
| A28 | Controlled isolated restore + app validation is necessary for rehearsed. |
| A29 | Missing WAL/key or wrong external dependency prevents proven recovery label. |
| A30 | Global YAML draft roundtrip via Visual/Form/YAML retains semantic fields and approval state. |
| A31 | Unpublished global draft does not change displayed effective revision. |
| A32 | Role/access restrictions remain enforced during client-side route navigation. |
| A33 | Stale Vue response from a previous tenant is discarded, not rendered. |
| A34 | Offline health view shows last-known timestamp and 'not current'. |
| A35 | Keyboard-only navigation reaches all admin audit/confirm/incident actions. |
| A36 | Assistive-tech checks confirm critical status messages and focus restoration in real browser. |

## Agent evaluation rubric (planned)

Use 16 routing prompts, 12 implementation prompts (health/commands/audit/recovery/UI), 8 adversarial prompts (overclaiming statuses, stale approvals, confidentiality) and 4 cross-domain delegation prompts. Record prompt, model, context version, artifacts, pass/fail and independent reviewer notes. Do not award a pass for simply reciting rules: require correct system boundaries, test evidence and explicit unknowns. No scores are reported here.
