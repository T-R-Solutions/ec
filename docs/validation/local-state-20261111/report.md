# Estado local — análise, implementação e limpeza

Pedido de 11/11/2026: somente logs por ferramenta em duas datas, sem backups.
Inspeção: leitura das autoridades, produtores shell/Python, testes, caminhos de
harnesses e fixtures de governança; inventário de disco e classificação de áreas.
Mudanças já existentes foram consideradas; não houve reset, commit ou publicação.

Antes da limpeza, `du -sb .local` observou 16.963.524.850 bytes aparentes.
Após a limpeza, observou 7.099.197 bytes (16 MiB alocados segundo `du -sh`,
em milhares de logs pequenos). Cerca de 16,96 GB aparentes foram removidos.
Os números descrevem essa manutenção, não desempenho ou resultado de teste.

| Área anterior | Classificação e destino |
| --- | --- |
| automation/runs | 59 execuções em cinco datas; manter logs de 09/11 e descartar artefatos/datas anteriores |
| build | caches Cargo/npm, targets e staging; removidos |
| archive | históricos de manutenção e cópias de recuperação; removidos |
| skill-installation | duplicata do catálogo instalado; removida |
| analiser | fontes originais tratadas abaixo; extrações e duplicata removidas |
| usage.md | índice da estrutura aposentada; removido |

Foram migrados por rename 3.544 logs/registros de execução. Restaram as datas
2026-11-09 e 2026-11-11, esta última contendo o registro da limpeza. Os logs
históricos mantêm conteúdo original; sua mudança de caminho não os torna
verificação atual. Data legada deriva de finished_at, com fallback documentado
para mtime de results.json/diretório quando ausente. A aplicação futura usa a
data de início, não mtime.

Foram removidos 83 arquivos de backup em docs/validation: previous ZIP/tar,
cópias previous de documentos/evidências, before/ e snapshots .bin de instalação.
Hashes/manifests de observações e relatórios originais foram mantidos. Referências
datadas a backups excluídos registram o passado; não comprovam disponibilidade.
Nenhuma cópia de segurança nova foi criada. Git/GitHub só recupera revisões que
estejam efetivamente disponíveis; não foi presumido upload de alterações locais.

O registro detalhado de exclusões está no log efêmero
`.local/logs/2026-11-11/local-cleanup.log` e expira pela mesma política.
Não foi criado arquivo durável com os conteúdos excluídos.

## Fontes únicas

- `docs/design/sources/ec-signal-visual.zip`: original movido de .local/analiser;
  membro ec-signal-visual/DESIGN.md observado no índice ZIP.
- `config/governance/sources/SKILLS-DESIGN-v4.0.0.zip`: cópia canônica já existente;
  igualdade de bytes com a entrada local conferida antes da remoção da duplicata.
- `.codex/skills/` e perfil/pontes continuam sendo a instalação vigente; nenhuma
  skill foi excluída. Fixtures dos testes passam a derivar desta instalação.

## Implementação e limites

Runner usa logs planos por data, stdout/stderr por ferramenta e registros
estruturados de resultados, hashes e limpeza. O lock fica fora das datas;
terceira data expira a mais antiga. Builds, snapshots de execução e relatórios
são temporários por run, descartados no EXIT. Os harnesses recebem os caminhos
dos binários transitórios; Vite entrega o build do mesmo run ao Playwright e usa
cache transitório quando iniciado pelo runner. Exportação/limpeza malsucedida retorna erro.
INT/TERM/HUP mantêm tratamento de cancelamento; SIGKILL/queda do host podem
exigir remoção manual de temporários após inspeção dos processos.

A remoção de caches locais pode impedir compilação offline até que dependências
já autorizadas estejam disponíveis no CARGO_HOME do usuário. Nenhum download
foi realizado. A nova retenção reduz a disponibilidade histórica de diagnósticos
e não constitui promessa de limite de bytes ou auditoria durável.

Testes foram escritos/adaptados para retenção, repetição de data, relógio regressivo,
links, conteúdo inesperado, descarte de artefatos, bytes brutos, lock e exportação.
**Nenhum script de verificação, teste, lint, typecheck, build, auditor SDD ou suíte
foi executado.** A limpeza real foi uma operação de manutenção autorizada,
separada da implementação do runner ainda não exercitada. AC-01 tem observação
de limpeza; demais critérios têm inspeção/escrita e continuam não verificados
como conjunto. A validação segue task-verification.md.

## Skills consultadas

- `.codex/skills/tool-output-engineering/SKILL.md`: SHA-256 `90108fc6932bb514bbaaf320117d3727358d3c98afd4130f70a6c21e328d3ad6`.
- `.codex/skills/workspace-integrity-engineering/SKILL.md`: SHA-256 `bcc2b63eef177063847e7a95a433fe95bef59cccf622ff2963a55d9d4d0e9d01`.

Contagem de arquivos removidos nas áreas de .local: 502200; symlinks foram tratados sem seguir destinos.
