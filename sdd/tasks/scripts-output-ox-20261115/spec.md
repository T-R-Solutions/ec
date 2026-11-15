# Configuração e apresentação da automação Ec a partir do Ox

## Contexto e cenários

O pedido é copiar as configurações gerais de scripts do Ox, adaptando-as ao Ec.
A comparação usa `/home/rodrigo/Projetos/ox/scripts/` em leitura apenas. O Ec
já centraliza tarefas, ambiente, descoberta, limites e políticas em
`scripts/config/automation.json`, com retenção privada implementada.
A lacuna principal é a apresentação humana das execuções.

## Requisitos e aceitação

- REQ-01: apresentar fases, atividades, duração, progresso observado, estados e referências de diagnóstico em português, adaptados aos componentes Ec.
- AC-01 → REQ-01: sucesso, falha e cancelamento produzem apresentação honesta; execução incompleta não anuncia 100%; TTY, saída redirecionada e NO_COLOR são respeitados.
- REQ-02: preservar stdout/stderr brutos, classificação, códigos, recibos, limpeza e política canônica de retenção Ec.
- AC-02 → REQ-02: testes de regressão demonstram streams e códigos preservados e logs em .local/logs/AAAA-MM-DD; texto externo não injeta controles no terminal nem expõe saída bruta.
- REQ-03: adaptar as configurações gerais úteis, com uma fonte responsável e documentação dos equivalentes e diferenças entre projetos.
- AC-03 → REQ-03: configuração válida é consumida, valores inválidos são rejeitados e documentação identifica saída, logs, retenção, temporários, ambiente, descoberta e limites reais do Ec.

## Riscos e limites

Progresso mede etapas concluídas, não trabalho interno estimado nem aprovação.
Logs privados e recibos são autoridades; a apresentação não reclassifica resultados.
O Ec mantém seu supervisor, lock e retenção de duas datas, sem importar caches Ox,
flags específicas de aplicação ou mudanças em políticas de scanners.
Mudanças preexistentes no checkout devem ser preservadas. Não há commit, publicação
ou download autorizado por inferência. Scans completos do produto ficam fora deste
pedido; o runner das superfícies afetadas e o gate são obrigatórios.

## Escopo e relações

Relação parcial com scripts-verification: AC-02, AC-25, AC-30, AC-33 e AC-35.
Esta tarefa cobre apresentação e configuração da automação e mantém todas as
pendências agregadas da feature. Não altera runtime do produto, frontend ou schemas.
