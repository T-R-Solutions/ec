# Tarefas — adoção de governança

As marcações abaixo registram entregas históricas. O aceite atual está em
[evidence.json](evidence.json); manutenção posterior exige reobservação.

- [x] T001 [REQ-01, AC-01, D-01, R-01]: inventariar 71 candidatas, selecionar 67, preservar fontes e recursos, adaptar escopos/metadados.
- [x] T002 [REQ-02, AC-02, D-02, R-02]: revisar cinco documentos; adaptar AGENTS, constituição, arquitetura, análise e ADR.
- [x] T003 [REQ-03, AC-03, D-03, R-03]: configurar pontes reais e SDD portátil; validar links e metadados.
- [x] T004 [REQ-04, AC-04, D-04, R-04]: implementar/executar verificadores e testes negativos; registrar resultados e limites após correções.
- [x] T005 [REQ-05, AC-05, D-05, R-05]: instalar 67 skills no destino real, conferir 594 arquivos, migrar perfil/pontes, atualizar estado e reexaminar evidências afetadas. Concluído após execução autorizada fora do sandbox em 2026-11-03.

- [x] T006 [REQ-06, AC-06, D-06, R-06]: inventariar todo o checkout, preservar fontes pertinentes, atualizar consumidores, remover somente entradas/cache classificados e verificar integridade, testes e aceitação atual.

## Reobservação documental — 2026-11-03

A organização inicial da aplicação atualiza README, contexto e navegação sem alterar REQ-01 a REQ-06. Reexecutar os verificadores e os controles existentes após adaptar suas fixtures aos novos destinos de links; comparar os inputs anteriores e preservar a observação da limpeza. O [relatório da estrutura](../../../docs/validation/application-layout-20261103/report.md) registra resultados e limites desta nova observação, sem repetir ou reatribuir a remoção histórica.

## Correção de caminhos — 2026-11-04

Resultado histórico registrado na [convergência](converge.md); esta seção
identifica a manutenção das tarefas, sem repetir a observação.

## Testes entre componentes sob application — 2026-11-04

Resultado histórico registrado na [convergência](converge.md); esta seção
identifica a manutenção das tarefas, sem repetir a observação.

## Auditoria consolidada dos caminhos — 2026-11-04

Resultado histórico registrado na [convergência](converge.md); esta seção
identifica a manutenção das tarefas, sem repetir a observação.

## Análise funcional e de contratos — 2026-11-04

A [revisão documental](../../../docs/validation/application-analysis-20261104/report.md) amplia o README e cria visão funcional/técnica, catálogo dos contratos e índice documental. T002/T003 recebem novas entradas de leitura; REQ-01 a REQ-06 permanecem inalterados. Preservar a evidência anterior, comparar inputs e reobservar os critérios pertinentes por inspeção das normas, links e ferramentas existentes. Não implementar ou aceitar runtime por esta revisão.

## Refatoração visual da skill documental — 2026-11-04

Resultado histórico registrado na [convergência](converge.md); esta seção
identifica a manutenção das tarefas, sem repetir a observação.

## Recuperação do instalador

- [x] T007 [REQ-07, AC-07, D-07, R-07]: reproduzir em fixture, implementar journal/lock e regressões de interrupção/conflito/concorrência; registrar comandos e limitações na validação de 2026-11-05.


## Reobservação após T004 do produto — 2026-11-05

Resultado histórico registrado na [convergência](converge.md); esta seção
identifica a manutenção das tarefas, sem repetir a observação.

## Mesclagem seletiva de design — 11/11/2026

T001/T002/T003/T005 recebem manutenção de conteúdo e metadados:

- Análise e plano: comparar as 11 fontes com referências atuais, registrar deltas e redundâncias.
- Implementação: atualizar quatro pacotes, baseline e perfil com versão anterior preservada.
- Documentação: atualizar AGENTS.md, seleção de métodos, estado e convergência.
- Verificação pendente: auditor, preflight, validador SDD e testes pelo fluxo autorizado.

As três primeiras atividades estão implementadas e inspecionadas; isso não marca
novamente os gates históricos como aprovados. AC-01 a AC-07 permanecem UNVERIFIED.
[Arquivos, origem e limites](../../../docs/validation/frontend-skills-20261111/report.md).

## Coesão — 11/11/2026

T001/T002/T003/T005 recebem revisão das 67 skills, referências, pontes, AGENTS,
contexto, templates e perfil. T007 recebe alinhamento da preparação temporária
à retenção vigente. Conteúdo editado e inspecionado; verificadores e ensaios de
eficácia não executados. AC-01 a AC-07 continuam UNVERIFIED.

[Resultado e condição de retomada](../../../docs/validation/governance-cohesion-20261111/report.md).
