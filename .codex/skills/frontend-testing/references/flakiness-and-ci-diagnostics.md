# Flaky-test diagnosis and CI evidence

> Ec-specific rules below apply only to an Ec consumer whose current contract establishes them.

If the same source/fixture/environment yields different outcomes, record `FLAKY` and investigate. Do not mark a pass after retry as clean. Inspect test order, shared state, asynchronous timing, animations, clocks, network mocks, worker concurrency and test data lifetime. Keep exact replay commands, seeds and artifacts. Use tracing on first retry or retain-on-failure where supported, but do not log credentials, cookies or unredacted personal data.

A quarantine must have an owner, tracked root-cause issue, scope and expiration; recurring rerun success must not satisfy a critical acceptance gate. Track environments (OS/browser/CPU) rather than claim framework causality without comparison.

Research: Luo et al. (2014), *An Empirical Analysis of Flaky Tests*, FSE, DOI 10.1145/2635868.2635920. Its examined open-source commits do not establish the prevalence or causes in any particular Ec/CI application.

Official: https://playwright.dev/docs/trace-viewer-intro ; https://playwright.dev/docs/best-practices
