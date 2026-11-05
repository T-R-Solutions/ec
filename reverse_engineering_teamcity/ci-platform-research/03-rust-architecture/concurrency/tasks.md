# Concorrência, supervisão e limites

Tokio somente para I/O: API, poll, heartbeat, pipes e uploads. Domínio é síncrono.
Supervisor mantém JoinSet de tasks, propaga cancelamento e espera término; panic de worker é observado e reinicia com backoff limitado.
Channels bounded por bytes e quantidade: defaults piloto 128 mensagens e 8MiB de dados por stream; limites precisam medição.
Spool em disco tem quota própria. Nunca soltar logs silenciosamente; bloquear com deadline e falhar LogQuotaExceeded.
Semáforo limita uploads e spawn_blocking; hashes/arquivos grandes processados em chunks e pool bloqueante limitado.
Não manter locks Rust através de await; locks SQL apenas em transações curtas sem rede, spawn ou upload dentro.
Heartbeat usa pool reservado/limites separados para não ser bloqueado por upload. Controle usa relógio do banco;
agente converte tempo restante em deadline monotônico conservador. TTL expira localmente mesmo sem resposta do servidor.
No shutdown: parar novos polls, sinalizar filhos, aguardar prazo, kill grupo/cgroup e reap; lease não é liberado antes de parada confirmada.
Arc é só compartilhamento de handles imutáveis/pool; evitar Arc<Mutex<domínio inteiro>>.
