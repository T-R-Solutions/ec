# Tipos e transições

IDs são newtypes, deadlines usam tipo próprio e fence inteiro monotônico checado contra overflow.
Enuns com payload evitam Running sem AttemptId/Lease. Campos privados e construtores validados.
Erros recuperáveis são Result; ausência explícita usa Option. Sem unsafe, hierarquia de herança ou estado global mutável.
Ownership: supervisor possui child handles; writer possui transações; mensagens carregam IDs e valores imutáveis.

| Entidade | Transições válidas | Regra de falha/recuperação |
|---|---|---|
| Run/build | Accepted → Queued → Running → Succeeded/Failed/Cancelled/Lost | agregado dos jobs; terminal imutável |
| Job | Blocked → Queued → Leased → Running → Uploading → Succeeded/Failed | Blocked só libera por pré-requisitos; erro de infraestrutura vira Lost |
| Job cancelado | Queued → Cancelled; ativo → CancelRequested → Cancelled/TimedOut | expiração durante cancelamento encerra sem reagendar |
| Pipeline | Validated → Active → Succeeded/Failed/Cancelled | ciclo inválido nunca entra em Validated |
| AgentSession | Pending → Approved → Online → Draining/Offline/Revoked | sessão nova não herda lease de sessão velha automaticamente |
| Lease | Active → Expired/Released/Revoked | terminal; renovar somente Active antes de expirar |
| Attempt/execution | Assigned → Started → Uploading → Finished/Lost/Cancelled/TimedOut | retry cria outro ID e fence |
| Cancellation | Absent → Requested → Acknowledged → Completed | conclusão anterior prevalece; confirmação API não é morte física |
| Artifact | Declared → Uploading → Staged → Verified → Committed → Deleted | falha vira Rejected; GC só sem referências |

Qualquer outra transição é InvalidTransition. Complete direto de Running pode ser permitido quando não há arquivos,
mas ainda verifica manifesto vazio e logs. Retry não reabre Attempt: Job volta a Queued apenas com política aprovada.
Em Lost sem retry, Run termina Lost; não confundir falha de infraestrutura com exit code do build.
Representação serializada possui schema_version; DB valida relações e domínio valida transições completas.
O protótipo cobre somente a submáquina Queued/Leased/CancelRequested/Terminal, sem prometer a implementação destes estados todos.
