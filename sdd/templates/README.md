# Templates de tarefa e feature

No Ec, ambas as [rotas](../workflow.md) exigem spec, plan, tasks,
traceability, evidence schema 2 e workflow para o gate. Copie os arquivos `.md`
e os três exemplos JSON para `sdd/tasks/<id>/` ou `sdd/features/<id>/`, adaptando
IDs, caminhos, critérios, escopo e superfícies reais. `converge.md` registra
síntese e limites; checklists/ADRs são opcionais conforme risco.

Os exemplos JSON formam um registro estrutural no próprio diretório de templates:
critério UNVERIFIED, sem evidências inventadas, feature ilustrativa sem aprovação.
Para tarefa, adapte workflow para `kind: task` com relações existentes, cobertura,
justificativa e revisão. Veja [contrato completo](../evidence.md).

1. Defina critérios, cenários/oráculos, decisões/riscos, dependências e dono por arquivo.
2. Colete testes/revisão com inputs realmente observados; preencha evidence e etapas.
3. Estabilize todos os documentos/evidências e encerre escritores.
4. Execute runner nas superfícies justificadas; entregue recibo real ao validador.
5. Execute gate --task ou --feature; alteração posterior exige nova verificação.

Modelos não são evidência. Não executar placeholders nem atribuir APPROVED só
porque o JSON é válido. Prosa nova segue [AGENTS.md](../../AGENTS.md).

## Uso portátil/manual

Fora do contrato Ec, o método pode usar rastreabilidade Markdown e observações
manuais. Esse uso não basta para aceitar alterações neste repositório; o fluxo
obrigatório vem da [política canônica](../../docs/development/task-verification.md).
