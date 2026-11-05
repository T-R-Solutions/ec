# Source register — documentation and reproducible decisions

> Imported authoring record. Research dates, consultations and execution claims below belong to the supplied source package; they were not reproduced in this adaptation. Referenced validation scripts, fixtures, product norms and logs are not bundled unless present in this folder. Verify their availability and provenance before use. No historical PASS is current acceptance.

**Verification date:** 2026-11-01. Source authority is separated: normative RFC/browser specifications; maintained library documentation; peer-reviewed research; and synthetic engineering choices. All URLs are references, not runtime dependencies or product configuration files.

## Official standards/specifications and maintained engineering documentation

| ID | Maintainer / source URL | Supports | Scope / caution |
|---|---|---|---|
| O01 | Vue: https://vuejs.org/guide/scaling-up/state-management.html | shared reactive vs component state, Pinia recommendation | Does not define server cache correctness |
| O02 | Vue: https://vuejs.org/guide/scaling-up/ssr.html | SSR state cross-request leakage | Needs verification in actual deployment |
| O03 | Pinia: https://pinia.vuejs.org/core-concepts/ | state/actions and store boundaries | Version-specific behavior |
| O04 | Pinia: https://pinia.vuejs.org/core-concepts/outside-component-usage.html | store acquisition outside setup | Requires installed instance |
| O05 | Pinia: https://pinia.vuejs.org/ssr/ | isolate SSR app state | Does not authorize data hydration |
| O06 | TanStack Query Vue v5: https://tanstack.com/query/latest/docs/framework/vue/guides/query-keys | deterministic key semantics | Library-specific, not a spec |
| O07 | TanStack Query Vue v5: https://tanstack.com/query/latest/docs/framework/vue/guides/important-defaults | staleTime, gcTime, retries | Confirm installed version/defaults |
| O08 | TanStack Query Vue v5: https://tanstack.com/query/latest/docs/framework/vue/guides/query-invalidation | targeted invalidation | Backend still authority |
| O09 | TanStack Query Vue v5: https://tanstack.com/query/latest/docs/framework/vue/guides/query-cancellation | AbortSignal and cancellation | Cancellation not proof of server rollback |
| O10 | TanStack Query Vue v5: https://tanstack.com/query/latest/docs/framework/vue/guides/optimistic-updates | snapshot and rollback | Does not mandate optimistic UI |
| O11 | TanStack Query Vue v5: https://tanstack.com/query/latest/docs/framework/vue/guides/caching | background refresh / inactive GC | Illustrative library behavior |
| O12 | TanStack Query Vue v5: https://tanstack.com/query/latest/docs/framework/vue/guides/ssr | SSR hydration/cache scope | Depends on app architecture |
| O13 | TanStack Query v5 migration: https://tanstack.com/query/latest/docs/framework/vue/guides/migrating-to-v5 | cacheTime renamed gcTime | Do not apply to v4 blindly |
| O14 | TanStack Vue queries: https://tanstack.com/query/latest/docs/framework/vue/guides/queries | pending/fetching/error state separation | Library state not server effect |
| O15 | MDN Fetch: https://developer.mozilla.org/en-US/docs/Web/API/Fetch_API/Using_Fetch | AbortController semantics | Documentation reflects web platform |
| O16 | RFC Editor: https://www.rfc-editor.org/rfc/rfc9111.html | HTTP Cache-Control, Vary, privacy, validation | Separate HTTP vs JS cache layer |
| O17 | RFC Editor: https://www.rfc-editor.org/rfc/rfc9110.html | conditional request / validator semantics | No fabricated revision ordering |
| O18 | OWASP: https://cheatsheetseries.owasp.org/cheatsheets/Session_Management_Cheat_Sheet.html | session handling threats | Normative project policies come from its owners |

## Peer-reviewed references, DOI and method boundaries

- **S01:** Cao, P. and Liu, C. (1998), *Maintaining strong cache consistency in the World Wide Web*, IEEE Transactions on Computers 47(4), 445–457. DOI `10.1109/12.675713`. Publisher/journal metadata, prototype/trace replay. https://doi.org/10.1109/12.675713
- **S02:** Terry, D. B., Demers, A. J., Petersen, K., Spreitzer, M., Theimer, M., Welch, B. B. (1994), *Session guarantees for weakly consistent replicated data*, IEEE PDIS. DOI `10.1109/PDIS.1994.331722`. Defines weakly consistent session guarantees, not Vue behavior. https://doi.org/10.1109/PDIS.1994.331722
- **S03:** Alici, S., Altingövde, I. S., Ozcan, R., Cambazoglu, B. B., Ulusoy, Ö. (2011), *Timestamp-based result cache invalidation for web search engines*, ACM SIGIR. DOI `10.1145/2009916.2010046`. Web search trace workload does not prove Ec client results. https://doi.org/10.1145/2009916.2010046
- **S04:** Bailis, P., Ghodsi, A., Hellerstein, J. M., Stoica, I. (2013), *Bolt-on causal consistency*, ACM SIGMOD, 761–772. DOI `10.1145/2463676.2465279`. Distributed storage research, not an application cache requirement. https://doi.org/10.1145/2463676.2465279
- **S05:** Kleppmann, M., Wiggins, A., van Hardenberg, P., McGranaghan, M. (2019), *Local-first software: You own your data, in spite of the cloud*, ACM Onward!. DOI `10.1145/3359591.3359737`. Proposal/prototypes and CRDT discussion; not proof of offline suitability for Ec. https://doi.org/10.1145/3359591.3359737

## Decisions (proposed techniques; not product law)

- Use query identities that contain context/filters and clear session state; because a cached record must not be silently reused across unrelated auth scopes.
- Use generation fences in addition to abort because transport cancellation may not prevent late completions.
- Preserve allowed stale data alongside a visible stale/error marker; freshness is not equivalent to a boolean `hasData`.
- Treat requests, optimistic data, ACKs and observed effective revisions as different stages.
- Avoid assuming a JS query cache is an HTTP cache or can override Cache-Control.
- Allow libraries to be swapped if tested invariants remain intact; no unmeasured library superiority claim.

**Ec integration limitation:** these are generic methods. Ec normative requirements remain in `EC-REGRAS-DE-DESIGN-E-PRODUTO.md` and `EC-REGRAS-CRIACAO-DE-TELAS-E-IMAGENS.md`; any normative conflict is recorded in SDD and resolved in favor of Ec rules rather than silently overridden.
