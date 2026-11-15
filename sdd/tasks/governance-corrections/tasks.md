# Tarefas e responsabilidade de escrita

- T001 [REQ-01, AC-01, D-01, R-01]: Aceite delimitado; dono gate_contracts. Sem dependências; conclusão exige evidência atual e revisão independente.
- T002 [REQ-02, AC-02, D-02, R-02]: Catálogo e histórico; dono gate_contracts. Depende do contrato inicial T001; conclusão exige evidência atual e revisão independente.
- T003 [REQ-03, AC-03, D-03, R-03]: Inventário de aceite; dono gate_contracts. Depende do contrato inicial T001; conclusão exige evidência atual e revisão independente.
- T004 [REQ-04, AC-04, D-04, R-04]: Workflow e templates; dono coordenador. Depende do contrato inicial T001; conclusão exige evidência atual e revisão independente.
- T005 [REQ-05, AC-05, D-05, R-05]: Revisão semântica README; dono skills_agents. Depende do contrato inicial T001; conclusão exige evidência atual e revisão independente.
- T006 [REQ-06, AC-06, D-06, R-06]: Avaliação das skills; dono skills_agents. Depende do contrato inicial T001; conclusão exige evidência atual e revisão independente.
- T007 [REQ-07, AC-07, D-07, R-07]: Metadados coerentes; dono skills_agents. Depende do contrato inicial T001; conclusão exige evidência atual e revisão independente.
- T008 [REQ-08, AC-08, D-08, R-08]: Capacidade de coordenação; dono skills_agents. Depende do contrato inicial T001; conclusão exige evidência atual e revisão independente.
- T009 [REQ-09, AC-09, D-09, R-09]: Políticas canônicas; dono skills_agents. Depende do contrato inicial T001; conclusão exige evidência atual e revisão independente.
- T010 [REQ-10, AC-10, D-10, R-10]: PostgreSQL vigente; dono skills_agents. Depende do contrato inicial T001; conclusão exige evidência atual e revisão independente.
- T011 [REQ-11, AC-11, D-11, R-11]: Navegação completa; dono coordenador. Depende do contrato inicial T001; conclusão exige evidência atual e revisão independente.

## Arquivos e dependências

- gate_contracts: task_gate/accept-task, validate, automation_config, runtime/inputs, configuração docs:sdd e testes desses contratos. T002–T003 dependem do contrato T001.
- skills_agents: .codex/skills, .codex/config e agents, profile.json, auditor/testes exceto task_gate, pontes das cinco skills. T005–T010 independentes do código, com hashes ao final.
- coordenador: AGENTS se necessário, workflow/evidence/política, READMEs/templates, todos artefatos desta tarefa e notas nas features, codex-agents e skill-policy-history (ownership transferido explicitamente após interrupção segura do escritor). T004/T011 dependem do contrato publicado por gate_contracts.
- revisão independente: leitura dos contratos/implementação e cenários; observações devolvidas, integração pelo coordenador.
- validação independente: confronto dos onze critérios com evidências e recibo real do runner; gate atual obrigatório.

Nenhum especialista escreve artefatos comuns desta tarefa. Correções retornam ao
dono ou são transferidas explicitamente após encerrar escritores. Estabilização
antes do runner final; qualquer entrada alterada exige repetição afetada.

Transferência em 14/11: documentação dos T008/T009 pertence ao coordenador;
configuração/perfis/profile continuam com skills_agents. Raiz pode encaminhar
somente a aplicação protegida do candidato .codex congelado, como ponte da
permissão real do cliente, sem assumir implementação ou coordenação.
