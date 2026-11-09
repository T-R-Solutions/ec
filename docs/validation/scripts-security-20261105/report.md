# Reintegração de Opengrep e OSV ao Ec — 2026-11-05

Opengrep e OSV foram reintegrados ao perfil security e ao padrão conservador do
runner. A configuração herdada foi adaptada, mantendo a necessidade dessas análises.
O scan real continua **UNVERIFIED**: Gitleaks, Opengrep e OSV estão ausentes no PATH,
e as bases OSV crates.io/npm não foram preparadas. Nada foi instalado globalmente,
nenhum binário foi baixado e não houve commit/publicação ou mudança de aplicação.

## Implementação e decisões

- scripts/security/commands/opengrep.sh, osv-scanner.sh e security/verify.sh encaminham ao runner real, preservando exit/cwd/argumentos.
- Opengrep 1.33.1: teste de regras com JSON/comparação de linhas (teste vazio rejeitado) e scan local de Rust/TS/Vue. Regras/fixtures para SQL PostgreSQL dinâmico, hashes fracos, memória não inicializada, eval/Function, HTML bruto, persistência de bearer e v-html/eval Vue. Não foram restauradas regras Tauri/Svelte/SQLite, owners de outro produto ou proibição geral do shell autorizado do Agent.
- Vue usa indicadores textuais generic, com limites de parsing/taint explicitados. O snapshot observado contém 14 .rs, 15 .ts e 3 .vue da aplicação; fontes e hashes em source-scope.json. Fixtures de regras ficam fora desse alvo.
- OSV 2.7.0: os dois Cargo.lock e package-lock.json explícitos, modo offline, inventário completo, all-vulns e sem call analysis Rust. Não resolve manifests, executa builds ou herda ignores do alvo. Config TOML local vazia de exceções.
- Bases em .local/security/osv, hashes e data de observação registrados explicitamente por record-osv-db.py, com retenção do manifesto anterior. Antes/depois do scan são comparados. Identidade por hash não comprova autenticidade nem atualização recente; a preparação deve ser atualizada e revisada antes do aceite, sem inventar prazo de validade.
- Relatórios estritos: erros/skips, arquivo ou lock omitido, pacote sem identidade, supressão reportada, JSON duplicado/inválido e exit inconsistente impedem aprovação. Diagnósticos/códigos brutos são preservados e o resultado parcial informa os checks selecionados.

A política canônica continua em docs/development/task-verification.md e remete ao
guia scripts/security/README.md. AGENTS/README/SDD/skills já usam essa política;
não houve edição de skills, baseline instalada, perfil ou bundle nesta revisão.
O inventário corrente registra cinco caminhos originais reintegrados/refatorados
mais recursos locais novos; o inventário e os relatórios da primeira adaptação
permanecem preservados. A falta de configuração não torna scanner inaplicável.

## Observações executadas

Baseline: auditores comum/require-installed PASS, preflight ALREADY_INSTALLED,
SDD comum PASS e 34 testes de governança PASS (baseline-1 a baseline-5).
Após implementar, 32 testes da automação PASS e 34 testes de governança PASS; logs
novos automation-tests-final.log e governance-tests.log. Novos testes verificam escopo,
versões, snapshots, execução por fixture, classificação de resultados, base
incompleta/alterada e metadados inválidos, caminhos com espaços/outro cwd e wrappers.
YAML/anotações positivas/negativas de oito regras foram inspecionados com PyYAML já
disponível; rules-inspection.json não representa validação do motor Opengrep.

A tentativa inicial do runner dentro do sandbox rejeitou a fixture de socket
loopback existente e evidências SDD ainda stale durante edição. Isso foi preservado
no run-l784ygi7; não se declara aprovação dessa execução. A suíte foi repetida com
permissão autorizada de sockets locais e passou. Falta de relatório é erro, distinto
de ferramenta ausente. Nenhum teste com scanner fixture representa motor real.

A rodada de encerramento usa:
`python3 scripts/automate.py run --surface security --surface scripts`.
Resultado retido em closure-results.json, com inputs estabilizados: **exit 3**, seis
checks de governança/automação passaram, quatro checks de segurança bloqueados;
nenhum input mudou durante a execução. Bloqueios observados: secrets/sast-rules/sast/dependencies por CLIs
não instaladas; também faltam as bases OSV. A execução final repetiu com sucesso os 32 testes da automação e 34 de governança.
Fontes e comandos de instalação atualizados
em erase.md (temporário) e pré-requisitos/preparo no guia permanente. Não há código
ou evidência vinculado a erase.md: sua remoção posterior não quebra gates.

Mudanças limitadas à automação/docs/SDD; Rust/frontend e harnesses de aplicação não
foram reexecutados neste incremento. Os resultados históricos desses componentes
continuam preservados, sem serem apresentados como execução nova. Aceite global
continua pendente, incluindo AC-01/AC-07 da primeira jornada e AC-04 da verificação.

## Fontes oficiais e retomada

Versões/flags/assets revisados: [Opengrep 1.33.1](https://github.com/opengrep/opengrep/releases/tag/v1.33.1),
[CLI](https://github.com/opengrep/opengrep/blob/v1.33.1/src/osemgrep/cli_scan/Scan_CLI.ml),
[OSV 2.7.0](https://github.com/google/osv-scanner/releases/tag/v2.7.0),
[flags](https://github.com/google/osv-scanner/blob/v2.7.0/cmd/osv-scanner/internal/helper/flags.go),
[JSON](https://github.com/google/osv-scanner/blob/v2.7.0/pkg/models/results.go),
[offline/bases](https://github.com/google/osv-scanner/blob/v2.7.0/docs/offline-mode.md).
Instruções usam versões/hashes publicados, sem tags flutuantes ou imagem Docker
presumida. Binários nativos consomem PATH; OSV offline não envia o grafo à API.

Preparar CLIs e bases explicitamente; repetir o perfil security, corrigir achados e
reexecutar critérios afetados, inclusive sast-rules com o motor real. Então atualizar
AC-04 com observação nova e executar --require-approved --require-fresh. Não alterar
hashes antigos nem dispensar gates de outras features.
