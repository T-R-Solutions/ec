# Automação Ec

A entrada é `bash scripts/automate.sh plan|run`. A configuração responsável é
[`config/automation.json`](config/automation.json): tarefas, dependências,
componentes, executores, ambiente, serviços, limites, cobertura, navegadores e
referências de políticas. O carregador
[`automation_config.py`](../tools/quality/automation_config.py) valida e projeta
esses dados; scripts não mantêm catálogos alternativos.

A autorização de execução/download pertence exclusivamente à
[política de verificação](../docs/development/task-verification.md).
Comandos abaixo descrevem a interface, sem autorizar execução automática pelo agente.

```bash
bash scripts/automate.sh plan --scope complete
bash scripts/automate.sh run --scope complete
```

Sem `--scope complete`, complementares Grype, Bandit, Syft e YARA-X ficam fora.
`--surface` e `--check` permitem seleção explícita, preservando governança e o
fechamento transitivo das dependências. `coverage` exige uma superfície explícita.
A suíte completa não inclui os opcionais `dev`, `setup`, `lint`, `performance` ou
`build-budget`. Uma opção sem script/política do produto não simula sucesso.

## Responsabilidades

| Execução | Recursos |
| --- | --- |
| Host, toolchain Rust ativo | Cargo check/fmt/clippy/test/build; plugins instalados machete/deny/audit/geiger/llvm-cov |
| Host, runtime da aplicação | Node/npm do frontend, Python dos harnesses, Git, binários Server/Agent |
| Toolbox/Docker | Ruff, ShellCheck, Gitleaks, Opengrep, OSV, Trivy, CodeQL, Grype, Bandit, Syft, YARA-X e Playwright |
| Docker, serviço de teste | PostgreSQL dedicado declarado na configuração |
| Playwright/Docker | Chromium e Firefox; nenhum Firefox/Chromium executado no sistema |

O Ec não fixa versões de ferramentas nem baixa uma cópia particular do Geiger.
Identifica os executáveis instalados e a imagem/versão/base usadas naquele run.
`Cargo.toml` continua definindo compatibilidade das bibliotecas; `Cargo.lock`
registra a resolução. O npm continua usando o manifest e lock da aplicação.

Configurações de regras nativas permanecem em `config/native/` e `rules/`, em seus
formatos próprios, referenciadas pela configuração principal. Não há segunda
política de cobertura nem cadastro paralelo de projetos CodeQL. `catalog.sh`,
`environment.sh` e `inputs.sh` apenas consomem as projeções do carregador.

O inventário de fontes usa `inputs.exclude_names` (padrões de nomes em qualquer
nível) e `inputs.exclude_paths` (caminhos exatos desde a raiz). Essas exclusões
valem antes da seleção de alvos dos scanners e da cópia das fontes; o `.gitignore`
não é interpretado. Dependências instaladas, builds, ambientes virtuais, caches,
cobertura, relatórios de testes e arquivos temporários ficam fora. Estado local
do frontend e da aplicação tem exclusões por caminho. Fontes, testes, fixtures,
lockfiles, logs de evidência e plugins/releases Yarn versionáveis continuam
elegíveis conforme o escopo; não há exclusão genérica de `.yarn` ou `*.log`.

## Preparo, execução e encerramento

1. Validar configuração e grafo antes de executar. `plan` não prepara recursos.
2. Criar ambiente selecionado, lock do checkout e workspace temporário privado.
3. Executar os recursos `prepare:*` necessários antes dos consumidores. Falha de
   um recurso bloqueia os dependentes, mantendo independentes executáveis.
4. Executar tarefas com deadlines, streams separados e classificação conservadora.
5. Comparar entradas, restaurar serviços iniciados pelo run, exportar logs e
   descartar temporários. Falha de limpeza impede aprovação.

Cargo resolve e verifica disponibilidade offline uma vez por workspace, antes
de consumir o grafo. Reutiliza o cache do usuário; não baixa crates implicitamente.
Não usa `--locked`. Mudança de lock é preservada, identificada por hashes e exige
nova suíte sobre a resolução final. Mudanças de outras entradas também reprovam.

Audit e Deny usam a mesma base RustSec selecionada. O overlay temporário do Deny
adiciona somente localização, URL e idade da base, mantendo a política de
licenças/origens. Dependências `path` de crates privados são permitidas sem
versão artificial; wildcards de registros externos permanecem proibidos.

Cobertura usa LLVM do toolchain ativo ou de outra instalação local com a mesma
release, commit, arquitetura e versão LLVM. Isso não troca o compilador nem fixa
uma versão. Ausência de ferramentas compatíveis identifica a instalação necessária.
Targets de cobertura e builds comuns são separados; falha de produtor/harness
não aprova LCOV, e cada componente mantém o limiar canônico de linhas.

PostgreSQL: o preparo valida o container dedicado e seu bind loopback antes de
iniciar. Não cria container, imagem ou volume. Se já estava ativo, continua ativo;
se o run o iniciou, o encerramento para exatamente aquele ID. Bancos individuais
continuam sendo criados/removidos pelos harnesses. Queda abrupta do host/SIGKILL
exige recuperação manual pelo recibo, sem alegação de limpeza garantida.

## Bases e scanners

OSV tem renovação automática própria da Toolbox. Trivy e Grype inspecionam suas
bases antes de scans; ausência/expiração permite manutenção explícita quando
habilitada e autorizada. Trivy renova DB e Java DB; Grype usa `db update` na sua
imagem ativa. Base futura, adulterada ou inválida não é tratada como mera expiração.
Aquisição bem-sucedida é seguida de nova inspeção, vínculo ao snapshot publicado
e verificação da idade upstream. Scan usa rede `none`, bases somente leitura e
sem supressões introduzidas pela automação. Downloads obedecem à política canônica.

Trivy conserva eficácia positiva/negativa de vuln/misconfig/secret e Checks Bundle
externo compatível. OSV/Trivy exigem proveniência dos bytes/gerações, sem usar a
hora do download para aparentar dados recentes. Grype analisa inventário Syft
validado. O check `security:syft` também submete seu SBOM ao Grype: aprova
somente com inventário válido, base íntegra e recente e zero vulnerabilidades.
Preserva a contagem de artefatos; a aprovação cobre as vulnerabilidades
conhecidas na base observada, não toda a segurança da aplicação.

CodeQL executa create/resolve/analyze para os componentes declarados e para a
automação Python adicional, com suítes security-and-quality por linguagem. O extrator Docker usa o toolchain host selecionado e cópia
privada apenas das dependências Cargo, sem copiar credenciais/configuração pessoal.

## Navegadores

`e2e:playwright` verifica a sessão com API simulada. `e2e:chromium` e `e2e:firefox`
executam a mesma jornada com Server, Agent, PostgreSQL e Git reais. Cada navegador
vem da Toolbox, selecionado por `exec --browser`, sem mudar a ativação global.
A plataforma exige variante preparada/registrada compatível com a versão ativa.
Navegador ausente bloqueia essa etapa; não há instalação ou fallback para o host.

O build estático e seu servidor/proxy Node executam dentro do container Playwright.
A jornada real usa rede host explicitamente para alcançar os serviços loopback;
os testes com API simulada continuam isolados com rede none. O controle do browser
usa arquivos no diretório privado do run, sem endpoint de automação aberto.
Respostas, credenciais e downloads dessa comunicação são temporários, sem entrar
nos logs. Cliques/teclado são Playwright; campos continuam usando eventos DOM.
Não se afirma cobertura de leitor de tela ou certificação WCAG por esse ensaio.

## Resultados e diagnóstico

A agregação mantém `2 erro > 3 bloqueado > 1 achados > 0 aprovado/medido`.
Um processo nativo sem relatório completo ou com limpeza falha não aprova.
O relatório agregado separa tarefa, retorno nativo, estado e motivo.

Somente logs privados de duas datas permanecem em `.local/logs/AAAA-MM-DD/`.
Além de stdout/stderr, permanecem resultados nativos JSON/SARIF como `.log`,
metadados de execução, resultados por alvo CodeQL, diagnósticos Geiger e recibo
de serviços. Gitleaks/Trivy exportam relatórios somente depois da redação de
segredos. JSON inválido de ferramenta sem segredos permanece para diagnóstico.
Snapshots, builds, perfis, IPC e screenshots são descartados conforme
[estado local](../docs/development/local-state.md).

Preparação e variáveis: [guia operacional](../docs/development/verification-tools.md).
Mapa de consumidores: [inventário](../docs/development/scripts-inventory.md).
Estado de aceite e histórico: [feature SDD](../sdd/features/scripts-verification/converge.md).


### Extração e achados revisados

CodeQL exige extração saudável comprovada pelos diagnósticos e métricas nativos;
a presença de um arquivo em SARIF não basta. Cargo/rustc do extrator preservam
stderr em logs privados. Geiger usa o plugin instalado com identidades resolvidas.
Bandit mantém o scan integral e aplica somente a triagem por hash/regra/linha
registrada na configuração canônica; o resultado distingue contagem bruta,
revisada e pendente. Gitleaks usa a mesma vinculação para indicadores públicos
da árvore atual; ela nunca aprova ocorrências do histórico. Consulte o
[contrato de preparação e triagem](../docs/development/verification-tools.md).


### Ampliação da cobertura

A política Ruff existente seleciona regras estáveis para scripts stdlib/CLI e
testes unittest. Erros, imports e construções suspeitas continuam bloqueando;
Bandit cobre segurança Python integralmente. Os grupos e as quatro exceções E402
de bootstrap estão somente no arquivo nativo referenciado pelo catálogo.
O gate ignora noqa e não aplica correções. Não existem configurações por diretório.

A configuração vigente amplia ShellCheck/Clippy, inclui dotenv e histórico
Git no Gitleaks, catálogos Opengrep/YARA com revisão/licença/hash e CodeQL Python.
Trivy/Syft/Grype examinam somente o snapshot de fontes, dependências e configurações
do Ec. A Toolbox fornece os executores; suas imagens e pacotes não são alvos de auditoria.
Detalhes e referências oficiais estão no [guia de cobertura](../docs/development/verification-tools.md).
As entradas root_patterns tornam dotenv da raiz elegível; exclusões de caches e
credenciais externas permanecem. Catálogos são conferidos antes do uso.
Não tratar os runs anteriores como validação da configuração ampliada.
