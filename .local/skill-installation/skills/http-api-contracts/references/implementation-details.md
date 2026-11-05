# Detailed decisions and failure cases

Read only for a decision needing more detail than the entry point. Apply the relevant steps to the
actual task; the sections are not a mandatory artifact or test checklist. Use existing project
contracts in their actual format. File names such as spec.md, plan.md or tasks.md are examples, not
required dependencies. Other skills are optional expertise, never enforcement mechanisms.

Ec references below are conditional consumer examples. They impose no Ec documents, YAML format,
stack or product policy on a generic task.

## Workflow: inputs → decisions → contract → evidence

### 1 — Locate the source of truth and capture consumers

Collect approved `spec.md`/acceptance criteria; provider version and deployment status; affected
client versions and request patterns; authentication and authorization source; resource
identity/scopes; media types; field constraints; caching privacy and retention; retry/timeout needs;
links and pagination stability; API gateway behavior; error expectations and evolution constraints.
Record unknown details as `SPEC GAP`, never infer them from examples. Map each endpoint to actor,
resource and operation. See [resource and method semantics](resource-and-method-semantics.md).

### 2 — Define HTTP behavior independently from backend implementation

For each method and resource, define method safety/idempotency and effects, `Content-Type`,
`Accept`, status matrix and required headers, resource path/identifier, request and response bodies,
nullable/missing/default semantics, server-confirmed outcome and examples. `GET` must not be a
mutation command; `POST` is not generally retry-safe without an endpoint-specific guarantee. `202
Accepted` means accepted, not completed. Use `405` with `Allow` if applicable; `415` for unsupported
request media, `406` for unavailable response representation. Keep policy authorization separate
from route visibility. See [request response media and
validation](request-response-media-and-validation.md) and [asynchronous operations and
idempotency](asynchronous-operations-and-idempotency.md).

### 3 — Specify concurrency and replay invariants

For mutable resources, specify server-issued strong validators, `ETag`/`If-Match`, atomic
application and error behavior (`412` on failed precondition; optional `428` when a precondition is
required); distinguish `304` on conditional reads from update rejection. Define the meaning of `409`
domain conflicts separately. Include concurrent-client race and stale-validator negative tests. If
offering an idempotency key, define its scope (tenant/actor/operation), payload fingerprint,
TTL/replay persistence, mismatch outcome, in-flight behavior and crash recovery; do not equate
duplicate-response suppression with exactly-once external side effects. See [conditional requests
and lost update](conditional-requests-and-lost-update.md) and [asynchronous operations and
idempotency](asynchronous-operations-and-idempotency.md).

### 4 — Define collection, cache and error behavior

Specify deterministic ordering and tie-breaking, cursor binding to filter/auth scope,
invalid/tampered cursor response, page size limits and behavior under concurrent modifications. Use
documented links where appropriate; do not invent a universal cursor standard. Define
`Cache-Control`, validator and `Vary` rules with sensitive data/authorization boundaries. Use a
stable Problem Details `type` with `application/problem+json` where appropriate; distinguish HTTP
status from the advisory `status` body member. Never return secrets or internal stack traces.
Specify `429`/`Retry-After` and budget policy only when the system actually enforces one. See
[pagination filtering and linking](pagination-filtering-and-linking.md), [caching privacy and
validators](caching-privacy-and-validators.md), [errors and problem
details](errors-and-problem-details.md) and [rate limits and client
resilience](rate-limits-and-client-resilience.md).

### 5 — Record compatibility and lifecycle choices

Capture contract version, provider/client compatibility window, pre/post states and deprecation
communication. Use consumer-impact categories: operation removal, required request field addition,
changed meaning, validation tightening, response narrowing, enum removal, changed auth and error
behavior. **No static OpenAPI diff proves runtime compatibility**. Treat optional request additions
and additive response fields as *potentially* compatible, conditional on strict consumer
assumptions. Record tested old-client scenarios, migration aids and rollback. RFC 9745
(`Deprecation`) and RFC 8594 (`Sunset`) can signal lifecycle without proving when a service will
stop. See [versioning deprecation and compatibility](versioning-deprecation-and-compatibility.md).

### 6 — Publish a reviewable description, and verify independently

Write a reviewable OpenAPI description where selected, validate its syntax/schema with compatible
tooling, perform static breaking-change screening, implement positive/negative provider/consumer
oracles and verify HTTP on a real network boundary. Test malformed bodies, missing/incorrect media,
unauthorized scope, stale revisions, repeated and conflicting idempotency keys, replay under
concurrency, cursor tampering, absent/expired resources, `202` monitoring, caching privacy, `429`,
and older supported clients. OpenAPI schema validation does **not** replace behavior and
authorization tests. Keep assertions at wire level and separate model versus live application
evidence. See [openapi and schema evolution](openapi-and-schema-evolution.md), [security
boundaries](security-boundaries.md) and [testing and evidence
matrix](testing-and-evidence-matrix.md).

### 7 — Deliver traceable decisions and block false success

Deliver: endpoint/method/status/header matrix; media/schema and invariants; authority links with
versions; explicit security and concurrency review; consumer/version compatibility table;
positive/negative/boundary tests and observed commands; unexecuted real-integration requirements;
project workflow `REQ → AC → task → evidence` references. State recommendations distinctly from
tested results. A structurally valid project workflow bridge or a passing synthetic HTTP fixture
**is not acceptance of the application's HTTP API**.


## Further references by decision

Read only the reference relevant to the unresolved decision. Research registers describe imported authorship, not current execution.

- [Asynchronous Acceptance, Replay Keys and Retry Semantics](asynchronous-operations-and-idempotency.md): asynchronous operations and idempotency.
- [HTTP Caching, Cache Privacy and Validators](caching-privacy-and-validators.md): caching privacy and validators.
- [Conditional Requests: Validators, Lost Updates and Atomicity](conditional-requests-and-lost-update.md): conditional requests and lost update.
- [Problem Details and Error Response Contracts](errors-and-problem-details.md): errors and problem details.
- [project workflow, Deployment Integration and Evidence Discipline](integration-and-evidence.md): integration and evidence.
- [OpenAPI, Schema Evolution and Description-vs-Implementation Gap](openapi-and-schema-evolution.md): openapi and schema evolution.
- [Collection Pagination, Filtering, Sorting and Links](pagination-filtering-and-linking.md): pagination filtering and linking.
- [Rate-Limit, Retry and Resilience Contracts](rate-limits-and-client-resilience.md): rate limits and client resilience.
- [Request / Response Representations and Validation](request-response-media-and-validation.md): request response media and validation.
- [Resource Identity and HTTP Method Semantics](resource-and-method-semantics.md): resource and method semantics.
- [Correct Skill Selection Oracles](routing-and-test-oracles.md): routing and test oracles.
- [Scientific Evidence and Applicability Limits](scientific-evidence-and-limits.md): source provenance or a disputed recommendation.
- [HTTP API Security and Authorization Boundaries](security-boundaries.md): security boundaries.
- [Source Register, Versions and Engineering Decisions](sources-and-decisions.md): source provenance or a disputed recommendation.
- [Positive, Negative and Boundary Oracles](testing-and-evidence-matrix.md): testing and evidence matrix.
- [API Versioning, Deprecation and Consumer Compatibility](versioning-deprecation-and-compatibility.md): versioning deprecation and compatibility.
