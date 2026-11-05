# Authoritative acknowledgements and version conflicts

## Server evidence outranks local belief

Define the authoritative API response and revision type before designing cache transitions. A revision may be a monotonic integer, opaque hash, ETag, timestamp or composite identity; **do not compare opaque hashes lexicographically** or infer global ordering from wall clocks. This skill's synthetic model uses integer revisions purely to create reproducible oracles.

Distinguish `command queued`, `accepted for processing`, `stored`, `published`, `reconciling`, and `effective` only where the product contract actually supports each. A server ACK does not automatically prove a downstream external effect. For configuration resources, retain the immutable published revision and draft separately.

## Conflict behavior

When an update is rejected due to optimistic concurrency (e.g. an API-defined 409/412), preserve the user's intended change, show the authoritative revision/validator where allowed, and use diff/merge or explicit retry based on policy. Do not silently choose "last write wins" for high-impact resources. If the response is ambiguous because of timeout, mark the outcome as unknown and reconcile by idempotency key or server query when supported.

## Oracles

1. A fetched resource at revision 6 cannot be overwritten by response revision 4 for the same resource identity.
2. A **new** local request supersedes a previous one even if the previous returns with numerically larger revision; query generation has separate meaning.
3. A 304 validated response follows HTTP validator semantics only if the actual HTTP contract is being used; do not fabricate a revision based on UI time.
4. An event with no authoritative ordering contract triggers invalidation rather than blind direct application.
5. An accepted mutation must not display an effective/deployed status until server evidence identifies it as such.

References: [RFC 9110 conditional requests](https://www.rfc-editor.org/rfc/rfc9110.html), [RFC 9111 revalidation](https://www.rfc-editor.org/rfc/rfc9111.html).
