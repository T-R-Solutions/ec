# Diretórios exclusivos para testes de processos do Agent

REQ-01: corrigir a falha `agent:test` com exit 101 observada no run-kKrEc98i de
15/11/2026. O panic ocorreu em `Fixture::new`, antes da execução do script,
quando `create_dir` retornou `AlreadyExists`. PID e timestamp não constituem
reserva exclusiva de nomes; o log não identifica o primeiro criador.

- AC-01 → REQ-01: colisão de nome é tratada sem reutilizar, modificar ou apagar o caminho preexistente; outros erros de filesystem permanecem explícitos.
- AC-02 → REQ-01: fixtures vivas simultaneamente possuem diretórios distintos e cada destrutor remove somente seu próprio diretório.
- AC-03 → REQ-01: regressões controladas exercitam colisão e isolamento, e os quatro cenários existentes de execução de processos continuam aprovados.

Escopo: helper e testes em `ec-agent-execution/tests/process.rs`. Nenhuma API ou
regra de execução do produto muda. Relação parcial com AC-05 da primeira jornada;
este aceite não aprova isolamento de workloads, protocolo nem a jornada completa.
