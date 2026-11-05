# Layered Verification: Pure Model, Browser, Loopback, Application

> Ec-specific rules below apply only to an Ec consumer whose current contract establishes them.

| Layer | Executable in package | Can verify | Does not verify |
|---|---|---|---|
| TypeScript strict + Node tests | Yes | Parser edge cases, domain scope/sequence, bounded queues, retry gates, deterministic seeded transitions | Real browser EventSource, server data and Vue mounting |
| Mutation tests on compiled JS | Yes | Specific regression oracles kill executable deliberately wrong behavior | Adequate mutation coverage of the Ec application |
| Chromium DOM fixture + injected fake transport | Yes | Real DOM update/focus/state handling, scope switch, logout, bounded list | Live EventSource/WS handshake or Vue framework runtime |
| Python aiohttp loopback SSE and WS | Yes, if installed | Actual local HTTP framing, handshake, origin/session denial and successful WS exchanges | Production Rust service, proxy/CDN, browser client or TLS |
| Real Vue 3 + actual app API + Playwright browser | **NOT EXECUTED** | Intended component lifecycle, end-to-end auth, replay, streaming UI and server behaviors | Nothing without actual source and environment |
| Firefox/WebKit, device/mobile, degraded network, reverse proxy | **NOT EXECUTED** | Compatibility and deployment-specific faults when run | No evidence from Chromium-only tests |
| Security audit and assistive-technology evaluation | **NOT EXECUTED** | Authorization/UX gates once environments and qualified reviewers available | A synthetic fixture alone cannot certify security/WCAG |

Oracles must be product-specific: replay gap, duplicate, unauthorized event, stale subscription after logout, max queue, unexpected binary message, 429/503 Retry-After, 401 stop, 410 snapshot path, reconnection storm, bad heartbeat assumptions, disconnect during editing, and critical update not falsely marked complete. Test network exceptions and incorrect response headers. Record exact versions and source revision.

**Do not invent results:** a mock test or loopback aiohttp toy server does not demonstrate Rust SSE/WS implementation, CI job delivery, replay retention, or Ec project acceptance. Store raw reports, commands, test environment, and not-executed fields.
