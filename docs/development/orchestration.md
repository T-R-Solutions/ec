# Fila durável e tentativas locais

T004 implementa no Server submissão de revisão publicada, fila PostgreSQL, reserva de um slot Linux por Agent, leases, fencing, cancelamento e recibos. [Contrato de envelopes e transições](../../sdd/features/first-functional-journey/spec.md) e [evidência T004](../validation/orchestration-20261105/report.md). O cliente HTTP do harness exercita o lado Server; Agent real, checkout, processos, logs, artefatos e frontend continuam pendentes.

Use a preparação e o provisionamento de usuários/repositórios de [publicação](publication.md). Mesmos Rust 1.99.1/edition 2024, PostgreSQL 18.4, postgres 0.19.12, tiny_http 0.12.0 e serde_json 1.0.145. Nenhuma biblioteca externa nova. Da raiz:

```bash
export CARGO_HOME="$PWD/.local/build/ec-cargo-home"
export CARGO_TARGET_DIR="$PWD/.local/build/ec-server-clean"
cargo +1.99.1 build --manifest-path application/server/backend/Cargo.toml --locked --offline
# EC_DATABASE_URL aponta para seu banco local de desenvolvimento.
.local/build/ec-server-clean/debug/ec-server-app migrate
.local/build/ec-server-clean/debug/ec-server-app provision-agent linux-a ec .local/build/linux-a.credential
.local/build/ec-server-clean/debug/ec-server-app serve
```

Migration 002 é aditiva e transacional; o comando aplica 001 e 002 sob o lock de migração. Banco v1 conserva revisões, recibos, ponteiros, sessões e grants. Não execute o binário novo sobre banco v1 antes de migrar. Rollback de código não remove os dados; binário antigo não entende fila/tentativas e não deve operar a entrega T004. Não há promessa de rollout misto. Backup/restauração e administração SQL são responsabilidade do operador confiável.

CLI `provision-agent NAME PROJECT FILE` exige projeto existente, nome novo e arquivo novo; grava credencial aleatória de 256 bits uma vez, modo 0600, armazena só hash SHA-256. Não compartilhar com usuário ou colocar no YAML. `revoke-agent NAME` revoga credencial e autoridade da sessão atual; recuperação ocorre pelo reconciliador. Sem rotação por HTTP nesta fatia; provisione outra identidade para substituir uma credencial revogada. Token escrito em arquivo antes de commit não prova provisionamento bem-sucedido.

| Operação HTTP /api/v1 | Autorização | Comportamento |
| --- | --- | --- |
| POST /projects/ec/runs | operator/admin | {requestId,revisionId}; retorna recibo com runId, revisão, hash e commit fixados |
| GET /projects/ec/run-receipts/{requestId} | usuário com acesso ao projeto | recibo da própria identidade; repetir submissão exige papel de escrita atual |
| GET /projects/ec/runs e /runs/{id} | viewer/operator/admin | estado, intenção de cancelar, histórico e resultado persistido |
| POST /projects/ec/runs/{id}/cancel | operator/admin | {}; effective=false se já terminal; caso contrário persiste intenção |
| POST /agent/session | credencial Agent | {protocolVersion:1,platform:"linux",slots:1}; retorna token de sessão, id e leaseSeconds=30 |
| POST /agent/reserve | sessão Agent vigente | {}; devolve tentativa ativa existente ou próxima execução do projeto, ou attempt:null |
| GET /agent/attempts e /attempts/{id} | sessão Agent vigente | histórico da identidade, inclusive sessões antigas e recibos, sem autorizar mutação antiga |
| POST /agent/attempts/{id}/heartbeat | sessão/fence/lease vigentes | {fence}; renova lease por 30s e informa cancelRequested |
| POST /agent/attempts/{id}/running | sessão/fence/lease vigentes | {fence}; confirma preparação; intenção de cancelamento bloqueia início |
| POST /agent/attempts/{id}/result | sessão/fence/lease vigentes, salvo replay aceito | {fence,outcome,exitCode}; recibo durável, mesmo replay retorna original |

Rotas completas de listagem e detalhe são `/projects/ec/runs`, `/projects/ec/runs/{id}`, `/agent/attempts`, `/agent/attempts/{id}`. ID numérico positivo, requestId alfanumérico/hífen até 64 bytes. Campos extras são rejeitados; não enviar configuração/hash/commit na submissão. Corpo máximo 128 KiB. Resultado aceita succeeded com exitCode=0, failed com inteiro i32 não zero, timed_out/cancelled/preparation_failed com null. Cancelled exige intenção. Preparation_failed somente assigned; sucesso/falha/timeout somente running. Falha de preparação persiste failed com reportedOutcome=preparation_failed. Replay compara valores JSON do envelope completo; campos e tipos devem coincidir. Divergência gera 409. Dados privados usam no-store e erros genéricos code/message/requestId. Tokens nunca são logados pelo Server.

O token da credencial serve apenas para criar sessão; a sessão expira absolutamente em 8h, sem renovação por heartbeat. Nova sessão invalida imediatamente a anterior. Resposta de criação de sessão perdida se resolve criando outra sessão autenticada, depois consultando histórico; nunca inferir permissão de execução a partir de um recibo antigo. A sessão nova pode ler recibos da identidade, mas não mutar uma tentativa da sessão anterior. Cada Agent pertence a um projeto; capacidades aceitas são Linux e um slot, sem políticas de pools.

Para submeter por cliente Python, mantendo token fora de argv:

```python
import json
from pathlib import Path
from urllib.request import Request, urlopen
headers = {'Authorization': 'Bearer ' + Path('.local/build/alice.session').read_text(),
           'Content-Type': 'application/json'}
body = json.dumps({'requestId': 'execucao-1', 'revisionId': 1}).encode()
with urlopen(Request('http://127.0.0.1:8080/api/v1/projects/ec/runs',
                     headers=headers, data=body, method='POST')) as response:
    receipt = json.load(response)
with urlopen(Request('http://127.0.0.1:8080/api/v1/projects/ec/runs/' + str(receipt['runId']),
                     headers=headers)) as response:
    run = json.load(response)
assert run['revisionId'] == receipt['revisionId']
assert run['commit'] == receipt['commit']
```

## Recuperação e invariantes

Todas as mutações usam guard advisory transacional 746232 no banco, compartilhado por processos/conexões Server. Reserva, tentativa, ocupação do slot e atualização de run compartilham commit. Índices únicos parciais também impedem duas tentativas ativas por run ou Agent. Escopo inicial conserva simplicidade com contenção global; não há ensaio de throughput, justiça ou escala. Sessão/grant de usuário permanecem bloqueados contra revogação durante mutação; o corpo é lido antes de adquirir o guard de orquestração. Captura única de `clock_timestamp()` após espera no guard é a referência temporal de cada operação. Nunca usar horário do cliente nem timestamp de início de transação antes da espera.

Reconciliador executa no startup e a cada segundo, e antes de reservar. Não usa fila em memória. Tentativa com lease válido e sessão vigente permanece ativa após restart. Expiração, sessão substituída/expirada ou credencial revogada perdem autoridade; tentativa vira lease_lost, histórico permanece. Primeira perda volta à fila; segunda encerra failed/attempts_exhausted. Intenção de cancelamento encerra cancelled em vez de reatribuir. Falha de script, preparação ou timeout são terminais e não recebem retry. Fence global cresce, com lacunas legítimas por rollback; ordinal por run só chega a dois.

Cancelamento e conclusão usam o mesmo guard/estado protegido. Pedido anterior a resultado mantém assigned/running com cancelRequested=true até confirmação; resultado posterior persiste cancelled mesmo se reportado succeeded. Pedido em fila confirma cancelled imediatamente. Resultado já commitado torna cancelamento effective=false. Cancelar não comprova término de processos externos; T005 deve efetuar e confirmar término. Nenhuma mensagem tardia sobrescreve terminal. Expirar lease também não encerra efeitos externos; não há exactly-once nem isolamento de workloads.

Recibos têm retenção indefinida. Submissão e resultado com resposta perdida devem ser consultados/repetidos antes de nova execução. Replay de submissão usa identidade/projeto/requestId e revisão idêntica; divergência 409 sem duplicar. Reserva repetida retorna a tentativa ativa do slot; heartbeat não deve ser interpretado como ordem para executar novamente. Resultado aceito é consultável depois de lease expirar; replay exige sessão atual e fence da tentativa. Resultado de tentativa perdida é rejeitado, mesmo antes da próxima atribuição.

Server escuta em loopback, quatro workers e um reconciliador. Sem implantação remota/TLS, limite de fila/listagem/retenção, garantia contra clientes lentos ou failover do banco. Banco/CLI são fronteira administrativa confiável. Referências de segredos continuam pendentes na T001; somente snapshots locais admitidos sem credenciais são aceitos. Não existe detecção genérica de segredos em scripts arbitrários.

## Reproduzir verificações

Use exclusivamente o container descartável `ec-publication-test`, porta 55439, descrito em publication.md. O harness cria/remove banco com UUID, Git e tokens temporários; não toca em bancos existentes. Os dois harnesses compartilham porta HTTP 58139, portanto execute sequencialmente.

```bash
cargo +1.99.1 build --manifest-path application/server/backend/Cargo.toml --locked --offline --features test-failpoints
python3 application/integration/tests/integration/orchestration_http.py
python3 application/integration/tests/integration/publication_http.py
cargo +1.99.1 test --manifest-path application/server/backend/Cargo.toml --locked --offline
cargo +1.99.1 clippy --manifest-path application/server/backend/Cargo.toml --locked --offline --all-targets --all-features -- -D warnings
cargo +1.99.1 fmt --manifest-path application/server/backend/Cargo.toml --all -- --check
```

Failpoints só no build test-failpoints: header X-Ec-Test-Failure before-commit retorna 500 com rollback; crash-before-commit sai com 86; after-commit fecha resposta depois de persistir. EC_TEST_PAUSE_RECONCILER=1 pausa apenas o worker periódico de teste, preservando reconciliação de startup e reserva: o harness demonstra heartbeat rejeitado com tentativa SQL ainda assigned após vencer durante espera. Testes encurtam deadlines via SQL administrativo e atravessam tempo PostgreSQL real; não aguardam os 30 segundos completos em cada caso. Corridas observam conexões HTTP aguardando guard em pg_stat_activity; SQL independente verifica índices, ordinais, estados e ausência de efeitos negados.

Fontes oficiais: [locks transacionais PostgreSQL 18](https://www.postgresql.org/docs/18/explicit-locking.html) e [clock_timestamp e demais referências temporais](https://www.postgresql.org/docs/18/functions-datetime.html). O driver e rollback continuam os mesmos fixados em Cargo.lock e inspecionados na fatia de publicação.

## Incremento T005

[Agent real, logs, artefatos, migration 003 e recuperação](agent.md). Os contratos e evidências anteriores permanecem históricos; o endpoint result legado não certifica entrega de saída. Harnesses devem rodar em sequência por compartilharem a porta HTTP.
