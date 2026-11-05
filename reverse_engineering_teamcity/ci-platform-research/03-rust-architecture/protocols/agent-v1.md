# Protocolo próprio v1

HTTPS; JSON para metadados e bytes para chunks/objetos. Limites explícitos, request_id e correlation_id sem segredos.
Bootstrap de agente usa token de uso único entregue por administrador; cria sessão aprovada e credencial curta por pool.
Sessão fica vinculada a AgentId e pool; rotação/revogação fecha polls e impede renovar leases. Usuário usa token com escopo por projeto.
Versionamento `/v1`; handshake negocia schema e capabilities suportadas, versão incompatível recebe erro antes de assignment.

| Operação | Contrato |
|---|---|
| POST /v1/runs | chave de idempotência por projeto + config_revision + source_revision; 202 só após commit |
| GET /v1/runs/{id} | autorizado por projeto, estado versionado e tentativas |
| POST /v1/agents/handshake | sessão autenticada, capacidades e limites aceitos pelo controle |
| POST /v1/agents/poll | session/slot; long poll até 20s; assignment vivo repetível por sessão/slot |
| POST /v1/attempts/{id}/start | fence + sessão; ack válido obrigatório antes de spawn |
| POST /v1/attempts/{id}/heartbeat | fence; renova só antes de expiry; retorna deadline e cancel intent |
| PUT /v1/attempts/{id}/logs/{stream}/{seq} | fence + hash + bytes; duplicata igual ack, diferente 409 |
| PUT /v1/attempts/{id}/objects/{digest} | fence, tamanho e bytes com limites; staging |
| POST /v1/attempts/{id}/manifest | fence + caminhos relativos e digests verificados |
| POST /v1/attempts/{id}/complete | fence + outcome + last_seq + manifest_digest; recibo persistido |
| POST /v1/runs/{id}/cancel | autorização e chave; intenção durável; retorna 202, sem alegar morte física |

Todas mutações de tentativa vinculam (agent_session, attempt_id, fence). Retries de rede com jitter exponencial limitado
entre 250ms e 5s não podem superar deadline do lease. 409 StaleFence encerra execução local; 429 respeita Retry-After;
503 permite retry dentro do prazo. Resposta perdida de complete retorna recibo idêntico mesmo após expiry;
conteúdo divergente é ResultConflict. Progresso não é autorização para continuar além do lease.
No CORE agente não assume trabalho de sessão anterior após restart: espera reconciliação/expiry e encerra órfãos locais.
