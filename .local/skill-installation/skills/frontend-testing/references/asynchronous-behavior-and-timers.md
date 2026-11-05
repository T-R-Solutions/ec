# Async causality and deterministic clocks

Use `await nextTick()` for Vue's batched DOM reactivity; `flushPromises()` when non-Vue promises must resolve; use Playwright's retryable assertions for visible outcomes. Do not mistake microtask flushing for completion of a pending server request, and do not assume all timers or network operations have settled.

For races, inject a deferred transport with explicit resolve/reject controls. Assert: pending is not success, only one in-flight operation when specified, cancelled/stale responses cannot overwrite current state, failure preserves user input, and session/scope changes invalidate old data. Ensure test tear-down resolves/rejects any pending promises to avoid suites that hang.

Clock guidance: use fake timers for deterministic debounce/timeout units; retain a real-timer integration test for interaction with browser scheduler. Verify the current Vitest timer APIs before use. Official: https://test-utils.vuejs.org/guide/advanced/async-suspense ; https://vitest.dev/guide/mocking/timers ; https://playwright.dev/docs/actionability
