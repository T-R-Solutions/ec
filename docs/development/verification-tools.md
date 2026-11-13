# Preparação das verificações

[`scripts/config/automation.json`](../../scripts/config/automation.json) é a
entrada responsável por tarefas, executores e recursos. O
[guia do runner](../../scripts/README.md) descreve operação e resultados;
[task-verification.md](task-verification.md) define autorização. Este guia
explica como satisfazer recursos declarados, sem criar configurações paralelas.

## Instalações e ambiente

O runner descobre a Toolbox no PATH ou nos candidatos declarados. `TOOLBOX_BIN`
seleciona explicitamente um executável; `TOOLBOX_ROOT` seleciona sua instalação.
Override presente e inválido, inclusive vazio, não recebe fallback silencioso.
`TOOLBOX_STATE` e seleções Docker são encaminhadas pela lista permitida; não há
transporte integral do ambiente nem alteração de perfis/shell do usuário.

Cargo/Rustup usam os homes já instalados. A seleção segue `EC_RUST_TOOLCHAIN`,
`RUSTUP_TOOLCHAIN` ou o toolchain ativo observado na raiz. Não adicionar versões
numéricas aos scripts para contornar falta de ferramenta. `EC_CARGO_BIN` permite
um diretório explícito dos plugins, sem substituir a prioridade PATH selecionada.
Geiger usa a instalação do sistema. As fontes em `tools/quality/vendor/cargo-geiger/`
permitem preparar o plugin com a correção de descoberta documentada em
[EC-PATCH.md](../../tools/quality/vendor/cargo-geiger/EC-PATCH.md).
O runner não compila essa fonte, não mantém um segundo executável e não instala
plugins; registra a identidade do executável instalado em cada execução.

| Seleção | Uso |
| --- | --- |
| `CARGO_HOME`, `RUSTUP_HOME`, `EC_CARGO_BIN` | Dependências e ferramentas instaladas no host |
| `EC_RUST_TOOLCHAIN` | Override do toolchain ativo; não causa instalação |
| `EC_RUSTSEC_DB` | Checkout Git RustSec já preparado |
| `TOOLBOX_BIN`, `TOOLBOX_ROOT`, `TOOLBOX_STATE` | Instalação/estado públicos da Toolbox |
| `DOCKER_HOST`, `DOCKER_CONTEXT`, `DOCKER_CONFIG`, `DOCKER_TLS_VERIFY`, `DOCKER_CERT_PATH` | Seleção explícita do daemon |
| `TOOLBOX_SELINUX_LABEL` | Modo de rótulo declarado; shared por padrão, somente no run |
| `EC_TRIVY_REFRESH`, `EC_GRYPE_REFRESH` | Habilitar/desabilitar manutenção de bases antes dos scans |
| `EC_CODEQL_RUSTUP_HOME`, `EC_CODEQL_CARGO_HOME`, `EC_CODEQL_CARGO_BIN` | Recursos host usados pelo extrator Docker; derivados dos homes Cargo/Rustup por padrão |

O runtime também define `EC_ROOT`, `EC_RUN_DIR`, `EC_TASK_DIR`, `EC_TASK_TIMEOUT`,
`TMPDIR`, caches npm, targets Cargo, build frontend e diretórios de cobertura.
São caminhos da execução atual, não variáveis a gravar em `.env` ou no shell.
`LLVM_COV`/`LLVM_PROFDATA` são resolvidos para a tarefa de cobertura e descartados
com ela. O requisito Node é lido de `package.json`, sem versão mínima duplicada.

## Cargo, RustSec e LLVM

O preparo Cargo faz update/fetch offline por workspace. Uma crate só pode ser
reutilizada se satisfizer os requisitos do manifest; existir em outra versão no
cache não prova compatibilidade. Crates ausentes precisam de aquisição autorizada.
Locks não são congelados; alterações exigem nova suíte para vincular todos os
resultados à resolução final.

RustSec é descoberto nos caminhos declarados, priorizando os diretórios Cargo.
O candidato legado do Ox está na configuração, não em código de execução;
pode ser retirado quando a base canônica do usuário estiver preparada. Seleções
ambíguas não escolhem arbitrariamente entre checkouts. Audit e Deny compartilham
a base e a idade definida na configuração; a existência da pasta não comprova
validade/atualidade. O preparo identifica ausência, data futura ou expiração.
Não há clone/fetch de RustSec implícito.

O Deny recebe um overlay temporário de sua política com localização/URL/idade da
base. `allow-wildcard-paths` permite dependências locais dos crates privados,
sem liberar wildcards de registros nem criar versões artificiais. Consulte a
[documentação oficial de bans](https://embarkstudios.github.io/cargo-deny/checks/bans/cfg.html#allow-wildcard-paths).

A cobertura primeiro procura LLVM no sysroot ativo. Se faltar, pode reutilizar
uma instalação local cujo rustc tenha a mesma release, commit, host e LLVM.
Caso nenhuma corresponda, preparar `llvm-tools-preview` para o toolchain ativo
mediante autorização específica de download. Não trocar para um toolchain antigo
apenas porque ele tem o componente. Os overrides usados seguem o contrato
[LLVM_COV/LLVM_PROFDATA](https://github.com/taiki-e/cargo-llvm-cov#environment-variables).

## Toolbox e bases

Análises nativas usam `scripts/toolbox.sh exec`; seleção e diagnóstico usam a CLI
pública, incluindo central list/status/resolve. Consumidores não importam módulos da
plataforma, não fazem `docker run` de scanners e não escolhem versões/imagens
fixas. Imagem/versão/base observadas entram nos diagnósticos. Preparação de imagens,
plugins da plataforma e Checks Bundle é responsabilidade da Toolbox externa.

OSV pode adquirir archives oficiais automaticamente na partida quando o preparo
central vence. Trivy inspeciona DB e Java DB e pode renovar ambas, em manutenção
com raiz vazia e flags explícitas de download. Grype inspeciona `db status --output
json` e pode executar `db update` em manutenção. A
[CLI oficial do Grype](https://oss.anchore.com/docs/reference/grype/cli/) define
essas operações. Renovar Trivy não renova Grype: são dados independentes.

Na inspeção Grype, o checksum é obrigatório e a idade é conferida separadamente
pela política canônica, antes de permitir manutenção ou scan. No scan, o mesmo
limite é projetado para `GRYPE_DB_MAX_ALLOWED_BUILT_AGE`; não se depende do prazo
padrão da ferramenta. A manutenção remove apenas o intervalo mínimo entre
consultas de atualização, permitindo conferir a origem quando a base vence.
As [variáveis oficiais do Grype](https://oss.anchore.com/docs/reference/grype/configuration/)
e a [separação de integridade/idade no status nativo](https://github.com/anchore/grype/blob/main/grype/db/v6/installation/curator.go)
fundamentam essa projeção. Base velha com checksum inválido continua reprovando.

O preparo não aceita datas futuras/corrupção como expiração. Depois da aquisição,
revalida datas upstream e snapshot publicado; uma aquisição recente de bytes
velhos continua reprovando. Falha mantém o diagnóstico e não aprova scan parcial.
`EC_TRIVY_REFRESH=0`/`EC_GRYPE_REFRESH=0` desabilitam essas aquisições no Ec;
a renovação OSV é própria da plataforma. A autorização para executar a suíte
não dispensa a autorização de downloads definida na política canônica.

## Serviços e navegadores

O PostgreSQL deve existir como container dedicado com bind loopback correspondente
à configuração. O runner pode iniciar o container parado e registra ID/estado
anterior. Não cria volumes/imagens nem altera containers de outro nome. O EXIT
restaura somente o container iniciado pelo run. Um recibo com limpeza falha impede
aceite; não apagar manualmente recursos possivelmente usados por outro processo.

Chromium e Firefox são selecionados exclusivamente no Docker da Toolbox pelo
Playwright. O operador prepara/registra a variante desejada na versão ativa;
`exec --browser` seleciona essa variante sem alterar a ativação global. Ausência
ou ambiguidade bloqueia, sem download ou navegador do host como alternativa.
Os [binários dos browsers devem corresponder ao Playwright](https://playwright.dev/docs/browsers),
e o [ambiente Docker](https://playwright.dev/docs/docker) concentra suas bibliotecas.

A inspeção posterior ao run-xK3jrVOn confirmou as variantes Chromium e Firefox
registradas para Playwright 1.56.1. A imagem Firefox também foi localizada pelo
daemon Docker; o registro central contém versão nativa verificada e uma única
revisão elegível. O runner existente pode selecioná-la por `--browser firefox`,
sem trocar a ativação global. Essa inspeção de disponibilidade não executa nem
aprova a jornada E2E; não é necessário baixar novamente o navegador.

## Diagnósticos Geiger

O plugin instalado deve analisar todos os membros do workspace; falha no primeiro
membro impede aprovação, sem remover dependências ou fontes geradas. O diagnóstico
complementar conserva caminhos/hashes dos streams e explica consumidores prováveis,
sem reclassificar análise parcial como sucesso. O run-xK3jrVOn usou o plugin
0.14.0 sem a correção de descoberta: 14 READMEs incorporados e Rust gerado por
serde_core ficaram sem contabilização. O preparo posterior instalou o
0.14.0+ec.1 offline, com autorização de escrita no host. A versão distingue o
patch; compilação/instalação não aprovam os gates ainda não reexecutados.
O runner não corrige caches nem substitui automaticamente o motor.


## Qualidade da extração e triagem — 12/11/2026

CodeQL usa os binários da instalação Rust selecionada diretamente, evitando que
os proxies Rustup acionem uma versão interna ausente do extrator. Os lançadores
efêmeros preservam stderr Cargo/rustc completo, sem registrar argv; runtime ro,
scan offline e ausência de instalação permanecem. O lançador preserva os descritores do jobserver e registra códigos das compilações internas; falha de build script/proc-macro impede aprovação mesmo quando o SARIF não a informa. A imagem CodeQL precisa de compilador/linker C para essas compilações. SARIF compartilha um único
validador: resolve severidade efetiva, rejeita falhas/avisos de extração e baixa
qualidade, exige fontes extraídas e métricas Rust suficientes. `artifacts` não
é prova de extração saudável.

Bandit mantém `--ignore-nosec`, todas as fontes e severidades. A triagem autorizada
fica somente em `tools.bandit.triage` de `scripts/config/automation.json`: hash
SHA-256 da fonte, regra, linhas exatas e justificativa. Alteração de conteúdo,
regra ou linha deixa a ocorrência pendente; nenhum comentário Ruff é aprovado
automaticamente. O relatório bruto permanece integral. Resultado e recibo
`bandit-review` distinguem achados nativos, revisados e pendentes; qualquer
pendência reprova. Não há baseline nativa, exclusão de testes ou skip global.

Gitleaks usa `tools.gitleaks.triage` no mesmo catálogo para dez indicadores
públicos de regras YARA importadas: SHA-256, regra e linha exatos, sem reproduzir
as strings dos indicadores em outra configuração. A revisão só se aplica à
árvore atual. Histórico nunca herda aprovação de conteúdo corrente. Os controles
nativos inserem um PAT sintético nos mesmos caminhos e exigem sua detecção;
alteração do arquivo invalida a revisão, sem alterar o relatório nativo.

Syft recebe nome Ec e versão derivada dos hashes da entrada observada. Deny
mantém a união de licenças dos workspaces e duplicidades como aviso; fixtures
positivas mantêm seus exits esperados. O alcance YARA-X ampliado e suas sentinelas estão descritos abaixo.


## Cobertura ampliada — 12/11/2026

O catálogo e os alvos permanecem somente em `scripts/config/automation.json`.
As políticas de cada motor ficam no formato nativo apontado por `policies`;
não há perfil alternativo ou configuração de compatibilidade. As versões abaixo
identificam a documentação consultada, sem fixar versões de ferramentas no Ec.

| Ferramenta / referência oficial consultada | Aplicação e limites |
| --- | --- |
| [Ruff 0.11.13: seleção de regras](https://github.com/astral-sh/ruff/blob/0.11.13/docs/linter.md) e [configuração](https://github.com/astral-sh/ruff/blob/0.11.13/docs/configuration.md) | Perfil estável explícito adequado a stdlib/CLI, unittest e namespaces, autorizado em 13/11/2026. Grupos, exceções de estilo/depreciação e quatro exceções E402 de bootstrap na política nativa única; sem ALL/preview ou exclusões de fontes. Segurança Python permanece no Bandit integral; o gate Ruff usa ignore-noqa e nunca aplica fixes. |
| [ShellCheck 0.11.0: CLI e rc](https://github.com/koalaman/shellcheck/blob/v0.11.0/shellcheck.1.md) | Todos os opcionais, dataflow, arquivos sourced e nível style. O Bash é o dialeto dos scripts declarados. |
| [Clippy: grupos e níveis](https://doc.rust-lang.org/clippy/usage.html) | all, pedantic, nursery e cargo, todos os targets/features do workspace; warnings bloqueiam. restriction em bloco contém regras contraditórias e não é habilitado. O target executado continua sendo o host, sem prometer uma matriz de plataformas. |
| [Cargo Geiger](https://github.com/geiger-rs/cargo-geiger) e [fonte dos argumentos 0.14](../../tools/quality/vendor/cargo-geiger/src/args.rs) | Todos os membros/features; all-deps inclui build/dev, mantendo o patch instalado e o erro por perda de associação ativa. Não impõe zero unsafe. |
| [Cargo Machete: metadata](https://github.com/bnjbvr/cargo-machete#false-positives) | with-metadata para nomes resolvidos e tipos de dependência; não introduz ignores. Locks continuam sujeitos ao controle canônico de alterações. |
| [Cargo Deny](https://embarkstudios.github.io/cargo-deny/checks/cfg.html) e [Cargo Audit](https://github.com/RustSec/rustsec/tree/main/cargo-audit) | Mantêm all-features, ausência de ignores de advisory e verificações estritas de origem/licença. Duplicidades e exceções já justificadas dos workspaces continuam conforme a política compartilhada, sem atualização incompatível forçada. |
| [Gitleaks 8.24.3: flags](https://github.com/gitleaks/gitleaks/blob/v8.24.3/cmd/root.go) e [git](https://github.com/gitleaks/gitleaks/blob/v8.24.3/README.md#git) | Árvore atual, dotenv e histórico local de refs/reflogs/HEAD, incluindo merges. Decodificação tem profundidade explícita no catálogo; não há corte por tamanho de arquivo. Esta versão não oferece max-archive-depth: não é passada uma flag inexistente nem declarada extração genérica de archives. |
| [Git: ambiente e protocolos](https://git-scm.com/docs/git) | Histórico ro, sem configuração global, protocolos/lazy fetch desabilitados, sem ext-diff/textconv. Histórico raso, parcial, com objetos externos ou mutável bloqueia. Não baixa/unshallow automaticamente. |
| [Opengrep 1.33.1: opções](https://github.com/opengrep/opengrep/blob/v1.33.1/src/osemgrep/cli_scan/Scan_CLI.ml) | Catálogo amplo mais regras Ec, taint intrafile, sem nosem/gitignore/corte de tamanho ou timeout por regra. Deadline global permanece; erro, skipped_rules e perda de arquivos reprovam. Validação nativa do catálogo e positivos/negativos locais têm papéis distintos. |
| [CodeQL: suítes](https://docs.github.com/en/code-security/concepts/code-scanning/codeql/codeql-query-suites) e [Rust security-and-quality](https://github.com/github/codeql/blob/main/rust/ql/src/codeql-suites/rust-security-and-quality.qls) | security-and-quality em Rust e JS/TS; projeto adicional Python da automação na mesma configuração. Exige extração saudável e compilações críticas Rust válidas. Ausência de pack/suíte/runtime bloqueia, sem fallback para consultas menores. |
| [OSV 2.7.0: fontes](https://github.com/google/osv-scanner/blob/v2.7.0/docs/scan-source.md) e [offline](https://google.github.io/osv-scanner/usage/offline-mode/) | Três locks declarados, all-packages, sem overrides/ignores. Call analysis Rust é experimental e contextualiza alcançabilidade; não substitui o inventário nem é usado para filtrar achados. |
| [Trivy 0.76.2: detecção](https://github.com/aquasecurity/trivy/blob/v0.76.2/pkg/flag/scan_flags.go), [imagem](https://github.com/aquasecurity/trivy/blob/v0.76.2/pkg/flag/image_flags.go) e [licenças](https://github.com/aquasecurity/trivy/blob/v0.76.2/pkg/flag/license_flags.go) | comprehensive, todas as severidades, dev deps e vulnerabilidades sem correção; somente fontes/configurações Ec do snapshot. As imagens das ferramentas não são auditadas. Licenças são inventariadas em chamada própria, sem inventar aprovação jurídica. |
| [Syft 1.23.1: catálogo](https://github.com/anchore/syft/blob/v1.23.1/cmd/syft/internal/options/catalog.go), [archives](https://github.com/anchore/syft/blob/v1.23.1/cmd/syft/internal/options/pkg.go) e [metadados de imagem](https://github.com/anchore/syft/blob/v1.23.1/syft/source/image_metadata.go) | Fonte única da catalogação: diretórios/arquivos Ec, archives indexados/não indexados e sem remoção de binários por sobreposição. Busca em archives tem o alcance do catalogador Java nessa versão. Não habilita enriquecimento de rede. |
| [Grype 0.119.0](https://github.com/anchore/grype/blob/v0.119.0/cmd/grype/cli/options/grype.go) | Consome o SBOM Syft identificado; sem ignores/filtros de correção/estado e com geração de CPEs ausentes. Não replica a política de catalogação/camadas no Grype. Analisa somente o inventário do filesystem Ec. |
| [Bandit 1.8.3](https://bandit.readthedocs.io/en/1.8.3/config.html) | Todos os testes/severidades; inclui except tipado em try/except pass/continue; nosec não reduz cobertura. Revisões de fontes alteradas são removidas, sem renovar hashes para herdar aprovação. |
| [YARA-X 1.0: scan](https://github.com/VirusTotal/yara-x/blob/v1.0.0/cli/src/commands/scan.rs) e [compile](https://github.com/VirusTotal/yara-x/blob/v1.0.0/cli/src/commands/compile.rs) | Catálogos importados são compilados uma vez por grupo de conteúdo/contexto, sem ignore-module. NDJSON comprova também arquivos sem matches; perda/duplicidade de arquivos reprovam. Metadados de autoria são preservados, sem strings de conteúdo dos alvos. |

### Catálogos, autoria e integridade

`tools.opengrep.catalogs` e `tools.yara-x.catalogs` registram repositório, revisão
imutável, licença, SHA-256 do archive e da árvore importada e quantidade de arquivos.
Arquivos importados ficam sob a pasta de regras responsável, com os avisos/licenças
originais. Não são configurações concorrentes; os bytes são conferidos antes do uso.

Foram selecionados os mantenedores [elttam](https://github.com/elttam/semgrep-rules),
[Trail of Bits](https://github.com/trailofbits/semgrep-rules),
[njsscan](https://github.com/ajinabraham/njsscan) e
[signature-base](https://github.com/Neo23x0/signature-base), com licenças preservadas
por catálogo. A importação Opengrep seleciona linguagens presentes no escopo;
não copia executáveis/fixtures dos fornecedores para aparentar código do produto.
O fork opengrep-rules está arquivado e não foi adotado. O catálogo atual Semgrep
restringe redistribuição segundo sua [licença](https://semgrep.dev/legal/rules-license/),
por isso não foi incorporado ao repositório.

Opengrep qualifica o conjunto importado por scan estrito offline sobre amostras
Rust, Python, JavaScript, TypeScript, YAML e HCL, com regras genéricas aplicadas
às mesmas amostras, rejeitando
erros/regras/arquivos omitidos, e mantém os testes locais das regras Ec.
O --validate dessa versão baixa p/semgrep-rule-lints obrigatoriamente; não é
utilizado offline nem substituído por liberação de rede. A qualificação comprova
schema/padrões nativos, sem alegar a execução desses metachecks remotos.
Essa combinação não equivale a um positivo executado para cada regra do catálogo.
YARA-X preserva regras Ec como sentinelas, acrescenta contexto por arquivo às
assinaturas que o utilizam e testa esse transporte separadamente. filename, filepath,
extension e owner vêm do arquivo original; filetype distingue PE comprovado pelo
header de UNKNOWN. Não há libmagic nem promessa de classificação universal.

Os catálogos são dados de análise: YARA-X não examina suas próprias assinaturas
nem as regras/positivos Opengrep como se fossem payloads do produto. Fontes e
testes reais da automação permanecem nos alvos. Metadados externos de autoria e
licença não são substituídos por licença Ec.

### Escopo Ec e dados privados

Por instrução vigente de 13/11/2026, os gates analisam somente o Ec: fontes,
dependências, configurações e inventário de pacotes do snapshot selecionado.
A Toolbox fornece ferramentas, versões e bases necessárias à execução; não é
alvo dos scanners nem da suíte do consumidor. Não há seletores de imagens,
exportação Docker ou auditoria de pacotes das imagens das ferramentas.

Trivy executa filesystem com vuln/misconfig/secret e inventário de licenças
separado. Syft cataloga diretórios/arquivos Ec e Grype analisa esse SBOM, exigindo
inventário dos três locks. Positivos/negativos de detecção e rejeição de relatórios
parciais continuam ativos. Nenhum achado do Ec é dispensado pela mudança de escopo.
Trechos de segredos são redigidos antes da exportação dos relatórios; snapshots
e bytecode são descartados ao encerrar o run.

A ampliação pode produzir achados de qualidade ou segurança antes ausentes.
Nenhum deles é dispensado automaticamente para recuperar o verde anterior.
Os testes e a qualificação foram autorizados; os resultados e as pendências
estão no [relatório da ampliação](../validation/security-coverage-20261112/report.md).
Aprovação histórica não se transfere para bytes alterados nem achados novos.

No CodeQL Rust permanece o build-mode none suportado oficialmente; a extração
usa rust-analyzer e as compilações de scripts/macros são verificadas. Não foi
inventada uma opção all-features do extrator nem declarada cobertura de todas
as combinações condicionais. Os gates Cargo que aceitam all-features continuam
explícitos. A qualificação deve confrontar os arquivos esperados com as fontes
extraídas saudáveis, conforme o contrato SARIF.

Clippy usa keep-going para examinar crates independentes mesmo após erro de
outro membro; os erros continuam reprovando. Não é promessa de análise de
dependentes que não puderam compilar. Limites de recursos e de diagnósticos dos
motores continuam capazes de interromper a execução, sempre sem aprovação parcial.
Os metadados NDJSON do YARA-X 1.0 são pares ordenados, preservados inclusive
quando há chaves repetidas, sem conversão que descarte autoria.

A seleção Opengrep respeita o parser da extensão; não força todos os parsers
sobre arquivos de extensão desconhecida. Regras genéricas continuam aplicáveis
aos arquivos selecionados, e omissão de qualquer alvo impede aprovação.
Na versão Trivy instalada, include-dev-deps pertence a filesystem; imagens
recebem o catálogo de pacotes instalado suportado pelo subcomando image. A flag
inexistente nesse subcomando não é enviada. Grype preserva ignoredMatches,
conta descartes nativos separadamente como pendências e nunca os usa para aprovar
sem revisão. Assim todos os alvos são examinados sem ocultar esses descartes.

A inspeção da configuração efetiva Syft revelou que dev dependencies não eram
incluídas por padrão. A política nativa foi corrigida com javascript.include-dev-dependencies,
a união dos catalogadores image/directory/file e a retirada da exigência de DLL
para catalogar pacotes .NET declarados. Mantém buscas remotas desativadas e
não inventa versões de requisitos sem pin. Fonte/imagem passam a incluir pacotes
declarados e instalados: uma associação não prova que o pacote está em execução.
O recibo nativo registra a configuração efetiva. Um oráculo independente exige
cada dependência externa resolvida, com nome/versão/tipo e localização do lock;
presença do lock no SBOM não basta. Registros locais/link de workspace continuam
no inventário nativo, sem tratá-los como dependências externas de registry.
