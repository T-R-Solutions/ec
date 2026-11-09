# Matriz de documentação oficial

Consulta em 2026-11-06. `read` significa conteúdo original aberto e lido; não significa que a integração foi executada. Fontes correntes e históricas não substituem o contrato da versão selecionada. As lacunas bloqueiam a integração correspondente.

| Solução | Versão/escopo | Fonte e seção | Estado | Decisão apoiada |
|---|---|---|---|---|
| Docker | 29.7.2 observed; online current | [Other Linux distributions](https://docs.docker.com/engine/install/) | read | Use distribution documentation for Tumbleweed, not an unlisted Docker upstream installation recipe. |
| Docker | online current | [Options and exit status](https://docs.docker.com/reference/cli/docker/container/run/) | read | Explicit pull policy, readonly mounts, UID/GID, network, stop and original container state. |
| Docker | online current | [Description](https://docs.docker.com/reference/cli/docker/image/inspect/) | read | Capture immutable local image ID, architecture and Size; not registry transfer size. |
| Docker | online current | [Description and compressed export](https://docs.docker.com/reference/cli/docker/image/save/) | read | Migration via save/load; gzip export size is not registry compressed layer size. |
| Debian | 13 trixie | [Life cycle and architectures](https://www.debian.org/releases/trixie/) | read | Choose trixie-slim; start with amd64 only; compatibility per tool still needs builds. |
| Debian | snapshot 20251015T000000Z proposed | [Archive usage](https://snapshot.debian.org/) | unread: timeout | APT snapshot proposed; content could not be read, snapshot reachability and packages not verified. |
| openSUSE | Tumbleweed 20261103 observed | [Installation](https://en.opensuse.org/SDB:Docker) | unread: HTTP 403 | Required installation documentation inaccessible; do not assert confirmed installation commands. |
| openSUSE | Tumbleweed current | [Foreword](https://doc.opensuse.org/documentation/tumbleweed/) | read | Rolling distribution; page recommends Tumbleweed knowledgebase; does not establish Docker installation commands. |
| Gitleaks | 8.24.3 | [Installing; Usage; Git/Dir; Configuration](https://raw.githubusercontent.com/gitleaks/gitleaks/v8.24.3/README.md) | read | Release binary, git auxiliary executable, explicit finding exit 42, JSON, redaction; no DB download required. |
| Ruff | 0.11.13 | [Installation; Usage; Configuration](https://raw.githubusercontent.com/astral-sh/ruff/0.11.13/README.md) | read | Official executable distribution; consumer TOML; scanner needs no Python runtime. |
| Ruff | current docs; reconcile 0.11.13 by real execution | [Exit codes](https://docs.astral.sh/ruff/linter/) | read | 0 clean, 1 findings, 2 operational; require valid JSON; disable fixes and cache. |
| ShellCheck | 0.10.0 | [Formats; Exit status; rcfile](https://raw.githubusercontent.com/koalaman/shellcheck/v0.10.0/shellcheck.1.md) | read | Explicit file list, json1 comments, .shellcheckrc; validate exit and report together. |
| Bandit | 1.8.3 release; current CLI docs | [Synopsis; Options](https://bandit.readthedocs.io/en/latest/man/bandit.html) | read | Python AST scanner, -c configuration, JSON and baseline; dependency closure and exact-version exit behavior remain unresolved. |
| Bandit | 1.8.3 | [Release](https://github.com/PyCQA/bandit/releases/tag/1.8.3) | read | Release existence confirmed; release page alone is not an integration contract. |
| Bandit | 1.8.3 | [main](https://raw.githubusercontent.com/PyCQA/bandit/1.8.3/bandit/cli/main.py) | unread: cache miss | Version-specific error/exit handling could not be read. |
| OSV-Scanner | 2.0.2 | [Installation; Scan source; Offline](https://raw.githubusercontent.com/google/osv-scanner/v2.0.2/README.md) | read | Official releases and source installation; v2 source scanning; contains older offline syntax, reconcile exact CLI before enabling. |
| OSV-Scanner | current V2, differs from 2.0.2 | [Post-extraction; Offline](https://google.github.io/osv-scanner/usage/) | read | Online V2 docs use offline-vulnerabilities, differing from tagged README; block guessed adapter flags. |
| Opengrep | 1.31.0 | [Distribution; Installation; Getting started](https://raw.githubusercontent.com/opengrep/opengrep/v1.31.0/README.md) | read | Self-contained Nuitka binaries, JSON/SARIF and local rules; shared-library closure and version-specific error behavior still require audit. |
| Trivy | 0.61.1 | [Release](https://github.com/aquasecurity/trivy/releases/tag/v0.61.1) | read | Release exists; assets and exact-version commands not sufficiently verified. |
| Trivy | 0.55 historical; not sufficient for 0.61.1 | [Network; Offline; DB](https://www.trivy.dev/docs/v0.55/guide/advanced/air-gap/) | read | Databases and skip-update flags exist historically; this does not authorize an unverified 0.61.1 integration. |
| Trivy | 0.61.1 | [Installation](https://trivy.dev/v0.61/getting-started/installation/) | unread: HTTP 404 | Versioned documentation request returned 404 after discovery; leave gap explicit. |
| Grype | 0.119.0 | [Installation; Basics](https://raw.githubusercontent.com/anchore/grype/v0.119.0/README.md) | read | Official release binaries; filesystem/image/SBOM targets; no need to expose Docker socket for directory scan. |
| Grype | 0.119.0 generated docs | [db; check-for-app-update](https://oss.anchore.com/docs/reference/grype/configuration/) | read | Disable implicit DB/application updates; select cache and explicit age policy; preserve native DB. |
| Grype | current docs | [Official builds; GitHub releases](https://oss.anchore.com/docs/installation/grype/) | read | Extract executable from official archive; signature verification is separate from checksum verification. |
| Grype | current docs | [Updating; Database age](https://oss.anchore.com/docs/guides/vulnerability/database/) | read | Local DB requires explicit refresh; native age validation must not be silently disabled. |
| Syft | 1.23.1 | [Release](https://github.com/anchore/syft/releases/tag/v1.23.1) | read | Release exists; tag alone insufficient to validate full integration. |
| Syft | current README | [Features; Basics](https://raw.githubusercontent.com/anchore/syft/main/README.md) | read | SBOM generation, SPDX/CycloneDX/Syft JSON; inventory measurement, not vulnerability approval. |
| YARA-X | 1.0.0 | [Release](https://github.com/VirusTotal/yara-x/releases/tag/v1.0.0) | read | Stable release exists; exact-version CLI output schema and match exit behavior unresolved. |
| YARA-X | current docs | [Pre-built binaries; Source](https://virustotal.github.io/yara-x/docs/intro/installation/) | read | Official release binaries or Rust build; do not add Python/Node merely because documentation build uses them. |
| YARA-X | current docs | [scan; compile; output-format](https://virustotal.github.io/yara-x/docs/cli/commands/) | read | Executable is yr; consumer supplies rules; JSON schema and error codes must be checked for 1.0.0. |
| Playwright | 1.56.1 | [engines; dependencies](https://raw.githubusercontent.com/microsoft/playwright/v1.56.1/packages/playwright/package.json) | read | Exact Node engine floor and playwright-core package match; use Node 22.20.0 candidate. |
| Playwright | 1.56.1 | [calculatePlatform](https://raw.githubusercontent.com/microsoft/playwright/v1.56.1/packages/playwright-core/src/server/utils/hostPlatform.ts) | read | Debian 13 amd64/arm64 platform handling exists; slim library closure still requires actual build. |
| Playwright | 1.56.1 | [Debian dependency tables](https://raw.githubusercontent.com/microsoft/playwright/v1.56.1/packages/playwright-core/src/server/registry/nativeDeps.ts) | read | Use selected-browser install --with-deps during preparation; no manual guessed library list. |
| Playwright | current docs, supplemental | [Own image; version matching; networking](https://playwright.dev/docs/docker) | read | Official Ubuntu container is reference only; own Debian slim build required. Headless does not require host browsers. |
| Playwright | current docs, supplemental | [APIRequestContext; Configuration](https://playwright.dev/docs/api-testing) | read | HTTP fixture works without page/browser fixture; demonstration still requires real runtime execution. |
| Node.js | 22.20.0 | [Linux downloads; signed SHASUMS](https://nodejs.org/en/download/archive/v22.20.0) | read | Official x64 archive; verify checksum; no claim of signature verification. |
| Node.js | 22.20.0 | [Loading from node_modules folders](https://nodejs.org/download/release/v22.20.0/docs/api/modules.html) | read | Explicit staged node_modules link; no global npm or NODE_PATH claim; ESM proof pending. |
| Python | 3.13 on host observed; runtime not selected for Bandit | [Creation and portability](https://docs.python.org/3.13/library/venv.html) | read | Host CLI uses stdlib; any Bandit venv belongs in its image, not consumer or global host. |
| pip | current docs; not yet used | [Hash checking](https://pip.pypa.io/en/stable/topics/secure-installs/) | read | Future Python integration must lock all transitive packages with hashes; do not introduce unpinned pip install. |
| Go | not installed by core | [Download and install](https://go.dev/doc/install) | read | Core uses release binaries; Go compiler would belong only in a source-build stage if needed. |

Os JSONs de preparação registrarão URLs efetivamente baixadas, hashes oficiais, tamanhos, pacote Debian e versões observadas. Ainda não existem esses registros de imagens: Docker inacessível e DNS indisponível no shell. Não foram inventados digests, checksums ou tamanhos.

## SELinux — 2026-11-06

`TOOLBOX_SELINUX_LABEL=private` habilita volumes `ro,Z` para fonte/base e `rw,Z`
para saída. O padrão permanece sem relabel. Usar somente diretórios dedicados:
`Z` altera o rótulo no host; o consumidor Ec fornece snapshot descartável.
[Docker: Configure the SELinux label](https://docs.docker.com/engine/storage/bind-mounts/),
consultado em 2026-11-06. Não usa privileged nem desabilita SELinux.

- ShellCheck 0.11.0: [release assets](https://github.com/koalaman/shellcheck/releases/tag/v0.11.0). Release API supplies SHA256; old 0.10.0 preserved; substitution test rejects mismatched download.

- Opengrep 1.33.1: [release and bundled CLI help](https://github.com/opengrep/opengrep/releases/tag/v1.33.1). Local rules, test/scan modes, metrics off, strict errors, disabled suppressions; real execution required.

- OSV-Scanner 2.7.0: [Offline mode and manual database download](https://raw.githubusercontent.com/google/osv-scanner/v2.7.0/docs/offline-mode.md). Explicit HTTPS database update; immutable local snapshot, age, exact lockfile inventory; scan offline.
