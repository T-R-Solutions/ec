# Repository Guidelines

## Project Structure & Module Organization

This checkout contains TeamCity research and an independent CI-platform specification, not a production server or agent.

- `docs/`: product, design, screen, and image guidelines.
- `reverse_engineering_teamcity/tools/`: Python analysis utilities and Java bytecode indexers.
- `reverse_engineering_teamcity/teamcity-reverse-engineering/`: numbered research chapters, JSON evidence, and the SQLite symbol index.
- `reverse_engineering_teamcity/ci-platform-research/`: requirements, architecture decisions, acceptance scenarios, and implementation plans. Its `04-validation/` directory contains tests, the Rust prototype, and generated findings.

## Build, Test, and Development Commands

Run commands from the repository root. Python 3 is required; model validation also requires `rustc` supporting edition 2024.

- `python3 reverse_engineering_teamcity/tools/query.py BuildQueueImpl`: search the symbol database; append `--full` for detailed records.
- `python3 reverse_engineering_teamcity/ci-platform-research/04-validation/tests/validate.py`: check document references and compile/run Rust contract tests; updates findings and research state.
- `python3 reverse_engineering_teamcity/build_research_v2.py`: regenerate research documents and verify selected artifact hashes. Inspect prerequisites first: it requires `/opt/jvm/TeamCity` and referenced evidence files.

Older documentation and utilities reference `rea/`. Check those paths before execution; the current directory is `reverse_engineering_teamcity/`. No application development server or package-level build manifest is present.

## Coding Style & Naming Conventions

Use four-space indentation for new Python and Rust code, `snake_case` functions, and descriptive names. Preserve surrounding style when editing legacy utilities. Serialize research JSON with two-space indentation and a trailing newline. Keep numbered chapter directories, identifiers, and `ADR-001.md` naming consistent. Follow the existing Portuguese prose in research documents. No repository-wide formatter or linter configuration is present.

## Testing Guidelines

Validation uses Python assertions/checks and Rust's built-in `#[test]` harness. Name tests after observable behavior, such as `old_owner_cannot_finish_after_retry`. Add relevant contract cases when changing the prototype and keep requirement/evidence references valid. No percentage coverage threshold is configured. Model tests do not establish database, transport, concurrency, or production acceptance guarantees; report those limits explicitly.

## Commit & Pull Request Guidelines

Git history is unavailable in this checkout, so no historical commit convention can be verified. Use short, imperative commit subjects. PRs should describe the change, affected requirements or ADRs, validation commands/results, and remaining gaps. Link relevant issues and evidence; review generated changes before submission.

## Evidence & Configuration

Preserve artifact hashes and distinguish static findings from runtime observations. Keep credentials out of fixtures and reports. Use disposable environments for future process-isolation tests.
