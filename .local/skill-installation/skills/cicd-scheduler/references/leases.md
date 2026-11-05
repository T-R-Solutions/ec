# leases.md
Leases have attempt ID, owner, expiration and monotonic fencing epoch. Renew only for current owner. Reject stale completion after reclaim. Enforce fencing at every downstream effect receiver. Expiration cannot undo side effects. Agent loss is ambiguous; never claim exactly-once external effects.
