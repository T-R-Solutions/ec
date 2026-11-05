# Abort, rollback and recovery

`Abort` stops an ongoing rollout; `rollback` attempts to restore an earlier known-good state. They have different preconditions, effects and audit records. A provider may make abort reversible only during certain rollout phases. Do not collapse abort and rollback into a green check.

Before offering rollback, verify previous release artifact is retained and trusted, provider supports restoration, target revision is still available, configuration and schema are backward-compatible, external side effects are understood, and any destructive migration is explicitly assessed. Data already modified by a release may not be restored by deploying older binaries. When rollback is unsafe, present a controlled roll-forward/fix with an incident procedure.

Google Cloud Deploy documents a rollback as a **new rollout** of an earlier release. Kubernetes revision rollback changes only parts of the Deployment resource; it does not automatically reverse database schema or third-party effects. Argo fast-track rollback has conditional behavior and revision limits. Always test on the chosen target/version.

A rollback command response is not confirmation. Demand observed target identity and independent health/reconciliation evidence before `ROLLED_BACK`.
