---
skill: plugin-protocol-engineering
skill_path: .codex/skills/plugin-protocol-engineering/SKILL.md
bridge_version: 1
---

# Ponte SDD: plugin-protocol-engineering

Método e escopo: leia a entrada indicada por `skill_path`.
Aplicação aos artefatos: [contrato comum das pontes](README.md).

## Contribuição específica

| Artefato | Contribuição |
| --- | --- |
| Spec | Negotiate version and capabilities without performing operations that are not yet authorized. |
| Plan / ADR | External process + versioned contract versus a dynamic library loaded in the host; decide by failure model and ABI. |
| Tasks | Implement a test harness for contracts, failures, limits and compatibility. |
| Evidence / convergence | message contract, host states, permission model, resource limits, compatibility matrix, failure and migration tests |

Questão auxiliar, se permanecer em aberto: `application-security` — Does the chosen isolation actually prevent unauthorized effects from a compromised plugin?
