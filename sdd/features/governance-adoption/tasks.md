# Tarefas — adoção de governança

- [x] T001 [REQ-01, AC-01, D-01, R-01]: inventariar 71 candidatas, selecionar 67, preservar fontes e recursos, adaptar escopos/metadados.
- [x] T002 [REQ-02, AC-02, D-02, R-02]: revisar cinco documentos; adaptar AGENTS, constituição, arquitetura, análise e ADR.
- [x] T003 [REQ-03, AC-03, D-03, R-03]: configurar pontes reais e SDD portátil; validar links e metadados.
- [x] T004 [REQ-04, AC-04, D-04, R-04]: implementar/executar verificadores e testes negativos; registrar resultados e limites após correções.
- [x] T005 [REQ-05, AC-05, D-05, R-05]: instalar 67 skills no destino real, conferir 594 arquivos, migrar perfil/pontes, atualizar estado e reexaminar evidências afetadas. Concluído após execução autorizada fora do sandbox em 2026-11-03.

- [x] T006 [REQ-06, AC-06, D-06, R-06]: inventariar todo o checkout, preservar fontes pertinentes, atualizar consumidores, remover somente entradas/cache classificados e verificar integridade, testes e aceitação atual.

## Reobservação documental — 2026-11-03

A organização inicial da aplicação atualiza README, contexto e navegação sem alterar REQ-01 a REQ-06. Reexecutar os verificadores e os controles existentes após adaptar suas fixtures aos novos destinos de links; comparar os inputs anteriores e preservar a observação da limpeza. O [relatório da estrutura](../../../docs/validation/application-layout-20261103/report.md) registra resultados e limites desta nova observação, sem repetir ou reatribuir a remoção histórica.

## Correção de caminhos — 2026-11-04

A instrução do usuário move Agent e Server para `application/agents/` e `application/server/`. Contratos e critérios de governança permanecem vigentes. A [reobservação](../../../docs/validation/application-layout-20261104/report.md) preserva a evidência anterior, confere links e estrutura e reexecuta os controles com a fixture ajustada.

## Testes entre componentes sob application — 2026-11-04

Os testes antes reservados na raiz passam a `application/integration/tests/`, com integração e ponta a ponta separados. A fixture passa a copiar apenas `application/`. A [nova observação](../../../docs/validation/application-integration-tests-20261104/report.md) preserva a evidência anterior e reexamina os mesmos critérios de governança, sem aceitar runtime.

## Auditoria consolidada dos caminhos — 2026-11-04

A [auditoria atual](../../../docs/validation/application-path-audit-20261104/report.md) confere todos os arquivos da aplicação, links vigentes, caminhos literais, exemplos e referências históricas após as duas movimentações. As entradas distinguem caminhos da raiz e locais. Os critérios de governança são reobservados sem alterar seu escopo.

## Análise funcional e de contratos — 2026-11-04

A [revisão documental](../../../docs/validation/application-analysis-20261104/report.md) amplia o README e cria visão funcional/técnica, catálogo dos contratos e índice documental. T002/T003 recebem novas entradas de leitura; REQ-01 a REQ-06 permanecem inalterados. Preservar a evidência anterior, comparar inputs e reobservar os critérios pertinentes por inspeção das normas, links e ferramentas existentes. Não implementar ou aceitar runtime por esta revisão.

## Refatoração visual da skill documental — 2026-11-04

A [revisão](../../../docs/validation/documentation-skill-20261104/report.md) registra a refatoração solicitada de `technical-documentation`: Mermaid, recursos visuais profissionais e critérios mensuráveis. T001/T003/T005 recebem pacote, perfil, ponte e baseline revisados; a observação dos seis critérios é reexaminada com fontes originais preservadas, testes existentes e gates atuais. Aprovação estrutural não certifica qualidade de futuras documentações, renderização ou eficácia da skill.

## Recuperação do instalador

- [x] T007 [REQ-07, AC-07, D-07, R-07]: reproduzir em fixture, implementar journal/lock e regressões de interrupção/conflito/concorrência; registrar comandos e limitações na validação de 2026-11-05.
