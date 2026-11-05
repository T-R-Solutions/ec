# Scientific Evidence: What Transfers and What Does Not

> Imported authoring record. Research dates, consultations and execution claims below belong to the supplied source package; they were not reproduced in this adaptation. Referenced validation scripts, fixtures, product norms and logs are not bundled unless present in this folder. Verify their availability and provenance before use. No historical PASS is current acceptance.

| Study and DOI | Supported observation | Engineering implication | Explicit transfer limit |
|---|---|---|---|
| Welsh et al., SOSP 2001, 10.1145/502034.502057 | Explicit queues and load conditioning can protect staged event services under overload in the evaluated architectures | Define bounds and policies for slow-client queues; test burst and sustained load | No measured claim that a browser EventSource/UI component will have a particular throughput |
| Akidau et al., PVLDB 2013, 10.14778/2536222.2536229 | Durable records/processing and specialized idempotency logic support the documented MillWheel semantics | Model replay tokens and idempotence only with service contracts and persistence | SSE/WS do **not** inherit MillWheel semantics; avoid “exactly once” UI claims |
| Akidau et al., PVLDB 2015, 10.14778/2824032.2824076 | Unbounded/out-of-order streams require explicit choices regarding correctness, latency and cost | Distinguish durable critical events from optional ephemeral progress; use snapshot reconciliation | Dataflow's framework, triggers and windows are not frontend state APIs |
| Dean & Barroso, CACM 2013, 10.1145/2408776.2408794 | Tail latency can dominate perceived responsiveness in large distributed services | Track last-event freshness, reconnect p95/p99 and causal error context if approved | No transferrable default `p99` target for a new service; measure an actual representative environment |

A peer-reviewed study can support choosing a **method** without proving that a specific implementation passes acceptance. No quantitative assertion from these studies is adopted as a benchmark for the destination project. A new transport comparison requires its own reproducible conditions, identical workloads, memory ceilings, network disruptions, and traceable code revision.

**Evidence gate:** if a task asks to prove a frontend component is free of event loss, the answer is not “the study proves it.” Specify server retention and replay contract, run fault injection with a real backend and log exact misses/duplicates. If that environment is absent, mark the claim NOT VERIFIED.

The published studies are **not a benchmark** of the browser fixture or of the Ec application.
