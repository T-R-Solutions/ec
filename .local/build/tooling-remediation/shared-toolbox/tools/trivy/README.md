# trivy 0.61.1

## Estado

`research-blocked`. Executado realmente nesta sessão: **não**. Arquitetura pretendida inicial: linux/amd64. Compatibilidade observada em Debian slim: **não verificada**. ARM64 não disponibilizado. Veja [matriz oficial](../../docs/references.md).

## Origem, instalação e dependências

Release oficial existente; referência de base upstream não comprova Debian.

## Comandos, configuração, relatórios e códigos

Filesystem e arquivos de imagens devem evitar socket Docker. Formatos estruturados e código configurável precisam de confirmação da versão.

## Dados, rede, offline, permissões e containers

DB principal, Java DB, checks e cache são diferentes. Flags skip-update e offline-scan históricas não bastam para prometer análise offline.

## Lacunas e condição de liberação

Bloqueado: documentação 0.61.1 inacessível; origem de assets, hashes, executáveis auxiliares e códigos não confirmados.

Tamanho local descompactado e tamanho transferido: não medidos. Checksums, digest e versões auxiliares ausentes não devem ser inventados. Pesquisa documental descreve o esperado; somente logs de build e execução poderão demonstrar comportamento observado.
