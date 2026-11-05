# Compiler settings, composite builds and `.d.ts` consumer contracts

> Ec-specific rules below apply only to an Ec consumer whose current contract establishes them.

Sources: official [TSConfig strict](https://www.typescriptlang.org/tsconfig/strict), [Project References](https://www.typescriptlang.org/docs/handbook/project-references), [Publishing declarations](https://www.typescriptlang.org/docs/handbook/declaration-files/publishing.html).

## Baseline checklist (not universal configuration)

- Set `strict` explicitly where feasible and consider `noUncheckedIndexedAccess`, `exactOptionalPropertyTypes`, `noImplicitOverride`, `noPropertyAccessFromIndexSignature` according to the API's semantics.
- Choose `target`, `lib`, `module`, `moduleResolution` from the **deployment runtime**, not from version defaults.
- Set `types` explicitly to avoid undesired ambient declarations; TS 6/7 changed defaults and usage expectations.
- For libraries, ensure declaration output is referenced in `types` and `exports` (if exported conditionally), and build a real consumer before release.
- Review `skipLibCheck`: it can hide errors in dependency `.d.ts`; do not present a green skip as proof all published declarations are sound.
- For composite packages, `tsc --build` sorts dependencies; `composite` requires declaration and complete input-file inclusion. Check changed `.d.ts` and the consumer-facing API, not just `.js` output.
- Test clean builds and build-output removal. Incremental caches can be stale or nonportable; record command and repository revision.

**Example of toolchain metadata (not an Ec application configuration resource):**

```json
{
  "compilerOptions": {
    "strict": true,
    "noUncheckedIndexedAccess": true,
    "exactOptionalPropertyTypes": true,
    "module": "NodeNext",
    "moduleResolution": "NodeNext",
    "target": "ES2022",
    "declaration": true,
    "rootDir": "src",
    "outDir": "dist"
  }
}
```

This is TypeScript's required compiler configuration format. It does **not** compete with Ec's YAML-only declarative product resource configuration. JavaScript packages may also need `package.json` metadata; that too is tooling/distribution metadata, not a desired-state resource in Ec.

## Test oracles

- Positive: clean `tsc -p`; exported `dist/index.d.ts` exists; consumer call with correct type compiles.
- Negative: consumer calls a function with the wrong argument and receives a real TS diagnostic; a broken `exports` map fails in a clean sandbox.
- Border: optional property absent versus explicitly `undefined`, and a library consumer under both NodeNext/bundler where supported.
- Check release compatibility under each supported TS/runtime version in CI **when those versions are installed**. A single compiler's success is not a compatibility matrix.
