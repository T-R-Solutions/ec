# Skill routing scenarios and integration acceptance plan

> Use the destination's existing requirements and evidence format; named workflow documents are examples, not prerequisites. Optional SDD mapping is configured outside this skill. Ec-specific rules below apply only to an Ec consumer whose current contract establishes them.

**Purpose:** distinguish `css-layout-engineering` from adjacent general skills, without recursive activation or claiming observed autonomous agent behavior. `YES` means primary; `NO` means do not activate; `CONDITIONAL` means select only if a separate concrete CSS defect exists. These are **authored test prompts**, not actual agent-run results.

## 24 authored selection cases

| ID | Example request | Expected | Why / boundary |
|---|---|---|---|
| S01 | Grid main column causes page overflow at 390px | **YES** | Intrinsic sizing and responsive geometry |
| S02 | Flex toolbar button labels overlap at narrow widths | **YES** | Flex wrapping and shrink limits |
| S03 | Same status widget must switch columns when inspector is resized, viewport unchanged | **YES** | Container query |
| S04 | Render equivalent responsive layout in RTL and LTR | **YES** | Logical properties and browser verification |
| S05 | 320px page reflow reveals unreachably wide sidebar | **YES** | CSS layout geometry, partial WCAG concern |
| S06 | Test 200% font enlargement for clipped status and resource names | **YES** | CSS reflow probes; WCAG conclusion out of scope |
| S07 | Nested `1fr` grid refuses to shrink below long hash | **YES** | Min-content, `minmax`, `min-inline-size` |
| S08 | A data table should scroll inside its own region instead of the whole page | **YES** | Overflow confinement |
| S09 | Audit `@container` support and provide fallback | **YES** | Feature query and sizing |
| S10 | Build screenshot/geometry layout regression tests across viewport widths | **YES** | Measured rendering verification |
| S11 | Explain why @layer overrides appear ignored | **YES** | CSS cascade/layer mechanics |
| S12 | Implement adaptable two-column shell with retained keyboard source order | **YES** | Grid structure and responsive order |
| S13 | Pick accent colors and Ec logo typography | **NO** | Product identity/design-system owns |
| S14 | Create a new Vue composable for YAML editor state | **NO** | Vue reactive ownership; no CSS problem |
| S15 | Define Git and role scopes in canonical YAML | **NO** | Ec domain schemas/permissions own |
| S16 | Repair Rust scheduler race in PostgreSQL lease reservation | **NO** | Backend and distributed-systems owner |
| S17 | Write HTTP API versioning policy | **NO** | `http-api-contracts` owns |
| S18 | Design the navigation taxonomy and user journey | **NO** | Information architecture and design review |
| S19 | Run full WCAG 2.2 audit including screen readers and ARIA | **CONDITIONAL** | Accessibility owner primary; CSS only for distinct reflow defect |
| S20 | Add localization catalogs and date/time formatting | **NO** | `internationalization` owns; no layout issue specified |
| S21 | Implement Vue component props/events and lifecycle | **NO** | `vue3-engineering` owns |
| S22 | Build form validity state, field errors and submit handling | **CONDITIONAL** | Form skill primary; CSS only if explicit layout defect |
| S23 | Investigate layout shift during streaming logs | **CONDITIONAL** | Streaming/UI/performance primary; CSS if measured geometry implicated |
| S24 | Assess CI/CD DAG keyboard use and accessible alternative list | **CONDITIONAL** | Ec pipeline/UX/accessibility own; CSS for responsive layout mechanics |

No measured LLM agent results, confusion matrix, skill-selection precision or reliability are available. An authorized evaluator must record actual selections before reporting them.

## Ec feature integration acceptance probes — planned, **NOT EXECUTED**

| ID | App feature and objective | Required evidence to consider acceptance |
|---|---|---|
| A01 | Global navigation and context, desktop | Browser geometry, focus order, permissions and project workflow requirements |
| A02 | Project/subproject tree 320px | Real long names, tree navigation reachable, no root overflow |
| A03 | Project create Visual/Form/YAML | Each layout accessible on target sizes; cross-mode semantic parity separately validated |
| A04 | Pipeline DAG and inspector resize | Container width variation, full keyboard alternative, no hidden node meaning |
| A05 | Editor YAML and diff split panels | Resizing, scroll boundaries, meaningful lines/diagnostics, correct revision labels |
| A06 | Team membership and permission matrix | Matrix is usable, focusable and authorized actions are visible |
| A07 | Users, invitations, role lists | Long emails/translated labels, 200% text, no clipped confirmation |
| A08 | Plugins catalogue and installation | Catalog filters, permission details and states stay operable; not simulated execution |
| A09 | Agent pool list and health | Long capabilities and disconnected states remain distinguishable and reachable |
| A10 | Run/log streaming view | Logs scroll internally, error banners and cancel state preserved, no false status |
| A11 | Build artefacts table | Table scroll confinement, row identity/retention and secure downloads |
| A12 | Environments and approval gates | Destination/revision/risk visible, confirmation not lost in narrow viewport |
| A13 | Administration and audit | Dense tables, security controls, real server-confirmed states |
| A14 | Internationalization/RTL | Target locales, long resource paths, logical borders and focus order |
| A15 | Accessibility WCAG 2.2 AA | 320px equivalent actual zoom, 200% resize, manual keyboard/AT audit and exceptions |
| A16 | Browser and performance matrix | Target browsers/devices, deterministic screenshot baselines and measured responsiveness |

**Status:** A01–A16 require an actual Ec app and current `REQ/AC` contracts, neither supplied for this artifact. Do not copy synthetic fixture breakpoints, CSS selectors, statistics or screenshots into Ec acceptance claims. project workflow evidence and convergence are managed by the original project workflow, not by this skill.
