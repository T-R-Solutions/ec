# Plano delimitado

D-01: reservar cada diretório com `create_dir`, distinguir `AlreadyExists` dos
demais erros e manter posse RAII somente após a reserva bem-sucedida. Evitar
dependência nova e serialização dos testes. R-01: colisão de nomes entre fixtures
pode abortar o setup ou compartilhar/apagar dados de outro teste.

Rota local: alteração recuperável no suporte privado dos testes, sem decisão
material de concorrência do produto. Os outros helpers de temporários no Agent
possuem prefixos próprios e um único consumidor por binário; não compartilham
`ec-process`. A correção cobre o helper usado pelos quatro testes paralelos que
produziram a falha. O produtor exato do caminho original não foi registrado.

Métodos efetivamente lidos: `.codex/skills/rust/SKILL.md` (primária) e
`.codex/skills/software-testing/SKILL.md` (regressões e prova causal), pontes v1.
Workspace Rust edição 2024, toolchain `stable`, sem `rust-version` declarada;
preservar manifests, dependências e APIs. ADR-002/003 permanecem vigentes.

Superfícies `docs` e `agent`: registro SDD e testes Rust do Agent. O plano canônico
inclui cobertura e seus preparos Server/PostgreSQL. Não há mudança de scripts,
frontend ou contratos que exija suas superfícies. Testes focados offline e
reprodução descartável antecedem revisão independente; depois de estabilizar
artefatos, executar `bash scripts/automate.sh run --surface docs --surface agent`
e entregar o recibo real ao validador para `scripts/accept-task.sh --task`.

Dono de `process.rs`: implementador fixture_fix. Dono deste registro: orquestrador.
Revisor/validador em contexto independente, somente leitura. Preservar todas as
mudanças preexistentes. Logs seguem a política de duas datas; temporários em /tmp.
