# Visão funcional e técnica do Ec

Ec é uma plataforma CI/CD em implementação. O [estado do projeto](../../sdd/project.md)
concentra capacidades implementadas, pendências e guias operacionais; esta página
organiza a visão de produto sem repetir cronologias de aceite. Requisitos vêm das
[normas de produto](../EC-REGRAS-DE-DESIGN-E-PRODUTO.md) e das
[autoridades](../constitution.md). Pesquisa TeamCity é referência histórica.

## Públicos e tarefas

| Público | Tarefa |
| --- | --- |
| Desenvolvedor | Configurar pipeline, validar, acompanhar execução e investigar saída |
| Administrador de projeto | Gerir hierarquia, repositórios, parâmetros e políticas |
| Operador | Investigar fila, agentes, tentativas, cancelamento e recuperação |
| Administrador global | Gerir identidade, integrações, plugins e saúde |
| Aprovador/auditor | Avaliar revisão, artefato, destino, autorização e histórico |

Esses contextos não são papéis RBAC implementados. A autorização efetiva pertence
ao backend, por recurso e escopo.

## Visão de produto e implementação

| Área | Responsabilidade do produto | Referência do incremento e limites |
| --- | --- | --- |
| Configuração | YAML canônico, projeções, revisões e publicação | [Publicação](../development/publication.md) |
| Projetos e pipelines | Hierarquia, parâmetros, templates e DAG | [Primeira jornada](../../sdd/features/first-functional-journey/spec.md) |
| Execuções e infraestrutura | Fila, elegibilidade, reservas e Agents | [Orquestração](../development/orchestration.md) |
| Execução local | Checkout, processo, heartbeat e recuperação | [Agent](../development/agent.md) |
| Resultados | Logs, testes, artefatos e retenção | [Agent/saída](../development/agent.md) |
| Autoria e operação web | Visual/Formulário/YAML e confirmações Server | [Frontend](../development/frontend.md) |
| Pessoas, entregas, extensões e administração | Gestão de acesso, promoção, plugins e operação | [Estado](../../sdd/project.md); demonstrações não são backend |

## Jornada e autoridades

Configurar YAML → validar → revisar/publicar → submeter revisão imutável →
reservar tentativa → executar no Agent → persistir saída → consultar resultado.
Promoções e aprovações pertencem à expansão prevista, sem implementação presumida.

YAML versionado é a configuração desejada; PostgreSQL mantém revisões e estado
operacional. Sessão, fila, leases, logs e artefatos não são configuração YAML.
Recibos resolvem respostas incertas, sem prometer efeitos externos exatamente uma
vez. Publicada, efetiva, rascunho e revisão de execução são conceitos separados.

## Estrutura

Há dois tipos de monólito Rust, Server e Agent, e um frontend Vue/TypeScript/Pinia.
O [mapa físico](application-layout.md) é a referência para pastas e proprietários.
O [contrato público v1](../../application/contracts/README.md) compartilha campos e
limites sem importar implementação privada; não é novo serviço ou armazenamento.
HTTP versionado/PostgreSQL são vigentes; gRPC/Protobuf e SQLite continuam propostas.

## Evolução e aceite

As fases A–E das normas orientam fundamentos, núcleo, gestão, expansão e escala.
Não são etapas concluídas. O [aceite da primeira jornada](../../sdd/features/first-functional-journey/evidence.json)
precisa de evidência por critério; testes de automação aprovados não promovem
segredos, isolamento, capacidade ou funcionalidades demonstrativas a disponíveis.
Registros datados em docs/validation preservam observações nos seus inputs.
