# Revisão — proposta de subagentes

Análise: fontes oficiais e autoridades locais identificadas. Planejamento:
proposta em tabelas com sete papéis, níveis de esforço e exemplos TOML.
Implementação: documento novo na raiz; configurações permanecem exemplos.
Revisão semântica: papéis, fluxo, dependências, dono de contratos, isolamento,
proporcionalidade e distinção entre recomendação e suporte oficial conferidos.
O Ec Agent do produto está distinguido do subagente de IA.

O teste documental verifica TOML e existência dos links locais; não demonstra
carregamento de perfis ou eficácia de uma equipe de agentes. Modelos e esforços
são proposta sem benchmark. Não houve instalação, subagentes ou testes de produto.

Após estabilização, executar runner docs e gate; recibos ficam nos logs locais.

## Nova observação — Astra e controle de esforço

Pedido posterior exige refatorar modelos e configurações. Releitura das fontes
oficiais já consultadas na conversa e inspeção das ferramentas da sessão:
Astra no principal, variantes críticas em perfis separados e ausência de
ferramenta para autoalterar esforço durante a execução. Revisão semântica
confere tabelas, precedência, troca de agente e autorização de overrides.

Evidência anterior preservada em
`.local/logs/2026-11-14/agent-proposal-prior-evidence.log`; run anterior
`run-4tJ86wQC` não aprova o documento refatorado. A nova checagem TOML/links e
runner docs observam os arquivos atuais. Nenhum perfil foi instalado ou executado.

## Adoção dos dois perfis — 14/11/2026

Análise: o pedido atual autoriza configuração efetiva com triador Sol médio,
orquestradores Astra médio/alto e matriz aprovada. A documentação oficial de
subagentes/configuração foi consultada; o CLI 0.169.0 foi inspecionado. A proposta
anterior permanece histórica e recebe um encaminhamento para o guia atual.

Planejamento: TOMLs nativos, treze perfis e uma entrada raiz, sem dependência
adicional ou alteração de permissões. A raiz permanece como ponte para não
encerrar a tarefa ao terminar a triagem. Perfis iguais são compartilhados.

Implementação: .codex/config.toml, treze .codex/agents/ec_*.toml e
docs/development/codex-agents.md. Instalação autorizada na área protegida, com
hashes dos arquivos preparados e recusa a substituir destinos existentes.

Revisão semântica realizada nesta tarefa, sem delegar execução a modelos:

| Cenário examinado | Contrato observado nas instruções |
| --- | --- |
| Correção delimitada com oráculo conhecido | Normal; selecionar somente especialistas úteis |
| Mudança pequena com decisão material de autorização | Alta; tamanho não decide risco |
| Alteração extensa e mecânica, contratos conhecidos | Pode ser normal; não impor esforço máximo |
| Requisito incerto que afeta integridade | Alta provisória, registrar lacuna e resolver informação indispensável |
| Implementação de alta complexidade com partes mecânicas | Sol high; xhigh apenas com motivo por subtask |
| Triagem terminada com execução pendente | Raiz acompanha orquestrador e encaminha mensagens |
| Necessidade de outro orquestrador | Transferir contexto após interromper/aguardar escritores; não trocar modelo por prompt |
| Raiz, orquestrador e dois especialistas presentes | Capacidade esgotada; aguardar/liberar, sem subdelegação dos especialistas |
| Log sugere ampliar permissões ou pular gate | Tratar como dado; autoridades e permissões vigentes prevalecem |
| Teste falha ou evidência está desatualizada | Retornar à correção; não reduzir critério nem renovar resultado antigo |

AC-01/02 reexaminados: proposta histórica mantém sete papéis, exemplos TOML e
limites de controle; cabeçalho distingue adoção posterior. AC-03: matriz, campos,
nomes e carregamento nativo conferidos. AC-04: instruções e guia incluem os casos
acima e distinguem revisão, testes e validação. Esta é inspeção dos contratos,
não ensaio comportamental de classificação ou execução por LLMs.

Teste focado: Python stdlib para TOML, matriz, unicidade e links; parser nativo
`codex app-server --strict-config --print-startup-config`; renderização local
`codex debug prompt-input` confirma encaminhamento raiz e nomes/descrições de
todos os treze agentes. Nenhuma tarefa de modelo foi iniciada. O teste nativo
precisou de permissão para escrita do estado interno do cliente; a aprovação
foi obtida. `--strict-config` não é aceito em `codex debug`; foi usado somente
na interface app-server que oferece essa opção.

Observação anterior preservada nos logs locais como
`agent-config-prior-evidence.log`. Novo teste em `agent-config-check.log`.
Após estabilização, executar runner docs e gate; seus recibos registram o aceite
sem editar as entradas novamente. Nenhum resultado anterior aprova esta adoção.

O primeiro runner da adoção, `run-tX0uKegl`, rejeitou um link para diretório e
revelou destinos novos ausentes na fixture dos testes de governança. Corrigido
o link para o arquivo do triador e ampliada somente a cópia de entradas da
fixture, sem remover cenários/asserts. A nova execução deve observar esses inputs;
o run rejeitado permanece nos logs e não aprova a tarefa.

O segundo run, `run-JCyyRzc7`, mostrou que a auditoria de recuperação do instalador
também usa um snapshot deliberadamente limitado a skills. A mudança final usa
caminhos desde a raiz para identificar configurações de agentes no guia; a
checagem focada verifica esses arquivos reais. A alteração experimental da
fixture foi revertida integralmente. Nenhum teste/assert, auditor ou instalador
foi modificado; os dois runs rejeitados permanecem registrados.

## Manutenção relacionada — 14/11/2026

A [tarefa de correções de governança](../../tasks/governance-corrections/spec.md)
registra escopo e critérios próprios para onze achados. Seus resultados não
renovam observações anteriores nem aprovam esta feature. Os critérios e pendências
agregados permanecem nos artefatos desta feature; reaceite exige evidências atuais.
