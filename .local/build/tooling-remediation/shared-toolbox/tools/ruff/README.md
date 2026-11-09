# ruff 0.11.13

## Estado

`candidate`. Executado realmente nesta sessão: **não**. Arquitetura pretendida inicial: linux/amd64. Compatibilidade observada em Debian slim: **não verificada**. ARM64 não disponibilizado. Veja [matriz oficial](../../docs/references.md).

## Origem, instalação e dependências

Binário oficial; Python não é necessário para executar o scanner.

## Comandos, configuração, relatórios e códigos

check --no-fix --no-cache --output-format=json, configuração TOML do consumidor. 0 sem diagnósticos, 1 achados, 2 falha; validar JSON.

## Dados, rede, offline, permissões e containers

Sem DB; cache desativado deliberadamente. Fontes somente leitura; relatório nativo é stdout.log; rede none.

## Lacunas e condição de liberação

Não mede cobertura de testes; bibliotecas e funcionamento real em trixie-slim ainda não observados.

Tamanho local descompactado e tamanho transferido: não medidos. Checksums, digest e versões auxiliares ausentes não devem ser inventados. Pesquisa documental descreve o esperado; somente logs de build e execução poderão demonstrar comportamento observado.
