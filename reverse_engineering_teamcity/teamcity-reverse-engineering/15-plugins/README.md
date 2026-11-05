# 15-plugins

Estado: investigação estática parcial. Catálogo por nomes: 506 conteúdos de classe; 4 com desassemblagem seletiva. Esses filtros se sobrepõem e não constituem uma classificação funcional comprovada.

## TC-014 — `SpringPluginLoader.pluginClassesLoaded`

Constrói contexto Spring do plugin, emite contextCreated, executa refresh e emite contextLoaded ou contextFailedToLoad.

Início: Carregamento de plugin. Fluxo: Descritores/configurações → contexto do plugin → refresh → callbacks.

Descriptors: `(Ljetbrains/buildServer/plugins/PluginData;)V`. Evidência: `rea/teamcity-reverse-engineering/25-evidencias/javap/62233beb2727bb236090938fd16bc7f6fa2aac41e7ce8532038f411e35af4f64.txt`; SHA-256 da classe `62233beb2727bb236090938fd16bc7f6fa2aac41e7ce8532038f411e35af4f64`.

Limitação: Classloader ativo e recarga real não foram observados.

Os 13 campos funcionais, componentes, chamadas, leituras/escritas, handlers, resultados e limitações estão em `features.json`. Candidatos completos estão em `20-classes-metodos/subsystem-catalog.json`. Lacuna: `U-15`.
