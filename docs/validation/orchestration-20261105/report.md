# Evidência T004 — fila durável e tentativas

Escopo: REQ-04 → D-04/R-04 → T004 → AC-04, com contribuição parcial a AC-07. PostgreSQL real descartável, processos HTTP reais e conexões concorrentes independentes. [Reprodução operacional](../../development/orchestration.md) e [contrato](../../../sdd/features/first-functional-journey/spec.md). Revisão semântica confrontou autoridades/baselines com código; o cliente de teste não implementa Agent.

## Ambiente e comandos

Linux x86_64, Rust/Cargo 1.99.1, edition 2024; PostgreSQL 18.4 x86_64 Alpine, READ COMMITTED, synchronous_commit=on. Imagem/container exclusivos já preparados na fatia de publicação, sem extensões PostGIS usadas; identificação em environment.log. Cache `.local/build/ec-cargo-home`, target `.local/build/ec-server-clean`. Dependências diretas exatas e Cargo.lock preservam driver/HTTP/JSON/crypto; somente um crate interno foi acrescentado. Docker exige acesso ao socket e Server exige bind loopback; ensaios foram executados com permissão real fora do sandbox, sempre em banco UUID novo.

```bash
export CARGO_HOME="$PWD/.local/build/ec-cargo-home"
export CARGO_TARGET_DIR="$PWD/.local/build/ec-server-clean"
cargo +1.99.1 build --manifest-path application/server/backend/Cargo.toml --locked --offline --features test-failpoints
python3 application/integration/tests/integration/orchestration_http.py
python3 application/integration/tests/integration/publication_http.py
cargo +1.99.1 test --manifest-path application/server/backend/Cargo.toml --locked --offline
cargo +1.99.1 clippy --manifest-path application/server/backend/Cargo.toml --locked --offline --all-targets --all-features -- -D warnings
cargo +1.99.1 fmt --manifest-path application/server/backend/Cargo.toml --all -- --check
python3 tools/governance/validate.py
python3 tools/governance/validate.py --require-installed
python3 sdd/tools/validate.py --root .
python3 sdd/tools/validate.py --root . --features sdd/features --require-fresh
python3 sdd/tools/validate.py --root . --features sdd/features --require-approved --require-fresh
python3 -m unittest discover -s tools/governance/tests -v
```

## Observações

`http-postgres.log` registra PASS para migration v1 com dados/recibos/ponteiros preservados, submissão imutável após publicação com outro SHA real, repetição concorrente sem duplicação e conflitos. O guard é mantido por conexão SQL externa e os dois workers HTTP são observados em pg_stat_activity aguardando advisory lock. Reserva do mesmo trabalho em slots diferentes aceita uma; reserva concorrente no mesmo slot devolve mesma tentativa e conserva segundo trabalho queued. Oráculos SQL independentes verificam no máximo um ativo por run/slot, ordinal contíguo, até duas tentativas, relação entre estado de run e tentativa, resultado terminal presente e snapshots sem mutações negadas.

Lease válido é renovado, fence incorreto é rejeitado; processo Server é reiniciado com reserva/running e lease válido preservados. Expiração atravessa relógio PostgreSQL real com deadlines encurtados por SQL de teste. Uma prova pausa exclusivamente o reconciliador periódico no build de teste, enfileira heartbeat no guard antes do vencimento, espera vencimento no SQL e libera: resposta 409 com tentativa ainda assigned. Não depende de novo dono para revogar autoridade. Sessão nova invalida anterior, sessão expirada é rejeitada, consulta de histórico/recibo da identidade não concede mutação de tentativa antiga. Recuperação por perda admite ordinal 2/fence maior; segunda perda encerra failed/attempts_exhausted. Script nonzero, timeout e preparação falha não reentram na fila.

Corridas controladas observam primeiro cancelamento e depois resultado bloqueados: intenção persiste enquanto running, resultado vira cancelled. Na ordem inversa resultado succeeded permanece e cancelamento retorna effective=false. Cancelamento queued confirma sem tentativa; intenção assigned é recuperada após restart/lease perdido. Replay idêntico de resultado devolve recibo original; divergente/obsoleto não altera terminal. Falhas de submissão antes de commit incluem retorno e exit(86); reserva também sofre exit antes do commit sem consumir tentativa/slot. Respostas de submissão, reserva e resultado são suprimidas depois do commit, com reinício real e recuperação por consulta/replay sem duplicação.

`publication-regression.log` deve ser executado com binário test-failpoints após qualquer cargo test/build que possa sobrescrever o binário compartilhado. `rust-tests.log`, `clippy.log`, `fmt.log` e logs de governança/SDD registram os checks finais. `production-build.log`/`production-failpoints.log` verificam build normal e ausência dos identificadores dos hooks; build operacional ignora headers/env de teste por cfg.

## Histórico da execução e limites

Preservados: previous.zip contém artefatos SDD anteriores; http-postgres-attempt1.log detectou que o filtro inicial do CLI não admitia novo subcomando (corrigido); attempt2 detectou requestId de fixture com underscore (corrigido). http-postgres-before-review.log contém ensaio completo antes dos reforços de expiração durante lock/queda em reserva. Uma execução inicial da regressão rodou acidentalmente binário normal sobrescrito por cargo test, e parou na expectativa de failpoint; publication-regression-before-rebuild.log preserva isso. Rebuild com feature precede o ensaio final. Governança inicialmente apontou link do relatório ainda não criado, causando também falhas nas fixtures dos unitários; arquivos before-report preservam os resultados, sem atribuir a falha ao produto.

AC-04 pode ser aprovado somente com o log final passando. AC-02/03 são reobservados pela regressão final. AC-01/05/06/07 permanecem UNVERIFIED; contribuição parcial a AC-07 cobre somente publicação/submissão/resultado persistido do Server. Não houve Agent/checkout/processo de workload/log/artefato/UI. Segredos por referência continuam T001; rejeição de campos não é detecção de segredos em texto arbitrário. Não há exatamente uma vez externo, isolamento, escala/justiça, resistência a clientes lentos, paginação/retenção, falha de disco ou failover PostgreSQL demonstrados. Confiança em operador SQL/CLI e exposição loopback permanecem. Checks estruturais não aprovam produto; gate agregado deve falhar enquanto houver esses critérios pendentes. Nenhum commit/tag/publicação.

## Revisão de invariantes e fronteiras

Ator HTTP usuário → bearer/sessão e grant bloqueados → API autorizada → snapshot público de projects → transação orchestration. Cliente não fornece configuração/hash/commit. Ator Agent → credencial administrativa distinta → sessão vigente no PostgreSQL → projeto fixado pelo cadastro → tentativa/fence/lease → resultado/recibo. IDs e capacidades declaradas não autenticam. Todos os writers de orquestração (incluindo CLI de revogação e reconciliador) compartilham o guard; resultados/cancelamento competem na mesma transação. Consultas de usuário usam guard para leitura consistente entre run/tentativas. Índices únicos e triggers protegem duplicação/histórico; administração do schema continua privilegiada. Mensagem sem autoridade rejeitada faz rollback. Relógio é capturado depois do guard e leitura do corpo; não há I/O externo de workload na transação.

Skills e pontes efetivamente lidas e aplicadas, contribuição e hashes em skills.json; sem delegação. Fontes primárias consultadas: [PostgreSQL 18 locks](https://www.postgresql.org/docs/18/explicit-locking.html) e [referências temporais](https://www.postgresql.org/docs/18/functions-datetime.html). O guard transacional e relógio após espera foram verificados no driver vigente e nas consultas reais, sem introduzir novo transporte/broker.


## Checks finais e descoberta de features

A regressão T002/T003 passou com binário test-failpoints. Rust: nove testes, Clippy com -D warnings e fmt --check passaram. Governança: auditor padrão/--require-installed e 34 testes passaram. Build normal compilou; identificadores de failpoints não estão presentes no executável.

A primeira invocação de --features apontou incorretamente para uma feature individual: o CLI real exige diretório pai de features. O PASS sem features não foi usado como aceite; --require-approved recusou com No feature directories found. Logs originais foram preservados com sufixo wrong-directory. Os comandos corrigidos usam sdd/features, abrangendo ambas as features. O validador global identificou quatro bindings de governança desatualizados pelos documentos editados; reobservação explícita com auditor/preflight/34 testes e inspeção, anterior em governance-previous.zip, comparação em governance-checks.json. Não se reutilizou aprovação histórica mediante troca silenciosa de hashes.
