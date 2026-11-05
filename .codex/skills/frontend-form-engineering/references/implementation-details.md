# Detailed decisions and failure cases

Read only for a decision needing more detail than the entry point. Apply the relevant steps to the
actual task; the sections are not a mandatory artifact or test checklist. Use existing project
contracts in their actual format. File names such as spec.md, plan.md or tasks.md are examples, not
required dependencies. Other skills are optional expertise, never enforcement mechanisms.

Ec references below are conditional consumer examples. They impose no Ec documents, YAML format,
stack or product policy on a generic task.

## Procedure

1. **Classify and identify authorities.** Distinguish declarative configuration editing, an
   operational action (e.g. revoke a session), and mere input assistance. Record where truth resides
   (server, revisioned configuration or provider), what counts as accepted/saved/effective, and
   which operations require explicit authorization/confirmation. Product norms override this method.
2. **Model the data contract.** Establish stable field names/IDs, value types, optionality, parsing,
   display formatting and references from the authoritative spec. Distinguish missing from `null`,
   empty string, zero and false. Avoid interpreting server data as trusted. Keep unknown or advanced
   fields intact unless the contract explicitly specifies removal. See [state and
   contract](form-contract-and-state.md).
3. **Define form states and transitions.** Separate initial/baseline, editable draft,
   touched/visited/dirty state, validation issues, submitting, confirmed revision, rejection,
   conflict and transport error. Use an explicit state machine or reducer when cross-field behavior
   warrants it. A local “request sent” event must never display “saved” before authoritative
   confirmation; include `aria-invalid` with appropriate field errors when needed. See [async and
   concurrency](async-submission-and-concurrency.md).
4. **Use semantic controls first.** Implement native `form`, `label`, `input`, `select`, `textarea`,
   `fieldset`, `legend`, `button`, descriptions and appropriate autocomplete/inputmode. Prefer
   native semantics to custom ARIA widgets. For custom controls, document the keyboard/role/value
   contract and request accessibility-specialist review. See [native accessible
   controls](native-accessible-controls.md).
5. **Implement validation in layers.** Treat HTML constraints and JavaScript validation as UX
   assistance, not security. Backend remains authoritative for trusted acceptance. Design a
   deterministic pipeline: parse → type/shape → field constraints → cross-field conditions →
   server/business validation. Normalize only per documented semantic rules. Map returned errors to
   stable keys and actionable messages. See [validation](validation-and-errors.md) and [field
   types](field-types-and-formatting.md).
6. **Design error timing and recovery from evidence.** Compare submission-time, blur-time and
   input-time feedback as alternatives, especially for IME composition and slow typing. Avoid
   unconditional “instant errors are always better”: controlled studies have conflicting trade-offs.
   Ensure visible field errors, accessible descriptions and an error summary with links/focus when
   appropriate. Preserve valid and invalid user values and provide a correction path. See
   [scientific evidence](scientific-evidence-and-limits.md).
7. **Keep advanced and dynamic values safe.** Define the semantics of hidden, disabled, untouched
   and conditionally irrelevant fields. Preserve noneditable fields on mode switch and reject silent
   field deletion. For repeated entries, use stable IDs, consistent validation keys and intentional
   ordering. If a field's hidden value must be dropped, make this an explicit product decision and
   show impact. See [conditional fields](conditional-and-dynamic-fields.md).
8. **Make async operations race-safe.** Snapshot the submitted revision/payload, disable or gate
   duplicate commits, separate async validation from submit, cancel obsolete requests where possible
   and ignore stale replies even when abort is ineffective. Do not overwrite later edits on
   acknowledgement of an earlier snapshot. On 409/conflict or validation rejection, keep the
   editable draft and expose a recovery path; transport failure must not appear as a valid-field
   error. See [async](async-submission-and-concurrency.md).
9. **Enforce security boundaries.** Server validates all values and permissions; browser constraints
   and UI hiding do not authorize operations. Never place secrets in plain text errors, logs, local
   persistence, URL, screenshots or declarative resources. Render untrusted error strings as text,
   not HTML. Don't assume credential/token fields belong in editable YAML. See [server
   reconciliation](server-reconciliation-and-security.md).
10. **Adapt to the framework only after defining behavior.** In Vue 3, `v-model` is a binding
    convenience rather than an independent configuration authority; check value modifiers, IME
    composition, stable keys, computed derivations and explicit emits. Keep type narrowing and API
    schema logic in their owning skills. See [Vue boundary](vue-adapter-boundaries.md).
11. **Produce executable acceptance oracles.** Test positives and negatives for optional/missing
    fields, invalid data, cross-field constraints, hidden values, stale async replies, duplicate
    clicks, server rejection, conflict, edit-while-saving, reset, focus/ARIA, long labels/mobile and
    screen-reader flows when available. Use real browsers and authoritative backend integration when
    possible. Measure only what ran; never turn synthetic fixture PASS into Ec homologation. See
    [testing](tests-and-evidence-oracles.md).
12. **Deliver evidence and unresolved decisions.** Supply field contract matrix, state/transition
    diagram or table, validation timing decision, DOM/keyboard contract, server error mapping, code
    patch plan, REQ/AC-linked positive/negative cases, actual results/versions and limitations.
    Prefer a bounded local project workflow route for small tasks; the optional `project
    workflow/chain/frontend-form-engineering.md` bridge supports project workflow 1.3.0 when
    warranted.


## Correctness constraints

- **No parallel configuration authority.** An input model is an *editable projection* of the
  product's desired configuration or operational command. For Ec, persistent declarative
  configuration is **YAML only**, and Visual/Form/YAML editing must preserve semantic equivalence
  and unknown supported fields; the actual parser, backend and product contract enforce that
  requirement.
- **No validation-as-authentication.** HTML `required`, client schemas and disabled buttons do not
  protect a backend. Fields hidden from a user may still be sent by malicious clients. Reject
  unauthorized actions server-side; report permission failures separately from correctable field
  errors.
- **No false saves.** `pending`, `server accepted`, `revision stored` and `effect applied` may be
  different states. Use the names and evidence actually defined by the product; never mark an
  operation effective based on an optimistic UI transition.
- **No destructive mode switches.** Don't drop values because an editor, form or browser cannot
  represent them. Field defaults, numeric coercions, locale parsing, list ordering and placeholders
  can change semantics; use explicit reversible handling.
- **No universal timing/layout claim.** Peer-reviewed studies on error message placement/timing are
  context-dependent and at times conflicting. Choose based on task and target users; test the
  proposed design, especially with assistive technology and mobile layouts.
- **No unsupported accessibility certificate.** Automated DOM/Chromium checks detect defined issues
  but do not establish WCAG 2.2 AA conformance or usability with disabled participants. Normative
  W3C criteria take precedence over example design-system guidance.
- **No unmeasured performance claim.** If a form revalidates on every keypress, demonstrate an
  actual problem before introducing speculative throttling, debouncing or expensive libraries;
  ensure cancellation correctness first.


## Further references by decision

Read only the reference relevant to the unresolved decision. Research registers describe imported authorship, not current execution.

- [Async validation, submissions, concurrent edits and acknowledgements](async-submission-and-concurrency.md): async submission and concurrency.
- [Conditional, advanced and repeated form fields](conditional-and-dynamic-fields.md): conditional and dynamic fields.
- [Field types, parsing and formatting](field-types-and-formatting.md): field types and formatting.
- [Field contracts and controlled form state](form-contract-and-state.md): form contract and state.
- [Native accessible form controls and keyboard recovery](native-accessible-controls.md): native accessible controls.
- [Skill routing oracles (for agent evaluation)](routing-and-selection-oracles.md): routing and selection oracles.
- [Peer-reviewed empirical evidence — what it supports and what it does not](scientific-evidence-and-limits.md): source provenance or a disputed recommendation.
- [Server trust, security and conflict boundaries](server-reconciliation-and-security.md): server reconciliation and security.
- [Official standards, documented API behavior and evidence provenance](sources-and-decisions.md): source provenance or a disputed recommendation.
- [Tests, evidence and acceptance oracles](tests-and-evidence-oracles.md): tests and evidence oracles.
- [Validation, error timing and user recovery](validation-and-errors.md): validation and errors.
- [Vue 3 adapter boundaries — consume, don't replace, generic form semantics](vue-adapter-boundaries.md): vue adapter boundaries.
