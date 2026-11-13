# Tarefas — 11/11/2026

- T001 / REQ-01: inventário, classificação e limpeza realizados; originais
  conservados em destinos canônicos, sem nova cópia de segurança.
- T002 / REQ-02, depende T001: runner, temporários, binários dos harnesses e
  fixtures de governança adaptados; inspeção de código, sem execução de suíte.
- T003 / REQ-03, depende T001: retenção por datas, logs únicos, lock e EXIT
  implementados. Limpeza inicial observada separadamente do runner não executado.
- T004 / REQ-04, depende T002/T003: AGENTS, guias, ADR-001 e SDD atualizados.
- T005 / AC-02/03: testes de retenção escritos e consumidores dos logs adaptados.
  Execução permanece não verificada. Próxima validação: suíte integral somente
  após pedido explícito de commit e resposta à pergunta canônica.

[Resultado e limitações](../../../docs/validation/local-state-20261111/report.md).

## Manutenção documental — 11/11/2026

T004: critérios AC-01 a AC-04 passam a ocupar linhas declarativas reconhecíveis
pelo formato SDD, sem alterar seus cenários. E02 permanece como observação histórica
em `docs/validation/local-state-20261111/observation-E02.json`, com hashes originais.
Os critérios continuam UNVERIFIED; nenhum teste ou validador foi executado.
