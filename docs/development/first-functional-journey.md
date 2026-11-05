# Executar o incremento inicial

Estado em 2026-11-05: validação local executável de um recurso projeto/pipeline, sem publicação, Server HTTP, banco, Agent ou frontend funcional. A [feature](../../sdd/features/first-functional-journey/spec.md) mantém o contrato e o aceite pendente da jornada completa. Este comando não executa o script do YAML.

Pré-requisitos observados: Linux x86_64, Rust/Cargo 1.99.1 instalados via rustup; acesso ao crates.io na primeira obtenção das cinco dependências transitivas fixadas em Cargo.lock. Sem requisito PostgreSQL para este incremento local. Não há garantia de versões de compilador inferiores. `yaml-rust2 = 0.13.0` sem features padrão foi escolhido pela API de eventos, que permite rejeitar duplicatas e construções proibidas antes de produzir mapas.

Da raiz do checkout, comandos executados:

```bash
export CARGO_HOME="$PWD/.local/build/ec-cargo-home"
export CARGO_TARGET_DIR="$PWD/.local/build/ec-server-clean"
cargo +1.99.1 fetch --manifest-path application/server/backend/Cargo.toml --locked
cargo +1.99.1 test --manifest-path application/server/backend/Cargo.toml --locked --offline
cargo +1.99.1 run --manifest-path application/server/backend/Cargo.toml --locked --offline -p ec-server-app -- validate-yaml < application/integration/tests/fixtures/project-pipeline.yaml
cargo +1.99.1 fmt --manifest-path application/server/backend/Cargo.toml --all -- --check
cargo +1.99.1 clippy --manifest-path application/server/backend/Cargo.toml --locked --offline --all-targets -- -D warnings
```

A coleta inicial de dependências exigiu acesso de rede fora do sandbox. Depois disso, testes/build/execução foram repetidos offline com target novo e cache próprio do projeto. A reprodução isolou artefatos de compilação, não a máquina inteira. O lockfile registra checksums de pacotes. Nenhum cache global foi convertido em dependência oculta.

O CLI retorna 0 e `VALIDATED_LOCAL` somente após parsing e validação semântica; retorna 1 para entrada inválida e 2 para uso incorreto. Input vem de stdin com limite de 64 KiB; diagnósticos de schema identificam campo, diagnósticos de parser incluem posição quando disponível. Não declara publicação, autorização ou execução. A fixture contém um SHA sintético para validar formato e não comprova existência de repositório/commit.

Os unitários de parsing/modelo ficam nos respectivos src; os três testes de integração pública/processo ficam em `ec-server-app/tests/validation.rs`. O harness integrado da plataforma ainda está pendente. Não há instrução para iniciar serviços de produto porque eles não foram implementados nem executados.

Fontes oficiais consultadas: [edition 2024](https://doc.rust-lang.org/edition-guide/rust-2024/index.html), [eventos do parser 0.13.0](https://docs.rs/yaml-rust2/0.13.0/yaml_rust2/parser/enum.Event.html), [Parser](https://docs.rs/yaml-rust2/0.13.0/yaml_rust2/parser/struct.Parser.html). A documentação de serde_yaml_ng foi examinada, mas essa dependência não foi adotada. Os limites do schema são política inicial do Ec, não resultados de capacidade do parser.

A próxima fatia obrigatória é T002/T003: decidir e implementar persistência/publicação e autenticação/autorização reais, com migrations e testes PostgreSQL isolados. Depois vêm leases/fencing, execução Agent e a interface. Nenhuma dessas pendências pode ser substituída por dados simulados ou pelo PASS da governança.
