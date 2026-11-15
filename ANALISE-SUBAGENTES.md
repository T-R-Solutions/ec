# Proposta de agentes para o Ec

Registro da proposta inicial. A adoção posterior, com valores aprovados pelo
usuário nesta revisão, está no [guia de configuração](docs/development/codex-agents.md).
As afirmações de ausência de instalação abaixo descrevem o momento desta proposta.

Análise revisada em 14/11/2026: Astra, perfis e controle de esforço. **Proposta documental; nenhum agente ou configuração foi instalado.**
Os papéis e esforços abaixo são recomendações para este projeto, sem avaliação
comparativa de modelos. O fluxo mantém as autoridades e o aceite do Ec.

| Base | O que estabelece |
| --- | --- |
| [Subagentes — OpenAI](https://learn.chatgpt.com/docs/agent-configuration/subagents) | Papéis nativos `default`, `worker`, `explorer`; agentes personalizados em TOML. |
| [Modelos — OpenAI](https://learn.chatgpt.com/docs/models) | Astra para trabalhos mais exigentes; Sol para execução recorrente com menor custo; Luna para tarefas delimitadas. |
| [Configuração — OpenAI](https://learn.chatgpt.com/docs/config-file/config-reference) | Configuração da sessão, modelo e esforço; configurações do projeto requerem confiança. |
| [Estado do Ec](sdd/project.md) e [arquitetura](docs/architecture/context.md) | Server/Agent Rust, frontend Vue/TypeScript e integração entre componentes. |
| [Contrato público](docs/decisions/ADR-003-public-journey-contract.md) | `application/contracts/` é compartilhado; mudanças precisam considerar todos os consumidores. |
| [Workflow](sdd/workflow.md), [verificação](docs/development/task-verification.md) e [evidência](sdd/evidence.md) | Etapas, testes, vínculos de conteúdo e critérios de conclusão. |

## Papéis e esforço recomendado

`ec_*` são nomes propostos. A referência ao papel nativo indica afinidade de função;
não existe um campo `extends` nesses exemplos. O orquestrador é a sessão principal.

| Agente / etapa | Afinidade | Perfil padrão / modelo / esforço | Como atua no Ec | Entrega exigida | Limite de atuação |
| --- | --- | --- | --- | --- | --- |
| **Orquestrador** — todo o ciclo | `default` | Sessão principal: `gpt-6-astra` / **low** | Classifica risco, distribui arquivos e critérios, resolve dependências e conflitos, integra resultados e conduz o encerramento. | Escopo, delegações, decisão sobre achados e resultado do gate. | Um responsável por integração e artefatos comuns; nunca concluir somente pelo parecer de um agente. |
| **ec_analista** — análise | `explorer` | `gpt-6.1-sol` / **medium** | Lê autoridades, rastreia contratos e consumidores, identifica estado real, lacunas e critérios observáveis. | Mapa de impacto e proposta de requisitos com caminhos e evidências. | Leitura e relatório; não decide novos requisitos de produto sem coordenação. |
| **ec_planejador** — planejamento | `default` | `gpt-6.1-sol` / **high** | Compara alternativas, ordena tarefas, define donos de arquivos, riscos, oráculos e superfícies de teste. | Proposta de `plan.md`/`tasks.md`, dependências e condição de conclusão. | Orquestrador integra o plano; preservar decisões e critérios existentes. |
| **ec_implementador** — implementação | `worker` | `gpt-6.1-sol` / **high** | Implementa uma tarefa delimitada, preserva mudanças alheias, documenta decisões e executa verificações focadas. | Mudança revisável, arquivos alterados, resultados e pendências. | Escrita apenas no escopo atribuído; não editar critérios para acomodar falhas. |
| **ec_revisor** — revisão | `default` | `gpt-6.1-sol` / **high** | Examina a implementação contra requisitos, autorização, invariantes, erros e consumidores. | Achados reproduzíveis, gravidade, arquivos/linhas e critérios afetados. | Contexto de revisão separado do implementador; relata sem corrigir por conta própria. |
| **ec_testador** — teste | `worker` | `gpt-6.1-sol` / **medium** | Define cenários normais e rejeitados, escreve testes autorizados e executa comandos reais da política. | Logs, comandos, exits, versões observadas e lacunas de cobertura. | Não executa a suíte final enquanto houver escritores ativos; não enfraquece oráculos para obter sucesso. |
| **ec_validador** — validação | `default` | `gpt-6.1-sol` / **high** | Confere requisito → mudança → evidência, adequação dos testes, freshness e recibo; executa `accept-task.sh`. | Parecer por critério e resultado observado do gate. | Não altera implementação nem requisitos durante a validação; devolve falhas ao orquestrador. |

Astra no orquestrador é a recomendação revisada para manter decisões entre
componentes. Sol permanece na execução delimitada. A recomendação oficial de
começar Astra em `low` não equivale a Sol `low`: esforço é uma configuração
interna a cada modelo. Isso não comprova superioridade em toda tarefa do Ec.

## Quem define modelo e esforço

| Alvo | Quem configura | Como aplicar | Limite |
| --- | --- | --- | --- |
| Orquestrador | Usuário ou controlador externo da sessão | Seletor da interface, `/model` no CLI ou configuração para iniciar a sessão. | Nesta sessão, o agente principal não possui ferramenta para mudar o próprio esforço. |
| Novo subagente | Orquestrador, quando autorizado e suportado pelo host | Selecionar um perfil previamente configurado ou parâmetros explícitos de criação. | Aqui, overrides exigem solicitação do usuário ou instrução aplicável; a ferramenta aceita modelo/esforço explícitos com contexto inicial próprio (`fork_turns="none"`). |
| Subagente em execução | Controlador externo, se houver suporte | Para esta proposta, o orquestrador encerra/interrompe o trabalho anterior e inicia outro perfil com um resumo de continuidade. | Não presumir alteração de esforço no agente existente; ele não muda seu próprio parâmetro. |
| Arquivo TOML editado | Usuário ou agente autorizado a editar configuração | Confirmar carregamento pelo cliente antes da próxima criação/sessão. | Editar arquivo não demonstra mudança no turno em execução. |

Pedir “pense mais” pode orientar o trabalho, mas não comprova alteração de
`model_reasoning_effort`. A proposta usa seleção explícita na criação.
[Configuração de subagentes](https://learn.chatgpt.com/docs/agent-configuration/subagents#choosing-models-and-reasoning)
e [controle da sessão](https://learn.chatgpt.com/docs/models).

## Perfis para complexidade e custo

| Perfil adicional | Modelo / esforço fixo | Critério para o orquestrador selecionar | Continuidade |
| --- | --- | --- | --- |
| `ec_planejador_complexo` | `gpt-6-astra` / `low` | Decisão material em contratos, autorização, concorrência, persistência ou recuperação. | Recebe mapa de impacto, alternativas e requisitos; devolve plano para integração. |
| `ec_revisor_critico` | `gpt-6-astra` / `low` | Mudança atravessa fronteiras de segurança, leases/fencing ou compatibilidade; pareceres divergem. | Recebe critérios e fontes, além dos achados anteriores identificados como hipóteses. |
| `ec_validador_critico` | `gpt-6-astra` / `low` | Evidências contraditórias ou dúvida sobre a adequação do oráculo. | Investiga a dúvida; preserva critérios e requisito de aprovação pelo gate. |
| `ec_testador_complexo` | `gpt-6.1-sol` / `high` | Teste intermitente, falha difícil de reproduzir ou oráculo complexo. | Recebe comandos, logs e hipóteses já descartadas; evita repetir investigação válida. |
| `ec_analista_inventario` | `gpt-6-luna` / `high` | Inventário ou busca delimitada, com resultado verificável e baixo grau de decisão. | Entrega referências ao analista/orquestrador; não substitui julgamento dos contratos críticos. |

Selecionar esses perfis conforme o problema, sem acionar todos em toda tarefa.
Se Astra `low` for insuficiente, o orquestrador informa a necessidade ao usuário
ou ao controlador externo. Um perfil adicional com esforço maior só deve ser
adotado com suporte e autorização confirmados; não há autoelevação implícita.

## Especialização da implementação

Usar o mesmo perfil `ec_implementador` com tarefas específicas inicialmente;
criar perfis adicionais só se o trabalho repetido justificar instruções próprias.

| Recorte atribuído | Arquivos / contratos | Métodos a selecionar conforme a questão |
| --- | --- | --- |
| Backend | `application/server/backend/`; PostgreSQL e fronteiras HTTP | `rust`, `database-engineering`, `http-api-contracts` e skill Ec pertinente. |
| Agent do produto | `application/agents/`; execução, sessão, leases e recuperação | `rust`, `cicd-execution-runtime`, `ec-agent-control-protocol`. O Ec Agent é software do produto; não é este subagente de IA. |
| Frontend | `application/server/frontend/`; Vue, estado, formulários e contrato visual | `vue3-engineering`, `frontend-state-and-cache`, `frontend-testing`; ler `DESIGN.md` para trabalho visual. |
| Automação / governança | `scripts/`, `tools/`, SDD | `software-testing`, `autonomous-verification-engineering`, documentação conforme o escopo. |

Skills são métodos selecionados e lidos por necessidade; a tabela não manda
carregar todas. O contrato compartilhado tem um único dono de edição por tarefa.

## Coordenação e ciclos

| Momento | Ação do orquestrador | Paralelismo e retorno |
| --- | --- | --- |
| Entrada | Define objetivo, critérios, fontes e escopo autorizado. | Pergunta explicativa não exige abrir todos os agentes. |
| Análise → plano | Recebe mapa de impacto, resolve lacunas materiais e integra o plano. | Investigações independentes podem ocorrer juntas; o plano definitivo depende dos achados relevantes. |
| Plano → implementação | Distribui tarefas com IDs, caminhos, dependências e condição de término. | Até três subagentes nesta sessão. Backend/frontend podem avançar juntos após definição do contrato. |
| Revisão e testes | Congela a mudança observada e distribui revisões; testa o comportamento. | Revisão de leitura pode ser paralela. Escrita de testes usa arquivos separados; suíte final tem um único executor. |
| Troca de perfil | Decide se a questão exige outro modelo/esforço e confirma autorização. | Interrompe ou aguarda o agente anterior; transfere objetivo, critérios, arquivos, evidências e pendências. Um único escritor continua responsável. |
| Falha | Classifica defeito de implementação, teste incorreto, requisito ambíguo ou dependência indisponível. | Corrige na etapa responsável e reexamina evidências afetadas. Sem repetição inútil diante de bloqueio externo inalterado. |
| Aceite | Estabiliza documentos/evidências, aguarda fim das edições, solicita run final e validação. | Os dados do run são passados ao gate por argumento; não editar entradas depois para registrar aprovação. |
| Tarefa pequena | Assume análise e plano e delega apenas a revisão quando útil. | As etapas continuam exigidas; sete papéis não significam sete processos em toda tarefa. |

O gate atual tem `analysis`, `planning`, `implementation`, `review`, `validation`;
teste e validação alimentam a última etapa. Múltiplos agentes concordando não
comprovam qualidade do oráculo. O gate local não impede bypass pelo host ou por
um escritor que controla seus arquivos.

## Configuração proposta

Os arquivos abaixo são exemplos para adoção posterior. Não substituir uma
configuração existente integralmente. Os limites efetivos do host prevalecem.

`.codex/config.toml` — modelo/esforço da sessão principal e concorrência:

```toml
model = "gpt-6-astra"
model_reasoning_effort = "low"

[agents]
enabled = true
max_concurrent_threads_per_session = 3
default_subagent_model = "gpt-6.1-sol"
default_subagent_reasoning_effort = "medium"
```

Um arquivo `.codex/agents/<name>.toml` por subagente; `name`, `description` e
`developer_instructions` são obrigatórios. Exemplo completo de
`.codex/agents/ec_revisor.toml`:

```toml
name = "ec_revisor"
description = "Revisa a implementação do Ec contra contratos e critérios."
model = "gpt-6.1-sol"
model_reasoning_effort = "high"
developer_instructions = """
Siga AGENTS.md e as autoridades aplicáveis ao escopo recebido.
Revise requisitos, invariantes, consumidores e cenários rejeitados.
Retorne achados com evidências, arquivos, linhas e critérios afetados.
Não edite arquivos; não aprove testes que não foram executados.
Encaminhe ao orquestrador as divergências e lacunas de evidência.
"""
```

Para configurar os demais, usar o mesmo formato com os valores abaixo; o modelo
segue a tabela principal. As instruções comuns são: seguir `AGENTS.md`, preservar
trabalho existente e devolver evidências e limitações ao orquestrador.

| Arquivo / `name` | `model_reasoning_effort` | `description` | Conteúdo específico de `developer_instructions` |
| --- | --- | --- | --- |
| `ec_analista.toml` / `ec_analista` | `medium` | Analisa impacto e contratos do Ec. | Leia as fontes e consumidores; separe fatos e hipóteses; proponha critérios e lacunas; não edite arquivos. |
| `ec_planejador.toml` / `ec_planejador` | `high` | Planeja mudanças e verificação do Ec. | Use a análise; compare alternativas; devolva tarefas com IDs, donos, dependências, riscos e oráculos; não implemente. |
| `ec_implementador.toml` / `ec_implementador` | `high` | Implementa tarefas delimitadas do Ec. | Edite somente os caminhos atribuídos; preserve contratos e alterações alheias; teste conforme a política; retorne diff e resultados. |
| `ec_testador.toml` / `ec_testador` | `medium` | Testa comportamento e rejeições do Ec. | Derive casos dos critérios; edite apenas testes atribuídos; registre exits e logs; coordene com o orquestrador o run sem escritores. |
| `ec_validador.toml` / `ec_validador` | `high` | Valida evidências e aceite do Ec. | Confira critérios, adequação dos oráculos e hashes; execute o gate com recibo real; não altere entradas; informe rejeição e retomada. |

Modelo/esforço explícitos no arquivo do agente prevalecem sobre os padrões.
Permissões e MCP vêm da sessão principal; uma instrução de leitura não cria
isolamento de escrita. [Contrato oficial](https://learn.chatgpt.com/docs/agent-configuration/subagents#custom-agents).

Os perfis adicionais usam o mesmo formato de arquivo. O nome do arquivo acompanha
`name`; copiar as instruções do papel correspondente e acrescentar o foco abaixo.

| Arquivo / `name` | `model` | `model_reasoning_effort` | `description` e foco adicional das instruções |
| --- | --- | --- | --- |
| `ec_planejador_complexo.toml` / `ec_planejador_complexo` | `gpt-6-astra` | `low` | Planeja mudanças críticas; explicitar invariantes, alternativas, transições e recuperação. |
| `ec_revisor_critico.toml` / `ec_revisor_critico` | `gpt-6-astra` | `low` | Revisa fronteiras críticas; investigar hipóteses com cenários de falha e evidência. |
| `ec_validador_critico.toml` / `ec_validador_critico` | `gpt-6-astra` | `low` | Valida evidências ambíguas; exigir oráculo adequado e gate atual, sem alterar requisitos. |
| `ec_testador_complexo.toml` / `ec_testador_complexo` | `gpt-6.1-sol` | `high` | Diagnostica testes complexos; reproduzir, discriminar causas e preservar logs. |
| `ec_analista_inventario.toml` / `ec_analista_inventario` | `gpt-6-luna` | `high` | Faz inventário delimitado; retornar caminhos e ocorrências, sem decidir políticas. |

Exemplo completo de `.codex/agents/ec_planejador_complexo.toml`:

```toml
name = "ec_planejador_complexo"
description = "Planeja mudanças críticas de contratos e recuperação no Ec."
model = "gpt-6-astra"
model_reasoning_effort = "low"
developer_instructions = """
Siga AGENTS.md e as autoridades aplicáveis ao escopo recebido.
Use requisitos e mapa de impacto para comparar alternativas.
Explicite invariantes, compatibilidade, recuperação e cenários rejeitados.
Devolva tarefas com IDs, donos de arquivos, dependências e oráculos.
Não implemente nem reduza critérios; reporte lacunas ao orquestrador.
"""
```

A seleção do perfil altera o trabalho delegado, não a configuração do agente
que já estava executando. Os valores no TOML do perfil têm precedência para
modelo/esforço; não tentar transformá-lo em outro perfil apenas pelo prompt.
A configuração efetivamente carregada e os limites do host devem ser conferidos
na adoção. Perfis TOML documentados para clientes locais não demonstram que esta
ferramenta de delegação da sessão os descobre automaticamente.

Para uma futura adoção, pedir: “Use os agentes propostos para esta tarefa;
delegue partes independentes, respeite os donos de arquivos, aguarde os resultados
necessários e conclua somente após o gate. Use o planejador complexo quando
houver decisão material sobre contratos, concorrência ou recuperação”. A mera existência de TOML não
implementa uma máquina de estados nem força esse encadeamento.

Manutenção: revisar esta proposta quando mudarem o contrato oficial de agentes,
os modelos disponíveis, a arquitetura ou a política de verificação do Ec.
