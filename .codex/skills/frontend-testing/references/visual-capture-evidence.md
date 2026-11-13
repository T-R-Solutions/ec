# Capturas comparáveis e limites de evidência

Consulte quando uma mudança requer captura, regressão visual ou comparação entre
renderers. Use a infraestrutura do projeto e a autorização de execução vigente.
Este método não exige instalar navegador, baixar assets ou montar uma suíte nova.

## Identificar uma observação

Reutilize o relatório ou manifesto existente. Para cada caso relevante, registre:

| Campo | O que vincula |
| --- | --- |
| Alvo e versão | Rota/tela, componente consumidor, revisão ou hashes dos arquivos |
| Dados e estado | Fixture/origem, seleção, filtros, painéis abertos, estágio de interação |
| Renderização | Browser/renderer, versão, SO e fonte observada ou desconhecida |
| Geometria | Viewport CSS observado ou apenas solicitado, tamanho da imagem, zoom e escala/DPR quando conhecidos |
| Apresentação | Tema, densidade, locale e preferências relevantes de movimento/contraste |
| Método e artefato | Captura manual/automatizada, caminho real e trecho visível ou página inteira |
| Resultado | Esperado, observado, problema e condição não exercitada |

Não complete campos desconhecidos por inferência. A dimensão de um PNG não
comprova o viewport CSS interno; escala de imagem, zoom e reflow são condições
diferentes. Redija o registro sem tokens, dados privados ou URLs com credenciais.

## Formar um par comparável

Preserve dados e estado ao comparar antes/depois. A alteração investigada deve
ser identificável; mudanças simultâneas de fixture, conteúdo, tempo ou ambiente
reduzem o que pode ser atribuído ao patch. Se as condições diferirem, descreva as
capturas como observações distintas, sem alegar regressão ou melhoria causal.

Espere uma condição de prontidão observável pertinente ao caso, como a resposta
que alimenta a lista e os assets utilizados. Para capturar carregamento, fixe o
estado pendente de forma explícita. Não troque espera por uma pausa arbitrária
ou apresente um cenário forçado como falha real do serviço. Quando não foi possível
confirmar prontidão, registre a limitação da captura.

Monte a matriz a partir dos consumidores e riscos alterados. Inclua variações de
foco, erro, fontes e conteúdo extremo quando puderem mudar a conclusão. Informe
casos observados, falhos, não executados e não aplicáveis, com denominador claro;
não transforme uma quantidade fixa de telas em requisito universal.

## Interpretar e reobservar

Abra cada imagem usada como evidência na escala adequada para leitura. Associe
achados à região/componente e às condições registradas. Um screenshot produzido,
mas não inspecionado, é um artefato capturado, não um parecer visual.

Quando houver comparação automática, declare a tolerância e sua razão. Não
mascare justamente o componente ou estado afetado; diferenças de rasterização
podem exigir análise, não atualização automática da baseline. Preserve a captura
com falha e gere nova observação após a correção, vinculada aos novos inputs.

Inspeção de pixels cobre o conteúdo visível. Medidas DOM, interação por teclado,
leitor de tela e integração com serviço são evidências distintas. Registre testes
não executados como tal, mesmo quando as capturas estão corretas. Consulte
[relato de evidências](test-evidence-and-reporting.md) para os limites por camada.

Síntese adaptada de ox-visual-qa, pacote SKILLS-DESIGN-v4.0.0. Sem contagens,
scripts, temas ou gates exclusivos do pacote original.
