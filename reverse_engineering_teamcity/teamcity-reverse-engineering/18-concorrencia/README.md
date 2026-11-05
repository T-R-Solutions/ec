# 18-concorrencia

Estado: investigação estática parcial. Catálogo por nomes: 683 conteúdos de classe; 6 com desassemblagem seletiva. Esses filtros se sobrepõem e não constituem uma classificação funcional comprovada.

## TC-008 — `BuildAgentImpl.registerOnBuildServer`

Usa tryAcquire para evitar registro concorrente, atualiza myRegistered e libera o semáforo em finally.

Início: Inicialização ou novo registro do agente. Fluxo: buildId → doRegisterOnBuildServer → estado registrado → ping.

Descriptors: `(Ljava/lang/String;)Z`. Evidência: `rea/teamcity-reverse-engineering/25-evidencias/javap/497cbdbca247d65864fe6e9e5d1d014ecaaea78bccf3d8dd2e25458b04c5def2.txt`; SHA-256 da classe `497cbdbca247d65864fe6e9e5d1d014ecaaea78bccf3d8dd2e25458b04c5def2`.

Limitação: Propriedades de liveness e comportamento em rede não foram testados.

Os 13 campos funcionais, componentes, chamadas, leituras/escritas, handlers, resultados e limitações estão em `features.json`. Candidatos completos estão em `20-classes-metodos/subsystem-catalog.json`. Lacuna: `U-18`.
