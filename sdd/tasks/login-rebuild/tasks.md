# Tarefas e propriedade

- [x] T001 [REQ-01, AC-01, D-01, R-01]: orquestrador inspecionou diffs e contrato; TeamCity oficial inspecionado antes do acabamento.
- [x] T002 [REQ-02, AC-02, AC-03, D-03, R-03]: implement_login dono frontend fontes/testes/index; reconstrução e remoção autorizadas, preservando módulos de contrato.
- [x] T003 [REQ-03, AC-04, AC-05, AC-06, AC-07, D-02, R-02]: login_harness dono frontend_browser.py; orquestrador captura e mede; revisor independente examina UI, harness e escopo.
- [ ] T004 [AC-08]: orquestrador dono docs/SDD e executor runner final; registros estabilizados para execução. Validador independente executa gate sobre recibo final, cujo resultado é externo a estes inputs para evitar ciclo de hashes.

Coordenador é dono de docs/development/frontend.md, frontend/DESIGN.md, READMEs de entrada, sdd/project.md, registro de evidências visuais e sdd/tasks/login-rebuild. Implementadores não editam documentos comuns nem código um do outro. Sem trabalho concorrente após estabilização. Dependências: T002 após T001, T003 após implementação, T004 após revisão/correções e evidências. Bloqueios devem nomear condição de retomada.
