> Histórico: as propostas deste plano foram substituídas pelo pedido de integração
> nativa em 08/11/2026. Consulte spec.md REQ-20–26, plan.md D-20 e tasks.md T028–33.
> Não executar preparo/importação/adapter/locks deste plano como fluxo atual.

> Atualização de 08/11/2026: a migração STIC e a exclusão de downloads locais
> substituem a ponte/preparo/armazenamento descritos anteriormente. Estado atual:
> wrapper comum implantado, gates de scanners bloqueados, nenhuma execução de suíte.
> Consulte `scripts/README.md` e `docs/validation/scripts-stic-20261108/report.md`
> (caminhos a partir da raiz Ec). O texto anterior registra decisões históricas.

# Plano de conclusão dos scanners no Ec

Data: 2026-11-07. Estado: **plano com T019 parcialmente implementada durante
a preparação de commits; demais etapas pendentes, sem aceite completo**.
Escopo autorizado: somente arquivos e estado local da solução
`/home/rodrigo/Projetos/ec`. O projeto `dev_audit/shared-toolbox` será apenas lido.

## Objetivo e limite de atuação

Concluir regras/configuração YARA-X, identidades reais de imagens/bases, inclusão
dos novos checks na suíte padrão e convergência Trivy sem perder as análises atuais.
Este documento detalha a evolução de REQ-12/AC-12 e D-14/R-14. A atualização documental registra REQ-13 a REQ-18 / AC-13 a AC-18 em `spec.md`.
AC-12 preserva a integração intermediária com checks adicionais; AC-16 define
sua promoção ao padrão. T019–T024 estão vinculadas em `tasks.md` e
`traceability.json`, com aceite UNVERIFIED em `evidence.json`.

Não editar, instalar, atualizar, limpar ou gerar arquivos no projeto Toolbox.
Não alterar configurações globais, receitas compartilhadas, outros consumidores
ou o host. Downloads não estão autorizados por este planejamento. Uso futuro do
Docker implica seu armazenamento normal de imagens/containers; não equivale a
permissão para modificar o projeto Toolbox ou outros recursos Docker.

Os artefatos gravados em filesystem, inclusive caches, locks de processo, snapshots,
logs, receitas locais e evidências, devem permanecer dentro do Ec. Não criar links
que redirecionem escrita para o projeto compartilhado. Não montar o socket Docker
dentro de imagens de análise.

## Situação observada

| Frente | Estado atual no Ec | Lacuna concreta |
| --- | --- | --- |
| YARA-X | Entrada 1.0.0 sem regras/config; wrapper bloqueia incondicionalmente | Regras úteis, execução local, classificação e imagem verificável |
| Imagens/bases | Lock existente contém seis ferramentas; Trivy/Grype/Bandit/Syft não têm registros próprios nele | Preparo real, identificação de bytes e adoção consistente |
| Suíte | Novos checks são criados apenas quando pedidos por `--check` | Inclusão padrão, dependências e preservação de seleção/deduplicação |
| Trivy | Gate nativo 0.61.1 completo e Docker 0.76.2 adicional somente vuln | Um contrato padrão com vuln/misconfig/secret, sem redução de cobertura |
| Escritas da integração | CLI Toolbox usa volumes dentro do projeto compartilhado | Redirecionar estado de toda integração afetada para o Ec |

`Store(root, tools_root)` já permite estado e volumes com raízes distintas.
`--state` da CLI muda apenas índices/locks gerais: sozinho não resolve o limite
de escrita. O preparo lê receitas e plataforma compartilhadas; `lock-base` escreve
na plataforma compartilhada e não será chamado. Essa conclusão vem da inspeção
de `toolbox/storage.py`, `cli.py` e `prepare.py`, sem executar seus comandos.

## Decisões propostas

**D-15 / R-15 — Estado local com reutilização da Toolbox.** Criar uma ponte fina do
consumidor Ec para a biblioteca existente, passando índices em
`.local/toolbox/state` e ferramentas/volumes em `.local/toolbox/tools`. Manter a
entrada shell e o runner únicos. Se for necessária uma ponte Python, colocá-la
sob `tools/`, sem reintroduzir Python operacional em `scripts/`. Usar
`PYTHONDONTWRITEBYTECODE=1`, não fazer monkeypatch e registrar o hash da biblioteca
efetivamente consumida. Validar a compatibilidade dessa API interna; mudança ou
escrita fora do Ec deve bloquear, sem fallback à CLI com armazenamento externo.
Não copiar toda a implementação da Toolbox para o Ec.

**D-16 / R-16 — YARA-X do consumidor.** Manter regras e adapter de integração
específicos do Ec neste repositório. A ausência de adapter compartilhado não será
tratada como incompatibilidade automática do executável `yr`. Reutilizar imagem
existente somente após comprovar sua versão e execução. Se não existir, prever
uma receita Docker local ao Ec com release oficial fixada e origem/integridade
verificadas; isso não altera o catálogo compartilhado. Não fabricar no lock Toolbox
metadados de um adapter que ele não possui: registrar a integração local em um
manifesto Ec separado e vinculá-lo aos resultados. Falta de artefato confiável
mantém a execução bloqueada.

**D-17 / R-17 — Um gate Trivy completo.** Alvo final: imagem 0.76.2 já prevista no
Ec, invocada por adapter shell do consumidor com vuln/misconfig/secret. O adapter
compartilhado fixa `--scanners vuln`: mudar apenas YAML não o amplia. A invocação
local precisa preservar bloqueio de rede, integridade/frescor da base, inventário
dos três locks, checks locais/embutidos e redação de segredos antes da persistência.
A disponibilidade de flags/checks na versão exata será demonstrada, não inferida
da documentação latest. Até a paridade observada, o gate nativo permanece ativo.

**D-18 / R-18 — Padrão sem duplicação e resultados honestos.** Bandit, Grype, Syft,
YARA-X e testes das regras YARA passam ao catálogo padrão. Trivy completo mantém
o nome `trivy`; `trivy-toolbox` torna-se alias compatível, sem executar duas vezes
a mesma análise. Syft confirma geração/completude do inventário e mantém a marca
de medição nos resultados agregados; não gera parecer de vulnerabilidades.

## Etapas e dependências

| ID | Depende de | Trabalho previsto e arquivos | Condição de conclusão |
| --- | --- | --- | --- |
| T019 | — | Atualizar contratos da feature, mapear todas as escritas da biblioteca e preparar ponte local em `tools/`, `scripts/lib/toolbox.sh` e `scripts/prepare.sh` | Preparo/run/erro/cancelamento usam somente caminhos Ec; fronteira externa protegida por teste |
| T020 | T019 | Registrar versões/receitas, preparar ou reutilizar imagens e snapshots via biblioteca existente; atualizar `toolbox.images.lock.json` e seleção em `toolbox.json` | IDs reais, versão e arquitetura conferidas, hashes íntegros; entradas antigas preservadas |
| T021 | T019 | Definir política, regras e fixtures YARA em `scripts/security/rules/yara-x/`; configuração em `config/quality/`; adapter shell local e receita Ec se necessária | Regras relevantes com fontes/licença, positivos/negativos, saída/exit e inventário verificados na versão fixada |
| T022 | T020 | Unificar Trivy em `scripts/security/check.sh`, configs, parser e wrapper; atualizar contrato de migração | Docker cobre os três tipos e os três locks; ausência/erro não aciona fallback silencioso |
| T023 | T020, T021, T022 | Integrar padrão e dependências em `scripts/lib/catalog.sh`, parser/ajuda de `automate.sh`, runtime e relatórios | Cada check aparece uma vez no padrão; regras precedem scan; seleção explícita continua coerente |
| T024 | T023 | Preparar regressões, atualizar guias/traceabilidade/evidências e executar verificação quando autorizada pela política Ec | Suíte completa observada; novos checks efetivamente executados; pendências explicitadas |

T019 foi iniciada para corrigir a fronteira durante a execução autorizada da suíte;
T020–T024 permanecem pendentes. O aceite de T019 ainda não foi demonstrado. Imagens/bases são pré-requisitos
da execução, não da autoria dos testes ou do ajuste documental. T020 e T021 podem
avançar independentemente depois da fronteira de escrita estabelecida por T019.

### Regras YARA-X

Separar regras de produção das fixtures para que testes positivos não contaminem
o scan do projeto. Definir alvo útil: fontes ativas e artefatos locais realmente
existentes, com inventário explícito; não declarar cobertura de binários ausentes.
Selecionar um conjunto pequeno e justificável de indicadores de conteúdo
indesejado, documentando o significado de cada match e suas limitações. Não aceitar
uma regra que apenas detecta uma sentinela de teste como cobertura de produção.

Começar com regras-fonte locais. Compilados são opcionais e somente serão usados
com vínculo à versão do compilador/runtime e hash das fontes. Não baixar regras
de registry no scan. Regras inválidas, includes externos, timeout, arquivos não
lidos, conjunto vazio e execução parcial precisam produzir erro/bloqueio.
O nome da regra e caminho bastam no relatório; não persistir conteúdo sensível
correspondente ao match. Confirmar formato e exits na versão exata antes de fixar
o parser; sucesso de processo sozinho não significa ausência de matches.

### Imagens, bases e locks

Reutilizar registros confiáveis e imagens locais quando disponíveis. Importar
snapshots externos somente por leitura para uma cópia local e conferir hashes e
frescor; não renovar sua data de preparo. Se faltarem artefatos, listar downloads
e tamanhos conhecidos para autorização específica antes de obtê-los.

Trivy exige DB principal e Java DB conforme o alvo; checks de misconfiguração
devem ter origem explícita na imagem ou em snapshot local. OSV mantém crates.io/npm;
Grype mantém DB nativa. Bandit/Syft/YARA não recebem base de vulnerabilidades fictícia.
Registrar separadamente ID local da imagem e digest de distribuição quando
disponível; não tratá-los como a mesma identidade.

Adotar lock e snapshot em operação protegida pelo lock de workspace do Ec, com
gravação atômica e metadados antes/depois. Se o preparo falhar, não publicar
seleção parcial como ambiente completo; preservar a configuração anterior válida.
Idade máxima, hash e assinatura/procedência são verificações distintas.

### Migração Trivy

Preparar comparações controladas entre o gate atual e o candidato Docker: pacote
vulnerável conhecido, segredo sintético e configuração insegura reconhecida, além
de negativos. Não exigir igualdade textual dos achados de versões diferentes;
investigar divergências e comprovar que nenhuma categoria foi perdida.

Separar requisito de inventário de vulnerabilidades da observação de
misconfig/secret no parser, mantendo ambos obrigatórios no gate completo.
Se checks embutidos não estiverem disponíveis, preparar bundle local de checks
explicitamente, com origem/hash, antes do scan offline. Não remover flag antiga
incompatível nem categoria somente para fazer o candidato passar.

Após paridade e suíte observadas, retirar a dependência operacional do executável
0.61.1 e de sua DB nativa do padrão. Preservar evidências/backup e documentar
recuperação explícita para o gate anterior; sem fallback automático ou dupla
contagem de achados. CodeQL e demais controles existentes permanecem no padrão.

## Critérios de aceite propostos

| Critério | Evidência necessária | Estado atual |
| --- | --- | --- |
| C-01 / AC-13: limite Ec | Testes de escritas com projeto externo readonly, incluindo caches, falha, cancelamento e SELinux; inspeção das raízes dos artefatos | UNVERIFIED |
| C-02 / AC-14: YARA operacional | Regras úteis versionadas; positivos/negativos por regra; compilação inválida/timeout/relatório parcial rejeitados; scan real do alvo Ec | UNVERIFIED |
| C-03 / AC-15: identidades reais | Inspeção de imagens executadas, inventário/hash de bases e lock corrente; corrupção, versão/arquitetura divergente e expiração rejeitadas | UNVERIFIED |
| C-04 / AC-16: padrão completo | Plano padrão contém cada check uma vez; execução real preserva dependências, exits e medição Syft no agregado | UNVERIFIED |
| C-05 / AC-17: Trivy preservado | Fixtures observadas das três categorias, três lockfiles inventariados, segredo redigido antes de gravar, scans offline e ausência de bypass | UNVERIFIED |
| C-06 / AC-18: verificação final | Run completo novo, sem filtros, com ferramentas reais e sem bloqueios/erros; evidências atuais da feature | UNVERIFIED |

Não executar testes, validadores ou scanners nesta tarefa de planejamento.
A execução posterior segue `docs/development/task-verification.md`: após pedido
explícito de commit e escolha `sim`, usar `bash scripts/automate.sh run` sem filtros.
Downloads continuam exigindo autorização específica. Critérios não observados
não serão promovidos a APPROVED. Pendências anteriores, como licenças próprias,
podem impedir C-06 e devem ser relatadas separadamente sem enfraquecer a suíte.

## Fontes e lacunas da pesquisa

Consultadas em 2026-11-07, somente fontes oficiais para decisões técnicas:

- [YARA-X CLI](https://virustotal.github.io/yara-x/docs/cli/commands/): regras-fonte,
  scan e compile; documentação corrente não comprova todas as flags da versão 1.0.0.
- [Configuração YARA-X](https://virustotal.github.io/yara-x/docs/cli/config-file/):
  referência para evitar configuração pessoal implícita; confirmar na versão alvo.
- [Release YARA-X 1.0.0](https://github.com/VirusTotal/yara-x/releases/tag/v1.0.0):
  ponto de partida para artefatos; a página não forneceu nesta pesquisa evidência
  suficiente dos assets/checksums. Não há hash nem receita de instalação aprovados.
- [Trivy misconfiguration](https://trivy.dev/docs/latest/scanner/misconfiguration/)
  e [secret](https://trivy.dev/docs/latest/scanner/secret/): categorias disponíveis
  no produto; confirmação por versão e execução permanece pendente.
- [Docker run](https://docs.docker.com/engine/containers/run/): imagem por identidade,
  mounts e substituição de argumentos/entrypoint sem reconstruir a Toolbox.

Tentativas de leitura dos fontes versionados YARA-X 1.0.0/Trivy 0.76.2 e de páginas
Trivy 0.61 não retornaram conteúdo utilizável. Não inferir compatibilidade a partir
dessas tentativas; a auditoria por versão faz parte de T021/T022. Fontes locais
efetivamente lidas: manifests, Dockerfiles, adapters, Store, preparo e CLI da Toolbox,
além dos wrappers/configs/catalog/runtime do Ec. Nenhum scanner, teste ou script de verificação foi executado.

Skill primária: `software-delivery`, ponte versão 1, caminho
`.codex/skills/software-delivery/SKILL.md`, SHA256
`b0166f50c4532b55cae5ac7e4cde24c35767d56e7db142ea7001402fdd2b94a0`.
Contribuição: separar preparo e execução, vincular identidades reais e condicionar
migração à preservação da cobertura. Sem delegação ou alterações fora do Ec.
