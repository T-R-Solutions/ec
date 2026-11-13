# Geiger — correção de descoberta instalada no host

O runner usa exclusivamente o `cargo-geiger` instalado, selecionado pelo ambiente
canônico. Não há compilação, instalação, fallback ou executável privado durante
os gates. A fonte deste diretório serve ao preparo explícito da instalação; não
é uma segunda configuração de automação. `Cargo.toml.orig` e
`upstream-files.json` preservam a proveniência importada, sem uso no build atual.

## Correção

O Geiger upstream 0.14.0 confronta os arquivos do dep-info com a busca de `.rs`
nas árvores dos pacotes. Isso acusa documentação incorporada como não examinada
e deixa código gerado fora dos contadores. O patch mantém motor, contadores,
grafo e política de unsafe, acrescentando:

- Grupos de dependências por arquivo `.d` para atribuir fontes ao pacote consumidor.
- Reconhecimento de inclusões literais de dados pela AST, sem dispensar pelo nome/extensão.
- Análise de Rust gerado pelo próprio motor `geiger`, com proprietário único.
- Rejeição de entrada desconhecida, atribuição ambígua e parsing inválido.

As regressões de `src/scan/supplemental.rs` cobrem documentação, código unsafe
gerado, includes de extensão diferente, consumidores ambíguos e dados desconhecidos.
O histórico da implementação e de execuções anteriores está no
[relatório de 09/11](../../../../docs/validation/geiger-coverage-remediation-20261109/report.md).
Essas execuções não aprovam a resolução atual de dependências.

## Preparo de 11/11/2026

O run-xK3jrVOn voltou a expor a limitação da versão upstream instalada. O pedido
de correção produziu e instalou offline `0.14.0+ec.1`, com autorização específica
para substituir o plugin em `~/.cargo`. O sufixo identifica a alteração local,
sem pin de versão nos scripts. Foram preservadas as licenças MIT/Apache-2.0 e
as mudanças anteriores das fontes; arquivos do registro Cargo não foram editados.

Build e instalação usaram o toolchain ativo e os crates já preparados. O target
temporário fica em `/tmp` somente durante o preparo. Cargo registra a instalação;
os logs privados guardam hashes das fontes, lock resolvido e executável. O
[relatório deste reparo](../../../../docs/validation/automation-run-xK3jrVOn-20261111/report.md)
distingue compilação, instalação e validação pendente.

A suíte, os scans e as regressões não foram executados neste reparo. Uma futura
reinstalação upstream pode remover o patch; a identidade registrada pelo gate
permite reconhecer a mudança. Não tratar ausência de warnings como prova de
segurança ou compilação como comprovação do inventário completo.


## Associação resolvida — 12/11/2026

O patch 0.14.0+ec.2 percorre `resolve.nodes.deps` do Cargo, preservando a
identidade resolvida. A busca antiga escolhia uma versão pela faixa declarada
inclusive para dependências opcionais inativas; isso produzia 593 tentativas
sem associação no run-IR9jPgS9. A ausência de nó/associação ativa agora retorna
erro, em vez de virar uma aresta descartada com exit zero. Features, target e
política de unsafe permanecem os do scan existente. Regressões novas exercitam
versão não resolvida, declaração opcional, rename e perda de nó ativo.
