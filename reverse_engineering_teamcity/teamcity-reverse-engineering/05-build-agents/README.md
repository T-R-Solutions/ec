# 05-build-agents

Estado: investigação estática parcial. Catálogo por nomes: 3554 conteúdos de classe; 16 com desassemblagem seletiva. Esses filtros se sobrepõem e não constituem uma classificação funcional comprovada.

## TC-002 — `AgentMain.main`

Cria URLClassLoader das bibliotecas do agente, troca o context classloader e invoca por reflexão AgentMain2.main.

Início: Launcher do agente. Fluxo: Bibliotecas em agent.home/lib ou caminho de depuração → classloader → main refletido.

Descriptors: `([Ljava/lang/String;)V`. Evidência: `rea/teamcity-reverse-engineering/25-evidencias/javap/fb6b5ae35a9e853677b47745198f34e16fe9ba19bf6a7765b23482a106b46540.txt`; SHA-256 da classe `fb6b5ae35a9e853677b47745198f34e16fe9ba19bf6a7765b23482a106b46540`.

Limitação: Nenhuma inicialização real de agente foi executada.

Os 13 campos funcionais, componentes, chamadas, leituras/escritas, handlers, resultados e limitações estão em `features.json`. Candidatos completos estão em `20-classes-metodos/subsystem-catalog.json`. Lacuna: `U-05`.
