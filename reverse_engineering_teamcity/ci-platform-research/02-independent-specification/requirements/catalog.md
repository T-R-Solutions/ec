# Requisitos independentes

## R01 — Submissão autenticada [CORE]

**motivation:** Resolver: Recusar solicitações inválidas e criar execução identificável.

**inputs:** identidade, projeto, config_revision, chave, payload

**outputs:** run_id durável

**rules:** validar autorização e chave por projeto; mesma chave/payload retorna mesmo run; payload diferente → conflito

**invariants:** nenhum run aceito sem commit

**errors:** Unauthorized, Forbidden, Conflict, InvalidConfig

**intended_guarantees:** uma criação lógica por chave

**dependencies:** 

**features:** F02, F03

**evidence:** TC-013, TC-004

**acceptance:** A01, A02

## R02 — Validar configuração declarativa [CORE]

**motivation:** Resolver: Organizar builds e limitar quem pode alterar ou executar cada projeto.

**inputs:** JSON v1 com jobs, steps, limites e revisão

**outputs:** plano imutável e digest

**rules:** limitar tamanho, rejeitar campos desconhecidos, caminhos absolutos/.., segredo literal e ciclos; CORE aceita um job

**invariants:** configuração nunca executa no control plane

**errors:** SchemaError, Cycle, Unsupported

**intended_guarantees:** mesmo plano/digest em retries

**dependencies:** R01

**features:** F01, F02

**evidence:** TC-016, D-PARAM

**acceptance:** A02

## R03 — Fila e histórico duráveis [CORE]

**motivation:** Resolver: Manter pedidos pendentes até haver capacidade.

**inputs:** plano validado e eventos

**outputs:** run/job Queued e audit entry

**rules:** criar run, job e fila na mesma transação; GET autorizado retorna estados versionados

**invariants:** fila deriva de jobs; nenhum enqueue volátil autoritativo

**errors:** DatabaseUnavailable

**intended_guarantees:** ack somente após commit; restart recupera pendentes

**dependencies:** R01, R02

**features:** F03

**evidence:** TC-003, TC-004

**acceptance:** A03, A06

## R04 — Agentes autorizados e compatibilidade [CORE]

**motivation:** Resolver: Executar onde existem recursos e ferramentas necessários.

**inputs:** credencial, sessão, labels e slot

**outputs:** sessão e capacidade elegível

**rules:** aprovação administrativa; capabilities declarativas por igualdade e recursos mínimos; pool de confiança fixado pelo servidor

**invariants:** agente não escolhe projeto/pool privilegiado

**errors:** UnauthorizedAgent, Incompatible, ProtocolMismatch

**intended_guarantees:** nenhum job em executor incompatível

**dependencies:** R03

**features:** F04, F05

**evidence:** TC-008, TC-009, D-MATCH

**acceptance:** A04

## R05 — Reserva exclusiva com lease [CORE]

**motivation:** Resolver: Executar onde existem recursos e ferramentas necessários.

**inputs:** job elegível, slot livre e relógio do banco

**outputs:** attempt_id, fence e expires_at

**rules:** serializar reserva do slot e job; um lease ativo por job e slot; TTL 30s e renovação a cada 10s

**invariants:** fence cresce; escrita obsoleta nunca altera resultado

**errors:** Busy, LeaseExpired, StaleFence

**intended_guarantees:** at most one current owner; não exactly once de processo

**dependencies:** R03, R04

**features:** F04, F09

**evidence:** TC-005, TC-004

**acceptance:** A05, A07

## R06 — Executar steps e revisão fixada [CORE]

**motivation:** Resolver: Executar comandos reproduzíveis e propagar falha.

**inputs:** assignment, argv, ambiente permitido, limites

**outputs:** exit code e status por step

**rules:** checkout em executor; sem shell implícito; env_clear e allowlist; workspace único; parar após falha

**invariants:** subprocesso não recebe credenciais do agente

**errors:** SpawnFailed, CheckoutFailed, Timeout, NonzeroExit

**intended_guarantees:** resultado explícito; filhos recolhidos

**dependencies:** R05, R02

**features:** F06, F11

**evidence:** TC-010, D-STEPS, D-VCS

**acceptance:** A08

## R07 — Logs com limite e retomada [CORE]

**motivation:** Resolver: Explicar resultado e disponibilizar evidência ao usuário.

**inputs:** attempt/fence, stream, seq, bytes

**outputs:** ack durável por sequência

**rules:** máximo 64KiB por chunk, spool 64MiB e 100MiB por tentativa; overflow falha explícita; dedup por hash

**invariants:** ack não precede persistência; mesmo seq diferente é conflito

**errors:** StaleFence, ChunkConflict, LogQuotaExceeded

**intended_guarantees:** sem duplicação lógica; gaps visíveis

**dependencies:** R05, R06

**features:** F07

**evidence:** D-LOG

**acceptance:** A09

## R08 — Publicar artefatos verificáveis [CORE]

**motivation:** Resolver: Vincular resultado aos produtos do build.

**inputs:** manifesto de caminhos relativos, tamanho, SHA-256 e bytes

**outputs:** objetos staged/committed

**rules:** rejeitar symlink e traversal; 1GiB por tentativa; commit somente após validar objetos; download autorizado

**invariants:** objeto de outro projeto nunca acessível por digest isolado

**errors:** DigestMismatch, QuotaExceeded, InvalidPath

**intended_guarantees:** sucesso exige artefatos obrigatórios confirmados

**dependencies:** R05, R06

**features:** F08

**evidence:** TC-012, D-ART

**acceptance:** A10

## R09 — Concluir com idempotência [CORE]

**motivation:** Resolver: Vincular resultado aos produtos do build.

**inputs:** attempt/fence, resultado, último seq e manifest_digest

**outputs:** resultado terminal versionado

**rules:** CAS sob lease vivo; repetição idêntica retorna recibo; divergente é conflito; arbitrar cancelamento na mesma linha

**invariants:** um resultado terminal por job; terminal imutável

**errors:** StaleFence, ResultConflict, MissingOutput

**intended_guarantees:** resultado lógico único, eventos de auditoria atômicos

**dependencies:** R07, R08

**features:** F08, F09

**evidence:** TC-012, TC-011

**acceptance:** A11

## R10 — Recuperar falhas sem ocultar ambiguidade [CORE]

**motivation:** Resolver: Interromper trabalho e tolerar falhas de comunicação.

**inputs:** restart, lease vencido, política retry

**outputs:** Lost ou nova tentativa

**rules:** registrar perda; repetir só com retry_safe explícito e limite; sem essa política, Lost terminal exige ação humana

**invariants:** tentativa antiga não finaliza nova; efeito externo pode duplicar

**errors:** DatabaseUnavailable, RetryLimit

**intended_guarantees:** entrega pelo menos uma vez; efeitos externos sem exactly once

**dependencies:** R05, R09

**features:** F09

**evidence:** TC-018, TC-012

**acceptance:** A06, A07, A12

## R11 — Cancelamento e timeout [CORE]

**motivation:** Resolver: Interromper trabalho e tolerar falhas de comunicação.

**inputs:** pedido autorizado ou deadline

**outputs:** CancelRequested e depois Cancelled/TimedOut

**rules:** persistir intenção; negar conclusão concorrente após intenção; matar grupo/cgroup e reap; deadline efetivo limita lease

**invariants:** reconhecimento da API não implica processo já parado

**errors:** AlreadyTerminal, Forbidden, KillFailed

**intended_guarantees:** intenção durável e terminalização auditável

**dependencies:** R05, R09

**features:** F09

**evidence:** TC-011

**acceptance:** A11, A12

## R12 — Isolamento e segredos do piloto [CORE]

**motivation:** Resolver: Conectar executores ao orquestrador e transferir trabalho.

**inputs:** pool de confiança, executor, secret references

**outputs:** processo limitado e credenciais efêmeras

**rules:** piloto só código confiável; nenhum socket Docker do host no job; segredo por projeto/tentativa; revogar após término

**invariants:** credencial de agente fora do workspace; logs não garantem eliminar exfiltração

**errors:** IsolationUnavailable, SecretDenied

**intended_guarantees:** execução recusada quando política não satisfeita

**dependencies:** R04, R06

**features:** F05, F06

**evidence:** D-AGENT, D-AUTH

**acceptance:** A13

## R13 — Auditoria e retenção [CORE]

**motivation:** Resolver: Explicar resultado e disponibilizar evidência ao usuário.

**inputs:** eventos, principal e política

**outputs:** histórico, métricas e GC

**rules:** auditoria transacional sem segredos; retenção piloto 30 dias; GC somente objetos sem referência e fora da janela de upload

**invariants:** GC não remove objeto referenciado por execução retida

**errors:** StorageUnavailable, AuditFailure

**intended_guarantees:** pós-restart mantém histórico; gaps log explícitos

**dependencies:** R03, R09

**features:** F07, F13

**evidence:** D-LOG, D-GC

**acceptance:** A03, A14

## R14 — DAG de jobs [IMPORTANT]

**motivation:** Resolver: Coordenar trabalho paralelo sobre entradas consistentes.

**inputs:** jobs e depends_on

**outputs:** execução paralela dos elegíveis

**rules:** revisão fixada por run; rejeitar ciclo; dependência failed cancela descendente como Skipped; limite por projeto

**invariants:** job só elegível após todos predecessores Succeeded

**errors:** Cycle, DependencyFailed

**intended_guarantees:** nenhum sucessor inicia cedo

**dependencies:** R02, R05, R09

**features:** F10

**evidence:** D-DAG

**acceptance:** A15

## R15 — Triggers Git [IMPORTANT]

**motivation:** Resolver: Iniciar builds sobre alterações relevantes.

**inputs:** hook assinado ou polling com cursor

**outputs:** pedido deduplicado por repo/revisão/pipeline

**rules:** validar assinatura, branch e permissão; persistir cursor com pedido; revisão resolvida uma vez

**invariants:** retry do hook não cria run extra

**errors:** SignatureInvalid, RevisionUnavailable

**intended_guarantees:** polling recupera hook perdido

**dependencies:** R01, R06

**features:** F11

**evidence:** D-VCS

**acceptance:** A16

## R16 — Múltiplos agentes e equidade por projeto [IMPORTANT]

**motivation:** Resolver: Executar onde existem recursos e ferramentas necessários.

**inputs:** pools e quotas

**outputs:** distribuição justa

**rules:** round-robin entre projetos ativos; FIFO dentro do projeto; quota configurável

**invariants:** não superar slots/limites; espera medida só para elegíveis

**errors:** CapacityUnavailable

**intended_guarantees:** nenhum projeto elegível ignorado por mais de um ciclo finito

**dependencies:** R05, R14

**features:** F04

**evidence:** TC-005, D-QUEUE

**acceptance:** A17

## R17 — Cache com domínio de confiança [ADVANCED]

**motivation:** Resolver: Conter armazenamento e evitar trabalho repetido.

**inputs:** namespace, key, digest e política

**outputs:** cache hit verificado ou miss

**rules:** chave inclui entradas/toolchain; PR não confiável não escreve cache de release

**invariants:** cache é otimização, miss mantém correção

**errors:** DigestMismatch

**intended_guarantees:** cache corrupto nunca aceito silenciosamente

**dependencies:** R08, R12

**features:** F13

**evidence:** D-CACHE

**acceptance:** A18

## R18 — Executar código não confiável [IMPORTANT]

**motivation:** Resolver: Executar comandos reproduzíveis e propagar falha.

**inputs:** política OCI endurecida ou VM

**outputs:** job isolado ou recusa

**rules:** gate com testes de escape de filesystem, rede, privilégios, recursos e segredos; hosts efêmeros

**invariants:** sem fallback para processo irrestrito

**errors:** IsolationUnavailable

**intended_guarantees:** limites aplicados externamente ao subprocesso

**dependencies:** R12

**features:** F06

**evidence:** D-AGENT

**acceptance:** A13

## R19 — Extensibilidade externa e UI [DEFERRED]

**motivation:** Resolver: Integrar sistemas e acrescentar capacidades.

**inputs:** contratos versionados e eventos

**outputs:** integrações sem código no controle

**rules:** extensão em processo separado; UI usa API autorizada

**invariants:** plugin não lê memória de controle

**errors:** ProtocolMismatch

**intended_guarantees:** ABI Rust dinâmica não exigida

**dependencies:** R01, R09

**features:** F12

**evidence:** TC-014

**acceptance:** A19

## R20 — Compatibilidade JVM/DSL/protocolo privado [REJECTED]

**motivation:** Resolver: Integrar sistemas e acrescentar capacidades.

**inputs:** solicitação de importar internals

**outputs:** erro de capacidade não suportada

**rules:** não carregar plugin JVM, compilar Kotlin ou espelhar protocolo interno

**invariants:** nenhum código proprietário incorporado no produto

**errors:** UnsupportedCompatibility

**intended_guarantees:** especificação independente

**dependencies:** 

**features:** F12

**evidence:** TC-014, TC-016

**acceptance:** A20
