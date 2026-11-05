# Detailed decisions and failure cases

Read only for a decision needing more detail than the entry point. Apply the relevant steps to the
actual task; the sections are not a mandatory artifact or test checklist. Use existing project
contracts in their actual format. File names such as spec.md, plan.md or tasks.md are examples, not
required dependencies. Other skills are optional expertise, never enforcement mechanisms.

Product rules, stack choices and authority locations must be verified in the Ec consumer. Examples
do not establish deployed capabilities or override its current contract.

## Engineering decision procedure

1. **Inspect real contracts first.** Find the approved project contract, current Ec YAML schemas,
   Rust host and agent interfaces, Vue/TypeScript extension surfaces, actual extension-point
   registry and compatibility test fixtures. If unavailable, mark names, manifests, supported
   versions and endpoints **illustrative and pending project workflow approval**. Avoid assuming
   TeamCity/JVM plugin compatibility.
2. **Separate five independent identities.** Inventory plugin ID, plugin release version, Ec
   manifest schema version, host API/extension contract versions and artifact identity/digest. Treat
   namespaced extension-point IDs and capability names as separate from permission names. Do not
   substitute one version axis for another or infer artifact trust from the manifest.
3. **Define a declarative metadata contract.** Specify (only in the approved project contract)
   stable publisher/plugin identifiers, versioned manifest schema, requested host API range,
   declared extension points and contract versions, provided/required capabilities,
   mandatory/optional dependencies, configuration schema references, requested permissions and
   human-readable catalog metadata. The Ec manifest and desired plugin configuration must use the
   **canonical YAML contract**. Normalize and validate typed data without executing manifest
   expressions. Reject ambiguous/unsafe tags, duplicate keys or undeclared security-relevant fields
   via the shared YAML-schema method; retain supported opaque extension payloads without silent
   loss.
4. **Register extension points before matching providers.** For each host-owned point, define its
   namespace, contract version, input/output types, lifecycle, cardinality, failure semantics and
   permitted side effects. Distinguish host-provided contract from plugin-provided implementation.
   Require publisher-scoped contribution IDs and reject collisions or attempts to overwrite core
   namespaces. A plugin's `provides` claim alone never creates a host extension point or grants any
   privilege.
5. **Evaluate compatibility along every axis.** Parse strict versions; apply the approved
   version-range language, including explicit prerelease policy and SemVer 0.x caveats. Check host
   API range, manifest-schema support, each extension contract, requested features, operating
   environment if declared, dependency constraints and configuration-schema compatibility. SemVer
   ranges are *claims of compatibility*, not runtime proof; require contract/behavioral tests when
   the host or plugin implementation changes. Never auto-select based on build metadata precedence.
6. **Resolve the complete dependency graph.** Evaluate mandatory, optional and transitive
   requirements against an explicitly bounded candidate set and frozen release identities. Report an
   explanation when no compatible solution exists (missing provider, incompatible range, conflicting
   providers, conflicting transitive constraints, unsupported capability, resolution budget
   exceeded). Do not mislabel a timeout as `incompatible`, and do not use a greedy latest-version
   choice if a lower compatible release may solve the graph. Delegate package acquisition and
   activation to the lifecycle skill.
7. **Keep metadata checks distinct from trust and authorization.** A syntactically valid,
   version-compatible manifest is only **metadata-compatible**. Permission requests are not
   permission grants; claimed signatures, digests and provenance are not verified evidence. Defer
   identity, integrity, isolation, platform approval and runtime activation to
   `ec-plugin-isolation-and-lifecycle` and `ec-effective-permissions`. Catalog UI must distinguish
   `invalid manifest`, `incompatible`, `unresolved`, `metadata-compatible`, `unverified`, `blocked`,
   `installed`, `active` and `failed` using authoritative observations; do not imply compatibility
   means safety.
8. **Verify with negative, migration and integration cases.** Test wrong manifest version, duplicate
   IDs, malformed/prerelease versions, incompatible extension contracts, missing mandatory
   dependency, optional missing dependency, transitive conflict, capability collision, reserved
   namespaces, unknown permission request, unsupported schema, unknown field retention,
   downgrade/upgrade, feature removal, deterministic resolution, permission escalation attempt,
   unbounded search and stale catalog observations. Run real Rust and Vue contract/integration tests
   only when the Ec repository exists. Otherwise label all bundled tests **synthetic**.


## Further references by decision

Read only the reference relevant to the unresolved decision. Research registers describe imported authorship, not current execution.

- [Capability Constraints, Dependency Resolution and Tests](constraint-resolution-and-contract-tests.md): constraint resolution and contract tests.
- [Manifest and Version Matrix — Engineering Reference](manifest-and-version-matrix.md): manifest and version matrix.
- [Activation Oracles and Acceptance Cases](routing-and-test-oracles.md): routing and test oracles.
- [Security Boundary and Plugin Catalog Presentation](security-catalog-and-ui.md): security catalog and ui.
- [Source Register, Scientific Synthesis and Open Decisions](sources-and-decisions.md): source provenance or a disputed recommendation.
