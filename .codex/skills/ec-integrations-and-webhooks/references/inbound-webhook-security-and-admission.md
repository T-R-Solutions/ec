# Inbound Webhook Trust, Replay and Durable Admission

## The security profile is defined by the sender, not a universal webhook validator

| Sender | Signed material and documented semantics | Replay / downgrade caveat |
|---|---|---|
| GitHub (webhook secret) | HMAC-SHA256 over **raw body**, `X-Hub-Signature-256: sha256=<hex>` | `X-GitHub-Delivery` is stable across redeliveries, but **not in the MAC**. No authenticated timestamp: HMAC alone does not ensure freshness; a captured signed body can be replayed. Use bounded scoped dedup and downstream semantic idempotence; describe residual risk honestly. |
| GitLab 19.1+ signing-token profile | Standard Webhooks style `webhook-id`, `webhook-timestamp`, `webhook-signature` over `{message_id}.{timestamp}.{raw_body}`; key decoded from `whsec_` base64; `v1,<base64>` | Timestamp and message ID are MAC-bound. Validate finite freshness, approved secrets and same per-connector identity; reject unsupported algorithms or malformed headers. Legacy `X-Gitlab-Token` is plaintext and must not silently substitute for invalid HMAC. |
| Slack | `v0:{timestamp}:{raw_body}` under HMAC-SHA256, with Slack's request timestamp/signature headers | Bounded timestamp skew and constant-time comparison; Slack-specific protocol, not interchangeable with GitHub or GitLab. |
| CloudEvents | `source` + `id` are the unique event identity in the CloudEvents specification | CloudEvents does **not** by itself specify an authenticating MAC or provide delivery guarantees. Add independent authenticated transport/signature profile. |
| Unspecified provider | No assumptions | Decline to enable reception until the provider contract and expected trust material are defined and testable. |

## Receiver algorithm

1. Match endpoint to one immutable connector/tenant/provider profile, before trusting unverified headers. Enforce TLS termination policy, byte limits, header cardinality limits and per-sender rate caps; use a raw-body buffer without parsing/mutating it.
2. Resolve expected active key(s) using secret references from trusted connector configuration and a current authorized scope. Do not choose a tenant or key solely from attacker-controlled payload fields.
3. Parse the documented header format strictly, authenticate required signed components in the specified byte order, and compare digests in constant time. For an authenticated timestamp, parse and check signed freshness against an agreed clock-skew window. Some providers have **no authenticated clock**; mark that risk rather than fabricating a window.
4. Only after verification, parse the payload with configured size/depth/schema restrictions; validate provider identity/installation, source scope, event type and allowed subscription, and reject cross-project routing. Distinguish a health-ping event from a run trigger.
5. Within one durable transaction, insert an inbound receipt keyed by `(connector_id, provider, documented_event_or_delivery_id)` and a work/outbox entry; require uniqueness and detect same-key different-body inconsistencies. Acknowledgment is emitted **after commit**. A duplicate is a no-op for the side effect while preserving receipt/audit evidence. The record must survive restart at least through the source redelivery horizon and any policy-defined recovery period.
6. A worker consumes the durable entry under retry/fencing/concurrency rules. Its downstream effects need their **own** idempotency because crash between external effect and local ACK can cause repeat execution. Hand authenticated Git events to `cicd-vcs-event-processing`; that skill determines branch/commit/revision/trigger semantics.

## Failure and concurrency oracles

- Valid GitHub vector from [O03]: `secret="It's a Secret to Everybody"`, body bytes `Hello, World!`, expected `sha256=757107ea0eb2509fc211221cce984b8a37570b6d7586c22c46f4379c8b043e17`.
- Reject altered body, altered unicode encoding or re-serialized JSON, unknown key, malformed signature, omitted signature, mismatched connector scope; never parse JSON before signature check.
- For GitLab: reject signed timestamp outside window, wrong/unsigned `webhook-id`, invalid base64 and unsupported `v0` signature, but accept one of multiple signatures **if** signed under an approved rotating key and the exact documented format.
- Inject failure between receipt insert and outbox insert: both must roll back; a retry should be accepted once. Inject lost HTTP response after commit: the sender may retry and must observe an idempotent duplicate without a second outbox item.
- Duplicate under concurrent workers: unique constraint wins; an in-memory lock by itself is not durable deduplication.
- Conflicting payload for an existing event ID is **not** an ordinary duplicate. Reject/quarantine and audit, especially where delivery ID is not authenticated.
- Out-of-order delivery must not overwrite confirmed source revisions or invoke workflows merely by arrival order; defer domain-specific ordering to the trigger owner.

## Acknowledgment language

`202 Accepted` means **durably admitted to processing**, only if the service actually committed that state. It is not proof of pipeline creation, webhook side effect, final result or remote completion. Do not return success when the durable store is unavailable.

**Source anchors:** [O03-O05], [O07-O09], [O10], [O13-O14], [O18-O20] and [P03-P06] in [sources and decisions](sources-and-decisions.md).
