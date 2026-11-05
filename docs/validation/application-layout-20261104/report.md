# Correção dos caminhos da aplicação

Data: 2026-11-04. Rota local documental; objetivo: colocar Agent e Server dentro de `application/`, conforme correção explícita do usuário.

Os diretórios passam a ser `application/agents/` e `application/server/`. Foram corrigidos os links relativos dos READMEs movidos, as entradas do repositório, o contexto SDD, a ADR-002, o mapa arquitetural e a política de testes. Os testes entre componentes continuam em `tests/` na raiz. A fixture de governança copia `application/` para resolver os links na nova estrutura. Os destinos já continham apenas diretórios vazios; não havia arquivos a substituir.

As versões anteriores dos arquivos afetados e da evidência estão em [previous.zip](previous.zip); o escopo original está em [scope.json](scope.json). Os relatórios de 2026-11-03, seus manifestos, logs e procedimentos foram preservados como observações históricas dos caminhos então existentes. Seus links para os caminhos anteriores não representam a navegação vigente.

| Critério | Estado | Método e evidência |
| --- | --- | --- |
| Agent e Server sob application, preservando os 23 módulos | APPROVED | Inspeção da árvore e conferência de README/src/tests por módulo em [verify.py](verify.py) |
| Navegação vigente e fixture ajustadas | APPROVED | Conferência dos links de application/tests, auditoria das entradas e 24 controles de governança; [checks.json](checks.json) e logs locais |
| Governança atual e vínculos de evidências | APPROVED | Comparação dos inputs anteriores restrita ao escopo; auditoria com instalação exigida, preflight e gates SDD em [gates.json](gates.json) |
| Runtime e renderização visual | UNVERIFIED | Sem implementação de produto ou ensaio visual nesta correção |

Revisão documental: a mudança de caminhos conserva responsabilidades, dependências planejadas, YAML canônico, HTTP/PostgreSQL e o status pendente das propostas. AC-01/05 são reobservados por auditoria e preflight; AC-02 pela revisão das entradas e autoridades; AC-03 pelos links e estrutura; AC-04 pelos controles com a fixture ajustada; AC-06 conserva a observação da limpeza e confere seus inputs, sem repetir remoções históricas. A evidência anterior não recebe hashes novos: evidence.json aponta para esta nova execução após as verificações passarem.

Skill utilizada: `.codex/skills/readme-lifecycle-engineering/SKILL.md`, SHA-256 `4a68d48bfa52160ab3cacbe7f04da1d7faadd6c76cde126617cbd945969473c9`, para entradas e navegação. Não houve delegação, instalação, commit ou publicação. Ao mover novos módulos, atualizar os caminhos, links e a descoberta futura de testes.
