# Jornadas da interface

Estado: diretório reservado; não há cenários executáveis.

## Mapa de responsabilidades

[![Onde cada tipo de teste deve ficar? Cada painel indica uma fronteira de teste e seu local. Não há relação de quantidade ou cobertura entre os painéis. Somente a suíte Python de governança está implementada.](../../../../../docs/assets/diagrams/test-boundaries.svg)](../../../../../docs/assets/diagrams/test-boundaries.svg)

Cada painel indica uma fronteira de teste e seu local. Não há relação de quantidade ou cobertura entre os painéis. Somente a suíte Python de governança está implementada.

[Ampliar figura](../../../../../docs/assets/diagrams/test-boundaries.svg) · [Fonte Mermaid](../../../../../docs/assets/diagrams/test-boundaries.mmd)

Reservado a fluxos de navegador cujo escopo seja a interface. Classificar explicitamente como interface com API simulada ou interface integrada com backend real. Ponta a ponta da plataforma com Agent pertence a `application/integration/tests/e2e/`; não reproduzir aqui o mesmo cenário.

Runner, fixtures, dependências de ambiente, descoberta e comandos permanecem pendentes. Não criar testes vazios para preencher este diretório.

[Política de testes](../../../../../docs/development/testing.md) · [Organização](../../../../../docs/architecture/application-layout.md).

[Voltar à organização dos testes](../../README.md)
