# Deterministic Replay, Evidence and Regression Corpus

Reproducing a property failure needs more than `seed=42`: record runtime, test library version, strategy code revision, operating system/architecture when relevant, locale/time zone, disabled/enabled optimizations, corpus hashes, target revision, case budget and reduction algorithm. A stored **concrete failing input** is the strongest portable artifact for regression.

## Suggested evidence fields

```text
requirement_id, acceptance_criterion, property_id, generator_id,
seed, test_cases, reject_count, original_input, reduced_input,
oracle_version, failure_signature, runtime_version, actual_target_revision,
command, observed_result, dependency_status, artifact_hash
```

A seed may be stable in a specific implementation but **not contractually portable across library versions**. Maintain examples or persisted failures using official facility (Hypothesis database/examples; proptest failure persistence). Distinguish saved regressions from emergent fuzz corpus samples.

## Corpus policy

Seed fuzz targets with representative valid and invalid specimens. Minimize by coverage when supported; hash artifacts and deduplicate. Protect credentials and personally identifying information. Do not store raw session tokens, personal records, private code or production secrets in a public corpus. Set storage, quarantine and deletion policies explicitly.

## Reproduction gate

The reference fixture uses Python `random.Random(seed)` with explicit Python version, and tests both seed replay and a saved concrete example. It is **not cryptographic**, and the generator may change in a future release. A broken target without available dependencies must fail **closed** rather than reporting skip-as-pass.
