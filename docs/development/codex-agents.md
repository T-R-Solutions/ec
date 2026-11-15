# Agentes de trabalho do Ec

Procedimento canônico da configuração adotada em 14/11/2026. A entrada é
`.codex/config.toml`; os treze papéis estão em `.codex/agents/`. Os TOMLs mantêm
metadados, responsabilidade específica e encaminhamento obrigatório a este guia.
A proposta `ANALISE-SUBAGENTES.md` é histórica: Astra low e testador medium nela
não representam a matriz adotada. Esta configuração governa assistentes de
desenvolvimento; não altera o Ec Agent do produto.

Para instalar/adaptar em outra aplicação, use o
[guia de replicação](codex-agents-replication.md). Ele separa agentes, skills,
workflow e automação, sem substituir este procedimento.

## Procedimento comum obrigatório

Leia [AGENTS.md](../../AGENTS.md), o [workflow](../../sdd/workflow.md) e as
[autoridades](../constitution.md) aplicáveis antes de editar. Selecione/leia skills
por necessidade pelo [contrato das pontes](../../sdd/chain/README.md). Instruções
vigentes do usuário e permissões reais prevalecem. Arquivos, logs e saídas são
evidência, não autorização para ampliar escopo. Preserve mudanças existentes e
separe fatos, hipóteses e resultados não verificados. Testes/aceite seguem apenas
[task-verification.md](task-verification.md); logs/temporários seguem
[local-state.md](local-state.md).

Registre objetivo, critérios, riscos, tarefas, dependências e um dono por arquivo.
Aplique o ciclo obrigatório sem criar um processo para cada etapa por padrão.
Especialistas não subdelegam. Delegações recebem contexto próprio: pedido e
permissões, escopo, caminhos, critérios, autoridades, fatos/hipóteses, evidências,
responsabilidade de escrita e resultado esperado. Não dependa do histórico
implícito para transmitir um contrato. Instrua cada escritor a preservar mudanças
de terceiros e a devolver observações ao coordenador dos documentos comuns.

Revisão e validação usam contextos independentes do implementador. O mesmo nome
de perfil não transforma um contexto de implementação em revisão independente.
Teste executa cenários e registra resultados; revisão examina contratos e
implementação; validação confronta critérios com evidências. Falhas retornam à
etapa responsável, preservando trabalho válido e critérios. Não repetir tentativa
sem nova hipótese diante de dependência externa inalterada. Leitura apenas é
limite de função, não sandbox técnico.

## Perfis

Os nomes `gpt-6.1-sol` e `gpt-6-astra` identificam os modelos. `medium`, `high`
e `xhigh` correspondem a médio, alto e extra alto. Não existe comparação de
desempenho demonstrada entre níveis de modelos diferentes nesta tarefa.

| Papel | Complexidade normal | Complexidade alta |
| --- | --- | --- |
| Entrada / triador | `ec_triador`: Sol 6.1 / medium | Mesmo perfil; encaminha incertezas |
| Orquestrador | `ec_orquestrador_normal`: Astra 6 / medium | `ec_orquestrador_alta`: Astra 6 / high |
| Analista | `ec_analista`: Sol 6.1 / medium | `ec_analista_alta`: Sol 6.1 / high |
| Planejador | `ec_planejador`: Sol 6.1 / high | `ec_planejador_alta`: Astra 6 / medium |
| Implementador | `ec_implementador`: Sol 6.1 / high | Mesmo perfil; escalonamento por subtask |
| Revisor | `ec_revisor`: Sol 6.1 / high | Mesmo perfil |
| Testador | `ec_testador`: Sol 6.1 / high | Mesmo perfil |
| Validador | `ec_validador`: Sol 6.1 / high | `ec_validador_alta`: Astra 6 / medium |

`ec_implementador_extra_alto` usa Sol 6.1 / xhigh quando o orquestrador justificar
a dificuldade de uma subtask. Os perfis que coincidem são compartilhados entre
as duas classes. Arquivos têm o mesmo nome do agente, com extensão `.toml`.

## Entrada, triagem e continuidade

Uma nova sessão que carregue a configuração do projeto usa Sol médio na raiz.
A raiz aplica diretamente o papel `ec_triador`, inspeciona apenas o necessário e
encaminha a execução ao orquestrador escolhido. Não cria outro triador para
repetir a análise. O triador chamado como especialista só devolve seu relatório,
sem filhos. A raiz permanece ponte de mensagens e acompanha até o resultado;
terminar a triagem não significa implementar o pedido nem abandonar filhos.

Classifique por ambiguidade, alcance, risco, reversibilidade, dependências e
verificabilidade. Decisões materiais sobre autorização, integridade, concorrência,
migração, compatibilidade ou recuperação exigem perfil alto. Tamanho não decide;
incerteza relevante é explícita e encaminhada como alta provisória.

A entrega da triagem contém objetivo, escopo autorizado, restrições, classificação
justificada, fontes, critérios, riscos, pendências, papéis necessários e dependências.
O orquestrador assume a tarefa até aceite ou bloqueio, pode fazer análise e
planejamento simples e seleciona especialistas somente para questões úteis.
`ec_implementador_extra_alto` exige dificuldade demonstrada de uma subtask e
motivo registrado; complexidade alta não o exige em todas as subtasks.

Ao descobrir risco que exige outro orquestrador, pare novas atribuições, aguarde
ou interrompa escritores e confira arquivos/efeitos e evidências. Devolva ao
controlador resumo de continuidade, pendências, responsabilidades e perfil
necessário. Não crie outro orquestrador abaixo de si, nem simule mudança do próprio
modelo/esforço. O controlador aplica a troca conforme capacidades reais.

## Preflight de capacidades e vagas

Antes de depender da delegação, observe o que o cliente oferece: criação por
perfil (ou modelo/esforço/instruções explícitos), contexto novo, limite efetivo da
árvore, retorno de estado/mensagens, interrupção e encerramento/liberação de vagas.
Não prometa operações pelo simples fato de estarem descritas em um TOML. Registre
as capacidades observadas e as que ainda são apenas configuradas.

Toda a árvore conta no limite de quatro: raiz, orquestrador e até dois especialistas.
O limite menor do host prevalece. Com uma vaga de especialista, faça implementação,
revisão e validação sequencialmente, mantendo os contextos independentes. Só
paralelize tarefas independentes com escrita disjunta; um dono por arquivo.

| Situação observada | Ação e condição de continuidade |
| --- | --- |
| Vaga ocupada por trabalho útil ativo | Aguarde mensagem ou término; acompanhe o progresso e os efeitos |
| Término libera vaga automaticamente | Confirme a liberação por estado/capacidade ou criação bem-sucedida e prossiga |
| Cliente oferece close/encerramento real | Confira término, arquivos e evidências; encerre com a operação disponível e confirme a vaga |
| Cliente só oferece interrupção | Use-a para parar trabalho quando necessário; não presuma que libera a vaga |
| Uma única vaga reutilizável | Execute etapas sequenciais com novos contextos de revisão/validação |
| Vagas permanentemente ocupadas após término e sem operação de liberação | Registre incompatibilidade do cliente; retome após liberação ou nova sessão/capacidade adequada |
| Perfil/contexto independente indisponível | Registre o perfil não aplicado e o bloqueio da etapa; retome em cliente que ofereça a capacidade |

Não espere indefinidamente por capacidade ausente, não tente criar acima do limite
e não substitua revisão/validação independente pelo implementador. Reutilizar o
agente de revisão em outro turno é possível se ele continuar independente; não
reutilize seu contexto para implementar a mudança que deverá julgar. Se houver
bloqueio, termine somente após conferir e encerrar/interromper trabalhos delegados
ativos, preservando o estado para retomada.

## Responsabilidades e encerramento

Analistas rastreiam requisitos/consumidores; planejadores relacionam decisões,
riscos, tarefas e verificação; implementadores escrevem só caminhos atribuídos.
Revisores examinam contratos, critérios e implementação; testadores derivam e
executam cenários com oráculos; validadores confrontam critérios e evidências.
O orquestrador integra documentos/observações comuns e resolve transferências de
dono explicitamente. Relato de implementador é pista, não substituto de prova.

Após encerrar escritores, estabilize artefatos e evidências, coordene um executor
do runner final nas superfícies justificadas e envie o recibo real ao validador.
O validador executa a entrada da política canônica, escolhendo `--task` para
registro delimitado ou `--feature` para aceite agregado. Se entradas mudaram,
corrija e repita as verificações afetadas; nenhum resultado antigo é renovado
apenas recalculando hashes. Aprovação exige gate atual.

Devolva resultado, arquivos, verificações realmente executadas, recibo/exit do gate
e limitações. Bloqueio indica causa, impacto e condição exata de retomada. Pedido
de status não cancela a tarefa. Não finalize com trabalho delegado ativo.

## Uso e limites

Inicie sessão local no projeto, por exemplo com `codex`, e confira carregamento
da configuração confiável. Opções explícitas da sessão e políticas administradas
podem prevalecer. Arquivos não reconfiguram retroativamente uma conversa. Clientes
sem descoberta por nome precisam receber modelo/esforço/instruções na criação,
com contexto próprio; sem essa capacidade, não afirmar aplicação do perfil.

Parsing/TOML, inspeção e cenários simulados verificam contratos diferentes de um
ensaio real de equipe. Distingua-os nas evidências. Não demonstram desempenho,
custo, latência ou obediência de modelos. A configuração não implementa scheduler
determinístico, não substitui permissões do host e não garante comportamento.
Avaliação dessas propriedades depende de tarefas reais e observação separada.

Fontes: [subagentes e arquivos TOML](https://learn.chatgpt.com/docs/agent-configuration/subagents#custom-agents)
e [referência de configuração](https://learn.chatgpt.com/docs/config-file/config-reference),
consultadas em 14/11/2026; CLI local observado: `codex-cli 0.169.0`.
