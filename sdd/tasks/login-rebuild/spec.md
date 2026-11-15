# Reconstrução do frontend: login

Pedido de 15/11/2026: substituir interface existente por entrada centrada em login. REQ-01: remover shell, demos e telas antigas; preservar infraestrutura, backend, API e mudanças alheias. REQ-02: usar composição TeamCity e cores DESIGN. REQ-03: demonstrar estados, acessibilidade e acabamento em navegador real.

- AC-01 → REQ-01: A entrada sem sessão apresenta somente o novo login, incluindo parâmetros antigos de demo/galeria.
- AC-02 → REQ-02: Marca, campos, rótulos, botão e mensagens compartilham alinhamentos e escala coerente de espaços e tipografia.
- AC-03 → REQ-02: Composição vertical baseada em referência TeamCity inspecionada e identidade Ec, sem cortes, sobreposições ou espaços acidentais.
- AC-04 → REQ-03: Desktop, tablet, celular e reflow com zoom mantêm conteúdo e mensagens utilizáveis sem rolagem horizontal.
- AC-05 → REQ-03: Controles funcionam por mouse e teclado, com foco, nomes acessíveis e contraste adequado.
- AC-06 → REQ-03: Estados vazio, envio, recusa, falha de conexão, confirmação e saída preservam legibilidade e posição; autenticação usa contrato real.
- AC-07 → REQ-03: Inspeção no navegador e capturas atuais documentam dimensões/estados e correções observadas.
- AC-08 → REQ-03: Verificações pertinentes e gate de aceite aprovam os inputs finais sem reduzir critérios.

A autenticação disponível é token provisionado consultado em GET /api/v1/session. Confirmação de identidade e revogação são o destino mínimo. Não criar usuário/senha, cadastro, recuperação ou SSO sem contrato. As telas operacionais antigas foram retiradas por pedido; requisitos da primeira jornada continuam pendentes e não são aprovados por esta tarefa. Sem commit/publicação/download de dependências.
