# Fixtures, reproducibility and test isolation

Give each test its own user/tenant/workspace scope, request queue, clock, storage and unique records. Reset global stores, mocks, timers and listeners. Use per-test `BrowserContext` or documented equivalent. For parallel workers, namespace created data using worker and test identifiers; avoid deleting shared staging records in broad cleanup.

A representative fixture states which conditions are synthetic, which API endpoints are mocked, which are live, and how credentials were provisioned. Include failure-on-missing-input behavior; never fall back from real integration mode to an unrelated synthetic mock silently. Use synthetic secrets in fixtures and redact them in reports.

Test inverse/negative isolation: data created for principal A should not appear in principal B's browser/storage, and a late response from scope A must not populate scope B. A UI check does not prove server data isolation without API observation.

Official: https://playwright.dev/docs/best-practices ; https://playwright.dev/docs/test-fixtures ; https://vitest.dev/guide/test-context
