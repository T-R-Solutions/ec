# Official standards, documented API behavior and evidence provenance

> Imported authoring record. Research dates, consultations and execution claims below belong to the supplied source package; they were not reproduced in this adaptation. Referenced validation scripts, fixtures, product norms and logs are not bundled unless present in this folder. Verify their availability and provenance before use. No historical PASS is current acceptance.

**Verification date:** 2026-11-01. Links below were checked through web retrieval during package authoring. When source wording or browser compatibility changes, revalidate the current version before new implementation decisions. This register is not a claim that all linked implementations were experimentally tested.

## Official primary specifications and authoritative guidance

| Source | Official location | Supports | Limits |
|---|---|---|---|
| WHATWG HTML Living Standard, forms | https://html.spec.whatwg.org/multipage/forms.html | Form controls, validity and submission semantics | Browser interop still requires actual tests |
| W3C WCAG 2.2 | https://www.w3.org/TR/WCAG22/ | Normative input assistance, labels, errors, keyboard, focus, status | Does not specify a universal form UI pattern |
| W3C WAI Forms tutorial | https://www.w3.org/WAI/tutorials/forms/ | Labels, grouping, instructions, validation, notifications | Informative techniques and examples |
| W3C WAI Labeling controls | https://www.w3.org/WAI/tutorials/forms/labels/ | Explicit label association and visible names | Doesn't define product labels |
| W3C WAI Grouping controls | https://www.w3.org/WAI/tutorials/forms/grouping/ | `fieldset`, `legend`, related options | Alternative equivalent semantics may apply |
| W3C WAI Validating input | https://www.w3.org/WAI/tutorials/forms/validation/ | Required controls and feedback, server remains authoritative | Not authorization guidance |
| W3C WAI User notification | https://www.w3.org/WAI/tutorials/forms/notifications/ | Summary and inline feedback; user correction | Multiple acceptable UI patterns |
| MDN Constraint validation guide | https://developer.mozilla.org/en-US/docs/Web/HTML/Guides/Constraint_validation | `checkValidity`, `reportValidity`, bypass edge cases | Documentation, not backend security |
| MDN Client-side form validation | https://developer.mozilla.org/en-US/docs/Learn_web_development/Extensions/Forms/Form_validation | Native HTML constraints + progressive JS validation | Client validation can be bypassed |
| Vue.js 3 Form Input Bindings | https://vuejs.org/guide/essentials/forms | `v-model` DOM binding and modifiers, IME behavior | Does not define data authority or schema |
| OWASP Input Validation Cheat Sheet | https://cheatsheetseries.owasp.org/cheatsheets/Input_Validation_Cheat_Sheet.html | Server trust boundary, parsing, shape/range validation | Does not authorize arbitrary regex defaults |
| GOV.UK Design System Error message | https://design-system.service.gov.uk/components/error-message/ | Error text, preserving input, proximity | Context-specific design system guidance |
| GOV.UK Design System Error summary | https://design-system.service.gov.uk/components/error-summary/ | Error summary navigation and focus | Not normative WCAG implementation |
| GOV.UK Recover from validation errors | https://design-system.service.gov.uk/patterns/validation/ | Submit-time validation rationale and recovery | Not a universal web law |
| Office for National Statistics Correct errors | https://service-manual.ons.gov.uk/design-system/patterns/correct-errors | Error summary and server/client validation distinction | Government service context |

**Source status:** all above URLs were checked during this research session. Source existence supports a citation; it does not prove a specific form implementation, code example or optimization succeeds in Ec.

## Peer-reviewed empirical studies (publisher/university record)

| Year | Peer-reviewed work | DOI | Specific use |
|---|---|---|---|
| 2007 | Bargas-Avila et al., *Usable error message presentation in the World Wide Web: Do not show errors right away* | 10.1016/j.intcom.2007.01.003 | Counterexample to universal instant error feedback |
| 2012 | Seckler et al., *User-friendly locations of error messages in web forms* | 10.1016/j.intcom.2012.03.002 | Error location/proximity under defined tasks |
| 2014 | Seckler et al., *Designing usable web forms: Empirical evaluation of web form improvement guidelines* | 10.1145/2556288.2557265 | Evaluating a bundle of improvements under controlled conditions |
| 2016 | Inal, *User-Friendly Locations of Error Messages in Web Forms: An Eye Tracking Study* | 10.16910/jemr.9.5.1 | Difference between attention and task-level outcomes |
| 2011 | Bargas-Avila et al., *Optimizing Date Entry Input Fields* | 10.1155/2011/202701 | Date control selection trade-offs |
| 2011 | Bargas-Avila et al., *Optimizing Multiple Selection Interface Elements* | 10.1155/2011/347171 | Checkbox/listbox trade-offs |

Specific author, design, participant counts, study implications and methodological limits appear in `scientific-evidence-and-limits.md`. Do not treat abstracts as a pooled meta-analysis, and do not invent statistics missing from sources.

## Explicit decisions and non-decisions

| Topic | Decision in this generic skill | Why / authority |
|---|---|---|
| Native forms | Prefer semantic HTML before custom controls | WHATWG and WAI support native labels/grouping/feedback |
| Validation location | Frontend assists; server authorizes and validates | WHATWG, MDN, OWASP |
| Error timing | Decision per task and cohort; initial default can be submit-time | Inconsistent empirical results; GOV.UK situational pattern |
| Error position | Field-proximal candidate and linked summary when appropriate | 2012/2016 experiments and WAI guidance; responsive caveat |
| User value retention | Don't silently clear user edits after errors | WAI/GOV.UK and preservation safety |
| Async acknowledgment | No success until server confirmation | Product semantic authority and engineering invariant, not claimed academic benchmark |
| Ec config truth | No competing file format; YAML authority outside generic skill | Normative Ec documents, not web research |
| Framework library | No mandatory forms library or state store | No comparative benchmark demonstrating a winner |
| Accessibility certification | Requires more than synthetic Chromium test | WCAG normative criteria and real assistive technology evidence |

## Source-to-test traceability

- Labels, legend and error summary → `validation/run_browser_tests.py` semantic checks.
- Native HTML constraint behavior → `#service-name`, `#region` checks in Chromium.
- Preserving input on server field errors and conflicts → synthetic Chromium tests and TS state model.
- Acknowledged save vs pending, conflict, stale overwrite → TypeScript/Node state transitions and Chromium fixture.
- Displaying untrusted messages as text → injected markup regression test and mutation oracle.
- Manual assistive technology and user tasks → planned only; no executed result.
