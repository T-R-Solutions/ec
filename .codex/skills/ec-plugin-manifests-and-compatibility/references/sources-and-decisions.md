# Source Register, Scientific Synthesis and Open Decisions

> Imported authoring record. Research dates, consultations and execution claims below belong to the supplied source package; they were not reproduced in this adaptation. Referenced validation scripts, fixtures, product norms and logs are not bundled unless present in this folder. Verify their availability and provenance before use. No historical PASS is current acceptance.

**Research date:** 2026-10-31. **Method:** inspect first-party specifications and documentation; read available full-text scientific papers or accessible sections; cross-check against independently published empirical studies; synthesize implementable decision rules. No community forums, social discussions, marketing claims or unverified benchmark assertions are used as technical evidence. This is a targeted systematic exploration, **not** a claim that every publication in the field has been located or read.

## Primary / official sources

| ID | Source, precise URL | Access actually used | Supported technical use |
|---|---|---|---|
| O01 | [SemVer 2.0.0 specification](https://semver.org/spec/v2.0.0.html) | Official specification sections on numbering, prerequisites, prerelease and build precedence | Version ordering, not runtime guarantees |
| O02 | [OSGi Core 8 Module Layer](https://docs.osgi.org/specification/osgi.core/8.0.0/framework.module.html) | Requirements/capabilities, namespaces, resolving process and metadata sections | Typed capability registry, mandatory constraints, collision concerns |
| O03 | [OSGi Core 8 Resolver Service](https://osgi.github.io/osgi/core/service.resolver.html) | Resolver description and consistency requirements | Transitive satisfaction and diagnostic distinction |
| O04 | [Cargo Book: Dependency Resolution](https://doc.rust-lang.org/cargo/reference/resolver.html) | Dependency graph, version selection, feature resolver and version guidance | Candidate closure, range ambiguity, optional features; Cargo's grammar is not Ec's |
| O05 | [Rust semver crate `VersionReq`](https://docs.rs/semver/latest/semver/struct.VersionReq.html) | Published library reference and matching rules | Rust implementation candidate only; **not** a final Ec decision |
| O06 | [Kubernetes CRD Versioning](https://kubernetes.io/docs/tasks/extend-kubernetes/custom-resources/custom-resource-definition-versioning/) | Served/storage versions, conversion semantics and caveats | Distinct schema versions, testing conversions and unknown fields |
| O07 | [Kubernetes CRD Structural Schemas](https://kubernetes.io/docs/tasks/extend-kubernetes/custom-resources/custom-resource-definitions/) | Schema validation, pruning and unknown-field behavior | Prevent silent semantic loss in Ec extension declarations |
| O08 | [GitHub Actions Metadata Syntax](https://docs.github.com/en/actions/reference/workflows-and-actions/metadata-syntax) | YAML metadata, inputs, outputs, runtime and release references | Illustrative independent example of extension metadata; no Ec schema inference |
| O09 | [OWASP CI/CD Security Cheat Sheet](https://cheatsheetseries.owasp.org/cheatsheets/CI_CD_Security_Cheat_Sheet.html) | Third-party plugins, dependency abuse, least privilege and artifacts | Manifest metadata cannot authorize installation or execution |
| O10 | [SLSA 1.2 specification](https://slsa.dev/spec/v1.2/) | Approved status, levels and provenance links | Trust evidence belongs to lifecycle/integrity subsystem |
| O11 | [SLSA 1.2 Provenance](https://slsa.dev/spec/v1.2/provenance) | Concept and provenance scope | Claimed metadata versus verifiable evidence |
| O12 | [YAML 1.2.2 specification](https://yaml.org/spec/1.2/) | Node/tag, mapping and representation sections | YAML typed parsing and safety delegated to shared schema skill |
| O13 | [Kubernetes Extension Architecture](https://kubernetes.io/docs/concepts/extend-kubernetes/) | Extension points, distinction between API and binary plugin, host boundaries | Architectural analogies without assuming Kubernetes execution model |
| O14 | [WCAG 2.2](https://www.w3.org/TR/WCAG22/) | Normative success criteria reference | Catalog labels, status, keyboard, focus and accessible feedback; real audit pending |
| O15 | [OpenSSF Scorecard](https://github.com/ossf/scorecard) | Official project documentation and checks | Supply-chain evidence pointers; not a substitute for verification |

**Official-source caution:** OSGi's Java/OSGi wire model and Cargo's `Cargo.toml` are **foreign implementation references**. Ec does not adopt those manifest formats or Java VM plugin compatibility. GitHub's `action.yml` is another product's format, not a proposed Ec schema.

## Peer-reviewed articles and academic contributions

| ID | Publication and canonical URL | Access depth on 2026-10-31 | Narrow supported conclusion / limitation |
|---|---|---|---|
| P01 | Raemaekers, S., Van Deursen, A., & Visser, J. (2014). *Semantic Versioning versus Breaking Changes: A Study of the Maven Repository*. SCAM/IEEE. [Author institutional full PDF](https://repository.tudelft.nl/file/File_69eb5c49-e7a8-4dcc-b087-719f850bec45) | Full-text PDF obtained; introduction, methods, results and threats-to-validity sections inspected | Version labels alone are weak empirical compatibility evidence. Maven study may not generalize quantitatively to Rust/Ec. |
| P02 | Tucker, C., Shuffelton, D., Jhala, R., & Lerner, S. (2007). *OPIUM: Optimal Package Install/Uninstall Manager*. ICSE/IEEE. [Author-hosted full PDF](https://www.cs.cornell.edu/~lerner/papers/opium.pdf) | Full-text PDF retrieved; introduction, constraint encoding, resolution and evaluation sections inspected | SAT/PB/ILP approaches can improve dependency solution completeness under stated package models; not an Ec benchmark. |
| P03 | Batory, D. (2005). *Feature Models, Grammars, and Propositional Formulas*. SPLC/Springer. [Full PDF](https://www.cse.chalmers.se/research/hats/sites/default/files/BatoryFeatureModels.pdf) | Full-text PDF retrieved; feature model, propositional encoding and conclusions examined | Boolean feature/capability constraints can be checked systematically; specific Ec capability vocabulary remains undecided. |
| P04 | Abate, P., Di Cosmo, R., Treinen, R., & Zacchiroli, S. (2012). *Dependency Solving: A Separate Concern in Component Evolution Management*. Journal of Systems and Software 85(10). [Publisher](https://www.sciencedirect.com/science/article/pii/S0164121212000477) | Abstract, introduction and selected HTML section extracts accessible; full journal article was **not independently verified** | Global dependency solving can become computationally hard; separate resolver method from upgrade lifecycle. |
| P05 | Keshani, M., Vos, S., & Proksch, S. (2023). *On the Relation of Method Popularity to Breaking Changes in the Maven Ecosystem*. Journal of Systems and Software 203. [Institutional record](https://research.tudelft.nl/en/publications/on-the-relation-of-method-popularity-to-breaking-changes-in-the-m/) | Abstract and bibliographic metadata; full publisher PDF retrieval returned access-denied (403) | Further evidence of SemVer violations; do not claim full article review. |
| P06 | Zerouali, A. et al. (2019). *A Formal Framework for Measuring Technical Lag in Component Repositories—and Its Application to npm*. Journal of Software: Evolution and Process. [Publisher](https://onlinelibrary.wiley.com/doi/10.1002/smr.2157) | Abstract available; full text **not verified** | Trade-off between stale pins and risky automatic updates; no transfer of npm quantitative results to Ec. |
| P07 | *How Java APIs Break—An Empirical Study* (2015). Information and Software Technology 65. [Publisher](https://www.sciencedirect.com/science/article/abs/pii/S0950584915000506) | Abstract, structured method/results and publisher-exposed excerpts only | API compatibility needs more than version assertions; Java-specific ABI evidence does not establish Rust behavior. |
| P08 | Di Lauro, F., Serbout, S., & Pautasso, C. (2021). *Towards Large-scale Empirical Assessment of Web APIs Evolution*. ICWE/Springer. [Author site](https://design.inf.unisi.ch/publications/2021/icwe/api-evolution) | Publication record and abstract; linked PDF **not fully examined** | Versioned APIs evolve; must test actual transformations and consumer contracts. |

## Authoritative technical book

| ID | Work | Access depth | Use and limitation |
|---|---|---|---|
| B01 | Winters, T. *Dependency Management*. In *Software Engineering at Google* (O'Reilly, 2020), [author-hosted online chapter](https://abseil.io/resources/swe-book/html/ch21.html) | Full publicly accessible chapter, inspected sections on diamond dependencies, compatibility and testing | Maintainability and constrained dependency upgrades; practitioner synthesis, not peer-reviewed empirical proof |

## Decisions derived from multiple independent sources

| ID | Synthesized method | Basis | Important boundary |
|---|---|---|---|
| D01 | Track distinct schema, host API, extension contract, dependency and plugin release axes | O01–O08, P01, P07 | Actual fields and supported ranges belong to approved SDD |
| D02 | Resolve constraints over a frozen dependency graph, not each plugin independently | O02–O05, P02–P04, B01 | Solver choice and search budget need SDD plan and benchmarking |
| D03 | Treat version claims as eligibility filters, not proof of binary behavior | O01, P01, P05, P07, B01 | Real Ec host/plugin conformance tests pending |
| D04 | Separate plugin metadata validation, requested permissions, artifact trust and installation state | O09–O11, O15 and both Ec norms | Installation/signing/sandbox belong to sibling skill |
| D05 | Preserve supported extension payloads and make incompatible conversions explicit | O06–O07, O12 and Ec norm §§14–16 | Schema authority remains in `ec-declarative-resource-schemas` |
| D06 | Produce explainable catalog diagnostics and distinguish unknown/absent evidence | O14 and both Ec norms | User interface tests with keyboard and screen reader pending |

## Decisions that require SDD approval before production implementation

1. Final `PluginManifest` YAML `apiVersion`, resource `kind`, stable plugin/publisher namespace and resource identifiers.
2. Manifest schema compatibility window, deprecation schedule and conversion strategy.
3. Host API compatibility policy; contract-version range grammar; handling of SemVer 0.x, prerelease, build metadata and multiple release digests per version.
4. Registry of host extension points, their required/optional features, provider cardinality, conflicting capabilities, reserved identifiers, and whether ABI or process-isolated wire calls are used.
5. Version solver algorithm, upper bounds and backtracking budget; optional dependencies and circular dependency policy; multiple versions of the same plugin in one host.
6. Supported configuration extension namespaces, behavior on unknown fields and plugin-provided schema evolution.
7. Complete permission request vocabulary, approval and execution authorization rules (outside this skill).
8. Integrity/provenance validation requirements and gates before installation (owned by next skill).
9. Catalog labels, observed status API sources, accessible interactions and migrations across host/plugin versions.

**Limits and negative findings:** Ec's real Rust/TypeScript source, live manifests, accepted schemas, plugin runtime, signing root and approval records were not supplied. A literature survey cannot prove that any mechanism is implemented. The reference model cannot certify dependency solver completeness for arbitrary metadata or security against adversarial binaries. No forums or unsupported numerical performance claims were used.

**Normative documents actually consulted:** `EC-REGRAS-DE-DESIGN-E-PRODUTO.md` (entire file, especially §§2, 11, 14–16, 23, 26, 34) and `EC-REGRAS-CRIACAO-DE-TELAS-E-IMAGENS.md` (entire file, especially §§11, 13–14, 17, 20). Their permanent Ec requirements override analogies to Cargo, OSGi and Kubernetes. No unresolved conflict remains at the methodology level; final technical decisions listed above are **not** filled by the skill.
