# Configurações de verificação do Ec

As configurações desta aplicação pertencem ao Ec. `.local/usage.md` descreve o
executor compartilhado, cujas integrações candidatas podem falhar no preparo real.
`toolbox.json` seleciona versões exatas e `toolbox.images.lock.json` registra imagens
locais imutáveis, receitas, checksums e versões observadas. Um lock não comprova scan.

## Execução e preparo

```bash
bash scripts/automate.sh plan
bash scripts/automate.sh run
bash scripts/automate.sh run --check ruff --check shellcheck
bash scripts/automate.sh run --check playwright
bash scripts/automate.sh run --check secrets
bash scripts/prepare.sh tools ruff gitleaks playwright
bash scripts/prepare.sh rustsec
```

O preparo usa rede explicitamente; o runner não instala ferramentas, imagens ou
bases. Antes do primeiro preparo, o toolbox exige `bin/toolbox lock-base` em sua
própria raiz. Essa operação modifica a configuração compartilhada, não o Ec.
`EC_TOOLBOX_BIN` permite selecionar a CLI instalada; o padrão segue `.local/usage.md`.
Não chamar o toolbox diretamente sobre a raiz inteira do Ec para scans: a ponte
cria snapshot sem caches, histórico, credenciais locais ou runs anteriores.

Em SELinux enforcing, a ponte habilita `TOOLBOX_SELINUX_LABEL=private`: fonte somente
leitura e saída dedicada recebem rótulo `Z`. Isso exige a correção do toolbox
registrada no relatório desta alteração; não desabilita SELinux nem usa privileged.
Os snapshots e outputs podem ser relabelados; o checkout original não é montado.
O Docker daemon continua sendo uma capacidade privilegiada do operador.

## Ferramentas e fontes oficiais consultadas em 2026-11-06

| Solução | Configuração e contrato Ec | Fonte oficial |
| --- | --- | --- |
| Cargo/rustup | Canal de cada workspace, cache separado e plugins globais no PATH; registra versão/hash do plugin efetivamente chamado | [Subcomandos](https://doc.rust-lang.org/cargo/reference/external-tools.html), [Cargo home](https://doc.rust-lang.org/cargo/guide/cargo-home.html) |
| rustfmt/Clippy/test | Sem auto-fix; todos os targets/features no Clippy; lockfile e execução offline | [rustfmt](https://github.com/rust-lang/rustfmt), [Clippy](https://doc.rust-lang.org/clippy/usage.html), [cargo test](https://doc.rust-lang.org/cargo/commands/cargo-test.html) |
| cargo-machete | Workspace atual; sem lista de dependências ignoradas | [CLI e metadata](https://github.com/est31/cargo-machete) |
| cargo-deny | `config/quality/deny.toml` explícito: origens desconhecidas negadas, licenças permitidas enumeradas, advisories sem ignores, yanked negado, base com até sete dias; versões duplicadas geram aviso | [Configuração](https://embarkstudios.github.io/cargo-deny/checks/cfg.html), [advisories](https://embarkstudios.github.io/cargo-deny/checks/advisories/cfg.html), [check](https://embarkstudios.github.io/cargo-deny/cli/check.html) |
| cargo-audit | `config/quality/audit.toml` copiado para diretório dedicado `.cargo`; lockfile explícito, no-fetch, warnings negados, JSON; não herda configuração pessoal de ignores | [Config](https://github.com/rustsec/rustsec/blob/main/cargo-audit/audit.toml.example), [uso](https://github.com/rustsec/rustsec/blob/main/cargo-audit/README.md) |
| cargo-geiger | Inventário unsafe, locked/offline; medição não equivale a vulnerabilidade ou proibição de unsafe | [Uso](https://github.com/geiger-rs/cargo-geiger) |
| cargo-llvm-cov | LCOV por run, workspace e lock; precisa llvm-tools-preview do canal; não inventa limiar de cobertura | [CLI](https://github.com/taiki-e/cargo-llvm-cov) |
| Ruff 0.11.13 | `ruff.toml`: erros de sintaxe/referências e regras S; sem fix/cache; S101 dispensada somente em testes porque assert é oráculo | [Configuração](https://docs.astral.sh/ruff/configuration/), [regras](https://docs.astral.sh/ruff/rules/) |
| ShellCheck 0.10.0 | `.shellcheckrc`, lista explícita de scripts, JSON; tarefa obrigatória mesmo sem binário nativo | [Manual por versão](https://github.com/koalaman/shellcheck/blob/v0.10.0/shellcheck.1.md) |
| Gitleaks 8.24.3 | Defaults em `scripts/security/rules/gitleaks.toml`, arquivos atuais, redaction; snapshot sem ignore file e preflight rejeita marcadores inline de supressão | [Config e CLI por versão](https://github.com/gitleaks/gitleaks/blob/v8.24.3/README.md) |
| Opengrep | Motor nativo revisado 1.33.1 mantém testes de regras e inventário estrito; versão 1.31.0 do toolbox não tem adapter | [Projeto/CLI](https://github.com/opengrep/opengrep), [fonte por versão](https://github.com/opengrep/opengrep/blob/v1.33.1/src/osemgrep/cli_scan/Scan_CLI.ml) |
| OSV | Motor nativo revisado 2.7.0 mantém três locks e bases crates.io/npm offline; versão 2.0.2 do toolbox não tem adapter; registro local expira em 24h | [Config](https://google.github.io/osv-scanner/configuration/), [offline por versão](https://github.com/google/osv-scanner/blob/v2.7.0/docs/offline-mode.md) |
| Trivy | `config/quality/trivy.yaml`: vuln, misconfig e secret, todas as severidades, sem ignorar unfixed; parser conta os três tipos; CLI/bases/checks locais necessários, adapter Docker bloqueado | [Arquivo de configuração 0.61](https://trivy.dev/v0.61/docs/references/configuration/config-file/) |
| CodeQL | `config/quality/codeql.qls`: suite JavaScript security-extended; exige CLI elegível, pack e banco preparados e vinculados às fontes; não prepara banco/packs em scan | [Suites](https://docs.github.com/en/code-security/tutorials/customize-code-scanning/create-query-suites) |
| Playwright 1.56.1 | `playwright.config.mjs`: Chromium, rede none, servidor estático interno, sem only/retry, zero skips/flaky, JSON obrigatório, sem traces/screenshots/estado de sessão | [Configuração](https://playwright.dev/docs/test-configuration), [Docker](https://playwright.dev/docs/docker) |
| Docker/Debian | Imagens por digest, readonly/UID/cap-drop/no-new-privileges; snapshot APT compatível com a base | [SELinux/mounts](https://docs.docker.com/engine/storage/bind-mounts/), [Snapshots](https://snapshot.debian.org/) |

Os comandos adicionais geiger, coverage, lint, performance, build-budget, Trivy e
CodeQL continuam explícitos. ESLint e coverage frontend precisam de dependências e
scripts npm próprios, ainda ausentes; performance exige um produtor real de amostras.
Não foram criados limites numéricos de performance/cobertura/tamanho sem requisito.
A lista de licenças é uma política técnica inicial conservadora, não parecer jurídico.
Não se adicionaram ignores para fazer dependências/lints passarem.

## Segurança, falhas e limites

O perfil Rust padrão acrescenta machete/deny/audit; scripts acrescenta Ruff e
ShellCheck. Segurança mantém Gitleaks, teste de regras, SAST e OSV obrigatórios.
Opengrep/OSV/Trivy sem adapter permanecem habilitados e bloqueados; não são omissões
silenciosas. Mesmo que um adapter futuro apareça, o contrato Ec precisa ser validado
antes de aprovar sua cobertura. Ausência de imagem é exit 3; erro/relatório inválido
é exit 2; achado é exit 1; somente execução completa e sem achados é exit 0.

OSV: 24h desde o registro local é validade operacional, não prova de atualização ou
autenticidade upstream. RustSec: até sete dias desde o último commit, sem permitir
base futura. Hashes, frescor, autenticidade e eficácia são propriedades diferentes.
Atualizar as bases separadamente e rever sua procedência antes de um aceite.

O Playwright verifica comportamento do frontend com API simulada; não comprova RBAC
no backend. O harness Firefox com Server/Agent/PostgreSQL reais continua no E2E padrão.
Nenhum scanner ou conjunto destes checks certifica segurança enterprise, isolamento
para código hostil, conformidade normativa ou ausência completa de vulnerabilidades.
