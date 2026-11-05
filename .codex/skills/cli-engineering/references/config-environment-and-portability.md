# Environment, portable configuration and distribution

> Ec-specific rules below apply only to an Ec consumer whose current contract establishes them.

Use explicit CLI flags for invocation-specific behavior, documented environment variables for user preferences, and OS-appropriate persisted state only when product policy authorizes it. Do not silently read project-local secret files. XDG 0.8 documents Unix-like config/state/cache directories, including defaults and absolute path requirements; it is not a Windows convention. Verify Windows/macOS path and file permission equivalents in their platform documentation before implementation.

**Ec boundary:** if a CLI exposes desired configuration, it must read/write the approved **YAML canonical resource** through the same validation, review, versioning, authorization and reconciliation service. It cannot offer JSON/TOML as alternative declarative files. A JSON read-only or machine-output format is not configuration. CLI access to operational status should be a separate server query with role checks.

Portability test matrix: OS and architecture, terminal/TTY vs pipe, shell quoting and completion, locale and encoding, home/config path behavior, Unicode filenames, target CI environment, offline help, installation and uninstallation, semantic versions, platform-specific exit interpretation and credential storage.
