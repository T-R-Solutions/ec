# Integration Contract, project workflow Requirements and Evidence Ledger

> Use the destination's existing requirements and evidence format; named workflow documents are examples, not prerequisites. Optional SDD mapping is configured outside this skill. Ec-specific rules below apply only to an Ec consumer whose current contract establishes them.

This is a method for project workflow artifacts, not a second product constitution. In the full flow:

- `spec.md`: REQ/AC defines expected transport behavior, source of authority, events, cursor semantics, per-scope authorization, error states, overflow and recovery decisions, latency/freshness criteria (if needed), and forbidden outcomes.
- `plan.md`/ADR: select SSE/WS based on evidence, record client transport and server/intermediary versions, security assumptions, relevant skills and their exclusive boundaries, limitations and testing gates.
- `tasks.md`: bind each approved AC to parser/reducer/transport/browser/live API tests and explicit data setup and cleanup.
- `evidence`/convergence: record exact commands, source revision, executed layers, outputs/hashes, environment, negative controls, witness traces, and one of `APROVADO`, `REPROVADO`, `NÃO VERIFICADO`, `INCONCLUSIVO`, `NÃO APLICÁVEL` for each AC. A structural validator cannot approve an unexecuted functional AC.

Example *illustrative test matrix* (not approved Ec behavior):

| ID | Expected behavior | Forbidden result | Test surface |
|---|---|---|---|
| STR-01 | SSE custom event dispatches after blank line | Event emits on EOF without separator | Pure parser + browser native API when available |
| STR-02 | Wrong workspace event ignored | Old user's data appears after switch | Reducer + browser + real auth service |
| STR-03 | Sequence gap enters recovery | UI says synced while missing sequence | Model + real snapshot/replay integration |
| STR-04 | WebSocket origin denied | Third-party origin obtains cookie-authenticated stream | Loopback + real backend security |
| STR-05 | Queue full signals resync for critical event | Silent drop | Model + slow-consumer real load |
| STR-06 | Disconnect cleans up resources | Background retries continue after logout | Lifecycle + real UI/browser |

**Evidence hierarchy:** structural and synthetic → browser-fixture → protocol loopback → deployed app end-to-end → load/security/assistive technology in representative environment. Neither this skill nor a bridge can assert production implementation from merely executing the first layers.
