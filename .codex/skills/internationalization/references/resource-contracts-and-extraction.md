# UI Catalog and Source-String Contract

> Commands, fixture paths and historical execution claims from the source package are illustrative or imported evidence unless the corresponding files are present and independently checked; they are not current test results or bundled executable tools.

Resource keys are stable implementation identifiers. The natural-language strings (and only their semantic surface) are translated. For each key store: meaning, UI context, screenshots or description, source and reviewer, variables and type, allowed markup/component slots, plural/gender agreement, max risk areas and release approval. A translation catalog **does not** define backend IDs, YAML file formats or audit retention.

**Static checks:** key completeness by release stage, unexpected keys, placeholder parity, type mismatch (text/plural/select), plural `other`, unknown components, accidental HTML, repeated values with different grammatical contexts, corrupt Unicode, and forbidden control characters in technical identifiers. Never silently substitute a fallback text and then claim the catalog is complete. Enforce a reviewed fallback policy.

**Runtime checks:** an unsupported/malformed locale cannot read an arbitrary resource file; missing values should be surfaced in development; backend errors must not be masked as translation errors. The translation bundle may be fetched asynchronously; validate before applying and discard stale switch results. Content without certified translation can be marked as partial rather than silently claimed as released.

**Fixture contract:** `validation/ts/i18n-model.ts` shows a small catalog and placeholder validation method. It is intentionally not a general ICU MessageFormat parser; production apps should use a maintained i18n library with validated formats.

**Test negatives:** wrong variable name, changed `count` semantics, missing selector arm, unsafe HTML in user interpolation, and catalog load race. **Result:** key inventory, unresolved strings, typed parameters, and release-blocking omissions mapped to acceptance criteria.
