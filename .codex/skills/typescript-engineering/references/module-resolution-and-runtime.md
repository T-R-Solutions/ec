# Module resolution, Node ESM, bundlers and emitted JavaScript

Primary source: official TypeScript [Modules Reference](https://www.typescriptlang.org/docs/handbook/modules/reference), particularly NodeNext vs Bundler and package `exports` resolution. Confirm actual host runtime and package metadata; never choose module flags by habit.

## Decision matrix

| Deployment | Typical starting point (verify target) | Probe required |
|---|---|---|
| Browser SPA built by modern bundler | `module: preserve` or `esnext`, `moduleResolution: bundler` (version/tool dependent) | Bundler actual build, browser import analysis |
| Node.js with native ESM/CJS | `module: nodenext`, `moduleResolution: nodenext` (or version-locked node mode) | Emitted `.js/.mjs/.cjs`, Node runtime, package `type` |
| Public library | Declaration emit + explicit `exports`/`types` contract aligned with supported consumers | Consumer packages under actual supported runtimes/resolvers |
| TypeScript-only typecheck for a bundler | `noEmit` and bundler resolution consistent with downstream build | Negative path/extension tests, actual bundler build |

**Node ESM example:**

```ts
// math.mts exports double()
import { double } from './math.mjs'; // .mjs runtime extension even in .mts source
```

A relative import with no extension may pass under `bundler` and fail under `nodenext`; these are not interchangeable semantics. A successful `tsc --noEmit` in bundler mode does **not** prove Node will resolve the same path.

## Verification sequence

1. Record runtime version, `package.json` `type`, package exports and dependency graph. For a library verify conditional `exports` and `.d.ts` locations.
2. Inspect effective options with `tsc --showConfig` for the installed compiler (support varies by version).
3. Build/emit a sample from the actual package; inspect generated import specifiers and extension.
4. Run emitted JS in the actual runtime; run `tsc` over a consumer that imports the declarations via published-looking paths.
5. Include negative tests for missing extensions and incompatible call signatures.
6. When using `paths`, configure the actual bundler/runtime aliases too; TypeScript's `paths` alone does not rewrite runtime specifiers.

## Compatibility warning — TypeScript 7

TypeScript 7.0 adopts stricter module defaults and hard-errors on some older module resolution options, including `node10`/`classic` (see [migration guidance](migration-and-version-gates.md)). Do not upgrade target compilers just to satisfy this reference. Some ecosystem tools may still require TypeScript 6's programmatic JS API even if `tsc` 7 is installed.
