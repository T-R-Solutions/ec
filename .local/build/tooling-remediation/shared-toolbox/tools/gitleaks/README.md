# gitleaks 8.24.3

## Estado

`candidate`. Executado realmente nesta sessão: **não**. Arquitetura pretendida inicial: linux/amd64. Compatibilidade observada em Debian slim: **não verificada**. ARM64 não disponibilizado. Veja [matriz oficial](../../docs/references.md).

## Origem, instalação e dependências

Binário oficial Go; imagem inclui Git e certificados. Não requer Go no runtime.

## Comandos, configuração, relatórios e códigos

Arquivos atuais via dir; histórico via git, exigindo .git completo. TOML e baseline pertencem ao consumidor. JSON nativo em /out/native.json; código 42 reservado a achados, demais erros não aprovam.

## Dados, rede, offline, permissões e containers

Sem base externa. Fontes /src somente leitura, resultados /out; rede none. Histórico raso não prova cobertura completa.

## Lacunas e condição de liberação

Origem SHA256 do release e bibliotecas dinâmicas precisam de confirmação no preparo real; execução offline ainda não demonstrada.

Tamanho local descompactado e tamanho transferido: não medidos. Checksums, digest e versões auxiliares ausentes não devem ser inventados. Pesquisa documental descreve o esperado; somente logs de build e execução poderão demonstrar comportamento observado.
