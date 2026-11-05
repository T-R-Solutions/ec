# Threat and compatibility matrix (review aid, not product policy)

> Use the destination's existing requirements and evidence format; named workflow documents are examples, not prerequisites. Optional SDD mapping is configured outside this skill.

| Threat or incompatible change | Unsafe pattern | Safer engineering method | Verification |
|---|---|---|---|
| Unauthorized agent joins | `agent_id` string accepted as identity | authenticated proof of possession + enrollment authorization + binding to stable principal | forged agent ID and replayed enrollment rejected |
| Enrollment key stolen | permanent shared credential reused | short-lived/scoped one-time enrollment and auditable revocation, as chosen by project workflow | used token replay and post-revocation negative tests |
| Downgrade of wire version | untrusted agent requests legacy mode that omits fence | authenticated negotiation, required feature gates | old client unable to acquire protected work |
| Duplicate heartbeat | replay extends liveness | session-epoch + monotonic sequence; duplicate receipt without state mutation | repeated request leaves server receive time unchanged |
| Agent restart | original worker session continues to report | server-side epoch changes, invalidate old session, reconcile pending assignments | old session cannot publish fresh status |
| Partial partition | connectivity alone proves work lease | independently validate work lease authority and expiry | connected session with stale lease rejected |
| Delayed/reordered result | older agent overwrites newer result | durable per-work increasing fence, check at commit | f+1 wins, f denied |
| Agent clock skew | client timestamp controls lease expiry | server-authoritative time and durable transactional state | skewed timestamps cannot extend ownership |
| Result replay | outcome updated twice | authenticated idempotency identity and payload digest; durable receipt | identical replay safe, altered replay conflict |
| Lost response after commit | caller creates duplicate assignment | lookup/receipt or contract-safe replay | lost result ack does not create second result |
| Backend restart | fence counter resets | persistent monotone counter or equivalent transactional generation | monotonicity survives reconstructed model state |
| Token leakage in logs | transport dumps credentials | redaction at edges, narrow diagnostic fields | secret marker absent from logs |
| DoS from heartbeats | unbounded requests or payload | bounded sizes/rate/inflight, jitter and backpressure | overload cannot force unlimited memory use |
| Capability spoofing | `linux`/`secure` self-report grants privilege | treat claims as telemetry; attest/enforce isolation separately | spoofed capability does not grant permission |
| TLS early-data replay | sensitive state change in 0-RTT | disallow unless reviewed replay-safe profile | mutation unavailable in early-data simulation |
| Old agent + new server | breaking required field | explicit version feature matrix, additive fields if compatible | contract-fixture parse/missing field tests |
| New agent + old server | assumes unsupported feature | negotiate/deny securely; don't assume auto-upgrade | incompatibility explicit, no mutation |

**Trust-boundary reminders:** the included test model *receives* an authenticated principal as an input because it does not implement real certificate validation. Any model adapter that reads a caller-supplied identity header is demonstrative only and must never be shipped as production security logic.

## Rolling migration evidence matrix

| Combination | Expected documentation | Test path |
|---|---|---|
| Old server + old agent | baseline protocol contract | pinned old-old fixture |
| New server + old agent | overlap range and required-feature availability | mixed-version fixture |
| Old server + new agent | downgrade or explicit unsupported | mixed-version fixture |
| New server + new agent | current protocol support | current-current fixture |
| Reconnected old agent after server rollout | resumption/re-registration contract | session-epoch and pending-command replay |
| Recovered server after crash | persistent fence/cursor/cancel state | restart with durable snapshot and queued delayed result |

**project workflow must specify** exact supported ranges, deprecation window, feature flags, whether a version is encoded in a path or content type, how long old agents may remain connected, security-review requirements, and rollout/rollback owner.
