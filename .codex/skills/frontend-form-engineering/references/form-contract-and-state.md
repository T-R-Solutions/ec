# Field contracts and controlled form state

> Ec-specific rules below apply only to an Ec consumer whose current contract establishes them. Commands, fixture paths and historical execution claims from the source package are illustrative or imported evidence unless the corresponding files are present and independently checked; they are not current test results or bundled executable tools.

## Required contract before coding

| Property | Questions and evidence | Never infer |
|---|---|---|
| Identity | Stable field ID/key, owning resource, visible label, scope | Label text as canonical key |
| Value semantics | String/number/boolean/list, missing/null/empty/false/zero distinctions, ordering | `.number` gives a number for any input |
| Constraint ownership | HTML hint, client UX validator, backend accepted range, authorization gate | Client regex is authoritative |
| Draft baseline | Loaded entity/revision, user edits, unresolved unknown fields | Server/latest version equals local draft |
| Visibility | Always visible, conditionally visible, read-only, advanced | Hidden value can be erased |
| Error | Field key, message ID, priority, submission vs async vs transport | One generic “invalid” state covers all cases |
| Persistence | Submit payload, request ID, confirmed revision, conflict response | Successful request dispatch equals persistence |

A baseline is a verified snapshot of previously accepted values. A draft is a mutable view of proposed changes. Values may be *dirty* before validation. Errors are observations with origins and freshness, not permanent properties of the domain resource. Published/effective or applied state belongs to the product-specific configuration or operational authority, not this skill.

## Minimal state model

```text
pristine --field edit--> dirty
  dirty --validate/invalid--> invalid --edit--> dirty
  dirty --validate/valid--> submitting
  submitting --server rejects fields--> invalid
  submitting --server reports conflict--> conflict
  submitting --transport failure--> failed
  submitting --server ACK, no further edits--> saved (confirmed)
  submitting --server ACK, subsequent edits present--> dirty
```

Submission may occur from pristine (a user can re-submit unchanged data) if the spec permits it. “Saved” does not necessarily mean “published” or “applied.” Do not assume successful HTTP status always indicates work fully completed; inspect product-specific API responses.

## Field registry and preservation

Maintain an explicit registry of supported controls and stable machine keys. On submission, prefer a scoped patch or a complete authoritative snapshot *only when the contract permits it*. For fields not projected by a simple form, retain their original semantic values. If a parser or renderer cannot preserve a field, disable incompatible editing or require a resolution path rather than deleting it.

Example (synthetic TypeScript types, not an Ec schema):

```ts
type FieldIssue = { key: string; code: string; message: string };
type FormDraft<T> = {
  initialRevision: string;
  baseline: Readonly<T>;
  proposed: T;
  issues: FieldIssue[];
  pendingRequestId?: string;
};
```

Do not copy a generic `Record<string, unknown>` into a security-sensitive persistence model without runtime validation. For high-trust fields define discriminated type-safe value models; for untrusted inputs still perform runtime parsing and backend checks.

## Change and reset semantics

- `touched` means the user visited/interacted; it is not equivalent to `dirty` or `invalid`.
- `dirty` is defined by domain-value equivalence to a captured baseline, not merely whether a DOM event occurred.
- `discard` restores the accepted baseline; it cannot implicitly revoke server operations already applied.
- Browser `form.reset()` may restore HTML defaults rather than the latest confirmed baseline; controlled reactive forms should implement explicit baseline restoration.
- A local edit while a submission is pending does not modify that already-submitted snapshot. The ACK updates the baseline for the submitted snapshot while retaining new edits as dirty.
- Unknown extension fields and secret **references** remain intact when the user edits another field.

## Test oracles

`validation/ts/form-state.ts` and its Node harness exercise fake backend acknowledgement and snapshot semantics. Tests must also cover stale replies, duplicate clicks, conflict, edits while saving, reset and preservation of unedited metadata using the actual product adapter when available.

**Boundary:** This file defines the *method* of constructing form models. A product spec owns fields, lifecycle and permitted persistence. In Ec, the YAML authority and semantic round-trip are established in the Ec norms and specialized skills.
