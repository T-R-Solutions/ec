# Wayfinding, current location and applicable accessibility requirements

## W3C requirements vs design patterns

WCAG 2.2 **normative** success criteria to consider include: 2.4.1 Bypass Blocks (A), 2.4.2 Page Titled (A), 2.4.4 Link Purpose in Context (A), 2.4.5 Multiple Ways (AA, exception for process-result/step), 2.4.6 Headings and Labels (AA), 3.2.3 Consistent Navigation (AA), 3.2.4 Consistent Identification (AA). Other relevant criteria still apply; this is not a WCAG conformance checklist.

The WAI menu/page-structure tutorials and USWDS/GOV.UK components give implementation **guidance**, not additional WCAG criteria. A breadcrumb is useful for hierarchical sites but not automatically required on every page. An inaccessible CSS/JS-only breadcrumb can fail relevant criteria even if the sitemap is correct.

## Structural examples

- Use a named primary nav and a distinctly named local nav when both exist.
- Mark the current item with an exposed state, e.g. `aria-current="page"` or `location`, when appropriate; retain meaningful visible indication.
- Use an ordered list of breadcrumb ancestors and a current-page indicator; do not show a “back” path as the ancestor tree.
- Use meaningful page headings and titles including local scope when ambiguity exists.
- Provide skip navigation and keyboard/focus contract, and a readable alternative for dense graphics. Defer exact ARIA widget behavior to `web-accessibility`.
- Avoid assuming disclosure menus and application menus share identical keyboard contracts. Ordinary site navigation can use standard list/link semantics.

## Testing scope

Markup validation or Playwright role checks demonstrate selected semantics. Screen reader announcement behavior requires actual browser/AT testing; full WCAG conformance requires evaluation of the applicable criteria in the target product.

Sources: [W3C WCAG 2.2](https://www.w3.org/TR/WCAG22/), [WAI Menu Structure](https://www.w3.org/WAI/tutorials/menus/structure/), [WAI Region Labels](https://www.w3.org/WAI/tutorials/page-structure/labels/), [USWDS Breadcrumb](https://designsystem.digital.gov/components/breadcrumb/), [GOV.UK Breadcrumb](https://design-system.service.gov.uk/components/breadcrumbs/).
