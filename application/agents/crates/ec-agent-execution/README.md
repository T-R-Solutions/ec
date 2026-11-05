# ec-agent-execution — Execução de etapas

**Estado:** módulo planejado; somente documentação e diretórios `src/` e `tests/`, sem código ou testes executáveis.

## Mapa de responsabilidades

[![Quais imports Ec são permitidos neste módulo? Setas representam dependências permitidas, não uma implementação existente. O texto abaixo mantém responsabilidades, limites, interfaces propostas e cenários que ainda precisam de verificação.](../../../../docs/assets/diagrams/agent-execution.svg)](../../../../docs/assets/diagrams/agent-execution.svg)

Setas representam dependências permitidas, não uma implementação existente. O texto abaixo mantém responsabilidades, limites, interfaces propostas e cenários que ainda precisam de verificação.

[Ampliar figura](../../../../docs/assets/diagrams/agent-execution.svg) · [Fonte Mermaid](../../../../docs/assets/diagrams/agent-execution.mmd)

## Escopo

Ciclo de vida de processos, saída drenada, deadlines, cancelamento e aplicação da política de isolamento e limites.

Não implementa scheduler global nem garante segurança apenas por executar em processo separado.

Fontes: [norma de produto](../../../../docs/EC-REGRAS-DE-DESIGN-E-PRODUTO.md); norma de produto §§20, 22, 23; [contexto arquitetural](../../../../docs/architecture/context.md).

## Integração planejada

Dependências diretas permitidas entre crates Ec: **ec-agent-workspace, ec-agent-reporting**. Bibliotecas externas ainda não foram escolhidas. Nunca depender do crate de composição.

Pedido de execução identificado por tentativa e revisão, sinais de controle e resultado local com causa de término. Tipos, assinaturas e serialização ainda precisam de especificação. A interface pública será mínima; adaptadores internos serão conectados pela composição, conforme o mapa de dependências.

## Onde implementar e verificar

- `src/`: código futuro e testes unitários próximos ao código, sob `#[cfg(test)]`.
- `tests/`: integração futura pela interface pública; testes com fronteira de processo explícita podem ter escopo ponta a ponta do componente.
- Cenários de integração entre crates do mesmo monólito pertencem ao `tests/` do crate de composição; entre Server e Agent pertencem a `application/integration/tests/`.

**Verificação futura:** exercitar saída volumosa, timeout, cancelamento e falha de preparação sem mascarar o estado final. Ainda não há testes executáveis ou cobertura.

## Referências

[Organização da aplicação](../../../../docs/architecture/application-layout.md) · [Política de testes](../../../../docs/development/testing.md) · [Autoridades](../../../../docs/constitution.md).

[Voltar ao componente](../../README.md). Mantenha este escopo junto de mudanças de responsabilidade, interface, dependências ou verificação; publique comandos somente após executá-los.
