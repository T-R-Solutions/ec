# Estratégia de validação

Executado nesta entrega: integridade de evidência reaproveitada, validação referencial dos documentos e
protótipo Rust std de submáquina de leases. Resultados e comandos em findings/validation.json.
O protótipo valida contratos de domínio sob relógio explícito; não valida banco, transporte, subprocessos ou isolamento.

Para implementação: cargo test em transições e propriedades; sequências geradas com proptest quando houver domínio completo.
Integração PostgreSQL real para corrida de polls, cancel/complete/expiry e crash antes/depois de commit.
Agent/process: workspace sintético e processo com descendentes, pipes volumosos e saída não zero.
Distribuído: proxy de falhas para partição, duplicação, atraso e reset; reiniciar agente, controle e banco nos pontos de commit.
Segurança: A13 em host descartável; nenhuma execução hostil na instalação operacional.
loom apenas para estruturas concorrentes em memória customizadas, sem substituir teste transacional.
Não foi iniciado TeamCity nem utilizado banco/credencial reais.
