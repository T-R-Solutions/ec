# Strictness, narrowing, discriminated unions and state-space proof

> Use the destination's existing requirements and evidence format; named workflow documents are examples, not prerequisites. Optional SDD mapping is configured outside this skill.

**Scope:** TypeScript correctness at language type boundaries, not business rule authority. Evidence: official TypeScript [Handbook narrowing](https://www.typescriptlang.org/docs/handbook/2/narrowing.html), [strict](https://www.typescriptlang.org/tsconfig/strict), [exact optional types](https://www.typescriptlang.org/tsconfig/exactOptionalPropertyTypes.html), [unchecked index](https://www.typescriptlang.org/tsconfig/noUncheckedIndexedAccess.html).

## Workflow

1. Collect observed data sources and every feasible state/transition from the project workflow/API, marking *known* versus *hypothesized* variants.
2. Prefer a discriminated union when state variants have exclusive fields. Do not make `error?`, `loading?`, and `data?` independently optional and thereby permit impossible combinations.
3. Switch on a literal discriminator and require an exhaustive `never` branch. Changing the union must make unhandled consumers fail compilation.
4. Treat `null`, `undefined` and absent object fields as distinct when observable in JavaScript; enable `strictNullChecks` and consider `exactOptionalPropertyTypes`.
5. Account for empty arrays and arbitrary-index object maps using `noUncheckedIndexedAccess`, rather than asserting `array[i]!` based on intent.
6. Use `unknown` for values from outside the trust boundary. Narrow with runtime guards. A user-written predicate (`value is T`) is itself a claim that must be tested.
7. Verify both compile-positive and compile-negative cases, then independent runtime behavior.

```ts
type LoadState<T> =
  | { phase: 'idle' }
  | { phase: 'pending'; requestId: string }
  | { phase: 'ready'; data: T }
  | { phase: 'failed'; reason: string };

function label<T>(state: LoadState<T>): string {
  switch (state.phase) {
    case 'idle': return 'idle';
    case 'pending': return state.requestId;
    case 'ready': return 'ready';
    case 'failed': return state.reason;
    default: { const unreachable: never = state; return unreachable; }
  }
}
```

**Important:** if the server can return additional states, this model must be updated from the real contract; compiling the example doesn't establish completeness in production.

## Positive, negative and boundary evidence

| Probe | Expected observation | Why |
|---|---|---|
| Valid discriminated variant | Strict `tsc` succeeds | Expected object shape has required fields |
| Missing `data` for `ready` | Compiler rejects | Absent required field |
| New variant with old exhaustive switch | Compiler rejects at `never` | Consumer missing a case |
| `unknown.id` access | Compiler rejects | Unsafe value boundary |
| `const x: string = array[0]` under `noUncheckedIndexedAccess` | Compiler rejects | Array might be empty |
| `reason?: string` supplied as `undefined` under `exactOptionalPropertyTypes` | Compiler rejects | Presence differs from absence |
| Runtime JSON payload missing fields | Runtime decoder rejects | Type assertions/checker are erased |

**Do not silently remove strict flags to clear diagnostics.** If migrating an existing repository, record the baseline/error budget, isolate independent commits, validate consumers, and document temporary exceptions with owners and expiry.

## Limits

TypeScript is deliberately structurally typed and not fully sound; a successful type-check is *not* memory safety, runtime schema validation, ACL enforcement or protocol proof. Read [Type Compatibility: A Note on Soundness](https://www.typescriptlang.org/docs/handbook/type-compatibility.html). `readonly` is shallow at the declaration boundary and does not freeze the runtime object; use defensive copies or runtime checks when mutability matters.
