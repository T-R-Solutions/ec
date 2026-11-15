# Tarefas

- T001: orquestrador inspeciona relatório, fontes e justificativas; pré-requisito da alteração.
- T002: orquestrador atualiza somente três registros da triagem, preservando mudanças existentes; depende T001.
- T003: revisor independente examina fluxos, escopo e diff; depende T002.
- T004: executar testes de regressão e Bandit real; integrar evidências; depende T002.
- T005: estabilizar artefatos, executar docs/scripts e entregar recibo ao validador independente para accept-task; depende T003/T004.
