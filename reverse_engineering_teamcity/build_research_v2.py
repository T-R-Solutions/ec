"""Generate the independent research delivery; never modify the legacy corpus."""
import hashlib
import json
import zipfile
from datetime import datetime, timezone
from pathlib import Path

ROOT = Path(__file__).resolve().parent
OUT = ROOT / 'ci-platform-research'
OLD = ROOT / 'teamcity-reverse-engineering'


def write(path, content):
    p = OUT / path
    p.parent.mkdir(parents=True, exist_ok=True)
    p.write_text(content.strip() + '\n')


def dump(path, content):
    write(path, json.dumps(content, ensure_ascii=False, indent=2))


sources = [
    ('D-QUEUE', 'https://www.jetbrains.com/help/teamcity/working-with-build-queue.html', 'TeamCity 2026.2: fila e agentes compatíveis'),
    ('D-AGENT', 'https://www.jetbrains.com/help/teamcity/install-and-start-teamcity-agents.html', 'TeamCity 2026.2: agentes e processos'),
    ('D-DAG', 'https://www.jetbrains.com/help/teamcity/configuring-dependencies.html', 'TeamCity 2026.2: dependências e revisão sincronizada'),
    ('D-VCS', 'https://www.jetbrains.com/help/teamcity/configuring-vcs-triggers.html', 'TeamCity 2026.2: polling e commit hooks'),
    ('D-ART', 'https://www.jetbrains.com/help/teamcity/build-artifact.html', 'TeamCity 2026.2: arquivos produzidos pelo build'),
    ('D-PROJECT', 'https://www.jetbrains.com/help/teamcity/project-administrator-guide.html', 'TeamCity 2026.2: projetos e fluxo básico'),
    ('D-STEPS', 'https://www.jetbrains.com/help/teamcity/configuring-build-steps.html', 'TeamCity 2026.2: steps e scripts'),
    ('D-AUTH', 'https://www.jetbrains.com/help/teamcity/managing-roles-and-permissions.html', 'TeamCity 2026.2: papéis por projeto'),
    ('D-LOG', 'https://www.jetbrains.com/help/teamcity/build-log.html', 'TeamCity 2026.2: saída de processos e eventos'),
    ('D-MATCH', 'https://www.jetbrains.com/help/teamcity/configuring-agent-requirements.html', 'TeamCity 2026.2: condições de compatibilidade'),
    ('D-PARAM', 'https://www.jetbrains.com/help/teamcity/configuring-build-parameters.html', 'TeamCity 2026.2: parâmetros e ambiente'),
    ('D-GC', 'https://www.jetbrains.com/help/teamcity/teamcity-data-clean-up.html', 'TeamCity 2026.2: retenção'),
    ('D-CACHE', 'https://www.jetbrains.com/help/teamcity/build-cache.html', 'TeamCity 2026.2: reutilização de arquivos'),
    ('T-PG', 'https://www.postgresql.org/docs/current/sql-select.html', 'PostgreSQL 18: locks de linha e SKIP LOCKED'),
    ('T-SQLITE', 'https://sqlite.org/whentouse.html', 'SQLite: usos e concorrência de escritores'),
    ('T-STD', 'https://doc.rust-lang.org/std/process/struct.Command.html', 'std::process::Command'),
    ('T-PROCESS', 'https://docs.rs/tokio/latest/tokio/process/index.html', 'Tokio process: cancelamento e reap de processos'),
    ('T-OCI', 'https://github.com/opencontainers/runtime-spec/blob/main/config-linux.md', 'OCI: namespaces, recursos e seccomp'),
    ('T-TOKIO', 'https://docs.rs/tokio/latest/tokio/', 'Tokio 1.54.1'),
    ('T-AXUM', 'https://docs.rs/axum/latest/axum/', 'Axum 0.8.9'),
    ('T-TOWER', 'https://docs.rs/tower/latest/tower/', 'Tower 0.5.3'),
    ('T-TONIC', 'https://docs.rs/tonic/latest/tonic/', 'Tonic 0.14.9'),
    ('T-SERDE', 'https://serde.rs/', 'Serde'),
    ('T-SQLX', 'https://docs.rs/sqlx/latest/sqlx/', 'SQLx 0.9.0'),
    ('T-DIESEL', 'https://diesel.rs/', 'Diesel'),
    ('T-TRACING', 'https://docs.rs/tracing/latest/tracing/', 'Tracing 0.1.44'),
    ('T-RUSTLS', 'https://docs.rs/rustls/latest/rustls/', 'Rustls 0.23.46'),
    ('T-GRAPH', 'https://docs.rs/petgraph/latest/petgraph/', 'Petgraph 0.8.3'),
    ('T-NEXTEST', 'https://nexte.st/', 'cargo-nextest'),
    ('T-PROP', 'https://docs.rs/proptest/latest/proptest/', 'proptest 1.12.0'),
    ('T-LOOM', 'https://docs.rs/loom/latest/loom/', 'loom 0.7.2'),
]
dump('01-teamcity-research/evidence/official-sources.json', [
    dict(id=i, url=u, subject=s, classification='DOCUMENTED', consulted_on='2026-10-30',
         scope='Documentação publicada; não comprova execução nem implementação interna.',
         maintenance='Documentação acessível nesta data; não constitui auditoria de manutenção ou garantia futura.')
    for i, u, s in sources
])
write('01-teamcity-research/evidence/sources.md', '# Fontes oficiais consultadas\n\n' + '\n'.join(
    f'- {i}: [{s}]({u}).' for i, u, s in sources) + '''

As páginas TeamCity retornaram o cabeçalho 2026.2, correspondente à versão dos artefatos investigados.
Versões de crates são as exibidas na consulta, não um lockfile de compatibilidade testado.
Não há benchmark nem auditoria de segurança dessas dependências nesta entrega.
A URL /docs/ do nextest retornou 404; foi substituída pela página oficial raiz.
''')

claims = json.loads((OLD / 'claims.json').read_text())
validation = []
for c in claims:
    name = c['artifact']
    outer, *inner = name.split('!/')
    p = Path('/opt/jvm/TeamCity') / outer
    data = p.read_bytes()
    if inner:
        import io
        for member in inner:
            data = zipfile.ZipFile(io.BytesIO(data)).read(member)
    actual = hashlib.sha256(data).hexdigest()
    evidence_path = Path(c['evidence']['javap'])
    if not evidence_path.is_absolute():
        evidence_path = ROOT.parent / evidence_path
    validation.append(dict(claim=c['id'], artifact=name, expected=c['artifact_sha256'], actual=actual,
                           matches=actual == c['artifact_sha256'], javap_exists=evidence_path.is_file()))
dump('01-teamcity-research/evidence/reuse-validation.json', validation)
dump('01-teamcity-research/evidence/legacy-claims.json', claims)
write('01-teamcity-research/evidence/reuse-audit.md', '''
# Auditoria de reaproveitamento

Base preservada: `rea/teamcity-reverse-engineering/`. Não foi reexecutado inventário, ASM, CFR ou catálogo.
`reuse-validation.json` revalida os artefatos de todas as 19 afirmações, inclusive o JAR aninhado de DSL,
e a presença das desassemblagens. Conteúdo estático idêntico permite reaproveitar a análise; não prova execução.
O catálogo antigo registra 412.168 conteúdos distintos de classe, 124 desassemblados seletivamente,
19 afirmações curadas, 546 rotas REST declaradas e 0/14 cenários dinâmicos executados.
1.239 verificações anteriores são resultado histórico, não testes reexecutados nesta entrega.

REA CLI instalado anuncia 6.8.0 (`rea --help` consultado antes de qualquer nova operação).
Não há ferramentas REA MCP expostas nesta sessão. Reaproveitado o bundle real
`../../../teamcity-reverse-engineering/25-evidencias/rea-desktop-bundle.json`.
Evidence de origem: `ev_f3ef0c08f2f33316db8a132d97e0760cdadd12c84153087152f2b37b908dc745`.
É reconhecimento parcial de diretório, com orçamento de metadados atingido; seu digest não é hash integral de cada arquivo.
Nenhuma nova operação de reconhecimento REA é necessária para as perguntas desta fatia.
Os hashes integrais dos JARs são verificados por Python, identificado como ferramenta externa.

Reutilizáveis: claims, desassemblagens, descriptors, fontes SQL catalogadas, índice SQLite de símbolos,
ferramentas de consulta, limitações e evidência REA. Não reutilizar como fatos: nomes de candidatos,
arestas simbólicas como despacho resolvido, Java inválido do CFR, índices sintáticos como entendimento semântico.

Classificações: OBSERVED exige execução de TeamCity; não há nenhuma nesta entrega.
STATIC_CONFIRMED aplica-se somente aos trechos dos artefatos; DOCUMENTED às fontes oficiais;
INFERRED é hipótese explícita; UNKNOWN permanece sem comprovação. Propostas Rust não são afirmações TeamCity.

Perguntas técnicas resolvidas sem nova descompilação: persistência da fila (TC-004),
compatibilidade (D-MATCH), despacho (TC-005/017), ordem de steps (TC-010), conclusão e artefatos (TC-012).
O vínculo transacional completo REST→fila→resultado segue UNKNOWN; a plataforma nova define seu próprio contrato.
''')

# Feature-oriented facts. Every entry has its own limitations and independent decision.
features = [
    ('F01', 'Projetos, configuração e parâmetros', 'D-PROJECT D-PARAM D-AUTH TC-015',
     'Projetos organizam configurações; parâmetros compõem settings e ambiente; permissões são concedidas por papéis.',
     'Organizar builds e limitar quem pode alterar ou executar cada projeto.',
     'Configuração, parâmetros e identidade → configuração efetiva; alterações são aplicadas por fluxo próprio de settings.',
     'Regras completas de herança, templates e precedência não reconstruídas.', 'Adaptar: projeto simples com revisão imutável; templates adiados.'),
    ('F02', 'Solicitação e validação', 'TC-013 TC-016',
     'Endpoint de fila consulta usuário e chama triggerBuild; compilador DSL converte diagnóstico em erro.',
     'Recusar solicitações inválidas e criar execução identificável.',
     'Requisição autenticada → build na fila ou erro; ligação transacional integral ainda desconhecida.',
     'Não se demonstrou atomicidade entre REST, configuração e fila.', 'Substituir: JSON declarativo validado sem compilação de código no servidor.'),
    ('F03', 'Fila durável', 'TC-003 TC-004 D-QUEUE',
     'Há índices de fila em memória e inserção parametrizada em build_queue.',
     'Manter pedidos pendentes até haver capacidade.',
     'Build pendente → posição e persistência; integridade sob crash não foi observada.',
     'Isolamento e recuperação TeamCity desconhecidos.', 'Adotar o requisito; fila é consulta ao estado SQL, sem cópia autoritativa em memória.'),
    ('F04', 'Compatibilidade e seleção', 'TC-005 TC-006 TC-007 D-MATCH D-QUEUE',
     'Distribuidor processa fila; filtro pode ordenar agentes por prioridade limitada; requisitos restringem agentes.',
     'Executar onde existem recursos e ferramentas necessários.',
     'Build + agentes → candidatos e distribuição ou espera.',
     'Justiça global, reserva e otimização global não demonstradas.', 'Adaptar: FIFO entre jobs elegíveis e slots autenticados; prioridade de agentes descartada inicialmente.'),
    ('F05', 'Registro e transporte', 'TC-008 TC-009 TC-017 TC-018 D-AGENT',
     'Registro evita concorrência local; agente consulta comandos e lê identificadores da resposta.',
     'Conectar executores ao orquestrador e transferir trabalho.',
     'Registro/poll → identidade e comando; protocolos alternativos podem ser tentados.',
     'Autenticação interna, serialização e leases não recuperados.', 'Substituir por HTTP versionado, credenciais de agente e leases próprios.'),
    ('F06', 'Steps e processos', 'TC-010 D-STEPS D-PARAM',
     'Estágios consultam interrupção e resultado; scripts podem executar ferramentas instaladas no agente.',
     'Executar comandos reproduzíveis e propagar falha.',
     'Coleção de estágios → status parcial/final; execução pode parar após falha.',
     'Montagem completa de estágios e subprocessos não reconstruída.', 'Adaptar: argv explícito, workspace isolado, steps sequenciais e timeout.'),
    ('F07', 'Logs, testes e diagnóstico', 'D-LOG D-ART',
     'Log inclui eventos e saída de processos; relatórios podem ser artefatos.',
     'Explicar resultado e disponibilizar evidência ao usuário.',
     'Saída/eventos → log e arquivos consultáveis.',
     'Parsing de testes, consistência do stream e limites internos UNKNOWN.', 'Adaptar: chunks numerados; relatórios como artefatos no CORE, parsing em fase posterior.'),
    ('F08', 'Artefatos e finalização', 'TC-012 D-ART',
     'Finalização inspecionada aguarda publicação de artefatos e informa término ou detached state.',
     'Vincular resultado aos produtos do build.',
     'Contexto final e arquivos → publicação e notificação.',
     'Confirmação remota e commit no servidor não comprovados.', 'Adaptar: manifesto verificado e conclusão idempotente com fencing.'),
    ('F09', 'Cancelamento, retry e perda de agente', 'TC-011 TC-012 TC-018',
     'Interrupção marca estado e alcança estágio atual; poller captura falhas e continua.',
     'Interromper trabalho e tolerar falhas de comunicação.',
     'Sinal de interrupção → encaminhamento; interrupção não comprova morte de descendentes.',
     'Reagendamento, exatamente uma vez e recuperação sob partição UNKNOWN.', 'Substituir por lease, fencing e cancelamento persistente; retry somente sob política explícita.'),
    ('F10', 'DAG e cadeias', 'D-DAG TC-005',
     'Dependências snapshot relacionam builds e sincronizam revisão segundo a documentação.',
     'Coordenar trabalho paralelo sobre entradas consistentes.',
     'Dependências → cadeia de builds com política de pré-requisitos.',
     'Otimizações internas de reúso e transações não reconstruídas.', 'Adaptar: DAG estático com revisão fixada e falha propagada; reúso complexo adiado.'),
    ('F11', 'VCS e triggers', 'D-VCS D-PROJECT',
     'Detecção por polling considera commit hooks e mudanças pendentes compatíveis com checkout rules.',
     'Iniciar builds sobre alterações relevantes.',
     'Mudança elegível → pedido de build; checkout fornece entradas.',
     'Drivers VCS, retries e validação de credenciais não inspecionados.', 'Adaptar: Git com revisão imutável primeiro; webhooks autenticados e polling posteriormente.'),
    ('F12', 'Plugins e APIs', 'TC-013 TC-014 TC-016',
     'REST oferece submissão; carregamento de plugin cria contexto e publica eventos de carga.',
     'Integrar sistemas e acrescentar capacidades.',
     'Plugin → contexto carregado ou evento de falha.',
     'ABI, segurança e isolamento de plugins não comprovados.', 'Adotar API própria; rejeitar plugins JVM e execução de DSL; extensões externas adiadas.'),
    ('F13', 'Retenção e cache', 'D-GC D-CACHE',
     'Limpeza remove dados antigos; cache reutiliza arquivos publicados entre builds.',
     'Conter armazenamento e evitar trabalho repetido.',
     'Política/cache key → retenção ou reaproveitamento.',
     'Atomicidade do GC e proteção contra cache poisoning não verificadas.', 'Adotar retenção básica; cache adiado até namespace por confiança e integridade.'),
]
dump('01-teamcity-research/features/features.json', [dict(
    id=i, feature=n, evidence=e.split(), behavior=b, problem=p, functional_contract=c,
    limitations=l, decision=d, classification='STATIC_CONFIRMED/DOCUMENTED; consultar cada referência')
    for i,n,e,b,p,c,l,d in features])
flow_details = {
 'F01': ('Administrador altera settings', 'projeto, identidade e parâmetros', 'permissões por papel: D-AUTH', 'aplicação agendada: TC-015'),
 'F02': ('POST de build', 'usuário atual e objeto Build: TC-013', 'limite de fila verificado: TC-013; demais validações UNKNOWN', 'triggerBuild; moveTop opcional: TC-013'),
 'F03': ('Atualização da fila', 'lista de QueuedBuildEx: TC-004', 'pré-condições completas UNKNOWN', 'conversão em tuplas e executeBatchForTuples: TC-004'),
 'F04': ('Distribuidor processa fila', 'entrada da fila e agentes: TC-005', 'requisitos por parâmetro: D-MATCH', 'clamp e ordenação condicional de prioridade: TC-006/007'),
 'F05': ('Agente registra ou consulta comando', 'buildId no registro; ID/nome de comando no poll', 'tryAcquire exclui registro local concorrente: TC-008', 'fallback de protocolo e atualização de ID: TC-009'),
 'F06': ('Executor recebe coleção de estágios', 'estágios: TC-010; parâmetros: D-PARAM', 'interrupção e status consultados: TC-010', 'callRunStage e propagação de status: TC-010'),
 'F07': ('Processo emite saída ou evento', 'stdout e eventos de execução: D-LOG', 'validação do stream UNKNOWN', 'armazenamento em formato interno: D-LOG'),
 'F08': ('Finalização de build no agente', 'BuildFinishContext: TC-012', 'status finalizado e detached state: TC-012', 'espera publicação e chama notificação de término/detach: TC-012'),
 'F09': ('Interrupção ou erro no poll', 'sinal interrupt: TC-011; erro do poll: TC-018', 'estágio atual pode estar ausente: TC-011', 'flag atômica e interrupt no estágio; laço do poll continua após erro'),
 'F10': ('Build downstream solicita dependência', 'configurações upstream e revisão: D-DAG', 'pré-condições detalhadas de reúso UNKNOWN', 'sincronização de snapshot documentada: D-DAG'),
 'F11': ('Polling ou commit hook sinaliza mudanças', 'repositório, branch e mudanças: D-VCS', 'checkout rules filtram mudanças: D-VCS', 'mudanças pendentes elegíveis disparam build: D-VCS'),
 'F12': ('Carregamento de plugin', 'classes e contexto do plugin: TC-014', 'pré-condições de segurança UNKNOWN', 'contextCreated, refresh, contextLoaded ou contextFailedToLoad: TC-014'),
 'F13': ('Agenda de limpeza ou consumo de cache', 'política de retenção ou arquivos de cache: D-GC/D-CACHE', 'regras de retenção: D-GC; demais validações UNKNOWN', 'remoção de dados ou publicação/consumo de cache documentados'),
}
dump('01-teamcity-research/flows/behavioral-contracts.json', [dict(
    feature_id=i, initial_event=flow_details[i][0], received_data=flow_details[i][1],
    preconditions=flow_details[i][2], validations=flow_details[i][2], business_rules=b,
    transformations=flow_details[i][3], intermediate_states='Somente os estados citados na evidência; máquina global UNKNOWN.',
    component_interactions=flow_details[i][3],
    persistence=('Inserção SQL confirmada em TC-004; isolamento UNKNOWN.' if i=='F03' else 'Garantias de commit neste fluxo UNKNOWN; não inferir durabilidade por documentação da funcionalidade.'),
    concurrency=('Exclusão local no registro: TC-008; concorrência distribuída UNKNOWN.' if i=='F05' else 'Sem garantia global comprovada; ver states/observed-boundaries.md.'),
    responses=c, possible_failures=l,
    recovery=('Laço do poll continua após falhas capturadas: TC-018; recuperação de execução UNKNOWN.' if i in ['F05','F09'] else 'Recuperação sob crash/partição UNKNOWN neste fluxo.'),
    final_result=c, evidence=es.split(), limitations=l)
    for i,n,es,b,p,c,l,d in features])
write('01-teamcity-research/features/catalog.md', '# Catálogo funcional\n\n' + '\n\n'.join(
    f'## {i} — {n}\n\n**FUNCIONALIDADE:** {b}\n\n**PROBLEMA RESOLVIDO:** {p}\n\n'
    f'**CONTRATO FUNCIONAL:** {c}\n\n**EVIDÊNCIAS:** {e}. Ver evidence/legacy-claims.json e official-sources.json.\n\n'
    f'**LIMITAÇÕES:** {l}\n\n**DECISÃO PARA O NOVO SISTEMA:** {d}'
    for i,n,e,b,p,c,l,d in features))

write('01-teamcity-research/flows/minimum-ci.md', '''
# Fluxo TeamCity de referência

1. Pedido REST chega ao recurso de fila (TC-013, STATIC_CONFIRMED); obtém identidade atual e chama triggerBuild.
2. Validação efetiva de toda configuração nesse caminho: UNKNOWN. TC-016 comprova diagnóstico na compilação DSL, em outro caminho.
3. Criação de promoção, execução e inserção na fila: ligação entre componentes INFERRED; TC-004 comprova apenas inserção SQL.
4. Fila e compatibilidade: TC-003/005 e D-QUEUE/D-MATCH. Ordem e compatibilidade são distintas.
5. Reserva atômica e prevenção de dupla atribuição: UNKNOWN. Não inferir pelo uso de AtomicReference.
6. Agente consulta comandos (TC-017); a correlação exata com a atribuição anterior permanece UNKNOWN.
7. Estágios verificam interrupção e status (TC-010/011); scripts executam ferramentas (D-STEPS).
8. Logs capturam eventos/saída (D-LOG); transporte e confirmação por chunk UNKNOWN.
9. Publicação de arquivos e espera na finalização (TC-012/D-ART).
10. Resultado comunicado ao servidor; confirmação transacional final UNKNOWN.

Pré-condições documentadas: configuração e agente compatível. Entradas: configuração, revisão,
parâmetros e identidade. Saídas: status, logs e artefatos. Exceções relevantes: configuração inválida,
ausência de agente, step com falha, interrupção, rede indisponível e falha de publicação.
As recuperações TeamCity não demonstradas serão especificadas independentemente, sem alegar equivalência.
''')
write('01-teamcity-research/states/observed-boundaries.md', '''
# Estados comprovados e fronteiras

DOCUMENTED: fila contém builds disparados esperando execução (D-QUEUE).
STATIC_CONFIRMED: registro do agente altera flag local (TC-008); stages retornam status e podem
ser interrompidos (TC-010/011); finalização distingue término e detached state (TC-012).
UNKNOWN: máquina global completa, precedência cancelamento/conclusão, eleição de servidor,
linearização de alterações, expiração e recuperação de trabalho. Não converter flags locais em garantias distribuídas.
''')
write('01-teamcity-research/algorithms/scheduling-evidence.md', '''
# Limites do algoritmo conhecido

TC-005 prova delegação para processador de distribuição e verificação topológica em um caminho.
TC-006/007 provam clamp de prioridade do agente e ordenação condicional de candidatos.
Não provam FIFO global, ausência de starvation, balanceamento ótimo, leases ou fencing.
O scheduler novo escolhe seu objetivo e contrato em 03-rust-architecture/scheduling/scheduler.md.
''')
unknowns = json.loads((OLD/'unknowns.json').read_text())
dump('01-teamcity-research/unknowns/reclassified.json', [dict(
    legacy_id=u['id'], original=u, disposition=(
        'accepted_for_independent_design' if u.get('subsystem','') in ['05-build-agents','06-build-queue','07-scheduler','08-build-lifecycle','16-persistencia','18-concorrencia']
        else 'deferred_original_internals'),
    reason='Não é necessário reproduzir a implementação; validar o contrato próprio com testes de falha.',
    reopen_when='Somente se requisito de compatibilidade explícita ou decisão concreta depender desse detalhe.') for u in unknowns])
write('01-teamcity-research/unknowns/critical-questions.md', '''
# Incertezas aceitas e gates

As lacunas legadas não bloqueiam o projeto independente. Não são declaradas resolvidas.
Não é necessário recuperar beans, scheduler exato, protocolo privado, todas as variáveis ou 14 cenários TeamCity.

Bloqueiam release do novo sistema, mas não esta especificação: isolamento efetivo no host escolhido;
teste de reserva concorrente no PostgreSQL; fencing em todas as escritas de resultado/log/artefato;
cleanup de descendentes; backup/restauração coordenados de metadados e objetos.
Responsável futuro: implementação, validada pelos cenários A04–A12 e gates do plano.

Hipóteses de produto adotadas: instalação própria Linux; uma organização com projetos;
código confiável no primeiro piloto; revisão Git explícita; um slot por agente no CORE;
controle em um processo e PostgreSQL. Não são propriedades descobertas do TeamCity.
Código hostil requer o gate de isolamento antes de habilitação, não apenas Rust ou container padrão.
''')

# Independent requirements: every record includes all required contract fields.
rows = [
 ('R01','CORE','Submissão autenticada','identidade, projeto, config_revision, chave, payload','run_id durável','validar autorização e chave por projeto; mesma chave/payload retorna mesmo run; payload diferente → conflito','nenhum run aceito sem commit','Unauthorized, Forbidden, Conflict, InvalidConfig','uma criação lógica por chave','', 'F02 F03','TC-013 TC-004','A01 A02'),
 ('R02','CORE','Validar configuração declarativa','JSON v1 com jobs, steps, limites e revisão','plano imutável e digest','limitar tamanho, rejeitar campos desconhecidos, caminhos absolutos/.., segredo literal e ciclos; CORE aceita um job','configuração nunca executa no control plane','SchemaError, Cycle, Unsupported','mesmo plano/digest em retries','R01','F01 F02','TC-016 D-PARAM','A02'),
 ('R03','CORE','Fila e histórico duráveis','plano validado e eventos','run/job Queued e audit entry','criar run, job e fila na mesma transação; GET autorizado retorna estados versionados','fila deriva de jobs; nenhum enqueue volátil autoritativo','DatabaseUnavailable','ack somente após commit; restart recupera pendentes','R01 R02','F03','TC-003 TC-004','A03 A06'),
 ('R04','CORE','Agentes autorizados e compatibilidade','credencial, sessão, labels e slot','sessão e capacidade elegível','aprovação administrativa; capabilities declarativas por igualdade e recursos mínimos; pool de confiança fixado pelo servidor','agente não escolhe projeto/pool privilegiado','UnauthorizedAgent, Incompatible, ProtocolMismatch','nenhum job em executor incompatível','R03','F04 F05','TC-008 TC-009 D-MATCH','A04'),
 ('R05','CORE','Reserva exclusiva com lease','job elegível, slot livre e relógio do banco','attempt_id, fence e expires_at','serializar reserva do slot e job; um lease ativo por job e slot; TTL 30s e renovação a cada 10s','fence cresce; escrita obsoleta nunca altera resultado','Busy, LeaseExpired, StaleFence','at most one current owner; não exactly once de processo','R03 R04','F04 F09','TC-005 TC-004','A05 A07'),
 ('R06','CORE','Executar steps e revisão fixada','assignment, argv, ambiente permitido, limites','exit code e status por step','checkout em executor; sem shell implícito; env_clear e allowlist; workspace único; parar após falha','subprocesso não recebe credenciais do agente','SpawnFailed, CheckoutFailed, Timeout, NonzeroExit','resultado explícito; filhos recolhidos','R05 R02','F06 F11','TC-010 D-STEPS D-VCS','A08'),
 ('R07','CORE','Logs com limite e retomada','attempt/fence, stream, seq, bytes','ack durável por sequência','máximo 64KiB por chunk, spool 64MiB e 100MiB por tentativa; overflow falha explícita; dedup por hash','ack não precede persistência; mesmo seq diferente é conflito','StaleFence, ChunkConflict, LogQuotaExceeded','sem duplicação lógica; gaps visíveis','R05 R06','F07','D-LOG','A09'),
 ('R08','CORE','Publicar artefatos verificáveis','manifesto de caminhos relativos, tamanho, SHA-256 e bytes','objetos staged/committed','rejeitar symlink e traversal; 1GiB por tentativa; commit somente após validar objetos; download autorizado','objeto de outro projeto nunca acessível por digest isolado','DigestMismatch, QuotaExceeded, InvalidPath','sucesso exige artefatos obrigatórios confirmados','R05 R06','F08','TC-012 D-ART','A10'),
 ('R09','CORE','Concluir com idempotência','attempt/fence, resultado, último seq e manifest_digest','resultado terminal versionado','CAS sob lease vivo; repetição idêntica retorna recibo; divergente é conflito; arbitrar cancelamento na mesma linha','um resultado terminal por job; terminal imutável','StaleFence, ResultConflict, MissingOutput','resultado lógico único, eventos de auditoria atômicos','R07 R08','F08 F09','TC-012 TC-011','A11'),
 ('R10','CORE','Recuperar falhas sem ocultar ambiguidade','restart, lease vencido, política retry','Lost ou nova tentativa','registrar perda; repetir só com retry_safe explícito e limite; sem essa política, Lost terminal exige ação humana','tentativa antiga não finaliza nova; efeito externo pode duplicar','DatabaseUnavailable, RetryLimit','entrega pelo menos uma vez; efeitos externos sem exactly once','R05 R09','F09','TC-018 TC-012','A06 A07 A12'),
 ('R11','CORE','Cancelamento e timeout','pedido autorizado ou deadline','CancelRequested e depois Cancelled/TimedOut','persistir intenção; negar conclusão concorrente após intenção; matar grupo/cgroup e reap; deadline efetivo limita lease','reconhecimento da API não implica processo já parado','AlreadyTerminal, Forbidden, KillFailed','intenção durável e terminalização auditável','R05 R09','F09','TC-011','A11 A12'),
 ('R12','CORE','Isolamento e segredos do piloto','pool de confiança, executor, secret references','processo limitado e credenciais efêmeras','piloto só código confiável; nenhum socket Docker do host no job; segredo por projeto/tentativa; revogar após término','credencial de agente fora do workspace; logs não garantem eliminar exfiltração','IsolationUnavailable, SecretDenied','execução recusada quando política não satisfeita','R04 R06','F05 F06','D-AGENT D-AUTH','A13'),
 ('R13','CORE','Auditoria e retenção','eventos, principal e política','histórico, métricas e GC','auditoria transacional sem segredos; retenção piloto 30 dias; GC somente objetos sem referência e fora da janela de upload','GC não remove objeto referenciado por execução retida','StorageUnavailable, AuditFailure','pós-restart mantém histórico; gaps log explícitos','R03 R09','F07 F13','D-LOG D-GC','A03 A14'),
 ('R14','IMPORTANT','DAG de jobs','jobs e depends_on','execução paralela dos elegíveis','revisão fixada por run; rejeitar ciclo; dependência failed cancela descendente como Skipped; limite por projeto','job só elegível após todos predecessores Succeeded','Cycle, DependencyFailed','nenhum sucessor inicia cedo','R02 R05 R09','F10','D-DAG','A15'),
 ('R15','IMPORTANT','Triggers Git','hook assinado ou polling com cursor','pedido deduplicado por repo/revisão/pipeline','validar assinatura, branch e permissão; persistir cursor com pedido; revisão resolvida uma vez','retry do hook não cria run extra','SignatureInvalid, RevisionUnavailable','polling recupera hook perdido','R01 R06','F11','D-VCS','A16'),
 ('R16','IMPORTANT','Múltiplos agentes e equidade por projeto','pools e quotas','distribuição justa','round-robin entre projetos ativos; FIFO dentro do projeto; quota configurável','não superar slots/limites; espera medida só para elegíveis','CapacityUnavailable','nenhum projeto elegível ignorado por mais de um ciclo finito','R05 R14','F04','TC-005 D-QUEUE','A17'),
 ('R17','ADVANCED','Cache com domínio de confiança','namespace, key, digest e política','cache hit verificado ou miss','chave inclui entradas/toolchain; PR não confiável não escreve cache de release','cache é otimização, miss mantém correção','DigestMismatch','cache corrupto nunca aceito silenciosamente','R08 R12','F13','D-CACHE','A18'),
 ('R18','IMPORTANT','Executar código não confiável','política OCI endurecida ou VM','job isolado ou recusa','gate com testes de escape de filesystem, rede, privilégios, recursos e segredos; hosts efêmeros','sem fallback para processo irrestrito','IsolationUnavailable','limites aplicados externamente ao subprocesso','R12','F06','D-AGENT','A13'),
 ('R19','DEFERRED','Extensibilidade externa e UI','contratos versionados e eventos','integrações sem código no controle','extensão em processo separado; UI usa API autorizada','plugin não lê memória de controle','ProtocolMismatch','ABI Rust dinâmica não exigida','R01 R09','F12','TC-014','A19'),
 ('R20','REJECTED','Compatibilidade JVM/DSL/protocolo privado','solicitação de importar internals','erro de capacidade não suportada','não carregar plugin JVM, compilar Kotlin ou espelhar protocolo interno','nenhum código proprietário incorporado no produto','UnsupportedCompatibility','especificação independente','', 'F12','TC-014 TC-016','A20'),
]
reqs=[]
for i,priority,obj,inputs,outputs,rules,inv,errors,guarantees,deps,fs,es,acs in rows:
    reqs.append(dict(id=i, priority=priority, objective=obj, motivation='Resolver: '+next(f[4] for f in features if f[0] == fs.split()[0]),
        inputs=inputs, outputs=outputs, rules=rules, invariants=inv, errors=errors,
        intended_guarantees=guarantees, dependencies=deps.split(), features=fs.split(), evidence=es.split(), acceptance=acs.split()))
dump('02-independent-specification/requirements/requirements.json', reqs)
write('02-independent-specification/requirements/catalog.md', '# Requisitos independentes\n\n' + '\n\n'.join(
    f"## {r['id']} — {r['objective']} [{r['priority']}]\n\n" + '\n\n'.join(
        f'**{k}:** '+(', '.join(v) if isinstance(v,list) else str(v)) for k,v in r.items() if k not in ['id','objective','priority']) for r in reqs))

write('02-independent-specification/domain-model/model.md', '''
# Modelo de domínio independente

Project tem escopo de autorização e quota. PipelineRevision é imutável, com digest e schema_version.
Run é uma invocação sobre revisão de configuração e source_revision fixadas; contém Jobs.
Job é a unidade de scheduling, contém Steps ordenados e política de retry. Attempt é uma execução física do Job.
AgentSession identifica um processo registrado; Slot é capacidade reservável. Lease dá autoridade temporária
sobre Attempt, com fence monotônico por Job. Logs e ArtifactManifest pertencem à tentativa, nunca apenas ao Job.
SecretRef identifica autorização de segredo, sem valor na configuração. AuditEvent registra mudança de domínio.

IDs distintos: ProjectId, RunId, JobId, AttemptId, AgentId, SessionId, SlotId; não intercambiáveis.
CompletedAttempt conserva outcome e recibo mesmo após vencimento do lease, para responder retry idêntico.
Release/deploy é step com efeito externo; deve usar chave idempotente do destino ou aprovação própria.
''')
write('02-independent-specification/invariants/invariants.md', '''
# Invariantes normativos

I01: ack de submissão implica run, jobs, chave de idempotência e auditoria confirmados juntos.
I02: no máximo uma tentativa com lease ativo por job e uma reserva por slot.
I03: fence novo é estritamente maior; nenhuma mensagem antiga altera estado, logs ou manifesto atual.
I04: estado terminal é imutável. Retry cria Attempt novo; nunca reabre uma tentativa terminal.
I05: configuração e revisão Git não mudam dentro de Run.
I06: sucesso exige exit codes válidos, logs confirmados até last_seq e manifesto obrigatório committed.
I07: autenticação não substitui autorização por projeto/pool/attempt.
I08: banco é autoridade de tempo e estado; canais e notificações não são fontes duráveis.
I09: cancelamento e conclusão disputam o mesmo lock/CAS; primeira transação válida prevalece.
I10: efeitos externos não são exatamente uma vez; lease controla aceitação lógica, não desfaz comandos.
I11: GC não remove objetos referenciados; acesso a bytes sempre verifica escopo do projeto.
I12: limites de memória, disco, log, tempo e concorrência são aplicados e observáveis.
''')
write('02-independent-specification/workflows/minimum-ci.md', '''
# Primeira fatia: do pedido ao resultado

1. API autentica e autoriza principal no projeto; recebe JSON v1, revisão Git fixa e chave idempotente.
2. Valida estrutura, limites, referências e compatibilidade de schema. Erro retorna 4xx sem execução parcial.
3. Transação cria Run, Job Queued, revisão imutável e auditoria; só então responde 202 com run_id.
4. Agente autorizado faz poll; scheduler escolhe job compatível e slot livre.
5. Na mesma transação bloqueia slot e job, cria Attempt, incrementa fence e registra lease/atribuição.
6. Poll retorna assignment; se resposta se perder, mesmo slot/sessão recebe a mesma atribuição viva.
7. Agente confirma início com fence; prepara workspace e checkout; nunca executa antes desse ack.
8. Steps executam argv em ordem com ambiente permitido e limites. stdout/stderr usam pipes drenados em paralelo.
9. Chunks numerados vão ao spool e servidor; ack é durável. Overflow gera falha explícita.
10. Agente sobe arquivos declarados a staging, servidor valida hash/tamanho/caminhos e confirma manifesto.
11. Complete inclui outcome, last_seq e manifest_digest. Sob lock/lease válido, verifica outputs e terminaliza.
12. Audit entry e release de slot são atômicos ao terminal. Agente recebe recibo e limpa workspace.

Crash antes de commit: retry seguro da API. Crash depois de commit: dedup retorna recibo.
Rede perdida: não iniciar trabalho novo sem ack; execução ativa obedece deadline local conservador.
Lease expirada: servidor marca Lost; retry exige política explícita. Artefatos antigos ficam sem publicação corrente.
Processo que já causou efeito externo pode continuar sob partição; usar isolamento e idempotência no destino.
''')

acceptance = [
 ('A01','Repetir submissão','10 pedidos mesma chave e payload criam um run; payload diferente retorna 409'),
 ('A02','Configuração inválida','ciclo, campo desconhecido, caminho ../, segredo literal e tamanho excedido retornam 422 sem jobs'),
 ('A03','Durabilidade e auditoria','reiniciar após 202 mantém fila/histórico; falha no commit retorna 503 sem ack'),
 ('A04','Autorização e compatibilidade','agente não aprovado, pool incorreto e labels incompatíveis recebem zero atribuições'),
 ('A05','Reserva concorrente','100 polls simultâneos em 2 schedulers reais: um owner por job/slot; índices únicos e transações preservados'),
 ('A06','Restart e resposta perdida','matar controle antes/depois do commit; poll repetido retorna assignment vivo ou trabalho pendente correto'),
 ('A07','Fencing e expiração','expirar lease e reatribuir; complete/log/upload do fence antigo recebem 409 sem mutar estado'),
 ('A08','Processos','step não zero impede próximo; timeout mata descendentes; checkout fixa commit; env herdado não vaza'),
 ('A09','Logs sob falha','duplicar chunk idêntico retorna ack; bytes divergentes conflitam; gap impede sucesso; spool cheio falha com razão'),
 ('A10','Artefatos','hash divergente, symlink, traversal, quota e acesso cruzado recusados; sucesso só com manifesto confirmado'),
 ('A11','Cancelamento versus conclusão','testar as duas ordens de commit; intenção persistida primeiro impede sucesso; conclusão primeiro retorna AlreadyTerminal'),
 ('A12','Partição e retry','agente some; lease vence; Lost por padrão; retry_safe cria nova tentativa limitada; destino externo pode ver duplicação'),
 ('A13','Segurança','processo tenta ler credencial do agente, montar host, usar socket Docker, esgotar RAM/CPU e acessar segredo de outro projeto; política recusa ou contém'),
 ('A14','Retenção e backup','GC durante upload não apaga objetos vivos; restore de banco+objetos reproduz resultados e detecta objetos faltantes'),
 ('A15','DAG','A→{B,C}→D: D aguarda ambos; falha em A gera Skipped; ciclo rejeitado antes de enqueue'),
 ('A16','VCS','hook duplicado cria um run; assinatura inválida rejeitada; polling recupera revisão perdida'),
 ('A17','Fairness','3 projetos, backlog contínuo e slots compatíveis: todos recebem turno em cada ciclo de round-robin'),
 ('A18','Cache','digest corrupto vira miss; pool não confiável não publica em namespace de release'),
 ('A19','Extensões futuras','versão incompatível não carrega; crash da extensão não termina controle'),
 ('A20','Independência','entrada de plugin JVM/Kotlin devolve Unsupported; produto não incorpora classes ou pseudocódigo TeamCity'),
]
dump('02-independent-specification/acceptance-criteria/scenarios.json', [dict(id=i, trigger=t, expected=e, status='specified_not_executed') for i,t,e in acceptance])
write('02-independent-specification/acceptance-criteria/scenarios.md', '# Aceitação do produto futuro\n\n' + '\n\n'.join(f'**{i} — {t}:** {e}.' for i,t,e in acceptance) + '\n\nSão contratos; testes do protótipo são um subconjunto explicitamente separado em 04-validation/findings.')

adrs = [
 ('001','Monólito modular e agentes separados','R01 R03 R06 R12','controle único versus microsserviços versus processo único com executor','Monólito modular; agente em processo/host separado','Transações locais e operação simples; execução exige fronteira de falha e credenciais separada. Escalar só após medição.','Menor isolamento entre módulos de controle; crescimento futuro pode exigir particionamento.'),
 ('002','Fila SQL e PostgreSQL','R03 R05 R10','PostgreSQL versus SQLite versus broker externo','PostgreSQL para controle; SQLite opcional para laboratório de processo único','Reservas concorrentes e durabilidade exigem transações; SQL evita coordenar broker e estado. SQLite reduz operação mas escritor único limita cenário multiworker.','Operar backup, pool e migrações; não introduzir event sourcing ou outbox no CORE.'),
 ('003','HTTP JSON e TLS','R04 R05 R07','HTTP JSON versus Tonic/gRPC versus protocolo privado','HTTP versionado com pull/long poll e chunks binários; TLS Rustls no agente','Inspeção e retries simples no piloto; gRPC só se contratos gerados/streaming comprovarem benefício.','Mais validação manual de schema e overhead JSON; sem alegação de desempenho.'),
 ('004','Scheduler por elegibilidade e FIFO','R04 R05 R16','FIFO versus prioridade versus fair/weighted versus duração estimada','FIFO por projeto no CORE de um projeto; round-robin de projetos no IMPORTANT','Ordem verificável e ausência de prioridade que cause starvation; SKIP LOCKED evita espera em linhas ocupadas.','Não otimiza makespan ou localidade; fairness somente entre trabalhos elegíveis e sob capacidade disponível.'),
 ('005','JSON declarativo e DAG','R02 R14 R20','JSON versus YAML versus TOML versus DSL Rust e UI geradora','JSON v1 canônico como contrato inicial; UI/TOML podem gerar o mesmo IR depois','Sem código arbitrário no controle; parser único reduz ambiguidade; DAG separado do formato.','JSON é verboso; DSL Rust exigiria compilação externa e política de confiança.'),
 ('006','Lease com fencing e retry explícito','R05 R09 R10 R11','execução única presumida versus reentrega automática versus lease/fence','at least once de entrega; uma autoridade lógica atual; retry_safe opt-in','Partições tornam efeitos externos ambíguos; CAS e tokens impedem resultado obsoleto sem prometer desfazer processo.','Disponibilidade e trabalho duplicado têm custo; Lost pode exigir decisão humana.'),
 ('007','Execução com isolamento progressivo','R06 R12 R18','processo versus namespaces/cgroups/seccomp versus OCI versus VM/microVM','piloto confiável com usuário dedicado e recursos limitados; OCI endurecido para ampliação; VM para hostil','Não acoplar segurança a memória segura de Rust; separar credenciais do executor e do job.','OCI compartilha kernel; política requer testes no host; VMs aumentam tempo e operação.'),
 ('008','Logs e objetos fora das linhas de domínio','R07 R08 R09 R13','blobs no SQL versus filesystem local versus object storage','filesystem durável no piloto em nó único; adaptador de objetos futuro; metadados SQL','Chunks e manifestos têm identidade, quota e confirmação; separar staging de referência committed.','Sem transação distribuída; reconciliação e GC conservador obrigatórios; filesystem local impede HA imediata.'),
 ('009','Rust com dependências seletivas','R01 R05 R06','std somente versus stack async versus abstrações genéricas','domínio std; Tokio/Axum/Tower/Serde/SQLx/Tracing/Rustls na infraestrutura','I/O concorrente justifica async; domínio permanece síncrono e testável; SQL explícito facilita auditar locks.','Compatibilidade e MSRV devem ser fixadas em lockfile na implementação; candidatos consultados não foram compilados juntos.'),
]
for i,title,rs,alts,choice,why,cost in adrs:
    write(f'03-rust-architecture/decisions/ADR-{i}.md',f'''# ADR-{i} — {title}

Status: proposta recomendada para implementação. Data: 2026-10-30.

Requisitos: {rs}.

Alternativas: {alts}.

Decisão: {choice}.

Justificativa independente: {why}

Consequências e limitações: {cost}

Validação: cenários associados aos requisitos em requirements.json e gates do plano incremental.
Fontes: ../components/technology-evaluation.md e ../../01-teamcity-research/evidence/sources.md.
Revisar quando workload, modelo de ameaça ou SLO contradisser a hipótese de implantação.
''')

write('03-rust-architecture/components/architecture.md', '''
# Componentes e fronteiras

```mermaid
flowchart LR
  User[Usuário / integração] --> API[API e autorização]
  subgraph Control[Control plane: monólito modular Rust]
    API --> Domain[Configuração e domínio]
    Domain --> Scheduler[Scheduler e reconciliação]
    API --> Outputs[Logs e manifestos]
  end
  Domain --> DB[(PostgreSQL)]
  Scheduler --> DB
  Outputs --> Objects[(Filesystem durável / objetos)]
  Outputs --> DB
  Agent[Agente Rust separado] -->|HTTPS pull / heartbeat / resultado| API
  Agent --> Sandbox[Executor isolado e subprocessos]
```

Crates propostas: domain (IDs, estados, regras puras), control (casos de uso), persistence (SQL e migrações),
transport (DTOs e auth), agent (supervisor), executor (processos e isolamento). Não criar um trait por entidade.
Traits somente em portas com alternativa real: ArtifactStore, Clock de testes, Executor.
Não compartilhar estruturas ORM ou DTOs como modelo de domínio. Erros DomainError/StorageError/TransportError
viram códigos estáveis, sem SQL, segredo ou stack trace para cliente. API: 401/403/409/413/422/429/503.

Responsabilidade persistente: PostgreSQL. Responsabilidade de executar: agente. Autoridade de resultado: controle.
Processo do job não acessa banco, token do agente, diretórios de outros jobs ou control plane administrativo.
Um slot por agente no CORE evita sobreposição local; ampliar slots exige contabilidade real de CPU/memória/disco.
''')

tech = [
 ('Tokio 1.54.1','runtime de I/O','std threads','T-TOKIO','Escolher controle/agente; features mínimas runtime, sync, time, process, io. Tasks supervisionadas; blocking fora do executor.','Runtime e features transitivas; memória por task/channel precisa limite; API estável não elimina mudanças entre releases.'),
 ('Axum 0.8.9 + Tower 0.5.3','API, timeouts e limites','servidor HTTP manual','T-AXUM T-TOWER','Escolher; extratores e middleware, timeout e limite de requests.','Tokio/HTTP stack; pré-1.0 exige revisão de upgrade e ajuste de handlers; timeout não cancela commit já executado.'),
 ('Tonic 0.14.9','RPC e streaming','HTTP JSON','T-TONIC','Adiar: HTTP atende primeiro fluxo; reconsiderar clientes gerados.','HTTP/2, Protobuf e geração ampliam build e operação; não há benchmark justificando troca.'),
 ('Serde','DTO e formato JSON','parser manual','T-SERDE','Escolher; deny_unknown_fields e limites antes de desserializar.','Formato é crate separado; derive gera código; validar semântica depois do parse; custo por payload a medir.'),
 ('SQLx 0.9.0','SQL async explícito','Diesel e driver direto','T-SQLX','Escolher candidato PostgreSQL; pool limitado e migrações versionadas.','Runtime/TLS/drivers opcionais; macros podem exigir metadata/banco no build; upgrade pré-1.0 requer integração.'),
 ('Diesel','query builder tipado','SQLx','T-DIESEL','Alternativa válida; não selecionada porque transações/locks específicos serão SQL explícito.','Schema gerado e features de backend; adapter async separado se necessário; não afirmar inferioridade de desempenho.'),
 ('Tracing 0.1.44','spans e eventos operacionais','logs std','T-TRACING','Escolher; campos run/job/attempt/fence; nunca segredo.','subscriber e exporter separados; cardinalidade e buffers limitados; audit trail durável é outro mecanismo.'),
 ('Rustls 0.23.46','TLS cliente/servidor','TLS de proxy no controle','T-RUSTLS','Escolher candidato do agente; controle pode terminar TLS em proxy de confiança definido.','Selecionar provider crypto e certificados explicitamente; rotação e validação de nomes; pré-1.0 e integração a testar.'),
 ('Petgraph 0.8.3','algoritmos de grafos','Kahn com Vec e índices','T-GRAPH','Adiar; validação topológica O(V+E) própria basta ao DAG estático pequeno.','Mais representações/algoritmos do que o CORE exige; introduzir só com necessidade medida.'),
 ('std::process / tokio::process','execução de comandos','executor OCI externo','T-STD T-PROCESS','std serve protótipo; Tokio para múltiplos pipes e deadlines no agente.','Drop não garante encerramento; kill/wait e descendentes exigem controle; execução não é isolamento.'),
 ('cargo test','testes básicos','runner externo','T-STD','Escolher inicialmente; sem dependências de teste no protótipo.','Sem detecção automática de todas as interleavings; integração real ainda necessária.'),
 ('cargo-nextest','execução e isolamento de testes','cargo test','T-NEXTEST','Adiar até suite justificar; não usar promessa upstream de velocidade como medida local.','Binário adicional, configuração e comportamento de runner devem ser fixados.'),
 ('proptest 1.12.0','geração de sequências','enumeração determinística','T-PROP','Adotar ao ampliar domínio; gravar seeds e regressões.','Shrinking e estratégias consomem tempo; não demonstra consistência distribuída sozinho.'),
 ('loom 0.7.2','interleavings em memória','transações reais PostgreSQL','T-LOOM','Adiar salvo sincronização custom; banco requer teste concorrente próprio.','Primitivas instrumentadas e explosão combinatória; não modela rede/banco real automaticamente.'),
]
write('03-rust-architecture/components/technology-evaluation.md', '# Avaliação tecnológica\n\nAs documentações oficiais estavam acessíveis em 2026-10-30. Isso demonstra disponibilidade documental, não saúde completa de manutenção. Antes do lockfile: conferir releases, MSRV, advisories e compatibilidade de features. Nenhuma superioridade de memória/desempenho foi medida.\n\n' + '\n\n'.join(
 f'## {n}\n\nUso: {use}. Alternativa: {alt}. Fontes: {src} em sources.md.\n\nDecisão: {decision}\n\nDependências, estabilidade, custos e limitações: {cost}' for n,use,alt,src,decision,cost in tech) + '''

PostgreSQL é o banco recomendado para múltiplos workers: locks de linha e transações (T-PG).
SQLite é alternativa para piloto de processo único com escrita serializada (T-SQLITE); não assumir equivalência de SQL/locks.
Não foram adicionadas dependências ao setup TeamCity nem ao protótipo. Versões observadas não são pins de implantação.
''')

write('03-rust-architecture/domain-types/states.md', '''
# Tipos e transições

IDs são newtypes, deadlines usam tipo próprio e fence inteiro monotônico checado contra overflow.
Enuns com payload evitam Running sem AttemptId/Lease. Campos privados e construtores validados.
Erros recuperáveis são Result; ausência explícita usa Option. Sem unsafe, hierarquia de herança ou estado global mutável.
Ownership: supervisor possui child handles; writer possui transações; mensagens carregam IDs e valores imutáveis.

| Entidade | Transições válidas | Regra de falha/recuperação |
|---|---|---|
| Run/build | Accepted → Queued → Running → Succeeded/Failed/Cancelled/Lost | agregado dos jobs; terminal imutável |
| Job | Blocked → Queued → Leased → Running → Uploading → Succeeded/Failed | Blocked só libera por pré-requisitos; erro de infraestrutura vira Lost |
| Job cancelado | Queued → Cancelled; ativo → CancelRequested → Cancelled/TimedOut | expiração durante cancelamento encerra sem reagendar |
| Pipeline | Validated → Active → Succeeded/Failed/Cancelled | ciclo inválido nunca entra em Validated |
| AgentSession | Pending → Approved → Online → Draining/Offline/Revoked | sessão nova não herda lease de sessão velha automaticamente |
| Lease | Active → Expired/Released/Revoked | terminal; renovar somente Active antes de expirar |
| Attempt/execution | Assigned → Started → Uploading → Finished/Lost/Cancelled/TimedOut | retry cria outro ID e fence |
| Cancellation | Absent → Requested → Acknowledged → Completed | conclusão anterior prevalece; confirmação API não é morte física |
| Artifact | Declared → Uploading → Staged → Verified → Committed → Deleted | falha vira Rejected; GC só sem referências |

Qualquer outra transição é InvalidTransition. Complete direto de Running pode ser permitido quando não há arquivos,
mas ainda verifica manifesto vazio e logs. Retry não reabre Attempt: Job volta a Queued apenas com política aprovada.
Em Lost sem retry, Run termina Lost; não confundir falha de infraestrutura com exit code do build.
Representação serializada possui schema_version; DB valida relações e domínio valida transições completas.
O protótipo cobre somente a submáquina Queued/Leased/CancelRequested/Terminal, sem prometer a implementação destes estados todos.
''')
write('03-rust-architecture/concurrency/tasks.md', '''
# Concorrência, supervisão e limites

Tokio somente para I/O: API, poll, heartbeat, pipes e uploads. Domínio é síncrono.
Supervisor mantém JoinSet de tasks, propaga cancelamento e espera término; panic de worker é observado e reinicia com backoff limitado.
Channels bounded por bytes e quantidade: defaults piloto 128 mensagens e 8MiB de dados por stream; limites precisam medição.
Spool em disco tem quota própria. Nunca soltar logs silenciosamente; bloquear com deadline e falhar LogQuotaExceeded.
Semáforo limita uploads e spawn_blocking; hashes/arquivos grandes processados em chunks e pool bloqueante limitado.
Não manter locks Rust através de await; locks SQL apenas em transações curtas sem rede, spawn ou upload dentro.
Heartbeat usa pool reservado/limites separados para não ser bloqueado por upload. Controle usa relógio do banco;
agente converte tempo restante em deadline monotônico conservador. TTL expira localmente mesmo sem resposta do servidor.
No shutdown: parar novos polls, sinalizar filhos, aguardar prazo, kill grupo/cgroup e reap; lease não é liberado antes de parada confirmada.
Arc é só compartilhamento de handles imutáveis/pool; evitar Arc<Mutex<domínio inteiro>>.
''')
write('03-rust-architecture/scheduling/scheduler.md', '''
# Scheduler independente

Unidade: Job elegível; Attempt é identidade física, Step não ocupa slot independente.
CORE: um projeto/pool e FIFO por enqueue_seq entre compatíveis. IMPORTANTE: round-robin persistido entre projetos,
FIFO interno, quotas por projeto. Critério de desempate determinístico: enqueue_seq e job_id.
Compatibilidade: conjunto explícito de labels igualdade + CPU/memória/disco mínimos + política de confiança do servidor.
Não avaliar código/regex arbitrário durante matching; agentes não autenticam privilégios pela própria declaração de capabilities.

| Alternativa | Benefício | Limitação / decisão |
|---|---|---|
| FIFO | simples, espera por ordem | usar entre elegíveis; jobs incompatíveis não bloqueiam todos |
| Prioridades | urgência | starvation sem aging; adiar |
| Fair round-robin | acesso por projeto | escolher na ampliação; um job longo ainda ocupa slot |
| Weighted fair | quotas proporcionais | adiar até necessidade multitenant medida |
| Capacidade / best fit | aproveitamento de recursos | filtrar requisitos primeiro; otimização posterior |
| Duração estimada | pode reduzir makespan | erro, cold start e starvation; exige workload/benchmark |

Reserva: transação bloqueia slot livre e job elegível, revalida limites, incrementa fence e cria Attempt/Lease.
Locks sempre na mesma ordem (slot, quota do projeto quando houver, job); workers usam SKIP LOCKED e repetem candidatos.
Não selecionar lote pequeno fixo que possa ocultar candidatos compatíveis indefinidamente: paginar elegíveis até achar candidato
ou orçamento por rodada; cursor avança e é retomado, com espera diagnosticada. Capacidade ignorada é métrica de bug.
Fairness só é garantida com conjunto finito de projetos elegíveis, serviço recorrente e capacidade compatível disponível.
TTL inicial 30s, heartbeat 10s; valores são hipóteses do piloto, não números derivados do TeamCity.

Expiry worker usa timestamp do banco, bloqueia job e slot e marca Lost/Expired; retry_safe permite nova tentativa com backoff
e máximo 2 tentativas no piloto. Lease expirada nunca é renovada. Complete e expiry competem pelo mesmo lock.
Mensagens duplicadas são esperadas. Fencing vale para logs, resultado e commit de manifesto, não apenas assignment.
Entrega pelo menos uma vez; execução física pode duplicar; resultado aceito tem uma autoridade corrente.
Métricas: latência p50/p95/p99 de elegível→lease, idade por projeto, incompatibilidade, leases vencidas e rejeições stale.
''')
write('03-rust-architecture/storage/consistency.md', '''
# Persistência e recuperação

Schema independente proposto: projects; pipeline_revisions; runs; jobs; attempts; agent_sessions;
slots; leases; submission_keys; log_chunks; artifact_manifests; artifact_objects; audit_events.
Não reproduz tabelas do TeamCity. IDs externos opacos; FK por projeto quando houver escopo.
Constraints: chave única (project,idempotency_key); unique parcial de lease Active por job e slot;
unique (attempt,stream,seq); unique attempt/result receipt; fence e version checados em updates.
Unique parcial não avalia tempo: expiry muda Active para Expired sob lock antes de uma reserva nova.

Transações: submissão+auditoria; reserva+attempt+lease+slot; renovação; conclusão+auditoria+release;
cancelamento; expiração+retry. READ COMMITTED com locks explícitos e predicates reavaliados.
Operações leem relógio DB após adquirir locks (clock_timestamp), não timestamp antigo do início de transação.
Ordem uniforme slot→quota→job para operações que tocam múltiplas linhas; consultas isoladas não invertem essa ordem.
CAS verifica job.version, attempt_id, fence, session_id, status e expires_at > clock_timestamp().
Nenhuma chamada externa ocorre dentro de transação de domínio. Deadlock/serialization failure tem retry limitado.

Logs/objetos: escrever temporário, fsync arquivo, rename e fsync diretório; só então gravar índice SQL e ack.
Se crash antes de índice: sobra órfão para GC. Se índice confirmar: bytes duráveis já existem.
Uploads são por tentativa; hash de conteúdo não concede acesso. Manifesto vira Committed na transação de conclusão,
após verificar objetos staged. Sucesso espera logs confirmados até last_seq, inclusive stdout/stderr declarados.
Storage indisponível impede sucesso e ack; quota encerra com razão explícita. Retenção inicial 30 dias, configurável.
GC marca candidato sem referência, espera janela maior que upload/lease e revalida sob coordenação antes de apagar.

Restart: reconstruir fila de jobs; expirar leases pelo relógio DB; reconciliar upload/objetos órfãos;
retry nunca perde tentativa anterior. Banco indisponível: sem leases novos, sem renovação/resultado; agente para no deadline.
Migrations expand/contract com backup e teste de restore; schema incompatível recusa startup.
Backup registra ponto do DB e snapshot de objetos; restore verifica referências e reporta missing objects.

Eventos: audit_events na mesma transação; UI lê SQL com cursor. Notificações em memória só aceleram poll.
Não há broker nem necessidade de outbox no CORE. Se webhooks externos exigirem entrega confiável,
adicionar outbox transacional com consumidor idempotente e retenção; sem event sourcing integral.
''')
write('03-rust-architecture/protocols/agent-v1.md', '''
# Protocolo próprio v1

HTTPS; JSON para metadados e bytes para chunks/objetos. Limites explícitos, request_id e correlation_id sem segredos.
Bootstrap de agente usa token de uso único entregue por administrador; cria sessão aprovada e credencial curta por pool.
Sessão fica vinculada a AgentId e pool; rotação/revogação fecha polls e impede renovar leases. Usuário usa token com escopo por projeto.
Versionamento `/v1`; handshake negocia schema e capabilities suportadas, versão incompatível recebe erro antes de assignment.

| Operação | Contrato |
|---|---|
| POST /v1/runs | chave de idempotência por projeto + config_revision + source_revision; 202 só após commit |
| GET /v1/runs/{id} | autorizado por projeto, estado versionado e tentativas |
| POST /v1/agents/handshake | sessão autenticada, capacidades e limites aceitos pelo controle |
| POST /v1/agents/poll | session/slot; long poll até 20s; assignment vivo repetível por sessão/slot |
| POST /v1/attempts/{id}/start | fence + sessão; ack válido obrigatório antes de spawn |
| POST /v1/attempts/{id}/heartbeat | fence; renova só antes de expiry; retorna deadline e cancel intent |
| PUT /v1/attempts/{id}/logs/{stream}/{seq} | fence + hash + bytes; duplicata igual ack, diferente 409 |
| PUT /v1/attempts/{id}/objects/{digest} | fence, tamanho e bytes com limites; staging |
| POST /v1/attempts/{id}/manifest | fence + caminhos relativos e digests verificados |
| POST /v1/attempts/{id}/complete | fence + outcome + last_seq + manifest_digest; recibo persistido |
| POST /v1/runs/{id}/cancel | autorização e chave; intenção durável; retorna 202, sem alegar morte física |

Todas mutações de tentativa vinculam (agent_session, attempt_id, fence). Retries de rede com jitter exponencial limitado
entre 250ms e 5s não podem superar deadline do lease. 409 StaleFence encerra execução local; 429 respeita Retry-After;
503 permite retry dentro do prazo. Resposta perdida de complete retorna recibo idêntico mesmo após expiry;
conteúdo divergente é ResultConflict. Progresso não é autorização para continuar além do lease.
No CORE agente não assume trabalho de sessão anterior após restart: espera reconciliação/expiry e encerra órfãos locais.
''')
write('03-rust-architecture/execution/agent.md', '''
# Agente e formato de pipeline

Supervisor: registrar → autenticar → anunciar capacidades → poll → reservar recursos locais → start ack → preparar
workspace → checkout revisão fixa → executar → drenar logs → subir outputs → complete → limpar.
Manifesto de workspaces local permite detectar órfãos no restart; nunca confiar em arquivo de PID sem identidade da execução.
Use processo/grupo/cgroup identificado, evitando matar PID reutilizado. Diretórios por AttemptId fora do diretório do agente.
Limites: deadline por step/job; CPU/memória/pids/disco do pool; rede conforme confiança.
stdout/stderr são drenados paralelamente; kill inclui descendentes; wait recolhe processo e valida cleanup.
Segredos só na etapa que precisa; arquivos temporários restritos e removidos. Não prometer redaction contra código malicioso.

Configuração independente, exemplo de contrato (não é parser implementado):

```json
{"schema_version":1,"source":{"url":"https://example.invalid/repo.git","revision":"FULL_COMMIT_ID"},
 "jobs":[{"id":"test","needs":[],"requirements":{"os":"linux","arch":"x86_64"},
 "timeout_seconds":600,"retry_safe":false,"steps":[{"argv":["cargo","test","--locked"],"cwd":"."}],
 "artifacts":[{"path":"reports/result.txt","required":true}]}]}
```

JSON é transporte inicial e IR tipado é separado. Validar limites antes de construir DAG;
revisão deve resolver identificador completo validado no checkout. CORE um job; IMPORTANT DAG estático.
Paralelismo existe entre jobs; steps sequenciais. Condições iniciais apenas all_success/always no domínio validado;
expressões arbitrárias rejeitadas. Matriz expande antes do scheduling com teto de jobs; adiada.
YAML tem familiaridade mas mais regras de parse; TOML é legível porém verboso em DAGs;
DSL Rust exige executar compilador fora do controle; UI pode gerar JSON, sem mudar semântica.
Artefatos intermediários são dependências explícitas por manifesto; cache e matriz não bloqueiam primeiro fluxo.
''')
write('03-rust-architecture/security/threat-model.md', '''
# Modelo de ameaças e gates

Ativos: credenciais de agente, segredos de projetos, integridade de resultado, código, artefatos e disponibilidade.
Adversários: usuário sem permissão, build com código hostil, agente comprometido, rede intermediária e consumidor de artefato.
Fronteiras: usuário/API; agente/controle; supervisor/job; projetos entre si; metadados/armazenamento.

| Ameaça | Política inicial | Gate de ampliação |
|---|---|---|
| Escape de processo | piloto com repositórios e código confiáveis, usuário dedicado | OCI endurecido com namespaces, cgroups, seccomp, drop capabilities e filesystem restrito |
| Kernel compartilhado | pools separados por confiança | VM/microVM efêmera para workloads hostis conforme modelo de implantação |
| Roubo de segredo | referências autorizadas, credenciais efêmeras, token do agente fora do job | rede/egress controlados; teste de exfiltração; nunca confiar só em masking |
| Agente mente sobre execução | autorização e trilha de identidade | não há atestação de execução no CORE; agente é parte confiável |
| DoS | quotas CPU/RAM/pids/disco/log/artefatos, HTTP limitado | medir saturação e impedir starvation de heartbeat |
| Traversal/symlink | resolver com operações relativas a diretório e negar symlinks | testes TOCTOU no coletor; canonicalize isoladamente é insuficiente |
| Cross-project | autorização por objeto/projeto e pool | testes negativos em cada endpoint, inclusive download por digest |
| Rede hostil | TLS com validação de nome e rotação | revogação efetiva e nenhuma opção insegura automática |

OCI é especificação de recursos e isolamento, não garantia de contenção absoluta. Processo dedicado sozinho não
isola código não confiável. Nunca montar socket Docker do host nos jobs. Código hostil só habilitado depois de A13.
Sem prova jurídica de independência: código do protótipo usa contratos próprios, sem copiar pseudocódigo/recursos TeamCity.
''')

write('04-validation/tests/strategy.md', '''
# Estratégia de validação

Executado nesta entrega: integridade de evidência reaproveitada, validação referencial dos documentos e
protótipo Rust std de submáquina de leases. Resultados e comandos em findings/validation.json.
O protótipo valida contratos de domínio sob relógio explícito; não valida banco, transporte, subprocessos ou isolamento.

Para implementação: cargo test em transições e propriedades; sequências geradas com proptest quando houver domínio completo.
Integração PostgreSQL real para corrida de polls, cancel/complete/expiry e crash antes/depois de commit.
Agent/process: workspace sintético e processo com descendentes, pipes volumosos e saída não zero.
Distribuído: proxy de falhas para partição, duplicação, atraso e reset; reiniciar agente, controle e banco nos pontos de commit.
Segurança: A13 em host descartável; nenhuma execução hostil na instalação operacional.
loom apenas para estruturas concorrentes em memória customizadas, sem substituir teste transacional.
Não foi iniciado TeamCity nem utilizado banco/credencial reais.
''')
write('04-validation/benchmarks/plan.md', '''
# Benchmark reproduzível proposto, ainda não executado

Antes de medir: registrar CPU/modelo/cores, RAM, kernel, filesystem/disco, rede/RTT,
versões Rust/crates/PostgreSQL, imagem de executor, limites cgroup e commit/lockfile.
Baseline proposto: controle 4 vCPU/8GiB, DB 4 vCPU/8GiB SSD, 4 agentes 2 vCPU/4GiB,
rede 1Gbps. É ambiente alvo, não hardware desta sessão. Registrar máquinas físicas compartilhadas e ruído.

Workloads: 10k jobs no-op; comandos fixos 1s/30s; DAG fanout 10; incompatibilidade de 50%;
logs 1MiB/s por agente durante 60s; artefatos de 1MiB/100MiB; desconexão de 25% dos agentes.
Warmup 60s, janela 10min, cinco repetições e seeds fixas; publicar raw samples e intervalos de confiança.
Carga aumenta 1/4/16/64 polls e 1/4/16 agentes; não comparar configurações com recursos diferentes.

Métricas: builds/s, pedido→commit, elegível→lease, lease→start, p50/p95/p99;
RSS/CPU por componente, bytes/s de logs/artefatos, spool máximo, idade por projeto,
tempo de recuperação após restart e leases vencidas. Separar tempo do comando de overhead de orquestração.
Metas propostas do piloto: p95 enqueue <200ms a 10 req/s; p95 elegível→lease <2s com slot livre;
zero resultado stale aceito; reconciliação <60s com TTL30s; RSS controle <1GiB no workload de 10k jobs.
São critérios para experimento, não garantias já obtidas. Falha de meta exige profile, não troca especulativa de arquitetura.
Comparação TeamCity somente com versão, hardware, jobs, outputs e política de durabilidade equivalentes; não realizada.
''')
write('implementation-plan.md', '''
# Plano incremental e gates

1. Núcleo testável: tipos, validação JSON, um job e steps, transições e chaves idempotentes. Gate A01/A02 e domínio puro.
2. Persistência: migrações PostgreSQL, API de submissão/consulta, fila SQL, reserva e leases.
   Gate A03–A07 com duas instâncias de scheduler e falhas de commit. Não usar mocks como prova de atomicidade.
3. Agente confiável: handshake, poll/start, checkout fixo, processo limitado, logs numerados e artefatos.
   Gate A08–A10 e build de ponta a ponta em host descartável. Este é o primeiro núcleo funcional do produto.
4. Confiabilidade: cancelamento, deadlines, perda/retry explícito, quotas, backup e retenção.
   Gate A11–A14; primeiro piloto somente após todos gates CORE. Sem credenciais reais nos fixtures.
5. Ampliação: DAG, múltiplos agentes, fairness, triggers Git e relatórios de testes. Gate A15–A17.
6. Workloads não confiáveis: isolamento validado no host e pools por confiança antes de habilitar uso hostil (A13/R18).
7. Capacidades opcionais: cache confiável, extensões externas, UI, matriz, condições e tuning após benchmarks.
   Gate A18/A19 e metas medidas. Multisserviços/HA apenas quando limites do monólito forem demonstrados.

Nenhuma etapa exige recuperar todos os métodos TeamCity. Reabrir investigação somente para dúvida que altere requisito/ADR.
Entregas deste prompt são pesquisa, especificação, arquitetura, contratos, plano e validação de modelo;
o servidor/agente de produção ainda precisa das etapas acima.
''')

# Complete decision matrix, one entry per investigated capability.
matrix = []
for i,n,es,b,p,c,l,d in features:
    related=[r for r in reqs if i in r['features']]
    matrix.append(dict(feature_id=i, feature=n, teamcity_behavior=b, evidence=es.split(), problem=p,
        independent_requirements=[r['id'] for r in related], rust_alternatives=[a[3] for a in adrs if set(a[2].split()) & {r['id'] for r in related}],
        chosen_solution=d, justification='Contrato próprio e menor complexidade conforme ADRs vinculados.',
        improvements='IDs tipados, estado durável e falhas explícitas; benefícios de desempenho ainda não medidos.',
        limitations=l, acceptance=sorted({a for r in related for a in r['acceptance']}),
        adrs=[f'ADR-{a[0]}' for a in adrs if set(a[2].split()) & {r['id'] for r in related}]))
dump('decision-matrix.json',matrix)
write('decision-matrix.md', '# Matriz de decisões\n\nCampos completos em decision-matrix.json; os fatos permanecem separados das escolhas.\n\n| Funcionalidade | Evidências | Requisitos | Solução | ADRs | Aceitação |\n|---|---|---|---|---|---|\n' + '\n'.join(
    '| '+ ' | '.join([m['feature_id']+' '+m['feature'], ', '.join(m['evidence']), ', '.join(m['independent_requirements']),m['chosen_solution'],', '.join(m['adrs']),', '.join(m['acceptance'])])+' |' for m in matrix))

dump('research-state.json', dict(updated_at=datetime.now(timezone.utc).isoformat(), prompt='rea/prompt_v2.md',
    prompt_sha256=hashlib.sha256((ROOT/'prompt_v2.md').read_bytes()).hexdigest(),
    status='research_specification_and_architecture_delivered', target_version='2026.2-239120',
    legacy_preserved=True, reused_claims=len(claims), new_decompilations=0, teamcity_runtime_scenarios_executed=0,
    phases={str(i):'delivered_as_research_and_design' for i in range(1,12)},
    product_implementation='not_implemented; independent domain prototype only',
    uncertainty_policy='legacy gaps accepted or deferred explicitly; production gates remain open',
    production_gates='implementation-plan.md', validation='04-validation/findings/validation.json'))
write('README.md', '''
# Pesquisa para uma plataforma CI/CD independente em Rust

Execução de `rea/prompt_v2.md` em 2026-10-30. Entrega de pesquisa, especificação funcional,
arquitetura recomendada e plano incremental; inclui protótipo executável de contratos de lease.
Não há servidor/agente de produção implementados nem validação dinâmica de TeamCity.

Ordem de leitura:

1. [Auditoria de reaproveitamento](01-teamcity-research/evidence/reuse-audit.md) e [funcionalidades](01-teamcity-research/features/catalog.md).
2. [Requisitos independentes](02-independent-specification/requirements/catalog.md), [fluxo mínimo](02-independent-specification/workflows/minimum-ci.md) e [invariantes](02-independent-specification/invariants/invariants.md).
3. [Arquitetura](03-rust-architecture/components/architecture.md), [tecnologias](03-rust-architecture/components/technology-evaluation.md) e ADRs em `03-rust-architecture/decisions/`.
4. [Scheduler](03-rust-architecture/scheduling/scheduler.md), [persistência](03-rust-architecture/storage/consistency.md), [protocolo](03-rust-architecture/protocols/agent-v1.md) e [ameaças](03-rust-architecture/security/threat-model.md).
5. [Matriz de decisões](decision-matrix.md), [aceitação](02-independent-specification/acceptance-criteria/scenarios.md), [plano](implementation-plan.md) e [validação](04-validation/findings/validation.json).

Decisão recomendada: control plane em monólito modular Rust, PostgreSQL como autoridade de estado/fila,
agentes separados com protocolo HTTP versionado e isolamento por política. Reserva transacional com lease/fence;
entrega pelo menos uma vez, sem promessa de efeitos externos exatamente uma vez.

Reprodução a partir da raiz do repositório:

```bash
python3 rea/build_research_v2.py
python3 rea/ci-platform-research/04-validation/tests/validate.py
```

O primeiro comando regenera documentos desta entrega e revalida hashes selecionados; preserva o corpus legado.
O segundo compila/testa o modelo Rust e verifica rastreabilidade. Consulte os limites do teste antes de interpretar aprovação.
Fontes oficiais estão no [registro de evidências](01-teamcity-research/evidence/sources.md).
''')
print(f'Generated {OUT}; reused {len(claims)} claims; hashes match: {all(v["matches"] for v in validation)}')
