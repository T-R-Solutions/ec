# Migração visual de tokens e estados

Consulte ao consolidar aliases/overrides, mudar papéis de cor ou transferir um
contrato visual para componentes reais. O objetivo é localizar qual consumidor
muda e por quê. Não é necessário criar um gerador, novo formato de tokens ou
pacote de componentes para uma correção local.

## Do valor declarado ao consumidor

Antes de substituir um literal, registre para os alvos afetados:

| Origem | Resolução | Significado | Consumidor |
| --- | --- | --- | --- |
| Arquivo/seletor e valor | Alias, tema, variante e regra vencedora na cascata | Ação, seleção, foco, estado, categoria, sintaxe ou série | Componente/estado e tela que o utiliza |

Valores iguais não provam papéis iguais. Um destaque de sintaxe e uma falha podem
usar a mesma cor hoje e precisar evoluir separadamente. Preserve aliases enquanto
houver consumidores identificados; remova cada override somente após localizar
quem depende de sua precedência. Uma regra global pode ser uma base legítima:
restrinja-a quando estiver alterando controles com contratos diferentes.

Diferencie migração de nomes de uma mudança visual. Para cada papel alterado,
registre valor anterior/proposto, motivo e consumidores, incluindo hover,
seleção, foco e indisponibilidade quando pertinentes. Contraste de amostras
opacas não representa superfícies com transparência ou sobreposição. Alterar uma
categoria não deve mudar a leitura de estado operacional; foco permanece
identificável quando o item também está selecionado.

Ao investigar excesso de cor, compare a composição neutra com o acréscimo
localizado que sustenta uma distinção necessária. Use os mesmos dados, tema,
viewport e estado. Não acrescente séries ou estados para exibir uma paleta.
Decida pela identificação da tarefa, leitura e contraste observados; registre
preferência estética como preferência. Temas alternativos exigem papéis próprios,
não inversão automática de cores.

## Estados incertos fazem parte do contrato visual

Para componentes que apresentam trabalho assíncrono, monte apenas as linhas que
existem no contrato: `estado → origem do dado → texto/símbolo → ação disponível
→ conteúdo conservado → evidência necessária`. Exemplos para orientar a seleção:

| Condição conhecida | Distinção que precisa continuar visível |
| --- | --- |
| Solicitação enviada, confirmação ausente | Pedido pendente ou incerto; não sucesso |
| Última consulta falhou, há snapshot anterior | Origem e desatualização; ausência não equivale a zero |
| Parte do trabalho confirmou e parte não | Resultado parcial com escopo; não conclusão global |
| Cancelamento solicitado | Intenção distinta de término; nenhuma garantia implícita de rollback |
| Resultado inferido | Hipótese com origem distinta de dado desconhecido |

O componente apresenta os fatos recebidos; aparência não implementa retries,
controle de concorrência ou recuperação. Se a correção exigir novas transições,
trate essa mudança no contrato de comportamento do consumidor, dentro do escopo
autorizado. Não invente êxito ou dados para completar estados de uma galeria.

## Rastreabilidade até a tela

Mantenha uma cadeia curta no registro do projeto: papel → componente/variante →
estado → tela consumidora → observação e limite. Um protótipo pode documentar
intenção; a observação precisa identificar o renderer realmente usado. Migre um
consumidor representativo antes de ampliar para todos, quando isso reduzir risco.
Não declare equivalência de interação por semelhança de pixels.

Se medir cobertura da migração, defina a unidade e o universo: consumidores,
valores ou ocorrências. Registre exclusões e exceções; um percentual isolado não
comprova consistência nem determina um limiar universal de aceitação.

Síntese adaptada de ox-extract-tokens, ox-chromatic-refinement, ox-harden e
ox-native-handoff, pacote SKILLS-DESIGN-v4.0.0. Somente métodos reutilizáveis;
sem dependência dessas skills, arquivos externos ou plataforma nativa.
