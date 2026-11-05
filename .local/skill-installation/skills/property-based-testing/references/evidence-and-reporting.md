# Evidence and reporting for generated tests

Use the consumer's existing acceptance and evidence records. No SDD installation,
feature directory, particular filenames or Git repository is required. An optional
external bridge can map this method to the consumer's SDD when selected.

## Bind the claim to observations

Record property and independent oracle; input domain and generator version; seed/search budget; original and reduced witness; discarded cases and surviving negative controls. Record actual input identities by content hash or another
checked version mechanism, the environment, command/method, result, artifact and
material limitations. A repository revision alone does not cover uncommitted inputs.

Identify the subject: structural check, deterministic reference model, real runtime
integration, agent evaluation or production observation. One tier does not establish
another. Missing tools, fixtures or integration remain unverified. An incomplete
observation is inconclusive; inapplicability needs a reason under the project policy.

## Preserve the oracle and failures

Keep expected outcomes independent from the implementation under test. Preserve
failed cases, unavailable checks and contrary observations. Reexamine results after
relevant inputs change; do not update hashes to make old results appear current.
A digest checks content identity, not publisher authenticity or semantic correctness.

Use negative controls where they test a consequential oracle. A finite generated
run does not prove universal correctness, a graph order does not prove runtime
scheduling, and a simulated deployment does not prove provider or data recovery.
Report only the claims that apply to the current task.
