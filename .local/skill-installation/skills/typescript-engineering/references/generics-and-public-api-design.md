# Generic relationships, inference, `satisfies` and API ergonomics

> Use the destination's existing requirements and evidence format; named workflow documents are examples, not prerequisites. Optional SDD mapping is configured outside this skill.

Source: official [Generics](https://www.typescriptlang.org/docs/handbook/2/generics.html), [Mapped Types](https://www.typescriptlang.org/docs/handbook/2/mapped-types.html), [Conditional Types](https://www.typescriptlang.org/docs/handbook/2/conditional-types.html), [TypeScript 4.9 satisfies](https://www.typescriptlang.org/docs/handbook/release-notes/typescript-4-9.html).

## Choose the smallest useful abstraction

- Retain inference for local variables and simple private functions. Explicit public types communicate contracts, especially inferred types depending on implementation details.
- A generic parameter should relate **two or more positions**, preserve key/value correlation, or express an indispensable constraint; avoid `function f<T>(v:T): void` where `T` brings no benefit.
- Use `K extends keyof T` to preserve the relationship between a particular key and `T[K]`.
- Prefer ordinary unions, interfaces and simple generics over high-depth recursive conditional types. If type-checking becomes slow, measure compiler diagnostics/profile and simplify only with evidence.
- Avoid turning an internal type helper into an unstable public `.d.ts` API; compile a consumer against emitted declarations.
- Use `satisfies` when an object literal must satisfy a shape *and* retain its inferred specifics; it does not validate `unknown` JSON at runtime.

```ts
function pick<T extends object, K extends keyof T>(obj: T, key: K): T[K] {
  return obj[key];
}

const handlers = {
  start: 1,
  stop: 2,
} satisfies Record<'start' | 'stop', number>;

const priority = pick({ id: 'task-1', priority: 2 }, 'priority'); // number
// pick({ id: 'task-1' }, 'priority'); // compile error by design
```

## Stability and testing

For public function signatures, exercise: valid consumer, mismatched argument, missing optional/exhaustive state, related generic key and wrong key. Add a `d.ts` consumer check after emit. Verify actual output module/`exports` rather than relying on editor-only success.

**Beware:** `satisfies` validates assignability, not exact runtime object key sets for arbitrary external values. Structural excess-property checks on fresh literals are not a secure runtime whitelist. A conditional type is evaluated at compile time and cannot inspect a parsed request.

## Anti-patterns

- `as unknown as T` to bypass a failing API boundary without a documented proof.
- Bare `Record<string, any>` used as a universal escape for schema-less data.
- Complex conditional/mapped aliases that hide error messages or make compilation fragile for marginal reuse.
- Generic constraints that encode product-policy decisions without the project workflow/domain owner's approval.
- `as const` on an object subsequently mutated by a different subsystem under a shared alias; compile-time immutability is not a freeze.
