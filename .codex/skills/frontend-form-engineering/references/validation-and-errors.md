# Validation, error timing and user recovery

## Separate validation layers

1. **DOM constraints** (`required`, appropriate `type`, `min/max`, patterns) are a fast client-side UX aid where sufficient.
2. **Client cross-field rules** give contextual guidance on relationships and conditions that native constraints alone cannot represent.
3. **Server parsing and business validation** are authoritative. They must reject bypassed client checks and unauthorized mutations.
4. **Concurrency/revision checks** return conflicts or precondition failures, not “invalid field” by default.
5. **Service failures** (network, unavailable resources, timeout) are operational errors, not blame directed at a user's input.

WHATWG/MDN document `checkValidity()` and `reportValidity()`; `form.submit()` bypasses constraint validation, and custom `novalidate` can suppress native interactive validation. Neither a custom JS implementation nor native HTML alone establishes security. A form using custom validation should still preserve native labels, semantic controls and server checks.

## Timing policy is an explicit decision

Baseline option for many complex administrative forms: give input instructions before editing, validate on attempted submit, then revalidate a previously failed field on edit/blur to aid recovery; avoid noisy error announcements on every keystroke. This is a **design hypothesis**, not a WCAG timing requirement. Other timing patterns may be preferable if measured with representative users and input methods.

- In Bargas-Avila et al. (2007), two controlled studies found immediate error feedback performed worse than validation after form completion in their tested conditions.
- Inal (2016) and Seckler et al. (2012) examine *location* of error messages; these findings cannot establish a universally optimal *timing* or placement for all mobile, RTL, assistive-technology and complex forms.
- The GOV.UK design system recommends submit-time validation for its services and enables inline messages after submission. GOV.UK guidance is contextual, **not** a universal web standard.
- A genuine input mode (IME composition) must not be interrupted by premature formatting, validation or destructive rewriting. Vue 3 documents composition-specific `v-model` behavior.

## Accessible field and summary feedback

- Each field uses a visible programmatically associated label; optional hints use `aria-describedby` when appropriate.
- Error messages identify the exact field, describe the problem in text and offer the correction when known. Distinguish instructions from error labels; do not merely write `Invalid`.
- When invalid, associate the concrete error element with the input and use `aria-invalid` as appropriate. Do not rely on red borders or color alone.
- For multi-field error submission, a prominent error summary can link to each invalid input. Focus the summary when newly shown after submit (when this fits the app's focus strategy). Avoid repeated assertive announcements for every edit.
- Preserve the user's entered values, including correct ones, after validation errors and server rejection.
- Render server error text as text, never untrusted HTML. Avoid including credentials in errors, logs or debug traces.

## Error mapping contract

Prefer structured server error codes/paths from the owning API contract. Map each key only to a permitted field and sanitize user-facing messages. Unknown server errors belong to a general non-field area, **not** a fabricated field. Keep a stable error order for consistent summary navigation. E.g. request returned `conflict` is not mapped to `name` merely because the name field was last edited.

For a field corrected during an outstanding async validation, the old result is stale. Require response identification/epoch checks and do not re-show an error for an obsolete value.

## Negative tests

Missing error label, stale error after edit, summary that does not identify a field, invalid focus target, dismissal hiding error while submit remains blocked, vulnerable `innerHTML`, wrong error attached to a sibling field, `submit()` bypassing constraints when not intended, malformed server payload shown as success. Record actual DOM focus/roles in a browser and assistive-technology observations separately.

**Official basis:** WHATWG HTML Forms; W3C WAI Forms (Validation / User Notifications); WCAG 2.2 SC 3.3.1–3.3.4; GOV.UK Error message and Recover from validation errors. See `sources-and-decisions.md`.
