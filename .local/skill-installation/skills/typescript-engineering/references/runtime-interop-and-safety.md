# Runtime trust boundaries, JavaScript interoperability and safe parsing

> Use the destination's existing requirements and evidence format; named workflow documents are examples, not prerequisites. Optional SDD mapping is configured outside this skill. Ec-specific rules below apply only to an Ec consumer whose current contract establishes them.

Primary sources: official TypeScript [Everyday Types (`any`, type assertions)](https://www.typescriptlang.org/docs/handbook/2/everyday-types.html), [Narrowing](https://www.typescriptlang.org/docs/handbook/2/narrowing.html), [Type Compatibility — soundness](https://www.typescriptlang.org/docs/handbook/type-compatibility.html).

## Threat and failure model

TypeScript emits JavaScript; interfaces and type annotations are erased. A value with type `T` at a compile-time boundary may differ from runtime reality after `JSON.parse`, untrusted APIs, plugin messages, storage, reflection, JS callbacks or unsafe `as T`. Structural typing intentionally permits assignments that do not prove runtime invariants. A generic `Result<T, E>` only constrains code that honors the typed contract; it does not enforce serialization rules or ACLs.

### Small safe decoder (illustrative, not an Ec schema)

```ts
type Message = {kind: 'start'; id: string};
function parse(input: unknown): Message | undefined {
  if (!input || typeof input !== 'object' || Array.isArray(input)) return;
  if (!('kind' in input) || input.kind !== 'start') return;
  if (!('id' in input) || typeof input.id !== 'string') return;
  if (input.id.length === 0) return;
  return {kind: 'start', id: input.id}; // new object, no unknown keys copied
}
```

**This example is not a hardened hostile-object parser:** Proxy getters and prototype behavior need a separate threat model. For encoded wire data, decode from a byte/string parser with strict size and syntax limits, then validate the semantic schema. Do not promise safety from the example alone.

## Contract tests

1. Valid input accepted without implicit default changes.
2. Missing key, wrong discriminator/type, invalid range, extra key and conflicting optional fields rejected.
3. `null`, array, date, nested objects or unexpected prototypes rejected when contract requires plain records.
4. Absence versus `undefined` remains distinct when semantics require it; compile-time `exactOptionalPropertyTypes` helps local code only.
5. Output is a newly created typed value, not the same untrusted object reference when unknown fields must be stripped.
6. Runtime errors are surfaced as explicit outcomes; the type guard is tested independently.
7. Security-sensitive checks (authz, secret exposure, permissions) happen on the backend with authoritative data. A TypeScript type is not a permission enforcement mechanism.

## Ec boundary

The Ec YAML configuration and schema authority resides in the project norms and Ec resource/schema project workflow. This generic skill can type a parsed resource AST **after** a genuine parser/validator and can help implement typed API consumption, but does not introduce JSON/TOML/TypeScript files as alternative Ec desired-state configuration. API payloads may use JSON as a transport format where the actual API contract permits; that does not make it a canonical resource file.
