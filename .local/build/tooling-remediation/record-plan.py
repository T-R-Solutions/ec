from pathlib import Path
p=Path('sdd/features/scripts-verification/plan.md')
with p.open('a') as f:f.write('''

## Correção das pendências — 2026-11-06

D-09 / R-09 / REQ-04/05/07/08: triagem individual dos 102 achados Ruff,
valores SQL como variáveis psql quoted, HTTP fixo em loopback sem redirects,
sentinela aleatória e exceções de subprocessos por ocorrência justificada.
PATH é fronteira de confiança do operador; nenhum input remoto seleciona executáveis.
ShellCheck 0.11.0 em receita separada tem digest oficial, preservando a 0.10.0.
Plugins Cargo ausentes na busca ampliada são preparados localmente com hashes
de release verificados, sem instalação global. Adapters compartilhados Opengrep
1.33.1 e OSV 2.7.0 executam em imagens readonly/network none, com preparo explícito,
bases por hash e prazo, inventário completo e rejeição de falhas/skips/supressões.
O pedido atual autoriza corrigir a integração compartilhada e substitui a restrição
anterior D-07 de não modificar adapters. Escrita externa exige permissão do sandbox.

Skills consultadas novamente, pontes versão 1, sem delegação:
- application-security primária, `.codex/skills/application-security/SKILL.md`,
  SHA-256 `0d992c0538df30bd5e0c6be689990184ec51671225809eac0f01759cc08257c4`:
  triagem contextual, redução de interpretadores e verificações negativas.
- software-delivery auxiliar, `.codex/skills/software-delivery/SKILL.md`,
  SHA-256 `86383b8494cab58e79cf2c31e2f0375a4851d6c60b9de23c89b8b066d3787e1f`:
  artefatos oficiais, preparo separado e motores reais.

Evidências: `docs/validation/tooling-remediation-20261106/`; registros anteriores
permanecem preservados. Testes de contrato não substituem execução dos scanners.
''')
p=Path('sdd/features/scripts-verification/tasks.md')
with p.open('a') as f:f.write('''
- [ ] T010 [AC-04/05/07/08, D-09/R-09]: corrigir/justificar cada alerta Ruff,
  preparar ShellCheck íntegro, disponibilizar Cargo Machete/Deny, integrar e executar
  Opengrep/OSV, rodar regressões e reobservar evidências afetadas.
''')
