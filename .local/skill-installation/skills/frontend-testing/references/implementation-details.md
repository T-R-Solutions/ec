# Detailed decisions and failure cases

Read only for a decision needing more detail than the entry point. Apply the relevant steps to the
actual task; the sections are not a mandatory artifact or test checklist. Use existing project
contracts in their actual format. File names such as spec.md, plan.md or tasks.md are examples, not
required dependencies. Other skills are optional expertise, never enforcement mechanisms.

Ec references below are conditional consumer examples. They impose no Ec documents, YAML format,
stack or product policy on a generic task.

## Method (use selectively; do not recurse into other skills)

1. **Build a requirement-to-oracle map.** For each REQ/AC, list input, user action, preconditions,
   observable expected result, explicit forbidden result, execution layer, fixture provenance and
   required evidence. If an expected result is undefined, return a specification gap; do not
   manufacture an assertion. [Oracles](acceptance-oracles-and-traceability.md).
2. **Choose the cheapest credible layer.** Pure transformations at unit level; SFC
   rendering/events/async Vue semantics with mounted Vue Test Utils; navigation+network+storage in
   real browsers; backend authorization and publication via nonmocked integration. A browser mock
   cannot prove backend policy. Record the trade-off, not a universal test pyramid ratio.
   [Layers](test-layers-and-scope.md).
3. **Inspect installed runners and configure isolation.** Confirm Vitest/Vite versions and DOM mode
   (`jsdom`, `happy-dom` or browser); Vue Test Utils v2 for Vue 3; Playwright projects/browsers.
   Restore mocks/timers after each test, use per-test browser contexts and independent data. Do not
   silently use mocks for a gate that needs live integration.
   [Setup](vitest-and-test-environments.md), [Isolation](fixtures-and-isolation.md).
4. **Test behavior accessible to the user.** Prefer roles and accessible names, labels and stable
   explicit contracts; assert events, visible messages, focus, navigation state, failure messages,
   keyboard alternatives and permission-dependent affordances. Avoid snapshots/selectors that only
   confirm the component's internal implementation. [Components](vue-component-testing.md),
   [Browser](playwright-e2e-and-locators.md).
5. **Control async causality.** Await Vue ticks for reactive DOM updates, `flushPromises()` for
   non-Vue promise queues when appropriate, actionability/retryable assertions in browsers, and
   deterministic deferred responses for race conditions. Prefer condition-based waits over fixed
   sleeps. Model cancellation, unmount, timeouts, failed requests, stale responses and double
   submission. [Async](asynchronous-behavior-and-timers.md).
6. **Construct realistic negative and boundary cases.** Validation errors, 403/409/5xx, network
   offline/reconnect, expired session, partial data, stale revisions, scope change, accessibility,
   empty/long/RTL text, small viewport and destructive-action confirmation when specified. Assert
   what must **not** happen (e.g. no early success; no private data after scope switch).
   [Network](network-and-contract-testing.md).
7. **Protect against test blindness.** Challenge tests with deliberate plausible bugs
   (mutation/negative controls). Record baseline pass + mutant failure with a specific oracle; an
   exception caused by malformed test infrastructure does not count as a detected product defect.
   Keep coverage as a map of unexercised code, not a correctness certificate.
   [Mutation](mutation-and-coverage.md).
8. **Diagnose flaky outcomes rather than masking them.** Track rerun changes, seeds, order
   dependencies, clock/network variability, shared state and environment. A test that passes only on
   retry must be reported as flaky or unresolved, not clean pass. Quarantine only with owner, ticket
   and expiration. [Reliability](flakiness-and-ci-diagnostics.md).
9. **Preserve trustworthy evidence.** Store command, revision, dependency/browser/OS versions, input
   fixture hash, environment, test case/REQ ID, actual expected/observed, retries, attachments,
   failure traces and limitations. Redact credentials, user data and network bodies. Distinguish
   `PASS`, `FAIL`, `NOT RUN`, `BLOCKED`, `INCONCLUSIVE`, `FLAKY`.
   [Evidence](test-evidence-and-reporting.md).
10. **Run required gates and report gaps.** Execute unit and browser checks appropriate to the AC;
    include cross-browser, real API, accessibility technology or production-representative checks
    only when environments are available. Mark each absent gate **NOT EXECUTED**. Do not report
    synthetic fixture success as Ec application acceptance or an agent-selection result.
    [Matrix](browser-matrix-and-accessibility.md), [Science](scientific-evidence-and-limits.md).


## Hard rejection criteria

Reject a test/evidence claim when it: asserts only internal implementation while user outcome is
missing; treats mocked 200 or a hidden button as backend permission evidence; calls a pending
request "saved"; silently accepts flaky reruns; shares identities/storage across supposedly isolated
cases; uses sleeps for causal synchronization without justification; depends on test order; counts a
syntax error as proof that a behavioral mutation was killed; uses snapshots alone to claim
correctness; claims WCAG AA from an automated scan; leaks credentials/traces; claims cross-browser
coverage from Chromium only; passes a contract without an approved oracle; or uses a fixture result
as proof of production behavior.


## Further references by decision

Read only the reference relevant to the unresolved decision. Research registers describe imported authorship, not current execution.

- [Acceptance oracles and traceability](acceptance-oracles-and-traceability.md): acceptance oracles and traceability.
- [Async causality and deterministic clocks](asynchronous-behavior-and-timers.md): asynchronous behavior and timers.
- [Browser, viewport, accessibility and user-path matrix](browser-matrix-and-accessibility.md): browser matrix and accessibility.
- [Fixtures, reproducibility and test isolation](fixtures-and-isolation.md): fixtures and isolation.
- [Flaky-test diagnosis and CI evidence](flakiness-and-ci-diagnostics.md): flakiness and ci diagnostics.
- [Mutation testing and coverage interpretation](mutation-and-coverage.md): mutation and coverage.
- [Network controls, mocks and live backend contracts](network-and-contract-testing.md): network and contract testing.
- [Playwright E2E and user-visible locators](playwright-e2e-and-locators.md): playwright e2e and locators.
- [Skill activation and selection oracles](routing-and-test-oracles.md): routing and test oracles.
- [Peer-reviewed scientific evidence — claims and limits](scientific-evidence-and-limits.md): source provenance or a disputed recommendation.
- [Sources, provenance and technical decision ledger](sources-and-decisions.md): source provenance or a disputed recommendation.
- [Evidence reporting and gate semantics](test-evidence-and-reporting.md): test evidence and reporting.
- [Test layers, scope, and ownership](test-layers-and-scope.md): test layers and scope.
- [Vitest configuration and test environments](vitest-and-test-environments.md): vitest and test environments.
- [Vue component testing with Vue Test Utils v2](vue-component-testing.md): vue component testing.
