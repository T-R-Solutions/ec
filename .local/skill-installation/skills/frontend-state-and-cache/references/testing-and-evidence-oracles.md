# Tests, concurrency schedules and evidence oracles

> Use the destination's existing requirements and evidence format; named workflow documents are examples, not prerequisites. Optional SDD mapping is configured outside this skill. Ec-specific rules below apply only to an Ec consumer whose current contract establishes them. Commands, fixture paths and historical execution claims from the source package are illustrative or imported evidence unless the corresponding files are present and independently checked; they are not current test results or bundled executable tools.

## Coverage categories

| Type | Representative probe | Observable oracle |
|---|---|---|
| Positive | two readers of same key | deduplication and identical scoped data |
| Negative | authenticated user A, then user B | no A data or private hydration in B scope |
| Boundary | TTL exactly reached | documented freshness boundary honored |
| Race | request A, invalidate, B, B finishes, then A | B remains visible; A marked ignored |
| Late cleanup | logout while fetch in flight | protected entry removed; late reply fenced |
| Read failure | transient transport error with old value | stale old value + error; never false fresh |
| Mutation | reversible proposal denied | rollback + invalidation; no saved claim |
| High-risk action | attempt optimism without explicit policy | blocked, pending authoritative confirmation |
| Stream | old/duplicate, missing event ID | no revision regression; gap revalidation |
| SSR | two simultaneous request principals | isolated cache instances/hydration |
| Resources | unobserve then idle expiry | eviction bounded and clock-controlled |
| Security | secrets in query metadata | key rejected/omitted; backend remains authority |

Use deterministic deferred promises and injected clocks for concurrency. Prefer server integration and browser tests to assumptions about framework behavior, but keep fake server responses clearly labeled as fixtures. Add optional high-volume/property-based testing only after explicit invariants exist; `property-based-testing` specializes those techniques.

## Evidence contract

For each REQ/AC: record authoritative spec revision, fixture inputs, code commit, client/Node/compiler/browser/API versions, commands, expected/observed states, scope identity (without secrets), outcome (`APROVADO`, `REPROVADO`, `NÃO VERIFICADO`, `INCONCLUSIVO`, `NÃO APLICÁVEL`), evidence link and owner. A script that prints `PASS` cannot approve an untested backend behavior.

This package includes one strict TypeScript model, Node test cases, a Chromium DOM fixture loading the actual compiled model, and controlled mutants. It **does not** include a real Vue component, an project workflow feature implementation, a live Ec server, a network fault injection cluster or an agent-routing benchmark.

Reference: [TanStack Vue query cancellation](https://tanstack.com/query/latest/docs/framework/vue/guides/query-cancellation); [RFC 9111](https://www.rfc-editor.org/rfc/rfc9111.html).
