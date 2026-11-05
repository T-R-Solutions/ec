# Estados comprovados e fronteiras

DOCUMENTED: fila contém builds disparados esperando execução (D-QUEUE).
STATIC_CONFIRMED: registro do agente altera flag local (TC-008); stages retornam status e podem
ser interrompidos (TC-010/011); finalização distingue término e detached state (TC-012).
UNKNOWN: máquina global completa, precedência cancelamento/conclusão, eleição de servidor,
linearização de alterações, expiração e recuperação de trabalho. Não converter flags locais em garantias distribuídas.
