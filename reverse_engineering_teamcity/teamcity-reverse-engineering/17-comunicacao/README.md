# 17-comunicacao

Estado: investigação estática parcial. Catálogo por nomes: 171 conteúdos de classe; 5 com desassemblagem seletiva. Esses filtros se sobrepõem e não constituem uma classificação funcional comprovada.

## TC-009 — `BuildAgentImpl.doRegisterOnBuildServer`

Resolve protocolos, tenta registerOnServer em sequência e prossegue para o próximo em ProtocolDoesNotWorkException; no sucesso atualiza configurações e ID.

Início: Registro do agente. Fluxo: AgentDetails → protocolos → AgentRegistrationDetails → configurações/ID.

Descriptors: `(Ljava/lang/String;)Z`. Evidência: `rea/teamcity-reverse-engineering/25-evidencias/javap/497cbdbca247d65864fe6e9e5d1d014ecaaea78bccf3d8dd2e25458b04c5def2.txt`; SHA-256 da classe `497cbdbca247d65864fe6e9e5d1d014ecaaea78bccf3d8dd2e25458b04c5def2`.

Limitação: Formatos de wire, timeouts e autenticação de cada protocolo exigem análise adicional.

## TC-017 — `ServerCommandsPoller.getNextCommand`

Consulta /app/agents/v1/commands/next e lê TeamCity-AgentCommandName e TeamCity-AgentCommandId da resposta.

Início: Loop de polling do agente. Fluxo: HTTP GET → status/headers → objeto Command.

Descriptors: `()Ljetbrains/buildServer/agent/impl/serverCommunication/ServerCommandsPoller$Command;`. Evidência: `rea/teamcity-reverse-engineering/25-evidencias/javap/f2a3bf0cae8212ded718618d2b1e69b217a0972473000f79b9f452f82b8695db.txt`; SHA-256 da classe `f2a3bf0cae8212ded718618d2b1e69b217a0972473000f79b9f452f82b8695db`.

Limitação: Servidor consumidor e todas as versões do protocolo precisam de correlação adicional.

## TC-018 — `ServerCommandsPoller.run`

Inicia intervalo configurável com padrão 5000 ms; após processar comando atribui o padrão de 500 ms; captura falhas e continua enquanto myStopped for falso.

Início: Thread de polling. Fluxo: getNextCommand → processOneCommand → espera → próxima iteração.

Descriptors: `()V`. Evidência: `rea/teamcity-reverse-engineering/25-evidencias/javap/f2a3bf0cae8212ded718618d2b1e69b217a0972473000f79b9f452f82b8695db.txt`; SHA-256 da classe `f2a3bf0cae8212ded718618d2b1e69b217a0972473000f79b9f452f82b8695db`.

Limitação: O intervalo não é redefinido para 5000 dentro do loop observado. Sem reprodução dinâmica de timing.

Os 13 campos funcionais, componentes, chamadas, leituras/escritas, handlers, resultados e limitações estão em `features.json`. Candidatos completos estão em `20-classes-metodos/subsystem-catalog.json`. Lacuna: `U-17`.
