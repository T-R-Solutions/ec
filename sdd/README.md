# SDD do Ec

Este diretório reúne contexto, workflow, pontes para skills e registros por
feature e tarefas delimitadas. O método adapta o SDD portátil 1.3.0; aceites e autorizações do produto
de origem não foram importados.

| Entrada | Responsabilidade |
| --- | --- |
| [project.md](project.md) | Estado, mapa da implementação e pendências |
| [Constituição](constitution.md) | Apontador para as autoridades do Ec |
| [workflow.md](workflow.md) | Rota local/completa, execução e retomada |
| [chain/README.md](chain/README.md) | Seleção de skills e integração com os artefatos |
| [templates/README.md](templates/README.md) | Artefatos obrigatórios e exemplos sem aceite |
| [evidence.md](evidence.md) | Estados, rastreabilidade e formato de evidência |

## Features

| Feature | Escopo |
| --- | --- |
| [governance-adoption](features/governance-adoption/spec.md) | Catálogo, instalação, adaptação e manutenção de governança |
| [first-functional-journey](features/first-functional-journey/spec.md) | Configuração, publicação e execução no primeiro recorte do produto |
| [scripts-verification](features/scripts-verification/spec.md) | Automação, ferramentas e critérios de verificação |
| [local-state](features/local-state/spec.md) | Retenção, temporários e limites de recuperação |
| [agent-orchestration-proposal](features/agent-orchestration-proposal/spec.md) | Proposta e configuração da coordenação de agentes |
| [workflow-enforcement](features/workflow-enforcement/spec.md) | Ciclo obrigatório e gate local de aceite |

O estado de cada feature pertence ao seu `evidence.json` e à convergência; este
índice não replica aprovações. O [contrato de evidência](evidence.md) documenta
as interfaces do validador; seu acionamento segue a
[política de verificação](../docs/development/task-verification.md).

## Tarefas delimitadas

O [índice de tarefas](tasks/README.md) reúne manutenções com critérios próprios e
relações explícitas às features. Aceitar uma tarefa não aprova uma feature.

[Projeto](../README.md) · [Documentação](../docs/README.md)
