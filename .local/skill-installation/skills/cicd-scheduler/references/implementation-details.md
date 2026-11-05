# Detailed decisions and failure cases

Read only for a decision needing more detail than the entry point. Apply the relevant steps to the
actual task; the sections are not a mandatory artifact or test checklist. Use existing project
contracts in their actual format. File names such as spec.md, plan.md or tasks.md are examples, not
required dependencies. Other skills are optional expertise, never enforcement mechanisms.

Ec references below are conditional consumer examples. They impose no Ec documents, YAML format,
stack or product policy on a generic task.

## Invariants
1. One active reservation per job attempt; job, slot and quota updates commit atomically in durable
   storage. Read-then-write without serialization is unsafe.
2. Differentiate eligible, selected, reserved, dispatched, acknowledged, running, expired and
   terminal. Reservation does not prove execution.
3. Each reassignment increases a fencing token; stale completion and stale external effects must be
   rejected by the actual receiver. Lease expiration alone is insufficient.
4. At-least-once delivery does not imply exactly-once external effects. Retry and attempt identity
   require approved policy.
5. Agent health, capabilities, trust, maintenance, capacity and tenant quota must be checked at
   authoritative reservation.
6. Fairness, priorities, starvation and preemption require a spec and measurements; `SKIP LOCKED`
   alone cannot guarantee fairness.

## Method
1. Read the accepted `spec.md`, actual data model and code. Record unspecified retry, trust, quota,
   fairness or failure semantics as `SPEC GAP`.
2. Separate DAG-ready from resource-eligible; record blocked reasons without guessing. Define
   deterministic candidate tie-break and scope.
3. Reserve job attempt, agent slot and tenant quota in one transaction. Consider row locking,
   conditional updates and uniqueness. Commit before network dispatch; evaluate durable outbox.
4. For PostgreSQL SERIALIZABLE errors, retry the entire transaction with bounded backoff. Never use
   in-memory simulations as proof of DB atomicity.
5. Issue lease/attempt/fencing epoch, renew only for matching owner, reject stale completion,
   reconcile expiration without claiming old side effects were undone.
6. Test two schedulers racing for one slot, quota exhaustion, disconnected/mismatched agents,
   duplicate messages, rollback, expired leases, stale owner, fairness starvation and clock
   boundaries.
7. Benchmark representative workloads before claiming throughput, wait-time or fairness improvement;
   record tool versions, seeds, data and limitations.
8. Map spec AC to plan/ADR, tasks and evidence. Mark missing real integration `NOT EXECUTED`, never `PASS`.

## Further references by decision

Read only the reference relevant to the unresolved decision. Research registers describe imported authorship, not current execution.

- [fairness.md](fairness.md): fairness.
- [leases.md](leases.md): leases.
- [oracles.md](oracles.md): oracles.
- [routing.md](routing.md): routing.
- [sources.md](sources.md): source provenance or a disputed recommendation.
- [transactions.md](transactions.md): transactions.
