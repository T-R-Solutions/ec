# Ec Server

**Local:** `application/server/`, relativo à raiz. Caminhos abreviados neste documento são locais ao componente ou módulo citado.

## Mapa de responsabilidades

[![Onde ficam autoria, controle e execução? Arquitetura alvo: o Server coordena e mantém estado operacional; os Agents executam trabalho autorizado. YAML representa configuração desejada. As setas indicam relações conceituais, sem definir endpoints ou topologia implantada.](../../docs/assets/diagrams/ec-system.svg)](../../docs/assets/diagrams/ec-system.svg)

Arquitetura alvo: o Server coordena e mantém estado operacional; os Agents executam trabalho autorizado. YAML representa configuração desejada. As setas indicam relações conceituais, sem definir endpoints ou topologia implantada.

[Ampliar figura](../../docs/assets/diagrams/ec-system.svg) · [Fonte Mermaid](../../docs/assets/diagrams/ec-system.mmd)

O Server reúne o monólito modular Rust do control plane e a interface Vue 3 +
TypeScript + Pinia. Há incrementos executáveis locais; os guias de
[orquestração](../../docs/development/orchestration.md) e
[frontend](../../docs/development/frontend.md) registram operação e limites.
A organização modular não demonstra conclusão de todas as capacidades planejadas.

- [Backend](backend/README.md): domínios, composição, API HTTP versionada e estado operacional planejado em PostgreSQL.
- [Frontend](frontend/README.md): administração, autoria e operação organizadas por domínios.

A organização não define serviços independentes por crate nem decide como os arquivos do frontend serão distribuídos. Configuração desejada continua em YAML canônico; sessões, fila, leases e resultados são estado operacional.

Integração interna pertence ao `tests/` do componente responsável. Jornadas que atravessam backend, frontend e/ou Agent ficam nos [testes da plataforma](../integration/tests/README.md). Manifests, locks e comandos reais estão nos guias dos componentes. A
[verificação](../../docs/development/task-verification.md) e o
[contrato de scanners](../../scripts/README.md) são comuns ao
checkout; a integração STIC tem estrutura migrada e gates bloqueados por capacidades externas.

[Organização](../../docs/architecture/application-layout.md) · [Política de testes](../../docs/development/testing.md) · [Autoridades](../../docs/constitution.md) · [Entrada do projeto](../../README.md).

[Mapa da aplicação](../README.md) · [Contribuir e verificar o checkout](../../docs/development/contributing.md)
