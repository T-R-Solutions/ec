# Replicar os agentes de desenvolvimento em outra aplicação

Este guia é para quem mantém outro repositório e quer adotar a configuração de
assistentes do Ec. A fonte operacional continua sendo
[codex-agents.md](codex-agents.md), com a matriz de treze perfis e o procedimento
comum. Os perfis não são os Agents de execução de pipelines do produto Ec.

O guia descreve os arquivos inspecionados em 14/11/2026 e a CLI local
`codex-cli 0.169.0`. Não houve instalação em outra aplicação nem ensaio de eficácia
com modelos. Outro cliente, conta ou política pode oferecer capacidades diferentes.

## Começo rápido e escolha do alcance

1. Leia o procedimento comum e inventarie configuração, instruções e workflow já
   existentes no destino. Confira permissões de escrita e preservação do trabalho
   local antes de instalar. Use uma revisão recuperável já disponível; não crie
   backups permanentes nem copie segredos.
2. Escolha uma das adoções abaixo. Prepare os arquivos em uma mudança revisável
   no destino autorizado; resolva as dependências antes de iniciar agentes.
3. Transfira os treze TOMLs e o guia comum, mescle a configuração raiz e adapte
   autoridades, caminhos, modelos e comandos pelos mapas deste guia.
4. Confira TOML, referências e todos os papéis. Abra uma nova sessão no projeto
   confiável, observe as capacidades e faça um ensaio delimitado se autorizado.
5. Registre os resultados separados: estrutura, carregamento, execução e aceite.
   Ausência de capacidade necessária exige bloqueio e condição de retomada.

| Adoção | O que entrega | O que precisa existir no destino |
| --- | --- | --- |
| Agentes integrados ao workflow do destino | Triagem, coordenação, responsabilidades, revisão e validação independentes | Políticas locais de autoridades, verificação, evidências e retenção; comandos reais e critérios revisados |
| Agentes com SDD e aceite automatizado | Método acima mais registros, vínculo por conteúdo, runner e gate adaptados | Templates/validador SDD, configuração de automação, supervisão e testes de rejeição próprios |

A primeira opção não possui paridade com o gate do Ec. Não mantenha instruções
mandando executar `scripts/accept-task.sh` se esse comando não foi integrado.
A segunda opção é uma adaptação de engenharia: copiar `scripts/` inteiro não
instala uma suíte independente da stack e da Toolbox do Ec.

## O que cada camada faz

| Camada e fonte no Ec | Função | Tratamento no destino |
| --- | --- | --- |
| `.codex/config.toml` | Modelo/esforço raiz, entrada de triagem, delegação e defaults | Mesclar chaves e instruções com a configuração existente |
| `.codex/agents/` | Identidade, modelo/esforço e responsabilidade de cada especialista | Copiar os treze arquivos e revisar cada instrução |
| [Procedimento comum](codex-agents.md) | Ciclo, matriz, controle de vagas, responsabilidades e encerramento | Copiar/adaptar uma única fonte comum e seus links |
| [AGENTS.md](../../AGENTS.md) | Entrada, autoridades e convenções do repositório | Integrar às instruções próprias e verificar instruções mais específicas nas subpastas |
| [Skills e pontes](../../sdd/chain/README.md) | Métodos selecionados por problema | Selecionar pacotes necessários; ler `SKILL.md` e recursos referidos |
| [Workflow](../../sdd/workflow.md) e [evidência](../../sdd/evidence.md) | Rotas, critérios e rastreabilidade | Integrar ao workflow existente ou adaptar o conjunto SDD |
| [Verificação](task-verification.md) e [estado local](local-state.md) | Comandos, autorização, aceite e retenção | Definir fontes únicas equivalentes no destino |
| [Runner](../../scripts/README.md) e [gate](../../tools/governance/task_gate.py) | Execução supervisionada e comparação de recibos/inputs | Integrar apenas na adoção completa, com testes próprios |

Um arquivo de agente não instala skills. `AGENTS.md` não configura o modelo.
O workflow não executa testes sozinho. O gate local não impede respostas de chat
ou comandos Git externos; uma proteção incontornável depende de integração
específica do host/CI. Nenhuma dessas camadas amplia permissões da sessão.

## Inventário exato dos perfis

A origem é `.codex/agents/` no Ec e o destino recomendado é `.codex/agents/` na
aplicação receptora. Os treze nomes de arquivo são:

```text
ec_triador.toml
ec_orquestrador_normal.toml
ec_orquestrador_alta.toml
ec_analista.toml
ec_analista_alta.toml
ec_planejador.toml
ec_planejador_alta.toml
ec_implementador.toml
ec_implementador_extra_alto.toml
ec_revisor.toml
ec_testador.toml
ec_validador.toml
ec_validador_alta.toml
```

Consulte a [matriz canônica](codex-agents.md#perfis) para modelos e esforços;
não mantenha uma segunda matriz independente. Os arquivos usam `name`,
`description`, `model`, `model_reasoning_effort` e `developer_instructions`.
Todos apontam para `docs/development/codex-agents.md`. Sem esse arquivo e suas
autoridades adaptadas, a cópia dos TOMLs fica incompleta.

Na configuração raiz, revisar `model`, `model_reasoning_effort`,
`developer_instructions` e a tabela `[agents]`: `enabled`,
`max_concurrent_threads_per_session`, `default_subagent_model` e
`default_subagent_reasoning_effort`. Não sobrescreva um `config.toml` existente:
mescle por chave, preservando provedores, MCP, hooks e controles do destino e
resolvendo instruções conflitantes. Não duplique a tabela `[agents]`.

O Ec configura três threads de subagentes, excluindo a raiz: limite operacional
de quatro nós em toda a árvore, normalmente raiz + orquestrador + dois
especialistas. Treze perfis disponíveis não significam treze agentes simultâneos.
O menor limite efetivo do host prevalece. A raiz faz a triagem diretamente;
especialistas não subdelegam. Extra alto exige dificuldade demonstrada de uma
subtask, e revisão/validação precisam de contextos independentes da implementação.

## Adaptação dos caminhos e instruções

Mantenha os caminhos Ec se forem adequados ao destino; caso contrário, escreva
um mapa explícito antes de editar. Exemplo de mapa, **não comando de instalação**:

| Origem Ec | Exemplo de destino | Consumidores que devem mudar juntos |
| --- | --- | --- |
| `docs/development/codex-agents.md` | `docs/engineering/agents.md` | Config raiz, todos os treze TOMLs, AGENTS e índices |
| `docs/development/task-verification.md` | `docs/engineering/verification.md` | Guia comum, AGENTS e ambos os validadores |
| `docs/development/local-state.md` | `docs/engineering/local-state.md` | Guia comum, AGENTS e skills que referenciam a política |
| `sdd/workflow.md`, `sdd/evidence.md` | Workflow e registro de evidência existentes | Guia comum, entrada e documentos/metodologias dependentes |
| `sdd/chain/README.md` | Catálogo de métodos do destino | Guia comum, AGENTS e plano de trabalho |
| `ec_` nos nomes | Prefixo próprio, se desejado | Nome do arquivo, campo `name`, config raiz, referências entre agentes, matriz e prompts |

Preserve `ec_*` se não houver benefício em renomear. Se renomear, faça a mudança
como uma unidade revisável e confira todos os consumidores. Substituição global
cega pode alterar exemplos, URLs, código de produto ou evidências históricas.

Na adoção mínima, adapte **todos** os grupos abaixo; não basta editar o guia:

| Papel/arquivo | Ajuste concreto necessário |
| --- | --- |
| Config raiz e `ec_triador` | Guia/nomes de perfis; critérios locais de risco, autoridades e etapas; manter raiz como entrada e especialista triador sem filhos |
| Ambos os orquestradores | Procedimento local, artefatos comuns, donos de caminhos e definição de aceite/bloqueio; troca de perfil devolvida ao controlador |
| Ambos os analistas | Guia, autoridades/contratos/consumidores próprios; preservar leitura apenas |
| Ambos os planejadores | Trocar artefatos, IDs, oráculos e “superfícies” pelos equivalentes reais; informar comandos locais de verificação no plano |
| Ambos os implementadores | Guia e limites de escrita/retomada; preservar evidências e critérios alheios; manter escalonamento justificado |
| Revisor | Guia, contratos e critérios do destino; preservar contexto independente e leitura apenas |
| Testador | Guia e suíte real: executor, pré-requisitos, evidências e recibos do destino; execução final após estabilização |
| Ambos os validadores | Substituir referência direta a `task-verification.md`, opções `--task`/`--feature` e gate pelo contrato local executável; definir como comparar critérios e registrar parecer se o processo local for manual |

Na variante manual, o procedimento comum deve exigir os mesmos atos de análise,
planejamento, implementação, revisão, teste e validação, mas encaminhar o aceite
para a política manual local. Remova comandos e garantias automatizadas que não
existem; registre arquivos/versões observados, método, resultado, responsável e
lacunas. Não declare freshness automatizada ou aprovação pelo gate do Ec.

Adapte também as dependências transitivas: `AGENTS.md` encaminha a contexto do
projeto, constituição, arquitetura, ADRs e, para trabalho visual, contrato do
frontend. Reescreva essas entradas com autoridades e stack reais; não copie as
normas Rust/Vue/CI/CD, a pesquisa TeamCity ou os estados de produto do Ec.

Skills selecionadas podem referenciar políticas Ec em seus `SKILL.md` e recursos.
Confira os arquivos efetivamente lidos, caminhos e dependências internas de cada
pacote. O [perfil de governança](../../config/governance/profile.json) distingue
selecionadas, condicionais e excluídas, origem e hashes; não é a configuração dos
agentes. Copiar seu JSON sem pacotes/fontes correspondentes não instala nem valida
skills. Para governança completa, adapte catálogo/proveniência e pontes conforme o
[guia de instalação](skill-installation.md); não copie recibos aprovados do Ec.

## Dependências da adoção completa

| Conjunto de origem | Destino recomendado | Dependências e adaptação |
| --- | --- | --- |
| `sdd/workflow.md`, `sdd/evidence.md`, `sdd/templates/` | Mesmos caminhos | Ajustar política local; criar registros novos com critérios próprios, inicialmente não verificados |
| `sdd/project.md`, `docs/constitution.md`, `docs/architecture/context.md`, `AGENTS.md` | Mesmos caminhos ou mapa explícito | Reescrever estado/autoridades e links; não importar features/evidências aceitas |
| `sdd/chain/` e skills selecionadas em `.codex/skills/` | Mesmos caminhos ou catálogo local | Adaptar somente pontes necessárias e pacotes completos; conferir recursos e hashes |
| `sdd/tools/validate.py` | Mesmo caminho, se adotar o validador | Python stdlib; referências, schemas, diretórios e convenções locais precisam coincidir |
| `scripts/accept-task.sh`, `tools/governance/task_gate.py` | Mesmos caminhos, após integração | O gate importa `sdd/tools/validate.py` e `tools/quality/automation_config.py`; requer recibos do runner e inventário compatíveis |
| `tools/quality/automation_config.py`, `scripts/config/automation.json` | Mesmos caminhos, após adaptação | Configuração define superfícies, checks, stacks, políticas e inventário; paths Ec não são defaults portáteis |
| `scripts/automate.sh`, `scripts/stacks/` (inclui `ec/common.sh`, `catalog.sh`, `inputs.sh`, `runtime.sh`), `scripts/local-state.py` | Integração própria do destino | Grafo transitivo de scripts/checks, supervisor, locks e retenção; Linux/Bash/Python/jq/coreutils/util-linux e ferramentas selecionadas |
| Testes em `tools/governance/tests/`, `scripts/tests/` | Suite própria adaptada | Rejeitar evidência obsoleta, etapa ausente, skip/falha, limpeza incompleta e inputs alterados/adicionados/removidos |

O runtime inclui `scripts/stacks/ec/environment.sh` e chama
`tools/quality/prepare_automation.py` para preparação/limpeza; esses consumidores
também precisam acompanhar a integração.
O runner também consome configurações, regras e ferramentas externas descritas
em [scripts/README.md](../../scripts/README.md) e
[verification-tools.md](verification-tools.md). Inspecione o grafo de checks
selecionados e seus imports/chamadas antes de integrar. Rust, npm, Docker,
PostgreSQL, Toolbox e bases de scanners são dependências dos checks do Ec; não
são requisitos universais para usar agentes. Não transporte permissões históricas
de download, caminhos absolutos do host ou credenciais.

Somente **no Ec**, na raiz, estes comandos reais descrevem/verificam documentação:

```bash
bash scripts/automate.sh plan --surface docs
bash scripts/automate.sh run --surface docs
```

O aceite exige registro próprio estabilizado e o recibo real. O exemplo seguinte
tem placeholders: substitua `NOME`, `AAAA-MM-DD` e `ID`; não o execute literalmente.
No destino, só use essa interface depois de integrar e testar o runner/gate:

```bash
bash scripts/accept-task.sh --task sdd/tasks/NOME --run .local/logs/AAAA-MM-DD/run-ID-automate.results.log
```

`--feature` aceita o agregado; `--task` aceita o recorte com relações revisadas.
Catálogo/estrutura não renovam observações históricas. O gate local precisa ser
testado com rejeições antes de alegar equivalência. Não copie `.local/`, caches,
logs, estado de sessão, credenciais, evidências aprovadas ou arquivos de produto
para montar o novo projeto.

## Verificar a cópia e o cliente

Na raiz do repositório receptor, Python 3.11+ permite esta conferência somente de
leitura. Ela assume treze perfis e guia no caminho original; ajuste conscientemente
se houver perfis adicionais ou o mapa mudar. Nenhum serviço/modelo é chamado:

```bash
python3 - <<'PY'
from pathlib import Path
import tomllib

root = Path.cwd()
config = tomllib.loads((root / '.codex/config.toml').read_text())
files = sorted((root / '.codex/agents').glob('*.toml'))
assert len(files) == 13, f'Esperados 13 perfis; encontrados {len(files)}'
assert (root / 'docs/development/codex-agents.md').is_file()
names = set()
for path in files:
    agent = tomllib.loads(path.read_text())
    assert agent['name'] == path.stem
    assert agent['name'] not in names
    names.add(agent['name'])
    for key in ('description', 'model', 'model_reasoning_effort', 'developer_instructions'):
        assert isinstance(agent[key], str) and agent[key].strip(), (path, key)
assert config['agents']['enabled'] is True
assert config['agents']['max_concurrent_threads_per_session'] == 3
print('TOML e inventário conferidos; carregamento e execução ainda não verificados.')
PY
```

Revise links e referências em texto, inclusive instruções TOML e recursos de
skills; o snippet não faz essa revisão. Na raiz Ec, esta busca ajuda a localizar
os consumidores do guia e dos comandos, sem alterá-los:

```bash
rg -n --hidden 'codex-agents\.md|task-verification\.md|accept-task\.sh|ec_orquestrador' .codex/config.toml .codex/agents AGENTS.md docs/development sdd/chain
```

Depois, no destino autorizado, confira `codex --version` e `codex --help`.
Na CLI observada, `codex -C /caminho/da/aplicacao --strict-config` inicia sessão
interativa; o caminho é um placeholder a substituir. `--strict-config` detecta
campos desconhecidos na configuração, mas não comprova disponibilidade do modelo
nem execução correta. A abertura pode criar estado local do cliente e pedir
confiança no projeto; avalie a confiança pelo procedimento real do host. Não
altere configuração global nem remova proteção para contornar uma rejeição.

`model` e esforços são pedidos de configuração, sujeitos ao cliente/provedor e à
conta. Confira que `gpt-6.1-sol`, `gpt-6-astra`, `medium`, `high` e `xhigh` estão
disponíveis no ambiente receptor. Se não estiverem, escolha explicitamente
substitutos suportados e atualize config, perfis e matriz juntos; não alegue
igualdade de desempenho. Flags da sessão e políticas administradas podem
prevalecer. Arquivos editados não reconfiguram retroativamente a conversa atual.

Observe criação por perfil, modelo/esforço aplicado, contexto independente,
estados/mensagens, interrupção e liberação de vagas. Use a tabela de
[preflight](codex-agents.md#preflight-de-capacidades-e-vagas). Com uma vaga
reutilizável, etapas podem ser sequenciais. Interrupção não prova liberação.
Sem revisão/validação independente, a tarefa fica bloqueada.

## Pedidos de uso e ensaio

Prompts ilustrativos, adaptáveis ao escopo e às políticas do destino:

- **Análise:** “Analise o impacto de X, somente leitura. Entregue riscos, caminhos,
  critérios e pendências; não implemente.”
- **Planejamento:** “Planeje X com alternativas, tarefas, donos de arquivos e
  verificações. Aguarde instrução de execução após entregar o plano.”
- **Execução:** “Implemente X seguindo AGENTS.md e o procedimento dos agentes.
  Preserve mudanças existentes e conduza revisão, testes e aceite até conclusão
  ou bloqueio explícito. Não publique nem crie commit.”
- **Retomada:** “Retome o registro Y: confira inputs atuais, tarefas pendentes,
  evidências afetadas e donos de caminhos antes de continuar.”
- **Skill explícita:** “Use `$technical-documentation` para atualizar o guia X.”
  Isso pressupõe skill instalada e descoberta; o nome não a instala.

Como ensaio autorizado, escolha uma pequena alteração documental descartável,
com critério verificável e caminhos exclusivos. Observe a triagem, transferência
ao orquestrador, um escritor por arquivo e revisão/validação independentes.
Confira que o agente registra bloqueio diante de comando ausente e que o aceite
não ocorre com verificação falha. Um ensaio confirma apenas os cenários observados;
não mede eficácia geral, custo, latência ou garantia de obediência dos modelos.

## Diagnóstico e manutenção

| Sintoma | Conferir e condição de retomada |
| --- | --- |
| Perfil desconhecido | Localização/nomes, TOML, descoberta do cliente e confiança no projeto; retomar em nova sessão que reconheça o perfil |
| Modelo/esforço rejeitado | Capacidade real da conta/provedor; revisar substituição explícita e reiniciar com configuração reconhecida |
| Vagas esgotadas | Toda a árvore e estado dos filhos; retomar após término/liberação confirmada ou sessão com capacidade adequada |
| Escrita em `.codex` protegida | Permissões reais; preparar mudança revisável e solicitar a autorização específica exigida pelo host, sem contornar proteção |
| Guia, política ou skill ausente | Resolver mapa e dependências transitivas; retomar após arquivos/capacidades necessários existirem |
| Runner/gate inexistente | Completar integração testada ou aplicar a adoção mínima com política manual explícita; não simular recibo |
| Evidência/recibo obsoleto | Reexaminar inputs afetados, executar checks necessários e obter novo aceite; não apenas recalcular hashes |

Mantenha o procedimento comum, TOMLs e este guia juntos quando mudar nomes,
modelos, capacidades ou políticas. A equipe que mantém os agentes é dona desses
contratos; índices só encaminham. Registre testes, revisão independente e lacunas
no workflow local. No Ec, logs seguem retenção de duas datas, diretórios 0700 e
arquivos 0600; temporários próprios ficam em `/tmp` e são removidos após uso.
No destino, defina e siga sua política de retenção e recuperação.

Fontes do cliente consultadas em 14/11/2026: documentação oficial de
[agentes customizados](https://learn.chatgpt.com/docs/agent-configuration/subagents#custom-agents),
[configuração e precedência](https://learn.chatgpt.com/docs/config-file/config-basic)
e [CLI](https://learn.chatgpt.com/docs/developer-commands?surface=cli). Contratos específicos
Ec vêm dos arquivos locais ligados acima; suporte no destino deve ser observado.
