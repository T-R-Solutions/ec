# Plano

D-01: separar apresentação pública do shell e reutilizar sessão/token e tokens visuais. R-01: regressão de sessão, demos ou acesso por teclado.

Rota local; frontend/testes sob implementar; coordenador dono deste registro; revisão e validação independentes. Preservar mudanças alheias. Referências: DESIGN.md frontend, normas Ec, pacote visual e capturas de 11/11/2026. Skills efetivas serão registradas com observações.

Superfícies docs, frontend e e2e: documentos SDD, componentes Vue, testes e consumidores browser alterados. e2e traz dependências Server/Agent reais pelo plano canônico. Testes focados, inspeção visual, revisão, estabilização e runner final antecedem gate.

Cenários de T003: vazio, pendente, credencial recusada, indisponibilidade, expiração, submissão duplicada; teclado e foco após entrada/expiração; desktop, mobile e 320 CSS px para reflow equivalente a zoom400%; temas claro e escuro candidato. Evidência deve vincular testes, componentes e CSS observados, além dos documentos.

Superfície scripts acrescentada: expected_tests passou2→3 para terceiro teste Playwright; fixtures de test_native acompanham três resultados preservando rejeições. Coordenador dono somente desse delta, revisão independente e teste focado1PASS. Skills efetivamente lidas pelo implementador: css-layout-engineering, vue3-engineering, frontend-form-engineering, design-system, frontend-testing e visual-capture-evidence.
