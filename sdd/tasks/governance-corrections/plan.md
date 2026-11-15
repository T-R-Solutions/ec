# Plano — correções de governança

Autoridades: AGENTS.md, docs/constitution.md, docs/architecture/context.md,
ADR-001, sdd/project.md, sdd/workflow.md, sdd/evidence.md, política de
verificação e estado local. Estado inicial: arquivos de gate/agentes/features
já modificados ou não rastreados; preservados e evoluídos, sem reset.

- D-01 / R-01 / REQ-01: Aceite delimitado; aplicar contrato do pedido e verificar cenário normal e rejeição/limite correspondente em AC-01.
- D-02 / R-02 / REQ-02: Catálogo e histórico; aplicar contrato do pedido e verificar cenário normal e rejeição/limite correspondente em AC-02.
- D-03 / R-03 / REQ-03: Inventário de aceite; aplicar contrato do pedido e verificar cenário normal e rejeição/limite correspondente em AC-03.
- D-04 / R-04 / REQ-04: Workflow e templates; aplicar contrato do pedido e verificar cenário normal e rejeição/limite correspondente em AC-04.
- D-05 / R-05 / REQ-05: Revisão semântica README; aplicar contrato do pedido e verificar cenário normal e rejeição/limite correspondente em AC-05.
- D-06 / R-06 / REQ-06: Avaliação das skills; aplicar contrato do pedido e verificar cenário normal e rejeição/limite correspondente em AC-06.
- D-07 / R-07 / REQ-07: Metadados coerentes; aplicar contrato do pedido e verificar cenário normal e rejeição/limite correspondente em AC-07.
- D-08 / R-08 / REQ-08: Capacidade de coordenação; aplicar contrato do pedido e verificar cenário normal e rejeição/limite correspondente em AC-08.
- D-09 / R-09 / REQ-09: Políticas canônicas; aplicar contrato do pedido e verificar cenário normal e rejeição/limite correspondente em AC-09.
- D-10 / R-10 / REQ-10: PostgreSQL vigente; aplicar contrato do pedido e verificar cenário normal e rejeição/limite correspondente em AC-10.
- D-11 / R-11 / REQ-11: Navegação completa; aplicar contrato do pedido e verificar cenário normal e rejeição/limite correspondente em AC-11.

## Decisões de transição

Manter evidence schema 2; workflow 1 continua aceitando features completas.
Workflow 2 explicita escopo task/feature e relações; tarefa delimitada não migra
ou remove ACs agregados. Catálogo global não exige freshness histórico; aceite
continua estrito. Inventário acceptance separado de source_files(all) preserva
consumidores scanners/cobertura. Recibo antigo sem contrato novo não aprova
a nova garantia. Nenhum hash de observação anterior será renovado.

## Métodos lidos e aplicados

- Primária: vínculos, oráculos e rejeições do gate; ponte sdd/chain/autonomous-verification-engineering.md, bridge_version 1; entrada .codex/skills/autonomous-verification-engineering/SKILL.md, SHA-256 lido `569696eab8348b370ab2f73d7ffc688d9fc88483419f8179c3bf84789e2866c0`. Leitura/aplicação não prova eficácia da skill.
- Auxiliar: coexistência workflow1/2 e recibos versionados; ponte sdd/chain/software-evolution.md, bridge_version 1; entrada .codex/skills/software-evolution/SKILL.md, SHA-256 lido `baef344475e0818541b9e5a14aa05bd61944caa17f6279aacbc993d577cf0436`. Leitura/aplicação não prova eficácia da skill.
- Auxiliar: autoridades canônicas, navegação e exemplos; ponte sdd/chain/technical-documentation.md, bridge_version 1; entrada .codex/skills/technical-documentation/SKILL.md, SHA-256 lido `60c83c5c34472fcfb0c3c6ede11253ecb14f0eb2e1ea550de3fede94db68ccf7`. Leitura/aplicação não prova eficácia da skill.

## Execução e verificação

Donos disjuntos em tasks.md. Análise/planejamento pelo coordenador; dois
implementadores especializados, revisão e validação em contextos independentes.
Preflight observado: criação por perfil e fork_turns=none disponível, interrupção
disponível, limite quatro incluindo raiz/coordenador, ausência de close explícito;
liberação automática observada ao criar o revisor independente após término
do implementador de código. Não simular independência.

Superfícies finais docs e scripts: mudanças em governança Python, Bash, TOML e
documentação; nenhum runtime de produto ou escopo real de scanner é alterado.
Testes focados usam fixtures temporárias e oráculos de rejeição. Runner final:
`bash scripts/automate.sh run --surface docs --surface scripts`, após encerrar
escritores e estabilizar documentos/evidências. Gate final com --task desta pasta.
Não executar ensaio LLM, rede ou downloads por inferência. Dependência externa
inalterada bloqueia com condição de retomada; nenhum PASS estrutural é aceite.
