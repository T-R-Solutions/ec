# shellcheck 0.10.0

## Estado

`candidate`. Executado realmente nesta sessão: **não**. Arquitetura pretendida inicial: linux/amd64. Compatibilidade observada em Debian slim: **não verificada**. ARM64 não disponibilizado. Veja [matriz oficial](../../docs/references.md).

## Origem, instalação e dependências

Executável do release oficial; não exige shell do consumidor para análise estática.

## Comandos, configuração, relatórios e códigos

Arquivos explícitos e --format=json1; --rcfile opcional. 0/1 cruzados com comments; outros códigos são falha.

## Dados, rede, offline, permissões e containers

Sem DB; .shellcheckrc pertence ao consumidor; stdout.log é relatório nativo. Fontes somente leitura; sem rede.

## Lacunas e condição de liberação

Bibliotecas do binário oficial e checksum do release ainda precisam ser obtidos; não há fallback sem hash oficial.

Tamanho local descompactado e tamanho transferido: não medidos. Checksums, digest e versões auxiliares ausentes não devem ser inventados. Pesquisa documental descreve o esperado; somente logs de build e execução poderão demonstrar comportamento observado.
