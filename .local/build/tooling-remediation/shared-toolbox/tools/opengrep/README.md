# opengrep 1.31.0

## Estado

`research-blocked`. Executado realmente nesta sessão: **não**. Arquitetura pretendida inicial: linux/amd64. Compatibilidade observada em Debian slim: **não verificada**. ARM64 não disponibilizado. Veja [matriz oficial](../../docs/references.md).

## Origem, instalação e dependências

Release oficial 1.31.0 autocontido via Nuitka; documentação dispensa Python externo.

## Comandos, configuração, relatórios e códigos

Regras locais do consumidor, JSON/SARIF. Não usar registry remoto durante análise normal.

## Dados, rede, offline, permissões e containers

Cache, extração do binário, Git auxiliar e escritas temporárias precisam de investigação individual; sem suposição de compartilhamento concorrente.

## Lacunas e condição de liberação

Bloqueado: flags de erro/telemetria e dependências de execução em Debian slim não verificados. Dockerfile é apenas receita candidata, não imagem disponibilizada.

Tamanho local descompactado e tamanho transferido: não medidos. Checksums, digest e versões auxiliares ausentes não devem ser inventados. Pesquisa documental descreve o esperado; somente logs de build e execução poderão demonstrar comportamento observado.
