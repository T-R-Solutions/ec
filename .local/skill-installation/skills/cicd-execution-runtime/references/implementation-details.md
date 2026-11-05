# Detailed decisions and failure cases

Read only for a decision needing more detail than the entry point. Apply the relevant steps to the
actual task; the sections are not a mandatory artifact or test checklist. Use existing project
contracts in their actual format. File names such as spec.md, plan.md or tasks.md are examples, not
required dependencies. Other skills are optional expertise, never enforcement mechanisms.

Ec references below are conditional consumer examples. They impose no Ec documents, YAML format,
stack or product policy on a generic task.

## Procedure
1. Establish trust boundary: trusted/untrusted repository, identity, filesystem, network,
   credentials and host. Do not equate working-directory changes, `no_new_privs`, or process groups
   with a complete sandbox.
2. Represent executable and argv separately; reject shell interpolation by default. If shell
   execution is explicitly required, treat it as a separate authorized contract with injection
   tests.
3. Define immutable attempt identity and explicit states: pending, starting, running,
   cancel-requested, terminating, exited, failed-to-start, timed-out and cancellation-confirmed.
   Distinguish signal request from reaped exit.
4. Allocate a unique workspace and bounded environment; use secure path resolution, controlled
   inherited variables, and secret references resolved only at execution time. Never log secrets.
5. Configure host-enforced containment *before* untrusted code executes. On Linux assess
   user/mount/PID/network namespaces, seccomp, LSM, cgroup v2 CPU/memory/PIDs and filesystem
   permissions. Confirm policy actually applied; fail closed if isolation is required and
   unavailable.
6. Spawn child, concurrently drain stdout and stderr, bound memory, provide chunk sequencing and
   truncation/drop policy. Backpressure must not deadlock the process.
7. Enforce monotonic deadline and cancellation escalation: request graceful termination, bounded
   grace period, force-kill the entire scoped process tree, reap descendants where supported, then
   report terminal evidence. Test race with natural exit.
8. Capture exit status, signal/timeout/cancel cause, cleanup errors and resource evidence without
   collapsing them into a single failure. External effects are not exactly-once.
9. Test positive, negative, boundary, fault and mutation scenarios, then perform real host isolation
   and cross-platform tests. Do not claim containment from synthetic tests.
10. Map observed evidence to acceptance criteria; report PASS / FAIL / NOT EXECUTED separately, with
    versions, commands, host and trust assumptions.


## Further references by decision

Read only the reference relevant to the unresolved decision. Research registers describe imported authorship, not current execution.

- [Cancellation and deadlines](cancellation.md): cancellation.
- [Isolation and resource limits](isolation.md): isolation.
- [Routing and executable oracles](oracles-and-routing.md): oracles and routing.
- [Process execution contract](process-execution.md): process execution.
- [Evidence ledger: official specifications and scientific literature](sources.md): source provenance or a disputed recommendation.
- [Output, secrets and resource lifecycle](streaming.md): streaming.
