# Skill routing examples

> Ec-specific rules below apply only to an Ec consumer whose current contract establishes them.

**Positive P1:** "Implement canary phase advancement only after fresh, adequately sampled metrics, then reconcile target receipt" -> `cicd-release-and-deployment`.
**Positive P2:** "Evaluate roll forward vs rollback for a schema-changing release" -> this skill; consult database/security skills only when independent technical scope is demonstrated.
**Negative N1:** "Create an environment approval YAML resource in Ec" -> `ec-environments-and-approvals` and Ec resource/schema owners, not this generic skill.
**Negative N2:** "Move an execution from queue to an agent" -> `cicd-scheduler`, not this skill.
**Negative N3:** "Design attractive deployment history page" -> UI/design skills, not this skill unless request also includes technical backend deployment semantics.
**Borderline B1:** "Implement a Rollback button" -> UI owner first; use this skill as auxiliary **only** if authoritative rollback preconditions, provider receipts and safe recovery behavior must also be specified.
Do not copy fixed Ec norms into generic method or imply other skills are automatically loaded.
