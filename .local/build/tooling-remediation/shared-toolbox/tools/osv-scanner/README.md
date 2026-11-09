# osv-scanner 2.0.2

## Estado

`research-blocked`. Executado realmente nesta sessão: **não**. Arquitetura pretendida inicial: linux/amd64. Compatibilidade observada em Debian slim: **não verificada**. ARM64 não disponibilizado. Veja [matriz oficial](../../docs/references.md).

## Origem, instalação e dependências

Release oficial ou compilação Go; binário e bibliotecas ainda não verificados.

## Comandos, configuração, relatórios e códigos

Versão 2.0.2 documenta scan source; flags offline divergem da documentação V2 corrente. JSON, códigos de erro e configuração precisam de reconciliação com fonte 2.0.2.

## Dados, rede, offline, permissões e containers

Bases locais OSV podem permitir matching offline; chamadas online, resolução transitiva e análise de chamadas têm outros requisitos.

## Lacunas e condição de liberação

Bloqueado: comando de download/preparo da base e contrato de versão não resolvidos. Não executar sintaxe inferida da documentação atual.

Tamanho local descompactado e tamanho transferido: não medidos. Checksums, digest e versões auxiliares ausentes não devem ser inventados. Pesquisa documental descreve o esperado; somente logs de build e execução poderão demonstrar comportamento observado.
