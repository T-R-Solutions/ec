# Ec — contrato visual da entrada

Reconstrução de 15/11/2026: entrada pública de login, seguida de confirmação da sessão. Shell, navegação, galeria, demonstrações, editor e views operacionais anteriores foram removidos por solicitação. O registro Signal de 11/11 é histórico em docs/validation/frontend-signal-20261111/report.md; não descreve a UI atual.

## Composição e identidade

Formulário estreito em superfície clara, marca Ec central acima do título e ação principal. Referência principal: captura oficial TeamCity https://resources.jetbrains.com/help/img/teamcity/2025.11/spaceauth.png, inspecionada em 15/11/2026, mostrando versão 2021.2 EAP. A composição é adaptada; marca, cores e método de autenticação pertencem ao Ec.

Tokens canônicos preservados em src/shared/ui/tokens.css: shell #243C59, contexto #182535, ação #28679F/#205483, seleção #E8F2FD, texto #263548, sucesso #226B50, atenção #80521D e falha #A53848. Ações, foco e mensagens têm papéis distintos. IBM Plex Sans/Mono continuam famílias preferidas; capturas usam fallback disponível, sem alegação de fonte instalada. Não houve download de fontes.

## Estados e comportamento

Entrada aceita token provisionado pelo administrador, consulta /api/v1/session e limpa campo no envio. Campos/controles HTML nativos têm rótulo, foco e descrição. Estado pendente impede duplicação; recusa/conexão devolvem foco ao campo. Confirmação mostra identidade/validade e permite revogação. Revalida a cada30s e ao retomar foco; expiração conhecida ou401 limpa contexto. Falha de conexão torna a confirmação explicitamente desatualizada. Falha de revogação distingue saída local de confirmação remota ausente. Não existem links de cadastro, recuperação, demonstração ou provedores não implementados.

## Reflow e evidência

Card até 27rem, largura fluida e espaçamento reduzido em viewports estreitos. Mensagens mantêm região reservada; textos longos quebram sem exceder viewport. Foco independente e reduced-motion/forced-colors têm regras próprias. Parâmetros antigos de galeria/demo/tema não habilitam UI retirada.

Capturas e observações atuais: docs/validation/login-rebuild-20261115/report.md. Elas não certificam WCAG integral nem substituem testes de interação e gate. Política de aceite: docs/development/task-verification.md (caminhos desde a raiz).

Fonte visual Ec preservada: docs/design/sources/ec-signal-visual.zip, membro ec-signal-visual/DESIGN.md. Normas de produto e instruções vigentes prevalecem.
