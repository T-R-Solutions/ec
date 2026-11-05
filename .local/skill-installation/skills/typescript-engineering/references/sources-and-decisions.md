# Technical evidence register — TypeScript Engineering v1.0

> Imported authoring record. Research dates, consultations and execution claims below belong to the supplied source package; they were not reproduced in this adaptation. Referenced validation scripts, fixtures, product norms and logs are not bundled unless present in this folder. Verify their availability and provenance before use. No historical PASS is current acceptance.

**Research cut:** 2026-10-31. Official pages and release announcements were browsed in this session; peer-reviewed material was verified via publisher metadata or institutional repository abstracts. This register states *which* decision each source supports and what it does **not** prove. Evidence citations are URLs, not invented benchmark results. The library's exact current installed version differs from the latest stable; the skill must check the consumer's own compiler.

## Official TypeScript primary sources

| ID | Source / primary URL | Supported contract / limit |
|---|---|---|
| T01 | [Handbook: Narrowing](https://www.typescriptlang.org/docs/handbook/2/narrowing.html) | Discriminant narrowing, `never` exhaustive checks. Not runtime schema validation. |
| T02 | [Handbook: Generics](https://www.typescriptlang.org/docs/handbook/2/generics.html) | Parameter relationships and constraints; not an argument for unnecessary generic complexity. |
| T03 | [Handbook: Everyday Types](https://www.typescriptlang.org/docs/handbook/2/everyday-types.html) | `any` escape, assertion erasure. Not a guarantee of runtime data shapes. |
| T04 | [TSConfig: strict](https://www.typescriptlang.org/tsconfig/strict) | Strict family, changing defaults across releases. Not whole-program proof. |
| T05 | [TSConfig: noUncheckedIndexedAccess](https://www.typescriptlang.org/tsconfig/noUncheckedIndexedAccess.html) | Potential `undefined` for undeclared indexed fields. |
| T06 | [TSConfig: exactOptionalPropertyTypes](https://www.typescriptlang.org/tsconfig/exactOptionalPropertyTypes.html) | Property absence versus explicit undefined is observably distinct. |
| T07 | [Modules: Reference](https://www.typescriptlang.org/docs/handbook/modules/reference) | NodeNext/Bundler resolution, package `exports`, runtime path alignment. |
| T08 | [Project References](https://www.typescriptlang.org/docs/handbook/project-references) | `composite`, `.d.ts` boundaries and `tsc --build`. No universal performance speedup. |
| T09 | [Publishing Declaration Files](https://www.typescriptlang.org/docs/handbook/declaration-files/publishing.html) | Library/consumer `types` metadata and declaration packaging. |
| T10 | [Handbook: Type Compatibility](https://www.typescriptlang.org/docs/handbook/type-compatibility.html) | Structural typing and intentional unsound cases. |
| T11 | [Mapped Types](https://www.typescriptlang.org/docs/handbook/2/mapped-types.html) | Mapped type syntax and modifiers. |
| T12 | [Conditional Types](https://www.typescriptlang.org/docs/handbook/2/conditional-types.html) | Type-level conditional computation; no runtime implications. |
| T13 | [TS 4.9 Release Notes — satisfies](https://www.typescriptlang.org/docs/handbook/release-notes/typescript-4-9.html) | `satisfies` keeps inferred type while checking conformance. |
| T14 | [TS 6.0 official release blog](https://devblogs.microsoft.com/typescript/announcing-typescript-6-0/) | Changed defaults and deprecations; migration prerequisite. |
| T15 | [TS 7.0 official release blog](https://devblogs.microsoft.com/typescript/announcing-typescript-7-0/) | Native compiler, compatibility gates and 7.0 absence of old programmatic API. Vendor performance examples are not an Ec benchmark. |
| T16 | [TS 7.1 Beta official release blog](https://devblogs.microsoft.com/typescript/announcing-typescript-7-1-beta/) | Beta compiler API (not stable baseline as of research date). |
| T17 | [npm TypeScript release listing](https://www.npmjs.com/package/typescript) | Stable 7.0.2 observed 2026-10-31, not 7.1 beta. |
| T18 | [GitHub TypeScript official releases](https://github.com/microsoft/TypeScript/releases) | 7.0.2 released August 2026; verify lockfile versions before assumptions. |
| T19 | [TSConfig Reference](https://www.typescriptlang.org/tsconfig/) | Exact option behavior in toolchain; version-sensitive defaults. |

## Peer-reviewed and empirical research

| ID | Publication and verified source | Direct evidence and bounded interpretation |
|---|---|---|
| P01 | Gao, Bird, Barr (ICSE 2017), *To Type or Not to Type: Quantifying Detectable Bugs in JavaScript*. [UCL repository abstract](https://discovery.ucl.ac.uk/id/eprint/10064729/), DOI [10.1109/ICSE.2017.75](https://doi.org/10.1109/ICSE.2017.75) | Annotations added retrospectively to selected public JS bugs; Flow 0.30 and TS 2.0 each identified 15% in that experimental setting. **Not** a universal future error-reduction rate, a TS 7.0 assessment, or a CI/CD-specific trial. |
| P02 | Barr, Harman, McMinn, Shahbaz, Yoo (IEEE TSE 2015), *The Oracle Problem in Software Testing: A Survey*. [UCL abstract](https://discovery.ucl.ac.uk/id/eprint/1471263/), DOI [10.1109/TSE.2014.2372785](https://doi.org/10.1109/TSE.2014.2372785) | Survey of problems automating expected results and oracle types. Motivates explicit positive/negative oracles. **Not** an independent test result for this package. |
| P03 | Inozemtseva, Holmes (ICSE 2014), *Coverage Is Not Strongly Correlated with Test Suite Effectiveness*. [Author paper](https://www.cs.ubc.ca/~rtholmes/papers/icse_2014_inozemtseva.pdf), DOI [10.1145/2568225.2568271](https://doi.org/10.1145/2568225.2568271) | Study of Java systems controlling test suite size, showing coverage alone is limited as an effectiveness proxy. **Not** quantitatively generalizable to TS, Vue or Ec. |
| P04 | *An empirical study on bugs in TypeScript programming language*, *Journal of Systems and Software* vol. 226 (2025) 112445. [Publisher abstract](https://www.sciencedirect.com/science/article/abs/pii/S016412122500113X), DOI [10.1016/j.jss.2025.112445](https://doi.org/10.1016/j.jss.2025.112445) | Historical study of compiler/language defect characteristics; motivates version regression and negative diagnostics. **Only abstract/metadata were accessible**, not independently reproduced or fully read. |

## Decisions and confidence

- **Strongly supported by primary docs**: `strict` checking semantics, `never` exhaustiveness, `unknown` boundary, `satisfies`, explicit `NodeNext`/Bundler resolution, composite declaration consumer testing, 6.0/7.0 migration constraints.
- **Engineering recommendation** (not a TS specification): make public domain states discriminated, test negative diagnostics, emit `.d.ts` to inspect actual consumers, fail closed when toolchain absent.
- **Experimentally observed locally**: TypeScript **5.8.3**, Node **22.16.0** synthetic code and diagnostics in `validation/*-output.txt`. Not evidence of TS 7.0.2 runtime/compiler compatibility.
- **Not verified**: external sources' study methods beyond accessible abstracts, compiler speedups on this project, actual Ec backend/Vue SFC runtime, cross-version CI matrix, security certification, live tool/agent selection. No unsupported performance percentages imported into decisions.

## Authority and format note

`README(3).md` and early Ec research documents describe historically superseded JSON desired-state proposals. Both current Ec norms override them with **YAML-only canonical desired-state resources**, semantic parity Visual/Form/YAML and distinct operational storage. `tsconfig.json`, `package.json`, `MANIFEST.json` in this portable skill package are **compiler/distribution metadata** and are not alternative Ec configuration resource definitions. Product norms and SDD outrank method recommendations; this skill remains agnostic.

**Evidence calibration:** A historical compiler-bug study is not a universal application effectiveness estimate; a survey is not an independent test of this package. Paper P04's full methods are not verified from available materials; TypeScript 7.0.2 compiler behavior is not tested locally and cross-version integration was not executed.
