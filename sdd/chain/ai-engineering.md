---
skill: ai-engineering
skill_path: .codex/skills/ai-engineering/SKILL.md
bridge_version: 1
---

# Ponte SDD: ai-engineering

Método e escopo: leia a entrada indicada por `skill_path`.
Aplicação aos artefatos: [contrato comum das pontes](README.md).

## Contribuição específica

| Artefato | Contribuição |
| --- | --- |
| Spec | Identify the task, supported population, observable completion, unacceptable errors, no-answer behavior and relevant quality/cost/latency constraints. Each criterion needs a concrete scenario and an oracle; do not invent thresholds. |
| Plan / ADR | Identify the application baseline, model/configuration, corpus/index, tools and evaluator versions. Map the diagnosed failure to intervention alternatives and trade-offs; link requirement, decision and risk IDs. |
| Tasks | Name changed surfaces, authorized fixtures/data, actual tools, dependencies and completion checks. Separate deterministic integration checks from actual model trials and component results from final effects. |
| Evidence / convergence | Return criterion IDs, baseline/candidate results on comparable cases, observed variability/cost, denied effects, failures and remaining gaps. Identify model/prompt/data/tool/evaluator versions needed to interpret the evidence. |
