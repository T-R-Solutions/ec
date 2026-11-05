# Catálogo funcional

## F01 — Projetos, configuração e parâmetros

**FUNCIONALIDADE:** Projetos organizam configurações; parâmetros compõem settings e ambiente; permissões são concedidas por papéis.

**PROBLEMA RESOLVIDO:** Organizar builds e limitar quem pode alterar ou executar cada projeto.

**CONTRATO FUNCIONAL:** Configuração, parâmetros e identidade → configuração efetiva; alterações são aplicadas por fluxo próprio de settings.

**EVIDÊNCIAS:** D-PROJECT D-PARAM D-AUTH TC-015. Ver evidence/legacy-claims.json e official-sources.json.

**LIMITAÇÕES:** Regras completas de herança, templates e precedência não reconstruídas.

**DECISÃO PARA O NOVO SISTEMA:** Adaptar: projeto simples com revisão imutável; templates adiados.

## F02 — Solicitação e validação

**FUNCIONALIDADE:** Endpoint de fila consulta usuário e chama triggerBuild; compilador DSL converte diagnóstico em erro.

**PROBLEMA RESOLVIDO:** Recusar solicitações inválidas e criar execução identificável.

**CONTRATO FUNCIONAL:** Requisição autenticada → build na fila ou erro; ligação transacional integral ainda desconhecida.

**EVIDÊNCIAS:** TC-013 TC-016. Ver evidence/legacy-claims.json e official-sources.json.

**LIMITAÇÕES:** Não se demonstrou atomicidade entre REST, configuração e fila.

**DECISÃO PARA O NOVO SISTEMA:** Substituir: JSON declarativo validado sem compilação de código no servidor.

## F03 — Fila durável

**FUNCIONALIDADE:** Há índices de fila em memória e inserção parametrizada em build_queue.

**PROBLEMA RESOLVIDO:** Manter pedidos pendentes até haver capacidade.

**CONTRATO FUNCIONAL:** Build pendente → posição e persistência; integridade sob crash não foi observada.

**EVIDÊNCIAS:** TC-003 TC-004 D-QUEUE. Ver evidence/legacy-claims.json e official-sources.json.

**LIMITAÇÕES:** Isolamento e recuperação TeamCity desconhecidos.

**DECISÃO PARA O NOVO SISTEMA:** Adotar o requisito; fila é consulta ao estado SQL, sem cópia autoritativa em memória.

## F04 — Compatibilidade e seleção

**FUNCIONALIDADE:** Distribuidor processa fila; filtro pode ordenar agentes por prioridade limitada; requisitos restringem agentes.

**PROBLEMA RESOLVIDO:** Executar onde existem recursos e ferramentas necessários.

**CONTRATO FUNCIONAL:** Build + agentes → candidatos e distribuição ou espera.

**EVIDÊNCIAS:** TC-005 TC-006 TC-007 D-MATCH D-QUEUE. Ver evidence/legacy-claims.json e official-sources.json.

**LIMITAÇÕES:** Justiça global, reserva e otimização global não demonstradas.

**DECISÃO PARA O NOVO SISTEMA:** Adaptar: FIFO entre jobs elegíveis e slots autenticados; prioridade de agentes descartada inicialmente.

## F05 — Registro e transporte

**FUNCIONALIDADE:** Registro evita concorrência local; agente consulta comandos e lê identificadores da resposta.

**PROBLEMA RESOLVIDO:** Conectar executores ao orquestrador e transferir trabalho.

**CONTRATO FUNCIONAL:** Registro/poll → identidade e comando; protocolos alternativos podem ser tentados.

**EVIDÊNCIAS:** TC-008 TC-009 TC-017 TC-018 D-AGENT. Ver evidence/legacy-claims.json e official-sources.json.

**LIMITAÇÕES:** Autenticação interna, serialização e leases não recuperados.

**DECISÃO PARA O NOVO SISTEMA:** Substituir por HTTP versionado, credenciais de agente e leases próprios.

## F06 — Steps e processos

**FUNCIONALIDADE:** Estágios consultam interrupção e resultado; scripts podem executar ferramentas instaladas no agente.

**PROBLEMA RESOLVIDO:** Executar comandos reproduzíveis e propagar falha.

**CONTRATO FUNCIONAL:** Coleção de estágios → status parcial/final; execução pode parar após falha.

**EVIDÊNCIAS:** TC-010 D-STEPS D-PARAM. Ver evidence/legacy-claims.json e official-sources.json.

**LIMITAÇÕES:** Montagem completa de estágios e subprocessos não reconstruída.

**DECISÃO PARA O NOVO SISTEMA:** Adaptar: argv explícito, workspace isolado, steps sequenciais e timeout.

## F07 — Logs, testes e diagnóstico

**FUNCIONALIDADE:** Log inclui eventos e saída de processos; relatórios podem ser artefatos.

**PROBLEMA RESOLVIDO:** Explicar resultado e disponibilizar evidência ao usuário.

**CONTRATO FUNCIONAL:** Saída/eventos → log e arquivos consultáveis.

**EVIDÊNCIAS:** D-LOG D-ART. Ver evidence/legacy-claims.json e official-sources.json.

**LIMITAÇÕES:** Parsing de testes, consistência do stream e limites internos UNKNOWN.

**DECISÃO PARA O NOVO SISTEMA:** Adaptar: chunks numerados; relatórios como artefatos no CORE, parsing em fase posterior.

## F08 — Artefatos e finalização

**FUNCIONALIDADE:** Finalização inspecionada aguarda publicação de artefatos e informa término ou detached state.

**PROBLEMA RESOLVIDO:** Vincular resultado aos produtos do build.

**CONTRATO FUNCIONAL:** Contexto final e arquivos → publicação e notificação.

**EVIDÊNCIAS:** TC-012 D-ART. Ver evidence/legacy-claims.json e official-sources.json.

**LIMITAÇÕES:** Confirmação remota e commit no servidor não comprovados.

**DECISÃO PARA O NOVO SISTEMA:** Adaptar: manifesto verificado e conclusão idempotente com fencing.

## F09 — Cancelamento, retry e perda de agente

**FUNCIONALIDADE:** Interrupção marca estado e alcança estágio atual; poller captura falhas e continua.

**PROBLEMA RESOLVIDO:** Interromper trabalho e tolerar falhas de comunicação.

**CONTRATO FUNCIONAL:** Sinal de interrupção → encaminhamento; interrupção não comprova morte de descendentes.

**EVIDÊNCIAS:** TC-011 TC-012 TC-018. Ver evidence/legacy-claims.json e official-sources.json.

**LIMITAÇÕES:** Reagendamento, exatamente uma vez e recuperação sob partição UNKNOWN.

**DECISÃO PARA O NOVO SISTEMA:** Substituir por lease, fencing e cancelamento persistente; retry somente sob política explícita.

## F10 — DAG e cadeias

**FUNCIONALIDADE:** Dependências snapshot relacionam builds e sincronizam revisão segundo a documentação.

**PROBLEMA RESOLVIDO:** Coordenar trabalho paralelo sobre entradas consistentes.

**CONTRATO FUNCIONAL:** Dependências → cadeia de builds com política de pré-requisitos.

**EVIDÊNCIAS:** D-DAG TC-005. Ver evidence/legacy-claims.json e official-sources.json.

**LIMITAÇÕES:** Otimizações internas de reúso e transações não reconstruídas.

**DECISÃO PARA O NOVO SISTEMA:** Adaptar: DAG estático com revisão fixada e falha propagada; reúso complexo adiado.

## F11 — VCS e triggers

**FUNCIONALIDADE:** Detecção por polling considera commit hooks e mudanças pendentes compatíveis com checkout rules.

**PROBLEMA RESOLVIDO:** Iniciar builds sobre alterações relevantes.

**CONTRATO FUNCIONAL:** Mudança elegível → pedido de build; checkout fornece entradas.

**EVIDÊNCIAS:** D-VCS D-PROJECT. Ver evidence/legacy-claims.json e official-sources.json.

**LIMITAÇÕES:** Drivers VCS, retries e validação de credenciais não inspecionados.

**DECISÃO PARA O NOVO SISTEMA:** Adaptar: Git com revisão imutável primeiro; webhooks autenticados e polling posteriormente.

## F12 — Plugins e APIs

**FUNCIONALIDADE:** REST oferece submissão; carregamento de plugin cria contexto e publica eventos de carga.

**PROBLEMA RESOLVIDO:** Integrar sistemas e acrescentar capacidades.

**CONTRATO FUNCIONAL:** Plugin → contexto carregado ou evento de falha.

**EVIDÊNCIAS:** TC-013 TC-014 TC-016. Ver evidence/legacy-claims.json e official-sources.json.

**LIMITAÇÕES:** ABI, segurança e isolamento de plugins não comprovados.

**DECISÃO PARA O NOVO SISTEMA:** Adotar API própria; rejeitar plugins JVM e execução de DSL; extensões externas adiadas.

## F13 — Retenção e cache

**FUNCIONALIDADE:** Limpeza remove dados antigos; cache reutiliza arquivos publicados entre builds.

**PROBLEMA RESOLVIDO:** Conter armazenamento e evitar trabalho repetido.

**CONTRATO FUNCIONAL:** Política/cache key → retenção ou reaproveitamento.

**EVIDÊNCIAS:** D-GC D-CACHE. Ver evidence/legacy-claims.json e official-sources.json.

**LIMITAÇÕES:** Atomicidade do GC e proteção contra cache poisoning não verificadas.

**DECISÃO PARA O NOVO SISTEMA:** Adotar retenção básica; cache adiado até namespace por confiança e integridade.
