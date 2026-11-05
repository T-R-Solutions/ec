# Forms, errors and status messages

## Native control contract

Keep explicit labels, group related controls in `fieldset`/`legend`, provide helper text and instructions, and distinguish a control's accessible **name** from its optional **description**. Do not rely exclusively on colors or placeholder text. On invalid submission, present a visible error, associate it using `aria-describedby`, set `aria-invalid=true` only when it is invalid, and move focus or present an error summary consistent with workflow. A disabled submit button with no explanation can obscure why a form is blocked.

Errors are not all the same. Distinguish client validation, authorization denial, network failure, server rejection, pending approval and successful publication. **Do not assert success until the relevant system confirms it.** The generic fixture reports only synthetic draft state, not a server transaction.

## Live feedback

- Use `role=status`/polite announcement for nonurgent status updates (e.g. saved, queue changed) without moving focus; `role=alert` for urgent errors where immediate attention is warranted. Do not use `alert` indiscriminately for every streaming log line.
- For rapidly updating streams, coalesce/summarize announcements instead of reading every item. Provide pause or controlled updates where appropriate. Live region updates should occur within an element already present in the DOM when possible.
- Test DOM role/region presence, message updates and stability of focus; separately **listen** with a screen reader for actual announcement quality, timing and duplication.
- For confirmation dialogs, use the right accessible name/description, explicit destructive impact and focus restoration.
- When validation fails, correct the form field state/description; when valid submission occurs, remove the stale error association.

## Negative probes

Break `label[for]`, remove `aria-invalid`, point to non-existent error ID, remove the status role, or delete feedback after an action; ensure each regression oracle reports failure. Do not call the fixture's error-handling model a backend or Vue test.

**Sources:** https://www.w3.org/WAI/tutorials/forms/labels/ ; https://www.w3.org/WAI/tutorials/forms/instructions/ ; https://www.w3.org/WAI/WCAG22/Understanding/status-messages ; https://www.w3.org/WAI/ARIA/apg/patterns/dialog-modal/ .
