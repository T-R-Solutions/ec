# Detailed decisions and failure cases

Read only for a decision needing more detail than the entry point. Apply the relevant steps to the
actual task; the sections are not a mandatory artifact or test checklist. Use existing project
contracts in their actual format. File names such as spec.md, plan.md or tasks.md are examples, not
required dependencies. Other skills are optional expertise, never enforcement mechanisms.

Ec references below are conditional consumer examples. They impose no Ec documents, YAML format,
stack or product policy on a generic task.

## Method
1. Separate desired configuration, local runtime state and server-authoritative assignments. Agent
   identity and credentials must be protected, not committed in plaintext.
2. Specify states `initializing`, `registering`, `ready`, `degraded`, `draining`, `disconnected`,
   `stopping`, `stopped`. Define transitions and guards. A heartbeat sent is not proof the server
   accepted it.
3. Discover host capabilities with source, timestamp and confidence; distinguish advertised
   capacities from allocatable resources. Reconcile capability changes; never overstate trust or
   sandbox support.
4. Account for slots with atomic reservation and release; maintain `0 <= active + reserved <=
   capacity`. Associate each reservation with unique attempt and epoch.
5. Heartbeats include monotonic sequence or epoch per agent incarnation. Define liveness from server
   observations; missed heartbeats are suspicion, not proof of process death.
6. Accept assignments only after authorization and epoch/fence verification. Replays, duplicates,
   stale epochs and unsupported requirements must not create additional work.
7. Persist minimal recovery journal as required; after restart, re-register a new incarnation,
   reconcile outstanding attempts, and never assume stale work can be safely resumed.
8. Drain rejects new assignments while allowing configured in-flight completion/cancellation.
   Disconnection follows a documented policy; do not claim external effects are exactly-once.
9. Instrument lifecycle transitions, resource eligibility, reconnection, failure causes and
   clock/sequence anomalies without exposing secrets.
10. Produce state-machine tests, negative/mutation cases, fault injection, host-specific capability
    evidence and acceptance criteria traceability. Separate synthetic model from real agent/server
    tests.


## Further references by decision

Read only the reference relevant to the unresolved decision. Research registers describe imported authorship, not current execution.

- [Capabilities and slots](capabilities-and-slots.md): capabilities and slots.
- [Lifecycle and liveness](lifecycle-and-liveness.md): lifecycle and liveness.
- [Recovery and fencing](recovery-and-fencing.md): recovery and fencing.
- [Routing and test oracles](routing-and-oracles.md): routing and oracles.
- [Security and observability](security-and-observability.md): security and observability.
- [Evidence ledger](sources-and-decisions.md): source provenance or a disputed recommendation.
