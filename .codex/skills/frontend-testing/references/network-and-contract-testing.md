# Network controls, mocks and live backend contracts

Distinguish stub fidelity from contract evidence. A `page.route('**/api/**', route.fulfill({status:403}))` tests the UI when receiving 403, not that the live server denies access. Pair boundary conditions with live integration evidence, API contract test authority and authentication policies. Use a route response table with status, body type, retry timing, idempotency semantics and expected UX.

Test controlled errors (401/403, 404, 409, 429, 500), network abort, disconnect/reconnect, delayed old response and inconsistent payload. Assert input preservation, no premature success, no disclosure of private data and safe retry. Isolate test credentials; never place access tokens or production traces in git. Playwright route mocks can be intercepted by service workers; evaluate service-worker behavior and use explicit settings if required.

Official: https://playwright.dev/docs/network ; https://playwright.dev/docs/mock ; https://owasp.org/www-project-web-security-testing-guide/
