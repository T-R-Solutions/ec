# 06-build-queue

Estado: investigação estática parcial. Catálogo por nomes: 213 conteúdos de classe; 10 com desassemblagem seletiva. Esses filtros se sobrepõem e não constituem uma classificação funcional comprovada.

## TC-003 — `BuildQueueImpl.<init>`

A fila mantém AtomicReference<List<QueuedBuildEx>>, índice por ID e índice por build type; a estratégia inicial é BuildQueueDefaultOrdering.

Início: Construção do serviço. Fluxo: Lista inicialmente vazia e mapas concorrentes → referências publicadas.

Descriptors: `()V`. Evidência: `rea/teamcity-reverse-engineering/25-evidencias/javap/2ecfc5708f45da5ce59ac5d7bcfd9e31e178447675f0cc166b53bcd62352bd03.txt`; SHA-256 da classe `2ecfc5708f45da5ce59ac5d7bcfd9e31e178447675f0cc166b53bcd62352bd03`.

Limitação: AtomicReference não prova ausência de corridas nem atomicidade entre as três referências.

Os 13 campos funcionais, componentes, chamadas, leituras/escritas, handlers, resultados e limitações estão em `features.json`. Candidatos completos estão em `20-classes-metodos/subsystem-catalog.json`. Lacuna: `U-06`.
