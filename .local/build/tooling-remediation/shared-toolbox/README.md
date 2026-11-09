# Shared Toolbox

Infraestrutura independente para centralizar ferramentas de testes, segurança e qualidade em imagens Docker separadas. Configuração e política continuam nos projetos consumidores. Um módulo por ferramenta; nenhum banco central ou serviço permanente.

**Estado: núcleo implementado, integração Docker ainda não validada.** Esta sessão não tem acesso ao daemon Docker nem DNS no shell. Não há imagens construídas ou ferramentas reais executadas. O digest Debian está intencionalmente sem valor e bloqueia builds. Isso não é uma entrega completa do inventário solicitado. Consulte [evidências e pendências](docs/validation.md) antes de adotar.

## Organização

- `bin/toolbox`: CLI central, Python 3.11+ e biblioteca padrão.
- `toolbox/`: configuração, preparação, Docker, armazenamento e resultados.
- `tools/<nome>/`: manifesto, receita independente, documentação e adapter quando implementado; dados locais em seu próprio `volume/` (ignorados pelo Git).
- `profiles.json`, `platform.json`: seleções disponíveis e identidade da plataforma.
- `examples/`: dois consumidores com regras diferentes e exemplos Playwright HTTP/UI externos.
- `tests/`: testes do núcleo e roteiro de aceitação real; `evidence/`: observações reproduzíveis.

## Começar

Consulte o [guia de configuração e execução por ferramenta](docs/usage.md) para preparar o ambiente, configurar cada aplicação e consultar os resultados.

```sh
bin/toolbox doctor
bin/toolbox catalog
bin/toolbox versions --config examples/consumer-a/toolbox.json
bin/toolbox validate --config examples/consumer-a/toolbox.json
bin/toolbox plan --config examples/consumer-a/toolbox.json
```

`versions` consulta o lock e a presença local das imagens; não instala ferramentas. Nesta sessão retornará pré-requisito ausente. Os dados persistentes ficam em `tools/<ferramenta>/volume/`. `TOOLBOX_STATE` e `--state` controlam somente índices de execuções e locks gerais; o padrão é `.local/state/` neste repositório.

Quando Docker/rede estiverem disponíveis, resolva/revise a base e prepare explicitamente:

```sh
bin/toolbox lock-base
bin/toolbox prepare --config examples/consumer-a/toolbox.json
bin/toolbox prepare --config examples/consumer-b/toolbox.json
bin/toolbox run --config examples/consumer-a/toolbox.json --only ruff
bin/toolbox results --project consumer-a
```

Os exemplos esperam Ruff/Gitleaks com achados no consumidor A e controles sem achados no B. Ambos selecionam as mesmas versões e receitas; a verificação de reúso de imagens ainda depende de build real. Nenhuma ferramenta é instalada no repositório consumidor; o preparo só grava seu lock de imagens. Bibliotecas para compilar/executar a aplicação testada continuam sendo responsabilidade do consumidor.

## Disponibilidade real

| Estado | Ferramentas |
|---|---|
| Receita e adapter candidatos, não executados | Gitleaks, Ruff, ShellCheck, Grype, Playwright |
| Pesquisa incompleta e integração bloqueada | OSV-Scanner, Opengrep, Bandit, Trivy, Syft, YARA-X |

Dockerfiles bloqueados falham deliberadamente; receitas candidatas também não equivalem a imagens funcionais. Perfis não baixam stacks não selecionadas; `web-api` não baixa browsers, `web-ui` deve escolher variante com navegador. Não foram importadas automações, regras, governança ou diretórios de Ec/Ox.

## Verificar

```sh
python3 -m unittest discover -s tests -v
python3 tests/acceptance.py --state /caminho/central/fora-dos-consumidores
```

Testes unitários validam lógica da plataforma. O roteiro de aceitação invoca a CLI com Docker real e falha quando imagens faltam; não usa scanners falsos. A aceitação registrada nesta sessão **falhou por pré-requisitos ausentes**, conforme esperado; não comprova execução das ferramentas.

## Documentação

[Arquitetura e sobreposição](docs/architecture.md), [matriz oficial](docs/references.md), [contrato JSON](docs/configuration.md), [Playwright](docs/playwright.md), [operação, atualização, backup e migração](docs/operations.md), [validação e lacunas](docs/validation.md). Os comandos de instalação no Tumbleweed ainda aguardam confirmação da fonte oficial inacessível; nenhuma mudança do host foi aplicada.
