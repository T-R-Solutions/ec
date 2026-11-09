# playwright 1.56.1

## Estado

`candidate`. Executado realmente nesta sessão: **não**. Arquitetura pretendida inicial: linux/amd64. Compatibilidade observada em Debian slim: **não verificada**. ARM64 não disponibilizado. Veja [matriz oficial](../../docs/references.md).

## Origem, instalação e dependências

Node 22.20.0 e @playwright/test 1.56.1 na imagem própria; somente navegador da variante escolhida.

## Comandos, configuração, relatórios e códigos

api não instala browsers; chromium/firefox/webkit são imagens variantes independentes. Consumer config habilita reporter json e usa /out para artefatos. Falhas de testes são achados; erros globais, zero testes e relatório ausente são falhas operacionais.

## Dados, rede, offline, permissões e containers

Fontes montadas somente leitura; cópia em /out/work recebe link node_modules para /opt/pw/node_modules. Sem npm global, NODE_PATH ou npx. Node builtin e pacote central são as únicas dependências de testes inicialmente aceitas.

## Lacunas e condição de liberação

Resolução ESM/CJS, dependências Debian, sandbox do Chromium, fontes, traces e imports externos ainda precisam de execução real. Veja docs/playwright.md.

Tamanho local descompactado e tamanho transferido: não medidos. Checksums, digest e versões auxiliares ausentes não devem ser inventados. Pesquisa documental descreve o esperado; somente logs de build e execução poderão demonstrar comportamento observado.
