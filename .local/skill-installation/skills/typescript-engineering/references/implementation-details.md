# Detailed decisions and failure cases

Read only for a decision needing more detail than the entry point. Apply the relevant steps to the
actual task; the sections are not a mandatory artifact or test checklist. Use existing project
contracts in their actual format. File names such as spec.md, plan.md or tasks.md are examples, not
required dependencies. Other skills are optional expertise, never enforcement mechanisms.

Ec references below are conditional consumer examples. They impose no Ec documents, YAML format,
stack or product policy on a generic task.

## Working method

1. **Locate authority, tooling and a testable task.** Inspect repository instructions, installed
   TypeScript version and lockfile, `tsconfig` inheritance, runtime/bundler, package exports,
   existing `.d.ts`, consumers, test runner and relevant project workflow feature or narrow local
   scope. Check whether the target actually uses TypeScript 5.x, 6.x, 7.x or a compiler API
   integration. Use the destination project's actual requirements; product-specific examples are
   conditional. Record current observed code, intended behavior and a reproducible failure; do not
   treat sample fixtures as implemented product features. Consult [version
   gates](migration-and-version-gates.md).
2. **Define types from real invariants rather than syntax fashion.** Identify API consumers, valid
   input/output states, optional vs absent properties, aliasing/mutability, error variants and who
   owns runtime validation. Prefer informative inferred local types; annotate public boundaries and
   ambiguous return types; model alternatives as tagged unions, use `never` exhaustiveness, prefer
   `unknown` over escaping into `any` at untrusted boundaries. Use `readonly` as a compile-time
   interface, not proof of deep runtime immutability. See [strictness and
   narrowing](strictness-control-flow-and-boundaries.md).
3. **Design constraints, reusable generics and public contracts.** Express relationships with `T`,
   `K extends keyof T`, mapped/conditional types only where they materially reduce duplication;
   favor simple types over brittle recursion or needless instantiation cost. Use `satisfies` to
   check a literal's conformance without replacing its inferred type, not as runtime schema
   validation. Avoid opaque casts and non-null assertions when a genuine guard or explicit state
   model is available. Specify callable variance and optional semantics where consumers need them.
   See [type-level API design](generics-and-public-api-design.md).
4. **Guard input at runtime and narrow safely.** Treat `JSON.parse`, `fetch`, messages, local
   storage, third-party callbacks and FFI as `unknown` until checked with trustworthy runtime
   predicates or a verified validator. Check discriminator, primitive values, allowed ranges and
   external references. Avoid `as T` assertions as substitutes for proof. Recognize the structural
   type system's intentional unsoundness and type erasure. For Ec, do not encode desired resource
   definitions in TypeScript files: the normative desired configuration remains YAML, and generated
   TS types are *projections of a separate contract*, not authority. See [runtime boundary and
   security](runtime-interop-and-safety.md).
5. **Choose compilation and module resolution to match deployment.** Distinguish browser/bundler
   builds from direct Node.js execution and from libraries consumed in both environments. Pair
   `module` with matching `moduleResolution`; verify file extensions, package `type`,
   `imports`/`exports`, type conditions and output format. `paths` only guides the compiler, not
   necessarily the runtime. For multi-project builds use `composite`, project references,
   declaration checks and actual consumers where justified. Prefer explicit reviewed `target`,
   `types`, `rootDir`, `skipLibCheck` and strictness; do not blindly copy a `tsconfig`. See
   [modules](module-resolution-and-runtime.md) and
   [compiler/builds](tsconfig-build-and-declarations.md).
6. **Version-gate and migrate without silent compatibility claims.** Read installed compiler
   diagnostics and official release notes before changing flags or APIs. Note that TypeScript 6
   changes defaults/deprecates options; TypeScript 7.0 (native compiler) makes some deprecated
   settings errors and does not expose the former JavaScript compiler API. Audit downstream tools
   such as `vue-tsc`, ESLint integrations and custom compiler API utilities before upgrading; pin
   reproducible dependencies and execute the consumer's own CI on that version. TypeScript 7.1 beta
   features must not be made baseline without approval. See [version
   gates](migration-and-version-gates.md).
7. **Prove compile-time and runtime contracts independently.** Run actual `tsc --project` or `tsc
   --build` at the target version; include positive compile probes, **negative** `TSxxxx`
   diagnostics for disallowed inputs, and consumer-built `.d.ts` compatibility checks. Also run
   runtime tests for boundary validators, state transitions, JavaScript interop and degraded cases;
   type-checking does not prove payload validity, side-effect safety or integration. Use isolated
   mutation probes to check that oracles detect intentional regressions. Use distinct gates for
   lint, tests, package emit, Ec project acceptance and performance measurements; no imaginary
   benchmarks. See [verification methodology](verification-and-diagnostic-oracles.md).
8. **Deliver bounded artifacts and transparent evidence.** Record compiler/runtime versions, changed
   files, type/API assumptions, output artifacts, negative diagnostic IDs, executed commands and
   exits, release compatibility, untested versions, application-test gaps, package path and
   requirement/acceptance IDs evidence. If the compiler, dependencies, lockfile or required fixture
   are missing, mark **NOT EXECUTED**, not successful. If a compiler check reports no errors on a
   `type assertion`, it does **not** certify runtime data. Do not alter product policy or weaken a
   test criterion just to obtain a green build.


## Further references by decision

Read only the reference relevant to the unresolved decision. Research registers describe imported authorship, not current execution.

- [Generic relationships, inference, `satisfies` and API ergonomics](generics-and-public-api-design.md): generics and public api design.
- [TypeScript 5.x → 6.x → 7.x version gates (as researched 2026-10-31)](migration-and-version-gates.md): migration and version gates.
- [Module resolution, Node ESM, bundlers and emitted JavaScript](module-resolution-and-runtime.md): module resolution and runtime.
- [Selection test oracles and future application integration matrix](routing-and-selection-oracles.md): routing and selection oracles.
- [Runtime trust boundaries, JavaScript interoperability and safe parsing](runtime-interop-and-safety.md): runtime interop and safety.
- [Technical evidence register — TypeScript Engineering v1.0](sources-and-decisions.md): source provenance or a disputed recommendation.
- [Strictness, narrowing, discriminated unions and state-space proof](strictness-control-flow-and-boundaries.md): strictness control flow and boundaries.
- [Compiler settings, composite builds and `.d.ts` consumer contracts](tsconfig-build-and-declarations.md): tsconfig build and declarations.
- [Tests, negative compiler probes, mutation sensitivity and evidence tiers](verification-and-diagnostic-oracles.md): verification and diagnostic oracles.
