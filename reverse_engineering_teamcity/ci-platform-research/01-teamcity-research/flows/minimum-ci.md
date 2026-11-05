# Fluxo TeamCity de referência

1. Pedido REST chega ao recurso de fila (TC-013, STATIC_CONFIRMED); obtém identidade atual e chama triggerBuild.
2. Validação efetiva de toda configuração nesse caminho: UNKNOWN. TC-016 comprova diagnóstico na compilação DSL, em outro caminho.
3. Criação de promoção, execução e inserção na fila: ligação entre componentes INFERRED; TC-004 comprova apenas inserção SQL.
4. Fila e compatibilidade: TC-003/005 e D-QUEUE/D-MATCH. Ordem e compatibilidade são distintas.
5. Reserva atômica e prevenção de dupla atribuição: UNKNOWN. Não inferir pelo uso de AtomicReference.
6. Agente consulta comandos (TC-017); a correlação exata com a atribuição anterior permanece UNKNOWN.
7. Estágios verificam interrupção e status (TC-010/011); scripts executam ferramentas (D-STEPS).
8. Logs capturam eventos/saída (D-LOG); transporte e confirmação por chunk UNKNOWN.
9. Publicação de arquivos e espera na finalização (TC-012/D-ART).
10. Resultado comunicado ao servidor; confirmação transacional final UNKNOWN.

Pré-condições documentadas: configuração e agente compatível. Entradas: configuração, revisão,
parâmetros e identidade. Saídas: status, logs e artefatos. Exceções relevantes: configuração inválida,
ausência de agente, step com falha, interrupção, rede indisponível e falha de publicação.
As recuperações TeamCity não demonstradas serão especificadas independentemente, sem alegar equivalência.
