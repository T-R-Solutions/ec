# Tarefas — scripts Ec

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
