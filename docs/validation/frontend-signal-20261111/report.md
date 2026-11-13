# Ec / Signal — evolução visual em 11/11/2026

## Inventário anterior à edição

Rota local: apresentação e demonstrações isoladas, preservando contratos HTTP,
sessão, concorrência, persistência e execução. Fontes atuais: `App.vue`,
`Editor.vue`, `Runs.vue`, `drafts.ts`, `access/src/index.ts`, `shared/api.ts` e
guias de frontend. Hashes anteriores em `baseline.json`. Alterações preexistentes
em scripts, documentação, testes de integração e organização de `.local` não
pertencem a esta entrega; nenhuma alteração prévia no frontend foi observada.

| Capacidade encontrada | Classificação | Evidência/limite |
| --- | --- | --- |
| Sessão opaca, projetos e papéis acessíveis | Operacional no código | GET/DELETE `/api/v1/session`, GET `/projects`; integração atual não executada |
| ProjectPipeline ec/v1alpha1 de uma etapa | Operacional no código | Editor Visual/Formulário/YAML; validação Server; revisão textual explícita |
| Publicação, base/revisão efetiva e pedido incerto | Operacional no código | `/projects/:id`, `/validation`, `/revisions/:id`, `/publications`, `/receipts/:id` |
| Submissão e execuções | Operacional no código | `/runs`, `/run-receipts/:id`, `/runs/:id/cancel`; polling sequencial |
| Saída e artefatos | Operacional no código | `/runs/:run/attempts/:attempt/output`; verificação de bytes/tamanho/SHA-256 |
| Agentes por projeto | Operacional no código | `/agents`; validade de sessão não mede heartbeat/capacidade |
| DAG, pools, entregas, identidades federadas, plugins, administração | Planejada | Sem clientes de gestão no frontend atual |
| Protótipo recebido | Demonstrativa | Renderizador HTML, fixtures e listeners; não é arquitetura Vue nem serviço |

O frontend tinha shell de duas colunas, três destinos locais, dois temas,
estilos globais compactados e três tokens (`panel`, `border`, `accent`). Editor,
Runs e App concentravam apresentação. Já existiam estados de validação, conflito,
pedido incerto, acesso restrito, consulta falha e saída parcial. Não havia
galeria, nove áreas, densidade configurável ou fontes IBM Plex locais.

## Fontes e diferenças

ZIPs originais preservados em `.local/analiser/`. Extração nova e exclusiva:
`.local/analiser/signal-reference-20261111/ec-signal-visual/ec-signal-visual/`
e `.local/analiser/signal-reference-20261111/SKILLS-DESIGN-v4.0.0/ox-design-system/.agents/skills/`.
Foram consultados DESIGN.md rev. 1.0, index.html, design-system-ec.html,
signal.css, workbench.css/js, projects.css, project-workspace.js,
ec-enterprise.css/js, fixture projects-v09.json, README-START, handoff,
manifesto e documentação v09/v010. As duas normas vigentes são as de `docs/`,
não as cópias do pacote.

Padrões aproveitáveis: painéis acoplados, árvore de contexto, lista com inspector,
revisão antes/depois, seleção independente de foco, tema/densidade. O protótipo
contém sobreposições de estilos e apresentação imperativa; não serão transplantadas.
DESIGN.md substitui o azul/Inter históricos por papéis Ox e IBM Plex. Não concede
funcionalidade aos exemplos. O manifesto cita `baseline/v09`, `v08.html`,
`mockups/v010` e `tests/v010`, ausentes nesta extração. QA-STATIC-RESULTS é um
registro recebido, não execução atual. A biblioteca completa de 125 glyphs não
foi entregue; existem SVGs inline de baseline e favicon. Fontes remotas são
referenciadas, mas os binários não estão no ZIP.

## Critérios e método

Preservar handlers/stores reais e rascunhos; separar demonstração da sessão;
expor nove áreas com tarefas avaliáveis; usar tokens semânticos, teclado nativo,
retorno ao contexto, tema/densidade e layout adaptativo. Sem nova biblioteca,
download, backend, catálogo de skills ou scripts de verificação.

Skill primária: `.codex/skills/design-system/SKILL.md`. Auxiliares lidas:
`vue3-engineering` (composição/reatividade), `css-layout-engineering` (reflow),
`web-accessibility` (semântica/foco), `design-review` (cobertura e limites).
Critérios de navegador, contraste, zoom e integração ficam abertos até observação
identificada. WCAG 2.2 AA é objetivo, não conclusão derivada da inspeção de código.

## Implementação e cobertura entregue

| Área | Jornada e composição implementadas | Classificação / limite |
| --- | --- | --- |
| Visão geral | Selecionar próxima tarefa, contexto e ausência explícita de telemetria | Entrada operacional; índice demonstrativo sem métricas inventadas |
| Projetos | Busca/lista real e abertura da configuração; fixture com hierarquia, propriedade, detalhe, formulário e revisão | Consulta operacional; gestão demonstrativa |
| Pipelines | Editor existente com inspector recolhível, modos e diff; fixture de sequência de uma etapa | Operacional preservado no código; DAG múltiplo planejado |
| Execuções | Revisão/hash/commit em lista de propriedades, saída com foco, aviso de polling; investigação sintética separada | Cliente operacional preservado; nenhum log sintético apresentado como real |
| Entregas | Lista de ambientes, promoção proposta, gate e timeline, revisão local | Demonstrativa; não aprova, implanta ou reverte |
| Infraestrutura | Tabela real de agentes/sessões; pools, requisitos e compatibilidade ilustrativos | Snapshot operacional; pools/capacidade demonstrativos |
| Pessoas e acesso | Lista de vínculos, origem direta/herdada, matriz, edição de papel e revisão | Demonstrativa; não cria identidade ou concede acesso |
| Extensões e integrações | Catálogo, origem/versão/permissões, revisão de compatibilidade e proposta | Demonstrativa; confiança/isolamento não comprovados |
| Administração | Políticas de retenção, contexto de auditoria, edição/revisão de consequência | Demonstrativa; nenhum expurgo ou mudança global |

Cada fixture de domínio (`domains/*/src/demo.ts`) registra persona, permissão,
tarefa, dados, consequência e limites. Ação principal comum: selecionar recurso,
editar proposta em formulário, revisar comparação e reter/descartar localmente.
Visual e YAML são projeções da proposta demonstrativa; YAML é somente leitura
nessas áreas sem schema. Nenhum rascunho demonstrativo é publicado ou incorporado
aos stores reais. O frontend não altera autenticação para escolher pessoas/papéis.

O seletor de cenários expõe carregamento, vazio, acesso negado, indisponibilidade,
desatualização, conflito, submissão pendente e falha parcial. São cenários visuais
controlados, não falhas de rede realmente injetadas. Busca sem resultados tem
recuperação. Foco é encaminhado ao detalhe/revisão; inspector tem controle
permanente de reabertura. Esses comportamentos foram inspecionados no código,
não exercitados manualmente no navegador nesta entrega.

Design system: tokens primitivos/semânticos, densidade, foco, movimento, tipografia,
contornos e elevação; StatusBadge, StateNotice, InspectorPanel, SignalGlyph e
DemoWorkspace. Os consumidores usam CSS/HTML nativo e Vue, sem renderizador do
protótipo, v-html, listeners globais ou dependência nova. O [contrato frontend](../../../application/server/frontend/DESIGN.md)
descreve props/slots/teclado/responsividade. A [análise Ox](skills-review.md)
recomenda mesclagens pontuais, sem executar alteração de catálogo.

## Observações visuais atuais

Método: Vite de desenvolvimento (`npm --prefix application/server/frontend run dev
-- --port 5173 --strictPort`) e captura pontual pelo Firefox 158.0.1 do sistema,
headless, perfil exclusivo `/tmp/ec-signal-firefox-profile`. Imagens abertas e
inspecionadas visualmente. Sem harness, suite ou script de verificação executado.
Firefox avisou impossibilidade de gravar dconf em `/run/user/1000`; as capturas
foram geradas. Não houve download nem uso do perfil pessoal.

| Captura final | Estado / fixture | Viewport solicitado | Tema / densidade | Observação |
| --- | --- | --- | --- | --- |
| [Projetos](projects-light-1440-final.png) | Checkout selecionado, disponível | 1440×900 | Claro / normal | Navegação e inspector lado a lado; seleção com faixa; conteúdo inferior exige rolagem |
| [Acesso](access-dark-390-final.png) | Revisores selecionados, disponível | 390×844 | Escuro / compacta | Menu reabrível; tabela legível no trecho; cabeçalhos reduzidos |
| [Extensões](extensions-light-768.png) | Conector Git, disponível | 768×1024 | Claro / normal | Sidebar mantém contexto; inspector passa abaixo; modos visíveis |
| [Galeria](gallery-dark-1920.png) | Papéis, controles e estados | 1920×1080 | Escuro / normal | Distinção visual entre seleção, ação e estados |
| [Sessão](session-light-1440.png) | Sem sessão e sem token | 1440×900 | Claro / normal | Entrada real visível, exploração demonstrativa separada |

Capturas iniciais `projects-light-1440.png` e `access-dark-390.png` são estados
intermediários desta implementação. Foram preservadas para explicar o refinamento
de cabeçalhos; não são baseline do frontend anterior nem screenshots do ZIP.
Os pares inicial/final usam a mesma fixture, viewport, tema e densidade. Não há
comparação renderizada com a versão anterior à tarefa. O viewport inclui a barra
de rolagem; não foi medida via DOM a área CSS interna. Não inferir ausência de
qualquer overflow fora do trecho capturado.

## Verificações não executadas e pendências

Não executados: automate.sh, typecheck, Vitest, build de produção, cobertura,
harness E2E/integração. Os testes de contraste foram adaptados aos papéis
semânticos; o teste de App acrescenta retenção local sem chamadas HTTP, e o de
Runs acompanha a lista de propriedades. São testes escritos, não aprovados.
O renderer registra intenção de foco; isso não comprova foco nativo.

Também não verificados: teclado completo, retorno de foco no navegador,
leitores de tela, zoom 200%/400%, larguras intermediárias, touch, contraste
medido, todos os estados nos dois temas/densidades, editor/execuções com sessão
real. Capturas são observações de renderização, não integração ou conformidade.
A política impede rodar scripts sem pedido de commit e escolha atual; não houve
pedido de commit. As evidências históricas permanecem históricas.

Pendências concretas de produto: fonte IBM Plex com pesos licenciados/preparados,
biblioteca completa de glyphs; edição YAML/schema avançada em áreas demonstrativas;
criação/duplicação/movimentação/arquivamento visual completos; convites e gestão
de identidades; instalação/remoção de plugins; DAG multi-etapa; gestão de pools,
deployments e telemetria; roteamento com histórico/deep links privados. Estes
recursos não são concluídos por esta implementação. A galeria não substitui a
verificação dos consumidores. Não houve mudança de backend, dependências,
autorização, persistência, integridade ou catálogo de skills.


## Análise do teste de retenção local — 11/11/2026

O log run-orZMYUEe-28-frontend-test.stderr.log registra que App.test.ts não
encontra o input de valor Revisores após Editar proposta local; input(undefined)
falha ao atribuir value. A cobertura encontra a mesma falha. Os logs registram
25 testes aprovados e um erro; não houve nova execução nesta análise.

A inspeção identifica perda de reatividade na primeira chamada de
useDemoWorkspace.open: a expressão de atribuição `drafts[key] = { ... }` retorna
o objeto bruto recém-criado. Pinia devolve o resultado da action sem convertê-lo.
DemoWorkspace armazena esse retorno em um objeto comum por Object.fromEntries;
o computed draft devolve a mesma referência. Alterar mode/stage por ela não
passa pelo proxy Vue, e o formulário não substitui a apresentação Visual.
O getter reativo só converte o objeto ao lê-lo novamente por drafts[key].
Isso também explica a diferença entre criar e reabrir um rascunho existente.

Correção proposta para src/app/demo/store.ts, sem aplicá-la neste pedido de análise:

```ts
function open(key: string, values: Record<string, string>) {
  if (!drafts[key]) {
    drafts[key] = { values: { ...values }, stage: 'detail', mode: 'Visual', retained: false }
  }
  return drafts[key]
}
```

A retomada deve conferir a identidade reativa já na primeira abertura, a
aparição do formulário após Editar, a revisão/retenção, a navegação entre áreas,
a preservação do rascunho existente e a ausência de chamadas HTTP. O teste de
App existente cobre a jornada; uma regressão do store pode observar isReactive
e a mesma identidade ao reabrir. Não mascarar a falha tolerando input indefinido.
Sem testes/browser atuais, a inspeção não comprova a correção executada.

Inputs observados: store.ts SHA-256
`f464fe13215c09e03197b3bc3bf342df6079f82b147f9aa20a6e970cef66db25`;
DemoWorkspace.vue SHA-256
`b779c2807b9ccaeaf0a08a666573d73b88f81c65d9c96f398d6090b4902bba2f`.
Método auxiliar: vue3-engineering, com leitura da referência de reatividade e
dos getters/actions das versões Vue/Pinia instaladas. Nenhuma dependência nova.

## Correção aplicada após autorização — 11/11/2026

O pedido posterior autorizou corrigir o frontend. `open` agora cria o objeto,
quando necessário, e sempre retorna a leitura de `drafts[key]` pelo proxy Vue.
Isso preserva reatividade na primeira abertura e a identidade ao reabrir.
Foi acrescentado `src/app/demo/store.test.ts` para observar modo, etapa e valor,
identidade do rascunho, isolamento entre chaves e preservação da fixture.
O teste de jornada App existente continua exigindo o formulário real; nenhum
assert foi relaxado para tolerar o input ausente.

Inspeção manual realizada. Vitest, typecheck, cobertura, navegador e suíte não
foram reexecutados nesta correção; aceite operacional permanece não verificado.
