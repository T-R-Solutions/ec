# Fluxo de dados reconstruído parcialmente

As funcionalidades em `../features.json` descrevem transformações observadas: DTO Build para trigger de promoção, agente/prioridade para lista ordenada, AgentDetails para registro, resposta HTTP para Command e parâmetros Kotlin para compilação/diagnósticos.

A sequência entre métodos é apoiada por referências de bytecode. Não foi resolvido um grafo interprocedural de def-use nem o formato completo dos dados em todos os transportes. `../data-flow.json` contém apenas acessos a campos; seus limites são explícitos. Relações entre DTO, execução remota e persistência não devem ser presumidas a partir de tipos semelhantes.
