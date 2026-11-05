# Prerequisite readiness is not execution scheduling

> Ec-specific rules below apply only to an Ec consumer whose current contract establishes them.

A graph analysis can accept a **pure callback** `satisfies(dependency_observed_status)` and report for each pending node:

- `READY`: every prerequisite satisfies the contract-specific policy.
- `WAITING`: some prerequisite remains nonterminal, with explicit blocking IDs.
- `UNSATISFIED`: some prerequisite has terminal state not accepted by the policy; whether that means skip/cancel/fail must come from the feature spec.
- `NOT_PENDING`: node has already left the pending state; no duplicate offer.

The included model uses success-only acceptance as a named *fixture default*, not Ec's rule. A policy allowing failure to count as fulfilled may be passed explicitly. It never mutates states, acquires leases, requeues or applies retry rules.

Do not assume every vendor behaves alike: [GitHub Actions `needs`](https://docs.github.com/en/actions/how-tos/write-workflows/choose-what-workflows-do/use-jobs) documents a particular default and conditional overrides; no transfer of that behavior to Ec is licensed by the source.

Review callback semantics for invalid statuses and stale snapshots. A scheduler needs concurrency-safe compare-and-set or transactional safeguards; pure `READY` does not guarantee work starts, runs once, or runs on time.
