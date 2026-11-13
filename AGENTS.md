# Orientações do repositório

## Entrada e autoridades

Antes de alterar, leia o [estado e mapa do projeto](sdd/project.md), as
[autoridades e invariantes](docs/constitution.md), o
[contexto arquitetural](docs/architecture/context.md) e os ADRs pertinentes.
Preserve mudanças existentes; instruções vigentes do usuário e permissões reais
do ambiente prevalecem sobre métodos, templates e registros históricos.

## Trabalho e skills

Siga o [workflow SDD](sdd/workflow.md), que define rota local, rota completa e
retomada. Use o [catálogo e contrato das pontes](sdd/chain/README.md) para
selecionar a skill primária e auxiliares por questões distintas. Leia o
`SKILL.md` efetivo; presença no catálogo não significa execução.

Para trabalho visual, leia o [contrato do frontend](application/server/frontend/DESIGN.md).
O [guia de métodos de frontend](docs/development/frontend-design-skills.md)
encaminha às referências pertinentes. Normas de produto definem requisitos;
skills fornecem métodos.

## Verificação e entrega

A [política de verificação](docs/development/task-verification.md) é a única
fonte do fluxo de scripts, da pergunta após pedido de commit e do alcance da
autorização. Recomendações de execução em skills, referências e SDD se subordinam
a ela. Registre inspeções, resultados e lacunas sem declarar checks não executados
como aprovados.

Somente para commits/tags solicitados, consulte a
[ponte commit-governance](sdd/chain/commit-governance.md), que encaminha ao
procedimento e à convenção de mensagens. PRs descrevem mudança, requisitos/ADRs,
verificações e limitações; telas alteradas incluem evidência visual pertinente.
Não incluir segredos nem publicar por inferência.

## Convenções e estado local

Documentação nova em pt-BR; preserve referências importadas. Código Python novo
usa quatro espaços e `snake_case`; JSON de governança usa dois espaços.
Descubra comandos nos manifests e guias vigentes; não dependa exclusivamente de Git.

Siga a [política de estado local](docs/development/local-state.md) para logs,
temporários, retenção e recuperação. Ela prevalece sobre recomendações de backups
ou snapshots em skills. Não presuma que mudanças sem commit estejam publicadas.
