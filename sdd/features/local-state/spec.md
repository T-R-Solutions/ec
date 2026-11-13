# Estado local: duas datas de logs

Autoridade: pedido explícito de 11/11/2026. Substitui a retenção de archives,
backups e baseline prevista em 10/11/2026. Atores: mantenedor, runner e ferramentas.
Não altera contratos de produto, PostgreSQL ou autenticação.

REQ-01: inventariar `.local` e eliminar backups, extrações e intermediários
persistidos. Mover fontes originais únicas para seu destino canônico; não criar
cópias de segurança.

- AC-01 → REQ-01: somente `logs/` permanece em `.local`, originais referenciados continuam acessíveis, e backups identificados são removidos.

REQ-02: scripts conservam somente logs por ferramenta em
`.local/logs/AAAA-MM-DD/`, com identificador único da execução. Saídas brutas e
registros de classificação/exit são distinguíveis.

- AC-02 → REQ-02: consumidores usam os caminhos novos; rascunhos de teste, fontes de scanner e builds são transitórios, sem baseline duplicada necessária à auditoria instalada.

REQ-03: reter no máximo duas datas de início, sob lock cooperativo separado das
datas. Antes da terceira, excluir a mais antiga.

- AC-03 → REQ-03: repetir a data não apaga logs; terceira expira só a mais antiga; rollback de relógio, symlinks e conteúdo inesperado falham sem exclusão indevida. EXIT limpa intermediários em erro e sucesso; ausência de exit/limpeza não pode ser interpretada como aprovação. SIGKILL/queda do host têm limite explícito, sem garantia de limpeza automática.

REQ-04: atualizar guias, AGENTS e SDD sem transformar evidência histórica em
validação atual.

- AC-04 → REQ-04: política vigente, perdas de retenção, fontes, pré-requisitos offline e verificações não executadas estão documentados.
