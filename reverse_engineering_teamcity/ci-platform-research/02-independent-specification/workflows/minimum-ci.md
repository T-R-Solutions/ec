# Primeira fatia: do pedido ao resultado

1. API autentica e autoriza principal no projeto; recebe JSON v1, revisão Git fixa e chave idempotente.
2. Valida estrutura, limites, referências e compatibilidade de schema. Erro retorna 4xx sem execução parcial.
3. Transação cria Run, Job Queued, revisão imutável e auditoria; só então responde 202 com run_id.
4. Agente autorizado faz poll; scheduler escolhe job compatível e slot livre.
5. Na mesma transação bloqueia slot e job, cria Attempt, incrementa fence e registra lease/atribuição.
6. Poll retorna assignment; se resposta se perder, mesmo slot/sessão recebe a mesma atribuição viva.
7. Agente confirma início com fence; prepara workspace e checkout; nunca executa antes desse ack.
8. Steps executam argv em ordem com ambiente permitido e limites. stdout/stderr usam pipes drenados em paralelo.
9. Chunks numerados vão ao spool e servidor; ack é durável. Overflow gera falha explícita.
10. Agente sobe arquivos declarados a staging, servidor valida hash/tamanho/caminhos e confirma manifesto.
11. Complete inclui outcome, last_seq e manifest_digest. Sob lock/lease válido, verifica outputs e terminaliza.
12. Audit entry e release de slot são atômicos ao terminal. Agente recebe recibo e limpa workspace.

Crash antes de commit: retry seguro da API. Crash depois de commit: dedup retorna recibo.
Rede perdida: não iniciar trabalho novo sem ack; execução ativa obedece deadline local conservador.
Lease expirada: servidor marca Lost; retry exige política explícita. Artefatos antigos ficam sem publicação corrente.
Processo que já causou efeito externo pode continuar sob partição; usar isolamento e idempotência no destino.
