# Semantic HTML, roles, names and information structures

**Start from meaning, not ARIA syntax.** Identify actual actions, navigation, grouping, relationships and current states; then choose native HTML behavior. A button should be a `<button type="button">` if it performs an action, whereas navigation is an `<a href="…">`. Avoid `div role="button"` unless native elements cannot implement the documented behavior, and if used implement focus, activation, states and disabled semantics.

- Use one primary `<main>`, meaningful headings, properly labeled navigation landmarks and a skip link when repetitive navigation precedes content. Source order should match a logical reading/tab sequence.
- Use `<label for="id">` and uniquely identified inputs. Provide a visible label for recognizability; placeholder text alone is not a durable substitute. `aria-describedby` supplies context/error, `aria-labelledby` names from reference, and the name/description algorithm determines precedence.
- Use `<table>` with `<caption>` and `<th scope="col|row">` for tabular data; use native tables for read-only tabular content instead of `role=grid`. Use interactive ARIA grid *only* if its keyboard model and cell focus affordances are implemented and tested.
- Use `hidden`/`inert` appropriately to prevent focus on non-interactive content; do not leave focusable controls inside `aria-hidden="true"` containers. Do not conflate CSS visibility with accessibility-tree visibility.
- Distinguish accessible **name**, **description**, **role**, **value** and **state**. A `role=button` without an informative name and keyboard behavior is not functional accessibility.
- Query by role and accessible name in integration tests. Remember such an assertion is only evidence of the queried browser's accessibility mapping, not all screen reader announcements.

**Negative oracle examples:** remove a label's `for`, point `aria-labelledby` to an absent ID, turn a column header into `td`, hide a focused control in an `aria-hidden` ancestor, or change button to non-focusable `div`. Check that the relevant test fails, then restore the original.

**Primary sources:** [HTML-ARIA author requirements](https://www.w3.org/TR/html-aria/), [WAI-ARIA 1.2](https://www.w3.org/TR/wai-aria-1.2/), [WAI form labels](https://www.w3.org/WAI/tutorials/forms/labels/), [WAI forms tutorial](https://www.w3.org/WAI/tutorials/forms/). Accessible Name and Description Computation 1.2 was still a *Working Draft* on 12 October 2026; do not mislabel it as a Recommendation: https://www.w3.org/TR/2026/WD-accname-1.2-20261012/ .
