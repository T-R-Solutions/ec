# Routing and Real Ec Acceptance Test Oracles

> Commands, fixture paths and historical execution claims from the source package are illustrative or imported evidence unless the corresponding files are present and independently checked; they are not current test results or bundled executable tools.

**NOT EXECUTED:** This document is a predeclared behavioral evaluation and integration test inventory, not a record of passing tests. Every routing case needs an agent evaluator; every integration oracle requires the actual Ec code and infrastructure. Synthetic checks are recorded separately.

## Skill-selection cases

| ID | User intent | Expected routing |
|---|---|---|
| R01 | Configure an organization GitHub App connector | Activate; installation scopes and credential ownership |
| R02 | Add a GitLab project webhook | Activate; provider/version signing profile |
| R03 | Verify a GitHub `X-Hub-Signature-256` header | Activate; raw-body trust boundary |
| R04 | Introduce a durable webhook receipt table | Activate; dedup and queue boundary |
| R05 | Debug duplicate webhook deliveries | Activate; idempotence and trace |
| R06 | Implement an outbound Slack callback | Activate; destination/security/send policy |
| R07 | Make outbound callbacks safe from DNS rebinding | Activate; SSRF egress controls |
| R08 | Rotate a provider signing key | Activate; overlap and revocation |
| R09 | Implement OAuth connection for source provider | Activate; PKCE/issuer/account binding |
| R10 | Design connector health UI | Activate; observed versus desired truth |
| R11 | Add dead-letter and controlled redelivery | Activate; operator-safe recovery |
| R12 | Investigate a stale provider credential reference | Activate; secret broker and status |
| R13 | Map push events to pipeline jobs | **Do not activate alone**; route `cicd-vcs-event-processing` |
| R14 | Fix a plugin sandbox escape | **Do not activate**; route `ec-plugin-isolation-and-lifecycle` |
| R15 | Change YAML schema parser | **Do not activate**; route `ec-declarative-resource-schemas` |
| R16 | Fix agent heartbeat protocol | **Do not activate**; route `ec-agent-control-protocol` |
| R17 | Change team RBAC membership | **Do not activate**; route `ec-effective-permissions` or people admin |
| R18 | Implement a pipeline-step retry | **Do not activate**; route `ec-run-execution-operations` |
| R19 | Publish a changed connector YAML revision | Activate for connector semantics, delegate publication to `ec-configuration-publication` |
| R20 | Add an integration provider through a plugin | Activate for connector boundary; delegate plugin runtime to `ec-plugin-isolation-and-lifecycle` |

## Planned real Ec oracles (NOT EXECUTED)

| ID | Real acceptance test | Required observed outcome |
|---|---|---|
| E01 | Visual create connector, inspect YAML | Exact semantic resource, no second config |
| E02 | Form edit provider scope, inspect visual/YAML | Scope preserved without hidden-field loss |
| E03 | YAML valid edit, reload Form | Consistent supported values and defaults |
| E04 | YAML invalid edit, switch mode | Published resource unchanged |
| E05 | Concurrent connector publication | Conflict reported, no last-write-wins |
| E06 | Publish credential reference | No secret value in Git/UI/API/log |
| E07 | Same credential reference in another tenant | Denied unless independently authorized |
| E08 | Non-admin changes global connector | Denied and audited |
| E09 | Repository owner changes installation | Provider and Ec scope revalidated |
| E10 | OAuth state replay | Rejected |
| E11 | OAuth issuer mix-up | Rejected |
| E12 | OAuth redirect mismatched | Rejected |
| E13 | OAuth code without matching PKCE verifier | Rejected |
| E14 | OAuth account/installation unlinked | Connection not reported healthy |
| E15 | OAuth revoked refresh grant | Degraded/unknown, not connected |
| E16 | GitHub published HMAC test vector | Accepted only with exact raw bytes |
| E17 | GitHub payload tampered, same delivery ID | Authentication rejected |
| E18 | GitHub copied MAC with spoofed GUID | Not claimed cryptographically replay-safe |
| E19 | GitLab valid signing-token profile | Signed ID, time, body accepted |
| E20 | GitLab expired timestamp | Rejected |
| E21 | GitLab changed message ID, same body | Rejected MAC |
| E22 | GitLab invalid MAC plus valid legacy token | No automatic downgrade |
| E23 | Slack stale request | Rejected |
| E24 | Duplicate provider delivery concurrent workers | One durable inbox/outbox work item |
| E25 | Same ID and different body | Conflict/quarantine, audit |
| E26 | Crash between receipt and enqueue | Both rollback; retry succeeds once |
| E27 | Crash after commit before ACK | Sender retry is duplicate; one outbox |
| E28 | Database unavailable before ACK | Retryable failure, no fictitious success |
| E29 | Out-of-order provider events | No implicit ordering guarantee |
| E30 | Unsubscribed event | No work triggered; policy-specific receipt |
| E31 | Source Git event handoff | Immutable revision selection delegated to trigger owner |
| E32 | Replay with revoked connector revision | Denied or reauthorized under new revision |
| E33 | Outbound URL targets loopback | Denied before connect |
| E34 | Outbound URL targets cloud metadata | Denied before connect |
| E35 | Outbound redirect to private IP | Denied on redirected connection |
| E36 | DNS rebinding between validation/connect | Connected IP bound to approved address |
| E37 | DNS changes on retry | Revalidated and denied if unsafe |
| E38 | IPv6 link-local callback destination | Denied unless explicit isolated policy |
| E39 | Outbound callback certificate invalid | TLS failure not treated as success |
| E40 | Outbound HTTP 202 | Only remote acceptance displayed, not completion |
| E41 | Outbound timeout after peer side effect | Unknown; no exactly-once assertion |
| E42 | Retry with same event identity | Receiver-side contract and idempotency verified |
| E43 | Retry budget exhausted | Durable dead-letter + actionable diagnostics |
| E44 | Two tenants share destination | Isolation on quotas, receipts and secrets |
| E45 | Rotate old/new signing secret | Bounded approved overlap; old key rejected later |
| E46 | Secret value appears in error payload | Test fails; redaction enforced |
| E47 | Keyboard + screen reader connector operations | WCAG 2.2 AA gates met in real browser tests |
| E48 | Mobile, light/dark, stale/unknown states | All actions and status semantics available |
