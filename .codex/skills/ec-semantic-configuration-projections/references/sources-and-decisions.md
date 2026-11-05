# Source Matrix and Synthesis — Ec Semantic Configuration Projections

> Imported authoring record. Research dates, consultations and execution claims below belong to the supplied source package; they were not reproduced in this adaptation. Referenced validation scripts, fixtures, product norms and logs are not bundled unless present in this folder. Verify their availability and provenance before use. No historical PASS is current acceptance.

**Research date:** 2026-10-31. **Language:** English. **Admissible sources:** Ec normative documents, official specifications and framework documentation, published peer-reviewed research. **Excluded:** forums, social-media recommendations, anonymous tutorials, unsourced benchmarks. This matrix distinguishes directly observed full-text technical content from publication metadata or abstracts. It is **not** a claim that the Ec repository, parser, feature behavior, or test runner has been inspected.

## Authority and evidence hierarchy

- **N (Ec product norms)** are the sole authority on Ec-specific requirements; the skill cites but does not restate or amend them.
- **O (official documentation/standards)** describe particular APIs and standards. They do not prescribe an Ec implementation absent an approved contract.
- **A (academic research)** supports mathematical/test methodology; adaptation to Ec is an engineering inference.
- **D (derived decisions)** are proposals justified by one or more sources, not externally proved properties of this project.

| ID | Source, reference URL | Access and status | Narrow supported claim | Limits / cautions |
|---|---|---|---|---|
| N1 | Ec: `EC-REGRAS-DE-DESIGN-E-PRODUTO.md`, §§2, 12–16, 26, 34 | User-provided local document, read | Desired configuration has one YAML authority; three modes must avoid semantic loss; invalid edit and version handling are specified product requirements. | Product specification, not implementation evidence. |
| N2 | Ec: `EC-REGRAS-CRIACAO-DE-TELAS-E-IMAGENS.md`, §§12–14, 17, 20–21 | User-provided local document, read | Visual/Form/YAML projections need mode-appropriate capabilities, clear unsupported edits and accessible alternatives. | No screen or browser interaction was evaluated. |
| O1 | YAML 1.2.2, <https://yaml.org/spec/1.2/> | Full official HTML specification inspected | Mapping keys have no representation-level order; sequences are ordered; presentation/serialization/representation are distinct. | Does not ensure a given parser preserves comments/anchors or supports source edits. |
| O2 | Vue 3, Computed Properties, <https://vuejs.org/guide/essentials/computed> | Full official guide inspected | Computed getters should be free of side effects and derived values should not be mutated. | Framework-level guidance, not proof of a store design. |
| O3 | Vue 3, State Management, <https://vuejs.org/guide/scaling-up/state-management> | Full official guide inspected | Views derive from state and actions change state; multiple components should not synchronize arbitrary independent copies. | Does not mandate Pinia; inspect actual Ec dependencies first. |
| O4 | Vue 3, Watchers, <https://vuejs.org/guide/essentials/watchers> | Official guide inspected | Watchers are intended for side effects in reaction to changes, unlike computed derivations. | Does not mean every side effect should be a watcher. |
| O5 | Vue 3, Testing, <https://vuejs.org/guide/scaling-up/testing> | Full official guide inspected | Unit, component, and end-to-end tests serve different purposes; test observable behavior, not snapshots alone. | Actual test runner/dependencies unobserved. |
| O6 | Vue 3, TypeScript with Composition API, <https://vuejs.org/guide/typescript/composition-api> | Official guide inspected | Props and events can have typed contracts using `defineProps`/`defineEmits`. | No specific Ec component API is known. |
| O7 | TypeScript Handbook, Narrowing / Discriminated Unions, <https://www.typescriptlang.org/docs/handbook/2/narrowing.html> | Full official page inspected | Discriminated unions and control-flow narrowing can model distinct edit outcome states. | Compile-time types do not replace runtime validation of YAML/user input. |
| O8 | W3C WCAG 2.2, <https://www.w3.org/TR/WCAG22/> | Full W3C Recommendation inspected | Testable accessibility criteria, including keyboard accessibility and alternatives for dragging. | Conformance requires actual interface audit; not achieved by a skill file. |
| O9 | W3C WCAG 2.5.7 Understanding, <https://www.w3.org/WAI/WCAG22/Understanding/dragging-movements> | Official informative explanation inspected | Dragging operations need an alternative that can be completed with a single pointer without dragging, except listed exceptions. | Keyboard equivalence alone does not satisfy this specific criterion; separately test both. |
| O10 | Playwright, Assertions, <https://playwright.dev/docs/test-assertions> | Full official guide inspected | Retry-aware assertions are available to verify observable asynchronous UI state. | Not assumed installed; does not prove real Ec tests were run. |
| A1 | Foster, Greenwald, Moore, Pierce, Schmitt, *Combinators for Bi-Directional Tree Transformations: A Linguistic Approach to the View Update Problem*, POPL 2005, DOI <https://doi.org/10.1145/1040305.1040325>; author-hosted <https://www.cs.cornell.edu/~jnfoster/papers/lenses.pdf> | Peer-reviewed paper, author-hosted full PDF inspected (abstract, definitions, law discussion and examples) | Formal `get`/`putback` concept; GetPut and PutGet; optional PutPut that some sound combinators do not satisfy. | Formal results for their lens calculus are **not** proof of Ec correctness or globally mandated laws for arbitrary UI patches. |
| A2 | Foster et al., journal extension *Combinators for Bidirectional Tree Transformations*, ACM TOPLAS 29(3), 2007, DOI <https://doi.org/10.1145/1232420.1232424> | Publisher bibliographic/abstract record and author publication list verified; publisher full text not accessed | Confirms an expanded peer-reviewed journal treatment of the same research line. | Do not claim line-by-line review of its inaccessible full text. |
| A3 | Bohannon, Pierce, Vaughan, *Relational Lenses: A Language for Updatable Views*, PODS 2006, pp. 338–347, <https://www.sigmod.org/publications/discs/2007/PODS2006.htm> | Peer-reviewed proceedings and authorship verified; complete paper **not accessed** | Establishes related work on updateable database views. | Contextual citation only, not evidence of specific rules or performance claims here. |
| A4 | Claessen, Hughes, *QuickCheck: A Lightweight Tool for Random Testing of Haskell Programs*, ICFP 2000, DOI <https://doi.org/10.1145/351240.351266> | Peer-reviewed university repository record and abstract inspected; available third-party PDF is corrupted for reliable text extraction and was **not** substantively reviewed | Property-based test methodology using generators and properties. | Not a Rust implementation prescription or evidence of any coverage guarantee. |
| A5 | Hofmann, Pierce, Wagner, *Symmetric Lenses*, POPL 2011, DOI <https://doi.org/10.1145/1925844.1926428>, author-hosted <https://www.cis.upenn.edu/~bcpierce/papers/symmetric-full.pdf> | Peer-reviewed research; author-hosted full-version PDF inspected (abstract, asymmetric/symmetric distinction) | When one structure is primary and the others are views, asymmetric lenses are the relevant starting model; symmetric lenses address different assumptions about both sides carrying private data. | This provides conceptual framing, not an Ec-specific theorem or proof of feasibility. |

## Decision log derived from the sources

| ID | Derived engineering decision | Basis | Not inferred |
|---|---|---|---|
| D01 | Preserve the original full resource when applying a partial form/canvas edit, rather than serializing only visible fields. | N1, A1 | Does not specify actual Ec AST/data structures. |
| D02 | Use conditional GetPut/PutGet test laws; do not make PutPut universally mandatory. | A1 | Algebraic law applies to well-defined views/updates; not a substitute for testing. |
| D03 | Separate semantic preservation from presentation-aware preservation and display a diff for unavoidable formatting changes. | N1, O1 | No current editor/parser feature support has been observed. |
| D04 | Prefer one typed semantic draft with derived Vue projections and narrow write operations; isolate invalid YAML in ephemeral text. | N1, O2, O3, O4 | An implementation recommendation, not a Vue API mandate. |
| D05 | Use tagged edit outcomes (`applied`, `rejected`, `conflict`) and stable IDs; never silently overwrite stale revisions. | N1, O7 | Exact wire protocol needs approval in Ec specs. |
| D06 | Require a representability/capability matrix per resource kind/schema version; disable lossy edits. | N1, N2, A1 | Does not claim that every field must appear in a diagram. |
| D07 | Test model laws, component behavior, accessible interaction and full mode switching separately. | N1, N2, O5, O8, O9, A4 | Tools must be confirmed in the actual repository. |
| D08 | Validate changes using the approved schema and backend authority; never equate visual success with published success. | N1, O7 | Validation/publishing implementation owned by other skills. |
| D09 | Prefer asymmetric source-to-view update reasoning over treating the three modes as three independent synchronized masters. | N1, A1, A5 | Does not imply a particular lens library or prove invertibility of all views. |

## Critical distinctions for the agent

- **A peer-reviewed paper proves a mathematical property for its formal setting**, not that Ec's current code satisfies it. Transferring the property requires an explicit mapping and executable tests.
- **YAML representation equality is not textual fidelity.** An editor can preserve semantic data and still accidentally strip important comments; review these separately.
- **Canonical source** means authoritative desired-configuration YAML in Ec's product rules, not an imposed YAML byte-level canonical format.
- **An implementation may be proposed but not claimed as present** until observed in the repository. None of the sources above is a benchmark or an Ec integration test.
- **Source availability is not conflated with source discovery.** A2/A3/A4 are not claimed as fully read; their methodological impact is deliberately limited.

## Revisit conditions

Reassess this matrix if Ec schema contracts, editor/parser library, versions of Vue/TypeScript, or W3C requirements change. Prefer the dependency versions and actual source-tree contracts observed at adoption; do not retrofit new behavior into historical SDD specs without an explicit decision.
