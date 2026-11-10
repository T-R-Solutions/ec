# Inventário vigente de scripts — inspeção de 09/11/2026

O mapa abaixo descreve os arquivos e consumidores observados após a integração
nativa. É inspeção estática; nenhuma suíte foi executada nesta limpeza. Execução
segue a [política canônica](task-verification.md); a autorização específica da
migração de 08/11 não se transfere a outras tarefas.

A [revisão de scripts/](../validation/scripts-cleanup-20261109/report.md) retirou
relatos superados e duplicação do README operacional. Os 44 arquivos de código,
configuração, regras, testes e licença foram mantidos por seus consumidores;
o texto anterior completo permanece arquivado no registro dessa revisão.

| Caminho a partir da raiz | Papel e acionamento observado |
| --- | --- |
| `scripts/automate.sh` | Entrada plan/run; seleciona superfícies/checks e carrega common, catalog, inputs e runtime por `source` |
| `scripts/toolbox.sh` | Interface pública transparente; TOOLBOX_BIN definido precisa ser executável, inclusive rejeição de vazio; senão descobre toolbox no PATH |
| `scripts/check.sh` | Tarefa syntax do catálogo; descobre recursivamente `scripts/**/*.sh` e inspeciona sintaxe |
| `scripts/stacks/ec/common.sh` | Funções usadas por source: caminhos, hashes, JSON, erros/bloqueios e agregação |
| `scripts/stacks/ec/catalog.sh` | Único catálogo de tarefas, argv, cwd e dependências; não há catalog.json |
| `scripts/stacks/ec/inputs.sh` | Descoberta dinâmica por escopo; exclui caches, segredos e históricos; locks explícitos para dependências |
| `scripts/stacks/ec/runtime.sh` | Supervisor chamado por automate; ambiente selecionado, lock, grupos de processos, prazos, logs e classificação |
| `scripts/stacks/ec/integration.sh` | Preflight inserido pelo runtime nos harnesses HTTP/Firefox; serviços e portas existentes, sem preparo implícito |
| `scripts/stacks/ec/scanner-gate.sh` | Encaminha um check para `tools/quality/native_gates.py`; este usa a CLI pública toolbox exec |
| `scripts/stacks/rust/check.sh` | Despacho Cargo nos dois workspaces; toolchain literal, plugins e políticas explícitos; cobertura carrega metrics/coverage |
| `tools/quality/geiger_diagnostics.py` | Chamado pelo check Rust após erro Geiger; Python operacional fora de scripts, preserva referências/hashes dos streams e explica caminhos, finalidade e retenção, sem abrir os arquivos dos avisos ou alterar o resultado do gate |
| `tools/quality/vendor/cargo-geiger/` | Mesmo Geiger 0.14.0 com correção local de descoberta, fontes/lock/licenças e hashes upstream preservados; build offline privado, dados incorporados identificados e Rust gerado contado pelo motor original |
| `tools/quality/collect_runtime_coverage.py` | Coleta os perfis das jornadas reais com binário atual instrumentado; preflight existente e targets exclusivos; falha de build/harness impede a geração aprovada do LCOV |
| `scripts/stacks/frontend/check.sh` | Despacho npm pelo package.json/lock, inclusive opções adicionais explícitas; coverage carrega biblioteca própria |
| `scripts/stacks/ec/metrics/coverage.sh`, `coverage.json`, `metrics/check.sh` | Geração/inventário/avaliação LCOV e limiar de linhas >=80%; check também fornece budget/performance opcionais |
| `scripts/stacks/ec/security/check-report.sh` | Parser usado por native_gates.parse para Gitleaks, Opengrep, OSV, SARIF e Trivy; não é scanner duplicado |
| `scripts/stacks/ec/security/redact-trivy.jq` | Filtro chamado por native_gates.redact_trivy; conserva identificação/contagens sem valores de segredos |
| `scripts/stacks/ec/security/validate-exceptions.sh` | Interface explícita de validação de registro; contrato preservado e exercitado por test_shell; validar não aplica exceções |
| `scripts/stacks/ec/reports/summarize.sh` | Interface de leitura de results.json schema 1; consumidor manual e contrato em test_shell; não altera registros |
| `scripts/stacks/frontend/eslint-ratchet.sh` | Interface para relatório/baseline explícitos e contrato em test_shell; não instala nem executa ESLint; frontend atual não define npm lint |
| `scripts/config/native/` | Onze configurações: cargo-audit, cargo-deny, ruff, shellcheck, gitleaks, osv-scanner, trivy, grype, bandit, codeql e playwright; lidas explicitamente pelos checks/gates |
| `scripts/rules/opengrep/` | Três regras e três fixtures Rust/TS/Vue; descoberta por diretório, extensões e anotações ruleid; gate de eficácia precede SAST |
| `scripts/rules/codeql/*.qls` | Suites distintas Rust e JS/TS; projetos explícitos em config/native/codeql/projects.json; não são duplicatas |
| `scripts/rules/yara-x/ec.yar` | Sentinela EICAR opcional, com fixtures positivas/negativas geradas pelo gate; não é catálogo universal de malware |
| `scripts/tests/test_consumer.py`, `test_native.py`, `test_quality.py`, `test_shell.py` | Descobertos por unittest no catálogo; contratos de wrapper, execução nativa, qualidade e shell; simulações não homologam ferramentas reais |
| `scripts/tests/test_geiger_diagnostics.py` | Descoberto pelo mesmo unittest; casos de retenção de dependências/gerados, caminhos desconhecidos, bytes inválidos, mensagens não reconhecidas e conteúdo tratado como dados; resultados e limites no relatório do diagnóstico Geiger |
| `scripts/tests/test_runtime_coverage.py` | Contratos do show-env como dados, alvos instrumentados, harnesses e propagação de erro; fixtures não comprovam integração ou cobertura real |
| `scripts/README.md`, `scripts/LICENSE` | Contrato operacional e licença MIT do componente importado; licença raiz GPL tem papel diferente |

O runner mantém governança obrigatória em `tools/governance/` e `sdd/tools/`.
Server/Agent têm membros Cargo explícitos; frontend usa scripts npm e padrões
Vitest. O catálogo registra três harnesses HTTP e a jornada Firefox em
`application/integration/tests/`; imports por sys.path reutilizam publication_http,
orchestration_http e o driver Firefox. Playwright é separado, com API simulada,
config ESM central e servidor/testes mantidos pelo Ec.

Os indiretos incluem `EC_ROOT`, `EC_TASK_DIR`, `EC_E2E_ARTIFACT_DIR`, `EC_TASK_TIMEOUT`,
`EC_CARGO_BIN`, `CARGO_HOME`, `RUSTUP_HOME`, `EC_RUSTSEC_DB`,
`TOOLBOX_STATE`, `DOCKER_HOST`, `DOCKER_CONTEXT`, `DOCKER_CONFIG`,
`DOCKER_TLS_VERIFY`, `DOCKER_CERT_PATH`, `EC_CODEQL_RUSTUP_HOME`, `EC_CODEQL_CARGO_HOME`,
`EC_CODEQL_CARGO_BIN`, `TOOLBOX_BIN` e `TOOLBOX_SELINUX_LABEL`. O ambiente do supervisor
é selecionado, não uma cópia integral do host. OSV/Trivy usam a proveniência
upstream do diagnóstico; o recibo externo OSV anterior foi retirado. Trivy exige
bundle externo identificado e mount ro observado.
[Adequação ao contrato central](../validation/toolbox-alignment-20261109/report.md).
Não executar arquivos configurados
como shell nem inferir download/preparo por existir um caminho.

Nesta limpeza foram removidos apenas caches Python sob scripts/tests e outras
raízes ativas, marcadores em diretórios já preenchidos e a consulta temporária
erase.md da raiz. A consulta foi [arquivada integralmente](../validation/cleanup-20261109/retired/erase.md)
como histórico; [preparação vigente](verification-tools.md). Nenhum wrapper,
runner, regra, parser, fixture, teste, configuração ou licença foi retirado.
[Relatório, inventário e recuperação](../validation/cleanup-20261109/report.md).

## Inventários e decisões anteriores — preservados integralmente

O conteúdo datado abaixo registra migrações anteriores, incluindo caminhos que já
não existem. Para operar, use o mapa acima e o [guia nativo](../../scripts/README.md).

> Integração nativa de 08/11/2026: `scripts/toolbox.sh exec` substitui o caminho
> STIC. Gates reais, configs/regras e parsers estão implementados no Ec; configs
> e lock obsoletos foram retirados. A execução depende de ferramentas centrais
> ativas, bases válidas e pré-requisitos do produto. Consulte `scripts/README.md`
> e `docs/validation/toolbox-native-20261108/report.md`. As observações datadas
> abaixo preservam estados anteriores e não descrevem o contrato atual.

> Atualização de 08/11/2026: a migração STIC e a exclusão de downloads locais
> substituem a ponte/preparo/armazenamento descritos anteriormente. Estado atual:
> wrapper comum implantado, gates de scanners bloqueados, nenhuma execução de suíte.
> Consulte `scripts/README.md` e `docs/validation/scripts-stic-20261108/report.md`
> (caminhos a partir da raiz Ec). O texto anterior registra decisões históricas.

# Inventário de scripts e decisões Ec — 2026-11-05

O conteúdo foi inspecionado antes de executar importados. O inventário exato por
arquivo/hash/decisão está em [inventory.json](../validation/scripts-ec-20261105/inventory.json).
A cópia integral anterior está em [previous.zip](../validation/scripts-ec-20261105/previous.zip),
incluindo licenças/fontes e versões anteriores dos documentos/skills editados. Não
foi removido material histórico fora de scripts/. As decisões abaixo cobrem todos
os arquivos originais por grupos coerentes; não conservam infraestrutura por hipótese futura.

| Arquivos/grupo | Finalidade, consumidores e efeitos observados | Decisão Ec e verificação |
| --- | --- | --- |
| `scripts/tauri/` (7 arquivos) | Release/teste/performance desktop Tauri; wrappers CLI não tinham tarefa configurada. Ec é Server/Agent e web, sem manifest Tauri. | Nenhum consumidor Ec; remover; conferir imports e wrappers. |
| `scripts/release/` (5 arquivos) | Updater Tauri, atestados de origem com produto fixo, certificados Windows/macOS; escreve config e importa/remove certificado do usuário. | Sem release desktop Ec ou consumidor; remover; preservar fonte/licença, não executar efeitos no host. |
| `scripts/architecture/` (4 arquivos) | Parser TypeScript/Svelte, owners e dependências src-tauri, baseline do produto importado. | Não representa módulos Rust/HTTP/Vue do Ec. Remover em vez de renomear regras sem oráculo. Arquitetura exige revisão/testes de contratos concretos. |
| `scripts/graphify/` (5 arquivos) | Grafo com corpus Svelte/Tauri, caminhos de produto e wrappers sem ferramenta/configuração. | ADR-001 exclui motores de contexto; não há consumidor Ec. Remover; buscar referências operacionais. |
| `scripts/bun/` (2 arquivos) | Wrapper de política e helper que cria TMPDIR e exige Bun. | Frontend Ec tem package-lock e npm. Remover dependência obrigatória; confirmar execução npm e lock preservado. |
| `scripts/frontend/` (13 arquivos) | Wrappers de npm/Bun sem configuração, ESLint ratchet e budgets importados; dev/setup não apontavam a tarefa real. | Substituir typecheck/test/build pelo runner npm real; remover lint/cobertura/performance sem scripts/budgets Ec. vue-tsc cobre .vue, sem parser Svelte. |
| `scripts/rust/` (9 arquivos) | Wrappers fmt/clippy/test e audit/deny/geiger/machete/cobertura sem pins/tarefas reais. | Substituir verificações reais por seleção server/agent; remover ferramentas sem política/consumidor Ec. Ler os dois toolchains e usar caches/targets reais. |
| `scripts/governance/` (3 arquivos) | Validador genérico JSON para frases/budgets e wrappers AGENTS/skills. | Redundante com tools/governance; remover e chamar auditor/instalador canônicos diretamente. Testar integridade e negativos da suíte existente. |
| `scripts/security/verify.sh` (1 arquivos) | Wrapper security sem configuração Ec de ferramentas/bases. | Remover wrapper e substituir por automate.py run --surface security; ausência de Gitleaks é blocked. |
| `scripts/security/containers/` (2 arquivos) | Dockerfiles Qodana Rust/JS com EAP, bibliotecas WebKit/desktop e apt global na imagem. | Remover: não há configuração, baseline, licença/token ou pipeline Ec; não construir nem puxar imagens por herança. |
| `scripts/security/commands/` (8 arquivos) | Wrappers de scanners e Qodana que faz build --pull, usa tokens de outro produto, src-tauri e caminhos scripts/checks inexistentes. | Remover wrappers sem tarefa real; Gitleaks passa ao runner com regra default e snapshot explícito. Nenhum download automático. |
| `scripts/security/rules/` (3 arquivos) | Regras Opengrep Rust/TS/Svelte/Tauri com owners/nomes/limites da origem. | Remover: regras de outra arquitetura, sem fixtures/política Ec. Não afirmar cobertura SAST substituta. |
| `scripts/security/analyzers/` (2 arquivos) | Parsers de relatórios e registro genérico de exceções sem consumidor configurado. | Remover: scanner mantido valida JSON no próprio runner, sem motor de exceções ou limiares fictícios. |
| `scripts/git/` (1 arquivos) | Setup de hook encaminhava para tarefa inexistente. | Remover; não há hook Ec para configurar. Encerramento é política explícita, sem alterar hooks existentes. |
| `scripts/lib/` (4 arquivos) | Cores/temp e logs por palavras-chave com owner fixo e retenção destrutiva de apenas dois logs. | Remover helpers sem consumidor após Qodana/TS; runner retém bytes, exit e runs únicos, sem apagar histórico. |
| `scripts/typescript/` (1 arquivos) | tsconfig usa tipos Bun e extends/pacotes ausentes. | Remover; frontend tem tsconfig próprio com Vue. |
| `scripts/verify/` (1 arquivos) | Wrapper full de biblioteca, sem configuração do consumidor. | Substituir por automate.py run com seleção Ec utilizável e padrão conservador. |
| `scripts/automation/examples/` (4 arquivos) | Exemplos Rust/Kotlin/Gradle/Vue/misto com paths/hashes de placeholder. | Remover: exemplos não são config ativa. Ec não tem Gradle/Kotlin. ec.toml é configuração real e testada. |
| `scripts/automation/adapters/` (2 arquivos) | Descoberta de stacks e defaults de scanners, bases offline, Gradle e scripts não configurados. | Substituir por plano fixo Ec; não manter plataforma genérica. Testar encaminhamento, flags e ausências. |
| `scripts/automation/rules/security.yml` (1 arquivos) | Cinco indicadores SAST incluindo Kotlin/Svelte; sem suite/configuração Ec. | Remover sem alegar SAST completo ou herdar regras de origem. |
| `scripts/automation/rules/suspicious.yar` (1 arquivos) | Dois indicadores textuais YARA de malware; sem política/dataset validado. | Remover: sem scanner/consumidor necessário; não certificaria comportamento de terceiros. |
| `scripts/automation/rules/gitleaks.toml` (1 arquivos) | Estende regras default da CLI; não contém exceções de outro produto. | Manter; verificar CLI revisada e redaction/JSON; scanner ausente bloqueia security. |
| `scripts/automation/powershell-lint.ps1` (1 arquivos) | PSScriptAnalyzer para PowerShell; parsing AST sem dot-source. | Remover: retirados releases Windows, Ec mantido não tem PowerShell; sem consumidor. |
| `scripts/automation/metrics.py` (1 arquivos) | Cobertura LCOV/JaCoCo, budget gzip, p95 e exceções; consumidor teria de fornecer limiares. | Remover: não há política Ec/configuração/testes de aplicação que usem essas métricas. |
| `scripts/automation/reports.py` (1 arquivos) | Parsers para dez scanners/formatos, sem scanners Ec configurados. | Remover: mantido só JSON Gitleaks, validado diretamente; sem falsa equivalência de cobertura. |
| `scripts/automation/runtime.py` (1 arquivos) | Supervisor POSIX de subprocessos, timeout/cancelamento e logs; antigo Runner requeria pins absolutos e datasets. | Refatorar: preservar execute/terminate e logs brutos; substituir Runner genérico por lock/plano Ec e observação de inputs, sem pins pessoais ativos. |
| `scripts/automation/model.py` (1 arquivos) | Modelos/configuração TOML genéricos e detecção de stacks. | Refatorar para superfícies/workspaces/comandos reais, mantendo paths contidos; testes de seleção e execução. |
| `scripts/automation/cli.py` (1 arquivos) | Entrada plan/run, sinais, seleção e resultados. | Refatorar para Ec; sem root/config obrigatórios artificiais. Testar cwd, espaços, saídas e bloqueios. |
| `scripts/automation/LICENSE` (1 arquivos) | Licença MIT do componente de automação importado, distinta da licença raiz. | Manter bytes; não relicenciar outros recursos. |
| `scripts/automation/__init__.py` (1 arquivos) | Marcador do pacote Python, consumidor automate.py. | Manter; verificar importação/entrada. |
| `scripts/reports/summarize.py` (1 arquivos) | Renderiza resultados schema 1 em Markdown; não cria aprovação. | Manter consumidor real de results.json; testar renderização e estados do runner. |
| `scripts/automate.py` (1 arquivos) | Entrada Python da biblioteca. | Refatorar e manter como entrada única Ec; root pela localização e overrides explícitos. |
| `scripts/automation/README.md` (1 arquivos) | Manual genérico com exemplos/placeholders e consumidores externos ausentes. | Refatorar: responsabilidades reais e referência à política canônica. |
| `scripts/README.md` (1 arquivos) | Inventário importado com links para src/doc ausentes e comandos não configurados. | Refatorar: comandos reais, links Ec e inventário permanente. |

## Reintegração Opengrep/OSV — 2026-11-05

A revisão posterior corrige a retirada das análises SAST/dependências: falta de
configuração ou instalação é um bloqueio de adaptação, sem justificar por si só a
inaplicabilidade da ferramenta. Foram reintegrados os wrappers opengrep.sh,
osv-scanner.sh e security/verify.sh e reescritas as regras Rust/TypeScript para Ec,
com regras/fixtures Vue próprias. O inventário original acima registra a primeira
adaptação; estas decisões substituem as linhas de comandos/regras correspondentes.

Os consumidores são automate.py (security e padrão conservador) e wrappers reais;
security.py normaliza apenas os relatórios dos scanners mantidos. Pré-requisitos,
licenças e preparo no [guia corrente](../../scripts/README.md).
[Inventário atual por caminho](../validation/scripts-security-20261105/inventory.json)
e [nova execução](../validation/scripts-security-20261105/report.md). Os backups e
relatórios anteriores permanecem intactos. Os demais scanners continuam fora deste
incremento; não há substituição de cobertura de licenças, artefatos ou malware.

## Consumidores e recursos externos

Busca fora de scripts (incluindo arquivos ocultos pertinentes) não encontrou caller
operacional dos wrappers importados. Referências nos próprios scripts/READMEs foram
retiradas com seus consumidores. `tools/governance` permanece canônico. Nenhum
arquivo da pesquisa TeamCity ou dos relatórios históricos foi reescrito/removido.
O harness Firefox era consumidor de um destino fixo histórico; agora aceita
EC_E2E_ARTIFACT_DIR ou cria pasta temporária única, preservando imagens anteriores.
Novos consumidores são AGENTS, README, guias, workflow/templates e duas pontes/skills,
com a política concentrada em task-verification.md. O inventário original menciona
caminhos retirados como registro, não como instruções executáveis.

Qodana suporta análise Rust/JS, portanto não é inaplicável pela linguagem. Foi
retirado pela falta de configuração/licença/token/baseline Ec e pelas operações
específicas de desktop. Fontes oficiais consultadas: [edições](https://www.jetbrains.com/help/qodana/pricing.html),
[Rust e token](https://www.jetbrains.com/help/qodana/rust.html),
[licenças de linters pagos](https://www.jetbrains.com/help/qodana/contributors.html).
CodeQL JS/TS poderia analisar frontend, mas o wrapper só encaminhava para tarefa
inexistente: sem banco/query suite/licença/repositório elegível configurados. Não
conservar um comando vazio; [condições oficiais](https://docs.github.com/en/code-security/concepts/code-scanning/codeql/codeql-cli)
e [licença da CLI](https://github.com/github/codeql-cli-binaries/blob/main/LICENSE.md).

Trivy, YARA, cargo-audit/deny/geiger/machete, Syft/Grype e scanners de
CI exigiriam ferramentas, bases, políticas, fixtures ou artefatos não configurados.
Os wrappers importados não entregavam essas análises. Não são gates Ec silenciosamente
aprovados; estas coberturas ficam como lacunas documentadas. As análises mantidas agora incluem Gitleaks, Opengrep e OSV, conforme a
reintegração acima; indisponibilidade de ferramentas/bases dá blocked. As dependências comuns de Vue/Rust continuam nos manifests/lockfiles.

## Verificação e recuperação

Reproduzir conforme [política canônica](task-verification.md). O relatório de
[execução](../validation/scripts-ec-20261105/report.md) distingue baseline, resultados,
bloqueios e evidências. Restaurar um arquivo de previous.zip exige comparar o conteúdo
atual para preservar edições posteriores. Não extrair todo o arquivo sobre o checkout.
Não usar erase.md como dependência operacional ou link essencial.

## Migração para shell — 2026-11-06

A solicitação posterior do usuário substitui a decisão de manter o runner Python.
A entrada vigente é `bash scripts/automate.sh`; módulos operacionais estão separados
em lib, rust, frontend, security, metrics e reports. Python em scripts é exclusivo
de testes. Ferramentas Python canônicas de governança/SDD e harnesses da aplicação
continuam em suas pastas originais.

Os antigos wrappers Rust/frontend foram consolidados em `rust/check.sh` e
`frontend/check.sh`; audit também foi incluído. metrics.py, check-report.py,
validate-exceptions.py, summarize.py e eslint-ratchet.ts foram substituídos por
implementações shell. Trivy e CodeQL têm entradas explícitas com pré-requisitos;
nenhuma base, licença, budget ou política de dependência é implicitamente aprovada.
Os scanners obrigatórios Gitleaks/Opengrep/OSV continuam no padrão.
O catálogo vigente também inclui Trivy nativo/CodeQL e os demais gates descritos
na [política de verificação](task-verification.md). O
[contrato Toolbox/Ec](../../scripts/README.md) registra novos
checks adicionais e o [plano de conclusão](../../sdd/features/scripts-verification/toolbox-completion-plan.md)
prevê sua inclusão no padrão e estado local. Essa evolução ainda não foi implementada;
as decisões de remoção acima preservam seu contexto histórico.

`scripts_erase` foi removida integralmente após backup por conteúdo. Componentes
Tauri/Svelte/Bun/Graphify e baselines de outro produto não foram reintegrados.
[Relatório e recuperação](../validation/scripts-shell-20261106/report.md) registram
escopo, testes e lacunas sem reescrever as observações anteriores desta página.
