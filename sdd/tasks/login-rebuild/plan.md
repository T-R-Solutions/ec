# Plano de reconstrução do login

## Autoridades e decisão

AGENTS.md, docs/constitution.md, docs/architecture/context.md, ADR-002/003, DESIGN.md e política de verificação. Rota completa por substituição de consumidores e preservação de sessão; perfil alto provisório mantido. A árvore disponível permite raiz, orquestrador e dois especialistas; escrita disjunta, revisão e validação independentes.

D-01 / R-01: remover UI antiga, preservar API e modelos sem view; não manter shell apenas para testes obsoletos. Testes de API/backend continuam; jornada browser passa a verificar sessão real. A capacidade de autoria/execução via UI fica pendente de reconstrução, sem reduzir requisitos agregados da feature.
D-02 / R-02: token provisionado real, limpo no envio, nunca em storage/URL; sucesso somente após /session. Falha DELETE deve distinguir encerramento local de revogação não confirmada.
D-03 / R-03: composição vertical central da captura oficial TeamCity, paleta Ec preservada. Estados usam área de feedback e controles nativos. Reflow, foco e contraste precisam observação, não apenas CSS.

## Referência

Imagem oficial https://resources.jetbrains.com/help/img/teamcity/2025.11/spaceauth.png, vinculada à documentação https://www.jetbrains.com/help/teamcity/how-to-configure-cicd-for-jetbrains-space.html. Inspecionada em 15/11/2026; imagem mostra 2021.2 EAP, não é alegação da versão atual. Marca acima do título, eixo central, superfície clara e ações agrupadas orientam composição; Ec usa token, sem Space/guest/marca JetBrains.

## Superfícies

Runner final: docs (guias e SDD), scripts (harness Python/guards), frontend (tipos, unidades, build, cobertura), e2e (Playwright e login real Chromium/Firefox). Backend e contratos preservados; e2e inclui suas dependências de compilação/checagem. Sem scanners externos com atualização de bases por inferência. Estabilizar registros e escritores antes do run, executar accept-task com recibo real.

## Métodos lidos

- frontend-form-engineering: primária: estado e reconhecimento de envio; ponte sdd/chain/frontend-form-engineering.md v1; entrada .codex/skills/frontend-form-engineering/SKILL.md; SHA-256 15f0692409322fc1bf119b85b032d554a7fd163297974439ac87842c1887481e.
- design-system: auxiliar: tokens e composição; ponte sdd/chain/design-system.md v1; entrada .codex/skills/design-system/SKILL.md; SHA-256 df68c2652f2b04b04bd67094d977dccc816fb3cfb1815df04ae90ed087c82e80.
- frontend-testing: auxiliar: evidência de interação e capturas; ponte sdd/chain/frontend-testing.md v1; entrada .codex/skills/frontend-testing/SKILL.md; SHA-256 5bdbd988f566bacc18de0b81b6de8baedbcd7ac9ef0de8f05a42fdb9299a4a69.
- vue3-engineering: implementador: bindings e lifecycle; ponte sdd/chain/vue3-engineering.md v1; entrada .codex/skills/vue3-engineering/SKILL.md; SHA-256 4a8a4602db93e0380421e194c865d055795334052c15d7924085edc18c4825f2.
- css-layout-engineering: implementador: reflow e alinhamentos; ponte sdd/chain/css-layout-engineering.md v1; entrada .codex/skills/css-layout-engineering/SKILL.md; SHA-256 44033d98b09a7980a454a54f570a900041e17884a4be505b9163b51e628f8d8e.

D-04 / R-04: porta5173 estava ocupada por servidor alheio; config canônica não possui override. services.frontend.port passa a58140 verificada livre, lida por todos consumidores do harness. Não encerrar processo externo. Revisão independente e E2E exercitam o delta.
