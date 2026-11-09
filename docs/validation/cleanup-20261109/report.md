# Limpeza da aplicação Ec — 09/11/2026

A limpeza retirou **32 arquivos** com evidência e recuperação: 17 marcadores
`.gitkeep` em diretórios já preenchidos, 14 caches CPython com fontes presentes
e a consulta temporária `erase.md` da raiz. O guia foi também arquivado
[integralmente como histórico](retired/erase.md). Foram atualizados 12 documentos
e `.gitignore` (13 arquivos). Nenhum código funcional, dependência, manifest, lock,
migration, regra, parser, fixture, teste ou licença foi removido ou refatorado.

Não houve testes, scripts de verificação, automate.sh, downloads, preparo de
ferramentas, commits ou publicação. Inspeção estática não aprova a suíte ou o
produto. Os critérios operacionais SDD não foram renovados.

## Escopo e método efetivamente aplicados

Foram lidos AGENTS.md, sdd/project.md, docs/constitution.md,
docs/architecture/context.md, sdd/workflow.md,
docs/development/task-verification.md, ADR-001/002, catálogo e pontes pertinentes.
O [plano](plan.md) registra critérios L01–L05, decisões, riscos e contribuições
das skills; [inputs.json](inputs.json) guarda caminhos/hashes observados. Primária
software-design, ponte 1, SHA-256 do SKILL.md
`0c9c262d893476999857858f12dba2b789dbd522294d5b19c1ed99831a7aaddc`;
auxiliar workspace-integrity-engineering, ponte 1, SHA-256
`bcc2b63eef177063847e7a95a433fe95bef59cccf622ff2963a55d9d4d0e9d01`.
Ambas foram lidas/aplicadas para análise de consumidores e recuperação, sem
executar suas avaliações automatizadas.

| Superfície | Análise efetivamente realizada e resultado |
| --- | --- |
| Server | Manifests dos seis membros Cargo, caminhos de dependências/importações, entrypoints, includes de fixtures e das migrations 001/002/003. Domínios reservados e endpoint result legado mantidos; não houve revisão semântica integral de cada método HTTP/SQL. |
| Agent | Manifests dos cinco membros, imports e composição control/workspace/execution/reporting/app, toolchain e lock. Todos mantidos; não foi executado job nem examinada uma instalação operacional. |
| Frontend | package.json/lock, Vite/Vitest, tsconfig, index, imports/exports de app/shared/domínios, testes e scripts npm. Pinia/Vue usados; ferramentas de build/tipos/cobertura têm consumidores. Nenhuma dependência classificada como descartável. |
| Testes | Descoberta Cargo/Vitest/unittest, fixture ProjectPipeline, harnesses HTTP e imports indiretos por sys.path, Firefox e Playwright com API simulada. Fontes e fixtures preservadas; nenhuma execução. |
| scripts/ | Todos os 45 arquivos não cache inventariados por caminho/hash em [scripts-map.json](scripts-map.json), incluindo entradas, shell/source, catálogo, supervisor, regras, fixtures, configs e testes. Contratos e mecanismos indiretos detalhados abaixo e no [guia vigente](../../development/scripts-inventory.md). |
| Dependências/configuração | Dois workspaces, três locks de produto, onze configs nativas, suites CodeQL, regras Opengrep/YARA, scripts npm e ferramentas externas. [Manifests observados](application-manifests.json). Pacotes transitivos mantidos; não foi inferida inutilidade por falta de import direto. |
| Governança/SDD | Leitura de perfil, pontes, instalador/auditor e suas formas de descoberta; análise das evidências que mencionam os candidatos. Pacotes/fontes/baseline e hashes históricos mantidos; auditores não executados. |
| Documentação/pesquisa | Entradas, arquitetura, guias de operação/verificação, índices, planos da feature e referências dos candidatos. Pesquisa TeamCity e docs/validation preexistentes preservados integralmente; não foi reavaliada a correção de cada evidência histórica. |
| Artefatos locais | Inventário por arquivo e classificação estrutural de .local/build, automation, toolbox, security, native-migration e skill-installation; [contagens](local-surfaces.json). Não se presumiu que snapshots, downloads remanescentes ou temporários de diagnóstico fossem descartáveis. |

O inventário de filesystem iniciou antes de qualquer exclusão e compreendeu
209.596 entradas acessíveis: 209.580 arquivos regulares, 14 links e duas FIFOs.
Foram calculados hashes dos arquivos regulares sem seguir links ou abrir FIFOs.
Isso demonstra observação de arquivos, **não cobertura semântica integral** da
aplicação ou dos artefatos de terceiros.

## scripts/: consumidores e achados

`automate.sh` é a entrada plan/run. Carrega common, catalog, inputs e runtime por
`source`. `catalog.sh` gera as tarefas com argumentos e dependências; não existe
catalog.json. O runtime injeta o preflight `integration.sh` nos harnesses HTTP e
Firefox. Rust/frontend despacham checks nos workspaces reais; a cobertura carrega
uma biblioteca separada, que chama o avaliador de métricas.

`scanner-gate.sh` chama `tools/quality/native_gates.py`, que faz snapshots e
encaminha argv a `toolbox.sh exec`. O wrapper descobre TOOLBOX_BIN/PATH; override
vazio/inválido é rejeitado. O módulo Python usa o parser shell de relatórios e o
filtro jq Trivy. Assim, wrapper, gate, parser e filtro cumprem funções distintas.
Nenhum deles foi eliminado por aparente sobreposição.

O consumo dinâmico foi inspecionado: `find` dos .sh na tarefa syntax; seleção por
raízes/escopos em inputs; descoberta unittest de test_*.py; padrões Vitest; membros
Cargo explícitos; diretório/anotações de regras Opengrep; suites referenciadas no
JSON CodeQL; imports por sys.path dos harnesses; configuração ESM Playwright e seu
servidor Node; políticas e runtimes selecionados por variáveis de ambiente. O mapa
vigente lista os caminhos e variáveis, sem registrar valores do ambiente.

Foram mantidos summarize, validate-exceptions, eslint-ratchet e métricas
budget/performance: interfaces explícitas e casos de contrato continuam presentes,
mesmo sem uso no catálogo padrão. Os quatro módulos de testes cobrem fronteiras
distintas. Fixtures Opengrep são consumidas por convenção, não apenas imports.
As suites CodeQL Rust e JS têm conteúdos/finalidades diferentes. A licença MIT de
scripts pertence ao componente importado; a GPL da raiz tem outra abrangência.

As migrações anteriores já haviam retirado automate.py, scripts/automation,
scripts/security, wrappers de stacks antigos e configs STIC/schema 2/lock de
imagens. Não havia outra cópia operacional desses caminhos para excluir. Há
snapshots históricos desses arquivos em runs/backups; eles foram preservados.
Os inventários datados não são novas instruções de execução.

O problema vigente encontrado foi documental: `erase.md` ainda orientava scanners
no PATH e o runner Python ausente. O guia permanente já contém o fluxo nativo,
pré-requisitos, bases, runtimes, política de downloads e limites. O mapa de scripts
também precisava distinguir o estado atual das decisões de migração. A tabela
Grype foi corrigida para a implementação observada: Syft produz o inventário e
Grype analisa o SBOM, sem repetir o scan direto descrito anteriormente.

## Exclusões e fontes mantidas

O [inventário por arquivo](inventory.json) foi escrito **antes** das exclusões;
cada entrada registra grupo, finalidade/motivo/evidências/consumidores/efeito,
fonte canônica, SHA-256, modo, tamanho, identidade e caminho da recuperação.

| Grupo | Arquivos retirados e motivo | Fonte/contrato mantido e consequências |
| --- | --- | --- |
| G01 — 17 marcadores | Cinco src do Agent; seis src do backend e tests de ec-server-app; cinco src frontend (access/configuration/infrastructure/projects/runs). Todos vazios e no mesmo diretório de código não vazio. Sua finalidade na ADR-002 era preservar diretórios sem código. | Fontes .rs/.ts/.vue e validation.rs listadas individualmente no inventário; diretórios e alvos continuam existentes. Os outros 29 marcadores preservam diretórios ainda vazios. Membros Cargo e descoberta por extensões não usam esses marcadores. |
| G02 — 14 caches | Quatro em scripts/tests; quatro em tools/governance, seus testes e tools/quality; um em sdd/tools; quatro nos harnesses de integração/E2E e um no driver Firefox. Total de 408.018 bytes derivados. | Cada .py correspondente existe e foi mantido. Imports continuam pelas fontes; caches podem ser recriados. inputs.sh exclui __pycache__, runtime desativa bytecode e .gitignore ignora esses artefatos. Não são logs ou resultados de execução. |
| G03 — guia temporário | erase.md, 5.627 bytes, retirado da raiz; spec e inventário já o tratavam como consulta temporária sem dependência operacional. | scripts/README, verification-tools e task-verification mantidos. Bytes íntegros em retired/erase.md e no backup privado; comandos antigos permanecem somente como histórico. Nenhum chamador/import/manifest/empacotamento ativo encontrado. |

São 413.645 bytes retirados das localizações anteriores. **Não é economia líquida
de disco**: os bytes permanecem nas cópias de recuperação e o guia também foi
arquivado como histórico.

Antes de cada unlink foram reobservados hash/inode/modo/mtime e o hash da cópia.
O diário tem registros de intenção e conclusão por exclusão, com data UTC,
caminho, motivo, evidência, ação, resultado e localização da recuperação.

## Preservações por contrato, história ou incerteza

- Dois rust-toolchain.toml byte a byte idênticos: workspaces independentes dependem da descoberta local; consolidar alteraria seu contrato.
- Cargo.toml/Cargo.lock/package.json/package-lock, dependências diretas/transitivas, toolchains, migrations, fixture YAML e todos os testes: consumidores reais ou contratos de build/teste; sem evidência suficiente para exclusão adicional.
- Módulos/domínios reservados, diretórios vazios e seus 29 marcadores: previstos pela ADR-002 e pelos READMEs; falta de implementação não significa desuso futuro.
- .codex/skills, .local/skill-installation/skills, bundle de fontes e perfil: instalação, baseline e originais têm funções distintas na ADR-001. Não sincronizados nem editados nesta tarefa.
- technical-documentation/ da raiz: pacote avulso de seis arquivos, com diferenças em SKILL/metadados e recursos exclusivos em relação à instalação. Origem/uso futuro insuficientemente demonstrados; mantido sem promover a cópia a fonte canônica.
- .local/usage.md, snapshots/builds/caches de dependências, downloads e preparações remanescentes: podem documentar ou sustentar diagnósticos/recuperação; finalidade de descarte não demonstrada. Não foi aplicada limpeza genérica por idade, extensão ou tamanho.
- .local/automation, .local/toolbox, native-migration, docs/validation, arquivos SQLite/JSONL/JAR e pesquisa TeamCity: histórico, auditoria ou evidência; inclusive falhas, estados superados, FIFOs remanescentes e arquivos vazios.
- Backups e cópias aparentemente duplicados dentro dos históricos: pertencem a execuções diferentes e não foram deduplicados.

## Referências e documentação ajustadas

As diferenças manuais estão em `.local/cleanup-20261109/documentation.diff`.
Foram editados exatamente os 13 caminhos abaixo, sempre com cópia anterior:

- `.gitignore`: área de recuperação privada explicitamente ignorada; não foi acrescentada exclusão à seleção dos scanners, que já exclui .local.
- `scripts/README.md`: ligação com o mapa/recuperação, escopo datado da autorização de migração e descrição real de Grype/Syft.
- `docs/development/scripts-inventory.md`: mapa atual de todos os grupos de scripts; texto anterior inteiro conservado em seção histórica.
- `docs/development/verification-tools.md` e `testing.md`: preparo vigente, guia retirado/arquivo histórico, substituição dos requisitos STIC e limites da inspeção.
- `docs/architecture/application-layout.md` e `application-overview.md`: estado observado e explicação dos marcadores retirados; árvores/descrições antigas identificadas como baseline e preservadas.
- `application/README.md` e `application/agents/README.md`: implementação nativa e alcance da retirada de marcadores/caches.
- `docs/README.md`: acesso ao inventário e a este relatório.
- `sdd/features/scripts-verification/{plan,tasks,converge}.md`: T034, ligação com esta observação e ausência de novo aceite operacional. Evidências JSON/traceability antigas não foram reescritas para atualizar hashes.

Nenhum import ou manifest exigiu ajuste: arquivos retirados não forneciam módulos
ou alvos ativos. Referências históricas aos caminhos originais foram mantidas como
observações datadas; o guia retirado é acessível pelo arquivo histórico.

## Inspeção, preservação de logs e limitações

A comparação integral dos arquivos preexistentes está em
[inspection.json](inspection.json). Reobservou SHA-256/link, tamanho, modo,
uid/gid e mtime; não executou nenhum código de produto, teste ou scanner.
A primeira classificação marcou duas entradas especiais como divergência de
bytes porque não eram arquivos regulares. A
[observação complementar](inspection-special-files.json) conserva esse resultado
inicial e demonstra que ambas são as mesmas FIFOs, com inode/device/metadados
iguais. Não foram abertas, drenadas ou apagadas.

O [fechamento](closure.json) registra também igualdade do guia arquivado com o
original, 122 registros no diário, cópias com hashes/modos/mtime/xattrs preservados
e que a única alteração não cache dentro de scripts/ foi seu README.

Resultado consolidado: **209.551 entradas inalteradas, 13 documentos/ignore
modificados e 32 arquivos removidos**, sem alteração regular inesperada e sem
erro de leitura ou de integridade dos backups. Isso inclui:

- **4.262 arquivos .log preexistentes preservados**, inclusive bytes vazios;
- 631 arquivos de docs/validation preexistentes e 818 da pesquisa histórica;
- 190.492 entradas de .local/automation e todo o restante de .local preexistente;
- 596 arquivos instalados em .codex e os 596 do baseline;
- os metadados Git observados (3.819 arquivos), sem alteração detectada.

O workspace já apresentava muitas mudanças frente ao índice Git, inclusive skills,
manifests, código, documentação e evidências. Um status somente de leitura foi
consultado; ele não é a base da preservação nem permite atribuir autoria. Os bytes
iniciais, as precondições e a comparação fora do conjunto autorizado demonstram
que essas mudanças não foram sobrescritas. Não foram criados commits.

L01–L05 têm evidências de operações e inspeção nos limites deste escopo. Eficácia
dos scanners, suíte, build, cobertura e critérios operacionais permanecem
**UNVERIFIED nesta tarefa**. O restaurador foi inspecionado, sem ensaio executado.

Não houve erro de acesso no inventário das entradas expostas pelo ambiente. Isso
não abrange dados escondidos por montagens/permissões nem autoriza alterar .git,
.codex, .agents ou .aws. Não foram consultados serviços externos, Docker/volumes,
bancos ativos, instalação central da Toolbox, consumidores fora do checkout,
registry ou estado operacional. Pacotes em node_modules/caches/targets tiveram
bytes inventariados, sem auditoria semântica ou decisão de descarte. Não foi feita
revisão linha a linha de todo o produto, pesquisa ou arquivos de terceiros.

Persistem dúvidas explícitas sobre a cópia avulsa da skill e a necessidade futura
de caches/preparações históricas. Esses itens foram preservados; não há exclusão
pendente sem cópia ou sem ajuste de referência conhecido dentro do escopo.

## Inventários, operações e recuperação

Área privada **excluída desta limpeza**: `.local/cleanup-20261109/`, dentro do
repositório e fora das raízes funcionais. Ela contém:

- `EXCLUIDA-DA-LIMPEZA.txt` e `README.md`: identificação e instruções;
- `baseline.jsonl` e `baseline-summary.json`: estado inicial por caminho/hash/link/metadados;
- `duplicates.json`: grupos byte a byte, sem decisão automática de exclusão;
- `inventory.json`: candidatos e documentos editados, fontes canônicas e metadados completos, incluindo xattrs e timestamps;
- `previous/<caminho-original>`: bytes de todos os 32 arquivos retirados e dos 13 editados, com modos/timestamps e xattrs copiados;
- `operations.jsonl`: diário incremental privado; [cópia da sessão](operations.jsonl) neste registro;
- `documentation.diff`, `restore.py` e utilitários de manipulação dos inventários.
- `after-edits.json`: hashes anteriores/posteriores dos 13 arquivos editados, para orientar comparação antes de qualquer merge de recuperação.

Os arquivos públicos deste diretório documentam a análise e não são uma nova suíte.
O inventário inicial privado inclui somente hashes/metadados, sem transcrever
conteúdos de logs, credenciais ou variáveis do ambiente. Não depender apenas do Git
para recuperar as exclusões.

Da raiz do Ec, para restaurar **somente um arquivo removido**:

```bash
python3 .local/cleanup-20261109/restore.py 'erase.md'
```

Para restaurar todos os removidos ainda ausentes:

```bash
python3 .local/cleanup-20261109/restore.py --all
```

O restaurador admite somente caminhos removidos inventariados, confere o hash da
cópia, recusa symlinks nos pais e cria com O_EXCL. Arquivos já existentes são
preservados, mesmo se diferentes; não há overwrite. Repõe modo, timestamps e
xattrs. Se um pai deixou de existir ou os owners/permissões mudaram, interrompe;
reconstrua somente o diretório apropriado após inspecioná-lo, ou copie o backup
para uma área separada de revisão. Não remover um arquivo novo para forçar restore.

Os documentos editados não são revertidos por `--all`. Para revisar uma versão
anterior, sem tocar no documento vigente:

```bash
mkdir .local/cleanup-20261109/review
cp -p --no-clobber .local/cleanup-20261109/previous/scripts/README.md .local/cleanup-20261109/review/scripts-README-before.md
diff -u .local/cleanup-20261109/review/scripts-README-before.md scripts/README.md
```

Examine a diferença e integre manualmente apenas os trechos desejados. Repita
com o caminho correspondente em previous; nunca copie a árvore inteira sobre o
checkout. Restaurar erase.md recupera um documento antigo, não torna válidos seus
comandos/caminhos retirados. Preserve esta área de recuperação e os registros
históricos mesmo depois de uma restauração.
