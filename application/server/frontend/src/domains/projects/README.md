# projects — Gestão de projetos

**Estado:** módulo planejado; somente documentação e diretórios `src/` e `tests/`, sem código ou testes executáveis.

## Mapa de responsabilidades

[![Onde termina o domínio e como ele se integra? O domínio exporá uma interface pública mínima e consumirá apenas os limites indicados. app não é dependência do domínio; stores e clientes privados permanecem internos. Código e fiscalização de imports ainda não existem.](../../../../../../docs/assets/diagrams/frontend-projects.svg)](../../../../../../docs/assets/diagrams/frontend-projects.svg)

O domínio exporá uma interface pública mínima e consumirá apenas os limites indicados. app não é dependência do domínio; stores e clientes privados permanecem internos. Código e fiscalização de imports ainda não existem.

[Ampliar figura](../../../../../../docs/assets/diagrams/frontend-projects.svg) · [Fonte Mermaid](../../../../../../docs/assets/diagrams/frontend-projects.mmd)

## Escopo

Lista/árvore, detalhes, hierarquia, parâmetros, templates e recursos associados.

Hierarquia de projetos não é DAG de pipeline.

Fontes: [norma de produto](../../../../../../docs/EC-REGRAS-DE-DESIGN-E-PRODUTO.md), §9; [norma de interface](../../../../../../docs/EC-REGRAS-CRIACAO-DE-TELAS-E-IMAGENS.md).

## Integração planejada

Pode consumir **shared e interface pública de configuration**, sem importar `app/` ou implementações privadas de outros domínios. Dependências externas adicionais ainda não foram escolhidas.

Páginas, contribuições de rota e descritores de recursos de projeto. A entrada pública futura será `src/index.ts`, com exportações explícitas e mínimas, sem expor stores e clientes internos por conveniência. Nenhum arquivo TypeScript é criado nesta etapa.

## Onde implementar e verificar

`src/` receberá páginas, componentes, composables, stores Pinia e acesso à API pertencentes ao domínio, conforme a necessidade. Testes unitários ficarão junto desses arquivos. `tests/` receberá integração entre partes do domínio pela interface pública; integração entre domínios e jornadas de interface ficam em `application/server/frontend/tests/`.

**Verificação futura:** mudança de hierarquia, permissões e revisão publicada. Casos com API simulada serão identificados como testes de interface, sem alegar ponta a ponta da plataforma.

## Referências

[Organização da aplicação](../../../../../../docs/architecture/application-layout.md) · [Política de testes](../../../../../../docs/development/testing.md) · [Autoridades](../../../../../../docs/constitution.md).

[Voltar ao componente](../../../README.md). Mantenha este escopo junto de mudanças de responsabilidade, interface, dependências ou verificação; publique comandos somente após executá-los.
