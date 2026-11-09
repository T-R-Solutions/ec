# Integração nativa Ec — 08/11/2026

A migração executável foi implementada no Ec. **O aceite integral está REJECTED**:
a validação final sem filtros terminou com exit 2, 24 tarefas aprovadas,
12 bloqueadas e sete com erro. As entradas permaneceram estáveis durante o run.
Nenhum scanner/browser central executou: as 12 chamadas da rodada final e dos
complementares terminaram em `infrastructure`, fase `resolve_active`, com código
nativo desconhecido (`null`). Não existem versões, imagens ou bases observadas
nessas chamadas para declarar homologação. `central list` continuou vazio.

## Alterações entregues

- Wrapper nativo comum TOOLBOX_BIN/PATH, com rejeição também do override vazio e encaminhamento literal de argv/exits; sem API consumer.
- Exclusão operacional de scripts/config/integration.json, toolbox.json e toolbox.images.lock.json, após migrar seus chamadores e testes. Bytes anteriores preservados no ZIP; hashes de locks não foram editados.
- Gates executáveis para as 12 ferramentas em tools/quality/native_gates.py, chamados pelo scanner-gate.sh e coordenados por automate.sh. Configs/regras e aprovação continuam no Ec.
- Inventário explícito, snapshot com SHA-256/modos, fontes ro em /project e resultados em /out. Não há dependência de /src, /out/work, schema/layout/staging/export da plataforma.
- Parsers estritos, supressões/ignores explícitos, três locks obrigatórios, eficácia Gitleaks/Opengrep/Trivy/YARA, validade de bases e Syft como medição.
- Streams separados, diagnósticos próprios, preservação dos códigos originais, outcomes/identidades/limpeza avaliados e classificação Ec separada. Supervisor não persiste argv; sinais e deadlines reservam tempo de limpeza.
- Playwright ESM central, servidor Node stdlib e build estático inventariado; sem runner/npm/ABI do host no container. API simulada identificada. Jornada Firefox separada preservada e executada.
- CodeQL create/resolve/analyze para Server Rust, Agent Rust e frontend, diretórios de bancos/resultados explícitos, suites sem pins independentes, metadata/SARIF e inventário validados. Runtime Rust/dependências requerem seleção explícita. Não houve análise real CodeQL.
- Correção demonstrada pela suíte: Vitest limpava os arquivos de evidência ao limpar seu diretório de relatório. O produtor agora escreve em subpasta, preservando manifestos/política do avaliador; regressão executada.

[Arquivos por comparação de conteúdo](changes.json), [contrato e mapeamento de cada gate](../../../scripts/README.md),
[preparação/operação](../../development/verification-tools.md), [fontes oficiais e limites de versão](references.md).
O status Git inicial foi conservado como contexto; a comparação usa bytes e não
restaura mudanças preexistentes. O primeiro backup cobre o inventário declarado,
não o checkout inteiro. Alterações pontuais adicionais estão identificadas.

## Mapeamento nativo e responsabilidade

Todos os comandos abaixo passam pelo wrapper `exec --tool … --project-root … -- …`.
A lista completa de tokens/configs está no código e no guia de scripts.

| Gate | Comando nativo | Seleção e política Ec |
| --- | --- | --- |
| Ruff obrigatório | check --no-fix --no-respect-gitignore --output-format json --config … | Python ativo explícito; supressões locais revisadas |
| ShellCheck obrigatório | --format=json1 --rcfile=… -- ARQUIVOS | Todos os scripts; diretivas existentes preservadas |
| Gitleaks obrigatório | dir --ignore-gitleaks-allow --redact=100 --exit-code 10 --config … --report-format json --report-path /out/native.json /project | Positivo inline, negativo, allowlist e limite de escopo; nenhum ignore pessoal; relatório sem conteúdo do segredo |
| Opengrep obrigatório | scan --test --json --strict …; scan --error --disable-nosem --no-git-ignore --max-target-bytes 0 … | Regras/fixtures Rust/TS/Vue, inventário exato dos três componentes, sem errors/skipped/supressões |
| OSV obrigatório | scan source --offline --all-packages --format json --config … --lockfile … | Dois Cargo.lock e package-lock; registro já publicado vinculado ao diagnóstico, idade <=24h |
| Trivy obrigatório | version --format json; filesystem --scanners vuln,misconfig,secret … | Upstream UpdatedAt/NextUpdate principal/Java, positivo/negativo das três categorias, três locks, sem updates/ignores ou filtro de severidade |
| CodeQL obrigatório | database create; resolve database --format=json; database analyze --format=sarifv2.1.0 … | Três alvos, build-mode none, suites security-extended do bundle, SARIF com baseline/diagnósticos e sem snippets/conteúdo de fontes |
| Playwright obrigatório | test --config=/project/scripts/config/native/playwright/playwright.config.mjs | Dois testes API simulada; Chromium central e Node stdlib, sem skipped/flaky |
| Grype opcional | dir:/project/application --config … --output json | Inventário dos três locks, todos os achados, validade nativa/observada de DB <=24h |
| Bandit opcional | --ignore-nosec --ini /project/.bandit -c … -f json -o /out/native.json ARQUIVOS | Sem baseline/exclusões/thresholds; inventário completo de Python |
| Syft opcional | scan dir:/project/application --output syft-json | Inventário não vazio dos três locks, estado measured; nenhum parecer de vulnerabilidade |
| YARA-X opcional | scan REGRAS ARQUIVO | Positivo/negativo e execução explícita por arquivo; alcance somente da sentinela EICAR existente |

Ec prepara fontes, configurações/regras, dependências da aplicação, serviços,
builds, bancos CodeQL, relatórios e aprovação. A plataforma prepara/qualifica/ativa
executáveis, imagens, browsers, packs, extratores, runtimes operacionais, bases e
caches. Nenhuma dessas operações centrais de preparo/ativação foi executada aqui.
`trivy-toolbox` foi retirado após convergir para o gate Trivy completo. Os quatro
complementares continuam opcionais; nenhum controle obrigatório foi removido.

## Operações e resultados observados

A última execução foi `bash scripts/automate.sh run`, sem filtros, com TOOLBOX_BIN
selecionado no ambiente e autorização de execução fora do sandbox. O sandbox
inicial impedia Docker e a gravação dos diagnósticos centrais. Não houve alteração
de grupos, ACLs, socket, namespace ou política do host para contornar isso.

[Resultados completos](full-run-results.json), [saída do coordenador](full-run.stdout),
[ledger de comandos/outcomes](operations.json), [complementares](optional-results.json).
Run final: `.local/automation/runs/run-fbfcTlTR`. Logs brutos e artefatos de produto
continuam privados nesse diretório, sem exportação pública de streams potencialmente
sensíveis. Runs anteriores foram preservados, inclusive a falha do teste que herdava
EC_TASK_DIR; o isolamento foi corrigido e revalidado.

| Observação | Resultado real |
| --- | --- |
| Governança, instalação e SDD | Passaram; estrutura/integridade, sem certificação de produto/skills |
| Testes de governança | 34 passaram |
| Testes da automação | 77 passaram; fronteira Toolbox simulada, sem homologar motores |
| Sintaxe Bash | Passou |
| Server e Agent | fmt, Clippy, testes, machete e builds dos harnesses passaram com Rust 1.99.1 |
| Frontend | typecheck, seis testes e build passaram |
| HTTP/PostgreSQL | publication_http, orchestration_http e agent_http passaram com componentes reais |
| Firefox | Jornada real passou, separadamente do Playwright com API simulada |
| Scanners/Playwright centrais | Bloqueados antes do processo nativo; central list vazio; image/version/database não observados |
| CodeQL | Rust sem ambiente explícito; frontend tentou a API nativa e foi bloqueado em resolve_active; nenhum banco/SARIF real produzido |
| Grype/Bandit/Syft/YARA-X | Quatro chamadas nativas tentadas; todas bloqueadas em resolve_active; exit agregado opcional 3 |
| Integridade | changed_inputs=[] no run final; nenhum gate recebeu --project-write |

Ambiente observado: Linux/amd64, Docker 29.7.2-ce, Cargo/Rust selecionado 1.99.1,
Node 26.13.0 e npm 11.20.0 no host. Cargo Audit 0.22.2, Deny 0.20.2,
Machete 0.9.2, Geiger 0.14.0 e llvm-cov 0.9.1 já existiam globalmente e foram
identificados. A primeira consulta direta de versão llvm-cov usou a forma errada;
`cargo llvm-cov --version` confirmou 0.9.1 e a suíte usa essa forma correta.
Nenhum plugin foi instalado/reinstalado. Esses cinco utilitários e RustSec não
constam dos 12 módulos encontrados no catálogo central consultado.

[Identificação do ambiente](doctor.json), [plugins e runtimes](product-tools.json),
[llvm-cov](llvm-cov-version.json), [catálogo](catalog-inventory.json),
[testes de contrato](contract-tests.stderr), [testes de governança](governance-tests.stderr),
[diagnósticos nativos por chamada](native-diagnostics).

## Reprovações e bloqueios pendentes

| Pendência | Evidência e requisito de retomada |
| --- | --- |
| Instalação central sem versões ativas | central-list-after.json e diagnósticos resolve_active. Operador deve preparar/qualificar/ativar as 12 ferramentas, Chromium e bundle CodeQL completo; não executar scanners por imagem histórica ou instalação local como fallback |
| Bases dos scanners | Nenhuma base selecionada observada nesta execução. Disponibilizar bases reais íntegras/atuais para OSV/Trivy/Grype; OSV precisa do registro publicado correspondente e Trivy valida timestamps upstream/NextUpdate; não renovar datas nem inventar IDs |
| CodeQL Rust | Fornecer EC_CODEQL_RUSTUP_HOME, EC_CODEQL_CARGO_HOME e EC_CODEQL_CARGO_BIN compatíveis com Linux da imagem, Rust 1.99.1/rust-src, dependências offline e requisitos de build scripts/macros. Bibliotecas/linker e Node/extratores/packs operacionais precisam ser qualificados na imagem central |
| RustSec / Deny / Audit | EC_RUSTSEC_DB ausente; Deny não encontrou seu advisory-db no CARGO_HOME Ec. Preparar base Git verdadeira externamente, com commit não futuro de até sete dias, e prover o caminho esperado por cada utilitário; manter offline/no-fetch |
| Geiger | Retornou erro com avisos de arquivos de dependências nunca escaneados, incluindo README e private.rs gerado. Qualificar/corrigir o utilitário e seu inventário; avisos não foram ignorados nem convertidos em sucesso |
| Cobertura Server | 332/1540 linhas = 21,56%; mínimo continua 80% |
| Cobertura Agent | 111/686 linhas = 16,18%; mínimo continua 80% |
| Cobertura frontend | 17/115 linhas = 14,78%; mínimo continua 80%; coleta corrigida preserva evidências |

[Valores exatos de cobertura](coverage.json). Para centralizar os utilitários Cargo
ou RustSec, são necessários módulos externos com distribuição/procedência verificável,
entrypoint nativo, Rust/LLVM compatíveis, dependências/caches explicitamente preparados,
modo offline e manutenção/validade RustSec. Isso é bloqueio externo, sem implementação
fictícia ou reinstalação de scanners no Ec.

Não foram executados scanners reais, Chromium central, criação/análise CodeQL real,
ensaios de cancelamento/timeout/concorrência com containers de ferramenta ativos ou
homologação dos formatos contra versões centrais efetivas. As referências de
versões candidatas não substituem essas verificações. A política de licenças/bans e
os limiares de cobertura foram preservados. Não criar exceções para obter sucesso.

## Segurança, rastreabilidade e recuperação

Metadados não persistem argv ou credenciais; teste sintético cobre também o
supervisor e campos extras no registro da base. PostgreSQL não é encaminhado ao
browser. Apenas runtime/dependências Rust explicitamente selecionados são montados
no CodeQL; Cargo copia registry/git para área privada e não copia config/credentials.
HOME, Docker socket, devices e host network não são montados por padrão.

Gitleaks usa redação nativa e o relatório Ec guarda apenas IDs/localização. Trivy
remove conteúdo de segredos do relatório após captura privada. Logs nativos podem
conter dados impressos pela ferramenta; não há garantia universal de redação. Runs
são privados, stdout/stderr separados e os diagnósticos/códigos não são inferidos
somente pelo número do exit. Sinais INT/TERM/HUP, timeout e limpeza falha foram
verificados em contrato; limpeza real de container continua pendente.

A baseline e os hashes identificam alterações preexistentes; evidências históricas,
instalações, caches e dados não foram apagados para a migração. Não houve mudanças
na implementação/configuração/imagens/bases centrais ou no Ox, nem commits/tags/publicação.
As chamadas autorizadas ao executor geraram somente seus diagnósticos operacionais
normais na plataforma; não foi executado central prepare/register/activate/rollback.

[Recuperação por arquivo e precondições](recovery.md), [backup anterior](before.zip),
[inputs antes](inputs-before.json), [inputs observados no run](full-run-inputs.json),
[inputs após o run](full-run-inputs-after.json). A convergência SDD distingue contrato
local aprovado de operação dos motores não verificada; o aceite agregado permanece
reprovado. A retomada exige resolver os bloqueios e executar novo run sem filtros,
sem reutilizar estes resultados como homologação de uma nova instalação.
