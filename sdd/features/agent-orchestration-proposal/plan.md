# Plano — proposta documental

- D-01 / R-01 / REQ-01: rota local; tabelas para distinguir responsabilidades e configuração. Reutilizar documentação oficial consultada em 14/11/2026 e autoridades locais; identificar recomendação de esforço sem alegar benchmark.

Primária aplicada: technical-documentation, ponte versão 1, entrada
`.codex/skills/technical-documentation/SKILL.md`, lida nesta tarefa. Método: fontes,
clareza de status e verificação de caminhos e exemplos. openai-docs já lida na
conversa orienta a consulta oficial. Sem delegação.

Verificação: leitura semântica, parsing TOML e links locais, runner docs e gate.
Superfície docs suficiente: somente documentação e registros de evidência novos;
sem alteração de scripts, produto ou configuração ativa.

- D-02 / R-02 / REQ-02: selecionar perfis antes da criação e transferir contexto ao substituir um agente. Diferenciar documentação dos clientes locais e ferramentas desta sessão; evitar prometer autoelevação ou descoberta automática de TOML. Astra low segue orientação oficial; distribuição de papéis continua proposta sem benchmark.

## Adoção — configuração aprovada na conversa

- D-03 / R-03 / REQ-03: usar TOMLs nativos do Codex, sem framework adicional. A entrada Sol médio faz triagem; orquestradores delegados usam Astra médio/alto. Perfis idênticos são compartilhados e diferenças têm nomes explícitos. Não alterar a configuração global nem permissões. A escrita em .codex depende da permissão real do ambiente.
- D-04 / R-04 / REQ-04: a raiz mantém a ponte com o usuário até a conclusão; só o orquestrador distribui a execução. Com raiz e orquestrador presentes, restam duas vagas. Encerrar escritores antes de trocar perfil ou executar o runner; revisão e validação usam contexto independente.

Método primário: prompt, ponte versão 1, entrada efetiva
`.codex/skills/prompt/SKILL.md`, com referência evaluation-and-trust.
Auxiliar openai-docs para contrato do cliente, documentação oficial atual e
inspeção do CLI 0.169.0. Nenhuma delegação de implementação nesta tarefa.

Rota local: decisões de perfis já aprovadas; configuração delimitada e recuperável.
Verificar TOML, matriz, nomes e vínculos; testar o carregamento pelo cliente sem
iniciar uma tarefa de modelo; revisar as instruções contra cenários representativos.
Preservar a proposta e reexaminar AC-01/02, sem renovar observação antiga.
Superfície docs permanece pertinente: configuração de assistentes e documentação
de governança, sem código do produto ou do runner. A checagem nativa focada cobre
o consumidor Codex que o runner docs não exercita. Depois de estabilizar os
artefatos, executar runner docs e gate para os quatro critérios.

O guia é também consumido em auditorias isoladas de instalação de skills, onde
a configuração de agentes não existe. Identificar esses arquivos por caminhos
desde a raiz; conferir sua existência no teste focado da configuração.
Não ampliar os snapshots do instalador de skills para incluir configuração de
sessões do Codex. Testes, instalador e runner permanecem inalterados.
