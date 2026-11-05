# 13-rest-api

Estado: investigação estática parcial. Catálogo por nomes: 1026 conteúdos de classe; 3 com desassemblagem seletiva. Esses filtros se sobrepõem e não constituem uma classificação funcional comprovada.

## TC-013 — `BuildQueueRequest.queueNewBuild`

POST na raiz de /app/rest/buildQueue verifica fila grande, obtém usuário atual, chama Build.triggerBuild e opcionalmente moveTop.

Início: Cliente HTTP. Fluxo: DTO Build → triggerBuild → SQueuedBuild → DTO de promoção.

Descriptors: `(Ljetbrains/buildServer/server/rest/model/build/Build;Ljava/lang/Boolean;Ljavax/servlet/http/HttpServletRequest;Ljavax/servlet/http/HttpServletResponse;)Ljetbrains/buildServer/server/rest/model/build/Build;`. Evidência: `rea/teamcity-reverse-engineering/25-evidencias/javap/f7c1c1852f43e8b44e4454590253619485bec166db43f75d564c798bd862feb0.txt`; SHA-256 da classe `f7c1c1852f43e8b44e4454590253619485bec166db43f75d564c798bd862feb0`.

Limitação: Permissões delegadas, todos os erros e códigos HTTP não estão exaustivamente rastreados.

Os 13 campos funcionais, componentes, chamadas, leituras/escritas, handlers, resultados e limitações estão em `features.json`. Candidatos completos estão em `20-classes-metodos/subsystem-catalog.json`. Lacuna: `U-13`.
