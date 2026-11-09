# Correção das pendências de ferramentas — 6 e 7 de novembro de 2026

As quatro integrações solicitadas executaram com motores reais. Ruff, ShellCheck,
Machete, Opengrep e OSV passaram após as correções. Cargo Deny executou nos dois
workspaces e **reprovou licenças dos 11 crates internos Ec**, que não têm licença
definida. Advisories, bans e sources passaram. Nenhuma exceção de licença foi
aplicada e não houve definição de licença por inferência.

## Triagem Ruff

[102 ocorrências originais](ruff-before.json), [decisão por arquivo/linha/regra](ruff-triage.json)
e [resultado novo vazio](ruff-after.json). Não são 102 vulnerabilidades confirmadas.

| Regra | Ocorrências | Tratamento observado |
| --- | ---: | --- |
| S603 | 43 | Revisão dos executáveis, argv e origem dos argumentos. Exceção junto a cada chamada: programas de teste literais, binários compilados em caminhos fixos, repositórios temporários, container dedicado e banco UUID. |
| S607 | 31 | Exceção por chamada documenta Docker/Git/npm/Bash do PATH confiável do operador. Não há escolha remota de comando ou interpolação em shell. A hipótese de host/PATH confiável permanece explícita. |
| S608 | 22 | Valores separados em variáveis psql e referências `:'nome'`, com quoting de literal; não concatenados ao SQL. O harness real prova que texto com aspas e `DROP TABLE` retorna como dado e não remove a tabela. |
| S108 | 3 | Cwd e teste de symlink usam o diretório temporário gerenciado e seu ancestral; nenhum arquivo previsível em `/tmp` é criado. |
| S310 | 2 | Cliente HTTP fixo em `127.0.0.1:58139`, sem proxies, outros esquemas ou redirects automáticos. Validação de caminhos ocorre antes da conexão. `Connection: close` preserva o comportamento anterior nos failpoints. |
| S105 | 1 | Sentinela de teste aleatória, usada para provar que variável não é herdada; nenhuma credencial real. |

São 28 alertas corrigidos e 74 diagnósticos de subprocesso/PATH justificados.
As regras continuam habilitadas; não há baseline geral nem ignore de arquivo para
essas categorias. S101 continua dispensada somente onde assert é oráculo de teste.

## ShellCheck e preparo íntegro

Ec seleciona ShellCheck **0.11.0** em receita separada; a 0.10.0 permanece preservada.
O [asset oficial](https://github.com/koalaman/shellcheck/releases/tag/v0.11.0)
publica SHA-256 `8c3be12b05d5c177a04c29e3c78ce89ac86f1595681cab149b65b97c4e227198`.
O preparador conferiu os bytes contra esse digest; ausência de hash e substituição
são rejeitadas nos testes. Não se calculou um hash local para fingir autoridade upstream.

A primeira análise encontrou nove referências a globais fornecidas pelos callers.
Foram adicionadas precondições explícitas para ROOT/PLAN/CODE/TASK_DIR e comentários
pontuais SC2154 para os arrays SURFACES/CHECKS preenchidos pela entrada.
[ShellCheck final](shellcheck-after.json): zero comentários. Sintaxe Bash passou.

## Cargo

A busca inicial ampliada não encontrou deny/machete. Foram preparados localmente
Cargo Deny 0.20.2 e Machete 0.9.2 usando artefatos oficiais com SHA-256 conferido,
sem instalação global. Depois, os binários globais apareceram no ambiente e foram
os efetivamente usados pelo runner; seus caminhos, versões e hashes constam dos
resultados. O fallback `.local/tools/bin` permanece disponível. A operação reproduzível
é `bash scripts/prepare.sh cargo-tools`, com pins em `config/quality/cargo-tools.json`.

Corrigida a posição de `--config` na CLI Deny 0.20.2; `--offline` mantém o scan sem
fetch. Cache de dependências completo foi preparado com `cargo fetch --locked`;
`bash scripts/prepare.sh deny-db` prepara a base no layout específico do Deny.
Dependências internas por caminho receberam versão `=0.1.0`; o gate de wildcards
permanece ativo. Machete e Audit passaram nos dois workspaces.

Deny: [Server](13-server-deny/stdout.log), [Agent](20-agent-deny/stdout.log).
O único erro remanescente é `unlicensed` nos membros privados já marcados
`publish=false`. README informa que a licença do produto não foi definida.
A opção de excluir somente esses membros da checagem de licenças foi apresentada
para decisão do usuário e **não aplicada**. Avisos de versões duplicadas e de
licenças permitidas não encontradas continuam visíveis; não são advisories.

## Opengrep e OSV

[Patch compartilhado](shared-toolbox.patch) e [hashes dos 18 arquivos](shared-inputs.json).
Adicionados módulos separados Opengrep 1.33.1 e OSV 2.7.0, preservando versões antigas.
Escrita externa foi autorizada pelo sandbox, com precondições de hash e backups.
[23 testes da plataforma](toolbox-tests.log) passaram, incluindo download divergente,
relatório inválido, inconsistência de exit, skips, ausência de inventário e oráculos
positivos de regras. Isso é complementado pelas execuções reais abaixo.

As imagens usam UID do operador, readonly, cap-drop, no-new-privileges, fontes
privadas readonly e rede `none`. Opengrep é extraído durante o build, evitando
execução no tmpfs. A CLI 1.33.1 não aceita `--metrics`; version check permanece
desativado e a ausência de rede impede transmissão. Locale UTF-8 permite ler as
regras em pt-BR. A opção versionada `--x-ignore-semgrepignore-files` desativa os
ignores implícitos que omitiam `tests/validation.rs`; o inventário Ec exige todas as
32 fontes, portanto futuras incompatibilidades dessa opção falham no gate.

[Oito testes de regras](opengrep-rule-tests.json) passaram e o [SAST final](sast-after.json)
cobriu as 32 fontes sem erros, omissões ou achados. Um apontamento Rust usava apenas
duas tabelas constantes; foi substituído por consultas literais, preservando os
parâmetros PostgreSQL e a transação, sem suprimir a regra.

`prepare.sh osv-db` baixa bases oficiais crates.io/npm e vincula um snapshot por hash,
com idade máxima de 24h, em `toolbox.json`. OSV 2.7.0 exige os namespaces
`osv-scanner` e `osv-scalibr` e ambas as variáveis de cache; isso foi confirmado na
[fonte do matcher](https://github.com/google/osv-scalibr/blob/5be78a00e3c2/enricher/vulnmatch/osvlocal/matcher.go)
e no motor real. O scan não baixa nem modifica bases. [Identidade da base](osv-database.json).

O [primeiro OSV completo](osv-before.json) encontrou 15 ocorrências de advisories no
frontend. Vite foi atualizado de 7.3.1 para **7.3.7**, Vitest/@vitest/mocker de
4.0.18 para **4.1.12** e esbuild de 0.27.7 para **0.28.2**, dentro da faixa aceita
pelo Vite corrigido, sem override. Foram consultados os advisories oficiais de
[Vite](https://github.com/vitejs/vite/security/advisories/GHSA-rq7h-c2jc-7f22) e
[esbuild](https://github.com/evanw/esbuild/security/advisories/GHSA-g7r4-m6w7-qqqr),
as faixas da base OSV e os manifests npm. Não foram introduzidos ignores de CVEs.
[OSV final](osv-after.json): zero vulnerabilidades, com inventários de 26 pacotes
Agent, 108 Server e 132 frontend nos três lockfiles explícitos.

## Execuções e limites

- `bash scripts/automate.sh run`, [run-QqUf7ohQ](results-full.json): Ruff/ShellCheck,
  43 testes de scripts, 34 de governança, fmt/Clippy/test/build/Machete/Audit em ambos
  os workspaces, frontend typecheck/test/build, Gitleaks, Opengrep, OSV, três
  integrações HTTP e Chromium passaram. Deny reprovou licenças. SDD ainda apontou
  hashes antigos, tratados pela nova coleta documental. Nenhum input mudou no run.
- Firefox teve falhas anteriores de inicialização Marionette. O harness passou a
  preservar `artifacts/firefox.log`, em vez de removê-lo com o perfil temporário.
  `bash scripts/automate.sh run --surface e2e`, [run-y02SwyvS](results-e2e.json):
  Firefox passou nos oito grupos e no cleanup, e Chromium passou. Os erros anteriores
  permanecem nos resultados históricos; não há retry/skip automático. A causa das
  falhas anteriores de startup não foi determinada, pois seus logs eram removidos.
- Um teste de expiração usava data local enquanto o validador usa UTC. A fixture
  passou a usar UTC, preservando a regra de expiração; os 43 testes passaram depois.
- Registros anteriores estão em [previous-evidence.zip](previous-evidence.zip).
  As observações SDD novas são vinculadas a estes resultados e inputs atuais;
  não se renovaram hashes de observações antigas. AC-04 de scripts fica REJECTED
  pelo Deny. AC-01/AC-07 da primeira jornada continuam UNVERIFIED.

Checks completos não estão aprovados por causa da licença indefinida do produto.
Não houve commit, publicação, instalação global, alteração de licença ou aceite
silencioso de risco. Hash de release não é verificação de assinatura; snapshot
OSV por HTTPS e hash local não prova frescor upstream. SAST cobre as oito regras
locais e Vue textual; scanners não certificam segurança completa. Playwright usa
API simulada e Firefox usa Server/Agent/PostgreSQL reais.

Fechamento: `bash scripts/automate.sh run --surface docs --surface scripts`,
[run-ajzm1IER](results-closure.json), passou integralmente (exit 0): auditores,
integridade das skills, SDD comum, 34 testes de governança, sintaxe, Ruff,
ShellCheck e 43 testes de scripts. Nenhum input mudou durante a execução.
`python3 sdd/tools/validate.py --root . --require-fresh` passou. O mesmo comando
com `--require-approved` retorna 1 pelos AC-01/AC-07 UNVERIFIED da primeira jornada
e AC-04 REJECTED de scripts. Esses estados não foram contornados.
