# Especificação — correções de governança

Pedido autorizado em 14/11/2026: executar os onze itens do plano posterior à auditoria.
Rota completa, complexidade alta: altera contrato de aceite, coexistência,
inventário e coordenação multiautor. Escopo: governança/SDD/runner, perfis/skills
ativos, documentação e testes pertinentes; sem produto, commit ou publicação.

- REQ-01: Aceite delimitado.
- AC-01 → REQ-01: Gate aceita tarefa com critérios próprios e rejeita aceite agregado de feature pendente; relações, escopo vazio/inválido e revisão ausente são rejeitados.
- REQ-02: Catálogo e histórico.
- AC-02 → REQ-02: Catálogo verifica schemas, IDs e referências sem exigir logs históricos; aceite estrito rejeita log expirado ou input obsoleto.
- REQ-03: Inventário de aceite.
- AC-03 → REQ-03: Adicionar, remover, renomear ou alterar perfil/config após run invalida aceite; links/escape são rejeitados; seleção de scanners permanece igual.
- REQ-04: Workflow e templates.
- AC-04 → REQ-04: Entrada Ec exige ciclo, seis artefatos e runner/gate; uso portátil/manual separado; exemplos válidos permanecem UNVERIFIED.
- REQ-05: Revisão semântica README.
- AC-05 → REQ-05: Método permite conferência semântica pelo agente com fontes e reserva humano para decisão ou autorização ainda pendente.
- REQ-06: Avaliação das skills.
- AC-06 → REQ-06: Cinco skills distinguem checks pertinentes à entrega de avaliações de seleção/eficácia, sem impor ensaio LLM à tarefa cotidiana.
- REQ-07: Metadados coerentes.
- AC-07 → REQ-07: Identidade e descrição profile/SKILL coincidem; negativos com hashes recalculados rejeitam divergência; UI representa o mesmo escopo.
- REQ-08: Capacidade de coordenação.
- AC-08 → REQ-08: Procedimento cobre preflight, limite real, sequenciamento, liberação observada e bloqueio por falta permanente de contexto/vaga sem espera infinita.
- REQ-09: Políticas canônicas.
- AC-09 → REQ-09: Treze perfis preservam modelos/esforços e encaminham ao procedimento comum; adaptações remetem a histórico substituído preservado.
- REQ-10: PostgreSQL vigente.
- AC-10 → REQ-10: Skills de banco/scheduler reconhecem decisão vigente PostgreSQL, implementação parcial e SQLite proposto, em coerência com constituição/projeto.
- REQ-11: Navegação completa.
- AC-11 → REQ-11: Índice SDD aponta seis features e tarefas, com links locais válidos e sem duplicar estados de aceite.

Os onze critérios são desta manutenção; não aprovam as features relacionadas.
Preservar os ACs e pendências de governance-adoption, workflow-enforcement e
agent-orchestration-proposal. O gate verifica vínculos, não completude semântica
do recorte; a revisão independente confronta o escopo com o pedido.

Riscos: autoenfraquecimento do aceite, rehash de histórico, expansão silenciosa
de scanners, perda de mudanças preexistentes e capacidades do cliente ausentes.
Evidências de fixture não demonstram eficácia comportamental de LLM nem CI protegida.
