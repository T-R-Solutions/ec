# Convergência — aceite obrigatório de tarefas

Observação local em 14/11/2026. Análise: a política anterior era opcional após
commit; o runner já preservava resultados, entradas e encerramento. Planejamento:
reutilizar esses contratos e o schema 2, com controle explícito de etapas.
Implementação: gate Python e entrada Bash dedicada, sem modificar o runner
existente ou renovar evidências históricas. Revisão: comparação com requisitos,
caminhos internos, tipos, critérios, tarefas duplicadas e hashes atuais.

| Critério | Evidência observada | Estado do critério local |
| --- | --- | --- |
| AC-01 | Testes de etapa ausente, estado pendente e input obsoleto | APPROVED |
| AC-02 | Testes de falhas, skip, limpeza, ausência, mutações, escopo, duplicação e fixture válida | APPROVED |
| AC-03 | Entrada Bash rejeitou a tarefa sem evidência; política revisada; sintaxe aprovada | APPROVED |

`python3 -m unittest discover -s tools/governance/tests -p test_task_gate.py -v`:
9 testes passaram após a última alteração do código. Saída em
`.local/logs/2026-11-14/workflow-gate-focused.log`.
Primeiro run integrado: `bash scripts/automate.sh run --surface docs --surface scripts`,
ID `run-zqdTRXC7`, exit 0, 42 testes de governança e 192 de automação;
ShellCheck, Ruff, sintaxe e auditores passaram. Esse run precede a última
regressão e o fechamento documental: não aprova os arquivos finais.

Após estabilizar estes artefatos, executar novamente as superfícies docs/scripts
e chamar accept-task.sh com o recibo retornado. O resultado final fica nos logs
do runner e do gate, sem editar entradas após a execução e invalidar o recibo.

Limite residual: não foi configurado supervisor externo protegido, interceptação
de chat ou proteção de Git. Etapas declaradas não provam sua ordem temporal e
recibos graváveis pelo mesmo usuário não são autenticados. O pedido de garantia
absoluta depende desse controle externo, fora da capacidade deste checkout.

## Manutenção relacionada — 14/11/2026

A [tarefa de correções de governança](../../tasks/governance-corrections/spec.md)
registra escopo e critérios próprios para onze achados. Seus resultados não
renovam observações anteriores nem aprovam esta feature. Os critérios e pendências
agregados permanecem nos artefatos desta feature; reaceite exige evidências atuais.
