# Plano de adaptação da automação

## Autoridades e análise

Aplicam-se AGENTS.md, docs/constitution.md, sdd/workflow.md,
docs/development/task-verification.md e docs/development/local-state.md.
A arquitetura Server/Agent/frontend e ADR-002 de layout permanecem preservadas.
Complexidade alta pela integração com cancelamento, integridade e compatibilidade.
A raiz encaminhou a coordenação; dois especialistas simultâneos no máximo.

## Decisões e riscos

- D-01 / R-01 / REQ-01: adaptar a apresentação de progress.sh do Ox aos resultados
  Ec; não substituir o supervisor. Progresso por etapas reais e status explícito;
  controles só em TTY compatível; testes de sucesso, erro e cancelamento.
- D-02 / R-02 / REQ-02: conservar local-state.py, streams brutos e recibos existentes.
  Referências apontam para logs retidos; textos livres não são despejados no terminal.
  Testes de bytes, código e retenção previnem perda/injeção na apresentação.
- D-03 / R-03 / REQ-03: reutilizar a configuração canônica existente e acrescentar
  apenas opções com consumidores. Ambiente e descoberta já equivalem ao Ox;
  caches particulares do Ox não se aplicam à política Ec. Validação estrita e docs.

## Métodos selecionados

Primária cli-engineering: contrato humano/máquina, TTY, NO_COLOR e códigos.
Auxiliar tool-output-engineering: projeção segura, sinais de falha e logs brutos.
- `cli-engineering`: ponte `sdd/chain/cli-engineering.md`, bridge_version 1; entrada `.codex/skills/cli-engineering/SKILL.md`; SHA-256 `75ce12875adc3ee4c23910408c87a5475eb8b4d63d0445f23885f1e7391719c4`.
- `tool-output-engineering`: ponte `sdd/chain/tool-output-engineering.md`, bridge_version 1; entrada `.codex/skills/tool-output-engineering/SKILL.md`; SHA-256 `9b308771ab92daecc800e67b4f260e96182d62e6ec8675d74568c485e6bcaf20`.

## Implementação e responsáveis

Implementador: scripts de produção, testes scripts/tests e carregador
 tools/quality/automation_config.py. Orquestrador: scripts/README.md e esta tarefa.
Revisor e validador independentes da implementação; observações integram o registro.
Preservar alterações anteriores por edições pontuais, sem restauração por Git.

## Superfícies e estabilização

Executar `docs` e `scripts`: contratos SDD/documentação, sintaxe, testes da automação,
Ruff e ShellCheck. Não foram alterados contratos ou fontes do produto, portanto
Server, Agent, frontend e scanners completos não são superfícies deste pedido.
Finalizar artefatos, encerrar escritores, executar o runner final e entregar seu
recibo ao validador para `accept-task.sh --task`. Falha demanda correção ou bloqueio
explícito; não ajustar critérios para aparentar aprovação.
