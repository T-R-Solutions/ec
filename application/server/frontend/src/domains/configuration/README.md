# configuration — Edição e publicação de configuração

**Estado:** módulo planejado; somente documentação e diretórios `src/` e `tests/`, sem código ou testes executáveis.

## Mapa de responsabilidades

[![Onde termina o domínio e como ele se integra? O domínio exporá uma interface pública mínima e consumirá apenas os limites indicados. app não é dependência do domínio; stores e clientes privados permanecem internos. Código e fiscalização de imports ainda não existem.](../../../../../../docs/assets/diagrams/frontend-configuration.svg)](../../../../../../docs/assets/diagrams/frontend-configuration.svg)

O domínio exporá uma interface pública mínima e consumirá apenas os limites indicados. app não é dependência do domínio; stores e clientes privados permanecem internos. Código e fiscalização de imports ainda não existem.

[Ampliar figura](../../../../../../docs/assets/diagrams/frontend-configuration.svg) · [Fonte Mermaid](../../../../../../docs/assets/diagrams/frontend-configuration.mmd)

## Escopo

Modelo de rascunho, projeções Visual/Formulário/YAML, diagnósticos, diff, conflito e estado de publicação.

Os modos compartilham semântica; a UI não substitui validação e autorização do backend.

Fontes: [norma de produto](../../../../../../docs/EC-REGRAS-DE-DESIGN-E-PRODUTO.md), §§14–16; [norma de interface](../../../../../../docs/EC-REGRAS-CRIACAO-DE-TELAS-E-IMAGENS.md).

## Integração planejada

Pode consumir **shared**, sem importar `app/` ou implementações privadas de outros domínios. Dependências externas adicionais ainda não foram escolhidas.

Interface de edição configurável por descritores, preservação de campos e resultados confirmados pelo backend. A entrada pública futura será `src/index.ts`, com exportações explícitas e mínimas, sem expor stores e clientes internos por conveniência. Nenhum arquivo TypeScript é criado nesta etapa.

## Onde implementar e verificar

`src/` receberá páginas, componentes, composables, stores Pinia e acesso à API pertencentes ao domínio, conforme a necessidade. Testes unitários ficarão junto desses arquivos. `tests/` receberá integração entre partes do domínio pela interface pública; integração entre domínios e jornadas de interface ficam em `application/server/frontend/tests/`.

**Verificação futura:** round-trip semântico, YAML inválido, campos desconhecidos e conflito de revisão. Casos com API simulada serão identificados como testes de interface, sem alegar ponta a ponta da plataforma.

## Referências

[Organização da aplicação](../../../../../../docs/architecture/application-layout.md) · [Política de testes](../../../../../../docs/development/testing.md) · [Autoridades](../../../../../../docs/constitution.md).

[Voltar ao componente](../../../README.md). Mantenha este escopo junto de mudanças de responsabilidade, interface, dependências ou verificação; publique comandos somente após executá-los.
