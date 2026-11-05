# Accessible composite widgets and structured alternatives

## Tabs

Use `tablist` with an accessible name, `tab` children with `aria-selected`, `aria-controls`, one active tab entry (`tabindex=0`) and `tabpanel` with `aria-labelledby`. Arrow keys move focus among tabs in a horizontal list; Home/End shortcuts are documented by APG. Automatic versus manual activation is a UX choice conditioned by predictable latency. Loading tab content may need manual activation. Verify `Tab` exits the tablist and enters panel content appropriately. `aria-selected` is not focus.

## Trees and hierarchical administration

Prefer a plain semantic nested list with disclosure buttons when sufficient; use `tree`/`treeitem` only for a real desktop-like tree with complete keyboard mapping (arrows, Home/End, selection, expansion and dynamic labeling). Roles without matching keyboard support can worsen assistive technology experience. Check multi-selection semantics and focus tracking against APG.

## Interactive grids vs data tables

Ordinary read-only records use `<table>` with captions and headers. `role=grid` implies row/column-navigation keyboard expectations and focus management in cells; it is not automatically better for dense datasets. Virtualization requires carefully preserved structure and announced changes when rows are replaced.

## Canvas, diagrams, drag and drop

A graphic-only canvas or node graph should expose a task-equivalent structured interface: list/tree to navigate nodes, inspector/form to edit properties, commands for adding/deleting dependencies, and explicit confirmation/feedback. Do not pretend a canvas is usable with keyboard just because it has a `tabindex`. WCAG 2.2 2.5.7 separately requires a **single-pointer alternative to dragging** where applicable; ensure actual click/tap controls perform the same action, not just keyboard shortcuts.

Test selection and reordering against the same domain operation, preserving stable IDs and permissions. The generic fixture includes reorder buttons and native HTML drag/drop, **not** a real DAG, access control or YAML editing.

**Guidance:** https://www.w3.org/WAI/ARIA/apg/patterns/tabs/ ; https://www.w3.org/WAI/ARIA/apg/patterns/treeview/ ; https://www.w3.org/WAI/ARIA/apg/patterns/grid/ ; https://www.w3.org/WAI/WCAG22/Understanding/dragging-movements .
