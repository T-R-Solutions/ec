# Detailed decisions and failure cases

Read only for a decision needing more detail than the entry point. Apply the relevant steps to the
actual task; the sections are not a mandatory artifact or test checklist. Use existing project
contracts in their actual format. File names such as spec.md, plan.md or tasks.md are examples, not
required dependencies. Other skills are optional expertise, never enforcement mechanisms.

Product rules, stack choices and authority locations must be verified in the Ec consumer. Examples
do not establish deployed capabilities or override its current contract.

## Implementation method

1. **Discover the actual authority and trust boundaries.** Read approved Ec project
   specificationifications, the real host/agent code, runtime and deployment platform,
   manifest/extension contracts, YAML schemas, publication pipeline and authorization contracts.
   Identify artifact producer, repository, signer, verifier, operator, control plane, runtime
   worker, secret broker and external destinations. **No Ec runtime, signing system or API may be
   presumed implemented.** If isolation, trust roots or acceptable plugin forms are undecided,
   record a decision gate rather than enabling untrusted code.
2. **Define the threat model and admissible runtime.** Enumerate malicious publisher, compromised
   registry/signing key, confused deputy, dependency substitution, stale controller, sandbox escape,
   malicious host calls, overconsumption, tenant crossing and privilege escalation. Compare a
   capability-limited WebAssembly guest, separately sandboxed native process/container and VM
   isolation against the concrete threat model; document every exposed host function, trust boundary
   and residual risk. In-process native dynamically linked libraries are **not** an isolation
   boundary. Do not rely on seccomp or a WASM memory limit alone.
3. **Pin and verify bytes before execution.** Resolve one immutable artifact digest and its
   manifest/dependency identities; verify downloaded bytes against an **authenticated** expected
   digest, signer/issuer identity against an independently configured trust root, provenance
   subject/source/builder expectations, metadata freshness/revocation and approved compatibility
   results. A bare hash, signature presence, producer-supplied trust policy or catalog label is
   insufficient. Quarantine unknown, stale, conflicting or unverifiable evidence; reject unsafe
   archives before any extraction or installation hook. Capture verifier version and immutable
   evidence references.
4. **Bind grants and capabilities independently.** Compare requested capabilities with
   scope-specific *effective grants* and deny undeclared/unapproved operations. Bind grants to
   plugin ID, artifact digest, scope, policy revision and runtime generation where appropriate.
   Expose only host-mediated operations; scope filesystem, network, secrets and actions explicitly,
   with runtime checks and audit. Revocation must prevent **new** host calls without waiting for a
   plugin restart. Never embed secrets or secret values in YAML or plugin logs.
5. **Separate desired config from operational state.** Configure enablement, exact artifact pin,
   scope and requested permission policy in Ec's versioned **canonical YAML** through
   `ec-configuration-publication`; Visual, Form and YAML are semantic projections. Persist
   downloads, verification receipts, runtime attempts, failures, health, audit, restarts and
   observed state in operational storage. Model `requested → verifying → staged → starting → active`
   and failure/quarantine/stop states as **illustrative operational transitions**, not an approved
   Ec enum or independent source of desired truth. Never display an unconfirmed activation as
   success.
6. **Stage, activate and reconcile with fencing.** Do non-executing verification and dependency
   checks first. Validate budget and sandbox controls *before* start. Recheck desired revision,
   artifact digest, signer/trust policy, compatibility and grants at the activation boundary. Use
   idempotent operation IDs, optimistic revision checks and a monotonically advancing runtime
   generation so stale starts, health messages and host calls cannot revive retired code. Treat
   process startup, readiness and safety authorization as separate observations. After
   crash/restart, recover from durable authoritative state; unknown is not active.
7. **Control changes, removal and rollback.** Explain impact on dependents, data, secrets and
   long-running work. On disable/revocation, deny new calls immediately, drain/stop with deadlines,
   revoke credentials, verify termination, then remove resources; inability to confirm termination
   yields `stopping`/`unknown`, never `stopped`. Upgrade with immutable target and explicit
   transition policy; rollback is a **new authorized change** to a previously verified artifact,
   with current trust/grants/compatibility checked again. Quarantine compromised digests
   irrespective of SemVer. Keep durable audit and incident evidence.
8. **Verify boundaries and failures, not only happy paths.** Test tampered artifact,
   trusted-signer-but-wrong-source, unsigned/expired/revoked metadata, dependency poisoning, archive
   traversal/symlinks/resource bombs, ungranted host calls, secret leakage, runtime escape attempts,
   CPU/memory/I/O exhaustion, stale generations, duplicate commands, interrupted activation,
   concurrent updates, failed cleanup, unavailable verifiers and rollback to revoked code. Require
   actual host/container/WASM/VM integration, policy enforcement, chaos, accessibility and
   adversarial tests on the real Ec stack before claiming isolation; illustrative models are
   **synthetic tests only**.


## Further references by decision

Read only the reference relevant to the unresolved decision. Research registers describe imported authorship, not current execution.

- [Artifact Verification, Provenance and Trust — On-Demand Method](artifact-verification-and-trust.md): artifact verification and trust.
- [Isolation and Runtime Capability Enforcement — On-Demand Method](isolation-and-capability-enforcement.md): isolation and capability enforcement.
- [Lifecycle, Recovery and Truthful Plugin Console](lifecycle-and-reconciliation.md): lifecycle and reconciliation.
- [Skill Routing and Acceptance Evaluation Oracles](routing-and-test-oracles.md): routing and test oracles.
- [Auditable Primary-Source and Scientific Evidence Register](sources-and-decisions.md): source provenance or a disputed recommendation.
