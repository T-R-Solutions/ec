Atualização consolidada de 2026-11-05: manifests, lockfiles e toolchains reais nos workspaces
`application/agents` e `application/server/backend`; frontend npm Vue/TS/Pinia em
`application/server/frontend`. Os incrementos e limites vigentes estão nos guias de
publicação, orquestração, Agent e frontend em docs/development. Descrições anteriores
de ausência de código abaixo são baseline histórica, superada somente nos incrementos
implementados. Encerramento de tarefas segue docs/development/task-verification.md;
não inventar comandos nem inferir funcionalidade pela existência de diretórios.

Atualização T005 em 2026-11-05: Agent Rust real, workspace com cinco crates, logs e artefatos PostgreSQL/HTTP implementados. [Operação e limites](../development/agent.md). Baselines documentais abaixo são preservadas historicamente; frontend e segredos por referência permanecem pendentes.

# Contexto arquitetural do Ec

Atualização T004 em 2026-11-05: parsing/schema, publicação PostgreSQL, sessões/autorização HTTP e fila/tentativas do Server estão implementados e exercitados. [Estado operacional, comandos e limites](../development/orchestration.md). Agent real, logs/artefatos e frontend continuam pendentes. As descrições de ausência de implementação abaixo preservam a baseline documental e são superadas por esta atualização somente nesses incrementos. Governança não constitui aceite da jornada completa.

## Leitura visual

[![Onde ficam autoria, controle e execução? Arquitetura alvo: o Server coordena e mantém estado operacional; os Agents executam trabalho autorizado. YAML representa configuração desejada. As setas indicam relações conceituais, sem definir endpoints ou topologia implantada.](../assets/diagrams/ec-system.svg)](../assets/diagrams/ec-system.svg)

Arquitetura alvo: o Server coordena e mantém estado operacional; os Agents executam trabalho autorizado. YAML representa configuração desejada. As setas indicam relações conceituais, sem definir endpoints ou topologia implantada.

[Ampliar figura](../assets/diagrams/ec-system.svg) · [Fonte Mermaid](../assets/diagrams/ec-system.mmd)

## Alvo e implementação observada

Server Rust: identidade/RBAC, YAML, projetos/pipelines, API, PostgreSQL, fila/scheduler e integração de Agents.
Agent Rust: sessão, capacidades/slots, checkout, execução, isolamento, saída e recuperação.
Frontend Vue 3/TypeScript/Pinia: administração e operação, três modos de configuração e confirmação pelo backend.
São limites planejados; não existem manifests de aplicação ou binários de produto neste checkout.

A pesquisa e o protótipo Rust em `reverse_engineering_teamcity/` não são implementação do Ec Server/Agent.
HTTP versionado/PostgreSQL são os contratos vigentes. gRPC/Protobuf e SQLite no Agent estão em [proposta](../ec-arquitetura-comunicacao-e-validacao.md).

A [visão funcional e técnica](application-overview.md) consolida os públicos, funcionalidades, jornada e responsabilidades dos módulos. O [catálogo de contratos](contracts.md) organiza invariantes, fontes, rejeições esperadas e especificações pendentes. Ambos sintetizam o estado observado sem promover requisitos a comportamento implementado; o [índice da documentação](../README.md) reúne as entradas vigentes.

## Organização física inicial

A [ADR-002](../decisions/ADR-002-application-layout.md) e o [mapa da aplicação](application-layout.md) registram a estrutura documental criada em `application/agents/crates/`, `application/server/backend/crates/` e `application/server/frontend/`. Cada domínio tem `src/` e `tests/`; composição e regras de dependência estão documentadas. Os testes entre componentes ficam em `application/integration/tests/`. A [política de testes](../development/testing.md) distingue unitários, integração e ponta a ponta. Pinia foi explicitada no pedido desta estrutura. A existência dos diretórios não significa código implementado.

## Próximas decisões

Fixar toolchains e configurar os workspaces planejados quando iniciar código; especificar schemas YAML e revisão publicada; definir contratos job/attempt/lease; avaliar transporte e durabilidade local por ADR e ensaios.
As fases A–E das [normas de produto](../EC-REGRAS-DE-DESIGN-E-PRODUTO.md) organizam fundamentos, núcleo seguro, paridade/gestão, expansão e ecossistema/escala. Não importar as fases F0 do Ox.
