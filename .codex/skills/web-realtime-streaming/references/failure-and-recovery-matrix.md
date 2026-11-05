# Failure Injection and Expected Recovery Oracles

| Fault | Client evidence | Expected policy decision (requires approved spec) | Counterexample to reject |
|---|---|---|---|
| Temporary network outage | Connection state changes, attempts captured | Capped/jittered retry, stale notice | Infinite tight reconnect or misleading success |
| Replayed SSE event | Same stable event ID/domain seq observed twice | De-duplicate only when contract supports it | Duplicate UI mutation or external action |
| Skipped sequence | Gap between expected and incoming sequence | Freeze completeness, obtain authorized snapshot | Mark stream synchronized anyway |
| Cursor retention expired | Server defined expiry status (e.g. 410) | Snapshot/reconcile | Assume transport has all missed updates |
| Unauthorized session / origin | 401/403 handshake or error by contract | Stop and reauth / deny | Reconnect loop or cross-tenant data leak |
| Slow consumer | Queue watermarks and drop counters | Coalesce ephemeral only; preserve critical with resync | Silent loss of critical logs/events |
| Oversized payload | Parser size limit reached | Reject, signal appropriate error | Unbounded growth or HTML injection |
| Hidden tab and page return | Resource/lifecycle observed | Revalidate freshness and subscriptions | Old connection publishes current green state |
| Scope change during reconnect | New generation created | Close old and ignore old callbacks | Previous user/project data reappears |
| WS send while disconnected | readyState not OPEN | Refuse local enqueue and surface action not sent | Treat `send` as remote acknowledgment |
| Proxy buffers SSE | Delayed live events in deployment traces | Configure/verify proxy flush and timeouts | Claim streaming proven by loopback only |
| Browser no cross-origin credentials | Native API error | Revisit auth/CORS/service design | Put long-lived secret in URL to bypass controls |

**Reporting:** list fault injection timestamp, payload/scope (redacted), sequence/cursor, expected state, observed state, trace ID, actual test command, browser/server versions and outcome. If the authoritative server isn't available, record `NOT EXECUTED` for its side of the scenario.
