# 11-kotlin-dsl

Estado: investigação estática parcial. Catálogo por nomes: 1189 conteúdos de classe; 7 com desassemblagem seletiva. Esses filtros se sobrepõem e não constituem uma classificação funcional comprovada.

## TC-015 — `VersionedSettingsUpdaterImpl.scheduleConfigUpdatingTask`

Prepara callbacks de geração, sucesso e falha e chama ConfigFilesUpdater.scheduleUpdateConfigTask.

Início: Atualização de configurações versionadas. Fluxo: Resultados gerados → opções/callbacks → tarefa de atualização de configurações.

Descriptors: `(Ljava/util/List;Ljava/io/File;Ljetbrains/buildServer/serverSide/impl/versionedSettings/GenOptions;JLjetbrains/buildServer/serverSide/impl/versionedSettings/VersionedSettingsUpdateConfig;Ljetbrains/buildServer/vcs/VcsRootInstance;Ljetbrains/buildServer/serverSide/impl/versionedSettings/VersionedSettingsUpdaterImpl$AffectedProjectsGroup;Ljava/util/Set;Ljava/util/function/Supplier;Ljetbrains/buildServer/serverSide/impl/versionedSettings/VersionedSettingsUpdaterImpl$ConfigUpdateCallback;Ljetbrains/buildServer/util/Disposable;)Ljetbrains/buildServer/serverSide/PersistTask;`. Evidência: `rea/teamcity-reverse-engineering/25-evidencias/javap/2a44ae045d3cd6545e42de45d60506c578e477a987f5e46ab0e6941bb1ce92b5.txt`; SHA-256 da classe `2a44ae045d3cd6545e42de45d60506c578e477a987f5e46ab0e6941bb1ce92b5`.

Limitação: Não equiparar esta etapa à execução dos passos de build; compilação Kotlin e aplicação completa ainda parciais.

## TC-016 — `KotlinCompiler.run`

Invoca execAndOutputXml por reflexão, acrescenta -jvm-target 1.8, linguagem e diretório de saída; converte diagnóstico XML em VersionedSettingsException quando o resultado não é OK.

Início: Geração de configurações DSL. Fluxo: Arquivos Kotlin e argumentos → compilador refletido → classes/JAR ou erros estruturados.

Descriptors: `(Ljava/io/File;Ljava/util/List;Ljava/util/List;Ljava/io/File;Ljava/lang/String;)V`. Evidência: `rea/teamcity-reverse-engineering/25-evidencias/javap/f1750a413a10c0b2795290d1a93c480f2187931c27fbe0dbe80d1e6f0c09f296.txt`; SHA-256 da classe `f1750a413a10c0b2795290d1a93c480f2187931c27fbe0dbe80d1e6f0c09f296`.

Limitação: Esse target de bytecode não determina o JDK mínimo do servidor; avaliação e aplicação da DSL ainda não foram reproduzidas.

Os 13 campos funcionais, componentes, chamadas, leituras/escritas, handlers, resultados e limitações estão em `features.json`. Candidatos completos estão em `20-classes-metodos/subsystem-catalog.json`. Lacuna: `U-11`.
