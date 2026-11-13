# Skills para design e frontend

A manutenção de 11/11/2026 incorpora métodos úteis do pacote
SKILLS-DESIGN-v4.0.0 em quatro skills existentes. O catálogo continua com 67
skills. Nenhuma das 11 entradas Ox foi instalada como skill adicional.
[Análise, plano e resultado](../validation/frontend-skills-20261111/report.md).

## Selecionar pelo problema

| Problema concreto | Skill / referência a carregar |
| --- | --- |
| Tokens, aliases, overrides, categorias ou estados de componentes | `design-system` → `references/visual-token-migration.md` |
| Fontes, pesos, fallback, ícones ou acabamento | `design-system` → `references/typography-and-finish.md` |
| Tarefa confusa, hierarquia ou decisão de design | `design-review` → `references/review-method.md` |
| Conteúdo comprimido, inspector, densidade e rolagem | `css-layout-engineering` → `references/responsive-shells-and-overflow.md` |
| Captura, comparação ou regressão visual | `frontend-testing` → `references/visual-capture-evidence.md` |
| Semântica, teclado, foco, reflow ou tecnologia assistiva | `web-accessibility` → referência pertinente existente |

Os caminhos de referência são relativos à skill em `.codex/skills/<nome>/`.
Leia seu SKILL.md efetivo primeiro. Não carregar a lista inteira nem executar
skills auxiliares por encadeamento obrigatório. Formulários, cache e projeções
só requerem seus métodos especializados quando a tarefa altera esses contratos.

## Aplicação no Ec

As duas normas Ec em `docs/` e o
[DESIGN.md do frontend](../../application/server/frontend/DESIGN.md) contêm a
identidade, paleta, fontes, composição e decisões do produto. As skills contêm
método reutilizável: não fixam IBM Plex, frameworks, cores, medidas ou nove áreas
como requisito universal. O DESIGN.md recebido continua preservado na extração
identificada pelo contrato frontend.

Exemplos de uso, não ensaios executados:

- Antes de remover `--accent`, localizar aliases, regras vencedoras e consumidores;
  distinguir ação, seleção e foco mesmo que seus valores coincidam.
- Antes de dizer que IBM Plex carregou, identificar assets/pesos e fonte observada;
  uma declaração CSS ou screenshot com fallback não confirma o carregamento.
- Antes de comparar capturas, registrar fixture, estado, tema, densidade, viewport
  CSS, zoom, renderer e prontidão; a imagem deve existir e ser inspecionada.
- Antes de compactar um inspector, conservar campos, estados e acesso à reabertura;
  testar o comportamento real quando a execução estiver autorizada.

Métodos de captura não autorizam scripts ou downloads. Seguir
[verificação de tarefas](task-verification.md). Disponibilidade da skill,
conteúdo instalado, integridade dos bytes, execução do método e eficácia
observada são afirmações diferentes.

## Manutenção e origem

O ZIP recebido foi preservado e copiado sem modificação para
`config/governance/sources/SKILLS-DESIGN-v4.0.0.zip`, a partir da raiz. O perfil
registra a fonte suplementar e as adaptações dos quatro destinos; `origin` e
`source_files` continuam descrevendo suas fontes originais. A instalação vigente permanece em `.codex/skills/`; baseline duplicada e
`previous.zip` foram removidos pela política posterior. Consulte o [procedimento de manutenção](skill-installation.md).

Desde a política de 11/11/2026, a baseline duplicada e arquivos de backup foram
removidos. Manutenções futuras alteram o pacote instalado e o perfil; recuperação
usa histórico Git/GitHub. Os bundles conservam apenas as fontes originais.
