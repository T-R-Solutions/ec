# Sequência parcial e estados

```mermaid
sequenceDiagram
  participant API as REST BuildQueueRequest
  participant DTO as Build DTO
  participant Q as BuildQueue
  participant Poll as ServerCommandsPoller
  participant Exec as ServerCommandsExecutor
  participant Stage as StagesExecutor
  participant Finish as FinishBuildOnAgentFStage
  API->>DTO: triggerBuild(user, serviceLocator, replacements)
  opt moveToTop verdadeiro
    API->>Q: moveTop(itemId)
  end
  Note over Q,Poll: Lacuna: inserção, distribuição e produtor concreto do comando
  Poll->>Poll: getNextCommand / headers ID e nome
  Poll->>Exec: executeAsync(command)
  Note over Exec,Stage: Lacuna: encadeamento dos handlers e montagem dos estágios
  Stage->>Stage: doStages / callRunStage
  Stage->>Stage: interrupt flag / estágio corrente
  Note over Stage,Finish: Lacuna: sequência completa de finalização
  Finish->>Finish: waitForPublishingFinish quando finalizado
  Finish->>Finish: buildFinished ou buildDetachedFromAgent
```

Estados comprovados localmente: flag de registro do agente, tentativa protegida por semáforo, interrupção atômica, estágio corrente, estado de término e detached. A máquina de estados global do build ainda não foi reconstruída. Não há transições globais confirmadas dinamicamente.
