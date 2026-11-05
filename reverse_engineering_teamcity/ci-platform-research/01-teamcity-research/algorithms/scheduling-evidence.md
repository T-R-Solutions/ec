# Limites do algoritmo conhecido

TC-005 prova delegação para processador de distribuição e verificação topológica em um caminho.
TC-006/007 provam clamp de prioridade do agente e ordenação condicional de candidatos.
Não provam FIFO global, ausência de starvation, balanceamento ótimo, leases ou fencing.
O scheduler novo escolhe seu objetivo e contrato em 03-rust-architecture/scheduling/scheduler.md.
