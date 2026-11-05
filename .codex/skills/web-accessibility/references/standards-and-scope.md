# Standards, versioning and defensible claims

## Authority hierarchy

1. **W3C Recommendation — WCAG 2.2:** testable success criteria and conformance requirements, Levels A/AA/AAA; latest Recommendation update verified as 12 Dec 2024. https://www.w3.org/TR/WCAG22/
2. **WAI-ARIA 1.2** and **ARIA in HTML** define roles/states and authoring constraints, but cannot implement behavior for authors: https://www.w3.org/TR/wai-aria-1.2/ ; https://www.w3.org/TR/html-aria/ (Recommendation 11 Aug 2026).
3. **WCAG-EM 2.0** (W3C Group Note 23 Jul 2026) describes a comprehensive sampling/evaluation/reporting method and does not add criteria: https://www.w3.org/TR/wcag-em-2/
4. **ACT Rules Format 1.1** (W3C Recommendation 5 Feb 2026) standardizes test rule format, not the definition of WCAG success criteria: https://www.w3.org/TR/act-rules-format/ . Individual ACT rules are informative: https://www.w3.org/WAI/standards-guidelines/act/rules/about/ .
5. **APG, Understanding WCAG, tutorials and techniques** are informative authoring/test implementation guidance, not new mandatory criteria: https://www.w3.org/WAI/ARIA/apg/ ; https://www.w3.org/WAI/WCAG22/understanding/ .
6. **Tool reports** are evidence with specific coverage limits, never a standards authority. See W3C on limitations: https://www.w3.org/WAI/test-evaluate/tools/selecting/

## Commonly misrepresented requirements

- WCAG 2.2 AA SC **2.4.11 Focus Not Obscured (Minimum)**: focusable component cannot be *entirely* obscured by author content, with notes/exceptions; this is not the stricter AAA SC **2.4.12**. SC **2.4.13 Focus Appearance** is **AAA**, not AA.
- WCAG 2.2 AA SC **2.5.8 Target Size (Minimum)**: at least 24×24 CSS px **or a specified exception** (spacing, equivalent, inline, UA control, essential); unconditionally failing every 20-pixel icon is not a faithful criterion test.
- WCAG 2.2 AA SC **2.5.7 Dragging Movements**: equivalent operation using a **single pointer without dragging** is required unless essential or UA-controlled; keyboard support alone does not satisfy this.
- WCAG 2.2 AA SC **1.4.10 Reflow**: the 320 CSS-px wide (or 256 high vertical writing) reference is related to specified viewport/zoom; exceptions exist for inherently two-dimensional content. A width-320 screenshot alone is not a test of native 400% zoom.
- WCAG 2.2 **SC 4.1.1 Parsing** is obsolete/removed: do not count as a 2.2 AA failure. Maintain valid DOM and IDs for functional reasons and other criteria.
- **Status messages SC 4.1.3**: when status messages arise without context change, assistive technology should be able to determine the message via role/properties. A script finding `role=status` does not prove an announcement by a screen reader.

## Minimum evaluation scope contract

Define pages/views and key processes; WCAG version/level; required technologies; accessibility support baseline (OS/browser/AT); supported input; complex widgets; permissions; locale/direction/theme/zoom; critical error states; third-party content; structured and random sampling when relevant; human evaluation competency; evidence retention. Refer to WCAG-EM 2 for its explicit five-step process.

Conformance claims require evaluating **all** applicable criteria at the declared level over the declared scoped digital product, not only accessible-name assertions or a sampled fixture. When only targeted criteria were tested, say `partial evaluation; no conformance determination`.
