# Contexto arquitetural do Ec

Atualização de estado em 2026-11-05: existe um incremento Rust executável de parsing/schema e CLI local de validação, com nove testes; publicação, API, PostgreSQL, Agent e frontend funcionais continuam pendentes. Consulte [estado e comandos executados](../development/first-functional-journey.md). As descrições de ausência total de código abaixo registram a baseline documental anterior e são superadas por esta atualização apenas quanto a esse incremento. O aceite de governança não é aceite de produto.

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
