# Migração dos scripts para shell — 2026-11-06

Objetivo autorizado: integrar as capacidades indicadas de scripts_erase, remover a
pasta e manter scripts operacionais em shell; Python somente para testes em scripts.
Rota completa por alteração da CLI e da supervisão. Sem mudança funcional de produto.

## Entrega e recuperação

Entrada `bash scripts/automate.sh`; 17 arquivos shell divididos em lib, rust,
frontend, security, metrics e reports. Um catálogo define cada tarefa e sua ordem.
Python em scripts existe exclusivamente em tests/test_shell.py. Fixtures Rust/TS/Vue
em security/rules são dados de testes de regras, sem execução como scripts.
Ferramentas canônicas Python externas de governança/SDD e harnesses não foram
movidas para disfarçar implementação. A licença importada permanece em scripts/LICENSE.

[Mapa de consolidação](migration-map.json) relaciona cada capacidade indicada ao
novo destino: Rust (incluindo audit), frontend dev/setup/lint/coverage, métricas
LCOV/budget/performance, ratchet ESLint, relatórios, exceções, Trivy e CodeQL.
Wrappers duplicados foram substituídos por entradas por área. Sem políticas,
limiares ou licenças inventados; ausência de pré-requisito é blocked.
Tauri, Svelte, Bun, Graphify e baselines de outro produto não foram adotados.

[Backup anterior](previous.tar.gz) preserva scripts, scripts_erase, entradas e
normas antes da migração; [inventário de transição](migration-inputs.json) registra
bytes observados durante a implementação. Scripts_erase foi removida integralmente.
Arquivos anteriores de evidência estão preservados neste diretório, além dos
relatórios históricos originais. Não restaurar todo o tar sobre alterações atuais.

## Verificação observada

- Bash -n e ShellCheck 0.11.0: todos os 17 scripts sem apontamentos.
- `PYTHONDONTWRITEBYTECODE=1 python3 -m unittest discover -s scripts/tests -v`:
  35 testes passaram. Fixtures testam contratos dos comandos, sem alegar scan real.
- Rodada completa `bash scripts/automate.sh run` fora do sandbox, com acesso
  autorizado ao Docker: governança/skills, 34 testes de governança, fmt/Clippy/test
  dos dois workspaces, builds com failpoints, tipagem/testes/build Vue e os três
  harnesses reais de integração passaram na rodada run-bNH0YTQw.
- Os oito cenários funcionais Firefox dessa rodada passaram; a limpeza do perfil
  falhou por recriação de datareporting/aborted-session-ping. O perfil descartável
  passou a desligar telemetria local. A execução posterior com `--surface e2e`
  reobserva browser, Rust/frontend e governança em run-nXBBV9SD.
- Encerramento dos scripts/documentação com `bash scripts/automate.sh run --surface
  docs --surface scripts`: resultado integral novo em [closure-results.json](closure-results.json).

Logs e hashes antes/depois de cada rodada permanecem em .local/automation/runs.
Os resumos oficiais neste diretório retêm estados e exits reais. Nenhuma rodada
com falha foi substituída por uma aprovação fictícia.

Regressões encontradas/corrigidas: limite de log inicialmente herdado por arquivos
do workload (agora coletores FIFO limitam somente stdout/stderr); ordenação
alfabética sobrescrevendo builds com failpoints (agora deduplicação estável,
fmt→clippy→test→harness-build); descriptor do lock nos coletores/utilitários
(agora fechado nos filhos). O teste de morte abrupta verifica liberação em até 2s
com workload ainda vivo, sem exigir que todo filho curto já tenha sido recolhido
no mesmo instante em que o pai morre. Há regressões dedicadas para essas garantias.

Testes cobrem seleção, espaços/outro cwd, erros de argumentos, logs binários,
continuação após falha, privacidade/retenção, lock/recuperação, timeout/cancelamento,
limite de logs sem limitar arquivos, alterações de input, paths e symlinks,
relatórios incompletos/supressões/exit inconsistente, base OSV alterada, metadados
de exceções, LCOV, bytes/gzip, p95, ESLint e resumo Markdown.

## Lacunas reais

Gitleaks, Opengrep e OSV/bases continuam ausentes; os quatro checks de segurança
retornam blocked (3), sem certificar eficácia dos motores. As ferramentas adicionais
cargo-machete/deny/geiger/audit/llvm-cov, Trivy e CodeQL também não estão instaladas.
Deny, cobertura/lint/performance frontend e CodeQL precisam de políticas/configuração
revisadas do projeto. A migração implementa os adapters e seus testes, não inventa
essas decisões nem faz instalação implícita. AC-04 de scripts-verification permanece
UNVERIFIED. Critérios pendentes da primeira jornada não são promovidos por esta tarefa.
O gate global --require-approved --require-fresh mantém essas pendências visíveis.

Fontes primárias consultadas para flags e ciclo do perfil:
[cargo-deny](https://embarkstudios.github.io/cargo-deny/cli/common.html),
[cargo-geiger](https://docs.rs/cargo-geiger/latest/cargo_geiger/args/constant.HELP.html),
[cargo-audit](https://github.com/rustsec/rustsec/blob/main/cargo-audit/README.md),
[cargo-llvm-cov](https://github.com/taiki-e/cargo-llvm-cov),
[Trivy offline](https://trivy.dev/docs/v0.53/advanced/air-gap/),
[CodeQL analyze](https://docs.github.com/en/code-security/reference/code-scanning/codeql/codeql-cli-manual/database-analyze),
[preferências Firefox](https://firefox-source-docs.mozilla.org/toolkit/components/telemetry/internals/preferences.html).
