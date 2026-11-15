# Revisão semântica dos achados atuais — 15/11/2026

Escopo: 74 ocorrências pendentes do Bandit 1.9.4 registradas em
`.local/logs/2026-11-15/run-Tfjg1Tyv-45-security-bandit.bandit-review.log`.
O relatório nativo contém 375 ocorrências, das quais 301 já tinham revisão vigente.
Esta inspeção não altera o relatório nativo nem revalida sua execução histórica.
As oito fontes foram lidas no contexto dos consumidores; seus hashes atuais
correspondiam aos inputs registrados antes da alteração da configuração.

Objetivo: substituir revisões invalidadas e revisar o arquivo novo sem remover
fontes, regras ou testes do scan. O matcher continua exigindo hash, regra, linha
e motivo. A autorização atual de executar o plano permite esta revisão; a
segurança geral do produto e a aceitação de riscos novos não são declaradas.
Não foi demonstrado defeito explorável nas 74 ocorrências. As condições locais
abaixo justificam triagem pontual; mudança de conteúdo exige revisão novamente.

| Fonte | Regra | Linhas atuais | Ocorrências | Justificativa e precondições examinadas |
| --- | --- | --- | ---: | --- |
| `application/integration/tests/e2e/frontend_browser.py` | B101 | 70, 72, 73, 74, 75, 85, 104, 109, 111, 112, 116, 123, 124, 125, 127, 131, 132, 139, 140, 141, 147, 148, 149, 155, 158, 167, 170, 171, 181, 184, 185, 196, 197, 201, 203, 204 | 36 | Oráculos HTTP/DOM/foco e proteção de screenshots do harness real de login. settings.py é importado antes de main por publication_http e rejeita -O/PYTHONOPTIMIZE; os asserts não implementam autorização do produto. O teste test_optimized_harness_cannot_drop_assertions_and_report_success comprova a rejeição de otimização. |
| `application/integration/tests/e2e/frontend_browser.py` | B404 | 12 | 1 | Import usado somente nas duas chamadas Docker revisadas abaixo; o harness usa serviços locais dedicados, não executa programa recebido por HTTP. |
| `application/integration/tests/e2e/frontend_browser.py` | B603 | 33, 231 | 2 | Docker exec com argv separado, comandos literais createdb/dropdb, container e usuário da configuração local confiável e nome de banco gerado por UUID em publication_http. Sem shell=True; dados da sessão/browser não selecionam programa ou argumentos. |
| `application/integration/tests/e2e/frontend_browser.py` | B607 | 33, 231 | 2 | docker é selecionado pelo PATH confiável do operador/runner. Esta revisão exige instalação e PATH locais confiáveis; não cobre execução em PATH controlado por terceiros. Argumentos são separados e banco UUID dedicado. |
| `scripts/tests/test_automation_config.py` | B104 | 425 | 1 | 0.0.0.0 é somente dado do mock docker inspect em test_wrong_postgres_binding_is_rejected_before_start. prepare.command é mockado e inspect_postgres deve lançar Unavailable; nenhum socket público é aberto. |
| `scripts/tests/test_automation_config.py` | B404 | 6 | 1 | Import de subprocess para testar o guard de otimização do harness; a única chamada atual está revisada em B603. |
| `scripts/tests/test_automation_config.py` | B603 | 31 | 1 | sys.executable executa -O e import literal de settings em cwd do checkout para exigir rejeição de asserts desabilitados; sem shell, sem serviço/rede e timeout de 10 segundos. |
| `scripts/tests/test_native.py` | B404 | 10 | 1 | Import para testes locais dos gates/supervisor; as seis chamadas atuais estão revisadas por B603 e a seleção de Git por B607. |
| `scripts/tests/test_native.py` | B603 | 314, 383, 721, 888, 926, 976 | 6 | Chamadas do teste usam /bin/bash com scanner-gate versionado e executor fixture, Git com argumentos literais em raiz temporária, ou Bash -c com harness literal. Raízes/plans são parâmetros posicionais, sem interpolação de entrada externa no programa; os harnesses exercitam redação, cancelamento e inventário grande. |
| `scripts/tests/test_native.py` | B607 | 383 | 1 | git vem do PATH confiável do ambiente de teste, com -C e raiz temporária; operações init/config/add/commit e mensagem são definidas no teste. PATH controlado por atacante não está coberto pela revisão. |
| `scripts/tests/test_shell.py` | B105 | 480 | 1 | Valor literal de redação usado como campo Secret da fixture do parser Gitleaks para conferir consistência relatório/exit. Não é uma credencial nem é utilizado para autenticação. |
| `scripts/tests/test_shell.py` | B108 | 34 | 1 | Namespace /tmp/ec-automation-UID-HASH é derivado da raiz criada por TemporaryDirectory do próprio teste e coincide com o contrato do runner. Os testes exercitam diretório privado/lock; tearDown remove apenas o namespace dessa fixture. Não é nome de arquivo aberto para armazenar segredo. |
| `scripts/tests/test_shell.py` | B110 | 443 | 1 | ProcessLookupError na limpeza significa que o grupo do processo fixture já encerrou; somente esse erro é aceito. Outros erros propagam e communicate(timeout=5) permanece obrigatório após a tentativa de término. |
| `scripts/tests/test_shell.py` | B404 | 11 | 1 | Import das rotinas shell/start_tasks e teste de caminho rejeitado; chamadas concretas revisadas em B603/B607. |
| `scripts/tests/test_shell.py` | B603 | 42, 82, 376 | 3 | Bash recebe script versionado ou harness literal; caminhos e valores da fixture seguem em argv/posicionais. start_tasks usa plan temporário gerado pelo teste; o teste de inside exige rejeição de ../outside. Nenhum input remoto vira código shell. |
| `scripts/tests/test_shell.py` | B607 | 42, 82, 376 | 3 | bash é dependência local resolvida pelo PATH confiável do operador/runner. Scripts versionados e programas literais; esta revisão não aceita PATH sob controle de atacante. |
| `sdd/tools/validate.py` | B110 | 197 | 1 | ValueError ao coletar bindings redundantes não aprova path inválido: audit chama trace_errors antes de input_errors e trace_errors valida os mesmos campos implementation/oracle com local_file, registrando o erro. Outros tipos de exceção não são capturados aqui. |
| `tools/governance/tests/test_governance.py` | B404 | 341, 361, 417 | 3 | Imports locais dos três testes de lock e morte/interrupção do instalador; chamadas concretas revisadas por B603. |
| `tools/governance/tests/test_governance.py` | B603 | 349, 376, 431 | 3 | sys.executable com programa -c literal e caminhos em argv separado exercita lock e mortes controladas do instalador sobre a raiz temporária criada em setUp. Código versionado, sem entrada HTTP e sem programa externo interpolado; não é instalação no checkout real. |
| `tools/governance/tests/test_sdd_catalog.py` | B404 | 5 | 1 | Import usado somente pelo helper cli que executa o validador SDD versionado, revisado em B603. |
| `tools/governance/tests/test_sdd_catalog.py` | B603 | 41 | 1 | sys.executable e caminho fixo CODE/sdd/tools/validate.py com --root apontando fixture temporária; args são flags literais dos casos de teste. Sem shell, rede ou serviços; timeout de 10 segundos. Não permite consumidor remoto fornecer programa. |
| `tools/quality/automation_config.py` | B112 | 715 | 1 | Somente FileNotFoundError em raiz opcional do inventário é ignorado. PermissionError e demais erros propagam; links e arquivos em lugar de diretório são rejeitados pelo fluxo. Raiz obrigatória da aplicação é validada pelos consumidores, não aprovada por este continue. |
| `tools/quality/automation_config.py` | B404 | 16 | 1 | Import usado na descoberta local Rustup; chamada específica revisada em B603. |
| `tools/quality/automation_config.py` | B603 | 586 | 1 | Executável rustup resolvido por shutil.which no PATH selecionado pelo operador; argv literal show active-toolchain, ambiente limitado e RUSTUP_AUTO_INSTALL=0. Timeout do catálogo e falha preservada como recurso indisponível pelos consumidores; não executa comando recebido pelo produto. |

## Contraprovas e limites

- Asserts: `application/integration/tests/settings.py:6` recusa Python otimizado,
  importado por `publication_http.py:18` antes do harness; o caso negativo está
  em `scripts/tests/test_automation_config.py:30`. Remover esse guard ou permitir
  execução que o contorne invalidaria a justificativa, inclusive a proteção antes
  de screenshots; a triagem não torna assert adequado à autorização de produção.
- Subprocess: separar argv não protege contra substituição do binário/PATH ou
  programa shell interpolado. Foram examinados os programas literais e origem dos
  argumentos de cada linha da tabela. A revisão exige checkout, configuração,
  Python e PATH locais confiáveis; não estende essa confiança a workloads remotos.
- B104: o teste deve rejeitar o bind público antes de iniciar serviço. B105 não
  representa segredo; introduzir credencial real requer resposta distinta. B108
  não autoriza arquivos temporários públicos nem limpeza de trabalho alheio.
- B110/B112: exceções mais amplas ou remoção do diagnóstico alternativo seriam
  defeitos; `trace_errors` registra os caminhos inválidos antes de `input_errors`,
  e a coleta de inventário propaga erros de acesso diferentes de ausência.
- Nenhuma nova supressão nativa, `nosec`, exclusão de testes ou regra foi criada.
  A lista explícita da tabela foi confrontada por tuplas fonte/regra/linha com
  todas as 74 pendências, sem deduzir aceitação apenas de sua severidade LOW/MEDIUM.

## Ajuste Rust

`service/http.rs` importa `std::io::Read` somente com `test-failpoints`, como seu
uso em stdin já fazia. O import `Arc` permanece incondicional. Edition 2024,
workspace e feature foram conferidos nos manifests; toolchain `stable` existente
preservado. Não há mudança de runtime, API, dependências ou MSRV declarado.

## Verificação

Executado `python3 -m unittest discover -s scripts/tests -p 'test_security_evidence.py'`:
8 testes passaram, exit 0. Saída real em
`.local/logs/2026-11-15/log-remediation-security-evidence.log`, com modo 0600.
Inclui mutações de hash, linha, regra, nova ocorrência e motivo vazio, preservando
a rejeição de revisões inválidas. O runner e o gate ficam a cargo do coordenador;
este resultado focado não constitui aprovação atual do gate.
