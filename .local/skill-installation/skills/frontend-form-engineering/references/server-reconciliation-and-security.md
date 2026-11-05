# Server trust, security and conflict boundaries

> Ec-specific rules below apply only to an Ec consumer whose current contract establishes them.

## Server authority

HTML5, Vue `v-model`, validators, `required`, disabled controls and client “read only” flags can be bypassed. The server must parse and validate the incoming request under size, shape, type, range, policy, authorization and revision constraints. Product authorization and schema semantics belong to the backend/spec and security-specialist skills.

OWASP's Input Validation Cheat Sheet recommends safe parsing, allowlists appropriate to a field, limits before parsing and revalidation at trust boundaries. Do not reject international text with naive denylisting; do not assume that escaping HTML or a regex alone prevents attacks. Log relevant event metadata without raw sensitive user input.

## Error handling by category

| Category | Client presentation | Do not do |
|---|---|---|
| `field_invalid` | Field correction, summary, retained values | Clear all inputs |
| `permission_denied` | Action unavailable or access explanation | Convert into “invalid field” |
| `version_conflict` | Diff/rebase/reload decision, keep edits | Auto-overwrite server version |
| `request_pending` | Pending/possible duplicate prevention | Claim stored/applied |
| `accepted/stored` | Confirm only level documented by server | Claim effective if separate reconciliation |
| `transport_failed` | Retry/cancel/reload guidance | Claim transaction rollback without evidence |
| `server_unavailable` | Operational recovery | Suggest changing a perfectly valid field |

The API contract must determine response keys, status codes, approval requirements and idempotency. This reference offers generic presentation mapping, not an HTTP status specification.

## Data minimization and injection safety

- Never display/store plaintext credentials in declarative configuration, logs, test fixtures, query strings, or error text. Use references and secure credential flows.
- Preserve unknown fields on form edits as required by the product contract; don't mass-assign untrusted extra properties to backend domain objects.
- Use text nodes for returned error strings, not raw HTML. Keep error code → localized message mappings separate from untrusted free-form values.
- If the browser aborts a request, this does not prove the backend avoided side effects. Query authoritative status/revision before claiming cancellation or reattempting dangerous operations.
- Verify backend authorization per action/resource; front-end field visibility is not a security boundary.

## Ec integration reminder

The Ec project's **YAML file** is the canonical desired configuration. A generic `draft` object or API JSON payload is a transient representation, not a competing persistent configuration format. Browser form actions affecting declarative configuration must use Ec-specific validation, version control, authorization, publication and reconciliation, with state confirmed by the server. Session/token/authentication operations are operational security flows, not declarative YAML resources.
