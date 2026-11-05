# Skill selection, counterexamples and test oracles

> Use the destination's existing requirements and evidence format; named workflow documents are examples, not prerequisites. Optional SDD mapping is configured outside this skill. Commands, fixture paths and historical execution claims from the source package are illustrative or imported evidence unless the corresponding files are present and independently checked; they are not current test results or bundled executable tools.

## A. Activation tests — proposed, **not executed with any LLM agent**

These examples are prompts for controlled tests of automatic skill selection. Expected target is `ec-run-execution-operations` unless explicitly stated otherwise. A target may use other skills only for independently justified work, not automatically.

| ID | Candidate task | Expected routing |
|---|---|---|
| S01 | Implement a run detail view with server-confirmed queued/running/outcome badges | Activate |
| S02 | Show cancellation requested separately from the eventual canceled state | Activate |
| S03 | Expose an idempotent operator retry operation with attempt history | Activate |
| S04 | Render stale/unknown run status after reconnect and missed events | Activate |
| S05 | Implement run list filtering by project and confirmed outcome | Activate |
| S06 | Review 202 Accepted handling in a run-cancel command view | Activate |
| S07 | Audit whether retried attempts retain immutable source/config revision IDs | Activate |
| S08 | Design a run authorization matrix without implementing policy evaluation | Activate with `ec-effective-permissions` only if evaluation is independently requested |
| S09 | Diagnose incorrect timeline after out-of-order run status updates | Activate |
| S10 | Test concurrent cancel and successful finish without false cancellation | Activate |
| S11 | Show queue reason and position only when backend can substantiate them | Activate |
| S12 | Explain failure/timeout/cancel classification in execution history | Activate |
| S13 | Verify a run retry returns the same receipt on lost-response replay | Activate |
| S14 | Present run controls accessibly with nonintrusive status announcements | Activate; `web-accessibility` optional for a distinct generic review |
| S15 | Define scheduler fairness, `SKIP LOCKED`, and lease acquisition | **Do not activate**; `cicd-scheduler` |
| S16 | Implement agent heartbeat and fencing-token wire protocol | **Do not activate**; `ec-agent-control-protocol` |
| S17 | Implement process-group termination for Rust jobs | **Do not activate**; `cicd-execution-runtime` |
| S18 | Store and stream stdout chunks in correct order | **Do not activate**; `cicd-logs-and-test-results` |
| S19 | Add expiring signed URLs to build artifacts | **Do not activate**; `cicd-artifacts-and-retention` |
| S20 | Define pipeline YAML validation with duplicate key rejection | **Do not activate**; `ec-declarative-resource-schemas` |
| S21 | Change a pipeline DAG canvas inspector | **Do not activate**; `ec-pipeline-authoring-workspace` |
| S22 | Implement partial deployment rollback | **Do not activate**; `cicd-release-and-deployment` |
| S23 | Test generic SSE reconnection library without run semantics | **Do not activate**; `web-realtime-streaming` |
| S24 | Handle a Git webhook trigger and fixed checkout revision | **Do not activate**; `cicd-vcs-event-processing` |
| S25 | Compare CI pipeline products' rerun UX patterns without an Ec feature task | **Do not auto-activate**; research/design task |
| S26 | Add a queue-position ETA based solely on count of rows returned by `SKIP LOCKED` | **Activate to reject unsafe assumption**; delegate queue algorithm |
| S27 | Implement complete execution from YAML to terminated OS process | **Activate only for operator-facing subtask**, delegate scheduling/runtime/schema |
| S28 | A run is reported as succeeded but event stream has a missing sequence | **Activate to mark freshness unknown and resync** |

The project specification must clarify semantics when mixed routing requires overlapping skills; named neighbors are not recursive activation commands.

## B. Synthetic state-machine and negative tests — **executable oracle**

`validation/reference_model.py` and `validation/test_reference_model.py` model a **single-process** run/attempt lifecycle and observable command protocol. No real DB, agent, network, authentication framework or Vue components are involved. Tests distinguish status from command receipt and from browser freshness.

| Class | Invariant | Failure expected |
|---|---|---|
| Authorization | Actor/scope/action checked before command and idempotency replay | Revoke permission and attempt replay -> `Denied` |
| Optimistic version | Mutations must match expected attempt version | Old expected_version -> `Conflict` |
| Idempotency | One actor + key + identical fingerprint returns same receipt | Same key with changed action/target/version -> `Conflict` |
| Cancel intent | Command accepted without changing authoritative run status | Cancel queued attempt remains queued until verified status event |
| Cancel/finish race | Confirmed terminal result is immutable | Complete before cancel -> `Invalid`; complete after accepted cancel -> receipt may be `too-late` |
| Retry identity | New attempt is linked, prior terminal result remains | Replayed key does not create a second attempt |
| Revision pinning | Code/config revision fixed across illustrative retries | New attempt retains both original pins |
| Unknown observations | Stale, disconnected, gapped or contradictory events cannot show success | Projected status becomes `unknown` until server snapshot |
| Runtime source | Only injected *verified* result accepted | Unverified status event -> `Denied` |
| Terminal monotonicity | Terminal attempt cannot return to running | Attempt terminal -> any transition rejected |

All example terminal states and retry policies are **test fixture choices**. They are not normative Ec decisions.

## C. Real Ec integration acceptance proposals — not executed

| ID | Test intent and measurable oracle |
|---|---|
| I01 | Submit an authorized run; verify immutable configuration revision/hash and fixed source commit in API and UI |
| I02 | Change and publish YAML after submission; prior run's pinned revisions remain unchanged |
| I03 | Query project-scoped run list; rows from another project do not leak across authorization scopes |
| I04 | Deny cancellation to user without permission even if a stale UI still displays the button |
| I05 | Revoke permission after a command receipt; subsequent read/replay must not leak restricted details |
| I06 | Concurrent cancel and success: exactly one authoritative final status; UI never rewrites a committed success |
| I07 | Cancellation of queued work awaits a scheduler-confirmed outcome and does not misreport running process termination |
| I08 | Cancellation of active work shows request accepted, cleaning/canceling if supported, then confirmed terminal evidence |
| I09 | Disconnect an agent after cancel accepted; UI displays uncertain state until verified recovery |
| I10 | Cancel a terminal run; return an actionable policy-defined rejection, no state mutation |
| I11 | Repeated cancel request with same key returns equivalent receipt and does not dispatch duplicate commands |
| I12 | Same idempotency key with different target or payload conflicts predictably |
| I13 | Drop the HTTP response after accepting a retry; lookup/replay identifies the same new attempt |
| I14 | Submit two different retries simultaneously; verify project-approved policy on concurrency and attempt count |
| I15 | Retry a failed run; previous failure, reason, logs and timestamps remain inspectable |
| I16 | Validate policy whether successful/canceled attempts are retry-eligible, with forbidden cases rejected |
| I17 | Retry retains or changes pinned revision **exactly as approved in project workflow**; no implicit tip-of-branch substitution |
| I18 | Late result from a fenced old agent cannot complete the new attempt |
| I19 | Simulate DB transaction rollback after command acceptance candidate; no phantom accepted receipt |
| I20 | Restart control plane after accepted action; durable dedup and state reconciliation restore expected behavior |
| I21 | Force database serialization conflict; retry whole transaction safely or surface conflict, no lost update |
| I22 | Emit status events out of order; UI cannot regress from terminal to running |
| I23 | Drop sequence number; UI marks data stale and retrieves authoritative snapshot |
| I24 | Reconnect after offline; event replay or full snapshot restores consistent version and status |
| I25 | Show queue position only if authoritative query guarantees it; unknown otherwise |
| I26 | Distinguish infrastructure failure, build/test failure, cancel, timeout and not-run statuses per actual domain spec |
| I27 | Ensure retained run events correlate to exact project, run, attempt and step IDs |
| I28 | Error and conflict responses use the approved HTTP contract with documented actionable messages |
| I29 | Screen-reader announcement of async cancel acceptance and resolution; keyboard focus not stolen |
| I30 | Responsive run detail preserves cancel/retry controls, revision fields, status and security labels |
| I31 | Run UI under stale connection cannot display a newly observed unverified green success state |
| I32 | Measure run list and detail p50/p95 latency against project workflow workload; no invented targets |
| I33 | Test unauthorized cross-project deep link to run details and error/redaction behavior |
| I34 | Test canceled run with external side effect: UI warns of possible already-performed effects, no exactly-once claim |
| I35 | Validate audit actor, reason, operation IDs and versioned outcome for retry/cancel with sensitive fields redacted |
| I36 | Test UI timeouts and 202/409/412 distinctions in browser without treating acceptance as completion |

## D. Agent evaluation plan (not executed)

For each S01–S28 prompt, compare with the skill available vs unavailable under **identical** model, context, project workflow, tools and inputs. Score routing precision/recall; unsupported feature invention; correctness of terminal-vs-acknowledgement distinctions; retention of immutable revision provenance; unsafe cancel/retry actions; citations to actual code; time and tokens per accepted project workflow criterion. Failing security/authority cases must veto acceptance regardless of average scores. Do not claim an improvement until matched results are collected.
