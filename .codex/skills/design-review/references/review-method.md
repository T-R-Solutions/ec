# Evidence-based interface review

The interface recommendations below synthesize Kholmatova and MacDonald; the decision communication section also draws on Tom Greever. All PDF locations are one-based physical pages. Greever was subsequently supplied and fully analyzed; see the coverage record (historical library source: `system/research/greever-coverage.json`; not required at runtime).

## Follow a task, not a gallery

**Action/context:** Trace a representative journey with realistic content, including interrupted and unsuccessful paths. **Evidence:** MacDonald chapter 3, PDF 109–158, and chapter 6, PDF 238–284; Kholmatova chapter 3, PDF 64–82. **Rationale:** isolated components can look coherent while their combination changes scope or loses progress. **Exceptions/costs:** a static mockup supports only partial evaluation; do not spend a full study budget on an obvious local defect. **Validate:** replay the same task and check intermediate state transitions; use appropriate research to test uncertain user behavior.

Example, illustrative and not executed: A global search leads to a filtered collection whose search box silently searches only that collection. The defect hypothesis concerns scope comprehension. Compare an explicit scope label plus clear-reset action with retaining global search. Check representative tasks and whether users can recover from an empty result; do not assert that either version increases conversion without evidence.

## Distinguish observation from interpretation

Record four separate statements when uncertainty matters:

- Observation: what the interface actually displayed or did, with location and conditions.
- Impact: why this can affect the specified task; qualify unmeasured reach/frequency.
- Recommendation: a concrete change tied to the cause.
- Validation: the test or research that could confirm or reject the expected improvement.

**Evidence:** Kholmatova chapter 2 PDF 47–63, chapter 7 PDF 167–184; MacDonald chapter 5 PDF 209–237. **Rationale:** product-specific principles and observed friction are more actionable than generic claims of simplicity. **Exceptions/costs:** a known criterion failure can justify repair without an A/B test; preference questions may need audience evidence. Extra measurement is not valuable when it cannot change a decision.

## Compare patterns by purpose

| Decision | Context and action | Trade-off and exception | Validation |
| --- | --- | --- | --- |
| Walkthrough or direct use | Explain complex unfamiliar tasks progressively; allow experienced users to bypass nonessential guidance | Tours interrupt; safe exploration may teach better, but consequential actions need protection | First-use and returning-user tasks; restart/help access |
| Pagination, load more or infinite scroll | Choose by deliberate finding versus open-ended browsing, return location and dataset constraints | Endless loading can frustrate orientation and footer access; pagination adds navigation | Locate and return to an item, keyboard navigation, empty/error states |
| Immediate or deferred validation | Give timely actionable feedback while preserving input | Premature errors during typing distract; delayed errors increase recovery effort | Valid/invalid/boundary inputs and correction without lost work |
| Modal or inline interaction | Use interruption only when the task needs focused interaction | Modal focus obligations and lost context may outweigh clarity; inline complexity can also overwhelm | Open/close, keyboard/focus and canceled-operation paths |

Evidence: MacDonald chapters 2–3 PDF 40–158 and chapter 5 PDF 209–237. These are contextual choices, not a universal ranking of patterns. For web behavior, consult the relevant [APG pattern](https://www.w3.org/WAI/ARIA/apg/) and exact [WCAG criteria](https://www.w3.org/TR/WCAG22/) rather than inferring conformance from this table.

## Keep consequences visible

**Action/context:** Retain clear explanations of material effects when simplifying labels, defaults and confirmation flows. **Evidence:** critical inspection of MacDonald PDF 64 (preselected marketing), PDF 156–157 (public favorites disclosure removed in a shortened version), and PDF 272 (public wish list); Kholmatova conclusion PDF 286–289. **Rationale:** shorter wording can reduce information needed for a meaningful choice. **Exceptions/costs:** not every action needs a confirmation dialog; repeated low-risk prompts create friction. **Validate:** compare the displayed promise with actual data visibility and resulting action; test recovery and a representative user's understanding when possible. This synthesis rejects problematic details in source examples rather than endorsing them.

Illustrative before/after, not executed: Before: “Like” silently publishes an item to a public list. After: “Add to public favorites” with a clear visibility explanation. The wording changes intentionally; verify the actual privacy contract before choosing the final text.

## Present decisions for review

**Action/context:** For a material choice, explain the problem, the user effect and why the recommended option fits better than viable alternatives. Link the rationale to the actual artifact and decision status. **Evidence:** Greever chapters 1 and 6, PDF 35–46 and 153–171; chapters 4 and 9, PDF 117–129 and 205–216. **Rationale:** reviewers need a checkable argument and a record of what remains unresolved. **Exceptions/costs:** a one-line explanation may suffice for a routine correction; extra mockups and meetings are justified only when they can resolve uncertainty. **Validate:** check the evidence and commitments, retain material objections and separately verify the product change. Agreement is not a usability test.

Use [decision communication](decision-communication.md) for preparation, clarification, objections and follow-up. Its safeguards explicitly qualify persuasive techniques in the source; they do not replace task observation or accessibility checks.

## Crítica visual com condições controladas

Para avaliar uma mudança visual, estabeleça primeiro qual decisão a observação
pode alterar. Um brief curto pode conter tarefa, problema observado, hipótese,
limites de escopo, consumidor e condições de comparação. Não exija uma nova peça
formal se esses dados já estiverem no pedido ou registro da tarefa.

Faça duas leituras complementares da mesma jornada: primeiro tarefa, hierarquia,
localização e consequência; depois papéis de cor/forma, densidade, contornos,
foco e truncamento. Isso ajuda a distinguir uma necessidade de espaço ou
orientação de uma preferência cromática. Corrija primeiro o problema que impede
ou induz a ação; acabamento não compensa uma confirmação indevida.

Para cada achado material, vincule região/componente e fixture à observação,
impacto, causa provável, mudança mínima e risco de regressão. Use a severidade
adotada pelo projeto; na ausência dela, descreva se o problema impede a tarefa,
induz uma consequência incorreta ou acrescenta fricção. Não invente frequência,
pontuação estética ou gravidade a partir apenas da preferência do revisor.

Compare baseline e candidato com o mesmo conteúdo, estado, viewport/zoom, tema
e densidade. Uma captura estática não demonstra recuperação nem compreensão do
usuário. Se as condições não forem equivalentes, registre o que ainda pode ser
observado e evite atribuir a diferença à mudança investigada. Após a correção,
reobserve os achados afetados sem substituir silenciosamente os registros de
falha. Uma correção direta e delimitada não exige alternativas artificiais,
reunião ou permissão já concedida.

Síntese adaptada de ox-design-brief e ox-critique, pacote SKILLS-DESIGN-v4.0.0.
O pacote original não é requisito de execução desta referência.
