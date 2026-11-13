# Estado local: somente logs de duas datas

Política solicitada em 11/11/2026, substituindo a organização de 10/11/2026.
Em `.local/` deve existir somente `logs/AAAA-MM-DD/`, com arquivos `.log` por
ferramenta. Não criar backups, baselines duplicadas, extrações ou arquivos de
recuperação no checkout. Recuperação de código depende da revisão disponível no
Git/GitHub; arquivos não publicados não são recuperáveis apenas por existir Git.

O runner escolhe a data local do host no início da execução. Mantém no máximo
duas datas distintas: antes de abrir a terceira, exclui a mais antiga. Várias
execuções no mesmo dia não apagam umas às outras. Uma execução atravessando a
meia-noite permanece na data de início; relógio anterior à última data existente
causa erro, sem apagar logs. Retenção de duas datas não é limite de bytes.

Os nomes incluem o identificador aleatório da execução, ordem, projeto e
ferramenta: `run-ID-05-server-clippy.stdout.log`, `.stderr.log` e `.result.log`.
Saídas brutas permanecem separadas e sem alteração de bytes. Logs estruturados
`run-ID-automate.results.log`, `.inputs.log`, `.inputs-after.log` e `.exit.log`
registram classificação, hashes, código de saída e limpeza. Não são backups de
fontes. Chamadas nativas e membros Geiger conservam seus logs individuais. Relatórios
nativos JSON/SARIF são exportados como .log; scanners de segredos exigem redação
antes da exportação. Recibos de serviços e resultados da jornada de browser
também são logs; mensagens privadas do controle do navegador são descartadas.
Não se deve considerar execução aprovada sem o exit final bem-sucedido: falhas
de exportação ou limpeza retornam erro. Logs podem conter informações privadas;
diretórios são 0700 e arquivos novos são 0600.

O lock cooperativo está em `/tmp/ec-automation-UID-HASH/workspace.lock`, separado
das datas que expiram. Builds Rust/frontend, snapshots de trabalho dos scanners, caches
npm, relatórios, screenshots e artefatos intermediários ficam em `run-ID/` nesse
diretório temporário e são removidos no encerramento, inclusive em falhas normais.
Snapshots de entrada existem somente para a execução isolada da ferramenta;
não são cópias de segurança. O lock não é apagado enquanto processos possam
utilizar seu inode. Caches Cargo já preparados pelo usuário são consumidos de
`CARGO_HOME` ou `~/.cargo`; não existe mais cache Cargo local duplicado.
Downloads continuam exigindo autorização específica, e falta de dependência
offline não autoriza download nem equivale a aprovação.

O supervisor trata INT/TERM/HUP e executa exportação/limpeza no EXIT. SIGKILL,
queda do host ou falha de filesystem podem deixar temporários em `/tmp`; a
recuperação exige inspecionar processos e remover apenas o workspace órfão.
Não apagar automaticamente trabalho possivelmente ativo. A retenção recusa
links, conteúdo inesperado e mounts nas datas; protege escritores cooperantes,
sem alegar isolamento contra alterações maliciosas pelo mesmo usuário.

`scripts/local-state.py` é auxiliar do runner (`runtime-path`, `prepare`,
`finish`); operações de mutação exigem seu lock herdado. Não é um verificador.
Não há mais archive, baseline ou journal de retenção persistente fora dos logs.
Os testes usam fixtures descartáveis no diretório temporário de cada teste.

As fontes originais de skills permanecem em `config/governance/sources/` por
proveniência; o ZIP original visual foi movido para
`docs/design/sources/ec-signal-visual.zip`. São entradas canônicas, sem extração
duplicada. A instalação vigente está somente em `.codex/skills/`.

[Registro da mudança](../validation/local-state-20261111/report.md).
Suíte, testes e validadores não foram executados; seguir
[task-verification.md](task-verification.md).
