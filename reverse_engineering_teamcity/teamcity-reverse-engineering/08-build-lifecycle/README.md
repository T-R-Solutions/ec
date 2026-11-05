# 08-build-lifecycle

Estado: investigação estática parcial. Catálogo por nomes: 224 conteúdos de classe; 10 com desassemblagem seletiva. Esses filtros se sobrepõem e não constituem uma classificação funcional comprovada.

## TC-010 — `StagesExecutor.doStages`

Percorre estágios, consulta interrupção e para diante de status não bem-sucedido, exceto quando shouldRunAllStages permite continuar.

Início: Executor dos estágios. Fluxo: Coleção de estágios → callRunStage → status parcial → resultado ou INTERRUPTED.

Descriptors: `(Ljava/util/Collection;)Ljetbrains/buildServer/agent/BuildFinishedStatus;`. Evidência: `rea/teamcity-reverse-engineering/25-evidencias/javap/0e7639aa89af22ce3a022f56c78b21288520ebd2ae3932b7c2e4bdb2d36ae65c.txt`; SHA-256 da classe `0e7639aa89af22ce3a022f56c78b21288520ebd2ae3932b7c2e4bdb2d36ae65c`.

Limitação: A ordem concreta de todos os estágios depende da montagem da coleção.

## TC-012 — `FinishBuildOnAgentFStage.doFinishStage`

Aguarda publicação de artefatos para status finalizado e informa buildDetachedFromAgent ou buildFinished conforme motivo de interrupção e detached state.

Início: Finalização no agente. Fluxo: BuildFinishContext → espera de artefatos → notificação ao agente.

Descriptors: `(Ljetbrains/buildServer/agent/impl/BuildFinishContext;)V`. Evidência: `rea/teamcity-reverse-engineering/25-evidencias/javap/03acd496911a398228f630cd2ddac48c6d42ffcb5132d0d3df6f1c13415cf79e.txt`; SHA-256 da classe `03acd496911a398228f630cd2ddac48c6d42ffcb5132d0d3df6f1c13415cf79e`.

Limitação: Confirmação remota e persistência do resultado no servidor permanecem parciais.

Os 13 campos funcionais, componentes, chamadas, leituras/escritas, handlers, resultados e limitações estão em `features.json`. Candidatos completos estão em `20-classes-metodos/subsystem-catalog.json`. Lacuna: `U-08`.
