# Capability Constraints, Dependency Resolution and Tests

> Use the destination's existing requirements and evidence format; named workflow documents are examples, not prerequisites. Optional SDD mapping is configured outside this skill.

## Resolver inputs and trust boundary

Input: a frozen candidate inventory with immutable plugin IDs/releases; the host API/extension registry and its versions; parsed, typed plugin manifests; the requested roots and approved constraints. Do not download, run, sign or grant permissions to plugins during compatibility analysis.

**Matching rules**

1. A requirement must target an existing registered extension-point namespace. Compare its required contract with the exact version provided by the host; compare a contribution's advertised contract with the host's accepted contract. A contribution ID must be qualified under the plugin/publisher namespace.
2. A required dependency needs a selected plugin release satisfying **all incoming constraints** from the same candidate graph. Optional dependencies are not blockers in the illustrative model, but the desired activation semantics and feature fallback are project workflow decisions.
3. Consider conflicts, dependencies on capabilities, missing providers, mutually exclusive features and cardinality limits if included in the approved Ec contract. **Never** assume two providers of a single-valued extension can be enabled together.
4. Version constraints are satisfiability claims, not authorization or proof. Reuse an established tested dependency solver when the graph is nontrivial; incomplete greedy heuristics must report limits rather than falsely reporting incompatibility.
5. If an explicit search budget is exceeded, return `unknown/budget-exceeded` with candidate/version diagnostics. An unknown is not the same as `incompatible`.
6. Resolve against the planned **entire** set of roots, not one manifest at a time. If simultaneous resolutions are impossible, produce a compact conflict trace; do not silently overwrite the user's pinned versions.

### Illustrative conflict

`ui-linter` requires `shared >=1.0.0,<2.0.0`; `supply-audit` requires `shared >=2.0.0,<3.0.0`. If the approved environment allows only one `shared` release, the combined selection is unsatisfiable. A catalog must show both constraints and the conflicting shared dependency; it must not blame network connectivity or permission failure.

### Lower-version rescue

A dependency offers `3.0.0` and `2.5.0`. The first breaks the host contract while `2.5.0` satisfies it. Select a satisfying candidate under the approved policy; an algorithm that only tries `latest` is insufficient. Do not infer a preferred version if multiple non-equivalent artifacts carry equal SemVer precedence.

## Contract conformance matrix

| Verification | Positive case | Negative or boundary case |
|---|---|---|
| Manifest schema | Known revision and typed required fields | Unknown revision, malformed version, ambiguous/unsafe YAML |
| Host API | Candidate in accepted range | Too old, too new, prerelease not explicitly allowed |
| Extension contract | Input/output and lifecycle match | Removed field, changed type/error, invalid contribution name |
| Dependency graph | Complete mandatory graph | Missing provider, diamond conflict, stale pin, bounded-search exhaustion |
| Capabilities | Registered point and namespaced contribution | Core namespace override, duplicate contribution, unsupported host point |
| Permission requests | Known request labels are displayed as requests | Unknown permission is blocked; known label alone never yields grant |
| Schema evolution | Additive compatible change with tests | Unknown fields dropped silently; renamed field without migration |
| Runtime evidence | Conformance tested using actual host/plugin artifacts | Metadata-compatible release with no real execution verification |
| Catalog presentation | Distinct metadata and trust/activation status | “Ready and safe” based only on parsed YAML |

## Implementation evidence gate

- Unit: parsing/range comparators, duplicate detection, deterministic reasons and non-mutating checks.
- Property/metamorphic: order-independent candidate resolution, stable version precedence, monotonic narrowing of requirements, opaque-field round-trip for supported extension contracts.
- Integration: host/plugin ABI or wire-contract conformance across supported versions, Rust host boundaries, Vue catalog/API, real Git YAML publication, permission enforcement and staging sandbox — **only when a real Ec repository and test environment exist**.
- Adversarial: forged capabilities, publisher collisions, pre-release ambiguity, permissive upper bounds, double providers, version-confusion, intentionally malformed YAML and attempted privilege escalation.
- Regression: pin the host, manifest, schema and extension contract versions as immutable fixtures; record both admitted and rejected examples.

**Research basis:** OSGi Core 8 requirement/capability resolver; Cargo's global dependency graph; Tucker et al. (ICSE 2007) SAT-based packages; Abate et al. (JSS 2012) complexity and separation; Batory (SPLC 2005) cross-feature constraints. These justify constraint-based design, **not** a claim that the illustrative reference resolver is complete for arbitrary Ec plugin ecosystems.
