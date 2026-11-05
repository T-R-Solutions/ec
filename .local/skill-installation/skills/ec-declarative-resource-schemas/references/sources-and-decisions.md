# Evidence Matrix — `ec-declarative-resource-schemas`

> Imported authoring record. Research dates, consultations and execution claims below belong to the supplied source package; they were not reproduced in this adaptation. Referenced validation scripts, fixtures, product norms and logs are not bundled unless present in this folder. Verify their availability and provenance before use. No historical PASS is current acceptance.

**English revision 1.2 — 2026-10-31.** Scope: official standards, Ec normative documents, maintainers' documentation, and academic literature with explicit access levels. No full-text reading of paywalled books, Ec parser execution, or application-level compatibility verification was performed. Forums, Q&A platforms, and social-media posts are excluded as technical evidence.

## Evidence classification and access

- **N (Ec normative source):** authority for Ec product requirements; these requirements do not originate from external literature.
- **P (primary/technical source):** specification, maintainer documentation, or library manual; supports **documented behavior**, not observed behavior in Ec.
- **A (academic source):** peer-reviewed article or scholarly book; supports **methods and limitations**, not empirical outcomes for Ec.
- **I (engineering inference):** a design recommendation derived from evidence; it requires verification before it may be reported as an observed result.
- Access labels: **official HTML sections**, **complete README**, **abstract/metadata**, **publisher preview**, or **complete local document**. Do not claim full-text review when only an abstract or preview was available.

## Traceable evidence register

| ID | Source, edition, and material examined | Access / type | Supported finding (with scope) | Implication for the skill and limitations |
|---|---|---|---|---|
| N1 | `EC-REGRAS-DE-DESIGN-E-PRODUTO.md`, §§ 2, 14–16, 23, 34 | Complete local document / N | YAML is the canonical configuration source; versioning, validation, and semantic preservation are Ec requirements. | The skill provides **implementation methods**, not an independent policy or an assertion that schemas already exist. |
| N2 | `EC-REGRAS-CRIACAO-DE-TELAS-E-IMAGENS.md`, §§ 13–14 | Complete local document / N | Visual, Form, and YAML modes must preserve meaning and communicate limitations of projection. | Verify the data contract; delegate UX, rendering, and comment-preserving editing to their dedicated specialties. |
| P1 | [YAML 1.2.2 specification](https://yaml.org/spec/1.2/), §§ 3.2.1, 3.2.2, 3.2.3, 3.3, and 7 | Official HTML sections / P | Mappings are unordered and require unique keys; sequences are ordered; comments belong to presentation; alias graphs may be cyclic; scalar equality is tag- and canonical-form-dependent. | Separate representation from presentation, test duplicate-key identity, and define an Ec-specific alias policy. **Do not** claim all cycles are invalid YAML syntax. |
| P2 | [YAML 1.2.2 changes](https://yaml.org/spec/1.2.2/ext/changes/), “Changes in version 1.2” | Official HTML section / P | YAML 1.1 and 1.2 differ in boolean/number resolution; merge key `<<` is not a core YAML 1.2 feature. | Provide scalar-resolution and compatibility fixtures; do not assume a Rust parser conforms to 1.2 because of its name. |
| P3 | [YAML Test Suite](https://github.com/yaml/yaml-test-suite), README and usage | Maintainers' README / P | Language-independent processor test vectors can be pinned to a `data-YYYY-MM-DD` release rather than a moving branch. | Running the suite against the selected Ec parser is **future work**, not an executed result of this package. |
| P4 | [Serde container attributes](https://serde.rs/container-attrs.html), `deny_unknown_fields` and `default`; [Serde field attributes](https://serde.rs/field-attrs.html), `flatten` | Official HTML sections / P | Unknown fields may be ignored in self-describing formats; `flatten` and `deny_unknown_fields` cannot be combined on the indicated structs. | Check the actual decoder and model; explicitly reject or preserve unknown fields under an approved extension contract. |
| P5 | [dtolnay/serde-yaml](https://github.com/dtolnay/serde-yaml), maintainer repository and archive notice | Maintainer repository page / P | The repository was archived on 2024-03-25 and is described as no longer maintained. | **Do not select it by default.** No alternative parser has been approved or benchmarked for Ec in this work. |
| P6 | [OWASP Deserialization Cheat Sheet](https://cheatsheetseries.owasp.org/cheatsheets/Deserialization_Cheat_Sheet.html), introduction and guidance | Official HTML sections / P | Untrusted deserialization needs controlled construction and resource consumption; language-specific APIs differ. | Treat untrusted YAML as input data, restrict tags and resource use; do not present Python `safe_load` as a Rust API. |
| P7 | [serde_path_to_error](https://docs.rs/serde_path_to_error/latest/serde_path_to_error/), description and API | Crate documentation page / P | The library provides a logical path to deserialization errors. | Do not infer line/column or source offsets from a logical path; spans require distinct parser support. |
| P8 | [Rust Fuzz Book — cargo-fuzz](https://rust-fuzz.github.io/book/cargo-fuzz.html), introduction | Technical HTML section / P | `cargo-fuzz` is an interface for libFuzzer. | Plan fuzz cases for parsing and resource limits only when an actual crate and harness exist; no fuzz run has been performed here. |
| A1 | [Claessen & Hughes, *QuickCheck*, ICFP 2000](https://doi.org/10.1145/351240.351266), ACM abstract; [Chalmers publication record](https://research.chalmers.se/en/publication/237427) | Peer-reviewed publication abstract and metadata / A | Property-based testing uses generators and test oracles; coverage is bounded by chosen generators and properties. | Supports round-trip and migration properties. **Do not** claim a full-paper reading or any measured improvement. |
| A2 | [Sassaman, Patterson, Bratus, Locasto & Shubina, *Security Applications of Formal Language Theory*, IEEE Systems Journal 7(3), 2013](https://ieeexplore.ieee.org/document/6553401/), abstract and metadata | Peer-reviewed publisher abstract and metadata / A | Consistent language interpretation and explicit validation are relevant to analyzing parser security risks. | Motivates divergent-interpretation testing; does **not** establish the security of any Ec parser. A technical-report PDF was identified but was not approved as a fully reviewed source. |
| A3 | [Grune & Jacobs, *Parsing Techniques*, 2nd ed., Springer](https://link.springer.com/book/10.1007/978-0-387-68954-8), publisher description and contents; [authors' supplementary materials](https://www.cs.vu.nl/pub/dick/PTAPG_2nd_Edition/) | Publisher preview and supplementary material / A | Background bibliographic reference on parsing techniques; full book access was not verified. | Do not derive detailed claims from chapters that were not read or label this as an exhaustive reading of the volume. |
| P9 | `skills_zip.zip` → `skills/sdd/model/{README.md,workflow.md}` and `skills/sdd/model/chain/README.md`, SDD 1.3.0 | Complete documents from provided local package / P (local workflow) | `skill_path` resolves relative to the project root; selective bridge metadata uses `bridge_version: 1` in the supplied validator; references do not automatically activate supporting skills. | Define a real bridge path and validate in an isolated fixture; do not claim SDD was installed in Ec. |

## Evidence-derived technical synthesis

**D1 — Two different schemas.** The *YAML schema* resolves tags and scalar forms; the *Ec resource schema* defines fields and domain invariants. A file may be syntactically valid YAML and invalid as an Ec resource (**P1, P2 + N1**).

**D2 — Detect duplicate keys before typed maps.** Lexically different mapping keys may resolve to the same semantic identity. Materializing a map too early can lose a duplicate, so the chosen event/representation-level approach needs parser-specific verification (**P1 + I**). Non-scalar and recursive keys require explicit policy and implementation tests.

**D3 — Aliases and resource cost.** YAML's representation model allows cycles; Ec may restrict them for security, but that is not a universal YAML syntax rule. Set limits using a threat model and measurements, not fabricated numeric thresholds (**P1, P6 + I**).

**D4 — Parser selection.** Available materials do not identify the parser used by Ec. The archived status of `serde_yaml` calls for an explicit alternative evaluation, not an implicit dependency choice (**P5**). Handling of unknown fields, defaults, and `flatten` depends on actual data structures and decoder behavior (**P4**).

**D5 — Validation is not execution.** YAML Test Suite evaluates YAML processor behavior; resource tests evaluate the Ec contract; SDD evidence evaluates the requested feature. These are distinct verification layers (**P3, P9 + N1**).

**D6 — Properties and fuzzing are not proofs.** Generators and oracles cover a bounded state space. Record seeds, versions, corpora, minimized examples, and observed outcomes; do not claim robustness or performance without execution (**A1, P8 + I**).

## Conflicts, limitations, and open questions

1. Which Rust parser and version, scalar-resolution rules, and security settings does Ec use? **Unknown.**
2. Does each resource allow multi-document YAML streams, aliases, custom tags, and non-scalar mapping keys? **Do not infer this** without an approved resource specification.
3. How can unknown extensions be retained without future field collisions? **Requires an approved contract and tests.**
4. What input size, nesting depth, alias expansion, CPU-time limits, and performance thresholds apply? **Not measured.**
5. What are the actual Ec repository paths, versions, CI commands, parser integrations, and installed SDD state? **Not provided.**
6. Was the complete text of the paid books or QuickCheck article accessed? **Not verified.** Do not report unexamined passages as fully read.
7. The LangSec technical-report PDF was not visually checked in this review; only the publisher's IEEE abstract was used to support the stated methodological point. **No full-text review is claimed.**
