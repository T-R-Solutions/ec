# Approvals, least privilege and change windows

> Use the destination's existing requirements and evidence format; named workflow documents are examples, not prerequisites. Optional SDD mapping is configured outside this skill.

Approval is scoped to exact `{tenant, environment, deployment_id, artifact_digest, config_revision, policy_revision}` and time validity. Authenticate approver and require role/permission; avoid self-approval when policy prohibits it. Protect against captured, stale and replayed approvals. A policy change must explicitly invalidate or re-evaluate pending authorizations.

Separate **review of planned change**, **authorization to start**, and **confirmation of physical action**. Do not grant deployment credentials until all relevant checks pass. An authorization flow can be satisfied by explicit policy conditions, and test fixtures may assume required approval; do not bake provider-specific self-review defaults into a generic skill.

Change windows are target-local policy with timezone and rule version; an absolute timestamp alone does not express recurrences or daylight-saving transitions. Pin policy revision and record timezone. Approval expiring while rollout is underway needs an explicit project workflow decision; do not silently invent cancellation.

Test: wrong tenant, approver lacking role, self-review violation, expired approval, wrong artifact/config, wrong policy revision, target change, duplicate approval, approval obtained only after attempted deploy, out-of-window request.
