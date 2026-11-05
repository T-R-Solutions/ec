# Engenharia reversa do TeamCity — checkpoint verificável

Alvo: `/opt/jvm/TeamCity`. Metadados locais: **2026.2-239120**, build **2026-09-29**. Edição/licença ativa: **UNKNOWN**. Estado geral: **em andamento**; esta entrega não constitui reconstrução exaustiva de todos os subsistemas.

O inventário cobre 9.521 arquivos físicos e 2.968 arquivos ZIP/JAR/WAR, incluindo arquivos aninhados e backups. Foram indexadas 822.094 ocorrências de classes, correspondentes a 412.168 conteúdos distintos por SHA-256, e 3.816.259 declarações de métodos. Bibliotecas duplicadas e classes multiversão não são contadas como uma única identidade de classloader. A disponibilidade no disco não prova carregamento em execução.

A instalação contém 27 `.BACKUP`, todos diferentes dos respectivos arquivos ativos. Não foi estabelecida sua equivalência à distribuição oficial. Os achados dizem respeito aos bytes instalados, com hashes individuais. Ver [comparação](01-inventario/backup-comparison.json).

## Como consultar

- [Estado e continuidade](analysis-state.json), [métricas](27-cobertura/metrics.json), [lacunas](unknowns.json).
- [Inventário com hashes, manifestos e arquivos aninhados](artifacts.json).
- [Achados classificados](claims.json) e [funcionalidades com os 13 campos solicitados](features.json).
- [Catálogo por subsistema](20-classes-metodos/subsystem-catalog.json) e [classes selecionadas com javap/CFR](20-classes-metodos/focus-index.json).
- [Índice de evidências](evidence-index.json), [chamadas](call-graph.json), [leituras/escritas de campos](data-flow.json) e [arestas de controle](22-call-graphs/control-flow.json).
- [546 declarações de rotas REST e 16 localizadores de sub-recursos](13-rest-api/endpoints.json).
- [Declarações de tabelas nos SQL embarcados](16-persistencia/table-declarations.json).

O índice completo está em `symbols.sqlite`; cada registro de classe contém JSON comprimido com zlib. `25-evidencias/symbols.jsonl.gz` contém os registros completos em JSON Lines comprimido com gzip. O esquema está em [symbols.json](symbols.json).

A partir da raiz do repositório:

```bash
python3 rea/tools/query.py BuildQueueImpl
python3 rea/tools/query.py PriorityAgentsFilter --full
python3 rea/tools/query.py ServerCommandsPoller --full
```

`--full` inclui chamadas com descriptor, opcode, ordinal da instrução, linha original disponível, campos acessados, variáveis locais, labels, desvios e handlers. O ordinal ASM não é um offset de bytecode: consulte a desassemblagem `javap` da classe selecionada para offsets JVM.

## Resultados funcionais

A fila mantém lista de builds e índices por ID/build type em referências atômicas. Há persistência parametrizada em `build_queue` e controle de ordem em `build_queue_order`. A existência dessas estruturas está comprovada; atomicidade global, isolamento e recuperação sob falha não foram demonstrados dinamicamente.

O distribuidor cria estados de distribuição e chama `BuildQueueDistributionProcessor`. O filtro de prioridade lê `teamcity.agent.priority`, limita valores a -10000…10000 e ordena candidatos em ordem decrescente quando aplicável. Dependências, limites de builds e coleta de mudanças aparecem como pré-condições. Isso não determina um algoritmo único de scheduling nem prova justiça global.

No agente, `AgentMain` monta classloader próprio e invoca `AgentMain2.main` por reflexão. O registro tenta protocolos resolvidos em sequência, com exclusão por semáforo. O polling consulta `/app/agents/v1/commands/next`, identifica comandos por headers e publica resultado/progresso/erro. Nenhum token ou sessão real foi coletado.

A execução de estágios verifica interrupção e status. A interrupção marca estado atômico e é encaminhada ao estágio atual. A finalização no agente espera publicação de artefatos quando aplicável e informa término ou detached state. A ligação completa entre transações do servidor, transporte e todos os passos do agente ainda tem lacunas.

O compilador de configurações Kotlin foi localizado dentro de `configs-dsl.zip`. O método inspecionado invoca `execAndOutputXml` por reflexão e converte falhas em erros estruturados. O target 1.8 encontrado nesse método é uma opção do compilador de DSL; não estabelece o JDK mínimo do servidor.

## Limites de cobertura

Os corpos de 340.669 métodos com código foram percorridos sintaticamente pelo ASM no recorte de namespaces próprios. Isso não significa revisão humana ou recuperação semântica de todos esses métodos. Corpos de terceiros foram omitidos; suas assinaturas permanecem indexadas.

A descompilação é seletiva e contém erros, stubs e trechos Java inválidos. `javap` e os bytes originais são a referência. Não há contagem defensável de métodos integralmente recuperados: as métricas correspondentes permanecem `null`.

As chamadas são referências simbólicas do bytecode. Alvos exatos presentes no recorte selecionado não equivalem a despacho virtual resolvido. Reflection, consumidores de eventos, resolução de beans, chamadas entre processos e efeitos persistentes exigem investigação adicional.

Não foram executados os 14 cenários dinâmicos previstos. Não houve acesso a banco, Data Directory ou escrita em instâncias reais. Nenhum componente TeamCity foi iniciado. Relatórios por subsistema distinguem achados confirmados de candidatos catalogados e lacunas.

Validação estática: [1.239 verificações passaram](24-testes/static-validation.json); [16 artefatos originais revalidados sem divergência](24-testes/original-artifacts-validation.json).
