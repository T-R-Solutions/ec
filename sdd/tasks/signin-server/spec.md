# Sign in do Ec Server

Página pública integrada ao frontend existente. REQ-01: cumprir o pedido de acesso inicial com identidade Ec, token existente, conteúdo visual, responsividade e acessibilidade. Não altera autenticação backend, não cria cadastro/SSO/senha nem aceita a jornada integral.

- AC-01 → REQ-01: Entrada pública apresenta sign in sem sessão e preserva destino autenticado, demos e galeria explícitos.
- AC-02 → REQ-01: Acesso valida token real via /session, preserva retorno e não inventa métodos de autenticação.
- AC-03 → REQ-01: Identidade Signal, mensagens e composição visual derivam das referências Ec com conteúdo demonstrativo honesto.
- AC-04 → REQ-01: Estados de acesso, teclado, foco, contraste e reflow permanecem utilizáveis nos temas e viewports suportados.
- AC-05 → REQ-01: Verificação funcional e capturas desktop/mobile inspecionadas sustentam aceite atual.
