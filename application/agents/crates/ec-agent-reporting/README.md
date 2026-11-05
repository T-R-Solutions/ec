# ec-agent-reporting — Saída e resultados

**Estado:** módulo planejado; somente documentação e diretórios `src/` e `tests/`, sem código ou testes executáveis.

## Mapa de responsabilidades

[![Quais imports Ec são permitidos neste módulo? Setas representam dependências permitidas, não uma implementação existente. O texto abaixo mantém responsabilidades, limites, interfaces propostas e cenários que ainda precisam de verificação.](../../../../docs/assets/diagrams/agent-reporting.svg)](../../../../docs/assets/diagrams/agent-reporting.svg)

Setas representam dependências permitidas, não uma implementação existente. O texto abaixo mantém responsabilidades, limites, interfaces propostas e cenários que ainda precisam de verificação.

[Ampliar figura](../../../../docs/assets/diagrams/agent-reporting.svg) · [Fonte Mermaid](../../../../docs/assets/diagrams/agent-reporting.mmd)

## Escopo

Preparação e envio de chunks de log, resultados de testes, metadados de artefatos e recibos; controle explícito de envio e reconexão.

Não escolhe retenção do servidor nem promete entrega única ou durabilidade local; armazenamento local permanece pendente.

Fontes: [norma de produto](../../../../docs/EC-REGRAS-DE-DESIGN-E-PRODUTO.md); norma de produto §20; [contexto arquitetural](../../../../docs/architecture/context.md).

## Integração planejada

Dependências diretas permitidas entre crates Ec: **nenhum crate Ec**. Bibliotecas externas ainda não foram escolhidas. Nunca depender do crate de composição.

Publicação de saída identificada, consulta de confirmação e diagnóstico de envio incompleto. Tipos, assinaturas e serialização ainda precisam de especificação. A interface pública será mínima; adaptadores internos serão conectados pela composição, conforme o mapa de dependências.

## Onde implementar e verificar

- `src/`: código futuro e testes unitários próximos ao código, sob `#[cfg(test)]`.
- `tests/`: integração futura pela interface pública; testes com fronteira de processo explícita podem ter escopo ponta a ponta do componente.
- Cenários de integração entre crates do mesmo monólito pertencem ao `tests/` do crate de composição; entre Server e Agent pertencem a `application/integration/tests/`.

**Verificação futura:** reenvio, confirmação perdida, ordenação e indisponibilidade devem preservar a distinção entre produzido e recebido. Ainda não há testes executáveis ou cobertura.

## Referências

[Organização da aplicação](../../../../docs/architecture/application-layout.md) · [Política de testes](../../../../docs/development/testing.md) · [Autoridades](../../../../docs/constitution.md).

[Voltar ao componente](../../README.md). Mantenha este escopo junto de mudanças de responsabilidade, interface, dependências ou verificação; publique comandos somente após executá-los.
