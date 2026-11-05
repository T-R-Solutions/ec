# Convergência — governança Ec após limpeza

Data: 2026-11-03. Nova observação após retirada das entradas dispensáveis; não reutiliza os hashes de aceitação anterior.

| Critério | Estado | Evidência e limite |
|---|---|---|
| AC-01 / REQ-01 / D-01 / R-01 / T001 | APPROVED | Catálogo de 71 candidatas, 67 selecionadas, seis condicionais e quatro excluídas; 578 recursos originais no bundle e 594 arquivos instalados conferidos. |
| AC-02 / REQ-02 / D-02 / R-02 / T002 | APPROVED | Cinco fontes documentais preservadas; limpeza não muda os contratos de produto nem adota propostas. |
| AC-03 / REQ-03 / D-03 / R-03 / T003 | APPROVED | Estrutura, pontes e vínculos revalidados com as novas fontes e evidências. |
| AC-04 / REQ-04 / D-04 / R-04 / T004 | APPROVED | 24 testes de governança, incluindo controles de ausência/alteração dos recursos originais e escape no bundle. |
| AC-05 / REQ-05 / D-05 / R-05 / T005 | APPROVED | Auditoria real --require-installed e preflight passam; instalação e baseline intactos. |
| AC-06 / REQ-06 / D-06 / R-06 / T006 | APPROVED | 16 arquivos removidos após preservação; inventário completo e comparação de hashes dos arquivos mantidos; verificações sem skills_to_use. |

Consultar [relatório de limpeza](../../../docs/validation/workspace-cleanup-20261103/report.md), [checks](../../../docs/validation/workspace-cleanup-20261103/checks.json) e [evidence.json](evidence.json). Os registros substituídos estão em [previous.zip](../../../docs/validation/workspace-cleanup-20261103/previous.zip). Eficácia das skills, descoberta na UI e runtime Ec não foram verificados. O material descartado de produto Ox não é recuperável pelo hash dos ZIPs antigos.

## Reobservação após estrutura da aplicação — 2026-11-03

Os estados da tabela acima descrevem a observação da limpeza. A [validação atual](../../../docs/validation/application-layout-20261103/report.md) registra a revisão dos contratos, verificações reexecutadas e comparação dos inputs após a documentação da aplicação. A evidência anterior foi preservada em [previous.zip](../../../docs/validation/application-layout-20261103/previous.zip); evidence.json passa a apontar para a observação atual. Nenhuma remoção histórica foi reexecutada. Os seis critérios continuam limitados à governança; runtime não foi implementado ou aceito.

## Correção de caminhos — 2026-11-04

A instrução do usuário move Agent e Server para `application/agents/` e `application/server/`. Contratos e critérios de governança permanecem vigentes. A [reobservação](../../../docs/validation/application-layout-20261104/report.md) preserva a evidência anterior, confere links e estrutura e reexecuta os controles com a fixture ajustada.

## Testes entre componentes sob application — 2026-11-04

Os testes antes reservados na raiz passam a `application/integration/tests/`, com integração e ponta a ponta separados. A fixture passa a copiar apenas `application/`. A [nova observação](../../../docs/validation/application-integration-tests-20261104/report.md) preserva a evidência anterior e reexamina os mesmos critérios de governança, sem aceitar runtime.

## Auditoria consolidada dos caminhos — 2026-11-04

A [auditoria atual](../../../docs/validation/application-path-audit-20261104/report.md) confere todos os arquivos da aplicação, links vigentes, caminhos literais, exemplos e referências históricas após as duas movimentações. As entradas distinguem caminhos da raiz e locais. Os critérios de governança são reobservados sem alterar seu escopo.

## Análise funcional e de contratos — 2026-11-04

A [nova observação](../../../docs/validation/application-analysis-20261104/report.md) examina as entradas ampliadas contra as autoridades e o conteúdo real do checkout. O registro anterior foi preservado em [previous.zip](../../../docs/validation/application-analysis-20261104/previous.zip). Os seis critérios continuam restritos à governança: inventário/instalação, contratos documentais, estrutura/links, testes de rejeição e preservação dos registros de limpeza. O relatório distingue essa reobservação de runtime, renderização e eficácia das skills, que permanecem não verificados.

## Refatoração visual da skill documental — 2026-11-04

A [revisão](../../../docs/validation/documentation-skill-20261104/report.md) registra a refatoração solicitada de `technical-documentation`: Mermaid, recursos visuais profissionais e critérios mensuráveis. T001/T003/T005 recebem pacote, perfil, ponte e baseline revisados; a observação dos seis critérios é reexaminada com fontes originais preservadas, testes existentes e gates atuais. Aprovação estrutural não certifica qualidade de futuras documentações, renderização ou eficácia da skill.

## Reorganização documental — 2026-11-04

A [revisão atual](../../../docs/validation/documentation-refresh-20261104/report.md) refaz as entradas do repositório e da aplicação, centraliza contribuição/verificação e revê as sínteses visuais. Os contratos de produto, pacotes e critérios da feature permanecem os mesmos. A versão anterior de evidence.json e dos documentos editados está no backup da revisão; os critérios são reexaminados com os comandos e inspeções ali registrados. A observação continua restrita à governança, sem aprovação de runtime ou renderização.

## Documentação visual enterprise — 2026-11-04

A [revisão visual](../../../docs/validation/documentation-enterprise-20261104/report.md) refatora README, entradas e documentação da aplicação com figuras SVG derivadas de Mermaid e mapas de composição. Fontes, contratos e registros históricos são preservados; a observação dos critérios de governança é refeita. Renderização da documentação e execução de produto têm escopos de evidência distintos.

## Recuperação — 2026-11-05

AC-01 a AC-06 reexaminados nos seus escopos de governança; AC-07 APPROVED para interrupções de processo em Linux local. A [validação da recuperação](../../../docs/validation/installer-recovery-20261105/checks.json) identifica reobservações, comandos, drift, falha inicial corrigida e limites. Registros anteriores preservados em previous.zip da mesma validação. Produto permanece fora deste aceite.
