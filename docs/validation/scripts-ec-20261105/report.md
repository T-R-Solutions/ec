# Adaptação de scripts e encerramento Ec — 2026-11-05

Implementação aplicada; verificação agregada **UNVERIFIED**, pois Gitleaks 8.30.1
não está instalado. Nenhuma ferramenta foi instalada globalmente, nenhum commit,
tag ou publicação foi realizado.

## Escopo e decisões

92 arquivos originais inventariados; 82 removidos da árvore ativa de scripts, com
bytes originais e licenças preservados em previous.zip e inventário por SHA-256 em
inventory.json. Retirados Tauri/releases/certificados desktop, Svelte, Graphify,
Bun obrigatório, exemplos Kotlin/Gradle, wrappers sem tarefa, baselines/regras de
outros produtos, métricas sem política, governança duplicada e scanners sem
configuração Ec. Critérios de utilidade/licença/configuração estão no inventário
permanente docs/development/scripts-inventory.md. Nenhum relatório histórico fora
do escopo foi alterado; pesquisa e bundle original permaneceram intactos.

Entrada única: `python3 scripts/automate.py run`; padrão conservador cobre todas as
superfícies. `plan` inspeciona e `--surface` repetível permite escopo conhecido.
Workspaces Rust usam seus toolchains/locks reais, fmt/clippy/test offline e targets
separados; frontend usa npm/vue-tsc/Vitest/Vite. Não há lint/performance/cobertura
fictícios. Configuração ativa em scripts/automation/ec.toml, sem placeholders.

Política canônica em docs/development/task-verification.md, referenciada por AGENTS,
README, guias, SDD/templates e pontes/skills delivery/testing. Dois SKILL.md e apenas
seus pacotes na baseline foram sincronizados com autorização do ambiente; metadados
e hashes revisados, source_files/bundle e as outras 65 entradas preservados. O auditor
rejeitou links que escapavam dos pacotes; referências textuais relativas à raiz Ec
resolvem isso sem enfraquecer o auditor. quick_validate passou nas duas skills.

O harness Firefox grava artefatos por run (ou temporário exclusivo no uso avulso),
preservando observações de frontend-20261105. A view de recuperação do instalador e
as fixtures de governança agora incluem scripts, destino real dos novos links.
Os testes existentes de recuperação/conflito continuaram obrigatórios e passaram.

## Baseline e correções observadas

baseline-1 a baseline-5: auditor, auditor require-installed, preflight ALREADY_INSTALLED,
SDD e 34 testes PASS antes de editar. Manifests/código reais foram confirmados, sem
usar as descrições documentais antigas como prova de ausência de aplicação.

Execuções intermediárias foram preservadas: bloqueio do socket Docker no sandbox;
refs de evidência ficaram stale após mudar harness/política; fixtures precisaram do
novo destino scripts; preflight de portas precisou SO_REUSEADDR para TIME_WAIT,
continuando a rejeitar listener ativo. Uma rodada durante edição rejeitou inputs
alterados (cli/tests/view do instalador). Esses resultados não são mascarados como
aprovação. A rodada final abaixo foi feita após estabilizar os arquivos.

## Resultado final observado

Comando executado:
`python3 scripts/automate.py run --surface integration --surface e2e --surface security --surface scripts`

Seleciona os mesmos comandos do padrão sem --surface: governança sempre incluída,
integração/E2E incluem ambos workspaces e frontend. Plano padrão em final-plan.json.
Run privado .local/automation/runs/run-05myecps; registros oficiais final-results.json,
final-inputs.json e final-*-stdout/stderr.log. Nenhum input mudou durante execução.

- `docs:governance`: passed, exit bruto 0.
- `docs:installed`: passed, exit bruto 0.
- `docs:skills`: passed, exit bruto 0.
- `docs:sdd`: passed, exit bruto 0.
- `docs:governance-tests`: passed, exit bruto 0.
- `scripts:tests`: passed, exit bruto 0.
- `server:fmt`: passed, exit bruto 0.
- `server:clippy`: passed, exit bruto 0.
- `server:test`: passed, exit bruto 0.
- `server:harness-build`: passed, exit bruto 0.
- `agent:fmt`: passed, exit bruto 0.
- `agent:clippy`: passed, exit bruto 0.
- `agent:test`: passed, exit bruto 0.
- `agent:harness-build`: passed, exit bruto 0.
- `frontend:typecheck`: passed, exit bruto 0.
- `frontend:test`: passed, exit bruto 0.
- `frontend:build`: passed, exit bruto 0.
- `security:secrets`: blocked, exit bruto None; ferramenta ausente: gitleaks.
- `integration:publication_http`: passed, exit bruto 0.
- `integration:orchestration_http`: passed, exit bruto 0.
- `integration:agent_http`: passed, exit bruto 0.
- `e2e:firefox`: passed, exit bruto 0.

34 testes de governança e 16 testes do runner passaram. Rust fmt/Clippy/test dos dois
workspaces passaram; frontend typecheck/test/build passaram. Builds test-failpoints
foram feitos antes de publication_http, orchestration_http, agent_http e Firefox,
executados sequencialmente em ec-publication-test/55439 com bancos UUID descartáveis.
O container preexistente observado usa itapiraco/postgis:18-3.6-alpine; digest da
imagem efetiva foi registrado por tarefa em final-results.json. O ensaio não criou
container nem alterou bancos existentes. O exemplo PostgreSQL oficial permanece
no guia publication.md; não foi baixado aqui.

Os 16 testes cobrem exit bruto e continuidade após falha, bloqueio por ferramenta e
dependência ausentes, timeout/cancelamento, overflow, retenção/permissões dos logs,
locks, alteração de inputs, rejeição de symlink, roteamento, outro cwd e raiz com
espaços, ausência de erase.md, listener ativo e classificação de relatórios/achados
Gitleaks por executável fixture. Esses dois últimos não equivalem a executar o
scanner real. Não foram instalados navegadores/dependências pelo runner.

Verificação de referências operacionais a arquivos removidos: nenhuma; código de
automação não referencia erase.md. O resumo de reports/summarize.py foi executado
sobre results.json observado, sem alterar estados. Logs brutos permanecem privados
no run; artefatos copiados foram revisados antes de retenção documental.

## Gates e retomada

A estrutura/freshness SDD comum passa após as novas observações. O gate com
--require-approved --require-fresh permanece bloqueado em scripts-verification AC-04
(Gitleaks ausente) e first-functional-journey AC-01/AC-07 (segredos por referência e
aceite integral ainda pendentes antes desta tarefa). Não foram reduzidos os gates.
Novos IDs de evidência registram execução/revisão corrente; versões anteriores
ficam em previous.zip. O aceite de governança não aprova a jornada de produto.

Preparar explicitamente Gitleaks 8.30.1 no PATH conforme documentação permanente
(e erase.md, consulta temporária com fontes oficiais) e repetir `python3 scripts/automate.py run --surface security`.
Depois reobservar AC-04 e executar os gates; os bloqueios da jornada exigem suas
próprias tarefas. Sem scan de histórico Git, SAST/CVEs/licenças de dependências ou
certificação de isolamento. Nada depende de apagar ou conservar erase.md.
