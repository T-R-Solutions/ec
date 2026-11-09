# Verificação Ec e execução nativa

`automate.sh` coordena tarefas do produto. `toolbox.sh` descobre `TOOLBOX_BIN` ou
`toolbox` no PATH e encaminha argumentos/exits à CLI pública. O template comum
`shared-toolbox/templates/consumer/toolbox-native.sh` foi adotado com um guard
adicional: override definido como vazio também falha, sem fallback.
Configurações STIC, schema 2 de consumidor e locks de imagens foram retirados,
após migração de seus chamadores. O backup preserva seus bytes para recuperação.

O operador prepara/qualifica/ativa ferramentas, browsers, packs, bases e caches
na plataforma. Ec fornece seleção, regras, configurações nativas, dependências
da aplicação, serviços, relatórios e aprovação. Não há instalação ou update em
gates, imports de internals, adapters locais ou seleção de versão pelo projeto.
Na instalação selecionada, o contrato principal está em
`docs/consumer-integration.md` (integração) e `docs/application.md` (operação).
`docs/native-execution.md` é somente um encaminhamento de compatibilidade.
Consulte o [guia de preparação](../docs/development/verification-tools.md)
e as [evidências e bloqueios](../docs/validation/toolbox-native-20261108/report.md).

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
A autorização específica da tarefa de migração de 08/11/2026 está registrada
como histórico e não se estende a outras tarefas. A limpeza de 09/11/2026 fez
somente inspeção estática/manual, sem scripts de verificação ou testes.
`plan` não executa ferramentas.
Filtros continuam disponíveis para diagnóstico, sem substituir a validação final.

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
plataforma. `/project` é ro; `/out` contém resultados selecionados. Não há `/src`
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

`trivy-toolbox` foi retirado: o gate `trivy` executa as três categorias nativas.
Não há dupla contagem ou substituição por análise somente de vulnerabilidades.
Os complementares continuam selecionáveis com `--check grype|bandit|syft|yara-x`.
Gitleaks e Opengrep exigem aprovação dos respectivos testes de regras antes do scan.

## Seleção, exceções e relatórios

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

## Inventário e retenção

O [mapa vigente](../docs/development/scripts-inventory.md) registra entradas,
source/imports, descoberta, configurações e auxiliares opcionais. O
[relatório de limpeza de 09/11](../docs/validation/cleanup-20261109/report.md)
lista remoções e recuperação. Logs, runs, auditorias, fontes importadas e backups
das migrações foram preservados; `.local/cleanup-20261109/` é a área privada de
recuperação desta tarefa e está explicitamente excluída da limpeza. Os scanners
já excluem `.local/`; nenhuma exceção de análise foi criada.

## Seleção do ambiente e contrato de dados — 09/11/2026

TOOLBOX_ROOT é uma conveniência do shell para definir TOOLBOX_BIN; não é uma
configuração lida pelo wrapper. O projeto não fixa um caminho pessoal ou versão.
Nesta árvore local, a instalação consultada fica em ../dev_audit/shared-toolbox;
configure TOOLBOX_BIN para seu bin/toolbox antes da execução autorizada.
Doctor consulta o daemon; central list/status leem registros e não homologam os
gates Ec. Os READMEs em tools/NOME descrevem módulos da plataforma: não executar
seus Dockerfiles/adapters nem importar a biblioteca interna pelo projeto.

O supervisor e o gate preservam seleções explícitas TOOLBOX_STATE, DOCKER_HOST,
DOCKER_CONTEXT, DOCKER_CONFIG, DOCKER_TLS_VERIFY e DOCKER_CERT_PATH para a CLI.
Não encaminham o ambiente inteiro ao container. TOOLBOX_STATE muda somente
índices/locks; os volumes continuam sob tools/NOME/volume na instalação central.
O daemon deve enxergar os mesmos caminhos de bind mount; Docker remoto não é
uma configuração homologada por esta alteração.

TOOLBOX_SELINUX_LABEL também é preservado, sem default Ec. A opção shared da
plataforma faz relabel dos caminhos selecionados; o operador deve revisar as
árvores dedicadas antes de exportá-la. Não alterar contextos/permissões por inferência.

Além de /project ro e /out rw, a plataforma oferece /cache rw, /data e /db ro;
Trivy monta snapshots em /cache/db e /cache/java-db e o bundle externo em
/cache/policy ro. Paths absolutos do host depois de -- não são convertidos.
Os mounts extras Ec continuam explícitos: SBOM Grype ro e bancos/cache CodeQL rw.
Cada snapshot Ec por tarefa tem raiz própria e, portanto, chave de cache central
própria; não há promessa de reutilização desse cache entre runs.

OSV/Trivy usam database_metadata presente no diagnóstico nativo: schema,
snapshot, fontes/hashes, timestamp_semantics e data_timestamp. O limite Ec de
24h incide sobre a fonte obrigatória mais antiga. Não há recibo externo
EC_OSV_SCANNER_DATABASE_RECORD nem uso de prepared_at para renovar dados antigos.
Trivy exige Checks Bundle externo correspondente à imagem/versão, mount bind ro
em /cache/policy e rede none observados, sem troca entre scans do mesmo gate.
O preparo desse bundle é manutenção do operador, separada de DB e Java DB.

O exemplo Ruff usa resultados novos por chamada. stdout/stderr continuam streams
nativos; --diagnostic-file é separado. --stdout-file/--stderr-file, quando usados
em chamadas manuais, redirecionam para arquivos novos sem tee e não substituem
--results para relatórios em /out. Os gates Ec conservam captura própria privada.
Outcome, código nativo, relatório, cobertura e cleanup são avaliados em conjunto.

[Análise, alterações, fontes observadas e limites](../docs/validation/toolbox-alignment-20261109/report.md).
Nenhum teste ou scanner foi executado nesta adequação documental e de consumidor.
