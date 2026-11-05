# Recovery Evidence, Accessible Operator UX and Test Oracles

> Use the destination's existing requirements and evidence format; named workflow documents are examples, not prerequisites. Optional SDD mapping is configured outside this skill.

## Backup/recovery evidence ladder

1. **Policy configured** — canonical Ec YAML desired settings (if the Ec resource is a declared desired setting); no backup has yet run.
2. **Backup attempt accepted/started** — a runtime operation request and a time-bound receipt, not a usable backup.
3. **Backup bytes committed** — storage/provider completion receipt with protected location and immutable ID; no restore proof.
4. **Integrity verified** — tool/scheme evidence with timestamps and explicit coverage; `pg_verifybackup` has documented coverage limits.
5. **Restoration rehearsed** — isolated controlled restore, integrity/application checks, verified desired configuration reconciliation, keys and necessary WAL/archive availability.
6. **Recovery capability evidenced** — review of last successful rehearsal, measured recovery objectives if actual drills supply measurements, retention and dependency coverage. Do not imply a measured RPO/RTO from a configured target.

Restoration, purge, retention shortening, plugin disablement, shutdown and global authentication changes require specialized safety preview. Never offer a single-click destructive action that bypasses project workflow approvals. The presence of a backup icon or completed job is not proof of recoverability.

## Observable and accessible UX

- Distinguish `unknown`, `degraded`, `unavailable`, `maintenance` and `healthy` with text and icon shape in both themes, not color only.
- Each nontrivial status declares the time and source of evidence, including whether it is stale/partial or permission restricted.
- With an offline/failed fetch, preserve last-known evidence as **last-known**, not live green. Cancel or ignore stale Vue watchers and requests; protect multi-tab filters and races.
- Keyboard navigation, focus-visible behavior, appropriately labeled destructive confirmation dialogs, and accessible live status changes must be verified in a real browser using assistive technology.
- Compare time series only with congruent units and windows; use explicit `no samples` and `zero` states.
- Attach runbook and correlation links to incident cards; distinguish `acknowledged` from `resolved` and `muted` from `healthy`.
- Restrict export columns/rows to server-authorized scope; client filters are not security boundaries.

## Validation layers

| Layer | Can establish | Cannot establish |
|---|---|---|
| Python pure reference model | Policy-bound, stale/duplicate and truthful state-transition invariants under modeled assumptions | Real Ec behavior, clock correctness, persistent durability |
| Local SQLite transaction fixture | Local admission and audit record atomicity under single-database failure injection | PostgreSQL isolation, audit tamper resistance, multi-system atomicity |
| TypeScript strict model | Typed UI status mapping and rejected out-of-order responses | Vue DOM, WCAG AA, actual browser async behavior |
| Original project workflow validator | Structural correctness of skill and optional bridge paths | project workflow requirements correctness or agent execution quality |
| Real Ec integrations (pending) | Rust/PostgreSQL controls, backup restore, browser/a11y, independent audit integrity under selected threat model | Universal absence of bugs; external guarantees beyond tests |

## Unresolved project workflow decisions

Exact status API versions, component criticality, liveness/readiness checks, stale/future timestamp tolerance, alert policy and SLO definitions, tenant partitioning, privileged actions and approval gates, reauthentication/break-glass contract, idempotency TTL, audit content/retention/integrity, export policy, backup provider and drills, publication/admission atomic boundaries, permission visibility and browser acceptance criteria.

Primary support: PostgreSQL backup verification/PITR/dump (O11/O12/O29/O30), Vue watchers/tests (O21/O22), WCAG and ARIA (O19/O20), NIST/OWASP auditing (O13–O16), recovery/snapshot literature (P02/P05/P07). See [source register](sources-and-decisions.md).
