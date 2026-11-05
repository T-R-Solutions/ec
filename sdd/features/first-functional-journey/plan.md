# Plano incremental

D-01 / R-01 / REQ-01: parser de eventos yaml-rust2 0.13.0 permite detectar duplicatas, tags e anchors antes de conversão. Evitar dois parsers com resoluções escalares divergentes. Todos os campos de texto são strings de schema, timeout é decimal validado explicitamente; não aplicar coerções implícitas de booleanos/datas do YAML. Parser genérico pertence a configuration; projetos recebem um mapa escalar e validam sem importar configuration. Composição conecta ambos, preservando as dependências da ADR-002.

D-02 / R-02 / REQ-02: publicação proposta com um ponto de commit PostgreSQL e bytes YAML imutáveis no mesmo banco. Não iniciar sincronização Git sem protocolo de recuperação. Implementação ainda pendente.

D-03 / R-03 / REQ-03: autenticação real e autorização antecedem mutações; segurança de sessões e provisão de credenciais ainda devem ser especificadas na implementação com skill pertinente. Nenhum endpoint será exposto com identidade simulada.

D-04 / R-04 / REQ-04: fila/slots/tentativas juntos em orchestration, um banco Server e locks por tentativa. Escolha ainda exige testes SQL, não prova por modelo em memória.

D-05 / R-05 / REQ-05: jobs inicialmente confiáveis no Linux; sem plugin externo. Separar execução, workspace e entrega de resultados, sem prometer isolamento. Implementação ainda pendente.

D-06 / R-06 / REQ-06: visual estruturado para uma etapa, sem canvas decorativo; texto inválido não sobrescreve modelo válido. Revisar ambas as normas antes de implementar tela.

Primária inicial: ec-declarative-resource-schemas, ponte 1, para parser, campos e rejeições. Auxiliar rust, ponte 1, para manifests, limites públicos e testes. Hashes e fontes oficiais consultadas registrados na validação. A seleção inicial não declara execução de skills de segurança, banco ou UI; consultá-las ao trabalhar nessas fronteiras.

Toolchain observada e fixada: Rust 1.99.1, edition 2024, Linux x86_64. Não há promessa de MSRV inferior; somente este compilador é verificado inicialmente. Cargo.lock deve ser versionado. Build usa CARGO_HOME e CARGO_TARGET_DIR isolados em .local/build; não escreve no cache global. YAML parser documentado em https://docs.rs/yaml-rust2/0.13.0/yaml_rust2/parser/index.html ; edition em https://doc.rust-lang.org/edition-guide/rust-2024/index.html . Dependências de frontend/PostgreSQL/HTTP serão fixadas quando seus runners forem exercitados.
