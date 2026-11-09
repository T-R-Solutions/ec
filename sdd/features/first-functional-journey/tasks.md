# Tarefas

- [ ] T001 [REQ-01, AC-01, D-01, R-01]: Parser, modelo, CLI e nove testes locais implementados; Referências projeto/repository/commit resolvidas contra cadastro PostgreSQL admitido de Git real por CLI. Campos de segredo continuam rejeitados; suporte de resolução de segredos pendente e AC-01 sem aceite.
- [x] T002 [REQ-02, AC-02, D-02, R-02]: Migration 001, publicação, recibos e recuperação PostgreSQL/HTTP demonstrados no harness publication_http.py.
- [x] T003 [REQ-03, AC-03, D-03, R-03]: Sessões opacas e papéis por projeto na API HTTP v1 demonstrados, inclusive negações sem efeitos.
- [x] T004 [REQ-04, AC-04, AC-07, D-04, R-04]: ec-server-orchestration implementa migration 002, submissão/consulta/recibos, sessão Agent autenticada separada, reserva de slot/trabalho, leases, fencing, reconciliação e cancelamento serializado. Harness orchestration_http.py demonstra PostgreSQL real, conexões independentes, reinícios e negações. AC-07 recebe somente contribuição parcial (publicação → submissão → resultado Server); não aprova Agent, artefato, frontend nem jornada completa.

Entrega T004: contrato na spec antes de código; migration aditiva após 001; composição HTTP/CLI em ec-server-app; snapshot autorizado por interface pública de projects; oráculos SQL e falhas antes/depois de commit. Condição de conclusão: AC-04 demonstrado no harness, regressão T002/T003 e checks Rust/governança executados, limites registrados em converge/evidence. Não há tarefa de Agent ou frontend implicitamente concluída.
- [x] T005 [REQ-05, AC-05, D-05, R-05]: Agent real, checkout, processos, logs, artefatos e recuperação.
- [x] T006 [REQ-06, AC-06, D-06, R-06]: Vue 3/TypeScript/Pinia real, três projeções, publicação/recibos, execuções/saída e agentes; Firefox do sistema integrado a Server/PostgreSQL/Agent/Git. Evidências e limites em docs/validation/frontend-20261105/report.md.
- [ ] T007 [REQ-04, AC-07, D-04, R-04]: harness ponta a ponta isolado com repositório controlado e todas as rejeições da spec.

## Backlog A–E posterior ao marco

A: consolidar fundamentos, ambientes reproduzíveis e decisões ainda pendentes; B: concluir núcleo seguro e o marco desta feature. C: completar edição e gestão, hierarquia, usuários/equipes e políticas. D: ampliar DAGs, triggers, templates, testes e entregas. E: plugins, operação e escala mediante medições e controles demonstrados. Nenhuma fase é declarada concluída.

T005 implementa workspace Agent, migration 003, results, rotas de saída, journal/spool, supervisão e harness Agent real. Condição de aceite: ensaio final Agent/Server/PostgreSQL, regressões T002–T004 e checks documentados. Recursos com credenciais externas, UI e jornada completa continuam T001/T006/T007 pendentes.
