# Contexto e estado do Ec

Ec é uma plataforma CI/CD em implementação. Esta entrada consolida o estado
documentado em 13/11/2026; não representa uma nova execução ou aceite do produto.
As [autoridades](../docs/constitution.md) definem requisitos e precedência;
o [contexto arquitetural](../docs/architecture/context.md) define responsabilidades.

## Implementação e caminhos

Os caminhos abaixo partem da raiz do repositório. Os guias dos componentes
registram incrementos implementados, comandos reais, evidências e limites.

| Área | Local | Estado e referência |
| --- | --- | --- |
| Contrato público v1 | `application/contracts/` | Campos/limites únicos, consumidos por Server, Agent e frontend. [Fronteira](../docs/decisions/ADR-003-public-journey-contract.md) |
| Agent Rust | `application/agents/` | Workspace executável; sessão, execução, entrega de saída e artefatos no recorte da primeira jornada. [Operação e limites](../docs/development/agent.md) |
| Server Rust | `application/server/backend/` | Parsing/schema, publicação, sessões/autorização HTTP, PostgreSQL e fila/tentativas. [Publicação](../docs/development/publication.md) e [orquestração](../docs/development/orchestration.md) |
| Frontend Vue 3/TypeScript/Pinia | `application/server/frontend/` | Editor, publicação e operação da primeira jornada; shell e demonstrações visuais têm limites próprios. [Guia](../docs/development/frontend.md) e [contrato visual](../application/server/frontend/DESIGN.md) |
| Testes entre componentes | `application/integration/tests/` | Harnesses de integração e ponta a ponta separados. [Guia](../docs/development/testing.md) |
| Verificação | `scripts/` | Runner e integração nativa com a Toolbox; disponibilidade/preparo externos não são presumidos. [Guia](../scripts/README.md) |
| Governança | `.codex/skills/`, `config/governance/`, `tools/governance/` | Instalação ativa, catálogo/proveniência e ferramentas. [Seleção e pontes](chain/README.md) |
| Documentação e pesquisa | `docs/`, `reverse_engineering_teamcity/` | Normas, decisões e registros; a pesquisa TeamCity é referência histórica, não implementação do produto |

Os workspaces Rust têm `Cargo.toml`, `Cargo.lock` e `rust-toolchain.toml`; o
frontend usa `package.json`, `package-lock.json` e npm. Consulte versões e comandos
nesses arquivos e nos guias, sem copiá-los para esta entrada.
Scripts de verificação seguem a [política canônica](../docs/development/task-verification.md).

## Pendências e aceitação

- A [primeira jornada](features/first-functional-journey/spec.md) mantém critérios
  pendentes, incluindo segredos por referência e aceite agregado; implementação
  parcial ou evidência histórica não aprova a jornada atual.
- Transporte gRPC/Protobuf e SQLite no Agent permanecem propostas. Isolamento,
  plataformas e capacidade precisam de decisões e evidências específicas; metas
  de carga não são capacidade demonstrada.
- As skills instaladas e o perfil não comprovam eficácia comportamental nem
  descoberta pela interface. O estado de aceite está nas evidências das features.

## Histórico e manutenção

Os registros datados permanecem nas [validações](../docs/README.md) e na
[convergência de governança](features/governance-adoption/converge.md). Consultar
um registro antigo não reativa seu estado, comando ou autorização. A evolução
dos scripts está na [feature correspondente](features/scripts-verification/converge.md).

Fontes originais ficam em `config/governance/sources/`; os hashes vigentes e de
origem estão no [perfil](../config/governance/profile.json). A instalação ativa
não tem baseline duplicada. Logs e temporários seguem a
[política de estado local](../docs/development/local-state.md).
