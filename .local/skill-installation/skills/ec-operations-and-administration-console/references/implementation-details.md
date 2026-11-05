# Detailed decisions and failure cases

Read only for a decision needing more detail than the entry point. Apply the relevant steps to the
actual task; the sections are not a mandatory artifact or test checklist. Use existing project
contracts in their actual format. File names such as spec.md, plan.md or tasks.md are examples, not
required dependencies. Other skills are optional expertise, never enforcement mechanisms.

Product rules, stack choices and authority locations must be verified in the Ec consumer. Examples
do not establish deployed capabilities or override its current contract.

## Implementation method

1. **Discover actual authority and operational surfaces.** Inspect applicable Ec normative
   documents, current project workflow, available Rust APIs, PostgreSQL schema, YAML resource
   contracts, Vue/TypeScript modules, authentication and existing observability. Identify
   administrator, operator, security auditor and read-only roles; instance-versus-project scope;
   component and service owners; available recovery tools; and what is *implemented, planned or
   unavailable*. Do not invent Ec endpoints, service levels, retention periods or operational
   capabilities.
2. **Separate desired state, observations and command results.** Versioned canonical Ec **YAML**
   governs desired global policies (where a declared schema exists): e.g. retention settings, alert
   routing or maintenance policies. Visual/Form/YAML must edit the same semantic configuration with
   validation, diff, authorization and publication; route changes to `ec-configuration-publication`.
   PostgreSQL/telemetry carry observations, sessions, audit events, administrative requests, jobs
   and actual backup/restore evidence. Secret values belong in a secrets backend; configuration
   stores references. Never create a competing desired-configuration store in the console.
3. **Make service health a qualified, sourced decision.** Define evidence by component, dependency,
   scope, observed-at/received-at, observation version, freshness budget and confidence; distinguish
   process liveness, serving readiness, dependency availability, operational degradation, telemetry
   blindness and desired-vs-observed drift. A green probe, outdated heartbeat, successful HTTP
   response or absence of alerts does not establish end-to-end service health. Stale, missing,
   contradictory, disconnected or unauthorized evidence must render **unknown** or qualified
   degraded states, never fabricated success. Present last-updated times, scope, thresholds and
   diagnostic links. See [health evidence and operator
   triage](health-evidence-and-information-architecture.md).
4. **Design actionable, scoped administration views.** Prefer diagnostic tables/timelines over
   decorative dashboards. Organize instance status, configuration/revisions/drift, audit, retention,
   backups/restores, security, maintenance and operational tasks. For each indicator state question,
   owner, evidence, unit, window, scope, timestamp, next action and whether value is measured,
   derived or estimated. Provide search, filters, stable pagination, mobile alternatives, keyboard
   access and visible focus; apply WCAG 2.2 AA and consistent text/icon status in both themes. Never
   claim that mockups or synthetic view-model checks demonstrate accessibility.
5. **Make audit evidence attributable and bounded.** Specify event identity, actor/service identity,
   action, resource and scope, outcome, reason/category, policy/config revision, correlation/trace,
   source and observation timestamps and retention classification. Include denials, attempted
   actions, approved/rejected decisions and actual outcomes where applicable. Enforce server-side
   scoped access to search/export, redact secrets/PII, protect against log injection, distinguish
   missing events from no activity, preserve export limits/continuation and legal holds. Append-only
   storage is *not automatically* tamper-evident; require an independently verifiable
   integrity/retention design if that guarantee is stated. See [audit
   controls](admin-command-and-audit-safety.md).
6. **Guard dangerous operations with staged evidence.** For restart, maintenance, drain, disable,
   purge, retention change, export, restore initiation or other project-defined actions, produce a
   target-specific impact preview bound to request identity, exact target/scope, current
   policy/config revision and expiry. Reauthorize on the **backend** at submission and at effect
   when relevant; enforce separation of duties and approval if the concrete policy requires it. Use
   expected revisions, one-time or scoped idempotency keys, durable command/audit recording and
   verified status transitions. `202 Accepted` is **not** completion. Reject stale previews,
   privilege changes, mismatched targets, unknown execution state and forged approvals; never
   promise atomic rollback of external effects. See [safe administrative
   actions](admin-command-and-audit-safety.md).
7. **Represent recovery honestly.** Distinguish last completed backup, backup integrity check,
   independently tested **restore**, recovery point, observed data freshness and actual service
   recovery. Inspect actual backup type: a logical dump is not a physical base backup plus WAL
   recovery chain, and cannot alone substantiate PITR. PostgreSQL `pg_verifybackup` cannot certify
   successful restoration. Expose each evidence source and its timestamp; show **not tested** when
   no restore drill ran. Treat emergency access, legal holds, destructive retention, failover and
   disaster-recovery thresholds as project workflow decisions, not hidden UI defaults. See [recovery
   and accessible UX oracles](recovery-and-ux-oracles.md).
8. **Prove behavior, failure handling and ownership.** Test stale/missing/contradictory evidence,
   cross-scope access, audit append failure, concurrent admins, stale policy and authorization,
   replayed commands, forged approvals, incomplete exports, legal-hold constraints, degraded
   dependencies, disconnected browsers, unconfirmed backend operations and status out-of-order
   updates. Verify Rust/PostgreSQL transactions and resilience, actual audit/access control, Vue
   component tests, screen readers/keyboard, light/dark and responsive behavior, and
   Visual/Form/YAML round-trip before claiming Ec conformance. Package reference-model tests are
   **not tests of Ec**.


## Further references by decision

Read only the reference relevant to the unresolved decision. Research registers describe imported authorship, not current execution.

- [Safe Administrative Commands, Audit and Authorization](admin-command-and-audit-safety.md): admin command and audit safety.
- [Evidence-bound Health and Operations Information Architecture](health-evidence-and-information-architecture.md): health evidence and information architecture.
- [Recovery Evidence, Accessible Operator UX and Test Oracles](recovery-and-ux-oracles.md): recovery and ux oracles.
- [Routing, Product Acceptance and Negative Oracles](routing-and-test-oracles.md): routing and test oracles.
- [Official and Scientific Source Register — Ec Operations and Administration Console](sources-and-decisions.md): source provenance or a disputed recommendation.
