# Tarefas — scripts Ec

## Escopo somente Ec — 13/11/2026

- [x] T069 [AC-41/42, D-33/R-33]: remover auditoria de imagens Toolbox, configuração obsoleta e código sem consumidor; manter verificações Ec.
- [x] T070 [AC-41/42, D-33/R-33]: executar regressões e scans reais Ec; registrar resultados atuais e fechamento documental.

## Reparação do run-SNe1BAXu — 12/11/2026

- [x] T066 [AC-36–42, D-32/R-32]: analisar relatórios nativos, registrar causas,
  corrigir qualidade e tratamento de erros, manter configurações centralizadas e
  remover somente configuração incorreta/sem consumidor comprovado.
- [x] T067 [AC-36–42, D-32/R-32; depende T066]: testar rejeições e casos válidos,
  revalidar gates reais e escopo completo; registrar resultados, pendências
  externas e limites sem promover evidências históricas.

- T068 [AC-38, D-32/R-32]: cancelada pela instrução de 13/11/2026 que retira auditoria da Toolbox do Ec. Os achados anteriores permanecem históricos; não foram corrigidos nem aceitos como risco por esta mudança.

- [x] T045 [REQ-30/AC-30, D-26/R-26]: implementar descoberta local do ambiente, integração comum a todos os escopos, proteção contra source, diagnóstico RustSec e documentação. Casos de regressão escritos; inspeção de fontes/diff realizada, sem execução.
- [ ] T046 [AC-30, D-26/R-26]: executar os contratos e a suíte pelo fluxo autorizado; observar descoberta, overrides, isolamento, encerramento, todos os escopos e recursos reais. Instalações/bases ausentes continuam bloqueando os gates dependentes.

- [x] T015 [REQ-04/09/10/11, AC-04/09/10/11, D-13, R-13]: analisar run-oUebC3O4; corrigir execução Geiger por membro, fixtures e justificativas Ruff, normalizar rejeição LCOV e preparar regressões; inspecionar diferenças e preservar originais. Implementação concluída; testes novos/alterados não executados.
- [ ] T016 [AC-04/09/10/11, D-13, R-13]: preparar ShellCheck com adapter revisado, cargo-llvm-cov/llvm-tools-preview, provider V8 e scanners/bases/bancos; aguardar autorização específica de downloads e decisão sobre licença dos crates/elegibilidade CodeQL. Depois retomar T014 pelo fluxo canônico, sem substituir suíte completa por checks avulsos.

- [x] T001 [REQ-01, AC-01, D-01, R-01]: ler autoridades/manifests/guias e importados; observar baseline; inventariar e preservar originais; retirar componentes sem consumidor.
- [x] T002 [REQ-02, AC-02, D-02, R-02]: implementar plano e runner Ec e testar contratos reais e negativos.
- [x] T003 [REQ-03, AC-03, D-03, R-03]: integrar política, skills e metadados; preservar fonte/baseline e reobservar evidência afetada.
- [ ] T004 [REQ-04, AC-04, D-04, R-04]: motores obrigatórios executados;
  aceite agregado REJECTED porque Deny reprova licenças dos 11 crates privados Ec.
  Nenhuma exceção ou licença aplicada sem decisão do usuário. Demais resultados
  atuais em docs/validation/tooling-remediation-20261106/report.md.

- [x] T005 [REQ-05, AC-05, D-05, R-05]: restaurar wrappers, configurar regras/fixtures Ec e scan OSV offline; testar encaminhamento, snapshots, relatórios, versões e base; atualizar documentação e preservar registros anteriores.

T004 permanece pendente pelo gate de licenças. Os motores/bases reais executaram;
a validação por fixtures em T005 não substitui critérios de outras features.

## Migração shell — 2026-11-06

- [x] T006 [REQ-06, AC-06, D-06, R-06]: preservar originais, integrar capacidades e
  converter runner/módulos para shell; remover scripts_erase, migrar callers.
- [x] T007 [REQ-06, AC-06, D-06, R-06]: testar contratos shell, lint/sintaxe e execução
  proporcional; reobservar evidências afetadas e registrar bloqueios reais de AC-04.

- [x] T008 [REQ-07/08, AC-07/08, D-07/08, R-07/08]: integrar toolbox, configurar
  ferramentas nativas e scanners, testar contratos e documentar fontes oficiais.
- [x] T009 [AC-04/07/08]: executar runner completo e preparos autorizados, registrar
  bloqueios e reobservar evidências atuais sem renovar registros históricos.

- [x] T010 [AC-04/05/07/08, D-09/R-09]: corrigir/justificar cada alerta Ruff,
  preparar ShellCheck íntegro, disponibilizar Cargo Machete/Deny, integrar e executar
  Opengrep/OSV, rodar regressões e reobservar evidências afetadas.


## Gates padrão — 2026-11-07

- [x] T011 [REQ-09, AC-09, D-10, R-10]: integrar catálogo, preservar seleção e target/ordem; implementar inventário Geiger e regressões de catálogo. Inspeção manual; execução não realizada.
- [x] T012 [REQ-10, AC-10, D-11, R-11]: implementar gate por componente, provider V8/lockfile, inventário/LCOV atual e testes de fronteira/rejeição. Inspeção manual; cobertura real desconhecida.
- [x] T013 [REQ-11, AC-11, D-12, R-12]: implementar adapters nativos, preparos explícitos, manifests de integridade e parsing conservador; documentar fontes/licença/limites. Inspeção manual; motores/preparos não executados.
- [ ] T014 [AC-04/09/10/11]: após pedido de commit e resposta sim, executar suíte completa sem filtros; investigar eventuais bloqueios, coberturas e relatórios. Sem autorização de execução neste pedido. A pendência histórica do Deny continua registrada, sem inventar licença.

## Contrato Toolbox/Ec — 2026-11-07

- [x] T017 [REQ-12, AC-12, D-14, R-14]: inspecionar catálogo/adapters, configurar dados/ambientes e wrappers, preservar gate nativo, documentar bloqueios e preparar testes de contrato. Conclusão por implementação e inspeção manual, sem aceite executado.
- [ ] T018 [AC-04/12, D-14, R-14; retomada substituída por T019–T024]: preparar imagens/snapshots com autorização específica de downloads; resolver adapter/regras YARA-X antes de sua execução; no fluxo autorizado executar suíte completa, incluindo regressões novas. Checks adicionais exigem observação própria autorizada para aceite, pois não integram o catálogo padrão. Não reduzir critérios nem substituir suíte completa por filtros.

## Conclusão somente no Ec — tarefas planejadas em 2026-11-07

T018 permanece como pendência histórica; não executar seu preparo delegado com
escrita externa. Sua retomada passa pela sequência abaixo, detalhada no
[plano](toolbox-completion-plan.md). T019 tem implementação parcial para corrigir a execução autorizada da suíte;
ainda falta aceite completo. T020–T024 continuam pendentes.

- [ ] T019 [REQ-13, AC-13, D-15/R-15; ponte local implementada, validação pendente]: mapear efeitos de escrita e criar ponte do consumidor com raízes locais, biblioteca compartilhada somente leitura, bytecode desativado e teste de fronteira. Sem monkeypatch ou cópia integral da Toolbox.
- [ ] T020 [REQ-15, AC-15, D-15/R-15; depende T019]: preparar/reutilizar imagens e bases localmente, validar identidades/frescor e adotar locks sem publicar estado parcial. Downloads dependem de autorização específica.
- [ ] T021 [REQ-14, AC-14, D-16/R-16; depende T019]: regras/configuração/fixtures e adapter YARA-X locais; imagem verificável e receita Ec somente se necessária. Sem modificar o catálogo externo.
- [ ] T022 [REQ-17, AC-17, D-17/R-17; depende T020]: adapter Ec Trivy completo e testes das três categorias; retirar dependência nativa apenas após paridade e suíte observadas.
- [ ] T023 [REQ-16, AC-16, D-18/R-18; depende T020/T021/T022]: promover novos checks e regras ao padrão, deduplicar alias Trivy e preservar dependências e medição Syft no agregado.
- [ ] T024 [REQ-13 a REQ-18, AC-13 a AC-18, D-15 a D-18; depende T023]: regressões e documentação/evidências atuais; quando houver pedido de commit e escolha sim, executar a suíte completa sem filtros. Falta de motor, base ou autorização mantém critérios UNVERIFIED.

## Migração STIC — 08/11/2026

- T025 (REQ-19, D-19): estrutura/wrapper/config/chamadores e documentação migrados;
  inspeção local concluída, execução de testes não realizada.
- T026 (depende T025): inventário e exclusão dos downloads solicitados concluídos;
  recibos por arquivo em docs/validation/scripts-stic-20261108/deleted-downloads.jsonl.
- T027 (depende H03/H05/H06/H07 externos): integração dos gates públicos, paridade
  Trivy/CodeQL, preparo externo e homologação permanecem bloqueados. Não remover
  capacidades/checks para passar. Executar suíte somente no fluxo autorizado de commit.

As tarefas anteriores sobre ponte local/preparo nativo foram superadas por D-19;
resultados históricos continuam preservados, sem atualizar hashes antigos.

## Migração nativa — REQ-20 a REQ-26 / D-20 / R-20

- T028: inventariar baseline, contrato vigente e instalação real; preservar backup e alterações preexistentes.
- T029: implementar wrapper, seleção/manifesto, chamadas nativas e parsers; remover configs legadas sem chamadores. Depende T028.
- T030: implementar runner Playwright e três alvos CodeQL; fornecer pré-requisitos explícitos e rejeitar parcial. Depende T029.
- T031: testes negativos/positivos de contrato, cancelamento, segredos e relatórios; manter Firefox e Cargo. Depende T029/T030.
- T032: executar automate.sh run sem filtros, complementares disponíveis e tentativas reais; registrar versões/imagens/bases e bloqueios. Depende T031.
- T033: atualizar operação/preparo/recuperação e convergência com os cinco estados; nenhuma alegação de aceite sem evidência real. Depende T032.

## Limpeza de 09/11/2026

- T034: inventário, cópia recuperável antes das exclusões e retirada de G01/G02/G03;
  documentação e mapa de consumidores atualizados. Concluída por operações locais
  e inspeção estática/manual; [evidências e limites](../../../docs/validation/cleanup-20261109/report.md).
  Nenhum wrapper, parser, configuração, regra, fixture ou teste foi removido.
  Sem execução de testes, scripts de verificação, automate.sh, commit ou publicação.
  Critérios operacionais AC-01–AC-26 não são aprovados por esta limpeza.

## Adequação ao contrato central — 09/11/2026

- T035 [REQ-27, D-21/D-22]: documentos centrais e consumidores inspecionados;
  versões anteriores e hashes preservados localmente.
- T036 [AC-27, R-21/R-22]: ambiente, proveniência de bases e Checks Bundle
  ajustados; regressões escritas e inspecionadas, sem execução.
- T037 [AC-27]: guias, rastreabilidade e convergência atualizados;
  [relatório](../../../docs/validation/toolbox-alignment-20261109/report.md).
  Aceite executado continua UNVERIFIED; não foi solicitada execução de testes,
  automate.sh, download, commit ou administração central.

## Revisão de obsolescência em scripts/ — 09/11/2026

- T038: 45 arquivos inventariados; relatos de migração, alias retirado e conteúdo
  duplicado removidos do README, com cópia anterior e referências canônicas.
  Os 44 arquivos operacionais/de suporte foram preservados após inspeção de
  consumidores, source/imports e descoberta. Sem testes, scripts ou commits.
  [Decisões, inventário e recuperação](../../../docs/validation/scripts-cleanup-20261109/report.md).

## Diagnóstico Geiger — 09/11/2026

- T039 [REQ-09/AC-09]: acrescentar explicação por arquivo à falha Geiger, com
  retenção dos streams brutos e código nativo, indicação de consumidores e
  ausência de autorização de remoção. Helper, integração e guias inspecionados;
  casos de regressão escritos e não executados. Nenhum critério promovido a PASS.
  [Observações e limites](../../../docs/validation/geiger-diagnostics-20261109/report.md).

Após novo pedido de commit com escolha sim, T039 foi exercitada em
run-8ca5QvB9: oito casos novos e o contrato shell passaram, mas a regra de
layout reprovou a localização do helper. Ele foi movido para tools/quality,
preservando a regra e as asserções. Reexecução integral pendente; sem commit
ou aceite, com os cinco erros operacionais anteriores ainda presentes.

Reexecução posterior de T039 em run-BvlgAwX0: 95 testes de automação aprovados,
incluindo os oito casos novos, contrato shell e layout corrigido. A suíte
integral terminou com 38 aprovações e cinco erros (Geiger e cobertura).
Diagnóstico exercitado; aceite integral permanece UNVERIFIED e nenhum novo
commit foi criado. Resultados e logs acrescentados ao relatório de T039.

## Correção dos cinco bloqueios — 09/11/2026

- T040 [REQ-28, D-23/R-23]: implementada e inspecionada a correção Geiger em
  tools/quality/vendor/cargo-geiger, com build offline privado e regressões
  específicas. Diagnóstico complementar existente preservado. Build, testes e
  inventários reais dos dois workspaces ainda não executados.
- T041 [REQ-29, D-24/R-24]: implementada e inspecionada a coleta dos harnesses
  reais antes do LCOV, alvos instrumentados/companheiros privados, preflight,
  propagação de falhas e término normal do Server de teste por stdin privada.
- T042 [REQ-29, D-25/R-25]: escritos testes de transporte/heartbeat, processos,
  checkout, spool, envelopes/chunks e componentes/estado Vue; contratos Python
  do coletor e fixture shell Geiger atualizados. Não executados.
- T043 [AC-28/29, AC-09/10]: pendente. Após pedido explícito de commit e escolha
  sim, executar somente a suíte integral canônica, corrigir falhas reveladas e
  repetir a suíte integral até critérios comprovados. Não inferir 80% ou sucesso
  Geiger dos inputs atuais. Downloads exigem autorização específica.

- T044: caminhos comuns e retenção dos intermediários implementados; mudanças e inspeções em `docs/validation/local-state-20261110/report.md`. Execução dos contratos novos e integração completa pendentes; observar a política canônica, sem scripts avulsos.


## Diagnóstico do run-orZMYUEe e resolução mutável — 11/11/2026

- T047 [REQ-31/AC-31, D-27/R-27]: retirar flags locked e pin exato do Geiger;
  registrar locks alterados sem aprovar versões mistas; ajustar contratos e
  documentação. Remover imports Python fora de indentação e reconciliar SDD
  atual sem renovar hashes históricos. Implementado e inspecionado.
- T048 [AC-31, AC-09/10/28/29]: executar contratos e suíte pelo fluxo autorizado,
  observar atualizações reais de lock, rejeição de drift não relacionado e
  reexecução estável. Pendente; testes escritos não equivalem a aprovação.

- T049 [REQ-32/AC-32, D-28/R-28]: padrões efêmeros, descoberta do RustSec/
  toolchain, resolução atual de crates, manutenção Trivy e correção do store
  demonstrativo. Implementados; regressões escritas e inspeção de fontes.
- T050 [AC-32]: executar contratos/scan/suíte autorizados e confirmar expiração,
  manutenção parcial, isolamento do chamador e jornada Vue. Pendente.

- T051 [REQ-33, D-29/R-29]: configuração única, carregador validado, plano e consumidores; eliminar listas/caminhos/políticas duplicados. Implementado e inspecionado; execução pendente.
- T052 [REQ-34, D-29/R-29]: Cargo instalado, preparo por recurso, bases e ciclo de vida do PostgreSQL; ambiente efêmero e diagnóstico de ausência. Implementado e inspecionado; execução pendente.
- T053 [REQ-35, D-29/R-29]: retenção dos relatórios, correção dos contratos de testes e documentação canônica. Implementado e inspecionado; execução pendente.
- T054 [AC-33/34/35]: inspeção de todos os consumidores, regressões e suíte completa pelo fluxo autorizado; registrar resultados reais e lacunas. Pendente, sem aceite antecipado.


## Reparação do run-xK3jrVOn — 11/11/2026

- T055 [AC-24/28/33/34/35]: ler/confrontar todos os logs, corrigir ShellCheck,
  fixture Ruff, testes de catálogo/diagnóstico, proxy e guarda de asserts;
  retirar permissões Deny sem uso e preparar o plugin Geiger instalado.
  Implementada/inspecionada; build/instalação do plugin concluídos offline.
- T056 [AC-24/28/33/34/35]: decidir triagem Bandit e executar regressões/gates
  pelo fluxo autorizado sobre os inputs corrigidos. Pendente; a suíte antiga
  não valida os bytes novos. Firefox excluído das correções por instrução vigente.


## Reparação autorizada de 12/11/2026

- T057 [AC-36, D-30/R-30]: reparar validação SARIF, runtime CodeQL e preservar erros completos; regressões de severidade/extração. Implementada; três alvos reais aprovados, validação completa concluída em T060.
- T058 [AC-37, D-30/R-30]: usar grafo resolvido no Geiger, testar casos ambíguos/inativos e validar instalação/gates reais. Implementada; 192 testes e probe Server aprovados, validação completa concluída em T060.
- T059 [AC-38, D-30/R-30]: triagem Bandit por conteúdo e ocorrência na configuração única, regressões e documentação. Implementada; 375 revisadas/zero pendentes no scan real, validação completa concluída em T060.
- T060 [AC-36/37/38, D-30/R-30]: executar suíte completa com complementares; corrigir e repetir até inputs estáveis e exit final zero. Concluída no run completo registrado abaixo.

Fechamento T057–T060: concluídas em 12/11/2026; `run-3BXITHOD` aprovou as 54 tarefas, sem drift/locks alterados, com limpeza concluída. Evidência histórica em `docs/validation/automation-repair-20261112/run-3BXITHOD.json`; não aprova a ampliação posterior.


## Ampliação da cobertura — 12/11/2026

- T061 [AC-39/42, D-31/R-31]: referências oficiais por versão consultadas; políticas existentes ampliadas, configuração antiga substituída e seletor inativo retirado. Implementada/qualificada; sem redução para recuperar verde.
- T062 [AC-40, D-31/R-31]: dotenv e histórico Git redigido, identidade e rejeições de histórico insuficiente/mutável. Testes e execução real concluídos; 49 ocorrências mantidas para triagem.
- T063 [AC-39, D-31/R-31]: download autorizado e catálogos de quatro mantenedores importados com licença/revisão/hashes. 231 regras Opengrep declaradas, oito locais testadas e 754 arquivos YARA externos compilados; sem promessa de positivo por assinatura.
- T064 [AC-41, D-31/R-31]: 13 imagens Toolbox/Firefox resolvidas pela CLI pública, cadeia de identidade comprovada e scans concluídos sem socket. SBOM com 283 pacotes e dependências dos três locks integralmente comprovadas. Ocorrências permanecem pendentes, sem aprovação de segurança.
- T065 [AC-42, D-31/R-31]: executada suíte completa run-cHRcsFAw: 43 passed/nove findings/dois errors Clippy, exit 2, sem drift/locks alterados, cleanup ok. Limpeza posterior de seletor inativo validada no run-Ur88v3HT: seis tarefas e 177 testes aprovados, exit 0, cleanup ok. Fechamento documental separado; não renova bindings da execução completa.

Entrega de configuração/qualificação concluída; aprovação operacional reprovada
pelos achados e Clippy. Critérios operacionais permanecem UNVERIFIED, sem
commit/publicação. Resultados e limites em docs/validation/security-coverage-20261112.
