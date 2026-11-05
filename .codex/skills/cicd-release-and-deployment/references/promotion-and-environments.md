# Promotion and environments

A release is an immutable candidate; a deployment is an **attempt** to realize that candidate in a particular environment. Separate `release_id`, `deployment_id`, tenant/project, target environment, requested/observed timestamps, actor, target generation and immutable artifact/config revisions. Each promotion creates a new attempt and approval evaluation; previous environments are not rewritten by new target state.

Define environment names/scopes and identity explicitly (a string `production` alone is insufficient across tenants). Require environment creation/administration from the owning product-domain skill. Promotion gates should incorporate artifact trust, upstream evidence as defined by policy (e.g. successful staging), target-specific security and change windows. Skipping an upstream environment is a policy decision, not automatically a security flaw; the policy must state what is required.

Operational state must distinguish submitted, acknowledged, applied, healthy and verified complete. Query target control plane to reconcile uncertain receipts. Reusing the same idempotency key with different artifact/target fingerprints is a conflict.

Test: tenant isolation, new attempt on promotion, duplicate command deduplication, mutable tag, stale baseline, replaced configuration revision and unsupported target.
