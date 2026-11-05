# Confirmation, preview and operational risk

> Ec-specific rules below apply only to an Ec consumer whose current contract establishes them. Commands, fixture paths and historical execution claims from the source package are illustrative or imported evidence unless the corresponding files are present and independently checked; they are not current test results or bundled executable tools.

A safe command design separates **read**, **preview**, **request**, **approval**, **execution** and **observation**. A `--dry-run` should document whether it is local syntax check, server-side validated preview, estimated impact or a full simulation. It must not mutate resources or consume an idempotency slot in the tested contract. `--yes` or similar explicit confirmation only indicates user intent, never bypasses server permissions or policy gates.

For high-risk mutations, present scope, target, current revision, requested change, approvals and impact. Noninteractive invocation must be deterministic and fail closed when confirmation is missing, under the approved spec. A failed or interrupted network call may have reached the server; never assume it did not, and avoid silent retries without operation identity.

Review race between preview and commit; use conditionals or revision checks where available. If the command is acting as an adapter for Ec, all desired-configuration mutations must go through the canonical YAML revision/validation/authorization/publication path. Never invent a CLI-only configuration database, bypass approvals or claim rollback when the backend doesn't support it.
