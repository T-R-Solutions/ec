# Sources, evidence depth, scientific grounding and engineering decisions

> Imported authoring record. Research dates, consultations and execution claims below belong to the supplied source package; they were not reproduced in this adaptation. Referenced validation scripts, fixtures, product norms and logs are not bundled unless present in this folder. Verify their availability and provenance before use. No historical PASS is current acceptance.

**Review date:** 2026-10-31. Only official specifications, standards bodies, first-party documentation, original conference/journal papers and academic institutional repositories were used. No forums, personal blogs, Reddit or benchmark claims from secondary reports. **Reading a title/abstract is not reading the complete article**; access depth is recorded per entry. This document is a compact source-to-decision map, not a reproduction of copyrighted works. The sources do not prove an implementation in the Ec repository.

## Ec authority (original project documents)

| ID | Source | Access verified | Decision that stays with this authority |
|---|---|---|---|
| EC-N1 | `EC-REGRAS-DE-DESIGN-E-PRODUTO.md` (provided project file) | Project document read; relevant §§2, 14–16, 19–23, 27, 33–35 checked against skill | Rust control plane/agents; HTTP versioned, leases/fencing; PostgreSQL operational state; desired YAML only; no invented implementation; real outcomes |
| EC-N2 | `EC-REGRAS-CRIACAO-DE-TELAS-E-IMAGENS.md` (provided project file) | Project document read; relevant §§8, 13–17, 20 checked against skill | Agent, pool, queue and stale-state visual feedback and security; not a new UI skill |

## Official / first-party sources

| ID | Official source / canonical URL | Access verified | Technical synthesis / limitations |
|---|---|---|---|
| P01 | IETF RFC 9110, HTTP Semantics — https://www.rfc-editor.org/rfc/rfc9110.html | Relevant §§9.2.2, 13.1.1, 15.3.3, 15.5, 10.2.3 inspected | Idempotency belongs to semantics; `If-Match` preconditions; retries after loss need safety. RFC does not define Ec fencing errors. |
| P02 | IETF RFC 9457, Problem Details for HTTP APIs — https://www.rfc-editor.org/rfc/rfc9457.html | Abstract, status and problem-detail purpose inspected | Machine-readable HTTP errors; exact types and privacy rules are SDD decisions. RFC 9457 obsoletes RFC 7807. |
| P03 | IETF RFC 9846, TLS 1.3 (July 2026) — https://www.rfc-editor.org/info/rfc9846/ | Abstract, relationship to 8446, 0-RTT replay sections inspected | Current TLS 1.3 specification **obsoletes RFC 8446**; unsafe agent mutations must not rely on replayable early data. Do not assume mTLS is already deployed. |
| P04 | IETF RFC 9525, Service Identity in TLS — https://www.rfc-editor.org/rfc/rfc9525.html | Introduction and §§6.1, 6.6 identity matching inspected | Verify expected service identity, not just certificate chain. |
| P05 | IETF RFC 8705, OAuth mutual TLS and certificate-bound tokens — https://www.rfc-editor.org/rfc/rfc8705.html | §§1–3 and TLS/client binding requirements inspected | Certificate-bound OAuth is an **option** for agent identity, not a requirement or an Ec integration result. |
| P06 | IETF RFC 6750, Bearer Token Usage — https://www.rfc-editor.org/rfc/rfc6750.html | Threats/token replay/mitigations examined | Bearer token theft/replay are distinct from transport security and must be threat-modeled. |
| P07 | IETF RFC 9745, Deprecation HTTP field — https://www.rfc-editor.org/rfc/rfc9745.html | Deprecation, Sunset relation and semantics examined | Version deprecation notification is possible; cannot assume agents support headers. |
| P08 | Kubernetes Lease API — https://kubernetes.io/docs/concepts/architecture/leases/ | Node heartbeat and leader-election sections examined | Illustrates lease-backed liveness and control-plane observation, not Ec TTL values or mandatory Kubernetes dependencies. |
| P09 | Kubernetes Node Status — https://kubernetes.io/docs/reference/node/node-status/ | Readiness/Unknown condition descriptions examined | Suspicion/unknown are legitimate states; no numeric Kubernetes grace interval transferred. |
| P10 | Kubernetes version-skew policy — https://kubernetes.io/releases/version-skew-policy/ | Version compatibility policy and scope examined | An example of documented coexistence; Ec must define its own tested version matrix. |
| P11 | etcd v3 API — https://etcd.io/docs/v3.7/learning/api/ | Transaction and Lease API sections examined | Atomic compare-and-swap and keep-alives illustrate source-controlled state; Ec uses its chosen backend rather than adopting etcd. |
| P12 | etcd API Guarantees — https://etcd.io/docs/v3.5/learning/api_guarantees/ | Operation completion, timeout uncertainty, revision text examined | A timeout does not prove non-commit; revisions can be logical ordering evidence. |
| P13 | PostgreSQL 18 `SELECT` — https://www.postgresql.org/docs/current/sql-select.html | Row locks and `SKIP LOCKED` caveats examined | Lock selection is not a global queue snapshot; scheduler owns actual concurrency algorithm. |
| P14 | PostgreSQL 17 Explicit Locking — https://www.postgresql.org/docs/17/explicit-locking.html | Row-level `FOR UPDATE` behavior examined | Transactional work-state updates need explicit isolation reasoning; no actual Pg test performed. |
| P15 | OpenAPI Specification v3.2 — https://spec.openapis.org/oas/v3.2.0.html | Versioning and interface-description sections examined | Contract-first HTTP API and compatibility fixtures; do not mistake OpenAPI document versions for agent wire protocol versions. |
| P16 | Rust std::time::Instant — https://doc.rust-lang.org/stable/std/time/struct.Instant.html | Monotonicity and platform caveats examined | Local elapsed time only; do not compare different hosts' `Instant`s as a global lease clock. |
| P17 | Tokio MissedTickBehavior — https://docs.rs/tokio/latest/tokio/time/enum.MissedTickBehavior.html | `Burst`/`Delay`/`Skip` strategies examined | Scheduled heartbeats can burst if missed ticks are not handled intentionally; timing choices need benchmarks. |
| P18 | OWASP CI/CD Security Cheat Sheet — https://cheatsheetseries.owasp.org/cheatsheets/CI_CD_Security_Cheat_Sheet.html | Purpose and untrusted pipeline attack-surface sections examined | Compromised runners can threaten secrets; agent transport auth does **not** isolate the job process. |
| P19 | NIST SP 800-207 Zero Trust Architecture — https://www.nist.gov/publications/zero-trust-architecture | Official abstract and resource-auth rationale examined | Network location is not sufficient authentication; threat model distinguishes agent/workload identity. |
| P20 | SPIFFE X.509-SVID specification — https://spiffe.io/docs/latest/spiffe-specs/x509-svid/ | Abstract and SVID identity model examined | Standard workload identity possibility; Ec has not selected SPIFFE. |

**Primary count:** 20 distinct official first-party/standards entries, plus 2 Ec normative documents. Entries with only an abstract or narrower section are **not** represented as full-specification reads. RFC 9846, not obsolete 8446, is the TLS reference as of this review.

## Scientific literature / institutional primary publications

| ID | Publication / primary URL | Access verified | Supported principle; scope limit |
|---|---|---|---|
| A01 | Gray & Cheriton (1989), *Leases: An Efficient Fault-Tolerant Mechanism for Distributed File Cache Consistency*, SOSP — https://www.cs.cmu.edu/afs/cs.cmu.edu/academic/class/15712-s12/www/papers/gray89.pdf | Original full-text PDF available; abstract, introduction and lease discussion/section structure examined | Time-bounded grants and failures; **paper studies cache consistency, not CI job leasing or Ec TTL tuning**. |
| A02 | Burrows (2006), *The Chubby lock service for loosely-coupled distributed systems*, OSDI — https://www.usenix.org/legacy/event/osdi06/tech/full_papers/burrows/burrows_html/index.html | Original full HTML; §§2.4 (lock generation, sequencers), 2.5/2.7 (events/keepalives) examined | A delayed operation can act after lock handoff; a recipient must validate a fresh sequencer/fence. Does **not** establish that Ec must use Chubby. |
| A03 | Ghemawat, Gobioff & Leung (2003), *The Google File System*, SOSP — https://static.googleusercontent.com/media/research.google.com/en/us/archive/gfs-sosp2003.pdf | Original PDF available; §§2, 3.1, 4.5 lease/heartbeat/chunk version excerpts examined | GFS piggybacks **explicit server-granted lease renewals** on heartbeats. This does not mean a bare Ec heartbeat renews a lease. |
| A04 | Ongaro & Ousterhout (2014), *In Search of an Understandable Consensus Algorithm*, USENIX ATC — https://www.usenix.org/conference/atc14/technical-sessions/presentation/ongaro | First-party USENIX abstract and publication details examined; not a claim of reading full paper | Terms and replicated-state-machine safety inform durable monotonic generation. **No claim of Ec consensus/Raft**. |
| A05 | Chandra & Toueg (1996), *Unreliable Failure Detectors for Reliable Distributed Systems*, JACM 43(2) — https://www.cs.cornell.edu/info/people/sam/FDpapers.html | Cornell author bibliography and abstract examined; complete journal text not read | Failure suspicion is not guaranteed crash knowledge; distinguish availability observation from authority. |
| A06 | Hayashibara et al. (2004), *The φ Accrual Failure Detector*, JAIST research report — https://dspace.jaist.ac.jp/dspace/handle/10119/4784?locale=en | Institutional metadata and abstract examined; **technical research report, not represented here as a verified peer-reviewed article** | Continuous suspicion is one option for heartbeat interpretation, not a mandated algorithm. |
| A07 | Akidau et al. (2015), *The Dataflow Model*, PVLDB 8(12) — https://www.vldb.org/pvldb/vol8/p1792-Akidau.pdf | Original full-text PDF available; abstract and time-domain/out-of-order sections examined | Message arrival order and event time differ; do not treat arrival of delayed status as newer authoritative state. Not a direct agent-protocol correctness proof. |

**Academic count:** 7 primary works. Four (A01, A02, A03, A07) were accessed via original text with sections inspected; A04/A05 were consulted by official abstracts/catalogs; A06 by its institutional research report abstract. Metadata/abstract access is not described as exhaustive full-paper study.

## Source-derived synthesis versus Ec-specific engineering proposal

| Question | Evidence-backed observation | **Proposed method**, not norm or verified Ec behavior |
|---|---|---|
| Is heartbeat equal to lease renewal? | Kubernetes Lease heartbeats and GFS piggyback both show explicit server-controlled renewal processes; Chubby separate lock/session mechanisms. | Model heartbeat and work-claim expiry as **separate checks**; a carrier message may include an explicit lease-renewal request only when SDD specifies it. |
| Is expiry enough to block late writes? | Chubby §2.4 demonstrates delayed writes after a lock transition. | Bind every work effect and result to a server-authoritative attempt/fence; reject stale fence at each protected sink. |
| Can agents infer state from an HTTP timeout? | etcd API Guarantees describe commit uncertainty after timeout; RFC 9110 constrains retries. | Persist command identities/receipts and reconcile unknown outcomes, never assume the previous message was lost. |
| Does reported capacity prove trusted authority? | NIST zero trust + OWASP CI/CD emphasize identity, resources and untrusted workloads. | Treat capabilities as telemetry and separately enforce permission, isolation and validated provenance. |
| Are 0-RTT mutations automatically safe? | RFC 9846 explicitly warns of cross-connection 0-RTT replay. | Block early-data mutation by default until a separately reviewed SDD replay-safe profile exists. |
| What should the wire version be? | OpenAPI and Kubernetes illustrate documented compatibility boundaries but do not specify Ec versions. | Use an approved version matrix, explicit supported intersection and fail-closed security gates; names and ranges in SDD. |
| How should time be compared? | Rust Instant is local monotonic; distributed work may involve distinct machines and clocks. | Use one server/durable lease authority and avoid trusting agent clock; test skew and restart. |

## Unresolved product decisions / evidence gaps

- Authenticated enrollment and credential rotation authority, TLS/mTLS or other agent certificate/token strategy, revocation consistency and audit retention.
- HTTP endpoint/method/payload, exact version policy, compatibility window, error mapping, content negotiation, feature gates and structured error types.
- Heartbeat interval/grace/jitter, connection modes, polling/streaming strategy, durable receipt retention, user-facing offline behavior and load objectives.
- Work-lease issuance and expiry authority, persistence model and transactional fence generation across crash/failover, scheduler ownership and reassignment policy.
- Whether external job effects can honor a fence, require idempotency, or need compensation; these are not solved by the HTTP protocol alone.
- Real Rust server/agent interoperability, real TLS verification, PostgreSQL multi-server transactions, performance and behavioral skill evaluations: **not executed**.

## Scope and limitations

- No live Ec repository was supplied, and no Ec server, Rust agent, PostgreSQL service, mTLS handshake or distributed deployment was exercised. The validation fixture is synthetic and local-only.
- No quantitative improvement in reliability, latency or token economy is claimed; future comparisons require preregistered tasks, comparable baselines and measured outputs.
- Source dates and access depth are snapshots of the review. Keep security standards current; for example, older RFC 8446 is now superseded by RFC 9846.
- This skill makes **no** requirement that Ec integrate etcd, Kubernetes, Chubby, GFS, SPIFFE or OAuth; those sources supply patterns and counterexamples only.
