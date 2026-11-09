# Repository Guidelines

## Scope and Architecture

This repository is an independent shared tool environment. Keep application tests, rules, exceptions, fixtures, and approval policies in consumers. Do not import product governance or directory structures from other repositories. Docker is the primary executor; each tool has its own Debian slim image. Do not introduce a central database, permanent service, or combined scanner image.

## Project Structure

Use `tools/<tool>/` for its manifest, Dockerfile, adapter, and operational notes. Put separately audited incompatible versions under `tools/<tool>/versions/<version>/`. Keep each tool’s persistent mounted data under its own `tools/<tool>/volume/`, ignored by Git. `.local/state/` holds only run indexes and general locks; `--state` must not relocate tool volumes. Shared execution belongs in `toolbox/`; the entrypoint is `bin/toolbox`. Keep consumer samples in `examples/`, verification in `tests/`, documentation in `docs/`, and observed evidence in `evidence/`.

## Development and Verification

Run `python3 -m unittest discover -s tests -v` for platform logic. Run `bin/toolbox validate --config examples/consumer-a/toolbox.json` for configuration diagnostics. Run `python3 tests/acceptance.py --state /absolute/central/state` only with real prepared Docker images. Missing images must produce failures, never simulated scanner success.

## Implementation Conventions

Use Python 3.11+ standard library, four-space indentation, descriptive snake_case functions, and argument arrays for subprocesses. Keep configuration versioned and reject unknown fields. Preserve stdout, stderr, native reports, image IDs, database identities, and original exit codes. Never interpret operational failure, skipped execution, or an unqualified measurement as approval.

## Research and Images

Read official version-specific documentation before integrating a tool. Record URLs, sections, consultation dates, decisions, and unresolved questions in `docs/references.json` and its readable matrix. Investigate source and test practically when documentation is insufficient. Require actual base digests and official artifact hashes; do not fabricate them or use mutable latest versions. Normal runs must never install packages or update databases.

## Changes and Review

Use focused, imperative commit subjects. Pull requests should explain behavior, relevant sources, tests performed, and remaining limitations. Distinguish implemented, documented, executed, and observed behavior. Keep source mounts read-only, use host UID/GID, isolate writable state, and preserve older consumer image locks during upgrades.
