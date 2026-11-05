# Deployment state and provider receipts

A representative state model (not a universal contract): `AWAITING_APPROVAL`, `READY`, `DEPLOYING`, `PAUSED`, `PROMOTING`, `ABORT_REQUESTED`, `ABORTED`, `COMPLETED`, `ROLLBACK_REQUESTED`, `ROLLED_BACK`, `FAILED`, `UNKNOWN`. `REQUEST_ACCEPTED` may be a transport response and is **not** a terminal state.

Transition permissions and phase/epoch must be explicit. Only a reconciled **provider-confirmed** receipt with matching deployment ID, target, artifact digest and attempt generation may finalize `PROMOTING -> COMPLETED`. Acknowledgment of an API request, optimistic UI event or timeout is not confirmation. A provider timeout can yield `UNKNOWN`, requiring reconciliation.

`ABORT_REQUESTED` means stop requested; `ABORTED` requires observed stopping/traffic removal according to provider support. This does not imply external data changes were undone. `ROLLBACK_REQUESTED` means initiation, not successful restore. Terminal states should not be silently changed by stale callbacks.

Observe/reconcile transitions and audit actor, approved manifest, policy revision, cause and next action. Log failure to reconcile; do not silently mask drift.
