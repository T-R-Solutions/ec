# Revisão da documentação — 2026-11-04

## Objetivo, escopo e fontes

Refazer a documentação de entrada para leitores do produto e contribuidores, preservando contratos e registros históricos. Rota local documental conforme o workflow: não há mudança de contrato de runtime, persistência ou autorização. As fontes são `sdd/project.md`, `docs/constitution.md`, `docs/architecture/context.md`, ADR-001/002, ambas as normas de produto/interface e os arquivos reais da aplicação/ferramentas.

O [README principal](../../../README.md) apresenta estado, produto, navegação e o primeiro check. O [índice documental](../../README.md) separa produto, contribuição, autoridades e registros. O [guia de contribuição](../../development/contributing.md) concentra procedimentos. Novas entradas para [aplicação](../../../application/README.md), [ferramentas](../../../tools/governance/README.md) e [pesquisa](../../../reverse_engineering_teamcity/README.md) evitam exploração manual de diretórios. Os 34 READMEs existentes da aplicação foram revisados, preservando responsabilidades, dependências e cenários previstos; 23 módulos ganham títulos mais diretos e retorno ao componente. SDD, estado e mapa físico acompanham as entradas.

A revisão usa `readme-lifecycle-engineering` como primária e `technical-documentation` como auxiliar para síntese, diagramas e limites de evidência. Os arquivos efetivos em `.codex/skills/` e seus hashes constam dos inputs. Não houve execução das demais skills, delegação, alteração de pacotes, instalação, commit ou publicação.

## Preservação e observação

[previous.zip](previous.zip) preserva as versões anteriores dos 43 arquivos candidatos à edição, incluindo evidence.json. O [escopo](scope.json) registra hashes anteriores e a comparação dos 1.436 inputs anteriores, íntegros antes da mudança. Arquivos novos são listados no [escopo](scope.json); o inventário atual está nos [checks](checks.json). Fontes normativas, fontes importadas, baseline e pesquisa interna permanecem intactas. A nova entrada na raiz da pesquisa apenas encaminha para os registros preservados.

Os checks registram comandos, saídas e códigos de saída, links locais das entradas vigentes, inventário atual da aplicação e comparação dos inputs. O gate posterior às edições rejeitou os vínculos antigos com exit 1 e `Stale input`, conforme [stale-gate.json](stale-gate.json); a evidência atual substitui a anterior após reexaminar os critérios. [gates.json](gates.json) registra o resultado final do validador padrão e do aceite com freshness.

| Critério | Método da nova observação | Estado |
| --- | --- | --- |
| AC-01 | Auditoria do perfil, pacotes, fontes e hashes, sem mudar catálogo | APPROVED |
| AC-02 | Revisão semântica contra autoridades preservadas | APPROVED |
| AC-03 | Links locais, estrutura SDD e navegação das entradas | APPROVED |
| AC-04 | Suíte existente em cópias temporárias, com casos de rejeição | APPROVED |
| AC-05 | Auditor com instalação exigida e preflight sem escrita | APPROVED |
| AC-06 | Comparação dos inputs fora do escopo, fontes e registros anteriores | APPROVED |

Auditor padrão e com instalação exigida: `PASS`, 67 skills e 596 arquivos. Preflight: `ALREADY_INSTALLED`. Estrutura SDD: `PASS`. A primeira execução da suíte falhou porque a fixture não copiava o novo README raiz da pesquisa; foi incluído esse arquivo na cópia temporária, sem alterar asserções. A nova execução terminou com **24 testes, OK**, exit 0. Resultados em [command-checks.json](command-checks.json) e [tests-final.json](tests-final.json). Foram conferidos 449 destinos locais em 52 documentos, sem erros. Os 42 inputs anteriores modificados estão dentro do escopo; nenhum input anterior fora dele diverge. A aplicação contém 81 arquivos: 35 READMEs e 46 marcadores, sem código de produto.

## Diagramas e limites visuais

As duas figuras em [application-overview.md](../../architecture/application-overview.md) usam Mermaid editável no próprio documento. Arquitetura responde onde ficam as responsabilidades; publicação explica o caminho de validação/permissão e as rejeições. Fontes: autoridades, contexto e catálogo de contratos, observados nesta revisão. As figuras descrevem requisitos planejados; não são topologia implantada, API ou máquina de estados executável.

Direção visual: agrupamentos por responsabilidade, leitura vertical, texto explícito nas relações e decisões, sem significado dependente só de cor. Tokens de autoria: interface/etapas azul (`#eff6ff`, borda `#1d4ed8`, texto `#172554`); Server verde (`#f0fdfa`, `#0f766e`, `#134e4a`); Agent violeta (`#f5f3ff`, `#6d28d9`, `#3b0764`); decisões âmbar e rejeições rosa, identificadas também por forma/rótulo. Tipografia e espaçamento são do renderizador de destino; nenhum ativo externo é necessário. A descrição `accDescr` e os parágrafos/listas adjacentes explicam as relações sem depender da figura.

Inspeção semântica e de fontes: realizada. Renderização, compatibilidade com versão Mermaid, legibilidade, contraste final, reflow e acessibilidade no cliente: **UNVERIFIED**. `mmdc` e Chromium não foram encontrados no PATH; nenhum renderizador foi instalado. Para concluir, renderizar os dois blocos com a versão do cliente Markdown escolhido e inspecionar cortes, relações, contraste e rótulos em larguras de 360, 768 e 1280 px, com ampliação da figura quando necessária. Não se declara aprovação visual.

## Limites e manutenção

Checks de links não validam URLs externas, renderização nem compreensão por leitores. Não foram executados produto, banco, rede, workloads ou benchmarks. A aprovação de governança não comprova eficácia de skills, isolamento, autorização ou acessibilidade do Ec.

Ao mudar estado, comandos, módulos, testes ou contratos, atualizar a fonte canônica e suas entradas consumidoras conforme o guia de contribuição. Ao mudar figura/contrato ou renderizador, repetir a revisão visual pertinente. Relatórios anteriores conservam seus bytes e resultados; a evidência corrente identifica esta observação sem reescrever o histórico.
