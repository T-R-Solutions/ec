# Plano — aceite obrigatório

- D-01 / R-01 / REQ-01: reutilizar validador SDD schema 2 e exigir etapas vinculadas; evitar um segundo formato de critérios. Declarações não provam ordem temporal nem qualidade semântica.
- D-02 / R-02 / REQ-02: consumir recibos nativos e comparar o inventário atual completo; rejeitar resultados incompletos e mutações. Recibos locais não são assinaturas externas.
- D-03 / R-03 / REQ-03: expor accept-task.sh ao lado do runner e substituir política opcional. Hooks Git podem ser ignorados e não interceptam chat; não instalá-los como falsa garantia.

Primária aplicada: ponte autonomous-verification-engineering, bridge_version 1,
`.codex/skills/autonomous-verification-engineering/SKILL.md`, SHA-256
`569696eab8348b370ab2f73d7ffc688d9fc88483419f8179c3bf84789e2866c0`.
Contribuição: separar autorização, execução e aceite; testar rejeições e freshness.
Sem auxiliares ou delegação.

Superfícies finais: docs e scripts, pois a mudança envolve governança Python,
entrada Bash e documentação, sem alteração do runtime da aplicação. Testes focados
exercitam entradas adversas em diretórios temporários antes do runner final.
