# Campos, variáveis e estado

O índice cataloga 1.202.665 campos em conteúdos de classe distintos e 601.088 entradas de variáveis locais no recorte de corpos analisados. Variáveis locais incluem slots e labels de escopo quando o atributo LocalVariableTable existe; ausência de metadados não é prova de ausência de variáveis.

`data-flow.json` liga métodos selecionados a leituras e escritas de campos por opcode e ordinal. GETSTATIC/GETFIELD indicam leitura; PUTSTATIC/PUTFIELD, escrita. Esse arquivo não calcula origem de valores, aliasing, propagação entre chamadas ou fluxo de dados ponta a ponta.

Estados inspecionados: referências da fila, índices derivados, versão da ordem, flag de registro, semáforo de registro, flag de interrupção e estágio corrente. Ver claims TC-003, TC-008, TC-011 e os registros de campos em `features.json`. O ciclo de vida persistente de todas as entidades permanece parcial.
