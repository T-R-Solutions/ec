# Convergência — primeira jornada funcional

Estado atual conforme [evidence.json](evidence.json). As observações datadas abaixo
são históricas; suas aprovações não se transferem aos inputs atuais. Backups
mencionados foram removidos pela política de retenção de 11/11/2026.

## Consolidação de responsabilidades — 13/11/2026

REQ-07 → D-07/R-07 → T008/T009 → contrato público v1, módulos privados de
composição, replay no domínio, dependências herdadas e entradas públicas Vue →
testes de fronteiras com mutações negativas e regressões HTTP/browser → AC-08.
Pedido atual autoriza execução/reexecução; não houve pedido de commit/publicação.

Inspeção e checks focais demonstraram compilação, Clippy, unitários Rust e 27
testes frontend. O primeiro run completo, run-NZqxTG9w, foi interrompido após
falhas de escrita da Toolbox fora do sandbox; seu resultado não aprova a suíte.
Reexecução com permissões apropriadas e confirmação dos inputs finais pendentes.
O harness de publicação acrescenta regressões para prioridade de erro em base
obsoleta/YAML inválido e replay após retirada da referência admitida. Não houve
mudança de schema SQL nem remoção de verificação de autorização.

O guard detecta versões locais em membros, dependências privadas entre
componentes, imports privados/de composição, repetição de decisão de replay,
campos sem validação semântica e incompatibilidade de limites com migration v1.
Seus limites são análise textual e escopo da jornada; validação independente em
fronteiras e configurações históricas de migração não são cópias a eliminar.

[Registro e resultados](../../../docs/validation/responsibility-consolidation-20261113/report.md).

## Dependências durante o desenvolvimento — 11/11/2026

Por pedido do usuário, os onze manifests de crates Agent/Server deixam de exigir
versões exatas de terceiros; preservam as versões mínimas e permitem atualizações
compatíveis. Dependências internas usam somente `path`. Os locks atuais não foram
atualizados e o congelamento para lançamento aguarda pedido posterior. Método:
inspeção dos manifests e diff, com a skill `rust`; distinção entre resolução,
atualização e execução orientada por `software-delivery`. Guia operacional em
[verification-tools](../../../docs/development/verification-tools.md#dependências-rust-em-desenvolvimento).
Nenhum download, build, teste ou script de verificação foi executado nesta tarefa;
compatibilidade com versões futuras permanece não verificada.

## Revisão dos scripts após análise do run-oUebC3O4 — 2026-11-07

Os scripts vinculados à inspeção anterior foram corrigidos; nenhum script foi
executado nesta tarefa. Critérios atuais permanecem UNVERIFIED. Esta revisão
inspecionou o impacto nas verificações; não exercitou a jornada de produto.
[Análise e limites](../../../docs/validation/automation-run-oUebC3O4-20261107/report.md).
Originais de evidência/convergência preservados em previous-related-evidence.zip
nesse diretório. As observações abaixo são históricas.

> Atualização de 2026-11-07 — os registros abaixo são históricos. A promoção
> de Geiger/Coverage/Trivy/CodeQL alterou inputs vinculados à evidência agregada
> desta feature. Os critérios atuais ficam UNVERIFIED até nova observação
> autorizada; nenhum resultado antigo foi reaproveitado como aprovação atual.
> [Registro e preservação](../../../docs/validation/default-quality-20261107/report.md).

## Observação T004 — fila e tentativas

| Critério | Estado | Observação |
| --- | --- | --- |
| AC-01 / T001 | UNVERIFIED | Parser preservado, trabalhos locais admitidos. Referências de segredos pendentes; não há detector de segredos arbitrários em scripts. |
| AC-02 / T002 | APPROVED | Regressão HTTP/PostgreSQL real de publicação, conflitos, replay e reinícios antes/depois de commit. |
| AC-03 / T003 | APPROVED | Regressão de sessões/grants e novas negações em submissão/cancelamento/contratos Agent sem efeitos persistidos. |
| AC-04 / T004 | APPROVED | PostgreSQL 18.4: revisão/hash/commit fixados, replay, disputa observada em conexões independentes por trabalho/slot, heartbeat/lease/fence/sessão, retry limitado, recibos, corridas cancelamento/conclusão nas duas ordens, recuperação/reinícios reais. |
| AC-05 / T005 | APPROVED | Agent Rust real com checkout/processos/descendentes/logs/artefatos/recuperação no ensaio final; ver relatório T005 e limites de workloads confiáveis. |
| AC-06 / T006 | UNVERIFIED | Interface e evidência no navegador pendentes. |
| AC-07 / T007 | UNVERIFIED | Contribuição parcial T004: publicação → submissão → resultado Server persistido e consultável. Não demonstra execução Agent/artefato, frontend ou jornada completa. |

REQ-04 → D-04/R-04 → T004 → ec-server-orchestration/src/lib.rs, migration 002, composição service.rs → orchestration_http.py e oráculos SQL → docs/validation/orchestration-20261105/http-postgres.log → AC-04. REQ-02/03 → D-02/03 e R-02/03 → T002/T003 → regressão publication_http.py. Caminhos/hashes e comandos em evidence.json; seleção, pontes e contribuições em skills.json. Evidência anterior da feature preservada no previous.zip desta fatia; registros originais de publicação continuam intactos.

Decisão: serialização transacional PostgreSQL global, com índices únicos parciais para slot/run; não promete escala ou justiça. Relógio capturado depois da espera; teste pausa worker periódico somente no build de testes e comprova expiração com tentativa SQL ainda assigned. Deadlines encurtados por SQL administrativo atravessam tempo real do banco. Falhas de processo/HTTP foram exercitadas; falha de disco, host/failover PostgreSQL, isolamento e efeitos exactly-once externos não foram demonstrados. Recibos/histórico e listagens sem retenção/paginação nesta capacidade inicial. Exposição apenas loopback; TLS remoto/IdP fora de escopo.

Relatório reproduzível: docs/development/orchestration.md e docs/validation/orchestration-20261105/report.md. O aceite agregado deve continuar falhando para AC-01/05/06/07; checks estruturais não os promovem. Retomar T001 por referências de segredos; T005 pelo contrato HTTP autenticado de sessão/reserva/reconexão, checkout e execução reais; depois T006/T007. Não há commit, tag ou publicação nesta entrega.

## Convergência T005

REQ-05 → D-05/R-05 → T005 → cinco crates Agent, results, migration 003 e composição HTTP → agent_http.py com SQL/processos/arquivos independentes → docs/validation/agent-20261105/. AC-02/03/04 reobservados nas regressões atuais; alterações dos contratos anteriores não reaproveitam hashes históricos. AC-07 recebe contribuição publicação → execução real → saída persistida, mas continua UNVERIFIED por T006/T007 e rejeições completas pendentes. Gate agregado continua falhando para AC-01/06/07. Fontes, decisões, comandos e limites no relatório e guia operacional. Sem commit/tag/publicação.


## Convergência T006 — observação de 05/11/2026

AC-06 APPROVED no recorte da primeira jornada: frontend executável, round-trip pelo parser/schema Rust, publicação/conflito/resposta incerta, execuções/saída/artefato reais, teclado e estados de erro no Firefox do sistema. Não constitui certificação WCAG completa ou de outros navegadores. REQ-06 → D-06/R-06 → T006 → Editor.vue, Runs.vue, stores Pinia, shared/api e extensões service.rs → frontend_firefox.py, Vitest e regressões HTTP → docs/validation/frontend-20261105/ → AC-06. AC-02/03/04/05 são reobservados nas regressões desta fatia; veja checks.json. AC-01/T001 e AC-07/T007 permanecem UNVERIFIED, aceite agregado não aprovado. As tabelas anteriores descrevem a observação histórica anterior, preservada também em previous.zip.

Projeções usam validação sem efeitos no Server; não há segundo parser com resolução divergente de escalares. Pedidos incertos conservam identidade/payload em memória; sessão expirada/revogada limpa contexto privado. Navegação normal conserva rascunhos por projeto. AbortController, gerações e cleanup descartam respostas obsoletas. Logs pós-processo e artefatos verificam bytes e completude conhecida. Limites, comandos de execução, falhas corrigidas, screenshots e passos exatos de T001/T007 no guia frontend.md e no relatório. Sem commit/tag/publicação externa.

## Reobservação de scripts e encerramento — 2026-11-05

[Registro atual](../../../docs/validation/scripts-ec-20261105/report.md): comandos
reais de governança, Rust, frontend, integração e Firefox reexecutados; observações
anteriores preservadas em previous.zip. A política de verificação foi centralizada
e somente duas skills atualizadas. Gitleaks ausente bloqueia o aceite agregado da
verificação; AC-01/AC-07 da primeira jornada continuam pendentes por seus próprios
motivos. Não há aceitação por renovar hashes de observações antigas.

## Migração do harness — 11/11/2026

Por pedido do usuário, a jornada agora usa Chromium/Firefox no Docker através
do Playwright da Toolbox. O oráculo corrente é `frontend_browser.py`; a seleção
e os serviços vêm da configuração única dos scripts. A aprovação histórica do
Firefox/Marionette não é reutilizada para esta implementação. A nova jornada
permanece UNVERIFIED até execução real autorizada; não houve download de browser.

## Fechamento da consolidação — run-hOzphSex

T008/T009 e AC-08 aprovados por suíte completa e revisão dos proprietários;
nenhum input de execução mudou. O fechamento atualiza somente registros de
aceite. Resultados/skills/limites em docs/validation/responsibility-consolidation-20261113.
REQ-01/AC-01 (segredos) e AC-07 mantêm seus próprios limites; nenhuma aprovação
agregada de produto é inferida. Nova validação após o fechamento documental.
