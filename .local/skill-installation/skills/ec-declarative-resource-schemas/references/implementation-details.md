# Detailed decisions and failure cases

Read only for a decision needing more detail than the entry point. Apply the relevant steps to the
actual task; the sections are not a mandatory artifact or test checklist. Use existing project
contracts in their actual format. File names such as spec.md, plan.md or tasks.md are examples, not
required dependencies. Other skills are optional expertise, never enforcement mechanisms.

Product rules, stack choices and authority locations must be verified in the Ec consumer. Examples
do not establish deployed capabilities or override its current contract.

## Decision procedure

1. **Define the contract before coding.** Build a per-field matrix: type, requiredness,
   omitted/`null`/empty distinctions, defaults and effects, references, constraints,
   introduction/removal versions, extensibility, and an invalid example. Distinguish the *YAML
   schema* (tag and scalar resolution) from the *Ec resource schema* (structure, types, and domain
   semantics). Functional decisions belong in the project specification; this skill
   translates them into validation methods.
2. **Assess the actual parser.** Inspect maintenance status, version, security policy, license, and
   effective support for YAML 1.2.x, multi-document streams, tags, aliases, complex keys,
   duplicates, and source locations. **Do not default to `serde_yaml`:** its original repository was
   archived in 2024. Run pinned test vectors for each candidate; a library's name or simple examples
   do not prove conformance.
3. **Detect information loss before typed conversion.** Reject duplicate mapping keys based on their
   resolved identity *before* conversion into structures that may overwrite entries. Test lexically
   different but semantically equivalent keys; document limitations for complex-key equality.
   Explicitly decide whether to accept or reject multiple documents, unapproved tags, and aliases.
   Do not label an Ec-specific restriction a universal YAML syntax error. Prevent arbitrary
   executable object construction and bound input size, nesting, references, and expansion cost
   using approved or measured thresholds, not invented values.
4. **Validate in observable layers.** Separate syntax/representation, tag/scalar resolution,
   resource kind and version dispatch, structure/types, domain rules, referential integrity, and
   authorization. Parse success never implies publication approval. Do not silently discard unknown
   fields: explicitly reject them or preserve them under a documented extension contract. Verify the
   actual deserializer's behavior; Serde's `deny_unknown_fields` is incompatible with `flatten` on
   the relevant structs.
5. **Design schema evolution.** Produce a version compatibility matrix and fixtures for additions,
   renames, removals, type changes, and defaults. Fail explicitly when a migration would lose
   meaning or lacks an approved mapping. Never silently fall back to an older version. Delegate
   formatting- and comment-preserving edits to their dedicated specialty.
6. **Report truthful diagnostics.** Include a stable category/code, resource and version, logical
   field path, cause, supported remediation, and source location when available. A deserialization
   error path does not guarantee line/column information; report offsets only when a parser actually
   provides source spans. Do not fabricate diagnostics.
7. **Check with independent oracles.** Separate parser conformance (YAML Test Suite at a pinned
   release/commit), resource-contract tests (fixtures and references), schema-evolution tests
   (migrations and semantic equivalence), abuse testing (fuzzing and limits), and integration
   evidence (project workflow and Ec code). For data round trips, compare **typed semantics**, not
   bytes, comments, or mapping key order. Generative tests are bounded by their generators and
   oracles; they are not formal proofs.


## Further references by decision

Read only the reference relevant to the unresolved decision. Research registers describe imported authorship, not current execution.

- [Test Scenarios and Oracles — Ec YAML Resource Contracts](checks-and-examples.md): checks and examples.
- [Evidence Matrix — `ec-declarative-resource-schemas`](sources-and-decisions.md): source provenance or a disputed recommendation.
