# Mutation Sensitivity and Oracle Adequacy

## Defects to inject

Choose independent plausible faults: off-by-one, silently dropping duplicates, accepting malformed framing, wrong revision check, applying rejected command, version misclassification, unauthorized scope reuse, stale-state update, violating idempotence and overflow. For each mutation:

1. Ensure it is applied to the expected target and is not syntactically invalid unless compiler diagnostics are the intended oracle.
2. Run the **baseline** property with an approved implementation; it must pass.
3. Run the same property with the mutant; require a reproducible failure attributable to this specific invariant.
4. Persist surviving mutants or false positives; update oracles rather than celebrating raw pass counts.

## Scope of evidence

A killed synthetic mutant demonstrates only that a specific test oracle would catch that deliberately injected defect in this fixture. It is not equivalent to production fault detection, a mutation score across all real program operations, or proof of security. Coverage and mutation counts are signals, not correctness guarantees. See Inozemtseva & Holmes ICSE 2014 and Barr et al. IEEE TSE 2015.

## Anti-vacuity gates

Reject `0 accepted cases`, `0 executed transitions`, `all inputs filtered out`, `all regressions skipped`, or missing real target as success. Track target revision, generator version and observed failures by AC.
