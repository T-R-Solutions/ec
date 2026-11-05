# Graph edits, revision fixation and incremental algorithms

**Transactional conceptual flow:** read revision → apply proposed node/edge edits to a new graph → validate references/duplicates/limits/cycles → calculate added/removed edges, affected descendants and possible policy impact → accept or reject proposed revision. Never mutate the already published or running graph in place.

A proposed `consumer.needs += prerequisite` may create a cycle if the proposed prerequisite is already transitively dependent on the consumer. A missing referenced node must be handled by the schema contract, not auto-created.

Full `O(V+E)` recomputation is often easy to reason about; incremental topological order has established research ([Pearce–Kelly 2007](https://doi.org/10.1145/1187436.1210590), [Bhattacharya–Kulkarni 2020](https://doi.org/10.1137/1.9781611975994.153)), but adoption requires actual graph sizes, update patterns, concurrent revisions and measured trade-offs. The reference fixture fully rebuilds after each proposed edge; it does not implement Pearce–Kelly or a database transaction.

Returning an impact set is not a promise of automatic retries, cancellation or deployment rollback; those are owned by separate runtime/application policies. Revision immutability and synchronization authority belong to the host product.
