# Keyboard/focus and modal dialog contract

## Test the entire workflow

Record initial focus, tab direction, event key, focus destination, activation result, invalid/denied states, contextual announcements and return path. Test both Tab and Shift+Tab; native controls should typically be in predictable source order, while arrow navigation belongs inside a composite widget. Avoid `tabindex` values greater than 0. Focus and selection are separate states.

### Recommended checks

- Keyboard-only navigation reaches each authorized function without traps; focus indicator is visible and not completely obscured by sticky header/dialog under SC 2.4.11 AA.
- A skip link can be activated by keyboard and moves focus to main content or changes its scroll target in a usable way.
- When a context is removed, move focus to a meaningful survivor; do not leave focus on detached nodes or hide focused content.
- A modal dialog traps interaction *semantically*: outside content is inert; focus enters dialog on open, remains in modal interactions, Escape behavior is explicit, dismissal restores focus to a reasonable invoker (unless workflow logically redirects). A native `<dialog>` using `showModal()` offers browser-supported modality; `show()` does not.
- Never rely on visually darkened backdrop alone to claim modality or focus confinement. Test focus and ability to activate underlying controls.
- If focus is replaced by the document body transiently during native dialog Tab wrapping, inspect whether **outside interactive controls are operable**, rather than treating any body focus as a confirmed escape. Browser implementations and AT behaviors differ.

### Test oracles

1. Open by keyboard; query role/name; inspect `:modal`, focus inside; traverse inner controls; verify no background interactive element is reachable; close via supported keyboard; check focus restoration.
2. Remove modal state (replace `showModal()` with `show()`) and require the modal oracle to fail.
3. Remove the accessible dialog title and require name-based locator to fail.
4. Disable focus styles and require a focus-visible test to fail; appearance alone is not a SC 2.4.13 AAA measurement unless indicator area/contrast is verified.
5. Check high-zoom/focus scroll and sticky overlays on target app; the synthetic workbench lacks complex overlays.

**Primary guidance:** https://www.w3.org/WAI/ARIA/apg/practices/keyboard-interface/ ; https://www.w3.org/WAI/ARIA/apg/patterns/dialog-modal/ ; https://www.w3.org/WAI/WCAG22/Understanding/focus-not-obscured-minimum .
