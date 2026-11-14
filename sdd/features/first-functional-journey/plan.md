# Plano incremental

## Consolidação de responsabilidades — 13/11/2026

D-07/R-07/REQ-07: manter os dois executáveis e workspaces. Extrair somente o
contrato público v1 para `application/contracts/`, sem dependência entre domínios
privados Server/Agent. Um descritor JSON de protocolo gera constantes Rust no
OUT_DIR e é importado pelo frontend; não é configuração declarativa Ec. A
ADR-003 registra esta fronteira. Centralizar versões com workspace.dependencies.
Separar administração, HTTP e operações do serviço em módulos privados;
replay fica no domínio com validação de referências sob demanda, preservando a
ordem autenticação → lock → replay → validação → persistência. Não substituir
verificações independentes por confiança no cliente. No frontend, entradas públicas
e catálogo de navegação determinam operação/demonstração, evitando listas paralelas.

Primária software-design; auxiliares software-architecture (fronteira de contrato),
rust (geração e manifests), vue3-engineering (composição/lifecycle) e software-testing
(replay e deriva). Pontes versão 1 em sdd/chain; entradas efetivas em
`.codex/skills/<nome>/SKILL.md`. Identidades por conteúdo serão registradas na
convergência. Alternativa de copiar constantes e comparar valores mantém o custo
manual; workspace único exigiria mudar a supervisão sem necessidade. Rejeitadas.

D-01 / R-01 / REQ-01: parser de eventos yaml-rust2 0.13.0 permite detectar duplicatas, tags e anchors antes de conversão. Evitar dois parsers com resoluções escalares divergentes. Todos os campos de texto são strings de schema, timeout é decimal validado explicitamente; não aplicar coerções implícitas de booleanos/datas do YAML. Parser genérico pertence a configuration; projetos recebem um mapa escalar e validam sem importar configuration. Composição conecta ambos, preservando as dependências da ADR-002.

D-02 / R-02 / REQ-02: publicação proposta com um ponto de commit PostgreSQL e bytes YAML imutáveis no mesmo banco. Não iniciar sincronização Git sem protocolo de recuperação. Implementação ainda pendente.

D-03 / R-03 / REQ-03: autenticação real e autorização antecedem mutações; segurança de sessões e provisão de credenciais ainda devem ser especificadas na implementação com skill pertinente. Nenhum endpoint será exposto com identidade simulada.

D-04 / R-04 / REQ-04: fila/slots/tentativas juntos em orchestration, um banco Server e locks por tentativa. Escolha ainda exige testes SQL, não prova por modelo em memória.

D-05 / R-05 / REQ-05: jobs inicialmente confiáveis no Linux; sem plugin externo. Separar execução, workspace e entrega de resultados, sem prometer isolamento. Implementação ainda pendente.

D-06 / R-06 / REQ-06: visual estruturado para uma etapa, sem canvas decorativo; texto inválido não sobrescreve modelo válido. Revisar ambas as normas antes de implementar tela.

Primária inicial: ec-declarative-resource-schemas, ponte 1, para parser, campos e rejeições. Auxiliar rust, ponte 1, para manifests, limites públicos e testes. Hashes e fontes oficiais consultadas registrados na validação. A seleção inicial não declara execução de skills de segurança, banco ou UI; consultá-las ao trabalhar nessas fronteiras.

Toolchain observada e fixada: Rust 1.99.1, edition 2024, Linux x86_64. Não há promessa de MSRV inferior; somente este compilador é verificado inicialmente. Cargo.lock deve ser versionado. Build usa CARGO_HOME e CARGO_TARGET_DIR isolados em .local/build; não escreve no cache global. YAML parser documentado em https://docs.rs/yaml-rust2/0.13.0/yaml_rust2/parser/index.html ; edition em https://doc.rust-lang.org/edition-guide/rust-2024/index.html . Dependências de frontend/PostgreSQL/HTTP serão fixadas quando seus runners forem exercitados.

## Implementação T002/T003

D-02/R-02: PostgreSQL 18, driver postgres síncrono, transação READ COMMITTED com SELECT FOR UPDATE no projeto; sem retry automático. Recibo persistido resolve commit incerto. Alternativa CAS exigiria coordenar recibos separadamente; Git duplo foi rejeitado. Migration versionada e transacional; trigger impede UPDATE/DELETE de revisões/recibos; banco confiável ainda pode alterar schema.
D-03/R-03: bearer opaco provisionado localmente evita escolher IdP/federação. Token aleatório do SO, hash no banco, expiração pelo relógio PostgreSQL e revogação explícita. Alternativa senha exigiria armazenamento e fluxo de login adicionais. Servidor loopback com quatro workers; sem promessa de produção remota. Autorização e sessão com locks compartilhados até commit; publicação usa lock exclusivo do projeto. Referências reais admitidas por CLI local não executam scripts nem acessam rede.

Skills aplicadas, todas ponte versão 1 e caminho `.codex/skills/<nome>/SKILL.md`: primária ec-configuration-publication (commit, replay, recuperação); auxiliares database-engineering (FK, locks e migration), application-security (sessões, grants e negações), http-api-contracts (rotas/erros), rust (ownership e driver), software-testing (oráculos HTTP/SQL e falhas). Hashes em skills.json na evidência desta fatia. Sem delegação.


## T004: D-04 / R-04 / REQ-04

Primária cicd-scheduler (reservas e recuperação); auxiliares database-engineering (migration/constraints/locks), application-security (credencial e sessão Agent separadas, negações), ec-agent-control-protocol (reconexão/fence/recibos), rust (transações e composição). Pontes versão 1 em sdd/chain/{nome}.md; fontes efetivas .codex/skills/{nome}/SKILL.md; contribuições e SHA-256 em docs/validation/orchestration-20261105/skills.json. Sem delegação.

Escolha: lock advisory transacional global 746232 serializa operações curtas de orchestration, incluindo cancelamento/conclusão e reconciliador. Alternativa SKIP LOCKED por slot/run permitiria maior paralelismo mas exigiria ordem de locks e recuperação mais complexa; fica fora da capacidade inicial. Índices únicos parciais são defesa adicional, não substituem protocolo. O relógio é capturado no PostgreSQL após adquirir lock; não usar transaction_timestamp anterior à espera. Todos os writers operacionais respeitam o guard; SQL administrativo é fronteira confiável. Nenhum efeito externo dentro da transação. READ COMMITTED, sem retries ocultos. Timeout de lock retorna erro sem commit; cliente resolve commit incerto pelo recibo.

Migration 002 aditiva sobre 001, sob lock de migration já existente; não destrói publicações. Credencial provisionada em arquivo 0600, sessão via endpoint autenticado, ambas aleatórias/hash SHA-256 no banco. Testes HTTP reais e conexões SQL independentes devem provar disputa, ambos ordenamentos de cancelamento/conclusão, reinícios e negações. Reconciliador no startup e a cada segundo; operação preserva leases válidos. Global guard limita throughput; não há promessa de justiça sob contenção ou implantação remota.

## T005: D-05 / R-05

Primária cicd-execution-runtime; auxiliares ec-agent-control-protocol, cicd-logs-and-test-results, cicd-artifacts-and-retention, database-engineering, application-security e rust. Pontes versão 1 conferidas, caminhos, hashes e contribuição em docs/validation/agent-20261105/skills.json. Sem delegação. Rust 1.99.1/edition 2024, dependências exatas/cache local. Rejeitados: SQLite, dependência Agent→Server, blobs externos com dual-write, credenciais herdadas, checkout da revisão mais recente e retry local. Migration 003 aditiva, upgrade a partir de v2; results usa interfaces públicas de orchestration no mesmo guard/transação. Logs e artefatos usam PostgreSQL para atomicidade conteúdo/recibo. Reinício com journal incerto bloqueia execução concorrente em vez de confiar em PID reutilizável.

Revisão de dependências T005 encontrou projects → access herdada da T002 apenas para SHA-256; substituída por sha2 0.10.9 já adotado, preservando bytes/hash e o grafo ADR-002. Regressão da publicação reexecutada após correção.


## T006 / D-06 / R-06 / REQ-06

Primária vue3-engineering; auxiliares ec-semantic-configuration-projections (rascunho e preservação), frontend-testing (oráculos browser), web-accessibility (teclado/reflow), application-security (sessão e conteúdo não confiável). Pontes versão 1, entradas .codex/skills/<nome>/SKILL.md; hashes/contribuições no skills.json da observação frontend. Sem delegação. Projeção obtida do parser Server em endpoint sem efeitos evita divergência de escalares. Pinia mantém sessão somente em memória; troca de sessão destrói componentes privados. Polling sequencial com AbortController e geração descarta respostas obsoletas, libera timers e requests ao sair. Pedidos incertos retêm identidade e payload para replay explícito. Edição estruturada propõe serialização com diff/escolha explícita antes de substituir bytes; no-op preserva fonte.

Auxiliar rust também consultada para as extensões HTTP no crate de composição, sem alterar toolchain/dependências Rust. Protocolo Marionette do Firefox do sistema substitui o runner Playwright inicialmente preparado, por instrução do usuário; nenhuma dependência de navegador baixado permanece.

### Entradas efetivas dos métodos da consolidação

| Método | Entrada efetiva | SHA-256 | Ponte |
| --- | --- | --- | --- |
| software-design | `.codex/skills/software-design/SKILL.md` | `576df4157d5f4d72065c05cd96462e373e9406f3c3894bd79c60ca41fe3b9eff` | 1 |
| software-architecture | `.codex/skills/software-architecture/SKILL.md` | `0c1ba63835245b8e1cb155375b49790474f271fae299cdaf8aca1f0317f82925` | 1 |
| rust | `.codex/skills/rust/SKILL.md` | `d027c1be9dbc5164168075427aa90a15a351202eb5021d2433ac0a016addf50a` | 1 |
| vue3-engineering | `.codex/skills/vue3-engineering/SKILL.md` | `4a8a4602db93e0380421e194c865d055795334052c15d7924085edc18c4825f2` | 1 |
| software-testing | `.codex/skills/software-testing/SKILL.md` | `047c71403f89a029b1ec797536169b90a3cd82e3d3c943f46dc7c3dc513cf3ba` | 1 |
| application-security | `.codex/skills/application-security/SKILL.md` | `3cc0f13461e849cf31e43db95ba3cbcf004881f0b7ade8e7f3dd7cd03d5f884f` | 1 |
