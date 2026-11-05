# Símbolos e representações

`symbols.sqlite` na raiz da entrega contém 412.168 conteúdos de classe e 3.816.259 declarações de métodos; `locations` liga cada conteúdo aos membros de arquivos da distribuição. `symbols.json` descreve o esquema. O catálogo não escolhe uma implementação efetivamente carregada quando há classes duplicadas.

`focus-index.json` liga 124 conteúdos selecionados à classe extraída, hash do JAR, saída `javap` e fonte Java reconstruída por CFR. Há 3.639 declarações de métodos nessas classes desassembladas; essa contagem não significa 3.639 métodos compreendidos. O conjunto inclui algumas dependências selecionadas por nomes comuns, como `Server`.

`subsystem-catalog.json` classifica candidatos por padrão de nome, com sobreposição. API pública, implementação interna e biblioteca de terceiros devem ser distinguidas consultando artefato, flags, hierarquia e corpo. Nomes e descriptors são observações; responsabilidades exigem evidência adicional.

Annotations Kotlin Metadata aparecem em 102.736 conteúdos distintos de classe. Não houve recuperação do código Kotlin original. As representações Java do CFR podem perder propriedades, coroutines, inlining, nomes de variáveis e detalhes de semântica.
