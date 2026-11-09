# Ferramentas nativas e toolbox — 2026-11-06

Refatoração autorizada da automação Ec, com software-delivery primária e
application-security auxiliar; requisitos AC-07/08 em scripts-verification.
[Configuração e fontes oficiais consultadas](../../development/verification-tools.md).
Nenhuma skill foi alterada nesta tarefa; mudanças anteriores no checkout foram preservadas.

## Alterações observadas

- Plugins Cargo globais no PATH independentemente do cache de dependências Ec;
  versão/hash do plugin chamado registrados. RustSec preparado por operação explícita.
- Políticas deny/audit explícitas, sem ignores de advisories; fontes/licenças
  controladas e base RustSec com até sete dias. OSV exige registro de até 24h.
- `toolbox.json` e lock real de imagens; snapshots privados sem caches/histórico/
  credenciais pessoais; network none, UID do operador, readonly e capabilities
  removidas. O launcher toolbox e a imagem são registrados; outputs brutos preservados.
- Ruff e ShellCheck obrigatórios em scripts, audit/deny/machete no perfil Rust.
  Ausências bloqueiam; relatórios inválidos e exits inconsistentes falham.
- Gitleaks: teste real distingue hash de recurso de chave sintética no mesmo arquivo;
  exceção exige caminho exato do perfil e formato de linha arquivo → SHA256.
  Demais regras default continuam habilitadas, inline suppression é rejeitada e
  nenhum ignore file é copiado. A exceção resolveu 28 falsos positivos observados.
- Trivy configura vulnerabilidades, segredos e misconfigurações; parser soma os três
  tipos, sem tratar ausência de CVE como ausência dos demais achados. Execução real
  continua bloqueada pelo ambiente, não certificada por configuração.
- Playwright/Chromium autocontido: API simulada, rejeição de sessão, storage vazio,
  identidade exibida como texto e revogação local. Dois testes reais passaram,
  sem skip/flaky/retry. Não substitui RBAC e jornadas reais do Firefox.
- Preparo de imagens/bases compartilha o lock da verificação; nenhuma instalação,
  pull, update de base ou resolução de registry ocorre implicitamente no scan.

## Ambiente compartilhado

O preparo revelou falhas reais do toolbox descrito em `.local/usage.md`:

1. Base sem digest: `lock-base` resolveu Debian em
   `sha256:a72b4433de1cf7d6bae0c4cee74d0547f58601a643b3375f070c51600d63b6b2`.
2. Gitleaks não compilou com base atual e snapshot 20251015 (perl/perl-base
   incompatíveis). Snapshot compartilhado foi alinhado a 20261106 e a imagem
   preparada com sucesso. Imagens Ruff/Playwright anteriores foram preservadas por
   seus digests/receitas; não foram renomeadas nem declaradas atualizadas.
3. SELinux impediu leitura dos fontes pelo container. Correção opt-in
   `TOOLBOX_SELINUX_LABEL=private` usa volumes `ro,Z` e `rw,Z`, valida caminhos,
   mantém readonly/cap-drop/no-new-privileges e não desabilita SELinux. Ec usa apenas
   snapshots descartáveis; 18 testes da plataforma passaram no cwd correto.
4. ShellCheck 0.10.0: release oficial não possui digest nem arquivo de checksums
   reconhecido pelo preparador. Preparo foi recusado, sem fabricar SHA256 ou
   desabilitar verificação de integridade.

[Patch do toolbox compartilhado](shared-toolbox.patch) registra configuração,
runtime, teste e referências alterados com autorização de acesso ao diretório
externo. Originais estão em `.local/build/toolbox-selinux/` e
`.local/build/toolbox-platform-before.json`. A primeira tentativa de testes fora do
cwd do toolbox falhou por import; a execução no cwd correto passou 18 testes.
Nenhum adapter de ferramenta bloqueada foi criado ou declarado validado.

## Verificação e registros

- `python3 -m unittest discover -s scripts/tests -v`: 41 testes passaram, incluindo
  relatório/exit, ausência de adapter/imagem, drift da configuração, inline
  suppression, skips/flaky/zero testes, timeout/cancelamento e diagnósticos.
- `python3 -m unittest discover -s tools/governance/tests -v`: 34 testes passaram.
  A inclusão inicial do guia em config/quality expôs uma dependência documental não
  copiada nas projeções de auditoria. O guia canônico foi colocado em docs/development,
  preservando o contrato do instalador e das fixtures existentes.
- `bash scripts/check.sh`: sintaxe aprovada. ShellCheck tem tarefa própria obrigatória.
- `toolbox validate --config toolbox.json`: configuração válida.
- `python3 tools/governance/validate.py --require-installed`: PASS, 67 skills/596 arquivos.
- `python3 tools/governance/install_skills.py --check`: ALREADY_INSTALLED, 67.
- Runner intermediário `run-Q4MQEQAL`: RustSec passou nos dois workspaces,
  build/Chromium passaram; Ruff 102 e Gitleaks 28 correspondências de hashes.
- Runner completo `run-8wB3pqVz`: fmt/Clippy/testes/builds Rust e frontend passaram;
  teste de regra e scan Gitleaks passaram; Chromium passou. PostgreSQL estava parado
  e bloqueou os harnesses reais. Uma falha na projeção documental foi corrigida;
  sua reexecução está registrada acima, sem reclassificar o run anterior.
- Runner completo `run-erlhfC5q`: três integrações passaram, além dos checks de produto disponíveis. Firefox concluiu os oito grupos de cenários, mas falhou na limpeza do perfil. O contêiner dedicado existente
  ec-publication-test foi iniciado após confirmar bind 127.0.0.1:55439, sem recriar
  volume, remover dados ou alterar sua imagem. A reexecução após correção está registrada abaixo.

Os runs antigos permanecem privados em `.local/automation/runs/`. O primeiro run
`run-fArkBcBG` detectou corretamente alteração do lock durante o preparo e não foi
promovido a sucesso. O preparo agora usa o mesmo lock do runner.

## Limitações e aceite

O Ruff reporta 102 pontos de revisão em utilitários/harnesses Python, principalmente
subprocessos e SQL dinâmico de teste; não há ignore amplo ou baseline que os aceite.
Estes achados exigem triagem contextual, não são 102 vulnerabilidades confirmadas.
Não se alteraram os harnesses de domínio para esconder os apontamentos.

Não localizados no ambiente: cargo-machete/cargo-deny/cargo-geiger/cargo-llvm-cov.
Não houve reinstalação global por inferência. Os dois primeiros bloqueiam o perfil
padrão Rust. Opengrep/OSV nativos revisados não estão disponíveis, e as versões do
toolbox não possuem adapter: SAST, testes de regras Opengrep e dependências OSV
continuam UNVERIFIED. ShellCheck também permanece bloqueado pelo preparo acima.
Trivy/CodeQL opcionais e cobertura/performance frontend não foram executados;
existência de configuração não comprova funcionamento de um motor ausente.

Os checks disponíveis não aprovam o perfil completo, AC-04 de scripts-verification
nem os critérios de produto que já estavam UNVERIFIED. Não há certificação enterprise,
aprovação legal de licenças, scan de vulnerabilidades das próprias imagens, sandbox
para código hostil ou certificação WCAG. O container PostgreSQL usado é o fixture
histórico já configurado, não uma escolha de implantação de produção.

[Observações SDD anteriores preservadas](previous-evidence.zip): as novas evidências
registram novos métodos/resultados e inputs; os hashes históricos não foram renovados.

## Resultado final observado

- [Runner completo](results-full.json), `run-erlhfC5q`: testes de governança 34/34,
  scripts 41/41, fmt/Clippy/testes/audit/build de ambos os workspaces, frontend
  typecheck/test/build, teste de regras/scan Gitleaks, três integrações HTTP e
  Chromium passaram. Ruff: 102 pontos de revisão. Oito tarefas bloqueadas
  (ShellCheck, machete/deny nos dois workspaces, teste/SAST Opengrep e OSV conforme
  tabela do resultado); consultar o JSON para a contagem exata, sem agrupar
  bloqueios distintos como uma análise aprovada. SDD estava stale antes da nova
  coleta e Firefox encontrou o defeito de cleanup descrito a seguir.
- [Reexecução E2E](results-e2e.json), `run-zQDvZcYo`: Firefox passou inclusive a
  limpeza, com os mesmos oito grupos funcionais, Chromium 2/2 sem skip/flaky,
  além dos checks Rust/frontend disponíveis. Defeito corrigido: o harness aguardava
  `processes[-1]`, que após as execuções apontava para o Agent, e removia o perfil
  antes do encerramento garantido do navegador. Agora guarda o handle Firefox,
  usa sessão própria e ExitStack para finalizar processos antes da remoção,
  tanto no sucesso quanto na falha. Não foi usada omissão de erros de cleanup.
- O Trivy opcional teve o YAML reconciliado com as chaves oficiais 0.61.1
  (`scan.offline/scanners`, `db.java-skip-update`, `rego.skip-check-update`,
  `vulnerability.ignore-unfixed`). Updates de checks/VEX estão desativados,
  ignore file explícito vazio e supressões reportadas são rejeitadas pelo parser.
  Testes shell passaram 41/41 após essa correção; motor real continua ausente.
- Não foram relaxados SDD ou critérios de produto para obter sucesso agregado.
  As observações ativas serão vinculadas a este relatório e aos inputs atuais;
  o gate de aceite continuará recusando os ACs UNVERIFIED.

O PostgreSQL dedicado existente foi deixado ativo em loopback para as verificações
subsequentes; bancos UUID e processos dos harnesses foram removidos por seus ciclos
de cleanup. Não foi alterada a imagem ou apagado o volume do contêiner.

## Encerramento da coleta

[Última verificação de documentação/scripts/segurança](results-final.json),
`run-tvrlWJgN`, executada após a reobservação: auditores, integridade das skills,
SDD comum, 34 testes de governança, sintaxe, 41 testes shell, teste real de regra e
scan Gitleaks passaram. Nenhum input mudou durante a execução. Exit agregado 3:
ShellCheck e Opengrep/OSV bloqueados; Ruff manteve os 102 pontos de revisão.
A prioridade 3 sobre findings 1 segue o contrato do runner; não apaga os achados.

`python3 sdd/tools/validate.py --root . --require-fresh` passou com as novas
observações. `--require-approved --require-fresh` continua retornando 1 por
AC-01/AC-07 da primeira jornada e AC-04 de scripts, todos UNVERIFIED. Os estados
aprovados de governança e dos contratos exercitados foram reobservados; não houve
aprovação de nenhum critério que já estava pendente.
