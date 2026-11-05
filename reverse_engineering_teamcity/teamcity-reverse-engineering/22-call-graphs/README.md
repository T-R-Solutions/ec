# Chamadas e controle

O grafo seletivo em `../call-graph.json` contém 28.907 call sites. Destes, 11.194 encontram declaração exata no conjunto selecionado; 17.713 não a encontram nesse conjunto. Isso não mede resolução no universo inteiro nem prova dispatch virtual.

Chamadas são classificadas como virtual, special, static, interface ou invokedynamic a partir do opcode. Bootstrap e seus argumentos são preservados no invokedynamic. A ausência de uma chamada direta não exclui reflection, callbacks ou RPC.

`control-flow.json` guarda labels, saltos, switches e regiões de handlers por método. As coordenadas são ordinais ASM; não se trata de CFG normalizado em blocos básicos. Os corpos completos indexados contêm 1.531.761 call sites, incluindo 51.459 invokedynamic; o recorte seletivo é menor e serve à revisão.
