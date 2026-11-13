---
skill: agent-runtime-engineering
skill_path: .codex/skills/agent-runtime-engineering/SKILL.md
bridge_version: 1
---

# Ponte SDD: agent-runtime-engineering

Método e escopo: leia a entrada indicada por `skill_path`.
Aplicação aos artefatos: [contrato comum das pontes](README.md).

## Contribuição específica

| Artefato | Contribuição |
| --- | --- |
| Spec | Sessions and tasks have legal states, stable identifiers and rejected transitions. |
| Plan / ADR | Contrato do supervisor CI/CD; não importar ACP por analogia. |
| Tasks | Criar processos controlados e verificar integração real do supervisor quando disponível. |
| Evidence / convergence | state machine, testable adapters, ordered events, process controls, cancellation handling, policy integration |

Questão auxiliar, se permanecer em aberto: `reliability-engineering` — How can orphaned processes, duplicate effects and event loss be bounded during failures?
