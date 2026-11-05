# Integration evidence, delivery and project workflow fit

> Use the destination's existing requirements and evidence format; named workflow documents are examples, not prerequisites. Optional SDD mapping is configured outside this skill.

Read approved project standards and the feature `spec.md` before the technical method. Deliver a table containing provider issuer, client type, trusted JWKS, exact callback registration, signed-token verification policy, browser flow, session expiry, refresh behavior, logout support and negative oracles. Include explicit version IDs for libraries and servers, never plaintext credentials.

Route outcomes by `spec.md` (behavior & AC), `plan.md`/ADR (methods, tool choices, risks), `tasks.md` (changes), and evidence/convergence (actual attempts, commands, observations). An optional integration is configured separately by the consumer.

Classify every statement as **protocol normative requirement**, **study observation**, **engineering recommendation**, **fixture observation**, or **real integration evidence**. Package local tests prove only their executable toy models and artifact consistency. For deployment, require TLS, real provider and client registration, secure secret manager, resource-server authorization, persistent session store, multi-instance concurrency and browser checks; label each not run item `NOT EXECUTED` / `NÃO VERIFICADO`.

**Forbidden shortcuts:** using JWT payload without verification, trusting `email_verified` as RBAC, storing login state in YAML, treating a successful identity-provider redirect as server-confirmed session creation, creating new product requirements out of the skill method.
