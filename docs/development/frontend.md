# Interface web — entrada de login

O frontend Vue 3/TypeScript/Pinia em `application/server/frontend/` foi reconstruído em 15/11/2026 para começar pela tela de login. Shell, demos, galeria, configuração e execuções da interface anterior foram retirados por pedido. As APIs e a lógica de domínio preservadas continuam disponíveis para futura reconstrução; nenhuma tela operacional é anunciada como pronta.

## Executar

Com dependências npm do lock já preparadas e Server/PostgreSQL configurados conforme [publicação](publication.md):

```bash
npm --prefix application/server/frontend run dev -- --port 5173 --strictPort
```

Abra http://127.0.0.1:5173. O proxy Vite encaminha `/api` ao endereço configurado em `scripts/config/automation.json`. Use o token provisionado pela CLI do Server; não coloque credenciais de banco ou Agent no navegador. Instalação de dependências exige autorização própria quando necessária.

## Contrato de acesso

O token opaco é consultado em GET `/api/v1/session`, fica apenas na memória da aba e é limpo do campo no envio. Não há usuário/senha, cadastro, recuperação ou SSO implementados. Confirmação mostra identidade e validade retornadas pelo Server. Recarregar a aba exige nova entrada; “Revogar e sair” envia DELETE da sessão. Uma falha distingue descarte local de revogação remota não confirmada. Revalidação ocorre a cada30s e ao retomar foco, sem sobreposição; erro de transporte exibe confirmação desatualizada e401 ou expiração conhecida remove o contexto. Autorização continua sendo decisão do Server.

## Verificação

Comandos locais: `npm run typecheck`, `npm test`, `npm run build` no frontend. O runner canônico e o gate são definidos na [política de verificação](task-verification.md). Playwright testa API simulada; `application/integration/tests/e2e/frontend_browser.py` testa sessão com Server/PostgreSQL reais via browsers Chromium/Firefox preparados na Toolbox Docker. As jornadas anteriores de editor/execuções são históricas, pois as telas foram retiradas; harnesses de API/backend continuam próprios.

Consulte [contrato visual](../../application/server/frontend/DESIGN.md), [observações desta reconstrução](../validation/login-rebuild-20261115/report.md) e [tarefa SDD](../../sdd/tasks/login-rebuild/spec.md). Aceite desta tarefa não aprova a primeira jornada funcional agregada. Capturas comprovam apresentação observada; leitor de tela e certificação WCAG integral não foram executados.
