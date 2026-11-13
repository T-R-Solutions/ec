# Espécimes de tipografia, ícones e acabamento

Consulte para troca de fonte, perda de legibilidade, clipping, densidade ou
acabamento de uma família de componentes. Use fontes, pesos, ícones e dimensões
definidos pelo produto. Não deduza disponibilidade de uma família declarada no CSS.

## Fonte observada e fallback

Registre família solicitada, asset/origem disponível, pesos presentes e fonte
realmente usada pelo renderer quando for possível observá-la. Uma captura sem
essa observação pode ser descrita como fallback ou fonte não confirmada; não
atribua diferenças à fonte pretendida sem evidência. Preparar/baixar assets segue
a autorização do projeto, não é consequência automática desta referência.

Use um pequeno corpus do consumidor que exponha ambiguidade e largura:
`0/O`, `1/l/I`, `=> !=`, parênteses, chaves, aspas, diacríticos, nomes longos,
caminhos e identificadores técnicos. Inclua números alinhados quando a tarefa
compara valores. Esses exemplos são pontos de partida, não fixture obrigatória.

Compare o mesmo conteúdo, zoom, viewport, tema e densidade. Observe altura de
linha, quebras, largura de colunas, baseline e cortes de caracteres nos tamanhos
reais dos controles. Reobserve com fallback e texto ampliado; fontes sintéticas
ou pesos ausentes podem alterar medidas. Uma imagem não comprova a qualidade do
nome acessível nem a leitura de um editor com tecnologia assistiva.

## Ícones e acabamento por papel

Confirme origem e conjunto disponível antes de substituir um ícone. Preserve
seu significado, rótulo e geometria; não anuncie uma biblioteca completa a partir
de um subconjunto. Confira alinhamento óptico junto ao texto e espessura nos
tamanhos e escalas usados pelo produto, sem impor uma grade universal.

Depois de resolver perda de informação e ações inacessíveis, revise:

- Baselines de texto/ícone, espaços entre controle e rótulo e alinhamento de dados.
- Bordas funcionais, foco e marcador de seleção: suavizar o chrome não deve
  eliminar a identificação do controle ou de seu estado.
- Hover/pressed sem deslocamento que perturbe código, tabelas ou a posição do alvo.
- Wrappers, cabeçalhos e sombras que ocupam espaço sem orientar a tarefa.
- Densidade compacta reduzindo chrome e espaço antes de reduzir texto essencial.

Cada ajuste deve ter um consumidor e um problema identificáveis. Retenha o menor
conjunto de mudanças sustentado pela comparação; não crie uma rodada obrigatória
de aprovação ou crítica independente para todo refinamento.

Síntese adaptada de ox-typeset-icons e ox-polish, pacote SKILLS-DESIGN-v4.0.0.
A fonte e a identidade pertencem ao contrato visual do produto consumidor.
