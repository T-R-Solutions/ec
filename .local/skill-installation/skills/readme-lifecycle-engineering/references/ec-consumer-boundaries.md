# Ec-specific conditional application

This reference is read **only when the target consumer is Ec**. It does not impose Ec
norms on projects using this skill elsewhere.

## Authority and truthfulness

Ec is a full CI/CD management product. A README should distinguish implemented, tested,
planned and unavailable capabilities in project administration, teams/users, plugins,
pipelines, agents, access control, operations and deployment. Do not reduce the product's
scope to a pipeline designer because only one module is implemented so far. Conversely,
planned UI or protocol does not prove a working release.

The permanent product rules have precedence over template examples:
- Product is named **Ec**.
- Rust modular monolith for Ec Server and Ec Agent, Vue + TypeScript frontend.
- **YAML only** as canonical *desired declarative configuration*; Visual/Form/YAML modes
  must preserve the same semantic model, without losing fields. Markdown README examples
  may show YAML but cannot invent a new Ec schema or assert an unimplemented command.
- Operational state belongs to PostgreSQL and relevant operational stores, not YAML.
- Current norm describes versioned HTTP between server/agents; **gRPC on HTTP/2 and
  SQLite agent-local outbox are architectural proposals** pending ADR and implementation
  validation. PostgreSQL is the planned global operational state authority.
- No statement that 1,000 agents are supported without benchmark evidence. That number
  is a proposed load-test target.
- No plaintext secrets in documented YAML examples; use references or safe placeholders.

## Recommended documentation shape if repository layout supports it

```text
README.md                        project identity, status, quick start, navigation
server/README.md                 only if separately installable/built and real source exists
agent/README.md                  only if separately installed/operated and real source exists
frontend/README.md               only if an independent frontend developer workflow exists
docs/getting-started/            implemented onboarding flow(s)
docs/how-to/                     task-specific usage and operations
docs/reference/                  supported versioned YAML/API/protocol contracts
docs/explanation/                architecture and trade-offs
docs/decisions/                  existing ADRs (if repo uses this layout)
```

These are **examples of content boundaries, not instructions to create a new repository
layout**. Inspect actual code/paths first. Do not create `server/`, `agent/` or a
`frontend/` directory merely to match the example. Respect active SDD paths and history.

## Reader path test examples

- New evaluator can distinguish the product vision from currently usable capabilities.
- Server operator can find the real prerequisites for installation and verify server readiness.
- Agent operator can find compatible version, credentials handling, enrollment and recovery,
  where these actually exist.
- Pipeline author can discover the canonical YAML schema and how Visual/Form/YAML are
  intended to interact, without treating planned support as available.
- Developer can find current `cargo`, frontend checks, test commands and actual directories.
- Security reviewer can find boundaries for trust, RBAC, secrets and job sandbox claims.

Evaluate these only where implemented/relevant; otherwise state a pending documentation need,
not a failed runtime outcome. The two Ec normative documents must be read in their current
version before editing actual Ec files.
