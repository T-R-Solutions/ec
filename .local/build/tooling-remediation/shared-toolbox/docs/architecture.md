# Decisões arquiteturais e limites

## Fronteira de responsabilidade

A infraestrutura fornece imagens independentes, executáveis, runtimes necessários, preparação explícita de dados e coleta de resultados. O consumidor mantém fontes, testes, fixtures, regras, exceções, políticas, configuração, URLs e serviços da aplicação. Nenhum conteúdo do Ec ou do Ox foi consultado, copiado ou modificado.

Docker é a execução principal; não há alternativa nativa silenciosa. Não existem Compose global, daemon da plataforma, scheduler, banco central ou serviço permanente. Python 3.11+ da CLI e o cliente/daemon Docker local são os únicos pré-requisitos de execução no host. Git é necessário para obter/manter este repositório, não para cada execução.

## Modularidade

`tools/<nome>/` contém `tool.json`, `Dockerfile`, documentação e, quando implementado, adapter. `toolbox/` contém configuração, armazenamento, Docker, preparação e execução. O consumidor copia apenas sua configuração e versiona o lock de IDs de imagens. Não copia wrappers nem Dockerfiles.

Uma versão adicional pode morar em `tools/<nome>/versions/<semver>/`, com seu próprio contrato, adapter, receita e pesquisa. O caminho de armazenamento inclui versão e identidade da receita; locks antigos continuam usando seus IDs imutáveis. Esse mecanismo foi implementado; versões incompatíveis reais em paralelo ainda não foram construídas/testadas. A seleção de outras versões sem um módulo auditado é rejeitada.

## Base e preparação

Base proposta: Debian 13 trixie-slim, linux/amd64. Debian documenta seu ciclo em [Release Information](https://www.debian.org/releases/trixie/). `platform.json` exige digest real, ainda **não resolvido**. `lock-base` usa pull explícito e captura RepoDigests; depois é necessário revisar e versionar esse arquivo. `prepare` recusa base sem digest. Nenhum hash fictício aparece como identidade válida.

APT utiliza um snapshot proposto fixo. Disponibilidade desse snapshot e seus pacotes não foi verificada. Receitas não afirmam reprodução bit a bit: conservar imagens prontas é a transferência mais forte atualmente prevista. Capturar dpkg, fontes, SHA256/SRI, versão observada, ID e tamanho local faz parte da preparação.

Binários oficiais evitam adicionar Go/Haskell/Python a imagens que não precisam desses runtimes. Multi-stage retira arquivos de extração e npm da imagem Playwright final. Bandit ainda precisa de um fechamento de pacotes Python com hashes.

## Execução e persistência

`run` não constrói, não instala, não faz pull nem atualiza bases. Fontes ficam em `/src:ro`; `/out` é o diretório privado gravável. Playwright usa uma cópia de trabalho. UID/GID vêm do host. Não há modo privilegiado nem socket Docker dentro das ferramentas; isso não constitui prova de sandbox para código hostil.

Dados persistentes de cada ferramenta ficam em `tools/<tool>/volume/`, com resultados separados por projeto/run. Os índices agregados e inputs ficam em `.local/state/` (configurável por `--state`) e não são montados nos containers. Escritas JSON são atômicas e IDs incluem timestamp UTC e UUID. Bases Grype são snapshots identificados pelo hash do conteúdo e verificadas antes de copiar para cada execução; não há SQLite gravável concorrente compartilhado. Credenciais não são montadas ou coletadas globalmente; o contrato inicial só aceita `BASE_URL` como ambiente de aplicação.

Timeout é por ferramenta e configurável, sem impor limite de aprovação. O runner solicita stop, coleta State/exit original e remove o container. SIGKILL do host não é recuperável automaticamente: arquivos `started.json` sem `result.json` indicam execução interrompida e exigem inspeção. Docker remoto não é suportado devido ao significado dos bind mounts.

## Sobreposição de vulnerabilidades

OSV compara dependências de múltiplos ecossistemas com advisories.

Trivy e Grype analisam pacotes identificados em filesystem/imagens; cobrem também pacotes do sistema. Bases, extração e matching diferem, então resultados podem divergir e não devem ser somados como vulnerabilidades únicas. Syft fornece inventário/SBOM reutilizável, sem emitir aprovação de segurança. Escolha por stack, alvo e política; disponibilizar um perfil não implica executar todos os scanners.

## Rede e offline

`network: none` bloqueia rede no runtime, mas não substitui flags específicas para evitar atualização. Grype desativa atualização e verifica idade conforme política explícita do consumidor. JSON inválido, base ausente/antiga ou análise não executada nunca aprovam. Gitleaks/Ruff/ShellCheck não usam DB externo, mas ainda dependem de suas imagens preparadas. Offline real de qualquer scanner ainda não foi demonstrado nesta sessão.
