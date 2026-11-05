# Ec — Reavaliação crítica das skills do Ox para a arquitetura de dois monólitos

> **Tipo:** parecer técnico complementar ao `config.md` anteriormente descrito; este arquivo não altera, substitui nem comprova a instalação de qualquer configuração.  
> **Data:** 03/11/2026. **Revisão:** 2 — comunicação confiável e escala. **Status:** recomendação de arquitetura e governança; implementação não verificada.

## 1. Síntese executiva

Recomenda-se **66 skills distintas no catálogo planejado**: 40 próprias do Ec e 26 oriundas do Ox (20 de adoção geral, 6 condicionais). Quatro skills do Ox permanecem excluídas. Em relação à decisão anterior comunicada de 65, a alteração líquida é **+1**, pela readmissão condicional de `agent-runtime-engineering`. `distributed-systems` já estava no conjunto anterior de 25 adotadas do Ox: sua prioridade muda, mas não adiciona outra entrada.

**Correção técnica importante:** o Ec Agent é um executor de jobs de CI/CD, não necessariamente um agente de IA. `agent-runtime-engineering` do Ox descreve supervisão de agentes CLI/ACP e sessões de IA. Seus padrões de processos, eventos, cancelamento e recuperação são úteis, mas precisam ser adaptados ao domínio do Ec, evitando duplicação com `cicd-agent-runtime`, `cicd-execution-runtime` e `ec-agent-control-protocol`.

**Arquitetura normativa vigente:** Ec Server, monólito modular Rust (control plane), e Ec Agent, monólito modular Rust executado independentemente; frontend Vue 3 + TypeScript separado. PostgreSQL armazena fila e estado operacional; comunicação por HTTP versionado. **Proposta revisada, ainda não aprovada por ADR:** especializar esse transporte em **gRPC bidirecional sobre HTTP/2, com Protobuf**, usar **SQLite privado e durável em cada Agent** para outbox/checkpoints e manter PostgreSQL como autoridade global; sem Kafka ou outro broker externo inicialmente. O YAML continua sendo a única fonte canônica da configuração declarativa desejada, e não substitui armazenamento operacional.

## 2. Escopo e fontes efetivamente examinadas

- `ox_resume.zip`: inventário real de 30 `SKILL.md` do Ox; entradas das skills relevantes e materiais de governança.
- `ec_skills.zip`: inventário real de 40 `SKILL.md` do Ec; entradas das skills relacionadas a agentes, processos, protocolo, scheduler, logs e plugins.
- `resume.md`: relatório explicativo da governança do Ox e escopos de suas especializações.
- `EC-REGRAS-DE-DESIGN-E-PRODUTO.md`: normas do Ec sobre arquitetura, produto, YAML, paridade entre modos, segurança e fases.
- `EC-REGRAS-CRIACAO-DE-TELAS-E-IMAGENS.md`: normas aplicáveis a UI, fluxos de agentes, logs e configuração.

**Limite de comparação:** o `config.md` anterior não está presente entre os arquivos disponibilizados nesta etapa. O estado inicial 40 + 19 + 6 + 5 foi tomado do resultado relatado na conversa, e não auditado linha a linha no arquivo anterior. Portanto, a matriz abaixo é uma **nova decisão proposta**, não uma afirmação de diff textual exato.

## 3. Arquitetura-alvo e fronteiras

```text
                   Ec Frontend (Vue 3 + TypeScript)
                                |
                        HTTP/API versionada
                                |
                   Ec Server (monólito Rust)
  auth / RBAC / projetos / YAML / scheduler / fila / API / plugins
                                |
         PostgreSQL (estado operacional; projeções derivadas)
                                |
     gRPC bidirecional / HTTP-2 / Protobuf / TLS ou mTLS
                [proposta sujeita a ADR]
                                |
          Ec Agent(s) (monólito Rust por instância)
  conexão / capacidades / slots / executor / processos / isolamento
                       logs / testes / artefatos
                SQLite local (outbox durável)
```

**Nota sobre desenho:** são dois **tipos de programa** em arquitetura monolítica modular, não necessariamente duas instâncias totais. Um servidor pode administrar múltiplos processos Ec Agent em hosts independentes. Essa topologia torna distribuídas as garantias operacionais, mesmo que cada binário seja monolítico.

**Autoridades e invariantes recomendados:**

1. **Server:** identidade e autorização central, configuração YAML publicada, fila durável, alocação/scheduler, tentativas e transições persistidas.
2. **Agent:** execução local de processos, isolamento, aplicação de limites, coleta da saída e comunicação; nunca decidir unilateralmente sucesso durável do job.
3. **Contrato entre ambos:** identidade de job/attempt, sessão, heartbeat, epoch, lease, fencing token, idempotência, versões e recibos explícitos. **Na proposta gRPC:** Protobuf versionado, streaming com backpressure, confirmação de recebimento separada da conclusão do job e compatibilidade N/N−1 testada.
4. **Confiabilidade:** PostgreSQL com inbox/outbox transacionais no Server; SQLite com outbox privada e durável no Agent, gravada antes da transmissão; ACK somente após commit durável no destino. `event_id` único, reenvio *at least once*, deduplicação com restrição de unicidade, sequência por stream/tentativa, reconciliação pós-restart e tratamento explícito de armazenamento esgotado. Término de processo não implica confirmação persistida; conexão perdida não implica interrupção do trabalho; cancelar não implica término efetivo. Não prometer `exactly once` para efeitos externos.
5. **Segurança:** processos distintos não constituem sandbox por si só. Isolamento de workloads externos exige controles demonstrados.
6. **YAML:** todo estado desejado configurável converge para YAML versionado; estados operacionais e credenciais não são persistidos como plaintext de configuração.

## 4. Decisão individual sobre as 30 skills do Ox

| Skill do Ox | Decisão revisada | Justificativa / fronteira |
|---|---|---|
| `agent-runtime-engineering` | **Condicional** | CONDICIONAL — aproveitar supervisão de processos, eventos, cancelamento e retomada; excluir semântica de sessões LLM/ACP quando não aplicável. Complementa cicd-agent-runtime e cicd-execution-runtime. |
| `agent-usage-accounting` | **Excluir** | NÃO ADOTAR — ledger de tokens, cache e custo de modelos, não contabilidade de CPU, duração ou quota de CI/CD. Reavaliar somente se o Ec incorporar agentes de IA cobrados por tokens. |
| `ai-engineering` | **Condicional** | CONDICIONAL — aplicável apenas a recursos de IA explicitamente aprovados; não é requisito do scheduler ou agent de CI/CD. |
| `api-testing` | **Adotar** | ADOTAR — contratos HTTP e, **se a ADR for aprovada**, gRPC/Protobuf versionados; compatibilidade N/N−1, autenticação, ACK, streams interrompidos e fluxos negativos. |
| `application-security` | **Adotar** | ADOTAR — isolamento de workloads, autorização, credenciais, plugins, APIs e fronteiras de confiança. |
| `autonomous-verification-engineering` | **Adotar** | ADOTAR — gates e evidências de execução/aceitação, com oráculos independentes para SDD e CI/CD. |
| `code-context-engineering` | **Excluir** | NÃO ADOTAR — indexação AST/LSP de símbolos é motor de contexto para IA/IDE, não exigência para checkout/build; reconsiderar em análise de impacto de código. |
| `code-knowledge-graph-engineering` | **Excluir** | NÃO ADOTAR — grafo semântico de código não é o DAG de pipeline nem a árvore de projetos. |
| `commit-governance` | **Condicional** | CONDICIONAL — útil no desenvolvimento do Ec quando commits/tags forem solicitados, não implica commits automáticos. |
| `database-engineering` | **Adotar** | ADOTAR — PostgreSQL global (fila, estados, inbox/outbox, unicidade e transações) e SQLite local privado por Agent (WAL, durabilidade crítica e recuperação), com migrações e limites operacionais. |
| `design-review` | **Adotar** | ADOTAR — revisão UX de fluxos de operação, administração, autoria e recuperação. |
| `design-system` | **Adotar** | ADOTAR — componentes e tokens próprios do Ec em Vue/TypeScript. |
| `distributed-systems` | **Adotar** | ADOTAR COMO PRIORIDADE FUNDACIONAL — Server e Agents falham independentemente; *at least once*, ACK pós-commit, perda de ACK, replay/deduplicação, leases, fencing, backpressure, reconexão com jitter e recuperação. |
| `domain-modeling` | **Adotar** | ADOTAR — entidades e invariantes de projeto, pipeline, job, attempt, agente, pool e configuração. |
| `engineering-productivity` | **Condicional** | CONDICIONAL — medir melhoria no trabalho dos desenvolvedores; não confundir com performance de jobs. |
| `iced-engineering` | **Excluir** | NÃO ADOTAR — framework desktop do Ox incompatível com UI Vue 3 do Ec. |
| `observability-and-diagnostics` | **Adotar** | ADOTAR — diagnosticar Server/Agents, jobs e filas; observar backlog SQLite/PostgreSQL, ACK/retries, heartbeats, latências, pools, sessões gRPC, perdas e degradação. |
| `performance-engineering` | **Adotar** | ADOTAR — ensaiar 100/500/1.000/2.000 agentes, distinguindo ociosos de jobs ativos; medir scheduler, PostgreSQL, ingestão de logs, CPU, RAM, conexões, recuperação e SLOs definidos por teste. **1.000 é meta experimental, não capacidade comprovada.** |
| `plugin-protocol-engineering` | **Adotar** | ADOTAR — contrato host-plugin versionado e permissões, subordinado à segurança específica de plugins do Ec. |
| `prompt` | **Condicional** | CONDICIONAL — autoria de prompts quando houver tarefa de IA ou instruções de execução de modelos; não converter jobs CI/CD em prompts. |
| `reliability-engineering` | **Adotar** | ADOTAR — recuperação após queda em qualquer ponto do fluxo SQLite→gRPC→PostgreSQL→ACK; órfãos, disk-full, indisponibilidade do banco, timeouts, retries limitados e efeitos externos duplicáveis. |
| `rust` | **Adotar** | ADOTAR — ambos os monólitos Rust; definir toolchain e políticas reais antes de compilar. |
| `software-architecture` | **Adotar** | ADOTAR — limites modulares, dependências, contratos e isolamento sem fragmentação prematura. |
| `software-delivery` | **Adotar** | ADOTAR — build, release e distribuição do Server, Agent e frontend com artefatos rastreáveis. |
| `software-design` | **Adotar** | ADOTAR — separação de módulos sem dispersão física em microsserviços. |
| `software-evolution` | **Condicional** | CONDICIONAL — mudanças de contrato, migrações e rollout servidor/agentes em versões distintas. |
| `software-testing` | **Adotar** | ADOTAR — unitário, integração, sistema, falha e cenários reproduzíveis. |
| `technical-documentation` | **Adotar** | ADOTAR — contratos, runbooks, SDD, instalação, manutenção e operação. |
| `tool-output-engineering` | **Adotar** | ADOTAR — captura e normalização não destrutiva de stdout/stderr de builds e testes; complementar cicd-logs-and-test-results. |
| `workspace-integrity-engineering` | **Adotar** | ADOTAR — integridade de checkout e workspace de jobs, concorrência e limpeza; adaptar sem supor editor multiagente. |

**Contagem reconciliada:** 20 adotar + 6 condicionais + 4 excluir = 30 Ox; 20 + 6 = 26 incorporáveis do Ox; 26 + 40 = 66 planejadas no Ec. **Planejadas** não significa instaladas, automaticamente invocadas ou tecnicamente aceitas.

## 5. Catálogo integral das 40 skills próprias do Ec

As 40 entradas abaixo são mantidas como candidatas do Ec, sujeitas a testes técnicos, contratos e gatilhos de uso. Não há eliminação por mera sobreposição nominal com skills do Ox.

| Skill do Ec | Área primária proposta |
|---|---|
| `cicd-agent-runtime` | Agent / runtime |
| `cicd-artifacts-and-retention` | Transversal / integração |
| `cicd-build-cache` | Agent / runtime |
| `cicd-execution-runtime` | Agent / runtime |
| `cicd-logs-and-test-results` | Transversal / integração |
| `cicd-pipeline-graph` | Server / domínio |
| `cicd-release-and-deployment` | Server / domínio |
| `cicd-scheduler` | Server / domínio |
| `cicd-vcs-event-processing` | Server / domínio |
| `cli-engineering` | Transversal / integração |
| `css-layout-engineering` | Frontend / experiência |
| `ec-agent-control-protocol` | Agent / runtime |
| `ec-agent-pool-console` | Frontend / experiência |
| `ec-configuration-publication` | Server / domínio |
| `ec-declarative-resource-schemas` | Server / domínio |
| `ec-effective-permissions` | Server / domínio |
| `ec-environments-and-approvals` | Server / domínio |
| `ec-integrations-and-webhooks` | Server / domínio |
| `ec-operations-and-administration-console` | Frontend / experiência |
| `ec-people-and-access-administration` | Frontend / experiência |
| `ec-pipeline-authoring-workspace` | Frontend / experiência |
| `ec-plugin-isolation-and-lifecycle` | Server / domínio |
| `ec-plugin-manifests-and-compatibility` | Server / domínio |
| `ec-product-contract-test-harness` | Transversal / integração |
| `ec-project-hierarchy-management` | Server / domínio |
| `ec-run-execution-operations` | Frontend / experiência |
| `ec-semantic-configuration-projections` | Frontend / experiência |
| `frontend-form-engineering` | Frontend / experiência |
| `frontend-routing` | Frontend / experiência |
| `frontend-state-and-cache` | Frontend / experiência |
| `frontend-testing` | Frontend / experiência |
| `http-api-contracts` | Transversal / integração |
| `identity-federation` | Server / domínio |
| `information-architecture` | Frontend / experiência |
| `internationalization` | Frontend / experiência |
| `property-based-testing` | Transversal / integração |
| `typescript-engineering` | Frontend / experiência |
| `vue3-engineering` | Frontend / experiência |
| `web-accessibility` | Frontend / experiência |
| `web-realtime-streaming` | Frontend / experiência |

**Observação:** a coluna indica uma área de aplicação predominante para guiar seleção, não uma regra de empacotamento. Por exemplo, protocolo Agent/Server, caches, execução, logs e artefatos exigem código e testes em ambos os lados da fronteira.

## 6. Sobreposições e recomendações de composição

| Problema | Skill principal Ec | Skill Ox auxiliar | Decisão de integração |
|---|---|---|---|
| Ciclo de vida e slots do Agent | `cicd-agent-runtime` | `agent-runtime-engineering` (condicional) | SQLite local durável para eventos/checkpoints/outbox, reinício e reenvio; não importar sessões LLM/ACP. A primária continua sendo Ec. |
| Criação e encerramento de processos de jobs | `cicd-execution-runtime` | `tool-output-engineering`, `reliability-engineering` | A primeira controla processo; a segunda preserva saída bruta e metadados; reliability avalia falhas. |
| Comunicação e reconexão | `ec-agent-control-protocol` | `distributed-systems`, `api-testing` | Propor gRPC bidirecional/Protobuf/HTTP-2, TLS/mTLS, ACK de commit, versionamento, reconciliação, retries seguros e backpressure; testar versões divergentes. |
| Reserva, fila e reatribuição | `cicd-scheduler` | `distributed-systems`, `database-engineering` | PostgreSQL como autoridade transacional, quotas/slots, leases/fencing, reatribuição reconciliada e deduplicação; ACK de mensagem não comprova job concluído. |
| Logs, testes e relatórios | `cicd-logs-and-test-results` | `tool-output-engineering` | Captura/parsing e ingestão/replay são fases distintas; preservar bytes, exit status e indicação de perdas. |
| Plugins externos | `ec-plugin-isolation-and-lifecycle` + `ec-plugin-manifests-and-compatibility` | `plugin-protocol-engineering`, `application-security` | Compatibilidade, origem e permissões não substituem isolamento demonstrável. |
| Configuração desejada | `ec-declarative-resource-schemas` + `ec-configuration-publication` | `domain-modeling`, `software-testing` | Schemas YAML, validação, diff, autorização, revisão publicada e reconciliação. |
| UI de administração | `vue3-engineering` e skills específicas Ec | `design-system`, `design-review` | Não usar Iced; preservar equivalência semântica Visual/Formulário/YAML. |

**Regra operacional:** selecionar a skill primária pelo problema concreto e somente auxiliares para questões diferentes. A simples disponibilidade de 66 entradas não exige carregar todas nem criar 66 agentes executores.

## 7. Revisões específicas em relação ao parecer anterior

### 7.1 `agent-runtime-engineering`: exclusão → adoção condicional

**Antes:** excluída como runtime interno do Ox. **Agora:** incluir no catálogo, com seleção restrita. Seu objeto original são agentes CLI/ACP, não runners CI/CD. É útil se a implementação demandar supervisor de subprocessos, máquinas de estado e cancelamento/recovery com propriedade verificável. Não reutilizar contratos ACP nem contabilização de tokens por analogia. Quando o problema for apenas job execution, `cicd-execution-runtime` deve ser a primária.

### 7.2 `distributed-systems`: manter, elevar prioridade

Não é habilidade extra. A comunicação entre dois processos sujeitos a falhas independentes, especialmente em hosts diferentes, requer garantias distribuídas explícitas. Priorizá-la nas specs de leases/fencing, perda de confirmação, retransmissão, reatribuição, reordenação, compatibilidade de versões e recuperação. *Não* concluir daí que o Ec precisa de microsserviços.

### 7.3 `tool-output-engineering`: manter com escopo técnico delimitado

Saída de build não é necessariamente texto UTF-8, log completo ou sucesso do processo. Conservar metadados, falhas, offsets, chunks e status bruto sob retenção apropriada. Complementar, e não substituir, `cicd-logs-and-test-results`.

### 7.4 `workspace-integrity-engineering`: manter adaptada

Reaproveitar preconditions, path boundaries, symlinks, concorrência e recuperação nos diretórios de checkout/workspace. Não importar sem justificativa o modelo de editor de código multiagente do Ox.

### 7.5 Excluídas permanecem excluídas

- `iced-engineering`: Ec usa web Vue/TypeScript.
- `code-context-engineering`: somente se o Ec passar a exigir indexação de código para análise semântica.
- `code-knowledge-graph-engineering`: DAG de pipeline ≠ grafo de conhecimento do código.
- `agent-usage-accounting`: custos de tokens/modelos ≠ quotas de CPU/memória/duração de CI/CD.

## 7.6 Alterações da revisão 2 — transporte, persistência e escala (propostas)

**Inventário inalterado:** 40 Ec + 26 Ox = **66 skills**; **nenhuma inclusão ou exclusão** nesta revisão. As mudanças abaixo representam **recomendações para futuros procedimentos `SKILL.md` e pontes SDD**, não edições nas skills originais dos ZIPs. A skill Ec específica é primária; Ox atua na questão transversal indicada.

| Skill existente | Atualização concreta recomendada | Critério verificável |
|---|---|---|
| `ec-agent-control-protocol` (Ec) | Adotar, mediante ADR, gRPC bidirecional em HTTP/2 com Protobuf versionado; conexões iniciadas pelo agente; TLS/mTLS, autenticação mútua, compatibilidade N/N−1, ACK pós-commit, streaming, controle de fluxo, backoff e jitter. | Interromper stream, derrubar servidor, autenticar incorretamente, trocar versões; verificar reconciliação sem perda de evento persistido. |
| `cicd-agent-runtime` (Ec) | Incluir SQLite privado por agente, outbox durável, checkpoints e estado de retomada; persistir eventos antes de enviar e eliminar pendências somente após ACK. | Reiniciar/desligar agente entre commit local, envio e ACK; recuperar outbox sem perda de registros confirmados localmente. |
| `cicd-execution-runtime` (Ec) | Separar confirmação de comando, início de processo, término e confirmação persistida; proteger cancelamentos e side effects. | Falha após start e antes de reportar conclusão não cria sucesso fictício. |
| `cicd-scheduler` (Ec) | Manter PostgreSQL como autoridade de jobs/attempts; usar leases, fencing monotônico, quotas, slots, fairness e reatribuição segura. | Repetir/atrasar ACK, expirar lease e reconectar agente antigo; rejeitar mutações com fencing obsoleto. |
| `cicd-logs-and-test-results` (Ec) | Chunks identificados por job/attempt/stream/offset, retomada e batching com limites; separar tráfego de controle do volumoso. | Simular perdas/reordenação, rede lenta e volume alto sem ocultar truncamento ou bloquear cancelamento. |
| `ec-product-contract-test-harness` (Ec) | Testes integrados de Protobuf, versionamento, deduplicação, persistência, backpressure e fluxo completo de recuperação. | Executar matriz de falhas e contratos entre versões do servidor e agente. |
| `database-engineering` (Ox) | PostgreSQL central com inbox/outbox e índices/UNIQUE; SQLite local WAL com `synchronous=FULL` para eventos críticos, limites de volume e transações curtas. | Crash antes/depois do commit em ambas as pontas, disk-full, integridade, migrações e concorrência. |
| `distributed-systems` (Ox) | Explicitar *at least once* para mensagens duráveis; IDs únicos, ACK pós-commit, replay, ordem, deduplicação, fencing e limites de idempotência externa. | Perda de ACK causa reenvio sem reaplicação de efeito transacional. |
| `reliability-engineering` (Ox) | Regras de retry com backoff/jitter, circuit/degraded states, recuperação após partições, limites da outbox e proteção contra tempestades de reconexão. | Reinício coletivo de agentes; falha do banco; esgotamento local; alerta sem mascarar indisponibilidade. |
| `performance-engineering` (Ox) | Experimentos escalonados com 100, 500, 1.000 e 2.000 agentes; distinguir conectados, ociosos e executando, com perfis de logs distintos. | Registrar baseline, hardware, duração, percentis, CPU/RAM, conexões, IOPS/WAL, filas e erros; somente então estimar limite. |
| `observability-and-diagnostics` (Ox) | Métricas de sessões/streams, backlog das outboxes, reenvios, ACK, filas, leases, heartbeats, pool PostgreSQL e latências. | Diagnosticar saturação distinguindo rede, banco, scheduler e ingestão de logs. |
| `api-testing` (Ox) | Contratos Protobuf e APIs públicas HTTP, versões N/N−1, autenticação/negação e erros transitórios/finais. | Validar consumidor e provedor reais, não apenas mocks. |

**Separação de responsabilidades:** gRPC é **transporte**, não fila durável. SQLite protege eventos persistidos localmente, PostgreSQL protege estado global. Nenhum banco impede a perda de evento que nunca foi gravado; nenhum `event_id` garante sozinho `exactly once` nos efeitos externos de um job. Broker dedicado (Kafka/NATS/RabbitMQ) só entra como nova decisão se requisitos, medições ou necessidade de múltiplos consumidores o justificarem.

**Critérios mínimos de escala:** testar patamares 100/500/1.000/2.000 em carga leve, carga nominal e rajadas; reconexão simultânea; perda de confirmação; degradação do PostgreSQL; saturação dos logs; throttling; reinício de processos; recuperação da fila. **1.000 agentes é alvo de ensaio, não especificação de capacidade assegurada.** Não fixar taxas, durações, limites ou SLOs como aprovados sem resultado medido. Os parâmetros iniciais sugeridos e cenários de falha estão detalhados no documento complementar `ec-arquitetura-comunicacao-e-validacao.md`.

**Configuração:** limites, políticas de pool/agente e opções declarativas editáveis no Ec devem ser modelados exclusivamente em **YAML versionado** e projetados sem perda semântica nos modos Visual/Formulário/YAML. SQLite/PostgreSQL contêm apenas estado operacional; Protobuf é contrato de mensagens em trânsito, **não formato alternativo de configuração**. Credenciais privadas fora de YAML plaintext.

**Condição de adoção:** a norma atualmente prevê HTTP versionado; gRPC sobre HTTP/2 é compatível com a família HTTP, mas sua seleção concreta, Protobuf, SQLite no Agent e novas garantias dependem de **ADR, especificação, alterações em skills e pontes, e evidências de testes**. Nenhum desses passos foi executado por este parecer.

## 8. SDD, governança, agentes de desenvolvimento e integração

**Não confundir três coisas:** `AGENTS.md` orienta o agente de desenvolvimento no repositório; `agents/openai.yaml` é metadado da interface de uma skill; Ec Agent é o executor do produto. A presença de arquivos `agents/` nas skills não instala processos Ec Agent.

### Estrutura documental recomendada (não instalada)

```text
AGENTS.md                      # entrada do executor de desenvolvimento
docs/constitution.md           # políticas permanentes do Ec
docs/architecture/             # Server, Agent, Frontend, contratos e ADRs
sdd/project.md                 # fase, escopo, estado autorizado
sdd/workflow.md                # rota local ou completa
sdd/evidence.md                # critérios, estados e freshness
sdd/chain/<skill>.md           # ponte para as 66 skills selecionadas
sdd/features/<feature>/       # spec, plan, tasks, traceability, evidence, converge
.codex/skills/<skill>/SKILL.md # catálogo e procedimentos especializados
```

### Gates de aceitação sugeridos para o desenho distribuído

1. Rejeitar aceite se uma tentativa puder ser confirmada por executor sem recibo durável do Server.
2. Testar duas atribuições concorrentes do mesmo job e comprovar a política de fencing.
3. Desconectar Agent após executar o efeito, mas antes de enviar confirmação; observar reconciliação, sem afirmar `exactly once`.
4. Reiniciar Server e Agent separadamente com jobs em andamento; medir efeito sobre estado e recuperação.
5. Testar versão N e N-1 do protocolo gRPC/Protobuf **se adotado**, capacidades incompatíveis, autenticação inválida e tentativas reexecutadas.
6. Testar cancelamento durante inicialização, execução e envio de logs, distinguindo `cancel requested` de `terminated`.
7. Validar que novos YAMLs passam pelo mesmo parser/validador a partir dos três modos, sem descarte semântico.
8. Garantir isolamento demonstrado por testes negativos de filesystem, rede, recursos e segredos antes de executar workloads não confiáveis.

**SDD:** usar a rota completa para contratos de Server/Agent, persistência, protocolos, segurança e migrações. Evidências históricas não são revalidadas por troca de hash; a aceitação deve estar ligada a inputs, testes e oráculos atuais. As decisões desta revisão não alteram automaticamente a constituição ou os critérios vigentes.

## 9. Proposta de modularização e ordem de execução

**Ec Server (Rust, um deployable modular):** identidade/RBAC; leitura-publicação YAML; modelagem de projetos/pipelines; persistência PostgreSQL com inbox/outbox e fila durável; scheduler, leases/fencing e quotas; protocolo Agent (proposta gRPC); logs/artefatos; plugins e API; observabilidade. Dividir por contratos internos estáveis, não por serviços de rede sem necessidade.

**Ec Agent (Rust, um deployable modular):** identidade/conexão; capacidades/slots; leases e attempt owner; SQLite privado (outbox/checkpoints); workspace/checkout; process supervision e isolamento; logs e resultados; cache local/artefatos; reconciliação após falhas. Múltiplos agentes remotos podem executar o mesmo binário, com estado e permissões independentes.

**Frontend (Vue 3 + TS):** áreas de gestão de projetos, equipes, usuários, agentes, pipelines, execuções, plugins e administração. Modos Visual/Formulário/YAML sobre modelo semântico único, sem simular operações de backend.

**Sequência recomendada:** (1) contratos de identidade, job/attempt e protocolo HTTP versionado (com proposta de gRPC/Protobuf sujeita a ADR); (2) transações, scheduler e leases/fencing; (3) Agent executor local isolado; (4) logs, testes, artefatos, retry e recuperação; (5) YAML com publicação/versionamento e paridade de edição; (6) APIs e UI administrativas; (7) plugins e expansão com gates específicos de segurança. Design system e contratos da UI devem ser considerados desde o começo, sem esperar o fim do backend.

## 10. Conformidade e limitações

**Normas consultadas e aplicadas:** `EC-REGRAS-DE-DESIGN-E-PRODUTO.md` e `EC-REGRAS-CRIACAO-DE-TELAS-E-IMAGENS.md`. O resultado respeita Ec como nome do produto, os dois componentes Rust, frontend Vue/TS, YAML canônico, modos equivalentes, segurança, escopo amplo de gestão e distinção entre plano e implementação.

**Conflitos detectados:** o protocolo normativo vigente é HTTP versionado; a especialização em gRPC bidirecional e o SQLite no Agent são **propostas**, ainda pendentes de ADR e validação. A orientação original da skill Ox `agent-runtime-engineering` é específica de agentes CLI/ACP e não deve ser transposta literalmente para agentes de CI/CD; a solução é adoção condicional e adaptação explícita. `iced-engineering` é incompatível com a stack frontend do Ec. A decisão gRPC requer atualizar/precisar o contrato normativo e as especificações afetadas antes de ser tratada como adotada.

**Não verificado:** execução de benchmark para 100–2.000 agentes, adoção de gRPC/SQLite, existência do repositório real Ec, binários Server/Agent, execução de testes de produto, integração real das 66 skills, funcionamento das pontes SDD, compatibilidade de protocolos ou instalação. O estudo é uma análise de documentos, arquivos de skills e fronteiras arquiteturais; não um certificado operacional.

## 11. Recomendação conclusiva

**Manter o plano de 66 skills como catálogo candidato, não como pacote executado.** A revisão 2 não adiciona nem remove skills; detalha responsabilidades de protocolo gRPC proposto, SQLite local, PostgreSQL central, ACK, deduplicação, backpressure e testes de escala. A alteração relevante é reincluir `agent-runtime-engineering` em regime condicional e elevar `distributed-systems` à categoria de método prioritário para contratos Server–Agent. Manter as 40 skills específicas do Ec como primárias quando cobrem diretamente o domínio. Formalizar esta mudança por ADR antes de modificar perfil, pontes, artefatos SDD ou constituição do projeto.
