from pathlib import Path
p=Path('docs/development/verification-tools.md');s=p.read_text().replace('bash scripts/prepare.sh tools ruff gitleaks playwright','bash scripts/prepare.sh tools ruff shellcheck gitleaks playwright opengrep osv-scanner\nbash scripts/prepare.sh osv-db\nbash scripts/prepare.sh cargo-tools\nbash scripts/prepare.sh deny-db').replace('ShellCheck 0.10.0','ShellCheck 0.11.0').replace('/v0.10.0/shellcheck.1.md','/v0.11.0/shellcheck.1.md').replace('Motor nativo revisado 1.33.1 mantém testes de regras e inventário estrito; versão 1.31.0 do toolbox não tem adapter','Adapter 1.33.1 no toolbox: testes de regras e inventário estrito; versão antiga 1.31.0 preservada').replace('Motor nativo revisado 2.7.0 mantém três locks e bases crates.io/npm offline; versão 2.0.2 do toolbox não tem adapter; registro local expira em 24h','Adapter 2.7.0 no toolbox: três locks e bases crates.io/npm offline; snapshot imutável expira em 24h').replace('Nenhum ignore de dependências/lints foi adicionado.','Ruff tem exceções S603/S607 por ocorrência, com justificativa e triagem dos 102 alertas em `docs/validation/tooling-remediation-20261106/ruff-triage.json`. SQL usa variáveis psql quoted; HTTP do harness tem destino fixo e não segue redirects.').replace('Opengrep/OSV/Trivy sem adapter permanecem habilitados e bloqueados; não são omissões\nsilenciosas. Mesmo que um adapter futuro apareça, o contrato Ec precisa ser validado\nantes de aprovar sua cobertura.','Opengrep/OSV têm adapters versionados, relatórios estritos e execução sem rede.\nTrivy opcional continua sem adapter.').replace('plugins globais no PATH','plugins globais no PATH e fallback local em `.local/tools/bin`')
s+='''

## Preparo local dos plugins e integridade

`cargo-tools` baixa somente os artefatos Linux x86_64 fixados em
`config/quality/cargo-tools.json`, confere SHA-256 publicado na release oficial e
instala em `.local/tools/bin`. Não altera a instalação global. O PATH do operador
continua prioritário; versão e hash efetivos são registrados. O preparo não é
chamado pelo runner. Cargo Deny 0.20.2 usa `--config` antes de `check`; `--offline`
impede fetch de bases. `deny-db` prepara o layout RustSec específico do plugin.
Para completar caches de todas as plataformas, execute `cargo fetch --locked`
explicitamente em cada workspace com o CARGO_HOME do Ec antes do scan offline.

ShellCheck 0.10.0 continua preservado no catálogo compartilhado. Ec seleciona
0.11.0, cujo asset oficial possui digest verificável. Opengrep é extraído no build,
sem precisar executar arquivos no tmpfs do container. Configuração e métricas
ficam em diretórios temporários; fontes são readonly, rede none e UID do operador.
''';p.write_text(s)
p=Path('scripts/security/README.md');s=p.read_text().replace('pelo toolbox compartilhado, Opengrep nativo','pelo toolbox compartilhado, Opengrep').replace('e OSV-Scanner nativo','e OSV-Scanner').replace('`.local/usage.md` ainda não oferece adapters Opengrep/OSV/Trivy: essa ausência bloqueia\nos respectivos checks, sem instalação nativa ou fallback online implícitos.','`.local/usage.md` preserva o catálogo anterior. Os adapters versionados Opengrep\n1.33.1 e OSV 2.7.0 foram adicionados ao toolbox; Trivy continua pendente.').replace('bash scripts/prepare.sh tools gitleaks','bash scripts/prepare.sh tools gitleaks opengrep osv-scanner\nbash scripts/prepare.sh osv-db')
a=s.index('Preparar crates.io');b=s.index('## Evidências',a);s=s[:a]+'''`bash scripts/prepare.sh osv-db` baixa os ZIPs oficiais crates.io/npm por HTTPS,
valida o arquivo, grava um snapshot imutável no volume `tools/osv-scanner/volume`
do toolbox e vincula seu hash em `toolbox.json`. Usa o lock do runner. O scan exige
hash íntegro, data não futura e idade de até 24h; copia a base para a saída privada
e confere integridade depois. Rede permanece desativada no container.
Não renovar só o timestamp para aparentar download novo. Hash local não é assinatura
nem prova de frescor upstream. `record-osv-db.sh` permanece para snapshots nativos
históricos e seus testes, sem ser usado pelo adapter atual.
RustSec usa preparos separados: `prepare.sh rustsec` para Audit e `prepare.sh deny-db`
para Deny. Ambos scans são offline.

'''+s[b:];s=s.replace('relatórios de Ruff/Gitleaks/ShellCheck/Playwright','relatórios de Ruff/Gitleaks/ShellCheck/Playwright/Opengrep/OSV');p.write_text(s)
p=Path('docs/development/task-verification.md');s=p.read_text().replace('Opengrep 1.33.1 e OSV-Scanner 2.7.0 nativos no PATH;\nOSV exige bases locais crates.io/npm registradas.','Opengrep 1.33.1 e OSV-Scanner 2.7.0 em imagens do toolbox;\nOSV exige snapshot crates.io/npm vinculado por hash em toolbox.json.').replace('Ruff/ShellCheck/Gitleaks/Playwright usam snapshots','Ruff/ShellCheck/Gitleaks/Playwright/Opengrep/OSV usam snapshots');p.write_text(s)
p=Path('.local/usage.md');s=p.read_text();s='''> Atualização Ec de 2026-11-06: receitas separadas ShellCheck 0.11.0, Opengrep 1.33.1
> e OSV-Scanner 2.7.0 foram adicionadas ao toolbox compartilhado com adapters e
> preparo de base OSV. A tabela abaixo preserva o catálogo anterior. Consulte
> `docs/development/verification-tools.md` para comandos e o relatório
> `docs/validation/tooling-remediation-20261106/report.md` para execução observada.

'''+s;p.write_text(s)
