# Scheduler independente

Unidade: Job elegível; Attempt é identidade física, Step não ocupa slot independente.
CORE: um projeto/pool e FIFO por enqueue_seq entre compatíveis. IMPORTANTE: round-robin persistido entre projetos,
FIFO interno, quotas por projeto. Critério de desempate determinístico: enqueue_seq e job_id.
Compatibilidade: conjunto explícito de labels igualdade + CPU/memória/disco mínimos + política de confiança do servidor.
Não avaliar código/regex arbitrário durante matching; agentes não autenticam privilégios pela própria declaração de capabilities.

| Alternativa | Benefício | Limitação / decisão |
|---|---|---|
| FIFO | simples, espera por ordem | usar entre elegíveis; jobs incompatíveis não bloqueiam todos |
| Prioridades | urgência | starvation sem aging; adiar |
| Fair round-robin | acesso por projeto | escolher na ampliação; um job longo ainda ocupa slot |
| Weighted fair | quotas proporcionais | adiar até necessidade multitenant medida |
| Capacidade / best fit | aproveitamento de recursos | filtrar requisitos primeiro; otimização posterior |
| Duração estimada | pode reduzir makespan | erro, cold start e starvation; exige workload/benchmark |

Reserva: transação bloqueia slot livre e job elegível, revalida limites, incrementa fence e cria Attempt/Lease.
Locks sempre na mesma ordem (slot, quota do projeto quando houver, job); workers usam SKIP LOCKED e repetem candidatos.
Não selecionar lote pequeno fixo que possa ocultar candidatos compatíveis indefinidamente: paginar elegíveis até achar candidato
ou orçamento por rodada; cursor avança e é retomado, com espera diagnosticada. Capacidade ignorada é métrica de bug.
Fairness só é garantida com conjunto finito de projetos elegíveis, serviço recorrente e capacidade compatível disponível.
TTL inicial 30s, heartbeat 10s; valores são hipóteses do piloto, não números derivados do TeamCity.

Expiry worker usa timestamp do banco, bloqueia job e slot e marca Lost/Expired; retry_safe permite nova tentativa com backoff
e máximo 2 tentativas no piloto. Lease expirada nunca é renovada. Complete e expiry competem pelo mesmo lock.
Mensagens duplicadas são esperadas. Fencing vale para logs, resultado e commit de manifesto, não apenas assignment.
Entrega pelo menos uma vez; execução física pode duplicar; resultado aceito tem uma autoridade corrente.
Métricas: latência p50/p95/p99 de elegível→lease, idade por projeto, incompatibilidade, leases vencidas e rejeições stale.
