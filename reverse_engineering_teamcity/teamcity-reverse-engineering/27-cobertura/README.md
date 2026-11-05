# Cobertura e denominadores

`index-metrics.json` conta ocorrências e conteúdos de classe distintos por SHA-256. Métodos e campos são contados por conteúdo de classe distinto: a mesma declaração pode aparecer em diferentes versões de bytecode e será contada em cada versão. Corpos indexados são um recorte por namespace; não equivalem a métodos revisados.

`metrics.json` acrescenta o denominador do recorte desassemblado, chamadas resolvidas apenas dentro dele, funcionalidades documentadas e cenários dinâmicos 0/14. Números de recuperação semântica permanecem `null`, pois desassemblar ou emitir Java não estabelece recuperação integral/parcial do comportamento.

Não há percentual geral de compreensão. Lacunas estão em `../unknowns.json`; cada subsistema permanece parcial até satisfazer os critérios do prompt. Os relatórios devem ser atualizados quando novo material confirmar ou contradizer um achado.
