# Async validation, submissions, concurrent edits and acknowledgements

> Ec-specific rules below apply only to an Ec consumer whose current contract establishes them.

## Three separate questions

1. **Is the draft locally valid?** This is an advisory form result.
2. **Did the server accept/store the requested change?** Only its authoritative response can confirm this, subject to the API contract.
3. **Has the change become effective?** This may require reconciliation, approval or execution; the form must not infer it from acceptance.

Generic frontend code cannot decide product publication/reconciliation semantics. In Ec, configuration publication belongs to `ec-configuration-publication` and operational states to their subsystem.

## Submission snapshot protocol

```text
current draft and base revision
  -> validate local fields
  -> create immutable {submissionId, baseRevision, snapshot}
  -> submit through authorized API
  -> pending display; block duplicate unsafe actions
  -> receive identified response
     |-- accepted: update confirmed revision + baseline for submitted snapshot
     |             keep edits after submit as new unsaved draft
     |-- field errors: attach only still-current errors; preserve values
     |-- conflict: keep draft, offer compare/rebase flow
     |-- transport failure: don't report success; allow safe retry policy
     |-- obsolete/cancelled: ignore stale response
```

Do not equate `AbortController.abort()` with a guarantee that the server performed no side effect. Depending on whether the server accepted the request before abort, the client may need to refresh/reconcile. For non-idempotent actions, duplicate prevention is necessary but insufficient; backend idempotency semantics must be obtained from the API contract.

## Independent async field checks

Availability checks (e.g. unique name) must be tied to the specific value and request generation. A late response for `alpha` must not mark a subsequently typed `beta` as invalid. Debouncing reduces load in some situations but does not solve the stale-reply race by itself. Treat successful asynchronous validation as advisory until the backend processes the final submission.

## Conflict preservation

On conflict, retain the original value, proposed value, server current value and relevant revision metadata when available. Present diff or manual merge; never silently choose last-writer-wins for sensitive administration. An error in authorization/approval is not a field constraint.

## Async test matrix

| Sequence | Expected form observation |
|---|---|
| submit → no server reply | Pending, never saved |
| submit → duplicate click | No duplicate unsafe request |
| submit A → edit B → ACK A | B remains unsaved; A may advance baseline |
| submit A → stale response for older request | No overwrite of newer draft |
| submit → 409 conflict | Values preserved; explicit conflict state |
| submit → field error | Correct field error, values retained |
| submit → network failure | Failed state, no success, retry policy |
| submit → cancel request | Abort not represented as guaranteed server rollback |
| async validator A → type B → response A | Discard or ignore stale advisory error |

**Evidence:** The accompanying TypeScript and browser fixtures exercise subset scenarios with an in-memory fake transport and explicit `acknowledge`. They do not validate a real distributed transaction or networking stack.
