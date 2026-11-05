---
name: technical-documentation
description: Create or maintain technical guides, API references, and conceptual documentation grounded in versioned sources, with Mermaid diagrams and polished, accessible visuals. Use when documentation is the main deliverable; skip routine inline comments, standalone ADR decisions, binary document layout, and prompt-only writing.
---

# Documentação técnica e visual

Produza documentação correta, navegável e visualmente elaborada, que permita ao
leitor realizar uma tarefa ou compreender um sistema. Preserve o formato, destino,
idioma, identidade visual, fontes canônicas e mudanças existentes do projeto.

## Estabeleça o leitor, a fonte e a apresentação

Identifique público, tarefa, pré-requisitos, versão/ambiente e tipo de documento:
tutorial, guia de procedimento, referência ou explicação. Inspecione implementação,
schemas, testes, configuração e contratos pertinentes. Distinga comportamento
observado, requisito vigente, proposta e desconhecido. Não infira suporte pelo nome
de uma API nem apresente conceito futuro como funcionalidade disponível.

Identifique também o destino de leitura: Markdown do repositório, portal, HTML,
PDF ou outro consumidor. Confira capacidades e versão do renderizador Mermaid,
suporte a SVG, ícones, temas e largura de conteúdo. Reutilize o sistema visual
existente; quando ausente, estabeleça um conjunto pequeno de tokens documentais.
Uma correção curta não exige novo sistema visual ou planejamento separado.

## Componha a narrativa visual

Em documentos substanciais, planeje recursos que expliquem relações, fluxos,
estados ou dados: diagramas Mermaid, gráficos, imagens técnicas e tabelas.
Associe cada figura a uma pergunta do leitor, uma fonte e uma conclusão textual.
Use hierarquia, agrupamento, alinhamento e iconografia contextual; um flowchart
bruto com caixas e ícones genéricos não atende a um pedido de acabamento enterprise.
Não acrescente gráficos decorativos, números inventados ou complexidade artificial.
Não imponha todos os formatos nem uma quantidade fixa de figuras em cada página.

Use **Mermaid como fonte editável padrão para diagramas técnicos**: fluxos,
sequência, estados, relações e arquitetura, conforme a semântica e o suporte do
destino. Mantenha a fonte junto do documento ou dos assets. Para gráficos de dados,
use ferramentas de plotagem com dados e código reproduzíveis quando Mermaid não
for adequado. Para imagens, selecione captura real, ilustração identificada ou
composição vetorial conforme a pergunta; IA não produz evidência de funcionamento.

Leia somente os recursos pertinentes:

- [Qualidade visual mensurável](references/visual-quality.md): ao criar ou revisar
  figuras, tokens, iconografia, gráficos e apresentação. Traduz beleza, enterprise
  e moderno em critérios e métodos verificáveis.
- [Mermaid e recursos visuais](references/mermaid-and-assets.md): ao escolher o tipo
  de diagrama, integrar ícones, exportar figuras ou verificar compatibilidade.

## Escreva e verifique

1. Declare pré-requisitos, diretório de trabalho, inputs, permissões e dependências
   reais. Descubra comandos existentes; não invente instalação, build ou suporte.
   Separe comandos de saídas e forneça valores de exemplo seguros, explicando os
   placeholders. Cubra erros, efeitos e recuperação, além do caminho feliz.
2. Alinhe nomes de API, tipos/defaults, retornos, ausência/null, falhas e efeitos com
   a fonte selecionada. Marque exemplos ilustrativos e diferenças de compatibilidade.
   Garanta que setas, ícones, estados e legendas expressem os mesmos contratos do texto.
3. Crie os visuais selecionados com composição e iconografia profissionais, segundo
   os critérios da referência. Inclua legenda, alternativa textual e proveniência.
   Para arquitetura, ícones especializados representam componentes reais; símbolos
   de decisão/estado continuam tendo função semântica, sem ornamentação obrigatória.
4. Execute o caminho documentado em ambiente autorizado quando disponível. Confira
   exit codes, outputs e efeitos. Compilar não prova executar; HTTP HEAD não prova
   download GET nem conteúdo utilizável. Declare precisamente os passos não verificados.
5. Confira links, identificadores, versões e assets locais. Renderize Mermaid e
   inspecione a figura no destino/tamanho efetivos; parser sem erro não comprova
   legibilidade. Verifique os critérios aplicáveis da matriz visual, inclusive
   contraste, cortes, ícones e alternativa textual. Sem renderizador, registre a
   lacuna e entregue fontes/alternativas úteis; não declare aprovação visual.
6. Revise a partir do ponto de entrada do leitor: remova setup oculto, contradições,
   repetição e rotas obsoletas. Mantenha figura e texto sincronizados; registre
   proprietário/local de manutenção, versão e gatilho de atualização proporcionais
   ao risco. Uma edição pontual não exige refazer os visuais de toda a documentação.

## Entrega, aceite e manutenção

Entregue documentos, fontes editáveis e assets necessários no destino solicitado.
Registre comandos/ambientes executados, observações, revisão das fontes e critérios
visuais verificados. Diferencie inspeção semântica, checagem estrutural, renderização,
execução e teste com leitores; um não substitui o outro. Use o vocabulário de evidência
do projeto; critério não observado permanece não verificado. Uma lista preenchida
não prova acessibilidade, eficácia ou compreensão por usuários.

Mantenha uma explicação canônica com links em vez de instruções duplicadas. Preserve
registros históricos e identifique a substituição vigente sem reescrever observações
antigas como se fossem atuais. Mudança de contrato, dado, componente, tema ou
renderizador exige revisar os visuais afetados e suas exportações.

A skill não autoriza publicação, deploy, mensagens externas, instalação de dependências
ou alterações de produção. Continue ações locais já autorizadas; use ferramentas ou
especialidades adicionais somente quando disponíveis e necessárias ao problema.
Não envie código ou dados privados para editores/renderizadores públicos por padrão.

Base do método textual: *Software Engineering at Google*, primeira edição,
capítulos 3 e 10 (páginas físicas 77–89 e 213–233). As referências visuais distinguem
requisitos técnicos externos de convenções locais de apresentação; nenhum deles
certifica a maturidade de um produto.
