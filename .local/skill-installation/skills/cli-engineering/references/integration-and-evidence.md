# Multi-layer validation and release gate

> Use the destination's existing requirements and evidence format; named workflow documents are examples, not prerequisites. Optional SDD mapping is configured outside this skill. Ec-specific rules below apply only to an Ec consumer whose current contract establishes them. Commands, fixture paths and historical execution claims from the source package are illustrative or imported evidence unless the corresponding files are present and independently checked; they are not current test results or bundled executable tools.

## Layer 1 — portable contract fixture (provided)

Run `python validation/run_model_tests.py` against a synthetic `democtl` and SQLite store. It exercises argument grammar, status/exit contracts, dry-run and confirmation, redaction, TTY flags, revision and idempotency with concurrency. The `server_finish` helper is never exposed as a command. Test names and observed output are stored under `validation/outputs/`; these are examples, not Ec contracts.

Run `python validation/run_mutations.py` to check that deliberate logic defects fail targeted oracles. This is not a security audit and only the altered cases are covered.

## Layer 2 — package/project workflow integrity

`run_checks.py`, `run_structural_negative_tests.py`, `run_fail_closed_tests.py`, `run_project workflow_fixture.py --source-zip /path/to/skills_zip.zip`, `build_package.py` and `check_zip.py` validate packaging and bridge routing without touching product data. SHA-256 detects unmodified package inconsistencies; it cannot authenticate a publisher without a signed trust root.

## Layer 3 — real product integration (NOT EXECUTED)

Obtain approved CLI command specifications, compiled Rust binary, actual server API, provider credentials in a secure test environment and sandbox test identities. Verify cross-platform terminal tests, real authentication/denial, protected resources, concurrent submissions, backend atomicity, post-restart recovery, network-failure ambiguity, audit correlations and rollback behavior (only if supported). Record version, revision, environment, principal, command and redacted stdout/stderr/exit, server operation ID and AC evidence.

## Exit gate

No product command is complete until its actual REQ/AC map is proven; a CLI that only works against SQLite test data cannot be labeled a working Ec CLI. Missing binaries, policy, real providers, user testing or production-like environment mean `NOT EXECUTED`/`NÃO VERIFICADO`.
