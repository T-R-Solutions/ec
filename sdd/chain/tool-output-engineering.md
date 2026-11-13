---
skill: tool-output-engineering
skill_path: .codex/skills/tool-output-engineering/SKILL.md
bridge_version: 1
---

# Ponte SDD: tool-output-engineering

Método e escopo: leia a entrada indicada por `skill_path`.
Aplicação aos artefatos: [contrato comum das pontes](README.md).

## Contribuição específica

| Artefato | Contribuição |
| --- | --- |
| Spec | Each transformed output preserves exit code, error signals and a reference to raw output. |
| Plan / ADR | Three forms: append-only raw output, normalized structure and compact view. |
| Tasks | Build an adversarial corpus and differential tests with authorized real output. |
| Evidence / convergence | preserved raw output, structured events, summaries flagging loss/truncation, comparable measurements |

Questão auxiliar, se permanecer em aberto: `application-security` — Can stdout text be mistaken for an instruction or authorized tool input?
