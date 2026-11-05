# Properties, Independent Oracles and Metamorphic Relations

> Ec-specific rules below apply only to an Ec consumer whose current contract establishes them.

## Formalize before implementing

Describe domain D, precondition P(x), system f(x), oracle Q(x,f(x)), and failure observation. A useful property has at least one plausible counterexample if implementation changes incorrectly. Anti-patterns include `assert parse(x) == parse(x)`, checking only type/non-crash for a semantic contract, and deriving the expected result by calling the same buggy utility.

## Oracle choices

1. **Contract/spec:** Approved grammar, range, state rules or external protocol.
2. **Reference implementation:** A deliberately simpler independently coded model; avoid reuse of the same helper/function.
3. **Algebraic laws:** Identity, inverse, idempotence, ordering, monotonicity, conservation and permutations when justified.
4. **Metamorphic relations:** Transform inputs with known relationships between outputs; verify preconditions for relation validity.
5. **Differential testing:** Compare independently produced implementations/versions with known shared-assumption limitations.

## Example patterns (not Ec contracts)

- Framed serializer: `decode(encode(xs)) == xs` within the declared valid domain. This alone is insufficient because both sides could share a defect; also compare against independent known examples and a canonical encoding check.
- Sorting: `sort(sort(xs)) == sort(xs)`, nondecreasing order, and multiset preservation. Idempotence alone allows a function that always returns an empty array.
- Revisioned store: successful compare-and-swap increments revision exactly once; stale expected revision leaves all state unchanged. Compare against an independent model after every command.
- Metamorphic: sorting any permutation yields the same result **if the order comparison is total and deterministic**. Float `NaN` needs an explicit policy.

## Failure report

Track `property_id`, requirement and AC, input classification, seed, generator version, input, actual result, expected/invariant violation, reduction trace and replay command. A `PASS` with no exercised cases is invalid. Never downgrade an unexpected exception to a discard.
