# 19-algoritmos

Estado: investigação estática parcial. Catálogo por nomes: 312 conteúdos de classe; 14 com desassemblagem seletiva. Esses filtros se sobrepõem e não constituem uma classificação funcional comprovada.

## TC-007 — `PriorityAgentsFilter.filterAgents`

Quando encontra prioridade diferente de zero e o requestor não é OTHER, ordena candidatos pela prioridade em ordem decrescente; o caminho sem prioridades preserva uma cópia da lista.

Início: Seleção de candidatos. Fluxo: Agentes → pares agente/prioridade → comparator reversed → lista.

Descriptors: `(Ljetbrains/buildServer/serverSide/buildDistribution/AgentsFilterContext;)Ljetbrains/buildServer/serverSide/buildDistribution/AgentsFilterResult;`. Evidência: `rea/teamcity-reverse-engineering/25-evidencias/javap/4df2740340d45d58a0f70af72093485be3652fbb6114b875ea24a17b98826b9d.txt`; SHA-256 da classe `4df2740340d45d58a0f70af72093485be3652fbb6114b875ea24a17b98826b9d`.

Limitação: Custo local de ordenação O(A log A); não é a complexidade do scheduler completo.

Os 13 campos funcionais, componentes, chamadas, leituras/escritas, handlers, resultados e limitações estão em `features.json`. Candidatos completos estão em `20-classes-metodos/subsystem-catalog.json`. Lacuna: `U-19`.
