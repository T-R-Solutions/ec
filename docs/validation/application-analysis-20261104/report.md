# Análise da aplicação e revisão da documentação

Data: 2026-11-04. Ambiente: checkout local sem histórico Git acessível; Python 3.13.16. Rota local documental conforme o [workflow](../../../sdd/workflow.md): síntese de contratos existentes, sem alteração de contrato de runtime, persistência, autorização ou implementação do produto.

## Objetivo e resultado documental

O pedido é analisar a aplicação e atualizar o README e documentação complementar sobre identidade, funcionalidades, implementações e contratos. O [README](../../../README.md) passa a cobrir esses quatro assuntos, estado real e comandos verificáveis. A [visão funcional e técnica](../../architecture/application-overview.md) organiza públicos, jornada, responsabilidades e implementação observada; o [catálogo de contratos](../../architecture/contracts.md) relaciona requisitos, fontes, módulos, rejeições e lacunas. O [índice documental](../../README.md), [contexto arquitetural](../../architecture/context.md) e [estado SDD](../../../sdd/project.md) conectam essas referências. Não há funcionalidades de produto implementadas por esta mudança.

## Base da análise

- Instruções locais, contexto/autoridades, as duas normas de produto/interface e ADR-001/002.
- Árvore completa de `application/` e seus READMEs: quatro domínios Agent, oito backend, dois módulos de composição, nove domínios frontend, app/shared e organização de testes. São 80 arquivos, sendo 34 READMEs e 46 marcadores vazios.
- Auditor, instalador e testes em `tools/governance/`; contrato de evidências, workflow, validador e feature `governance-adoption`; perfil e registros de instalação/limpeza/caminhos pertinentes.
- Entrada da pesquisa histórica, modelo Rust sequencial de leases e seu validador, além do inventário de utilitários. A pesquisa foi consultada para delimitar sua relação com Ec; não houve nova engenharia reversa de TeamCity, auditoria de todo o corpus ou reexecução de geradores/ensaios históricos.
- Proposta de comunicação, durabilidade e escala: gRPC/Protobuf, SQLite, mTLS concreto e parâmetros experimentais não são promovidos a implementação ou decisão vigente.

O [inventário observado](inventory.json) registra os arquivos da aplicação e as fontes pertinentes com SHA-256. Sem código de produto, manifests, schemas executáveis, migrations ou endpoints, a análise funcional é de requisitos e responsabilidades documentadas. A inspeção das ferramentas serve à documentação dos comandos e limites, sem representar auditoria exaustiva de segurança dos utilitários.

## Métodos, verificações e evidência

As versões anteriores dos arquivos editados e de `evidence.json` estão em [previous.zip](previous.zip); o [escopo](scope.json) foi registrado antes das edições. Os 1.415 inputs da evidência anterior foram comparados antes da mudança, sem divergência. As fontes normativas, histórico, aplicação e ferramentas são preservados; os inputs modificados devem estar contidos no escopo declarado.

A inspeção semântica compara as sínteses com as fontes acima: YAML/estado operacional separados; HTTP/PostgreSQL vigentes; gRPC/SQLite propostos; revisão imutável; autorização no backend; retry/cancelamento e leases explícitos; segurança/acessibilidade como requisitos ainda não demonstrados. A tabela abaixo identifica os métodos de reobservação dos critérios existentes, sem criar novos gates de produto.

| Critério existente | Estado | Método e resultado desta observação |
| --- | --- | --- |
| AC-01 / T001 | APPROVED | Auditor real confere catálogo, origens, bundle e hashes dos pacotes; PASS |
| AC-02 / T002 | APPROVED | Inspeção das normas e novas sínteses preserva os contratos; hashes das fontes originais sem alteração |
| AC-03 / T003 | APPROVED | Links locais das entradas/documentação vigente e aplicação resolvem; estrutura SDD com exit 0; vínculos reobservados |
| AC-04 / T004 | APPROVED | 24 testes existentes em cópias isoladas, incluindo controles de rejeição, com resultado OK |
| AC-05 / T005 | APPROVED | Auditor com `--require-installed` retorna PASS; instalador em `--check` retorna ALREADY_INSTALLED para 67 skills |
| AC-06 / T006 | APPROVED | Inputs fora do escopo e registros de limpeza/fontes/baseline preservados; nenhuma remoção histórica repetida |

Os [checks](checks.json) guardam comandos, saídas, códigos de saída, comparação de inputs e resultados de links/inventário. O gate anterior passou antes das edições e rejeitou os cinco inputs modificados após elas, com exit 1 e diagnóstico `Stale input`. Auditor padrão, auditor com instalação exigida, preflight, estrutura SDD e suíte de governança retornaram exit 0 na nova coleta. A conferência usa enumeração de arquivos e resolução dos destinos Markdown locais a partir do documento; não valida âncoras ou URLs externas. Após a coleta, a evidência corrente é vinculada aos inputs observados e os [gates](gates.json) registram validação padrão e aceite com freshness. Esses estados aprovam somente os critérios de governança listados.

## Skills e manutenção

Skill primária aplicada: `.codex/skills/readme-lifecycle-engineering/SKILL.md`, para entradas, estado honesto, navegação e comandos reproduzíveis. Auxiliar aplicada: `.codex/skills/technical-documentation/SKILL.md`, para síntese de funcionalidades/contratos baseada nas fontes e identificação das lacunas. Caminhos e hashes efetivamente observados constam no inventário; não se alega execução das demais skills. Não houve delegação, instalação, commit ou publicação.

Revisitar README, visão, contratos e índice ao introduzir código, dependências, schemas, APIs, testes ou novas decisões. Manter detalhes locais nos READMEs dos componentes e contratos de produto nas autoridades; produzir observações novas quando inputs de evidência mudarem.

## Limites

Esta revisão não executa Ec Server/Agent/frontend, banco, rede, workloads, testes de produto ou benchmarks; tampouco demonstra isolamento, acessibilidade, renderização Markdown/Mermaid em um cliente específico, eficácia das skills ou descoberta pela UI. URLs externas não foram consultadas/validadas; o objeto são fontes locais. A cópia importada `docs/readme-lifecycle-engineering-SKILL.md` conserva cinco links relativos indisponíveis naquele diretório, já identificados na auditoria anterior; os recursos existem no pacote instalado. Evidências históricas e seus caminhos antigos são preservados.
