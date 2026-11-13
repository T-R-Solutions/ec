# Application shells, density and intentional overflow

> Ec-specific rules below apply only to an Ec consumer whose current contract establishes them.

**Purpose:** build non-decorative responsive workspaces whose navigation, main content and inspector remain usable without fragile fixed dimensions. The method is generic; Ec requirements and authorized actions are owned by its authoritative project documents, not embedded here.

## Layout contract worksheet

Record: regions; semantic DOM order; layout geometry at wide/medium/narrow viewports; minimum content width; accepted scroll containers; long strings and error messages; important controls; keyboard tab order; split-pane sizes; dynamic open/close; native zoom and text-only zoom acceptance; screen reader and cross-browser tests owned elsewhere.

Example intent, not a fixed design system:

| Region | Wide | Medium | Narrow | Constraint |
|---|---|---|---|---|
| Header and actions | Row/flow with wrap | Wrap | Wrap | No clipped critical buttons |
| Navigation | Sidebar | Sidebar | Stack or accessible alternative | Preserve access and semantic order |
| Workbench/main | Flexible Grid track | Flexible main | Full column | `min-inline-size:0`; no root horizontal overflow |
| Inspector | Dedicated track | Below or collapsible | Full width/accessible drawer | No unreachable fields |
| Data table | Region-based scrolling | Region-based scrolling | Region-based scrolling | No page-wide horizontal scrolling |

## Scroll choice

`overflow-x:auto` on a **bounded and labeled** table wrapper can be a reasonable exception for inherently two-dimensional content. A global `body{overflow-x:hidden}` hides root symptoms without preserving access. `min-width` on a table can be acceptable *inside* its own scrollport, but not on the shell. For DAGs and code editors, consider a semantic alternative view and reachable resizing controls; that UX decision belongs to the relevant product/accessible interaction owner.

Keep primary actions on screen and test for long project names, long commit hashes and locally translated strings. A resized inspector can reduce the workbench below a container breakpoint without resizing the viewport: hence the division between `@media` and `@container`.

## Common failures to reproduce

- `grid-template-columns: 220px 1fr 300px` leaves no feasible content width at 700px; use breakpoint logic and minimum tracks.
- `1fr` without minimum consideration expands to min-content; inspect `minmax(0,1fr)` and nested items.
- Hidden or absolutely positioned navigation removes discoverability and keyboard access.
- Fixed `height` on multi-line labels or status cells clips enlarged content.
- `overflow:hidden` on top-level body makes critical content inaccessible while a superficial width oracle passes.
- Truncating long IDs without a discoverable way to recover full text can violate requirements.

Evidence: compare bounding boxes, root scrollWidth, internal scrollWidth and critical visibility. A screenshot at one width is insufficient. See [browser oracles](browser-verification-and-oracles.md) and [accessibility](layout-accessibility-and-zoom.md).

## Prioridade geométrica e densidade

Quando painéis competem por espaço, identifique a região principal da tarefa e
qual informação cada região auxiliar precisa conservar. Registre mínimos,
máximos e condições de recolhimento a partir do conteúdo real; dimensões de um
protótipo são hipóteses, não constraints comprovadas no consumidor.

Antes de reduzir fontes, procure cabeçalhos duplicados, wrappers e espaçamentos
que comprimem a área útil. Compare normal/compacto mantendo os mesmos dados e
controles críticos; reduzir densidade visual não autoriza omitir erros, estados,
rótulos ou o controle de reabertura. Considere uma região recolhida aceitável
somente quando sua informação e ação continuam recuperáveis.

Observe abrir/recolher e, quando houver, redimensionar o inspector: o conteúdo
principal pode cruzar uma restrição interna sem alterar a janela. Registre região,
limite atingido, rolagem intencional e controle que recupera o painel. Não acrescente
redimensionamento ou drag apenas porque existe no protótipo; teclado, foco e
alternativas precisam corresponder ao comportamento implementado.

Síntese adaptada de ox-layout, pacote SKILLS-DESIGN-v4.0.0, sem medidas fixas,
convenções nativas ou matriz obrigatória de monitores.
