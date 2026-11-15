# Observações da correção — 15/11/2026

O run-kKrEc98i completo falhou no setup de
`captures_both_streams_and_preserves_exit_failure`: `create_dir` devolveu OS 17,
`AlreadyExists`, e o unwrap provocou panic/exit 101. A compilação havia terminado.
O log não contém o caminho nem a identidade do primeiro criador. A reprodução
controlada de PID/timestamp iguais demonstra a vulnerabilidade do esquema de
nomes, sem reconstruir o agendamento histórico.

A fixture agora usa PID e contador atômico, reserva com `create_dir` e tenta
outro candidato somente para `AlreadyExists`, até 1024 candidatos. Outros erros
retornam diretamente; só a reserva bem-sucedida cria o proprietário RAII.
O contador não publica dados, portanto `Relaxed` basta; a operação de filesystem
decide a posse. Nenhum manifest, lockfile ou código de produto foi alterado.

Teste focado offline: `cargo test --offline -p ec-agent-execution --test process`,
exit 0, sete testes aprovados (quatro originais e três regressões). Regressões
preservam diretório com sentinela e arquivo preexistentes, comprovam oito fixtures
vivas com caminhos distintos e limpeza individual, e preservam erro `NotFound`.
Logs: `.local/logs/2026-11-15/fixture-fix-ac585f0c.focused.stdout.log` e
`.results.log`; hashes observados e rustfmt exit 0 em `.inputs.log`.

A prova controlada do esquema antigo está em `.original-proof-source.log` e
`.original-proof.stdout.log`, sob o mesmo prefixo. A primeira extração para
mutação não compilou por referência restante a Spool e não constitui prova.
A extração corrigida contém literalmente helper e regressões std-only, removendo
apenas o método spool não exercitado: baseline compila/passa; retirar o retry de
EEXIST compila e causa exit 101 na regressão de colisão. Evidência em
`.mutation2-source.log`, `.baseline2-test.stdout.log`, `.mutation2-test.stdout.log`
e `.mutation2-results.log`. Temporários isolados foram descartados.

Revisão independente por `revisao_fixture` sobre código de hash
`d97d266564f2266b81eb3697715052786cfc45e3e17426f2edb8a59d94a1cdde`:
sem achados bloqueadores; confirmou oráculos, reserva exclusiva, limites de
retry, cleanup, ausência de mudanças nos quatro cenários originais, escopo,
consumidores, superfícies docs/agent e relação parcial com AC-05 da primeira
jornada. Inspecionou logs reais, prova e mutação; `git diff --check` exit 0.

Limites: não se demonstra ausência universal de falhas nem origem histórica do
caminho ocupado. O helper é suporte privado de testes confiáveis; não estabelece
isolamento de produto. Este registro estabiliza observações anteriores ao runner
final; o aceite depende de seu recibo atual e do gate independente, mantidos nos
logs. Não se declara aprovação global de `--scope complete`.
