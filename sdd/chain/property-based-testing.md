---
skill: property-based-testing
skill_path: .codex/skills/property-based-testing/SKILL.md
bridge_version: 1
---

# SDD bridge: property-based-testing

Copy only this selected bridge to the consumer's sdd/chain/ and set skill_path to
the project-relative path of its complete skill folder's SKILL.md. Read the actual
entry and check its name. Presence in this catalog does not activate or install it.

## Selection

Design or repair generated-input, metamorphic and state-machine tests with meaningful oracles,
shrinking and replay. Skip generic suite strategy and claims of correctness from random sampling.

Record the bridge/version, configured entry and selection reason in the existing
feature plan. A bounded local task may use the skill alone without a bridge or
new feature. Existing acceptance gates still apply in either route.

## Inputs and contributions

Supply the current requirement/acceptance, decision/risk and task IDs, authorized
scope, actual implementation and the following task-specific context:
Identify the invariant, actual implementation, input domain, framework and reproducibility
requirements. A property must come from an independent contract, not the implementation output.

| Artifact | Contribution |
| --- | --- |
| Constitution | Reconcile existing principles and permissions; introduce no new product policy. |
| Spec | Identify the required behavior, rejected outcomes and observable acceptance for property-based-testing. Examples in the skill are not requirements. |
| Plan / ADR | Compare mechanisms against those criteria, recording decision/risk IDs, assumptions, compatibility and recovery costs where relevant. |
| Tasks | Name bounded changes, real files/tools, dependencies and completion checks. Apply the skill's method without copying it here. |
| Evidence / convergence | Return criterion/task IDs, observed inputs and versions, commands, results and limitations. Separate review, simulation, integration and production observations. |

## Conditional handoff

Use another available skill only for a concrete question outside this entry's
responsibility that remains unresolved. Pass the question, IDs, facts, constraints,
required evidence and existing permission scope. Do not recursively activate a
catalog or claim that an absent specialist performed work; continue independent work.

## Completion and authority

The destination SDD defines its outcome vocabulary and evidence contract. For this
model, preserve APPROVED, REJECTED, UNVERIFIED, INCONCLUSIVE and NOT APPLICABLE
with an explicit justification for inapplicability. Missing tools or actual integration
leave the relevant criterion unverified; a structural PASS does not approve behavior.

Spec owns requirements, plan/ADRs decisions, tasks executable work and evidence the
observed result. Preserve requirement → decision/risk → task → change → evidence
traceability and recheck observations whose inputs changed. This bridge grants no
additional authorization and requires no modification to the portable SDD core.

## Integração Ec

Selecionar pelo problema concreto descrito acima, respeitando as normas de produto do Ec.

Pacote instalado em `.codex/skills/` com integridade conferida. Descoberta pela interface exige observação própria.
