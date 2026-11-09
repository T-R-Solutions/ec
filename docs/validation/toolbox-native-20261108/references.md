# Referências consultadas e limites

Consulta em 08/11/2026. Nenhuma ferramenta central estava ativa: não há versão
real executada de scanner/browser a atribuir a estes comandos. As versões abaixo
são candidatas do catálogo consultado, sem pin de instalação no consumidor.
Após ativação, confrontar --help nativo/diagnóstico com estas fontes e homologar
os modos usados. Erro de flag/formato nunca se converte em aprovação.

| Componente | Fonte primária consultada | Aplicação / limite |
| --- | --- | --- |
| Toolbox | docs/consumer-integration.md, native-execution.md, operations.md, native-validation.md e seções vigentes de docs/doc.md/README.md na instalação central | CLI nativa, /project ro, /out, registros, sinais e preparo separado; histórico STIC não é requisito |
| Ruff candidata 0.11.13 | [Configuração versionada](https://raw.githubusercontent.com/astral-sh/ruff/0.11.13/docs/configuration.md) | Config explícita e controle de fixes/ignores; versão efetiva ainda não observada |
| ShellCheck candidata 0.11.0 | [Manual versionado](https://raw.githubusercontent.com/koalaman/shellcheck/v0.11.0/shellcheck.1.md) | json1, rcfile e delimitador; diretivas locais revisadas preservadas |
| Gitleaks candidata 8.24.3 | [CLI/config](https://raw.githubusercontent.com/gitleaks/gitleaks/v8.24.3/README.md), [redação](https://raw.githubusercontent.com/gitleaks/gitleaks/v8.24.3/report/finding.go) | dir, ignore-gitleaks-allow, redact100, exit-code, relatório JSON; REDACTED é marcador nativo |
| Opengrep candidata 1.33.1 | [CLI versionada](https://raw.githubusercontent.com/opengrep/opengrep/v1.33.1/src/osemgrep/cli_scan/Scan_CLI.ml) | scan/test/json, strict, disable-nosem, no-git-ignore, alvos explícitos e max-target-bytes0; não usar flag metrics inexistente |
| OSV candidata 2.7.0 | [Usage](https://raw.githubusercontent.com/google/osv-scanner/v2.7.0/docs/usage.md), [offline](https://raw.githubusercontent.com/google/osv-scanner/v2.7.0/docs/offline-mode.md) | scan source, offline, all-packages, config/lockfile/output-file e caches centrais |
| Trivy candidata 0.76.2 | [CLI](https://raw.githubusercontent.com/aquasecurity/trivy/v0.76.2/pkg/commands/app.go), [version info](https://raw.githubusercontent.com/aquasecurity/trivy/v0.76.2/pkg/version/version.go), [misconfig](https://raw.githubusercontent.com/aquasecurity/trivy/v0.76.2/pkg/flag/misconf_flags.go) | version --format json; scanners, não falhas e UpdatedAt/NextUpdate principal/Java |
| Bandit candidata 1.8.3 | [CLI versionada](https://raw.githubusercontent.com/PyCQA/bandit/1.8.3/bandit/cli/main.py) | ignore-nosec, ini explícito, config, JSON, inventário/erros |
| Grype candidata 0.119.0 | [README versionado](https://raw.githubusercontent.com/anchore/grype/v0.119.0/README.md) | diretório/JSON e validação nativa de DB; formato efetivo precisa de homologação |
| Syft candidata 1.23.1 | [Documentação oficial CLI](https://oss.anchore.com/docs/reference/syft/cli/) e módulo/adapter existente consultado somente como inventário | Versão tag README retornou Cache miss; não alegar conferência completa da CLI desta versão até help/execução nativos |
| YARA-X candidata 1.0.0 | [Comandos oficiais](https://virustotal.github.io/yara-x/docs/cli/commands/) e módulo/receita da plataforma consultados | Source tag CLI retornou Cache miss; usa somente scan RULE FILE e texto padrão, sem inventar schema JSON. Formato/exits permanecem não homologados |
| Playwright candidata 1.56.1 | [JSONReporter versionado](https://raw.githubusercontent.com/microsoft/playwright/v1.56.1/packages/playwright/src/reporters/json.ts), contrato ESM/Node da Toolbox | runner ESM absoluto, testes/results/stats completos; browser real não executado |
| CodeQL candidata 2.27.3 | [create](https://docs.github.com/en/code-security/reference/code-scanning/codeql/codeql-cli-manual/database-create), [analyze](https://docs.github.com/en/code-security/reference/code-scanning/codeql/codeql-cli-manual/database-analyze), [resolver e opções SARIF da implementação oficial](https://raw.githubusercontent.com/github/codeql-action/main/src/codeql.ts), [resolve database](https://docs.github.com/en/code-security/reference/code-scanning/codeql/codeql-cli-manual/resolve-database), [requisitos](https://codeql.github.com/docs/codeql-overview/system-requirements/), [Rust build mode](https://docs.github.com/en/code-security/reference/code-scanning/codeql/build-options-for-compiled-languages) | Bundle central, Rust build-mode none ainda requer cargo/rustup/build scripts/macros; manual corrente exige confrontar CLI2.27.3 real, ainda inativa |

As fontes consultadas não comprovam homologação de receitas/imagens nem a
compatibilidade do runtime da aplicação. Não houve aquisição de dependências
ou preparo/ativação central para resolver essas lacunas.
