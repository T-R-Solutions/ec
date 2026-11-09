# Migração STIC e limpeza de ferramentas — 08/11/2026

A estrutura do consumidor foi migrada e os downloads de ferramentas encontrados
no Ec foram excluídos por pedido explícito. A integração operacional **não está
homologada**: capacidades públicas ainda faltam na Toolbox, e a suíte não foi
executada. Nenhum arquivo da plataforma externa foi alterado.

## Fontes, decisão e método

Contrato consultado: `/home/rodrigo/Projetos/dev_audit/shared-toolbox/docs/doc.md`,
com `docs/consumer-migration-20261108.md`, template comum e implementação pública
`toolbox/consumer.py`. O caminho inicial `dev_audit/docs/docs.md` não existia; o
usuário confirmou o caminho efetivo no pedido seguinte.

Foram lidos autoridades/estado/arquitetura/workflow/política de verificação do Ec,
ponte e skill software-delivery com build-inputs-and-trust. Rota completa: alteração
de contrato e retirada de instalações. [Migração](migration.json) registra hash da
skill, versão da ponte 1 no plano e hash/origem do wrapper. D-19/R-19/T025–T027 e
AC-19 estão na feature scripts-verification. Sem delegação a outros agentes.

Inspeções: leitura de arquivos, comparação de conteúdo/diffs, inventário de paths,
tamanhos e SHA256, leitura de configurações, enumeração de payloads de imagens e
busca de referências antigas. Não executados: automate.sh, scripts avulsos,
validadores, testes, scanners, Docker, preparo ou downloads. A revisão não substitui
verificação de sintaxe/executabilidade nem validação real da CLI.

## Implementação

- Wrapper 1.0.0 idêntico ao template: SHA256
  `e90c7ea418e6f9fbe71157c9c5157945ffe205f77ba33572bbc89ed8890b946e`.
- Configuração em `scripts/config/`, root `../..`, versões/seleção explícitas,
  configs nativas e regras relocadas; lista ShellCheck acompanha os scripts atuais.
- Playwright ajustado para localização nova, cwd por URL decodificada e saídas em
  `/out`; continua fixture com API simulada, não jornada integrada do produto.
- Lock movido sem mudar bytes: SHA256
  `32b03422396d2c1dc6447a31789ee28229299ca06ce62fe77a67c86ede205b3f`.
  Isso preserva identidade; não resolve incompatibilidades ou frescor.
- Tarefas do produto em `scripts/stacks/`; supervisão, cobertura e oráculos
  de relatórios preservados; fixtures Opengrep e requisitos dos testes reais de
  regras permanecem. O executor Gitleaks antigo foi retirado com seu backend.
- Removidos bridge interno Python, `toolbox-local.sh`, biblioteca de execução de
  scanners duplicada, backends nativos e downloaders/configs de preparo antigos.
- Supervisor encaminha `TOOLBOX_BIN` (inclusive valor vazio explicitamente definido)
  e `TOOLBOX_STATE`; não acrescenta `.local/tools/bin` ao PATH.
- Tests de componentes removidos foram retirados junto de seus backends; parsers,
  regras e critérios correspondentes permanecem. Acrescentados casos de wrapper,
  argv/exit, config, bloqueios e propagação de ambiente. Nenhum teste foi executado.

[Mapa de movimentos/exclusões](migration.json), [inventário anterior](before.json)
e [inventário atual](after.json) permitem revisar a mudança sem depender de Git.
Baseline de código/configs para revisão local: `/tmp/ec-stic-before-20261108`.
Não foi criada cópia dos binários ou bases removidos nesse baseline.

## Exclusões autorizadas

**38.008 arquivos regulares, 2.720.319.239 bytes de conteúdo (2,72 GB decimais)**.
Esse total soma tamanhos lógicos; não mede blocos efetivamente liberados nem
considera deduplicação do filesystem. Symlinks de bundles também constam no recibo.

| Grupo | Conteúdo excluído |
| --- | --- |
| Plugins Cargo | cargo-deny/cargo-machete instalados e cópias/pacotes de remediação |
| CodeQL | Bundle e pacote tar.gz; metadados/recibos anteriores preservados |
| RustSec | Clone local e advisory-dbs do cache Cargo usado para checks |
| OSV | Bases crates.io/npm, incluindo cópia de compatibilidade osv-scalibr |
| Payloads de imagens locais | Gitleaks, Opengrep, duas cópias OSV, Node/Playwright, Ruff e duas cópias ShellCheck |

[Resumo de limpeza](cleanup.json) e [recibo por arquivo/hash](deleted-downloads.jsonl).
A exclusão ocorreu com o lock do supervisor adquirido, sem iniciar verificação.
Busca inicial por nomes foi complementada pela inspeção dos contextos de imagens,
onde binários tinham nomes genéricos `tool`/`tool.tar`.

Preservados: fontes/lockfiles/dependências npm e Cargo da aplicação, targets de
build, skills e baseline de recuperação, pesquisa TeamCity, relatórios, snapshots
de fontes e evidências históricas. Recibos/Dockerfiles de imagens locais não são
instalações utilizáveis após remoção de payloads. Instalações globais, imagens do
daemon e armazenamento da Toolbox externa não pertencem ao escopo de exclusão
interna do Ec. Não foram apagados.

A recomendação do contrato de reter instalações até homologação foi superada pelo
pedido explícito de excluir os downloads. Não há restauração local desses bytes:
recibos identificam o que saiu, mas não são backup. Reposição depende de aquisição
externa autorizada. Índices remanescentes apontam a observações históricas, sem
nova execução pela ponte removida.

## Bloqueios e retomada

| Dependência | Consequência e retomada |
| --- | --- |
| H03/H05/H06/H07 externos | Sem suppression-policy-v2, layout-v2, export e staging-manifest-v1. A negociação recusa; implementar plataforma e integrar gates públicos antes de homologar |
| Trivy | Gate obrigatório exige vuln/misconfig/secret; adapter atual somente vuln. Demonstrar equivalência antes de ativar |
| CodeQL/YARA-X | Sem adapters disponíveis; manter bloqueados e implementar suporte efetivo |
| Locks/bases | Repreparar/revalidar adapters e imagens, atualizar/adotar bases externamente com autorização de downloads; não inventar hashes |
| Plugins Cargo/RustSec | Disponibilizar externamente no PATH/EC_CARGO_BIN e EC_RUSTSEC_DB; instalações locais foram excluídas |
| Suíte | Executar somente após pedido de commit e escolha prevista na política; nenhuma aprovação nesta entrega |

O gate do Ec retorna bloqueio 3 para scanners e mantém os critérios no catálogo;
não os executa em árvore sem staging nem interpreta SBOM como segurança aprovada.
A futura disponibilidade das capacidades não ativa o gate automaticamente: T027
inclui integração das APIs públicas, preservação de política e homologação real.
As tarefas locais do produto continuam separadas, com resultados schema 1, sem
fingir layout 2 da plataforma. AC-01–AC-19 permanecem UNVERIFIED nos inputs atuais.

Evidências/planos anteriores foram preservados em previous-* neste diretório;
seus hashes/observações não foram atualizados para aparentar validade nova.
