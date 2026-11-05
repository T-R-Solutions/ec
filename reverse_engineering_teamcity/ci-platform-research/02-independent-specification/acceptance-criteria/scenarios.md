# Aceitação do produto futuro

**A01 — Repetir submissão:** 10 pedidos mesma chave e payload criam um run; payload diferente retorna 409.

**A02 — Configuração inválida:** ciclo, campo desconhecido, caminho ../, segredo literal e tamanho excedido retornam 422 sem jobs.

**A03 — Durabilidade e auditoria:** reiniciar após 202 mantém fila/histórico; falha no commit retorna 503 sem ack.

**A04 — Autorização e compatibilidade:** agente não aprovado, pool incorreto e labels incompatíveis recebem zero atribuições.

**A05 — Reserva concorrente:** 100 polls simultâneos em 2 schedulers reais: um owner por job/slot; índices únicos e transações preservados.

**A06 — Restart e resposta perdida:** matar controle antes/depois do commit; poll repetido retorna assignment vivo ou trabalho pendente correto.

**A07 — Fencing e expiração:** expirar lease e reatribuir; complete/log/upload do fence antigo recebem 409 sem mutar estado.

**A08 — Processos:** step não zero impede próximo; timeout mata descendentes; checkout fixa commit; env herdado não vaza.

**A09 — Logs sob falha:** duplicar chunk idêntico retorna ack; bytes divergentes conflitam; gap impede sucesso; spool cheio falha com razão.

**A10 — Artefatos:** hash divergente, symlink, traversal, quota e acesso cruzado recusados; sucesso só com manifesto confirmado.

**A11 — Cancelamento versus conclusão:** testar as duas ordens de commit; intenção persistida primeiro impede sucesso; conclusão primeiro retorna AlreadyTerminal.

**A12 — Partição e retry:** agente some; lease vence; Lost por padrão; retry_safe cria nova tentativa limitada; destino externo pode ver duplicação.

**A13 — Segurança:** processo tenta ler credencial do agente, montar host, usar socket Docker, esgotar RAM/CPU e acessar segredo de outro projeto; política recusa ou contém.

**A14 — Retenção e backup:** GC durante upload não apaga objetos vivos; restore de banco+objetos reproduz resultados e detecta objetos faltantes.

**A15 — DAG:** A→{B,C}→D: D aguarda ambos; falha em A gera Skipped; ciclo rejeitado antes de enqueue.

**A16 — VCS:** hook duplicado cria um run; assinatura inválida rejeitada; polling recupera revisão perdida.

**A17 — Fairness:** 3 projetos, backlog contínuo e slots compatíveis: todos recebem turno em cada ciclo de round-robin.

**A18 — Cache:** digest corrupto vira miss; pool não confiável não publica em namespace de release.

**A19 — Extensões futuras:** versão incompatível não carrega; crash da extensão não termina controle.

**A20 — Independência:** entrada de plugin JVM/Kotlin devolve Unsupported; produto não incorpora classes ou pseudocódigo TeamCity.

São contratos; testes do protótipo são um subconjunto explicitamente separado em 04-validation/findings.
