# grype 0.119.0

## Estado

`candidate`. Executado realmente nesta sessão: **não**. Arquitetura pretendida inicial: linux/amd64. Compatibilidade observada em Debian slim: **não verificada**. ARM64 não disponibilizado. Veja [matriz oficial](../../docs/references.md).

## Origem, instalação e dependências

Arquivo binário oficial 0.119.0; certificados no runtime. Sem socket Docker.

## Comandos, configuração, relatórios e códigos

Adapter inicial somente dir:, saída JSON com matches e descriptor. Sem severidade imposta; todo match reportado e código não zero tratado como falha.

## Dados, rede, offline, permissões e containers

update-db explícito gera snapshot por SHA256 e preserva formato nativo; execução usa cópia privada da base. Auto-update e check-for-app-update desativados; rede none por padrão.

## Lacunas e condição de liberação

Suporte a imagem arquivada e SBOM ainda não integrado. Consumidor deve fixar database e max_database_age_hours; idade nativa não é desativada. Build, DB e offline reais pendentes.

Tamanho local descompactado e tamanho transferido: não medidos. Checksums, digest e versões auxiliares ausentes não devem ser inventados. Pesquisa documental descreve o esperado; somente logs de build e execução poderão demonstrar comportamento observado.
