# Primary Sources, Status and Technical Decisions

> Imported authoring record. Research dates, consultations and execution claims below belong to the supplied source package; they were not reproduced in this adaptation. Referenced validation scripts, fixtures, product norms and logs are not bundled unless present in this folder. Verify their availability and provenance before use. No historical PASS is current acceptance.

Last reviewed: 2026-11-01. The links below are **sources**; the decisions are engineering recommendations for a portable skill, not product requirements. Treat latest web pages as moving targets; pin the release actually used for compatibility testing.

## Normative/authoritative specifications

| Source | Link | Supports | Cannot support |
|---|---|---|---|
| BCP 47 (RFC 5646) | https://www.rfc-editor.org/rfc/rfc5646 | Language-tag syntax and semantics | Which locales product ships |
| RFC 4647 | https://www.rfc-editor.org/rfc/rfc4647 | Language range matching and lookup | Automatically best translation quality |
| ECMA-402 2026 | https://tc39.es/ecma402/2026/ | Intl locale formatters, plural, collation | Reverse parsing or all zones updated |
| Unicode CLDR 49 core (2026-10-21) | https://www.unicode.org/reports/tr35/ | Locale data model | Runtime ICU version in this installation |
| Unicode CLDR 49 numbers | https://www.unicode.org/reports/tr35/tr35-numbers.html | Numbers, currency, plurals | Translation correctness |
| Unicode CLDR 49 dates | https://www.unicode.org/reports/tr35/tr35-dates.html | Time/civil presentation | Product's DST ambiguity policy |
| Unicode UAX #9 | https://www.unicode.org/reports/tr9/ | Bidirectional algorithm | Exact UI mirror behavior |
| IANA tzdb, latest 2026f (2026-10-27) | https://www.iana.org/time-zones | Mutable civil zone rules | Installed runtime tzdb matches latest |
| W3C direction guidance | https://www.w3.org/International/questions/qa-html-dir | `dir`, root direction, unknown input | Product's locale selection |
| W3C inline bidi | https://www.w3.org/International/articles/inline-bidi-markup/index | `bdi`, `dir=auto` | User-tested UX in all scripts |
| W3C HTML language declarations | https://www.w3.org/International/questions/qa-html-language-declarations | Root/nested `lang` | Human translation certification |

## Official/maintainer documentation and guidance

| Source | Link | Purpose |
|---|---|---|
| Vue I18n guide | https://vue-i18n.intlify.dev/guide/essentials/started | Plugin initialization, BCP47 tag guidance |
| Vue I18n pluralization | https://vue-i18n.intlify.dev/guide/essentials/pluralization | Toolkit-specific plural messages |
| Vue I18n fallback | https://vue-i18n.intlify.dev/guide/essentials/fallback | Tool-supported fallback behavior |
| Vue I18n HTML security | https://vue-i18n.intlify.dev/guide/essentials/syntax | HTML interpolation and XSS caveats |
| MDN Intl API | https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Intl | Frontend language APIs |
| Microsoft localization testing | https://github.com/MicrosoftDocs/globalization/blob/main/globalization/testing/how-to-perform-localization-testing.md | Functional, visual and linguistic testing split |
| Microsoft internationalization testing | https://github.com/MicrosoftDocs/globalization/blob/main/globalization/testing/how-to-perform-internationalization-testing.md | Engineering i18n validation process |

## Peer-reviewed research (see evidence limits)

- Mahajan et al., ICST 2018, DOI [10.1109/ICST.2018.00030](https://doi.org/10.1109/ICST.2018.00030).
- Mahajan et al., Software Testing, Verification and Reliability 2021, DOI [10.1002/stvr.1746](https://doi.org/10.1002/stvr.1746).
- Leiva & Alabau, ACM TOCHI 2015, DOI [10.1145/2701422](https://doi.org/10.1145/2701422).
- Rau & Liang, Ergonomics 2003, DOI [10.1080/00140130303527](https://doi.org/10.1080/00140130303527).
- Awwad & Slany, Mobile Information Systems 2016, DOI [10.1155/2016/2872067](https://doi.org/10.1155/2016/2872067).
- Kabát, International Journal of Interactive Mobile Technologies 2023, DOI [10.3991/ijim.v17i01.36367](https://doi.org/10.3991/ijim.v17i01.36367).

## Decisions and prohibitions

**Selected methods:** BCP 47 tag canonicalization and explicit supported-locale policy; `Intl` for locale presentation; Unicode direction isolation; stable technical identifiers; catalog placeholder checks; structural/behavioral/bidi/pseudo/linguistic test layers. Vue I18n is an optional appropriate host integration, not a mandated dependency of this generic skill.

**Avoid:** date-only to local instant conversions; hardcoded UTC offset calculations; manually enumerated plural rules; machine key translation; saving translated display numbers as authoritative numeric values; `innerHTML`/`v-html` on interpolated user input; `last response wins` locale switching; automatic claims of locale coverage from just a pseudo-locale test.

## Version-sensitive caution

The date- and number-format examples in this package run against the **installed Node/Chromium ICU**, not automatically against CLDR 49 or IANA 2026f. Rules changed as recently as October 2026. A production gate should record browser, OS, ICU/CLDR, tzdb and locale set and compare against product requirements.
