# Convergência — scripts de verificação

Estado atual conforme [evidence.json](evidence.json). As seções datadas abaixo
registram execuções e decisões anteriores; seus resultados e autorizações não
se transferem a novos inputs ou tarefas. Retenção vigente em
[local-state](../local-state/spec.md).

## Exclusões do inventário — 12/11/2026

Pedido local: completar exclusões de arquivos e pastas gerados, preservando
fontes e testes. `scripts/config/automation.json` amplia os padrões centrais
para ambientes virtuais, caches, cobertura, relatórios, arquivos compilados,
temporários e resíduos de editor; estado operacional da aplicação e saídas
específicas do frontend usam caminhos exatos. Não interpretar `.gitignore`,
excluir testes ou suprimir regras do Bandit. O guia de scripts documenta o
contrato; `test_automation_config.py` acrescenta regressão para exclusão em
all/sast/trivy/inventory e preservação de fontes, fixtures, logs de evidência,
plugins/releases Yarn e lockfiles, inclusive com `.gitignore` divergente.

Método: leitura dos consumidores `source_files`, `inventory`, `Gate.prepare` e
`Gate.targets`, comparação com `.gitignore` e inspeção manual das alterações.
AC-21 permanece sem novo aceite executado nestes inputs. Testes, scanners e
suíte não executados neste pedido, conforme a política canônica; os 373 alertas
do Bandit no run-JGLXfZgv não foram resolvidos por esta mudança de inventário.
Alterações preexistentes e evidências históricas foram preservadas.

## Ambiente automático — 11/11/2026

REQ-30 → D-26/R-26 → T045 → `environment.sh`, `runtime.sh`, `automate.sh` e
diagnóstico RustSec. O ambiente passa a ser montado uma vez por run e entregue
às tarefas de qualquer escopo, preservando overrides e sem alterar o chamador.
Regressões em `scripts/tests/test_environment.py` cobrem descoberta local,
precedência, espaços, bases múltiplas, segredos e saída do filho; ainda não
executadas. Inspeção manual dos consumidores, fontes e diff; AC-30 UNVERIFIED,
T046 pendente. A Toolbox existente foi localizada, mas bases/caches/ferramentas
não foram preparados, baixados ou homologados. O modo Cargo offline permanece;
os erros de testes e de SDD observados no run anterior não foram corrigidos por
esta alteração de ambiente. Nenhuma suíte, teste ou script de verificação foi
executado neste pedido, conforme a política canônica.

## Diagnóstico do run-3WGnk3sO — 11/11/2026

Inspeção dos logs existentes identificou Toolbox não selecionada, dependências
Cargo indisponíveis offline, RustSec/CodeQL não configurados e falhas locais de
documentação/testes. A instalação Toolbox foi localizada em
`/home/rodrigo/Projetos/dev_audit/shared-toolbox/bin/toolbox`; existência do
executável não comprova ferramentas ou bases ativas. A referência quebrada a
`previous.zip` em `docs/development/scripts-inventory.md` foi substituída pelo
registro da remoção autorizada. A política de faixas Rust em desenvolvimento
está no guia `docs/development/verification-tools.md`; o runner offline e os
locks não foram alterados. Método: inspeção de streams/fontes e diff, sem
reexecução, download ou renovação de evidências. Os demais erros permanecem
pendentes; esta correção documental não constitui aprovação da suíte.

## Convergência nativa — 08/11/2026

REQ-20–26 → D-20/R-20 → T028–T033. Implementação independente no Ec concluída;
aceite integral REJECTED. Run final run-13bf8eQO, sem filtros: exit2, 24 passed,
12 blocked, 7 error, sem alteração de inputs e sem cancelamento. 80 testes de
contrato e 34 de governança passaram; HTTP/PostgreSQL e Firefox reais passaram.

| Critério atual | Estado | Evidência / limite |
| --- | --- | --- |
| AC-20 | APPROVED | Wrapper/argv/override/exits em contrato; E-NATIVE-CONTRACT |
| AC-21 | APPROVED | Seleção, relatórios, supressões, modos/symlinks e integridade em contrato; E-NATIVE-CONTRACT |
| AC-22 | UNVERIFIED | Comandos/regras implementados, motores sem versões centrais ativas |
| AC-23 | UNVERIFIED | Import/isolamento de dependências implementados; Chromium central não executado. Firefox separado passou |
| AC-24 | UNVERIFIED | Create/resolve/analyze implementados, nenhum banco/SARIF real; runtime Rust/Cargo fornecido; falta ativação/qualificação central |
| AC-25 | APPROVED | Contrato de outcomes/códigos/segredos/sinais/cancelamento tardio; limpeza real Docker ainda pendente em AC-26 |
| AC-26 | REJECTED | E-NATIVE-FULL: requisitos obrigatórios bloqueados e sete erros |

T028–T031: implementação/contratos exercitados. T032: suíte executada, aceite
rejeitado. T033: documentação, evidência e recuperação entregues com limites.
Reabrir T030–T032 para homologar imagens/bases, runtime CodeQL e operações reais
após preparo externo; não instalar fallback ou reduzir limiares. Os critérios
históricos AC-01–19 não foram promovidos por estes testes; seu estado anterior
permanece preservado e as partes substituídas estão identificadas na spec.

[Relatório e retomada](../../../docs/validation/toolbox-native-20261108/report.md),
[recuperação](../../../docs/validation/toolbox-native-20261108/recovery.md).
O binding de conteúdo corresponde às fontes observadas; nenhuma evidência antiga
foi renovada para parecer teste atual. Sem commits, downloads ou manutenção central.

## Histórico preservado

# Convergência — documentação e fronteira Ec em 7 de novembro de 2026

Documentação vigente e `.gitignore` sincronizados com o plano. REQ-13 a REQ-18,
AC-13 a AC-18 e T019–T024 estão registrados; critérios permanecem **UNVERIFIED**.
A implementação ainda usa volumes externos, checks adicionais e Trivy nativo no
padrão, com YARA-X bloqueado. O ignore local não corrige esse comportamento.
Nenhum scanner, teste, preparo ou script de verificação foi executado.

T018 foi substituída como roteiro de retomada por T019–T024; não iniciar preparo
pela CLI compartilhada sob a restrição de escrita apenas no Ec. Retomar por T019,
com redirecionamento de estado via ponte local e testes de fronteira; seguir
[dependências e critérios](toolbox-completion-plan.md). Downloads e verificações
continuam sujeitos às autorizações e à política canônica. A escolha de executar
uma suíte não autoriza mudanças externas.

[Relatório desta revisão](../../../docs/validation/toolbox-docs-20261107/report.md).
Registros e estados anteriores abaixo são históricos, sem renovação de hashes.

# Convergência — contrato Toolbox/Ec em 7 de novembro de 2026

T017 implementada e inspecionada; AC-12 **UNVERIFIED**. Nenhuma imagem/base foi
preparada e nenhum script/teste executado. T018 permanece pendente. AC-01 a AC-11
continuam UNVERIFIED nos inputs atuais; observações históricas abaixo são preservadas.
[Relatório atual](../../../docs/validation/toolbox-contract-20261107/report.md).

# Convergência — gates padrão em 7 de novembro de 2026

## Análise e correções do run-oUebC3O4

O run histórico contém 31 passed, seis error, um findings e cinco blocked;
exit agregado 2, sem drift registrado. Foram corrigidos os defeitos demonstrados
nos testes/Geiger/LCOV e as justificativas Ruff. T015 concluída por implementação
e inspeção; T016 e a execução de T014 permanecem pendentes. Nenhum script foi
executado nesta tarefa. AC-01 a AC-11 continuam UNVERIFIED nos inputs editados.

Licença própria, preparo ShellCheck, ferramentas/provider de cobertura e
Trivy/CodeQL continuam pendentes conforme o
[relatório atual](../../../docs/validation/automation-run-oUebC3O4-20261107/report.md).
As observações abaixo são anteriores às correções e permanecem históricas;
seus arquivos/evidências originais foram preservados em previous.zip.

AC-01 a AC-11: **UNVERIFIED nos inputs atuais**. A implementação e os testes foram
preparados e inspecionados manualmente; não houve execução de scripts, preparos
nativos ou motores neste pedido. O lockfile foi atualizado por npm com
--package-lock-only --ignore-scripts; isso não comprova build nem cobertura.

T011/T012/T013 registram implementação, não aceite executado. T014 permanece
pendente do fluxo de commit e resposta explícita sim. A observação histórica de
Deny reprovado por licenças é preservada; não é uma execução nos inputs atuais.
A cobertura dos três componentes permanece desconhecida.

[Relatório, inputs e backup](../../../docs/validation/default-quality-20261107/report.md).
Retomada: preparar dependências/ferramentas/bases/bancos conforme o contrato nativo;
quando houver autorização no fluxo vigente, executar a suíte completa e analisar
os resultados sem reduzir os gates. Ferramenta/pack/base ausente bloqueia.

## Convergência anterior, preservada como histórico

# Convergência — 7 de novembro de 2026

AC-01/02/03/05/06/07/08 permanecem APPROVED pela inspeção dos contratos,
43 testes de scripts, 34 de governança, 23 da plataforma e execução real dos
scanners conforme o [relatório](../../../docs/validation/tooling-remediation-20261106/report.md).

AC-04 está REJECTED: todas as ferramentas executaram, mas Cargo Deny reprova os
11 crates privados Ec sem licença definida. Advisories/bans/sources passaram;
nenhuma exceção de licença foi aplicada. T010 conclui a correção das integrações,
a triagem e a coleta; T004 continua dependente da decisão sobre licença/escopo.
AC-01/AC-07 da primeira jornada permanecem UNVERIFIED por suas lacunas próprias.

A observação anterior está preservada em previous-evidence.zip do relatório.
Retomada: resolver a política de licença dos membros privados, executar Deny nos
dois workspaces e atualizar apenas as evidências afetadas. Não confundir sucesso
dos scanners ou integração com aceite agregado do produto.

## Convergência de 08/11/2026 — estrutura migrada, operação bloqueada

REQ-19 → D-19/R-19 → T025/T026 → scripts/config, wrapper comum, stacks e recibos.
T027 depende da plataforma e execução autorizada. AC-19: UNVERIFIED para aceite
operacional; implementação local e exclusões foram inspecionadas. AC-01–AC-18
continuam UNVERIFIED nos inputs atuais. Não foram executados automate.sh, testes,
preparos, downloads ou scanners. Histórico anterior preservado em
[relatório](../../../docs/validation/scripts-stic-20261108/report.md) e previous-*.

## Limpeza de 09/11/2026 — inspeção, sem execução da suíte

T034 e critérios L01–L05 estão registrados no
[relatório da limpeza](../../../docs/validation/cleanup-20261109/report.md).
Os consumidores vigentes e a integração nativa foram inspecionados. Foram
preservados código de automação, contratos opcionais, regras/fixtures, testes,
configs, logs e históricos. Exclusões têm inventário, cópias, precondições e diário.
O guia temporário erase.md saiu da raiz e foi arquivado integralmente como histórico.
Os estados anteriores permanecem datados; esta limpeza não reexecuta nem aprova
AC-01–AC-26, cuja validação nos inputs alterados permanece UNVERIFIED.

## Adequação ao contrato central — 09/11/2026

REQ-27 → D-21/D-22, R-21/R-22 → T035–T037. Ambiente de Docker/estado,
database_metadata upstream e Checks Bundle externo foram implementados e
inspecionados conforme os guias centrais. Casos de regressão escritos, não
executados. AC-27 permanece UNVERIFIED; os critérios operacionais anteriores
não são renovados. [Relatório e recuperação](../../../docs/validation/toolbox-alignment-20261109/report.md).

## Revisão de obsolescência em scripts/ — 09/11/2026

T038 conclui a retirada documental justificada no
[relatório](../../../docs/validation/scripts-cleanup-20261109/report.md).
Os arquivos operacionais mantêm os mesmos bytes; conteúdo histórico foi
preservado fora do guia corrente. Nenhum critério operacional foi renovado;
AC-27 continua UNVERIFIED, sem execução de testes ou scripts de verificação.

## Diagnóstico Geiger — 09/11/2026

T039 preserva o inventário e a reprovação de REQ-09/AC-09, adicionando explicações
por caminho e referências aos logs brutos. O helper não transforma aviso em
autorização de exclusão; membros seguintes continuam não examinados após falha.
Os consumidores dos READMEs e do private.rs foram inspecionados estaticamente.
Regressões escritas, não executadas; AC-09 e demais critérios continuam
UNVERIFIED. [Evidências e limitações](../../../docs/validation/geiger-diagnostics-20261109/report.md).

Após novo pedido de commit e escolha sim, a execução run-8ca5QvB9 revelou um
problema de localização do helper; ele foi movido para tools/quality sem
enfraquecer a regra. A reexecução integral run-BvlgAwX0 aprovou os 95 testes de
automação e produziu os diagnósticos reais, mas manteve dois erros Geiger e
três de cobertura. AC-09 e demais critérios continuam UNVERIFIED; nenhum novo
commit foi criado. Os resultados das duas rodadas e os logs anteriores foram
preservados no relatório de T039.

## Correção Geiger/cobertura — 09/11/2026, sem execução

REQ-28 → D-23/R-23 → T040: correção no mesmo motor Geiger, classificação de dados
por consumidor e inclusão de fontes geradas por dep-info. REQ-29 → D-24/D-25,
R-24/R-25 → T041/T042: coleta das jornadas instrumentadas e novos testes úteis.
Fontes, decisões e hashes em
[relatório e observações](../../../docs/validation/geiger-coverage-remediation-20261109/report.md).

AC-09/10/28/29: UNVERIFIED nos inputs atuais. A última execução histórica
run-BvlgAwX0 permanece com cinco erros; não foi reclassificada ou sobrescrita.
Não houve build, execução de testes/scanners, scripts de verificação, automate.sh,
download, staging, commit ou push nesta tarefa. Rustfmt foi usado somente para
editar a formatação de arquivos Rust; isso não é evidência de compilação.
T043 depende da execução autorizada pela política vigente. Os demais critérios
anteriores permanecem sem promoção de estado por esta alteração.

## Convergência executada após pedido de commits — 09/11/2026

As pendências de execução T040–T043 descritas acima foram superadas por
run-iNYFOPoN: 43 tarefas aprovadas, exit 0, inputs estáveis e sem cancelamento.
T043 concluída. AC-09/10/28/29 APPROVED com observações e bindings novos em
evidence.json; critérios restantes mantêm seus estados, sem aprovação inferida.
Geiger completo nos seis membros Server e cinco Agent; cobertura Server 92,60%,
Agent 91,25% e frontend 97,32%. Segurança, HTTP, Playwright e Firefox passaram.
As três rodadas anteriores deste pedido reprovaram e estão preservadas no
[relatório](../../../docs/validation/geiger-coverage-remediation-20261109/report.md).
A autorização específica para baixar dependências mais recentes foi respeitada;
foram selecionadas as versões compatíveis com o manifesto/MSRV do Geiger, com
455 archives conferidos. Nenhum lock de aplicação foi atualizado por esse preparo.
Somente registros documentais foram ajustados após a suíte; sem alteração
funcional posterior ou execução de checks avulsos.


## Escopo completo explícito — 11/11/2026

Pedido: oferecer `--scope complete` em `scripts/automate.sh`. Método aplicado:
`cli-engineering`, por inspeção da CLI, catálogo, inventário e manifesto frontend.
Mudança delimitada: parser/help e metadado `scope`; catálogo inclui os quatro
scanners complementares preservando tarefas, dependências e argumentos padrão.
Filtros e valores inválidos são rejeitados; seleção padrão permanece compatível.
README registra comandos, pré-requisitos e exclusões justificadas pelo inventário
(preparo/dev e opções frontend sem configuração). Helpers mantêm seus consumidores.

Foram adicionados casos em `scripts/tests/test_shell.py` para preservação das
tarefas padrão, inclusão exata dos complementares, ausência de duplicatas e
rejeição de filtros/valores inválidos. Inspeção manual dos arquivos alterados
concluída; testes, planejamento e suíte não executados conforme a política vigente.
Aceite operacional do novo escopo permanece UNVERIFIED; nenhum resultado ou
binding histórico foi renovado. Retomar execução pelo fluxo de autorização
canônico ou instrução explícita da sessão.


## Diagnóstico de run-orZMYUEe e correções — 11/11/2026

Pedido: analisar sete grupos de falhas, corrigir imports Python/SDD e analisar
a falha Vue; confirmação posterior autoriza atualizar Cargo.lock durante as
verificações. Método: leitura dos logs existentes, documentação/implementação
Toolbox, recursos locais, consumidores e diffs. Nenhum check foi reexecutado.

- SELinux: a documentação central application.md#selinux já prevê
  TOOLBOX_SELINUX_LABEL=shared; o diagnóstico deste run registra seleção vazia,
  enforcing e automatic_relabel=false. Ec transporta a seleção quando fornecida.
  Negação de acesso foi observada; AVCs não foram inspecionados.
- Cargo: locks da aplicação registram postgres 0.19.12, ryu 1.0.23 e
  serde_json 1.0.145; ~/.cargo contém serde_json 1.0.154, sem as fontes/archives
  de postgres/ryu nas versões solicitadas. O cache privado foi removido pela
  limpeza documentada de 11/11; um manifest/lock não contém os bytes dos crates.
- RustSec: base existente em
  /home/rodrigo/Projetos/ox/.cache/ox/security/generations/prepared-PV2MOyyo/rustsec,
  HEAD 309392b18f6b17022f82c701f300433126901068, commit de 06/11/2026, árvore
  sem diferenças. O caminho antigo .local/security não existe. É um problema
  de seleção do recurso local, sem necessidade demonstrada de download.
- Toolbox: native.py chama refresh_osv_before_run somente para osv-scanner;
  application.md documenta ausência/idade de aquisição >12h. O diagnóstico OSV
  deste run registra osv_refresh.refreshed=true. Trivy mantém skip de DB/Java DB;
  sua base de 09/11 estava expirada para o limite Ec de 24h. Docker não renova
  universalmente bancos; o caminho externo correto usa shared-toolbox, com hífen.
- Python: removidos os dois imports hashlib sem indentação em test_shell.py;
  o import de módulo existente permanece. Nenhum teste foi apagado.
- SDD: a referência em prosa AC-05 deixou de começar uma linha como declaração.
  E-COMMIT-FULL-20261109 foi desvinculado do aceite atual: os 160 bindings do
  manifesto commit-passed-inputs.json e o recibo geiger-dependencies.json já
  preservam os hashes originais. Resultado e relatório históricos permanecem.
  Não foram renovados hashes para fazer uma execução antiga parecer atual.

REQ-31 → D-27/R-27 → T047: removidos --locked de Rust/LLVM/Geiger e o requisito
exato de syn; todos continuam offline. O supervisor registra updated_lockfiles
com identidades antes/depois, conserva alterações e exige nova suíte para
comprovar a resolução final. As regressões cobrem persistência do lock,
reexecução estável e mudança simultânea de fonte; escritas, não executadas.
T048/AC-31 permanecem UNVERIFIED, assim como os demais critérios da feature.
A alteração permite resolver novamente quando necessário; não executa cargo
update automaticamente nem garante que o cache existente seja suficiente.

Observações do frontend e proposta de correção em
[frontend-signal](../../../docs/validation/frontend-signal-20261111/report.md).
Não houve edição do store nesta análise, download, instalação, relabel,
administração da Toolbox, execução da suíte, commit ou publicação.

Inputs das correções inspecionadas (SHA-256):
- `scripts/tests/test_shell.py`: `b0957e59f08b4517177ce9dd585a3957690ad7b2518b382e17425ea94ae93a01`.
- `scripts/tests/test_runtime_coverage.py`: `aa902acf7f9ddf1a5264190e3bdf5f8ba792faf1f3896992c7be985d62b981a8`.
- `scripts/stacks/ec/runtime.sh`: `fe6d6ab190dd8d5508182d7554bfe7594074568bcd0b522abcd4f73b9ba8a0a4`.
- `scripts/stacks/rust/check.sh`: `1a2a95d0499430672f5a240d9ad082a46113b15a4b710b54825688334ac49c47`.
- `tools/quality/collect_runtime_coverage.py`: `112d078814dd1acfdcbaf438bd71e5f003ac2686248504b4116c0e5ec0bc52fe`.
- `tools/quality/vendor/cargo-geiger/Cargo.toml`: `eb33da4e537974a0c9ed53550ae689ebc526746401b9cf2a89782c98f18bd3cc`.
- `sdd/features/governance-adoption/spec.md`: `1cd2dc61a7dcb0f83c6759f323b39808c0ca4780fe17c217ca89be94fb424ab5`.

## Preparo automático e correções complementares — 11/11/2026

Pedido posterior: definir ambiente somente durante a execução, usar o toolchain
ativo, reutilizar crates compatíveis existentes, baixar ausentes e corrigir os
demais pontos. REQ-32 → D-28/R-28 → T049 implementados; T050/AC-32 UNVERIFIED.

- `RUN_ENV` define Toolbox/estado, Cargo/Rustup, runtime CodeQL, cache Geiger,
  RustSec, SELinux shared e preparo Trivy. Respeita overrides; não altera perfis
  nem o shell chamador. A vida das variáveis é a dos subprocessos.
- Toolchain observado via Rustup, sem seleção numérica fixa em Rust/CodeQL.
  `cargo update --offline` resolve as faixas compatíveis antes do consumo do
  grafo. A fonte Geiger com a correção Ec permanece versionada; dependências
  e lock podem ser atualizados, sem exigência exata de syn.
- Audit reutiliza o RustSec localizado; Deny recebe a mesma base por link e
  política temporários. Não houve download de RustSec, cópia do banco nem
  alteração da política de advisories/licenças.
- Trivy renova DB e Java DB ausentes/vencidas antes das fixtures e do scan,
  pela manutenção pública da Toolbox, com raiz vazia e rede somente no preparo.
  Imagem ativa, proveniência, idade e publicação são conferidas; falha parcial
  impede aprovação. O scan continua offline. A autorização para renovar ambas
  as bases foi concedida explicitamente nesta sessão.
- Imports Python/SDD permanecem corrigidos. A correção de reatividade do store
  Vue foi aplicada e ganhou regressão; a jornada App existente foi preservada.

Preparo Cargo executado, sem build/teste: update offline e fetch nos manifests
Agent, Server e Geiger. Primeiro foram usadas as versões locais; cargo info
preencheu índices/crates ausentes e fetch completou arquivos da nova resolução.
Agent/Server reutilizam serde_json 1.0.154; ryu saiu dos dois grafos e não foi
baixado. Postgres não estava no cache: foi adquirido 0.19.14, compatível com a
faixa declarada. sha2 0.11.1 existia, mas não atende à API 0.10 da aplicação;
foi preparada a dependência compatível sem alterar essa faixa.

Ao final, `cargo update --offline` e `cargo fetch --offline` terminaram com
exit 0 para os três manifests. O preparo não executa build scripts nem prova
compatibilidade da aplicação. Os locks registram a resolução obtida, sem
transformar seus números em novas restrições dos manifests. Scripts de
verificação, testes, compilação, scanners e suíte não foram executados.
Nenhum aceite histórico foi promovido nem hash renovado para simular aprovação.

### Renovação Trivy observada nesta sessão

Somente aquisição, sem scan: imagem ativa 0.76.2,
`sha256:216aeb3c2b69bc83a066f639678d36a62f2e0e22110e3371b24e7bf230b7f518`.
Primeiro, DB foi renovada com sucesso; Java download-only retornou zero e
preservou a base de 09/11 por causa do cache nativo de três dias. A fonte oficial
[Updater.Update/isNewDB](https://raw.githubusercontent.com/aquasecurity/trivy/v0.76.2/pkg/javadb/client.go)
confirma a decisão por NextUpdate/DownloadedAt. A data da aquisição não foi
usada para disfarçar a idade upstream.

Foi aplicada a opção pública --refresh-database na Toolbox externa, limitada
ao download correspondente em manutenção. Somente o cache selecionado no
staging é descartado; o snapshot anterior permanece intacto. Foram escritos
testes de rejeição de uso indevido, preservação em falha e publicação em sucesso,
sem executar a suíte da Toolbox. Documentação e referências externas atualizadas.
O Ec exige essa interface no preparo. Integridade de todas as fontes é
conferida antes de considerar expiração, para uma base velha não ocultar
metadados inválidos da outra.

Nova aquisição Java terminou com exit 0 e cleanup removed. Ponteiro final:
`4ee9b35ca9163683f2dc7303dad06bb545435c90f662a2c1e12b1eba5a16cab6`.
Metadados nativos lidos diretamente no snapshot publicado:

| Base | UpdatedAt UTC | NextUpdate UTC | DownloadedAt UTC |
| --- | --- | --- | --- |
| DB | 11/11/2026 19:04:54 | 12/11/2026 19:04:54 | 11/11/2026 22:01:45 |
| Java DB | 11/11/2026 01:07:27 | 14/11/2026 01:07:27 | 11/11/2026 22:11:30 |

Ambas estavam dentro de 24h na observação final. Diagnósticos registram SELinux
enforcing com seleção shared, sem OOM, saída nativa zero e container removido.
Logs sujeitos à retenção em `.local/logs/2026-11-11/`:
`prepare-756ae79d2d-trivy-{db,java-db}.*.log` e
`prepare-b66294d85b-trivy-java-db.*.log`. A primeira tentativa Java sem renovação
continua registrada; não foi substituída pelo sucesso posterior.

As variáveis do preparo existiram apenas nos subprocessos; a raiz vazia em
/tmp foi removida ao encerrar. Bases/snapshots centrais e cache Cargo permanecem
como recursos preparados. A execução real comprova a aquisição; não aprova
os gates, testes de regressão ou a suíte completa, que continuam não executados.

## Unificação e navegadores no Docker — 11/11/2026

O pedido posterior ao run-d2D7orGR produziu uma configuração responsável em
`scripts/config/automation.json`. T051–T053 estão implementadas e inspecionadas;
T054 e AC-33/34/35 permanecem sem evidência executada. O run anterior não valida
estes bytes. Nenhuma nova suíte, build, scan ou teste foi executado nesta etapa.

- Catálogo, ambiente, inventário, componentes CodeQL, cobertura, recursos e
  serviços consomem o JSON validado. As políticas nativas continuam nos formatos
  das ferramentas, referenciadas uma vez. Configurações paralelas de cobertura e
  projetos CodeQL foram retiradas. Grafo, listas, backends e limites têm validação
  anterior à execução; raízes symlink não são atravessadas pelo inventário.
- Cargo e plugins são os instalados no host, usando o toolchain observado.
  Preparo offline resolve cada workspace antes dos consumidores; cobertura inclui
  também o preparo do componente companheiro. A cópia Geiger anterior foi retirada
  dos gates e identificada como histórica. Não se oculta falha do plugin instalado.
- A inspeção dos metadados locais encontrou LLVM em outra instalação com a mesma
  release, commit, host e versão LLVM do compilador ativo. O código compara esses
  campos dinamicamente, sem nome/número de toolchain fixado. Essa inspeção não
  demonstra produção de cobertura nem atendimento do limiar.
- PostgreSQL valida o bind loopback e registra ID/estado antes de iniciar. A
  limpeza restaura apenas o container iniciado pelo run, tem deadline e invalida
  o resultado se falhar. Variáveis permanecem no ambiente dos subprocessos.
- Grype ganhou inspeção de integridade/idade, manutenção antes do scan e vínculo
  com o snapshot publicado. Trivy mantém preparo das duas bases. Relatórios
  nativos JSON/SARIF e diagnósticos sobrevivem como logs privados após limpeza;
  dados de segredos recebem redação específica. Nenhuma base foi baixada nesta
  etapa de implementação; autorização de novos downloads permanece distinta.
- A Toolbox externa recebeu seleção pública `exec --browser` com aprovação
  explícita para a alteração fora do checkout. A seleção usa variante registrada
  compatível com a versão ativa, sem alterar a ativação. Seus testes foram escritos
  e não executados; essa mudança externa é requisito para as novas jornadas.
- Chromium e Firefox usam o mesmo harness real via Playwright Docker. O servidor
  estático/proxy do frontend também está no container. IPC, credenciais e downloads
  são privados e descartáveis; downloads são publicados somente após conclusão.
  O driver Firefox/Marionette do host foi removido. Só Chromium foi encontrado
  preparado; Firefox continua a cargo do usuário, sem instalação implícita.

Regressões escritas cobrem configuração inválida, seleção transitiva, backend,
serviço previamente ativo/parado, LLVM compatível/incompatível, bases vencidas/
futuras/corrompidas, falha de manutenção, retenção e a interface dos dois browsers.
Asserts frontend foram ajustados para verificar o valor do campo e os controles
privados da sessão. Inspeção manual e `git diff --check` não são aprovação dessas
regressões nem dos oráculos reais. A nova suíte completa foi apresentada para
autorização conforme a política de verificação; até sua execução, não há aceite.

### Correção da seleção de recursos internos

O usuário encontrou `superfície de tarefa desconhecida` antes de iniciar o run.
A validação adicionada na unificação rejeitava `surfaces: ["prepare"]` nas quatro
tarefas internas, pois `prepare` não pertence às superfícies públicas. Corrigido
para `surfaces: []`: os recursos entram somente pelo fechamento de dependências.
O carregador agora identifica tarefa/valor desconhecidos no diagnóstico e rejeita
superfície pública em tarefa exclusiva de dependência. Regressões foram escritas.
`./scripts/automate.sh plan --scope complete` retornou zero e produziu o grafo com
os quatro recursos antes dos consumidores, com `executed: false`. Essa inspeção
do plano não executou testes, downloads, serviços ou a suíte; aceite segue pendente.

### Revisão abrangente após o erro de superfície

Pedido vigente: analisar a automação completa em busca de outros erros. Método
software-design mantido, com rastreamento de consumidores, propriedade de recursos,
erros e limpeza. A análise abrangeu entrada/seleção, grafo, configuração/ambiente,
inventário, executor Rust/frontend, cobertura, parsers, Toolbox, manutenção de bases,
CodeQL, supervisor, retenção/resumo, harnesses HTTP e ponte Playwright Docker.

Problemas confirmados por inspeção e corrigidos:

| Caminho | Problema | Correção |
| --- | --- | --- |
| Descoberta Rustup | Timeout/erro de lançamento abortava a preparação global, inclusive tarefas independentes de Rust | Consulta limitada; ausência segue para os consumidores Rust, com aviso, sem abortar outras superfícies |
| LLVM | Instalações auxiliares eram consultadas antes de aproveitar LLVM ativo; uma instalação quebrada interrompia toda a busca | Priorizar LLVM ativo; ignorar candidatos auxiliares inoperantes e exigir identidade exata dos candidatos aproveitados |
| PostgreSQL/EXIT | Cleanup relia configuração antes do recibo; configuração inválida impedia restaurar o container iniciado pelo run | Cleanup depende exclusivamente do recibo e ID já publicados |
| Playwright | Curto-circuito booleano evitava aguardar downloads se uma operação anterior falhasse | Aguardar todos os downloads também no encerramento com falha |
| Diagnóstico Toolbox | Falha de limpeza com outcome infrastructure era classificada como simples bloqueio | Limpeza falha tem precedência e resulta em erro |
| Grype | Status aninhado podia ocultar erro ou não confirmar validade da base | Exigir valid=true e ausência de erro no status efetivamente usado |
| CodeQL | Ordem das chaves dos componentes era tratada como condição de validade | Comparar mapeamento de identidades/linguagens, sem impor ordem de propriedades JSON |
| Retenção CodeQL | Inventários das dependências usadas pelo extrator eram descartados na limpeza | Exportar somente os metadados/hashes correspondentes como logs privados |
| Resumo | O estado measured, usado pelo Syft, era rejeitado pelo schema do resumo | Aceitar medição sem convertê-la em aprovação de vulnerabilidades |
| Inventário | os.walk podia omitir subárvores por erro de acesso sem informar falha | Propagar erros de leitura; raízes existentes devem ser diretórios; ausência continua distinta de acesso recusado |

O prazo de consultas de metadados está em runtime.probe_timeout, na configuração
canônica. Foram escritas regressões para falha de descoberta, candidato LLVM
quebrado, limpeza independente da configuração, limpeza Toolbox falha, Grype com
status aninhado, retenção, medição Syft e leitura recusada. Elas não foram executadas.

Observado: `plan --scope complete`, `plan --surface docs`, `plan --check firefox`
e `plan --surface agent --check coverage` retornaram zero, todos com executed=false.
O plano completo contém 54 tarefas; o de docs contém somente as cinco tarefas de
governança; Firefox e cobertura incluem seus recursos e builds necessários sem
selecionar todas as outras tarefas. Inspeção de diff sem erros de whitespace.

Limite: geração de plano não executa Rust, scanners, PostgreSQL, browser, testes
ou limpeza real. Ausência de outros defeitos na inspeção não demonstra ausência
de erros de execução. A suíte consolidada e os testes da Toolbox externa continuam
pendentes; Firefox preparado e comportamento do Geiger instalado precisam de
observação real. Não houve download nem nova aprovação dos critérios SDD.


## Análise do run-xK3jrVOn e correções — 11/11/2026

Pedido vigente: análise criteriosa de cada log, correções e configuração unificada;
a instrução posterior excluiu Firefox. Rota local para defeitos demonstrados,
preservando os contratos existentes. Métodos lidos/aplicados: software-delivery
(preparo e identidade), application-security (triagem/proxy) e rust (revisão do
patch Geiger existente); sem delegação.

A leitura dos 1.115 logs/18.764.674 bytes e o confronto de 54 resultados/208
diagnósticos estão no [relatório](../../../docs/validation/automation-run-xK3jrVOn-20261111/report.md).
Resultado histórico: 45 passed, quatro findings, três error, um measured e um
blocked; exit 2, inputs estáveis e limpeza concluída. Logs originais preservados.

T055 implementada: texto literal do plugin ShellCheck; justificativa da fixture
Ruff; expectativas dos dois testes desatualizados; proxy restrito a loopback/
portas inteiras; rejeição de Python otimizado nos harnesses; retirada de quatro
licenças sem uso dos grafos observados. Regressões foram escritas, não executadas.

Geiger: preparo offline e instalação externa autorizada do 0.14.0+ec.1, usando a
correção já presente nas fontes e crates locais. Compilação/instalação retornaram
zero; binário/lock/fontes registrados em logs privados. A versão diferencia o
patch. O runner permanece consumidor do plugin instalado, sem cópia executável
alternativa, build por gate ou nova configuração. Não se afirma aprovação dos
scans nem das regressões atuais por esse preparo.

Bandit: triagem das 370 ocorrências registrada, com guard contra remoção de asserts.
A decisão sobre compartilhar justificativas pontuais com Ruff foi solicitada;
sem resposta específica, o gate estrito permanece. Nenhum skip/baseline/dispensa
foi aplicado. Firefox ficou fora das correções por instrução expressa.

T056/AC-24/28/33/34/35: validação após correções permanece UNVERIFIED. Nenhuma suíte,
scan ou teste foi reexecutado, conforme política canônica; não foram renovados
bindings antigos para simular execução atual. A continuação requer a decisão
Bandit e execução pelo fluxo autorizado; Firefox continua fora desta tarefa.

### Firefox preparado pelo usuário

A informação posterior de instalação foi confirmada por leitura do registro
central e inspeção do Docker: variante Firefox 1.56.1, imagem
`sha256:85f34c82201dcd9b400805dfc879fed090b364a664808d926e775f215852e890`,
com uma revisão elegível e versão nativa verificada. O runner já fornece
`--browser firefox`; não foi alterada a configuração nem executado download.
A jornada E2E não foi reexecutada, portanto o bloqueio histórico não foi
reclassificado como aprovação. Guia de preparo e relatório foram atualizados.

## 11/11/2026 — Aprovação do inventário Syft

Por solicitação do usuário, `security:syft` reutiliza o caminho Syft → Grype
já implementado: valida inventário e lockfiles, vincula o SBOM por identidade,
exige base íntegra/recente e reprova qualquer vulnerabilidade. Contagem de
artefatos e scanner responsável permanecem no resultado. AC-12/AC-16 foram
atualizados; a geração isolada continua sendo medição.

Inspeção manual de `tools/quality/native_gates.py` e dos consumidores; testes
foram ajustados para aprovação e rejeição por vulnerabilidade/base inválida.
Não executados, conforme política de verificação. O run XLfj4Slq é anterior
a esta mudança: Bandit registrou 373 achados (370 baixos, 3 médios) em testes
e ferramentas Python; não houve correção nem supressão desses achados.


## Reparação autorizada do run-IR9jPgS9 — 12/11/2026

REQ-36/37/38 → D-30/R-30 → T057–T060. O usuário autorizou implementação,
testes e reexecuções completas com complementares. Métodos software-delivery,
application-security e rust, sem delegação. Nenhum commit/publicação solicitado.

- CodeQL: validador SARIF único resolve severidade efetiva, rejeita diagnóstico
  degradado e exige extração saudável/métricas. O extrator acionava proxies
  Rustup com 1.99.0 ausente; lançadores usam os binários da instalação selecionada,
  sem pin/download, preservando stderr original. Os três alvos passaram realmente
  no run-ozM6Y3nN; Rust Server 11/11 e Agent 9/9 sem erros de extração.
- Geiger: 0.14.0+ec.2 instalado offline. Arestas vêm do resolve Cargo; plataforma
  e ativação opcional são verificadas antes de exigir associação. Metadata inclui
  phf→serde mesmo com apenas default/std ativos; serde?/std não ativa serde.
  Dependência ativa ausente agora retorna erro. Testes de rename, dep:, referência
  forte/fraca, versão não resolvida, cfg(any()) e nó ausente integram 192 testes
  de biblioteca aprovados. Probe real do grafo completo do Server passou.
- Bandit: scan integral e relatório bruto preservados; 375 ocorrências revisadas
  por hash/regra/linha/justificativa na configuração única. Nenhuma ocorrência
  pendente no run-ozM6Y3nN. Casos novos ou com conteúdo alterado reprovam.
- Syft recebe nome e identidade das entradas; aviso de origem não reapareceu.
  Deny mantém avisos de duplicidade e união de licenças necessária aos workspaces;
  fixtures positivas e alcance EICAR não foram convertidos em promessas maiores.

Iterações: CP24bjsM foi diagnóstica, com alterações durante execução e falhas
preservadas. ozM6Y3nN teve 53 passed e uma falha Geiger Server anterior à última
correção; inputs estáveis, Agent/CodeQL/Bandit/integrações/browsers aprovados,
limpeza concluída. Os 156 testes de scripts também passaram isoladamente.
A próxima suíte completa deve observar todos os gates com a instalação final;
AC-36/37/38 permanecem UNVERIFIED até esse fechamento, sem aceite antecipado.


Na iteração ywYuxSGi, ambos os gates Geiger passaram nos 11 membros; os três
alvos CodeQL e os demais motores/jornadas também produziram resultados saudáveis.
A revisão adicional reproduziu um override SARIF por índice não aplicado quando
a notificação referia somente o ID. O lookup passou a resolver o índice do
descritor e ganhou regressão. A depuração temporária CodeQL foi retirada; stderr
Cargo/rustc completo permanece nos lançadores. Como houve alterações durante
a execução, a rejeição por drift foi preservada. A próxima repetição completa
usa esses bytes estabilizados; nenhum resultado anterior foi reclassificado.


A inspeção dos novos stderr revelou falhas de build scripts por linker `cc`
ausente, não refletidas nas métricas SARIF. As aprovações anteriores do gate
não comprovam essa parte semântica. O lançador também fechava os FDs do jobserver;
o repasse explícito desses descritores foi implementado e testado. Recibos por
subprocesso distinguem probes de compilações críticas; estas precisam terminar
com zero, antes de analisar a base. Uma fixture com SARIF saudável e compilação
interna falha agora reprova. A correção da imagem compartilhada e os downloads
Debian necessários dependem da autorização específica solicitada nesta sessão.
Nenhum aceite foi antecipado; é necessária nova suíte com a imagem completa.


O novo gate foi exercitado contra a imagem real ainda incompleta: rejeitou
`cargo/check` com exit 101 antes da análise, preservando stderr e recibos. Os
159 testes de scripts passaram, incluindo jobserver, falha interna com SARIF
saudável e diagnóstico ausente. Geiger conserva 192 testes aprovados. A imagem
externa não foi alterada e nenhum pacote foi baixado enquanto a autorização
específica está pendente. Patch concreto da receita atual preparado em
`/tmp/ec-codeql-image.patch`: acrescenta build-essential à instalação existente,
sem imagem paralela nem nova configuração. Após autorização: preparar/qualificar
imagem pelo fluxo da Toolbox, ativar a revisão verificada e repetir a suíte
completa até exit zero estável. AC-36/37/38 não foram antecipados como aprovados.


O usuário autorizou explicitamente a correção da Toolbox externa e os downloads
Debian necessários. A receita vigente recebeu build-essential; o bundle CodeQL
2.27.3 já disponível foi reutilizado após conferência contra checksum oficial,
sem download repetido. Os 85 testes da Toolbox passaram, validate/doctor ficaram
saudáveis e a imagem foi qualificada com Cargo check offline real nos dois
workspaces antes da ativação. Revisão ativa
`8fd2130d9f45e79a148931b62ac3008e7a63fd6cd28a366bf725b5a125b25e8b`, imagem
`sha256:0ee3284ac49c0a2ab676e90c6e9f7ead5985d021d1ee3c47fd9b6f80ae1bc494`.
Os probes CodeQL posteriores de Agent e Server passaram, incluindo os recibos
das compilações críticas. O export dos recibos JSONL mantém bytes e sufixo .log
para respeitar a retenção. Falta o fechamento da suíte completa estável;
AC-36/37/38 continuam UNVERIFIED até esse resultado.


### Fechamento observado — run-3BXITHOD

As 54 tarefas de `bash scripts/automate.sh run --scope complete` passaram,
exit final zero, sem cancelamento, alterações de inputs/locks ou erro de limpeza.
CodeQL Rust: Server 11/11 e Agent 9/9 saudáveis, frontend sem achados; Geiger
cobre seis membros Server e cinco Agent, sem falhas de associação; Bandit
preserva 375 achados brutos, 375 revisados e zero pendentes. Foram aprovados
192 testes do plugin, 85 testes da Toolbox e 159 testes de scripts, além das integrações e dos dois
browsers no run completo. T057–T060 concluídas; AC-24/28/36/37/38 aprovados neste
escopo, sem promover os demais critérios históricos.

[Registro imutável de resultados e identidades](../../../docs/validation/automation-repair-20261112/run-3BXITHOD.json).
Logs privados seguem retenção de duas datas. O fechamento documental posterior
registra a execução observada; não renova um hash antigo para simular novo teste.
Não houve commit/publicação. O usuário autorizou a correção externa e os downloads Debian; o bundle CodeQL foi reutilizado por checksum oficial. As compilações internas foram verificadas separadamente das métricas SARIF.


## Ampliação posterior aos runs aprovados — 12/11/2026

O usuário autorizou o download de catálogos e indicou a instalação
`/home/rodrigo/Projetos/dev_audit/shared-toolbox`. Os seletores `toolbox:active`
e `toolbox:playwright:firefox` consultam a autoridade pública da plataforma;
Ec não mantém outra tabela de IDs ativos. A consulta de metadados identificou
12 imagens ativas e uma variante Firefox; consulta não executa análise.

Políticas existentes ampliadas: Ruff ALL/preview, ShellCheck opcionais/dataflow,
Clippy all/pedantic/nursery/cargo, Machete metadata, Geiger all-deps e CodeQL
security-and-quality com Python adicional. Gitleaks inclui dotenv e histórico
Git local redigido, rejeitando histórico raso/parcial ou mutável. Trivy amplia
detecção e inventaria licenças separadamente; Syft centraliza all-layers e
archives, Grype consome o mesmo inventário. Imagens exportadas por ID são lidas
sem socket, com integridade antes/depois e remoção dos temporários.

Importação autorizada de elttam, Trail of Bits, njsscan e signature-base:
231 IDs Opengrep declarados, incluindo os oito locais, e 754 arquivos YARA
externos, com revisão/licença/hashes registrados no JSON canônico. Esses totais
são de importação, não prova de compatibilidade ou eficácia. Catálogos antigos
arquivados e regras com redistribuição incompatível não foram adotados.
YARA-X compila os grupos de conteúdo/contexto, preserva autoria e exige NDJSON
para cada arquivo, inclusive sem matches. Sentinelas EICAR/contexto não
substituem a qualificação das assinaturas externas.

Regressões escritas para omissão, duplicidade, proveniência alterada, redação,
histórico insuficiente e imagens ausentes. Revisões Bandit das fontes alteradas
foram retiradas; hashes não foram renovados para herdar aceite. Novo finding
permanece pendente até revisão vinculada ao código. Nenhum achado foi dispensado.

T061–T064 implementadas/inspecionadas; T065 e AC-39–42 permanecem UNVERIFIED.
Os aceites afetados do reparo anterior foram desvinculados das entradas atuais;
seus relatórios datados permanecem preservados. O último run-pdrzzgoG aprovou
54 checks antes destas mudanças e não prova a cobertura ampliada. Não foram
executados testes, scans nem suíte nesta etapa; autorização de execução foi
solicitada pela política canônica, incluindo eventual atualização das bases.
Sem commit/publicação. Sem delegação.


O usuário autorizou nesta retomada a qualificação nativa, os testes Ec/Toolbox,
a suíte completa com complementares e as atualizações de bases necessárias.
A autorização inclui correções e repetições desta ampliação; não implica
commit/publicação nem atualização de binários/imagens por inferência.

Primeira execução autorizada: Toolbox validate/doctor aprovados e 87 testes
passaram. No Ec, run-V2IEc7NA encontrou sintaxe em uma regressão nova e fixture
Trivy sem SchemaVersion, ambos corrigidos; resultado anterior não foi alterado.
A revisão Bandit da fixture modificada foi retirada, sem renovar seu aceite.
A suíte completa seguinte qualificará os consumidores e registrará achados.


O run completo run-6MwleBfs terminou com exit 2, inputs/locks estáveis e sem
cancelamento; os 173 testes passaram. Ruff/ShellCheck/Gitleaks/Clippy e o novo
CodeQL retornaram ocorrências. Todos os quatro alvos CodeQL tiveram extração
saudável. Integrações e três jornadas de navegador passaram. SAST ficou bloqueado
pela qualificação: --validate exige metachecks remotos, incompatível com o scan
offline. A chamada foi substituída por qualificação estrita nas sete linguagens
do catálogo, sem afirmar execução dos metachecks. Docker/containerd retornou ID
do índice OCI; a prova agora percorre índice/manifesto/configuração/camadas e os
relatórios nativos são comparados ao config_digest. Um export local real Gitleaks
provou essa cadeia; archive descartado. Metadados YARA-X 1.0 são pares ordenados,
não objetos: parser e fixtures foram corrigidos, preservando chaves repetidas.
Os catálogos YARA compilaram no motor real. Regressões negativas cobrem camada
OCI alterada e identidades distintas. Clippy recebeu keep-going para continuar
por crates independentes. Nenhuma ocorrência foi suprimida para obter verde.
A próxima execução focal qualifica as correções antes de repetir o escopo completo.

O run focal run-QEywEbtW aprovou os 174 testes e completou o YARA-X real
(360 arquivos, uma ocorrência). A agregação excedeu ARG_MAX ao transportar os
recibos por argv; foi substituída por slurpfile sobre o resultado já persistido,
com regressão de 2000 recibos. A qualificação Opengrep deixou de forçar parsers
sobre texto genérico; genéricas usam as amostras das linguagens corretas. Trivy
image não aceita include-dev-deps nessa versão: mantém o catálogo de pacotes da
imagem sem a flag exclusiva de filesystem. Grype registra seus descartes
automáticos como pendências separadas, sem apagá-los nem aprová-los, e continua
pelas imagens seguintes. Os relatórios e resultados anteriores foram preservados.

No run focal run-lsz5eH6D, Trivy terminou fontes e 13 imagens com 8519
ocorrências; Grype e Syft/Grype terminaram 13 imagens com 9290 associações
nativas e 3009 descartes preservados como pendências. YARA completou 360
arquivos; a agregação extensa foi preservada sem ARG_MAX. A ocorrência única
foi comprovada como literal EICAR da fixture em test_native.py; o literal
passou a ser composto em runtime, mantendo o controle positivo e todos os
arquivos/regras analisados. Os testes tiveram um erro de fixture Grype sem
descriptor, corrigido. A qualificação Opengrep passou; o teste local requer
um config e agora usa cópia efêmera byte a byte somente dos arquivos locais
no mesmo caminho canônico, sem catálogos externos sem fixtures associadas.
A próxima execução focal cobre regressões e o SAST completo antes da repetição final.

run-y4MBZCOs: 176 testes e eficácia das oito regras locais Opengrep aprovados;
SAST ampliado concluiu com achados, sem erros/omissão. As correções estão
estabilizadas para a repetição completa com complementares. Relatório de
alcance/qualificação em docs/validation/security-coverage-20261112/report.md.
Nenhuma ocorrência foi promovida a aprovação nem houve mudança de aplicação
para apagar o estoque de qualidade. Critérios operacionais ficam UNVERIFIED
antes da repetição final; não há aceite de ausência de vulnerabilidades.

run-4f8byi1k: 43 passed, nove findings e dois erros Clippy, sem drift/locks
alterados; YARA-X, integrações e navegadores aprovados. Conferência adicional
do descriptor Syft identificou dev dependencies desligadas. A política única
passou a incluir dev deps, união de catalogadores e pacotes .NET sem exigência
de DLL. Qualificação nativa confirmou 283 pacotes e nenhuma omissão entre
as 249 dependências externas distintas declaradas nos três locks. Novo
oráculo liga pacote/versão/tipo a cada lock; simples presença do arquivo não
aprova. A repetição completa seguinte usa a política ampliada, sem reaproveitar
um resultado anterior como validação dos novos bytes.

Fechamento observado run-cHRcsFAw: 43 passed, nove findings e dois errors
Clippy; exit 2, 177 testes aprovados, sem drift/locks alterados, cleanup ok.
Todas as 13 imagens foram processadas; dependências por lock completas no SBOM.
YARA-X, integrações e navegadores passaram. O relatório mantém ocorrências,
limites e triagens pendentes. A limpeza posterior retirou scanner_roots, campo
sem consumidor nativo; aliases auxiliares usam inputs.roots, com equivalência
à seleção all em regressão. Esse refactor e o fechamento têm validação focal
separada; hashes de execução não foram renovados. Critérios operacionais
continuam UNVERIFIED/reprovados pelos achados, sem commit/publicação.

Fechamento focal run-Ur88v3HT: seis tarefas e 177 testes passaram, exit 0,
inputs/locks estáveis e cleanup concluído. O seletor inativo foi removido sem
alterar a seleção all usada pelos scanners; aliases auxiliares agora equivalem
a ela, incluindo dotenv. Esse resultado não reclassifica achados da suíte
completa, cujos hashes permanecem originais. Documentação/evidência atualizadas
para resultado real reprovado; sem commit/publicação.


## Reparação e adequação Ruff — 13/11/2026

A instrução vigente autorizou adequar regras às convenções do código. Ruff
usa perfil estável explícito na política nativa já existente; stdlib/CLI,
unittest e namespaces foram preservados. Segurança Python mantém Bandit
integral e revisão exata por conteúdo. Não há novos arquivos de política por diretório.

T066/T067 concluídas quanto a diagnóstico, correção e verificação com lacunas
registradas. Run-ZPA4aciU executou 54 tarefas: 51 aprovadas, três com achados,
exit 1 e zero erros/bloqueios. 180 testes da automação, os dois Clippy, CodeQL
nos quatro alvos, segurança das fontes, cobertura, HTTP e E2E passaram.

A receita Playwright recebeu WORKDIR e as duas imagens foram requalificadas.
O preparo atualiza o registro Firefox; por isso a validação posterior run-NSqpobP8
identifica os novos digests e repete Trivy/Grype/Syft e as jornadas reais.
Resultado: 13 tarefas aprovadas e três com achados;
Trivy 5561, Grype 5822 e Syft/Grype
5822, exclusivamente em imagens. Não são vulnerabilidades únicas.
DS-0013 foi eliminado. Sem alteração de código/políticas Ec entre essas duas rodadas.

AC-38 e o agregado da suíte permanecem REJECTED; T068 continua aberta.
Scans corretos e imagens operacionais não aprovam vulnerabilidades remanescentes.
O fechamento altera somente documentos SDD, com inspeção própria e validação documental posterior.
Relatório, comandos, hashes, imagens, limites e próximos passos:
[reparação do run-SNe1BAXu](../../../docs/validation/run-sne1baxu-repair-20261112/report.md).


## Escopo somente Ec — 13/11/2026

Instrução vigente remove auditoria da Toolbox do consumidor. D-33/R-33 e
T069/T070 substituem o escopo anterior de imagens. Removidos seletores, exportação
Docker e código de scan de imagens; Trivy filesystem e Syft/Grype do inventário Ec
continuam obrigatórios quando selecionados. T068 cancelada por escopo, sem afirmar
correção dos achados históricos. Validação da versão atual ainda pendente.

Validação concluída: 174 testes Ec passaram em run-jbTDZYji. Após corrigir
um import sem uso e revisar as 20 ocorrências Bandit afetadas, run-EWXWaWlJ
aprovou Ruff, Bandit, Trivy, Grype, Syft e governança, exit 0, sem drift.
T069/T070 concluídas; suíte completa de produto não foi repetida nesta alteração.
Recibo: docs/validation/run-sne1baxu-repair-20261112/scope-ec-only-validation.json.
