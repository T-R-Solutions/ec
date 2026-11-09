# Preparação, dados, retenção e migração

## Pré-requisitos no openSUSE

Host observado: Tumbleweed 20261103 x86_64; pacotes Docker 29.7.2_ce e Python 3.13.16. A [documentação Docker](https://docs.docker.com/engine/install/) remete às distribuições não listadas para seus mantenedores. A página [openSUSE SDB:Docker](https://en.opensuse.org/SDB:Docker) retornou 403 nesta sessão: a confirmação documental dos comandos de instalação no Tumbleweed está **pendente**. Não foi aplicada instalação nem mudança de grupos/socket. Não use chmod global no socket.

É necessário Docker Engine local acessível ao usuário e Python 3.11+. Verifique com `bin/toolbox doctor`. A CLI não precisa de Node, npm, pip ou scanners nativos. Host remoto, Docker Desktop e UID remapeado/rootless não foram validados. Não tente corrigir permissões por scripts desta plataforma.

## Preparar imagens separadamente das análises

```sh
bin/toolbox doctor
bin/toolbox lock-base
# Revise e versione o digest real em platform.json.
bin/toolbox prepare --config examples/consumer-a/toolbox.json
bin/toolbox prepare --config examples/consumer-b/toolbox.json
bin/toolbox prepare --config examples/playwright-api/toolbox.json
bin/toolbox prepare --config examples/playwright-ui/toolbox.json
```

A preparação pode usar rede para pull, apt, arquivos oficiais, npm e browsers. A análise não realiza esses passos. GitHub assets exigem SHA256 oficial (API ou checksum do release), sem fallback para hash de confiança inicial. npm usa SRI; Node usa SHASUMS oficial. Verificação de assinatura não foi implementada: checksum não é assinatura. Downloads com certificado inválido ou hash divergente falham.

`prepare` reusa uma imagem já preparada para a mesma versão/variante/receita. Alteração de receita cria outra identidade; não altera locks antigos automaticamente. Se o ID local foi removido, restaure a imagem salva ou prepare explicitamente e revise a mudança de lock. Não use `latest` para resolver versões de consumidores.

## Preparar e atualizar bases

Apenas o atualizador candidato de Grype está implementado. Primeiro prepare sua imagem por uma configuração dedicada. Depois:

```sh
bin/toolbox update-db --config /caminho/consumidor/toolbox.json --only grype
```

A atualização usa rede e um diretório de staging, preserva stdout/stderr e publica snapshot por hash. Adote explicitamente o ID retornado na configuração. Snapshot anterior continua disponível; `run` não muda sua seleção. Formato nativo, SQLite/índices e metadados do Grype são conservados. Não existe banco da plataforma.

## Armazenamento e concorrência

Cada ferramenta possui seu próprio diretório persistente no repositório compartilhado:

```text
tools/<ferramenta>/volume/
├── images/<versão>/<recipe>/       # Contexto, fontes e registros de preparo; não as camadas Docker
├── databases/<versão>/<sha>/       # Snapshots no formato nativo
├── preparation/<id>/              # Staging e logs de atualização de bases
├── projects/<projeto>/runs/<id>/   # stdout, stderr, relatórios e cópias de trabalho
└── locks/                         # Locks de preparo da ferramenta
```

Exemplo: `tools/grype/volume/`. O diretório existe para todos os módulos, inclusive os ainda bloqueados; isso não significa que a ferramenta já foi disponibilizada. `.gitkeep` mantém a pasta no Git; seu conteúdo operacional fica ignorado. Caches persistentes futuros também devem ficar dentro do volume da respectiva ferramenta; os caches temporários atuais continuam descartáveis.

Os mounts são bind mounts: fontes do consumidor em `/src:ro`, resultado de sua ferramenta em `/out:rw` e snapshot da base em `/db:ro`, quando necessário. Playwright escreve em `/out/work`; Grype utiliza cópia privada em `/out/database`. `/tmp` continua sendo tmpfs descartável. As imagens propriamente ditas permanecem no armazenamento do Docker.

`.local/state/projects/<projeto>/runs/<id>/` guarda apenas `inputs.json` e `summary.json`, com referências aos diretórios de cada ferramenta. `.local/state/locks/` contém locks gerais. Esses arquivos de coordenação não são montados nos containers. `TOOLBOX_STATE` e `--state` agora mudam **somente esses índices e locks gerais**, sem deslocar volumes para fora dos módulos. Todos os caminhos são resolvidos a partir da localização da CLI, independentemente do diretório de onde ela foi chamada.

Execuções normais não compartilham cache gravável. Grype copia a base para o diretório da execução; caches temporários ficam no container. Caches de compilação, credenciais de registry e pacotes adicionais Playwright ainda não estão implementados. Bases compartilháveis não incluem credenciais ou fontes de outros consumidores.

```sh
bin/toolbox results --project consumer-a
bin/toolbox prune --project consumer-a --keep 20
bin/toolbox prune --project consumer-a --keep 20 --apply
```

`prune` é dry-run até `--apply`; só apaga runs concluídos do projeto solicitado. Não apaga bases, imagens, outros projetos ou runs incompletos. Retenção é decisão do operador; 20 acima é exemplo, não política. Bases e imagens exigem remoção manual identificada após verificar todos os locks consumidores. Nunca execute prune global do Docker como parte desta CLI.

## Backup e restauração

Pause execuções, preparações e atualizações enquanto copia os volumes e índices. Para cada consumidor, conserve configuração, lock, regras/testes, estado de suas execuções, snapshots referenciados e metadados de imagens. Não precisa copiar caches temporários. Faça backup em destino fora do repositório e dos volumes.

Salve os IDs exatos referenciados por cada lock:

```sh
docker image save --output imagens.tar sha256:ID_REAL_REGISTRADO
# Na máquina de destino:
docker image load --input imagens.tar
```

O ID no exemplo é um marcador a substituir, não uma imagem válida. [Docker save](https://docs.docker.com/reference/cli/docker/image/save/) preserva camadas/metadados. Para compactação, gzip do arquivo exportado é opcional. Seu tamanho não representa necessariamente bytes transferidos por um registry.

Copie o repositório e consumidores para o destino. Copie também explicitamente `tools/*/volume/` e `.local/state/`, pois são dados ignorados pelo Git e não acompanham um clone. Se usar `--state`/`TOOLBOX_STATE` personalizado, copie também esse índice. Restaure permissões para o usuário local sem alterar fontes de outros projetos; selecione o novo caminho de estado. Verifique hashes de snapshots e IDs com `docker image inspect`, depois `doctor`, `plan` e `run`. Fontes absolutos de execuções antigas são metadados históricos; novas execuções resolvem o root local da configuração.

Reconstrução precisa do digest da base, snapshot APT disponível, hashes/URLs dos artefatos, versões npm, receita e dependências. Nesta versão, os manifests de preparação ainda não foram produzidos e não existe promessa de reconstrução bit a bit. Arquivar imagens prontas evita depender de disponibilidade futura dos distribuidores. AMD64 é o único alvo de recipes; ARM64 exige novas receitas e evidência. Kernel, Docker, CPU e sandbox do navegador também afetam a portabilidade. Nenhuma segunda máquina foi usada.

## Dados anteriores à mudança de layout

Dados antigos não são movidos nem apagados automaticamente. Os diagnósticos anteriores em `.local/validation-state/` permanecem como histórico; nenhum banco ou imagem tinha sido preparado nesta sessão. Novas execuções usam o layout por ferramenta.

Em instalações antigas com dados reais, migre com as execuções paradas e backup prévio: `images/<tool>/<version>/<recipe>` vai para `tools/<tool>/volume/images/<version>/<recipe>`; `databases/<tool>/<version>/<sha>` e os metadados irmãos vão para `tools/<tool>/volume/databases/<version>/`; cada `projects/<project>/runs/<id>/<tool>/` vai para `tools/<tool>/volume/projects/<project>/runs/<id>/`. Preserve os índices antigos para consulta histórica: eles podem conter caminhos absolutos antigos. Não execute limpeza desses índices antes de reconciliar os dados migrados. A migração de uma instalação com imagens/bases reais não foi validada.
