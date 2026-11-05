# Sources, Access Evidence, and Derived Engineering Decisions

> Imported authoring record. Research dates, consultations and execution claims below belong to the supplied source package; they were not reproduced in this adaptation. Referenced validation scripts, fixtures, product norms and logs are not bundled unless present in this folder. Verify their availability and provenance before use. No historical PASS is current acceptance.

**Scope:** Ec desired-configuration publication, concurrent updates, immutable revision identity, authorized promotion, operational reconciliation, and recovery. Sources were consulted on 31 October 2026. This ledger documents *what was checked*; it does not assert exhaustive reading of every publication, every page of an entire book, or every version of a product.

## Internal normative authorities

| ID | Document | Used scope | Authority |
|---|---|---|---|
| N1 | `EC-REGRAS-DE-DESIGN-E-PRODUTO.md` | Full source reviewed; especially product invariants, YAML publication/reconciliation, security, user-visible status, architecture and acceptance | Ec requirement source, **not** external scientific evidence |
| N2 | `EC-REGRAS-CRIACAO-DE-TELAS-E-IMAGENS.md` | Full source reviewed; especially status honesty, canonical desired YAML, operation/approval feedback, revision and drift representation | Ec requirement source, **not** an independent requirement copy |

The two original Portuguese-language documents are deliberately **not** translated or rewritten as new canonical policy. English-language skill instructions only reference their authority. The current SDD `spec.md` supplies feature-specific acceptance criteria, not this ledger.

## Primary technical documentation

| ID | Official source | Consulted evidence and resulting decision | Access / caution |
|---|---|---|---|
| O1 | [YAML 1.2.2](https://yaml.org/spec/1.2.2/) | Desired-resource bytes are YAML; parser/schema behavior is owned by the schema skill. Do not infer YAML formatting equivalence from resource-semantic equality. | Normative reference and prior skill; not a publication-transaction protocol. |
| O2 | [Git `update-ref`](https://git-scm.com/docs/git-update-ref) | Expected old OID checks support ref-level compare-and-swap; transaction CLI supports grouped updates, but readers may observe a subset of multiple ref changes. | Official Git manual consulted. Local ref operations are not remote repository guarantees. |
| O3 | [Git `push`](https://git-scm.com/docs/git-push) | Remote ref updates need provider/branch-policy verification; `--atomic` depends on remote support and applies to pushes, not Git+PostgreSQL. Protect history; avoid blind `--force`. | Official Git manual consulted; remote hosting behavior remains integration-specific. |
| O4 | [RFC 9110, HTTP Semantics §13, §15](https://www.rfc-editor.org/rfc/rfc9110.html) | `If-Match` prevents lost updates when used with strong validators; `412` is the precondition-failed response; `409` is distinct application conflict. | IETF standards-track source read in relevant sections; do not invent HTTP endpoint contracts. |
| O5 | [RFC 6585 §3](https://www.rfc-editor.org/rfc/rfc6585.html) | `428 Precondition Required` is an option when the chosen API requires conditional requests. | Official RFC consulted. It is not mandated in every Ec API. |
| O6 | [PostgreSQL 18: Transaction Isolation §13.2](https://www.postgresql.org/docs/18/transaction-iso.html) | Serializable transactions may abort and require full retry (`40001`); choose explicit CAS/constraints/locks to fit the invariant. Database isolation does not cover remote Git writes. | Official PG 18 docs consulted; actual Ec PG major version not observed. |
| O7 | [AWS: Transactional Outbox Pattern](https://docs.aws.amazon.com/prescriptive-guidance/latest/cloud-design-patterns/transactional-outbox.html) | Atomic database state + outbox event helps reliable notification; consumers must be idempotent. **It does not make Git and PostgreSQL one transaction**. | AWS prescriptive guidance (engineering practice, not peer-reviewed experimental proof). |
| O8 | [Kubernetes: Controllers](https://kubernetes.io/docs/concepts/architecture/controller/) | A controller reconciles observed toward desired, potentially repeatedly; mere enqueue or intent is not confirmed convergence. | Official architecture guidance; concepts transferred, no claim Ec uses Kubernetes. |
| O9 | [Kubernetes API Concepts](https://kubernetes.io/docs/reference/using-api/api-concepts/) | `resourceVersion` detects stale updates and returns `409` in Kubernetes; serves as an example of explicit state validation, not as an Ec API field. | Official docs consulted; **not** permission to adopt Kubernetes APIs verbatim. |
| O10 | [Kubernetes Objects: `spec` vs `status`](https://kubernetes.io/docs/concepts/overview/working-with-objects/) | Separate desired intent from observed status; do not write runtime statuses into YAML configuration. | Official docs consulted; Ec domain remains distinct. |
| O11 | [Argo CD: Automated Sync Policy](https://argo-cd.readthedocs.io/en/stable/user-guide/auto_sync/) | Desired commit and live state may diverge; reconciliation and self-healing are separate policy decisions; retries and rollback have operational nuances. | Official product docs consulted; Argo semantics not an Ec contract. |
| O12 | [Argo CD: Diff Strategies](https://argo-cd.readthedocs.io/en/latest/user-guide/diff-strategies/) | Structured desired/live/last-applied diffs help reveal drift; compare before applying, avoid assuming all fields are stable. | Official docs consulted; note some Argo strategies are discontinued. |
| O13 | [OWASP Authorization Cheat Sheet](https://cheatsheetseries.owasp.org/cheatsheets/Authorization_Cheat_Sheet.html) | Default-deny and re-check every protected operation, including final publication; a preview cannot grant authority. | Maintained OWASP guidance, not peer-reviewed controlled study. |
| O14 | [OWASP CI/CD Security Cheat Sheet](https://cheatsheetseries.owasp.org/cheatsheets/CI_CD_Security_Cheat_Sheet.html) | Protected configuration updates, review, credential handling, auditing and separation of privileges are important threats to evaluate. | Maintained OWASP guidance; implementation-specific controls still require threat modeling. |

## Peer-reviewed / archival research

| ID | Work and venue | What the accessible text supports | Access / transfer limit |
|---|---|---|---|
| A1 | Berenson, Bernstein, Gray, Melton, O'Neil, O'Neil, [*A Critique of ANSI SQL Isolation Levels*](https://web.stanford.edu/class/cs345d-01/rl/SQL-isolation-critique.pdf), ACM SIGMOD 1995 | Concurrent transactions can expose anomalies beyond simplistic isolation-level labels; write explicit consistency invariants rather than relying on a vague "transactional" claim. | Original paper PDF available and relevant material inspected. Does not design Ec's SQL schema. |
| A2 | Ports and Grittner, [*Serializable Snapshot Isolation in PostgreSQL*](https://vldb.org/pvldb/vol5/p1850_danrkports_vldb2012.pdf), PVLDB 5(12), 2012 | Implementation and anomaly detection for PostgreSQL SSI; supports deliberate selection of serializable isolation and retry for relevant cross-row predicates. | Original full PDF accessible; relevant sections reviewed. Historical evaluation results are **not** Ec performance evidence. |
| A3 | Sun et al., [*Anvil: Verifying Liveness of Cluster Management Controllers*](https://www.usenix.org/system/files/osdi24-sun-xudong.pdf), OSDI 2024, pp. 649–666 | Distinguishes safety from liveness and defines eventually stable reconciliation under explicit assumptions (e.g. faults eventually cease); incomplete intermediate-state handling can prevent progress after a crash. | Open-access conference paper; introduction, model and failure treatment inspected. The existence of Anvil does not prove any Ec controller verified. |
| A4 | Burrows, [*The Chubby Lock Service for Loosely-Coupled Distributed Systems*](https://static.usenix.org/events/osdi06/tech/full_papers/burrows/burrows_html/index.html), OSDI 2006 | Illustrates coarse-grained coordination, leader/lock ownership, availability trade-offs and client notifications. Relevant when choosing coordination but **not** a prescription to deploy Chubby. | Author's full conference paper available; introduction and relevant system sections inspected. |
| A5 | Verma et al., [*Large-scale Cluster Management at Google with Borg*](https://research.google/pubs/large-scale-cluster-management-at-google-with-borg/), EuroSys 2015 | Declarative job specification, admission and monitoring have operational value; does not prove Ec's specific publication workflow. | Publisher/author abstract verified; **full paper not substantively reviewed for this skill**. Background only. |
| A6 | Ongaro and Ousterhout, [*In Search of an Understandable Consensus Algorithm*](https://www.usenix.org/system/files/conference/atc14/atc14-paper-ongaro.pdf), USENIX ATC 2014 | Replicated-log consensus addresses a different guarantee than simple Git CAS and should not be assumed necessary for Ec without HA requirements. | Conference paper abstract and introductory sections checked; not a complete consensus-engine selection review. |

## Book

| ID | Book | Use | Access limitation |
|---|---|---|---|
| B1 | Leslie Lamport, [*Specifying Systems: The TLA+ Language and Tools for Hardware and Software Engineers*](https://lamport.azurewebsites.net/tla/book.html), Addison-Wesley, 2002 | Justifies specifying transitions, safety invariants, eventual progress assumptions and model-based checking, especially for ambiguous acknowledgment and crash windows. | Author-hosted full PDF is accessible; contents and relevant introductory formal-method material checked, **not all 364 pages read**. The supplied Python reference model is not a TLA+ proof. |

## Evidence-to-method synthesis (not quotations)

| Method decision | Supporting sources | Why it appears in the skill |
|---|---|---|
| Explicit base revision and atomic authority check | O2, O3, O4, O5, O9; A1, A2 | Prevent silent lost updates and stale approval publication; require meaningful conflict diagnostics. |
| Distinct durable publication and target application acknowledgment | O8, O10, O11, O12; A3 | Avoid conflating intent, accepted YAML revision and observed convergence. |
| No claimed Git+PostgreSQL atomic transaction | O2, O3, O6, O7; A1 | Force a design for duplicate events, unknown remote outcome and projection repair. |
| Revalidate authority and content-bound approval | O13, O14, O4 | Prevent TOCTOU escalation and approval of a different YAML revision than was reviewed. |
| Retry-safe, restart-safe reconciliation with convergence assumptions | O8, O11; A3, A4; B1 | Tests must cover partial effects, crash/restart and stale workers, not just the happy path. |
| Explicit immutable revision pin and audit linkage | N1, N2; O2, O3 | Bind execution to a verifiable effective revision rather than a mutable branch name. |
| Treat rollback as a new reviewed desired-state revision | N1, N2; O11 | Preserve history; do not promise external side effects reversed. |

## Research and confidence boundaries

No forums, social-media anecdotes, unverified AI summaries, or benchmark numbers were used as evidence. Official product manuals, open-access archival research and an author-hosted book are different types of evidence and are labeled accordingly. **No Ec source repository, actual Git authority, real PostgreSQL schema, configured SDD feature, agent session, operational permissions or production publication test was supplied or executed.** The reference model tests in this package establish only behavior of that synthetic model, not implementation correctness or model equivalence to Ec.
