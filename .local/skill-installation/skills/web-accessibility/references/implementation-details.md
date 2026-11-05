# Detailed decisions and failure cases

Read only for a decision needing more detail than the entry point. Apply the relevant steps to the
actual task; the sections are not a mandatory artifact or test checklist. Use existing project
contracts in their actual format. File names such as spec.md, plan.md or tasks.md are examples, not
required dependencies. Other skills are optional expertise, never enforcement mechanisms.

Ec references below are conditional consumer examples. They impose no Ec documents, YAML format,
stack or product policy on a generic task.

## Workflow with evidence gates

1. **Establish scope and authority.** Read the current product norms, relevant project workflow
   `spec.md`, browser/AT support baseline, routes and roles, interactions and user tasks, and
   existing code. Define the version and level of WCAG, plus any local/legal policies. Use the
   destination project's actual requirements; product-specific examples are conditional. If absent,
   record `NOT VERIFIED` and continue only with bounded recommendations. Separate conformance
   requirements from informative APG patterns and heuristics. See [standards and
   claims](standards-and-scope.md).
2. **Write the task/interaction contract.** Enumerate complete user tasks, entry/exit,
   permission-dependent states, loading/validation/failure, keyboard and pointer operations, text
   resizing and mobile flows. Tie each expectation to a `REQ/AC`, WCAG criterion (when applicable),
   expected outcome, oracle, tester and target browser/AT. Audit the *entire task*, including denied
   and destructive actions, not merely the first page. For dense apps include table/graph/list
   alternative and different scopes of operation when the product specifies them. See [coverage
   matrix](evaluation-matrix.md).
3. **Build semantic HTML first.** Prefer native buttons, links, labels, fieldsets, tables, headings,
   main/nav landmarks and `<dialog>` when usable in the declared baseline. Evaluate accessible
   names/descriptions with browser role locators and the accessibility tree rather than reasoning
   solely from attributes; use `aria-labelledby`/`aria-describedby` only when needed. Do not replace
   native semantics with `div` + `role` unless there is a validated reason. Use ARIA roles/states
   only in permitted HTML contexts; an ARIA role does not implement keyboard behavior. See
   [semantics](semantics-and-names.md) and [forms](forms-errors-and-feedback.md).
4. **Implement keyboard and focus semantics.** Inspect tab sequence, visible focus, skip navigation,
   heading/landmark discovery, composite widget key maps, focus restoration after overlays, Escape
   handling, keyboard traps, focus obscured by sticky regions and the relationship between selected
   state and DOM focus. For a modal dialog, implement modality and test focus on open, internal
   sequence, dismissal and return. For tabs/trees/grids follow the appropriate APG pattern *as
   design guidance* and verify the actual browser/AT interactions. See [keyboard and
   focus](keyboard-focus-and-dialogs.md) and
   [widgets](composite-widgets-and-structured-alternatives.md).
5. **Cover perceivability, input and feedback.** Inspect text/non-text contrast, spacing, readable
   labels, error association, 200%-text resizing, 320-CSS-px reflow, 400%-zoom in a real browser
   when relevant, 24-CSS-px target-size AA requirements *including exceptions*, alternative non-drag
   single-pointer operation for draggable interactions, reduced motion and explicit programmatically
   exposed status changes. Do not substitute keyboard-only support for WCAG 2.2 SC 2.5.7. Check that
   `aria-live` does not spam progress updates and that status announcements have correct urgency.
   See [perception and input](contrast-reflow-motion-pointer.md) and
   [feedback](forms-errors-and-feedback.md).
6. **Choose the right accessibility evaluation methods.** Use automated tools (e.g. axe-core) for
   detectable issues, browser-driven role/key/geometry assertions for behavioral contracts, manual
   review for contextual correctness and a sampled browser/AT matrix for usability, with users with
   disabilities when feasible. Attribute findings to the *tool/version and test scope*. Never treat
   a clean scanner as a WCAG conformance certificate. WCAG-EM 2.0 provides a five-step evaluation
   method and **does not add WCAG requirements**. See
   [testing](testing-oracles-and-assistive-technology.md) and
   [research](scientific-evidence-and-limits.md).
7. **Create negative oracles and measure what they detect.** For each high-risk behavior, add an
   intentionally invalid fixture or mutation: missing name, disconnected error, broken arrow key,
   absent dialog modality, offscreen focus, inaccessible drag-only action, lost live region,
   insufficient contrast, lost non-text state. Require regression tests to reject the defect. A
   detected synthetic mutant is only test sensitivity for that fixture; it is **not** the expected
   detection rate on real applications. Record failures, untested paths and exact commands.
8. **Run and document in the actual supported environment.** Capture browser and AT versions, OS,
   viewport/zoom, input method, theme and locales, URLs/revisions, exact assertion results, manual
   notes and known false positives. When an environment is unavailable, mark `NOT EXECUTED` or
   `INCONCLUSIVE`; do not infer assistive technology announcements from DOM `role=status` alone.
   Issue acceptance only when the acceptance criteria are observed, not when a tool's installation
   succeeds. See [reporting](evaluation-matrix.md).
9. **Deliver scoped changes and traceability.** Return changed code, a criterion-by-criterion
   evidence matrix, reproduction steps, remediation rationale, regression tests including negative
   probes, a list of baseline environments actually exercised, residual risks and explicit
   unverified WCAG criteria. A local change does not need artificial project workflow paperwork;
   complete-feature work maps to existing `spec.md`, `plan.md`, `tasks.md` and evidence files.
   optional integration is configured separately by the consumer.


## Rules for interpreting WCAG and tests

- The **WCAG 2.2 Recommendation is normative**. WAI Understanding/Techniques and APG are supporting
  **informative** guidance. WCAG-EM 2.0 is a W3C Group Note and an **evaluation method, not an added
  criterion**. The ACT Rules Format governs rule descriptions; individual ACT rules are informative.
  A browser role assertion is not an evaluation of every relevant WCAG criterion.
- WCAG 2.2 AA includes **SC 2.4.11** Focus Not Obscured (Minimum), **SC 2.5.7** Dragging Movements
  and **SC 2.5.8** Target Size (Minimum); **SC 2.4.13** Focus Appearance is AAA. Do not accidentally
  impose AAA as the AA minimum. The 24 × 24 CSS-px target condition has
  spacing/equivalent/inline/UA/essential exceptions; the fixture's universal >=24 assertion is a
  *deliberately stricter fixture contract*, not a general WCAG verdict.
- WCAG 2.2 removes/obsoletes SC 4.1.1 Parsing; do not count it as a WCAG 2.2 AA failure. WAI-ARIA
  attributes do not make widgets interactive; focus and keyboard behavior must be implemented and
  tested. `aria-hidden="true"` must not hide a focusable interactive control from assistive
  technology.
- A text ratio ≥4.5:1 is a common **SC 1.4.3 normal text** baseline, with exceptions and different
  thresholds for large text; SC 1.4.11 addresses visual information required to identify interface
  components and graphic objects, not all borders. Measuring two fixture colors cannot establish
  site-wide contrast conformance.
- **Synthetic browser, scanner, manual review, assistive technology and real-user evidence are
  separate classes of evidence.** Do not invent tests, participants, benchmarks or compatibility.
  Successful keyboard input in Chromium does not establish NVDA/JAWS/VoiceOver functionality.
- Accessibility checks never override authorization, secret redaction or source-of-truth rules. For
  Ec, declarative settings use canonical YAML, but runtime status, sessions, audit and tests are
  operational data. This generic skill supplies techniques, not a competing product policy.


## Further references by decision

Read only the reference relevant to the unresolved decision. Research registers describe imported authorship, not current execution.

- [Accessible composite widgets and structured alternatives](composite-widgets-and-structured-alternatives.md): composite widgets and structured alternatives.
- [Perceptual and input constraints: what to verify](contrast-reflow-motion-pointer.md): contrast reflow motion pointer.
- [WCAG/REQ/AC evidence matrix and reporting template](evaluation-matrix.md): evaluation matrix.
- [Forms, errors and status messages](forms-errors-and-feedback.md): forms errors and feedback.
- [Keyboard/focus and modal dialog contract](keyboard-focus-and-dialogs.md): keyboard focus and dialogs.
- [Agent skill activation oracles](routing-and-selection-oracles.md): routing and selection oracles.
- [Peer-reviewed evidence: supported insights and limitations](scientific-evidence-and-limits.md): source provenance or a disputed recommendation.
- [Semantic HTML, roles, names and information structures](semantics-and-names.md): semantics and names.
- [Primary source ledger and decision log](sources-and-decisions.md): source provenance or a disputed recommendation.
- [Standards, versioning and defensible claims](standards-and-scope.md): standards and scope.
- [Testing layers, oracles and assistive technology](testing-oracles-and-assistive-technology.md): testing oracles and assistive technology.
