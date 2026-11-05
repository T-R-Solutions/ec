# Shrinking and Counterexample Minimization

**Goal:** produce a smaller *still failing* case with the **same defect signature**, not merely any unrelated failure. Frameworks perform distinct shrink processes; prefer native shrinkers when available.

## ddmin for sequences

1. Capture the original failing sequence and deterministic replay environment.
2. Divide into chunks, attempt deleting chunks; retain a reduction only when the oracle still fails with the same signature and valid preconditions.
3. Increase granularity when necessary until no more chunks can be removed under the budget.
4. For remaining values, try domain-aware simplifications (0, ±1, small magnitude, fewer fields, shorter text) if they preserve the trigger.
5. Repeat and stop on budget or local fixed point; save witness with exact replay test.

A local greedy shrinker is **not guaranteed** to find a globally minimal example. Some sequence shrinkers must preserve earlier state transitions or identifiers; shrinking a transaction after deleting its creation can invalidate a useful counterexample. Track `attempts`, original case, reduced case and signature.

## Tests for the shrinker itself

- A failing witness remains failing after reduction.
- Reduced witness meets the input grammar and spec preconditions.
- Size/cost never increases.
- Running reduction twice yields a stable reproducer under the same deterministic conditions.
- When the witness does not fail, the shrinker explicitly rejects the request; never report success.

## Regression

Keep a minimal human-readable regression example **in addition** to seed/engine databases. Framework releases can change generation/shrinking and invalidate seed-only reproduction. Store corpus hashes, case source, approved retention and sanitization policy.
