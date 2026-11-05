# 16-persistencia

Estado: investigação estática parcial. Catálogo por nomes: 574 conteúdos de classe; 1 com desassemblagem seletiva. Esses filtros se sobrepõem e não constituem uma classificação funcional comprovada.

## TC-004 — `BuildQueueImpl.insertNewItems`

Persiste tuplas em build_queue com build_state_id, build_type_id, agent_restrictor_type_id, agent_restrictor_id e requestor via executeBatchForTuples.

Início: Atualização da fila. Fluxo: QueuedBuildEx → QueuedBuildTuple → lote SQL parametrizado.

Descriptors: `(Ljetbrains/buildServer/serverSide/db/DBFunctions;Ljava/util/List;)V`. Evidência: `rea/teamcity-reverse-engineering/25-evidencias/javap/2ecfc5708f45da5ce59ac5d7bcfd9e31e178447675f0cc166b53bcd62352bd03.txt`; SHA-256 da classe `2ecfc5708f45da5ce59ac5d7bcfd9e31e178447675f0cc166b53bcd62352bd03`.

Limitação: Isolamento transacional e comportamento de drivers não foram reproduzidos.

Os 13 campos funcionais, componentes, chamadas, leituras/escritas, handlers, resultados e limitações estão em `features.json`. Candidatos completos estão em `20-classes-metodos/subsystem-catalog.json`. Lacuna: `U-16`.
