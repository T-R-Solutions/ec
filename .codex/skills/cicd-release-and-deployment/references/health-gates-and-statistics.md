# Health gates and statistical defensibility

Specify independent, testable evidence: candidate/target/stage identity, collection start/end, source, observed timestamp, unit, denominator/samples, comparator/baseline, threshold and missing-data policy. A numerical success percentage without sample size or population scope is inadequate. Avoid comparing canary traffic to a nonrepresentative control.

Require **fresh** measurements and a minimum sample size determined by the spec. Missing, delayed, conflicting, zero denominator or stale signals return `INCONCLUSIVE` (often pause or require operator review), not `PASS`. Explicitly distinguish a measured failure (`FAIL`), a data-plane outage and an instrumentation outage.

Canary promotion should occur only after a successful evaluation for the *current* stage, artifact and environment, not for an earlier phase or another release. Repeated/stale observations must not advance phases. Use control/holdout and guardrails where reasonable; small canaries may not detect rare regressions. Thresholds in fixtures are **illustrative**, not suggested production SLOs.

Test: 0 samples, below-minimum sample size, 100% healthy with a single request, stale timestamp, wrong target/release, metric absent, nonfinite latency, threshold violation, equal-to-threshold boundary, repeated phase result, out-of-order results and conflicting telemetry.
