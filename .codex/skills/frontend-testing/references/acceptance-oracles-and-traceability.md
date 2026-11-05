# Acceptance oracles and traceability

## Procedure

Treat the approved specification as the only source of expected behavior. For each `REQ-...`/`AC-...`, record: route/component, precondition, user, action, observable positive result, observable forbidden result, known time window, layer, fixture scope, and test identifier. If an AC is ambiguous, report a spec gap before writing a passing assertion. Never use a rendered screenshot as a behavioral contract by itself.

| Oracle dimension | Positive | Deliberate negative | Boundary |
|---|---|---|---|
| Status confirmation | confirmation only after accepted response | no success while pending | late success after scope change ignored |
| Authorization UI | permitted user sees action | denied user sees nonrevealing error | stale session with previously loaded view |
| Draft preservation | typed value persists through validation | backend error must not erase fields | concurrent revision conflict preserves revision |
| Navigation | bookmark restores route/query | malformed route does not navigate privately | empty/duplicated query semantics |

An asserted UI message is evidence for the frontend only. A mocked API 403 does not establish backend access control. Store exact observed status and source revision. A flaky suite with pass-on-retry is not a full `PASS`.

## Research grounding

Barr et al. (2015), *The Oracle Problem in Software Testing: A Survey*, IEEE TSE, DOI 10.1109/TSE.2014.2372785. The survey addresses the challenge of determining expected outcomes; it does not prescribe an oracle for a particular application. https://discovery.ucl.ac.uk/id/eprint/1471263/
