# Convergência — governança Ec

Estado atual: AC-01 a AC-07 UNVERIFIED, conforme [evidence.json](evidence.json).
A [revisão de coesão](../../../docs/validation/governance-cohesion-20261111/report.md)
alterou pacotes, metadados e documentação sem executar verificadores. As seções
datadas seguintes preservam observações históricas, não aprovações atuais.
Backups nelas mencionados foram removidos pela política de retenção de 11/11/2026.

## Revisão dos scripts após análise do run-oUebC3O4 — 2026-11-07

Os scripts vinculados à inspeção anterior foram corrigidos; nenhum script foi
executado nesta tarefa. Critérios atuais permanecem UNVERIFIED. Esta revisão
inspecionou o impacto nas verificações; não exercitou a jornada de produto.
[Análise e limites](../../../docs/validation/automation-run-oUebC3O4-20261107/report.md).
Originais de evidência/convergência preservados em previous-related-evidence.zip
nesse diretório. As observações abaixo são históricas.

> Atualização de 2026-11-07 — os registros abaixo são históricos. A promoção
> de Geiger/Coverage/Trivy/CodeQL alterou inputs vinculados à evidência agregada
> desta feature. Os critérios atuais ficam UNVERIFIED até nova observação
> autorizada; nenhum resultado antigo foi reaproveitado como aprovação atual.
> [Registro e preservação](../../../docs/validation/default-quality-20261107/report.md).

## Observação da limpeza — 03/11/2026

Data: 2026-11-03. Nova observação após retirada das entradas dispensáveis; não reutiliza os hashes de aceitação anterior.

| Critério | Estado | Evidência e limite |
|---|---|---|
| AC-01 / REQ-01 / D-01 / R-01 / T001 | APPROVED | Catálogo de 71 candidatas, 67 selecionadas, seis condicionais e quatro excluídas; 578 recursos originais no bundle e 594 arquivos instalados conferidos. |
| AC-02 / REQ-02 / D-02 / R-02 / T002 | APPROVED | Cinco fontes documentais preservadas; limpeza não muda os contratos de produto nem adota propostas. |
| AC-03 / REQ-03 / D-03 / R-03 / T003 | APPROVED | Estrutura, pontes e vínculos revalidados com as novas fontes e evidências. |
| AC-04 / REQ-04 / D-04 / R-04 / T004 | APPROVED | 24 testes de governança, incluindo controles de ausência/alteração dos recursos originais e escape no bundle. |
| AC-05 / REQ-05 / D-05 / R-05 / T005 | APPROVED | Auditoria real --require-installed e preflight passam; instalação e baseline intactos. |
| AC-06 / REQ-06 / D-06 / R-06 / T006 | APPROVED | 16 arquivos removidos após preservação; inventário completo e comparação de hashes dos arquivos mantidos; verificações sem skills_to_use. |

Consultar [relatório de limpeza](../../../docs/validation/workspace-cleanup-20261103/report.md), [checks](../../../docs/validation/workspace-cleanup-20261103/checks.json) e [evidence.json](evidence.json). Os registros substituídos estão em `../../../docs/validation/workspace-cleanup-20261103/previous.zip` (cópia posteriormente removida). Eficácia das skills, descoberta na UI e runtime Ec não foram verificados. O material descartado de produto Ox não é recuperável pelo hash dos ZIPs antigos.

## Reobservação após estrutura da aplicação — 2026-11-03

Os estados da tabela acima descrevem a observação da limpeza. A [validação atual](../../../docs/validation/application-layout-20261103/report.md) registra a revisão dos contratos, verificações reexecutadas e comparação dos inputs após a documentação da aplicação. A evidência anterior foi preservada em `../../../docs/validation/application-layout-20261103/previous.zip` (cópia posteriormente removida); evidence.json passa a apontar para a observação atual. Nenhuma remoção histórica foi reexecutada. Os seis critérios continuam limitados à governança; runtime não foi implementado ou aceito.

## Correção de caminhos — 2026-11-04

A instrução do usuário move Agent e Server para `application/agents/` e `application/server/`. Contratos e critérios de governança permanecem vigentes. A [reobservação](../../../docs/validation/application-layout-20261104/report.md) preserva a evidência anterior, confere links e estrutura e reexecuta os controles com a fixture ajustada.

## Testes entre componentes sob application — 2026-11-04

Os testes antes reservados na raiz passam a `application/integration/tests/`, com integração e ponta a ponta separados. A fixture passa a copiar apenas `application/`. A [nova observação](../../../docs/validation/application-integration-tests-20261104/report.md) preserva a evidência anterior e reexamina os mesmos critérios de governança, sem aceitar runtime.

## Auditoria consolidada dos caminhos — 2026-11-04

A [auditoria atual](../../../docs/validation/application-path-audit-20261104/report.md) confere todos os arquivos da aplicação, links vigentes, caminhos literais, exemplos e referências históricas após as duas movimentações. As entradas distinguem caminhos da raiz e locais. Os critérios de governança são reobservados sem alterar seu escopo.

## Análise funcional e de contratos — 2026-11-04

A [nova observação](../../../docs/validation/application-analysis-20261104/report.md) examina as entradas ampliadas contra as autoridades e o conteúdo real do checkout. O registro anterior foi preservado em `../../../docs/validation/application-analysis-20261104/previous.zip` (cópia posteriormente removida). Os seis critérios continuam restritos à governança: inventário/instalação, contratos documentais, estrutura/links, testes de rejeição e preservação dos registros de limpeza. O relatório distingue essa reobservação de runtime, renderização e eficácia das skills, que permanecem não verificados.

## Refatoração visual da skill documental — 2026-11-04

A [revisão](../../../docs/validation/documentation-skill-20261104/report.md) registra a refatoração solicitada de `technical-documentation`: Mermaid, recursos visuais profissionais e critérios mensuráveis. T001/T003/T005 recebem pacote, perfil, ponte e baseline revisados; a observação dos seis critérios é reexaminada com fontes originais preservadas, testes existentes e gates atuais. Aprovação estrutural não certifica qualidade de futuras documentações, renderização ou eficácia da skill.

## Reorganização documental — 2026-11-04

A [revisão atual](../../../docs/validation/documentation-refresh-20261104/report.md) refaz as entradas do repositório e da aplicação, centraliza contribuição/verificação e revê as sínteses visuais. Os contratos de produto, pacotes e critérios da feature permanecem os mesmos. A versão anterior de evidence.json e dos documentos editados está no backup da revisão; os critérios são reexaminados com os comandos e inspeções ali registrados. A observação continua restrita à governança, sem aprovação de runtime ou renderização.

## Documentação visual enterprise — 2026-11-04

A [revisão visual](../../../docs/validation/documentation-enterprise-20261104/report.md) refatora README, entradas e documentação da aplicação com figuras SVG derivadas de Mermaid e mapas de composição. Fontes, contratos e registros históricos são preservados; a observação dos critérios de governança é refeita. Renderização da documentação e execução de produto têm escopos de evidência distintos.

## Recuperação — 2026-11-05

AC-01 a AC-06 reexaminados nos seus escopos de governança; AC-07 APPROVED para interrupções de processo em Linux local. A [validação da recuperação](../../../docs/validation/installer-recovery-20261105/checks.json) identifica reobservações, comandos, drift, falha inicial corrigida e limites. Registros anteriores preservados em previous.zip da mesma validação. Produto permanece fora deste aceite.


## Reobservação após T004 do produto — 2026-11-05

T004 de first-functional-journey atualizou quatro documentos vinculados a esta evidência: contexto do projeto/arquitetura e READMEs do backend/orquestração. A revisão preserva autoridades, dependências e propostas; reconhece apenas implementação e evidência efetivamente observadas. AC-01 a AC-07 desta feature continuam restritos à governança, com nova execução do auditor, preflight e 34 testes em cópias isoladas. Registros anteriores preservados em docs/validation/orchestration-20261105/governance-previous.zip; comandos, comparação de inputs e limites em governance-checks.json na mesma validação. Nenhuma remoção histórica ou instalação foi reatribuída; aceite de produto é independente.


## Reobservação T006 — interface web

Entradas frontend atualizadas com implementação real, preservando baselines históricas. Auditor require-installed PASS, install_skills --check ALREADY_INSTALLED e 34 testes OK; revisão dos links/autoridades atuais em docs/validation/frontend-20261105/governance-review.json. Evidência anterior preservada em previous-governance.zip; sem reinstalação ou mudança de skills. Governança não aprova a jornada do produto nem AC-01/AC-07 da feature funcional.

## Reobservação de scripts e encerramento — 2026-11-05

[Registro atual](../../../docs/validation/scripts-ec-20261105/report.md): comandos
reais de governança, Rust, frontend, integração e Firefox reexecutados; observações
anteriores preservadas em previous.zip. A política de verificação foi centralizada
e somente duas skills atualizadas. Gitleaks ausente bloqueia o aceite agregado da
verificação; AC-01/AC-07 da primeira jornada continuam pendentes por seus próprios
motivos. Não há aceitação por renovar hashes de observações antigas.

## Reintegração SAST/dependências — 2026-11-05

[Reobservação](../../../docs/validation/scripts-security-20261105/report.md): Opengrep
e OSV reintegrados, documentação de pré-requisitos e contratos revisados; 32 testes
de automação e 34 de governança passam. Instalação de skills/bundle não alterada;
aceite de scans reais permanece bloqueado por CLIs/bases ausentes. Fontes/evidências
anteriores preservadas no backup da revisão, sem redução dos critérios.


## Manutenção das skills de frontend — 11/11/2026

[Registro atual](../../../docs/validation/frontend-skills-20261111/report.md):
mesclagem autorizada em quatro skills, sincronização da baseline e perfil,
fonte suplementar e referências sob demanda. AGENTS.md e documentação atualizados.
As observações históricas acima preservam seus inputs e limites; não aprovam
esta manutenção. O backup previous.zip contém a evidência anterior sem renovação.

| Critérios | Alteração / observação | Estado atual |
| --- | --- | --- |
| AC-01, AC-03, AC-05 | Pacotes, referências e metadados inspecionados; auditor/preflight não executados | UNVERIFIED |
| AC-02 | Instruções e documentação atualizadas; sem aceite automatizado | UNVERIFIED |
| AC-04, AC-06, AC-07 | Nenhuma reexecução dos respectivos oráculos; limites anteriores preservados | UNVERIFIED |

A eficácia comportamental das skills não foi ensaiada. Retomar verificações
somente no fluxo autorizado; tratar quaisquer divergências pela origem, sem
renovar hashes de uma observação antiga para fazê-la parecer atual.


## Correção de declaração ambígua — 11/11/2026

O log docs:sdd de run-orZMYUEe rejeitou uma referência em prosa iniciada por
AC-05. A frase foi reescrita com “critério AC-05” na continuação, preservando a
declaração normativa e seu requisito. Inspeção do parser e da spec; nenhum
validador executado ou critério promovido. O estado atual continua UNVERIFIED.
