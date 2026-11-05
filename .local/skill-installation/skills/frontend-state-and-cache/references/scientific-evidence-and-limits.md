# Peer-reviewed scientific evidence and inference limits

> Imported authoring record. Research dates, consultations and execution claims below belong to the supplied source package; they were not reproduced in this adaptation. Referenced validation scripts, fixtures, product norms and logs are not bundled unless present in this folder. Verify their availability and provenance before use. No historical PASS is current acceptance.

The studies below were verified against publisher, institutional repository, scholarly bibliographic or author pages. They are empirical or formal research **about web cache/distributed consistency**, not performance tests of Vue, Pinia, TanStack Query, Ec, or the synthetic fixture. No universal threshold or performance improvement is inferred.

| Study | DOI / venue | Supported conclusion | Does NOT prove |
|---|---|---|---|
| Cao and Liu (1998), *Maintaining strong cache consistency in the World Wide Web* | `10.1109/12.675713`, *IEEE Transactions on Computers* 47(4):445–457 | Trace-replay/prototype comparison illustrates that invalidation and TTL can have materially different stale-data behavior and cost trade-offs | A particular frontend query library is faster, or Ec has the same workload |
| Terry et al. (1994), *Session guarantees for weakly consistent replicated data* | `10.1109/PDIS.1994.331722`, IEEE PDIS | Defines read-your-writes, monotonic reads and other session guarantees for replicated data | A browser cache alone can implement server-side guarantees |
| Alici et al. (2011), *Timestamp-based result cache invalidation for web search engines* | `10.1145/2009916.2010046`, ACM SIGIR | Evaluates invalidation using change metadata and web-search traces against TTL-related baselines | Search engine results generalize directly to an administrative SPA |
| Bailis et al. (2013), *Bolt-on causal consistency* | `10.1145/2463676.2465279`, ACM SIGMOD | Explores layering causal-consistency protections over weaker replicated data stores | Causal consistency is available in the current backend or UI |
| Kleppmann et al. (2019), *Local-first software: You own your data, in spite of the cloud* | `10.1145/3359591.3359737`, ACM Onward! | Discusses local-first, offline collaboration and CRDT tradeoffs with prototype evidence | Offline writes/conflict-free editing are appropriate or built into Ec |

## How this evidence is used

Translate consistency models into **testable requirements**, not unexplained architectural mandates. The engineering choices to partition query keys, fence stale completions and differentiate server acknowledgment are informed by established concurrency/consistency principles, official API semantics and conservative safety reasoning; the studies do not directly validate the specific source code in this package.

Research constraints: many experiments are older or use back-end/web-search traces, not contemporary Vue SPA workloads; published performance numbers cannot be transplanted. A production decision needs the application's approved contract, representative requests, latency measurements and real failure-injection tests.

**Not measured:** UX improvement, user comprehension, cross-browser compatibility, HTTP cache hit rate on Ec, throughput, production security or a verified framework comparison. See [source register](sources-and-decisions.md).

**Limitations:** reported experimental workloads and peer review establish bounded research claims only, not current frontend production outcomes.
