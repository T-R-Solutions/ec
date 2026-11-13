---
skill: rust
skill_path: .codex/skills/rust/SKILL.md
bridge_version: 1
---

# Ponte SDD: rust

Método e escopo: leia a entrada indicada por `skill_path`.
Aplicação aos artefatos: [contrato comum das pontes](README.md).

## Contribuição específica

| Artefato | Contribuição |
| --- | --- |
| Spec | Establish observable safety and error invariants at Rust boundaries. |
| Plan / ADR | Resolve edition/MSRV, ownership, unsafe/FFI justification and synchronization alternatives. |
| Tasks | Implement the changed rust surface and run supported compiler/tests or concurrency checks. |

Questão auxiliar, se permanecer em aberto: `software-testing` — Which controlled schedule can falsify the publication invariant?
