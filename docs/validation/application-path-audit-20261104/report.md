# Auditoria consolidada dos caminhos da aplicação

Data: 2026-11-04. Rota local documental. Objetivo: conferir todas as referências vigentes após mover Agent, Server e testes entre componentes para `application/`.

O escopo cobre todos os 80 arquivos de application, as entradas README/AGENTS, documentos vigentes, SDD, ferramentas e configuração de governança. O [inventário da auditoria](path-audit.json) registra arquivos, hashes, links e caminhos classificados; o [procedimento](audit_paths.py) confere links locais, caminhos literais completos, padrões de domínio, manifests futuros e ausência dos diretórios antigos na raiz. A árvore e a fixture de testes também são conferidas por [verify.py](verify.py). A busca inicial com rg incluiu menções em texto e scripts, seguida de revisão contextual de caminhos locais e exemplos.

| Critério | Estado | Resultado e limite |
| --- | --- | --- |
| Caminhos e links de application | APPROVED | Links locais resolvem para arquivos reais; caminhos completos existentes conferem; 23 módulos mantêm README/src/tests |
| Referências nas entradas e consumidores vigentes | APPROVED | Revisão e auditoria de README, AGENTS, docs, SDD e ferramentas; [path-audit.json](path-audit.json) |
| Governança após as correções | APPROVED | Auditoria com instalação exigida, preflight, estrutura e 24 testes em [checks.json](checks.json); vínculos atuais em [gates.json](gates.json) |
| Runtime e renderização visual | UNVERIFIED | Checkout documental; nenhuma execução ou interface de produto foi testada |

Os links e caminhos físicos da aplicação já estavam corretos na inspeção inicial. As correções tratam a orientação das entradas: README, contexto SDD e política de testes ainda destacavam observações anteriores às movimentações como referência atual; AGENTS não apresentava application. Essas entradas agora apontam para a auditoria consolidada. O contexto arquitetural explicita os testes entre componentes. O mapa e os READMEs de entrada distinguem caminhos a partir da raiz, caminhos locais e links relativos ao documento. A estrutura vigente é `application/agents/`, `application/server/` e `application/integration/tests/`, incluindo `integration/` e `e2e/` dentro desta última pasta.

Não são erros de migração: `agents/openai.yaml` é metadado de skills; `tests/` local é o diretório do módulo citado; exemplos no modelo SDD são genéricos. Cargo.toml e package.json documentados como futuros continuam ausentes. Padrões com `<dominio>` são conferidos contra os nove domínios reais. Não foram criados manifests ou testes de produto.

Limitação preexistente fora da aplicação: a cópia importada `docs/readme-lifecycle-engineering-SKILL.md` tem cinco links `references/...` que não resolvem a partir de docs; os cinco recursos existem no pacote instalado `.codex/skills/readme-lifecycle-engineering/`. Essa fonte preservada não foi reescrita. Relatórios anteriores, manifestos, hashes, scripts de observação e pesquisa histórica conservam caminhos antigos por identificarem versões passadas; não são instruções para a árvore vigente. Os caminhos da evidência atual são conferidos pelo gate SDD com freshness exigida.

As versões anteriores dos arquivos alterados e da evidência estão em [previous.zip](previous.zip); o escopo de edição está em [scope.json](scope.json). Os inputs anteriores são comparados antes da nova observação. AC-01/05 são reobservados pelo auditor/preflight; AC-02 pela revisão das autoridades e entradas; AC-03 por links e estrutura; AC-04 pelos controles existentes; AC-06 pela preservação dos registros de limpeza e comparação dos inputs, sem repetir remoções.

Skill utilizada: `.codex/skills/readme-lifecycle-engineering/SKILL.md`, SHA-256 `4a68d48bfa52160ab3cacbe7f04da1d7faadd6c76cde126617cbd945969473c9`, para entradas, navegação e revisão de referências. Não houve delegação, instalação, commit ou publicação. Mudanças futuras de módulos, manifests ou harness exigem repetir a auditoria e reexaminar as evidências afetadas.
