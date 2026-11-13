---
skill: prompt
skill_path: .codex/skills/prompt/SKILL.md
bridge_version: 1
---

# Ponte SDD: prompt

Método e escopo: leia a entrada indicada por `skill_path`.
Aplicação aos artefatos: [contrato comum das pontes](README.md).

## Contribuição específica

| Artefato | Contribuição |
| --- | --- |
| Spec | Establish intended prompt behavior, accepted outputs and prohibited actions. |
| Plan / ADR | Resolve instruction/data boundaries, evaluation cases and model/tool assumptions. |
| Tasks | Author or revise the prompt and evaluate representative cases; do not execute its described task without authorization. |

Questão auxiliar, se permanecer em aberto: `application-security` — Can retrieved content cause a tool action outside the caller authority?
