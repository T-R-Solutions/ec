---
skill: autonomous-verification-engineering
skill_path: .codex/skills/autonomous-verification-engineering/SKILL.md
bridge_version: 1
---

# Ponte SDD: autonomous-verification-engineering

Método e escopo: leia a entrada indicada por `skill_path`.
Aplicação aos artefatos: [contrato comum das pontes](README.md).

## Contribuição específica

| Artefato | Contribuição |
| --- | --- |
| Spec | Every verdict is bound to an explicit criterion, method and input snapshot. |
| Plan / ADR | Mapear os estados do motor ao contrato de evidência do consumidor, sem vocabulário concorrente. |
| Tasks | Create fixtures with false tests, failures, skips and post-validation changes. |
| Evidence / convergence | gate model, auditable states, requirement-evidence-snapshot bindings, implementation/oracle separation, replay procedures |

Questão auxiliar, se permanecer em aberto: `software-testing` — Does the chosen oracle actually fail when a representative defect is introduced?
