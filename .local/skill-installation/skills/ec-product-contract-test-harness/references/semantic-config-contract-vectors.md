# Semantic Configuration Vectors and Three-Mode Oracles

> Commands, fixture paths and historical execution claims from the source package are illustrative or imported evidence unless the corresponding files are present and independently checked; they are not current test results or bundled executable tools.

**Authority:** The Ec normative documents and the feature-specific, implemented/versioned resource schema define desired configuration semantics. YAML 1.2.2 defines mapping/sequence/scalar representation; actual Ec parsing, permitted tags and schema coercion must be tested against the real parser. The examples in `validation/fixtures/` are intentionally **illustrative** and not a validated `Pipeline` contract. Do not import them as if the currently deployed Ec supports `ec.dev/v1alpha1`, `spec.jobs` or `secretRef`.

## Required cross-mode matrix

| Scenario | Action sequence | Independent expected observation |
|---|---|---|
| Y01 basic round-trip | YAML → validated resource → Form → YAML | Same typed semantics; raw formatting may differ |
| Y02 visual no-op | YAML → Visual → YAML with no edit | Same semantic tree and supported extensions |
| Y03 form no-op | YAML → Form → YAML with hidden advanced fields | Advanced fields retained verbatim semantically |
| Y04 visual edit | Visual edit job/needs or supported property → Form/YAML | All three projections reflect same new semantics |
| Y05 YAML edit | Valid YAML edit → Visual/Form | All representable nodes update; explicitly unsupported visual features are not dropped |
| Y06 unsupported visual field | YAML includes supported extension not visualizable | Visual editing is constrained/disabled; Form/YAML retain field, no silent loss |
| Y07 mapping permutation | Reorder YAML mapping keys | Identical typed representation except presentation-only changes |
| Y08 sequence permutation | Reorder steps or other ordered list | Different semantics when list order is meaningful |
| Y09 scalar distinction | false vs 0, 0 vs '0', null vs absent | Distinct unless **approved schema** explicitly normalizes |
| Y10 comments/format | Change only comments and indentation | Semantic equivalence; separate test for loss-of-comment warnings/diff |
| Y11 unknown extension | Roundtrip supported vendor field/reference/expression | Field still present; unsupported key may be rejected under schema policy |
| Y12 duplicates | Two equal YAML mapping keys | Must produce controlled error, not last-write-wins |
| Y13 parser hazards | Untrusted tags, recursive/deep/oversized aliases | Rejection or bounded diagnostics according to confirmed parser limits |
| Y14 invalid draft | Invalid YAML entered while published revision exists | Draft remains non-effective, last valid projection not corrupted |
| Y15 secret reference | YAML points to approved secret reference | Secret value does not appear in YAML or UI/logs |
| Y16 inline secret | YAML contains a credential value in forbidden location | Rejected/denied by Ec policy and redacted in evidence |
| Y17 schema compatibility | Unknown `apiVersion`/deprecated field | Precise typed error or approved migration, never silent rewrite |
| Y18 conflict | Second editor publishes over stale base revision | Conflict and diff; stale change does not become effective |
| Y19 reference identity | Change a referenced project/pool/role | Reference validation, scoped impact, no orphan resource silently |
| Y20 DAG cycle | Introduce `needs` cycle when graph semantics apply | Real graph validator detects/rejects, no silent scheduling |

## Critical implementation cautions

1. Do not parse YAML as JSON, treat JSON Schema **transport validation** as Ec configuration authority, or invent a second machine-readable configuration format. JSON in the package is only a ZIP integrity manifest; test expectations in Python are test data, not product config.
2. YAML permits alias/reference graphs and unordered mapping keys. Deep-cycle, alias-expansion and arbitrary tag hazards require **Ec parser testing and configured budgets**, not a simplistic Python `dict` roundtrip. A YAML decoder that implicitly applies YAML 1.1 scalar resolution is not evidence of YAML 1.2.2 conformance.
3. A textual equality assertion is too strong for legitimate presentation changes and too weak for schema/default/merge behavior. Maintain independent typed expected semantics and, separately, source-preservation tests for comments, expression formatting and stable diffs.
4. Unknown fields require a documented handling policy by schema/version: extension points may be preserved, disallowed keys may be rejected. Do not make universal claims that arbitrary unknown keys are valid.
5. Compare identities and referenced object versions, not just labels; a rename or parent move can impact access and behavior even when the resulting YAML parses.
6. Never serialize operational queues, sessions, agent heartbeats, audit events, run history or cleartext secrets to resource YAML. These are test *observations*, not desired resource configuration.

## What shipped as a synthetic demonstration

The files `valid-pipeline.yaml` and `equivalent-pipeline.yaml` are intended as semantically equivalent specimen pairs. `duplicate-keys.yaml`, `inline-secret.yaml`, `cyclic-dependency.yaml` and `unknown-version.yaml` are rejection/example vectors, subject to actual Ec schema validation before real use. The `reference_model.py` explicitly **does not parse YAML**; it maps these filenames to predeclared typed fixture semantics. That architecture prevents synthetic success from being mistaken for proof that a real YAML 1.2.2 decoder or Ec schema validator works.

For real integration, capture the implementation-under-test's typed resource output and compare it to **separately reviewed expected semantics**. Add forced parser errors and malicious-input budgets with documented resource limits, and verify that none of those inputs changed published state or leaked diagnostics.
