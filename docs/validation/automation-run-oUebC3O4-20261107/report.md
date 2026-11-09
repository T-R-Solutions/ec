# Análise e correções do run-oUebC3O4

Data: 2026-11-07. Objetivo: analisar os logs existentes em
`.local/automation/runs/run-oUebC3O4` e corrigir suas causas demonstradas.
Não houve pedido de commit. A política de `docs/development/task-verification.md`
permite inspeção/edição e reserva scripts de verificação ao fluxo de commit.

## Resultado observado nos logs existentes

43 tarefas: **31 passed, seis error, um findings e cinco blocked**. Exit agregado
2, `complete=false`, `changed_inputs=[]`. Esta é uma observação do run fornecido,
anterior às correções desta tarefa; não equivale a execução nos inputs atuais.

| Tarefa | Causa identificada | Correção ou pendência |
| --- | --- | --- |
| ShellCheck | Adapter diferente do hash registrado no preparo; a ferramenta não chegou a analisar o código | Exige revisão/preparo explícito do ShellCheck 0.11.0, sem substituir manualmente o hash no lock |
| Ruff | Três S607 em `test_quality.py`; comentários estavam na linha da chamada, mas Ruff reportava a lista argv | Justificativas específicas movidas para as linhas apontadas, preservando S603 na chamada |
| Testes de scripts: duas falhas de snapshot | Fixtures QualityTests não criavam AGENTS.md e README.md, obrigatórios para `observe_inputs all` | Ambos criados no setUp; contrato de inventário preservado |
| Testes de scripts: LCOV sem linhas | jq propagava exit 5, enquanto o contrato exige erro 2 | Falha de cálculo explicitamente convertida em erro 2, mantendo a rejeição do denominador vazio |
| Testes de scripts: duas fixtures Trivy | Inventário não tinha os três lockfiles com pacotes | Fixtures completas com SchemaVersion 2, caminhos e inputs correspondentes; parser não foi flexibilizado |
| Fixture SARIF no mesmo teste | O exemplo anterior não tinha driver/regras/ruleId; ficava oculto pela falha Trivy anterior | Fixture atualizada com regra identificável e invocação bem-sucedida |
| Geiger Server/Agent | Geiger 0.14.0 rejeita manifesto virtual | Iteração de todos os membros do metadata por manifesto concreto, incluindo all-features/locked/offline e relatórios/exits individuais |
| Cargo Deny Server/Agent | 11 crates próprios sem licença; advisories/bans/sources passaram | Depende da definição de licença/política pelo usuário; nenhuma licença ou exceção foi inventada |
| Coverage Server/Agent | cargo-llvm-cov ausente | Instalação e componente llvm-tools-preview pendentes de preparo/download autorizado |
| Coverage frontend | @vitest/coverage-v8 ausente em node_modules | Provider 4.1.12 já está no lockfile; instalação explícita pendente |
| Trivy | CLI ausente | CLI 0.61.1 e base com integridade/frescor precisam de preparo autorizado |
| CodeQL | Bundle ausente | Elegibilidade/licença, bundle 2.27.3, packs e três bancos vinculados precisam de preparo explícito |

O teste de scripts registrou 57 testes e cinco falhas. A fixture SARIF era outro
defeito latente no teste interrompido pela rejeição Trivy. Rust fmt/Clippy/test,
frontend typecheck/test/build, integrações HTTP e E2E passaram naquele run; isso
não aprova cobertura ou scanners bloqueados.

## Implementação e inspeção

Foram alterados `scripts/rust/check.sh`, `scripts/metrics/check.sh`,
`scripts/tests/test_quality.py`, `scripts/tests/test_shell.py` e a descrição Geiger
em `scripts/security/native-scanners.md`. Não houve edição de código de produto,
manifests/licenças, thresholds, regras de scanner ou registros brutos do run.

O novo oráculo Geiger usa dois membros, sendo um independente da árvore do
executável, e prepara cenários de sucesso, erro bruto 17 no segundo membro,
relatório vazio e metadata vazio. O teste geral de encaminhamento Rust mantém
os demais comandos; Geiger passa ao teste próprio que cobre metadata e membros.
Os testes LCOV/Trivy existentes continuam exigindo rejeição de entradas inválidas.

A inspeção leu results.json, logs brutos e relatórios toolbox/Ruff; comparou o
fluxo com os adapters/fixtures e revisou as diferenças contra previous.zip.
A fonte local de Geiger em
`/home/rodrigo/.cargo/registry/src/index.crates.io-1949cf8c6b5b557f/cargo-geiger-0.14.0/src/main.rs`
confirma a exigência de pacote; `src/args.rs` declara manifest-path/all-features.
A fonte do preparo toolbox mostra que uma receita nova pode baixar artefatos;
o preparo não foi acionado sem autorização de download.

Nenhum script, teste, lint, scanner ou validador foi executado nesta tarefa.
Regressões novas/alteradas estão preparadas, sem resultado de execução.
AC-01 a AC-11 de scripts-verification permanecem **UNVERIFIED nos inputs atuais**.
As outras duas features continuam sem nova aprovação; suas inspeções anteriores
vinculadas aos scripts alterados foram preservadas antes de registrar esta revisão.

## Preservação e retomada

`previous.zip` e `inputs-before.json` preservam os arquivos diretamente afetados;
`previous-related-evidence.zip` preserva evidências/convergências relacionadas.
`changed-files.json` identifica os bytes editados e `run-observations.json` registra
os resultados/fontes observados, com hashes. Os dados originais em `.local/` não
foram modificados. Mudanças prévias do checkout foram preservadas.

Skill primária: `.codex/skills/software-delivery/SKILL.md`, ponte versão 1,
SHA-256 `b0166f50c4532b55cae5ac7e4cde24c35767d56e7db142ea7001402fdd2b94a0`.
Contribuição: diagnóstico por inputs/artefatos e distinção entre correção,
preparo e aprovação executada. Não houve delegação.

T015 registra a correção inspecionada; T016 mantém os preparos/decisões pendentes.
Foram solicitadas definição de licença e autorização específica dos downloads de
cobertura/Trivy; sem resposta até este registro. CodeQL depende também da
elegibilidade. Não houve downloads, preparos ou instalação de dependências.
Após os preparos, T014 segue o fluxo canônico: pedido de commit, pergunta obrigatória
e escolha explícita; com sim, suíte completa sem filtros. A cobertura real continua
desconhecida e poderá revelar trabalho adicional.
