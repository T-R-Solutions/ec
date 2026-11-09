# Acionamento de scripts após pedido de commit — 2026-11-07

Rota local documental. Objetivo: scripts de verificação somente após pedido de
commit e resposta à pergunta “Deseja executar o script automate.sh (que roda todos
os outros scripts)?”, com apenas `sim` e `não`. `não` permite os commits sem
scripts; `sim` exige sucesso de toda a suíte antes de criar os commits.

Superfícies e consumidores: AGENTS.md, política canônica em
docs/development/task-verification.md, entradas/workflow/evidências/templates SDD,
pontes de commit e verificação, 67 SKILL.md instalados e respectivas cópias de
recuperação. config/governance/profile.json registra os novos hashes de conteúdo
das skills e a adaptação; hashes de fontes originais permanecem preservados.
Scripts e CLI do runner permanecem como estavam; a mudança orienta seu acionamento
pelo agente. O comando autorizado pela resposta `sim` é
`bash scripts/automate.sh run`, sem filtros, cobrindo o catálogo padrão completo.

Revisão observada por leitura com `sed`/`cat` e buscas com `rg`: a skill de commit
contém os dois caminhos, as entradas de encerramento remetem à escolha e a busca
pela seção de acionamento retorna 67 SKILL.md. A política distingue erro,
interrupção e execução incompleta de sucesso e mantém critérios de aceite
separados da criação de commits. A preparação das alterações conferiu os hashes
anteriores das skills e a igualdade com suas cópias de recuperação antes da escrita.

Nenhum pedido de commit nesta tarefa; nenhuma suíte, teste, auditor ou validador
do projeto executado. Nenhum commit criado. Revisão documental observada;
verificação automatizada e comportamento em uma sessão futura permanecem não
verificados. Evidências anteriores não foram reexecutadas nem tiveram seus hashes
renovados: observações vinculadas a arquivos alterados devem ser tratadas como
históricas e reavaliadas quando a suíte for autorizada. Esta alteração não declara
novo aceite das features existentes.

## Pedido posterior de commit com execução autorizada

O usuário solicitou criar os commits com execução prévia do runner e confirmou
`sim`. Executado `bash scripts/automate.sh run`, sem filtros, com resultados em
`.local/automation/runs/run-Ds6Cvh56/results.json` e logs brutos por tarefa.
As 36 tarefas tiveram resultado: 33 passaram e três falharam; exit agregado 2,
sem alteração de inputs durante a execução. O campo `complete` ficou `false`,
conforme a implementação do runner para uma suíte sem aprovação agregada.

- `docs:sdd`, exit 1: inputs de evidências desatualizados nas três features após
  a mudança das instruções. As evidências antigas não foram renovadas para
  aparentar nova observação.
- `server:deny` e `agent:deny`, exit 4: ausência de licença declarada em seis
  crates do Server e cinco do Agent. A pendência já aparece no relatório anterior
  `docs/validation/tooling-remediation-20261106/report.md`. Nenhuma licença foi
  escolhida nem a política de licenças foi relaxada para permitir o commit.

Governança, instalação/integridade das skills, testes de governança/automação,
Ruff, ShellCheck, fmt, Clippy, testes Rust, Machete, Audit, builds dos harnesses,
frontend, segurança, três integrações HTTP e E2E Playwright/Firefox passaram.
Nenhum commit criado nem arquivo adicionado ao índice nesta execução. A escolha
`sim` mantém os commits bloqueados até uma nova suíte completa sem erros.
Retomada: reobservar as evidências SDD afetadas com preservação do histórico e
resolver a declaração de licença dos crates conforme decisão do projeto; então
reexecutar o runner completo antes de criar os commits autorizados.

## Novo pedido de commits incluindo não versionados — 2026-11-07

O usuário solicitou os commits incluindo arquivos não versionados e respondeu
`sim` à pergunta obrigatória. Executado `bash scripts/automate.sh run`, sem
filtros. Resultados em `.local/automation/runs/run-oUebC3O4/results.json`, com
stdout/stderr por tarefa no mesmo diretório. Exit agregado 2: das 43 tarefas,
31 passaram, seis tiveram erro, uma apresentou achados e cinco ficaram bloqueadas.
O campo `complete` ficou `false`; nenhuma aprovação agregada foi obtida.

- ShellCheck: adapter alterado desde o preparo; exige revisão e preparo explícitos.
- Ruff: três diagnósticos.
- Testes de automação: cinco falhas em 57 testes, envolvendo fixtures de fontes,
  cobertura e inventário dos lockfiles Trivy.
- Deny Server/Agent: licença ausente nos 11 crates internos; permanece sem decisão
  de licença do produto, conforme o README e a observação anterior.
- Geiger Server/Agent: invocação contra manifest virtual rejeitada.
- Cobertura Server/Agent: `cargo-llvm-cov` ausente; frontend: provider
  `@vitest/coverage-v8` ausente.
- Trivy ausente e bundle CodeQL elegível indisponível.

Governança, integridade das skills, SDD, testes de governança, fmt/Clippy/testes
Rust, Machete, Audit, builds dos harnesses, typecheck/testes/build frontend,
Gitleaks, Opengrep, OSV, três integrações HTTP e E2E Playwright/Firefox passaram.
Esses resultados não aprovam os checks restantes nem substituem o aceite SDD.

Nenhum commit criado e nenhum arquivo adicionado ao índice. Fontes e arquivos
não versionados foram preservados. A inspeção identificou também caches de build,
dependências e binários de ferramentas em `.local/`; sua presença como não
versionados não comprova adequação ao índice. Este registro foi acrescentado após
o término da suíte e não foi verificado por uma nova execução. Retomada: resolver
os erros e pré-requisitos, com decisão explícita sobre a licença, revisar o escopo
dos não versionados e executar novamente toda a suíte antes dos commits.
