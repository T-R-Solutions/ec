# Evidence ledger
## Official/maintainer documentation (consulted)
> Imported authoring record. Research dates, consultations and execution claims below belong to the supplied source package; they were not reproduced in this adaptation. Referenced validation scripts, fixtures, product norms and logs are not bundled unless present in this folder. Verify their availability and provenance before use. No historical PASS is current acceptance.

1. Gradle Build Cache: https://docs.gradle.org/current/userguide/build_cache.html — complete inputs and outputs, repeatability, local/remote cache.
2. Gradle Build Cache concepts: https://docs.gradle.org/current/userguide/build_cache_concepts.html — stable inputs, relocatability and output determinism.
3. Bazel remote caching: https://bazel.build/remote/caching — action cache and CAS; read/write policy.
4. GitHub Actions dependency caching: https://docs.github.com/en/actions/reference/workflows-and-actions/dependency-caching — poisoning, low-trust writes and secrets.
5. GitHub dependency cache concepts: https://docs.github.com/en/actions/concepts/workflows-and-actions/dependency-caching — artifacts vs caches, trust restrictions.

## Peer-reviewed publications (verified venue; conclusions bounded)
- Mokhov, Mitchell, Peyton Jones (2018), *Build Systems à la Carte*, ICFP 2018, DOI: https://doi.org/10.1145/3236774 — executable framework to reason about build system dependencies; does not prove Ec cache performance.
- Ghaleb, da Costa, Zou (2026), *The Promise and Reality of Continuous Integration Caching: An Empirical Study of Travis CI Builds*, EASE 2026, https://conf.researchr.org/details/ease-2026/ease-2026-research-papers/64/The-Promise-and-Reality-of-Continuous-Integration-Caching-An-Empirical-Study-of-Trav — empirical evidence that CI caching may have maintenance/staleness costs; observed results apply to sampled Travis CI projects, not Ec.

## Limits
No benchmarks of Ec, no proof of safety for a particular CI backend, and no inference of implemented functionality. Source documentation establishes techniques and observed constraints, not universal performance gains.
