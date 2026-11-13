# Jornadas de navegador

A jornada real está em `application/integration/tests/e2e/frontend_browser.py`.
Chromium e Firefox são executados pelo Playwright da Toolbox no Docker; não há
uso de navegador/driver instalado no host. Serviços, portas e seleção pertencem
a `scripts/config/automation.json`. O runner prepara dependências e registra
limpeza/diagnósticos. A variante escolhida precisa estar preparada na Toolbox.

[Operação e autorização](../../../../../scripts/README.md).
