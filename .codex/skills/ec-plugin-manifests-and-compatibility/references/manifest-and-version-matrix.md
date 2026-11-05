# Manifest and Version Matrix — Engineering Reference

> Use the destination's existing requirements and evidence format; named workflow documents are examples, not prerequisites. Optional SDD mapping is configured outside this skill.

## Version axes that must not be conflated

| Axis | Question answered | Decision authority | Consequence of mismatch |
|---|---|---|---|
| Manifest schema revision | Can Ec parse and validate the plugin's metadata? | Approved project workflow and Ec schema registry | Unsupported/invalid manifest; no promotion to catalog-compatible |
| Plugin release version | Which immutable release is being considered? | Artifact identity/release record, with project workflow pin policy | Different release; must reassess all dependent evidence |
| Host API version | Which host functions can the plugin call? | Approved host public API contract | Incompatible host API; not a trust judgement |
| Extension-point contract version | Can a contribution implement a specific extension point? | Host extension registry and conformance tests | Contract incompatibility even if host API range matches |
| Dependency-provider version | Can required plugin/capability be provided by an actual candidate? | Constraint resolver and frozen candidate inventory | Missing/unsatisfiable graph |
| Build metadata and artifact digest | Which release bytes are being evaluated? | Artifact registry and lifecycle trust subsystem | Distinct release identity may share SemVer precedence |

An API version bump does not automatically imply a manifest schema bump. A declaration of a capability is an *offer*, not proof that the implementation meets the contract or is safe. A manifest may request permissions but cannot grant them.

## Example-only YAML (NOT a published Ec schema)

The following field names and version ranges are **illustrative placeholders** and must not be treated as Ec-supported fields without approved project contract and runnable schema tests.

```yaml
apiVersion: ec.example/illustrative-v1
kind: PluginManifestExample
metadata:
  pluginId: acme.analysis
  publisher: acme
  releaseVersion: 2.4.1
spec:
  hostApiRange: ">=1.4.0,<2.0.0"
  manifestSchemaRevision: 1
  contributions:
    - extensionPoint: ec.pipeline.step
      contributionId: acme.analysis/lint
      contractRange: ">=1.0.0,<2.0.0"
      configurationSchemaRef: acme.analysis/lint-schema-v2
  requires:
    - pluginId: acme.shared
      versionRange: ">=1.2.0,<2.0.0"
      optional: false
  requestedPermissions:
    - pipeline.read
```

Do **not** infer that this YAML enables a plugin, installs software, proves a digest, executes code, or adds authentication credentials. Actual desired-state `Plugin` resources and operational plugin records must be specified independently through approved project contract. All declarative configuration **files** for Ec must use canonical YAML; foreign package formats, API payloads and packaging-integrity metadata do not become alternative Ec configuration files.

## Version parsing and comparisons

1. Use the SemVer 2.0.0 precedence rules only for versions that actually follow SemVer. Require normalized `MAJOR.MINOR.PATCH` for the illustrative test model; the actual accepted grammar is a decision for the Ec project workflow.
2. Compare numeric identifiers numerically, not lexicographically (`2.0.0` follows `1.12.0`).
3. A prerelease precedes its release (`2.0.0-rc.1` precedes `2.0.0`). Numeric prerelease identifiers compare numerically and precede alphanumeric identifiers.
4. Build metadata (`+build.7`) does not influence **version precedence**, but remains part of distinct artifact/release identity. Never collapse two different artifact digests merely because their versions sort equally.
5. An upper/lower bound grammar is **illustrative**; its semantics must be separately versioned and specified in the project workflow. Do not silently import Cargo, npm or OSGi requirement grammar; those implementations differ.
6. A prerelease should not be admitted silently in the illustrative resolver. Whether explicit prerelease ranges or tags are accepted is a product/project workflow policy.
7. SemVer `0.y.z` gives no stability guarantee. Treat code compatibility as **unverified** until contract and behavioral tests demonstrate it.
8. An open-ended range may produce future incompatibility. Require explicit impact analysis when a host or extension API evolves.

## Evolution method

- Before releasing a new host version, inventory all supported plugin contract versions and the affected extension points. Classify removed/renamed types, changed error payloads, capability requirements, and permission semantics.
- For each concurrently supported version, test positive and negative manifest parsing, typed input/output, unknown-field retention, conversion and clear deprecation diagnostics.
- If a converter is required, verify that its mapping is semantics-preserving for supported fields and does not silently remove extensions; a conversion failure is not a compatible result.
- Keep `proposed`, `published`, `observed`, `compatible` and `active` statuses distinct in the catalog. A successful metadata validation is never an installation receipt.

**Grounding:** SemVer 2.0.0, Cargo Book resolver behavior, Kubernetes CRD multi-version conversion, the OSGi requirement/capability model, and the Raemaekers et al. empirical compatibility study. See [sources and decisions](sources-and-decisions.md).
