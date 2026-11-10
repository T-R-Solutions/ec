# Verificação Ec e execução nativa

`automate.sh` coordena tarefas do produto. `toolbox.sh` descobre `TOOLBOX_BIN` ou
`toolbox` no PATH e encaminha argumentos/exits à CLI pública. O template comum
`shared-toolbox/templates/consumer/toolbox-native.sh` foi adotado com um guard
adicional: override definido como vazio também falha, sem fallback.

O operador prepara/qualifica/ativa ferramentas, browsers, packs, bases e caches
na plataforma. Ec fornece seleção, regras, configurações nativas, dependências
da aplicação, serviços, relatórios e aprovação. Não há instalação ou update em
gates, imports de internals, adapters locais ou seleção de versão pelo projeto.
Na instalação selecionada, o contrato principal está em
`docs/consumer-integration.md` (integração) e `docs/application.md` (operação).
Consulte o [guia de preparação](../docs/development/verification-tools.md)
para pré-requisitos, ambiente e limites de operação.

## Entradas e execução

```bash
export TOOLBOX_ROOT=/caminho/da/instalacao/shared-toolbox
export TOOLBOX_BIN="$TOOLBOX_ROOT/bin/toolbox"
bash scripts/toolbox.sh doctor
bash scripts/toolbox.sh central list
bash scripts/toolbox.sh central status
bash scripts/automate.sh plan
bash scripts/automate.sh run
```

A execução segue a [política canônica](../docs/development/task-verification.md).
`plan` não executa ferramentas.
Filtros continuam disponíveis para diagnóstico, sem substituir a validação final.

TOOLBOX_ROOT é uma conveniência do shell para definir TOOLBOX_BIN; o wrapper lê
TOOLBOX_BIN ou descobre toolbox no PATH. A seleção de TOOLBOX_STATE, Docker e
SELinux é descrita no [guia de preparação](../docs/development/verification-tools.md).

Todos os comandos de ferramenta usam esta forma, em argv separado:

```bash
mkdir -p .local/reports
run_dir=$(mktemp -d "$PWD/.local/reports/ruff.XXXXXXXX")
bash scripts/toolbox.sh exec --tool ruff --project-root "$PWD" \
  --results "$run_dir/results" --diagnostic-file "$run_dir/diagnostic.json" \
  --network none --timeout 600 -- \
  check --no-fix --config /project/scripts/config/native/ruff/ruff.toml /project/tools
```

O exemplo usa árvore ativa explicitamente escolhida. Os gates criam snapshot
Ec por tarefa, com manifesto SHA-256/modos. O snapshot não é requisito da
plataforma. Cada raiz de snapshot tem sua própria chave de cache central, sem
promessa de reutilização entre runs. `/project` é ro; `/out` contém resultados
selecionados. Não há `/src`
ou `/out/work`, socket, host network, dispositivos, home ou credenciais implícitos.
Correções são operações distintas, com `--project-write` solicitado explicitamente;
nenhum gate de análise usa escrita em fontes.

## Gates e argumentos nativos

A lista completa, incluindo cada token, está em
[tools/quality/native_gates.py](../tools/quality/native_gates.py). Python stdlib
trata JSON, snapshots e os sinais de um gate; não coordena a suíte nem implementa
o executor. O [catálogo](stacks/ec/catalog.sh) mantém obrigatoriedade e dependências.

| Gate Ec | Ferramenta e comando nativo | Avaliação Ec |
| --- | --- | --- |
| ruff | `ruff check --no-fix --no-respect-gitignore --output-format json --config … ARQUIVOS` | Python ativo explicitamente inventariado; noqa revisados preservados |
| shellcheck | `shellcheck --format=json1 --rcfile=… -- ARQUIVOS` | Todos os scripts; rcfile e diretivas locais preservados |
| secrets-rules / secrets | `gitleaks dir --config … --ignore-gitleaks-allow --redact=100 --exit-code 10 --report-format json --report-path /out/native.json /project` | Positivo inline, negativo e allowlist revisada; nenhum ignore pessoal |
| sast-rules / sast | `opengrep scan --test --json --strict …` / `scan --error --disable-nosem --no-git-ignore --max-target-bytes 0 … ARQUIVOS` | Fixtures positivas/negativas; inventário exato Rust/TS/Vue, sem skipped/errors |
| dependencies | `osv-scanner scan source --offline --all-packages --format json --config … --lockfile …` | Três locks completos; database_metadata do diagnóstico, duas gerações upstream, menor data <=24h |
| trivy | `trivy version --format json`; `filesystem --scanners vuln,misconfig,secret --list-all-pkgs --include-dev-deps --include-non-failures … /project/application` | Três categorias com positivo/negativo, três locks; sem updates, supressões ou filtro de severidade; DB <=24h/NextUpdate e Checks Bundle externo identificado, ro e estável |
| codeql | `codeql database create`, `resolve database --format=json`, `database analyze --format=sarifv2.1.0 …` | Três bancos Ec, suites security-extended sem pins independentes; SARIF completo, sem avisos ou supressões |
| playwright | `playwright test --config=/project/scripts/config/native/playwright/playwright.config.mjs` | Dois testes de API simulada, Chromium real, sem skipped/flaky |
| grype (opcional) | `syft scan dir:/project/application --output syft-json` seguido de `grype sbom:/inventory.json --config … --output json` | Todos os achados; base nativa <=24h e inventário dos três locks |
| bandit (opcional) | `bandit --ignore-nosec --ini /project/.bandit -c … -f json -o /out/native.json ARQUIVOS` | Sem nosec, baseline, exclusão ou severidade mínima; inventário completo |
| syft (opcional) | `syft scan dir:/project/application --output syft-json` | Estado `measured`, contagem de artefatos; nenhum parecer de vulnerabilidade |
| yara-x (opcional) | `yr scan REGRAS ARQUIVO` | Positivo/negativo EICAR e execução por arquivo; alcance limitado à sentinela declarada |

Os complementares continuam selecionáveis com `--check grype|bandit|syft|yara-x`.
Gitleaks e Opengrep exigem aprovação dos respectivos testes de regras antes do scan.

## Seleção, exceções e relatórios

O gate Geiger usa a fonte 0.14.0 com a
[correção Ec de descoberta](../tools/quality/vendor/cargo-geiger/EC-PATCH.md),
compilada locked/offline em target privado, sem instalação global ou download.
Inclusões literais de documentação são identificadas pela AST dos consumidores;
fontes Rust geradas entram nos contadores do mesmo motor Geiger, atribuídas por
dep-info. Entradas desconhecidas, atribuição ambígua e parsing inválido falham.
O inventário de arquivos usados pelo build é mantido. Versão e hash do executável
ficam no recibo da tarefa, e fontes/lock/licenças estão versionados. Regressões
específicas da correção são executadas dentro desse gate quando a suíte é autorizada.

A cobertura Rust coleta testes Cargo e jornadas reais existentes nos mesmos
perfis LLVM do run antes de gerar o LCOV. Isso exige também o PostgreSQL dedicado,
Git e porta HTTP livre, conforme o preflight de integração. Binários instrumentados
e companheiros têm targets privados; Server encerra normalmente por stdin privada
somente no build de teste para gravar os perfis. Não altera o inventário de produção
nem o mínimo de 80%. [Coletor](../tools/quality/collect_runtime_coverage.py) e
[estado da correção](../docs/validation/geiger-coverage-remediation-20261109/report.md).

Falhas Cargo Geiger recebem um diagnóstico complementar por membro: caminho de
cada arquivo não examinado, finalidade provável, orientação de preservação e
próximo passo. `geiger-N.txt` e `geiger-N.stderr.log` preservam stdout/stderr;
o stderr é reproduzido após o término do comando daquele membro.
`geiger-N-exit.txt` mantém o código nativo e `geiger-N-diagnostic.json` registra
avisos reconhecidos, linhas, hashes e limites da classificação. O helper
[tools/quality/geiger_diagnostics.py](../tools/quality/geiger_diagnostics.py) não executa o conteúdo dos avisos nem abre
os arquivos mencionados. Caminhos desconhecidos permanecem indeterminados.
Erros sem caminho reconhecido remetem aos logs, sem serem convertidos em sucesso.
O gate continua reprovando e para no primeiro membro com erro; a lista de avisos
não comprova análise de todos os membros. Veja [como interpretar os arquivos
Geiger](../docs/development/verification-tools.md#diagnósticos-geiger).

[inputs.sh](stacks/ec/inputs.sh) define raízes ativas: application, scripts, tools,
sdd, config e docs/development, mais AGENTS.md/README.md. Não seleciona pesquisa
histórica, docs/validation, .git, .local, .aws, .codex, .agents, node_modules,
target, dist, __pycache__, .env/.env.* ou fontes de pacotes de governança.
Ruff/Bandit recebem somente Python nos diretórios declarados; SAST recebe somente
Rust/TS/Vue dos três componentes; dependências recebem exatamente os três locks.
Playwright recebe separadamente apenas o build dist inventariado.
Symlinks são recusados antes de copiar; modos e bytes são preservados e reobservados.

Ruff/ShellCheck mantêm supressões locais revisadas. Gitleaks ignora allow inline e
usa somente a allowlist específica do TOML. Opengrep desativa nosem; arquivo
.semgrepignore vazio elimina defaults de seleção no snapshot e todos os alvos
são explícitos. Bandit desativa nosec. Trivy rejeita diretivas inline e usa
ignorefile vazio. Configs OSV/Grype não contêm exceções. O validador de exceções
continua disponível, mas validar um registro não o aplica automaticamente.
Todos os achados são reprovados independentemente de severidade; não foram
criadas novas exceções. Syft produz apenas medição.

Cada tarefa guarda `sources.json`, `targets.json`, `classification.json`,
`native-executions.json` e etapas numeradas com `stdout.log`, `stderr.log`,
`diagnostic.json`, `exit.json` e `results/`. Diagnóstico é arquivo separado;
stdout contém bytes da ferramenta, nunca um envelope JSON da plataforma.
`returncode`/`native_exit_code` são preservados; classificação Ec fica separada.
Outcome, imagem, versão, base, OOM e limpeza são avaliados antes dos relatórios.
Código 124/125/130 nativo não é classificado como infraestrutura pelo número.
Troca central de imagem/base entre etapas invalida o gate.

Supervisor usa ambiente selecionado, omite argv dos metadados e mantém streams
privados (diretórios 0700, arquivos criados sob umask 077). Relatórios de segredos
Gitleaks usam redação nativa; Trivy remove conteúdo de segredos do relatório
persistido após a captura privada. Ferramentas podem imprimir dados recebidos;
nenhuma garantia de redação universal é feita para logs brutos. Não passar
segredos em argumentos; usar arquivo ro/variável explicitamente selecionada
quando um comando próprio realmente necessitar. Os gates atuais não precisam
de credenciais do PostgreSQL.

Cada chamada tem timeout do executor, dentro do prazo da tarefa. CodeQL tem
14400s totais e até 1800s por criação/análise. Há reserva de 120s para encerramento
e limpeza antes de kill externo. Cancelamento/timeout/cleanup failed nunca aprovam.
SIGKILL/queda do host exigem recuperação manual pelo UUID do diagnóstico; não há
garantia de limpeza nesse caso. Não mudar ACL/grupos/socket nem executar prune.

## Referências

- [Inventário de scripts](../docs/development/scripts-inventory.md): entradas, consumidores, descoberta e auxiliares opcionais.
- [Preparação e operação](../docs/development/verification-tools.md): ambiente, mounts, bases e Checks Bundle externo.
- [Registros de análise e execução](../docs/README.md#evidências-e-pesquisa): observações datadas, limites e recuperação.
