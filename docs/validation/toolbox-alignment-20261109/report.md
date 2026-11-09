# Adequação do consumidor Ec à Shared Toolbox — 09/11/2026

O Ec foi ajustado ao fluxo principal `exec` descrito no README e nos guias
consolidados da instalação em
`/home/rodrigo/Projetos/dev_audit/shared-toolbox/`. As mudanças corrigem ambiente
descartado pelo supervisor, validação de frescor baseada em preparo e ausência
de observação do Checks Bundle externo Trivy. Não foram alterados arquivos,
volumes, imagens, versões ativas ou serviços da plataforma externa.

## Fontes e conclusão da análise

O README central encaminha consumidores para `docs/consumer-integration.md`;
operadores usam `docs/application.md`. `docs/native-validation.md` delimita os
ensaios reais. Os guias antigos native-execution/operations são encaminhamentos,
e o catálogo em tools/NOME descreve módulos independentes da plataforma.

Foram inspecionados os três documentos principais, o template nativo, o módulo
Trivy, o guia OSV 2.7.0, as formas de emissão dos diagnósticos e os consumidores
Ec: wrapper, catálogo/supervisor, seleção de fontes, gates, configurações nativas,
testes de contrato e documentação. Os hashes das fontes principais estão em
[sources.json](sources.json). A leitura do código central serviu para conferir
campos emitidos, sem importar ou copiar sua implementação para o Ec.

O [plano](plan.md) registra escopo, decisões, riscos e tarefas. A skill primária
software-delivery foi lida/aplicada para entradas, identidade dos dados, integração
e limites, pelo caminho e SHA-256 ali registrados.

| Tema | Contrato central observado | Situação/ação no Ec |
| --- | --- | --- |
| Interface e descoberta | exec, TOOLBOX_BIN/PATH, argv nativo depois de --; sem JSON/lock de consumidor | Já adotado. Mantido o wrapper e seu guard adicional de rejeição de override vazio, explicitamente documentado. TOOLBOX_ROOT é só conveniência para exportar TOOLBOX_BIN. |
| Ambiente | Endpoint/contexto Docker e estado são seleções explícitas; sem ambiente integral no container | runtime.sh descartava DOCKER_HOST/CONTEXT/CONFIG e TOOLBOX_STATE antes de native_gates. Corrigidas ambas as camadas; TLS/cert path também selecionados explicitamente. |
| Estado/armazenamento | TOOLBOX_STATE muda índices/locks, não tools/NOME/volume | Seleção agora conservada; guias distinguem estado, cache, dados e volumes. Nenhum volume foi deslocado ao Ec. |
| Idade de base | database_metadata Trivy/OSV informa fonte upstream mais antiga; prepared_at não mede idade dos dados | Retirado Gate.database_record e o requisito EC_OSV_SCANNER_DATABASE_RECORD. O gate usa snapshot/fontes/hashes/semântica/data_timestamp do diagnóstico. |
| Trivy | DB/Java DB e Checks Bundle externo têm preparos separados; bundle observado em /cache/policy ro | Nova validação de checks/checks_runtime, origem, identidade, versão/imagem, mount bind ro e rede none. Troca entre scans reprova o gate. |
| Paths e escrita | /project ro, /out rw selecionado, /cache rw, /db e /data ro; paths do host não convertidos | Modelo já compatível; documentados mounts Trivy e os extras explícitos CodeQL/Grype. Config Trivy conserva skip-check-update. |
| Relatórios e códigos | Streams nativos, diagnóstico separado, arquivos novos, código nativo e cleanup | Modelo conservado. Exemplo Ruff passou a criar run_dir novo para impedir sobrescrita/reutilização. Não adicionado envelope de stdout ou export obrigatório. |
| Playwright | ESM importa index.mjs central; serviços/configs do consumidor e ABI compatível | Config e testes Ec já usam import absoluto central, sem runner duplicado; mantidos API simulada e Firefox separado. |
| CodeQL | Bancos de análise do consumidor em /out ou mount rw; não na base central | /ec-codeql por tarefa já é compatível. Rust continua dependente de runtime explicitamente preparado; sem alegação de homologação baseada no ensaio JS da plataforma. |
| Compatibilidade | run/consumer/schema 1 continuam legados; schema 2/layout/export não implementados | Não reintroduzidos configs STIC, locks ou adapters. A configuração nativa permanece em scripts/config/native. |

O limite de 24h e a reprovação de achados continuam sendo política Ec. Não são
imposições novas à plataforma. Grype mantém idade nativa descriptor.db.built;
Trivy conserva também UpdatedAt/NextUpdate e controles positivos/negativos das
três categorias. A nova validação do bundle não cria prazo de validade adicional
baseado em DownloadedAt; exige timestamp válido não futuro e identidade observada.

## Alterações concretas

- `scripts/stacks/ec/runtime.sh`: transporta TOOLBOX_STATE, seleções Docker,
  TLS/certificados e SELinux até a CLI; deixa de encaminhar o recibo externo OSV.
- `tools/quality/native_gates.py`: valida database_metadata OSV/Trivy, fontes
  obrigatórias distintas, hashes, gerações/caminhos de metadados e a menor data
  upstream; conserva somente campos públicos conhecidos no resumo da base.
  Observa Checks Bundle externo, mount/rede e estabilidade; registra o hash do
  bundle nas etapas. Remove a leitura do recibo externo por prepared_at.
- `scripts/config/native/trivy/trivy.yaml`: esclarece que o bundle externo é
  fornecido pela plataforma, mantendo a configuração offline existente.
- `scripts/tests/test_native.py` e `test_shell.py`: fixtures atualizadas e casos
  de regressão para perda de ambiente, proveniência ausente/parcial/divergente,
  timestamp futuro/vencido/agregado incorreto, semântica/hash/geração inválidos,
  preparo recente incapaz de renovar dado velho, bundle ausente/gravável,
  rede diferente, imagem divergente e troca entre etapas. Casos apenas escritos.
- `scripts/README.md`, `docs/development/verification-tools.md`,
  `scripts-inventory.md`, `task-verification.md`, README e índice docs:
  referências principais, preparo, descoberta, mounts, ambiente e evidências.
- `sdd/project.md` e a feature scripts-verification: REQ-27/AC-27,
  D-21/D-22, R-21/R-22 e T035–T037, com rastreabilidade e critério UNVERIFIED.
  As entradas de evidência anteriores e seus hashes permanecem históricos.
- `.gitignore`: exclui apenas a área privada de recuperação desta tarefa.

O [manifesto de alterações](changes.json) identifica os 19 arquivos observados,
hash anterior/posterior e recuperação. A cópia anterior completa está em
`.local/toolbox-alignment-20261109/previous/`, respeitando os caminhos originais.
Mudanças preexistentes foram o ponto de partida, sem reset ou substituição pela
versão Git. Os logs/runs/relatórios antigos não foram reescritos.

## Configuração de acesso neste workspace

No shell que for executar uma tarefa posteriormente autorizada, a partir da raiz
do Ec, selecione a instalação consultada:

```bash
export TOOLBOX_ROOT="$(cd ../dev_audit/shared-toolbox && pwd -P)"
export TOOLBOX_BIN="$TOOLBOX_ROOT/bin/toolbox"
```

O wrapper conserva a alternativa toolbox no PATH. Não precisa apontar diretamente
para cada tools/NOME, nem instalar scanners ou adicionar JSON/locks ao Ec.
Não foi gravado um caminho pessoal como default do wrapper.

O guia central sugere doctor/central list/central status para observação pelo
operador; essas consultas não foram executadas nesta tarefa. Não é necessário
definir TOOLBOX_STATE para a configuração padrão. Se o operador o definir, a
variável será conservada, sem deslocar volumes. Docker deve enxergar os mesmos
caminhos do host usados nos mounts. SELinux shared permanece seleção explícita,
com efeitos de relabel a revisar previamente no guia central.

## Inspeção e limites

Foi feita revisão manual das diferenças, dos consumidores por source/chamadas,
dos caminhos configurados e dos campos emitidos pelo executor central. As
consultas foram leituras de arquivos, buscas e manipulação local de inventários;
nenhum script de verificação, teste, scanner, doctor ou comando Docker/Toolbox foi
executado. Não houve downloads, commits, publicação, ativação ou manutenção.

As regressões escritas não foram executadas, por
`docs/development/task-verification.md`. AC-27 e a eficácia da integração nos
inputs novos permanecem **UNVERIFIED**. A revisão não comprova disponibilidade
de imagens/bases atuais, compatibilidade CodeQL Rust, permissões/SELinux do
processo real, nem comportamento de Docker remoto. O inventário central de 12
imagens descrito no README é evidência datada da plataforma, não observação desta
execução Ec. Dados upstream com mais de 24h continuarão reprovados pelo Ec, mesmo
se a cópia/preparação for recente.

Os diagnósticos originais de cada chamada continuam sendo a evidência completa;
o resumo Ec seleciona campos conhecidos. A comparação de hashes das fontes e das
cópias no [fechamento](inspection.json) é inspeção de arquivos, não teste de
produto ou execução dos gates. A suíte completa só pode ser acionada no fluxo
de autorização previsto pelo projeto.

## Recuperação sem sobrescrever trabalho posterior

Conservar `.local/toolbox-alignment-20261109/`, explicitamente ignorada pelo Git.
`before.json` contém hashes/modos e caminhos de backup; `changes.diff` permite
comparar versões anteriores com o conjunto desta sessão. Para recuperar um
trecho, primeiro copie o arquivo anterior para uma pasta nova de revisão:

```bash
mkdir .local/toolbox-alignment-20261109/review
cp -p --no-clobber .local/toolbox-alignment-20261109/previous/tools/quality/native_gates.py .local/toolbox-alignment-20261109/review/native_gates.before.py
diff -u .local/toolbox-alignment-20261109/review/native_gates.before.py tools/quality/native_gates.py
```

Faça merge manual dos trechos necessários; não copie previous em massa sobre o
checkout. Se o hash atual divergir do hash posterior em changes.json, preservar
também essa versão antes de qualquer recuperação. Repor o recibo OSV antigo não
o torna compatível com o contrato atual e prepared_at não comprova frescor dos
dados. As cópias são para recuperação/revisão, não uma aprovação da implementação
anterior ou da suíte atual.
