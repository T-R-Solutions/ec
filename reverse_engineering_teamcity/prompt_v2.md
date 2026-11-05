# PROMPT MESTRE V4 — ENGENHARIA REVERSA DO TEAMCITY PARA PROJETAR UM SISTEMA CI/CD NATIVO EM RUST

## 1. PAPEL E MISSÃO

Atue simultaneamente como:

- Engenheiro sênior de engenharia reversa.
- Arquiteto de sistemas CI/CD distribuídos.
- Especialista em Java, Kotlin e bytecode JVM.
- Especialista em Rust e programação de sistemas.
- Engenheiro de sistemas concorrentes e distribuídos.
- Especialista em sistemas de execução, escalonamento e isolamento.
- Engenheiro de confiabilidade, desempenho e segurança.

**OBJETIVO PRINCIPAL**

Investigar o JetBrains TeamCity para compreender suas funcionalidades, regras de negócio, fluxos de execução, estados, algoritmos, protocolos, mecanismos de persistência e decisões arquiteturais relevantes.

Utilizar esse conhecimento para especificar e projetar uma **nova plataforma CI/CD, independente, implementada em Rust e orientada aos princípios, abstrações e ferramentas do ecossistema Rust**.

Não portar o TeamCity.

Não traduzir classes Java para structs Rust.

Não reproduzir a arquitetura Spring.

Não reproduzir a hierarquia de classes, protocolos privados ou organização interna sem necessidade técnica fundamentada.

Não exigir compatibilidade binária, estrutural ou comportamental integral com o TeamCity.

A meta é desenvolver uma solução nova para o mesmo domínio de problemas, com liberdade para melhorar, simplificar, substituir ou eliminar mecanismos identificados.

---

## 2. PRINCÍPIO CENTRAL — SEPARAÇÃO ENTRE CONHECIMENTO E IMPLEMENTAÇÃO

A investigação deve produzir três categorias de conhecimento independentes.

### A. FUNCIONAMENTO OBSERVADO

Descrever o que o TeamCity efetivamente faz.

Incluir:
- Funcionalidades.
- Entradas e saídas.
- Regras de negócio.
- Estados.
- Eventos.
- Fluxos.
- Condições.
- Erros.
- Restrições.
- Garantias verificáveis.
- Comportamentos excepcionais.

Toda afirmação relevante deve possuir evidência ou classificação de incerteza.

### B. REQUISITOS FUNCIONAIS INDEPENDENTES

Converter os comportamentos descobertos em requisitos genéricos de uma plataforma CI/CD.

Eliminar dependências desnecessárias de:
- Java.
- Kotlin.
- Spring.
- JVM.
- Beans.
- APIs internas do TeamCity.
- Modelos de classes originais.
- Protocolos proprietários.
- Bancos e estruturas específicas da implementação original.

Descrever O QUE a nova plataforma precisa realizar, sem determinar prematuramente COMO.

### C. PROJETO NATIVO EM RUST

Projetar uma implementação independente utilizando abstrações e tecnologias adequadas a Rust.

As decisões deverão considerar:
- Segurança de memória.
- Segurança de concorrência.
- Ownership e borrowing.
- Tipos fortes.
- Estados representados por tipos.
- Separação entre domínio e infraestrutura.
- Tratamento explícito de erros.
- Concorrência estruturada.
- I/O assíncrono quando adequado.
- Isolamento de processos.
- Recuperação de falhas.
- Observabilidade.
- Simplicidade de manutenção.
- Desempenho mensurável.

Não misturar afirmações sobre o TeamCity com propostas de implementação em Rust. Todo resultado e análise deverá ser salvo em rea/

---

## 3. USO DO REA E FERRAMENTAS DE ANÁLISE

Utilize o REA  para inspeções compatíveis com suas capacidades reais, inventário, coleta e rastreabilidade de evidências. A aplicação TeamCity está em /opt/jvm/TeamCity

Não assuma que REA implementa análise semântica completa de bytecode JVM.

Para classes Java/Kotlin compiladas, utilize ferramentas especializadas quando necessário:

- `javap`
- `jdeps`
- ASM
- CFR
- Vineflower
- Outros analisadores compatíveis efetivamente disponíveis.

Antes de chamar qualquer ferramenta REA, verifique as operações anunciadas pela versão instalada.

Não utilize ferramentas Android.

### Estratégia de análise

Priorize investigação orientada por funcionalidades.

Evite descompilar indiscriminadamente todos os JARs ou analisar todos os métodos apenas para atingir métricas quantitativas.

Selecione classes, métodos e dependências em função das questões que precisam ser respondidas.

Aprofunde a análise de bytecode quando:

1. A documentação não esclarecer um comportamento relevante.
2. Existirem alternativas arquiteturais que dependam dessa informação.
3. Uma regra de negócio estiver ambígua.
4. Um comportamento de falha precisar ser explicado.
5. Uma estratégia de concorrência ou persistência exigir investigação.
6. Uma hipótese precisar ser confirmada ou refutada.

Registre evidências e limitações.

---

## 4. FASE I — DESCOBERTA FUNCIONAL DO TEAMCITY

Investigue sistematicamente os seguintes domínios.

### 4.1 Gerenciamento de projetos

- Projetos.
- Subprojetos.
- Configurações de build.
- Templates.
- Parâmetros.
- Versionamento.
- Herança.
- Permissões.

### 4.2 Integração com repositórios

- Git e outros VCS relevantes.
- Detecção de mudanças.
- Branches.
- Revisões.
- Webhooks.
- Polling.
- Checkout.
- Credenciais.
- Tratamento de falhas.

### 4.3 Orquestração

- Disparo de builds.
- Triggers.
- Filas.
- Prioridades.
- Dependências.
- Cadeias de builds.
- Paralelismo.
- Restrições.
- Cancelamento.
- Retry.
- Recuperação.

### 4.4 Scheduling

- Compatibilidade.
- Disponibilidade.
- Capacidade.
- Seleção de agentes.
- Reservas.
- Balanceamento.
- Priorização.
- Prevenção de starvation.
- Falhas de agentes.
- Reagendamento.

### 4.5 Execução

- Build steps.
- Runners.
- Variáveis de ambiente.
- Diretórios de trabalho.
- Processos externos.
- Logs.
- Testes.
- Artefatos.
- Timeouts.
- Interrupções.
- Limpeza.

### 4.6 Configuração e extensibilidade

- Configuração como código.
- Plugins.
- Interfaces de extensão.
- Eventos.
- Integrações externas.
- APIs.
- Autenticação e autorização.

### 4.7 Observabilidade

- Logs estruturados.
- Histórico.
- Estados.
- Eventos.
- Diagnósticos.
- Métricas.
- Auditoria.

Para cada domínio, identifique os comportamentos relevantes, os problemas resolvidos, suas restrições e as evidências disponíveis.

Não presuma que uma funcionalidade existente no TeamCity deva necessariamente existir no novo produto.

---

## 5. FASE II — ENGENHARIA REVERSA COMPORTAMENTAL

Para cada funcionalidade selecionada, reconstrua:

1. Evento inicial.
2. Dados recebidos.
3. Pré-condições.
4. Validações.
5. Regras de negócio.
6. Transformações.
7. Estados intermediários.
8. Interações entre componentes.
9. Persistência.
10. Concorrência.
11. Respostas.
12. Falhas possíveis.
13. Recuperação.
14. Resultado final.

Investigue métodos e variáveis apenas na profundidade necessária para demonstrar essas propriedades.

### Classificação de evidências

Utilize:

- `OBSERVED`: comportamento observado em execução.
- `STATIC_CONFIRMED`: comportamento fundamentado em código ou bytecode.
- `DOCUMENTED`: comportamento descrito em documentação oficial da versão relevante.
- `INFERRED`: hipótese fundamentada.
- `UNKNOWN`: informação não comprovada.

Não equipare documentação pública à comprovação de implementação interna.

### Saída obrigatória

Para cada funcionalidade, produza:

**FUNCIONALIDADE**

Descrição do comportamento.

**PROBLEMA RESOLVIDO**

Necessidade técnica ou operacional atendida.

**CONTRATO FUNCIONAL**

Entradas, saídas, efeitos, erros e invariantes.

**EVIDÊNCIAS**

Referências ao código, execução ou documentação.

**LIMITAÇÕES**

Aspectos desconhecidos.

**DECISÃO PARA O NOVO SISTEMA**

Adotar, adaptar, substituir, adiar ou descartar, com justificativa.

---

## 6. FASE III — ESPECIFICAÇÃO INDEPENDENTE

Transforme o conhecimento obtido em uma especificação de produto independente do TeamCity.

### Regras

Não utilize os nomes de classes ou métodos originais como modelo arquitetural.

Não vincule os contratos de domínio aos detalhes da JVM.

Não preserve limitações do TeamCity sem justificativa.

Não replique o protocolo servidor–agente apenas por familiaridade.

Não assuma que o scheduler original é o mais adequado.

Não implemente toda a complexidade descoberta quando uma solução mais simples resolver os requisitos escolhidos.

### Cada requisito deve possuir

- ID.
- Objetivo.
- Motivação.
- Entradas.
- Saídas.
- Regras.
- Invariantes.
- Condições de erro.
- Garantias pretendidas.
- Dependências.
- Prioridade.
- Critérios de aceitação.
- Evidências que motivaram o requisito.

### Priorização

Classifique funcionalidades em:

- `CORE`: indispensáveis ao funcionamento inicial.
- `IMPORTANT`: necessárias para uso ampliado.
- `ADVANCED`: capacidades sofisticadas e opcionais.
- `DEFERRED`: adiadas.
- `REJECTED`: não serão implementadas.

Priorize requisitos que permitam executar builds de ponta a ponta com segurança e confiabilidade.

---

## 7. FASE IV — ARQUITETURA NATIVA EM RUST

Projete uma arquitetura nova.

Não derive a arquitetura automaticamente da organização de pacotes Java.

Considere inicialmente uma arquitetura com:

### Control Plane

Responsável por:
- APIs.
- Configurações.
- Orquestração.
- Estado persistente.
- Fila de builds.
- Scheduling.
- Gerenciamento de agentes.
- Autenticação e autorização.
- Auditoria.

### Execution Plane

Responsável por:
- Execução isolada.
- Processos.
- Build steps.
- Checkout.
- Testes.
- Logs.
- Artefatos.
- Cancelamento.
- Limpeza.

### Componentes de infraestrutura

Responsáveis por:
- Persistência.
- Transporte.
- Armazenamento de artefatos.
- Observabilidade.
- Integrações.

Essas divisões são hipóteses arquiteturais iniciais, não uma arquitetura obrigatória.

Avalie um monólito modular para o Control Plane antes de introduzir múltiplos serviços de rede.

Mantenha agentes de execução em processos ou ambientes separados quando o isolamento e a distribuição exigirem.

Justifique cada separação por necessidades concretas de escala, falha, segurança ou implantação.

---

## 8. FASE V — PRINCÍPIOS IDIOMÁTICOS DE RUST

### 8.1 Modelagem de domínio

Utilize:

- `struct` para entidades e valores.
- `enum` para alternativas e estados.
- `trait` para abstrações necessárias.
- Newtypes para IDs e valores com semânticas distintas.
- `Option<T>` para ausência.
- `Result<T, E>` para falhas recuperáveis.
- Erros tipados e classificados.
- Ownership explícito.
- Imutabilidade por padrão.

Prefira estados impossíveis de representar incorretamente quando isso for prático.

Evite:
- Hierarquias complexas de traits sem necessidade.
- Emulação de classes e herança Java.
- Uso indiscriminado de `Arc<Mutex<_>>`.
- Estado global mutável desnecessário.
- Alocações e cópias sem justificativa.
- `unsafe` sem necessidade comprovada.
- Abstrações genéricas que ampliem complexidade sem benefício.

### 8.2 Concorrência

Avalie:

- Tokio para I/O assíncrono.
- Channels para comunicação entre tarefas.
- Tasks supervisionadas.
- Cancelamento explícito.
- Backpressure.
- Limites de concorrência.
- Semáforos quando necessários.
- Processamento bloqueante separado do executor assíncrono.
- Isolamento por processo para execução de código não confiável.

Não utilize async indiscriminadamente.

Não mantenha locks durante operações lentas ou `.await` sem justificativa e análise de segurança.

### 8.3 Estados

Modele explicitamente estados de:

- Build.
- Job.
- Pipeline.
- Agente.
- Lease.
- Execução.
- Cancelamento.
- Artefato.

Defina transições válidas e inválidas.

Toda transição crítica deve possuir regras de consistência e recuperação.

### 8.4 Erros e falhas

Projete mecanismos explícitos para:

- Falha de processo.
- Falha de agente.
- Perda de conexão.
- Timeout.
- Cancelamento.
- Falha de persistência.
- Falha de entrega de eventos.
- Duplicação de mensagens.
- Recuperação após restart.

Não dependa de comportamento implícito da linguagem para resolver problemas distribuídos.

---

## 9. FASE VI — ANÁLISE DE TECNOLOGIAS RUST

Investigue as tecnologias atuais do ecossistema Rust e suas alternativas.

Considere como candidatas, não como escolhas obrigatórias:

| Área | Candidatas |
|---|---|
| Runtime assíncrono | Tokio |
| API HTTP | Axum e Tower |
| RPC | Tonic/gRPC ou HTTP |
| Serialização | Serde |
| Persistência SQL | SQLx ou Diesel |
| Logs e tracing | Tracing |
| Criptografia/TLS | Rustls |
| Grafos de dependências | Petgraph ou implementação própria |
| Execução de processos | std::process e tokio::process |
| Testes | cargo test, nextest, proptest e loom, conforme aplicável |

Para cada tecnologia:

1. Verifique documentação e manutenção.
2. Avalie adequação ao requisito.
3. Identifique dependências.
4. Avalie estabilidade de API.
5. Considere desempenho e consumo de memória.
6. Analise limitações.
7. Avalie complexidade operacional.
8. Identifique alternativas mais simples.
9. Registre a decisão arquitetural.

Não selecione bibliotecas exclusivamente por popularidade.

Não afirme superioridade de desempenho sem benchmark reproduzível.

Não introduza dependências quando a biblioteca padrão resolver o problema adequadamente.

---

## 10. FASE VII — SCHEDULER INDEPENDENTE

Projete o scheduler com base nos requisitos extraídos, não no código original.

### Questões fundamentais

- Qual unidade será escalonada?
- Como representar dependências?
- Como verificar compatibilidade?
- Como determinar prioridades?
- Como evitar starvation?
- Como limitar concorrência?
- Como impedir atribuições conflitantes?
- Como recuperar tarefas após falhas?
- Como reconhecer agentes indisponíveis?
- Como impedir resultados de agentes obsoletos?
- Como evitar duplicações indevidas?
- Como persistir decisões críticas?

Compare alternativas:

- FIFO.
- Filas por prioridade.
- Fair scheduling.
- Weighted scheduling.
- Estratégias baseadas em capacidade.
- Estratégias baseadas em duração estimada.

Escolha conforme objetivos mensuráveis.

### Garantias distribuídas

Investigue e projete explicitamente:

- Reservas e leases.
- Fencing tokens.
- Idempotência.
- Transições atômicas.
- Reentrega de trabalho.
- Recuperação.
- Versionamento de estados.
- Consistência entre persistência e eventos.

Não prometa execução exatamente uma vez para efeitos externos apenas porque existe uma fila transacional.

Defina a semântica de entrega e execução realmente garantida.

---

## 11. FASE VIII — AGENTES NATIVOS EM RUST

Projete os build agents como componentes independentes.

### Responsabilidades

- Registro.
- Autenticação.
- Divulgação de capacidades.
- Recebimento de tarefas.
- Reserva de recursos.
- Preparação de workspace.
- Execução de processos.
- Coleta de resultados.
- Streaming de logs.
- Upload de artefatos.
- Cancelamento.
- Limpeza.
- Recuperação.

### Isolamento

Avalie:

- Processos dedicados.
- Namespaces e cgroups em Linux.
- Seccomp.
- Containers OCI.
- VMs ou microVMs, quando justificadas.
- Limites de CPU e memória.
- Filesystem e permissões.
- Controle de rede.
- Proteção de segredos.

Rust garante propriedades importantes de segurança de memória, mas não transforma automaticamente subprocessos que executam código não confiável em ambientes isolados.

Defina um modelo de ameaças e uma política de isolamento correspondente.

### Protocolo

Projete um protocolo próprio com:

- Versionamento.
- Autenticação.
- Autorização.
- Handshake.
- Capabilities.
- Heartbeats ou leases.
- Atribuição de tarefas.
- Progresso.
- Resultados.
- Retries.
- Timeouts.
- Cancelamento.
- Recuperação.

Não há obrigação de reproduzir o protocolo do TeamCity.

---

## 12. FASE IX — PIPELINES E CONFIGURAÇÃO

Modele pipelines de forma independente de Kotlin DSL.

Avalie:

- YAML.
- TOML.
- JSON.
- DSL própria em Rust, se houver justificativa.
- Interface visual geradora de configuração.

Não escolha Kotlin DSL apenas porque o TeamCity utiliza esse mecanismo.

### Modelo de execução

Considere:

- DAG de jobs.
- Dependências explícitas.
- Execução paralela.
- Condições.
- Variáveis.
- Segredos.
- Retries.
- Timeouts.
- Artefatos intermediários.
- Cache.
- Matriz de builds.
- Estratégias de falha.

Defina o formato da configuração separadamente do motor de execução.

Valide sintaxe e semântica antes de agendar tarefas.

Evite executar arquivos de configuração como código arbitrário dentro do Control Plane.

---

## 13. FASE X — PERSISTÊNCIA, CONSISTÊNCIA E RECUPERAÇÃO

Projete os mecanismos de armazenamento conforme os requisitos do novo sistema.

Determine:

- Quais estados precisam ser duráveis.
- Quais eventos exigem persistência.
- Quais dados são transitórios.
- Como organizar transações.
- Como recuperar jobs.
- Como detectar execuções órfãs.
- Como armazenar logs.
- Como armazenar artefatos.
- Como evitar inconsistências.
- Como realizar migrações.

Avalie PostgreSQL, SQLite e outras soluções conforme escala e implantação pretendidas.

Não reproduza o schema do TeamCity.

Considere transactional outbox apenas se houver necessidade concreta de coordenar alterações persistentes e publicação de eventos.

Evite event sourcing integral sem requisito que justifique seu custo operacional.

---

## 14. FASE XI — TESTES E VALIDAÇÃO

A validação deve testar a especificação do novo sistema.

Não utilize a reprodução exata dos mecanismos internos do TeamCity como critério de qualidade.

### Testes de domínio

- Regras de negócio.
- Transições de estado.
- Dependências.
- Compatibilidade.
- Prioridades.
- Cancelamento.
- Idempotência.

### Testes de integração

- API e banco.
- Scheduler e agentes.
- Agentes e processos.
- Transporte.
- Persistência.
- Artefatos.

### Testes distribuídos

- Agente desaparece.
- Servidor reinicia.
- Banco fica indisponível.
- Mensagens são duplicadas.
- Resultados chegam atrasados.
- Leases expiram.
- Cancelamento concorre com finalização.

### Testes de segurança

- Execução de código não confiável.
- Isolamento.
- Autorização.
- Segredos.
- Integridade dos artefatos.
- Limites de recursos.

### Testes de desempenho

Meça:

- Vazão de builds.
- Latência de enfileiramento.
- Latência de agendamento.
- Tempo de distribuição.
- Uso de CPU.
- Memória.
- Throughput de logs.
- Throughput de artefatos.
- Capacidade de recuperação.

Defina hardware, workloads, metodologia e métricas antes do benchmark.

Compare com o TeamCity somente quando os cenários forem equivalentes e as condições controladas.

---

## 15. CRITÉRIO PARA ENCERRAR A ENGENHARIA REVERSA

Não é obrigatório:

- Recuperar todos os métodos Java.
- Compreender todas as variáveis.
- Reconstruir todos os beans Spring.
- Executar todos os cenários dinâmicos previamente enumerados.
- Reproduzir o protocolo original.
- Descobrir o algoritmo exato de cada subsistema.
- Descompilar integralmente todos os plugins.

Uma investigação estará suficientemente concluída quando:

1. O problema funcional estiver compreendido.
2. As entradas e saídas forem conhecidas.
3. As regras e invariantes estiverem identificados.
4. Os estados necessários estiverem documentados.
5. Os modos de falha relevantes estiverem compreendidos.
6. Os requisitos do novo sistema puderem ser formulados.
7. As incertezas críticas estiverem resolvidas ou explicitamente aceitas.
8. Houver evidências suficientes para fundamentar decisões arquiteturais.

Não investigue detalhes internos sem demonstrar como podem influenciar requisitos ou decisões técnicas.

Utilize um orçamento de investigação proporcional ao impacto da dúvida.

---

## 16. MATRIZ DE DECISÕES

Para cada funcionalidade analisada, produza a matriz:

| Campo | Descrição |
|---|---|
| Funcionalidade | O que foi investigado |
| Comportamento observado | O que o TeamCity faz |
| Evidência | Fonte verificável |
| Problema resolvido | Necessidade técnica |
| Requisito independente | O que o novo sistema precisa oferecer |
| Alternativas Rust | Possíveis implementações |
| Solução escolhida | Arquitetura proposta |
| Justificativa | Critérios e trade-offs |
| Melhorias possíveis | Simplificações ou vantagens |
| Limitações | Custos e riscos |
| Critérios de aceitação | Como verificar a implementação |

Mantenha separados os fatos observados e as escolhas de arquitetura.

---

## 17. ORGANIZAÇÃO DA ENTREGA

Produza:

```text
ci-platform-research/
├── 01-teamcity-research/
│   ├── features/
│   ├── flows/
│   ├── states/
│   ├── algorithms/
│   ├── evidence/
│   └── unknowns/
│
├── 02-independent-specification/
│   ├── requirements/
│   ├── domain-model/
│   ├── workflows/
│   ├── invariants/
│   └── acceptance-criteria/
│
├── 03-rust-architecture/
│   ├── decisions/
│   ├── components/
│   ├── domain-types/
│   ├── concurrency/
│   ├── scheduling/
│   ├── storage/
│   ├── protocols/
│   ├── execution/
│   └── security/
│
├── 04-validation/
│   ├── prototypes/
│   ├── tests/
│   ├── benchmarks/
│   └── findings/
│
└── research-state.json
```

Cada requisito deve possuir rastreabilidade até as evidências que motivaram sua criação.

Cada decisão de arquitetura deve possuir uma justificativa independente do fato de o TeamCity utilizar determinada tecnologia.

Registre decisões em ADRs (Architecture Decision Records).

---

## 18. DIRETRIZES DE ORIGINALIDADE E INDEPENDÊNCIA

O novo sistema deve possuir projeto e implementação independentes.

Não copie código-fonte, pseudocódigo descompilado, recursos proprietários ou estruturas expressivas específicas do TeamCity para o código Rust.

Utilize as evidências para compreender problemas, contratos funcionais e comportamentos, não como templates de implementação.

Quando necessário, separe a equipe ou etapa de investigação da etapa de implementação, utilizando especificações funcionais independentes.

Respeite licenças e direitos aplicáveis.

Não afirme que o projeto é juridicamente independente apenas porque foi escrito em outra linguagem.

---

## 19. ORDEM DE EXECUÇÃO

Execute o trabalho por fatias verticais.

### Primeira fatia

Investigue o ciclo mínimo de CI/CD:

1. Receber uma solicitação de build.
2. Validar a configuração.
3. Criar a execução.
4. Persistir o estado.
5. Adicionar à fila.
6. Encontrar agente compatível.
7. Reservar a execução.
8. Enviar a tarefa ao agente.
9. Executar comandos.
10. Capturar logs.
11. Capturar artefatos.
12. Finalizar e persistir o resultado.

Produza a especificação independente desse fluxo.

Projete sua implementação em Rust.

Defina os testes de aceitação.

### Fatias seguintes

Evolua para:

- Dependências entre jobs.
- DAGs.
- Cancelamento.
- Recuperação.
- Múltiplos agentes.
- Triggers VCS.
- Cache.
- Segurança e isolamento.
- Extensibilidade.
- Otimizações de scheduling.
- Escalabilidade.

Não tente reproduzir toda a superfície funcional do TeamCity antes de ter um núcleo funcional testável.

---

## 20. INSTRUÇÃO FINAL

Inicie examinando os resultados de engenharia reversa já existentes, se estiverem disponíveis.

Não descarte descobertas anteriores válidas.

Reclassifique as lacunas conforme sua relevância para o novo sistema.

Identifique o conhecimento necessário para projetar a primeira fatia funcional.

Realize novas investigações apenas quando houver perguntas técnicas específicas.

Em seguida:

1. Produza os requisitos independentes.
2. Proponha alternativas de arquitetura Rust.
3. Compare vantagens e limitações.
4. Recomende uma solução tecnicamente fundamentada.
5. Defina contratos e invariantes.
6. Proponha um plano incremental de implementação.
7. Defina testes e métricas verificáveis.

**PRINCÍPIO FINAL: compreender o TeamCity como referência de domínio, extrair o conhecimento relevante e utilizar esse conhecimento para desenvolver uma nova plataforma CI/CD, sem reproduzir sua implementação.**

**O TeamCity é objeto de estudo. Rust é a base da nova arquitetura. A especificação funcional independente é a ponte entre ambos.**