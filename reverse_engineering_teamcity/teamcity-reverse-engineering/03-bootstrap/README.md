# 03-bootstrap

Estado: investigação estática parcial. Catálogo por nomes: 260 conteúdos de classe; 5 com desassemblagem seletiva. Esses filtros se sobrepõem e não constituem uma classificação funcional comprovada.

## TC-001 — `TeamCityDispatcherServlet.init`

Servlet carrega o contexto inicial e constrói StartupProcessor com WebApplicationCreatorAndDestroyer e os padrões de configuração Spring.

Início: Contêiner servlet. Fluxo: Configuração web → StartupContext → StartupProcessor.

Descriptors: `(Ljavax/servlet/ServletConfig;)V`. Evidência: `rea/teamcity-reverse-engineering/25-evidencias/javap/a7d05c88b55fc89811bf8cafecd1a2c4eedfabec9359bd42bd22278266cc1ffc.txt`; SHA-256 da classe `a7d05c88b55fc89811bf8cafecd1a2c4eedfabec9359bd42bd22278266cc1ffc`.

Limitação: A ordem interna completa de criação dos beans depende de StartupProcessor e dos contextos.

## TC-019 — `StartupProcessor.processTeamCityLifecycle`

Coordena processamento do ciclo de inicialização por estágios, distinto do simples registro do servlet.

Início: Thread de inicialização. Fluxo: StartupContext e StartupStage → processamento de estágio → próximo estado.

Descriptors: `()V`. Evidência: `rea/teamcity-reverse-engineering/25-evidencias/javap/911831a92c7aa1cad27d64e4d10a974e1abd0a2214bd20e3dc2b6ec246126d28.txt`; SHA-256 da classe `911831a92c7aa1cad27d64e4d10a974e1abd0a2214bd20e3dc2b6ec246126d28`.

Limitação: Predicados de todos os estados e efeitos de migração não foram integralmente reconstruídos.

Os 13 campos funcionais, componentes, chamadas, leituras/escritas, handlers, resultados e limitações estão em `features.json`. Candidatos completos estão em `20-classes-metodos/subsystem-catalog.json`. Lacuna: `U-03`.
