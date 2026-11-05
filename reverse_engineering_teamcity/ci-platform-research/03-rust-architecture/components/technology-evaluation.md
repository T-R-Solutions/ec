# Avaliação tecnológica

As documentações oficiais estavam acessíveis em 2026-10-30. Isso demonstra disponibilidade documental, não saúde completa de manutenção. Antes do lockfile: conferir releases, MSRV, advisories e compatibilidade de features. Nenhuma superioridade de memória/desempenho foi medida.

## Tokio 1.54.1

Uso: runtime de I/O. Alternativa: std threads. Fontes: T-TOKIO em sources.md.

Decisão: Escolher controle/agente; features mínimas runtime, sync, time, process, io. Tasks supervisionadas; blocking fora do executor.

Dependências, estabilidade, custos e limitações: Runtime e features transitivas; memória por task/channel precisa limite; API estável não elimina mudanças entre releases.

## Axum 0.8.9 + Tower 0.5.3

Uso: API, timeouts e limites. Alternativa: servidor HTTP manual. Fontes: T-AXUM T-TOWER em sources.md.

Decisão: Escolher; extratores e middleware, timeout e limite de requests.

Dependências, estabilidade, custos e limitações: Tokio/HTTP stack; pré-1.0 exige revisão de upgrade e ajuste de handlers; timeout não cancela commit já executado.

## Tonic 0.14.9

Uso: RPC e streaming. Alternativa: HTTP JSON. Fontes: T-TONIC em sources.md.

Decisão: Adiar: HTTP atende primeiro fluxo; reconsiderar clientes gerados.

Dependências, estabilidade, custos e limitações: HTTP/2, Protobuf e geração ampliam build e operação; não há benchmark justificando troca.

## Serde

Uso: DTO e formato JSON. Alternativa: parser manual. Fontes: T-SERDE em sources.md.

Decisão: Escolher; deny_unknown_fields e limites antes de desserializar.

Dependências, estabilidade, custos e limitações: Formato é crate separado; derive gera código; validar semântica depois do parse; custo por payload a medir.

## SQLx 0.9.0

Uso: SQL async explícito. Alternativa: Diesel e driver direto. Fontes: T-SQLX em sources.md.

Decisão: Escolher candidato PostgreSQL; pool limitado e migrações versionadas.

Dependências, estabilidade, custos e limitações: Runtime/TLS/drivers opcionais; macros podem exigir metadata/banco no build; upgrade pré-1.0 requer integração.

## Diesel

Uso: query builder tipado. Alternativa: SQLx. Fontes: T-DIESEL em sources.md.

Decisão: Alternativa válida; não selecionada porque transações/locks específicos serão SQL explícito.

Dependências, estabilidade, custos e limitações: Schema gerado e features de backend; adapter async separado se necessário; não afirmar inferioridade de desempenho.

## Tracing 0.1.44

Uso: spans e eventos operacionais. Alternativa: logs std. Fontes: T-TRACING em sources.md.

Decisão: Escolher; campos run/job/attempt/fence; nunca segredo.

Dependências, estabilidade, custos e limitações: subscriber e exporter separados; cardinalidade e buffers limitados; audit trail durável é outro mecanismo.

## Rustls 0.23.46

Uso: TLS cliente/servidor. Alternativa: TLS de proxy no controle. Fontes: T-RUSTLS em sources.md.

Decisão: Escolher candidato do agente; controle pode terminar TLS em proxy de confiança definido.

Dependências, estabilidade, custos e limitações: Selecionar provider crypto e certificados explicitamente; rotação e validação de nomes; pré-1.0 e integração a testar.

## Petgraph 0.8.3

Uso: algoritmos de grafos. Alternativa: Kahn com Vec e índices. Fontes: T-GRAPH em sources.md.

Decisão: Adiar; validação topológica O(V+E) própria basta ao DAG estático pequeno.

Dependências, estabilidade, custos e limitações: Mais representações/algoritmos do que o CORE exige; introduzir só com necessidade medida.

## std::process / tokio::process

Uso: execução de comandos. Alternativa: executor OCI externo. Fontes: T-STD T-PROCESS em sources.md.

Decisão: std serve protótipo; Tokio para múltiplos pipes e deadlines no agente.

Dependências, estabilidade, custos e limitações: Drop não garante encerramento; kill/wait e descendentes exigem controle; execução não é isolamento.

## cargo test

Uso: testes básicos. Alternativa: runner externo. Fontes: T-STD em sources.md.

Decisão: Escolher inicialmente; sem dependências de teste no protótipo.

Dependências, estabilidade, custos e limitações: Sem detecção automática de todas as interleavings; integração real ainda necessária.

## cargo-nextest

Uso: execução e isolamento de testes. Alternativa: cargo test. Fontes: T-NEXTEST em sources.md.

Decisão: Adiar até suite justificar; não usar promessa upstream de velocidade como medida local.

Dependências, estabilidade, custos e limitações: Binário adicional, configuração e comportamento de runner devem ser fixados.

## proptest 1.12.0

Uso: geração de sequências. Alternativa: enumeração determinística. Fontes: T-PROP em sources.md.

Decisão: Adotar ao ampliar domínio; gravar seeds e regressões.

Dependências, estabilidade, custos e limitações: Shrinking e estratégias consomem tempo; não demonstra consistência distribuída sozinho.

## loom 0.7.2

Uso: interleavings em memória. Alternativa: transações reais PostgreSQL. Fontes: T-LOOM em sources.md.

Decisão: Adiar salvo sincronização custom; banco requer teste concorrente próprio.

Dependências, estabilidade, custos e limitações: Primitivas instrumentadas e explosão combinatória; não modela rede/banco real automaticamente.

PostgreSQL é o banco recomendado para múltiplos workers: locks de linha e transações (T-PG).
SQLite é alternativa para piloto de processo único com escrita serializada (T-SQLITE); não assumir equivalência de SQL/locks.
Não foram adicionadas dependências ao setup TeamCity nem ao protótipo. Versões observadas não são pins de implantação.
