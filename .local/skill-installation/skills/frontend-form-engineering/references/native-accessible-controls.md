# Native accessible form controls and keyboard recovery

> Commands, fixture paths and historical execution claims from the source package are illustrative or imported evidence unless the corresponding files are present and independently checked; they are not current test results or bundled executable tools.

## Structural semantics

Use visible `<label for="...">` with unique `id` or equivalent nested label. Prefer native inputs, select, textarea, buttons and fieldsets to custom clickable divs. Related radio buttons or checkboxes should have a short `<legend>` and semantic grouping. Use text for error identification and visible focus indicators; colored borders alone are insufficient. The W3C WAI Forms tutorial covers labels, grouping, instructions, validation and feedback.

Where browser-native validation is suitable, HTML constraints help users early; custom validation may be needed for consistent business errors and summary handling. If using `novalidate`, own the full validation/error/focus experience and test it; do not disable native constraints without a reason. The HTML Constraint Validation API still exposes `checkValidity()` and `reportValidity()`.

## Descriptions, errors and summaries

A field hint should be associated with `aria-describedby`, without replacing the visible label. On an invalid attempted submission, attach an explicit error to the field, set `aria-invalid="true"` when appropriate, and update the description references without dropping the hint. A top-of-form summary can list anchor links for each current problem and be focused at submission; in an SPA, ensure the focus transition is perceivable and not a focus trap. Use `role="alert"` or live regions deliberately; rapidly re-announcing entire forms can be harmful.

Use `textContent` or equivalent safe text binding for server-supplied messages. Never insert raw server error strings through `innerHTML`. Avoid a message like “Invalid” without a field-specific cause and correction. Permission denied, connectivity lost and resource conflicts have different recovery paths.

## Keyboard and small screens

- Labels activate the correct controls; `Tab`, `Shift+Tab`, `Space`, arrow navigation in radios and `Enter` submit behavior must work.
- A custom composite widget needs explicit focus and keyboard behavior per its role, not merely `tabindex=0`.
- After validation, a focused summary links to meaningful field IDs. Verify focus is not stolen repeatedly while the user is correcting input.
- Long labels, 320px layouts, 200%/400% text zoom and RTL require separate observations; avoid width-dependent assumptions such as all errors appearing to the right.
- Field errors and summaries must remain perceivable with CSS disabled or high-contrast settings where applicable.

## Normative vs informative authority

WCAG 2.2 SC 1.3.1, 2.1.1, 2.4.3, 2.4.6, 2.4.7, 3.3.1–3.3.4, 3.3.7 and 4.1.2 (where applicable) are normative success criteria. WAI tutorials and GOV.UK examples are guidance patterns, not mandatory implementations for every product. Compliance is evaluated against WCAG and application behavior, not whether one screenshot uses a particular pattern.

## Required assessment evidence

Record DOM checks and keyboard route separately from accessibility tooling and human assistive-tech testing. Automated browser checks prove the tested DOM behavior but do not prove screen-reader announcement quality, comprehension, motor accessibility or WCAG 2.2 AA for the product.
