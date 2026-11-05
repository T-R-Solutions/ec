# Persistência e recuperação

Schema independente proposto: projects; pipeline_revisions; runs; jobs; attempts; agent_sessions;
slots; leases; submission_keys; log_chunks; artifact_manifests; artifact_objects; audit_events.
Não reproduz tabelas do TeamCity. IDs externos opacos; FK por projeto quando houver escopo.
Constraints: chave única (project,idempotency_key); unique parcial de lease Active por job e slot;
unique (attempt,stream,seq); unique attempt/result receipt; fence e version checados em updates.
Unique parcial não avalia tempo: expiry muda Active para Expired sob lock antes de uma reserva nova.

Transações: submissão+auditoria; reserva+attempt+lease+slot; renovação; conclusão+auditoria+release;
cancelamento; expiração+retry. READ COMMITTED com locks explícitos e predicates reavaliados.
Operações leem relógio DB após adquirir locks (clock_timestamp), não timestamp antigo do início de transação.
Ordem uniforme slot→quota→job para operações que tocam múltiplas linhas; consultas isoladas não invertem essa ordem.
CAS verifica job.version, attempt_id, fence, session_id, status e expires_at > clock_timestamp().
Nenhuma chamada externa ocorre dentro de transação de domínio. Deadlock/serialization failure tem retry limitado.

Logs/objetos: escrever temporário, fsync arquivo, rename e fsync diretório; só então gravar índice SQL e ack.
Se crash antes de índice: sobra órfão para GC. Se índice confirmar: bytes duráveis já existem.
Uploads são por tentativa; hash de conteúdo não concede acesso. Manifesto vira Committed na transação de conclusão,
após verificar objetos staged. Sucesso espera logs confirmados até last_seq, inclusive stdout/stderr declarados.
Storage indisponível impede sucesso e ack; quota encerra com razão explícita. Retenção inicial 30 dias, configurável.
GC marca candidato sem referência, espera janela maior que upload/lease e revalida sob coordenação antes de apagar.

Restart: reconstruir fila de jobs; expirar leases pelo relógio DB; reconciliar upload/objetos órfãos;
retry nunca perde tentativa anterior. Banco indisponível: sem leases novos, sem renovação/resultado; agente para no deadline.
Migrations expand/contract com backup e teste de restore; schema incompatível recusa startup.
Backup registra ponto do DB e snapshot de objetos; restore verifica referências e reporta missing objects.

Eventos: audit_events na mesma transação; UI lê SQL com cursor. Notificações em memória só aceleram poll.
Não há broker nem necessidade de outbox no CORE. Se webhooks externos exigirem entrega confiável,
adicionar outbox transacional com consumidor idempotente e retenção; sem event sourcing integral.
