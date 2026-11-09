---
skill: commit-governance
skill_path: .codex/skills/commit-governance/SKILL.md
bridge_version: 1
---
# SDD bridge: commit-governance

Select only for explicitly requested creation, organization, verification or inspection
of Ec commits or version tags. Implementation work does not authorize Git operations.
Read the actual skill; identify its version by content hash in the active plan.

Inputs: authorized Git scope, applicable constitution/AGENTS, feature requirements,
actual working tree and validation results. Spec defines acceptance; plan records
classification and risks; tasks identify authorized paths and checks. Return observed
commits/tags, residual changes, checks and limitations to evidence/convergence.
Do not infer acceptance from a commit or publication from a local tag. Preserve the
five SDD states. This bridge neither installs skills nor authorizes effects.

## Escolha antes dos scripts

Após pedido explícito de commit, perguntar “Deseja executar o script automate.sh (que roda todos os outros scripts)?”,
com apenas `sim` e `não`; aguardar a resposta. `não`: criar os commits normalmente
sem scripts. `sim`: executar `bash scripts/automate.sh run` sem filtros e bloquear
os commits até todos os scripts terminarem sem erros. Não executar scripts avulsos
ou antecipar a execução durante outras tarefas. Consultar a
[política canônica](../../docs/development/task-verification.md).

## Integração Ec

Uso condicional: Somente em operações Git explicitamente solicitadas.

Pacote instalado em `.codex/skills/` com integridade conferida. Descoberta pela interface exige observação própria.
