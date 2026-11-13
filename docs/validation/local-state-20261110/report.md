# Organização e limpeza local — 10/11/2026

Implementadas organização, manutenção com retenção por encerramento e atualização
documental. Nenhuma suíte, script de verificação, commit, publicação ou download
foi executado. A CLI operacional de limpeza foi simulada e aplicada por autorização
deste pedido; isso não substitui testes ou aceite integrado.

## Levantamento e decisões

Leitura das autoridades, workflow, ADR-001/002, produtores em scripts, ferramentas e
harnesses; inventário de arquivos regulares nas áreas de aplicação, documentação,
configuração, SDD, pesquisa e diretórios ocultos. `inventory.json` registra contagem
e bytes lógicos, excluindo dependências/builds por nomes comuns (`node_modules`,
`target`, `dist`, `.git`, `__pycache__`). Não representa auditoria semântica de cada
linha nem prova de inexistência de outros artefatos obsoletos. Não coletados valores
de credenciais; `.aws` e `.agents` não tinham arquivos regulares no levantamento.

Comparação SHA-256 de arquivos não vazios abaixo de 2 MB nas áreas de código,
configuração e entradas auxiliares identificou somente duplicações intencionais:

| Candidato | Papel/produtor e consumidores | Decisão |
| --- | --- | --- |
| rust-toolchain.toml Server/Agent iguais | Rustup/Cargo, workspaces independentes | Preservar contratos independentes |
| Três pares de snapshots stderr Geiger iguais | Fixtures vendorizadas, testes upstream distintos | Preservar oráculos por cenário |
| `.codex/skills` e baseline local | Instalador/perfil de governança, recuperação | Preservar conforme ADR-001 |
| Pacote `technical-documentation/` na raiz | Entrada importada com recursos próprios; diferente da skill adaptada | Preservar, sem substituição comprovada |
| Pesquisa TeamCity, índices e relatórios datados | Pesquisa e query.py; evidência histórica | Preservar; tamanho/idade não demonstram obsolescência |
| Builds Geiger e targets de cobertura por run | rust/check.sh e collect_runtime_coverage.py; executáveis temporários do próprio run | Remover após encerramento; fonte, locks e dependências preservados |
| Caches Cargo/npm e targets compartilhados | Runner e harnesses, modo offline | Preservar; não aplicar quota/idade sem plano de reconstrução |
| Scripts de manutenção, recuperações e materiais Toolbox retirados | Produtores históricos, sem chamada no runner vigente | Reunir em archive; preservar fontes e diagnósticos |
| Runs incompletos e `run-TdsIKMPf` inválido | Supervisor histórico, resultado não suficiente para expiração | Preservar; nenhuma inferência de término por mtime |

O levantamento de referências inclui produtores e consumidores ativos em `scripts/`,
`tools/`, `application/` e configurações. Documentos e logs históricos registram
caminhos da coleta, traduzidos por `moves.json`. Scripts arquivados com caminhos
antigos não foram promovidos a comandos operacionais. Não há prova de que consumidores
externos desconhecidos tenham sido migrados; reativação requer consulta ao mapa.

## Alterações

- `.local/` passou a reunir `build`, `automation`, `archive`, `skill-installation`
  e a entrada `usage.md`. O diretório `tools`, inventariado vazio, foi removido.
- Materiais de tarefas anteriores saíram da raiz e de `build/` para `archive/`.
  Foram movidos, sem criar cópias permanentes. `moves.json` registra cada origem.
- `tracked-moves.json` registra SHA-256 de 578 arquivos antes versionados; comparação
  adicional com os blobs do índice observou zero diferenças de conteúdo. O estado
  inicial informado por Git estava limpo; renames e hashes complementam essa leitura.
  Nenhum desses destinos ficou oculto pelo ignore, conforme `visibility.txt`.
- Runner usa `local_path`, mantém os targets compartilhados e chama a retenção
  após publicar `finished_at`, ainda sob seu lock. Falha de retenção produz erro.
- Harnesses compartilham a área temporária da tarefa; execução direta tem fallback
  `build/tmp`, com remoção pelo contexto `TemporaryDirectory`.
- Guia canônico, README, índice, guia de ferramentas, entrada local e registros SDD
  atualizados. Fontes/relatórios anteriores não foram reescritos para aparentar atualização.

## Limpeza inicial observada

`retention-plan.jsonl` registra a simulação inicial. A aplicação por
`python3 scripts/local-state.py --apply` terminou com exit 0 e removeu 18 diretórios.
Resultado em `retention-applied.jsonl`; avisos em `retention.stderr.log`.
A soma estimada de blocos dos candidatos era **19.761.577.984 bytes (18,40 GiB)**.

Medição `du -s -B1 .local`, registrada em disk-before.txt/disk-after.txt:

| Momento | Bytes alocados observados |
| --- | ---: |
| Antes | 34.616.471.552 |
| Depois | 18.297.614.336 |
| Diferença | **16.318.857.216 (15,20 GiB)** |

A diferença de `du` é a redução medida na árvore local, não uma medição de blocos
livres do filesystem. A estimativa por candidato pode contar blocos compartilhados
ou hardlinks repetidamente. Não alegar que 18,40 GiB foram fisicamente recuperados.
Materiais de arquivo foram apenas movidos; não contam como espaço recuperado.

Uma nova simulação operacional com a versão final do utilitário encontrou zero
candidatos elegíveis; `retention-after.jsonl` e stderr correspondente preservam o
resultado. Restam aproximadamente 16 GiB em runs: resultados, cópias privadas,
material diagnóstico e execuções sem encerramento demonstrado. Não se aplicou
expiração global para ocultar essa lacuna nem se apagaram fontes pela idade.

## Convergência e lacunas

| Critério | Inspeção/observação | Estado e limite |
| --- | --- | --- |
| AC-01 | Inventário e decisões por finalidade | UNVERIFIED como aceite completo; inventário não prova ausência global de obsoletos |
| AC-02 | 578 conteúdos movidos sem diferença, destinos visíveis, chamadas atualizadas | UNVERIFIED para integração dos harnesses; não executados |
| AC-03 | Limpeza real, journal e simulação posterior; casos de regressão escritos | UNVERIFIED para concorrência, falhas e integração; testes não executados |
| AC-04 | Guia e entradas inspecionados por leitura | UNVERIFIED para validação automatizada de documentação/SDD, não executada |

`scripts/tests/test_local_state.py` cobre simulação/aplicação, evidências preservadas,
run incompleto, assinatura inválida/ausente, link externo e lock concorrente.
Os testes estão escritos, sem execução; não equivalem a evidência de eficácia.
A integração de `finished_at`, herança do lock e propagação do erro no runner foi
inspecionada, sem execução. A CLI aplicada inicialmente ainda não tinha a opção
explícita `--lock-fd`; essa interface foi adicionada depois para o runner. A simulação
posterior usou a versão final, em modo manual. Não alegar validação do modo herdado.

Próxima verificação depende de pedido de commit e escolha explícita conforme a
política canônica. Nenhuma evidência de aprovação anterior foi renovada; a feature
scripts-verification registra a necessidade de reobservação das entradas afetadas.
Sem promessas de isolamento contra escritores hostis do mesmo usuário; ver o guia.
