# Tarefas: [funcionalidade]

Inputs: spec e plan desta feature. Descobrir comandos reais; não executar placeholders.

- [ ] T001 [REQ-01, AC-01, D-01, R-01]: estabelecer oráculo e caso de falha; arquivos,
  responsável, entradas e comando a preencher; sem dependências.
- [ ] T002 [REQ-01, AC-01, D-01, R-01]: implementar mudança delimitada; depende de T001;
  concluir quando os cenários e verificações pertinentes passarem.
- [ ] T003 [AC-01]: registrar evidências e convergência; depende de T002; declarar
  resultados observados, limites e tarefas residuais.

## Encerramento no Ec

Antes de concluir tarefas, aplicar a [política canônica de verificação](../../docs/development/task-verification.md):
inspecionar as alterações e registrar evidências e lacunas sem acionar scripts.
Somente após pedido de commit, perguntar “Deseja executar o script automate.sh (que roda todos os outros scripts)?”,
com apenas `sim` ou `não`, e aguardar a escolha. Com `não`, seguir com os commits;
com `sim`, executar `bash scripts/automate.sh run` sem filtros e só criar commits
se todos os scripts terminarem sem erros. Não executar scripts avulsos.
Checks não executados não aprovam critérios da feature; registrar essa limitação.
