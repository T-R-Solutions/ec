# Detailed decisions and failure cases

Read only for a decision needing more detail than the entry point. Apply the relevant steps to the
actual task; the sections are not a mandatory artifact or test checklist. Use existing project
contracts in their actual format. File names such as spec.md, plan.md or tasks.md are examples, not
required dependencies. Other skills are optional expertise, never enforcement mechanisms.

Ec references below are conditional consumer examples. They impose no Ec documents, YAML format,
stack or product policy on a generic task.

## Procedure

1. **Establish authorities.** Split *local view state* (selection, inspector, transient
   interactions), *shared local UI state* (client preferences), *remote derived state* (cached
   server observations), and *draft mutations*. Do not use an application cache as the authoritative
   server data store or configuration source. If Ec is the target, consult its norms and YAML
   specialists before proposing persistent configuration changes.
   [Ownership](state-ownership-and-projections.md).
2. **Define query identity and security scope.** Make keys deterministic and include the full
   data-shaping variables, authenticated tenant/principal/context and authorization/session epoch as
   appropriate. Never embed tokens/passwords in keys, URLs, persisted caches, telemetry or logs.
   Verify isolation and clean up on logout, role change, tenant switch or SSR request disposal.
   Cache namespaces are an isolation aid, **not backend authorization**.
   [Keys](query-identity-and-isolation.md), [Security](security-privacy-and-http-cache.md).
3. **Specify the freshness contract.** Separate *has data*, *is fetching*, *is fresh*, *is stale*,
   *is offline*, *has error*, and *was accepted* (not necessarily effective). Name the authorized
   source of each fact, age unit and scope. Configure `staleTime`, refetch/reconnect, invalidation
   and cache eviction explicitly; avoid undocumented library defaults. Do not claim that a TTL
   prevents data from changing at the origin. [Freshness](cache-freshness-and-invalidation.md).
4. **Handle concurrent reads deterministically.** Deduplicate equivalent in-flight queries, but let
   new forced reads supersede old ones. Use AbortSignal when supported **and** generation/identity
   fencing because cancellation may not stop completion. Keep previous data during permitted
   background revalidation while displaying staleness. A late reply from a prior scope must never
   repopulate the current scope. [Concurrency](concurrency-cancellation-and-fencing.md).
5. **Model mutation and reconciliation.** Document whether a change is reversible and permitted to
   render speculatively. For high-risk/admin/deployment/configuration actions, prefer pending
   confirmation rather than false optimistic success. For explicit low-risk optimism, snapshot
   previous data, correlate operation ID, detect overlapping edits, restore or refetch on rejection,
   and require authoritative acknowledgements. Keep `accepted`, `persisted`, `published` and
   `effective` distinct when the product uses them. [Mutations](mutation-and-optimistic-safety.md),
   [ACKs](server-ack-conflict-and-revisions.md).
6. **Integrate push events conservatively.** Only apply events directly when the authoritative
   contract defines identities, trust validation, monotonic revisions and possibly contiguous
   sequences. Discard old events; on gaps, expired replay cursors or ambiguous event ordering, mark
   stale and revalidate. Events and WebSocket connections are **observations**, not automatic proof
   of effective operation. [Realtime](realtime-offline-and-resumption.md).
7. **Plan lifecycle and failure behavior.** Dispose subscriptions, bound memory use, distinguish
   `staleTime` from inactivity garbage collection, isolate SSR-request state and hydrate only
   sanitized per-principal data. On offline/background failure, retain allowed stale view with an
   explicit error; on logout/permission change clear private caches, purge persistence and fence
   in-flight work. [Lifecycle](lifecycle-memory-and-ssr.md).
8. **Adapt libraries only after defining invariants.** Vue reactivity/Pinia address shared local
   state; query libraries such as TanStack Query address server-state synchronization; neither
   replaces server policies. Inspect actual installed library version, especially v4 `cacheTime` vs
   v5 `gcTime`, and document any non-default behavior. Use project-approved dependencies, not a
   presumptive package choice. [Adapters](vue-pinia-query-adapters.md).
9. **Build acceptance oracles.** Cover positive, negative, boundary and interleaved sequences: query
   identity, cross-user leaks, logout while requests are in flight, stale responses, offline/error
   recovery, revision regressions, conflicting optimistic updates, memory lifecycle, SSR scope,
   reconnection and keyboard announcements of status. Validate with a real browser/runtime and real
   backend contract where available. [Testing](testing-and-evidence-oracles.md),
   [Science](scientific-evidence-and-limits.md).
10. **Report evidence accurately.** Provide ownership table, key schema, lifecycle/state diagram,
    invalidation matrix, concurrency rules, error/ack UI contract, REQ/AC-linked tests, commands,
    versions, test outputs, limitations, and unverified assumptions. project workflow uses an
    optional integration is configured separately by the consumer.


## Correctness constraints

- **No phantom authority:** a cached projection, optimistic UI state or local storage entry is not
  the source of truth for published configuration, access control or server-operational state.
- **No cross-scope contamination:** authenticated user/tenant/project queries and hydration must be
  isolated, including role changes and SSR requests. Backend authorization still applies to every
  sensitive access.
- **No stale success:** a stale result, request initiation, successful client-side update or
  unverified event never proves server acceptance or effective application.
- **No cancellation fallacy:** AbortSignal is a cooperative cancellation mechanism; stale-response
  fences and revision checks are separate, testable requirements.
- **No universal TTL/retry:** freshness tolerance, retry eligibility and cache lifetimes require a
  feature contract and representative measurement. Disallow automatic retries of non-idempotent
  mutations without an explicit server contract.
- **No secret persistence:** tokens, session cookies, private material and privileged operations
  cannot be exposed via localStorage, query keys, shared caches, screenshots or logs. HTTP
  `no-store` does not alone solve client/server privacy.
- **No automatic offline editing promise:** offline writes and eventual reconciliation need an
  explicitly specified conflict, authorization and data-security policy; CRDTs or local-first
  concepts are not a default requirement.
- **No unsupported performance claim:** library defaults, paper benchmarks, synthetic cache hit
  counts and fixture-based browser checks do not establish product-level speed, security or user
  success rates.


## Further references by decision

Read only the reference relevant to the unresolved decision. Research registers describe imported authorship, not current execution.

- [Freshness, expiry and invalidation](cache-freshness-and-invalidation.md): cache freshness and invalidation.
- [Concurrent remote reads, abort and generation fencing](concurrency-cancellation-and-fencing.md): concurrency cancellation and fencing.
- [Lifecycle, bounded memory, SSR and hydration](lifecycle-memory-and-ssr.md): lifecycle memory and ssr.
- [Mutations and optimistic client rendering](mutation-and-optimistic-safety.md): mutation and optimistic safety.
- [Deterministic query identity and isolation](query-identity-and-isolation.md): query identity and isolation.
- [Realtime events, offline and resumption](realtime-offline-and-resumption.md): realtime offline and resumption.
- [Routing and selection oracles](routing-and-selection-oracles.md): routing and selection oracles.
- [Peer-reviewed scientific evidence and inference limits](scientific-evidence-and-limits.md): source provenance or a disputed recommendation.
- [Privacy, authorization and HTTP/application-cache separation](security-privacy-and-http-cache.md): security privacy and http cache.
- [Authoritative acknowledgements and version conflicts](server-ack-conflict-and-revisions.md): server ack conflict and revisions.
- [Source register — documentation and reproducible decisions](sources-and-decisions.md): source provenance or a disputed recommendation.
- [State ownership and projections](state-ownership-and-projections.md): state ownership and projections.
- [Tests, concurrency schedules and evidence oracles](testing-and-evidence-oracles.md): testing and evidence oracles.
- [Vue 3 / Pinia / TanStack Query adapter boundaries](vue-pinia-query-adapters.md): vue pinia query adapters.
