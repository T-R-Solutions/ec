---
name: commit-governance
description: Review and create explicitly requested Ec commits or version tags, preserving scope, unrelated changes and current validation. Publishing requires its own authorization.
---

## Acionamento de scripts no Ec

Neste checkout, esta regra prevalece sobre recomendações de execução desta skill e
suas referências. Scripts de verificação só podem ser acionados após pedido explícito
de commit. Pergunte exatamente: “Deseja executar o script automate.sh (que roda todos os outros scripts)?”
Ofereça somente `sim` e `não`, sem resposta automática; aguarde a escolha.
Com `não`, prossiga normalmente com os commits solicitados, sem executar scripts.
Com `sim`, execute da raiz `bash scripts/automate.sh run`, sem filtros, e só crie
os commits após todos os scripts da suíte terminarem sem erros. Falha, interrupção,
ferramenta ausente ou execução incompleta bloqueiam os commits nessa opção.
Não execute scripts avulsos nem os acione automaticamente durante implementação,
revisão ou encerramento. Inspeção manual continua permitida; checks não executados
permanecem explicitamente não verificados. Detalhes: `docs/development/task-verification.md`
(caminho relativo à raiz Ec). Em outros projetos, siga a política do consumidor.

# Commit Governance — Ec

## Escopo e convenção

Use para commits/tags explicitamente solicitados. Implementar, revisar ou documentar não implica fazer commit. Descubra a raiz real com `git rev-parse --show-toplevel`; se Git estiver indisponível, relate o limite sem inicializar um repositório por conta própria.

Conventional Commits é a convenção adotada para trabalho futuro nesta integração, não inferida do histórico indisponível. Use `type(scope): imperative subject` em inglês; tipos: feat, fix, docs, style, refactor, test, perf, build, ci, chore, revert. Escopos opcionais: server, agent, frontend, yaml, scheduler, skills, sdd, docs. Exemplo: `docs(sdd): clarify evidence boundaries`.

## Procedimento

1. Inspecione status, diff staged/unstaged e arquivos não rastreados na raiz verificada. Preserve trabalho alheio e staged fora do escopo.
2. Relacione arquivos ao pedido. Revise segredos, gerados, conflitos e exclusões reais; não suponha `application/` ou workspace Cargo.
3. Após o pedido de commit, aplique a pergunta e a escolha da seção “Acionamento de scripts no Ec”. Com `não`, prossiga sem scripts; com `sim`, só prossiga após sucesso de toda a suíte. Confira evidências SDD por inspeção; hash atualizado sem nova observação não renova aceite.
4. Agrupe por mudança coerente, podendo incluir backend, frontend e docs juntos. Stage caminhos explícitos e revise o índice antes do commit.
5. Registre hash, assunto, arquivos e estado residual. Não contorne hooks nem faça amend, reset destrutivo ou force-push sem pedido correspondente.
6. Para tag, confirme versão/alvo e convenção vigente. Tag local, push e release são ações distintas; não publicar tags extras. Verifique o objeto criado e não substitua tags existentes.

## Limites

Não instalar hooks, mudar configurações Git ou aplicar labels remotos como efeito de um commit local. Inspecione labels reais quando houver pedido de triagem remota. Não transportar a lista de labels, crates, versões ou autorizações históricas do Ox para o Ec. Relate verificações ausentes e não apresente um commit como aceite técnico do produto.
