---
skill: workspace-integrity-engineering
skill_path: .codex/skills/workspace-integrity-engineering/SKILL.md
bridge_version: 1
---

# Ponte SDD: workspace-integrity-engineering

Método e escopo: leia a entrada indicada por `skill_path`.
Aplicação aos artefatos: [contrato comum das pontes](README.md).

## Contribuição específica

| Artefato | Contribuição |
| --- | --- |
| Spec | All file changes respect the authorized root and observed version. |
| Plan / ADR | Write through staging+rename when platform semantics allow; document fallback. |
| Tasks | Implement tests in temporary directories and with concurrency. |
| Evidence / convergence | validated file transactions, loss prevention, change trails, snapshot and recovery strategy |

Questão auxiliar, se permanecer em aberto: `application-security` — Are there alternative paths/symlinks that escape the root granted to the agent?
