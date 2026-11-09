# Repository Guidelines

## Contexto e autoridades

Ec é uma plataforma CI/CD em implementação: Server e Agent como monólitos modulares Rust e frontend Vue 3/TypeScript/Pinia. Há workspaces e código executável local; guias dos componentes registram limites e pendências.
Antes de alterar, leia [estado/escopo](sdd/project.md), [autoridades](docs/constitution.md), [arquitetura](docs/architecture/context.md) e ADRs pertinentes. Preserve mudanças existentes e evidências históricas; instruções do usuário e permissões reais prevalecem sobre templates.

## Estrutura

`application/agents/` contém o Agent; `application/server/` contém backend e frontend; `application/integration/tests/` reúne os testes entre componentes, com integração e ponta a ponta separados. Os caminhos partem da raiz do repositório. Cargo.toml/Cargo.lock e rust-toolchain.toml existem nos dois workspaces; frontend usa package.json/package-lock.json e npm. Diretórios, isoladamente, não comprovam funcionalidade.
`docs/` reúne normas e decisões; `reverse_engineering_teamcity/` preserva pesquisa, utilitários e protótipo; `sdd/` contém workflow, pontes e features; `config/governance/` registra catálogo e hashes; `tools/governance/` contém verificações.
As 67 skills selecionadas estão instaladas em `.codex/skills/`, com hashes conferidos. A preparação em `.local/skill-installation/skills/` permanece como baseline de recuperação. Consulte o perfil e o caminho real na ponte; não alegue instalação automática.

## Workflow e skills

Siga [sdd/workflow.md](sdd/workflow.md): rota local para reparos delimitados; completa para contratos, persistência, segurança, concorrência e recuperação. Selecione uma primária pelo problema e auxiliares por questões distintas no [catálogo](sdd/chain/README.md). Leia o `SKILL.md` efetivo e registre contribuição/hash quando usar a rota completa. Disponibilidade não significa execução; não carregue todo o catálogo.

## Contratos do produto

YAML é a configuração desejada canônica; Visual/Formulário/YAML preservam a mesma semântica. PostgreSQL guarda estado operacional; HTTP versionado é vigente. gRPC/Protobuf/SQLite local são propostas pendentes. Ec Agent não implica IA. Não prometer exactly-once, isolamento ou capacidade sem evidência. Consultar ambas as normas de `docs/` para UI; preservar autorização, acessibilidade e estados reais.

## Comandos, estilo e testes

Siga a [política canônica de verificação](docs/development/task-verification.md): scripts só são acionados após pedido explícito de commit. Pergunte “Deseja executar o script automate.sh (que roda todos os outros scripts)?”, oferecendo somente `sim` e `não`, e aguarde. Com `não`, crie os commits normalmente sem scripts. Com `sim`, execute `bash scripts/automate.sh run` sem filtros e só crie commits se todos os scripts terminarem sem erros; erro, interrupção ou execução incompleta bloqueiam os commits. Não executar scripts avulsos ou automaticamente ao encerrar tarefas. Registre inspeções, resultados e lacunas; checks não executados não aprovam critérios SDD. Não depender exclusivamente de Git.

Ao executar a tarefa de verificação com `automate.sh`, há autorização positiva do usuário para todas as ações necessárias à execução e à resolução dos problemas encontrados, exceto downloads. Não solicitar confirmações adicionais para essas ações; downloads exigem autorização específica. Esta autorização se aplica após a escolha de executar a suíte e permanece válida nas correções e reexecuções da mesma tarefa, respeitando as permissões reais do ambiente.

Código Python novo: quatro espaços e `snake_case`; JSON de governança: dois espaços. Documentação nova em pt-BR; preserve referências importadas. Testes verificam invariantes e rejeições. Não inventar comandos; usar manifests e guias vigentes.

## Commits e revisão

Apenas com pedido de commit/tag, leia a ponte `commit-governance`. Use Conventional Commits em inglês como convenção desta integração; histórico Git está indisponível. PRs descrevem mudança, requisitos/ADRs, verificações e limitações; telas alteradas incluem evidência visual pertinente. Não incluir segredos nem publicar por inferência.
