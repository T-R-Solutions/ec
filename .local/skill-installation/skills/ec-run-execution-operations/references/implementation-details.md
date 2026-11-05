# Detailed decisions and failure cases

Read only for a decision needing more detail than the entry point. Apply the relevant steps to the
actual task; the sections are not a mandatory artifact or test checklist. Use existing project
contracts in their actual format. File names such as spec.md, plan.md or tasks.md are examples, not
required dependencies. Other skills are optional expertise, never enforcement mechanisms.

Product rules, stack choices and authority locations must be verified in the Ec consumer. Examples
do not establish deployed capabilities or override its current contract.

## Activate and establish evidence

Activate for run list/detail/queue, status and timeline, command handling for run/cancel/retry,
rerun traceability, stale event reconciliation, state explanations, run-scoped authorization, or an
API/view that reports run outcomes. Do **not** activate for task execution
(`cicd-execution-runtime`), queue allocation and fairness (`cicd-scheduler`), agent-wire
acknowledgements and fencing (`ec-agent-control-protocol`), logs/test ingestion
(`cicd-logs-and-test-results`), publication (`ec-configuration-publication`), or YAML/editor
semantics (`ec-semantic-configuration-projections`). Naming a neighbor is not a reason to load it.

1. **Inventory authority and vocabulary.** Read the relevant approved `spec.md`, `plan.md`,
   `tasks.md`, current HTTP contract, authorization matrix, persisted run records, status events,
   and UI components. Identify run vs job vs step vs attempt vs command operation, immutable
   code/config revisions, event source, and authoritative status. Mark each detail `specified`,
   `observed`, `proposed`, or `unknown`. Raise an project workflow decision for missing status
   transitions, retry eligibility, cancellation policy, side effects, retention or permission scope;
   do not adopt competitor semantics silently.
2. **Separate three independent truths.** Maintain (a) actual **observed backend run/attempt
   state**, (b) **operation-request state** (`requested`, `accepted`, `rejected`, `in progress`,
   `resolved`, or `unknown` as approved), and (c) UI transport freshness. The interface must not
   equate a click, HTTP `202`, agent cancel signal, or websocket notification with a terminal
   outcome. A stale successful snapshot is not a fresh success. Prefer a visible `unknown /
   reconnecting` condition when authoritative confirmation is unavailable.
3. **Design an explicit transition and operation matrix.** For each supported run/job status and
   action, specify actor, permission, target scope, preconditions, expected version, idempotency
   identity, durable acknowledgement, successor or rejection, eventual terminal evidence, and
   recovery behavior. Treat runtime state as operational storage, **not canonical desired YAML**. An
   execution must retain verifiable immutable source and configuration revisions as fixed at
   submission; do not rebind a previous execution to a later published revision.
4. **Route all controls through authorized, concurrency-safe APIs.** Recheck authorization on the
   server for the exact action and resource; UI hiding is not security. Use a persisted idempotency
   key scoped and bound to actor, operation kind, target, and request fingerprint **if the approved
   API adopts that mechanism**. The same key and same payload may replay the prior receipt;
   mismatched reuse must not silently submit another action. Use atomic compare-and-swap/version
   checks or equivalent transaction semantics to reject stale state changes. HTTP semantics and
   error payloads must be selected in the approved API contract, not invented here.
5. **Specify cancellation as a protocol, not a button.** Distinguish requested/accepted, signaled,
   observed in-progress cancellation, cleanup, and authoritative terminal confirmation. Completion
   may race with cancellation: if success was committed first, do not retroactively claim
   `canceled`. Conversely, an accepted request might fail, time out, or remain unresolved. Represent
   `cancel` on queued work differently from terminating a running process where required by the
   contract. Delegate process termination, cleanup, signal propagation, lease fencing and
   stale-worker rejection to runtime/agent skills; this skill verifies their externally observable
   outcomes.
6. **Make retry provenance explicit.** An operator retry must create a traceable new **attempt or
   run identity** as specified by project workflow, linked to the previous one; retain prior
   outcomes, actor, reason, timestamps, fixed code/config revision and artifact/log references. Do
   not confuse re-execution with resuming the same process; do not imply exactly-once external
   effects. Include result-sensitive retry eligibility and explicit side-effect warnings. A
   timeout/lost HTTP response requires status lookup or safe request replay, not blind
   re-submission.
7. **Project only confirmed and causally safe observations.** Consume versioned server
   snapshots/events. Detect duplicates, older events, missing sequence ranges, deleted/missing
   resources, changed permissions, and reconnects; fetch an authoritative snapshot when event
   continuity cannot be proven. Never infer queue position, ETA, agent eligibility, success
   percentage or progress from arbitrary local counters. Show queue reason/position only when the
   server supplies evidence with a scope, timestamp and caveat. Keep browser state as an observation
   cache, never a second operational source of truth.
8. **Return actionable, accessible diagnostics.** Show execution/job/attempt identity, immutable
   revisions, trigger origin, phase and outcome, timestamps with timezone, cancellation/retry
   operation status, backend-confirmed errors, and scoped actions. Differentiate build failure, test
   failure, infrastructure failure, timeout, cancellation, skipped work and missing data when
   approved semantics permit. Present loading, stale/offline, permission denied, conflict,
   cancellation pending and partial failure without masking them as success. Announce asynchronous
   status messages to assistive technology without gratuitously stealing focus (WCAG 2.2 SC 4.1.3).
9. **Validate across independent layers.** Test operation and transition invariants, authorization
   including revoked roles, duplicate/reordered/gapped events, concurrent cancel-vs-complete,
   retry-vs-retry, lost response, revision pinning, query freshness, resource scoping, event
   auditability and status accessibility. Add representative Rust/PostgreSQL concurrency tests, Vue
   browser/assistive-technology tests and fault-injected agent integration *when the real repository
   is available*. Use actual measurements for latency/queue behavior; no invented benchmark or
   assertion of production readiness.


## Further references by decision

Read only the reference relevant to the unresolved decision. Research registers describe imported authorship, not current execution.

- [Lifecycle, request, and observation contract — implementation guide](lifecycle-and-operation-contract.md): lifecycle and operation contract.
- [Skill selection, counterexamples and test oracles](routing-and-test-oracles.md): routing and test oracles.
- [Primary sources, scientific grounding and decision traceability](sources-and-decisions.md): source provenance or a disputed recommendation.
