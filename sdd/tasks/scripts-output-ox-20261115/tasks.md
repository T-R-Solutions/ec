# Tarefas da adaptação

- [x] T001 [REQ-01, REQ-02, REQ-03, AC-01, AC-02, AC-03, D-01, D-02, D-03, R-01, R-02, R-03]: comparar Ox e Ec, mapear consumidores; orquestrador, leitura apenas.
- [x] T002 [REQ-01, REQ-02, REQ-03, AC-01, AC-02, AC-03]: adaptar módulos/configuração e testes; implementador, scripts/ e tools/quality/automation_config.py, exceto README; depende de T001.
- [x] T003 [AC-01, AC-02, AC-03]: documentar, revisar independentemente e testar; orquestrador dono de README/SDD; revisor independente, sem escrita de código; depende de T002.
- [ ] T004 [AC-01, AC-02, AC-03]: estabilizar evidências, executar superfícies docs/scripts e gate; orquestrador executor do runner, validador independente do código; depende de T003.

Retomada: corrigir falha concreta de teste/gate ou preparar capacidade ausente;
não reaproveitar recibos de entradas alteradas. A feature agregada permanece aberta.
