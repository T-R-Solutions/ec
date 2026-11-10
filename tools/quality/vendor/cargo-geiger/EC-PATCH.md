# Geiger 0.14.0 — reconciliação de entradas do build

Cópia das fontes já disponíveis localmente do pacote crates.io cargo-geiger
0.14.0, sem download. Licenças upstream MIT/Apache-2.0 preservadas. O inventário
`upstream-files.json` guarda os SHA-256 anteriores às alterações. Não se trata
de outro scanner: permanecem o parser, os contadores, o grafo e os gates Geiger.

O upstream compara todos os arquivos do dep-info com `.rs` encontrados nas
árvores dos pacotes. Isso classifica documentação de `include_str!` como fonte
não examinada e deixa código de OUT_DIR fora dos contadores. A correção:

- guarda grupos de dependências por arquivo `.d`;
- reconhece somente inclusões literais de dados pela AST dos consumidores;
- conserva entradas desconhecidas como avisos nativos e erros;
- examina fontes Rust adicionais com o mesmo motor `geiger`, atribuindo-as
  ao único pacote com fontes no mesmo grupo dep-info;
- falha em atribuição ausente/ambígua ou erro de parsing, sem resultados parciais;
- inclui as métricas adicionais no inventário, sem mudar a regra de unsafe.

O gate compila esta fonte com Cargo locked/offline em target privado e usa esse
binário pelo PATH somente na tarefa. O cache de build da ferramenta pode ser
indicado por `EC_GEIGER_BUILD_CARGO_HOME`; por padrão usa o Cargo home global
já preparado. Nenhuma instalação/download ocorre implicitamente. Dependências
ausentes bloqueiam. A identidade da fonte integra os inputs do run; a identidade
do executável/versão integra o recibo tool.json.

Regressões em `src/scan/supplemental.rs` cobrem documentação, código unsafe
gerado, extensão não Rust e entrada desconhecida. Sua execução deve ocorrer
somente na suíte autorizada. Implementação e build permanecem UNVERIFIED até lá.

## Atualização autorizada de dependências — 09/11/2026

Após pedido explícito para baixar as versões mais recentes, Cargo atualizou o
lock para as versões mais recentes compatíveis com o manifesto e Rust 1.97.
O pacote continua sendo Geiger 0.14.0 com motor geiger 0.5.0. `upstream-files.json`
preserva a baseline anterior ao patch e não é um inventário dos bytes atuais.
O cache privado `.local/build/ec-geiger-cargo-home` foi preparado por cargo fetch
com lock, separadamente da suíte offline e sem atualizar locks de aplicação.
[Versões, hashes e recibo](../../../../docs/validation/geiger-coverage-remediation-20261109/geiger-dependencies.json).
