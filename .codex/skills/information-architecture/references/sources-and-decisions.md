# Source register, authority classification and technical decisions

> Imported authoring record. Research dates, consultations and execution claims below belong to the supplied source package; they were not reproduced in this adaptation. Referenced validation scripts, fixtures, product norms and logs are not bundled unless present in this folder. Verify their availability and provenance before use. No historical PASS is current acceptance.

**Verification date:** 2026-10-31. All citations below were accessed through public search and publisher/standard pages. Sources are not bundled or mirrored; this package includes original summaries only. Network access is not required for fixture testing.

## W3C normative requirement

- [W3C WCAG 2.2 Recommendation](https://www.w3.org/TR/WCAG22/): applicable normative success criteria such as 2.4.5 Multiple Ways (AA), 2.4.6 Headings and Labels (AA), 3.2.3 Consistent Navigation (AA) and 3.2.4 Consistent Identification (AA). Full applicability depends on the product and conformance scope. Do not confuse AA with AAA success criteria.

## W3C informative explanation and tutorials

- [Understanding SC 2.4.5 Multiple Ways](https://www.w3.org/WAI/WCAG22/Understanding/multiple-ways): process exception and alternate routes.
- [Understanding SC 2.4.6 Headings and Labels](https://www.w3.org/WAI/WCAG22/Understanding/headings-and-labels): descriptive labels.
- [Understanding SC 3.2.3 Consistent Navigation](https://www.w3.org/WAI/WCAG22/Understanding/consistent-navigation): relative repeated order, not an identical menu on every page.
- [Understanding SC 3.2.4 Consistent Identification](https://www.w3.org/WAI/WCAG22/Understanding/consistent-identification): repeated functions, not necessarily byte-identical labels.
- [WAI Menus Tutorial](https://www.w3.org/WAI/tutorials/menus/): structure/navigation and keyboard recommendations.
- [WAI Menu Structure](https://www.w3.org/WAI/tutorials/menus/structure/): lists, labeled navigation, current item.
- [WAI Labeling Regions](https://www.w3.org/WAI/tutorials/page-structure/labels/): distinguish global/local navs and landmarks.
- [WAI Page Structure Tutorial](https://www.w3.org/WAI/tutorials/page-structure/): headings and page regions.
- [WAI Navigation Design Curriculum](https://www.w3.org/WAI/curricula/designer-modules/navigation-design/): different navigation mechanisms.

## Government design systems and user research — guidance, not binding standards

- [U.S. Web Design System Breadcrumb](https://designsystem.digital.gov/components/breadcrumb/): hierarchical breadcrumb use, accessible ordered list and when not to use it; newer guidance says wrapping may be preferred to truncating.
- [U.S. Web Design System Side Navigation](https://designsystem.digital.gov/components/side-navigation/): current state, depth/breadth trade-offs, test navigation.
- [U.S. Web Design System Search](https://designsystem.digital.gov/components/search/): labeled search, query persistence, recovery and discoverability.
- [U.S. Web Design System In-page Navigation](https://designsystem.digital.gov/components/in-page-navigation/): heading-based local navigation for longer content.
- [GOV.UK Design System Breadcrumbs](https://design-system.service.gov.uk/components/breadcrumbs/): hierarchical relationship versus linear progress.
- [GOV.UK Service Manual — Moderated Usability Testing](https://www.gov.uk/service-manual/user-research/using-moderated-usability-testing): realistic tasks and observed sessions.
- [GOV.UK Service Manual — Planning Research](https://www.gov.uk/service-manual/user-research/plan-round-of-user-research): questions, participant recruitment, practice sessions.

## Academic peer-reviewed sources — see scientific evidence file

- [Wentzel et al. (2016) — 10.1016/j.ijmedinf.2015.12.003](https://doi.org/10.1016/j.ijmedinf.2015.12.003).
- [Schmettow & Sommer (2016) — 10.1080/0144929X.2016.1157207](https://doi.org/10.1080/0144929X.2016.1157207).
- [Toms (1999) — 10.1108/02640479910330714](https://doi.org/10.1108/02640479910330714).
- [Pirolli & Card (1999) — 10.1037/0033-295X.106.4.643](https://doi.org/10.1037/0033-295X.106.4.643).

## Decisions and evidence limits

1. The skill is **agnostic** and does not embed Ec's information hierarchy or change its standards. The Ec norms govern when the skill is used for Ec.
2. A taxonomy model, search aliases, URL policy and sidebar layout are candidates requiring a feature specification and research; no success rates or optimal click counts are asserted.
3. Card sorting is used for concept formation, with contrary experimental findings retained; it is not equivalent to measuring findability.
4. Synthetic model fixture uses YAML as test input and a JSON API payload only as an ephemeral runtime transport in one optional server path. Neither is a separate declarative configuration format for Ec.
5. Unit models and Chromium `set_content` tests check invariants on invented data and hash-based simulated routes. Production HTTP, route library, backend authorization, SSR, real permissions and user studies remain untested.
6. No live product or agent-routing evaluation ran; all examples are explicitly synthetic or proposed.
