---
skill: distributed-systems
skill_path: .codex/skills/distributed-systems/SKILL.md
bridge_version: 1
---

# Ponte SDD: distributed-systems

Método e escopo: leia a entrada indicada por `skill_path`.
Aplicação aos artefatos: [contrato comum das pontes](README.md).

## Contribuição específica

| Artefato | Contribuição |
| --- | --- |
| Spec | Establish consistency, ordering, duplication and partial-failure guarantees. |
| Plan / ADR | Resolve failure domains, ownership, coordination and delivery alternatives. |
| Tasks | Exercise lost/delayed/duplicated messages and recovery against explicit invariants. |

Questão auxiliar, se permanecer em aberto: `domain-modeling` — Which intermediate state is forbidden during cross-node recovery?
