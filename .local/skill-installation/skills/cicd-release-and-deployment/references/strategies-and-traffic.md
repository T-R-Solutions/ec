# Deployment strategies and traffic semantics

> Ec-specific rules below apply only to an Ec consumer whose current contract establishes them.

- **Rolling:** gradually replace instances, constrained by maxUnavailable/maxSurge and readiness/availability conditions. In Kubernetes, both zero is invalid. Progress deadline reports a stalled rollout; it does not itself mean recovery succeeded.
- **Canary:** evaluate a selected subset for a bounded phase before promotion. Weighted routing via ingress/service mesh provides explicit request traffic approximation; without traffic routing, weights derived from replica proportions are **not equivalent** to exact request ratios.
- **Blue/green:** provision candidate alongside active and switch routing after required readiness/analysis/approval. Switching services can be fast but does not guarantee zero errors or reversible DB changes. Readiness alone is not global correctness.
- **Recreate:** terminate old workload before starting new; downtime is expected unless mitigated elsewhere.
- **A/B, dark launch and shadow:** require separate isolation of traffic, feature data, side effects and analysis. They are not synonyms for canary or blue/green.

Provider capability matrix must state: weighted routing?, metrics source?, rollback?, atomic switch?, how long previous revision retained?, availability budget?, data migration compatibility?, audit/approval support? No generic strategy is automatically supported by all providers.

Examples from Argo Rollouts, Flux Flagger and Kubernetes inform patterns, but do not impose their CRDs or file formats on Ec.
