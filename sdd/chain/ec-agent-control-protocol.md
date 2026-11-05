---
skill: ec-agent-control-protocol
skill_path: .codex/skills/ec-agent-control-protocol/SKILL.md
bridge_version: 1
---

# SDD bridge: ec-agent-control-protocol

Copy only this selected bridge to the consumer's sdd/chain/ and set skill_path to
the project-relative path of its complete skill folder's SKILL.md. Read the actual
entry and check its name. Presence in this catalog does not activate or install it.

## Selection

Implement or review Ec agent/control-plane authentication, version negotiation, heartbeats, leases
and result receipts. Skip scheduling policy and local process execution.

Record the bridge/version, configured entry and selection reason in the existing
feature plan. A bounded local task may use the skill alone without a bridge or
new feature. Existing acceptance gates still apply in either route.

## Inputs and contributions

Supply the current requirement/acceptance, decision/risk and task IDs, authorized
scope, actual implementation and the following task-specific context:
Locate actual agent/server contracts, enrollment authority, session epochs, attempt identities,
persistence and protocol versions. Discover the implementation language instead of assuming a Rust
service exists.

| Artifact | Contribution |
| --- | --- |
| Constitution | Reconcile existing principles and permissions; introduce no new product policy. |
| Spec | Identify the required behavior, rejected outcomes and observable acceptance for ec-agent-control-protocol. Examples in the skill are not requirements. |
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

HTTP versionado é a decisão vigente. gRPC bidirecional/HTTP2/Protobuf e compatibilidade N/N−1 específica são propostas sujeitas a ADR e ensaios. Separar ACK durável, efeito aplicado e conclusão; verificar autenticação, sessão, tentativa, fencing, replay, reconexão e backpressure.

Pacote instalado em `.codex/skills/` com integridade conferida. Descoberta pela interface exige observação própria.
