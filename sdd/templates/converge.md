# Convergence: [feature]

| Criterion | Requirement / decision / risk / task | Change | Method and evidence | State and limits |
|---|---|---|---|---|
| AC-01 | REQ-01 / D-01 / R-01 / T001–T003 | To be filled in | Not executed | UNVERIFIED |

Separate structure, semantic review, execution and effectiveness. Record gaps
with an exact resumption action. Include observed conclusion, date and environment;
do not approve based on absence of errors.

For each observation, identify the checked implementation, oracle, configuration
and contract by path and observed version/hash. On resumption, compare inputs and
reevaluate affected criteria; a Git revision alone does not cover uncommitted changes.

## Encerramento no Ec

Antes de concluir tarefas, aplicar a [política canônica de verificação](../../docs/development/task-verification.md):
inspecionar as alterações e registrar evidências e lacunas sem acionar scripts.
Somente após pedido de commit, perguntar “Deseja executar o script automate.sh (que roda todos os outros scripts)?”,
com apenas `sim` ou `não`, e aguardar a escolha. Com `não`, seguir com os commits;
com `sim`, executar `bash scripts/automate.sh run` sem filtros e só criar commits
se todos os scripts terminarem sem erros. Não executar scripts avulsos.
Checks não executados não aprovam critérios da feature; registrar essa limitação.
