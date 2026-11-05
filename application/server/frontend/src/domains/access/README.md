# access — Pessoas e acesso

**Estado:** módulo planejado; somente documentação e diretórios `src/` e `tests/`, sem código ou testes executáveis.

## Mapa de responsabilidades

[![Onde termina o domínio e como ele se integra? O domínio exporá uma interface pública mínima e consumirá apenas os limites indicados. app não é dependência do domínio; stores e clientes privados permanecem internos. Código e fiscalização de imports ainda não existem.](../../../../../../docs/assets/diagrams/frontend-access.svg)](../../../../../../docs/assets/diagrams/frontend-access.svg)

O domínio exporá uma interface pública mínima e consumirá apenas os limites indicados. app não é dependência do domínio; stores e clientes privados permanecem internos. Código e fiscalização de imports ainda não existem.

[Ampliar figura](../../../../../../docs/assets/diagrams/frontend-access.svg) · [Fonte Mermaid](../../../../../../docs/assets/diagrams/frontend-access.mmd)

## Escopo

Sessão da interface, usuários, equipes, papéis, vínculos e visualização de permissões efetivas.

Ocultar ação na UI não autoriza nem protege a operação no backend.

Fontes: [norma de produto](../../../../../../docs/EC-REGRAS-DE-DESIGN-E-PRODUTO.md), §§10, 23; [norma de interface](../../../../../../docs/EC-REGRAS-CRIACAO-DE-TELAS-E-IMAGENS.md).

## Integração planejada

Pode consumir **shared e interface pública de configuration**, sem importar `app/` ou implementações privadas de outros domínios. Dependências externas adicionais ainda não foram escolhidas.

Páginas, descritores e interface mínima de sessão para composição em app. A entrada pública futura será `src/index.ts`, com exportações explícitas e mínimas, sem expor stores e clientes internos por conveniência. Nenhum arquivo TypeScript é criado nesta etapa.

## Onde implementar e verificar

`src/` receberá páginas, componentes, composables, stores Pinia e acesso à API pertencentes ao domínio, conforme a necessidade. Testes unitários ficarão junto desses arquivos. `tests/` receberá integração entre partes do domínio pela interface pública; integração entre domínios e jornadas de interface ficam em `application/server/frontend/tests/`.

**Verificação futura:** revogação, sessão expirada, acesso negado e origem de permissão herdada. Casos com API simulada serão identificados como testes de interface, sem alegar ponta a ponta da plataforma.

## Referências

[Organização da aplicação](../../../../../../docs/architecture/application-layout.md) · [Política de testes](../../../../../../docs/development/testing.md) · [Autoridades](../../../../../../docs/constitution.md).

[Voltar ao componente](../../../README.md). Mantenha este escopo junto de mudanças de responsabilidade, interface, dependências ou verificação; publique comandos somente após executá-los.
