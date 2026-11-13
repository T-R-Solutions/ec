# Ec / Signal — contrato da implementação visual

Referência: DESIGN.md rev. 1.0 do ZIP `ec-signal-visual.zip`, preservado em
`docs/design/sources/ec-signal-visual.zip`, membro
`ec-signal-visual/DESIGN.md` (ZIP original movido em 11/11/2026;
extrações locais foram removidas)
(caminhos a partir da raiz). Prevalecem AGENTS.md e as duas normas Ec em `docs/`.
Este arquivo registra decisões desta implementação, não substitui a referência.

## Decisões

Precision Workbench: shell persistente, contexto de projeto, conteúdo dominante,
separadores e inspector recolhível. Navegação global com nove áreas; seleção de
projeto é independente da área. Galeria e demonstrações são acessíveis sem sessão.
As demonstrações não recebem cliente API e usam fixtures isoladas por domínio.
Pinia retém propostas somente na memória da aba. A seleção é conservada pelo
KeepAlive entre áreas demonstrativas; sair desse modo pode reiniciar a seleção,
mas conserva as propostas locais. Operação preserva rascunhos no store existente.

Primitivos e papéis: `src/shared/ui/tokens.css`. Shell #243C59, contexto #182535,
ação #28679F/#205483, seleção #E8F2FD, texto #263548, sucesso #226B50, atenção
#80521D e falha #A53848. Seleção tem faixa persistente; foco tem outline independente.
Categorias não representam resultados. Neutros Light propostos foram implementados
como candidatos; Dark é opt-in, identificado como candidato, com valores propostos
pelo Ec. Não há aceite de contraste/comparação abrangente.

IBM Plex Sans/Mono são famílias preferidas, pesos 400/500/600/700. Não há binários
no pacote nem instalados encontrados por `fc-list` neste ambiente. Sem download ou
CSS remoto. Capturas usam fallback; carregar e observar os pesos reais continua
pendente. Nove SVGs inline de baseline foram preservados em SignalGlyph.vue,
com geometria da referência. Não são apresentados como a biblioteca de 125 glyphs.
Favicon azul histórico continua no ZIP; não foi promovido à nova marca.

## Contratos compartilhados

| Componente | Props / slots | Estados e teclado | Consumidores |
| --- | --- | --- | --- |
| StatusBadge | label, tone restrito | Texto + símbolo; não interativo | Shell, agentes, demonstrações, galeria |
| StateNotice | title, tone; default/action | role=status; ações nativas | Entrada, consultas, editor, demos, galeria |
| InspectorPanel | title; default | Botão com aria-expanded; Tab/Enter/Espaço; recolhe sem remover controle | Editor, projetos, demos, galeria |
| SignalGlyph | name de baseline | Decorativo aria-hidden; nome fica no controle | Nove áreas |
| DemoWorkspace | definition, openDraft; visual(record,values) | Busca, seleção, modo, formulário, revisão/descartar/reter; foco no detalhe/revisão | Oito demonstrações por domínio |

Botões/campos/tabelas usam HTML nativo e estilos compartilhados. Componentes não
possuem flags de domínio; cada domínio fornece fixture, campos, consequências e
composição visual. DemoDefinition é contrato de apresentação, não schema Ec.
YAML demonstrativo é projeção somente leitura, sem parser ou promessa de round-trip.
O editor operacional mantém o parser Server e a comparação/aceite textual existente.

Breakpoints: 1600, 1100, 760 e 420 CSS px. Grid colapsa o inspector abaixo do
conteúdo; navegação móvel usa botão reabrível. Tabelas possuem rolagem local;
logs/editores preservam região técnica. Densidade reduz espaços/controles, não
texto essencial; movimento reduzido respeitado. Foco e reflow precisam de revisão
manual completa nos consumidores, especialmente zoom 200%/400%.

## Acesso à galeria e demonstrações

No frontend em desenvolvimento: botão “Galeria de componentes” ou `?gallery=1`.
Demonstrações: `?demo=projects`, `?demo=access`, `?demo=extensions`, etc., usando
os IDs de `src/app/navigation.ts`. `theme=dark` e `density=compact` permitem
capturas reproduzíveis. Esses parâmetros não contêm sessão nem projeto privado.
Navegação permanece interna à aba, como no frontend anterior; não implementa
histórico de rotas/deep links privados.

[Inventário, cobertura, evidências e limites](../../../docs/validation/frontend-signal-20261111/report.md).
