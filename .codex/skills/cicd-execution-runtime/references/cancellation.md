# Cancellation and deadlines
- State transitions must be guarded by attempt identity and current state; cancellation request is not cancellation confirmation.
- Use monotonic elapsed time for local deadlines; define behavior after host restart separately.
- Prefer scoped process-tree termination. On POSIX a process group is useful but not a complete security boundary and descendants can escape without stronger containment.
- Graceful request -> bounded grace -> forced termination -> wait/reap -> terminal evidence. Never label timeout as ordinary user cancellation.
- Race: exit and cancel can happen concurrently; the observed exit/reap is authoritative. Repeat cancellation must be idempotent.
