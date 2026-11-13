# Reparação do run-SNe1BAXu — 12/11/2026

Estado atual: escopo somente Ec por instrução posterior. Os gates afetados passaram em run-EWXWaWlJ. Os resultados de imagens abaixo são históricos e não integram o escopo vigente; não foram convertidos em correção ou aceite de risco.

## Diagnóstico

[Resumo do run original](diagnosis.json): 43 tarefas aprovadas, nove checks com
achados e dois erros Clippy, exit final 2. Sem drift ou alteração de lockfiles.
Ruff: 13.027 ocorrências; ShellCheck: 4.234 ocorrências (há repetição por source).
Clippy interrompia o grafo nas primeiras bibliotecas; havia documentação,
formatação, casts e duplicidades transitivas. SAST: dez usos de unwrap em funções
Result; CodeQL: 17 ocorrências Python. Gitleaks: dez indicadores públicos YARA e
39 mapas SHA-256 no histórico. Bandit: revisão por conteúdo invalidada pelas
alterações recentes. Trivy/Grype/Syft reprovaram fontes/imagens; as dependências
da aplicação passaram no OSV. Totais de scanners não são vulnerabilidades únicas.

O hexadecimal final `a1f46a9f68d439b673b0fd38ce67e46180d4194d46c442807af3e12013d958c8`
é o ID do container PostgreSQL exibido na limpeza. O registro de serviços confirma
restauração do estado anterior; essa linha não é uma falha adicional.

## Plano e execução

1. Preservar mudanças existentes e o alcance das fontes; manter política na
   configuração canônica e nos formatos nativos referenciados. Em 13/11/2026,
   adequar Ruff às convenções do código conforme instrução explícita do usuário.
2. Aplicar correções mecânicas seguras com pré-condição SHA-256, revisar o
   resultado e exercitar regressões. Corrigir retornos mascarados, publicação
   de JSON e limites de chamadas, sem alterar filtros para recuperar verde.
3. Corrigir os contratos Rust e decompor responsabilidades longas preservando
   transação, autoridade, heartbeat e entrega. Propagar falhas de mutex, pipe e
   recibo pelo Result; ampliar testes de poison, persistência e codificação.
4. Revisar os falsos positivos por contexto. Revisão Gitleaks exige hash da
   fonte, regra e linha exatos, sem duplicar indicadores públicos na configuração;
   estruturas de integridade históricas têm exceções restritas no perfil nativo.
   Controles negativos introduzem PAT sintético novo nos mesmos caminhos.
   Não excluir catálogos nem aprovar histórico por revisão da árvore atual.
5. Corrigir a origem das imagens na Toolbox autorizada: canais Debian/security
   e updates gerados do snapshot canônico, upgrade dos pacotes da base e usuário
   padrão não root. Reutilização de payload exige checksum oficial atual.
6. Qualificar candidatas com operações reais e oráculos antes da ativação;
   conferir as bases da versão selecionada. Executar a suíte completa sobre
   entradas estabilizadas e preservar os achados residuais sem falso verde.

## Configuração

Ec mantém scripts/config/automation.json como catálogo e fonte de políticas.
A política Clippy única é referenciada por esse catálogo via CLIPPY_CONF_DIR.
Permite somente os seis nomes transitivos já observados em duplicidade; novas
crates duplicadas continuam bloqueando. Deny e Audit preservam o inventário e a
análise de cada versão. A recomendação oficial é não forçar uma API incompatível
apenas para deduplicar: [Clippy](https://clippy.rust-lang.org/master/index.html#multiple_crate_versions).
Ruff substitui ALL/preview pela seleção estável explícita na política já existente,
por autorização do usuário em 13/11/2026. Mantém erros, imports, compatibilidade
Python 3.11, construções suspeitas e bugs; preserva unittest, namespaces, CLI e
tipagem/documentação incremental. Quatro exceções E402 permitem bootstrap da
raiz; concatenação de listas continua válida e UP038 foi retirada do Ruff atual.
Os ignores antigos de docstrings/formatter foram removidos com ALL. Segurança
Python permanece no Bandit integral, com revisão por hash/regra/linha e sem skips.
[Seleção Ruff](https://github.com/astral-sh/ruff/blob/0.11.13/docs/linter.md) e
[remoção de UP038](https://docs.astral.sh/ruff/rules/non-pep604-isinstance/).
Não há exclusões novas de fontes nem configuração por diretório.

Na Toolbox, toolbox.json continua único para versões, base e snapshot. As receitas
consomem apt.sources.list efêmero gerado pela preparação; blocos APT antigos foram
retirados, inclusive dos estágios finais. [Debian Security](https://www.debian.org/security/)
e [Snapshot Debian](https://snapshot.debian.org/) fundamentam os canais assinados.
O pull autorizado confirmou que o digest Debian vigente ainda era o publicado;
não foi inventado um digest novo.

Candidatas selecionadas após leitura oficial: CodeQL 2.27.4, Grype 0.121.3,
Syft 1.57.1, Playwright 1.65.0 e Node 22.23.5 LTS de segurança.
[CodeQL](https://github.com/github/codeql-cli-binaries/releases/tag/v2.27.4),
[Grype](https://github.com/anchore/grype/releases/tag/v0.121.3),
[Syft](https://github.com/anchore/syft/releases/tag/v1.57.1),
[Playwright](https://github.com/microsoft/playwright/releases/tag/v1.65.0) e
[índice Node](https://nodejs.org/dist/index.json).
Na rodada seguinte foram qualificadas e ativadas Bandit 1.9.4, Gitleaks 8.30.1
e OSV 2.7.1, com checksums oficiais. O OSV atualizado detectou a vulnerabilidade
da fixture lodash 4.17.20, corrigindo a lacuna da versão anterior. Wheels Bandit
foram atualizados pelo manifesto único; pip check aprovado na construção.
Não se afirma eliminação de toda vulnerabilidade embarcada por atualizar a release.

## Evidência parcial

- Ruff: 10.163 edições mecânicas em 52 arquivos, com hashes das entradas
  conferidos; formatter aplicado às fontes Python próprias. O estoque residual
  de estilo/documentação/tipos motivou a adequação autorizada do perfil. Após ela,
  houve 11 correções seguras de imports/precedência e correção explícita de captura
  tardia em callbacks; scan atual Ruff zero. [Controles reais do perfil](ruff-profile.json)
  aceitam unittest/CLI e detectam F401/F821/B023, inclusive diante de noqa; Bandit
  detecta shell=True na fixture estática. Nenhuma fixture insegura foi executada.
- ShellCheck: diagnóstico nativo posterior com zero achados, preservando todos
  os opcionais, severidades e fontes. Regressão de JSON inválido garante que o
  último resultado válido não seja substituído por saída vazia ou truncada.
- Clippy: Server e Agent sem diagnósticos nos quatro grupos, com -D warnings,
  após corrigir todas as bibliotecas e consumidores. O run-QXU6jfUW confirmou
  testes, cobertura e integração; a última rodada completa ainda será registrada.
- Toolbox: 88 testes aprovados; 13 imagens reconstruídas e qualificadas, com
  extração CodeQL, inventários, scanners e interação real Chromium/Firefox.
  Rodada posterior repetiu os 88 testes e qualificou as três novas releases.
- Os runs intermediários conservam reprovações e correções de fixtures/imports.
  Uma execução anterior foi invalidada por mudança de configuração durante os
  testes; ela não foi usada como aprovação de bytes posteriores.

[Métodos e autorização observados](method.json). Resultados finais serão
registrados separadamente, sem renovar os hashes dos runs históricos.

O run-QXU6jfUW completo teve 49 tarefas aprovadas e cinco com achados, sem erros
operacionais. A cópia de IOC público no perfil Gitleaks causou o achado YARA e
foi substituída pela revisão por identidade; run-C9GwG2fJ confirmou Gitleaks
(dez revisadas/zero pendentes), YARA (zero) e 180 testes. Bandit 1.9.4 detectou
393 ocorrências, incluindo uma fixture nova de marcador de redação; todas foram
revisadas novamente após estabilizar imports/callbacks. Aprovação depende do gate.


## Resultado final — 13/11/2026

A suíte completa `bash scripts/automate.sh run --scope complete` terminou no
[run-ZPA4aciU](run-ZPA4aciU.json): 51/54 tarefas aprovadas, três com achados,
zero erros/bloqueios, exit 1, nenhuma alteração de inputs/locks durante execução
e serviço PostgreSQL restaurado. Passaram Ruff, ShellCheck, os dois Clippy,
CodeQL nos quatro alvos, Bandit (393 revisadas/zero pendentes), Gitleaks
(dez indicadores públicos revisados/zero pendentes), SAST, OSV, YARA, testes,
coberturas e todos os HTTP/E2E. Automação: 180 testes. Toolbox: 88 testes.

Cobertura de linhas: Server 93.12%, Agent 91.01% e
frontend 90.31%, todas acima do mínimo de 80%.

Depois da correção única `RUN cd` → `WORKDIR` do Playwright, Chromium e Firefox
foram reconstruídos e qualificados. O preparo já atualiza o registro Firefox;
a validação final abaixo observa os dois novos digests e não renova os resultados
das imagens anteriores. Chromium foi ativado após encerrar a rodada completa.

`bash scripts/automate.sh run --check trivy --check grype --check syft --check chromium --check firefox`
terminou no [run-NSqpobP8](run-NSqpobP8.json): 13 tarefas aprovadas e
três com achados, sem erros/bloqueios; as duas jornadas passaram e DS-0013
desapareceu. Todas as fontes Ec permaneceram idênticas entre essas duas rodadas.

| Check pendente | Ocorrências nas imagens | Achados nas fontes Ec |
| --- | ---: | ---: |
| Trivy | 5561 | 0 |
| Grype | 5822 | 0 |
| Syft + Grype | 5822 | 0 |

No Trivy são 5.548 ocorrências de vulnerabilidade e 13 avisos DS-0026
(HEALTHCHECK). Dentre as vulnerabilidades, 327 têm FixedVersion informada e
5.221 não têm. No Grype, 5.490 correspondências mais 332 ignoredMatches mantidos
pendentes formam as 5.822 ocorrências; os mesmos resultados são consumidos pelo
gate Syft. Essas contagens não devem ser somadas entre scanners.

Os três resultados reprovam o agregado. Há pacotes Debian sem FixedVersion
informada pela base e dependências embarcadas em bundles oficiais, inclusive
com versões corrigidas publicadas separadamente. Substituir bibliotecas dentro
dos bundles sem suporte não foi considerado uma atualização qualificada.
Avisos HEALTHCHECK em ferramentas CLI e associações de headers/camadas seguem
pendentes de decisão contextual; não foram filtrados globalmente.
[Inventário e plano de retomada por imagem](remaining-image-findings.json).
[Qualificação, fontes e referências da Toolbox](toolbox-repair.json).

O fechamento documental atualiza tarefas/rastreabilidade/evidências sem modificar
fontes executáveis, políticas nativas ou lockfiles. Nenhum commit ou publicação
foi solicitado. T068 registra a remediação externa ainda necessária.

Fechamento documental: `bash scripts/automate.sh run --surface docs`,
run-dcCdYjcQ, cinco tarefas aprovadas, exit 0, sem drift.
[Recibo documental](documentation-validation.json). O agregado dos scans continua REJECTED.


## Alteração de escopo: somente Ec — 13/11/2026

Por instrução explícita, retirada a auditoria da Toolbox do consumidor. Removidos
inputs.images, resolução/exportação Docker e scans das imagens das ferramentas.
Syft usa diretórios/arquivos Ec; Trivy filesystem e Grype do SBOM Ec preservam
severidades, inventário dos três locks e rejeição de achados/relatórios inválidos.
O campo antigo é rejeitado e testes impedem comandos de scan de imagens.

174 testes Ec passaram em run-jbTDZYji; a primeira rodada encontrou apenas import
sem uso no Ruff e revisão Bandit invalidada pelas edições. Após correção/revisão,
run-EWXWaWlJ passou nos gates Ruff/Bandit/Trivy/Grype/Syft e governança, exit 0,
sem drift. Não foi repetida a suíte completa de produto; nenhuma auditoria, teste
ou reconstrução da Toolbox externa foi executada nesta correção de escopo.
[Recibo atual](scope-ec-only-validation.json).

Fechamento deste escopo: run-VOkc9sfQ, cinco tarefas documentais aprovadas, exit 0
e sem drift. [Recibo](scope-ec-only-documentation.json).
