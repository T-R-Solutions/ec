# Ec — Proposta de arquitetura, comunicação confiável e plano de validação

**Data:** 2026-11-03  
**Status:** **PROPOSTA PARA TESTES, NÃO ADOTADA NEM IMPLEMENTADA**  
**Objetivo:** consolidar as decisões discutidas sobre um Ec Server e múltiplos Ec Agents, documentar parâmetros iniciais para experimentação e fornecer critérios reproduzíveis de validação.  
**Relação com entregas anteriores:** complemento ao `config.md` (governança/skills/SDD) e à reavaliação das skills; não os substitui.

## 1. Decisão resumida e delimitação

| Elemento | Proposta | Fundamento / limitação |
|---|---|---|
| Ec Server | Um **monólito modular Rust** (control plane) | Scheduler, API, RBAC, recursos, fila, decisões de estado, controle de agentes. Um processo lógico inicialmente; disponibilidade de alta redundância não presumida. |
| Ec Agent | **Monólito modular Rust** por host/instância, múltiplas instâncias independentes | Execução local de jobs, isolamento, spool, captura de eventos, reconexão. |
| Comunicação Server ↔ Agent | **gRPC sobre HTTP/2**, streams bidirecionais, TLS/mTLS | **Alternativa proposta** ao HTTP versionado atualmente estabelecido. gRPC não é fila durável. |
| Estado central | **PostgreSQL** | Fila de jobs, transações, leases, fencing, inbox/outbox do servidor, auditoria e estados operacionais. |
| Estado local | **SQLite** por agente | Outbox de eventos, checkpoints e ACKs pendentes; não substitui o banco do servidor. |
| Frontend | **Vue.js + TypeScript**, API pública HTTP versionada | Visual, Formulário e YAML representam o mesmo YAML canônico. |
| Broker externo | **Não obrigatório na primeira fase** | Kafka/NATS/RabbitMQ só após requisito específico ou medição que justifique. |
| Escala | **1.000 agentes conectados = meta inicial, não capacidade comprovada** | Validar 100, 500, 1.000 e 2.000; agentes ociosos ≠ agentes executando e enviando logs. |

**Fronteira normativa:** `EC-REGRAS-DE-DESIGN-E-PRODUTO.md` estabelece **HTTP versionado**, não gRPC como decisão efetiva. O uso de gRPC proposto aqui precisa de ADR e aprovação arquitetural; por usar HTTP/2 pode ser tecnicamente compatível com a exigência de transporte HTTP, mas não comprova compatibilidade contratual nem significa adoção automática. `EC-REGRAS-CRIACAO-DE-TELAS-E-IMAGENS.md` continua aplicável à apresentação operacional. **Nenhuma implementação ou benchmark é alegada neste documento.**

## 2. Fluxo e limites de responsabilidade

```text
          Usuário / Vue.js + TypeScript
                    |
             API HTTP/TLS
                    |
          +-----------------------+
          | Ec Server (Rust)      |
          | API, Auth/RBAC        |
          | scheduler, fila      |
          | conexões gRPC        |
          +-----+------------+----+
                |            |
          PostgreSQL      gRPC/TLS  <--- conexão iniciada pelos agentes
          (global)          |
                +-----------+---------------------------+
                            |                           |
                        Ec Agent A                  Ec Agent N
                        (Rust)                      (Rust)
                        SQLite                     SQLite
                        executor                   executor
```

Os agentes **iniciam conexões de saída** para minimizar exigências de portas de entrada e facilitar NAT. Não pressupor que todas as redes corporativas aceitem HTTP/2 persistente; testar proxies, balanceadores, limites de idle, rotação de certificados e interrupções. O servidor não deve depender da disponibilidade contínua de um agente para manter consistência.

**Configuração declarativa:** somente **YAML** é autoritativo, inclusive políticas dos agentes, limites, recursos, pools e opções de comunicação. **Estado operacional** (leases, filas, tentativas, logs, eventos, sessões, ACKs) reside em PostgreSQL/SQLite/armazenamento apropriado — não no YAML. Arquivos `.proto` são **contratos de interface compiláveis**, não arquivos alternativos de configuração do Ec. Dados trafegados em Protobuf não violam a regra YAML.

## 3. Fluxo seguro de mensagens e reenvio

### 3.1 Agent → Server (eventos, logs estruturados, resultado)

1. Gerar `event_id` estável, `agent_id`, `job_id`, `attempt_id`, `sequence`, `event_type` e payload. Nunca gerar novo `event_id` para um reenvio do mesmo evento.
2. **Persistir primeiro** na outbox SQLite por transação confirmada em disco; apenas depois considerar o evento enfileirado localmente. Para eventos de conclusão, registrar atomicamente o estado local da tentativa e seu evento quando fizerem parte da mesma base transacional.
3. Enviar por gRPC com limite explícito de eventos/bytes em trânsito. Adotar prioridade para mensagens de controle e fechamento, de modo que logs volumosos não impeçam ACKs.
4. O Server, em uma transação PostgreSQL, registra (`agent_id`, `event_id`) com restrição UNIQUE, verifica a tentativa e a versão de lease/fencing e aplica os efeitos **na mesma transação**. A confirmação de transporte **não** deve anteceder o commit.
5. O Server devolve ACK **aplicado**, **já processado** ou **rejeitado definitivamente** com o mesmo identificador e motivo. Distinga ACK de recebimento durável de confirmação de efeito de negócio.
6. Após ACK durável/aplicado, o Agent marca o registro como confirmado e executa limpeza segura; sem ACK, mantém o evento e tenta novamente após reconexão. Não tratar timeout como prova de falha da operação remota.
7. Se houver erro permanente, registrar estado de quarentena, diagnóstico e intervenção; não reexecutar indefinidamente entrada inválida.

### 3.2 Server → Agent (atribuição e cancelamento)

O Server grava a intenção de comando e seu `command_id` na própria transação que altera o estado da fila. Uma atribuição deve incluir `job_id`, `attempt_id`, `lease_id`, **fencing token monotônico**, deadline e revisão imutável do YAML efetivo. O Agent responde com confirmação de recebimento, mas a mudança para `RUNNING` exige evidência de processo iniciado. Comando repetido com mesmo ID é idempotente; comando de lease antigo é rejeitado. O servidor pode reatribuir trabalho após expiração somente sob regras de fencing, mantendo a possibilidade de efeitos externos não idempotentes: **não prometer exactly-once em execução de CI/CD**.

### 3.3 Falhas que precisam de tratamento específico

| Falha | Resposta exigida |
|---|---|
| Agente cai antes de persistir evento | Evento não garantido; observar/reconstituir efeitos locais quando possível; testes de crash antes/depois do commit. |
| Agente cai após persistir e antes de enviar | Outbox recuperada no restart e reenvio após conexão. |
| Servidor aplica efeito, mas ACK se perde | Reenvio; UNIQUE + transação impedem aplicação duplicada. |
| Servidor cai antes do commit | Nenhum ACK de aplicação; novo envio reprocessa a tentativa. |
| Disco local cheio/SQLite indisponível | Backpressure, suspender novos jobs quando segurança exigir, expor `DEGRADED`; nunca declarar evento persistido. |
| PostgreSQL indisponível | Suspender novos grants/ACKs de aplicação, preservar outboxes, monitorar crescimento e recuperação. |
| Partição de rede com job ainda ativo | Lease expira; processos locais podem persistir. Exigir fencing, reconciliação e tratamento de side effects. |
| Mensagens duplicadas/fora de ordem | `event_id` único; `sequence` monotônica por stream/tentativa e regras explícitas de reordenação. |
| Perda total do disco do agente | SQLite local não protege contra perda física; exigir estratégia de redundância/backup quando SLA demandar. |
| Reinício em massa de agentes | Exponential backoff com jitter e limite global de handshakes. |

**Escopo de garantias:** entrega lógica *at least once* para registros duráveis; processamento idempotente de efeitos transacionais locais ao PostgreSQL. Efeitos em sistemas externos (deploy, publicação, scripts) requerem chaves próprias, fencing e procedimentos compensatórios; **não** há transação distribuída automática.

## 4. Persistência e limites operacionais

**Ec Agent — SQLite:** base privada por agente, em disco persistente local (não em diretório temporário apagado por reinício), WAL se adequado, `synchronous=FULL` para eventos críticos, transações curtas, limite de tamanho da outbox, política explícita de retenção e proteção de dados. O WAL não substitui backups; testar corrupção e durabilidade real em cada plataforma. Não compartilhar arquivo SQLite por NFS entre agentes.

**Ec Server — PostgreSQL:** uma autoridade central para `jobs`, `attempts`, `leases`, `agent_sessions`, `inbox_events`, `outbox_commands`, `event_receipts` e `audit_events`; tabelas/nomes são **modelo lógico sugerido**, não schema aplicado. Implementar índices de `(state, priority, ready_at)` e chaves únicas por origem/ID, seleção transacional de filas (p.ex., `FOR UPDATE SKIP LOCKED` quando apropriado), operações em lote e pool de conexões **limitado**. Não guardar necessariamente todo byte de log em linhas de banco: preferir storage de artefatos/logs com offsets e metadados no PostgreSQL, após validar requisitos de retenção e integridade.

**Gestão de segredos:** YAML só contém referências a credenciais, certificados ou secret stores; chaves reais não são escritas em YAML nem exibidas em logs. mTLS com identidade vinculada ao agente e política de rotação/revogação. Validar autorização por comando e recurso; autenticar o servidor no agente.

## 5. Proteções contra sobrecarga — 50 a 2.000 agentes

| Mecanismo | Proposta | Validação |
|---|---|---|
| Conexão | Preferir uma sessão gRPC persistente por agente, com controle de streams | Verificar memória/sockets/HTTP2 keepalive. |
| Heartbeat | Intervalo inicial ilustrativo de **15 s**, com ±20% de jitter; expirabilidade deve ser diferente do tempo de heartbeat | Simular atrasos, flapping e GC/suspensão. |
| Reconexão | Backoff exponencial inicial de **1 s**, máximo **60 s**, jitter completo | Reiniciar servidor com 1.000 agentes sem pico de reconexões descontrolado. |
| In-flight | Limite inicial ilustrativo **32 eventos** ou **256 KiB** por agente, o que ocorrer primeiro | Medir fila, RTT e saturação por fluxo. |
| Lotes | Até **100 eventos** ou **256 KiB**, prazo de flush **250 ms** para eventos não críticos | Medir overhead e latência; conclusões/cancelamentos não devem aguardar lote. |
| Backpressure | Créditos por agente/stream, bounded queues, pausa de logs não críticos antes de perder eventos de controle | Simular recepção 10× mais lenta. |
| Scheduler | Quotas por pool/projeto e slots declarados por agente; limites de jobs simultâneos no servidor | Verificar justiça e ausência de starvation. |
| Banco | Pool de conexões sob orçamento global; consultas em lote e transações curtas | Observar waits, lock contention, IOPS, WAL, uso de conexões. |
| Logs | Chunks ordenados, compressão opcional, hash/offset, armazenamento apropriado e limites por job | Testar perda, truncamento explícito, retomada e integridade. |
| Prioridade | Comandos de cancelamento, ACK e lease acima de streams volumosos de logs | Verificar prazo de controle sob carga. |

**Todos os números acima são valores experimentais de partida, NÃO defaults aprovados, benchmarks, SLOs ou limites comprovados.** Devem ser ajustados por testes, hardware, perfil de logs e política operacional. As escolhas de heartbeats, ACKs, prazos e lease têm dependências e não devem ser alteradas isoladamente.

## 6. YAML ilustrativo de configuração declarativa

Este bloco representa uma **proposta de contrato** a formalizar em um schema YAML versionado. **Não é configuração executável do Ec atual; os campos ainda não foram implementados ou validados.** Configurar servidor e agente em arquivos YAML separados, com escopos e autorização próprios. Nomes `kind`, `apiVersion` e chaves abaixo são ilustrativos. O backend deve interpretar os três modos de edição sobre a **mesma estrutura semântica**.

### 6.1 Ec Server (`ec-server.yaml` — exemplo de destino, não arquivo criado)

```yaml
apiVersion: ec.dev/v1alpha1
kind: ServerRuntime
metadata:
  name: primary
spec:
  transport:
    agentProtocol: grpc
    grpc:
      listen: "0.0.0.0:7443"
      tls:
        mode: mutual
        serverCertificateRef: secret://ec/server/tls-cert
        serverKeyRef: secret://ec/server/tls-key
        trustedClientCARef: secret://ec/agent/ca
      maxConcurrentStreamsPerAgent: 4
  database:
    engine: postgresql
    connectionRef: secret://ec/server/postgres-url
    maxPoolConnections: 32
  scheduler:
    queueStorage: postgresql
    maxConcurrentDispatches: 64
    requireLeaseFencing: true
    perAgentSlotPolicy: declared-capacity
  delivery:
    ackAfterDurableCommit: true
    deduplicationKey: agent-id-and-event-id
    rejectStaleFencingTokens: true
    maxEventsPerBatch: 100
    maxBatchBytes: 262144
    nonCriticalFlushMilliseconds: 250
  flowControl:
    maxInflightEventsPerAgent: 32
    maxInflightBytesPerAgent: 262144
    prioritizeControlEvents: true
  monitoring:
    recordQueueLag: true
    recordAgentCount: true
    recordDeduplication: true
```

### 6.2 Ec Agent (`ec-agent.yaml` — exemplo de destino, não arquivo criado)

```yaml
apiVersion: ec.dev/v1alpha1
kind: AgentRuntime
metadata:
  name: worker-local
spec:
  server:
    endpoint: "https://ec-server.example.internal:7443"
    protocol: grpc
    tls:
      mode: mutual
      clientCertificateRef: secret://ec/agent/tls-cert
      clientKeyRef: secret://ec/agent/tls-key
      trustedServerCARef: secret://ec/server/ca
  persistence:
    engine: sqlite
    path: "/var/lib/ec-agent/state.db"
    journalMode: wal
    synchronous: full
    outboxMaxBytes: 1073741824
  reconnect:
    initialBackoffMilliseconds: 1000
    maxBackoffMilliseconds: 60000
    jitter: full
  heartbeat:
    intervalMilliseconds: 15000
    jitterPercent: 20
  delivery:
    persistBeforeSend: true
    retainUntilAck: true
    eventIdStrategy: uuid-v7
    maxInflightEvents: 32
    maxInflightBytes: 262144
  execution:
    slots: 2
    rejectStaleFencingTokens: true
    requireImmutableConfigRevision: true
```

**Nota de implantação:** caminhos, limites, URLs, nomes de recursos e `secret://` são exemplos; precisam ser validados para Linux/Windows/macOS, configuração de confiança, storage persistente e versões reais. `eventIdStrategy: uuid-v7` é sugestão, não obrigação: qualquer esquema que garanta unicidade estável no escopo serve. Persistência e contrato operacional não devem depender de API de configuração ainda inexistente.

## 7. Contrato lógico de gRPC (não é arquivo de configuração)

Propor interface Protobuf versionada, com mensagens conceituais `OpenAgentSession`, `AgentEvent`, `AgentHeartbeat`, `ServerCommand`, `EventAck`, `FlowControl` e `AgentCapabilities`. Para cada envelope: `protocol_version`, `agent_id`, `session_id`, `message_id`, `timestamp`, `payload_type`, `payload`, `job_id?`, `attempt_id?`, `lease_epoch?`, `sequence?`, `trace_id?`. Campos e tipos devem ser formalizados em `.proto` antes de gerar código; **não usar nomes conceituais como se fossem RPCs existentes**.

Regras mínimas: negociação de versão/capabilities; tamanho máximo de mensagem; deadlines; propagação de cancelamento; autenticação mTLS; autorização separada; métricas de stream; tratamento de erros `INVALID_ARGUMENT` versus `UNAVAILABLE`/`RESOURCE_EXHAUSTED`; retry apenas de operações idempotentes; ACK da aplicação com estados distintos de sucesso, duplicado e rejeição permanente. Eventos do agente não devem ser tratados como instruções de autoridade.

## 8. SDD para implementar e validar a proposta

Abrir uma **feature SDD completa** porque introduz alteração de contrato de comunicação, concorrência, durabilidade e escopo entre máquinas. Reutilizar o workflow SDD adotado em Ec, sem copiar automaticamente política específica do Ox. Sugestão de feature: `sdd/features/agent-server-reliable-transport/` com:

- `spec.md`: REQs e critérios AC; atores/erros; invariantes de outbox/inbox, idempotência, leases, segurança e YAML.
- `plan.md`: ADR comparando HTTP REST, gRPC e Kafka; topologia; modelos de dados; trade-offs; riscos; skills consultadas e versão/hash.
- `tasks.md`: implementação por etapas com checkpoints e tarefas verificáveis.
- `traceability.json`: cada AC → REQ → decisão/risco → tarefa → implementação → oráculo.
- `evidence.json`: ensaios, resultados, SHA-256 dos inputs e estado de aprovação; registrar `UNVERIFIED` até executar.
- `converge.md`: decisão por critério, lacunas, reexame, aprovação ou rejeição.

Skills prioritárias por problema técnico: **distributed-systems**, **reliability-engineering**, **database-engineering**, **rust**, **api-testing**, **software-testing**, **application-security**, **observability-and-diagnostics**, **performance-engineering**. `agent-runtime-engineering` do Ox só como auxiliar adaptada à supervisão de processos; não substituir as skills próprias de CI/CD do Ec. A seleção real deve ser registrada por tarefa; mencionar não significa aplicar.

## 9. Plano de ensaios reproduzíveis e gates

**Pré-requisitos:** implementar o contrato e uma bancada de agentes sintéticos + agentes executores reais; fixar versão/commit do servidor, agentes, schemas YAML, DB e harness; registrar CPU, RAM, disco/IOPS, RTT e topologia. **Não há resultados de execução neste documento.**

### 9.1 Sequência de carga

| Fase | Agentes conectados | Carga a observar | Evidência mínima |
|---|---:|---|---|
| B0 | 1–10 | Eventos normais e uma execução real | Contrato e integridade ponta a ponta. |
| B1 | 100 | 100% ociosos; depois 25% com jobs; depois logs de alto volume | Métricas base, conectividade, durabilidade. |
| B2 | 500 | Mesmo mix; 50% ativos | Saturação do servidor/DB e latência. |
| B3 | 1.000 | 0%, 25%, 50% e 100% ativos em testes separados | Meta inicial, não aprovada até passar gates. |
| B4 | 2.000 | Stress e recuperação; manter mix reproduzível | Encontrar limites e comportamento degradado. |

**Não confundir** 1.000 conexões ociosas com 1.000 jobs simultâneos. O throughput e a capacidade de slots devem ser medidos independentemente. Configurar duração suficientemente longa para medir steady state, picos e pós-falha; comparar ao baseline em hardware fixo.

### 9.2 Injeção de falhas e invariantes

| ID | Cenário | Critério de aprovação proposto |
|---|---|---|
| AC-01 | Desconectar Agent antes de enviar, depois de commit SQLite | Evento enviado após reconexão e registrado uma vez em efeito lógico. |
| AC-02 | Interromper Server depois do commit PostgreSQL e antes do ACK | Reenvio retorna ACK de duplicata, sem efeito duplo. |
| AC-03 | Simular crash do Agent antes do commit SQLite | Não declarar durabilidade; registrar lacuna e recuperar só o que for reconstruível. |
| AC-04 | Agent desconectado durante job, lease expira, tarefa reatribuída | Fencing impede aceitação de conclusão obsoleta; efeitos externos são explicitamente classificados. |
| AC-05 | 1.000 agentes reconectam após restart do Server | Sem pico incontrolado, OOM ou perda de mensagens duráveis; tempo de convergência medido. |
| AC-06 | PostgreSQL indisponível e depois recuperado | Sem ACK falso nem nova atribuição insegura; retomada e outboxes íntegros. |
| AC-07 | SQLite com disco cheio, corrupção ou erro de I/O | Agent entra em `DEGRADED`/não aceita novos jobs críticos; dados remanescentes preservados quando possível. |
| AC-08 | Enviar duplicate/out-of-order/stale `event_id`/lease | Chave UNIQUE, sequence e fencing impedem efeitos inválidos. |
| AC-09 | Excesso de logs e rede lenta | Backpressure preserva comandos de controle; perda/truncamento de logs nunca silencioso. |
| AC-10 | Certificado revogado/agente não autorizado | Conexão/ação recusada, evento auditável, sem execução. |
| AC-11 | Editar config em Visual, Formulário e YAML | Round-trip semanticamente equivalente, campos avançados preservados, diff antes de publicar. |
| AC-12 | Falha e reinício durante publish de YAML | Versão antiga permanece efetiva até commit/reconciliação; job fixa revisão imutável. |

### 9.3 Métricas e thresholds

Registrar: conexões, reconexões/min, heartbeats/s, eventos/s, bytes/s, ACK p50/p95/p99, fila atrasada, bytes de outbox, retries, duplicatas detectadas, comandos atrasados, falsos grants, jobs simultâneos, uso CPU/RAM por processo, pool DB, locks, WAL, IOPS, rede e perda/truncamento de logs. **Qualquer limite percentual/latência/tempo de recuperação é PENDENTE DE ACORDO** após baseline e SLA. Propor como *gate funcional absoluto*: zero efeitos transacionais duplicados aceitos; zero ACKs de aplicação antes de persistência; zero resultados de lease obsoleto aplicados; zero secrets expostos; nenhuma perda silenciosa de eventos reconhecidos como duráveis. Métricas quantitativas adicionais devem ser versionadas em YAML de testes quando formalizadas.

**Resultado mínimo:** tabelas por cenário com hardware, inputs, parâmetros, versão, comandos, séries temporais, percentis, falhas observadas e decisões `APPROVED`, `REJECTED`, `UNVERIFIED` ou `INCONCLUSIVE`, e `NOT APPLICABLE` só com razão.

## 10. Administração, UX e observabilidade

O Ec deve expor no frontend, com dados reais: agente conectado/desconectado/degradado, slots, último heartbeat (e atraso), queue lag, outbox local pendente reportada, tentativas de reconexão, erros de certificado, jobs/leases ativos, motivo de inelegibilidade, eventos reprocessados e comandos pendentes. Nunca mostrar `SUCCESS` só porque um RPC respondeu antes da transação. Diferenciar **comando enviado**, **entregue**, **aceito**, **iniciado**, **finalizado** e **confirmado**. Telas administrativas devem manter as modalidades Visual/Formulário/YAML apenas para configuração desejada; logs e estados continuam operacionais.

Incluir filtros por agente/pool/job, ações autorizadas, histórico auditável, operações de retry/quarentena com prevenção de replay perigoso, acessibilidade WCAG 2.2 AA e feedback de drift/config publicada. Nenhum layout é entregue aqui; trata-se de requisitos funcionais para interfaces futuras.

## 11. Matriz de decisões pendentes e critérios para broker

| Decisão | Proposta | Para confirmar |
|---|---|---|
| Trocar API entre Agent/Server para gRPC | **Avaliar/ADR** | Testar transporte em redes-alvo, proxy HTTP/2, streaming, observabilidade, contratos e compatibilidade. |
| SQLite no agente | **Adotar se testes comprovarem** | Durabilidade/crashes, volumes de spool, corrupção, recuperação, sistemas operacionais. |
| PostgreSQL como fila única de jobs | **Manter** | Contenção de locks, taxa de dispatch, latência p99, operações de limpeza e vacuum. |
| Meta 1.000 agentes | **Meta de validação, não SLA** | Carga ociosa/ativa + falhas, com hardware representativo. |
| Introduzir Kafka/NATS/RabbitMQ | **Adiar** | Requisito real de fan-out, replay, retenção multi-consumidor ou saturação medida cuja correção seja mais cara que broker. |
| Alta disponibilidade do Server | **Fase posterior/ADR** | RPO/RTO, failover, liderança do scheduler, DB HA e fencing entre réplicas. |
| Semântica de logs/artefatos | **Especificar separadamente** | Chunks, checksum, retenção, storage, cache, limites e consistência por job. |

**Critério de evolução:** se a fila PostgreSQL se tornar gargalo reproduzível, primeiro examinar índices, batching, concorrência, capacidade do hardware, particionamento e separação de logs; introduzir broker somente com justificativa por requisitos/medições. Broker por si só não evita duplicação de execução ou cria `exactly-once` para side effects.

## 12. Checklist de conformidade documental (não equivale a testes do produto)

- [x] Ec e dois monólitos modulares Rust preservados; frontend Vue.js + TypeScript separado.
- [x] PostgreSQL para estado operacional central; SQLite local proposto, sem torná-lo configuração autoritativa.
- [x] YAML como única configuração declarativa canônica, com modos Visual/Formulário/YAML semanticamente equivalentes.
- [x] Proposta de gRPC identificada como **mudança pendente** do HTTP versionado vigente.
- [x] Idempotência, ACK após commit, retries, leases e fencing explicitados.
- [x] Objetivo de 1.000 agentes separado de garantia, com etapas de testes até 2.000.
- [x] Segurança, limites, diagnósticos, testes negativos e observabilidade previstos.
- [ ] Schemas YAML reais definidos/implementados — **PENDENTE**.
- [ ] ADR gRPC aprovada e contratos `.proto` validados — **PENDENTE**.
- [ ] Ec Server/Ec Agent instrumentados e testes executados — **PENDENTE**.
- [ ] Benchmark, metas de SLO e aceite SDD por evidência — **PENDENTE**.

**Documentos normativos efetivamente consultados:** `EC-REGRAS-DE-DESIGN-E-PRODUTO.md` e `EC-REGRAS-CRIACAO-DE-TELAS-E-IMAGENS.md`. **Conflito/limitação material:** a adoção de gRPC, SQLite local e os campos YAML propostos ainda não está documentada como decisão vigente; formalizar revisão de arquitetura e schema antes de caracterizar a proposta como configuração real. Nenhum ZIP ou repositório foi modificado por esta entrega.

## 13. Referências técnicas oficiais para implementação e auditoria

- gRPC — conceitos e comunicação: https://grpc.io/docs/what-is-grpc/core-concepts/
- gRPC — retries: https://grpc.io/docs/guides/retry/
- gRPC — flow control: https://grpc.io/docs/guides/flow-control/
- PostgreSQL — `SELECT` e locking: https://www.postgresql.org/docs/current/sql-select.html
- PostgreSQL — transações: https://www.postgresql.org/docs/current/tutorial-transactions.html
- SQLite — WAL: https://www.sqlite.org/wal.html
- SQLite — PRAGMA synchronous: https://www.sqlite.org/pragma.html#pragma_synchronous
- YAML 1.2: https://yaml.org/spec/1.2.2/
- OWASP — CI/CD: https://cheatsheetseries.owasp.org/cheatsheets/CI_CD_Security_Cheat_Sheet.html

Estas referências são apoio técnico; nenhuma afirma medição ou implementação do Ec.
