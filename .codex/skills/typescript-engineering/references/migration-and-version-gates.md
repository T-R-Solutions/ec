# TypeScript 5.x → 6.x → 7.x version gates (as researched 2026-10-31)

> Ec-specific rules below apply only to an Ec consumer whose current contract establishes them. Commands, fixture paths and historical execution claims from the source package are illustrative or imported evidence unless the corresponding files are present and independently checked; they are not current test results or bundled executable tools.

**Official release sources:** [TypeScript 6.0](https://devblogs.microsoft.com/typescript/announcing-typescript-6-0/), [TypeScript 7.0](https://devblogs.microsoft.com/typescript/announcing-typescript-7-0/), [TypeScript 7.1 beta](https://devblogs.microsoft.com/typescript/announcing-typescript-7-1-beta/), [npm stable releases](https://www.npmjs.com/package/typescript), [TypeScript 7 release assets](https://github.com/microsoft/TypeScript/releases). URLs checked during authoring; changes after the research date require re-verification.

| Compiler | Evidence from official release | Required action |
|---|---|---|
| 5.8.3 | Actually installed for this package's checks | Diagnostics/results apply to this environment only |
| 6.0 | `strict`, `module`, `target`, ambient `types`/rootDir defaults change; multiple flags deprecated | Pin explicit options, review deprecations and test consumers |
| 7.0.2 (stable on 2026-10-31) | Go native compiler, strict 6 defaults and deprecations become errors; 7.0 ships without the former JS compiler API | Run migration matrix; audit programmatic users of `typescript` before replacing package |
| 7.1 beta (2026-10-20 announcement) | New compiler API design under beta evaluation | No baseline dependency without explicit prerelease approval |

## Upgrade checklist

1. Inventory real workspace `package.json`, lockfile, local compiler binary, `vue-tsc`/Vite, ESLint, bundler plugins, code generators, custom scripts importing the compiler API. Distinguish `tsc` CLI and JS module API consumers.
2. Record the exact 5.x project configuration and produced `.d.ts`/JS output; run clean `tsc --showConfig`, compile, declaration consumer and runtime verification.
3. Review the TypeScript 6.0 release notes *before* moving to 7; set `rootDir`, `types`, `target` and `module` explicitly where necessary. Remove deprecated options or apply documented migration tools; do not suppress and forget warnings.
4. For a 7.0 toolchain, verify `typescript` new native `tsc` and whether tooling still expects `typescript`'s JS `createProgram` or language service API. The official announcement describes `@typescript/typescript6` and npm aliasing as a compatibility path for 7.0, subject to the consuming tool's constraints.
5. Execute actual runtime/bundler, public `.d.ts` consumer, Vue SFC type checks (if applicable) and full CI under *each supported version*. Preserve a rollback plan. Do not assume a Vue plugin has already adopted TypeScript 7.
6. Compare diagnostics, emitted import paths and declaration changes, not just successful `tsc` exit. Record per-version failures and reconcile against the product's version support policy.
7. For performance claims, run repeatable benchmarks on target projects and runners with warm/cold separation, hardware specification and comparable options. Vendor-reported speedups are not a benchmark of the Ec application.

## Rejected assumptions

- “TypeScript 7 is compatible with every npm compiler API consumer”: **false** for the former JS compiler API in 7.0.
- “A developer machine with 5.8.3 proves 7.0.2 compatibility”: **false**; distinct compiler/build/toolchain.
- “`moduleResolution: bundler` is safe for Node ESM directly”: **false in general**, resolution paths differ.
- “Default `strict` stays constant across major releases”: **false**; TypeScript 6.0 made it default on.
- “7.1 beta API is stable”: **not established**.
- “TypeScript 7 is 10x faster in this product”: **not measured here**; the official team's performance measurements are external case studies, not a reproducible Ec benchmark.
