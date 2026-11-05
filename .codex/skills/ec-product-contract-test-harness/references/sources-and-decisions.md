# Primary Sources, Papers, Evidence Depth, and Engineering Decisions

> Imported authoring record. Research dates, consultations and execution claims below belong to the supplied source package; they were not reproduced in this adaptation. Referenced validation scripts, fixtures, product norms and logs are not bundled unless present in this folder. Verify their availability and provenance before use. No historical PASS is current acceptance.

**Research date:** 2026-10-31. **Method:** Original supplied Ec normative documents and catalog/SDD 1.3.0 first; provider/standards primary technical pages next; primary academic publication records and accessible original text for test-method evidence. Sources support **techniques**, while **Ec norms and feature-specific SDD** alone authorize product obligations. No forums, benchmark replication, new API contracts or application tests were used.

## Normative and project evidence

| ID | Source read | Authority / applicable boundary |
|---|---|---|
| E01 | `EC-REGRAS-DE-DESIGN-E-PRODUTO.md`, sections 2, 14–16, 22–23, 28, 32–35 | Ec desired config YAML, three modes, revisions, operational separation, access/UX, real acceptance gates |
| E02 | `EC-REGRAS-CRIACAO-DE-TELAS-E-IMAGENS.md`, sections 13–17, 20–22 | Truthful visible states, accessibility, feature coverage and consistency of UI evidence |
| E03 | `EC-CATALOGO-COMPLETO-SKILLS-SDD-v1.md`, sections "Contrato de autoridade", "16 novas skills" and acceptance | Skill responsibility, no duplication, planned 16th new Ec skill |
| E04 | Original `skills_zip.zip`, `skills/sdd/model/{README,workflow,evidence}.md`, `skills/sdd/chain/README.md` | SDD 1.3.0 routes, five acceptance statuses, optional bridge and validator |
| E05 | Prior supplied `EC-FIFTEENTH-SKILL-EN-v1.0-VALIDATED.zip` | Packaging, progressive references, actual SDD fixture pattern and labels of synthetic versus real evidence |

## Official technical sources and inspected scope

| ID | Primary source / canonical URL | Inspected scope and justified usage |
|---|---|---|
| O01 | [YAML 1.2.2 specification](https://yaml.org/spec/1.2/) | Representation graph, unique mapping keys, scalar tags/types, mapping-key order and sequence order; independent semantic contracts, **not** Ec schema fields |
| O02 | [RFC 9110 HTTP Semantics](https://www.rfc-editor.org/rfc/rfc9110.html) | HTTP 202 acceptance, conditional requests/If-Match and precondition conflicts; API design conditional on actual Ec choice |
| O03 | [OpenAPI 3.1.1 specification](https://spec.openapis.org/oas/v3.1.1.html) | Description and consumer visibility for HTTP APIs; OpenAPI is not Ec desired-configuration format |
| O04 | [OWASP Authorization Cheat Sheet](https://cheatsheetseries.owasp.org/cheatsheets/Authorization_Cheat_Sheet.html) | Deny-by-default and checking authorization on every request; require real backend tests |
| O05 | [OWASP API1:2023 BOLA](https://api-security.owasp.org/editions/2023/en/0xa1-broken-object-level-authorization/) | Cross-object ID substitution and scope attack vectors |
| O06 | [OWASP CI/CD Security](https://cheatsheetseries.owasp.org/cheatsheets/CI_CD_Security_Cheat_Sheet.html) | CI/CD privilege, secrets, source and plugin provenance, integrity and logging threats |
| O07 | [OWASP Secrets Management](https://cheatsheetseries.owasp.org/cheatsheets/Secrets_Management_Cheat_Sheet.html) | Secret lifecycle, minimizing plaintext exposure and redacted test artifacts |
| O08 | [OWASP WSTG project](https://owasp.org/projects/web-security-testing-guide) | Security testing methodology; current release metadata and versioned vs latest distinction |
| O09 | [PostgreSQL current transaction isolation](https://www.postgresql.org/docs/current/transaction-iso.html) | Read Committed snapshots versus Serializable anomalies; SQLite demos cannot certify PostgreSQL |
| O10 | [PostgreSQL explicit locking](https://www.postgresql.org/docs/current/explicit-locking.html) | Transaction/session locks and limitations; actual concurrency must be tested in PostgreSQL |
| O11 | [Rust proptest reference](https://docs.rs/proptest/latest/proptest/) | Strategies, generated properties and prop_assert; external Rust tests not executed |
| O12 | [Hypothesis stateful testing](https://hypothesis.readthedocs.io/en/latest/stateful.html) | Rule-based state machines, invariant sequences and generated tests, not installed here |
| O13 | [Hypothesis settings](https://hypothesis.readthedocs.io/en/latest/settings.html) | Reproducible seeds, saved examples, shrink/CI trade-offs |
| O14 | [Python unittest](https://docs.python.org/3/library/unittest.html) | Local stdlib test fixture and assertion lifecycle |
| O15 | [Python sqlite3](https://docs.python.org/3/library/sqlite3.html) | Local transaction and rollback semantics, not PostgreSQL guarantee |
| O16 | [Vitest browser mode](https://vitest.dev/guide/browser/) | Actual browser provider and limitations; no browser tests included |
| O17 | [Vitest projects](https://vitest.dev/guide/projects) | Separating test suites/projects; avoid assuming Vitest is installed in Ec |
| O18 | [Vue Testing Library](https://testing-library.com/docs/vue-testing-library/intro/) | User-oriented Vue DOM queries; actual DOM/accessibility needs runtime |
| O19 | [Testing Library queries](https://testing-library.com/docs/queries/about/) | Priority of accessible names/roles and async UI states |
| O20 | [WCAG 2.2 W3C recommendation](https://www.w3.org/TR/WCAG22/) | Measurable keyboard/focus/accessibility requirements (source target still Ec normative) |
| O21 | [W3C ARIA keyboard interface](https://www.w3.org/WAI/ARIA/apg/practices/keyboard-interface/) | Predictable keyboard navigation and focus; cannot certify a static model |
| O22 | [Git revision syntax](https://git-scm.com/docs/git-rev-parse) | Git revision identifiers and commit resolution for immutable references |
| O23 | [Git objects](https://git-scm.com/book/en/v2/Git-Internals-Git-Objects) | Versioned object identities; choosing Git as authority remains an Ec architecture/SDD decision |
| O24 | [RFC 6902 JSON Patch](https://www.rfc-editor.org/info/rfc6902/) | Helps classify transport-level patch operations; **not** an alternative Ec config file, nor prescribed |
| O25 | [OWASP IDOR prevention](https://community.owasp.org/attacks/insecure_direct_object_reference) | Additional original OWASP object authorization methodology |
| O26 | [Git operations versioning concepts](https://git-scm.com/book/en/v2/Git-Tools-Revision-Selection) | Explicit revision selection vs mutable branch pointers; actual Ec source path not supplied |

**Scope note:** O01–O26 are official/primary documentation references supporting methods and terminology. Source pages were consulted for relevant sections; this is **not** an independent conformance certification of any code or an exhaustive verification of every subsection. Version-specific behavior must be rechecked against dependencies actually installed in the Ec repository.

## Original scholarly work and depth of access

| ID | Work / primary URL | Access depth actually used and relevant concept |
|---|---|---|
| P01 | Chen, Cheung & Yiu (1998), [*Metamorphic Testing: A New Approach for Generating Next Test Cases*](https://www.cse.ust.hk/faculty/scc/publ/CS98-01-metamorphictesting.pdf) | **Accessible original full-text report, abstract and introductory/method examples inspected**; source-derived transformations test relations between outputs, not substitute for all independent oracles. Technical report, not described as peer-reviewed publication |
| P02 | Claessen & Hughes (2000), [*QuickCheck: a Lightweight Tool for Random Testing of Haskell Programs*](https://doi.org/10.1145/351240.351266) | **Publisher abstract/metadata only**; property-based randomized testing with explicit properties; no results reproduced |
| P03 | Fraser & Zeller (2012), [*Mutation-driven Generation of Unit Tests and Oracles*](https://www.st.cs.uni-saarland.de/publications/details/fraser-tse-2011/) | **Author publication page and abstract**; mutated program reveals insufficiency of oracles; package probes mutate synthetic observations, not Ec code |
| P04 | Pacheco, Lahiri, Ernst & Ball (2007), [*Feedback-directed Random Test Generation*](https://people.csail.mit.edu/cpacheco/publications/feedback-random-abstract.html) | **Author abstract/metadata**; input generation with feedback and contract checking, not claimed performance for Ec |
| P05 | Weyuker (1982), [*On Testing Non-Testable Programs*](https://academic.oup.com/comjnl/article-abstract/25/4/465/366384) | **Publisher abstract/metadata only**; limits of oracle assumptions motivate independent observations and explicit unknowns |
| P06 | Godefroid, Klarlund & Sen (2005), [*DART: Directed Automated Random Testing*](https://osl.cs.illinois.edu/publications/conf/pldi/GodefroidKS05.html) | **Author/institution abstract/metadata only**; directed fault/path test generation; no DART runtime run |
| P07 | Kingsbury & Alvaro (2021), [*Elle: Inferring Isolation Anomalies from Experimental Observations*](https://arxiv.org/abs/2003.10554) | **Original abstract/metadata**; observable histories and isolation anomaly detection; no Jepsen/Elle/postgres test executed |
| P08 | Kleppmann & Beresford (2017), [*A Conflict-Free Replicated JSON Datatype*](https://martin.kleppmann.com/2017/04/24/json-crdt.html) | **Author publication page and abstract**; alternative merging illustrates explicit trade-offs; not adopted as Ec merge policy nor Ec config format |

No quantitative claim from these papers is transferred as a measured outcome of the Ec tool. P08 is a conceptual contrast, not endorsement of JSON or CRDT as an Ec declarative config authority.

## Technical synthesis and decisions

| ID | Evidence | Derived skill-method decision (non-normative; SDD resolves concrete choices) |
|---|---|---|
| D01 | E01, O01 | Require typed semantic goldens, not naive YAML text equality. |
| D02 | E01, O01 | Preserve sequence ordering and field-sensitive scalar types; reject duplicate keys and unsafe tags through real parser. |
| D03 | E01, E03 | Keep test harness separate from canonical YAML, RBAC and publication engines. |
| D04 | E01, E04 | Capture requirement/acceptance IDs and original SDD evidence statuses; do not create a second SDD implementation. |
| D05 | O04, O05, O25 | Test authorization at backend resource/action/tenant boundaries, including denial and revocation races. |
| D06 | O02, O09, O10 | Observe race/conflict outcomes; conditional HTTP and transaction isolation are implementation choices to verify. |
| D07 | O06, O07 | Test secret references, logging redaction, code/build trust boundaries and isolation. |
| D08 | O11–O13, P01–P04 | Use deterministic metamorphic/stateful and mutation-sensitive cases; label synthetic evidence precisely. |
| D09 | O17–O21 | Browser/assistive technology acceptance is separate from pure TypeScript checks. |
| D10 | O22, O23, E01 | Capture immutable config and code revision IDs for run history; no branch-name pin substitution. |
| D11 | P05, P07 | Missing/ambiguous observations must stay unresolved, not green. |
| D12 | P08, E01 | Reject implicit merge/last-write-wins as a substitute for the Ec revision/approval contract. |
| D13 | E04, E05 | Use original SDD 1.3.0 actual validator on isolated fixture and negative broken `skill_path`. |
| D14 | O14, O15 | Keep stdlib Python/SQLite samples reproducible without external network/test infrastructure. |
| D15 | E01, E02 | Use two independent sources when possible: backend effect receipt and UI display must agree. |
| D16 | E03, E05 | Ship package manifest and logs, but install only the skill directory and optional SDD bridge as needed. |

## External-model conflicts explicitly avoided

- YAML representation versus application schema: YAML grammar does not establish that illustrative Ec `kind`, `apiVersion`, `spec` or extension keys exist in any released implementation.
- JSON for API transport, ZIP integrity or academic reference is **not** a permitted alternative file configuration for Ec; this package adds no JSON config resource.
- Generic CRDT convergence, SQLite transaction behavior and RFC 9110 If-Match semantics are references, **not automatically** the chosen Ec conflict-resolution/persistence policy.
- A frontend `allowed` boolean is a display hint; effective authorization must be verified on the backend.
- Passing a mock/referenced implementation and positive SDD structure is not product feature approval. No Ec application repository, actual parsed YAML, API implementation, active PostgreSQL, current Rust toolchain, real Vue/browser/assistive tech or remote agent was available to evaluate.

**Current research limits:** The P01 source is a technical report; other publisher/author academic records were used at metadata/abstract depth unless explicitly stated. The source catalog is methodological, not a replicated benchmark or a claim that scientific experiments were performed here.
