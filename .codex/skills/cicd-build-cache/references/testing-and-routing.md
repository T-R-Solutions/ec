# Routing and test oracles
Positive: same declared inputs/trust -> same key; verified cache hit; authorized trusted write; valid eviction.
Negative: changed toolchain/lockfile -> miss; corrupted blob -> miss; untrusted writer -> denied; cross-tenant read -> denied; conflicting digest -> rejected; path traversal -> rejected.
Boundary: empty blob, zero capacity, simultaneous writers, TTL at deadline, huge output, noncacheable action, missing input.
Fault: interrupted upload, stale action mapping, cache server unavailable, storage full, credential compromise, concurrent eviction and restore.
Mutation testing must demonstrate that each oracle passes the correct baseline and fails the defective variant. Local model != real CI performance/security. Use this skill for caching, not retained artifacts or runtime execution.
