# Validação da estrutura documental da aplicação

Data: 2026-11-03. Resultado: APPROVED para a entrega documental e a reobservação de governança. Runtime Ec permanece UNVERIFIED; não há código, manifests, testes de produto ou aplicação executável.

## Escopo, método e resultado

A rota local documental materializou 14 crates planejados (quatro domínios Agent, oito backend e dois pontos de composição), nove domínios frontend, app/shared e diretórios de testes. Cada domínio tem README, src e tests; diretórios sem código têm .gitkeep.

A árvore completa está no [mapa arquitetural](../../architecture/application-layout.md); as escolhas e alternativas estão na [ADR-002](../../decisions/ADR-002-application-layout.md). O [manifesto](manifest.json) identifica todos os arquivos preparados e seus hashes. O [procedimento](verify.py), os [resultados](checks.json) e os logs registram esta execução.

| Critério desta entrega | Estado | Evidência e limite |
| --- | --- | --- |
| Estrutura materializada, src/tests por domínio, sem manifests fictícios | APPROVED | Inspeção de árvore e inventário real; todos os módulos têm arquivos de preservação |
| Navegação, referências e conteúdo preparado | APPROVED | Links locais dos componentes e auditoria documental passam; hashes dos arquivos preparados conferidos |
| Responsabilidades, dependências e política de testes | APPROVED | Revisão contra pedido, contexto e duas normas; grafo permitido sem dependência reversa de composição; frontend usa interfaces públicas |
| Verificações existentes | APPROVED | Auditor, instalação, estrutura SDD, preflight e 24 testes de governança com exit 0 |
| Produto, segurança, capacidade e acessibilidade em runtime | UNVERIFIED | Não implementados nesta etapa |
| Renderização visual e pesquisa com leitores | UNVERIFIED | Houve inspeção do Markdown e links, sem ensaio de renderização/usabilidade |

A separação mantém os dois monólitos. PostgreSQL/HTTP e YAML canônico conservam seu status; gRPC/Protobuf/SQLite local permanecem propostas. Pinia vem do pedido vigente. Não foram herdados toolchain, licença, dependências ou domínios Ox.

Cenários de teste descritos nos READMEs são trabalho futuro. Testes de governança permanecem em tools/governance/tests; a única adaptação de código inclui agents/server/tests nas fixtures para resolver seus novos links. Não foram adicionados testes de produto nem testes que apenas reproduzam a documentação.

## Reobservação da governança existente

A auditoria e o gate de evidências atuais passaram antes das alterações. As versões anteriores dos oito arquivos acompanhados, incluindo evidence.json, estão em [previous.zip](previous.zip). A comparação pós-edição conferiu os 1.293 inputs únicos da observação anterior: somente entradas documentais e a fixture autorizada mudaram; o conjunto exato está em checks.json.

Reexaminamos os seis critérios de governance-adoption:
- AC-01/AC-05: catálogo, origens, 67 skills/594 arquivos e baseline verificados novamente por auditor e preflight.
- AC-02: revisão das novas entradas contra as fontes preserva autoridades e distingue estrutura de implementação e proposta de decisão.
- AC-03: links, pontes, metadados e estrutura foram revalidados; evidence.json aponta para esta nova observação.
- AC-04: os 24 controles existentes passaram em fixtures que incluem os novos diretórios.
- AC-06: o histórico de limpeza foi conservado e seus inputs não alterados fora do escopo. Os gates atuais foram reexecutados; a remoção histórica não foi repetida nem apresentada como evento desta etapa.

A observação anterior não recebeu hashes novos para aparentar execução atual. Foi arquivada antes da edição; novos registros de evidência vinculam a revisão e os testes desta etapa aos arquivos atuais. O gate final é registrado separadamente em [gates.json](gates.json), depois de estabilizar os inputs. Seu resultado valida vínculos e estados, não runtime ou adequação semântica por si só.

## Skills efetivamente utilizadas

| Caminho | SHA-256 observado | Contribuição |
| --- | --- | --- |
| .codex/skills/readme-lifecycle-engineering/SKILL.md | 4a68d48bfa52160ab3cacbe7f04da1d7faadd6c76cde126617cbd945969473c9 | Entradas, navegação e distinção de estado |
| .codex/skills/software-architecture/SKILL.md | 739571c2d2d28a4bd8ad389cc30ed59e599f4c8d65068f6d7e86ee0094dcefba | Domínios, alternativas e dependências |

Não houve delegação, instalação de skills, commit ou publicação. technical-documentation não foi necessária como especialidade adicional: a separação de entradas e documentos canônicos foi coberta pela skill primária.

## Documentos criados ou atualizados

- [README.md](../../../README.md)
- [agents/README.md](../../../agents/README.md)
- [agents/crates/ec-agent-app/README.md](../../../agents/crates/ec-agent-app/README.md)
- [agents/crates/ec-agent-control/README.md](../../../agents/crates/ec-agent-control/README.md)
- [agents/crates/ec-agent-execution/README.md](../../../agents/crates/ec-agent-execution/README.md)
- [agents/crates/ec-agent-reporting/README.md](../../../agents/crates/ec-agent-reporting/README.md)
- [agents/crates/ec-agent-workspace/README.md](../../../agents/crates/ec-agent-workspace/README.md)
- [docs/architecture/application-layout.md](../../../docs/architecture/application-layout.md)
- [docs/architecture/context.md](../../../docs/architecture/context.md)
- [docs/decisions/ADR-002-application-layout.md](../../../docs/decisions/ADR-002-application-layout.md)
- [docs/development/testing.md](../../../docs/development/testing.md)
- [sdd/README.md](../../../sdd/README.md)
- [sdd/features/governance-adoption/converge.md](../../../sdd/features/governance-adoption/converge.md)
- [sdd/features/governance-adoption/tasks.md](../../../sdd/features/governance-adoption/tasks.md)
- [sdd/project.md](../../../sdd/project.md)
- [server/README.md](../../../server/README.md)
- [server/backend/README.md](../../../server/backend/README.md)
- [server/backend/crates/ec-server-access/README.md](../../../server/backend/crates/ec-server-access/README.md)
- [server/backend/crates/ec-server-app/README.md](../../../server/backend/crates/ec-server-app/README.md)
- [server/backend/crates/ec-server-configuration/README.md](../../../server/backend/crates/ec-server-configuration/README.md)
- [server/backend/crates/ec-server-delivery/README.md](../../../server/backend/crates/ec-server-delivery/README.md)
- [server/backend/crates/ec-server-extensions/README.md](../../../server/backend/crates/ec-server-extensions/README.md)
- [server/backend/crates/ec-server-operations/README.md](../../../server/backend/crates/ec-server-operations/README.md)
- [server/backend/crates/ec-server-orchestration/README.md](../../../server/backend/crates/ec-server-orchestration/README.md)
- [server/backend/crates/ec-server-projects/README.md](../../../server/backend/crates/ec-server-projects/README.md)
- [server/backend/crates/ec-server-results/README.md](../../../server/backend/crates/ec-server-results/README.md)
- [server/frontend/README.md](../../../server/frontend/README.md)
- [server/frontend/src/app/README.md](../../../server/frontend/src/app/README.md)
- [server/frontend/src/domains/access/README.md](../../../server/frontend/src/domains/access/README.md)
- [server/frontend/src/domains/administration/README.md](../../../server/frontend/src/domains/administration/README.md)
- [server/frontend/src/domains/configuration/README.md](../../../server/frontend/src/domains/configuration/README.md)
- [server/frontend/src/domains/delivery/README.md](../../../server/frontend/src/domains/delivery/README.md)
- [server/frontend/src/domains/extensions/README.md](../../../server/frontend/src/domains/extensions/README.md)
- [server/frontend/src/domains/infrastructure/README.md](../../../server/frontend/src/domains/infrastructure/README.md)
- [server/frontend/src/domains/pipelines/README.md](../../../server/frontend/src/domains/pipelines/README.md)
- [server/frontend/src/domains/projects/README.md](../../../server/frontend/src/domains/projects/README.md)
- [server/frontend/src/domains/runs/README.md](../../../server/frontend/src/domains/runs/README.md)
- [server/frontend/src/shared/README.md](../../../server/frontend/src/shared/README.md)
- [server/frontend/tests/e2e/README.md](../../../server/frontend/tests/e2e/README.md)
- [server/frontend/tests/integration/README.md](../../../server/frontend/tests/integration/README.md)
- [tests/README.md](../../../tests/README.md)
- [tests/e2e/README.md](../../../tests/e2e/README.md)
- [tests/integration/README.md](../../../tests/integration/README.md)

## Pendências e manutenção

Fixar toolchains, versões, manifests, runners e descoberta de testes; especificar schemas, APIs, transações, contratos de revisão/tentativa/lease e recuperação; implementar e demonstrar autorização, isolamento e acessibilidade. Os READMEs não autorizam execução de workloads ou plugins sem esses contratos e verificações.

Ao alterar limites de domínio, atualizar ADR, mapa e README correspondente. Ao criar código e manifests, documentar comandos somente após executá-los e atualizar a classificação de maturidade. Mudanças de inputs invalidam esta observação conforme [evidence.md](../../../sdd/evidence.md).
