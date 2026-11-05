# Deployment security and supply chain

The target should verify release provenance against trusted policy, artifact digest, environment-specific authorization, and permitted credential scopes. CI pipeline success does not automatically establish artifact trust. Signatures and attestations must be cryptographically verified with trusted identities, not simply present. SLSA provenance is one useful schema; it does not guarantee security by itself.

Deploy credentials/secrets only become available to trusted authorized execution context after gates; do not log tokens or inject production secrets into untrusted pull-request workloads. Enforce target/actor authorization backend-side; UI visibility is not authorization. Protect against replay, cross-tenant access, self-approval escalation, stale receipts and simultaneous deployments.

Require audit evidence of requester/approver, release digest, code/config revisions, policy revision, target and result. Do not claim compliance, zero-downtime or guarantees of exactly-once external effects from a conceptual flow.
