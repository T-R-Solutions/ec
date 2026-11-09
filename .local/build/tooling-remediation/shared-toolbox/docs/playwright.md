# Contrato Playwright

A receita candidata usa Debian slim próprio, Node 22.20.0 e @playwright/test 1.56.1 com dependências npm exatas/SRI. As tabelas [nativeDeps.ts](https://raw.githubusercontent.com/microsoft/playwright/v1.56.1/packages/playwright-core/src/server/registry/nativeDeps.ts) e [hostPlatform.ts](https://raw.githubusercontent.com/microsoft/playwright/v1.56.1/packages/playwright-core/src/server/utils/hostPlatform.ts) incluem Debian 13. Isso estabelece comportamento esperado, **não um build validado**.

## Variantes e imports

`variant: api` prepara a mesma ferramenta sem browsers; `chromium`, `firefox` e `webkit` preparam somente o navegador escolhido. Não há download de navegador no openSUSE ou durante testes. Imagens oficiais Ubuntu são referências, sem atender por si ao requisito Debian slim.

O consumidor fornece testes, configuração e fixtures externos. `/src` é somente leitura. A CLI copia o projeto para `/out/work`, omitindo `.git` e `node_modules`; o entrypoint cria `/out/work/node_modules -> /opt/pw/node_modules`. Imports nativos procuram esse diretório, sem depender de npm global ou NODE_PATH. Exemplos `.mjs` usam imports ESM inclusive em fixtures e configuração. A ligação é implementação candidata; a demonstração prática ainda está bloqueada pelo daemon.

O contrato inicial aceita somente módulos builtin do Node e o pacote central. Pacotes adicionais de teste precisam de um bundle separado por consumidor, com package-lock, integridade e preparo explícito. Esse bundle ainda **não está implementado**; não instale uma união global dos pacotes dos consumidores nem use dependências da máquina host. Dependências da aplicação continuam pertencendo ao ambiente da aplicação.

## Configuração e artefatos

O consumidor deve habilitar reporter `json`. A CLI fornece `PLAYWRIGHT_JSON_OUTPUT_FILE=/out/native.json`. Mantenha reporters extras na configuração; HTML usa `/out/html` e `open: never`. `outputDir: '/out/test-results'` preserva screenshots, traces e vídeos habilitados, mesmo após remover o container. A cópia inteira de trabalho permanece no resultado para diagnóstico. Testes não devem escrever em `/src`.

Headless é obrigatório no fluxo inicial. O exemplo API usa apenas `request`/APIRequestContext contra servidor HTTP local do fixture e não solicita `page` ou `browser`. O exemplo UI usa `page` em Chromium. Nenhum teste executado, erro global, JSON ausente ou processo sem exit original resulta em aprovação.

## Endereços da aplicação

- Host: selecione `network: bridge` e configure `BASE_URL=http://host.docker.internal:PORT`; o runner adiciona `host-gateway`. A aplicação precisa escutar em endereço acessível pela bridge, não apenas no loopback do host.
- Outro container: consumidor cria sua rede Docker e configura `network: nome-da-rede`, com `BASE_URL=http://nome-do-servico:PORT`.
- URL externa: `network: bridge`, `BASE_URL=https://...`, certificados e acessos exigidos pela aplicação.
- Testes autocontidos: `network: none`, como os dois exemplos.

Não é necessário socket Docker dentro do Playwright. Bancos e serviços da aplicação são preparados pelo consumidor. As rotas acima não foram verificadas nesta sessão.

## Depuração visual opcional

Primeiro preserve e examine HTML, screenshots e traces. Para `--headed`/`--ui`, será necessário adicionar um fluxo específico, revisar dependências de display, encaminhamento e segurança. Esse fluxo não está implementado e não exige instalação nativa de browsers como parte do caminho headless. Playwright não substitui testes de desktop nativo ou aceitação X11/Wayland. Não há afirmação de isolamento de código não confiável; sandbox do navegador e seccomp precisam ser validados no host antes de prometer esse uso.
