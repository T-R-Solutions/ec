# Revisão documental da integração Toolbox/Ec — 2026-11-07

## Objetivo e escopo

Atualizar entradas e guias da aplicação, contratos operacionais de scanners,
SDD e `.gitignore` para refletir o plano de conclusão solicitado, com atuação
somente no Ec. Não implementar scanners, ponte, migração ou preparo nesta revisão.

Os arquivos anteriores desta alteração estão em `previous.zip`. A pesquisa histórica,
relatórios datados anteriores e seus hashes não foram reescritos. Nenhum arquivo
foi editado no projeto Toolbox ou em outro projeto.

## Fontes e método

Leitura dos manifests/configurações e scripts atuais, requisitos/plano/tarefas da
feature scripts-verification, autoridades/workflow/política de verificação Ec e
skill técnica de documentação. A pesquisa oficial do planejamento permanece
referenciada em `sdd/features/scripts-verification/toolbox-completion-plan.md`;
esta atualização não amplia compatibilidade nem declara nova versão suportada.

Inspeção textual e comparação por conteúdo com o backup, sem depender de Git.
Nenhum script de verificação, teste, validador, scanner, Docker, preparo ou download
executado. Nenhum commit criado. Figuras existentes foram preservadas como baseline,
com legendas que distinguem a organização inicial do estado dos guias vigentes;
não houve renderização ou alegação de aprovação visual.

## Alterações

| Superfície | Atualização |
| --- | --- |
| README principal e índice docs | Navegação para contrato atual, plano e convergência; política de acionamento explícita; remoção de indicação de ausência total de aplicação executável no índice |
| READMEs application, Server/backend/frontend/Agent e testes | Estado conforme guias atuais e referências comuns de verificação/scanners; figuras históricas identificadas; sem alegar conclusão de domínios |
| scripts e segurança | Opengrep/OSV usam adapters já existentes; Trivy nativo e Docker são distintos; novos checks continuam adicionais e YARA-X bloqueado |
| Guias de ferramentas, testes, contribuição e verificação | CLI atual escreve volumes externos; não usar lock-base nem comandos delegados sob fronteira Ec; dados/regras/locks e futuro estado local diferenciados |
| Estado/índice SDD e feature scripts-verification | REQ-13 a REQ-18, AC-13 a AC-18, tarefas T019–T024, rastreabilidade e convergência; todos os critérios novos UNVERIFIED |
| `.gitignore` | Ignorar `/.local/toolbox/`, `/.local/tools/` e relatórios raiz de navegador; preservar configurações, locks, fontes, regras, receitas, SDD e evidências oficiais |

T018 permanece como registro de uma retomada anterior, substituída pelo roteiro
T019–T024. AC-12 descreve a etapa intermediária de checks adicionais; AC-16 exige
promoção ao padrão. AC-17 condiciona retirada do gate nativo à paridade Trivy.
D-15 a D-18 e C-01 a C-06 do plano estão vinculados aos critérios atuais.

## Inspeção e limites

A leitura cruzada localizou e corrigiu afirmações de adapters Opengrep/OSV bloqueados,
instrução de alterar a plataforma compartilhada e descrições centrais de ausência
total de código/testes. READMEs de domínio e observações anteriores podem conter
baselines históricas, subordinadas aos guias de implementação pertinentes.
Foram revisados textualmente nomes e destinos dos links introduzidos, IDs e
referências de tarefas; não houve execução de verificador de links ou SDD.

A ponte continua sem redirecionamento de volumes, as novas imagens/bases não foram
preparadas, YARA-X continua bloqueado, novos checks continuam adicionais e Trivy
nativo permanece no padrão. `.gitignore` não altera executor, não remove arquivos
e não tira automaticamente arquivos já rastreados de um índice Git. Histórico/
índice Git não foi usado como prova do estado desta alteração.

Nenhum critério foi promovido a APPROVED por inspeção documental. As futuras
verificações seguem pedido de commit e escolha de execução da suíte completa;
downloads exigem autorização própria. Não renovar hashes de observações antigas
para aparentar atualidade. Retomada funcional começa por T019, dentro do Ec.

Skill utilizada: `.codex/skills/technical-documentation/SKILL.md`, ponte versão 1,
SHA256 `784a09861f93774ce4a3ee43da709e698b7815645c9ee43c27c9e811aa00b166`.
Sem delegação. Registro da contribuição também consta de `plan.md` da feature.
