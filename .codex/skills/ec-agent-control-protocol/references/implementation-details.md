# Detailed decisions and failure cases

Read only for a decision needing more detail than the entry point. Apply the relevant steps to the
actual task; the sections are not a mandatory artifact or test checklist. Use existing project
contracts in their actual format. File names such as spec.md, plan.md or tasks.md are examples, not
required dependencies. Other skills are optional expertise, never enforcement mechanisms.

Product rules, stack choices and authority locations must be verified in the Ec consumer. Examples
do not establish deployed capabilities or override its current contract.

## Execution method

1. **Establish authority and inventory the actual implementation.** Read the relevant approved
   `spec.md`, `plan.md`, `tasks.md`, Ec norms, existing Rust HTTP contracts/handlers, agent state,
   database schema, audit and tests. Map **agent identity**, enrollment credential, authenticated
   principal, agent instance/session epoch, negotiated version, job/attempt, assignment ID, work
   lease, fencing generation and receipt. Mark every field or operation `specified`, `observed`,
   `proposed`, or `unknown`; do not invent a route or enum for Ec.
2. **Draw the protocol boundary and independent lifecycles.** Separate **agent liveness** (heartbeat
   and connection observations), **work ownership** (lease/attempt/fence), **delivery
   acknowledgements** (commands and results), and **scheduler eligibility** (owned elsewhere). A
   live agent does not prove that any particular work lease is valid; an expired lease must not be
   revived by a heartbeat. Keep run, queue, lease and session state in operational storage, not
   canonical desired YAML.
3. **Specify an explicit version/capability negotiation matrix.** Compare agent-supported and
   control-plane-supported protocol versions; select only an approved compatible intersection and
   define an explicit incompatible outcome. Treat self-reported capabilities as input to validated
   scheduling policy, never as proof of identity or authorization. Define
   additive-field/unknown-field behavior, required security-feature gates, downgrade policy, and
   coexistence/upgrade tests. Do not copy Kubernetes version-skew ranges as Ec policy.
4. **Authenticate the correct principal before accepting enrollment, registration or a lease
   response.** Distinguish **one-time enrollment authority**, registered agent identity, current
   session epoch and workload authorization. Require the approved authenticated-channel mechanism,
   endpoint identity verification, credential rotation/revocation, scoped permissions, replay
   resistance and secret redaction. mTLS / certificate-bound credentials are candidates, not
   predetermined by this skill. Never trust a plaintext `agent_id` field or a caller-supplied header
   as authentication, and never log tokens/private keys.
5. **Design bounded heartbeats and reconnects.** Specify heartbeat request identity, session epoch,
   monotonic per-session sequence, authenticated status/capacity evidence, bounded payload, receipt
   and freshness time recorded by the server. Duplicates must not extend freshness; out-of-order or
   cross-session updates must not overwrite newer observations. A delayed heartbeat means
   **suspected unavailable / unknown**, not proof of process death. On reconnect use new
   authenticated session identity and reconciliation of outstanding assignments. Define timeouts,
   jitter, load limits and missed-tick policy in project workflow; do not transfer client wall-clock
   timestamps into server lease authority.
6. **Carry a server-authoritative, monotone fence for every work claim.** For each attempt, bind the
   assignment/lease to its **agent, session epoch, attempt ID, unique command/claim ID and fencing
   generation**, with a server-controlled expiry. Renew only when the current claim is active and
   valid. Reject obsolete, expired, revoked, mismatched or replayed claims. On reassignment advance
   a monotone fence in durable authority and make every relevant result/state commit check it
   atomically. A fencing token cannot stop an already-triggered external side effect unless that
   destination also enforces fencing or another suitable idempotency/compensation contract.
7. **Implement retry-safe receipt and error semantics.** Distinguish command delivery,
   acknowledgement, work execution and result acceptance. A timeout or lost response means **unknown
   until reconciled**, not failure or success. Retry only if HTTP method semantics or approved
   application-level operation identity makes it safe; bind idempotency identity to principal,
   target, session/attempt and request fingerprint and retain receipts for a documented period.
   Document `202`, `401`, `403`, `409`, `412`, `429`, `503` and `Retry-After` where semantically
   appropriate; exact status mapping and structured errors belong to the approved API specification.
   Do not claim exactly-once execution.
8. **Make Rust implementation observable without overstating health.** Specify bounded and typed
   wire structures, transport deadlines, cancellation, content limits, malformed-payload handling,
   secret-safe traces, correlation IDs, reason-coded rejections, metrics separated by
   agent/session/lease, and server-confirmed status. Use Rust monotonic timers for elapsed local
   intervals; store authoritative expiries in the chosen durable backend with a documented
   clock/transaction strategy. Prefer structured errors and tested compatibility fixtures over
   implicit defaults.
9. **Verify each safety boundary under faults.** Test concurrent session replacement,
   duplicate/reordered heartbeats, lost acknowledgements, delayed results, work expiry/reassignment,
   fence monotonicity across restart, malformed and incompatible versions, revoked credentials,
   clock skew, replay, concurrency and partial network partition. Then run real Rust HTTP,
   PostgreSQL transactional, multi-agent and fault-injection tests **only once the Ec repository is
   available**. Never claim that this package's simulated controller validates TLS, distributed
   consensus, real PostgreSQL, or Ec itself.


## Further references by decision

Read only the reference relevant to the unresolved decision. Research registers describe imported authorship, not current execution.

- [Protocol design and safety decision guide](protocol-decision-guide.md): protocol decision guide.
- [Activation routing and acceptance oracles](routing-and-test-oracles.md): routing and test oracles.
- [Sources, evidence depth, scientific grounding and engineering decisions](sources-and-decisions.md): source provenance or a disputed recommendation.
- [Threat and compatibility matrix (review aid, not product policy)](threat-and-compatibility-matrix.md): threat and compatibility matrix.
