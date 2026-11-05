# Conditional, advanced and repeated form fields

> Ec-specific rules below apply only to an Ec consumer whose current contract establishes them.

## Distinguish hidden, disabled, read-only and absent

- **Hidden by UI condition** does not mean a field's value should be forgotten. Store or retain it according to explicit domain semantics and show impact before destructive removal.
- **Disabled controls** are usually excluded from native `FormData`, which can accidentally drop values if FormData is treated as the entire configuration snapshot. Choose patch semantics or domain snapshots intentionally.
- **Read-only** fields may remain meaningful and need a visible relationship to the underlying authority; they are not editable simply because a user can inspect them.
- **Not mounted** components lose local DOM state; draft values must outlive UI mount/unmount when the user expects to return to advanced sections.
- **Conditionally irrelevant** fields should be validated only when the domain rule says they matter; their stored values need not be deleted.

## Dependent-field decisions

Create an explicit table for every dependent group:

| Parent | Child | Show when | On hide | Validation when hidden | Persistence policy |
|---|---|---|---|---|---|
| Enable notifications | Contact address | enabled | retain unless spec says otherwise | none if disabled | contract-owned |
| Advanced mode | Retention days | enabled | retain in draft | domain-specific | contract-owned |
| Strategy | Threshold | threshold strategy | preserve or explicitly reset | only if relevant | contract-owned |

For a user-facing operation, a conditional choice can mean the child value should be cleared; where clearing is required, explain and test its effects rather than assuming preservation always.

## Repeaters and lists

Stable domain IDs are preferable to array positions for identifying each repeated row and its error. If reordering is semantically significant, preserve the list order. If not, avoid inferring execution order from the visual arrangement. When deleting an item, distinguish removing a local draft row from removing a persisted entity and protect destructive operations with the applicable product approval policy.

## Basic and advanced views

A “basic” form is a projection, not a separate schema; it may omit *visibility* but not silently overwrite advanced configuration. A single field edited in a basic form must not cause a full replacement body that lacks unknown or advanced fields. Provide a `diff` and recovery when the backend supports it. Product-specialized skills determine the canonical format and merge rules.

## Test oracles

Toggle open → edit child → toggle closed → reopen; submit with child hidden; toggle while save pending; validate hidden irrelevance; reset to baseline; reorder repeaters; delete and undo; server returns error for currently hidden child (must offer a reachable recovery path); nested group enabled under unsatisfied prerequisite. Test both mouse and keyboard.

**Distribution:** the synthetic browser fixture hides and reopens `retention` without silently erasing its value; it is a mechanical example only, not a policy for Ec.
