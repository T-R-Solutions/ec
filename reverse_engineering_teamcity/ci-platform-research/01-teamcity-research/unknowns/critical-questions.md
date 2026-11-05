# Incertezas aceitas e gates

As lacunas legadas não bloqueiam o projeto independente. Não são declaradas resolvidas.
Não é necessário recuperar beans, scheduler exato, protocolo privado, todas as variáveis ou 14 cenários TeamCity.

Bloqueiam release do novo sistema, mas não esta especificação: isolamento efetivo no host escolhido;
teste de reserva concorrente no PostgreSQL; fencing em todas as escritas de resultado/log/artefato;
cleanup de descendentes; backup/restauração coordenados de metadados e objetos.
Responsável futuro: implementação, validada pelos cenários A04–A12 e gates do plano.

Hipóteses de produto adotadas: instalação própria Linux; uma organização com projetos;
código confiável no primeiro piloto; revisão Git explícita; um slot por agente no CORE;
controle em um processo e PostgreSQL. Não são propriedades descobertas do TeamCity.
Código hostil requer o gate de isolamento antes de habilitação, não apenas Rust ou container padrão.
