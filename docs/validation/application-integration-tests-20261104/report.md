# Testes entre componentes dentro da aplicação

Data: 2026-11-04. Rota local documental. Pedido: mover os testes da raiz para `application/integration/tests/`.

A pasta foi movida com suas três entradas documentais, preservando `integration/` e `e2e/`. Os links de entrada e saída, a árvore, a política de testes, a ADR-002, os READMEs consumidores, o contexto SDD e a fixture de governança foram ajustados. Testes internos dos módulos mantêm sua localização. A palavra integration no caminho agrupa os testes entre componentes; e2e continua identificando jornadas completas, com classificação própria.

As versões anteriores estão em [previous.zip](previous.zip) e o escopo em [scope.json](scope.json). As observações anteriores permanecem intactas, inclusive os registros dos caminhos antigos. A evidência vigente passa a apontar para esta execução depois das verificações.

| Critério | Estado | Método e evidência |
| --- | --- | --- |
| Três READMEs preservados no destino e pasta tests ausente da raiz | APPROVED | Inspeção da árvore e conferência em [verify.py](verify.py) |
| Referências vigentes e fixture atualizadas | APPROVED | Revisão dos caminhos e classificação; verificação de links e 24 controles de governança; [checks.json](checks.json) e logs locais |
| Governança e evidências atuais | APPROVED | Comparação dos inputs anteriores restrita ao escopo, auditoria com instalação exigida, preflight e gates SDD em [gates.json](gates.json) |
| Runtime, cobertura e renderização visual | UNVERIFIED | Não há testes executáveis de produto nesta pasta; nenhuma validação visual foi realizada |

Revisão dos critérios existentes: AC-01/05 pelo auditor e preflight; AC-02 pela revisão documental que conserva autoridades e contratos; AC-03 por links e estrutura; AC-04 pelos testes com fixture ajustada; AC-06 por preservação da observação histórica de limpeza e comparação dos inputs sem repetir remoções. A mudança afeta somente localização e navegação, sem implementar contratos de produto.

Skill aplicada: `.codex/skills/readme-lifecycle-engineering/SKILL.md`, SHA-256 `4a68d48bfa52160ab3cacbe7f04da1d7faadd6c76cde126617cbd945969473c9`, para manutenção das entradas e links. Não houve delegação, instalação, commit ou publicação. Novas mudanças de caminhos ou harness exigem rever descoberta e referências.
