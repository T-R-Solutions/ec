# Correção Geiger e cobertura — 09/11/2026

Implementação preparada e inspecionada, **sem execução de build, testes, scanners
ou suíte**. Os cinco bloqueios permanecem **UNVERIFIED nos inputs atuais**.
Os percentuais abaixo pertencem à execução histórica; não são novas medições.

| Gate | Último resultado observado, run-BvlgAwX0 | Estado atual |
| --- | --- | --- |
| Geiger Server | Código nativo 1, gate 2, 15 arquivos não examinados | UNVERIFIED |
| Geiger Agent | Código nativo 1, gate 2, um arquivo não examinado | UNVERIFIED |
| Cobertura Server | 332/1540 linhas, 21,56% | UNVERIFIED |
| Cobertura Agent | 111/686 linhas, 16,18% | UNVERIFIED |
| Cobertura frontend | 17/115 linhas, 14,78% | UNVERIFIED |

Baseline: [relatório anterior](../geiger-diagnostics-20261109/report.md), seus dois
diagnósticos e verification-results.json. Os streams brutos foram lidos para
identificar os erros finais `Found 15 warnings` e `Found 1 warnings`; os caminhos
e SHA-256 consultados constam em [observations.json](observations.json). O helper
de diagnóstico só extraía avisos: a confirmação da causa veio também dos logs
completos e da implementação local da versão 0.14.0. Não se inferiu vulnerabilidade.

## Causa e alteração Geiger

O upstream obtém os arquivos do dep-info rustc, incluindo READMEs incorporados,
mas busca arquivos a examinar nas árvores dos pacotes com extensão `.rs`.
O confronto entre esses conjuntos gera avisos para documentação e para
private.rs de serde_core em OUT_DIR. Na saída em tabela, o número de avisos
chega a FoundWarningsError e ao código nativo 1. Não foi erro de localização do
helper, cuja correção e regressões anteriores continuam preservadas.

A [cópia versionada do mesmo Geiger 0.14.0](../../../tools/quality/vendor/cargo-geiger/EC-PATCH.md)
mantém licenças, fontes e lock upstream identificáveis. upstream-files.json
registra os hashes da cópia local antes das alterações; os arquivos do registro
Cargo não foram editados. O pacote mantém o motor `geiger`, seus contadores,
grafo e classificação; não foi substituído por outro scanner.

`scan/rs_file.rs` passa os grupos dep-info ao scanner; `scan/default.rs` integra
`scan/supplemental.rs` após a busca original. O módulo adicional examina a AST
dos consumidores para identificar inclusões literais de dados. Dados assim
demonstrados permanecem no inventário de arquivos usados pelo build, com registro
explícito de sua classificação, e não são tratados como Rust não examinado.
Extensão não Rust, sozinha, não dispensa a análise: um include! literal tem
precedência sobre include_str!/include_bytes! do mesmo caminho.

Fontes Rust adicionais, inclusive geradas, são examinadas pelo próprio motor
Geiger e entram nos contadores do único pacote com fontes no mesmo grupo
dep-info. Não viram um novo entry point e não alteram a regra de forbid.
Atribuição ausente/ambígua ou parsing inválido encerra com erro. Caminhos
desconhecidos e inclusões dinâmicas sem resolução continuam sem dispensa,
mantendo a reprovação nativa quando não puderem ser examinados.

O [check Rust](../../../scripts/stacks/rust/check.sh) compila essa fonte e executa
suas regressões específicas em target privado quando o gate for autorizado.
Usa locked/offline e o Cargo home de ferramentas já preparado; não instala
scanner global nem faz download implícito. Cargo home do produto permanece
separado. O PATH inclui explicitamente cargo-home/bin depois do binário corrigido,
para preservar sua prioridade na busca de plugins Cargo. A fonte local Cargo
0.96.0, main.rs:357–379, foi consultada para essa condição.

O recibo tool.json identifica patch, fonte, versão e hash do executável. Os códigos
do build e das regressões ficam em geiger-build-exit.txt e geiger-tests-exit.txt;
os códigos do scanner, stdout/stderr, diagnóstico complementar e interrupção
na primeira falha continuam por membro. O helper vigente segue em
tools/quality/geiger_diagnostics.py, sem alteração nesta tarefa. Nada foi apagado
dos caches, dependências, fontes geradas ou logs históricos.

## Causa e alteração da coleta de cobertura

A política canônica mede linhas: >=80% por componente, sem arredondamento ou
média. A geração histórica Rust executava testes Cargo. Os harnesses HTTP/PG
eram executados depois com outros binários, sem incorporar seus perfis ao LCOV.
Assim, service.rs, orquestração, resultados e publicação tinham zero linhas
observadas nesse relatório. Agent app e transporte/execução também tinham zero.
Isso caracteriza uma lacuna de coleta além da necessidade de novos testes;
não demonstra previamente quanto cada jornada acrescentará à porcentagem.

O gate agora executa testes Cargo com --no-report, compila o componente atual
instrumentado e seu companheiro em targets privados, executa os harnesses reais
e só então gera o LCOV. Server coleta publication_http, orchestration_http e
agent_http; Agent coleta agent_http. Os mesmos oráculos existentes observam
rejeições, persistência, replay, leases, processos e saída. Não se importam perfis
antigos nem se reutilizam binários do target operacional padrão.

O [coletor](../../../tools/quality/collect_runtime_coverage.py) interpreta o formato
literal de cargo-llvm-cov show-env como dados por shlex, com variáveis permitidas;
não usa eval/source. A versão 0.9.1 foi identificada pelas fontes locais e suas
opções/atribuições foram inspecionadas. A saída show-env fica no run. Os comandos
Cargo mantêm locked/offline/all-features onde aplicável. O preflight de integração
existente exige container dedicado, PostgreSQL pronto e porta livre. Erro de
build, harness ou relatório impede o gate. Fontes e limiar não foram alterados.

Os harnesses aceitam binários explícitos do coletor, preservando os padrões
anteriores fora da cobertura. Para gravar os perfis do Server em saída normal,
o build com test-failpoints aceita EOF em stdin privada somente com
EC_TEST_GRACEFUL_STOP=1. O harness fecha essa entrada e exige exit 0. Não há
endpoint HTTP de shutdown; a variável não habilita esse mecanismo no build
sem test-failpoints. Os testes deliberados de crash continuam deliberados e
podem perder perfis desse processo; isso não é compensado com números inventados.

## Testes acrescentados

Todos estão escritos e inspecionados, **não executados**:

- Geiger: documentação separada de Rust gerado unsafe, inventário de build
  preservado, extensão não Rust contendo código, precedência de include!,
  entrada desconhecida, ausência/ambiguidade de proprietário e parsing inválido.
- Agent: HTTP real loopback com payload/credencial/status/envelope; heartbeat
  com cancelamento/rejeição; stdout/stderr, chunks, exit 7, sucesso, timeout e
  interrupção do grupo; checkout de commit fixado preservando fonte local;
  spool, limite, integridade, replay e perda de autoridade.
- Server: nomes e IDs nas bordas, envelopes exatos, bytes hex/limites,
  sequências e JSON persistido corrompido. Arquivos de testes unitários separados
  usam o padrão *_tests.rs já reconhecido pelo inventário de produção.
- Frontend: cliente HTTP, geração de sessão, logout com erro, edição/revisão,
  publicação, conflito, pedido incerto/replay/recuperação, viewer, debounce,
  resposta tardia, polling, cancelamento, paginação e download com integridade.
- Automação: show-env como dados, paths com espaços, flags, seleção do binário
  atual e propagação de erro dos harnesses; fixture shell Geiger adaptada ao
  build privado, mantendo o teste de código nativo 17 e os avisos brutos.

Vue e Pinia são reais nos testes de componentes, HTTP é simulado e o renderer
é de teste. Isso não comprova DOM real, foco, teclado, acessibilidade ou layout;
as jornadas Playwright/Firefox permanecem. `@vue/compiler-dom` 3.5.45 já estava
instalado transitivamente e foi explicitado como devDependency para fragmentos
estáticos. serde_json já existente foi explicitado como devDependency nos testes
de execução Rust. Não houve instalação ou download de dependências.

## Pré-requisitos e retomada

A inspeção por nomes do lock Geiger encontrou 98 entradas sem fonte ou arquivo
.crate no Cargo home global atual. Isso inclui alvos opcionais/plataformas, não
significa que as 98 sejam necessárias no Linux. Entre as ausências estão versões
de build e testes, como cc-1.8.0, annotate-snippets-0.12.16, assert_cmd-2.2.2 e rstest.
[Inventário e limites](offline-cache-inspection.json). A inspeção não verifica
integridade, índices, resolução ou compilação. Uma fonte existente não prova que
o build offline passe; dependências ausentes devem bloquear, sem fallback.

Foi solicitada autorização específica para preparar essas dependências com
cargo fetch. Sem resposta positiva não haverá download. Essa pergunta não pede
nem concede execução de build/testes/suíte. Qualquer resposta e preparo posterior
devem ser registrados separadamente, preservando esta observação.

Nenhum pedido de commit existe nesta tarefa. Portanto não foram executados
scripts de verificação ou automate.sh. Rustfmt foi usado apenas para editar
formatação de fontes Rust, sem --check, compilação ou execução dos testes.
Os hashes atuais e origens estão em observations.json. A evidência SDD anterior
está preservada integralmente em [previous-evidence.json](previous-evidence.json).
Novos requisitos/decisões/tarefas estão em
[scripts-verification](../../../sdd/features/scripts-verification/plan.md).

Quando a suíte estiver autorizada pela política vigente, a retomada é sua
execução integral, sem filtros. Corrigir os problemas efetivamente revelados e
repetir a suíte integral; confirmar todos os membros Geiger e >=80% de linhas
individualmente. Antes dessa observação, não declarar os cinco bloqueios
resolvidos, não aprovar AC-09/10/28/29 e não criar commits por inferência.

Não houve staging, commit, push, instalação global ou alteração da Toolbox.
Os guias correntes documentam os novos consumidores; resultados antigos e seus
hashes permanecem históricos. Esta inspeção não renova os demais gates aprovados
em run-BvlgAwX0 para os inputs agora alterados.

## Novo pedido de commits — suíte autorizada

O usuário solicitou commits incluindo arquivos não versionados e escolheu sim.
A suíte integral `bash scripts/automate.sh run` foi executada em
`run-pZn5E71s`, sem filtros; exit 2.
[Resultado preservado](commit-first-results.json). Nenhum commit criado.

Passaram os gates de segurança, os três harnesses HTTP e Playwright/Firefox.
Falharam a cópia descartável dos testes de governança (referência ao novo
EC-PATCH.md ausente), Ruff, formatação Agent, testes de componentes frontend
e geração de cobertura. Geiger foi bloqueado no build offline por
allocator-api2 0.2.21 ausente. A inspeção dos caches existentes e caminhos
catalogados encontrou 273 pacotes do lock da ferramenta sem archive disponível;
foi solicitada autorização específica de download, ainda pendente.

Foram corrigidas as duas cópias de auditoria para incluir tools/quality,
formatação do teste de checkout, posicionamento das justificativas Ruff e
fixtures textuais sem caminho temporário real. O coletor passou a reconhecer
as variáveis de wrapper emitidas pelo cargo-llvm-cov instalado, mantendo a
validação de atribuições e de instrumentação; há regressão para esse formato.
O ambiente Vitest usa Node com compilação cliente para o renderer Vue,
sem declarar DOM ou evidência de navegador. Requer reexecução integral.

Skill primária commit-governance; auxiliares software-testing para as falhas de
fixtures/oráculos, rust para a coleta instrumentada e frontend-testing para
o ambiente de componentes. Não foram executados checks avulsos. As observações
e falhas históricas acima permanecem preservadas; nenhuma foi promovida a PASS.

Hashes das skills efetivamente lidas nesta retomada:

- `.codex/skills/commit-governance/SKILL.md`: `86befaae4ff6c59c0b5b9469bb80ba96a1dceac725b0acadfecef2058c2df3d3`.
- `.codex/skills/software-testing/SKILL.md`: `67fb6565d48fbad42b6c58f48caeaf16c76bcb448f518fe835d2ab480564d6a8`.
- `.codex/skills/rust/SKILL.md`: `a2926aaeee5df8492c86acd31b09df7ba3c33545b146e635506962f053759b12`.
- `.codex/skills/frontend-testing/SKILL.md`: `67b711a02c687c87d153c6037ca872d30be3900c0b0a582b86f2d2716daaca06`.

## Segunda suíte e autorização de dependências

A execução integral `run-BG05idAs` terminou com exit 2, entradas estáveis
e sem cancelamento. [Resultado preservado](commit-second-results.json).
Governança e os testes de automação passaram. Cobertura Server: 1439/1554
linhas (92,60%); Agent: 626/686 (91,25%). Os 15 testes de componentes Vue
passaram, mas o teste de contraste que lê CSS via Node falhou porque o ambiente
cliente fora aplicado também a ele. Ruff manteve um apontamento S607 na linha
do argumento, apesar da justificativa na linha do subprocess. Geiger continuou
bloqueado por dependências ausentes. Os gates de segurança, HTTP, Playwright
e Firefox passaram. Nenhum commit criado.

Após a rodada, o ambiente cliente foi restrito aos três testes de componentes,
usando a interface vitest/runtime vigente; os testes puros continuam em Node.
A justificativa específica de S607 foi posicionada na linha apontada.
O usuário respondeu à pergunta de download: “Baixa as versões mais recentes.”
Foi adotada atualização para versões mais recentes compatíveis com o manifesto
do mesmo Geiger 0.14.0 e seu MSRV, seguida de preparo no cache privado
.local/build/ec-geiger-cargo-home. O cache é explicitamente ignorado; fontes,
lock e recibos permanecem versionáveis. O manifesto/lock anteriores ficam em
.local/cleanup-20261109/geiger-dependency-update. Nenhum lock de aplicação
é atualizado por essa operação. A nova suíte ainda é necessária.

## Terceira suíte: build real Geiger e correções adicionais

`run-EQIsASIV` terminou com exit 2, sem alteração de entradas nem cancelamento.
[Resultado preservado](commit-third-results.json). O build e as regressões da
ferramenta passaram, mas os dois scanners abortaram no primeiro membro com
stack overflow em thread Rayon (código nativo 134).
[Diagnóstico Server](commit-third-server-geiger.json) e
[diagnóstico Agent](commit-third-agent-geiger.json). Não são os avisos históricos
de READMEs/gerados nem comprovação de inventário completo.

Foi configurado RUST_MIN_STACK=16777216 somente na chamada do scanner, com
registro no tool.json e verificação da variável na fixture shell. Não há mudança
de ulimit, configuração do host ou exclusão de fontes. É uma correção a verificar
na próxima suíte, não uma prova antecipada da causa ou do sucesso.

O formato de anotação por arquivo do Vitest instalado só reconhece nomes de
ambiente, não caminhos locais. Por isso os componentes voltaram a ser compilados
como SSR nesta rodada. A configuração foi corrigida para o ambiente Node com
compilação cliente por padrão, com anotação explícita node no teste de contraste
que lê CSS do filesystem; sem adicionar DOM ou mascarar SSRContext.

Ruff, governança e automação passaram; cobertura Rust manteve >=80%. O Firefox
falhou ao iniciar Marionette: log registrou Could not bind to port 58282, seguido
de ConnectionRefusedError no cliente. O preflight não observara listener.
Nenhum retry interno, supressão ou limpeza de processo externo foi adicionado;
a falha permanece histórica e a nova suíte deve observar a jornada inteira.
Nenhum staging, commit ou push foi realizado.

## Quarta suíte: aprovação integral

`run-iNYFOPoN` terminou com **43 tarefas aprovadas, exit 0**, sem cancelamento
e sem alteração das entradas durante a execução.
[Resultado agregado](commit-passed-results.json),
[inputs observados](commit-passed-inputs.json) e
[inventários/códigos/hashes Geiger](commit-passed-geiger.json).

Geiger aprovou todos os seis membros Server e cinco Agent; build offline e
regressões próprias passaram. Os logs identificam dados incorporados pelos
consumidores e o Rust gerado examinado pelo mesmo motor. O ajuste de pilha
restrito à chamada foi suficiente nesta execução; isso não é garantia geral
para qualquer código, versão ou carga. Não foi imposta política zero unsafe.

Cobertura de linhas: Server 1439/1554 (92,60%), Agent 626/686 (91,25%) e
frontend 109/112 (97,32%), mínimo 80% individual. Os 25 testes frontend passaram,
incluindo contraste em Node e componentes compilados para cliente no renderer
de teste. Segurança, três harnesses HTTP, Playwright e Firefox passaram.
A falha de bind da terceira rodada permanece registrada; não houve retry
interno do teste nem alteração de processo externo para obter este resultado.

AC-09/10/28/29 foram reavaliados com os testes/gates reais e bindings dos inputs
estáveis. Os demais critérios não foram promovidos automaticamente. A versão
anterior de evidence.json está em [commit-evidence-before.json](commit-evidence-before.json).
T043 concluiu a execução canônica e correção dos cinco bloqueios.

Após o sucesso da suíte, foram atualizados somente este relato, convergência e
registro de evidências, com inspeção manual. Nenhum código, configuração,
manifesto ou lock mudou após a execução aprovada. Os commits locais incluem os
arquivos novos não ignorados; caches operacionais continuam ignorados.
Publicação não foi solicitada.

### Revisão do índice e commits

O primeiro commit local é b3f1367, chore: record Cargo cache refresh manifests
and receipts, com 292 arquivos. O segundo grupo reúne 149 arquivos de correção,
testes, fontes/licenças Geiger e evidências. O conteúdo de cada grupo foi
comparado por SHA-256 entre workspace e blobs do índice, com inspeção de
indicadores de segredo sem expor valores. A inspeção Git apontou whitespace
em README/snapshots upstream e linhas de contexto de ec-dep-info.patch; os
bytes importados foram preservados, sem reformatar fixtures ou baseline.
Nenhum script avulso foi executado nessa revisão.
