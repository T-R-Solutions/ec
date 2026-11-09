# syft 1.23.1

## Estado

`research-blocked`. Executado realmente nesta sessão: **não**. Arquitetura pretendida inicial: linux/amd64. Compatibilidade observada em Debian slim: **não verificada**. ARM64 não disponibilizado. Veja [matriz oficial](../../docs/references.md).

## Origem, instalação e dependências

Release oficial 1.23.1; não necessita runtime Go se adotado executável pronto.

## Comandos, configuração, relatórios e códigos

Inventário SBOM; JSON Syft, SPDX e CycloneDX na documentação corrente. Sucesso deve ser measurement, não aprovação de vulnerabilidades.

## Dados, rede, offline, permissões e containers

Diretórios/arquivos OCI evitam socket; atualizações/check de aplicação e caches precisam ser desativados ou explicitados.

## Lacunas e condição de liberação

Bloqueado: documentação exata 1.23.1, dependências e contrato de falhas ainda não auditados. Dockerfile candidato não é evidência de imagem funcional.

Tamanho local descompactado e tamanho transferido: não medidos. Checksums, digest e versões auxiliares ausentes não devem ser inventados. Pesquisa documental descreve o esperado; somente logs de build e execução poderão demonstrar comportamento observado.
