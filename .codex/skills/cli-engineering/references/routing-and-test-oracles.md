# Skill routing, counterexamples and oracles

## Positive routing

1. Build an administrative CLI `help`/`--version` command tree ⇒ cli-engineering is primary.
2. Add safe noninteractive `--yes` and `--dry-run` semantics ⇒ cli-engineering primary; use API spec for actual authorization.
3. Fix terminal control injection or corrupted machine-readable output ⇒ cli-engineering primary; application-security secondary as justified.
4. Add an operation-status polling contract that follows a returned ID ⇒ cli-engineering, with http-api-contracts when the wire contract itself changes.

## Negative routing

1. Change RBAC roles without CLI behavior ⇒ ec-effective-permissions.
2. Define YAML resource schemas ⇒ ec-declarative-resource-schemas.
3. Implement OIDC federation session validation ⇒ identity-federation.
4. Write a shell script for internal single-use CI ⇒ not necessarily a new public CLI.
5. Design API endpoint pagination ⇒ http-api-contracts, not cli-engineering.

## Boundary routing

"Create an `ec admins delete` CLI command" without product spec: identify as spec gap, never infer the command exists. "Review the CLI UX of a documented command without changing the backend": activate cli-engineering, mark server behavior as supplied/not validated. "Recommend a CLI framework": activate when it materially affects grammar/testability; otherwise software-architecture may suffice.

## Oracles

- Help/version work offline and return correct exit.
- Invalid flags rejected; no ambiguous abbreviation.
- Data on stdout; errors on stderr; JSON on stdout remains parseable under piping.
- Accepted request != completed backend action; preview has no mutation.
- Noninteractive high-risk operation without confirmation fails under approved policy.
- Secret fields redacted; untrusted ANSI not interpreted.
- Identical idempotency replay resolves same ID; incompatible replay rejects.
- Stale revision and cross-tenant lookups reject; concurrent SQLite toy test demonstrates only fixture behavior.
- Every observed test must be tagged as mock, local transactional, staging or production and tied to an acceptance criteria.
