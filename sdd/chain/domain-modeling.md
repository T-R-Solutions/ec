---
skill: domain-modeling
skill_path: .codex/skills/domain-modeling/SKILL.md
bridge_version: 1
---

# Ponte SDD: domain-modeling

Método e escopo: leia a entrada indicada por `skill_path`.
Aplicação aos artefatos: [contrato comum das pontes](README.md).

## Contribuição específica

| Artefato | Contribuição |
| --- | --- |
| Spec | Establish business vocabulary, invariants and allowed/rejected state transitions. |
| Plan / ADR | Resolve aggregate/context boundaries, transaction ownership and consistency trade-offs. |
| Tasks | Implement domain rules and verify transitions with representative domain examples. |

Questão auxiliar, se permanecer em aberto: `distributed-systems` — Can the invariant survive a delayed or duplicated cross-context event?
