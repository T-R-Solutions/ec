# Vitest configuration and test environments

> Ec-specific rules below apply only to an Ec consumer whose current contract establishes them.

Confirm Vite/Vitest versions, the lockfile, Node engine, Vue plugin and actual `test.environment`. `node` works for pure logic; `jsdom`/`happy-dom` approximate DOM but do not replace real rendering engines, CSS geometry, or accessibility technology. Vitest Browser Mode requires an appropriate installed provider; choose according to the observed toolchain rather than claiming one is universally fastest.

Restore spies/mocks/stubbed environment and real timers after each test; choose fake timers only when time is a controlled dependency. Avoid `vi.mock()` for a real-API acceptance gate. Type-check SFCs with `vue-tsc` separately; TypeScript and Vitest transpilation are not equivalent to SFC template type checking.

Minimal illustrative config:

```ts
import { defineConfig } from 'vitest/config'
export default defineConfig({
  test: { environment: 'jsdom', clearMocks: true, restoreMocks: true }
})
```

This is a setup **example**, not a proven optimum nor an Ec app configuration. Official: https://vitest.dev/guide/ ; https://vitest.dev/guide/mocking.html ; https://vitest.dev/guide/coverage ; https://vitest.dev/guide/browser/
