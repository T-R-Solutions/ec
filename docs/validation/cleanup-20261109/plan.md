# Escopo e plano da limpeza — 09/11/2026

## Requisitos e critérios

- L01: excluir somente conteúdo com finalidade, substituição/desuso, consumidores e consequências conhecidos; sem mudanças funcionais.
- L02: preservar logs, auditorias, pesquisa, baselines de recuperação, propostas e alterações preexistentes; não operar serviços ou dados.
- L03: inventariar antes de excluir, copiar bytes/metadados e comparar precondições; restauração individual sem overwrite.
- L04: reconstruir acionamento direto/indireto de scripts e atualizar guias afetados sem reescrever registros históricos.
- L05: somente inspeção estática/manual; sem testes, scripts de verificação, downloads, commits ou publicação.

## Método e decisões

Rota completa proporcional por abrangência e necessidade de recuperação; este
plano, o inventário e o relatório concentram escopo, decisões, tarefas e evidência.
As regras de produto não mudam. Autoridades lidas: AGENTS, sdd/project,
docs/constitution, architecture/context, workflow, task-verification e ADR-001/002.

Primária: `software-design`, ponte 1 em `sdd/chain/software-design.md`, pacote
`.codex/skills/software-design/SKILL.md`: rastrear responsabilidades, contratos,
duplicação e consumidores antes de retirar conteúdo. Auxiliar:
`workspace-integrity-engineering`, ponte 1 em
`sdd/chain/workspace-integrity-engineering.md`, pacote
`.codex/skills/workspace-integrity-engineering/SKILL.md`: baseline por bytes,
precondições de hash/inode/modo/mtime, cópias e diário. Hashes observados ficam em
`inputs.json` deste registro. Ambas lidas/aplicadas nesses limites; não foram
executadas as avaliações automatizadas sugeridas pelas skills.

Decisões: manter interfaces operacionais e recursos opcionais; retirar apenas
G01/G02/G03 definidos no [inventário](inventory.json). Comparar arquivos idênticos
por papel, além do hash: toolchains, licenças, marcadores vazios e baselines têm
contratos distintos. A cópia avulsa `technical-documentation/` tem diferenças e
recursos exclusivos; preservar por incerteza, sem sincronizar instalação/perfil.

Riscos: consumidores fora do checkout não são demonstráveis; recusada remoção de
scripts com interface ou contrato vigente mesmo quando não pertencem ao padrão.
Recriação de caches ou edição concorrente conflita com precondições e deve ser
preservada. Recuperação protegida em `.local/cleanup-20261109/`, excluída de toda
seleção desta tarefa e fora das raízes funcionais/snapshots de scanners.

## Tarefas e observação

1. Inventário inicial de arquivos/links e SHA-256, sem seguir symlinks; capturado em `.local/cleanup-20261109/baseline.jsonl`.
2. Reconstrução de consumidores e classificação: scripts, aplicação, testes, manifests, configurações, governança, documentação e artefatos locais; detalhar limites no relatório.
3. Preparação antes das exclusões: 32 candidatos e 13 arquivos a editar (12 documentos e .gitignore) copiados para `previous/`; metadados e grupos em `inventory.json`; operações de backup no diário.
4. Exclusão após precondições e atualização documental; cada ação recebe registro incremental.
5. Inspeção do conjunto alterado e comparação dos arquivos preexistentes, com atenção a logs/históricos; resultados em `inspection.json` e relatório. Nenhuma execução da suíte aprova critérios SDD.
