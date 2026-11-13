# Revisão de coesão — skills, SDD e AGENTS

Data: 11/11/2026. Pedido: analisar e remover duplicações e redundâncias, tornando
as configurações coesas. Escopo: catálogo local Ec; não inclui as skills globais
do ambiente nem os pacotes de plugins externos.

## Resultado

As regras passam a ter responsáveis explícitos no
[mapa de autoridades](../../constitution.md#responsabilidade-das-configurações).
AGENTS encaminha às fontes canônicas; SDD define execução e evidência; skills
mantêm seus métodos; pontes conservam localização e contribuições específicas.

O conjunto de 108 arquivos de skills/referências e 67 pontes passou de 11.892
para 7.931 linhas: redução de 3.961 linhas. As entradas centrais e templates
também foram consolidados. A seleção permanece em 67 skills, sem renomear
pacotes, alterar gatilhos de descoberta ou fundir especialidades.

## Cobertura e método

- Inventário dos 599 arquivos instalados: 67 entradas, 67 metadados de interface
  e 465 referências Markdown; inventário dos 104 arquivos SDD existentes e do
  AGENTS.md raiz.
- Comparação das políticas repetidas, escopos, contribuições das pontes,
  referências operacionais, templates, formatos de evidência e consumidores do
  catálogo. Leitura das autoridades, ADR-001, workflow, políticas de execução e
  retenção, código do auditor/instalador/validador e fixtures pertinentes.
- Busca transversal de duplicações textuais e instruções conflitantes; revisão
  dos trechos atingidos e precondições de conteúdo antes de aplicar candidatos.
  As buscas e comparações de hashes são operações de inspeção/edição, não execução
  dos verificadores do repositório.
- Métodos aplicados e hashes lidos registrados no
  [plano da feature](../../../sdd/features/governance-adoption/plan.md).
  As 67 skills foram objetos da revisão; isso não significa executar suas
  especialidades ou ensaios.
- Fontes técnicas importadas e registros bibliográficos foram preservados.
  Não houve releitura independente de todos os livros, padrões e URLs, nem
  comprovação de eficácia das recomendações técnicas.

## Achados e decisões

| Achado | Correção e responsável |
| --- | --- |
| Política de scripts copiada nas 67 skills e em entradas, templates e pontes SDD | Regra integral apenas em task-verification.md; pontos de uso encaminham à política |
| Autorização de correções existente no AGENTS ausente/conflitante na política central | Alcance da escolha positiva incorporado à fonte canônica, com exceção de downloads e respeito às permissões reais |
| Exceção da migração de 08/11 tratada como pedido atual | Identificada como autorização histórica limitada àquela tarefa |
| Workflow, estados e instruções de instalação repetidos nas pontes | Contrato comum em chain/README.md; removidas instruções para copiar pontes já instaladas e alegações permanentes de integridade conferida |
| Contexto com ausência de código, seguida de atualizações que a contradiziam | project.md consolida implementação e pendências com referências aos componentes; contexto arquitetural concentra responsabilidades |
| Templates em inglês contrariando a convenção local | Templates em pt-BR; contrato técnico importado de evidência preservado com ajustes operacionais |
| Preâmbulos e ressalvas repetidos dentro dos pacotes | Entrada resumida, remoção de preâmbulos redundantes em 40 implementation-details e de avisos auxiliares já cobertos pelo escopo |
| Recomendações incondicionais de snapshots/backups | Skill de integridade e referência subordinadas à retenção do consumidor; não presumem recuperação sem revisão disponível |
| Critérios local-state no meio de parágrafos | Quatro declarações AC em linhas reconhecíveis pela gramática, sem mudar cenários ou resultados esperados |
| Observação E02 vinculada à spec anterior | Transferida intacta para registro histórico; nenhum hash antigo renovado e nenhum critério promovido |
| Convergências exibindo aprovações antigas como atuais | Entrada aponta ao evidence.json vigente; narrativas datadas permanecem históricas |
| Links para ZIPs removidos e narrativas duplicadas entre tarefas/convergência | Caminhos de backups identificados como históricos; relatos repetidos substituídos por referência à convergência |
| Diagrama de governança orientava baseline permanente | Guia de ferramentas encaminha ao contrato atual de instalação/recuperação |
| Localização repetida em links fixos nas pontes | Campo skill_path permanece como localização migrável, compatível por inspeção com o instalador e suas fixtures |

## Repetições preservadas deliberadamente

Os pacotes continuam utilizáveis de forma independente. Referências curtas às
políticas locais e avisos de proveniência em registros importados evitam que uma
consulta isolada seja interpretada como autorização ou evidência atual. Não são
cópias concorrentes da regra completa.

Identidades, descrições e hashes exigidos pelo perfil, frontmatter e metadados
de interface têm consumidores diferentes; foram mantidos. Pontes formato 1
continuam existindo para cada pacote porque auditor e instalador dependem delas.
Não foi introduzida dependência transversal entre pastas de skills: o auditor
restringe links Markdown internos ao próprio pacote.

As fronteiras entre scheduler, protocolo, runtime e processos; schemas,
projeções e publicação; autenticação, autorização e administração; e métodos de
frontend permanecem. Semelhança de assunto não torna esses métodos equivalentes.
A análise de seleção existente continua sendo a referência para cada identidade.

## Rastreabilidade e estado

O [manifesto das alterações](changes.json) identifica conteúdo observado antes e
depois quando disponível. Não contém cópias dos arquivos nem constitui backup.
O perfil atualiza somente hashes do conteúdo editado e notas de adaptação;
source_files, source_documents, source_archives e bundles originais permanecem.

A [observação E02](../local-state-20261111/observation-E02.json) mantém seus
resultados e hashes originais como registro histórico único. Foi retirada da
lista de evidências ativas de local-state, pois a declaração da spec mudou.
Ela não é uma cópia de instalação ou de código para recuperação.

As features de governança e estado local mantêm todos os critérios UNVERIFIED.
As demais observações históricas não foram renovadas. Nenhum critério recebeu
aprovação por edição de texto, por atualizar o perfil ou por reduzir duplicações.

## Verificação e limites

Realizados: leitura, buscas textuais, inspeção dos candidatos e do resultado,
comparação das identidades de conteúdo usadas nas edições e reconciliação do
perfil. A escrita em .codex, protegida pelo ambiente, recebeu autorização.

Não executados: automate.sh, auditor de governança, instalador/preflight,
validador SDD, quick_validate, testes, renderização ou ensaios comportamentais
das skills. Não há alegação de PASS automatizado, aceitação de produto ou
eliminação de defeitos preexistentes fora deste escopo.

Retomada da validação: seguir task-verification.md quando houver pedido de commit.
A execução autorizada deve reexaminar catálogo, preparação/instalação nas fixtures,
referências e critérios atuais; resultados históricos não substituem essa execução.
Sem downloads, commits, publicação ou mudanças no código operacional nesta revisão.
