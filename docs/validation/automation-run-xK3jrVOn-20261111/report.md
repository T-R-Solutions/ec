# Análise do run-xK3jrVOn e correções — 11/11/2026

Pedido: examinar cada log e corrigir as causas sem fragmentar a configuração ou
manter configuração obsoleta. A instrução posterior excluiu Firefox das correções.
As alterações locais anteriores foram preservadas. Não houve commit/publicação.

## Evidência examinada

Foram lidos integralmente os **1.115 arquivos, 18.764.674 bytes** do prefixo
`run-xK3jrVOn-*`, em `.local/logs/2026-11-11/`. O inventário
`analysis-xK3jrVOn-inventory.log` registra tamanho, linhas, SHA-256 e formato de cada
arquivo, comparações por tarefa e 208 diagnósticos nativos. Todos os arquivos
estruturados eram JSON legível; nenhum resultado individual divergiu do agregado.
Os streams brutos originais permanecem intactos, sujeitos à retenção canônica.

As 54 tarefas resultaram em 45 passed, quatro findings, três error, uma measured
e uma blocked. O exit final foi 2, com limpeza concluída, sem cancelamento,
sem inputs alterados e sem locks atualizados durante o run. O hash final solto
fornecido na mensagem não foi tratado como aprovação; prevalecem os recibos.

## Falhas e correções

| Grupo | Causa confirmada | Correção e limite |
| --- | --- | --- |
| ShellCheck | SC2100 interpreta `plugin=llvm-cov` como aritmética provável | Valor textual entre aspas; preservado o comando llvm-cov |
| Ruff | S104 em objeto fixture de bind rejeitado | Justificativa pontual no teste; nenhuma exclusão de arquivo/regra |
| Testes de scripts | Duas falhas em 147 casos: seleção de complementares ignorava os preparos Cargo; assert de diagnóstico esperava redação anterior | Expectativa exata de dependências antes de Grype/Syft; assert atualizado sem remover o teste |
| Geiger Server/Agent | Plugin upstream 0.14.0 não contabilizou 14 READMEs incorporados e Rust gerado por serde_core; código nativo 1 nos primeiros membros | Plugin corrigido preparado e instalado offline no host, com autorização; gates ainda não reexecutados |
| CodeQL | js/file-access-to-http no proxy: host/porta lidos do arquivo eram enviados diretamente à API HTTP | Host loopback imposto como invariante; porta inteira validada e convertida explicitamente; regressão do proxy com HTTP simulado escrita |
| Bandit | 370 ocorrências: imports, subprocessos, asserts de testes, temporários privados e fixture de bind | Triagem individual registrada; guard contra Python otimizado aplicado. Política estrita preservada enquanto a decisão de triagem compartilhada está pendente |
| Firefox | Toolbox falhou em resolve_active; central status só encontrou Chromium 1.56.1 | Fora do escopo por instrução posterior; sem preparação ou modificação de configuração para omitir a tarefa |

O proxy continua consumindo as portas de `scripts/config/automation.json`.
Não foi criado outro arquivo de configuração. A restrição loopback é validação
de segurança, compatível com o carregador Python, sem duplicação de portas.
A conversão numérica restringe o dado enviado à API; não foi suprimida a consulta
CodeQL. [Descrição oficial do achado](https://codeql.github.com/codeql-query-help/javascript/js-file-access-to-http/).

### Geiger: preparo e identidade

A correção já existente em `tools/quality/vendor/cargo-geiger/src/scan/supplemental.rs`
foi revisada e preservada: dados incorporados são demonstrados pela AST; fontes Rust
geradas entram nos contadores do motor Geiger com proprietário único. Fonte
ambígua, desconhecida ou inválida mantém erro. Nenhum README ou fonte gerada foi
apagado e nenhum warning foi filtrado pelo runner.

O build em `/tmp` usou `cargo build --offline --release`, quatro jobs, otimização 1
e o toolchain ativo. `cargo install --offline --force --path ... --root ~/.cargo`
foi autorizado para a instalação externa e terminou com zero. Não houve download.
O pacote foi identificado como `0.14.0+ec.1`; manifest e lock canônicos registram
esse identificador. O runner continua chamando somente o plugin instalado, sem
build automático, fallback ou nova variável/configuração de ferramenta.

Binário instalado: `~/.cargo/bin/cargo-geiger`, SHA-256
`ac908287a94f2070f0881e7cf1010478d2c432c8b2813ecb87e3092115a55360`.

Os logs `repair-xK3jrVOn-geiger-{build,install}.log` conservam saída do build,
identidades, lock resolvido, código anterior e confirmação de que as fontes Rust
compiladas coincidem com as canônicas. As dependências preparadas continuam no
Cargo home; intermediários deste reparo são descartáveis. Compilar/instalar não
comprova o inventário de todos os membros nem a execução das regressões.

### Bandit: divergência de política ainda não resolvida

A revisão por ocorrência está em `analysis-xK3jrVOn-bandit-review.log`:

| Regra | Quantidade | Contexto |
| --- | ---: | --- |
| B101 | 249 | Asserts nos quatro harnesses de integração/E2E; não são controles de autorização do produto |
| B603 | 62 | Execução com argv, sem shell=True; programas locais, harnesses ou executor Toolbox |
| B607 | 38 | Programas descobertos no PATH do operador, incluindo Docker, Git, Bash e jq |
| B404 | 18 | Imports de subprocess; as chamadas concretas exigem a revisão acima |
| B108 | 2 | Namespace privado UID/hash em /tmp e fixture correspondente; sem arquivo público compartilhado |
| B104 | 1 | Bind inseguro simulado e rejeitado antes de iniciar o PostgreSQL |

A documentação do [B101](https://bandit.readthedocs.io/en/latest/plugins/b101_assert_used.html)
confirma que Python otimizado remove asserts. `settings.py`, importado pelos
quatro harnesses, agora rejeita esse modo antes de iniciar serviços. O teste
correspondente foi escrito. O [B603](https://bandit.readthedocs.io/en/1.7.7/plugins/b603_subprocess_without_shell_equals_true.html)
requer revisar os argumentos mesmo sem shell; somente usar argv não dispensa
essa revisão.

As justificativas Ruff existentes não são consumidas pelo Bandit. Foi solicitada
uma decisão explícita sobre unificar a triagem pontual, preservando relatórios
brutos e reprovando ocorrências sem revisão. Não houve resposta específica até
este registro: `--ignore-nosec`, inventário integral e todos os níveis continuam
ativos. Não foram adicionados skips, baseline ou aprovação artificial. Portanto,
o Bandit permanece pendente; os 370 achados históricos não foram convertidos em zero.

## Tarefas aprovadas e avisos adicionais

- Governança: cinco tarefas sem erro, incluindo instalação/SDD e testes.
- Rust: fmt/check/clippy/test/machete/audit aprovados nos dois componentes.
  Nenhum teste ignorado foi indicado nos resumos nativos. Os erros do Geiger
  permanecem independentes desses resultados.
- Deny: passou conforme política, mas avisou sobre versões simultâneas de
  block-buffer, cpufeatures, crypto-common, digest, sha2 e wasi no Server.
  São grafos de dependências distintos; não foi forçada uma atualização de API
  para esconder avisos. A política mantém `multiple-versions = warn`.
- Deny: BSD-2-Clause, BSD-3-Clause, ISC e CC0-1.0 não foram usadas em nenhum dos
  dois grafos observados; permissões removidas do único TOML. Unicode-3.0/Zlib
  continuam necessárias ao Server. Exceções GPL dos crates próprios continuam
  válidas no workspace correspondente; avisos do workspace companheiro não
  justificam duplicar a configuração nem apagar exceções usadas.
- Cobertura: Server 1439/1554 (92,60%), Agent 626/686 (91,25%), frontend 205/227
  (90,31%). São observações do run original, sem promoção para os bytes corrigidos.
- Frontend: typecheck, testes, build e cobertura aprovados no run original.
- Segurança: Gitleaks/Opengrep e eficácia das regras, OSV, Trivy, Grype e YARA-X
  aprovados. Exits nativos não zero nas fixtures positivas Gitleaks/Trivy são
  esperados pelos seus oráculos; não foram confundidos com erros de scan.
- CodeQL Server/Agent: zero achados; frontend tem o único resultado detalhado
  acima. Consultas e relatórios de cada componente foram preservados.
- Syft: medição concluída, sem parecer de vulnerabilidade. O aviso de nome/versão
  da origem derivado do diretório ocorreu no SBOM isolado e no inventário de Grype;
  não é falha de pacote. Identidade do snapshot é mantida pelos hashes do runner.
- Três integrações HTTP, Playwright com API simulada e jornada real Chromium
  passaram. Firefox não executou a jornada.

## Validação e pendências

Realizado nesta tarefa: inspeção de fontes/consumidores, leitura e confronto dos
logs, consulta de metadados Toolbox/Cargo, compilação e instalação do plugin,
revisão do diff. Testes de regressão e mudanças nos gates foram escritos, mas não
executados, conforme `docs/development/task-verification.md`.

A suíte completa não foi executada novamente. Aceites SDD permanecem sem promoção.
Retomar validação pelo fluxo canônico ou por instrução explícita da sessão.
Bandit depende da decisão sobre sua política; Firefox foi excluído desta tarefa.

## Atualização de disponibilidade do Firefox

Após o usuário informar a instalação, `toolbox central status --tool playwright`
passou a listar a variante Firefox 1.56.1. A revisão
`9dea8d49a7b80802e323a768357b81905937fbfabd68fca61c77d291f6810dbc`
registra `native_version: Version 1.56.1` e verificação nativa em
`2026-11-12T00:26:19.821210+00:00`. Existe uma única revisão Firefox elegível
para a versão ativa, conforme o seletor da Toolbox.

`docker image inspect` confirmou a imagem
`sha256:85f34c82201dcd9b400805dfc879fed090b364a664808d926e775f215852e890`.
A primeira consulta tentou ler Labels ausentes e retornou erro de template;
a consulta corrigida do ID retornou zero. Nenhum container foi iniciado.

O runner já fornece `--browser firefox`; nenhuma configuração, ativação ou
instalação adicional foi necessária. O bloqueio histórico do run permanece
registrado, mas o recurso agora está disponível. A jornada E2E continua sem
nova execução; esta inspeção não aprova seu comportamento.

## Resultado original por tarefa

| Tarefa | Estado | Exit |
| --- | --- | ---: |
| docs:governance | passed | 0 |
| docs:installed | passed | 0 |
| docs:skills | passed | 0 |
| docs:sdd | passed | 0 |
| docs:governance-tests | passed | 0 |
| scripts:syntax | passed | 0 |
| scripts:shellcheck | findings | 1 |
| scripts:ruff | findings | 1 |
| scripts:tests | error | 1 |
| server:fmt | passed | 0 |
| prepare:server-cargo | passed | 0 |
| server:check | passed | 0 |
| server:clippy | passed | 0 |
| server:test | passed | 0 |
| server:machete | passed | 0 |
| prepare:rustsec | passed | 0 |
| server:deny | passed | 0 |
| server:audit | passed | 0 |
| server:geiger | error | 2 |
| prepare:agent-cargo | passed | 0 |
| prepare:postgres | passed | 0 |
| server:coverage | passed | 0 |
| server:harness-build | passed | 0 |
| agent:fmt | passed | 0 |
| agent:check | passed | 0 |
| agent:clippy | passed | 0 |
| agent:test | passed | 0 |
| agent:machete | passed | 0 |
| agent:deny | passed | 0 |
| agent:audit | passed | 0 |
| agent:geiger | error | 2 |
| agent:coverage | passed | 0 |
| agent:harness-build | passed | 0 |
| frontend:typecheck | passed | 0 |
| frontend:test | passed | 0 |
| frontend:build | passed | 0 |
| frontend:coverage | passed | 0 |
| security:secrets-rules | passed | 0 |
| security:secrets | passed | 0 |
| security:sast-rules | passed | 0 |
| security:sast | passed | 0 |
| security:dependencies | passed | 0 |
| security:trivy | passed | 0 |
| security:codeql | findings | 1 |
| security:grype | passed | 0 |
| security:bandit | findings | 1 |
| security:syft | measured | 0 |
| security:yara-x | passed | 0 |
| integration:publication_http | passed | 0 |
| integration:orchestration_http | passed | 0 |
| integration:agent_http | passed | 0 |
| e2e:playwright | passed | 0 |
| e2e:chromium | passed | 0 |
| e2e:firefox | blocked | 3 |
