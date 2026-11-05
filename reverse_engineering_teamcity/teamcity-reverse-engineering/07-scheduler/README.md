# 07-scheduler

Estado: investigação estática parcial. Catálogo por nomes: 200 conteúdos de classe; 65 com desassemblagem seletiva. Esses filtros se sobrepõem e não constituem uma classificação funcional comprovada.

## TC-005 — `DefaultBuildDistributor.assignBuildsToAgents`

Cria estados de distribuição e chama BuildQueueDistributionProcessor.doDistribute; há verificação periódica da ordem topológica fora do modo de emulação.

Início: Processamento da fila. Fluxo: Entrada da fila → estados de distribuidores → processador → DistributionResult.

Descriptors: `(Ljetbrains/buildServer/serverSide/impl/buildDistribution/BuildDistributorInputEx;Ljava/util/Map;Ljetbrains/buildServer/serverSide/impl/buildDistribution/BuildStarter;Ljetbrains/buildServer/serverSide/impl/buildDistribution/BuildStarter;Ljetbrains/buildServer/serverSide/impl/buildDistribution/BuildStarter;Ljetbrains/buildServer/serverSide/impl/buildDistribution/SetWaitReasonCallback;Ljetbrains/buildServer/serverSide/impl/buildDistribution/CompositeBuildStarter;Ljetbrains/buildServer/serverSide/impl/buildDistribution/AgentlessBuildStarter;Ljetbrains/buildServer/serverSide/impl/buildDistribution/BuildDistributor$QueuedBuildOptimizer;Ljetbrains/buildServer/serverSide/impl/buildDistribution/BuildDistributor$QueuedBuildTerminator;Z)Ljetbrains/buildServer/serverSide/impl/buildDistribution/DistributionResult;`. Evidência: `rea/teamcity-reverse-engineering/25-evidencias/javap/7cc9af8aea2da8a7f509f77ecd38b25e3d3c850e1b431dfa6c15c4968d5b6d0f.txt`; SHA-256 da classe `7cc9af8aea2da8a7f509f77ecd38b25e3d3c850e1b431dfa6c15c4968d5b6d0f`.

Limitação: CFR produz trechos inválidos; javap é a referência. Não se afirma um algoritmo global de otimização.

## TC-006 — `PriorityAgentsFilter.getAgentPriority`

Lê teamcity.agent.priority, usa zero para ausência e limita o inteiro ao intervalo [-10000,10000].

Início: Filtro de agentes. Fluxo: Parâmetro disponível do agente → parseInt → clamp → prioridade.

Descriptors: `(Ljetbrains/buildServer/serverSide/SBuildAgent;)I`. Evidência: `rea/teamcity-reverse-engineering/25-evidencias/javap/4df2740340d45d58a0f70af72093485be3652fbb6114b875ea24a17b98826b9d.txt`; SHA-256 da classe `4df2740340d45d58a0f70af72093485be3652fbb6114b875ea24a17b98826b9d`.

Limitação: Não mede justiça, starvation ou precedência global dos demais filtros.

Os 13 campos funcionais, componentes, chamadas, leituras/escritas, handlers, resultados e limitações estão em `features.json`. Candidatos completos estão em `20-classes-metodos/subsystem-catalog.json`. Lacuna: `U-07`.
