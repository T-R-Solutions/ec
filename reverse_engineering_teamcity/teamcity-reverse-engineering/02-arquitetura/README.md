# Arquitetura observada e fronteiras

Os módulos foram observados no inventário e nos métodos referenciados em `claims.json`. As relações abaixo são estáticas; uma aresta entre processos não indica uma sessão observada.

```mermaid
flowchart TD
  webxml[web.xml] --> servlet[TeamCityDispatcherServlet.init]
  servlet --> startup[StartupProcessor]
  startup --> contexts[Criação dos contextos de aplicação]
  rest[BuildQueueRequest.queueNewBuild] --> dto[Build.triggerBuild]
  queue[BuildQueueImpl] --> sql[SQLRunnerEx / DBFunctions]
  sql --> tables[build_queue / build_queue_order]
  distributor[DefaultBuildDistributor] --> processor[BuildQueueDistributionProcessor]
  processor --> candidates[Distribuidores e filtros de candidatos]
  candidates --> priority[PriorityAgentsFilter]
  main[AgentMain] --> loader[URLClassLoader]
  loader --> main2[AgentMain2.main por reflexão]
  registration[BuildAgentImpl] --> protocols[CommunicationProtocol]
  poller[ServerCommandsPoller] --> executor[ServerCommandsExecutor]
  stages[StagesExecutor] --> stage[Estágio corrente]
  finish[FinishBuildOnAgentFStage] --> watcher[ArtifactsWatcher]
  finish --> notify[BuildAgentImpl.buildFinished]
  plugins[SpringPluginLoader] --> pluginctx[Contexto Spring do plugin]
```

`Build.triggerBuild → inserção na fila`, montagem dos estágios e o handler concreto servidor–agente ainda precisam de correlação completa. Essas ligações não foram desenhadas como chamadas confirmadas.
