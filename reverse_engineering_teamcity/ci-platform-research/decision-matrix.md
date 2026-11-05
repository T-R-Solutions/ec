# Matriz de decisões

Campos completos em decision-matrix.json; os fatos permanecem separados das escolhas.

| Funcionalidade | Evidências | Requisitos | Solução | ADRs | Aceitação |
|---|---|---|---|---|---|
| F01 Projetos, configuração e parâmetros | D-PROJECT, D-PARAM, D-AUTH, TC-015 | R02 | Adaptar: projeto simples com revisão imutável; templates adiados. | ADR-005 | A02 |
| F02 Solicitação e validação | TC-013, TC-016 | R01, R02 | Substituir: JSON declarativo validado sem compilação de código no servidor. | ADR-001, ADR-005, ADR-009 | A01, A02 |
| F03 Fila durável | TC-003, TC-004, D-QUEUE | R01, R03 | Adotar o requisito; fila é consulta ao estado SQL, sem cópia autoritativa em memória. | ADR-001, ADR-002, ADR-009 | A01, A02, A03, A06 |
| F04 Compatibilidade e seleção | TC-005, TC-006, TC-007, D-MATCH, D-QUEUE | R04, R05, R16 | Adaptar: FIFO entre jobs elegíveis e slots autenticados; prioridade de agentes descartada inicialmente. | ADR-002, ADR-003, ADR-004, ADR-006, ADR-009 | A04, A05, A07, A17 |
| F05 Registro e transporte | TC-008, TC-009, TC-017, TC-018, D-AGENT | R04, R12 | Substituir por HTTP versionado, credenciais de agente e leases próprios. | ADR-001, ADR-003, ADR-004, ADR-007 | A04, A13 |
| F06 Steps e processos | TC-010, D-STEPS, D-PARAM | R06, R12, R18 | Adaptar: argv explícito, workspace isolado, steps sequenciais e timeout. | ADR-001, ADR-007, ADR-009 | A08, A13 |
| F07 Logs, testes e diagnóstico | D-LOG, D-ART | R07, R13 | Adaptar: chunks numerados; relatórios como artefatos no CORE, parsing em fase posterior. | ADR-003, ADR-008 | A03, A09, A14 |
| F08 Artefatos e finalização | TC-012, D-ART | R08, R09 | Adaptar: manifesto verificado e conclusão idempotente com fencing. | ADR-006, ADR-008 | A10, A11 |
| F09 Cancelamento, retry e perda de agente | TC-011, TC-012, TC-018 | R05, R09, R10, R11 | Substituir por lease, fencing e cancelamento persistente; retry somente sob política explícita. | ADR-002, ADR-003, ADR-004, ADR-006, ADR-008, ADR-009 | A05, A06, A07, A11, A12 |
| F10 DAG e cadeias | D-DAG, TC-005 | R14 | Adaptar: DAG estático com revisão fixada e falha propagada; reúso complexo adiado. | ADR-005 | A15 |
| F11 VCS e triggers | D-VCS, D-PROJECT | R06, R15 | Adaptar: Git com revisão imutável primeiro; webhooks autenticados e polling posteriormente. | ADR-001, ADR-007, ADR-009 | A08, A16 |
| F12 Plugins e APIs | TC-013, TC-014, TC-016 | R19, R20 | Adotar API própria; rejeitar plugins JVM e execução de DSL; extensões externas adiadas. | ADR-005 | A19, A20 |
| F13 Retenção e cache | D-GC, D-CACHE | R13, R17 | Adotar retenção básica; cache adiado até namespace por confiança e integridade. | ADR-008 | A03, A14, A18 |
