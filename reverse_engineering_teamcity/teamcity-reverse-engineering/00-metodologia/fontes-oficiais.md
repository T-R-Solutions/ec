# Fontes primárias consultadas

Consulta em 2026-10-30. A documentação orienta a inspeção; não substitui o bytecode local nem prova versão de endpoint.

- [JetBrains — downloads anteriores](https://www.jetbrains.com/help/teamcity/previous-releases-downloads.html): referência de versões; identidade local deriva de build-version.jar e teamcity_build.properties.
- [Oracle — javap Java 25](https://docs.oracle.com/en/java/javase/25/docs/specs/man/javap.html): opções de desassemblagem e descriptors.
- [Oracle — formato de classe JVM 25](https://docs.oracle.com/javase/specs/jvms/se25/html/jvms-4.html): estrutura de classe, atributos e metadados.
- [ASM oficial](https://asm.ow2.io/): biblioteca para inspeção de bytecode; origem de distribuição Maven.
- [CFR oficial](https://www.benf.org/other/cfr/): descompilação de classes/JAR; versão usada 0.152.
- [JetBrains — packaging de plugins](https://plugins.jetbrains.com/docs/teamcity/plugins-packaging.html): modelo de empacotamento e contextos, confrontado com SpringPluginLoader.
- [JetBrains — BuildQueueApi](https://www.jetbrains.com/help/teamcity/rest/buildqueueapi.html): orientação sobre API de fila; endpoints desta entrega extraídos das annotations locais.
- [JetBrains — configuração de instalação do agente](https://www.jetbrains.com/help/teamcity/configure-agent-installation.html): configuração do agente, sem ler tokens da instalação.

Não se importou a descrição das versões mais recentes como implementação desta instalação. Respostas HTTP não foram observadas.
