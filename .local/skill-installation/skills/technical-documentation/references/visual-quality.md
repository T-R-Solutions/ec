# Qualidade visual mensurável

Use ao conceber ou revisar figuras e apresentação. Os termos abaixo descrevem
propriedades verificáveis, não um estilo comercial ou selo de conformidade:

- **Beleza:** hierarquia de leitura, composição alinhada, espaçamento consistente,
  tipografia legível, equilíbrio entre densidade e espaço e ausência de defeitos visuais.
- **Enterprise:** semântica correta, iconografia especializada e consistente,
  proveniência/licenças, acessibilidade, compatibilidade e reprodução/manutenção.
- **Moderno:** fontes editáveis, vetores quando adequados, adaptação ao destino e
  temas suportados, com informação progressiva e tokens reutilizáveis.

## Briefing visual proporcional

Antes de desenhar, identifique pergunta, público, mensagem, fonte/revisão, estado
(real, planejado ou ilustrativo), suporte final e dimensões de exibição. Defina a
leitura principal e quais relações/decisões precisam aparecer. Use o design system
do projeto; se ausente, documente tokens mínimos de cores semânticas, fonte,
espaçamentos, raios, linhas e ícones. Prefira uma família tipográfica principal e
uma monoespaçada para dados técnicos.

## Matriz de critérios e métodos

Os valores de tamanho, grade e densidade são **convenções iniciais desta skill**,
ajustáveis ao projeto com justificativa registrada. Contraste vem das fontes WCAG
indicadas ao final; verificar estes itens isolados não equivale a conformidade WCAG.
Não substitua necessidades reais por uma pontuação estética agregada.

| Dimensão | Critério verificável | Método / limite |
| --- | --- | --- |
| Utilidade | Toda figura responde uma pergunta e possui conclusão/explicação adjacente | Inspecionar ligação com tarefa; remover recurso sem função informativa |
| Fidelidade | Todo componente, estado e relação relevante tem fonte ou identificação de hipótese | Comparar com contrato/implementação; zero setas ou números fabricados apresentados como reais |
| Hierarquia | Título da figura, agrupamentos e conteúdo têm papéis distintos e ordem de leitura consistente | Inspeção no destino, sem competir com a hierarquia de títulos do documento |
| Alinhamento e espaço | Espaçamentos controlados por tokens; partida: grade de 4/8 px, margens de figura de 16–24 px | Medir no canvas/exportação; preservar ritmo e agrupamento após escala |
| Tipografia | Partida no tamanho final: corpo 16 px e rótulos essenciais de diagramas 14 px; no máximo duas famílias | Medir após escala de exibição; impressão usa tamanho físico equivalente, não pixels do arquivo |
| Contraste | Texto normal ≥4,5:1; texto grande ≥3:1; elementos gráficos necessários à compreensão ≥3:1 contra cores adjacentes | Calcular cores efetivas em cada tema oferecido; observar as exceções normativas, sem arredondar resultado para aprovação |
| Semântica visual | Toda distinção crítica por cor também usa texto, forma, padrão ou rótulo | Inspecionar em tons de cinza e pela alternativa textual |
| Geometria | Zero textos cortados/sobrepostos, setas sobre rótulos, ícones ausentes ou conexões com destino ambíguo | Inspecionar render final a 100% e após redimensionar; corrigir layout, não apenas reduzir fonte |
| Complexidade | Partida: revisar divisão acima de 12 nós ou três níveis em uma figura de visão geral | Não é limite do domínio; dividir em visão geral/detalhe quando leitura ou relações ficarem ambíguas; não ocultar falhas importantes |
| Iconografia | Uma família vetorial principal, mesma grade/estilo e significado; ícones com texto e fonte/licença identificados | Zero emojis, glifos quebrados ou mistura arbitrária de outline/filled/3D; logos oficiais só para tecnologias realmente envolvidas |
| Exibição e reflow | Zero corte de conteúdo essencial no destino; conteúdo textual deve refluir | Testar larguras reais; sem alvo definido, amostras de 360, 768 e 1280 CSS px. Diagramas 2D podem ter versão ampliada e descrição estruturada equivalente; não comprimir rótulos até ficarem ilegíveis |
| Acessibilidade | Toda figura informativa possui alternativa textual; figuras complexas têm descrição de relações/conclusões | Inspecionar Markdown/HTML/SVG; apenas escrever “diagrama” ou repetir o título é insuficiente |
| Integridade dos assets | Zero links quebrados ou recursos externos indispensáveis não declarados | Conferir paths, resolução offline conforme destino, viewBox/dimensões e exportações |
| Rastreabilidade | Fonte editável, comando/ferramenta/versão, origem/licença dos ícones e dados registrados | Regerar quando possível; hash identifica bytes, não prova correção semântica |

Texto grande para contraste segue a definição WCAG, não um tamanho arbitrário:
18 pt ou 14 pt em negrito (aproximadamente 24 e 18,67 CSS px). Conferir tamanho no
destino; declarar `fontSize` na fonte não garante tamanho final da figura.

## Ícones, imagens e composição profissional

Para arquitetura, infraestrutura e integrações, use ícones vetoriais especializados
com semântica reconhecível, em vez de depender exclusivamente dos pictogramas
padrão de cloud/database do renderizador. Reutilize uma biblioteca licenciada do
projeto; se precisar selecionar outra, confira origem, licença e integração antes
de incorporar. Partida para uma família outline: viewBox 24×24, traço consistente
entre 1,5 e 2 unidades e exibição de 20–24 px. Ícones filled ou logos oficiais seguem
a própria família e preservam proporções; não force traços artificiais.

A exigência de acabamento não transforma todas as formas em ícones: decisões,
estados, lifelines e cardinalidades usam sua notação correta. Ícones complementam
rótulos, limites, conexões e legendas; não indicam fornecedor ou serviço que não
existe na arquitetura. Evite relevo/3D, sombras e gradientes sem função de leitura.

Use SVG para diagramas/ícones nítidos quando suportado e PNG para captura real ou
fallback necessário. Recorte imagens para a tarefa, preserve proporções, mantenha
texto técnico selecionável quando possível e remova dados sensíveis. Para bitmap,
partida de exportação: resolução de 2× a dimensão de exibição, medindo legibilidade
e tamanho do arquivo. Não distorça screenshot nem use upscale como prova de qualidade.
Ilustrações geradas devem ser identificadas; não substituirão uma captura solicitada
ou um gráfico calculado a partir de dados.

## Gráficos de dados

Escolha representação pela pergunta: barras para comparação, linhas para evolução,
distribuições para dispersão e percentis. Use ferramentas de plotagem para figuras
analíticas e de publicação, preservando script/dados ou consulta reproduzível.
Inclua unidade, período, população/amostra, origem, escala e estado dos dados.
Barras partem de zero; escala logarítmica ou eixos truncados exigem justificativa
explícita apropriada ao gráfico. Legendas/marcadores permitem distinguir séries
sem cor. Mostre incerteza quando pertinente, sem precisão superior à fonte.

Não crie métricas para preencher espaço. Sem dados, use um esquema conceitual
rotulado ou explicite a lacuna; dados demonstrativos precisam de marca inequívoca.

## Registro e aceitação

Para um documento substancial, registre de forma compacta: figura → pergunta →
fonte/revisão → fonte editável → renderizador/versão → tamanho/tema observados →
critérios verificados → resultado/lacunas. Use o registro de evidências existente
ou uma nota de revisão; não imponha novo arquivo ou burocracia a toda edição.

Rejeite defeitos semânticos, segredos expostos, assets quebrados, texto ilegível,
ausência de alternativa informativa e medições inventadas. Corrija e repita só os
checks afetados. Se renderização, medição ou teste com leitores não ocorreu,
registre essa dimensão como não verificada, mesmo que fonte e links sejam válidos.

## Fontes técnicas

- [W3C: contraste de texto, SC 1.4.3](https://www.w3.org/WAI/WCAG22/Understanding/contrast-minimum.html).
- [W3C: contraste não textual, SC 1.4.11](https://www.w3.org/WAI/WCAG22/Understanding/non-text-contrast.html).

Consultadas em 2026-11-04. São fundamentos dos critérios de contraste; grades,
limiares de revisão e direção de arte desta skill são convenções editoriais locais.
