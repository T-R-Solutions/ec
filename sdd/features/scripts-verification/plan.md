# Plano — rota completa

## Correção do run-oUebC3O4 — 2026-11-07

D-13 / R-13 / REQ-04/09/10/11: corrigir causas demonstradas pelos logs sem reduzir
gates. Geiger 0.14.0 exige manifesto de pacote concreto (confirmado na fonte local
`cargo-geiger-0.14.0/src/main.rs`); percorrer todos os membros do metadata, preservando
relatório/exit individual, cobre inclusive membros fora da árvore do executável.
Fixtures devem criar AGENTS.md/README.md e relatórios completos Trivy/SARIF; a
rejeição de denominador vazio deve usar exit 2, preservando 1 para cobertura baixa.
Risco de falso aceite por fixtures incompletas ou supressões globais: manter
inventários, parsers e política de licenças; justificativas Ruff por ocorrência.
Preparo de ShellCheck divergente exige revisão/preparo explícito, sem editar hashes
do lock para contornar a rejeição. Downloads e licença própria dependem de decisão
do usuário; execução segue a política canônica e T014 permanece pendente.

T015 registra implementação/inspeção; T016 reúne os pré-requisitos pendentes.
Skill primária software-delivery, ponte versão 1, caminho efetivamente lido
`.codex/skills/software-delivery/SKILL.md`, SHA-256
`b0166f50c4532b55cae5ac7e4cde24c35767d56e7db142ea7001402fdd2b94a0`:
triagem dos inputs/artefatos, separação de preparo e execução e limites do resultado.
Não houve delegação. Evidências e originais em
`docs/validation/automation-run-oUebC3O4-20261107/`.

D-01 / R-01 / REQ-01: inventariar por conteúdo e preservar originais antes de remover.
Risco de fonte única/referência quebrada; cópia por arquivo em previous.zip, sem
modificar pesquisa/relatórios anteriores.
D-02 / R-02 / REQ-02: manter automate.py e execute/terminate; substituir configuração
genérica de pins absolutos/placeholder por plano Ec + ec.toml. Alternativa de preencher
exemplos manteria stacks sem consumidor e exigiria pins pessoais. Risco de perda de
exit/log, inferência de aprovação, downloads ou concorrência; testes negativos e lock.
D-03 / R-03 / REQ-03: política única, referências em entradas e só dois pacotes alterados.
Risco de drift; precondições e sincronização seletiva, preservação de source_files,
bundle e versões anteriores; autorização real para destino protegido.
D-04 / R-04 / REQ-04: comandos conforme manifests/guias, caches offline e harnesses
sequenciais em container dedicado; scanner ausente bloqueia. Risco de sobrescrever
histórico: Firefox grava por run, sem mudar cenários/oráculos de domínio.

## Skills consultadas e contribuição

- `.codex/skills/software-delivery/SKILL.md`, ponte versão 1; SHA-256 `86383b8494cab58e79cf2c31e2f0375a4851d6c60b9de23c89b8b066d3787e1f`. Primária: comandos/inputs reais e separação de preparo/verificação.
- `.codex/skills/cli-engineering/SKILL.md`, ponte versão 1; SHA-256 `530da9febf3cc02dad798db1c484a4dcffcdd40a5df3d660c755b197563ee466`. Auxiliar: plano, cwd, exit bruto/agregado e bloqueios explícitos.
- `/home/rodrigo/.codex/skills/.system/skill-creator/SKILL.md`; SHA-256 `6656e54755638e8efcf275a472b9672eaa8a9a1b9e59dc210e275b03b59e1e66`. Auxiliar para edição limitada das duas skills; software-testing foi lida como objeto de edição, sem ativar especialistas por catálogo.

Não houve delegação. Não foram carregadas todas as skills do catálogo. As versões
anteriores dos dois SKILL.md estão no backup; hashes atuais identificam os conteúdos
efetivamente usados após a revisão. Bibliotecas de aplicação não foram instaladas.

## Reintegração de Opengrep/OSV — rota completa

D-05 / R-05 / REQ-05: restaurar consumidores úteis, substituindo somente regras e
paths herdados. Ausência de configuração/instalação não fundamenta inaplicabilidade.
Opengrep 1.33.1 com regras locais e fixtures, fonte Rust/TS e inspeção textual de .vue;
OSV 2.7.0 offline nos três locks, inventário completo e bases registradas por hash.
Risco: falso aceite por skips/erro/ausência/base divergente; report parsers estritos,
versões e escopo explícitos. Sem coleta online do grafo nem download de executáveis.

Alternativas: scan OSV online enviaria dependências e dependeria de rede em cada
encerramento; offline separa preparo e observação, exigindo atualização explícita de
bases. Não inventar prazo de validade: registrar identidade/data e exigir revisão
operacional de atualidade. Fixtures de subprocesso testam o adapter; não comprovam
parsing/matching do motor, que continua bloqueado se a CLI estiver ausente.
Backups em docs/validation/scripts-security-20261105/previous.zip; relatórios
scripts-ec-20261105 permanecem intactos. Nenhuma skill instalada foi alterada.

Skills consultadas nesta reintegração (pontes versão 1):
- `.codex/skills/application-security/SKILL.md`, SHA-256 `0d992c0538df30bd5e0c6be689990184ec51671225809eac0f01759cc08257c4`. Primária: escopo de controles/scanners, limites, supressões e evidências sem falso aceite.
- `.codex/skills/cli-engineering/SKILL.md`, SHA-256 `530da9febf3cc02dad798db1c484a4dcffcdd40a5df3d660c755b197563ee466`. Auxiliar: wrappers, CLI, código bruto/agregado, diagnósticos e seleção parcial.

Não houve delegação, instalação global, mudança de produto, commit ou publicação.

## Migração shell — 2026-11-06

D-06 / R-06 / REQ-06: entrada automate.sh e bibliotecas Bash por responsabilidade,
jq para dados JSON, sem eval de políticas. Alternativa de wrappers sobre Python
violaria o pedido; cópia de todos os wrappers manteria duplicação e tarefas vazias.
Preservar fontes compactadas antes da remoção; atualizar callers. Manter pré-requisitos
separados do scan e não inventar thresholds/licenças. Linux com timeout/setsid/flock,
ambiente allowlist, runs privados e hashes antes/depois. Risco principal: regressão
de cancelamento/exit/relatório; testes negativos por subprocesso e validação estática.

Skills consultadas, pontes versão 1, sem delegação:

- Primária software-design, `.codex/skills/software-design/SKILL.md`, SHA-256
  `4e214fd5dbf141ee437814a5c817b6924a05e8ad75cf6bc208281155204f6143`:
  responsabilidades e consolidação de contratos repetidos.
- Auxiliar cli-engineering, `.codex/skills/cli-engineering/SKILL.md`, SHA-256
  `530da9febf3cc02dad798db1c484a4dcffcdd40a5df3d660c755b197563ee466`:
  migração de callers, seleção, stdout JSON e códigos de saída.
- Auxiliar software-testing, `.codex/skills/software-testing/SKILL.md`, SHA-256
  `fff974d6760dfae6f64837541beface3e16df32ba481b19830ec45e32365c1b9`:
  testes por subprocesso com oráculos para falhas e efeitos proibidos.

## Configuração de ferramentas — 2026-11-06

D-07 / R-07 / REQ-07: consumir a CLI toolbox existente com snapshots privados e
configuração somente da ferramenta selecionada; preservar relatórios/exit/imagem.
Não modificar adapters compartilhados nem executar Dockerfiles sem os locks reais.
Ausência de adapter é bloqueio; não habilitar fallback online nem instalar plugins.
PATH preserva executáveis do operador e acrescenta binários globais; CARGO_HOME
continua cache de dependências separado. Risco de falso aceite: testes negativos,
configuração explícita, rejects de omissão e inspeção de relatórios.
D-08 / R-08 / REQ-08: política conservadora de origens/licenças sem exceções,
falha por warnings RustSec, inventário de dependências e lints Python de segurança.
Duplicidade de versões é aviso, não vulnerabilidade automaticamente. Validade
operacional proposta nesta configuração: OSV 24h desde registro do snapshot,
RustSec sete dias desde último commit; os hashes não demonstram atualidade upstream.
Playwright autocontido usa API simulada; Firefox real continua obrigatório no E2E.
Não há certificação de segurança, aprovação legal de licenças ou limiar artificial
de cobertura/performance. Benchmarks sem produtor/política continuam bloqueados.

Skills efetivamente consultadas, pontes versão 1, sem delegação:
- `.codex/skills/software-delivery/SKILL.md`: Primária: inputs, preparo e execução; SHA-256 `86383b8494cab58e79cf2c31e2f0375a4851d6c60b9de23c89b8b066d3787e1f`.
- `.codex/skills/application-security/SKILL.md`: Auxiliar: supressões, fronteiras e falhas fechadas; SHA-256 `0d992c0538df30bd5e0c6be689990184ec51671225809eac0f01759cc08257c4`.

Observações de execução de D-07/08: preparo autorizou correção do snapshot Debian
e suporte opt-in SELinux no toolbox compartilhado, com backups e testes próprios;
nenhum adapter bloqueado foi implementado. Gitleaks recebeu uma exceção estrutural
para hashes do perfil, validada contra credencial sintética no mesmo arquivo.
O E2E revelou espera do processo incorreto e remoção precoce do perfil Firefox;
handle explícito e cleanup ordenado corrigiram o defeito, com reexecução real.
Relatório atual: docs/validation/tooling-20261106/report.md.


## Correção das pendências — 2026-11-06

D-09 / R-09 / REQ-04/05/07/08: triagem individual dos 102 achados Ruff,
valores SQL como variáveis psql quoted, HTTP fixo em loopback sem redirects,
sentinela aleatória e exceções de subprocessos por ocorrência justificada.
PATH é fronteira de confiança do operador; nenhum input remoto seleciona executáveis.
ShellCheck 0.11.0 em receita separada tem digest oficial, preservando a 0.10.0.
Plugins Cargo ausentes na busca ampliada são preparados localmente com hashes
de release verificados, sem instalação global. Adapters compartilhados Opengrep
1.33.1 e OSV 2.7.0 executam em imagens readonly/network none, com preparo explícito,
bases por hash e prazo, inventário completo e rejeição de falhas/skips/supressões.
O pedido atual autoriza corrigir a integração compartilhada e substitui a restrição
anterior D-07 de não modificar adapters. Escrita externa exige permissão do sandbox.

Skills consultadas novamente, pontes versão 1, sem delegação:
- application-security primária, `.codex/skills/application-security/SKILL.md`,
  SHA-256 `0d992c0538df30bd5e0c6be689990184ec51671225809eac0f01759cc08257c4`:
  triagem contextual, redução de interpretadores e verificações negativas.
- software-delivery auxiliar, `.codex/skills/software-delivery/SKILL.md`,
  SHA-256 `86383b8494cab58e79cf2c31e2f0375a4851d6c60b9de23c89b8b066d3787e1f`:
  artefatos oficiais, preparo separado e motores reais.

Evidências: `docs/validation/tooling-remediation-20261106/`; registros anteriores
permanecem preservados. Testes de contrato não substituem execução dos scanners.


Ampliação observada de D-09: OSV real encontrou 15 ocorrências de advisories nas
ferramentas frontend; atualizar versões corrigidas e repetir frontend/OSV/E2E.
SAST identificou SQL cujo identificador vinha de duas constantes; consultas
literais preservam os mesmos parâmetros e transação, sem supressão da regra.
Opengrep precisa locale UTF-8 e desativação explícita dos ignores embutidos para
cobrir o inventário incluindo tests/validation.rs. OSV 2.7.0 exige também cache
SCALIBR, confirmado na fonte versionada e no motor real. Teste de validade de
exceções passa a usar UTC, conforme o validador, sem alterar a política de expiração.
Skill Rust auxiliar consultada em `.codex/skills/rust/SKILL.md`: preservar queries,
parâmetros, toolchain 1.99.1/edition 2024 e validar limites com integração real.
SHA-256 da skill Rust: `da7b9ada8326b2d2cb4288ac6f67864c7b9defb833641470904c6fe9221d8891`.


## Gates padrão — 2026-11-07

D-10 / R-10 / REQ-09: promover os checks já existentes ao catálogo padrão, mantendo
uma definição por tarefa e deduplicação. Não criar runners paralelos. Coverage usa
target privado por tarefa e precede harness-build; o supervisor preserva seus
contratos e acrescenta métricas observadas ao resultado. Risco: contaminação de
build ou omissão por filtro; oráculos de catálogo e seleções em test_quality.py.

D-11 / R-11 / REQ-10: gate de linhas >=80% por Server/Agent/frontend, solicitado
explicitamente. LCOV com contadores, inventário independente das fontes, diretório
novo e hashes antes/depois. Rust cargo-llvm-cov all-targets/all-features; frontend
Vitest/provider V8 4.1.12 e include de TS/Vue não importados. Não adotar média global
nem arredondamento; arquivos sem relatório não são excluídos para alcançar o gate.
Risco: fontes omitidas, relatório velho ou produtor falho; fixtures de fronteira e
omissão. A cobertura real permanece desconhecida e pode reprovar.

D-12 / R-12 / REQ-11: manter Trivy 0.61.1 do contrato existente; fixar CodeQL 2.27.3
e packs do bundle correspondente (Rust 0.1.45 e JavaScript 2.4.8), security-extended.
Trivy usa DB schema 2 e checks embutidos vinculados ao binário. DB tem hash e
validade operacional máxima de 24h desde UpdatedAt upstream e NextUpdate futuro,
sem renovar timestamp local. CodeQL exige três bancos finalizados, vinculados a
fontes/manifests/configs e ao bundle por hash. Preparo explícito compartilha lock;
scan copia os artefatos e usa bwrap sem rede/configuração pessoal.
Alternativas rejeitadas: preparar/atualizar em scan introduziria rede e inputs
mutáveis; deixar bancos apenas no frontend omitiria Rust suportado pela versão.
Bwrap ausente bloqueia; falha de namespace não habilita fallback. Conservadoramente,
qualquer aviso/erro em notificações SARIF reprova; todo resultado não suprimido é
finding. Sem zero unsafe, supressões automáticas ou contratação/publicação.
Riscos: licença/elegibilidade, limites do extractor, duração/disco/RAM e defaults
de regras embutidas; fontes/limites/preparo em scripts/security/native-scanners.md.

Skills lidas nesta alteração, pontes versão 1, sem delegação:
- Primária software-delivery, `.codex/skills/software-delivery/SKILL.md`, SHA-256
  `b0166f50c4532b55cae5ac7e4cde24c35767d56e7db142ea7001402fdd2b94a0`:
  separação de preparo/scan, dependências fixadas e inputs por run.
- Auxiliar application-security, `.codex/skills/application-security/SKILL.md`, SHA-256
  `322ad2397adeceb69bcb2215d2ee589512a11cfb2ed0d2bb17cce10c801ce3ac`:
  fronteira de rede/configuração, supressões, redação e classificação de falhas.
- Auxiliar software-testing, `.codex/skills/software-testing/SKILL.md`, SHA-256
  `67fb6565d48fbad42b6c58f48caeaf16c76bcb448f518fe835d2ab480564d6a8`:
  oráculos independentes de limite, inventário e relatórios inconsistentes.

Registros originais anteriores à edição em docs/validation/default-quality-20261107/previous.zip.
Inspeção manual não equivale a scripts executados; ACs afetados permanecem UNVERIFIED.

## Responsabilidades Toolbox/Ec — 2026-11-07

D-14 / R-14 / REQ-12: usar o catálogo e adapters reais da Toolbox compartilhada,
sem editá-los. Trivy Docker 0.76.2 é adicional porque seu adapter somente cobre
vuln; preservar o gate nativo 0.61.1 evita perder misconfig/secret. Grype, Bandit e
Syft têm configurações explícitas; snapshots e imagens sem preparo não ganham
hash fictício. YARA-X permanece bloqueado por adapter e regras ausentes. Gitleaks
history é recusado porque o snapshot não inclui histórico. OSV mantém os três locks.
Atualização de DB é comando explícito separado; Toolbox prepara e Ec adota seu hash.
Syft confirma geração de inventário, preservando measurement e resultado bruto.
Alternativas rejeitadas: substituir integralmente o Trivy perderia cobertura;
inventar regras YARA ou aceitar ausência promoveria lacunas a sucesso.
Riscos: primeira execução, bases/imagens não preparadas e incompatibilidade nativa
das configurações; testes de contrato preparados e scanners reais ainda não rodados.

Skill primária software-delivery, ponte `sdd/chain/software-delivery.md` versão 1,
path `.codex/skills/software-delivery/SKILL.md`, SHA256
`b0166f50c4532b55cae5ac7e4cde24c35767d56e7db142ea7001402fdd2b94a0`.
Contribuição: separar ambiente/dados de política/inputs e preservar identidade e
cobertura dos consumidores. Sem delegação, download, commit ou alteração externa.

## Planejamento de conclusão limitado ao Ec — 2026-11-07

O [plano de conclusão](toolbox-completion-plan.md) detalha D-15 a D-18, riscos,
dependências T019–T024 e critérios C-01 a C-06 para regras YARA-X, locks reais,
suíte padrão e migração Trivy. Planejado, sem implementação ou execução. Toda
escrita de filesystem deverá permanecer no Ec, incluindo estado da integração;
o projeto Toolbox não será alterado. REQ-13 a REQ-18 / AC-13 a AC-18 registram o destino; AC-12 preserva a etapa
intermediária. AC-16 exige checks padrão e AC-17 condiciona a migração Trivy. Este planejamento não renova evidências antigas.

## Sincronização documental e ignore — 2026-11-07

A documentação vigente aponta para o contrato atual e para o plano de conclusão,
sem apresentar T019–T024 como implementadas. `.gitignore` prevê estado local
Toolbox/ferramentas/relatórios, preservando configurações, regras, locks e evidências
oficiais. Sua alteração não redireciona o executor. Documentos de domínio históricos
continuam subordinados aos guias vigentes e observações datadas são preservadas.

Skill primária desta atualização: technical-documentation, ponte versão 1,
`.codex/skills/technical-documentation/SKILL.md`, SHA256
`784a09861f93774ce4a3ee43da709e698b7815645c9ee43c27c9e811aa00b166`.
Contribuição: navegação/autoridades, estado atual versus destino e rastreabilidade
sem renovar evidências. Sem delegação, alteração externa ou scripts de verificação.
Relatório: `docs/validation/toolbox-docs-20261107/report.md`.

## Correções para o pedido de commit — 2026-11-07

D-15 / R-15: índices/volumes locais e biblioteca externa somente leitura.
D-16 / R-16: regras e adapter YARA-X próprios do consumidor, ainda planejados.
D-17 / R-17: paridade das três categorias antes da migração Trivy, ainda planejada.
D-18 / R-18: suíte padrão e medição Syft sem duplicação, ainda planejadas.

O run-Oc8k3QwU demonstrou drift de evidências, IDs não declarados diretamente
neste plano e fixture Trivy que buscava o parser no diretório de fontes da fixture.
Observações antigas foram arquivadas sem renovar hashes. A ponte local necessária
para executar a suíte dentro do Ec é implementada nesta correção; seu aceite depende
de reexecução completa. O primeiro run foi interrompido após confirmar que a CLI
externa gravava relatórios em seus volumes. Nenhum código da Toolbox foi alterado.

## D-19 / R-19 — integração STIC e retirada de ferramentas locais

REQ-19 / AC-19 / T025–T027. Rota completa por mudança de contrato e armazenamento.
Primária: software-delivery, ponte sdd/chain/software-delivery.md versão 1,
skill .codex/skills/software-delivery/SKILL.md e referência build-inputs-and-trust.
Contribuição: separar wrapper/config de instalação, preservar locks e evidências,
inventariar downloads e não inferir equivalência de scanners. SHA256 efetivo e
fonte do template constam em docs/validation/scripts-stic-20261108/migration.json.

Alternativas: conservar a ponte manteria divergência e downloads proibidos pelo
pedido; importar internals ou copiar adapters recriaria a plataforma. Adotado:
wrapper exato, config deslocada, tarefas locais em stacks, eliminação de bridge,
downloaders e instalações. A ausência de APIs públicas mantém gates bloqueados.
Não editar plataforma externa nem gerar locks/IDs substitutos por inferência.

R-19: limpeza impede reutilizar instalações locais; recibo de hash não é backup.
Fontes e configurações anteriores ficam em /tmp/ec-stic-before-20261108, fora do Ec,
para revisão local; binários/bases não têm backup criado nesta tarefa. Reposição
exige aquisição explícita externa. Histórico e índices remanescentes são somente
observações, não locais ativos de execução. Nenhuma ACL foi alterada.

## D-20 / R-20 — transporte nativo, REQ-20 a REQ-26

Rota completa. Wrapper comum com guard adicional para override vazio, sem API
consumer. Scripts mantêm automate.sh como coordenador; Python stdlib em
tools/quality/native_gates.py trata JSON/argv/sinais do gate, reutilizando seleção
inputs.sh e parser Ec existente. Não é serviço, novo schema ou cópia do executor.
A Toolbox mantém controle de versões/imagens/bases/caches. Ec não prepara nem
ativa instalação central nesta tarefa. Risco: instalação vazia, formatos ainda
não homologados e runtime Rust ausente na imagem candidata; nenhum recebe aprovação.

Snapshot é política do Ec, sem staging/export da plataforma. /project ro e /out
selecionado. Base deve ter identidade do diagnóstico, validade de 24h e validação
nativa; RustSec mantém sete dias. Registros de DB já publicados são evidência,
não seleção independente. Segredos não vão em argv nem resumo, streams privados
podem conter dados impressos pelo motor. Supervisor remove argv de metadados e
reserva 120s para limpeza. Mudança central entre passos força repetição.

Playwright ESM absoluto usa Node/browser central, sem node_modules host; build
estático e servidor Node stdlib são Ec. Firefox continua separado. CodeQL usa
bundle/packs centrais, build-mode none e montagens explícitas Rust/Cargo; não
instalar scanner/runtime como contorno. Relatórios parciais nunca aprovam.

Recuperação: cópia completa anterior em .local/native-migration-20261108/before.zip,
manifesto SHA-256 e status Git inicial; comparar precondições antes de restaurar
apenas arquivos desta integração. Nenhum checkout reset/prune ou troca central.

Skills consultadas, pontes versão 1, sem delegação:
- software-delivery: `.codex/skills/software-delivery/SKILL.md`, SHA-256 `b0166f50c4532b55cae5ac7e4cde24c35767d56e7db142ea7001402fdd2b94a0`; primária: preparo separado, entradas, identidade e recuperação.
- application-security: `.codex/skills/application-security/SKILL.md`, SHA-256 `322ad2397adeceb69bcb2215d2ee589512a11cfb2ed0d2bb17cce10c801ce3ac`; auxiliar: fronteira de montagem, logs e supressões.
- cli-engineering: `.codex/skills/cli-engineering/SKILL.md`, SHA-256 `1d1e7d70435a6469bfb9981227ecf0774c723cb4aec2f0ca498078ba568a54b8`; auxiliar: argv, streams, códigos e cancelamento.

## Limpeza por evidência — 09/11/2026

Escopo, critérios L01–L05, skills/ponte/hash, riscos e tarefas no
[plano da limpeza](../../../docs/validation/cleanup-20261109/plan.md).
Nenhuma interface ou política de gate foi removida. G01 retira marcadores de
diretórios preenchidos, G02 caches derivados e G03 a consulta temporária erase.md
da raiz, com arquivo histórico integral e guias nativos mantidos. O mapa corrente
de scripts substitui instruções antigas somente para operação; os registros
anteriores e hashes históricos não são reescritos. Não houve testes nesta tarefa.

## Adequação ao contrato central — 09/11/2026

REQ-27/AC-27 → D-21/D-22, R-21/R-22 → T035–T037. D-21 mantém exec,
TOOLBOX_BIN/PATH e seleção explícita de ambiente Docker/TOOLBOX_STATE nas duas
camadas; R-21 impede transportar o ambiente inteiro ou inferir autorização de host.
D-22 usa database_metadata upstream, sem recibo externo OSV nem prepared_at, e
Checks Bundle externo Trivy observado/estável; R-22 rejeita base velha, futura,
parcial ou divergente, bundle ausente/escrita e identidades misturadas.
Primária software-delivery, ponte 1, pacote .codex/skills/software-delivery/SKILL.md,
SHA-256 b0166f50c4532b55cae5ac7e4cde24c35767d56e7db142ea7001402fdd2b94a0.
[Plano, fontes, alterações e limites](../../../docs/validation/toolbox-alignment-20261109/plan.md).

## Cinco bloqueios Geiger/cobertura — 09/11/2026, rota completa

REQ-09/10/28/29 → D-23/D-24/D-25, R-23/R-24/R-25 → T040–T043.
O pedido vigente permite correções e inspeções, sem executar verificações ou
criar commits. As autorizações históricas de outras tarefas não são reutilizadas.

D-23 / R-23: copiar o mesmo Geiger 0.14.0 já disponível e corrigir sua descoberta
dep-info em fontes versionadas, com licenças, lock e inventário de hashes upstream.
Build/testes específicos offline em target privado, sem instalação global. A AST
identifica dados incorporados; grupos dep-info atribuem fontes Rust geradas a um
único pacote e o motor original fornece os contadores. Não remover arquivos nem
filtrar stderr/exit. Alternativas rejeitadas: dispensar warnings, ignorar extensões
indiscriminadamente, modificar o registro Cargo ou usar outro scanner. Risco:
parser/atribuição incompletos; desconhecidos, ambiguidades e parsing falham.

D-24 / R-24: os harnesses HTTP/PG existentes têm asserções de negócio e exercitam
superfícies sem testes Cargo. Coletá-los antes do relatório, com binários do run
instrumentados e companheiros em targets privados; nenhum relatório histórico
entra na medição. Manter fontes completas e mínimo de 80%, sem exclusões novas.
Encerramento por EOF em stdin privada existe somente com test-failpoints e flag
explícita, para saída normal do Server e gravação dos perfis. O preflight de
integração e os deadlines existentes permanecem; falhas não produzem aprovação.
Alternativa de repetir somente testes Cargo deixaria a mesma lacuna de coleta.

D-25 / R-25: acrescentar testes Rust de contratos e testes Vue/Pinia de estado,
eventos e ciclo de vida, com oráculos de rejeição, bytes, replay e autoridade.
Sem biblioteca DOM instalada e sem autorização de downloads, usar renderer Vue
de teste e explicitar o compiler-dom já presente como devDependency. Isso não
comprova DOM, foco ou navegador; os harnesses existentes continuam obrigatórios.

Skills lidas/aplicadas, pontes versão 1, sem delegação:

- Primária `.codex/skills/software-testing/SKILL.md`, SHA-256
  `67fb6565d48fbad42b6c58f48caeaf16c76bcb448f518fe835d2ab480564d6a8`:
  particionamento dos comportamentos, oráculos e distinção cobertura/aceite.
- Auxiliar `.codex/skills/rust/SKILL.md`, SHA-256
  `a2926aaeee5df8492c86acd31b09df7ba3c33545b146e635506962f053759b12`:
  toolchain/locks, parser de fontes e composição dos testes Rust.
- Auxiliar `.codex/skills/frontend-testing/SKILL.md`, SHA-256
  `67b711a02c687c87d153c6037ca872d30be3900c0b0a582b86f2d2716daaca06`:
  testes de sessão/componentes, debounce, polling e descarte de respostas.
- Auxiliar `.codex/skills/tool-output-engineering/SKILL.md`, SHA-256
  `90108fc6932bb514bbaaf320117d3727358d3c98afd4130f70a6c21e328d3ad6`:
  logs brutos/códigos nativos e show-env interpretado como dados, sem shell.

[Inspeção e limitações](../../../docs/validation/geiger-coverage-remediation-20261109/report.md).
