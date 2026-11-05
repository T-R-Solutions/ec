# Repository Guidelines

## Contexto e autoridades

Ec é uma plataforma CI/CD planejada: Server e Agent como monólitos modulares Rust e frontend Vue 3/TypeScript. Este checkout contém pesquisa e governança, sem aplicação implementada.
Antes de alterar, leia [estado/escopo](sdd/project.md), [autoridades](docs/constitution.md), [arquitetura](docs/architecture/context.md) e ADRs pertinentes. Preserve mudanças existentes e evidências históricas; instruções do usuário e permissões reais prevalecem sobre templates.

## Estrutura

`application/agents/` contém o Agent; `application/server/` contém backend e frontend; `application/integration/tests/` reúne os testes entre componentes, com integração e ponta a ponta separados. Os caminhos partem da raiz do repositório. A estrutura é documental, sem aplicação implementada.
`docs/` reúne normas e decisões; `reverse_engineering_teamcity/` preserva pesquisa, utilitários e protótipo; `sdd/` contém workflow, pontes e features; `config/governance/` registra catálogo e hashes; `tools/governance/` contém verificações.
As 67 skills selecionadas estão instaladas em `.codex/skills/`, com hashes conferidos. A preparação em `.local/skill-installation/skills/` permanece como baseline de recuperação. Consulte o perfil e o caminho real na ponte; não alegue instalação automática.

## Workflow e skills

Siga [sdd/workflow.md](sdd/workflow.md): rota local para reparos delimitados; completa para contratos, persistência, segurança, concorrência e recuperação. Selecione uma primária pelo problema e auxiliares por questões distintas no [catálogo](sdd/chain/README.md). Leia o `SKILL.md` efetivo e registre contribuição/hash quando usar a rota completa. Disponibilidade não significa execução; não carregue todo o catálogo.

## Contratos do produto

YAML é a configuração desejada canônica; Visual/Formulário/YAML preservam a mesma semântica. PostgreSQL guarda estado operacional; HTTP versionado é vigente. gRPC/Protobuf/SQLite local são propostas pendentes. Ec Agent não implica IA. Não prometer exactly-once, isolamento ou capacidade sem evidência. Consultar ambas as normas de `docs/` para UI; preservar autorização, acessibilidade e estados reais.

## Comandos, estilo e testes

Da raiz: `python3 tools/governance/validate.py`, `python3 sdd/tools/validate.py --root .` e `python3 -m unittest discover -s tools/governance/tests -v`. `--require-installed` exige instalação efetiva; aceite SDD usa `--require-approved --require-fresh` e deve falhar enquanto houver lacunas.
Não inventar builds Cargo/npm. Código Python novo: quatro espaços e `snake_case`; JSON de governança: dois espaços. Documentação nova em pt-BR; preserve referências importadas. Testes devem verificar invariantes e rejeitar falhas, sem confundir estrutura com produto. Siga [evidence.md](sdd/evidence.md).

## Commits e revisão

Apenas com pedido de commit/tag, leia a ponte `commit-governance`. Use Conventional Commits em inglês como convenção desta integração; histórico Git está indisponível. PRs descrevem mudança, requisitos/ADRs, verificações e limitações; telas alteradas incluem evidência visual pertinente. Não incluir segredos nem publicar por inferência.
